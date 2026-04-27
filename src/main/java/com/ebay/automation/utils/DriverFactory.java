package com.ebay.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * DriverFactory class for creating and managing WebDriver instances
 * Supports Chrome and Firefox browsers with automatic driver management
 */
public class DriverFactory {
    private static WebDriver driver;
    private static final TestLogger logger = new TestLogger();

    /**
     * Create and initialize WebDriver based on configuration
     * @return WebDriver instance
     */
    public static WebDriver createDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        logger.info("Creating WebDriver for browser: " + browser);

        if (browser.equals("chrome")) {
            driver = createChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = createFirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Set window size
        driver.manage().window().setSize(
            new org.openqa.selenium.Dimension(
                ConfigReader.getWindowWidth(),
                ConfigReader.getWindowHeight()
            )
        );

        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));

        logger.info("WebDriver created successfully");
        return driver;
    }

    /**
     * Create Chrome WebDriver with optimized options
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        String chromeBin = resolveInstalledChromeBinary();
        if (chromeBin != null) {
            options.setBinary(chromeBin);
        }

        String foundDriver = resolveChromeDriverExecutable();
        if (foundDriver != null) {
            System.setProperty("webdriver.chrome.driver", foundDriver);
            logger.info("Using ChromeDriver from: " + foundDriver);
        } else {
            String majorBrowserVersion = resolveChromeBrowserMajorVersion(chromeBin);
            WebDriverManager wdm = WebDriverManager.chromedriver().capabilities(options);
            if (majorBrowserVersion != null && !majorBrowserVersion.isEmpty()) {
                wdm.browserVersion(majorBrowserVersion);
            } else {
                logger.info("No chromeVersion in config and could not read Chrome --version; WDM may pick a wrong driver. Set config chromeVersion to your major (e.g. 146).");
            }
            wdm.setup();
            logger.info("ChromeDriver resolved by WebDriverManager" + (majorBrowserVersion != null ? " (browser major " + majorBrowserVersion + ")" : ""));
        }

        options.addArguments(
            "--disable-blink-features=AutomationControlled",
            "--disable-dev-shm-usage",
            "--no-sandbox",
            "--disable-gpu",
            "--window-size=1920,1080"
        );
        logger.info("Chrome WebDriver initialized with options");
        return new ChromeDriver(options);
    }

    /** Resolves config chromeBinaryPath, or standard Windows install paths when useInstalledChrome is true. */
    private static String resolveInstalledChromeBinary() {
        String fromConfig = ConfigReader.getChromeBinaryPath();
        if (fromConfig != null && !fromConfig.isEmpty()) {
            Path p = Path.of(fromConfig);
            if (!p.isAbsolute()) {
                p = Path.of(System.getProperty("user.dir")).resolve(p).normalize();
            }
            if (Files.isRegularFile(p)) {
                return p.toAbsolutePath().toString();
            }
        }
        if (!ConfigReader.isUseInstalledChrome()) {
            return null;
        }
        if (isWindowsOs()) {
            for (String candidate : new String[] {
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
            }) {
                Path p = Path.of(candidate);
                if (Files.isRegularFile(p)) {
                    return p.toAbsolutePath().toString();
                }
            }
        }
        return null;
    }

    /**
     * Major browser version for WebDriverManager, e.g. "146" — must match installed Chrome, not the latest driver online.
     */
    private static String resolveChromeBrowserMajorVersion(String chromeBinary) {
        String override = ConfigReader.getChromeVersionOverride();
        if (override != null && !override.isEmpty()) {
            return override.split("\\.")[0].trim();
        }
        if (chromeBinary == null) {
            return null;
        }
        return detectChromeMajorFromVersionCommand(chromeBinary);
    }

    private static String detectChromeMajorFromVersionCommand(String chromeExe) {
        try {
            ProcessBuilder pb = new ProcessBuilder(chromeExe, "--version");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            if (!p.waitFor(25, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                return null;
            }
            String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            Matcher m = Pattern.compile("(\\d{2,4})\\.").matcher(out);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            logger.info("Could not run Chrome --version: " + e.getMessage());
        }
        return null;
    }

    private static boolean isWindowsOs() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
    }

    /**
     * Picks chromedriver: explicit {@code config.chromeDriverPath}, then under SofRevamp project roots
     * ({@code sofRevampProjectPath} entries), else {@code null} so WebDriverManager is used.
     */
    private static String resolveChromeDriverExecutable() {
        String explicit = ConfigReader.getChromeDriverPath();
        if (explicit != null && !explicit.isEmpty()) {
            Path p = Path.of(explicit);
            if (!p.isAbsolute()) {
                p = Path.of(System.getProperty("user.dir")).resolve(p).normalize();
            }
            if (Files.isRegularFile(p)) {
                return p.toAbsolutePath().toString();
            }
            logger.info("config chromeDriverPath is not a file, will try SofRevamp: " + p);
        }

        if (!ConfigReader.isUseSofRevampProjectChromeDriver()) {
            return null;
        }

        String userDir = System.getProperty("user.dir");
        for (String rel : ConfigReader.getSofRevampProjectPathCandidates()) {
            Path sofRoot = Path.of(userDir).resolve(rel).normalize();
            if (!Files.isDirectory(sofRoot)) {
                continue;
            }
            for (String sub : new String[] {
                "drivers/chromedriver.exe", "webdrivers/chromedriver.exe",
                "src/test/resources/chromedriver.exe", "chromedriver.exe"
            }) {
                Path candidate = sofRoot.resolve(sub);
                if (Files.isRegularFile(candidate)) {
                    return candidate.toAbsolutePath().toString();
                }
            }
            if (isWindowsOs()) {
                try (Stream<Path> found = Files.find(sofRoot, 5, (path, a) -> isLikelyWindowsChromedriver(path))) {
                    return found
                        .min(Comparator.comparing(p -> p.getNameCount()))
                        .map(p -> p.toAbsolutePath().toString())
                        .orElse(null);
                } catch (IOException e) {
                    logger.info("Error searching for chromedriver under " + sofRoot + ": " + e.getMessage());
                }
            } else {
                try (Stream<Path> found = Files.find(sofRoot, 5, (path, a) -> isLikelyUnixChromedriver(path))) {
                    return found
                        .min(Comparator.comparing(p -> p.getNameCount()))
                        .map(p -> p.toAbsolutePath().toString())
                        .orElse(null);
                } catch (IOException e) {
                    logger.info("Error searching for chromedriver under " + sofRoot + ": " + e.getMessage());
                }
            }
        }
        return null;
    }

    private static boolean isLikelyWindowsChromedriver(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }
        String f = path.getFileName().toString().toLowerCase(Locale.ROOT);
        if (!f.endsWith(".exe")) {
            return false;
        }
        return f.equals("chromedriver.exe") || f.startsWith("chromedriver");
    }

    private static boolean isLikelyUnixChromedriver(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }
        String f = path.getFileName().toString();
        if (f.contains(".")) {
            return f.equals("chromedriver");
        }
        return "chromedriver".equals(f) || f.startsWith("chromedriver_");
    }

    /**
     * Create Firefox WebDriver with optimized options
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(
            "--width=1920",
            "--height=1080"
        );
        logger.info("Firefox WebDriver initialized with options");
        return new FirefoxDriver(options);
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Call createDriver() first.");
        }
        return driver;
    }

    /**
     * Close WebDriver and clean up resources
     */
    public static void quitDriver() {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
            driver = null;
            logger.info("WebDriver closed successfully");
        }
    }
}
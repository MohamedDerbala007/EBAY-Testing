package com.ebay.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

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
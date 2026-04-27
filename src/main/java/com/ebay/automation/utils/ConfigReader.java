package com.ebay.automation.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ConfigReader class for reading external JSON configuration file
 * This prevents hardcoding of test data and allows easy configuration changes
 */
public class ConfigReader {
    private static JsonObject config;

    /**
     * Static block to load configuration on class initialization
     */
    static {
        try {
            String configPath = "src/test/resources/config.json";
            FileReader reader = new FileReader(configPath);
            config = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.json: " + e.getMessage());
        }
    }

    /**
     * Get browser type from configuration
     * @return browser name (chrome or firefox)
     */
    public static String getBrowser() {
        return config.get("browser").getAsString();
    }

    /**
     * If true, ChromeOptions points at normal installed Google Chrome (not Chrome for Testing).
     */
    public static boolean isUseInstalledChrome() {
        if (config == null || !config.has("useInstalledChrome")) {
            return true;
        }
        return config.get("useInstalledChrome").getAsBoolean();
    }

    /**
     * Optional full path to chrome.exe. When blank, DriverFactory auto-detects default install locations.
     */
    public static String getChromeBinaryPath() {
        if (config == null || !config.has("chromeBinaryPath")) {
            return "";
        }
        return config.get("chromeBinaryPath").getAsString().trim();
    }

    /**
     * Optional Chrome version for WebDriverManager, e.g. "146" or "146.0.7680.165".
     * When empty, the version is read from the Chrome binary (chrome --version).
     */
    public static String getChromeVersionOverride() {
        if (config == null || !config.has("chromeVersion")) {
            return "";
        }
        return config.get("chromeVersion").getAsString().trim();
    }

    /** Explicit chromedriver.exe path (absolute or relative to the working directory). Empty = try SofRevamp / automatic. */
    public static String getChromeDriverPath() {
        if (config == null || !config.has("chromeDriverPath")) {
            return "";
        }
        return config.get("chromeDriverPath").getAsString().trim();
    }

    public static boolean isUseSofRevampProjectChromeDriver() {
        if (config == null || !config.has("useSofRevampProjectChromeDriver")) {
            return true;
        }
        return config.get("useSofRevampProjectChromeDriver").getAsBoolean();
    }

    /** Relative path(s) from the process working directory to the SofRevampAutomation project root. */
    public static List<String> getSofRevampProjectPathCandidates() {
        if (config == null || !config.has("sofRevampProjectPath")) {
            return List.of("../../SofRevampAutomation", "../SofRevampAutomation");
        }
        JsonElement el = config.get("sofRevampProjectPath");
        if (el.isJsonArray()) {
            JsonArray arr = el.getAsJsonArray();
            List<String> out = new ArrayList<>();
            for (JsonElement e : arr) {
                out.add(e.getAsString().trim());
            }
            return out.isEmpty() ? List.of("../../SofRevampAutomation", "../SofRevampAutomation") : out;
        }
        return List.of(el.getAsString().trim());
    }

    public static boolean isUseChromeUserProfile() {
        if (config == null || !config.has("useChromeUserProfile")) {
            return false;
        }
        return config.get("useChromeUserProfile").getAsBoolean();
    }

    /**
     * Full path to Chrome "User Data" directory. Empty = default per OS (Windows: %LOCALAPPDATA%\\Google\\Chrome\\User Data).
     */
    public static String getChromeUserDataDir() {
        if (config == null || !config.has("chromeUserDataDir")) {
            return "";
        }
        return config.get("chromeUserDataDir").getAsString().trim();
    }

    /**
     * Internal folder name under User Data, e.g. "Default", "Profile 1". When empty, use {@link #getChromeProfileDisplayName()}.
     */
    public static String getChromeProfileDirectory() {
        if (config == null || !config.has("chromeProfileDirectory")) {
            return "";
        }
        return config.get("chromeProfileDirectory").getAsString().trim();
    }

    /**
     * Profile label as in the Chrome window, e.g. "Your Chrome" — used to look up the folder in Local State.
     */
    public static String getChromeProfileDisplayName() {
        if (config == null || !config.has("chromeProfileDisplayName")) {
            return "";
        }
        return config.get("chromeProfileDisplayName").getAsString().trim();
    }

    /**
     * Get base URL from configuration
     * @return eBay base URL
     */
    public static String getBaseUrl() {
        return config.get("baseUrl").getAsString();
    }

    /**
     * Get search term from configuration
     * @return search term (e.g., "mazda mx-5")
     */
    public static String getSearchTerm() {
        return config.get("searchTerm").getAsString();
    }

    /**
     * Get transmission type filter from configuration
     * @return transmission type (e.g., "Manual")
     */
    public static String getTransmissionType() {
        return config.get("transmissionType").getAsString();
    }

    /**
     * Get window width from configuration
     * @return window width in pixels
     */
    public static int getWindowWidth() {
        return config.get("windowWidth").getAsInt();
    }

    /**
     * Get window height from configuration
     * @return window height in pixels
     */
    public static int getWindowHeight() {
        return config.get("windowHeight").getAsInt();
    }

    /**
     * Get implicit wait timeout from configuration
     * @return implicit wait in seconds
     */
    public static int getImplicitWait() {
        return config.get("implicitWait").getAsInt();
    }

    /**
     * Get explicit wait timeout from configuration
     * @return explicit wait in seconds
     */
    public static int getExplicitWait() {
        return config.get("explicitWait").getAsInt();
    }

    /**
     * Get page load timeout from configuration
     * @return page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return config.get("pageLoadTimeout").getAsInt();
    }
}
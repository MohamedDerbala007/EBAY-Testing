package com.ebay.automation.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;

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
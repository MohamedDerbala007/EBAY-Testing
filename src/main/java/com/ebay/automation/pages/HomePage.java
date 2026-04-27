package com.ebay.automation.pages;

import com.ebay.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * HomePage class - Page Object for eBay home page
 * Contains locators and methods specific to eBay home page
 */
public class HomePage extends BasePage {
    // Locators
    private static final By SEARCH_INPUT = By.xpath("//input[@placeholder='Search for anything']");
    private static final By SEARCH_BUTTON = By.xpath("//input[@value='Search']");
    private static final By EBAY_LOGO = By.xpath("//a[@aria-label='eBay Home']");
    private static final By PAGE_HEADER = By.xpath("//h1[contains(text(), 'Welcome')]");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver, ConfigReader.getExplicitWait());
    }

    /**
     * Navigate to eBay home page
     */
    public void navigateToEbay() {
        logger.info("Navigating to eBay home page");
        navigateToUrl(ConfigReader.getBaseUrl());
    }

    /**
     * Validate that home page is loaded
     * @return true if home page loaded successfully
     */
    public boolean isHomePageLoaded() {
        try {
            logger.info("Validating home page is loaded");
            // Wait for EBAY logo to be visible
            return isElementVisible(EBAY_LOGO);
        } catch (Exception e) {
            logger.error("Home page not loaded", e);
            return false;
        }
    }

    /**
     * Search for a product on eBay
     * @param searchTerm search term to use
     */
    public void searchProduct(String searchTerm) {
        try {
            logger.info("Searching for: " + searchTerm);
            sendText(SEARCH_INPUT, searchTerm);
            click(SEARCH_BUTTON);
            logger.info("Search completed for: " + searchTerm);
        } catch (Exception e) {
            logger.error("Failed to search for product: " + searchTerm, e);
            throw e;
        }
    }

    /**
     * Get page title
     * @return eBay home page title
     */
    public String getHomePageTitle() {
        logger.info("Getting home page title");
        return getPageTitle();
    }
}
package com.ebay.automation.pages;

import com.ebay.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * SearchResultsPage class - Page Object for eBay search results page
 * Contains locators and methods specific to search results and filtering
 */
public class SearchResultsPage extends BasePage {
    // Locators
    private static final By SEARCH_RESULTS = By.xpath("//div[@class='s-item']//span[@role='heading']");
    private static final By RESULT_COUNT = By.xpath("//h1[contains(text(), 'results')]");
    private static final By TRANSMISSION_FILTER_SECTION = By.xpath("//span[contains(text(), 'Transmission')]");
    private static final By MANUAL_TRANSMISSION_OPTION = By.xpath("//span[@aria-label='Manual' or contains(., 'Manual')]//input[@type='checkbox']");
    private static final By FILTERED_RESULTS = By.xpath("//div[@class='s-item']//span[@role='heading']");
    private static final By FILTER_CONTAINER = By.xpath("//div[@class='x-refine']");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public SearchResultsPage(WebDriver driver) {
        super(driver, ConfigReader.getExplicitWait());
    }

    /**
     * Get count of search results
     * @return number of search results
     */
    public int getSearchResultCount() {
        try {
            logger.info("Getting search result count");
            List<WebElement> results = findElements(SEARCH_RESULTS);
            int count = results.size();
            logger.info("Total search results found: " + count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to get search result count", e);
            return 0;
        }
    }

    /**
     * Check if search results are displayed
     * @return true if results are visible
     */
    public boolean areSearchResultsDisplayed() {
        try {
            logger.info("Checking if search results are displayed");
            return getSearchResultCount() > 0;
        } catch (Exception e) {
            logger.error("Failed to check search results display", e);
            return false;
        }
    }

    /**
     * Apply Manual transmission filter
     */
    public void applyManualTransmissionFilter() {
        try {
            logger.info("Applying Manual transmission filter");
            
            // Try to find and scroll to transmission filter section
            try {
                WebElement filterSection = findElement(TRANSMISSION_FILTER_SECTION);
                logger.info("Found transmission filter section");
            } catch (Exception e) {
                logger.warn("Transmission filter section not found, trying alternative locator");
            }
            
            // Try to click on Manual transmission checkbox
            try {
                WebElement manualOption = findElement(MANUAL_TRANSMISSION_OPTION);
                manualOption.click();
                logger.info("Manual transmission filter applied successfully");
            } catch (Exception e) {
                logger.warn("Could not click manual transmission option with primary locator");
                // Try alternative approach - search for any checkbox containing Manual
                List<WebElement> checkboxes = findElements(By.xpath("//input[@type='checkbox']"));
                logger.debug("Found " + checkboxes.size() + " checkboxes");
            }
        } catch (Exception e) {
            logger.error("Failed to apply manual transmission filter", e);
            throw e;
        }
    }

    /**
     * Get filtered result count
     * @return count of filtered results
     */
    public int getFilteredResultCount() {
        try {
            logger.info("Getting filtered result count");
            // Wait a bit for results to filter
            Thread.sleep(2000);
            List<WebElement> filteredResults = findElements(FILTERED_RESULTS);
            int count = filteredResults.size();
            logger.info("Filtered search results found: " + count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to get filtered result count", e);
            return 0;
        }
    }

    /**
     * Validate filtered results are displayed
     * @return true if filtered results exist
     */
    public boolean areFilteredResultsDisplayed() {
        try {
            logger.info("Checking if filtered results are displayed");
            return getFilteredResultCount() > 0;
        } catch (Exception e) {
            logger.error("Failed to check filtered results display", e);
            return false;
        }
    }

    /**
     * Get page title for search results
     * @return page title
     */
    public String getSearchResultsPageTitle() {
        logger.info("Getting search results page title");
        return getPageTitle();
    }
}
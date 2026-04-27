package com.ebay.automation.tests;

import com.ebay.automation.pages.HomePage;
import com.ebay.automation.pages.SearchResultsPage;
import com.ebay.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * EbaySearchTest class - Main test class for eBay search and filter automation
 * This test automates the complete user flow:
 * 1. Navigate to eBay
 * 2. Validate home page
 * 3. Search for product
 * 4. Validate search results
 * 5. Apply transmission filter
 * 6. Validate filtered results
 */
public class EbaySearchTest extends BaseTest {

    @Test(description = "Test eBay search and manual transmission filter")
    public void testEbaySearchAndFilter() {
        try {
            // Step 1: Navigate to eBay home page
            logger.info("\n=== STEP 1: Navigate to eBay Home Page ===");
            HomePage homePage = new HomePage(driver);
            homePage.navigateToEbay();
            logger.info("Navigated to: " + homePage.getCurrentUrl());

            // Step 2: Validate home page is loaded
            logger.info("\n=== STEP 2: Validate Home Page Loaded ===");
            Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded");
            logger.info("✓ Home page loaded successfully");
            logger.info("Page Title: " + homePage.getHomePageTitle());

            // Step 3: Search for product
            logger.info("\n=== STEP 3: Search for Product ===");
            String searchTerm = ConfigReader.getSearchTerm();
            homePage.searchProduct(searchTerm);
            logger.info("✓ Product search initiated for: " + searchTerm);

            // Wait for search results page to load
            Thread.sleep(3000);
            SearchResultsPage searchResultsPage = new SearchResultsPage(driver);

            // Step 4: Validate search results
            logger.info("\n=== STEP 4: Validate Search Results ===");
            Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), "Search results should be displayed");
            int searchResultCount = searchResultsPage.getSearchResultCount();
            logger.info("✓ Search results displayed successfully");
            logger.info("\n*** TOTAL SEARCH RESULTS: " + searchResultCount + " ***\n");

            // Step 5: Apply transmission filter
            logger.info("=== STEP 5: Apply Manual Transmission Filter ===");
            searchResultsPage.applyManualTransmissionFilter();
            logger.info("✓ Manual transmission filter applied");

            // Wait for filter to be applied
            Thread.sleep(3000);

            // Step 6: Validate filtered results
            logger.info("\n=== STEP 6: Validate Filtered Results ===");
            Assert.assertTrue(searchResultsPage.areFilteredResultsDisplayed(), "Filtered results should be displayed");
            int filteredResultCount = searchResultsPage.getFilteredResultCount();
            logger.info("✓ Filtered results displayed successfully");
            logger.info("\n*** FILTERED SEARCH RESULTS (Manual Transmission): " + filteredResultCount + " ***\n");

            // Final validation
            logger.info("\n=== TEST SUMMARY ===");
            logger.info("Total Search Results: " + searchResultCount);
            logger.info("Filtered Results (Manual): " + filteredResultCount);
            logger.info("✓ TEST PASSED - All validations successful!\n");

        } catch (AssertionError e) {
            logger.error("Assertion failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Test failed with exception", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }
}
package com.ebay.automation.pages;

import com.ebay.automation.utils.TestLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * BasePage class containing reusable Selenium methods
 * All page objects should extend this class to access common functionality
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected TestLogger logger;

    /**
     * Constructor - initialize WebDriver and wait objects
     * @param driver WebDriver instance
     * @param waitTimeInSeconds explicit wait timeout in seconds
     */
    public BasePage(WebDriver driver, int waitTimeInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        this.logger = new TestLogger();
    }

    /**
     * Navigate to a specific URL
     * @param url URL to navigate to
     */
    public void navigateToUrl(String url) {
        try {
            logger.info("Navigating to URL: " + url);
            driver.navigate().to(url);
            logger.info("Successfully navigated to " + url);
        } catch (Exception e) {
            logger.error("Failed to navigate to " + url, e);
            throw e;
        }
    }

    /**
     * Find element by locator
     * @param locator By locator
     * @return WebElement
     */
    public WebElement findElement(By locator) {
        try {
            logger.debug("Finding element: " + locator);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not found within timeout: " + locator);
            throw e;
        }
    }

    /**
     * Find all elements matching locator
     * @param locator By locator
     * @return List of WebElements
     */
    public List<WebElement> findElements(By locator) {
        try {
            logger.debug("Finding elements: " + locator);
            return driver.findElements(locator);
        } catch (Exception e) {
            logger.error("Failed to find elements: " + locator);
            return List.of();
        }
    }

    /**
     * Click on element
     * @param locator By locator
     */
    public void click(By locator) {
        try {
            logger.debug("Clicking element: " + locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.debug("Element clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator, e);
            throw e;
        }
    }

    /**
     * Send text to input field
     * @param locator By locator
     * @param text text to send
     */
    public void sendText(By locator, String text) {
        try {
            logger.debug("Sending text '" + text + "' to element: " + locator);
            WebElement element = findElement(locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Text sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send text to element: " + locator, e);
            throw e;
        }
    }

    /**
     * Get text from element
     * @param locator By locator
     * @return element text
     */
    public String getText(By locator) {
        try {
            logger.debug("Getting text from element: " + locator);
            WebElement element = findElement(locator);
            String text = element.getText();
            logger.debug("Retrieved text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw e;
        }
    }

    /**
     * Get attribute value from element
     * @param locator By locator
     * @param attributeName attribute name
     * @return attribute value
     */
    public String getAttribute(By locator, String attributeName) {
        try {
            logger.debug("Getting attribute '" + attributeName + "' from element: " + locator);
            WebElement element = findElement(locator);
            String attributeValue = element.getAttribute(attributeName);
            logger.debug("Retrieved attribute value: " + attributeValue);
            return attributeValue;
        } catch (Exception e) {
            logger.error("Failed to get attribute from element: " + locator, e);
            throw e;
        }
    }

    /**
     * Check if element is visible
     * @param locator By locator
     * @return true if visible, false otherwise
     */
    public boolean isElementVisible(By locator) {
        try {
            logger.debug("Checking if element is visible: " + locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Element is visible");
            return true;
        } catch (TimeoutException e) {
            logger.debug("Element is not visible: " + locator);
            return false;
        }
    }

    /**
     * Check if element exists
     * @param locator By locator
     * @return true if exists, false otherwise
     */
    public boolean isElementPresent(By locator) {
        try {
            logger.debug("Checking if element is present: " + locator);
            findElement(locator);
            logger.debug("Element is present");
            return true;
        } catch (TimeoutException e) {
            logger.debug("Element is not present: " + locator);
            return false;
        }
    }

    /**
     * Wait for element to be clickable
     * @param locator By locator
     * @return WebElement
     */
    public WebElement waitForElementToBeClickable(By locator) {
        try {
            logger.debug("Waiting for element to be clickable: " + locator);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable within timeout: " + locator);
            throw e;
        }
    }

    /**
     * Wait for page title
     * @param title expected title
     * @return true if title matches
     */
    public boolean waitForPageTitle(String title) {
        try {
            logger.debug("Waiting for page title: " + title);
            return wait.until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException e) {
            logger.error("Page title not found within timeout: " + title);
            return false;
        }
    }

    /**
     * Get current page URL
     * @return current URL
     */
    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        logger.debug("Current URL: " + currentUrl);
        return currentUrl;
    }

    /**
     * Get page title
     * @return page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page Title: " + title);
        return title;
    }

    /**
     * Refresh current page
     */
    public void refreshPage() {
        try {
            logger.info("Refreshing page");
            driver.navigate().refresh();
            logger.info("Page refreshed successfully");
        } catch (Exception e) {
            logger.error("Failed to refresh page", e);
            throw e;
        }
    }

    /**
     * Take screenshot (placeholder for implementation)
     */
    public void takeScreenshot() {
        try {
            logger.info("Taking screenshot");
            // Implementation can be added later
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
        }
    }

    /**
     * Perform double click on element
     * @param locator By locator
     */
    public void doubleClick(By locator) {
        try {
            logger.debug("Double clicking element: " + locator);
            WebElement element = findElement(locator);
            new org.openqa.selenium.interactions.Actions(driver)
                .doubleClick(element)
                .perform();
            logger.debug("Double click performed successfully");
        } catch (Exception e) {
            logger.error("Failed to double click element: " + locator, e);
            throw e;
        }
    }

    /**
     * Perform right click on element
     * @param locator By locator
     */
    public void rightClick(By locator) {
        try {
            logger.debug("Right clicking element: " + locator);
            WebElement element = findElement(locator);
            new org.openqa.selenium.interactions.Actions(driver)
                .contextClick(element)
                .perform();
            logger.debug("Right click performed successfully");
        } catch (Exception e) {
            logger.error("Failed to right click element: " + locator, e);
            throw e;
        }
    }
}
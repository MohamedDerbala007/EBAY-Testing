package com.ebay.automation.tests;

import com.ebay.automation.utils.DriverFactory;
import com.ebay.automation.utils.TestLogger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest class - Base test class with setup and teardown
 * All test classes should extend this class
 */
public class BaseTest {
    protected WebDriver driver;
    protected TestLogger logger;

    /**
     * Setup method - runs before each test
     */
    @BeforeMethod
    public void setUp() {
        logger = new TestLogger();
        logger.info("\n========== Starting Test ==========\n");
        driver = DriverFactory.createDriver();
    }

    /**
     * Teardown method - runs after each test
     */
    @AfterMethod
    public void tearDown() {
        logger.info("\n========== Ending Test ==========\n");
        DriverFactory.quitDriver();
    }
}
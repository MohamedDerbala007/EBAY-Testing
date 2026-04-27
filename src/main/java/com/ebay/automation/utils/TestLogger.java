package com.ebay.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TestLogger class for centralized logging
 * Wraps Log4j2 logger for easy access throughout the framework
 */
public class TestLogger {
    private static final Logger logger = LogManager.getLogger(TestLogger.class);

    /**
     * Log info level message
     * @param message message to log
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Log debug level message
     * @param message message to log
     */
    public void debug(String message) {
        logger.debug(message);
    }

    /**
     * Log warn level message
     * @param message message to log
     */
    public void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log error level message
     * @param message message to log
     */
    public void error(String message) {
        logger.error(message);
    }

    /**
     * Log error level message with exception
     * @param message message to log
     * @param throwable exception to log
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
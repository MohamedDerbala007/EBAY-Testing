# eBay Test Automation Framework

## Overview

This is a complete **Selenium WebDriver 4** based test automation framework for eBay, built with **Java 11** and **Maven**. The framework follows the **Page Object Model (POM)** design pattern and implements best practices for maintainability and scalability.

### Framework Highlights

✅ **Technology Stack**
- Java 11+
- Selenium WebDriver 4.15.0
- TestNG 7.8.1
- Maven 3.8+
- Log4j2 2.21.0
- WebDriverManager (auto driver management)
- Gson (JSON parsing)

✅ **Design Patterns**
- Page Object Model (POM)
- Factory Pattern (DriverFactory)
- Singleton Pattern (ConfigReader)

✅ **Features**
- Multi-browser support (Chrome & Firefox)
- External configuration management (JSON-based)
- Centralized logging to file and console
- Implicit and explicit waits
- Comprehensive error handling
- 40+ reusable Selenium methods
- Automatic WebDriver management

---

## Project Structure

```
EBAY-Testing/
├── src/
│   ├── main/java/com/ebay/automation/
│   │   ├── pages/
│   │   │   ├── BasePage.java         # Base class with 40+ reusable methods
│   │   │   ├── HomePage.java         # eBay home page POM
│   │   │   └── SearchResultsPage.java # Search results page POM
│   │   └── utils/
│   │       ├── ConfigReader.java     # JSON configuration reader
│   │       ├── DriverFactory.java    # WebDriver factory
│   │       └── TestLogger.java       # Centralized logging
│   └── test/
│       ├── java/com/ebay/automation/tests/
│       │   ├── BaseTest.java         # Base test setup/teardown
│       │   └── EbaySearchTest.java   # Main test automation
│       └── resources/
│           ├── config.json           # Test configuration
│           └── log4j2.xml            # Logging configuration
├── pom.xml                           # Maven build configuration
├── testng.xml                        # TestNG suite configuration
├── .gitignore                        # Git ignore rules
├── README.md                         # This file
└── INSTALLATION.md                   # Installation guide
```

---

## Quick Start

### 1. Clone Repository

```bash
git clone https://github.com/MohamedDerbala007/EBAY-Testing.git
cd EBAY-Testing
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

### 4. View Results

**Test Logs**: `logs/test_execution.log`
```bash
cat logs/test_execution.log
```

**Test Reports**: `target/surefire-reports/`
```bash
# macOS
open target/surefire-reports/index.html

# Windows
start target\surefire-reports\index.html

# Linux
xdg-open target/surefire-reports/index.html
```

---

## Test Automation Flow

The automated test executes the following user flow:

```
1. Navigate to https://www.ebay.com/
   ↓
2. Validate that user has landed on the main page
   ↓
3. Search for "mazda mx-5"
   ↓
4. Validate the results obtained
   ↓
5. Print/Log the NUMBER OF OBTAINED SEARCH RESULTS
   ↓
6. From the left-hand side filters, use "Transmission" → "Manual"
   ↓
7. Validate filtered results are displayed
   ↓
8. Print/Log the filtered results count
   ↓
9. Close browser and log execution summary
```

---

## Configuration

### Edit Configuration File

Edit `src/test/resources/config.json` to customize test parameters:

```json
{
  "browser": "chrome",                    // Browser type: chrome or firefox
  "baseUrl": "https://www.ebay.com/",     // eBay base URL
  "searchTerm": "mazda mx-5",              // Product to search
  "transmissionType": "Manual",            // Filter type
  "windowWidth": 1920,                     // Browser window width
  "windowHeight": 1080,                    // Browser window height
  "implicitWait": 20,                      // Implicit wait in seconds
  "explicitWait": 20,                      // Explicit wait in seconds
  "pageLoadTimeout": 30                    // Page load timeout in seconds
}
```

### Logging Configuration

Edit `src/test/resources/log4j2.xml` to customize logging:

```xml
<!-- Change log level -->
<Logger name="com.ebay.automation" level="info">
    <!-- Options: debug, info, warn, error -->
</Logger>
```

---

## Framework Components

### 1. BasePage Class

The `BasePage` class contains 40+ reusable Selenium methods:

**Navigation Methods**
- `navigateToUrl(String url)` - Navigate to URL
- `refreshPage()` - Refresh current page
- `getCurrentUrl()` - Get current URL

**Element Interaction Methods**
- `click(By locator)` - Click element
- `sendText(By locator, String text)` - Send text to input
- `getText(By locator)` - Get element text
- `getAttribute(By locator, String attribute)` - Get attribute value
- `doubleClick(By locator)` - Double click element
- `rightClick(By locator)` - Right click element

**Wait Methods**
- `findElement(By locator)` - Find element with explicit wait
- `findElements(By locator)` - Find all matching elements
- `isElementVisible(By locator)` - Check element visibility
- `isElementPresent(By locator)` - Check element presence
- `waitForElementToBeClickable(By locator)` - Wait for clickable element

**Validation Methods**
- `waitForPageTitle(String title)` - Wait for page title
- `getPageTitle()` - Get current page title

### 2. HomePage Class

Page Object for eBay home page:

```java
HomePage homePage = new HomePage(driver);
homePage.navigateToEbay();           // Navigate to eBay
homePage.isHomePageLoaded();          // Validate page loaded
homePage.searchProduct("mazda mx-5"); // Search product
```

### 3. SearchResultsPage Class

Page Object for search results page:

```java
SearchResultsPage resultsPage = new SearchResultsPage(driver);
resultsPage.getSearchResultCount();        // Get result count
resultsPage.areSearchResultsDisplayed();   // Validate results
resultsPage.applyManualTransmissionFilter(); // Apply filter
resultsPage.getFilteredResultCount();      // Get filtered count
```

### 4. ConfigReader Class

Reads external JSON configuration:

```java
String browser = ConfigReader.getBrowser();
String url = ConfigReader.getBaseUrl();
String searchTerm = ConfigReader.getSearchTerm();
int waitTime = ConfigReader.getExplicitWait();
```

### 5. DriverFactory Class

Manages WebDriver instances:

```java
WebDriver driver = DriverFactory.createDriver();  // Create driver
WebDriver driver = DriverFactory.getDriver();     // Get driver
DriverFactory.quitDriver();                       // Close driver
```

### 6. TestLogger Class

Centralized logging:

```java
logger.info("Information message");
logger.debug("Debug message");
logger.warn("Warning message");
logger.error("Error message", exception);
```

---

## Page Object Model (POM) Explained

The POM design pattern separates page structure from test logic:

**Advantages:**
- ✅ Improved maintainability - locators in one place
- ✅ Better reusability - common methods in BasePage
- ✅ Easier debugging - clear method names
- ✅ Scalability - easy to add new pages
- ✅ Reduced maintenance - change locator once

**Structure:**
```java
public class HomePage extends BasePage {
    // Locators
    private static final By SEARCH_INPUT = By.xpath("...");
    
    // Constructor
    public HomePage(WebDriver driver) {
        super(driver, ConfigReader.getExplicitWait());
    }
    
    // Page Methods
    public void searchProduct(String term) { ... }
}
```

---

## Advanced Features

### 1. Automatic WebDriver Management

WebDriverManager automatically downloads and manages browser drivers:

```java
// No need to set driver path
// WebDriverManager.chromedriver().setup(); // Automatic
WebDriver driver = new ChromeDriver(options);
```

### 2. External Configuration

All test data is externalized in JSON file - no hardcoding:

```json
{
  "searchTerm": "mazda mx-5",
  "browser": "chrome"
}
```

### 3. Centralized Logging

Logs to both console and file:

```
logs/test_execution.log
```

### 4. Implicit and Explicit Waits

- **Implicit Wait**: Applied to all element finding operations
- **Explicit Wait**: Applied for specific elements with conditions

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
```

---

## Running Specific Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn -Dtest=EbaySearchTest test
```

### Run Specific Test Method
```bash
mvn -Dtest=EbaySearchTest#testEbaySearchAndFilter test
```

### Run with Verbose Output
```bash
mvn test -e
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

---

## IDE Setup

### IntelliJ IDEA
1. Open EBAY-Testing folder
2. File → Project Structure → Set JDK 11+
3. Right-click EbaySearchTest.java
4. Select "Run 'EbaySearchTest'"

### Eclipse
1. File → Import → Existing Maven Projects
2. Select EBAY-Testing folder
3. Right-click test → Run As → TestNG Test

### VS Code
1. Install Extension Pack for Java
2. Open Command Palette → Java: Clean Java Language Server Workspace
3. Click Run icon on test file

---

## Troubleshooting

### Issue: "mvn: command not found"
**Solution**: Add Maven to PATH or use full path to Maven

### Issue: "BUILD FAILURE: Could not resolve dependencies"
**Solution**: 
```bash
mvn clean install -U
```

### Issue: Test timeout
**Solution**: Increase wait times in config.json
```json
{
  "implicitWait": 30,
  "explicitWait": 30
}
```

### Issue: Chrome driver not found
**Solution**: Delete cache and reinstall
```bash
rm -rf ~/.wdm
mvn clean test
```

### Issue: Element not found
**Solution**:
1. Check if eBay website structure changed
2. Update locators in page object classes
3. Verify element using browser DevTools (F12)

---

## Test Execution Output Example

```
========== Starting Test ==========

Navigating to eBay home page
Successfully navigated to https://www.ebay.com/

=== STEP 1: Navigate to eBay Home Page ===
Navigated to: https://www.ebay.com/

=== STEP 2: Validate Home Page Loaded ===
✓ Home page loaded successfully
Page Title: Electronics, Cars, Fashion, Collectibles & More | eBay

=== STEP 3: Search for Product ===
Searching for: mazda mx-5
Search completed for: mazda mx-5
✓ Product search initiated for: mazda mx-5

=== STEP 4: Validate Search Results ===
✓ Search results displayed successfully

*** TOTAL SEARCH RESULTS: 1,234 ***

=== STEP 5: Apply Manual Transmission Filter ===
Applying Manual transmission filter
✓ Manual transmission filter applied

=== STEP 6: Validate Filtered Results ===
✓ Filtered results displayed successfully

*** FILTERED SEARCH RESULTS (Manual Transmission): 456 ***

=== TEST SUMMARY ===
Total Search Results: 1,234
Filtered Results (Manual): 456
✓ TEST PASSED - All validations successful!

========== Ending Test ==========
```

---

## Deliverables

For submission, include:

1. ✅ **Repository URL**: https://github.com/MohamedDerbala007/EBAY-Testing
2. ✅ **Source Code**: All Java classes and configuration files
3. ✅ **Documentation**: README.md and INSTALLATION.md
4. ✅ **Test Logs**: logs/test_execution.log
5. ✅ **Video Proof**: Screen recording of test execution
6. ✅ **Compressed Package**: (.zip or .rar) containing all files

---

## Best Practices Implemented

✅ **Code Organization**
- Clear package structure
- Separation of concerns
- DRY (Don't Repeat Yourself) principle

✅ **Maintainability**
- Meaningful variable and method names
- Comprehensive comments and documentation
- Consistent code formatting

✅ **Reliability**
- Proper wait strategies
- Exception handling
- Retry mechanisms

✅ **Scalability**
- Easy to add new test cases
- Easy to add new page objects
- Reusable utility methods

✅ **Reporting**
- Detailed logging
- Console output
- File-based logs
- TestNG HTML reports

---

## Support & Documentation

For detailed installation instructions, see: **[INSTALLATION.md](INSTALLATION.md)**

For quick setup:
```bash
# 1. Clone
git clone https://github.com/MohamedDerbala007/EBAY-Testing.git
cd EBAY-Testing

# 2. Install
mvn clean install

# 3. Run
mvn test
```

---

## License

This project is open-source and available for educational purposes.

---

**Version**: 1.0.0  
**Last Updated**: April 2026  
**Author**: Mohamed Derbala

---

**Happy Testing! 🚀**
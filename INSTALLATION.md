# Installation & Setup Guide

This guide provides step-by-step instructions to set up and run the eBay Test Automation Framework.

## Table of Contents
1. [System Requirements](#system-requirements)
2. [Prerequisites Installation](#prerequisites-installation)
3. [Repository Setup](#repository-setup)
4. [Framework Configuration](#framework-configuration)
5. [Running Tests](#running-tests)
6. [Troubleshooting](#troubleshooting)

---

## System Requirements

### Minimum Requirements
- **OS**: Windows 10/11, macOS 10.14+, Ubuntu 18.04+
- **RAM**: 4 GB minimum (8 GB recommended)
- **Disk Space**: 1 GB free space
- **Internet**: Required for downloading dependencies

### Recommended Requirements
- **OS**: Windows 11, macOS 12+, Ubuntu 20.04+
- **RAM**: 8 GB or more
- **Disk Space**: 2-3 GB free space
- **Java Version**: Java 11 LTS or newer
- **Maven Version**: 3.8.0+

---

## Prerequisites Installation

### 1. Install Java Development Kit (JDK)

#### Windows
```bash
# Option 1: Using Chocolatey (if installed)
choco install openjdk

# Option 2: Download and Install
# 1. Visit: https://www.oracle.com/java/technologies/downloads/
# 2. Download JDK 11 or higher
# 3. Run the installer
# 4. Follow installation wizard
```

#### macOS
```bash
# Using Homebrew
brew install openjdk@11

# Or download from: https://www.oracle.com/java/technologies/downloads/
```

#### Ubuntu/Debian
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk-headless
```

#### Verify Installation
```bash
java -version
javac -version
```

Expected output:
```
openjdk version "11.0.x" ...
javac 11.0.x
```

### 2. Install Maven

#### Windows
```bash
# Option 1: Using Chocolatey
choco install maven

# Option 2: Manual Installation
# 1. Download from: https://maven.apache.org/download.cgi
# 2. Extract to: C:\tools\maven
# 3. Add to PATH: C:\tools\maven\bin
```

#### macOS
```bash
# Using Homebrew
brew install maven

# Or download and extract from: https://maven.apache.org/download.cgi
```

#### Ubuntu/Debian
```bash
sudo apt-get install maven
```

#### Verify Installation
```bash
mvn -version
```

Expected output:
```
Apache Maven 3.8.x
Maven home: ...
Java version: 11.0.x ...
```

### 3. Install Git

#### Windows
```bash
# Option 1: Using Chocolatey
choco install git

# Option 2: Download from
# https://git-scm.com/download/win
```

#### macOS
```bash
brew install git
```

#### Ubuntu/Debian
```bash
sudo apt-get install git
```

#### Verify Installation
```bash
git --version
```

### 4. Set JAVA_HOME Environment Variable

#### Windows
```bash
# Set permanently:
# 1. Search "Edit Environment Variables"
# 2. Click "Environment Variables..."
# 3. Under "System variables", click "New..."
# 4. Variable name: JAVA_HOME
# 5. Variable value: C:\Program Files\Java\jdk-11.x.x (your JDK path)
# 6. Click OK and restart terminal

# Verify in command prompt:
echo %JAVA_HOME%
```

#### macOS/Linux
```bash
# Edit ~/.zshrc (macOS) or ~/.bashrc (Linux)
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
export PATH="$JAVA_HOME/bin:$PATH"

# Apply changes
source ~/.zshrc
# or
source ~/.bashrc

# Verify
echo $JAVA_HOME
```

---

## Repository Setup

### 1. Clone Repository

```bash
# Create a directory for your projects
mkdir automation_projects
cd automation_projects

# Clone the repository
git clone https://github.com/MohamedDerbala007/EBAY-Testing.git

# Navigate to project
cd EBAY-Testing

# Verify structure
ls -la
# or (Windows)
dir
```

### 2. Verify Project Structure

```
EBAY-Testing/
├── src/
│   ├── main/java/com/ebay/automation/
│   ├── test/java/com/ebay/automation/tests/
│   └── test/resources/
├── pom.xml
├── testng.xml
├── README.md
└── INSTALLATION.md
```

### 3. Download Dependencies

```bash
# Install all dependencies from pom.xml
mvn clean install

# This will download:
# - Selenium WebDriver
# - TestNG
# - Log4j2
# - Gson
# - WebDriverManager
# - Other dependencies

# Output should show: BUILD SUCCESS
```

---

## Framework Configuration

### 1. Configure Browser Type

Edit `src/test/resources/config.json`:

```json
{
  "browser": "chrome"  // Options: "chrome" or "firefox"
}
```

### 2. Configure Timeouts

```json
{
  "implicitWait": 20,      // Seconds - wait for element presence
  "explicitWait": 20,      // Seconds - wait for element visibility
  "pageLoadTimeout": 30    // Seconds - wait for page load
}
```

### 3. Configure Window Size

```json
{
  "windowWidth": 1920,   // Browser window width
  "windowHeight": 1080   // Browser window height
}
```

### 4. Configure Logging

Edit `src/test/resources/log4j2.xml`:

```xml
<!-- Change log level -->
<Logger name="com.ebay.automation" level="info">
    <!-- Options: debug, info, warn, error -->
</Logger>
```

### 5. Default Configuration

```json
{
  "browser": "chrome",
  "baseUrl": "https://www.ebay.com/",
  "searchTerm": "mazda mx-5",
  "transmissionType": "Manual",
  "windowWidth": 1920,
  "windowHeight": 1080,
  "implicitWait": 20,
  "explicitWait": 20,
  "pageLoadTimeout": 30
}
```

---

## Running Tests

### 1. Run All Tests

```bash
# From project root directory
mvn test

# This will:
# 1. Compile source code
# 2. Execute all test cases
# 3. Generate reports
# 4. Create log files
```

### 2. Run Specific Test Class

```bash
mvn -Dtest=EbaySearchTest test
```

### 3. Run Specific Test Method

```bash
mvn -Dtest=EbaySearchTest#testEbaySearchAndFilter test
```

### 4. Run with Verbose Output

```bash
mvn test -X
# or
mvn test -e
```

### 5. Skip Tests During Build

```bash
mvn clean install -DskipTests
```

### 6. Run Tests Silently

```bash
mvn test -q
```

---

## Viewing Results

### 1. Test Execution Logs

**Location**: `logs/test_execution.log`

```bash
# View log file
# Windows
type logs\test_execution.log

# macOS/Linux
cat logs/test_execution.log

# Follow log in real-time (Linux/macOS)
tail -f logs/test_execution.log
```

### 2. TestNG Reports

**Location**: `target/surefire-reports/`

```bash
# Test reports are automatically generated
# Open in browser:
# - Windows: start target\surefire-reports\index.html
# - macOS: open target/surefire-reports/index.html
# - Linux: xdg-open target/surefire-reports/index.html
```

### 3. Detailed Test Output

```bash
# View test summary in console
mvn test

# Look for:
# ✓ Tests run: X
# ✓ Failures: 0
# ✓ Errors: 0
# ✓ Time elapsed: X seconds
```

---

## IDE Setup

### Setup in IntelliJ IDEA

```
1. Open IntelliJ IDEA
2. Click "Open"
3. Navigate to EBAY-Testing folder
4. Click "OK"
5. Wait for project indexing

6. Project Structure:
   - File → Project Structure → Project
   - Set SDK to JDK 11+
   - Click "OK"

7. Run Test:
   - Right-click: src/test/java/com/ebay/automation/tests/EbaySearchTest.java
   - Select: "Run 'EbaySearchTest'"

8. View Results:
   - Results appear in Run console
   - Logs visible in Console tab
```

### Setup in Eclipse

```
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Select EBAY-Testing folder
4. Click "Finish"
5. Wait for build

6. Run Test:
   - Right-click: EbaySearchTest.java
   - Select: Run As → TestNG Test

7. View Results:
   - Results in TestNG Results View
   - Logs in Console tab
```

### Setup in VS Code

```
1. Install Extension Pack for Java (Microsoft)
2. Open EBAY-Testing folder
3. Open command palette (Ctrl+Shift+P)
4. Type: Java: Clean the Java language server workspace
5. Wait for indexing

6. Run Test:
   - Click Test icon on left sidebar
   - Click play button next to test
   - Or: Right-click test file → Run

7. View Results:
   - Results in Test Explorer
   - Logs in Terminal
```

---

## Troubleshooting

### Issue 1: "mvn: command not found"

**Solution**: Maven not in PATH
```bash
# Windows: Add Maven to Environment Variables
# or use full path: C:\tools\maven\bin\mvn test

# macOS/Linux: Check PATH
echo $PATH

# Add to ~/.bashrc or ~/.zshrc
export PATH="/usr/local/opt/maven/bin:$PATH"
```

### Issue 2: "Java home not set"

**Solution**: Set JAVA_HOME
```bash
# Check current JAVA_HOME
echo $JAVA_HOME

# If empty, add to ~/.bashrc or ~/.zshrc
export JAVA_HOME=$(/usr/libexec/java_home -v 11)

# Reload shell
source ~/.bashrc
```

### Issue 3: "BUILD FAILURE: Could not resolve dependencies"

**Solution**: Update Maven dependencies
```bash
# Clear Maven cache
mvn clean

# Update dependencies
mvn clean install -U

# If still failing:
# 1. Check internet connection
# 2. Delete ~/.m2/repository folder
# 3. Run mvn clean install again
```

### Issue 4: Tests timeout

**Solution**: Increase wait times
```json
{
  "implicitWait": 30,
  "explicitWait": 30,
  "pageLoadTimeout": 40
}
```

### Issue 5: "Chrome driver not found"

**Solution**: WebDriverManager will auto-download
```bash
# If still failing:
# 1. Delete ~/.wdm folder (cache)
# 2. Run mvn clean test again

# Or manually set:
# Windows: Set CHROMEDRIVER path in system variables
# macOS/Linux: export CHROMEDRIVER=/path/to/chromedriver
```

### Issue 6: "Element not found" error

**Solution**: Update wait times or locators
```
1. Increase explicitWait in config.json
2. Check if eBay website structure changed
3. Update locators in HomePage.java or SearchResultsPage.java
4. Verify element using browser DevTools (F12)
```

### Issue 7: Port already in use

**Solution**: Change port or kill process
```bash
# Linux/macOS: Find process using port
lsof -i :8080

# Kill process
kill -9 <PID>

# Windows: Use Task Manager to kill process
```

### Issue 8: Permission denied (macOS/Linux)

**Solution**: Add execute permission
```bash
chmod +x mvn
chmod +x src/test/java/com/ebay/automation/tests/*.java
```

### Issue 9: No log file generated

**Solution**: Create logs directory
```bash
# Create manually
mkdir logs

# Or configure path in log4j2.xml:
<File name="File" fileName="logs/test_execution.log" append="false">
```

### Issue 10: Tests pass but browser doesn't open

**Solution**: Headless mode issue
```bash
# Check if running on CI/CD server
# Edit DriverFactory.java to add headless options:

ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--no-sandbox");
```

---

## Verification Checklist

After setup, verify everything works:

- [ ] Java installed: `java -version`
- [ ] Maven installed: `mvn -version`
- [ ] Git installed: `git --version`
- [ ] Repository cloned
- [ ] Dependencies downloaded: `mvn clean install`
- [ ] config.json exists and is valid JSON
- [ ] log4j2.xml exists
- [ ] Test runs without errors: `mvn test`
- [ ] Logs file created: `logs/test_execution.log`
- [ ] Test reports generated: `target/surefire-reports/`

---

## Next Steps

1. ✅ Complete setup following this guide
2. ✅ Run tests: `mvn test`
3. ✅ Record video proof of execution
4. ✅ Package deliverables (.rar or .zip)
5. ✅ Include repository URL in submission

---

## Quick Reference Commands

```bash
# Setup
git clone https://github.com/MohamedDerbala007/EBAY-Testing.git
cd EBAY-Testing
mvn clean install

# Run Tests
mvn test                                          # Run all tests
mvn -Dtest=EbaySearchTest test                    # Run specific class
mvn -Dtest=EbaySearchTest#testEbaySearchAndFilter test  # Run specific method

# Clean Up
mvn clean                                         # Remove target folder
rm -rf logs                                       # Remove log files

# View Results
cat logs/test_execution.log                       # View logs
open target/surefire-reports/index.html          # View reports (macOS)
start target\surefire-reports\index.html         # View reports (Windows)
```

---

## Support

For issues or questions:
1. Review this installation guide
2. Check README.md for framework details
3. Review log files in `logs/` folder
4. Verify configuration in `config.json`

---

**Installation completed! Happy testing! 🚀**

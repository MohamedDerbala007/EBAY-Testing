@echo off
REM Run eBay Test Automation Framework
REM This script executes the test suite and generates reports

echo ========================================
echo eBay Test Automation Framework
echo ========================================
echo.

REM Step 1: Clean previous builds
echo [1/4] Cleaning previous builds...
call mvn clean

REM Step 2: Install dependencies
echo.
echo [2/4] Installing dependencies...
call mvn install -DskipTests

REM Step 3: Run tests
echo.
echo [3/4] Running tests with TestNG...
call mvn test

REM Step 4: Display results
echo.
echo [4/4] Test execution completed!
echo.
echo ========================================
echo Test Results:
echo ========================================
echo Log file: logs\test_execution.log
echo Reports: target\surefire-reports\
echo.

REM Display log file if it exists
if exist logs\test_execution.log (
    echo Latest log entries:
    echo ========================================
    type logs\test_execution.log
    echo ========================================
)

echo.
echo Done!
@echo off
REM Course Mate Backend - Quick Start Script

echo.
echo ========================================
echo  Course Mate Backend - Setup & Run
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java 17 from: https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo [OK] Java is installed
java -version

echo.

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo [OK] Maven is installed
mvn -version

echo.
echo ========================================
echo  Building Project...
echo ========================================
echo.

REM Build the project
mvn clean install

if errorlevel 1 (
    echo [ERROR] Build failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Build Complete! Starting Application...
echo ========================================
echo.

REM Run the application
mvn spring-boot:run

pause

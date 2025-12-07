#!/bin/bash

# Course Mate Backend - Quick Start Script

echo ""
echo "========================================"
echo "  Course Mate Backend - Setup & Run"
echo "========================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "[ERROR] Java is not installed"
    echo "Please install Java 17 from: https://adoptium.net/"
    echo ""
    exit 1
fi

echo "[OK] Java is installed"
java -version

echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "[ERROR] Maven is not installed"
    echo "Please install Maven from: https://maven.apache.org/download.cgi"
    echo ""
    exit 1
fi

echo "[OK] Maven is installed"
mvn -version

echo ""
echo "========================================"
echo "  Building Project..."
echo "========================================"
echo ""

# Build the project
mvn clean install

if [ $? -ne 0 ]; then
    echo "[ERROR] Build failed!"
    exit 1
fi

echo ""
echo "========================================"
echo "  Build Complete! Starting Application..."
echo "========================================"
echo ""

# Run the application
mvn spring-boot:run

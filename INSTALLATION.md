# Course Mate Backend - Installation Guide

## Prerequisites Installation

### Option 1: Manual Installation (Recommended)

#### 1. Install Java 17 (OpenJDK)

1. Download Java 17 from: https://adoptium.net/
2. Choose "OpenJDK 17 LTS" and your OS (Windows x64)
3. Run the installer and follow the prompts
4. Verify installation:
   ```powershell
   java -version
   ```

#### 2. Install Maven

1. Download Maven from: https://maven.apache.org/download.cgi
2. Choose "apache-maven-3.9.x-bin.zip"
3. Extract to a folder (e.g., `C:\maven`)
4. Add Maven to system PATH:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Add new SYSTEM variable:
     - Variable name: `MAVEN_HOME`
     - Variable value: `C:\maven\apache-maven-3.9.x` (adjust path)
   - Edit PATH and add: `%MAVEN_HOME%\bin`
5. Close and reopen PowerShell as Administrator
6. Verify installation:
   ```powershell
   mvn -version
   ```

#### 3. Install MySQL

1. Download MySQL from: https://dev.mysql.com/downloads/mysql/
2. Choose MySQL Community Server (latest version)
3. Run the installer and complete the setup
4. Default credentials: username=`root`, password=`root`
5. Create database:
   ```sql
   CREATE DATABASE coursemate_db;
   ```

### Option 2: Using Chocolatey (Admin Required)

If you have admin privileges, open PowerShell as Administrator and run:

```powershell
# Install Chocolatey (if not already installed)
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Install Java and Maven
choco install openjdk17 maven mysql -y

# Verify installations
java -version
mvn -version
```

---

## Running the Application

### Step 1: Navigate to Project Directory
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
```

### Step 2: Build the Project
```powershell
mvn clean install
```
This will download all dependencies and compile the code.

### Step 3: Run the Application
```powershell
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Step 4: Verify it's Running
Open your browser and visit:
- `http://localhost:8080/api/health` (if endpoint exists)
- Or check the console for "Started CourseMateApplication"

---

## Database Setup

### Create Database Manually
```sql
CREATE DATABASE coursemate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Hibernate Auto-Creates Tables
The application will automatically create all tables when you first run it (configured in `application.properties` with `spring.jpa.hibernate.ddl-auto=update`)

---

## Environment Configuration

Update `application.properties` if your MySQL credentials differ:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coursemate_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

---

## Troubleshooting

### "java is not recognized"
- Java is not in your PATH
- Restart PowerShell after installation
- Verify with: `java -version`

### "mvn is not recognized"
- Maven is not in your PATH
- Verify `MAVEN_HOME` environment variable is set
- Restart PowerShell after setting environment variables

### MySQL Connection Error
- Verify MySQL is running: `mysql -u root -p`
- Check `application.properties` credentials
- Ensure database `coursemate_db` exists

### Port 8080 Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or kill the process: `lsof -ti:8080 | xargs kill -9`

---

## Quick Start (After Installation)

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
mvn clean install
mvn spring-boot:run
```

Then visit: `http://localhost:8080`


# 🚀 QUICK INSTALL GUIDE - For Running Course Mate

## ⚡ 3-Step Installation (10 minutes)

### Step 1: Download Java 17
**Link:** https://adoptium.net/

- Click "Latest LTS Release" (Java 17)
- Choose Windows x64
- Download the .msi file
- Run installer → Click "Next" → Check "Add to PATH" → Finish
- **Restart PowerShell**

### Step 2: Download Maven
**Link:** https://maven.apache.org/download.cgi

- Download `apache-maven-3.9.5-bin.zip`
- Extract to `C:\maven`
- Add to system PATH:
  - Press `Win + X` → Select "System"
  - Click "Advanced system settings"
  - Click "Environment Variables"
  - Click "New" (System variables)
  - Variable name: `MAVEN_HOME`
  - Variable value: `C:\maven\apache-maven-3.9.5`
  - Edit PATH → Add `%MAVEN_HOME%\bin`
- **Restart PowerShell**

### Step 3: Download MySQL
**Link:** https://dev.mysql.com/downloads/mysql/

- Download MySQL Community Server
- Run installer
- Set password to `root` during setup
- Complete installation
- **Restart your computer**

---

## ✅ Verify Installation

```powershell
java -version
mvn -version
mysql -u root -p
# (Enter password: root, then EXIT)
```

## 🚀 After Installation - Run Your Project

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"

# Create database (do this once)
mysql -u root -p
CREATE DATABASE coursemate_db;
EXIT

# Build and run
mvn clean install
mvn spring-boot:run
```

**Then open in Chrome:** http://localhost:8080

---

**Estimated time:** 10 minutes installation + 3 minutes first run


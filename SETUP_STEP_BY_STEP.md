# ⚙️ SETUP INSTRUCTIONS - Course Mate Backend

## Step 1: Install Java 17

1. Visit: **https://adoptium.net/**
2. Download **OpenJDK 17 LTS** for Windows x64
3. Run the installer
4. Accept defaults and click "Next" through all steps
5. **IMPORTANT**: Check "Add to PATH" during installation
6. Click "Finish"
7. Close and reopen PowerShell

**Verify:**
```powershell
java -version
```
You should see output like: `openjdk version "17.x.x"`

---

## Step 2: Install Maven

1. Visit: **https://maven.apache.org/download.cgi**
2. Download **apache-maven-3.9.5-bin.zip** (or latest)
3. Extract to `C:\maven` or any folder
4. **Set Environment Variable:**
   - Right-click **This PC** → **Properties**
   - Click **Advanced system settings**
   - Click **Environment Variables**
   - Under "System variables", click **New**
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\maven\apache-maven-3.9.5` (adjust version)
   - Click **OK**

5. **Add to PATH:**
   - In Environment Variables, find **Path** (System variables)
   - Click **Edit**
   - Click **New**
   - Add: `%MAVEN_HOME%\bin`
   - Click **OK** and **OK**

6. Close and reopen PowerShell

**Verify:**
```powershell
mvn -version
```
You should see Maven version output.

---

## Step 3: Install MySQL

1. Visit: **https://dev.mysql.com/downloads/mysql/**
2. Download **MySQL Community Server** (latest version)
3. Run the installer
4. Choose "Developer Default" or "Server only"
5. Complete setup with defaults
6. **IMPORTANT**: Set root password to `root` (or change `application.properties` later)
7. Complete installation

**Verify:**
```powershell
mysql -u root -p
# Enter password: root
# Type: EXIT to quit
```

**Create Database:**
```powershell
mysql -u root -p
# Enter password: root
```

Then in MySQL prompt:
```sql
CREATE DATABASE coursemate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SHOW DATABASES;
EXIT
```

---

## Step 4: Run the Application

### Method 1: Using Windows Batch Script (Easiest)

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
```

The script will:
1. ✅ Check Java installation
2. ✅ Check Maven installation
3. ✅ Build project (`mvn clean install`)
4. ✅ Start application (`mvn spring-boot:run`)

### Method 2: Using PowerShell Commands

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"

# Build project (downloads dependencies, compiles code)
mvn clean install

# Start the application
mvn spring-boot:run
```

Wait for message: **"Started CourseMateApplication"**

---

## Step 5: Test the Application

Once you see "Started CourseMateApplication" in the console:

1. **Open Browser** and visit: `http://localhost:8080`

2. **Test API with cURL:**
```powershell
# Health check
curl http://localhost:8080/api/health

# Register user
curl -X POST http://localhost:8080/api/auth/register `
  -H "Content-Type: application/json" `
  -d '{
    "firstName":"John",
    "lastName":"Doe",
    "username":"johndoe",
    "email":"john@example.com",
    "password":"password123",
    "role":"STUDENT"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{
    "username":"johndoe",
    "password":"password123"
  }'
```

3. **View Sample Data:**

The application auto-creates sample data on first run:
- Admin user: `admin` / `admin123`
- Instructor: `instructor1` / `instructor123`
- Students: `student1` / `student123`, `student2` / `student123`

---

## Troubleshooting

### ❌ "Java is not recognized"
- Java not installed or not in PATH
- Solution: Restart PowerShell after Java installation
- Verify: `java -version`

### ❌ "MVN is not recognized"
- Maven not installed or MAVEN_HOME not set
- Solution: 
  1. Restart PowerShell
  2. Verify: `mvn -version`
  3. Check MAVEN_HOME: `$env:MAVEN_HOME`

### ❌ "MySQL connection failed"
- MySQL not running or credentials wrong
- Solution:
  1. Start MySQL: `mysql -u root -p`
  2. Check credentials in `application.properties`
  3. Verify database exists: `SHOW DATABASES;`

### ❌ "Port 8080 already in use"
- Another application using port 8080
- Solution: Edit `application.properties`
```properties
server.port=8081
```

### ❌ Build fails with "Cannot find symbol"
- Missing dependencies
- Solution:
```powershell
mvn clean install -U
```

### ❌ Application starts but won't respond
- Check logs for errors
- Solution:
  1. Check MySQL is running
  2. Check database exists
  3. Verify credentials

---

## First Run Checklist

- [ ] Java installed and working (`java -version`)
- [ ] Maven installed and working (`mvn -version`)
- [ ] MySQL installed and running
- [ ] Database `coursemate_db` created
- [ ] Navigate to project folder
- [ ] Run `mvn clean install` (takes 2-5 minutes first time)
- [ ] Run `mvn spring-boot:run`
- [ ] See "Started CourseMateApplication" message
- [ ] Visit `http://localhost:8080`

---

## Next Steps

Once running, explore:

1. **API Documentation**: See `API_DOCUMENTATION.md`
2. **Sample Users**: Use credentials above to test
3. **REST Endpoints**: Try the endpoints in the API docs
4. **Database**: View tables in MySQL with: `mysql -u root -p coursemate_db`

---

## Need Help?

Refer to:
- `README.md` - Full project documentation
- `QUICK_START.md` - Quick reference
- `API_DOCUMENTATION.md` - All API endpoints

**Good luck! 🚀**

# 🔴 WINDOWS USERS - COPY & PASTE COMMANDS

## Step 1: Install Java 17 (Do This First!)

1. **Download Java:** https://adoptium.net/
2. **Run installer** → Accept all defaults → Check "Add to PATH"
3. **Restart PowerShell**
4. **Verify Java:**
```powershell
java -version
```

## Step 2: Install Maven (Do This Second!)

1. **Download Maven:** https://maven.apache.org/download.cgi
   - Download: `apache-maven-3.9.5-bin.zip` (or latest)
2. **Extract to:** `C:\maven`
3. **Set Environment Variables:**

Press `Win + X` → "System" → "Advanced system settings" → "Environment Variables"

**Click "New" under System variables:**
- Variable name: `MAVEN_HOME`
- Variable value: `C:\maven\apache-maven-3.9.5`

**Edit "Path" (System variables):**
- Click "New"
- Add: `%MAVEN_HOME%\bin`
- Click OK

4. **Restart PowerShell**
5. **Verify Maven:**
```powershell
mvn -version
```

## Step 3: Install MySQL (Do This Third!)

1. **Download MySQL:** https://dev.mysql.com/downloads/mysql/
2. **Run installer** → Accept defaults → **Set password to `root`**
3. **Create database:**

Open PowerShell and run:
```powershell
mysql -u root -p
```
Enter password: `root`

Then in MySQL prompt, copy-paste:
```sql
CREATE DATABASE coursemate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SHOW DATABASES;
EXIT
```

---

## Step 4: Run the Application (Finally!)

### Method A: Using Batch Script (Easiest)

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
```

**Wait for message:** "Started CourseMateApplication in X seconds"

### Method B: Using PowerShell Commands

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"

# First time only: Download dependencies and build
mvn clean install

# Start the application
mvn spring-boot:run
```

---

## 🎉 Application Running!

Once you see:
```
Started CourseMateApplication in X.XXX seconds
```

Open browser: **http://localhost:8080**

---

## 🧪 Test with Sample Data

### Option 1: Using Browser

Visit: `http://localhost:8080/api/users`

You might get an error asking for credentials. That's normal! The API requires JWT tokens.

### Option 2: Using PowerShell cURL

**Login as Admin:**
```powershell
$body = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

$response = curl -X POST http://localhost:8080/api/auth/login `
    -H "Content-Type: application/json" `
    -Body $body

$response | ConvertFrom-Json
```

Copy the `token` value from the response.

**Get All Users:**
```powershell
$token = "paste_your_token_here"

curl -X GET http://localhost:8080/api/users `
    -H "Authorization: Bearer $token"
```

### Option 3: Using Postman (Best for Testing)

1. **Download:** https://www.postman.com/downloads/
2. **Install** and open Postman
3. **Create new request:**
   - URL: `http://localhost:8080/api/auth/login`
   - Method: `POST`
   - Go to "Body" tab
   - Select "raw" → "JSON"
   - Paste:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
   - Click "Send"

4. **Copy the token** from response
5. **Create another request:**
   - URL: `http://localhost:8080/api/courses`
   - Method: `GET`
   - Go to "Headers" tab
   - Add header:
     - Key: `Authorization`
     - Value: `Bearer [paste_token_here]`
   - Click "Send"

---

## 📊 Sample Data Available

After first run, test with:

| Role | Username | Password | Email |
|------|----------|----------|-------|
| **Admin** | `admin` | `admin123` | admin@coursemate.com |
| **Instructor** | `instructor1` | `instructor123` | instructor1@coursemate.com |
| **Student 1** | `student1` | `student123` | student1@coursemate.com |
| **Student 2** | `student2` | `student123` | student2@coursemate.com |

---

## 🛑 To Stop the Application

Press `Ctrl + C` in the PowerShell terminal running the app.

---

## 🚀 Run Again (Tomorrow or Later)

Just repeat Step 4:

```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
```

No need to reinstall Java/Maven/MySQL!

---

## ❌ Troubleshooting

### "Java is not recognized"
```powershell
# Verify installation
java -version

# If not found: restart PowerShell after Java installation
```

### "Maven is not recognized"
```powershell
# Verify installation
mvn -version

# If not found: 
# 1. Check MAVEN_HOME: $env:MAVEN_HOME
# 2. Restart PowerShell
# 3. Verify PATH includes %MAVEN_HOME%\bin
```

### "Cannot connect to MySQL"
```powershell
# Check if MySQL is running
mysql -u root -p
# Press Enter, type: root
# If connects, type: EXIT

# If not: Start MySQL from Services
# Win + R → services.msc → Find MySQL → Start
```

### "Port 8080 already in use"

Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

Then run again.

### "Build fails"
```powershell
# Clear cache and try again
mvn clean install -U
```

---

## ✅ Verification

After running, should see in console:

```
...
[INFO] Scanning for projects...
[INFO] --------< com.coursemate:coursemate-backend:1.0.0 >--------
...
[INFO] Building Project...
[INFO] --------< ... BUILDING IN ECLIPSE ... >--------
...
Started CourseMateApplication in X.XXX seconds
```

Then API is ready at: `http://localhost:8080`

---

## 📞 Need Help?

Refer to:
- `START_HERE.md` - Overview
- `SETUP_STEP_BY_STEP.md` - Detailed walkthrough  
- `API_DOCUMENTATION.md` - All endpoints
- `README.md` - Full documentation

---

**You got this! 💪**

**Command to launch:** 
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"; .\RUN.bat
```


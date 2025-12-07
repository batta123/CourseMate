# 🚀 Course Mate Backend - Quick Start Guide

## Prerequisites Checklist

Before running the application, ensure you have:

- ✅ **Java 17** - Download from [Adoptium](https://adoptium.net/)
- ✅ **Maven 3.8+** - Download from [Maven.Apache.org](https://maven.apache.org/download.cgi)
- ✅ **MySQL 8.0+** - Download from [MySQL.com](https://dev.mysql.com/downloads/mysql/)

## Installation Steps

### 1️⃣ Install Java 17
```powershell
# Verify Java installation
java -version
```

### 2️⃣ Install Maven
```powershell
# Verify Maven installation
mvn -version
```

### 3️⃣ Install MySQL & Create Database
```sql
-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE coursemate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Verify
SHOW DATABASES;
```

## Running the Application

### Option 1: Using the Batch Script (Windows)
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
```

### Option 2: Using Maven Directly
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
mvn clean install
mvn spring-boot:run
```

### Option 3: Build and Run JAR
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
mvn clean package
java -jar target/coursemate-backend-1.0.0.jar
```

## Testing the Application

Once running, test with:

```bash
# Test health endpoint
curl http://localhost:8080/api/health

# Or visit in browser
http://localhost:8080/api/health
```

## API Endpoints Summary

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login with credentials

### Users
- `GET /api/users` - List all users
- `GET /api/users/{id}` - Get user details
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Courses
- `GET /api/courses` - List all courses
- `POST /api/courses` - Create course (Instructor)
- `GET /api/courses/{id}` - Get course details
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

### Enrollments
- `POST /api/enrollments` - Enroll in course
- `GET /api/enrollments` - Get enrollments
- `GET /api/enrollments/{id}` - Get enrollment details

### Assessments
- `GET /api/assessments/course/{courseId}` - Get course assessments
- `POST /api/assessments` - Create assessment
- `PUT /api/assessments/{id}` - Update assessment

### Submissions
- `POST /api/submissions` - Submit assignment
- `GET /api/submissions/{id}` - Get submission
- `PUT /api/submissions/{id}/grade` - Grade submission

### Progress
- `GET /api/progress/{courseId}` - Get course progress
- `GET /api/progress/student/{studentId}` - Get student progress

## Default Credentials (After Seeding)

After first run, sample data is seeded:

**Admin Account:**
- Username: `admin`
- Password: `admin123`
- Email: `admin@coursemate.com`

**Instructor Account:**
- Username: `instructor1`
- Password: `instructor123`
- Email: `instructor1@coursemate.com`

**Student Accounts:**
- Username: `student1`, Password: `student123`, Email: `student1@coursemate.com`
- Username: `student2`, Password: `student123`, Email: `student2@coursemate.com`

## Configuration

Edit `src/main/resources/application.properties` to modify:

```properties
# Server Port
server.port=8080

# Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/coursemate_db
spring.datasource.username=root
spring.datasource.password=root

# JWT Settings
app.jwt.secret=coursemate_secret_key_for_jwt_token_generation_min_32_chars_long
app.jwt.expiration=86400000
```

## Troubleshooting

### Port 8080 Already in Use
```powershell
# Change port in application.properties
server.port=8081
```

### MySQL Connection Error
```powershell
# Verify MySQL is running
mysql -u root -p

# Check credentials in application.properties
```

### Dependency Download Issues
```powershell
# Clear Maven cache and rebuild
mvn clean install -U
```

### Java/Maven Not Found
```powershell
# Restart PowerShell/Terminal after installation
# Verify installations
java -version
mvn -version
```

## Project Structure

```
Course Mate/
├── src/main/java/com/coursemate/
│   ├── CourseMateApplication.java
│   ├── controller/          # REST API endpoints
│   ├── service/             # Business logic
│   ├── entity/              # JPA entities
│   ├── dto/                 # Data transfer objects
│   ├── repository/          # Data access layer
│   ├── config/              # Spring configuration
│   ├── security/            # JWT & Security
│   └── exception/           # Error handling
├── src/main/resources/
│   └── application.properties
├── pom.xml                  # Maven configuration
├── README.md                # Full documentation
├── SETUP_GUIDE.md           # Detailed setup
├── API_DOCUMENTATION.md     # API reference
└── RUN.bat / RUN.sh         # Start scripts
```

## Support

For issues or questions, refer to:
- `README.md` - Full documentation
- `SETUP_GUIDE.md` - Detailed setup instructions
- `API_DOCUMENTATION.md` - Complete API reference

---

**Happy coding! 🎉**

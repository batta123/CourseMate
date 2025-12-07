# 📋 Course Mate Backend - Complete Setup & Launch Guide

## 📥 What You Have

Your Course Mate folder now contains a **complete Spring Boot backend** with:
- ✅ **7 Entities** (User, Role, Course, Enrollment, Assessment, Submission, Progress)
- ✅ **7 Services** with full business logic
- ✅ **7 REST Controllers** with CRUD endpoints
- ✅ **JWT Authentication** with role-based access
- ✅ **MySQL Database** integration
- ✅ **Sample Data Seeder** with test users and courses
- ✅ **Exception Handling** with @ControllerAdvice
- ✅ **Full API Documentation**

---

## 🎯 Quick Summary

| Component | Technology | Details |
|-----------|-----------|---------|
| Framework | Spring Boot 3.1.5 | Latest version with Java 17 |
| Database | MySQL 8.0+ | Configured in application.properties |
| Authentication | JWT (JJWT 0.11.5) | Token-based auth with roles |
| ORM | Spring Data JPA | Hibernate for database mapping |
| Build Tool | Maven 3.8+ | Dependency management |
| Default Port | 8080 | Configurable in application.properties |

---

## 🚀 FASTEST WAY TO RUN (Windows)

### One-Time Setup
```powershell
# 1. Open PowerShell as Administrator
# 2. Install Java: https://adoptium.net/ (select Java 17)
# 3. Install Maven: https://maven.apache.org/download.cgi
# 4. Install MySQL: https://dev.mysql.com/downloads/mysql/
# 5. Create database:
mysql -u root -p
CREATE DATABASE coursemate_db;
EXIT
```

### Every Time You Want to Run
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
# Wait for: "Started CourseMateApplication"
# Then visit: http://localhost:8080
```

---

## 📋 Complete Step-by-Step Setup

See detailed instructions in: **SETUP_STEP_BY_STEP.md**

---

## 🔐 Sample Login Credentials

After first run, use these to test:

| Role | Username | Password | Email |
|------|----------|----------|-------|
| Admin | `admin` | `admin123` | admin@coursemate.com |
| Instructor | `instructor1` | `instructor123` | instructor1@coursemate.com |
| Student | `student1` | `student123` | student1@coursemate.com |
| Student | `student2` | `student123` | student2@coursemate.com |

---

## 📁 Project Structure

```
c:\Users\batta\OneDrive\Desktop\Course Mate\
├── src/main/java/com/coursemate/
│   ├── CourseMateApplication.java        ← Main entry point
│   ├── controller/                       ← REST endpoints (7 files)
│   │   ├── AuthController.java
│   │   ├── CourseController.java
│   │   ├── UserController.java
│   │   ├── EnrollmentController.java
│   │   ├── AssessmentController.java
│   │   ├── SubmissionController.java
│   │   └── ProgressController.java
│   ├── service/                          ← Business logic (7 interfaces + 7 impl)
│   │   ├── AuthService.java
│   │   ├── CourseService.java
│   │   ├── impl/
│   │   │   ├── AuthServiceImpl.java
│   │   │   ├── CourseServiceImpl.java
│   │   │   └── ... (5 more implementations)
│   ├── entity/                           ← JPA Entities (7 files)
│   │   ├── User.java
│   │   ├── Role.java
│   │   ├── Course.java
│   │   ├── Enrollment.java
│   │   ├── Assessment.java
│   │   ├── Submission.java
│   │   └── Progress.java
│   ├── dto/                              ← Data Transfer Objects
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── AuthResponse.java
│   │   ├── CourseDTO.java
│   │   └── ... (more DTOs)
│   ├── repository/                       ← Database Access (7 interfaces)
│   │   ├── UserRepository.java
│   │   ├── CourseRepository.java
│   │   └── ... (5 more)
│   ├── config/                           ← Spring Configuration
│   │   ├── SecurityConfig.java           ← JWT & Security setup
│   │   ├── CustomUserDetailsService.java ← User loading
│   │   └── DataSeeder.java               ← Sample data
│   ├── security/                         ← JWT Implementation
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtAuthenticationEntryPoint.java
│   └── exception/                        ← Error Handling
│       ├── GlobalExceptionHandler.java
│       ├── ResourceNotFoundException.java
│       └── BadRequestException.java
├── src/main/resources/
│   └── application.properties             ← Database & Server Config
├── pom.xml                               ← Maven Dependencies
├── README.md                             ← Full documentation
├── SETUP_GUIDE.md                        ← Installation guide
├── SETUP_STEP_BY_STEP.md                 ← Detailed walkthrough
├── API_DOCUMENTATION.md                  ← All endpoints
├── QUICK_START.md                        ← Quick reference
├── RUN.bat                               ← Windows start script
└── RUN.sh                                ← Linux/Mac start script
```

---

## 🔌 Main API Endpoints

### Authentication
```
POST   /api/auth/register      - Register new user
POST   /api/auth/login         - Login & get JWT token
```

### Users Management
```
GET    /api/users              - List all users
GET    /api/users/{id}         - Get user details
PUT    /api/users/{id}         - Update user
DELETE /api/users/{id}         - Delete user
```

### Course Management
```
GET    /api/courses            - List all courses
POST   /api/courses            - Create course (Instructor)
GET    /api/courses/{id}       - Get course details
PUT    /api/courses/{id}       - Update course (Instructor)
DELETE /api/courses/{id}       - Delete course (Admin)
```

### Course Enrollment
```
POST   /api/enrollments        - Enroll in course
GET    /api/enrollments        - List enrollments
GET    /api/enrollments/{id}   - Get enrollment
PUT    /api/enrollments/{id}   - Update enrollment
```

### Assessments
```
GET    /api/assessments                - List assessments
POST   /api/assessments                - Create assessment (Instructor)
GET    /api/assessments/{id}           - Get assessment
PUT    /api/assessments/{id}           - Update assessment
DELETE /api/assessments/{id}           - Delete assessment
GET    /api/assessments/course/{courseId} - Course assessments
```

### Submissions
```
POST   /api/submissions                    - Submit assignment
GET    /api/submissions/{id}               - Get submission
PUT    /api/submissions/{id}               - Update submission
PUT    /api/submissions/{id}/grade         - Grade submission (Instructor)
GET    /api/submissions/assessment/{id}    - Assessment submissions
```

### Progress Tracking
```
GET    /api/progress/course/{courseId}     - Get course progress
GET    /api/progress/student/{studentId}   - Get student progress
PUT    /api/progress/{id}                   - Update progress
```

---

## 🗄️ Database Configuration

Default settings in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coursemate_db
spring.datasource.username=root
spring.datasource.password=root
```

**To change:**
1. Open `application.properties`
2. Modify the above values
3. Restart the application

---

## 🔒 Security Features

- ✅ **JWT Token Authentication** - Secure API access
- ✅ **Role-Based Access Control** - Admin, Instructor, Student roles
- ✅ **Password Hashing** - BCrypt encryption
- ✅ **CORS Configuration** - Cross-origin request handling
- ✅ **Input Validation** - Request validation with @Valid
- ✅ **Exception Handling** - Consistent error responses

---

## 💾 Database Tables Created Automatically

When you first run the app, these tables are created:
- `users` - User accounts
- `roles` - User roles (Admin, Instructor, Student)
- `user_roles` - User-Role mapping
- `courses` - Course information
- `enrollments` - Student enrollments
- `assessments` - Quizzes, assignments, exams
- `submissions` - Student submissions
- `progress` - Student progress tracking

---

## ✨ Key Features Implemented

### 🔐 Authentication & Authorization
- JWT token-based authentication
- User registration and login
- Role-based endpoint access (Admin/Instructor/Student)
- Token validation and refresh

### 👥 User Management
- Create, read, update, delete users
- User profile management
- Role assignment
- User activation/deactivation

### 📚 Course Management
- Instructors create and manage courses
- Students browse and enroll in courses
- Course details, credits, capacity
- Active course filtering

### ✏️ Assessments & Submissions
- Create multiple assessment types (Quiz, Assignment, Exam, Project)
- Students submit assessments
- Instructors grade submissions
- Late submission tracking

### 📊 Progress Tracking
- Student progress per course
- Completion percentage calculation
- Average score tracking
- Submission rate monitoring
- Progress status (Not Started, In Progress, Completed, Failed)

### 🎯 Data Validation
- Request body validation with @Valid
- Custom validation logic
- Error handling with meaningful messages

---

## 🧪 Testing the API

### Using cURL (PowerShell)
```powershell
# Register
curl -X POST http://localhost:8080/api/auth/register `
  -H "Content-Type: application/json" `
  -d '{
    "firstName":"Test","lastName":"User","username":"testuser",
    "email":"test@example.com","password":"test123","role":"STUDENT"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d '{"username":"testuser","password":"test123"}'

# Get courses (replace TOKEN with JWT from login)
curl -X GET http://localhost:8080/api/courses `
  -H "Authorization: Bearer TOKEN"
```

### Using Postman
1. Download [Postman](https://www.postman.com/downloads/)
2. Create new request
3. Set URL: `http://localhost:8080/api/auth/login`
4. Method: `POST`
5. Body (JSON): `{"username":"admin","password":"admin123"}`
6. Send and copy the token
7. Use token in other requests: `Authorization: Bearer [TOKEN]`

---

## 🆘 Common Issues & Solutions

### Issue: "Java is not recognized"
**Solution:** 
1. Download Java 17 from https://adoptium.net/
2. Run installer with "Add to PATH" checked
3. Restart PowerShell

### Issue: "Maven not found"
**Solution:**
1. Download Maven from https://maven.apache.org/download.cgi
2. Set MAVEN_HOME environment variable
3. Add %MAVEN_HOME%\bin to PATH
4. Restart PowerShell

### Issue: "Cannot connect to MySQL"
**Solution:**
1. Ensure MySQL is running
2. Check credentials in application.properties
3. Create database: `CREATE DATABASE coursemate_db;`

### Issue: "Port 8080 already in use"
**Solution:** Change port in application.properties:
```properties
server.port=8081
```

### Issue: Build takes too long
**Solution:** First build downloads all dependencies. Subsequent builds are faster.

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| `README.md` | Full project documentation |
| `INSTALLATION.md` | Installation instructions |
| `SETUP_STEP_BY_STEP.md` | Detailed step-by-step guide |
| `SETUP_GUIDE.md` | General setup information |
| `QUICK_START.md` | Quick reference guide |
| `API_DOCUMENTATION.md` | Complete API reference |
| `THIS FILE` | Overview and getting started |

---

## 🎓 Learning Path

1. **Week 1**: Set up environment, run app, test sample data
2. **Week 2**: Study API endpoints, test with Postman
3. **Week 3**: Review service layer, understand business logic
4. **Week 4**: Study database schema, modify entities if needed
5. **Week 5+**: Build frontend to consume these APIs

---

## 🚀 Next Steps

1. **Install Prerequisites** (Java, Maven, MySQL)
2. **Run the Application** (`.\RUN.bat`)
3. **Test Sample Users** (use credentials above)
4. **Explore API** (use Postman or cURL)
5. **Build Frontend** (React, Vue, Angular - consuming these APIs)
6. **Customize** (modify entities, add features)

---

## ✅ Verification Checklist

After running the app, verify:

- [ ] Application starts without errors
- [ ] Can access `http://localhost:8080`
- [ ] Can login with `admin`/`admin123`
- [ ] Sample courses exist
- [ ] Students are enrolled
- [ ] JWT token works for authenticated requests

---

## 📞 Support Resources

- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **JWT Documentation**: https://jwt.io/
- **MySQL Documentation**: https://dev.mysql.com/doc/

---

## 🎉 You're All Set!

Your complete Spring Boot backend is ready to:
- ✅ Accept user registrations
- ✅ Authenticate with JWT tokens
- ✅ Manage courses and enrollments
- ✅ Handle assessments and submissions
- ✅ Track student progress
- ✅ Provide role-based access control

**Time to launch:** 
```powershell
cd "c:\Users\batta\OneDrive\Desktop\Course Mate"
.\RUN.bat
```

**Enjoy! 🚀**


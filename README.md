# Course Mate - Smart Learning Application Backend

A comprehensive Spring Boot REST API for a Smart Learning Management System (LMS) built with modern Java technologies.

## 🚀 Features

### Core Features
- **User Authentication**: JWT-based authentication with role-based access control
- **User Management**: Admin, Instructor, and Student roles
- **Course Management**: Create, update, delete, and manage courses
- **Student Enrollment**: Seamless course enrollment system with capacity management
- **Assessment Management**: Support for quizzes, assignments, exams, and projects
- **Submission Handling**: Students submit work; instructors grade submissions
- **Progress Tracking**: Real-time student progress calculation and analytics
- **Role-Based Access Control**: Fine-grained permission management

### Technical Features
- Spring Boot 3.1.5 with Spring Web, Spring Data JPA, and Spring Security
- JWT token-based authentication using jjwt library
- MySQL 8.0 database with Hibernate ORM
- Comprehensive exception handling with @ControllerAdvice
- Input validation with Jakarta Bean Validation
- RESTful API endpoints with consistent response format
- CORS support for frontend integration
- Database seeding with sample data

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

## 🔧 Installation & Setup

### Step 1: Clone or Download the Project
```bash
cd coursemate-backend
```

### Step 2: Create MySQL Database
```sql
CREATE DATABASE coursemate_db;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON coursemate_db.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

**Note**: Adjust username/password/database name in `src/main/resources/application.properties` if needed.

### Step 3: Install Dependencies
```bash
mvn clean install
```

### Step 4: Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📊 Database Schema

### Entities
- **User**: Represents registered users (Admin, Instructor, Student)
- **Role**: User roles with permissions
- **Course**: Academic courses taught by instructors
- **Enrollment**: Student enrollment in courses
- **Assessment**: Quizzes, assignments, exams, and projects
- **Submission**: Student submissions for assessments
- **Progress**: Student progress tracking in courses

### Entity Relationships
```
User (1) ---> (M) Role
User (1) ---> (M) Course (as instructor)
User (1) ---> (M) Enrollment (as student)
Course (1) ---> (M) Enrollment
Course (1) ---> (M) Assessment
Assessment (1) ---> (M) Submission
User (1) ---> (M) Submission (as student)
User (1) ---> (M) Progress (as student)
Course (1) ---> (M) Progress
```

## 🔐 Sample Users for Testing

The database seeder creates sample users on first run:

### Admin
- **Username**: admin
- **Password**: admin123
- **Email**: admin@coursemate.com

### Instructors
- **Username**: instructor1
- **Password**: instructor123
- **Email**: instructor1@coursemate.com

- **Username**: instructor2
- **Password**: instructor123
- **Email**: instructor2@coursemate.com

### Students
- **Username**: student1
- **Password**: student123
- **Email**: student1@coursemate.com

- **Username**: student2
- **Password**: student123
- **Email**: student2@coursemate.com

- **Username**: student3
- **Password**: student123
- **Email**: student3@coursemate.com

## 📡 API Endpoints

### Authentication Endpoints (`/api/auth`)
- `POST /register` - Register new user
- `POST /login` - Login and get JWT token
- `POST /refresh` - Refresh JWT token
- `POST /logout` - Logout (invalidate token)

### User Endpoints (`/api/users`)
- `GET /{id}` - Get user by ID
- `GET /username/{username}` - Get user by username
- `PUT /{id}` - Update user profile
- `DELETE /{id}` - Delete user (Admin only)
- `GET /instructors/all` - Get all instructors
- `GET /students/all` - Get all students (Admin only)
- `GET /all` - Get all users (Admin only)

### Course Endpoints (`/api/courses`)
- `POST` - Create course (Instructor only)
- `GET /{id}` - Get course details
- `PUT /{id}` - Update course (Instructor only)
- `DELETE /{id}` - Delete course (Admin only)
- `GET /list/all` - Get all courses
- `GET /list/active` - Get active courses
- `GET /instructor/{instructorId}` - Get instructor's courses
- `GET /student/enrolled` - Get student's enrolled courses
- `GET /search?keyword=` - Search courses
- `GET /code/{courseCode}` - Get course by code

### Enrollment Endpoints (`/api/enrollments`)
- `POST?courseId=` - Enroll student in course
- `GET /{id}` - Get enrollment details
- `PUT /{id}` - Update enrollment (Admin only)
- `DELETE /{id}` - Remove enrollment
- `GET /student/{studentId}` - Get student's enrollments
- `GET /course/{courseId}` - Get course enrollments (Instructor)
- `GET /check?studentId=&courseId=` - Check enrollment status
- `GET /course/{courseId}/count` - Get enrollment count

### Assessment Endpoints (`/api/assessments`)
- `POST` - Create assessment (Instructor only)
- `GET /{id}` - Get assessment details
- `PUT /{id}` - Update assessment (Instructor only)
- `DELETE /{id}` - Delete assessment (Instructor only)
- `GET /course/{courseId}` - Get course assessments
- `GET /course/{courseId}/published` - Get published assessments
- `PUT /{id}/publish` - Publish assessment (Instructor only)
- `GET /course/{courseId}/type/{type}` - Get assessments by type

### Submission Endpoints (`/api/submissions`)
- `POST` - Submit assignment (Student)
- `GET /{id}` - Get submission details
- `PUT /{id}/grade` - Grade submission (Instructor only)
- `DELETE /{id}` - Delete submission (Instructor only)
- `GET /assessment/{assessmentId}` - Get assessment submissions (Instructor)
- `GET /student/{studentId}` - Get student submissions
- `GET /student/{studentId}/assessment/{assessmentId}` - Get specific submission
- `GET /course/{courseId}/student` - Get student's course submissions
- `GET /assessment/{assessmentId}/count` - Get submission count
- `GET /assessment/{assessmentId}/average` - Get average marks

### Progress Endpoints (`/api/progress`)
- `GET /student/{studentId}/course/{courseId}` - Get student progress
- `PUT /{id}` - Update progress (Admin only)
- `GET /student/{studentId}` - Get all student progress
- `GET /course/{courseId}` - Get course progress (Instructor)
- `DELETE /{id}` - Delete progress (Admin only)
- `POST /calculate/{studentId}/{courseId}` - Calculate/update progress
- `GET /course/{courseId}/average-completion` - Get average completion (Instructor)
- `GET /course/{courseId}/average-score` - Get average score (Instructor)
- `GET /my-progress/{courseId}` - Get current user's progress (Student)

## 🔑 Authentication

### JWT Token Format
- **Header**: `Authorization: Bearer {token}`
- **Token Expiry**: 24 hours (configurable in `application.properties`)

### Login Flow
1. Send credentials to `/api/auth/login`
2. Receive JWT token in response
3. Include token in Authorization header for authenticated requests
4. Token automatically extracted and validated by JWT filter

### Role-Based Access
- **ADMIN**: Full system access
- **INSTRUCTOR**: Manage own courses, view student submissions, grade assessments
- **STUDENT**: View enrolled courses, submit assignments, track progress

## 📝 Example API Usage

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123",
    "role": "STUDENT"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

### Get All Courses
```bash
curl -X GET http://localhost:8080/api/courses/list/all
```

### Create Course (Instructor)
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Java",
    "description": "Advanced Java concepts",
    "courseCode": "CS301",
    "credits": 4,
    "maxStudents": 30
  }'
```

### Enroll in Course (Student)
```bash
curl -X POST "http://localhost:8080/api/enrollments?courseId=1" \
  -H "Authorization: Bearer {token}"
```

### Submit Assignment (Student)
```bash
curl -X POST http://localhost:8080/api/submissions \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "assessmentId": 1,
    "studentId": 5,
    "submissionContent": "My solution here...",
    "submissionFileUrl": "https://example.com/file.pdf"
  }'
```

## 🏗️ Project Structure

```
coursemate-backend/
├── src/main/java/com/coursemate/
│   ├── controller/          # REST Controllers
│   ├── service/             # Service interfaces
│   ├── service/impl/        # Service implementations
│   ├── repository/          # JPA Repositories
│   ├── entity/              # JPA Entities
│   ├── dto/                 # Data Transfer Objects
│   ├── config/              # Configuration classes
│   │   ├── SecurityConfig.java
│   │   ├── CustomUserDetailsService.java
│   │   └── DataSeeder.java
│   ├── security/            # JWT and Security
│   ├── exception/           # Exception handling
│   └── CourseMateApplication.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 🔍 Key Configuration Files

### `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coursemate_db
spring.datasource.username=root
spring.datasource.password=root
app.jwt.secret=coursemate_secret_key_for_jwt_token_generation_min_32_chars_long
app.jwt.expiration=86400000
```

## 🛡️ Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Encoding**: BCrypt password hashing
- **CORS Support**: Configured for frontend domain
- **Role-Based Access Control**: Method-level security annotations
- **Exception Handling**: Centralized error handling with proper HTTP status codes
- **Input Validation**: Request validation with meaningful error messages

## 🚨 Error Handling

All errors follow a consistent format:
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": 1234567890
}
```

Common HTTP Status Codes:
- `200 OK` - Successful request
- `201 Created` - Resource created
- `400 Bad Request` - Invalid input
- `401 Unauthorized` - Missing/invalid token
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## 📈 Database Optimization

- Lazy loading for relationships
- Proper indexing on frequently queried columns
- Pagination support for large datasets
- Query optimization with custom repository methods

## 🧪 Testing the API

### Using Postman
1. Import the endpoints from the API documentation
2. Create an environment with `base_url=http://localhost:8080`
3. Use the sample users to test different roles
4. Test role-based access restrictions

### Using cURL
See examples above for common operations

## 📚 Technologies Used

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Hibernate with Spring Data JPA
- **Authentication**: JWT with jjwt
- **Build Tool**: Maven
- **API Documentation**: RESTful API

## 🤝 Contributing

This is a sample project for educational purposes. Feel free to extend and customize based on your needs.

## 📄 License

This project is open source and available under the MIT License.

## 📞 Support

For issues or questions, please check the code comments and documentation in the respective classes.

## 🔮 Future Enhancements

- Real-time notifications
- File upload for submissions
- Advanced analytics dashboard
- Email notifications
- Two-factor authentication
- API rate limiting
- Caching with Redis
- GraphQL API support

---

**Last Updated**: December 2024
**Backend Version**: 1.0.0

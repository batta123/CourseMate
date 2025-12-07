# Course Mate API Documentation

Complete REST API documentation for the Course Mate Smart Learning Application.

## Base URL
```
http://localhost:8080
```

## Authentication
All endpoints except `/api/auth/**` and `/api/public/**` require JWT authentication.

**Header Format:**
```
Authorization: Bearer {token}
```

---

## Authentication Endpoints

### 1. Register User
**Endpoint:** `POST /api/auth/register`

**Description:** Create a new user account

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "role": "STUDENT"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "roles": ["ROLE_STUDENT"]
  },
  "timestamp": 1702000000000
}
```

**Validation:**
- First name and last name: required, non-blank
- Username: required, 3-50 characters, unique
- Email: required, valid email format, unique
- Password: required, 6-100 characters
- Role: required, must be STUDENT, INSTRUCTOR, or ADMIN

---

### 2. Login
**Endpoint:** `POST /api/auth/login`

**Description:** Authenticate user and receive JWT token

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "roles": ["ROLE_STUDENT"]
  },
  "timestamp": 1702000000000
}
```

**Error Response (401 Unauthorized):**
```json
{
  "success": false,
  "message": "Authentication failed: Invalid username or password",
  "data": null,
  "timestamp": 1702000000000
}
```

---

### 3. Refresh Token
**Endpoint:** `POST /api/auth/refresh`

**Header:**
```
Authorization: Bearer {expired_token}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "roles": ["ROLE_STUDENT"]
  },
  "timestamp": 1702000000000
}
```

---

### 4. Logout
**Endpoint:** `POST /api/auth/logout`

**Header:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Logout successful",
  "data": null,
  "timestamp": 1702000000000
}
```

---

## User Endpoints

### 1. Get User by ID
**Endpoint:** `GET /api/users/{id}`

**Authentication:** Required (All roles)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User fetched successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "username": "johndoe",
    "email": "john@example.com",
    "bio": "Computer Science enthusiast",
    "profilePictureUrl": "https://example.com/pic.jpg",
    "isActive": true,
    "fullName": "John Doe"
  },
  "timestamp": 1702000000000
}
```

---

### 2. Get User by Username
**Endpoint:** `GET /api/users/username/{username}`

**Authentication:** Required (All roles)

**Example:** `GET /api/users/username/johndoe`

**Response:** Same as Get User by ID

---

### 3. Update User Profile
**Endpoint:** `PUT /api/users/{id}`

**Authentication:** Required (All roles - can edit own or if admin)

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "bio": "Updated bio",
  "profilePictureUrl": "https://example.com/new-pic.jpg"
}
```

**Response (200 OK):**
Same as Get User by ID

---

### 4. Delete User
**Endpoint:** `DELETE /api/users/{id}`

**Authentication:** Required (Admin only)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "User deleted successfully",
  "data": null,
  "timestamp": 1702000000000
}
```

---

### 5. Get All Instructors
**Endpoint:** `GET /api/users/instructors/all`

**Authentication:** Required (All roles)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Instructors fetched successfully",
  "data": [
    {
      "id": 2,
      "firstName": "Dr.",
      "lastName": "Smith",
      "username": "instructor1",
      "email": "instructor1@coursemate.com",
      "bio": "Computer Science expert",
      "profilePictureUrl": null,
      "isActive": true,
      "fullName": "Dr. Smith"
    }
  ],
  "timestamp": 1702000000000
}
```

---

### 6. Get All Students
**Endpoint:** `GET /api/users/students/all`

**Authentication:** Required (Admin only)

**Response:** List of student objects (same format as instructors)

---

### 7. Get All Users
**Endpoint:** `GET /api/users/all`

**Authentication:** Required (Admin only)

**Response:** List of all user objects

---

## Course Endpoints

### 1. Create Course
**Endpoint:** `POST /api/courses`

**Authentication:** Required (Instructor only)

**Request Body:**
```json
{
  "title": "Advanced Java Programming",
  "description": "Learn advanced Java concepts and design patterns",
  "courseCode": "CS301",
  "credits": 4,
  "maxStudents": 30
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Course created successfully",
  "data": {
    "id": 1,
    "title": "Advanced Java Programming",
    "description": "Learn advanced Java concepts and design patterns",
    "courseCode": "CS301",
    "credits": 4,
    "maxStudents": 30,
    "isActive": true,
    "instructorId": 2,
    "instructorName": "Dr. Smith",
    "enrolledStudentsCount": 0
  },
  "timestamp": 1702000000000
}
```

---

### 2. Get Course by ID
**Endpoint:** `GET /api/courses/{id}`

**Authentication:** Required (All roles)

**Response (200 OK):** Same as Create Course response

---

### 3. Update Course
**Endpoint:** `PUT /api/courses/{id}`

**Authentication:** Required (Instructor only - owner of course)

**Request Body:** (Only fields to update)
```json
{
  "title": "Updated Course Title",
  "description": "Updated description",
  "credits": 3,
  "maxStudents": 25,
  "isActive": true
}
```

**Response (200 OK):** Updated course object

---

### 4. Delete Course
**Endpoint:** `DELETE /api/courses/{id}`

**Authentication:** Required (Admin only)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Course deleted successfully"
}
```

---

### 5. Get All Courses
**Endpoint:** `GET /api/courses/list/all`

**Authentication:** Not required

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Courses fetched successfully",
  "data": [
    {
      "id": 1,
      "title": "Advanced Java Programming",
      "description": "Learn advanced Java concepts",
      "courseCode": "CS301",
      "credits": 4,
      "maxStudents": 30,
      "isActive": true,
      "instructorId": 2,
      "instructorName": "Dr. Smith",
      "enrolledStudentsCount": 5
    }
  ],
  "timestamp": 1702000000000
}
```

---

### 6. Get Active Courses
**Endpoint:** `GET /api/courses/list/active`

**Authentication:** Not required

**Response:** List of active courses only

---

### 7. Get Courses by Instructor
**Endpoint:** `GET /api/courses/instructor/{instructorId}`

**Authentication:** Required (Admin or the Instructor)

**Response:** List of courses taught by the instructor

---

### 8. Get Enrolled Courses (for Current Student)
**Endpoint:** `GET /api/courses/student/enrolled`

**Authentication:** Required (Student only)

**Response:** List of courses the logged-in student is enrolled in

---

### 9. Search Courses
**Endpoint:** `GET /api/courses/search?keyword=Java`

**Authentication:** Not required

**Parameters:**
- `keyword` (required, string): Search term for course title or code

**Response:** Matching courses

---

### 10. Get Course by Course Code
**Endpoint:** `GET /api/courses/code/{courseCode}`

**Authentication:** Not required

**Example:** `GET /api/courses/code/CS301`

**Response:** Single course object

---

## Enrollment Endpoints

### 1. Enroll Student in Course
**Endpoint:** `POST /api/enrollments?courseId={courseId}`

**Authentication:** Required (Student only)

**Parameters:**
- `courseId` (required, integer): ID of the course to enroll in

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Enrolled successfully",
  "data": {
    "id": 1,
    "studentId": 5,
    "studentName": "John Doe",
    "courseId": 1,
    "courseName": "Advanced Java Programming",
    "status": "ACTIVE",
    "grade": 0.0,
    "enrolledAt": "2024-01-15 10:30:00"
  },
  "timestamp": 1702000000000
}
```

**Error Response (400 Bad Request):**
- Student already enrolled in the course
- Course is full

---

### 2. Get Enrollment by ID
**Endpoint:** `GET /api/enrollments/{id}`

**Authentication:** Required (All roles)

**Response (200 OK):** Enrollment object

---

### 3. Update Enrollment
**Endpoint:** `PUT /api/enrollments/{id}`

**Authentication:** Required (Admin only)

**Request Body:**
```json
{
  "status": "COMPLETED",
  "grade": 85.5
}
```

**Response (200 OK):** Updated enrollment object

---

### 4. Remove Enrollment
**Endpoint:** `DELETE /api/enrollments/{id}`

**Authentication:** Required (Admin or Student)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Enrollment removed successfully"
}
```

---

### 5. Get Student Enrollments
**Endpoint:** `GET /api/enrollments/student/{studentId}`

**Authentication:** Required (Admin or Student)

**Response:** List of enrollments for the student

---

### 6. Get Course Enrollments
**Endpoint:** `GET /api/enrollments/course/{courseId}`

**Authentication:** Required (Admin or Instructor)

**Response:** List of enrollments in the course

---

### 7. Check Enrollment Status
**Endpoint:** `GET /api/enrollments/check?studentId={studentId}&courseId={courseId}`

**Authentication:** Required (Admin, Instructor, or Student)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Enrollment status checked",
  "data": true,
  "timestamp": 1702000000000
}
```

---

### 8. Get Enrollment Count
**Endpoint:** `GET /api/enrollments/course/{courseId}/count`

**Authentication:** Not required

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Enrollment count fetched",
  "data": 25,
  "timestamp": 1702000000000
}
```

---

## Assessment Endpoints

### 1. Create Assessment
**Endpoint:** `POST /api/assessments`

**Authentication:** Required (Instructor only)

**Request Body:**
```json
{
  "title": "Midterm Exam",
  "description": "Comprehensive exam covering topics 1-5",
  "type": "EXAM",
  "courseId": 1,
  "totalMarks": 100.0,
  "passingMarks": 40.0,
  "dueDate": "2024-02-15T18:00:00"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Assessment created successfully",
  "data": {
    "id": 1,
    "title": "Midterm Exam",
    "description": "Comprehensive exam",
    "type": "EXAM",
    "courseId": 1,
    "courseName": "Advanced Java",
    "totalMarks": 100.0,
    "passingMarks": 40.0,
    "dueDate": "2024-02-15T18:00:00",
    "isPublished": false,
    "submissionCount": 0
  },
  "timestamp": 1702000000000
}
```

**Assessment Types:**
- `QUIZ`: Short quick assessments
- `ASSIGNMENT`: Homework assignments
- `EXAM`: Full examinations
- `PROJECT`: Project-based assessments
- `PARTICIPATION`: Participation grades

---

### 2. Get Assessment by ID
**Endpoint:** `GET /api/assessments/{id}`

**Authentication:** Required (All roles)

**Response:** Assessment object

---

### 3. Update Assessment
**Endpoint:** `PUT /api/assessments/{id}`

**Authentication:** Required (Instructor only)

**Request Body:** (Fields to update)
```json
{
  "title": "Updated Title",
  "totalMarks": 150.0,
  "dueDate": "2024-02-20T18:00:00"
}
```

**Response:** Updated assessment object

---

### 4. Delete Assessment
**Endpoint:** `DELETE /api/assessments/{id}`

**Authentication:** Required (Instructor only)

**Response:**
```json
{
  "success": true,
  "message": "Assessment deleted successfully"
}
```

---

### 5. Get Assessments by Course
**Endpoint:** `GET /api/assessments/course/{courseId}`

**Authentication:** Required (All roles)

**Response:** List of all assessments in the course

---

### 6. Get Published Assessments by Course
**Endpoint:** `GET /api/assessments/course/{courseId}/published`

**Authentication:** Required (All roles)

**Response:** List of published assessments only

---

### 7. Publish Assessment
**Endpoint:** `PUT /api/assessments/{id}/publish`

**Authentication:** Required (Instructor only)

**Response:** Published assessment object

---

### 8. Get Assessments by Type
**Endpoint:** `GET /api/assessments/course/{courseId}/type/{type}`

**Authentication:** Required (All roles)

**Example:** `GET /api/assessments/course/1/type/QUIZ`

**Response:** List of assessments of specified type

---

## Submission Endpoints

### 1. Submit Assignment
**Endpoint:** `POST /api/submissions`

**Authentication:** Required (Student only)

**Request Body:**
```json
{
  "assessmentId": 1,
  "studentId": 5,
  "submissionContent": "My solution code here...",
  "submissionFileUrl": "https://example.com/assignment.pdf"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Assignment submitted successfully",
  "data": {
    "id": 1,
    "assessmentId": 1,
    "assessmentTitle": "Midterm Exam",
    "studentId": 5,
    "studentName": "John Doe",
    "submissionContent": "My solution...",
    "submissionFileUrl": "https://example.com/assignment.pdf",
    "marksObtained": null,
    "feedback": null,
    "submittedAt": "2024-01-15 10:30:00",
    "gradedAt": null,
    "status": "SUBMITTED"
  },
  "timestamp": 1702000000000
}
```

**Submission Status:**
- `SUBMITTED`: Initial submission
- `LATE`: Submitted after due date
- `GRADED`: Instructor has graded
- `MISSING`: Not submitted

---

### 2. Get Submission by ID
**Endpoint:** `GET /api/submissions/{id}`

**Authentication:** Required (All roles)

**Response:** Submission object

---

### 3. Grade Submission
**Endpoint:** `PUT /api/submissions/{id}/grade`

**Authentication:** Required (Instructor only)

**Parameters:**
- `marksObtained` (required, decimal): Marks obtained
- `feedback` (optional, string): Feedback for student

**Example:** `PUT /api/submissions/1/grade?marksObtained=85.5&feedback=Great work`

**Response:** Updated submission with status GRADED

---

### 4. Delete Submission
**Endpoint:** `DELETE /api/submissions/{id}`

**Authentication:** Required (Instructor only)

**Response:**
```json
{
  "success": true,
  "message": "Submission deleted successfully"
}
```

---

### 5. Get Submissions by Assessment
**Endpoint:** `GET /api/submissions/assessment/{assessmentId}`

**Authentication:** Required (Admin or Instructor)

**Response:** List of submissions for the assessment

---

### 6. Get Student Submissions
**Endpoint:** `GET /api/submissions/student/{studentId}`

**Authentication:** Required (All roles)

**Response:** List of all student submissions

---

### 7. Get Specific Student Submission
**Endpoint:** `GET /api/submissions/student/{studentId}/assessment/{assessmentId}`

**Authentication:** Required (All roles)

**Response:** Student's submission for specific assessment

---

### 8. Get Student Course Submissions
**Endpoint:** `GET /api/submissions/course/{courseId}/student`

**Authentication:** Required (All roles - gets current user's submissions)

**Response:** List of student submissions in the course

---

### 9. Get Submission Count
**Endpoint:** `GET /api/submissions/assessment/{assessmentId}/count`

**Authentication:** Required (Admin or Instructor)

**Response:** Number of submissions for the assessment

---

### 10. Get Average Marks
**Endpoint:** `GET /api/submissions/assessment/{assessmentId}/average`

**Authentication:** Required (Admin or Instructor)

**Response:** Average marks for the assessment

---

## Progress Endpoints

### 1. Get Student Progress in Course
**Endpoint:** `GET /api/progress/student/{studentId}/course/{courseId}`

**Authentication:** Required (All roles)

**Response:**
```json
{
  "success": true,
  "message": "Progress fetched successfully",
  "data": {
    "id": 1,
    "studentId": 5,
    "studentName": "John Doe",
    "courseId": 1,
    "courseName": "Advanced Java",
    "completionPercentage": 75.5,
    "averageScore": 82.3,
    "status": "IN_PROGRESS",
    "totalAssignmentsSubmitted": 6,
    "totalAssignmentsAssigned": 8,
    "submissionRate": 75.0,
    "startDate": "2024-01-01 10:00:00",
    "lastUpdated": "2024-01-15 14:30:00"
  },
  "timestamp": 1702000000000
}
```

**Progress Status:**
- `NOT_STARTED`: No submissions yet
- `IN_PROGRESS`: Some submissions completed
- `COMPLETED`: All assignments submitted
- `ON_HOLD`: Temporarily paused
- `FAILED`: Student hasn't met minimum requirements

---

### 2. Update Progress
**Endpoint:** `PUT /api/progress/{id}`

**Authentication:** Required (Admin only)

**Request Body:**
```json
{
  "completionPercentage": 85.5,
  "averageScore": 88.0,
  "status": "IN_PROGRESS"
}
```

**Response:** Updated progress object

---

### 3. Get All Progress for Student
**Endpoint:** `GET /api/progress/student/{studentId}`

**Authentication:** Required (All roles)

**Response:** List of progress objects for all courses

---

### 4. Get All Progress in Course
**Endpoint:** `GET /api/progress/course/{courseId}`

**Authentication:** Required (Admin or Instructor)

**Response:** List of all students' progress in the course

---

### 5. Delete Progress
**Endpoint:** `DELETE /api/progress/{id}`

**Authentication:** Required (Admin only)

**Response:**
```json
{
  "success": true,
  "message": "Progress deleted successfully"
}
```

---

### 6. Calculate/Update Progress
**Endpoint:** `POST /api/progress/calculate/{studentId}/{courseId}`

**Authentication:** Required (Admin or Instructor)

**Response:** Recalculated progress object

---

### 7. Get Average Course Completion
**Endpoint:** `GET /api/progress/course/{courseId}/average-completion`

**Authentication:** Required (Admin or Instructor)

**Response:** Average completion percentage across all students

---

### 8. Get Average Course Score
**Endpoint:** `GET /api/progress/course/{courseId}/average-score`

**Authentication:** Required (Admin or Instructor)

**Response:** Average score for the course

---

### 9. Get Current User's Progress
**Endpoint:** `GET /api/progress/my-progress/{courseId}`

**Authentication:** Required (Student only)

**Response:** Current logged-in student's progress

---

## Error Responses

### Common Error Format
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": 1702000000000
}
```

### Common HTTP Status Codes

| Code | Meaning | Example |
|------|---------|---------|
| 200 | OK | Successful GET/PUT request |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid input data |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Internal server error |

### Common Error Messages

- `"Unauthorized: Full authentication is required"` (401)
- `"User not found with username: 'xyz'"` (404)
- `"Course not found with id: '123'"` (404)
- `"Username is already taken"` (400)
- `"Student is already enrolled in this course"` (400)
- `"Course is full. Maximum students reached."` (400)

---

## Rate Limiting

Currently, there is no rate limiting implemented. Consider adding it in production.

## Version

**API Version:** 1.0.0  
**Last Updated:** December 2024

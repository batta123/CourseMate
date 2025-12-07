# Course Mate Backend - Setup and Installation Guide

## Quick Start Guide

### Prerequisites
- Java Development Kit (JDK) 17
- Apache Maven 3.6 or later
- MySQL Server 8.0 or later
- Git (optional)

### Step 1: Database Setup

#### Using MySQL Command Line:
```bash
mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS coursemate_db;
CREATE USER IF NOT EXISTS 'coursemate'@'localhost' IDENTIFIED BY 'coursemate123';
GRANT ALL PRIVILEGES ON coursemate_db.* TO 'coursemate'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### Using Default Credentials (Recommended for Development):
If using default root credentials, ensure:
- Database: `coursemate_db`
- Username: `root`
- Password: `root`

### Step 2: Clone/Extract Project

```bash
cd coursemate-backend
```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
# For custom credentials, update these:
spring.datasource.url=jdbc:mysql://localhost:3306/coursemate_db
spring.datasource.username=coursemate
spring.datasource.password=coursemate123
```

### Step 4: Build the Project

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile Java code
- Run any tests
- Package the application

### Step 5: Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/coursemate-backend-1.0.0.jar
```

### Step 6: Verify Installation

Open your browser and visit:
```
http://localhost:8080/api/courses/list/all
```

You should see a JSON response with courses.

## Initial Setup Complete! 🎉

The application will:
1. Create all database tables automatically
2. Seed sample data on first run
3. Be ready to accept API requests

## Testing the Setup

### 1. Login with Sample Admin Account

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "admin",
    "email": "admin@coursemate.com",
    "fullName": "Admin User",
    "roles": ["ROLE_ADMIN"]
  },
  "timestamp": 1234567890
}
```

### 2. Use the Token for Authenticated Requests

```bash
curl -X GET http://localhost:8080/api/users/all \
  -H "Authorization: Bearer {YOUR_TOKEN_HERE}"
```

### 3. Try Different Roles

**Instructor Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "instructor1",
    "password": "instructor123"
  }'
```

**Student Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "password": "student123"
  }'
```

## Troubleshooting

### Issue: Connection Refused to MySQL
**Solution:**
1. Verify MySQL is running: `systemctl status mysql` (Linux) or check Services (Windows)
2. Check connection string in `application.properties`
3. Verify credentials are correct
4. Ensure database exists: `SHOW DATABASES;`

### Issue: Port 8080 Already in Use
**Solution:**
Change the port in `application.properties`:
```properties
server.port=8081
```

Or kill the process using port 8080:
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Issue: Maven Build Fails
**Solution:**
```bash
mvn clean
mvn install -U
```

### Issue: Database Already Seeded
The seeder checks if data exists before creating new data. To reset:
```sql
DROP DATABASE coursemate_db;
CREATE DATABASE coursemate_db;
```

Then restart the application.

### Issue: Permission Denied Errors
**Solution:**
Ensure your MySQL user has proper permissions:
```sql
GRANT ALL PRIVILEGES ON coursemate_db.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

## IDE Setup (IntelliJ IDEA)

1. Open project folder
2. Configure Project SDK: File → Project Structure → Project → JDK 17
3. Mark folder as source: src/main/java → Mark Directory as → Sources Root
4. Mark folder as resources: src/main/resources → Mark Directory as → Resources Root
5. Run: Right-click CourseMateApplication.java → Run

## IDE Setup (Eclipse)

1. File → Import → Maven → Existing Maven Projects
2. Select the project folder
3. Finish
4. Right-click project → Run As → Maven clean
5. Right-click project → Run As → Maven install
6. Right-click CourseMateApplication.java → Run As → Java Application

## IDE Setup (VS Code)

1. Install Extensions:
   - Extension Pack for Java
   - Maven for Java
   - Spring Boot Extension Pack

2. Open the project folder

3. Terminal → Run Task → Select "Maven: clean install"

4. Press F5 to start debugging or use Terminal → Run Task → Maven start

## Application Properties Reference

```properties
# Server Configuration
server.port=8080                          # Port number
server.servlet.context-path=/             # API base path

# Database Configuration
spring.datasource.url=...                # JDBC URL
spring.datasource.username=root          # DB username
spring.datasource.password=root          # DB password

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update     # auto, create, update, none
spring.jpa.show-sql=false                # Show SQL queries

# JWT Configuration
app.jwt.secret=...                       # JWT secret key (min 32 chars)
app.jwt.expiration=86400000              # Token expiry in ms (24 hours)

# Logging
logging.level.root=INFO                  # Root log level
logging.level.com.coursemate=DEBUG       # App log level
```

## Environment Variables Setup (Optional)

Create a `.env` file in the project root:

```env
DATABASE_URL=jdbc:mysql://localhost:3306/coursemate_db
DATABASE_USER=root
DATABASE_PASSWORD=root
JWT_SECRET=coursemate_secret_key_for_jwt_token_generation_min_32_chars_long
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

## Docker Setup (Optional)

### Build Docker Image

```bash
docker build -t coursemate-backend:1.0.0 .
```

### Run with Docker Compose

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: coursemate_db
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/coursemate_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root

volumes:
  mysql_data:
```

Run with: `docker-compose up -d`

## Production Deployment Checklist

- [ ] Update `app.jwt.secret` with a strong, random value
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate`
- [ ] Change logging level to `INFO`
- [ ] Use production database credentials
- [ ] Configure HTTPS/SSL
- [ ] Set appropriate database connection pool size
- [ ] Enable database backups
- [ ] Configure monitoring and logging
- [ ] Set up CI/CD pipeline
- [ ] Test all API endpoints
- [ ] Configure firewall rules
- [ ] Set up CORS appropriately for production domain

## Performance Optimization Tips

1. **Enable Query Caching**:
   ```properties
   spring.jpa.properties.hibernate.cache.use_second_level_cache=true
   ```

2. **Adjust Connection Pool**:
   ```properties
   spring.datasource.hikari.maximum-pool-size=20
   ```

3. **Enable SQL Batch Processing**:
   ```properties
   spring.jpa.properties.hibernate.jdbc.batch_size=50
   ```

4. **Add Database Indexes**:
   ```sql
   CREATE INDEX idx_user_username ON users(username);
   CREATE INDEX idx_course_code ON courses(course_code);
   ```

## Next Steps

1. Configure frontend CORS origin in `SecurityConfig.java`
2. Integrate with your React frontend
3. Set up API documentation with Swagger/SpringDoc
4. Implement additional business logic as needed
5. Set up monitoring and alerting
6. Configure automated backups

## Support & Documentation

- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **Spring Data JPA**: https://spring.io/projects/spring-data-jpa
- **JWT (jjwt)**: https://github.com/jwtk/jjwt

---

**Last Updated**: December 2024

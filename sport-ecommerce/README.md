# Sport E-Commerce - Backend

This is the backend for **Sport E-Commerce**, a modern ecommerce platform for selling sports products, built with **Spring Boot**, **JWT Authentication**, and **PostgreSQL**.

---

## 🔧 Tech Stack

- ☕ Java 17
- ⚙️ Spring Boot 3.4.3
- 🔐 Spring Security 6 (JWT-based auth)
- 🗃️ Spring Data JPA + Hibernate
- 🐘 PostgreSQL
- 🛫 Flyway (DB migrations)
- ⚒️ Gradle
- 📦 Lombok + MapStruct

---

---

## 🚀 Deployment Info

Both the frontend and backend applications are deployed on Render using the free tier.

Please note that since free plans put services to sleep after inactivity, the initial request might take a few seconds to respond. We appreciate your patience!

🔗 URLs
🌐 Frontend (Web App): https://sport-ecommerce-1.onrender.com

🔙 Backend (API): https://sport-ecommerce.onrender.com

📘 Swagger API Docs: https://sport-ecommerce.onrender.com/swagger-ui/index.html

---

## Running the Project Locally

### Prerequisites

- Java 17
- PostgreSQL running locally on port `5432`
- Recommended IDE: **IntelliJ IDEA**

### Database and Properties Configuration (`application-dev.properties`)

Make sure you have the file configured `application-dev.properties` with the following variables:

```properties
# ----------------------------------
# SECURITY - JWT
# ----------------------------------
jwt.secret=<secret>
jwt.cookieName=<cookie-name>
jwt.jwtExpirationMs=<exp-time>

# ----------------------------------
# DATABASE - PostgreSQL
# ----------------------------------
spring.datasource.url=<db-url>
spring.datasource.username=<db-user>
spring.datasource.password=<db-password>
spring.datasource.hikari.auto-commit=false
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

> Make sure the database exists before running the app.  
> Flyway will automatically create the schema and populate it via `init_schema` and `seed_data`.

### Start the app

You can run it directly from IntelliJ (`▶`) or via Gradle:

```bash
./gradlew bootRun
```

The application will be available at:
📍 http://localhost:8080

## 🚀 Run with Docker

On the root folder, build the image:
```bash
docker build -t sport-ecommerce .
```

Run the container:
```bash
docker run -p 8080:8080 \
-e SPRING_PROFILES_ACTIVE=dev \
-e JWT_SECRET=<secret> \
-e JWT_COOKIE_NAME=JWT \
-e JWT_JWTEXPIRATIONMS=36000000 \
-e SPRING_DATASOURCE_URL=<db-url> \
-e SPRING_DATASOURCE_USERNAME=<username> \
-e SPRING_DATASOURCE_PASSWORD=<pass> \
sport-ecommerce
```


### 🔐 Authentication

This backend uses JWT tokens for authentication.
Public endpoints like /auth/** and /swagger-ui/** are accessible without a token. All others require a valid JWT token.

Demo credentials:
👤 User:  user@test.com / test1234  
🛠️ Admin: admin@test.com / test1234

Passwords are hashed with BCrypt and stored securely in the database.

### 📚 Swagger UI

Interactive API documentation is available via Swagger:

🔗 http://localhost:8080/swagger-ui/index.html

### 📌 Notes

- Flyway automatically runs database migrations on startup.
- application-dev.properties is for local dev. Avoid committing sensitive configs to source control.
- Environment-specific configs should be managed via environment variables or profiles (spring.profiles.active).


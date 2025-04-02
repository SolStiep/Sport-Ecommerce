# 🛍️ Sport E-Commerce

Sport E-Commerce is a modern and fully-featured e-commerce platform designed for selling sports products. It consists of a **React + Vite** frontend and a **Spring Boot** backend, providing seamless shopping experiences for customers and an intuitive admin dashboard for managing products, orders, and presets.

## 📖 Full project documentation: [Project Scope & Architecture](https://ethereal-magician-915.notion.site/Project-Scope-E-commerce-sport-shop-Achievable-in-2-Weeks-1bc64e827cec80d6bc06d7caa1c3844e)

This documentation includes:
- Project Scope: Business requirements and objectives.
- Project Architecture: technologies, decisions, class diagram and entity-relation diagram.

---

## 📦 Tech Stack

### Frontend [(view more)](https://github.com/SolStiep/Sport-Ecommerce/blob/main/e-commerce-frontend/README.md)
- ⚛️ **React 19** + **Vite**
- 💨 **TailwindCSS v4** + **Ant Design**
- 🧪 **TypeScript**
- 🔁 **React Query**
- 🔐 **JWT-based authentication**
- 📦 **Axios**

### Backend [(view more)](https://github.com/SolStiep/Sport-Ecommerce/blob/main/sport-ecommerce/README.md)
- ☕ **Java 17** + **Spring Boot 3.4.3**
- 🔐 **Spring Security 6 (JWT-based auth)**
- 🗃️ **PostgreSQL** + **Hibernate**
- 🛫 **Flyway (DB migrations)**
- ⚒️ **Gradle**
- 📚 **Swagger API Documentation**

---

## 🚀 Deployment Info

Both the frontend and backend applications are deployed on Render using the free tier.

Please note that since free plans put services to sleep after inactivity, the initial request might take a few seconds to respond. We appreciate your patience!

🔗 URLs
🌐 Frontend (Web App): https://sport-ecommerce-1.onrender.com

🔙 Backend (API): https://sport-ecommerce.onrender.com

📘 Swagger API Docs: https://sport-ecommerce.onrender.com/swagger-ui/index.html

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-user/sport-ecommerce.git
cd sport-ecommerce
```

### 2. Setup and Run Backend

#### Prerequisites
- Java 17
- PostgreSQL running on port `5432`
- Recommended IDE: **IntelliJ IDEA**

#### Configure `application-dev.properties`

```properties
jwt.secret=<secret>
jwt.cookieName=<cookie-name>
jwt.jwtExpirationMs=<exp-time>
spring.datasource.url=<db-url>
spring.datasource.username=<db-user>
spring.datasource.password=<db-password>
```

> Ensure the database exists before starting the backend. Flyway will handle schema migrations.

#### Start the backend
```bash
./gradlew bootRun
```
The backend will be available at: [http://localhost:8080](http://localhost:8080)

### 3. Setup and Run Frontend

#### Install dependencies
```bash
cd frontend
npm install
```

#### Configure environment variables
Create a `.env` file and set the API URL:
```bash
VITE_API_URL=<api-url>
```

#### Start the frontend
```bash
npm run dev
```
The frontend will be available at: [http://localhost:5173](http://localhost:5173)

---

## ✨ Features

- 🛒 **E-Commerce Functionality** (Product browsing, product customization, shopping cart, order placement)
- 🧑‍💼 **Admin Dashboard** (Manage products, preset products, categories, and orders)
- 🔐 **JWT-based Authentication** (Login, Register)
- 📣 **Real-time notifications**
- 📚 **Swagger API Documentation** ([http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html))

---

## 📌 Notes

- Ensure database migrations are properly set up with Flyway.
- Environment-specific configurations should be managed via profiles.
- Avoid committing sensitive configurations to version control.

Happy coding! 🚀

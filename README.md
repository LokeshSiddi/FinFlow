# FinFlow: Finance Dashboard Backend

A robust, monolithic Spring Boot backend API designed to power a financial dashboard system. This application manages user roles, processes financial records, and delivers aggregated analytics with strict Role-Based Access Control (RBAC) and high-performance database-level calculations.

## 🚀 Tech Stack
* **Language/Framework:** Java 21, Spring Boot 3.x
* **Security:** Spring Security 6, JSON Web Tokens (JWT)
* **Database (Default/Evaluation):** H2 In-Memory Database
* **Database (Production):** PostgreSQL (via Spring Profiles)
* **ORM:** Spring Data JPA / Hibernate
* **Testing:** JUnit 5, Mockito
* **Documentation:** Springdoc OpenAPI 3 (Swagger UI)

## ✨ Key Features & Architectural Decisions

* **Role-Based Access Control (RBAC):** Strict JWT-based security. `ADMIN` users have full CRUD access, `ANALYST` users can view records and analytics, and `VIEWER` users are restricted to read-only record access.
* **Mass Assignment Protection:** The public registration endpoint (`/api/auth/register`) strictly forces the `VIEWER` role. Elevated privileges can only be manually granted by an `ADMIN` via protected API routes.
* **Database-Level Aggregation:** Dashboard summaries (total income, net balance, category grouping) are computed directly in the database using custom JPQL projections, avoiding costly in-memory Java loops and improving performance.
* **Global Exception Handling:** A `@RestControllerAdvice` layer intercepts validation errors, missing resources, and bad credentials to return clean, standardized JSON error responses (e.g., `400 Bad Request`, `401 Unauthorized`) instead of raw stack traces.
* **Secure Configuration:** Sensitive data, such as JWT secret keys, are strictly managed via environment variables rather than hardcoded into the source code.

---

## 🛠️ Getting Started (Zero-Setup Evaluation)

To make the evaluation process entirely frictionless, this application defaults to an **H2 In-Memory Database**. No external database installation is required to run and test the core logic.

### Prerequisites
* Java 17 or higher
* Maven 3.6+

### 1. Environment Variables Configuration
For security best practices, the JWT Secret Key is managed via system environment variables. Before booting the application, you must set the following environment variable in your terminal or IDE run configuration:

**Variable Name:** `secret-key`
**Value:** `dGhpcyBpcyBhIHRvcCBzZWNyZXQga2V5IGRvIGxlYWs`
*(Note: If you choose to use your own key, it must be a Base64 string that decodes to at least 256 bits/32 bytes to satisfy the HMAC-SHA algorithm requirements).*

**How to set it in the terminal:**
* **Mac/Linux:** `export secret-key="dGhpcyBpcyBhIHRvcCBzZWNyZXQga2V5IGRvIGxlYWs"`
* **Windows (CMD):** `set secret-key=dGhpcyBpcyBhIHRvcCBzZWNyZXQga2V5IGRvIGxlYWs`
* **Windows (PowerShell):** `$env:secret-key="dGhpcyBpcyBhIHRvcCBzZWNyZXQga2V5IGRvIGxlYWs"`

### 2. Booting the Application
Once the environment variable is set, navigate to the root directory of the project in your terminal and run:
```bash
mvn spring-boot:run
```
The server will start locally on `http://localhost:8080`.

### 3. Auto-Seeded Test Data
To save time during testing, a `CommandLineRunner` automatically seeds the database on startup with test accounts and a set of mock financial records.

**Pre-configured Test Accounts:**
| Role | Email | Password |
| :--- | :--- | :--- |
| **ADMIN** | `admin@zorvyn.io` | `password` |
| **VIEWER** | `viewer@zorvyn.io` | `password` |

---

## 🗄️ Inspecting the H2 Database Console

You can visually inspect the seeded tables and data directly via the browser using the H2 console.

1. Navigate to: **http://localhost:8080/h2-console**
2. Ensure the **JDBC URL** is set exactly to: `jdbc:h2:mem:finflowdb`
3. **User Name**: `sa`
4. **Password**: *(leave completely blank)*
5. Click **Connect** to view the `USERS` and `FINANCIAL_RECORDS` tables.

---

## 📖 API Documentation & Testing

This project integrates OpenAPI 3. You can test all endpoints directly from your browser without needing external tools like Postman.

1. Navigate to the Swagger UI: **http://localhost:8080/swagger-ui/index.html**
2. Use the `POST /api/auth/login` endpoint with the Admin credentials listed above.
3. Copy the `token` string from the JSON response.
4. Click the green **Authorize** button at the top of the Swagger page, paste your token, and click **Apply**.
5. You can now seamlessly execute any of the protected endpoints!

### Core Endpoints Overview
* **Authentication:** `/api/auth/register`, `/api/auth/login`
* **User Management (Admin Only):** `GET /api/users`, `PUT /api/users/{id}/role`
* **Financial Records:** `/api/records` (Supports Pagination & Sorting: e.g., `?page=0&size=10&sort=date,desc`)
* **Analytics (Admin/Analyst):** `GET /api/dashboard/summary`

---

## 🧪 Running the Test Suite

Unit tests have been implemented for the Service layer to demonstrate business logic isolation, exception handling, and database mocking using Mockito.

To execute the test suite, run the following command in the project root:
```bash
mvn test
```

---

## ⚙️ Production Profile (PostgreSQL)

While H2 is used for immediate assessment, the application is fully production-ready. A `prod` profile is included in `application-prod.yml` configured for PostgreSQL.

To run the application with PostgreSQL:
1. Ensure PostgreSQL is running locally on port `5432`.
2. Create a database named `finflowdb`.
3. Update the credentials in `src/main/resources/application-prod.yml` if necessary.
4. Run the application with the `prod` profile active:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
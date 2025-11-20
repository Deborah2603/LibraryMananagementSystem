# 📘 Library Management System

# 📌 Overview

The Library Management System is a Spring Boot-based application that helps manage books, borrowers, and book lending activities in a library. It allows users to add and update books, register borrowers, borrow and return books, track due dates, and calculate fines for late returns. The system also provides reports like top borrowed books and borrower activity. It is built using Spring Boot, JPA, H2 database, Flyway.

## 🛠️ Tools, Libraries, and Frameworks Used

### **Backend Technology**

- Java 17+
- Spring Boot 3.5.7(Web, JPA, Validation)

### **Database**

- H2 Database (In-memory)
- Flyway for database migrations(To avoid manual script excecution)

### **ORM & Utilities**

- Hibernate (UUID support)
- Lombok

### **Build Tool**

- Maven

---

## 🏗️ Architecture Overview
```
controller → service → repository → entity → dto → exception
```

**Layers:**
- **Controller:** Exposes REST APIs
- **Service:** Business logic and transaction handling
- **Repository:** Database CRUD and queries
- **Entity:** JPA models and relationships
- **DTO:** Data transfer objects for clean responses
- **Exception:** Centralized error handling using `@RestControllerAdvice`

---

## 📘 Core Entities

### **Book**
- id (UUID)
- title
- author
- category
- totalCopies
- availableCopies
- isAvailable
- deleted

### **Borrower**
- id (UUID)
- name
- email
- membershipType (BASIC / PREMIUM)
- maxBorrowLimit

### **BorrowRecord**
- id (UUID)
- bookId
- borrowerId
- borrowDate
- dueDate (borrowDate + 14 days)
- returnDate
- fineAmount

---

## ⚙️ Functional APIs

### **Book Management**
| Method | Endpoint | Description |
|---------|-----------|--------------|
| POST | `/api/books` | Add new book or increase total copies |
| GET | `/api/books` | List books with optional filters & pagination |
| PUT | `/api/books/{id}` | Update metadata or available copies |
| DELETE | `/api/books/{id}` | Soft delete if no active borrow |

### **Borrower Management**
| Method | Endpoint | Description |
|---------|-----------|--------------|
| POST | `/api/borrowers` | Register new borrower |
| GET | `/api/borrowers/{id}/records` | Get borrow history |
| GET | `/api/borrowers/overdue` | List overdue borrowers |

### **Borrowing Workflow**
| Method | Endpoint | Description |
|---------|-----------|--------------|
| POST | `/api/borrow` | Borrow a book |
| POST | `/api/return` | Return a borrowed book |
| GET | `/api/records/active` | List currently borrowed books |

---

## ✅ Validation Rules
- Borrower cannot exceed `maxBorrowLimit`.
- Book must have `availableCopies > 0` before borrowing.
- Fine = `(days_late * finePerDay)`.
---

🚀 **How to Set Up and Run the Project**

Follow the steps below to set up and run the Spring Boot application locally:

### **1. Clone the Repository**

```bash
git clone <repo-url>
cd <project-folder>
```

### **2. Build the Project**

Make sure Maven is installed.

```bash
mvn clean install
```

### **3. Run the Application**

```bash
mvn spring-boot:run
```

The app will start on the default port `8080`.

### **4. Access H2 Database Console**

Go to:

```
http://localhost:8080/h2-console
```

Use the JDBC URL from your application properties.

---

## 🧠 Overall Approach & Thought Process

1. **Layered Architecture** was used to keep the code clean:

   - **Controller Layer** → Handles HTTP requests.
   - **Service Layer** → Business logic.
   - **Repository Layer** → DB operations.
   - **Models & DTOs** → Data transfer separation.

2. **Flyway Migration** ensures the DB schema is version-controlled and initialized automatically when the application starts.

3. **UUID Identifiers** were used instead of numeric IDs to:

   - Prevent ID prediction.
   - Improve security.
   - Make entities database-agnostic.

4. **Validation API** ensures request bodies are validated before processing.

5. **DTOs** were used to avoid exposing internal entity structure and to simplify API responses.

6. **Exception Handling** was implemented globally for clean and consistent error responses.

---

## 🧩 Challenges Faced & Solutions

### **1. Mapping Entities with DTOs**

**Challenge:** Builder or mapping errors when converting between entity and DTO.

**Solution:**

- Used explicit private mapping methods in service layer.
- Ensured all fields required for the builder were present.

### **2. Flyway Not Running on H2**

**Cause:** Incorrect folder structure or wrong migration table.

**Fix:**

- Created migration files under `src/main/resources/db/migration`.
- Ensured file format matches: `V1__init.sql`.

### **3. H2 UUID Storage Issues**

**Fix:** Enabled Hibernate UUID generator in entity:

```java
@Id
@GeneratedValue
private UUID id;
```

## 👏 Author
**Deborah Cathearine**
**Github:


Vendor Management System
Overview

The Vendor Management System is a Spring Boot–based RESTful application designed to manage vendors, their associated categories, and payout transactions. The system follows a layered architecture and provides robust validation, exception handling, and scalable data access using Spring Data JPA.

Features
Vendor lifecycle management (create, update, retrieve, delete)
Category management with uniqueness constraints
Many-to-many mapping between vendors and categories
Vendor payout processing and tracking
Pagination and search capabilities
Input validation using Jakarta Validation
Centralized exception handling
API documentation using OpenAPI (Swagger)
Secure password storage using BCrypt

Technology Stack
Java 17
Spring Boot 3.x
Spring Data JPA (Hibernate)
MySQL
Spring Validation (Jakarta Validation API)
Spring Security (Password Encoding)
OpenAPI / Swagger (springdoc)
Maven

Project Structure
com.example.vendormanagement

```

├── controller        # REST controllers (API layer)
├── service           # Service interfaces
├── service.impl      # Business logic implementations
├── repository        # Data access layer (Spring Data JPA)
├── entity            # JPA entities (database models)
├── dto               # Request and response objects
├── exception         # Custom exceptions and global handler
├── config            # Application configuration

```

---
Architecture

The application follows a standard layered architecture:

Controller Layer: Handles HTTP requests and responses
Service Layer: Contains business logic
Repository Layer: Handles database interactions
Entity Layer: Maps Java objects to database tables
DTO Layer: Transfers data between layers
Database Design
Entities
Vendor
VendorCategory
VendorCategoryMapping
VendorPayout
Relationships
Vendor to Category: Many-to-Many (via mapping table)
Vendor to Payout: One-to-Many
Configuration

Update the application.yml file with your database credentials:

spring:
datasource:
url: jdbc:mysql://localhost:3306/vendor_db
username: root
password: your_password

jpa:
hibernate:
ddl-auto: validate
Running the Application
Prerequisites
Java 17 installed
postgreSQL running
Maven installed

Steps
Clone the repository:
git clone https://github.com/ShopHub-Ecommerce/Vendor-Mgmt.git
cd vendor-management
Build the project:
mvn clean install
Run the application:
mvn spring-boot:run
API Base URL
http://localhost:8080/api/v1

API Endpoints
Vendor APIs
Method	Endpoint	Description
POST	/vendors	Create a vendor
GET	/vendors/{id}	Retrieve vendor by ID
GET	/vendors	Retrieve all vendors (paginated)
GET	/vendors/status/{status}	Filter vendors by status
GET	/vendors/search?query=	Search vendors
PUT	/vendors/{id}	Update vendor
DELETE	/vendors/{id}	Delete vendor

Category APIs
Method	Endpoint	Description
POST	/categories	Create category
GET	/categories/{id}	Retrieve category
GET	/categories	Retrieve all categories
PUT	/categories/{id}	Update category
DELETE	/categories/{id}	Delete category

Payout APIs
Method	Endpoint	Description
POST	/payouts	Create payout
GET	/payouts/{id}	Retrieve payout
GET	/payouts	Retrieve all payouts
GET	/payouts/vendor/{vendorId}	Get payouts by vendor
GET	/payouts/vendor/{vendorId}/total	Get total paid amount
PATCH	/payouts/{id}/status	Update payout status
DELETE	/payouts/{id}	Delete payout

Validation

The application enforces input validation using Jakarta Validation:

Email format validation
Password length constraints
Phone number pattern validation
Numeric constraints for payout amounts
Exception Handling

A global exception handler manages:

Resource not found exceptions (404)
Duplicate resource conflicts (409)
Validation errors (400)
Internal server errors (500)



Security
Passwords are hashed using BCrypt
Configured via Spring Security PasswordEncoder
Future Enhancements
JWT-based authentication and authorization
Role-based access control
Audit logging
Unit and integration testing
Docker containerization


Author

Perumal S


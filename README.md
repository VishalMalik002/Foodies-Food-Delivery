# 🍔 Foodies - Food Delivery Web Application

Foodies is a full-stack food delivery web application built using Java Spring Boot, Spring Security, JWT authentication, JPA/Hibernate, MySQL, HTML, CSS and JavaScript.

The application provides restaurant browsing, menu management, cart and checkout, coupon discounts, order management, payment handling, delivery tracking, customer reviews and role-based administration.

## ✨ Features

- 🔐 JWT-based authentication
- 👤 Role-based access control
- 🍽️ Restaurant browsing and management
- 📋 Menu item management
- 🛒 Shopping cart and checkout
- 🎟️ Coupon and discount system
- 📦 Order placement and status management
- 💳 Payment and Cash on Delivery support
- 🚚 Delivery partner management
- 📍 Delivery tracking
- ⭐ Restaurant reviews and ratings
- 👨‍💼 Admin dashboard
- 👥 User management
- 📊 Order management
- 🎁 Offers and coupons

## 👥 User Roles

### Customer

- Browse restaurants
- View menus
- Add items to cart
- Apply coupons
- Place orders
- Make payments
- Track orders
- Review restaurants
- Manage profile

### Admin

- Manage users
- Manage restaurants
- Manage menu items
- Manage orders
- Manage delivery partners
- Manage coupons and offers

### Delivery Partner

- View assigned orders
- Update delivery status
- Update delivery location
- Manage delivery availability

## 🛠️ Tech Stack

### Frontend

- HTML5
- CSS3
- JavaScript

### Backend

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- REST APIs

### Database

- MySQL

### ORM

- JPA
- Hibernate

### Build Tool

- Maven

## 🔐 Security

The application uses Spring Security with JWT-based authentication and role-based authorization.

Different application features and APIs are protected according to the user's role:

- CUSTOMER
- ADMIN
- DELIVERY_PARTNER

Passwords are stored using BCrypt password hashing.

## 🏗️ Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.foodies.fooddelivery
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       ├── service
│   │       └── serviceImpl
│   │
│   └── resources
│       ├── static
│       │   ├── css
│       │   ├── js
│       │   └── *.html
│       └── application.properties
│
└── test
```

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/VishalMalik002/Foodies-Food-Delivery.git
```

### 2. Open the Project

Open the project in IntelliJ IDEA, Eclipse, Spring Tool Suite or VS Code.

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE foodies_db;
```

Update your MySQL username and password in:

```text
src/main/resources/application.properties
```

Make sure MySQL is running before starting the application.

### 4. Run the Application

For Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Open the Application

```text
http://localhost:8080
```

## 📡 Main API Modules

The application provides REST APIs for:

- Authentication
- Users
- Restaurants
- Menu Items
- Cart
- Orders
- Payments
- Coupons
- Reviews
- Delivery Partners

## 🔄 Order Flow

```text
PLACED
   ↓
CONFIRMED
   ↓
PREPARING
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

## 🚀 Future Enhancements

- Online payment gateway integration
- Real-time delivery tracking
- Email and SMS notifications
- Advanced restaurant search and filtering
- Redis caching
- Docker deployment
- Cloud deployment
- Automated testing
- Swagger/OpenAPI documentation

## 👨‍💻 Developer

**Vishal Malik**

B.Tech – Computer Science & Engineering (Data Science)

GitHub:

https://github.com/VishalMalik002

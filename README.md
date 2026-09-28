# 🍔 Foodies - Food Delivery Web Application

Foodies is a full-stack food delivery web application built using Java Spring Boot, Spring Security, JWT authentication, JPA/Hibernate, MySQL, HTML, CSS and JavaScript.

The application provides restaurant browsing, menu management, cart and checkout, coupon discounts, order management, payment handling (including Cash on Delivery), delivery tracking, customer reviews and role-based administration.

## 🌐 Live Demo

**https://foodies-food-delivery-production.up.railway.app/**

The application is deployed on [Railway](https://railway.app).

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

## 👥 User Roles

### Customer
- Browse restaurants and view menus
- Add items to cart and apply coupons
- Place orders and make payments
- Track orders and review restaurants
- Manage profile

### Admin
- Manage users, restaurants and menu items
- Manage orders and delivery partners
- Manage coupons and offers

### Delivery Partner
- View assigned orders
- Update delivery status and location
- Manage delivery availability

## 🛠️ Tech Stack

| Layer | Technologies |
|---|---|
| Frontend | HTML5, CSS3, JavaScript |
| Backend | Java, Spring Boot, Spring Security, JWT, REST APIs |
| Database | MySQL |
| ORM | JPA, Hibernate |
| Build Tool | Maven |
| Deployment | Railway |

## 🔐 Security

The application uses Spring Security with JWT-based authentication and role-based authorization. APIs are protected according to the user's role:

- `CUSTOMER`
- `ADMIN`
- `DELIVERY_PARTNER`

Passwords are stored using BCrypt hashing.

## 🏗️ Project Structure

```
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
│   └── resources
│       ├── static
│       │   ├── css
│       │   ├── js
│       │   └── *.html
│       └── application.properties
└── test
```

## ⚙️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/VishalMalik002/Foodies-Food-Delivery.git
```

### 2. Configure MySQL

```sql
CREATE DATABASE foodies_db;
```

Update your MySQL username and password in `src/main/resources/application.properties`, and make sure MySQL is running.

### 3. Run the application

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

### 4. Open the application

```
http://localhost:8080
```

## 📡 Main API Modules

Authentication, Users, Restaurants, Menu Items, Cart, Orders, Payments, Coupons, Reviews, Delivery Partners.

## 🔄 Order Flow

```
PLACED → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED
```

## 🚀 Future Enhancements

- Online payment gateway integration
- Real-time delivery tracking (WebSockets/maps)
- Email and SMS notifications
- Advanced restaurant search and filtering
- Redis caching
- Docker containerization and CI/CD
- Automated testing
- Swagger/OpenAPI documentation

## 👨‍💻 Developer

**Vishal Malik**
B.Tech – Computer Science & Engineering (Data Science)
GitHub: [VishalMalik002](https://github.com/VishalMalik002)

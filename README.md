<h1> LabourLink</h1>

LabourLink is a web-based platform designed to connect **workers and contractors** in one place. It provides features for user registration, job management, applications, worker profiles, work history, favorites, notifications, ratings, endorsements, and file uploads.

The project is built using **Spring Boot, Java, MySQL, HTML, CSS, and JavaScript**.

---

## 🚀 Features

### 👤 User Management
- Worker and Contractor registration
- User login
- Role-based user experience
- Worker profiles
- User information management

### 💼 Job Management
- Contractors can create and manage jobs
- Workers can browse available jobs
- Workers can apply for jobs
- Job application status management

### ⭐ Worker Profiles
- Worker profile management
- Skills
- Work history
- Ratings
- Endorsements

### ❤️ Favorites
- Save/favorite workers
- Manage favorite workers

### 🔔 Notifications
- Job-related notifications
- Application-related notifications
- Notification management

### 📁 File Uploads
- File upload functionality
- Uploaded files are managed through the backend

### 🌐 Multilingual Support
The project includes localization resources for:

- 🇬🇧 English
- 🇮🇳 Hindi
- 🇮🇳 Marathi

### 🎨 Responsive Web Interface
The application includes separate interfaces/pages for:

- Landing page
- Login
- Signup
- Worker Dashboard
- Worker Profile
- Contractor Dashboard

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs
- Maven

### Frontend
- HTML
- CSS
- JavaScript

### Database
- MySQL

---

## 📂 Project Structure

```text
LabourLink/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── labourlink/
│       │           ├── config/
│       │           ├── controller/
│       │           ├── dto/
│       │           ├── model/
│       │           ├── repository/
│       │           └── service/
│       │
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   ├── images/
│           │   ├── js/
│           │   ├── locales/
│           │   ├── index.html
│           │   ├── login.html
│           │   ├── signup.html
│           │   ├── worker-dashboard.html
│           │   ├── worker-profile.html
│           │   └── contractor-dashboard.html
│           │
│           └── application.properties
│
├── pom.xml
└── mvnw.cmd



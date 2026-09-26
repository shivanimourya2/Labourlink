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

### 2. Create the Database

Open MySQL and create the LabourLink database:

```sql
CREATE DATABASE labourlink;
```

### 3. Configure Database Credentials

Open:

```text
src/main/resources/application.properties
```

Make sure the database configuration uses:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/labourlink?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

Set your MySQL password as an environment variable.

**Windows PowerShell:**

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

> Replace `your_mysql_password` with your own MySQL password.
>
> **Never commit your actual database password to GitHub.**

### 4. Run the Application

From the project root directory, run:

```bash
mvn spring-boot:run
```

Once the application starts, open:

```text
http://localhost:8080
```

---

## 🤝 How to Contribute

Contributions, bug fixes, and new ideas are welcome!

### 1. Fork the Repository

Fork the **LabourLink** repository to your GitHub account.

### 2. Clone Your Fork

```bash
git clone https://github.com/YOUR-USERNAME/Labourlink.git
cd Labourlink
```

### 3. Create a New Branch

Create a separate branch for your changes:

```bash
git checkout -b feature/your-feature-name
```

Example:

```bash
git checkout -b feature/job-search
```

### 4. Make Your Changes

Implement your feature, fix a bug, or improve the project.

Check your changes:

```bash
git status
```

### 5. Commit Your Changes

```bash
git add .
git commit -m "Add job search feature"
```

### 6. Push Your Changes

```bash
git push origin feature/job-search
```

### 7. Create a Pull Request

Go to your GitHub fork and create a **Pull Request** to the `main` branch of the LabourLink repository.

In your Pull Request, briefly describe:

- What you changed
- Why you made the change
- Any new features or improvements
- Any relevant issues or limitations

Please test your changes before submitting the Pull Request.
---

## 👩‍💻 Author

**Shivani Mourya**

---

⭐ If you found this project interesting, feel free to star the repository & contribute to this project


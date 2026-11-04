# SkillNavi – Student Skill Gap Analyzer and Career Readiness System

**SkillNavi** is a Java-based desktop application designed to help students evaluate their technical skills, identify skill gaps and assess their readiness for specific career roles. It compares a student's current proficiency with predefined industry-oriented job-role requirements and provides a structured learning roadmap to improve career readiness.

## 1. Project Overview

Students often find it difficult to understand which technical skills are required for their desired career and how their current abilities compare with those requirements.

SkillNavi addresses this problem through a centralized platform that enables students to:
- Create an account and log in using email and password.
- Maintain their student profile.
- Add and update technical skills.
- Assess their proficiency levels.
- Select a target job role.
- Identify missing and insufficient skills.
- Calculate career readiness.
- Generate a priority-based learning roadmap.
- Access learning resources.
- Track learning progress.

## 2. Objectives

- Develop a user-friendly student skill assessment system.
- Compare existing skills against predefined job-role requirements.
- Identify technical skill gaps.
- Calculate career readiness using proficiency-based comparisons.
- Recommend a structured learning roadmap.
- Provide a platform for continuous skill development and progress tracking.

## 3. Key Features

### User Authentication
- Email and password-based registration and login.
- Account-based access to student profiles.
- Credential verification during login.

### Student Profile Management
- Create and maintain student profiles.
- Retrieve existing student information.

### Skill Assessment
- Add, update and manage technical skills.
- Assign proficiency levels:
  - Beginner
  - Intermediate
  - Advanced

### Job Role Selection
- Select a desired career role.
- Retrieve the technical skills required for the selected role.

### Skill Gap Analysis
Compares student proficiency with job-role requirements and classifies skills into:

| Status | Description |
|---|---|
| MATCHED | Student proficiency meets or exceeds the requirement. |
| NEEDS_IMPROVEMENT | Student has the skill but does not meet the required proficiency. |
| MISSING | Student has not added the required skill. |

### Career Readiness Calculation

SkillNavi calculates career readiness based on the number of matched skills compared with the total required skills.

**Formula:**

Readiness Score = (Matched Required Skills / Total Required Skills) × 100

### Learning Roadmap
- Identifies skills requiring improvement.
- Prioritizes learning activities.
- Generates a structured roadmap based on identified gaps.

### Learning Resources
- Provides learning resources associated with technical skills.

### Progress Tracking
- Enables students to monitor their learning progress.

## 4. Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| Frontend | Java Swing |
| Application Logic | Java |
| Database | MySQL |
| Database Connectivity | JDBC |
| Build Tool | Apache Maven |
| Testing | JUnit 5 |
| Version Control | Git and GitHub |
| Development Environment | Visual Studio Code / IntelliJ IDEA |

## 5. System Architecture

SkillNavi follows a layered architecture that separates the user interface, business logic and database operations.

**1. Presentation Layer**
- Developed using Java Swing.
- Provides forms, buttons, tables, dashboards and navigation.

**2. Service Layer**
- Handles application business logic.
- Performs skill-gap analysis.
- Calculates career readiness.
- Generates learning roadmaps.

**3. DAO (Data Access Object) Layer**
- Handles database operations.
- Uses JDBC and SQL queries.
- Separates database access from application logic.

**4. Database Layer**
- Uses MySQL to store student accounts, profiles, skills, job roles, learning resources and progress information.

### Architecture Flow

Student → Java Swing UI → Service Layer → DAO Layer → JDBC → MySQL Database

## 6. Object-Oriented Programming Concepts

SkillNavi applies the four fundamental OOP concepts.

### Encapsulation
- Data members are declared private in model classes.
- Getter and setter methods provide controlled access to object data.
- Examples: Student, Skill, JobRole and other model classes.

### Abstraction
- Implemented through the DAO layer.
- Database operations and SQL implementation details are hidden behind reusable methods.
- Examples: StudentDAO, SkillDAO and StudentSkillDAO.

### Inheritance
- Implemented by extending Java Swing classes.
- MainFrame extends JFrame.
- Individual screen panels extend JPanel.

### Polymorphism
- Implemented through method overriding in custom Swing components and table models.
- Custom table models provide their own implementations of inherited methods such as getRowCount() and getValueAt().

## 7. Skill Gap Analysis Algorithm

1. Retrieve the student's selected job role.
2. Retrieve the skills required for that role.
3. Retrieve the student's existing skills and proficiency levels.
4. Compare the student's proficiency against the required proficiency.
5. Classify each skill as MATCHED, NEEDS_IMPROVEMENT or MISSING.
6. Calculate the readiness percentage.
7. Generate a priority-based learning roadmap.

### Proficiency Mapping

| Proficiency | Numeric Value |
|---|---:|
| Beginner | 1 |
| Intermediate | 2 |
| Advanced | 3 |

## 8. Database Design

SkillNavi uses MySQL for persistent data storage.

The database manages information related to:

- Student accounts and profiles
- Technical skills
- Student proficiency levels
- Job roles
- Job-role skill requirements
- Selected target roles
- Learning resources
- Learning progress

The application uses JDBC to communicate with the database.

## 9. Project Structure

```text
SkillNavi/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/skillnavi/
│   │   │       ├── App.java
│   │   │       ├── model/
│   │   │       ├── model/enums/
│   │   │       ├── dao/
│   │   │       ├── service/
│   │   │       ├── ui/
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       └── java/
│
├── database/
│   └── schema.sql
│
├── pom.xml
└── README.md
```

## 10. Installation and Execution

### Prerequisites
- Java Development Kit (JDK 17 or later)
- Apache Maven
- MySQL Server 8.0 or later
- Git

### Step 1: Clone the repository

```bash
git clone https://github.com/kirthanas262007-hub/SkillNavi.git
```

### Step 2: Navigate to the project

```bash
cd SkillNavi
```

### Step 3: Configure the database

- Start the MySQL server.
- Create the required database.
- Execute the SQL schema script.
- Configure the database connection properties using your local credentials.

### Step 4: Build the project

```bash
mvn clean compile
```

### Step 5: Run the application

```bash
mvn exec:java "-Dexec.mainClass=com.skillnavi.App"
```

## 11. Future Enhancements

- Introduce AI-assisted personalized career recommendations.
- Expand the collection of job roles and industry skill requirements.
- Integrate external learning platforms.
- Develop a web-based version for broader accessibility.
- Add advanced progress analytics.
- Improve authentication and account security.

## 12. Project Information

**Project Name:** SkillNavi – Student Skill Gap Analyzer and Career Readiness System

**Domain:** Java Application Development / Career Readiness

**Programming Language:** Java

**Database:** MySQL

**Repository:** https://github.com/kirthanas262007-hub/SkillNavi

---

*SkillNavi aims to bridge the gap between students' current technical abilities and their desired career requirements through structured skill assessment, analysis and continuous learning.*
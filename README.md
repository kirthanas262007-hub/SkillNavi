# SkillNavi – Student Skill Gap Analyzer and Career Readiness System

SkillNavi is a Java-based desktop application designed to help students evaluate their technical skills, identify skill gaps, and understand their readiness for selected career roles.

The system allows students to create accounts, log in using their email and password, assess their proficiency in different technical skills, and compare their current abilities with the skills required for their target job roles.

Based on this analysis, SkillNavi calculates a career-readiness score and generates a personalized learning roadmap to help students improve their skills.

---

## 🎯 Objectives

- Provide a structured platform for students to assess their technical skills.
- Identify the gap between existing skills and industry-required skills.
- Help students select suitable target job roles.
- Calculate career-readiness scores based on skill matching.
- Generate learning roadmaps to improve technical proficiency.
- Track learning progress and access relevant learning resources.

---

## ✨ Key Features

### 1. User Registration and Login
- New users can create an account.
- Existing users can log in using their email address and password.
- User profiles are maintained in the database.

### 2. Student Profile Management
- Maintain student information.
- Manage individual student profiles.

### 3. Technical Skill Assessment
- Add and update technical skills.
- Select proficiency levels:
  - Beginner
  - Intermediate
  - Advanced

### 4. Target Job Role Selection
- Browse available job roles.
- Select a target career role.
- Identify the technical skills required for the selected role.

### 5. Skill Gap Analysis
- Compare student skills with job-role requirements.
- Classify skills as:
  - Matched
  - Needs Improvement
  - Missing

### 6. Career Readiness Score
- Calculate a readiness percentage based on matched required skills.
- Display the student's current career readiness.

### 7. Personalized Learning Roadmap
- Generate a roadmap based on identified skill gaps.
- Prioritize skills that need improvement.

### 8. Learning Resources
- Provide relevant learning resources to support skill development.

### 9. Progress Tracking
- Monitor learning progress.
- Maintain records of progress over time.

---

## 🛠️ Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| Frontend | Java Swing |
| Backend Logic | Java |
| Database | MySQL |
| Database Connectivity | JDBC |
| Build Tool | Apache Maven |
| Testing | JUnit 5 |
| IDE | Visual Studio Code |
| Version Control | Git and GitHub |

---

## 🏗️ System Architecture

SkillNavi follows a layered architecture that separates the user interface, business logic, database operations, and data storage.

```text
              USER
                |
                v
       PRESENTATION LAYER
           Java Swing
                |
                v
         SERVICE LAYER
       Business Logic
                |
                v
           DAO LAYER
       Database Operations
                |
                v
          JDBC CONNECTIVITY
                |
                v
          MySQL DATABASE
```

### Layers Explained

**1. Presentation Layer**
- Developed using Java Swing.
- Provides forms, buttons, tables, dashboards, and navigation.
- Accepts user input and displays results.

**2. Service Layer**
- Contains the application's main business logic.
- Performs skill-gap analysis.
- Calculates career-readiness scores.
- Generates learning roadmaps.

**3. Data Access Object (DAO) Layer**
- Handles database operations.
- Uses JDBC to execute SQL queries.
- Separates database logic from the user interface and business logic.

**4. Database Layer**
- MySQL stores student details, skills, job roles, learning resources, and progress information.

---

## 🧠 Skill Gap Analysis Algorithm

SkillNavi compares the student's proficiency with the proficiency required for a selected job role.

### Proficiency Mapping

| Proficiency Level | Numeric Value |
|---|---:|
| Beginner | 1 |
| Intermediate | 2 |
| Advanced | 3 |

### Skill Classification

- **Matched:** Student proficiency meets or exceeds the required proficiency.
- **Needs Improvement:** Student has the skill, but the proficiency is below the required level.
- **Missing:** Student has not added the required skill.

### Career Readiness Formula

\[
\text{Readiness Score} =
\frac{\text{Matched Required Skills}}
{\text{Total Required Skills}}\times100
\]

**Example:**

If a job role requires five skills and a student matches three of them:

\[
\frac{3}{5}\times100=60\%
\]

Therefore, the student's career-readiness score is **60%**.

The system uses rule-based logic to perform the analysis.

---

## 💻 Object-Oriented Programming Concepts

SkillNavi applies the fundamental concepts of Object-Oriented Programming.

| OOP Concept | Implementation |
|---|---|
| Encapsulation | Model classes maintain data through fields and methods. |
| Abstraction | DAO classes hide database implementation details. |
| Inheritance | Swing classes extend framework classes such as JFrame and JPanel. |
| Polymorphism | Method overriding is used in custom table models and UI components. |

These concepts help improve code organization, reusability, and maintainability.

---

## 🗄️ Database

The application uses MySQL for persistent data storage.

The database is named:

`student_skill_gap`

Core database tables include:

- `students`
- `skills`
- `student_skills`
- `job_role`
- `student_target_role`

Additional tables support learning resources and progress tracking where configured in the project.

---

## 📂 Project Structure

The project follows a Maven-based Java application structure.

```text
SkillNavi/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/skillnavi/
│   │   │       ├── App.java
│   │   │       ├── dao/
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       ├── ui/
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## ⚙️ Installation and Execution

### Prerequisites

Make sure the following are installed:

- Java Development Kit (JDK)
- Apache Maven
- MySQL Server
- Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/kirthanas262007-hub/SkillNavi.git
```

### Step 2: Navigate to the Project

```bash
cd SkillNavi
```

### Step 3: Configure MySQL

1. Start the MySQL server.
2. Create the `student_skill_gap` database.
3. Configure the database connection details in the project's database configuration file.
4. Ensure that the required database tables are created.

### Step 4: Build the Project

```bash
mvn clean compile
```

### Step 5: Run the Application

For Windows PowerShell:

```powershell
mvn exec:java "-Dexec.mainClass=com.skillnavi.App"
```

---

## 🚀 Future Enhancements

- Integration of AI-based career recommendations.
- Resume analysis and skill extraction.
- Integration with external job-market APIs.
- Advanced analytics and visual dashboards.
- Personalized recommendations based on industry trends.
- Web-based and mobile application versions.

---

## 👩‍💻 Project Information

**Project Name:** SkillNavi – Student Skill Gap Analyzer and Career Readiness System

**Domain:** Java Application Development

**Programming Language:** Java

**Database:** MySQL

**Project Type:** Desktop Application

**Developed as part of:** Object Oriented Programming Using Java

**Institution:** Easwari Engineering College, Ramapuram, Chennai

---

## 🔗 GitHub Repository

[https://github.com/kirthanas262007-hub/SkillNavi](https://github.com/kirthanas262007-hub/SkillNavi)

---

## 📌 Conclusion

SkillNavi provides students with a structured approach to understanding their technical capabilities and preparing for career opportunities. By combining skill assessment, job-role comparison, readiness calculation, and personalized learning roadmaps, the application helps students identify areas for improvement and work towards becoming career-ready.
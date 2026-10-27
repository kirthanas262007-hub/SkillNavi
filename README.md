@'
# SkillNavi

SkillNavi is a Java Swing desktop application for students to compare their current technical skills with the predefined requirements of a target job role.

The result is a **skill requirement coverage score**, not a prediction of getting a job. The project uses deterministic Java comparison logic only; it does not use AI or ML.

Course project: 2311CSC304R – Object Oriented Programming Using Java, Easwari Engineering College (CSE).
Authors: Kirthana S (310625104174) and Kaavya S (310625104151).

## Features

- Register and log in with a password (stored only as a salted PBKDF2 hash)
- Maintain a student profile
- Add current skills and proficiency levels
- Select a target job role
- Compare current skills with role requirements from MySQL
- Classify every required skill as `MATCHED`, `NEEDS_IMPROVEMENT`, or `MISSING`
- Calculate career readiness as required-skill coverage
- Dashboard with readiness score, skill counts and a current-vs-required skill profile
- Generate a priority-based learning roadmap
- Display database-backed learning resources
- Track progress for saved student skills

## Screens

Welcome / Login, Dashboard, Profile, Manage Skills, Target Role, Skill Analysis, Roadmap, Resources, Progress.

## Technology

- Java 17+
- Java Swing
- MySQL 8+
- JDBC with `PreparedStatement`
- Maven
- JUnit 5
- PBKDF2-HMAC-SHA256 (`javax.crypto`) for password hashing

## Setup

1. Install a JDK 17 or later, Maven, and MySQL 8 or later.
2. Create the database and insert the demonstration data by running [schema.sql](database/schema.sql).
3. Copy `src/main/resources/database.properties.example` to `src/main/resources/database.properties`.
4. Update the database username and password in `database.properties`.
5. From the `SkillNavi` directory, run:

   ```powershell
   mvn clean test
   mvn package
   mvn exec:java -Dexec.mainClass=com.skillnavi.App
   ```

The `database.properties` file is intentionally ignored by Git so credentials are not committed.

## Sample job roles

The schema seeds sample job roles such as Java Developer, Data Analyst and Web Developer. Each role has its own required skills, proficiency levels, and priorities. You can extend these sample records directly in MySQL for a demonstration.

## Proficiency and gap logic

| Proficiency | Internal value |
|---|---:|
| BEGINNER | 1 |
| INTERMEDIATE | 2 |
| ADVANCED | 3 |

For every required job skill:

- No student skill record → `MISSING`
- Student level below required level → `NEEDS_IMPROVEMENT`
- Student level meets or exceeds required level → `MATCHED`

```text
Readiness Score = (matched required skills / total required skills) × 100
```

For example, 3 matched skills out of 5 required skills gives `60%` coverage.

## Design overview

- `model` contains the application entities and calculated result objects.
- `model.enums` provides type-safe proficiency, status, and priority values.
- `dao` contains JDBC database access only.
- `service` performs skill comparison, score calculation, roadmap generation, and authentication.
- `ui` contains Java Swing panels and table models.
- `util` contains database configuration, validation, session state, and exceptions.

The `STUDENT_TARGET_ROLE` table is a small addition to the requested schema. It stores each student's currently selected role so it remains available after restarting the app.

## OOP concepts used

- **Encapsulation:** private fields with controlled constructors, getters, and setters.
- **Abstraction:** DAOs hide JDBC details from the rest of the application.
- **Composition:** `StudentSkill`, `JobSkill`, `SkillGap`, and `RoadmapItem` combine related objects.
- **Polymorphism:** Swing panels and table models extend standard Swing abstractions.
- **Collections:** lists, maps, streams, sorting, and immutable views are used in the service layer.
- **Exception handling:** invalid input and SQL failures produce meaningful application messages.

## Authentication

Passwords are never stored in plain text. Each password is hashed with PBKDF2-HMAC-SHA256 (random 16-byte salt, 600,000 iterations), and the iteration count, salt and hash are stored together in the `password_hash` column. Login failures show one generic message. This protects data inside the desktop application but is not a production identity service: password reset, account lock-out and two-factor authentication are not implemented.

## Testing

`mvn test` runs the service-layer unit tests: `ReadinessCalculatorTest`, `RoadmapGeneratorTest` and `SkillGapAnalyzerTest`. The screens were checked manually against the MySQL database.
'@ | Set-Content -Path README.md -Encoding utf8
CREATE DATABASE IF NOT EXISTS skillnavi_db;
USE skillnavi_db;

CREATE TABLE IF NOT EXISTS STUDENT (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    education VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS SKILL (
    skill_id INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS JOB_ROLE (
    job_id INT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS STUDENT_SKILL (
    student_id INT NOT NULL,
    skill_id INT NOT NULL,
    proficiency_level VARCHAR(30) NOT NULL,
    PRIMARY KEY (student_id, skill_id),
    CONSTRAINT fk_student_skill_student FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_student_skill_skill FOREIGN KEY (skill_id)
        REFERENCES SKILL(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS JOB_SKILL (
    job_id INT NOT NULL,
    skill_id INT NOT NULL,
    required_level VARCHAR(30) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    PRIMARY KEY (job_id, skill_id),
    CONSTRAINT fk_job_skill_job FOREIGN KEY (job_id)
        REFERENCES JOB_ROLE(job_id) ON DELETE CASCADE,
    CONSTRAINT fk_job_skill_skill FOREIGN KEY (skill_id)
        REFERENCES SKILL(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS LEARNING_RESOURCE (
    resource_id INT PRIMARY KEY AUTO_INCREMENT,
    skill_id INT NOT NULL,
    resource_name VARCHAR(150) NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    resource_link VARCHAR(500) NOT NULL,
    UNIQUE KEY uk_resource_skill_name (skill_id, resource_name),
    CONSTRAINT fk_resource_skill FOREIGN KEY (skill_id)
        REFERENCES SKILL(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PROGRESS (
    student_id INT NOT NULL,
    skill_id INT NOT NULL,
    progress_percentage INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    PRIMARY KEY (student_id, skill_id),
    CONSTRAINT fk_progress_student FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_progress_skill FOREIGN KEY (skill_id)
        REFERENCES SKILL(skill_id) ON DELETE CASCADE,
    CONSTRAINT chk_progress_percentage CHECK (progress_percentage BETWEEN 0 AND 100)
);

-- Stores one current target role per student so the selection remains after restarting the application.
CREATE TABLE IF NOT EXISTS STUDENT_TARGET_ROLE (
    student_id INT PRIMARY KEY,
    job_id INT NOT NULL,
    selected_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_target_role_student FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_target_role_job FOREIGN KEY (job_id)
        REFERENCES JOB_ROLE(job_id) ON DELETE CASCADE
);

INSERT IGNORE INTO SKILL (skill_name, category) VALUES
    ('Java', 'Programming'),
    ('SQL', 'Database'),
    ('Git', 'Tools'),
    ('Spring Boot', 'Backend Development'),
    ('REST API', 'Backend Development'),
    ('Python', 'Programming'),
    ('Excel', 'Data Analysis'),
    ('Statistics', 'Data Analysis'),
    ('Power BI', 'Data Visualization'),
    ('HTML', 'Web Development'),
    ('CSS', 'Web Development'),
    ('JavaScript', 'Web Development');

INSERT IGNORE INTO JOB_ROLE (job_title, description) VALUES
    ('Java Developer', 'Builds and maintains Java-based backend applications.'),
    ('Data Analyst', 'Analyzes data to produce useful reports and insights.'),
    ('Web Developer', 'Builds responsive web applications and websites.');

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'ADVANCED', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'Java'
WHERE jr.job_title = 'Java Developer';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'SQL'
WHERE jr.job_title IN ('Java Developer', 'Data Analyst');

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'MEDIUM'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'Git'
WHERE jr.job_title = 'Java Developer';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'Spring Boot'
WHERE jr.job_title = 'Java Developer';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'REST API'
WHERE jr.job_title = 'Java Developer';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name IN ('Python', 'Statistics')
WHERE jr.job_title = 'Data Analyst';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'MEDIUM'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'Excel'
WHERE jr.job_title = 'Data Analyst';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'BEGINNER', 'MEDIUM'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name = 'Power BI'
WHERE jr.job_title = 'Data Analyst';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'INTERMEDIATE', 'HIGH'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name IN ('HTML', 'CSS', 'JavaScript')
WHERE jr.job_title = 'Web Developer';

INSERT IGNORE INTO JOB_SKILL (job_id, skill_id, required_level, priority)
SELECT jr.job_id, s.skill_id, 'BEGINNER', 'MEDIUM'
FROM JOB_ROLE jr JOIN SKILL s ON s.skill_name IN ('Git', 'SQL')
WHERE jr.job_title = 'Web Developer';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Dev.java Learn Java', 'Website', 'https://dev.java/learn/'
FROM SKILL WHERE skill_name = 'Java';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'MySQL Reference Manual', 'Documentation', 'https://dev.mysql.com/doc/'
FROM SKILL WHERE skill_name = 'SQL';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Pro Git Book', 'Book', 'https://git-scm.com/book/en/v2'
FROM SKILL WHERE skill_name = 'Git';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Spring Boot Guides', 'Documentation', 'https://spring.io/guides'
FROM SKILL WHERE skill_name = 'Spring Boot';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'MDN HTTP Overview', 'Documentation', 'https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview'
FROM SKILL WHERE skill_name = 'REST API';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Python Tutorial', 'Documentation', 'https://docs.python.org/3/tutorial/'
FROM SKILL WHERE skill_name = 'Python';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Microsoft Excel Help', 'Documentation', 'https://support.microsoft.com/excel'
FROM SKILL WHERE skill_name = 'Excel';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Khan Academy Statistics', 'Course', 'https://www.khanacademy.org/math/statistics-probability'
FROM SKILL WHERE skill_name = 'Statistics';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'Microsoft Learn Power BI', 'Course', 'https://learn.microsoft.com/training/powerplatform/power-bi/'
FROM SKILL WHERE skill_name = 'Power BI';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'MDN Learn HTML', 'Documentation', 'https://developer.mozilla.org/en-US/docs/Learn/HTML'
FROM SKILL WHERE skill_name = 'HTML';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'MDN Learn CSS', 'Documentation', 'https://developer.mozilla.org/en-US/docs/Learn/CSS'
FROM SKILL WHERE skill_name = 'CSS';

INSERT IGNORE INTO LEARNING_RESOURCE (skill_id, resource_name, resource_type, resource_link)
SELECT skill_id, 'MDN JavaScript Guide', 'Documentation', 'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide'
FROM SKILL WHERE skill_name = 'JavaScript';

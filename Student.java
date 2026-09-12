package com.skillnavi.model;

public class Student {
    private Integer studentId;
    private String name;
    private String email;
    private String education;

    public Student() {
    }

    public Student(String name, String email, String education) {
        this.name = name;
        this.email = email;
        this.education = education;
    }

    public Student(Integer studentId, String name, String email, String education) {
        this(name, email, education);
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

package com.skillnavi.model;

public class Student {

    private Integer studentId;
    private String name;
    private String email;
    private String department;
    private Integer year;

    public Student() {
    }

    public Student(String name, String email, String department, Integer year) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
    }

    public Student(Integer studentId, String name, String email,
                   String department, Integer year) {
        this(name, email, department, year);
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
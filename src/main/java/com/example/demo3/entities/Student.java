package com.example.demo3.entities;

public class Student {
    private String id;
    private String studentName;
    private int age;
    private String studentEmail;

//    public Student(String id, String studentName, int age, String studentEmail) {
//        this.id = id;
//        this.studentName = studentName;
//        this.age = age;
//        this.studentEmail = studentEmail;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}

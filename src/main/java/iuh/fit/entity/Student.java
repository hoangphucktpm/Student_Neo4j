/*
 * @(#) Student.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.entity;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class Student {
    // student(student_id, name, gpa)

    private String studentID;
    private String name;
    private double gpa;

    public Student() {
    }

    public Student(String studentID, String name, double gpa) {
        this.studentID = studentID;
        this.name = name;
        this.gpa = gpa;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID + ", name=" + name + ", gpa=" + gpa + '}';
    }


}

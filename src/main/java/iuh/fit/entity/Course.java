/*
 * @(#) Course.java       1.0  24/03/2024
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
public class Course {
    // course(course_id, name, hours, dept_id) //dept_id là khóa ngoại tham chiếu dept(dept_id)
    private String courseID;
    private String name;
    private int hours;
    private Department department;

    public Course() {
    }

    public Course(String courseID, String name, int hours, Department department) {
        this.courseID = courseID;
        this.name = name;
        this.hours = hours;
        this.department = department;
    }

    public Course(String courseID, String name, int hours) {
        this.courseID = courseID;
        this.name = name;
        this.hours = hours;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Course{" + "courseID=" + courseID + ", name=" + name + ", hours=" + hours + ", department=" + department + '}';
    }
}

/*
 * @(#) Enrollment.java       1.0  24/03/2024
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
public class Enrollment {
    // enrollment(course_id, student_id) //course_id là khóa ngoại tham chiếu course(course_id) và student_id là
    //khóa ngoại tham chiếu student(student_id)

    private Course course;
    private Student student;

    public Enrollment() {
    }

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Enrollment{" + "course=" + course + ", student=" + student + '}';
    }


}

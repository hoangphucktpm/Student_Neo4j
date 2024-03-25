/*
 * @(#) StudentDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.StudentDao;
import iuh.fit.utils.AppUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDaoTest {
    public static String DB_NAME = "neo4j";
    private StudentDao studentDao;

    @BeforeAll
    public void setUp() {
        studentDao = new StudentDao(AppUtils.initDriver(), DB_NAME);
    }

    //1. Liệt kê danh sách n sinh viên
    @Test
    public void testGetAllStudents() {
        System.out.println(studentDao.listStudents());
//        assertEquals(3, studentDao.listStudents().size());
    }

    //2. Tìm kiếm sinh viên khi biết mã số
    @Test
    public void testGetStudentByID() {
        System.out.println(studentDao.findStudentById("22"));
//        assertEquals("Nguyen Van A", studentDao.findStudentById("1").getName());
    }

    //12. Liệt kê danh sách các tên của các sinh viên đăng ký học khóa học CS101
    @Test
    public void testListStudentsByCourse() {
        studentDao.listStudentsByCourse("CS101").forEach(System.out::println);
    }

    // 18. Danh sách sinh viên có gpa >= 3.2, kết quả sắp xếp giảm dần theo gpa
    @Test
    public void testListStudentsByGPA() {
        studentDao.listStudentsByGpa().forEach(System.out::println);
    }

    @AfterAll
    public void tearDown() {
        studentDao.close();
    }
}
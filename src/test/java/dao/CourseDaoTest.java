/*
 * @(#) CourseDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.CourseDao;
import iuh.fit.entity.Course;
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
public class CourseDaoTest {
    public static String DB_NAME = "neo4j";
    private CourseDao courseDao;

    @BeforeAll
    public void setUp() {
        courseDao = new CourseDao(AppUtils.initDriver(), DB_NAME);
    }

    // 3. Tìm danh sách khóa học thuộc của một khoa nào đó khi biết mã khoa
    @Test
    public void testGetlistCoursesByDepartment() {
        System.out.println(courseDao.listCoursesByDepartment("CS"));
//        assertEquals(3, courseDao.listCoursesByDepartment("CS").size());
    }

    //6. Thêm khóa học vào khoa IE: IE202, Simulation, 3 hours
    @Test
    public void testAddCourse() {
        Course course = new Course("IE202", "Simulation", 3);
        courseDao.addCourseToDepartment("IE", course);
        System.out.println("Thêm khóa học thành công");
    }

    //7. Xóa toàn bộ các khóa học - DUNG THUC HIEN TRUOC BI XOA HET KHONG CON DU LIEU DE TEST
    @Test
    public void testDeleteAllCourses() {
        courseDao.deleteAllCourses();
        System.out.println("Xóa toàn bộ các khóa học thành công");
    }

    @AfterAll
    public void tearDown() {
        courseDao.close();
    }
}


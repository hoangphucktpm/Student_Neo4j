/*
 * @(#) DepartmentDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.CourseDao;
import iuh.fit.dao.DepartmentDao;
import iuh.fit.entity.Department;
import iuh.fit.utils.AppUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DepartmentDaoTest {
    public static String DB_NAME = "neo4j";
    private DepartmentDao departmentDao;

    @BeforeAll
    public void setUp() {
        departmentDao = new DepartmentDao(AppUtils.initDriver(), DB_NAME);
    }

    //4. Cập nhật name = “Mathematics” cho department_id = “Math”
    @Test
    public void testUpdateDepartment() {
        departmentDao.updateDepartmentName("Math", "Mathematics");
        System.out.println("Cập nhật thành công");
    }

    // 5. Cập nhật name = “Rock n Roll” cho department_id = “Music”
    @Test
    public void testUpdateDepartment2() {
        departmentDao.updateDepartmentName("Music", "Rock n Roll");
        System.out.println("Cập nhật thành công");
    }

    //8. Liệt kê tất cả các khoa
    @Test
    public void testListAllDepartments() {
        System.out.println(departmentDao.listAllDepartments());
    }

    //9. Liệt kê tên của tất cả các trưởng khoa
    @Test
    public void testListAllDepartmentHeads() {
        System.out.println(departmentDao.listAllDepartmentHeads());
    }

    // 10. Tìm tên của trưởng khoa của khoa “CS”
    @Test
    public void testFindDepartmentHead() {
        System.out.println(departmentDao.findDepartmentHead("CS"));
    }

    //11. Liệt kê tất cả các khóa học của CS và IE
    @Test
    public void testListAllCourses() {
        System.out.println(departmentDao.listAllCoursesOfCSAndIE());
    }

    //13. Tổng số sinh viên đăng ký học của mỗi khoa
    @Test
    public void testCountStudentsByDepartment() {
        System.out.println(departmentDao.countStudentsByDepartment());
    }

    //14. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo mã khoa
    @Test
    public void testCountStudentsByDepartmentOrderByDeptID() {
        System.out.println(departmentDao.countStudentsByDepartmentOrderByDeptID());
    }

    //15. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo số sinh viên
    @Test
    public void testCountStudentsByDepartmentOrderByCount() {
        System.out.println(departmentDao.countStudentsByDepartmentOrderBystudentID());
    }

    // 16. Liệt kê danh sách tên của các trưởng khoa mà các khoa này không có sinh viên đăng ký học
    @Test
    public void testListDepartmentHeadsNoStudent() {
        System.out.println(departmentDao.listDepartmentHeadsNoStudent());
    }

    // 17. Danh sách khoa có số sinh viên đăng ký học nhiều nhất
    @Test
    public void testListDepartmentMaxStudent() {
        System.out.println(departmentDao.listDepartmentMaxStudent());
    }

    @AfterAll
    public void tearDown() {
        departmentDao.close();
    }
}


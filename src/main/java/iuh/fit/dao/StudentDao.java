/*
 * @(#) StudentDao.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.dao;

import iuh.fit.entity.Student;
import iuh.fit.utils.AppUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class StudentDao {

    private Driver driver;
    private SessionConfig sessionConfig;

    public StudentDao(Driver driver, String dbName) {
        this.driver = driver;
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();

    }

    //  1. Liệt kê danh sách n sinh viên
    public List<Student> listStudents() {
        String query = "MATCH (a:Student) RETURN a";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("a").asNode();
                            return AppUtils.convert(node, Student.class);
                        })
                        .toList();
            });
        }
    }

    //2. Tìm kiếm sinh viên khi biết mã số
    public Student findStudentById(String studentId) {
        String query = "MATCH (a:Student {studentID: $studentId}) RETURN a";
        Map<String, Object> params = Map.of("studentId", studentId);
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("a").asNode();
                            return AppUtils.convert(node, Student.class);
                        })
                        .findFirst()
                        .orElse(null);
            });
        }
    }

    //12. Liệt kê danh sách các tên của các sinh viên đăng ký học khóa học CS101
    public List<String> listStudentsByCourse(String courseID) {
        String query = "MATCH (a:Student) -[:REGISTER]-> (b:Course {courseID: $courseID}) RETURN a.name";
        Map<String, Object> params = Map.of("courseID", courseID);
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> record.get("a.name").asString())
                        .toList();
            });
        }
    }

    // 18. Danh sách sinh viên có gpa >= 3.2, kết quả sắp xếp giảm dần theo gpa
    public List<Student> listStudentsByGpa() {
        String query = "MATCH (a:Student) WHERE a.gpa >= 3.2 RETURN a ORDER BY a.gpa DESC";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("a").asNode();
                            return AppUtils.convert(node, Student.class);
                        })
                        .toList();
            });
        }
    }

    public void close() {
        driver.close();
    }
}


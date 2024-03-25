/*
 * @(#) CourseDao.java       1.0  24/03/2024
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
import iuh.fit.entity.Course;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class CourseDao {

    private Driver driver;
    private SessionConfig sessionConfig;

    public CourseDao(Driver driver, String dbName) {
        this.driver = driver;
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();

    }

    //3. Tìm danh sách khóa học thuộc của một khoa nào đó khi biết mã khoa
    public List<Course> listCoursesByDepartment(String deptID) {
        String query = "MATCH (b:Course) -[:BELONGS_TO]-> (a:Department) WHERE toUpper(a.deptID) = $id RETURN b";
        Map<String, Object> params = Map.of("id", deptID);
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("b").asNode();
                            return AppUtils.convert(node, Course.class);
                        })
                        .toList();
            });
        }
    }

    // 6. Thêm khóa học vào khoa IE: IE202, Simulation, 3 hours
    public void addCourseToDepartment(String deptID, Course course) {
        String query = "CREATE (a:Course {courseID: $courseID, name: $name, hours: $hours}) WITH a MATCH (b:Department {deptID: $deptID}) CREATE (a) -[:BELONGS_TO]-> (b)";
        Map<String, Object> params = Map.of(
                "deptID", deptID,
                "courseID", course.getCourseID(),
                "name", course.getName(),
                "hours", course.getHours());
        try (Session session = driver.session(sessionConfig)) {
            session.writeTransaction(tx -> {
                tx.run(query, params);
                return null;
            });
        }
        }

        //7. Xóa toàn bộ các khóa học
        public void deleteAllCourses() {
            String query = "MATCH (a:Course) DETACH DELETE a";
            try (Session session = driver.session(sessionConfig)) {
                session.writeTransaction(tx -> {
                    tx.run(query);
                    return null;
                });
            }
        }

    public void close() {
        driver.close();
    }

}

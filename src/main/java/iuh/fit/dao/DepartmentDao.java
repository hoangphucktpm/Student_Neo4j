/*
 * @(#) DepartmentDao.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.dao;

import iuh.fit.entity.Department;
import iuh.fit.utils.AppUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.types.Node;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class DepartmentDao {
    private Driver driver;
    private SessionConfig sessionConfig;

    public DepartmentDao(Driver driver, String dbName) {
        this.driver = driver;
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();

    }

    //4 Cập nhật name
    public boolean updateDepartmentName(String deptID, String name) {
        String query = "MATCH (a:Department {deptID: $deptID}) SET a.name = $name";
        Map<String, Object> params = Map.of("deptID", deptID, "name", name);
        try (Session session = driver.session(sessionConfig)) {
            return session.writeTransaction(tx -> {
                Result result = tx.run(query, params);
                return result.consume().counters().nodesCreated() > 0;
            });
        }
    }

    //8.Liệt kê tất cả các khoa
    public List<Department> listAllDepartments() {
        String query = "MATCH (a:Department) RETURN a";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> {
                            Node node = record.get("a").asNode();
                            return AppUtils.convert(node, Department.class);
                        })
                        .toList();
            });
        }
    }

    // 9. Liệt kê tên của tất cả các trưởng khoa
    public List<String> listAllDepartmentHeads() {
        String query = "MATCH (a:Department) RETURN a.dean";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("a.dean").asString())
                        .toList();
            });
        }
    }

    // 10. Tìm tên của trưởng khoa CS
    public String findDepartmentHead(String deptID) {
        String query = "MATCH (a:Department {deptID: $deptID}) RETURN a.dean";
        Map<String, Object> params = Map.of("deptID", deptID);
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("a.dean").asString())
                        .findFirst()
                        .orElse(null);
            });
        }
    }

    // 11. Liệt kê tất cả các khóa học của CS và IE
    public List<String> listAllCoursesOfCSAndIE() {
        String query = "MATCH (c:Course)\n" +
                "WHERE c.deptID IN ['CS', 'IE']\n" +
                "RETURN c.courseID";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("c.courseID").asString())
                        .toList();
            });
        }
    }

    //13. Tổng số sinh viên đăng ký học của mỗi khoa
    public Map<String, Integer> countStudentsByDepartment() {
        String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                "RETURN d.deptID AS deptID, COUNT(*) AS total";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("deptID").asString(),
                                record -> record.get("total").asInt(),
                                (x, y) -> y
                        ));
            });
        }
    }

    //14. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo mã khoa
    public Map<String, Integer> countStudentsByDepartmentOrderByDeptID() {
        String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                "RETURN d.deptID AS deptID, COUNT(*) AS total \n" +
                "ORDER BY d.deptID";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("deptID").asString(),
                                record -> record.get("total").asInt(),
                                (x, y) -> y,
                                LinkedHashMap::new
                        ));
            });
        }
}


    // 15. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo số sinh viên
    public Map<String, Integer> countStudentsByDepartmentOrderBystudentID() {
        String query = "MATCH (s:Student)-[:ENROLLED]->(c:Course)\n" +
                "WITH c.deptID AS deptID, COUNT(DISTINCT s) AS totalStudents\n" +
                "RETURN deptID, totalStudents\n" +
                "ORDER BY totalStudents DESC";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("deptID").asString(),
                                record -> record.get("totalStudents").asInt(),
                                (x, y) -> y,
                                LinkedHashMap::new
                        ));
            });
        }
    }


    // 16. Liệt kê danh sách tên của các trưởng khoa mà các khoa này không có sinh viên đăng ký học
    public List<String> listDepartmentHeadsNoStudent() {
        String query = "MATCH (d:Department)\n" +
                "WHERE NOT (d)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student)\n" +
                "RETURN d.dean";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("d.dean").asString())
                        .toList();
            });
        }
    }

    //17. Danh sách khoa có số sinh viên đăng ký học nhiều nhất
    public List<String> listDepartmentMaxStudent() {
        String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) WITH d, COUNT(*) AS totalStudents RETURN d.deptID AS deptID, totalStudents ORDER BY totalStudents DESC LIMIT 1";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("deptID").asString())
                        .toList();
            });
        }
    }



    public void close() {
        driver.close();
    }
}

/*
 * @(#) Department.java       1.0  24/03/2024
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
public class Department {
    // department(dept_id, name, dean, building, room)
    private String deptID;
    private String name;
    private String dean;
    private String building;
    private String room;

    public Department() {
    }

    public Department(String deptID, String name, String dean, String building, String room) {
        this.deptID = deptID;
        this.name = name;
        this.dean = dean;
        this.building = building;
        this.room = room;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Department{" + "deptID=" + deptID + ", name=" + name + ", dean=" + dean + ", building=" + building + ", room=" + room + '}';
    }
}

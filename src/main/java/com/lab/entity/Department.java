package com.lab.entity;

import java.util.List;

/**
 * 部门实体类，一对多关系：一个部门对应多个员工
 */
public class Department {
    private Integer deptId;         //部门编号，主键
    private String deptName;        //部门名称
    private String location;        //部门办公地点
    private List<Employee> empList; //当前部门下所有员工集合（一对多映射）

    //无参构造
    public Department(){}

    //getter & setter
    public Integer getDeptId() {
        return deptId;
    }
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<Employee> getEmpList() {
        return empList;
    }
    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }
}

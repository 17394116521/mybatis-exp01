package com.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工实体类
 * 单表CRUD + 多对一关联Department（员工所属部门）
 */
@TableName("emp")
public class Emp {

    @TableId(type = IdType.AUTO)
    private Integer empId;
    private String empName;
    private String gender;
    private String dept;
    private String post;
    private BigDecimal salary;
    private Date hireDate;
    private Integer status;

    // ========= 多表关联新增字段 =========
    private Integer deptId;                // 外键：部门编号
    private Department department;         // 多对一：员工对应的部门对象

    //无参构造
    public Emp(){}

    //getter & setter
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }

    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeptId() {
        return deptId;
    }
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    // ====== 优化点：重写toString，打印多表关联结果，日志输出直观 ======
    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", dept='" + dept + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", status=" + status +
                ", deptId=" + deptId +
                ", department=" + department +
                '}';
    }
}

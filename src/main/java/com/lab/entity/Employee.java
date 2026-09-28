package com.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工实体类，多对一关系：多个员工属于同一个部门 对应employee表，MyBatis-Plus注解
 */
@TableName("employee")
public class Employee {
    @TableId(type = IdType.AUTO) // 主键自增
    private Integer empId;          //员工编号，主键
    private String empName;         //员工姓名
    private String jobTitle;        //岗位职位
    private Integer managerId;      //直属上级员工编号
    private Date hireDate;          //入职日期
    private BigDecimal salary;      //基本工资
    private BigDecimal bonus;       //绩效奖金
    private Integer deptId;         //所属部门编号
    private Department department;  //所属部门对象（多对一映射）

    //无参构造
    public Employee(){}

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
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public Integer getManagerId() {
        return managerId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
    public Date getHireDate() {
        return hireDate;
    }
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public BigDecimal getBonus() {
        return bonus;
    }
    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
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
}

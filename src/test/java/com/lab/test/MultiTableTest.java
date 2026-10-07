package com.lab.test;

import com.lab.entity.Department;
import com.lab.entity.Emp;
import com.lab.mapper.DepartmentMapper;
import com.lab.mapper.EmployeeMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MultiTableTest {

    //多对一：查询员工，附带部门信息
    @Test
    public void testEmpAndDept(){
        try(SqlSession session = MyBatisUtil.openSession()){
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Emp emp = mapper.getEmpAndDeptById(2);
            System.out.println(emp);
        }
    }

    //一对多：查询部门，带出该部门所有员工
    @Test
    public void testDeptWithEmp(){
        try(SqlSession session = MyBatisUtil.openSession()){
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            Department dept = mapper.findDeptWithEmpById(1);
            System.out.println(dept);
        }
    }
}

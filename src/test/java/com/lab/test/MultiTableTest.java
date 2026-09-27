package com.lab.test;

import com.lab.entity.Department;
import com.lab.entity.Employee;
import com.lab.mapper.DepartmentMapper;
import com.lab.mapper.EmployeeMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.util.List;

/**
 * 多表关联查询测试：多对一、一对多
 */
public class MultiTableTest {
    /**
     * 多对一测试：查询员工，同时获取所属部门信息
     */
    @Test
    public void testEmpAndDept(){
        try(SqlSession session = MyBatisUtil.openSession()){
            com.lab.mapper.EmployeeMapper mapper = session.getMapper(com.lab.mapper.EmployeeMapper.class);
            Employee emp = mapper.getEmpAndDeptById(2);
            System.out.println(emp);
        }
    }

    /**
     * 多对一测试：查询全部员工附带部门
     */
    @Test
    public void testListEmpWithDept(){
        try(SqlSession session = MyBatisUtil.openSession()){
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            List<Employee> empList = mapper.listEmpWithDept();
            empList.forEach(System.out::println);
        }
    }

    /**
     * 一对多测试：查询部门，带出该部门所有员工
     */
    @Test
    public void testDeptWithEmp(){
        try(SqlSession session = MyBatisUtil.openSession()){
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            Department dept = mapper.getDeptWithEmpById(2);
            System.out.println(dept);
            System.out.println("部门下员工列表："+dept.getEmpList());
        }
    }
}

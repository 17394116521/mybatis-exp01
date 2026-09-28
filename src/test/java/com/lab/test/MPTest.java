package com.lab.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lab.entity.Department;
import com.lab.entity.Employee;
import com.lab.mapper.DepartmentMapper;
import com.lab.mapper.EmployeeMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * MyBatis-Plus基础CRUD测试
 */
public class MPTest {

    @Test
    public void testMPSelect() {
        try(SqlSession session = MyBatisUtil.openSession()){
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            // MP条件构造器，查询薪资大于6000员工
            LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
            wrapper.ge(Employee::getSalary,new BigDecimal(6000));
            List<Employee> list = mapper.selectList(wrapper);
            for(Employee emp : list){
                System.out.println(emp.getEmpName() + " " + emp.getSalary());
            }
        }
    }
}

package com.lab.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lab.entity.Emp;
import com.lab.mapper.EmployeeMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class MPTest {

    /**
     * MyBatis-Plus 条件构造器查询
     */
    @Test
    public void testMPSelect() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            // 查询薪资大于6000员工
            LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
            wrapper.ge(Emp::getSalary, new BigDecimal("6000"));
            List<Emp> list = mapper.selectList(wrapper);
            for (Emp emp : list) {
                System.out.println(emp.getEmpName() + " --- " + emp.getSalary());
            }
        }
    }
}

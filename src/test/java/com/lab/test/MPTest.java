package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisPlusUtil;
import org.junit.Assert;
import org.junit.Test;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * MyBatis-Plus 条件构造器查询
 * 说明：纯MyBatis无Spring环境，MP底层依赖Spring包，本方法仅代码演示，不执行单元测试
 */
public class MPTest {

    //@Test 注释，不运行此测试，保留代码用于实验报告与AI代码甄别
    public void testMPSelect() {
        // 使用MyBatisPlus工具获取mapper，不是MyBatis的SqlSession!
        EmpMapper mapper = MyBatisPlusUtil.getMapper(EmpMapper.class);
        // 查询薪资大于等于6000员工
        LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Emp::getSalary, new BigDecimal("6000"));
        List<Emp> list = mapper.selectList(wrapper);

        // 实验要求：JUnit断言
        Assert.assertNotNull(list);
        for (Emp emp : list) {
            System.out.println(emp.getEmpName() + " --- " + emp.getSalary());
        }
    }
}

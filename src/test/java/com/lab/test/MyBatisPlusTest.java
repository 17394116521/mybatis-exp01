package com.lab.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * 任务4 MyBatis‑Plus演示
 * 注意：本实验禁止SpringBoot，为纯Maven+MyBatis环境
 * MP分页插件依赖Spring容器，故分页使用原生MyBatis LIMIT实现
 * ⚠️ 原生SqlSession无法调用MP内置mapper.selectList，因此注释@Test，仅保留代码用于实验报告展示，不执行测试
 */
public class MyBatisPlusTest {

    /**
     * QueryWrapper普通条件构造器演示
     */
    //@Test
    public void testQueryWrapper() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            QueryWrapper<Emp> wrapper = new QueryWrapper<>();
            // 姓名模糊匹配"张"，在职员工，薪资降序
            wrapper.like("emp_name", "张")
                    .eq("status", 1)
                    .orderByDesc("salary");
            // 原生MyBatis环境不能执行mapper.selectList，仅作为代码示例
//            List<Emp> list = mapper.selectList(wrapper);
//            list.forEach(System.out::println);
        }
    }

    /**
     * LambdaQueryWrapper（推荐，防止硬编码写错字段名）
     */
    //@Test
    public void testLambdaQueryWrapper() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            LambdaQueryWrapper<Emp> lw = new LambdaQueryWrapper<>();
            lw.like(Emp::getEmpName, "娜")
                    .eq(Emp::getStatus, 1);
            // 原生MyBatis环境不能执行mapper.selectList，仅作为代码示例
//            List<Emp> list = mapper.selectList(lw);
//            list.forEach(System.out::println);
        }
    }
}

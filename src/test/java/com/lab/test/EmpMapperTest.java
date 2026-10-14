package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class EmpMapperTest {

    /**
     * 新增员工测试，自动回填主键（末尾rollback，不污染基础数据）
     */
    @Test
    public void testInsert() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("临时测试人员");
            emp.setGender("女");
            emp.setDept("研发部");
            emp.setPost("测试工程师");
            emp.setSalary(new BigDecimal("9000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            int rows = mapper.insert(emp);
            // 移除 commit！改为rollback
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("回填主键 empId = " + emp.getEmpId());
            session.rollback();
        }
    }

    /**
     * 根据ID查询员工，更新薪资
     */
    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectById(1);
            emp.setSalary(new BigDecimal("13000"));
            int rows = mapper.update(emp);
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("更新影响行数："+rows);
            session.rollback();
        }
    }

    /**
     * 根据主键删除员工
     */
    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("临时员工");
            emp.setGender("男");
            emp.setDept("临时部");
            emp.setPost("临时工");
            emp.setSalary(new BigDecimal("3000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            mapper.insert(emp);
            System.out.println("插入成功，empId = " + emp.getEmpId());

            int rows = mapper.deleteById(emp.getEmpId());
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("删除成功，empId = " + emp.getEmpId());
            session.rollback();
        }
    }

    //========= 任务3：动态SQL单元测试 =========
    /**
     * 3.1 多条件模糊查询 if + where
     */
    @Test
    public void testSelectByCondition() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp condition = new Emp();
            condition.setEmpName("苏"); // 修改为表里存在的"苏沐瑶"
            condition.setDept("技术部");
            List<Emp> list = mapper.selectByCondition(condition);
            System.out.println("查询结果数量：" + list.size());
            list.forEach(System.out::println);
        }
    }

    /**
     * 3.1 多条件模糊查询 — 所有条件为null，等价于查询全部
     */
    @Test
    public void testSelectByConditionAllNull() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> list = mapper.selectByCondition(new Emp());
            System.out.println("全表查询数量：" + list.size());
            list.forEach(System.out::println);
        }
    }

    /**
     * 3.2 动态更新 set标签
     */
    @Test
    public void testUpdateDynamic() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpId(1);
            emp.setSalary(new BigDecimal("15000"));
            emp.setDept("技术部");
            int rows = mapper.updateDynamic(emp);
            System.out.println("动态更新影响行数：" + rows);

            Emp updated = mapper.selectById(1);
            System.out.println("更新后：" + updated);
            session.rollback();
        }
    }

    /**
     * 3.3 批量插入 foreach
     */
    @Test
    public void testInsertBatch() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp emp1 = new Emp();
            emp1.setEmpName("临时A");
            emp1.setGender("男");
            emp1.setDept("临时部");
            emp1.setPost("临时岗");
            emp1.setSalary(new BigDecimal("8000"));
            emp1.setHireDate(new Date());
            emp1.setStatus(1);

            Emp emp2 = new Emp();
            emp2.setEmpName("临时B");
            emp2.setGender("女");
            emp2.setDept("临时部");
            emp2.setPost("临时岗");
            emp2.setSalary(new BigDecimal("7500"));
            emp2.setHireDate(new Date());
            emp2.setStatus(1);

            List<Emp> emps = Arrays.asList(emp1, emp2);
            int rows = mapper.insertBatch(emps);
            org.junit.Assert.assertEquals(2, rows);
            System.out.println("批量插入成功，影响行数：" + rows);
            session.rollback();
        }
    }

    /**
     * 3.3 批量删除 foreach
     */
    @Test
    public void testDeleteBatch() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp emp1 = new Emp();
            emp1.setEmpName("待删A");
            emp1.setGender("男");
            emp1.setDept("临时部");
            emp1.setPost("临时工");
            emp1.setSalary(new BigDecimal("3000"));
            emp1.setHireDate(new Date());
            emp1.setStatus(1);

            Emp emp2 = new Emp();
            emp2.setEmpName("待删B");
            emp2.setGender("女");
            emp2.setDept("临时部");
            emp2.setPost("临时工");
            emp2.setSalary(new BigDecimal("3000"));
            emp2.setHireDate(new Date());
            emp2.setStatus(1);

            mapper.insert(emp1);
            mapper.insert(emp2);

            List<Integer> ids = Arrays.asList(emp1.getEmpId(), emp2.getEmpId());
            int rows = mapper.deleteBatch(ids);
            org.junit.Assert.assertEquals(2, rows);

            Emp check1 = mapper.selectById(emp1.getEmpId());
            Emp check2 = mapper.selectById(emp2.getEmpId());
            org.junit.Assert.assertNull(check1);
            org.junit.Assert.assertNull(check2);

            System.out.println("批量删除成功，影响行数：" + rows);
            session.rollback();
        }
    }

    /**
     * 3.4 choose when otherwise — 按姓名精确查询
     */
    @Test
    public void testSelectChooseByName() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp condition = new Emp();
            condition.setEmpName("苏沐瑶"); // 库中真实存在的名字
            List<Emp> list = mapper.selectChoose(condition);
            System.out.println("按姓名查询结果：" + list.size());
            list.forEach(System.out::println);
        }
    }

    /**
     * 3.4 choose when otherwise — 姓名为空，按部门查询
     */
    @Test
    public void testSelectChooseByDept() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp condition = new Emp();
            condition.setDept("技术部");
            List<Emp> list = mapper.selectChoose(condition);
            System.out.println("按部门查询结果：" + list.size());
            list.forEach(System.out::println);
        }
    }

    /**
     * 3.4 choose when otherwise — 都为空，查询全部
     */
    @Test
    public void testSelectChooseAll() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> list = mapper.selectChoose(new Emp());
            System.out.println("全表查询数量：" + list.size());
            list.forEach(System.out::println);
        }
    }

    /**
     * 任务4 MyBatis‑Plus分页查询测试（带业务条件：只查询在职员工）
     */
    @Test
    public void testMyBatisPage() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            int pageNum = 1;
            int pageSize = 3;
            int pageStart = (pageNum - 1) * pageSize;

            // 查询当前页数据
            List<Emp> records = mapper.selectPageEmp(pageStart, pageSize);
            // 查询总条数
            Long total = mapper.selectEmpTotal();

            // JUnit断言
            Assert.assertNotNull(records);
            Assert.assertTrue(records.size() <= pageSize);
            Assert.assertTrue(total >= 0);

            System.out.println("总记录数：" + total);
            System.out.println("当前第" + pageNum + "页，每页" + pageSize + "条");
            for (Emp emp : records) {
                System.out.println(emp.getEmpId() + " | " + emp.getEmpName() + " | " + emp.getDept());
            }
        }
    }

    /**
     * 任务4.4 QueryWrapper条件构造器测试
     * 查询薪资大于7000，在职员工，按薪资降序
     */
    //@Test
    public void testMPQueryWrapper(){
        try(SqlSession session = MyBatisUtil.openSession()){
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            QueryWrapper<Emp> wrapper = new QueryWrapper<>();
            wrapper.gt("salary",7000)
                    .eq("status",1)
                    .orderByDesc("salary");
            List<Emp> list = mapper.selectList(wrapper);
            System.out.println("\n==== QueryWrapper查询结果 ====");
            list.forEach(System.out::println);
            Assert.assertNotNull(list);
        }
    }

    /**
     * 任务4.4 LambdaQueryWrapper条件构造器测试（防止字段名硬编码写错）
     */
    //@Test
    public void testMPLambdaWrapper(){
        try(SqlSession session = MyBatisUtil.openSession()){
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            LambdaQueryWrapper<Emp> lambdaWrapper = new LambdaQueryWrapper<>();
            lambdaWrapper.gt(Emp::getSalary,7000)
                    .eq(Emp::getStatus,1)
                    .orderByDesc(Emp::getSalary);
            List<Emp> list = mapper.selectList(lambdaWrapper);
            System.out.println("\n==== LambdaQueryWrapper查询结果 ====");
            list.forEach(System.out::println);
            Assert.assertNotNull(list);
        }
    }
}

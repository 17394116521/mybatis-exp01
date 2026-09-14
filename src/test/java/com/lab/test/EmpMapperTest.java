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

public class EmpMapperTest {

    /**
     * 新增员工测试，自动回填主键
     */
    @Test
    public void testInsert() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("陈晨");
            emp.setGender("女");
            emp.setDept("研发部");
            emp.setPost("测试工程师");
            emp.setSalary(new BigDecimal("9000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            int rows = mapper.insert(emp);
            session.commit(); //增删改必须手动提交事务
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("回填主键 empId = " + emp.getEmpId());
        }
    }

    /**
     * 根据ID查询员工，更新薪资
     */
    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectById(1); //读取id=1员工
            emp.setSalary(new BigDecimal("13000"));
            int rows = mapper.update(emp);
            session.commit();
            org.junit.Assert.assertEquals(1, rows);
        }
    }

    /**
     * 根据主键删除员工
     * 删除的emp_id必须在数据库中存在，否则受影响行数为0，断言失败
     */
    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            // 1. 插入一条临时测试数据
            Emp emp = new Emp();
            emp.setEmpName("临时员工");
            emp.setGender("男");
            emp.setDept("临时部");
            emp.setPost("临时工");
            emp.setSalary(new BigDecimal("3000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            mapper.insert(emp);
            session.commit();
            System.out.println("插入成功，empId = " + emp.getEmpId());

            // 2. 用回填的主键删除
            int rows = mapper.deleteById(emp.getEmpId());
            session.commit();
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("删除成功，empId = " + emp.getEmpId());
        }
    }

        //========= 任务3：动态SQL单元测试 =========

        /**
         * 3.1 多条件模糊查询 if + where
         * 测试场景：传姓名关键字 + 部门，验证模糊匹配
         */
        @Test
        public void testSelectByCondition() {
            try (SqlSession session = MyBatisUtil.openSession()) {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                Emp condition = new Emp();
                condition.setEmpName("陈");
                condition.setDept("研发部");
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
         * 3.2 动态更新 set标签 — 只更新薪资和部门，其他字段不变
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
                session.commit();
                System.out.println("动态更新影响行数：" + rows);

                Emp updated = mapper.selectById(1);
                System.out.println("更新后：" + updated);
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
                emp1.setEmpName("张三"); emp1.setGender("男");
                emp1.setDept("市场部"); emp1.setPost("销售");
                emp1.setSalary(new BigDecimal("8000"));
                emp1.setHireDate(new Date()); emp1.setStatus(1);

                Emp emp2 = new Emp();
                emp2.setEmpName("李四"); emp2.setGender("女");
                emp2.setDept("财务部"); emp2.setPost("会计");
                emp2.setSalary(new BigDecimal("7500"));
                emp2.setHireDate(new Date()); emp2.setStatus(1);

                List<Emp> emps = Arrays.asList(emp1, emp2);
                int rows = mapper.insertBatch(emps);
                session.commit();
                org.junit.Assert.assertEquals(2, rows);
                System.out.println("批量插入成功，影响行数：" + rows);
            }
        }

        /**
         * 3.3 批量删除 foreach
         * 先插入两条数据，再用返回的empId批量删除
         */
        @Test
        public void testDeleteBatch() {
            try (SqlSession session = MyBatisUtil.openSession()) {
                EmpMapper mapper = session.getMapper(EmpMapper.class);

                Emp emp1 = new Emp();
                emp1.setEmpName("待删A"); emp1.setGender("男");
                emp1.setDept("临时部"); emp1.setPost("临时工");
                emp1.setSalary(new BigDecimal("3000"));
                emp1.setHireDate(new Date()); emp1.setStatus(1);

                Emp emp2 = new Emp();
                emp2.setEmpName("待删B"); emp2.setGender("女");
                emp2.setDept("临时部"); emp2.setPost("临时工");
                emp2.setSalary(new BigDecimal("3000"));
                emp2.setHireDate(new Date()); emp2.setStatus(1);

                mapper.insert(emp1);
                mapper.insert(emp2);
                session.commit();

                List<Integer> ids = Arrays.asList(emp1.getEmpId(), emp2.getEmpId());
                int rows = mapper.deleteBatch(ids);
                session.commit();
                org.junit.Assert.assertEquals(2, rows);
                System.out.println("批量删除成功，影响行数：" + rows);
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
                condition.setEmpName("陈晨");
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
                condition.setDept("研发部");
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

}

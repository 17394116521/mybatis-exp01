package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * MyBatis一级、二级缓存测试
 * 一级缓存：SqlSession级别，默认开启，同session重复查询不走数据库
 * 二级缓存：Mapper(namespace)级别，需要配置<cache/>，跨SqlSession共享，session close/commit才写入二级缓存
 */
public class CacheTest {

    /**
     * 测试一级缓存：同一个SqlSession，两次查询同一条数据，只执行1次SQL
     */
    @Test
    public void testFirstLevelCache() {
        SqlSession session = MyBatisUtil.openSession();
        EmpMapper mapper = session.getMapper(EmpMapper.class);

        System.out.println("====第一次查询empId=1====");
        Emp e1 = mapper.selectById(1);

        System.out.println("\n====第二次查询empId=1（同一session，命中一级缓存，不输出SQL）====");
        Emp e2 = mapper.selectById(1);

        // 对象引用相等，一级缓存返回同一个对象
        System.out.println("e1 == e2 ? " + (e1 == e2));

        session.close();
    }

    /**
     * 测试一级缓存失效：执行update之后缓存清空，再次查询重新发SQL
     */
    @Test
    public void testFirstCacheInvalidByUpdate() {
        SqlSession session = MyBatisUtil.openSession();
        EmpMapper mapper = session.getMapper(EmpMapper.class);

        mapper.selectById(1);
        System.out.println("\n执行更新操作，清空一级缓存");
        Emp emp = new Emp();
        emp.setEmpId(1);
        emp.setEmpName("test");
        emp.setSalary(new java.math.BigDecimal("8000"));
        mapper.update(emp);
        session.rollback();//回滚，不修改数据库真实数据

        System.out.println("\n更新后再次查询，重新执行SQL");
        mapper.selectById(1);
        session.close();
    }

    /**
     * 测试二级缓存：第一个session查询后close，第二个session可以命中二级缓存，不执行SQL
     */
    @Test
    public void testSecondLevelCache() {
        //会话1查询，关闭后数据写入二级缓存
        SqlSession s1 = MyBatisUtil.openSession();
        EmpMapper m1 = s1.getMapper(EmpMapper.class);
        m1.selectById(1);
        s1.close(); //❗关键点：必须close/commit，一级缓存才提交给二级缓存

        //会话2，新SqlSession，命中二级缓存，不打印SQL
        SqlSession s2 = MyBatisUtil.openSession();
        EmpMapper m2 = s2.getMapper(EmpMapper.class);
        System.out.println("新Session查询，尝试命中二级缓存");
        Emp emp = m2.selectById(1);
        s2.close();
    }

    /**
     * 二级缓存失效：执行增删改，清空当前mapper的二级缓存
     */
    @Test
    public void testSecondCacheInvalid() {
        SqlSession s1 = MyBatisUtil.openSession();
        EmpMapper m1 = s1.getMapper(EmpMapper.class);
        m1.selectById(1);
        s1.close();

        //执行更新，清空二级缓存
        SqlSession s2 = MyBatisUtil.openSession();
        EmpMapper m2 = s2.getMapper(EmpMapper.class);
        Emp emp = new Emp();
        emp.setEmpId(1);
        emp.setEmpName("xxx");
        m2.update(emp);
        s2.rollback();
        s2.close();

        //再次查询，二级缓存已清空，重新访问数据库
        SqlSession s3 = MyBatisUtil.openSession();
        EmpMapper m3 = s3.getMapper(EmpMapper.class);
        m3.selectById(1);
        s3.close();
    }
}

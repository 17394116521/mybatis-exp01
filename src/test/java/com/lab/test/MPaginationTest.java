package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * MyBatis-Plus分页插件单元测试
 * 任务4：验证分页插件、实体MP注解、分页查询
 */
public class MPaginationTest {

    @Test
    public void testEmpPageQuery(){
        try (SqlSession session = MyBatisUtil.openSession()){
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            int pageNum = 1;
            int pageSize = 3;
            int pageStart = (pageNum -1) * pageSize;

            // 查询分页数据
            List<Emp> pageData = mapper.selectPageEmp(pageStart, pageSize);
            // 查询总条数
            Long total = mapper.selectEmpTotal();

            // =========JUnit断言=========
            assertNotNull(pageData);
            assertTrue(total >= 0);
            assertTrue(pageData.size() <= pageSize);

            //打印日志
            System.out.println("====分页结果====");
            System.out.println("当前页："+pageNum);
            System.out.println("每页大小："+pageSize);
            System.out.println("总记录数："+total);
            pageData.forEach(System.out::println);
        }
    }
}

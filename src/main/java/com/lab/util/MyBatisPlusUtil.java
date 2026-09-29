package com.lab.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * MyBatis-Plus 专用工具类
 * 与 MyBatisUtil 的区别：
 * 1. 使用 MP 的 MybatisConfiguration 解析配置（支持 BaseMapper 内置方法注入）
 * 2. 注册 PaginationInnerInterceptor 分页插件，否则 selectPage 分页不生效
 */
public class MyBatisPlusUtil {
    // 全局唯一SqlSessionFactory
    private static SqlSessionFactory factory;

    static {
        try (InputStream is = Resources.getResourceAsStream("mybatis-config.xml")) {
            // 用MP的配置解析器读取mybatis-config.xml
            MybatisXMLConfigBuilder builder = new MybatisXMLConfigBuilder(is);
            MybatisConfiguration configuration = (MybatisConfiguration) builder.parse();

            // ===== 注册MP分页插件（关键步骤） =====
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            configuration.addInterceptor(interceptor);

            factory = new SqlSessionFactoryBuilder().build(configuration);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // 获取SqlSession
    public static SqlSession openSession() {
        return factory.openSession();
    }
}
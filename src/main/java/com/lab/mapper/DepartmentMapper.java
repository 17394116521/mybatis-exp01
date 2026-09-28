package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Department;

/**
 * MyBatis-Plus Mapper，继承BaseMapper自动获得增删改查
 */
public interface DepartmentMapper extends BaseMapper<Department> {
    Department getDeptWithEmpById(int i);
    // 原来手写的多表关联方法保留，MP自动新增基础CRUD，不需要写XML基础SQL
}

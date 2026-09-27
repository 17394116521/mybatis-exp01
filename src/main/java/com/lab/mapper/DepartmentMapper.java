package com.lab.mapper;

import com.lab.entity.Department;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {
    /**
     * 根据部门id查询部门，同时查询部门下所有员工（一对多）
     * @param deptId 部门编号
     * @return 部门对象（包含员工集合）
     */
    Department getDeptWithEmpById(@Param("deptId") Integer deptId);
}

package com.lab.mapper;

import com.lab.entity.Department;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {
    //一对多：根据部门id查询部门以及下属全部员工
    Department findDeptWithEmpById(@Param("deptId") Integer deptId);
}

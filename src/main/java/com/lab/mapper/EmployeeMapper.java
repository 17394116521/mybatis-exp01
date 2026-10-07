package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper extends BaseMapper<Emp> {
    // ===== 多对一查询：员工+所属部门 =====
    Emp getEmpAndDeptById(@Param("empId") Integer empId);
}

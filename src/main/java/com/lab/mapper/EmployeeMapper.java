package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper extends BaseMapper<Employee> {
    // ===== 多对一查询：员工+所属部门 =====
    Employee getEmpAndDeptById(@Param("empId") Integer empId);
}

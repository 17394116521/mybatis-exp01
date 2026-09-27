package com.lab.mapper;

import com.lab.entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmployeeMapper {
    /**
     * 根据员工id查询员工信息 + 所属部门（多对一）
     * @param empId 员工编号
     * @return 员工对象（内部包含部门对象）
     */
    Employee getEmpAndDeptById(@Param("empId") Integer empId);

    /**
     * 查询所有员工，附带所属部门信息
     * @return 员工集合
     */
    List<Employee> listEmpWithDept();
}

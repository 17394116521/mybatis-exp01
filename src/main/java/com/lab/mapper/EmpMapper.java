package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EmpMapper
 * 继承 BaseMapper<Emp>，自带MyBatis-Plus通用CRUD方法
 * 下方手写方法为原生MyBatis XML自定义实现
 */
public interface EmpMapper extends BaseMapper<Emp> {

    //===== 手写MyBatis基础CRUD（XML实现）=====
    Emp selectById(Integer empId);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(Integer empId);

    List<Emp> selectAll();

    //========= 任务3 动态SQL =========
    /**
     * 3.1 多条件模糊查询 if + where
     */
    List<Emp> selectByCondition(Emp emp);

    /**
     * 3.1 使用trim标签实现等效where逻辑
     */
    List<Emp> selectByConditionTrim(Emp emp);

    /**
     * 3.2 动态更新 set标签
     */
    int updateDynamic(Emp emp);

    /**
     * 3.3 批量删除 foreach
     */
    int deleteBatch(@Param("ids") List<Integer> ids);

    /**
     * 3.3 批量插入 foreach
     */
    int insertBatch(@Param("list") List<Emp> emps);

    /**
     * 3.4 choose when otherwise 分支选择
     * 优先按姓名精确查询；姓名为空，则按部门查询；都空，查询全部
     */
    List<Emp> selectChoose(Emp emp);

    /**
     * 任务4 原生MyBatis手写分页
     */
    List<Emp> selectPageEmp(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    /**
     * 任务4 统计总记录数，配合原生分页
     */
    Long selectEmpTotal();

    /**
     * 根据员工id查询员工，同时带出所属部门信息【多对一】
     */
    Emp findEmpWithDeptById(@Param("empId") Integer empId);
}

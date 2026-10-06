package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;
import java.util.List;

// 重点：继承 BaseMapper<Emp>，MP自带 selectPage、selectList 等方法
public interface EmpMapper extends BaseMapper<Emp> {

    //===== 手写MyBatis基础CRUD（自己XML实现，保留）=====
    Emp selectById(Integer empId);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(Integer empId);

    List<Emp> selectAll();

    //========= 任务3 动态SQL新增方法 =========

    /**
     * 3.1 多条件模糊查询 if + where
     */
    List<Emp> selectByCondition(Emp emp);

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
     * 任务4 原生MyBatis手写分页（拓展，保留）
     */
    List<Emp> selectPageEmp(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    /**
     * 任务4 统计总记录数（原生MyBatis手写分页，保留）
     */
    Long selectEmpTotal();

}
package com.lab.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmpMapper {

    //===== 基础CRUD方法 =====
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
     * 任务4 手写分页查询
     */
    List<Emp> selectPageEmp(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    /**
     * 任务4 统计总记录数
     */
    Long selectEmpTotal();

    /**
     * MyBatis-Plus分页查询
     */
    IPage<Emp> selectPage(Page<Emp> page, @Param("query") Emp emp);

}

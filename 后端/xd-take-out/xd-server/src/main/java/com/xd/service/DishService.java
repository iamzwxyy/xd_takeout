package com.xd.service;

import com.xd.dto.DishDTO;
import com.xd.dto.DishPageQueryDTO;
import com.xd.entity.Dish;
import com.xd.result.PageResult;
import com.xd.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和对应口味数据
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品和对应口味数据
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应口味信息
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    void setStatus(Integer status, Integer id);

    /**
     * 条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
}

package com.xd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xd.constant.MessageConstant;
import com.xd.constant.StatusConstant;
import com.xd.dto.SetmealDTO;
import com.xd.dto.SetmealPageQueryDTO;
import com.xd.entity.Setmeal;
import com.xd.entity.SetmealDish;
import com.xd.exception.DeletionNotAllowedException;
import com.xd.mapper.SetmealDishMapper;
import com.xd.mapper.SetmealMapper;
import com.xd.result.PageResult;
import com.xd.service.SetmealService;
import com.xd.vo.DishItemVO;
import com.xd.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            return;
        }

        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId()); //important
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 根据id查询套餐和关联的菜品数据
     * @param id
     * @return
     */
    public SetmealVO getByIdWithDishes(Long id) {
        //根据id查询套餐数据
        Setmeal setmeal = setmealMapper.getById(id);

        //根据id查询关联菜品数据
        List<SetmealDish> setmealDishes = setmealDishMapper.getDishesBySetmealId(id);

        //将查询到的数据封装到VO并返回
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealDishMapper.getDishItemBySetmealId(id);
    }

    /**
     * 套餐起售停售
     * @param status
     * @param id
     */
    public void setStatus(Integer status, Long id) {
        setmealMapper.updateStatus(status, id);
    }

    /**
     * 修改套餐基本信息和对应菜品信息
     * @param setmealDTO
     */
    @Transactional
    public void updateWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //修改套餐表基本信息
        setmealMapper.update(setmeal);

        //删除原有的菜品数据
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());

        //重新插入菜品数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            return;
        }

        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId()); //important
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (Objects.equals(setmeal.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }
}

package com.ysytech.tourism.auth.service;


import com.ysytech.tourism.auth.domain.PowerVO;
import com.ysytech.tourism.auth.entity.Power;
import com.ysytech.tourism.common.call.R;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author mc
 * Create date 2020-02-10 10:15:20
 * Version 1.0
 * Description 权限表接口
 */
public interface PowerService {
    /**
     * 新增
     *
     * @param power 对象
     * @return 前端返回格式
     */
    Power insert(Power power);

    /**
     * 修改
     *
     * @param power 对象
     */
    void update(Power power);

    /**
     * 根据主键删除
     *
     * @param id 标识
     */
    void deleteById(@NotNull @Min(1) Long id);

    /**
     * 根据主键查询
     *
     * @param id 标识
     * @return 前端返回格式
     */
    Optional<Power> findById(@NotNull @Min(1) Long id);

    /**
     * 条件分页查询
     *
     * @param page 当前页
     * @param size 显示多少条
     * @param sort 排序字段
     * @param vo   查询条件
     * @return 前端返回格式
     */
    R pageQuery(Integer page, Integer size, String sort, PowerVO vo);


    /**
     * 新增修改
     *
     * @param vo vo
     * @return Banner
     */
    Power insertOrUpdate(PowerVO vo, Long userId);

    /**
     * 添加权限菜单
     *
     * @param powerId 权限标识
     * @param menuId  菜单标识
     */
    void addPowerMenu(Long powerId, Long menuId);

    /**
     * 删除权限菜單
     *
     * @param powerId 权限标识
     * @param menuId  菜单标识
     */
    void deletePowerMenu(Long powerId, Long menuId);

    /**
     * 根据权限ID分页查询菜单
     *
     * @param page    当前页
     * @param size    大小1
     * @param sort    排序
     * @param powerId 权限标识
     * @return result
     */
    R pageQueryMenu(Integer page, Integer size, String sort, Long powerId);

    /**
     * 添加权限接口
     *
     * @param powerId      权限标识
     * @param operationsId operationsId
     */
    void addPowerOperations(Long powerId, Long operationsId);

    /**
     * 删除权限接口
     *
     * @param powerId      权限标识
     * @param operationsId operationsId
     */
    void deletePowerOperations(Long powerId, Long operationsId);

    /**
     * 根据权限ID分页查询接口
     *
     * @param page    当前页
     * @param size    大小
     * @param sort    排序
     * @param powerId 权限
     * @return Result
     */
    R pageQueryOperations(Integer page, Integer size, String sort, Long powerId);
}

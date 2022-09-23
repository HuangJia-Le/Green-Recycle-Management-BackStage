package com.qingge.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingge.springboot.common.Result;

import com.qingge.springboot.service.IOrderStateService;
import com.qingge.springboot.entity.OrderState;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/order-state")
public class OrderStateController {

    @Resource
    private IOrderStateService orderStateService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody OrderState orderState) {
        orderStateService.saveOrUpdate(orderState);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        orderStateService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        orderStateService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(orderStateService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderStateService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<OrderState> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(orderStateService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}


package com.qingge.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingge.springboot.common.Result;

import com.qingge.springboot.service.IItemTypeService;
import com.qingge.springboot.entity.ItemType;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/item-type")
public class ItemTypeController {

    @Resource
    private IItemTypeService itemTypeService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody ItemType itemType) {
        itemTypeService.saveOrUpdate(itemType);
        return Result.success();
    }

    // 查询购买物品对应的积分
    @PostMapping("/{item}")
    public Result select(@PathVariable String item) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item",item);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        itemTypeService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        itemTypeService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(itemTypeService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(itemTypeService.getById(id));
    }

    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Tid","1");
        queryWrapper.orderByDesc("id");
        return Result.success(itemTypeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page2")
    public Result findPage2(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Tid","2");
        queryWrapper.orderByDesc("id");
        return Result.success(itemTypeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}


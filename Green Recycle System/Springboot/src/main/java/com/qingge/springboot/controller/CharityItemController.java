package com.qingge.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingge.springboot.common.Result;
import com.qingge.springboot.service.ICharityItemService;
import com.qingge.springboot.entity.CharityItem;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/charity-item")
public class CharityItemController {

    @Resource
    private ICharityItemService charityItemService;
    @Value("${server.port}")
    private String port;

    private static final String ip="http://localhost";
    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody CharityItem charityItem) {
        charityItemService.saveOrUpdate(charityItem);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        charityItemService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        charityItemService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(charityItemService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(charityItemService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                               @RequestParam String item,
                                @RequestParam Integer mingrade,
                              @RequestParam Integer maxgrade) {
        QueryWrapper<CharityItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(item)) {
            queryWrapper.like("name", item);
        }
        if(mingrade>=0&&maxgrade>0) {
                queryWrapper.between("grade", mingrade, maxgrade);
        }
        return Result.success(charityItemService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<CharityItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(charityItemService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }



}


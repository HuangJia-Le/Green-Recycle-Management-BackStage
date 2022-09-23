package com.qingge.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingge.springboot.common.Result;
import com.qingge.springboot.entity.Order;
import com.qingge.springboot.entity.User;
import com.qingge.springboot.service.IOrderService;
import com.qingge.springboot.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<Order> list = orderService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (Order order : list) {
            Date createTime = order.getCreatetime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @GetMapping("/members1")
    public Result members1() {
        List<User> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @GetMapping("/members2")
    public Result members2() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","慈善订单");
        List<Order> list = orderService.list(queryWrapper);
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (Order order : list) {
            Date createTime = order.getCreatetime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @GetMapping("/members3")
    public Result members3() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","回收订单");
        List<Order> list = orderService.list(queryWrapper);
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (Order order : list) {
            Date createTime = order.getCreatetime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }

        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
//获取用户的总数量
    @GetMapping("/members4")
    public Result members4() {
        return Result.success(orderService.count());
    }

    @GetMapping("/members5")
    public Result members5() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","慈善订单");
        return Result.success(orderService.count(queryWrapper));
    }

    @GetMapping("/members6")
    public Result members6() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","回收订单");
        return Result.success(orderService.count(queryWrapper));
    }

    @GetMapping("/members7")
    public Result members7() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","公益商城订单");
        return Result.success(orderService.count(queryWrapper));
    }

    @GetMapping("/members8")
    public Result members8() {
        return Result.success(userService.count());
    }

    @GetMapping("/members9")
    public Result members9() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role","ROLE_USER");
        return Result.success(userService.count(queryWrapper));
    }

    @GetMapping("/members10")
    public Result members10() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role","ROLE_CHARITY");
        return Result.success(userService.count(queryWrapper));
    }

    @GetMapping("/members11")
    public Result members11() {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role","ROLE_RECYCLE");
        return Result.success(userService.count(queryWrapper));
    }

}

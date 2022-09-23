package com.qingge.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingge.springboot.entity.CharityItem;
import com.qingge.springboot.entity.ItemType;
import com.qingge.springboot.entity.User;
import com.qingge.springboot.mapper.CharityItemMapper;
import com.qingge.springboot.mapper.UserMapper;
import com.qingge.springboot.service.ICharityItemService;
import com.qingge.springboot.service.IItemTypeService;
import com.qingge.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qingge.springboot.common.Result;

import com.qingge.springboot.service.IOrderService;
import com.qingge.springboot.entity.Order;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @Resource
    private IItemTypeService iItemTypeService;

    @Resource
    private ICharityItemService charityItemService;

    @Resource
    private IUserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CharityItemMapper charityItemMapper;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Order order) {
        order.setCreatetime(new Date());
        orderService.saveOrUpdate(order);
        return Result.success();
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        orderService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(orderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderService.getById(id));
    }
//慈善订单
    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String user,
                                @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","慈善订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("company", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
//回收订单
    @GetMapping("/page2")
    public Result findPage2(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String user,
                           @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","回收订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("company", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }
//公益商城订单
    @GetMapping("/page3")
    public Result findPage3(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String user,
                            @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","公益商城订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
            }
        if (!"".equals(company)) {
            queryWrapper.like("company", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //回收公司搜索订单
    @GetMapping("/page4")
    public Result findPage4(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String user,
                            @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","回收订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("state", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //慈善公司搜索订单
    @GetMapping("/page5")
    public Result findPage5(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String user,
                            @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","慈善订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("state", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //慈善公司搜索订单
    @GetMapping("/page6")
    public Result findPage6(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam String charity,
                            @RequestParam(defaultValue = "") String user,
                            @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("company",charity);
        queryWrapper.eq("type","慈善订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("state", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/page7")
    public Result findPage7(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam String recycle,
                            @RequestParam(defaultValue = "") String user,
                            @RequestParam(defaultValue = "") String company) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("company",recycle);
        queryWrapper.eq("type","回收订单");
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        if (!"".equals(company)) {
            queryWrapper.like("state", company);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    /**
     * 慈善订单导出接口
     */
    @GetMapping("/export1")
    public void export1(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","慈善订单");
        List<Order> list = orderService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("user", "用户");
        writer.addHeaderAlias("company", "公益团体");
        writer.addHeaderAlias("orderItem", "订单物品");
        writer.addHeaderAlias("orderNum", "订单数量");
        writer.addHeaderAlias("grade", "公益积分");
        writer.addHeaderAlias("telephone", "联系方式");
        writer.addHeaderAlias("address", "订单地址");
        writer.addHeaderAlias("state", "订单状态");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("慈善订单信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import1")
    public Result imp1(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Order> orders = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Order order = new Order();
            order.setUser(row.get(0).toString());
            order.setCompany(row.get(1).toString());
            order.setOrderItem(row.get(2).toString());
            order.setOrderNum(Integer.valueOf(row.get(3).toString()));
            order.setGrade(Integer.valueOf(row.get(4).toString()));
            order.setAddress(row.get(5).toString());
            order.setState(row.get(6).toString());
            orders.add(order);
        }
        orderService.saveBatch(orders);
        return Result.success(true);
    }

    /**
     * 回收订单导出接口
     */
    @GetMapping("/export2")
    public void export2(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","回收订单");
        List<Order> list = orderService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("user", "用户");
        writer.addHeaderAlias("company", "回收公司");
        writer.addHeaderAlias("orderItem", "订单物品");
        writer.addHeaderAlias("orderNum", "订单数量");
        writer.addHeaderAlias("grade", "公益积分");
        writer.addHeaderAlias("address", "订单地址");
        writer.addHeaderAlias("state", "订单状态");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("回收订单信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import2")
    public Result imp2(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Order> orders = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Order order = new Order();
            order.setUser(row.get(0).toString());
            order.setCompany(row.get(1).toString());
            order.setOrderItem(row.get(2).toString());
            order.setOrderNum(Integer.valueOf(row.get(3).toString()));
            order.setGrade(Integer.valueOf(row.get(4).toString()));
            order.setAddress(row.get(5).toString());
            order.setState(row.get(6).toString());
            orders.add(order);
        }
        orderService.saveBatch(orders);
        return Result.success(true);
    }

    /**
     * 公益商城订单导出接口
     */
    @GetMapping("/export3")
    public void export3(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","公益商城订单");
        List<Order> list = orderService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("user", "用户");
        writer.addHeaderAlias("company", "发货人");
        writer.addHeaderAlias("orderItem", "订单物品");
        writer.addHeaderAlias("orderNum", "订单数量");
        writer.addHeaderAlias("grade", "公益积分");
        writer.addHeaderAlias("address", "订单地址");
        writer.addHeaderAlias("state", "订单状态");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("公益商城订单信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import3")
    public Result imp3(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Order> orders = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Order order = new Order();
            order.setUser(row.get(0).toString());
            order.setCompany(row.get(1).toString());
            order.setCharityItem(row.get(2).toString());
            order.setOrderNum(Integer.valueOf(row.get(3).toString()));
            order.setGrade(Integer.valueOf(row.get(4).toString()));
            order.setAddress(row.get(5).toString());
            order.setState(row.get(6).toString());
            orders.add(order);
        }
        orderService.saveBatch(orders);
        return Result.success(true);
    }

    // 用户发布慈善/回收订单
    @PostMapping("/user")
    public Result saveUserOrder(@RequestBody Order order) {
        order.setCreatetime(new Date());
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item",order.getOrderItem());
        List<ItemType> list=iItemTypeService.list(queryWrapper);
//        获取到对应商品的数量
        int a=order.getOrderNum();
//        获取到对应商品的公益积分
        int b=list.get(0).getGrade();
        int c=a*b;
        order.setGrade(c);
        orderService.saveOrUpdate(order);
        return Result.success();
    }

    // 用户发布公益商城订单，需要设计公益积分是否满足商品积分的闲置
    @PostMapping("/user1")
    public Result saveUserOrder1(@RequestBody Order order) {
        order.setCreatetime(new Date());
        //在用户表中查询到此订单的公益商品
        QueryWrapper<CharityItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",order.getCharityItem());
        List<CharityItem> list=charityItemService.list(queryWrapper);
        //在用户表中查询到此订单的用户
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username",order.getUser());
        List<User> list1=userService.list(queryWrapper1);
//        获取到对应商品的公益积分
        int a=list.get(0).getGrade();
//        获取到对应用户的剩余公益积分
        int b=list1.get(0).getGrade();
//        获取当前公益商品的剩余数量
        int c=list.get(0).getNum();
//        当前商品的数量为0或者数量少于用户下单数量
        if(c==0||c<order.getOrderNum())
        {
            return Result.error();
        }
//        用户的剩余公益积分少于订单要求的公益积分
        if(b<a*order.getOrderNum())
        {
            return Result.error();
        }
//        如果成功执行下单操作，则用户的公益积分减少，商品数量减少
        int d=c-order.getOrderNum();
        int e=b-a*order.getOrderNum();
//        获取该公益商品的Id，以便于根据Id进行更新
        int f=list.get(0).getId();
//        获取该用户的Id，以便于根据Id进行更新
        int g=list1.get(0).getId();
//        更新用户的公益积分
        User user=new User();
        user.setId(g);
        user.setGrade(b-a*order.getOrderNum());
        userMapper.updateById(user);
//        更新商品的剩余数量
        CharityItem charityItem=new CharityItem();
        charityItem.setId(f);
        charityItem.setNum(c-order.getOrderNum());
        charityItemMapper.updateById(charityItem);
//        完成上述操作之后保存订单
        order.setGrade(a*order.getOrderNum());
        orderService.saveOrUpdate(order);
        return Result.success();
    }


    // 用户取消订单时,各个表的数据变化操作
    @PostMapping("/user2")
    public Result update(@RequestBody Order order) {
        QueryWrapper<CharityItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",order.getCharityItem());
        List<CharityItem> list=charityItemService.list(queryWrapper);
        //在用户表中查询到此订单的用户
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username",order.getUser());
        List<User> list1=userService.list(queryWrapper1);
//        获取到对应商品的公益积分
        int a=list.get(0).getGrade();
//        获取到对应用户的剩余公益积分
        int b=list1.get(0).getGrade();
//        获取当前公益商品的剩余数量
        int c=list.get(0).getNum();
//        获取该公益商品的Id，以便于根据Id进行更新
        int f=list.get(0).getId();
//        获取该用户的Id，以便于根据Id进行更新
        int g=list1.get(0).getId();
//        更新用户的公益积分
        User user=new User();
        user.setId(g);
        user.setGrade(b+a*order.getOrderNum());
        userMapper.updateById(user);
//        更新商品的剩余数量
        CharityItem charityItem=new CharityItem();
        charityItem.setId(f);
        charityItem.setNum(c+order.getOrderNum());
        charityItemMapper.updateById(charityItem);
        orderService.saveOrUpdate(order);
        return Result.success();
    }

    // 当订单状态为发放积分时,用户表的积分变化
    @PostMapping("/user3")
    public Result update1(@RequestBody Order order) {
        order.setCreatetime(new Date());
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item",order.getOrderItem());
        List<ItemType> list=iItemTypeService.list(queryWrapper);
//        获取到对应商品的数量
        int a=order.getOrderNum();
//        获取到对应商品的公益积分
        int b=list.get(0).getGrade();
        int c=a*b;
        order.setGrade(c);
        //在用户表中查询到此订单的用户
        if(order.getState().equals("发放积分")) {
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("username", order.getUser());
            List<User> list1 = userService.list(queryWrapper1);
//        获取到对应用户的剩余公益积分
            int d = list1.get(0).getGrade();
//        获取该用户的Id，以便于根据Id进行更新
            int g = list1.get(0).getId();
//        更新用户的公益积分
            User user = new User();
            user.setId(g);
            user.setGrade(d + order.getGrade());
            userMapper.updateById(user);
        }
        orderService.saveOrUpdate(order);
        return Result.success();
    }



    //用户查看慈善订单
    @GetMapping("/pageplus")
    public Result findPageplus(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                               @RequestParam String username,
                            @RequestParam(defaultValue = "") String item,
                            @RequestParam(defaultValue = "") String state) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","慈善订单");
        queryWrapper.eq("user",username);
        if (!"".equals(item)) {
            queryWrapper.like("order_item", item);
        }
        if (!"".equals(state)) {
            queryWrapper.like("state", state);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //用户查看回收订单
    @GetMapping("/pageplus1")
    public Result findPageplus1(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String username,
                               @RequestParam(defaultValue = "") String item,
                               @RequestParam(defaultValue = "") String state) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","回收订单");
        queryWrapper.eq("user",username);
        if (!"".equals(item)) {
            queryWrapper.like("order_item", item);
        }
        if (!"".equals(state)) {
            queryWrapper.like("state", state);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //用户查看回收订单
    @GetMapping("/pageplus2")
    public Result findPageplus2(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam String username,
                                @RequestParam(defaultValue = "") String item,
                                @RequestParam(defaultValue = "") String state) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","公益商城订单");
        queryWrapper.eq("user",username);
        if (!"".equals(item)) {
            queryWrapper.like("order_item", item);
        }
        if (!"".equals(state)) {
            queryWrapper.like("state", state);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }


}


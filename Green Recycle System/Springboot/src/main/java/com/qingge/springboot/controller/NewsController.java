package com.qingge.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingge.springboot.entity.News;
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

import com.qingge.springboot.service.INewsService;
import com.qingge.springboot.entity.News;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private INewsService newsService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody News news) {
        news.setCreatetime(new Date());
        newsService.saveOrUpdate(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        newsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        newsService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(newsService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(newsService.getById(id));
    }


    // 慈善文章显示
    @GetMapping("/page1")
    public Result findPage1(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String author) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","慈善文章");
        if (!"".equals(author)) {
            queryWrapper.like("author", author);
        }
        return Result.success(newsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 绿色文章显示
    @GetMapping("/page2")
    public Result findPage2(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String author) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","绿色文章");
        if (!"".equals(author)) {
            queryWrapper.like("author", author);
        }
        return Result.success(newsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 其他文章显示
    @GetMapping("/page3")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String author) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type","其他文章");
        if (!"".equals(author)) {
            queryWrapper.like("author", author);
        }
        return Result.success(newsService.page(new Page<>(pageNum, pageSize), queryWrapper));
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
        List<News> Newss = CollUtil.newArrayList();
        for (List<Object> row : list) {
            News News = new News();
            News.setTitle(row.get(0).toString());
            News.setAuthor(row.get(1).toString());
            News.setContent(row.get(2).toString());
            Newss.add(News);
        }
        newsService.saveBatch(Newss);
        return Result.success(true);
    }

    /**
     * 慈善文章导出接口
     */
    @GetMapping("/export1")
    public void export1(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<News> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","慈善文章");
        List<News> list = newsService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("author", "作者");
        writer.addHeaderAlias("content", "内容");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("慈善文章信息", "UTF-8");
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
        List<News> Newss = CollUtil.newArrayList();
        for (List<Object> row : list) {
            News News = new News();
            News.setTitle(row.get(0).toString());
            News.setAuthor(row.get(1).toString());
            News.setContent(row.get(2).toString());
            Newss.add(News);
        }
        newsService.saveBatch(Newss);
        return Result.success(true);
    }

    /**
     * 慈善文章导出接口
     */
    @GetMapping("/export2")
    public void export2(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<News> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","绿色文章");
        List<News> list = newsService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("author", "作者");
        writer.addHeaderAlias("content", "内容");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("慈善文章信息", "UTF-8");
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
        List<News> Newss = CollUtil.newArrayList();
        for (List<Object> row : list) {
            News News = new News();
            News.setTitle(row.get(0).toString());
            News.setAuthor(row.get(1).toString());
            News.setContent(row.get(2).toString());
            Newss.add(News);
        }
        newsService.saveBatch(Newss);
        return Result.success(true);
    }

    /**
     * 慈善文章导出接口
     */
    @GetMapping("/export3")
    public void export3(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        QueryWrapper<News> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","其他文章");
        List<News> list = newsService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("author", "作者");
        writer.addHeaderAlias("content", "内容");
        writer.addHeaderAlias("createtime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("慈善文章信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}


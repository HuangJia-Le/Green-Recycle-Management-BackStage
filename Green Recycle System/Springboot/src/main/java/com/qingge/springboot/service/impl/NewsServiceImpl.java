package com.qingge.springboot.service.impl;

import com.qingge.springboot.entity.News;
import com.qingge.springboot.mapper.NewsMapper;
import com.qingge.springboot.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-15
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}

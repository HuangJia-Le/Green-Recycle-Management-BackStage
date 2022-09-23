package com.qingge.springboot.service.impl;

import com.qingge.springboot.entity.Paper;
import com.qingge.springboot.mapper.PaperMapper;
import com.qingge.springboot.service.IPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-23
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

}

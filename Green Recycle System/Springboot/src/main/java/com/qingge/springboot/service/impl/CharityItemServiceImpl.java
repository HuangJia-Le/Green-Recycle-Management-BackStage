package com.qingge.springboot.service.impl;

import com.qingge.springboot.entity.CharityItem;
import com.qingge.springboot.mapper.CharityItemMapper;
import com.qingge.springboot.service.ICharityItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-18
 */
@Service
public class CharityItemServiceImpl extends ServiceImpl<CharityItemMapper, CharityItem> implements ICharityItemService {

}

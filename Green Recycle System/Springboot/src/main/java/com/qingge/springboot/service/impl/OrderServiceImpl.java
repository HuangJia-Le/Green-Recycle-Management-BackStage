package com.qingge.springboot.service.impl;

import com.qingge.springboot.entity.Order;
import com.qingge.springboot.mapper.OrderMapper;
import com.qingge.springboot.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}

package com.qingge.springboot.service.impl;

import com.qingge.springboot.entity.OrderState;
import com.qingge.springboot.mapper.OrderStateMapper;
import com.qingge.springboot.service.IOrderStateService;
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
public class OrderStateServiceImpl extends ServiceImpl<OrderStateMapper, OrderState> implements IOrderStateService {

}

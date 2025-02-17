package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderDetail;

import java.util.List;

public interface OrderService extends BaseService<Long, Order> {
    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);
    List<Order> findByUserId(Long userId);
}

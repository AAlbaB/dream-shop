package com.spring.dreamshop.service.order;

import com.spring.dreamshop.dto.OrderDto;
import com.spring.dreamshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}

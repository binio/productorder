package com.binio.productorder.service;

import java.util.Optional;

import com.binio.productorder.model.Order;
import com.binio.productorder.model.OrderApi;

public interface OrderService {

    Optional<OrderApi> addProduct(Long productId, final Long orderId);
    Optional<OrderApi> addOrder(Order order) throws OrderApiException;
    Optional<OrderApi> getOrderSummary(Order order);
}

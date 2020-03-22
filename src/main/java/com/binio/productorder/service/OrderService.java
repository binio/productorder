package com.binio.productorder.service;

import java.util.Optional;

import com.binio.productorder.model.OrderApi;
import com.binio.productorder.model.OrderCreateApi;

public interface OrderService {

    Optional<OrderApi> addProduct(Long productId, final Long orderId);
    Optional<OrderApi> addOrder(OrderCreateApi orderCreateApi) throws OrderApiException;
    Optional<OrderApi> getOrderSummary(Long orderId);
}

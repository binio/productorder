package com.binio.productorder.service;

import java.util.Optional;

import com.binio.productorder.model.Order;

public interface OrderService {

    Optional<Order> addProduct(Long productId);
    Optional<Order> addOrder(Order order) throws OrderApiException;
}

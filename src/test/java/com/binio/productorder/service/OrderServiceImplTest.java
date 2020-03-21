package com.binio.productorder.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.binio.productorder.model.Order;
import com.binio.productorder.model.Product;
import com.binio.productorder.repository.OrderRepository;
import com.binio.productorder.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void addProduct() {
        //when(productRepository.existsById(any())).thenReturn(true);
        //when(orderRepository.existsById(any())).thenReturn(true);
        //when(productRepository.findById(any())).thenReturn(getTestProduct());
        //when(orderRepository.findById(any())).thenReturn(getTestOrder());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            orderService.addProduct(1L,2L).get();
        });

    }

    private Optional<Order> getTestOrder() {
        return Optional.empty();
    }

    private Optional<Product> getTestProduct() {
        return Optional.empty();
    }

    @Test
    void addOrder() {
    }

    @Test
    void getOrderSummary() {
    }
}
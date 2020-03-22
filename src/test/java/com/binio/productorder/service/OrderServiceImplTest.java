package com.binio.productorder.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import com.binio.productorder.model.Order;
import com.binio.productorder.model.OrderApi;
import com.binio.productorder.model.OrderCreateApi;
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
        when(productRepository.existsById(any())).thenReturn(true);
        when(orderRepository.existsById(any())).thenReturn(true);
        when(productRepository.findById(any())).thenReturn(Optional.of(productToBeAdded()));
        when(orderRepository.findById(any())).thenReturn(getTestOrder());
        when(orderRepository.save(any())).thenReturn(updatedOrder());

        OrderApi order = orderService.addProduct(5L,1L).get();
        assertEquals(5, order.getProducts().size());
        assertEquals("51.25", order.getOrder_total().toString());

    }

    @Test
    void getOrderSummary() {
        when(orderRepository.existsById(any())).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(getTestOrder());

        OrderApi order = orderService.getOrderSummary(1L).get();
        assertEquals(4, order.getProducts().size());
        assertEquals("41.00", order.getOrder_total().toString());
    }

    @Test
    void addProduct_NoValueSet() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            orderService.addProduct(1L,2L).get();
        });
    }

    @Test
    void addOrder() throws Exception{
        OrderCreateApi order = OrderCreateApi.builder()
                .order_email("test@google.com")
                .build();
        when(orderRepository.save(any())).thenReturn(
                Order.builder()
                        .order_id(1L)
                        .order_email("test@google.com")
                        .products(new HashSet<>())
                        .build()
        );
        OrderApi orderApi = orderService.addOrder(order).get();
        assertEquals(0, orderApi.getProducts().size());
        assertEquals(1L, orderApi.getOrder_id());
        assertEquals("test@google.com", orderApi.getOrder_email());
    }

    private Product productToBeAdded() {
        return getTestProduct("test product 5",5L, new BigDecimal(10.25));
    }

    private Order updatedOrder() {
        Order order = getTestOrder().get();
        order.getProducts().add(productToBeAdded());
        return  order;
    }

    private Optional<Order> getTestOrder() {
        return Optional.of(Order.builder()
                .order_email("test@google.com")
                .order_id(1L)
                .products(getTestProducts())
                .build());
    }

    private Set<Product> getTestProducts() {
        Set<Product> products = new HashSet();
        products.add(getTestProduct("test product 1",1L, new BigDecimal(10.25)));
        products.add(getTestProduct("test product 2",2L, new BigDecimal(10.25)));
        products.add(getTestProduct("test product 3",3L, new BigDecimal(10.25)));
        products.add(getTestProduct("test product 4",4L, new BigDecimal(10.25)));
        return products;

    }

    private  Product getTestProduct(String name, Long id, BigDecimal price) {
        return Product.builder()
                .product_name(name)
                .product_price(price)
                .product_id(id)
                .build();
    }


}
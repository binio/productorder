package com.binio.productorder.api;

import java.util.Optional;

import com.binio.productorder.model.OrderAddProductApi;
import com.binio.productorder.model.OrderApi;
import com.binio.productorder.model.OrderCreateApi;
import com.binio.productorder.service.OrderApiException;
import com.binio.productorder.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/order")
@AllArgsConstructor
public class OrderController {

    OrderService orderService;

    @ApiOperation(value = "Creates Order in the database", response = OrderApi.class)
    @PostMapping(path="/create")
    public @ResponseBody
    ResponseEntity<OrderApi> createProduct(
            @ApiParam(value = "OrderCreateApi", required = true)
            @RequestBody OrderCreateApi orderCreateApi) {
        try {
            return ResponseEntity.of(orderService.addOrder(orderCreateApi));
        } catch (OrderApiException e) {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Adds product to order", response = OrderApi.class)
    @PutMapping(path="/product/add")
    public ResponseEntity<OrderApi> addProductToOrder(
            @ApiParam(value = "OrderAddProductApi", required = true)
            @RequestBody OrderAddProductApi orderAddProductApi) {
        return ResponseEntity.of(
                orderService.addProduct(
                        orderAddProductApi.getProduct_id(),orderAddProductApi.getOrder_id()
                ));
    }

    @ApiOperation(value = "Shows order content", response = OrderApi.class)
    @GetMapping(path="/id/{id}")
    public ResponseEntity<OrderApi> viewOrder(@PathVariable("id") Long id) {
        if(orderService.getOrderSummary(id).equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.of(orderService.getOrderSummary(id));
        }
    }
}

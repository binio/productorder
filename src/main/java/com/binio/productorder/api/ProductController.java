package com.binio.productorder.api;

import java.util.List;
import java.util.Optional;

import com.binio.productorder.model.ProductApi;
import com.binio.productorder.service.ProductApiException;
import com.binio.productorder.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "Product Store Api")
@Controller
@RequestMapping(path="/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "View a list of ProductApi which represent database entities Product", response = ProductApi.class)
    @GetMapping(path="/all")
    public @ResponseBody
    List<ProductApi> getAllProducts() {

        return productService.getAllProducts();
    }


    @ApiOperation(value = "View a ProductApi which represent database entity Product", response = ProductApi.class)
    @GetMapping(path="/id/{id}")
    public @ResponseBody
    ResponseEntity<ProductApi> getProduct(@PathVariable("id") Long id) {
        if(productService.getProductById(id).equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.of(productService.getProductById(id));
        }
    }

    @GetMapping(path="/sku/{sku}")
    public @ResponseBody
    ResponseEntity<ProductApi> getProduct(@PathVariable("sku") String sku) {
        if(productService.getProductBySku(sku).equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.of(productService.getProductBySku(sku));
        }
    }

    @ApiOperation(value = "Fetches Product from the database by SKU and DELETED", response = ProductApi.class)
    @GetMapping(path="/sku/{sku}/deleted/{deleted}")
    public @ResponseBody
    ResponseEntity<ProductApi> getProductBySkuAndDeleted(
            @ApiParam(value = "SKU code", required = true)
            @PathVariable("sku") String sku,
            @ApiParam(value = "DELETED boolean value 1 or 0", required = true)
            @PathVariable("deleted") Boolean deleted ) {
        if(productService.getProductBySkuAndProductDeleted(sku, deleted).equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

                return ResponseEntity.of(productService.getProductBySku(sku));
        }
    }

    @ApiOperation(value = "Updates Product in the database", response = ProductApi.class)
    @PostMapping("/update")
    ResponseEntity<ProductApi> updateProduct(@RequestBody ProductApi productApi) {
        return ResponseEntity.of(productService.updateProduct(productApi));
    }

    @ApiOperation(value = "Deletes Product from the database", response = Boolean.class)
    @DeleteMapping(path="/delete/{id}")
    ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        if(productService.deleteProduct(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Creates Product in the database", response = ProductApi.class)
    @PostMapping(path="/create")
    public @ResponseBody
    ResponseEntity<ProductApi> createProduct(
            @ApiParam(value = "ProductApi", required = true)
            @RequestBody ProductApi productApi) {
        try {
            return ResponseEntity.of(productService.createProduct(productApi));
        } catch (ProductApiException e) {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

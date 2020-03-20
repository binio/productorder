package com.binio.productorder.api;

import java.util.List;
import java.util.Optional;

import com.binio.productorder.model.ProductApi;
import com.binio.productorder.service.ProductService;
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

@Controller
@RequestMapping(path="/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path="/all")
    public @ResponseBody
    List<ProductApi> getAllProducts() {

        return productService.getAllProducts();
    }


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

    @GetMapping(path="/sku/{sku}/deleted/{deleted}")
    public @ResponseBody
    ResponseEntity<ProductApi> getProductBySkuAndDeleted(@PathVariable("sku") String sku,
                                                         @PathVariable("deleted") Boolean deleted) {
        if(productService.getProductBySkuAndProductDeleted(sku, deleted).equals(Optional.empty())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.of(productService.getProductBySku(sku));
        }
    }

    @PostMapping("/update")
    ResponseEntity<ProductApi> updateProduct(@RequestBody ProductApi productApi) {
        return ResponseEntity.of(productService.updateProduct(productApi));
    }

    @DeleteMapping(path="/delete/{id}")
    ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        if(productService.deleteProduct(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="/create")
    public @ResponseBody
    ResponseEntity<ProductApi> createProduct(@RequestBody ProductApi productApi) {
//        if(productService.getProductBySku(sku).equals(Optional.empty())){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return ResponseEntity.of(productService.getProductBySku(sku));
//        }
        return ResponseEntity.of(productService.createProduct(productApi));
    }


}

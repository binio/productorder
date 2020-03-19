package com.binio.productorder.service;

import java.util.List;
import java.util.Optional;

import com.binio.productorder.model.ProductApi;


public interface ProductService {
    List<ProductApi> getAllProducts();
    Optional<ProductApi> getProductById(Long id);
    Optional<ProductApi> getProductBySku(String sku);
    Optional<ProductApi> getProductBySkuAndProductDeleted(String sku, Boolean deleted);
    Optional<ProductApi> updateProduct(ProductApi productApi);
    Boolean deleteProduct(Long id);
}

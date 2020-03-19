package com.binio.productorder.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.binio.productorder.model.Product;
import com.binio.productorder.model.ProductApi;
import com.binio.productorder.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    public List<ProductApi> getAllProducts() {
        return productRepository.findAll().stream().map( p -> {
            ProductApi product =  ProductApi.builder()
                    .product_sku(p.getProductSku())
                    .product_price(p.getProduct_price())
                    .product_name(p.getProduct_name())
                    .product_id(p.getProduct_id())
                    .product_created_date(p.getProduct_created_date())
                    .build();
            return product;
        }).collect(Collectors.toList());
    }

    public Optional<ProductApi> getProductById(final Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.equals(Optional.empty())){
            return Optional.empty();
        } else {
            return convertProduct(product.get());
        }

    }

    public Optional<ProductApi> getProductBySku(final String sku) {
        Optional<Product> product = productRepository.findByProductSku(sku);

        if(product.equals(Optional.empty())){
            return Optional.empty();
        } else {
            return convertProduct(product.get());
        }

    }

    public Optional<ProductApi> getProductBySkuAndProductDeleted(final String sku, final Boolean deleted) {
        Optional<Product> product = productRepository.findByProductSkuAndProductDeleted(sku, deleted);

        if(product.equals(Optional.empty())){
            return Optional.empty();
        } else {
            return convertProduct(product.get());
        }

    }

    public Optional<ProductApi> updateProduct(ProductApi productApi) {
        Optional<Product> product = productRepository.findById(productApi.getProduct_id());
        if(product.equals(Optional.empty())){
            return Optional.empty();
        } else {
            Product p = product.get();
            p.setProduct_created_date(productApi.getProduct_created_date());
            p.setProduct_name(productApi.getProduct_name());
            p.setProduct_price(productApi.getProduct_price());
            p.setProductSku(productApi.getProduct_sku());
            productRepository.save(p);
            return convertProduct(p);
        }
    }

    public Boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.equals(Optional.empty())){
            return false;
        } else {
            Product p = product.get();
            p.setProductDeleted(true);
            if(!productRepository.save(p).equals(null)){
                return true;
            } else {
                return false;
            }

        }
    }

    private Optional<ProductApi> convertProduct(final Product product){
        return Optional.of(
                 ProductApi.builder()
                .product_created_date(product.getProduct_created_date())
                .product_id(product.getProduct_id())
                .product_name(product.getProduct_name())
                .product_price(product.getProduct_price())
                .product_sku(product.getProductSku())
                .build()
        );
    }
}

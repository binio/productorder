package com.binio.productorder.repository;

import java.util.Optional;

import com.binio.productorder.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductSkuAndProductDeleted(String sku, Boolean deleted);
    Optional<Product> findByProductSku(String sku);
}

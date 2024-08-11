package com.ststore.productCatalogService.ProductCatalogService.repositories;

import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long Id);
    List<Product> findAll();
    Product save(Product product);
    void deleteById(Long Id);
    Page<Product> findByName(String query, Pageable pageable);

}

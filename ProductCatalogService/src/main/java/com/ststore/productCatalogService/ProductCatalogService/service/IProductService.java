package com.ststore.productCatalogService.ProductCatalogService.service;

import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product deleteProduct(Long id);

    Product getProductBasedOnScope(Long pid, Long uid);
}

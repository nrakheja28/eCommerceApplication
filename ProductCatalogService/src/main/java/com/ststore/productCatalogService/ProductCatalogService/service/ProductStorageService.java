package com.ststore.productCatalogService.ProductCatalogService.service;

import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import com.ststore.productCatalogService.ProductCatalogService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ststore.productCatalogService.ProductCatalogService.dtos.UserDto;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductStorageService implements IProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {

        return productRepository.save(product);

    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product oldProduct = getProductById(id);
        if(oldProduct != null) {
            oldProduct.setName(product.getName());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategory(product.getCategory());
            oldProduct.setImageUrl(product.getImageUrl());
            return productRepository.save(oldProduct);
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product oldProduct = getProductById(id);
        if(oldProduct != null) {
            productRepository.deleteById(id);
        }
        return oldProduct;
    }

    @Override
    public Product getProductBasedOnScope(Long pid, Long uid) {
        Product product = productRepository.findById(pid).get();
        UserDto userDto = restTemplate.getForEntity("http://userauthservice/users/{uid}", UserDto.class, uid).getBody() ;
        if(userDto == null) {
            return null;
        }
        return product;
    }
}

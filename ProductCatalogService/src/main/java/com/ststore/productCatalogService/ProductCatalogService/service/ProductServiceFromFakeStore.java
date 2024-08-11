package com.ststore.productCatalogService.ProductCatalogService.service;

import com.ststore.productCatalogService.ProductCatalogService.clients.IClient;
import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreRequestDto;
import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreResponseDto;
import com.ststore.productCatalogService.ProductCatalogService.models.Category;
import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@Primary
public class ProductServiceFromFakeStore implements IProductService {

    @Autowired
    private IClient client;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Product getProductById(Long id){
        ProductFakestoreResponseDto productFakestoreResponseDto = (ProductFakestoreResponseDto) redisTemplate.opsForHash().get("products", id);
        if(productFakestoreResponseDto == null){
            productFakestoreResponseDto = client.getProductById(id);
            if(productFakestoreResponseDto == null) {
                return null;
            }
            redisTemplate.opsForHash().put("products", id, productFakestoreResponseDto);

        }
        return getProductFromProductFakestoreResponseDto(productFakestoreResponseDto);
    }

    @Override
    public List<Product> getAllProducts(){
        List<ProductFakestoreResponseDto> productFakestoreResponseDtos = client.getAllProducts();
        if(productFakestoreResponseDtos == null) {
            return null;
        }
        List<Product> products = new ArrayList<>();
        for(ProductFakestoreResponseDto productFakestoreResponseDto : productFakestoreResponseDtos) {
            products.add(getProductFromProductFakestoreResponseDto(productFakestoreResponseDto));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        ProductFakestoreRequestDto productFakestoreRequestDto = getProductFakestoreRequestDtoFromProduct(product);
        ProductFakestoreResponseDto productFakestoreResponseDto = client.createProduct(productFakestoreRequestDto);
        if(productFakestoreResponseDto == null) {
            return null;
        }
        return getProductFromProductFakestoreResponseDto(productFakestoreResponseDto);
    }

    @Override
    public Product updateProduct(Long id, Product product){
        ProductFakestoreRequestDto productFakestoreRequestDto = getProductFakestoreRequestDtoFromProduct(product);
        ProductFakestoreResponseDto responseDto = client.updateProduct(id, productFakestoreRequestDto);
        if(responseDto == null) return null;
        return getProductFromProductFakestoreResponseDto(responseDto);
    }

    @Override
    public Product deleteProduct(Long id){
        ProductFakestoreResponseDto productFakestoreResponseDto = client.deleteProduct(id);
        if(productFakestoreResponseDto == null) {
            return null;
        }
        return getProductFromProductFakestoreResponseDto(productFakestoreResponseDto);
    }

    @Override
    public Product getProductBasedOnScope(Long pid, Long uid) {
        return null;
    }


    private Product getProductFromProductFakestoreResponseDto(ProductFakestoreResponseDto productFakestoreResponseDto){
        Product product = new Product();
        product.setId(productFakestoreResponseDto.getId());
        Category category = new Category();
        category.setName(productFakestoreResponseDto.getCategory());
        product.setCategory(category);
        product.setName(productFakestoreResponseDto.getTitle());
        product.setPrice(productFakestoreResponseDto.getPrice());
        product.setDescription(productFakestoreResponseDto.getDescription());
        product.setImageUrl(productFakestoreResponseDto.getImage());
        product.setRating(productFakestoreResponseDto.getRating().getRate());
        return product;

    }

    private ProductFakestoreRequestDto getProductFakestoreRequestDtoFromProduct(Product product){
        ProductFakestoreRequestDto productFakestoreRequestDto = new ProductFakestoreRequestDto();
        productFakestoreRequestDto.setDescription(product.getDescription());
        productFakestoreRequestDto.setPrice(product.getPrice());
        productFakestoreRequestDto.setTitle(product.getName());
        productFakestoreRequestDto.setImage(product.getImageUrl());
        productFakestoreRequestDto.setCategory(product.getCategory().getName());
        return productFakestoreRequestDto;
    }
}

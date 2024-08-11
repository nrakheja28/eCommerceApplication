package com.ststore.productCatalogService.ProductCatalogService.controllers;

import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductRequestDto;
import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductResponseDto;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidProductBody;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidProductID;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.NoProductFound;
import com.ststore.productCatalogService.ProductCatalogService.models.Category;
import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import com.ststore.productCatalogService.ProductCatalogService.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iproductService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) {
        if(id <= 0){
            throw new InvalidProductID();
        }
        Product product = iproductService.getProductById(id);
        if(product == null){
            throw new NoProductFound("No Product found with id: " + id);
        }
        ProductResponseDto productResponseDto = getProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto){
        try{
            Product product = getProduct(productRequestDto);
            Product returnedProduct = iproductService.createProduct(product);
            if(returnedProduct == null){
                throw new InvalidProductBody();
            }
            ProductResponseDto productResponseDto = getProductResponseDto(returnedProduct);
            return new ResponseEntity<>(productResponseDto, HttpStatusCode.valueOf(201));
        }
        catch (NullPointerException e){
            throw new InvalidProductBody();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<Product> response = iproductService.getAllProducts();
        if(response == null){
            throw new NoProductFound();
        }
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for(Product product : response){
            ProductResponseDto productResponseDto = getProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return new ResponseEntity<>(productResponseDtoList, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long id){
        if(id <= 0){
            throw new InvalidProductID();
        }
        Product deletedProduct = iproductService.deleteProduct(id);
        if(deletedProduct == null){
            throw new NoProductFound("No Product found with id: " + id);
        }
        ProductResponseDto productResponseDto = getProductResponseDto(deletedProduct);
        return new ResponseEntity<>(productResponseDto, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable Long id){
        if(id <= 0){
            throw new InvalidProductID();
        }
        try {
            Product product = getProduct(productRequestDto);
            Product modifiedProduct = iproductService.updateProduct(id, product);
            if(modifiedProduct == null){
                throw new NoProductFound("No Product found with id: " + id);
            }
            ProductResponseDto productResponseDto = getProductResponseDto(modifiedProduct);
            return new ResponseEntity<>(productResponseDto, HttpStatusCode.valueOf(200));
        }
        catch(NullPointerException e){
                throw new InvalidProductBody();
        }
    }

    @GetMapping("/{pid}/{uid}")
    public ProductResponseDto getProductBasedOnScope(@PathVariable Long pid, @PathVariable Long uid){
        Product product = iproductService.getProductBasedOnScope(pid, uid);
        return getProductResponseDto(product);
    }


    private ProductResponseDto getProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        if(product.getCategory() != null){
            productResponseDto.setCategory(product.getCategory().getName());
        }
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setRating(product.getRating());
        productResponseDto.setImageUrl(product.getImageUrl());
        return productResponseDto;

    }

    private Product getProduct(ProductRequestDto productRequestDto){
        Product product = new Product();
        if(productRequestDto.getName() != null) {
            product.setName(productRequestDto.getName());
        }
        else{
            throw new IllegalArgumentException("Invalid product name");
        }
        Category category = new Category();
        category.setName(productRequestDto.getCategory());
        product.setCategory(category);
        product.setDescription(productRequestDto.getDescription());
        if(productRequestDto.getPrice() != null && productRequestDto.getPrice() > 0) {
            product.setPrice(productRequestDto.getPrice());
        }
        else{
            throw new IllegalArgumentException("Invalid product price");
        }
        product.setImageUrl(productRequestDto.getImageUrl());
        return product;
    }
}

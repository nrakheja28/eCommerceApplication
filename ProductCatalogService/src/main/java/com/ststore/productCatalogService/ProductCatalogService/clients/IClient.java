package com.ststore.productCatalogService.ProductCatalogService.clients;

import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreRequestDto;
import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreResponseDto;

import java.util.List;

public interface IClient {
    ProductFakestoreResponseDto getProductById(Long id);

    List<ProductFakestoreResponseDto> getAllProducts();

    ProductFakestoreResponseDto createProduct(ProductFakestoreRequestDto productFakestoreRequestDto);

    ProductFakestoreResponseDto deleteProduct(Long id);

    ProductFakestoreResponseDto updateProduct(Long id, ProductFakestoreRequestDto productFakestoreRequestDto);
}

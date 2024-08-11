package com.ststore.productCatalogService.ProductCatalogService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private String name;
    private String category;
    private String description;
    private Double price;
    private String imageUrl;
    private Double rating;
}

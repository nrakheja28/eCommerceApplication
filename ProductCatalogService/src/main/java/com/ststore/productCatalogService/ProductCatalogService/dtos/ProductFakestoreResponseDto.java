package com.ststore.productCatalogService.ProductCatalogService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductFakestoreResponseDto implements Serializable {
    private String category;
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private RatingFakestoreResponseDto rating = new RatingFakestoreResponseDto();
}

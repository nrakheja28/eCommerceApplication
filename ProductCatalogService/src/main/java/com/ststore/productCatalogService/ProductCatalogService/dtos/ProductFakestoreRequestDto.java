package com.ststore.productCatalogService.ProductCatalogService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductFakestoreRequestDto implements Serializable {
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
}

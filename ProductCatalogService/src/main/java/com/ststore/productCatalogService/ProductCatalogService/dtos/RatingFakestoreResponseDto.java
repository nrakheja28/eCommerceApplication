package com.ststore.productCatalogService.ProductCatalogService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RatingFakestoreResponseDto implements Serializable {
    private Double rate = 3.0;
    private Long count = 0L;
}

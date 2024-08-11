package com.ststore.productCatalogService.ProductCatalogService.dtos;

import com.ststore.productCatalogService.ProductCatalogService.enums.SortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortDto {
    private String parameterName;
    private SortType sortType;
}

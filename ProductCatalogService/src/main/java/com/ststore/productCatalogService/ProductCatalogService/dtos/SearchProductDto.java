package com.ststore.productCatalogService.ProductCatalogService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchProductDto {
    private String query;
    private int page;
    private int size;
    private List<SortDto> sorts = new ArrayList<>();
}

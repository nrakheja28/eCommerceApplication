package com.ststore.productCatalogService.ProductCatalogService.service;

import com.ststore.productCatalogService.ProductCatalogService.dtos.SortDto;
import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductSearchService {
    Page<Product> searchProduct(String query, int page, int size, List<SortDto> sortDtos);
}

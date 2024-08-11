package com.ststore.productCatalogService.ProductCatalogService.service;

import com.ststore.productCatalogService.ProductCatalogService.dtos.SortDto;
import com.ststore.productCatalogService.ProductCatalogService.enums.SortType;
import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import com.ststore.productCatalogService.ProductCatalogService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService implements IProductSearchService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> searchProduct(String query, int page, int size, List<SortDto> sortDtos) {

        Sort sort = Sort.by("name");
        if(sortDtos != null && !sortDtos.isEmpty()) {
            if ( sortDtos.get(0).getSortType() == null || !sortDtos.get(0).getSortType().equals(SortType.DESC)) {
                sort = Sort.by(sortDtos.get(0).getParameterName());
            } else {
                sort = Sort.by(sortDtos.get(0).getParameterName()).descending();
            }
            for(int i = 1; i < sortDtos.size(); i++){
                if(sortDtos.get(i).getSortType() == null || !sortDtos.get(i).getSortType().equals(SortType.DESC)){
                    sort.and(Sort.by(sortDtos.get(i).getParameterName()));
                }
                else{
                    sort.and(Sort.by(sortDtos.get(i).getParameterName()).descending());
                }
            }
        }

        if(size == 0){
            size = 5;
        }

        return productRepository.findByName(query, PageRequest.of(page, size, sort));
    }
}

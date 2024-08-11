package com.ststore.productCatalogService.ProductCatalogService.controllers;


import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductResponseDto;
import com.ststore.productCatalogService.ProductCatalogService.dtos.SearchProductDto;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidSearchQuery;
import com.ststore.productCatalogService.ProductCatalogService.models.Product;
import com.ststore.productCatalogService.ProductCatalogService.service.IProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class ProductSearchController {

    @Autowired
    private IProductSearchService iProductSearchService;


    @PostMapping
    public Page<ProductResponseDto> searchProduct(@RequestBody SearchProductDto searchProductDto) {
        if(searchProductDto.getQuery() == null){
            throw new InvalidSearchQuery();
        }
        Page<Product> response = iProductSearchService.searchProduct(searchProductDto.getQuery(), searchProductDto.getPage(), searchProductDto.getSize(), searchProductDto.getSorts());

        return response.map(ProductSearchController::getProductResponseDto);
    }

    private static ProductResponseDto getProductResponseDto(Product product){
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

}

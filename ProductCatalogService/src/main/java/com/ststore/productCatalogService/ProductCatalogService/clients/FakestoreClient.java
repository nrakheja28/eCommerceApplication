package com.ststore.productCatalogService.ProductCatalogService.clients;

import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreRequestDto;
import com.ststore.productCatalogService.ProductCatalogService.dtos.ProductFakestoreResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakestoreClient implements IClient {

    @Autowired
    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @Override
    public ProductFakestoreResponseDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductFakestoreResponseDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", ProductFakestoreResponseDto.class, id);
        return response.getBody();
    }

    @Override
    public List<ProductFakestoreResponseDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ProductFakestoreResponseDto[] response = restTemplate.getForEntity("https://fakestoreapi.com/products", ProductFakestoreResponseDto[].class).getBody();
        if (response == null){
            return null;
        }
        return new ArrayList<>(Arrays.asList(response));
    }

    @Override
    public ProductFakestoreResponseDto createProduct(ProductFakestoreRequestDto productFakestoreRequestDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductFakestoreResponseDto> response = restTemplate.postForEntity("https://fakestoreapi.com/products", productFakestoreRequestDto, ProductFakestoreResponseDto.class);
        return response.getBody();
    }

    @Override
    public ProductFakestoreResponseDto deleteProduct(Long id){
        if(getProductById(id) == null){
            return null;
        }
        return deleteForEntity("https://fakestoreapi.com/products/{id}", ProductFakestoreResponseDto.class, id).getBody();
    }

    @Override
    public ProductFakestoreResponseDto updateProduct(Long id, ProductFakestoreRequestDto productFakestoreRequestDto) {
        if(getProductById(id) == null){
            return null;
        }
        ResponseEntity<ProductFakestoreResponseDto> response = putForEntity("https://fakestoreapi.com/products/{id}", productFakestoreRequestDto, ProductFakestoreResponseDto.class, id);
        return response.getBody();
    }


    private <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    public <T> ResponseEntity<T> deleteForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
    }
}

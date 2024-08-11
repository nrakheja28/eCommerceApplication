package com.ststore.productCatalogService.ProductCatalogService.controllers;

import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidProductBody;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidProductID;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.InvalidSearchQuery;
import com.ststore.productCatalogService.ProductCatalogService.exceptions.NoProductFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler({NoProductFound.class, NullPointerException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({InvalidProductID.class, InvalidProductBody.class, IllegalArgumentException.class, InvalidSearchQuery.class})
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RestClientException.class})
    public ResponseEntity<String> RestClientException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FAILED_DEPENDENCY);
    }
}

package com.ststore.productCatalogService.ProductCatalogService.exceptions;

public class InvalidSearchQuery extends RuntimeException{
    public InvalidSearchQuery() {
        super("Invalid Search Query");
    }
}

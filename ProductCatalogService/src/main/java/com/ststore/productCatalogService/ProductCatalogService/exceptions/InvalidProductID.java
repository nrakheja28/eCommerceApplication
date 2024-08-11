package com.ststore.productCatalogService.ProductCatalogService.exceptions;

public class InvalidProductID extends RuntimeException{
    public InvalidProductID(){
        super("Invalid Product ID");
    }
}

package com.ststore.productCatalogService.ProductCatalogService.exceptions;

public class NoProductFound extends RuntimeException{
    public NoProductFound(){
        super("No Product Found");
    }
    public NoProductFound(String message){
        super(message);
    }
}

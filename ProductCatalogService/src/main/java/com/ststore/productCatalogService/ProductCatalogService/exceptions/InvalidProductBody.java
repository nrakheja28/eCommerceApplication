package com.ststore.productCatalogService.ProductCatalogService.exceptions;

public class InvalidProductBody extends RuntimeException{
    public InvalidProductBody(){
        super("Invalid Product Parameters, Please make sure all the fields are valid");
    }
}

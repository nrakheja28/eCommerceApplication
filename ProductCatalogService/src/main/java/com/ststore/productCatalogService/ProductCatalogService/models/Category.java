package com.ststore.productCatalogService.ProductCatalogService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name = "NA";
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}

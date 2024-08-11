package com.ststore.productCatalogService.ProductCatalogService.models;

import com.ststore.productCatalogService.ProductCatalogService.enums.EntityState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    @Enumerated(EnumType.ORDINAL)
    private EntityState state;
}

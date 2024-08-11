package com.ststore.userauthservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String username;
    private String password;
    @ManyToMany
    @Cascade(CascadeType.ALL)
//    @JsonManagedReference
    private List<Role> roles;
}
package com.ststore.userauthservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Role extends BaseModel {
    private String role;

    @ManyToMany(mappedBy = "roles")
//    @JsonBackReference
    private List<User> users;
}

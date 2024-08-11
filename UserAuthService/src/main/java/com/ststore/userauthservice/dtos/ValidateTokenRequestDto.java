package com.ststore.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequestDto {
    String token;
    String username;
}

package com.ststore.emailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {

    private String from;
    private String to;
    private String subject;
    private String body;
}

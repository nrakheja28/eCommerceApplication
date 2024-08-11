package com.ststore.emailservice.consumer;

import java.util.Properties;
import com.ststore.emailservice.utils.EmailUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ststore.emailservice.dtos.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "signup", groupId = "emailService")
    public void sendEmail(String message) {
        try {
            MailDto mailDto = objectMapper.readValue(message, MailDto.class);
            String fromEmail = "nrakheja28@gmail.com"; //requires valid gmail id
            String password = "ixwinozpxfsmmnqy"; // correct password for gmail id
            String toEmail = mailDto.getTo();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
            props.put("mail.smtp.port", "465"); //SMTP Port

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getDefaultInstance(props, auth);
            System.out.println("Session created");
            EmailUtil.sendEmail(session, toEmail, mailDto.getSubject(), mailDto.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}

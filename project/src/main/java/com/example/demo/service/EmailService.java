package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.po.Post;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("您的一次性驗證碼為: " + text);
        emailSender.send(message);
    }
    @Async
    public void sendNewstoMail(String to, String subject, String text) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text+"\n"+"http://localhost:8082/page/testpage.html");
        
        emailSender.send(message);
    }
}

package ru.almaz.dailycalorieintake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    private void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendVerifyLink(String userEmail, String uuid) {
        String subject = "Подтверждение адреса электронной почты";
        String text = "http://localhost:8085/auth/verify/" + uuid;
        sendMessage(userEmail, subject, text);
    }

}

package com.mepro.democrud.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MailerService {
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendFileByEmail(String to,
                                String subject,
                                String body,
                                String uploadFolder,
                                String fileId,
                                String originalFilename) throws MessagingException, IOException {
        File file = new File(uploadFolder + File.separator + fileId);
        if (!file.exists()) {
            throw new IOException("File tidak ditemukan: " + file.getAbsolutePath());
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        InputStreamSource attachmentSource = () -> new FileInputStream(file);
        helper.addAttachment(originalFilename, attachmentSource);
        mailSender.send(message);
    }
    
    
    public void sendEmailWithUploadedFile(String to, String subject, String body,
                                          MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.addAttachment (
                file.getOriginalFilename(),
                new ByteArrayResource(file.getBytes()),
                file.getContentType());
        mailSender.send(message);
    }
}

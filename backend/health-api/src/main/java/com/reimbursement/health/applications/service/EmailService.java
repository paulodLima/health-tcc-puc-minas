package com.reimbursement.health.applications.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private Environment env;
    private final JavaMailSender emailSender;
    private final String url = "http://localhost:4200/reset/";

    @Value("${spring.mail.username}")
    private String email;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendResetPasswordCreatedMessage(String to, String token, String name) {
        var subject = env.getProperty("email.subject.account-created");
        var bodyTemplate = env.getProperty("email.body.account-created");
        assert bodyTemplate != null;
        var body = bodyTemplate
                .replace("{user}", name)
                .replace("{resetPasswordLink}", url.concat(token));


        sendEmail(to, subject, body);
    }
    public void sendMessageOk(String to, String name) {
        var subject = env.getProperty("email-status-ok.subjects");
        var bodyTemplate = env.getProperty("email-status-ok.body");
        assert bodyTemplate != null;
        var body = bodyTemplate
                .replace("{user}", name);

        sendEmail(to, subject, body);
    }

    public void sendMessageRejecterd(String to, String name,String observation) {
        var subject = env.getProperty("email-status-rejected.subjects");
        var bodyTemplate = env.getProperty("email-status-rejected.body");
        assert bodyTemplate != null;
        var body = bodyTemplate
                .replace("{user}", name)
                .replace("{observation}",observation);

        sendEmail(to, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom(email);
            helper.setTo(to);
            assert subject != null;
            helper.setSubject(subject);

            String htmlContent = "<html><body>"
                    + "<p>" + body + "</p>"
                    + "<img src='cid:image001' />"
                    + "</body></html>";

            helper.setText(htmlContent, true);

            ClassPathResource image = new ClassPathResource("static/img-reembolso.png");
            helper.addInline("image001", image);

            emailSender.send(message);
            log.error("Email enviado com sucesso!");
        } catch (MessagingException e) {
            log.error("Falha ao enviar e-mail: {}", e.getMessage());
        }
    }

    public void sendResetPasswordMessage(String email, String token, String name) {
        var subject = env.getProperty("email.subject.account-created");
        var bodyTemplate = env.getProperty("email.body.account-reset");
        assert bodyTemplate != null;
        var body = bodyTemplate
                .replace("{user}", name)
                .replace("{resetPasswordLink}", url.concat(token));


        sendEmail(email, subject, body);
    }
}

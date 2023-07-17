package de.merkeg.vsrentbe.mail;

import de.merkeg.vsrentbe.confirmation.Confirmation;
import de.merkeg.vsrentbe.confirmation.ConfirmationService;
import de.merkeg.vsrentbe.user.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final ConfirmationService confirmationService;

    @Value("${spring.mail.from}")
    private String fromEmail;


    public void sendEmailFromTemplate(String to, EmailTemplate template, EmailVariables variables){
        sendEmailFromTemplate(to, template.getDefaultSubject(), template, variables);
    }

    public void sendEmailFromTemplate(String to, String subject, EmailTemplate template, EmailVariables variables) {
        Context context = new Context();
        context.setVariables(variables.toMap());
        log.debug("Loading thymeleaf template from path: {}", template.getPath());
        String htmlBody = templateEngine.process(template.getPath(), context);

        sendHTMLMessage(to, subject, htmlBody);
    }

    @SneakyThrows
    public void sendHTMLMessage(String to, String subject, String htmlBody) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

        helper.setText(htmlBody, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(fromEmail);

        emailSender.send(mimeMessage);
    }

    @Async("emailAsyncExecutor")
    public void sendConfirmationEmail(User user) {
        Confirmation emailConfirmation = confirmationService.createEmailConfirmation(user);
        EmailVariables emailVariables = EmailVariables.builder()
                .registrationUrl(confirmationService.createAbsoluteUri(emailConfirmation).toString())
                .name(user.getFullName())
                .build();
        sendEmailFromTemplate(user.getEmail(), EmailTemplate.MAIL_REGISTRATION, emailVariables);
    }

}

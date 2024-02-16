package com.ishanitech.ipasal.ipasalwebservice.Services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ishanitech.ipasal.ipasalwebservice.dto.EmailDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration freeMarkerConfiguration;

    public void sendAccountActivationEmail(EmailDTO email, String reset) throws MessagingException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        
        try {
            Template template;
            if (reset.equalsIgnoreCase("pw")){
                template = freeMarkerConfiguration.getTemplate("password-reset-email.ftl");
            }else if (reset.equalsIgnoreCase("wholeseller")){
                template = freeMarkerConfiguration.getTemplate("whole-seller.ftl");
            }else if (reset.equalsIgnoreCase("neworder")){
                template = freeMarkerConfiguration.getTemplate("neworder-email.ftl");
            }
            else{
                template = freeMarkerConfiguration.getTemplate("email.ftl");
            }

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getModel());
            messageHelper.setTo(InternetAddress.parse(email.getTo()));
            messageHelper.setFrom(email.getFrom());
            messageHelper.setText(html, true);
            messageHelper.setSubject(email.getSubject());
            messageHelper.addInline("logo.png", new ClassPathResource("logo.png"));
            javaMailSender.send(mimeMessage);

        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
package com.nidhi.cms.service.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.nidhi.cms.config.MailConfig;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.domain.email.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private MailConfig mailConfig;
	
	@Deprecated
	@SuppressWarnings("unused")
	public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

			Template t = config.getTemplate("");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(mailConfig.getFrom());
			sender.send(message);

			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
	
	public MailResponse sendEmail(MailRequest request, Map<String, Object> model, String attachmentName, String templateName) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			if (StringUtils.isNotBlank(attachmentName)) {
				helper.addAttachment(attachmentName, new ClassPathResource(attachmentName));
			}

			Template template = config.getTemplate(templateName);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			addRecipients(request, helper);
			helper.setText(html, true);
			helper.setSubject(request.getSubject());
			helper.setFrom(new InternetAddress(mailConfig.getFrom(), "cms support"));
			sender.send(message);

			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (Exception e) {
			System.out.println("Mail Sending failure : "+e.getMessage());
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
	
	public void sendMailAsync(MailRequest request, Map<String, Object> modal, String attach, String templateName) {
		CompletableFuture.runAsync(() -> 
			CompletableFuture.runAsync(() -> sendEmail(request, modal, attach, templateName))
		 );
	}
	
	
	private void addRecipients(MailRequest request, MimeMessageHelper helper) throws MessagingException {
		if (request.getTo() != null) {
			helper.setTo(request.getTo());
		}
		if (request.getCc() != null) {
			helper.setCc(request.getCc());
		}
		if (request.getBcc() != null) {
			helper.setBcc(request.getBcc());
		}
		
	}
	

}
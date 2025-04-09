package com.cgpi.cgpi.services;



import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {
	

	    @Autowired
	    private JavaMailSender mailSender;

	    // ✅ Send Order Status Update Email
	    public void sendEmail(String toEmail, String subject, String body) {
	        try {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(toEmail);
	            message.setSubject(subject);
	            message.setText(body);

	            mailSender.send(message);
	            System.out.println("Email sent to: " + toEmail);
	        } catch (Exception e) {
	            System.err.println("Error sending email: " + e.getMessage());
	        }
	    }

	    // ✅ Send Invoice Email with PDF (Base64)
	    public void sendInvoiceEmail(String toEmail, String pdfBase64, String orderId) {
	        try {
	            // Convert Base64 string to PDF file
	            byte[] decodedBytes = Base64.getDecoder().decode(pdfBase64);
	            File pdfFile = new File("invoice_" + orderId + ".pdf");
	            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
	                fos.write(decodedBytes);
	            }

	            // Create Email with PDF Attachment
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setTo(toEmail);
	            helper.setSubject("Your Order Invoice - Kitchenary Kart");
	            helper.setText("Thank you for your order! Please find your invoice attached.");

	            FileSystemResource file = new FileSystemResource(pdfFile);
	            helper.addAttachment("Invoice_" + orderId + ".pdf", file);

	            mailSender.send(message);
	            System.out.println("Invoice email sent to: " + toEmail);
	        } catch (Exception e) {
	            System.err.println("Error sending invoice email: " + e.getMessage());
	        }
	    }

	    // ✅ Send OTP Email
//	    public void sendOtpEmail(String toEmail, int otp) {
//	        try {
//	            SimpleMailMessage message = new SimpleMailMessage();
//	            message.setTo(toEmail);
//	            message.setSubject("Password Reset OTP");
//	            message.setText("Your OTP for password reset is: " + otp);
//
//	            mailSender.send(message);
//	            System.out.println("OTP sent to: " + toEmail);
//	        } catch (Exception e) {
//	            System.err.println("Error sending OTP email: " + e.getMessage());
//	        }
//	    }
	    public void sendOtpEmail(String toEmail, int otp) {
	        try {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(toEmail);
	            message.setSubject("Password Reset OTP");

	            String emailBody = "Dear User,\n\n"
	                    + "We received a request to reset your password. Please use the OTP below to proceed:\n\n"
	                    + "OTP: " + otp + "\n\n"
	                    + "This OTP is valid for 10 minutes. Do not share it with anyone.\n"
	                    + "If you did not request a password reset, please ignore this email or contact our support team.\n\n"
	                    + "Best regards,\n"
	                    + "Your Company Name\n"
	                    + "Support Team";

	            message.setText(emailBody);

	            mailSender.send(message);
	            System.out.println("OTP sent to: " + toEmail);
	        } catch (Exception e) {
	            System.err.println("Error sending OTP email: " + e.getMessage());
	        }
	    }
	}
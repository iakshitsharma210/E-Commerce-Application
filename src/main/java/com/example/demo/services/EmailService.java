package com.example.demo.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Order;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendOrderConfirmation(String toEmail, Order order) {
        String subject = "Order Confirmation - #" + order.getId();
        String body = "Dear Customer,\n\n" +
                      "Thank you for placing your order!\n\n" +
                      "Order Details:\n" +
                      "Order ID: " + order.getId() + "\n" +
                      "Description: " + order.getDescription() + "\n" +
                      "Address: " + order.getAddress() + "\n" +
                      "Mobile: " + order.getMobile() + "\n" +
                      "Amount: " + order.getPrice() + "\n" +
                      "Payment Type: " + order.getPaymentType() + "\n" +
                      "Date: " + order.getDate() + "\n\n" +
                      "We will contact you shortly with further updates.\n\n" +
                      "Best regards,\n" +
                      "Your Company Team";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("your_email@gmail.com"); // replace with your actual Gmail

            System.out.println("Sending email to: " + toEmail);
            mailSender.send(message);
            System.out.println("Email sent successfully!");
            return true;

        } catch (MailException e) {
            System.out.println("Failed to send email to: " + toEmail);
            e.printStackTrace(); // prints full stack trace to console
            return false;
        }
    }
}

package com.example.fxproject.controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Properties;

public class EmailController {

    public TextField txtEmailTo;
    public TextField txtSubject;
    public TextArea Description;

    public void btnsendEmail(ActionEvent actionEvent) {

        String to = txtEmailTo.getText().trim();
        String subject = txtSubject.getText().trim();
        String messageText = Description.getText().trim();

        final String fromEmail = "rathnayakaisuru422@gmail.com";
        final String password = "cxzj ajub oddu mwly";

        // SMTP Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageText);

            // Send the email
            Transport.send(message);

            // Show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Email Status");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Email sent successfully to " + to);
            successAlert.showAndWait();

        } catch (MessagingException e) {
            e.printStackTrace();

            // Show error alert
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Email Status");
            errorAlert.setHeaderText("Email sending failed");
            errorAlert.setContentText("Please check the email address, internet connection, and credentials.");
            errorAlert.showAndWait();
        }
    }
}

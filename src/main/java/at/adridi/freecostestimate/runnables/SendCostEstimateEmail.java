/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.runnables;

import at.adridi.freecostestimate.util.EmailAuthenticator;
import at.adridi.freecostestimate.util.EmailSettings;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Background thread runnable that sends cost estimate emails.
 *
 * @author A.Dridi
 */
public class SendCostEstimateEmail implements Runnable {

    private String emailAddress;
    private String emailText;

    public SendCostEstimateEmail(String emailAddress, String emailText) {
        this.emailAddress = emailAddress;
        this.emailText = emailText;
    }

    @Override
    public void run() {
        String result = sendCostEstimateEmail(this.emailAddress, this.emailText);
        if (result.equals("")) {
            System.out.println("Cost Estimate email was sent to: " + this.emailAddress);
        } else {
            System.out.println(result);
        }
    }

    /**
     * Send CostEstimate as an email with the defined settings in the util class
     * "EmailSettings".
     *
     * @param emailAddress The email address that will get the CostEstimate
     * email.
     * @param emailText The content of the costEstimate email.
     * @return Method returns an empty string, if email was sent successfully.
     * Otherwise the error message is returned and it is also printed out (log).
     */
    public String sendCostEstimateEmail(String emailAddress, String emailText) {
        Properties emailProperties = System.getProperties();
        emailProperties.setProperty("mail.smtp.host", EmailSettings.SETTINGS_SMTP_HOST);
        switch (EmailSettings.SETTINGS_SMTP_PORT) {
            case 465:
                emailProperties.setProperty("mail.smtp.port", "465");
                emailProperties.setProperty("mail.smtp.auth", "true");
                emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                break;
            case 587:
                emailProperties.setProperty("mail.smtp.starttls.enable", "true");
                emailProperties.setProperty("mail.smtp.auth", "true");
                break;
            default:
                emailProperties.setProperty("mail.smtp.port", "25");
                emailProperties.setProperty("mail.smtp.auth", "true");
                emailProperties.setProperty("mail.smtp.starttls.enable", "false");
        }

        EmailAuthenticator costEstimateEmailAuthenticator = new EmailAuthenticator();
        PasswordAuthentication costEstimateEmailPasswordAuthentication = costEstimateEmailAuthenticator.getPasswordAuthentication();
        if (costEstimateEmailPasswordAuthentication == null) {
            return "ERROR: No email address or email password was set in the util class EmailSettings";
        }

        //Create (mail) session to send an email
        Lock emailSessionLock = new ReentrantLock();
        Session emailSession = null;
        try {
            emailSessionLock.lockInterruptibly();
            emailSession = Session.getDefaultInstance(emailProperties, costEstimateEmailAuthenticator);
        } catch (Exception e) {
            Logger.getLogger(SendCostEstimateEmail.class.getName()).log(Level.SEVERE, null, e);
            return "EMAIL SESSION COULD NOT BE CREATED: " + e.getCause() + " - " + e.getMessage();
        } finally {
            emailSessionLock.unlock();
        }

        try {
            Message costEstimateMessage = new MimeMessage(emailSession);
            costEstimateMessage.setFrom(new InternetAddress(EmailSettings.SETTINGS_SENDER_EMAIL));
            costEstimateMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            costEstimateMessage.setSubject(EmailSettings.EMAIL_SUBJECT);
            //Create email message parts
            Multipart messageMultiParts = new MimeMultipart();
            //Create body of email message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(emailText);
            messageMultiParts.addBodyPart(messageBodyPart);
            costEstimateMessage.setContent(messageMultiParts);
            Transport.send(costEstimateMessage);
            return "";
        } catch (Exception e) {
            Logger.getLogger(SendCostEstimateEmail.class.getName()).log(Level.SEVERE, null, e);
            return "SENDING EMAIL FAILED: " + ": " + e.getCause() + " - " + e.getMessage();
        }

    }

}

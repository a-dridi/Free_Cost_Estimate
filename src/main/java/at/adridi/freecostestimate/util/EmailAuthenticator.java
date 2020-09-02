/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * Authenticator class for sending CostEstimate emails. Uses email and password
 * from util class "EmailSettings".
 *
 * @author A.Dridi
 */
public class EmailAuthenticator extends Authenticator {

    public EmailAuthenticator() {
        super();
    }

    /**
     * Returns null if passed email or password is null or an empty string.
     *
     * @return
     */
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        if (EmailSettings.SETTINGS_SENDER_EMAIL == null || EmailSettings.SETTINGS_SENDER_EMAIL.trim().isEmpty() || EmailSettings.SETTINGS_EMAIL_PASSWORD == null || EmailSettings.SETTINGS_EMAIL_PASSWORD.trim().isEmpty()) {
            return null;
        }
        return new PasswordAuthentication(EmailSettings.SETTINGS_SENDER_EMAIL, EmailSettings.SETTINGS_EMAIL_PASSWORD);
    }

}

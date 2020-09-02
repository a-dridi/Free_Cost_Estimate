/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.util;

/**
 * You can configure here your email settings.
 *
 * @author A.Dridi
 */
public class EmailSettings {

    //SMTP Server name. Example: mail.gmx.net
    public static String SETTINGS_SMTP_HOST = "mailserver.tld";
    //SMTP Port. Possible options: 465, 587, 25. Use 465 for SSL encryption (recommended). 
    public static Integer SETTINGS_SMTP_PORT = 465;

    //Your real email address that is used to send CostEstimate emails
    public static String SETTINGS_SENDER_EMAIL = "MY_EMAIL@EMAIL.TLD";
    //Your password of your email address
    public static String SETTINGS_EMAIL_PASSWORD = "MY_PASSWORD";

    public static String EMAIL_SUBJECT = "Your created cost estimate";
    public static String EMAIL_FOOTER = "My company\nStreet 123\n\nemail@email.tld";
}

package com.yue.czcontrol;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public final class MailSender {

    /**
     * SMTP Server.
     */
    private static final String mailServer = "smtp.gmail.com";
    /**
     * Port.
     */
    private static final String mailPort = "465";
    /**
     * My Mail.
     */
    private static final String from = "czteamcontrol@gmail.com";

    private MailSender(){

    }

    /**
     * Send mail.
     * @param name Name
     * @param subject subject
     * @param mail to mail
     * @param list message list
     */
    public static void send(String name, String subject, String mail, List<String> list) {
        InternetAddress[] address;
        Session mailSession;

        try {
            Properties props = new Properties();
            props.put("mail.host", mailServer);
            props.put("mail.smtp.port", mailPort);
            props.put("mail.smtp.socketFactory.port", mailPort);
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.auth", "true");
            props.put("mail.transport.protocol", "smtp");
            mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("czteamcontrol","admin897317");
                        }
                    });

            Message message = new MimeMessage(mailSession);

            message.setSubject(subject);

            message.setText(toMessage(list));

            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(from, name));

            address = InternetAddress.parse(mail,false);
            message.setRecipients(Message.RecipientType.TO, address);

            Transport.send(message);

            System.out.println("Mail Send Completed...");
            System.out.println("--------------------------------");
            System.out.println("Send Server: " + mailServer + ":" + mailPort);
            System.out.println(" ");
            System.out.println("Send Account: " + from);
            System.out.println("To: " + mail);
            System.out.println();
            System.out.println("Subject: " + subject);
            System.out.println("Text(List):\n" + list);
            System.out.println("Text(String):\n" + toMessage(list));
            System.out.println("--------------------------------");

            AlertBox.show("MailSender", "Please pay attention to your mailbox.", AlertBox.Type.INFORMATION);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * List to message
     * @param list list
     * @return string
     */
    private static String toMessage(List<String> list) {
        String msg = "";

        for (String s : list) {
            msg = msg.concat(s + "\n");
        }

        msg = msg.concat("\n\nPlease Note: This message is automatically sent, please do not reply directly!");

        return msg;
    }

}

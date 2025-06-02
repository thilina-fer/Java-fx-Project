/*
package lk.ijse.finalproject.controller;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.mysql.cj.Session;
import javafx.scene.control.Alert;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class JavaMailUtil {

    public static boolean sendMail(String recepient, int otp) {
        try {
            System.out.println("Preparing to send mail to ");
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myAccountEmail = "dilshanfernando20031010@gmail.com";
             String password1 = "gaae syaz cqxh aige";

            Session session = Session.getInstance(properties , new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password1.toCharArray());
                }
            });
            Message message = prepareMessage(session, myAccountEmail, recepient, otp);
                if (message!= null){
                    Transport.send(message);
                    System.out.println("Email sent Successfully");
                    return true;
            }else {
                    new Alert(Alert.AlertType.ERROR,"Failed to prepare message").show();
                }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"failed to prepare message").show();
            return false;
        }
        return true;
    }
    private static javax.mail.Message prepareMessage(com.mysql.cj.Session session, String myAccountEmail, String recepient, int otp) {
        try {
            javax.mail.Message message = new javax.mail.internet.MimeMessage((javax.mail.internet.MimeMessage) session);
            message.setFrom(new javax.mail.internet.InternetAddress(myAccountEmail));
            message.setSubject("Your OTP");
            message.setText("Your OTP is " + otp);
            return message;
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Connect Internet Connection").show();
        }
        return null;
    }
}
*/

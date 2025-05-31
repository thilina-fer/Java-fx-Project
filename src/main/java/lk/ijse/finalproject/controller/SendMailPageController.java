/*
package lk.ijse.finalproject.controller;

import com.google.protobuf.Message;
import com.mysql.cj.Session;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

public class SendMailPageController {
    public TextField txtTo;
    public TextField txtSubject;
    public TextArea txtMessage;

    @Setter
    private String customerEmail;

    public void btnSendOnAction(ActionEvent actionEvent) {

        System.out.println(customerEmail);
        String toMail = customerEmail;
        String subject = txtSubject.getText();
        String message = txtMessage.getText();

        if (toMail == null || subject == null || message == null) {
            return;
        }
            String from = "thilinadilshan1010@gmail.com";
            String password = "aazf zphk eyix hujd";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password.toCharArray());
        }
    };
        Session session = Session.getInstance(props , auth);
        try {
            Message mineMessage = new mineMessage(session);

            mineMessage.setFrom
        }
        }
}
*/

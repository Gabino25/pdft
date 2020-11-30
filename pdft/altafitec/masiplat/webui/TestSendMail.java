package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class TestSendMail {

    public static String strXxqpPdftEmail;
    public static String strXxqpPdftEmailPwd;
    public static String strXxqpPdftEmailRecipient;
    
    public TestSendMail() {
    }
    public static void main(String[] args) {
      /********************************************************************************
      System.out.println("Hello");
      Properties properties =System.getProperties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
                                                        protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                                                         return new javax.mail.PasswordAuthentication (TestSendMail.strXxqpPdftEmail,TestSendMail.strXxqpPdftEmailPwd);
                                                        }
                                                        }
                                               );
       Message message = new MimeMessage(session);

        try {
            message.setFrom( new InternetAddress(TestSendMail.strXxqpPdftEmail));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strXxqpPdftEmailRecipient));
            message.setSubject("Subject");
            message.setText("Text");
            Transport.send(message);
        } catch (MessagingException me) {
            System.out.println("MessagingException:"+me.getMessage());
        }
        *******************************************************************************/
       System.out.println("Hello");
        Properties properties =System.getProperties();
         properties.put("mail.smtp.auth","true");
         properties.put("mail.smtp.starttls.enable","true");
         properties.put("mail.smtp.host","mail.qualitypost.com.mx");
         properties.put("mail.smtp.port","465"/*1825*/);
         
        properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class

         
         Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
                                                         protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                                                          return new javax.mail.PasswordAuthentication (TestSendMail.strXxqpPdftEmail,TestSendMail.strXxqpPdftEmailPwd);
                                                         }
                                                         }
                                                );
        session.setDebug(true);                                       
        Message message = new MimeMessage(session);

         try {
             message.setFrom( new InternetAddress(TestSendMail.strXxqpPdftEmail));
             message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strXxqpPdftEmailRecipient));
             message.setSubject("Subject");
             
             // Create the message part
                      BodyPart messageBodyPart = new MimeBodyPart();

                      // Now set the actual message
                      messageBodyPart.setText("This is message body");

                      // Create a multipar message
                      Multipart multipart = new MimeMultipart();

                      // Set text message part
                      multipart.addBodyPart(messageBodyPart);

                      // Part two is attachment
                      messageBodyPart = new MimeBodyPart();
                      String filename = "C:\\Users\\root\\Desktop\\AltaFichaTecnicaMyP.pdf";
                      DataSource source = null; /**new FileDataSource(filename); **/
                        try {
                            source = new ByteArrayDataSource(new ByteArrayInputStream("HOLA".getBytes("UTF-8")),"application/pdf");
                        } catch (IOException e) {
                            // TODO
                        }
            messageBodyPart.setDataHandler(new DataHandler(source));
                      messageBodyPart.setFileName("AltaFichaTecnicaMyP.pdf");
                      multipart.addBodyPart(messageBodyPart);

                      // Send the complete message parts
                      message.setContent(multipart);
             
             Transport.send(message);
         } catch (MessagingException me) {
             System.out.println("MessagingException:"+me.getMessage());
         }
         
    }

}

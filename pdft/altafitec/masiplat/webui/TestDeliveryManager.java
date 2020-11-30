package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import java.util.Properties;

import oracle.apps.xdo.delivery.DeliveryException;
import oracle.apps.xdo.delivery.DeliveryManager;
import oracle.apps.xdo.delivery.DeliveryPropertyDefinitions;
import oracle.apps.xdo.delivery.DeliveryRequest;
import oracle.apps.xdo.delivery.InvalidFactoryException;
import oracle.apps.xdo.delivery.UndefinedRequestTypeException;

public class TestDeliveryManager {
    
    public static String strXxqpPdftEmail;
    public static String strXxqpPdftEmailPwd;
    public static String strXxqpPdftEmailRecipient;
    
    public TestDeliveryManager() {
    }
    public static void main(String[] args) {
        System.out.println("Hola");
        DeliveryManager deliveryManager = null;
        DeliveryRequest deliveryRequest = null; 
        try {
            deliveryManager = new DeliveryManager();
          
            deliveryRequest = deliveryManager.createRequest(DeliveryManager.TYPE_SMTP_EMAIL);
         
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_SUBJECT,"Test Delivery Manager");
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_HOST,"mail.qualitypost.com.mx");
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_PORT,"465"/*"1825"*/);
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_FROM,TestDeliveryManager.strXxqpPdftEmail);
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_USERNAME,TestDeliveryManager.strXxqpPdftEmail);
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_PASSWORD,TestDeliveryManager.strXxqpPdftEmailPwd);
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_TO_RECIPIENTS,TestDeliveryManager.strXxqpPdftEmailRecipient);
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_CONTENT_FILENAME,"AltaFichaTecnicaMyP.pdf");
            deliveryRequest.addProperty(DeliveryPropertyDefinitions.SMTP_CONTENT_TYPE,"application/pdf");
            
            Properties properties =System.getProperties();
            properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            
            deliveryRequest.validateProperties();
            deliveryRequest.setDocument("C:\\Users\\root\\Desktop\\AltaFichaTecnicaMyP.pdf");
            deliveryRequest.submit(); 
            deliveryRequest.close();
        } catch (InvalidFactoryException e) {
           System.out.println("InvalidFactoryException:"+e.getMessage());
        } catch (UndefinedRequestTypeException e) {
            System.out.println("UndefinedRequestTypeException:"+e.getMessage());
        } catch (DeliveryException e) {
             System.out.println("DeliveryException:"+e.getMessage()+", "+e.getCause());
        }
    }
    
}

package xxqp.oracle.apps.ar.pdft.altacliente;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oracle.apps.fnd.cp.request.CpContext;
import oracle.apps.fnd.cp.request.JavaConcurrentProgram;
import oracle.apps.fnd.cp.request.LogFile;
import oracle.apps.fnd.cp.request.OutFile;
import oracle.apps.fnd.cp.request.ReqCompletion;
import oracle.apps.fnd.util.NameValueType;
import oracle.apps.fnd.util.ParameterList;
import oracle.apps.xdo.delivery.DeliveryException;
import oracle.apps.xdo.delivery.DeliveryManager;
import oracle.apps.xdo.delivery.DeliveryPropertyDefinitions;
import oracle.apps.xdo.delivery.DeliveryRequest;
import oracle.apps.xdo.delivery.InvalidFactoryException;
import oracle.apps.xdo.delivery.UndefinedRequestTypeException;

public class CreationClientsAlert implements JavaConcurrentProgram {

    public static String strXxqpPdftEmail;
    public static String strXxqpPdftEmailPwd;
    
    public CreationClientsAlert() {
    }

    public void runProgram(CpContext pCpContext) {
      ReqCompletion lRC = pCpContext.getReqCompletion();
      String CompletionText = "";
        // get the JDBC connection object
      Connection mJConn = pCpContext.getJDBCConnection();
      
        // get parameter list object from CpContext

        ParameterList lPara = pCpContext.getParameterList();
        
        String strPsiCorreos = null; 
        String strEmailContent = ""; 
        
        while (lPara.hasMoreElements())
           {
              NameValueType aNVT = lPara.nextParameter();
              if ( aNVT.getName().equals("PsiCorreos") )
              strPsiCorreos = aNVT.getValue();
           }

      
        String lQuery = " SELECT nvl( nvl(p.attribute1,p.JGZZ_FISCAL_CODE), p.tax_reference) JGZZ_FISCAL_CODE,\n" + 
        "               p.party_name RAZON_SOCIAL  FROM hz_parties p,\n" + 
        "       hz_party_sites ps,\n" + 
        "       hz_locations     hl,\n" + 
        "       fnd_territories_vl terr,\n" + 
        "       fnd_lookup_values partytype,\n" + 
        "       fnd_lookup_values certification,\n" + 
        "       fnd_lookup_values registrystatus\n" + 
        " WHERE  1=1     \n" + 
        "       /** and p.party_type IN ('PERSON', 'ORGANIZATION') **/\n" + 
        "       AND registrystatus.lookup_code = p.status\n" + 
        "       AND  ps.LOCATION_ID      =  hl.LOCATION_ID\n" + 
        "       AND ps.party_id(+) = p.party_id\n" + 
        "       AND ps.identifying_address_flag(+) = 'Y'\n" + 
        "       AND terr.territory_code(+) = p.country\n" + 
        "       AND partytype.view_application_id = 222 /** AR **/\n" + 
        "       AND partytype.lookup_type = 'PARTY_TYPE'\n" + 
        "       AND partytype.language = USERENV ('LANG')\n" + 
        "       AND partytype.lookup_code = p.party_type\n" + 
        "       AND certification.view_application_id(+) = 222  /** AR **/\n" + 
        "       AND certification.lookup_type(+) = 'HZ_PARTY_CERT_LEVEL'\n" + 
        "       AND certification.language(+) = USERENV ('LANG')\n" + 
        "       AND certification.lookup_code(+) = p.certification_level\n" + 
        "       AND registrystatus.lookup_type = 'HZ_CPUI_REGISTRY_STATUS'\n" + 
        "       AND registrystatus.language = USERENV ('LANG')\n" + 
        "       and p.party_type = 'ORGANIZATION'\n" + 
        "       and p.status = 'A'\n" + 
        "       and trunc(p.creation_date)=trunc(sysdate) ";        
      lRC.setCompletion(ReqCompletion.NORMAL, CompletionText);
    
        // get OutFile object from CpContext
            OutFile lOF = pCpContext.getOutFile();
        
             lOF.writeln("strPsiCorreos:"+strPsiCorreos);
             
            // get LogFile object from CpContext
            LogFile lLF = pCpContext.getLogFile();
            
        strEmailContent = "Se Crearon los siguientes Clientes:\n  RFC Cliente   Razon Social";
        int countClientesCreados = 0; 
        try
           {
             PreparedStatement lStmt = mJConn.prepareStatement(lQuery);
             ResultSet lRs = lStmt.executeQuery();
            while( lRs.next() )
                    {
                      lOF.writeln(lRs.getString("party_name")  );
                        strEmailContent = strEmailContent+"\n"+lRs.getString("JGZZ_FISCAL_CODE")+lRs.getString("RAZON_SOCIAL");
                        countClientesCreados = countClientesCreados+1;
                    }
             
             if(null!=lRs){
               lRs.close();
             }
             if(null!=lStmt){
                 lStmt.close();
             }
             
           }catch (SQLException e)
           {
          lRC.setCompletion(ReqCompletion.ERROR, e.toString());
          }
          finally
             {
                pCpContext.releaseJDBCConnection();
             }
          
          if(0==countClientesCreados){
              strEmailContent = "No se crearon clientes";
          }   
             
          Properties properties =System.getProperties();
          properties.put("mail.smtp.auth","true");
          properties.put("mail.smtp.starttls.enable","true");
          properties.put("mail.smtp.host","mail.qualitypost.com.mx");
          properties.put("mail.smtp.port","465");
        
          properties.put("mail.smtp.socketFactory.port", "465"); /*SSL Port*/
          properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); /*SSL Factory Class*/
        
          this.strXxqpPdftEmail =  pCpContext.getProfileStore().getProfile("XXQP_PDFT_EMAIL");
          this.strXxqpPdftEmailPwd = pCpContext.getProfileStore().getProfile("XXQP_PDFT_EMAIL_PWD");
        
          Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
                                                          protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                                                           return new javax.mail.PasswordAuthentication (CreationClientsAlert.strXxqpPdftEmail,CreationClientsAlert.strXxqpPdftEmailPwd);
                                                          }
                                                          }
                                                 );
         Message message = new MimeMessage(session);
        String[] recipientList = strPsiCorreos.split(";");
        InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
      

          try {
              for(int i=0;i<recipientList.length;i++){
                  recipientAddress[i]= new InternetAddress(recipientList[i].trim());
              } 
              
              message.setFrom( new InternetAddress(CreationClientsAlert.strXxqpPdftEmail));
             /* message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strPsiCorreos)); */
              message.setRecipients(Message.RecipientType.TO,recipientAddress);
              message.setSubject("Aviso de Alta de Cliente nuevo");
              message.setText(strEmailContent);
              Transport.send(message);
          } catch (MessagingException me) {
              lOF.writeln("MessagingException:"+me.getMessage());
          }

   
    }
    
}

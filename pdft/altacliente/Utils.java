package xxqp.oracle.apps.ar.pdft.altacliente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.sql.SQLException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.common.AppsContext;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.domain.BlobDomain;

import xxqp.oracle.apps.ar.pdft.altacliente.server.AltaDeClienteAMImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesDirFiscalVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesDirFiscalVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;

public class Utils {
    public static void revisarPDF(AltaDeClienteAMImpl altaDeClienteAMImpl, 
                                  OAPageContext pageContext) {
        System.out.println("Comienza altacliente.Utils:");
        XxqpPdftClientesHeaderVOImpl voHeader = altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1();
        System.out.println("voHeader:"+voHeader);
        if(null==voHeader){
          return; 
        }
        XxqpPdftClientesHeaderVORowImpl rowHeader = (XxqpPdftClientesHeaderVORowImpl)voHeader.getCurrentRow();    
        System.out.println("rowHeader:"+rowHeader);
        if(null==rowHeader){
        return;
        }
        XxqpPdftClientesDirFiscalVOImpl voDirFiscal = altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1();
        System.out.println("voDirFiscal:"+voDirFiscal);
        if(null==voDirFiscal){
         return; 
        }
        XxqpPdftClientesDirFiscalVORowImpl rowDirFiscal = (XxqpPdftClientesDirFiscalVORowImpl)voDirFiscal.getCurrentRow();
        System.out.println("rowDirFiscal(Current):"+rowDirFiscal);
        if(null==rowDirFiscal){
            rowDirFiscal = (XxqpPdftClientesDirFiscalVORowImpl)voDirFiscal.first();
            System.out.println("rowDirFiscal(First):"+rowDirFiscal);
            if(null==rowDirFiscal){
                return; 
            }
        }
        String strNombreCliente = rowHeader.getNombreCliente();
        String strOperatingUnit = rowDirFiscal.getPrimOperatingUnit();
        System.out.println("strNombreCliente:"+strNombreCliente);
        strNombreCliente = strNombreCliente.replaceAll("\\.","");
        strNombreCliente = strNombreCliente.replaceAll(",","");
        strNombreCliente = strNombreCliente.replaceAll("\\s","_")+strOperatingUnit;
        System.out.println("strNombreCliente:"+strNombreCliente);
        DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
        HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
        String contentDisposition = "attachment;filename="+strNombreCliente+".pdf";
        response.setHeader("Content-Disposition",contentDisposition);
        response.setContentType("application/pdf");
        ServletOutputStream os=null;               
        String strXML = null;
        strXML = altaDeClienteAMImpl.getXxPdftCustomerInfo("READ_ONLY");
        if(null==strXML){
         return;
        }
        ByteArrayInputStream bAiSXmlFile = null; 
        ByteArrayOutputStream bAoSXmlFile = null; 
        try {
            os = response.getOutputStream();
            byte[] aByte = strXML.getBytes();
            bAiSXmlFile = new ByteArrayInputStream(aByte);
            bAoSXmlFile = new ByteArrayOutputStream();
            AppsContext appsContext = ((OADBTransactionImpl)altaDeClienteAMImpl.getOADBTransaction()).getAppsContext();
            Locale locale = ((OADBTransactionImpl)altaDeClienteAMImpl.getOADBTransaction()).getUserLocale();
            TemplateHelper.processTemplate(appsContext, 
                                           AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                           "XXQP_PDFT_CUSTOMER", 
                                           locale.getLanguage(), 
                                           locale.getCountry(), 
                                           bAiSXmlFile, 
                                           TemplateHelper.OUTPUT_TYPE_PDF, 
                                            null, 
                                           bAoSXmlFile);

                                byte[] b = bAoSXmlFile.toByteArray();
                                response.setContentLength(b.length);
                                os.write(b, 0, b.length);
                                os.flush();
                                os.close();
           
        } catch (IOException e) {
           throw new OAException("IOException revisarPDF al obtener el ServletOutputStream.",OAException.ERROR); 
        } catch (SQLException e) {
            throw new OAException("SQLException revisarPDF al obtener el DataTemplate.",OAException.ERROR);
        } catch (XDOException e) {
            throw new OAException("XDOException revisarPDF al obtener el DataTemplate.",OAException.ERROR);
        }finally{
            /** Realizar cierre en cascada **/
           if(null!=bAoSXmlFile){
               try {
                   bAoSXmlFile.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           
            if(null!=bAiSXmlFile){
                try {
                    bAiSXmlFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }


      
    }

    public static void enviarPDFConCedula(AltaDeClienteAMImpl altaDeClienteAMImpl
                                         ,OAPageContext pageContext
                                         ,String pOperacion) {
        System.out.println("Comienza xxqp.oracle.apps.ar.pdft.altacliente.Utils.enviarPDFConCedula");
        XxqpPdftClientesHeaderVOImpl voHeader = altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1();
        if(null==voHeader){
          return; 
        }
        XxqpPdftClientesHeaderVORowImpl rowHeader = (XxqpPdftClientesHeaderVORowImpl)voHeader.getCurrentRow();                     
        if(null==rowHeader){
        return;
        }
        XxqpPdftClientesDirFiscalVOImpl voDirFiscal = altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1();
        if(null==voDirFiscal){
         return; 
        }
        XxqpPdftClientesDirFiscalVORowImpl rowDirFiscal = (XxqpPdftClientesDirFiscalVORowImpl)voDirFiscal.getCurrentRow();
        if(null==rowDirFiscal){
         return; 
        }
        String strXML = null;
        strXML = altaDeClienteAMImpl.getXxPdftCustomerInfo(pOperacion);
        if(null==strXML){
         return;
        }
        String strNombreCliente = rowHeader.getNombreCliente();
        String strOperatingUnit = rowDirFiscal.getPrimOperatingUnit();
        String strRFC = rowHeader.getRfc();
        System.out.println("strNombreCliente:"+strNombreCliente);
        String strNombrePdf = strNombreCliente.replaceAll("\\s","_")+strOperatingUnit;
        System.out.println("strNombreCliente:"+strNombreCliente);
        String strPrimCedulaFileName = rowDirFiscal.getPrimCedulaFileName(); 
        String strPrimCedulaContentType = rowDirFiscal.getPrimCedulaContentType(); 
        BlobDomain blobPrimCedulaFile = rowDirFiscal.getPrimCedulaFile(); 
        String strRazonSocial = rowHeader.getRazonSocial();
         
        ByteArrayInputStream bAiSxmlCliente = null;
        ByteArrayOutputStream bAoSpdfFile = null; 
        InputStream iSpdfFile = null; 
        InputStream isPrimCedulaFile = blobPrimCedulaFile.getInputStream();
        
        try {
            byte[] aByte = strXML.getBytes();
            bAiSxmlCliente = new ByteArrayInputStream(aByte);
            bAoSpdfFile = new ByteArrayOutputStream();
            AppsContext appsContext = ((OADBTransactionImpl)altaDeClienteAMImpl.getOADBTransaction()).getAppsContext();
            Locale locale = ((OADBTransactionImpl)altaDeClienteAMImpl.getOADBTransaction()).getUserLocale();
            TemplateHelper.processTemplate(appsContext, 
                                           AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                           "XXQP_PDFT_CUSTOMER", 
                                           locale.getLanguage(), 
                                           locale.getCountry(), 
                                           bAiSxmlCliente, 
                                           TemplateHelper.OUTPUT_TYPE_PDF, 
                                            null, 
                                           bAoSpdfFile);

                                byte[] b = bAoSpdfFile.toByteArray();
                                 iSpdfFile = new ByteArrayInputStream(b);
                                altaDeClienteAMImpl.enviaCorreos(iSpdfFile
                                                                ,strNombreCliente
                                                                ,strRFC
                                                                ,strNombrePdf
                                                                ,isPrimCedulaFile
                                                                ,strPrimCedulaFileName
                                                                ,strPrimCedulaContentType
                                                                ,pOperacion
                                                                ,strRazonSocial
                                                                );
            
        } catch (IOException e) {
           throw new OAException("IOException revisarPDF al obtener el ServletOutputStream.",OAException.ERROR); 
        } catch (SQLException e) {
            throw new OAException("SQLException revisarPDF al obtener el DataTemplate.",OAException.ERROR);
        } catch (XDOException e) {
            throw new OAException("XDOException revisarPDF al obtener el DataTemplate.",OAException.ERROR);
        }finally{
        
           /** Realizar cierre en cascada **/
            if(null!=iSpdfFile){
                try {
                    iSpdfFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(null!=bAoSpdfFile){
                try {
                    bAoSpdfFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(null!=bAiSxmlCliente){
                try {
                    bAiSxmlCliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
           if(null!=isPrimCedulaFile){
               try {
                   isPrimCedulaFile.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
            
        }


       
        System.out.println("Finaliza xxqp.oracle.apps.ar.pdft.altacliente.Utils.enviarPDFConCedula");
    }
}

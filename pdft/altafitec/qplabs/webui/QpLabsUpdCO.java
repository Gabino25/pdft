/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.sql.SQLException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.common.AppsContext;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.QpLabsAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVORowImpl;

/**
 * Controller for ...
 */
public class QpLabsUpdCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoFileUpload");
      
      if(null!=NombreUsuarioEBSBean){
           NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
       }
       
      if(null!=ContratoExamineBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "ContratoFileName"); 
          ContratoExamineBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
       
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
      if(!pageContext.isFormSubmission()){
          String strQplabsHeaderId = pageContext.getParameter("pQplabsHeaderId");
          qpLabsAMImpl.initQplabsHeaderVO(strQplabsHeaderId);
          qpLabsAMImpl.initQplabsInfoOperVO(strQplabsHeaderId);
          qpLabsAMImpl.initQplabsPrecClieVO(strQplabsHeaderId); 
      }else{
      
      }
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
      DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoFileUpload"); 
      String strContratoFileUploadContentType = null; 
      if(null!=ContratoExamineUploadData){
        strContratoFileUploadContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
       }
       
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
    String strEventParam =  pageContext.getParameter(this.EVENT_PARAM);
    
      XxqpPdftQplabsHeaderVOImpl  xxqpPdftQplabsHeaderVOImpl = null; 
      XxqpPdftQplabsHeaderVORowImpl  xxqpPdftQplabsHeaderVORowImpl = null; 
      xxqpPdftQplabsHeaderVOImpl = qpLabsAMImpl.getXxqpPdftQplabsHeaderVO1(); 
      
      RowSetIterator QplabsHeaderIterator =  xxqpPdftQplabsHeaderVOImpl.createRowSetIterator(null);
      if(QplabsHeaderIterator.hasNext()){
        xxqpPdftQplabsHeaderVORowImpl = (XxqpPdftQplabsHeaderVORowImpl)QplabsHeaderIterator.next();
      }
      QplabsHeaderIterator.closeRowSetIterator();
    
    if("GrabarEvt".equals(strEventParam)){
      if(null!=qpLabsAMImpl){
      
          oracle.jbo.domain.BlobDomain blobContratoFile = xxqpPdftQplabsHeaderVORowImpl.getContratoFile();
          if(null==blobContratoFile){
              xxqpPdftQplabsHeaderVORowImpl.setContratoFileName(null);
              xxqpPdftQplabsHeaderVORowImpl.setContratoContentType(null);
          }else{
              System.out.println("blobContratoFile.getLength():"+blobContratoFile.getLength());
          }
          
          if(null!=strContratoFileUploadContentType&&!"".equals(strContratoFileUploadContentType)){
              xxqpPdftQplabsHeaderVORowImpl.setContratoContentType(strContratoFileUploadContentType);
          }
          
          qpLabsAMImpl.getOADBTransaction().commit();
          
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          oracle.jbo.domain.Number numQpLabsHeaderId = xxqpPdftQplabsHeaderVORowImpl.getId();
          parameters.put("pQplabsHeaderId",numQpLabsHeaderId.toString() );
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsReOnPG" /*url*/
                                    ,null /*functionName*/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                    ,null /*menuName*/
                                    ,parameters /*parameters*/
                                    ,false /*retainAM*/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                    ,OAException.ERROR /*messagingLevel*/
                                    );     
                                    
      }
    } /** END if("GrabarEvt".equals(strEventParam)){ **/
    
     if("RevisarPDFEvt".equals(strEventParam)){
         DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
         HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
         String contentDisposition = "attachment;filename=AltaFichaTecnicaQplabs.pdf";
         response.setHeader("Content-Disposition",contentDisposition);
         response.setContentType("application/pdf");
         ServletOutputStream os=null;
        
          String strXML = null;
             strXML = qpLabsAMImpl.executeQplabsGetInfo();
             try {
                 os = response.getOutputStream();
                 byte[] aByte = strXML.getBytes();
                 ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
                 ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
                 AppsContext appsContext = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getAppsContext();
                 Locale locale = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getUserLocale();
                 TemplateHelper.processTemplate(appsContext, 
                                                AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                "XXQP_PDFT_QPLABS", 
                                                locale.getLanguage(), 
                                                locale.getCountry(), 
                                                inputStream, 
                                                TemplateHelper.OUTPUT_TYPE_PDF, 
                                                 null, 
                                                pdfFile);

                                     byte[] b = pdfFile.toByteArray();
                                     response.setContentLength(b.length);
                                     os.write(b, 0, b.length);
                                     os.flush();
                                     os.close();
                
             } catch (IOException e) {
                throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
             } catch (SQLException e) {
                 throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
             } catch (XDOException e) {
                 throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
             }
         } /** END if("RevisarPDFEvt".equals(strEventParam)){ **/
    if("ProcesarEvt".equals(strEventParam)){
     
        String strXML = null;
        strXML = qpLabsAMImpl.executeQplabsGetInfo();
        try {
            byte[] aByte = strXML.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
            ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
            AppsContext appsContext = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getAppsContext();
            Locale locale = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getUserLocale();
            TemplateHelper.processTemplate(appsContext, 
                                           AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                           "XXQP_PDFT_QPLABS", 
                                           locale.getLanguage(), 
                                           locale.getCountry(), 
                                           inputStream, 
                                           TemplateHelper.OUTPUT_TYPE_PDF, 
                                            null, 
                                           pdfFile);
            if(null!=inputStream){
            inputStream.close();
            }
            
            byte[] a2Byte =pdfFile.toByteArray(); 
            InputStream inputStream2 = new ByteArrayInputStream(a2Byte);
            
            String strCorreos = qpLabsAMImpl.enviaCorreos(inputStream2); 
            System.out.println("strCorreos:"+strCorreos);
            
            if(null!=pdfFile){
                pdfFile.close();
            }
            
            if(null!=inputStream2){
                inputStream2.close();
            }
            
            xxqpPdftQplabsHeaderVORowImpl.setStatus("ABIERTA");
            qpLabsAMImpl.getOADBTransaction().commit();
            
            com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
            oracle.jbo.domain.Number numQpLabsHeaderId = xxqpPdftQplabsHeaderVORowImpl.getId();
            parameters.put("pQplabsHeaderId",numQpLabsHeaderId.toString() );
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsReOnPG" /*url*/
                                      ,null /*functionName*/
                                      ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                      ,null /*menuName*/
                                      ,parameters /*parameters*/
                                      ,false /*retainAM*/
                                      ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                      ,OAException.ERROR /*messagingLevel*/
                                      );    
                                      
            return;                           
           
        } catch (IOException e) {
           throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
        } catch (SQLException e) {
            throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
        } catch (XDOException e) {
            throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
        }
     
    } /** END if("ProcesarEvt".equals(strEventParam)){ **/
    
  }

}

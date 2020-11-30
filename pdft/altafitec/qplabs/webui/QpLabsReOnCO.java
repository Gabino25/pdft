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
import oracle.apps.fnd.framework.OAFwkConstants;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAProcessingPage;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OABodyBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OATableLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;
import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.QpLabsAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsInfoOperVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsInfoOperVORowImpl;

/**
 * Controller for ...
 */
public class QpLabsReOnCO extends OAControllerImpl
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
      OAWebBean body = pageContext.getRootWebBean(); 
       if (body instanceof OABodyBean){
          ((OABodyBean)body).setBlockOnEverySubmit(true); 
       }
       
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAButtonBean  CancelarFTBtnBean = (OAButtonBean)webBean.findChildRecursive("CancelarFTBtn");
      OAButtonBean  CopiarBtnBean = (OAButtonBean)webBean.findChildRecursive("CopiarBtn");
      OAButtonBean  ModificarBtnBean = (OAButtonBean)webBean.findChildRecursive("ModificarBtn");
      OAButtonBean  EnviarPorCorreoBtnBean = (OAButtonBean)webBean.findChildRecursive("EnviarPorCorreoBtn");
      OAButtonBean  RevisarPDFBtnBean = (OAButtonBean)webBean.findChildRecursive("RevisarPDFBtn");
      
      OAPageButtonBarBean PageButtonBarRNBean = (OAPageButtonBarBean)webBean.findChildRecursive("PageButtonBarRN");
      OATableLayoutBean  TabLayHeadBean = (OATableLayoutBean)webBean.findChildRecursive("TabLayHead");
      OATableLayoutBean  TabLayHead2Bean = (OATableLayoutBean)webBean.findChildRecursive("TabLayHead2");
      OATableLayoutBean  TabLayHead3Bean = (OATableLayoutBean)webBean.findChildRecursive("TabLayHead3");
      OASubTabLayoutBean SubTabLayoutRNBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
      OALinkBean returnNavigation = (OALinkBean)webBean.findChildRecursive("RegresarBusquedaDeFichaTecnica");
      
      if(null!=returnNavigation)
       returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaDeFichaTecnicaPG");
      
      
      if(null!=NombreUsuarioEBSBean){
           NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
       }
       
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftQplabsHeaderVORowImpl xxqpPdftQplabsHeaderVORowImpl = null; 
    XxqpPdftQplabsInfoOperVORowImpl  xxqpPdftQplabsInfoOperVORowImpl = null; 
    
      String strQplabsHeaderId = pageContext.getParameter("pQplabsHeaderId");
      String strQplabsExtern =  pageContext.getParameter("pQplabsExtern");
      pageContext.writeDiagnostics(this,"strQplabsHeaderId:"+strQplabsHeaderId,OAFwkConstants.STATEMENT);
      pageContext.writeDiagnostics(this,"strQplabsExtern:"+strQplabsExtern,OAFwkConstants.STATEMENT);
      
      try{
      oracle.jbo.domain.Number numQplabsHeaderId = new oracle.jbo.domain.Number(strQplabsHeaderId);
      }catch(SQLException sqle){
        if(null!=PageButtonBarRNBean)
            PageButtonBarRNBean.setRendered(false);
        if(null!=TabLayHeadBean)
            TabLayHeadBean.setRendered(false);
        if(null!=TabLayHead2Bean)
            TabLayHead2Bean.setRendered(false);
        if(null!=TabLayHead3Bean)
           TabLayHead3Bean.setRendered(false);
        if(null!=SubTabLayoutRNBean)
            SubTabLayoutRNBean.setRendered(false);
        throw new OAException("Ocurrio una Excepcion al recuperar el parametro strQplabsHeaderId:"+strQplabsHeaderId,OAException.ERROR);     
      }
      
    if(!pageContext.isFormSubmission()){
        xxqpPdftQplabsHeaderVORowImpl = qpLabsAMImpl.initQplabsHeaderVO(strQplabsHeaderId);
        xxqpPdftQplabsInfoOperVORowImpl = qpLabsAMImpl.initQplabsInfoOperVO(strQplabsHeaderId);
        qpLabsAMImpl.initQplabsPrecClieVO(strQplabsHeaderId); 
    }else{
  
    }
    
    if(null==xxqpPdftQplabsHeaderVORowImpl){
      if(null!=CancelarFTBtnBean)
      CancelarFTBtnBean.setDisabled(true);
      if(null!=CopiarBtnBean)    
      CopiarBtnBean.setDisabled(true);
      if(null!=ModificarBtnBean)
      ModificarBtnBean.setDisabled(true);
      if(null!=EnviarPorCorreoBtnBean)
      EnviarPorCorreoBtnBean.setDisabled(true);
      if(null!=RevisarPDFBtnBean)
      RevisarPDFBtnBean.setDisabled(true);
     throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftQplabsHeaderVO.",OAException.ERROR); 
    }
    if(null==xxqpPdftQplabsInfoOperVORowImpl){
        if(null!=CancelarFTBtnBean)
        CancelarFTBtnBean.setDisabled(true);
        if(null!=CopiarBtnBean)    
        CopiarBtnBean.setDisabled(true);
        if(null!=ModificarBtnBean)
        ModificarBtnBean.setDisabled(true);
        if(null!=EnviarPorCorreoBtnBean)
        EnviarPorCorreoBtnBean.setDisabled(true);
        if(null!=RevisarPDFBtnBean)
        RevisarPDFBtnBean.setDisabled(true);
        throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftQplabsInfoOperVO.",OAException.ERROR); 
    }
    
      String strStatus = null; 
      if(null!=xxqpPdftQplabsHeaderVORowImpl.getStatus()){
      strStatus = xxqpPdftQplabsHeaderVORowImpl.getStatus();
      }
      
      System.out.println("strStatus:"+strStatus);
      
      if("CANCELADA".equals(strStatus)){
       if(null!=CancelarFTBtnBean)
           CancelarFTBtnBean.setDisabled(true);
       if(null!=ModificarBtnBean)
           ModificarBtnBean.setDisabled(true);
       if(null!=EnviarPorCorreoBtnBean)
           EnviarPorCorreoBtnBean.setDisabled(true);
      } 
       else if("ABIERTA".equals(strStatus)){
                   if(null!=ModificarBtnBean)
                   ModificarBtnBean.setDisabled(true);
       }else if("PRELIMINAR".equals(strStatus)){
                   if(null!=EnviarPorCorreoBtnBean)
                   EnviarPorCorreoBtnBean.setDisabled(true);
       }/** END if("CANCELADA".equals(strStatus)){ **/
       
        if("Y".equals(strQplabsExtern)){
            if(null!=NombreUsuarioEBSBean)
            NombreUsuarioEBSBean.setRendered(false);
            if(null!=CancelarFTBtnBean)
            CancelarFTBtnBean.setRendered(false);
            if(null!=CopiarBtnBean)
            CopiarBtnBean.setRendered(false);
            if(null!=ModificarBtnBean)
            ModificarBtnBean.setRendered(false);
            if(null!=EnviarPorCorreoBtnBean)
            EnviarPorCorreoBtnBean.setRendered(false);
            if(null!=RevisarPDFBtnBean)
            RevisarPDFBtnBean.setRendered(false);
            if(null!=returnNavigation)
                returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/ConsultaFichaTecnicaPG");
        } 
    
      if("Y".equals(pageContext.getParameter("pQplabsCopiar"))){
          OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui.QpLabsCopiarCO"); 
          oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
          oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
          oAProcessingPage.setProcessName("<Process Name>"); 
          pageContext.forwardToProcessingPage(oAProcessingPage);  
          
      }
    
      if("Y".equals(pageContext.getParameter("pQplabsCambioDePrecio"))){
          xxqpPdftQplabsHeaderVORowImpl.setStatus("CERRADA");
          OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui.QpLabsCopiarCO"); 
          oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
          oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
          oAProcessingPage.setProcessName("<Process Name>"); 
          pageContext.forwardToProcessingPage(oAProcessingPage);  
          
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
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    
      XxqpPdftQplabsHeaderVOImpl xxqpPdftQplabsHeaderVOImpl = null; 
      XxqpPdftQplabsHeaderVORowImpl xxqpPdftQplabsHeaderVORowImpl = null; 
      xxqpPdftQplabsHeaderVOImpl = qpLabsAMImpl.getXxqpPdftQplabsHeaderVO1(); 
      RowSetIterator QplabsHeaderIterator = xxqpPdftQplabsHeaderVOImpl.createRowSetIterator(null); 
      if(QplabsHeaderIterator.hasNext()){
        xxqpPdftQplabsHeaderVORowImpl = (XxqpPdftQplabsHeaderVORowImpl)QplabsHeaderIterator.next();
      }
      QplabsHeaderIterator.closeRowSetIterator();
    
    if("ModificarEvt".equals(strEventParam)){
       
        oracle.jbo.domain.Number numRev = xxqpPdftQplabsHeaderVORowImpl.getRev();
        if(null==numRev){
            xxqpPdftQplabsHeaderVORowImpl.setRev(new oracle.jbo.domain.Number(1));
        }else{
            xxqpPdftQplabsHeaderVORowImpl.setRev(numRev.add( new oracle.jbo.domain.Number(1)));
        }
        
        qpLabsAMImpl.getOADBTransaction().commit();
        
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        oracle.jbo.domain.Number numQpLabsHeaderId = xxqpPdftQplabsHeaderVORowImpl.getId();
        parameters.put("pQplabsHeaderId",numQpLabsHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsUpdPG" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,parameters /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );     
                                  
    } /** END if("ModificarEvt".equals(strEventParam)){ **/
    
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
                                                 AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
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
        
        if("EnviarPorCorreoEvt".equals(strEventParam)){
         
            String strXML = null;
            strXML = qpLabsAMImpl.executeQplabsGetInfo();
            try {
                byte[] aByte = strXML.getBytes();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
                ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
                AppsContext appsContext = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getAppsContext();
                Locale locale = ((OADBTransactionImpl)qpLabsAMImpl.getOADBTransaction()).getUserLocale();
                TemplateHelper.processTemplate(appsContext, 
                                               AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
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
                
               return;
               
            } catch (IOException e) {
               throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
            } catch (SQLException e) {
                throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
            } catch (XDOException e) {
                throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
            }
         
        } /** END if("EnviarPorCorreoEvt".equals(strEventParam)){ **/
 
     if("CopiarEvt".equals(strEventParam)){
         OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui.QpLabsCopiarCO"); 
         oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
         oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
         oAProcessingPage.setProcessName("<Process Name>"); 
         pageContext.forwardToProcessingPage(oAProcessingPage);  
     }
     
     if("CancelarFTEvt".equals(strEventParam)){
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsCancelPG" /*url*/
                                   ,null /*functionName*/
                                   ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                   ,null /*menuName*/
                                   ,null /*parameters*/
                                   ,true /*retainAM*/
                                   ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                   ,OAException.ERROR /*messagingLevel*/
                                   );     
                                   
        return; 
     }
     
  }
 
}

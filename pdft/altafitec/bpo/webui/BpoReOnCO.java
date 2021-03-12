/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui;

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
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OATableLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;
import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;
import oracle.apps.fnd.framework.webui.beans.table.OATableBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.domain.Number;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.BpoAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPrecioVORowImpl;

/**
 * Controller for ...
 */
public class BpoReOnCO extends OAControllerImpl
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
    
      String strPuserPdft = null; 
      System.out.println("BpoReOnCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("BpoReOnCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("BpoReOnCO strPuserPdftId:"+strPuserPdftId);
      }
      
      if(null==strPuserPdft||null==strPuserPdftId||"".equals(strPuserPdft)||"".equals(strPuserPdftId)){
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/LoginPdftPG" /*url*/
                                   ,null /*functionName*/
                                   ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                   ,null /*menuName*/
                                   ,null /*parameters*/
                                   ,false /*retainAM*/
                                   ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                   ,OAException.ERROR /*messagingLevel*/
                                   );
      return;
      }
      
      environmentCurrencyFormat(pageContext,webBean);
      
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
      OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN"); 
      
      if(null!=returnNavigation)
          returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaDeFichaTecnicaPG");
          
      if(null!=NombreUsuarioEBSBean){
           /** NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());  110320211527 **/
            NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft); 
       }
       
    BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
    
    XxqpPdftBpoHeaderVORowImpl xxqpPdftBpoHeaderVORowImpl = null; 
    XxqpPdftBpoPrecioVORowImpl xxqpPdftBpoPrecioVORowImpl = null; 
    
      String strBpoHeaderId = pageContext.getParameter("pBpoHeaderId");
      String strBpoExtern =  pageContext.getParameter("pBpoExtern");
      pageContext.writeDiagnostics(this,"strBpoHeaderId:"+strBpoHeaderId,OAFwkConstants.STATEMENT);
      pageContext.writeDiagnostics(this,"strBpoExtern:"+strBpoExtern,OAFwkConstants.STATEMENT);
      
      try{
      oracle.jbo.domain.Number numBpoHeaderId = new oracle.jbo.domain.Number(strBpoHeaderId);
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
        throw new OAException("Ocurrio una Excepcion al recuperar el parametro numBpoHeaderId:"+strBpoHeaderId,OAException.ERROR);     
      }
      
    if(!pageContext.isFormSubmission()){
      if(null!=bpoAMImpl){
          
          xxqpPdftBpoHeaderVORowImpl = bpoAMImpl.initBpoHeaderVO(strBpoHeaderId);
          xxqpPdftBpoPrecioVORowImpl = bpoAMImpl.initBpoPrecioVO(strBpoHeaderId);
          bpoAMImpl.initBpoServicioVO(strBpoHeaderId);
          bpoAMImpl.intiAllBpoRequeAdicioVOS(strBpoHeaderId); 
          bpoAMImpl.initBpoPagoVO(strBpoHeaderId); 
          bpoAMImpl.initReglasDeNegocioVO(strBpoHeaderId);
          
          String strUnidadDeNegocioM = xxqpPdftBpoHeaderVORowImpl.getUnidadDeNegocioM();
          
          if(null==PageLayoutRNBean){
           PageLayoutRNBean = pageContext.getPageLayoutBean();
          }
          if(null!=PageLayoutRNBean){
            if(null!=strUnidadDeNegocioM){
              PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strUnidadDeNegocioM);
              PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strUnidadDeNegocioM);
            }
          }
          
      }
    }else{
    
    }
    
    if(null==xxqpPdftBpoHeaderVORowImpl){
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
        throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftBpoHeaderVO.",OAException.ERROR);
    }
    if(null==xxqpPdftBpoPrecioVORowImpl){
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
        throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftBpoPrecioVO.",OAException.ERROR);
    }
    
    if(null!=xxqpPdftBpoHeaderVORowImpl.getStatus()){
     if("CANCELADA".equals(xxqpPdftBpoHeaderVORowImpl.getStatus())){
         if(null!=CancelarFTBtnBean)
         CancelarFTBtnBean.setDisabled(true);
         if(null!=ModificarBtnBean)
         ModificarBtnBean.setDisabled(true);
         if(null!=EnviarPorCorreoBtnBean)
         EnviarPorCorreoBtnBean.setDisabled(true);
        }else if("ABIERTA".equals(xxqpPdftBpoHeaderVORowImpl.getStatus())){
            if(null!=ModificarBtnBean)
            ModificarBtnBean.setDisabled(true);
        }else if("PRELIMINAR".equals(xxqpPdftBpoHeaderVORowImpl.getStatus())){
            if(null!=EnviarPorCorreoBtnBean)
            EnviarPorCorreoBtnBean.setDisabled(true);
        }
    }
    
      if("Y".equals(strBpoExtern)){
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
          /** RevisarPDFBtnBean.setRendered(false);  011220201018**/
           RevisarPDFBtnBean.setRendered(true);
          if(null!=returnNavigation)
              returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/ConsultaFichaTecnicaPG");
      } 
    
      if("Y".equals(pageContext.getParameter("pBpoCopiar"))){
          OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui.BpoCopiarCO"); 
          oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
          oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
          oAProcessingPage.setProcessName("<Process Name>"); 
          pageContext.forwardToProcessingPage(oAProcessingPage);  
      }
    
      if("Y".equals(pageContext.getParameter("pBpoCambioDePrecio"))){
          xxqpPdftBpoHeaderVORowImpl.setStatus("CERRADA");
          pageContext.putSessionValue("sCambioDePrecio","Y");
          OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui.BpoCopiarCO"); 
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
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    
    BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftBpoHeaderVORowImpl xxqpPdftBpoHeaderVORowImpl = null; 
    xxqpPdftBpoHeaderVORowImpl = (XxqpPdftBpoHeaderVORowImpl)bpoAMImpl.getXxqpPdftBpoHeaderVO1().getCurrentRow();
      
    if("ModificarEvt".equals(strEventParam)){
        /** Creacion Numeracion de las revisiones **/
        oracle.jbo.domain.Number numRev = null; 
        numRev = xxqpPdftBpoHeaderVORowImpl.getRev();
        if(null==numRev){
            xxqpPdftBpoHeaderVORowImpl.setRev(new oracle.jbo.domain.Number(1));
        }else{
            numRev = xxqpPdftBpoHeaderVORowImpl.getRev().add(new oracle.jbo.domain.Number(1)); 
            xxqpPdftBpoHeaderVORowImpl.setRev(numRev);
        }
        bpoAMImpl.getOADBTransaction().commit();
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        oracle.jbo.domain.Number numBpoHeaderId =  (oracle.jbo.domain.Number)bpoAMImpl.getXxqpPdftBpoHeaderVO1().getCurrentRow().getAttribute("Id");
        parameters.put("pBpoHeaderId",numBpoHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
          String contentDisposition = "attachment;filename=AltaFichaTecnicaBpo.pdf";
          response.setHeader("Content-Disposition",contentDisposition);
          response.setContentType("application/pdf");
          ServletOutputStream os=null;
         
           String strXML = null;
              strXML = bpoAMImpl.executeBpoGetInfo();
              try {
                  os = response.getOutputStream();
                  byte[] aByte = strXML.getBytes();
                  ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
                  ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
                  AppsContext appsContext = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getAppsContext();
                  Locale locale = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getUserLocale();
                  TemplateHelper.processTemplate(appsContext, 
                                                 AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                 "XXQP_PDFT_BPO", 
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
        strXML = bpoAMImpl.executeBpoGetInfo();
        try {
            byte[] aByte = strXML.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
            ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
            AppsContext appsContext = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getAppsContext();
            Locale locale = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getUserLocale();
            TemplateHelper.processTemplate(appsContext, 
                                           AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                           "XXQP_PDFT_BPO", 
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
            oracle.jbo.domain.Number numBpoNumeroFtReferencia =  (oracle.jbo.domain.Number)xxqpPdftBpoHeaderVORowImpl.getAttribute("NumeroFtReferencia");
            oracle.jbo.domain.Number numBpoNumeroFt =  (oracle.jbo.domain.Number)xxqpPdftBpoHeaderVORowImpl.getAttribute("NumeroFt");
            String strNombreCliente = (String)xxqpPdftBpoHeaderVORowImpl.getAttribute("NombreDelCliente");
            String strArticuloOracle = (String)xxqpPdftBpoHeaderVORowImpl.getAttribute("ArticuloOracle");
            if(null==numBpoNumeroFtReferencia){
                numBpoNumeroFtReferencia = new oracle.jbo.domain.Number(0);
            }
            if(null==numBpoNumeroFt){
                numBpoNumeroFt = new oracle.jbo.domain.Number(0);
            }
            String strStatusFT = (String)bpoAMImpl.getXxqpPdftBpoHeaderVO1().getCurrentRow().getAttribute("Status");
            System.out.println("strStatusFT:"+strStatusFT);
            String strCorreos = bpoAMImpl.enviaCorreos(inputStream2
                                                      ,numBpoNumeroFtReferencia
                                                      ,strStatusFT
                                                      ,numBpoNumeroFt
                                                      ,pageContext
                                                      ,strNombreCliente
                                                      ,strArticuloOracle
                                                      ,xxqpPdftBpoHeaderVORowImpl
                                                      ); 
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
           OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui.BpoCopiarCO"); 
           oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
           oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
           oAProcessingPage.setProcessName("<Process Name>"); 
           pageContext.forwardToProcessingPage(oAProcessingPage);  
       }   
       
      if("CancelarFTEvt".equals(strEventParam)){
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoCancelPG"
                                    ,null /*functionName*/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                    ,null /*menuName*/
                                    ,null /*parameters*/
                                    ,true /*retainAM*/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                    ,OAException.ERROR /*messagingLevel*/
                                    ); 
         return; 
         
      } /** END if("CancelarFTEvt".equals(strEventParam)){ **/
      
    
  }

    private void environmentCurrencyFormat(OAPageContext pageContext, 
                                           OAWebBean webBean) {
        OATableBean PrecioTmpBean = (OATableBean)webBean.findChildRecursive("PrecioTmp");
        if(null!=PrecioTmpBean){
          OAMessageTextInputBean PrecioUnitarioBean = (OAMessageTextInputBean)PrecioTmpBean.findChildRecursive("PrecioUnitario");
          OAMessageTextInputBean TotalPorConceptoBean = (OAMessageTextInputBean)PrecioTmpBean.findChildRecursive("TotalPorConcepto");  
          if(null!=PrecioUnitarioBean){
              PrecioUnitarioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          if(null!=TotalPorConceptoBean){
              TotalPorConceptoBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          
        }
        
        OAMessageTextInputBean SubtotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Subtotal");
        OAMessageTextInputBean IVABean = (OAMessageTextInputBean)webBean.findChildRecursive("IVA");
        OAMessageTextInputBean TotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Total");
        if(null!=SubtotalBean){
            SubtotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        if(null!=IVABean){
            IVABean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        System.out.println("TotalBean:"+TotalBean);
        if(null!=TotalBean){
            TotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }                  
        
        OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioBpoVORN");
        if(null!=ReglasDeNegocioVORNBean){
          OAMessageTextInputBean PrecioRegNegBean = (OAMessageTextInputBean)ReglasDeNegocioVORNBean.findChildRecursive("PrecioRegNeg");
          if(null!=PrecioRegNegBean){
              PrecioRegNegBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
        }
        
    }
}

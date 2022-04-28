/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

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

import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OATableLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;
import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;
import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.fnd.framework.webui.beans.table.OATableBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypGeneralVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVORowImpl;

/**
 * Controller for ...
 */
public class MasivoYPlatinumReOnCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      String strPuserPdft = null; 
      System.out.println("MasivoYPlatinumReOnCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("MasivoYPlatinumReOnCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("MasivoYPlatinumReOnCO strPuserPdftId:"+strPuserPdftId);
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
    
      OAWebBean body = pageContext.getRootWebBean(); 
       if (body instanceof OABodyBean){
          ((OABodyBean)body).setBlockOnEverySubmit(true); 
       }
    
    OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN");   
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
    
      environmentCurrencyFormat(pageContext,webBean);
    
    if(null!=returnNavigation)
        returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaDeFichaTecnicaPG");
      
    if(null!=NombreUsuarioEBSBean){
         /** NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName()); **/
         NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
     }
      
      MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
      XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = null;
      XxqpPdftMypGeneralVORowImpl xxqpPdftMypGeneralVORowImpl  = null;
      
      String strMyPHeaderId =  pageContext.getParameter("pMyPHeaderId");
      String strMyPExtern =  pageContext.getParameter("pMyPExtern");
      pageContext.writeDiagnostics(this,"strMyPHeaderId:"+strMyPHeaderId,OAFwkConstants.STATEMENT);
      pageContext.writeDiagnostics(this,"strMyPExtern:"+strMyPExtern,OAFwkConstants.STATEMENT);
      System.out.println("strMyPHeaderId:"+strMyPHeaderId);
      
      try{
      oracle.jbo.domain.Number numMyPHeaderId = new oracle.jbo.domain.Number(strMyPHeaderId);
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
        throw new OAException("Ocurrio una Excepcion al recuperar el parametro numMyPHeaderId:"+strMyPHeaderId,OAException.ERROR);     
      }
      
      if(!pageContext.isFormSubmission()){
          xxqpPdftMypHeaderVORowImpl = masivoYPlatinumAMImpl.initMypHeaderVO(strMyPHeaderId);
          xxqpPdftMypGeneralVORowImpl = masivoYPlatinumAMImpl.initMypGeneralVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypCoberturaVO(strMyPHeaderId); 
          masivoYPlatinumAMImpl.initMypDistribucionVO(strMyPHeaderId); 
          masivoYPlatinumAMImpl.initMypProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypOtrosProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypComentsProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initReglasDeNegocioVO(strMyPHeaderId);
      }else{
          /** Se Agrega porque falla cuando se conuslta pdf y despues se envia por correo 13052021 **/
          xxqpPdftMypHeaderVORowImpl = masivoYPlatinumAMImpl.initMypHeaderVO(strMyPHeaderId);
          xxqpPdftMypGeneralVORowImpl = masivoYPlatinumAMImpl.initMypGeneralVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypCoberturaVO(strMyPHeaderId); 
          masivoYPlatinumAMImpl.initMypDistribucionVO(strMyPHeaderId); 
          masivoYPlatinumAMImpl.initMypProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypOtrosProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypComentsProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initReglasDeNegocioVO(strMyPHeaderId);
      }
      
      if(null==xxqpPdftMypHeaderVORowImpl){
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
          throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftMypHeaderVO.",OAException.ERROR);
      }  
      String strPUnidadDeNegocioM = xxqpPdftMypHeaderVORowImpl.getUnidadDeNegocioM();
      if(null==PageLayoutRNBean){
       PageLayoutRNBean = pageContext.getPageLayoutBean();
      }
      if(null!=PageLayoutRNBean){
        if(null!=strPUnidadDeNegocioM){
          PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strPUnidadDeNegocioM);
          PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strPUnidadDeNegocioM);
        }
      }
      
      if(null==xxqpPdftMypGeneralVORowImpl){
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
          throw new OAException("No se realizo de manera correcta la inicializacion del View Object PdftMypGeneralVO.",OAException.ERROR);
      }
      String strStatus = null;
      if(null!=xxqpPdftMypHeaderVORowImpl.getStatus()){
          strStatus = xxqpPdftMypHeaderVORowImpl.getStatus();
          if("CANCELADA".equals(strStatus)){
              if(null!=CancelarFTBtnBean)
              CancelarFTBtnBean.setDisabled(true);
              if(null!=ModificarBtnBean)
              ModificarBtnBean.setDisabled(true);
              if(null!=EnviarPorCorreoBtnBean)
              EnviarPorCorreoBtnBean.setDisabled(true);
          }else if("ABIERTA".equals(strStatus)){
              if(null!=ModificarBtnBean)
              ModificarBtnBean.setDisabled(true);
          }else if("PRELIMINAR".equals(strStatus)){
              if(null!=EnviarPorCorreoBtnBean)
              EnviarPorCorreoBtnBean.setDisabled(true);
          }
      }
     
     if("Y".equals(strMyPExtern)){
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
         /** RevisarPDFBtnBean.setRendered(false); 01122020924**/
          RevisarPDFBtnBean.setRendered(true);
         if(null!=returnNavigation)
             returnNavigation.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/ConsultaFichaTecnicaPG");
     } 
     
     if("Y".equals(pageContext.getParameter("pMyPCopiar"))){
         pageContext.putSessionValue("sCambioDePrecio","N");
         pageContext.putSessionValue("sUserPdftId",pageContext.getParameter("pUserPdftId"));
         OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui.MyPcopiarCO"); 
         oAProcessingPage.setConciseMessage("Reglas de Negocio para una copia"); 
         oAProcessingPage.setDetailedMessage("El estatus de la ficha tecnica a copiar no debera moverse, el estatus de la nueva ficha tecnica sera Preliminar y el articulo Oracle no debera copiarse"); 
         oAProcessingPage.setProcessName("<Copia de Ficha Tecnica>"); 
         pageContext.forwardToProcessingPage(oAProcessingPage);  
     }
      
      if("Y".equals(pageContext.getParameter("pMyPCambioDePrecio"))){
          xxqpPdftMypHeaderVORowImpl.setStatus("CERRADA");
          pageContext.putSessionValue("sCambioDePrecio","Y");
          pageContext.putSessionValue("sUserPdftId",pageContext.getParameter("pUserPdftId"));
          OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui.MyPcopiarCO"); 
          oAProcessingPage.setConciseMessage("Reglas de Negocio para un cambio de precio"); 
          oAProcessingPage.setDetailedMessage("El estatus de la ficha a cambiar de precio sera Cerrada, y el estatus de la nueva ficha sera Cambio de Precio, El articulo Oracle se copiara de la anterior a la nueva"); 
          oAProcessingPage.setProcessName("<Cambio de Precio de Ficha Tecnica>"); 
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
     MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM); 
    System.out.println("strEventParam:"+strEventParam);
    if("ModificarEvt".equals(strEventParam)){
        XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = null;
        xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1().getCurrentRow();
        oracle.jbo.domain.Number numRev = xxqpPdftMypHeaderVORowImpl.getRev();
        if(null==numRev){
            xxqpPdftMypHeaderVORowImpl.setRev( new oracle.jbo.domain.Number(1));
        }else{
            xxqpPdftMypHeaderVORowImpl.setRev(numRev.add(new oracle.jbo.domain.Number(1)));
        }
        
        masivoYPlatinumAMImpl.getOADBTransaction().commit();
        
        oracle.jbo.domain.Number numMasiYPlatHeaderId = (oracle.jbo.domain.Number)masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1().getCurrentRow().getAttribute("Id");
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pMyPHeaderId",numMasiYPlatHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
          
              XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1().getCurrentRow();
              String statusMail = xxqpPdftMypHeaderVORowImpl.getStatusMail();
             
              
          DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
          HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
          String contentDisposition = "";
         
         
              String strXML = null;
              if("MODIFICACION".equals(statusMail)){
                  contentDisposition = "attachment;filename=CambioFichaTecnicaMyP.pdf";
                  strXML = masivoYPlatinumAMImpl.executeMypGetInfo("Y");
              }else if("ALTA".equals(statusMail)){
                  contentDisposition = "attachment;filename=AltaFichaTecnicaMyP.pdf";
                  strXML = masivoYPlatinumAMImpl.executeMypGetInfo("N");
              }else{
                  throw new OAException("No se pudo determinar el status mail:"+statusMail,OAException.ERROR); 
              }
             
              response.setHeader("Content-Disposition",contentDisposition);
              response.setContentType("application/pdf");
              ServletOutputStream os=null; 
              ByteArrayInputStream bAiSxml = null; 
              ByteArrayOutputStream bAoSpdfFile = null;
              try {
                  os = response.getOutputStream();
                  byte[] aByte = strXML.getBytes();
                  bAiSxml = new ByteArrayInputStream(aByte);
                  bAoSpdfFile = new ByteArrayOutputStream();
                  AppsContext appsContext = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getAppsContext();
                  Locale locale = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getUserLocale();
                  TemplateHelper.processTemplate(appsContext, 
                                                 AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                 "XXQP_PDFT_MYP", 
                                                 locale.getLanguage(), 
                                                 locale.getCountry(), 
                                                 bAiSxml, 
                                                 TemplateHelper.OUTPUT_TYPE_PDF, 
                                                  null, 
                                                 bAoSpdfFile
                                                 );

                                      byte[] b = bAoSpdfFile.toByteArray();
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
              }finally{
                if(null!=bAoSpdfFile){
                    try {
                        bAoSpdfFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(null!=bAiSxml){
                    try {
                        bAiSxml.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
              
          } /** END if("RevisarPDFEvt".equals(strEventParam)){ **/
          
          if("CopiarEvt".equals(strEventParam)){
           pageContext.putSessionValue("sCambioDePrecio","N");
           OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui.MyPcopiarCO"); 
           oAProcessingPage.setConciseMessage("Reglas de Negocio para una copia"); 
           oAProcessingPage.setDetailedMessage("El estatus de la ficha tecnica a copiar no debera moverse, el estatus de la nueva ficha tecnica sera Preliminar y el articulo Oracle no debera copiarse"); 
           oAProcessingPage.setProcessName("<Copia de Ficha Tecnica>"); 
           pageContext.forwardToProcessingPage(oAProcessingPage);  
          } /** END if("CopiarEvt".equals(strEventParam)){ **/
          
          if("CancelarFTEvt".equals(strEventParam)){
              pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumCancelPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
          
           if("EnviarPorCorreoEvt".equals(strEventParam)){
               XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1().getCurrentRow();
               String statusMail = xxqpPdftMypHeaderVORowImpl.getStatusMail();
               String strXML = null;
               if("MODIFICACION".equals(statusMail)){
                   strXML = masivoYPlatinumAMImpl.executeMypGetInfo("Y");
               }else if("ALTA".equals(statusMail)){
                   strXML = masivoYPlatinumAMImpl.executeMypGetInfo("N");
               }else{
                   throw new OAException("No se pudo determinar el status mail:"+statusMail,OAException.ERROR); 
               }
               
               ByteArrayInputStream bAiSxml = null;
               ByteArrayOutputStream bAoSpdfFile = null; 
               InputStream iSpdfFile = null; 
               try {
                   byte[] aByte = strXML.getBytes();
                   bAiSxml = new ByteArrayInputStream(aByte);
                   bAoSpdfFile = new ByteArrayOutputStream();
                   AppsContext appsContext = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getAppsContext();
                   Locale locale = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getUserLocale();
                   TemplateHelper.processTemplate(appsContext, 
                                                  AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                  "XXQP_PDFT_MYP", 
                                                  locale.getLanguage(), 
                                                  locale.getCountry(), 
                                                  bAiSxml, 
                                                  TemplateHelper.OUTPUT_TYPE_PDF, 
                                                   null, 
                                                  bAoSpdfFile
                                                  );
                  
                   
                   byte[] a2Byte =bAoSpdfFile.toByteArray(); 
                   iSpdfFile = new ByteArrayInputStream(a2Byte);
                   oracle.jbo.domain.Number numMypHeaderId =  (oracle.jbo.domain.Number)xxqpPdftMypHeaderVORowImpl.getAttribute("NumeroFtReferencia");
                   oracle.jbo.domain.Number oracleNumeroFt = (oracle.jbo.domain.Number)xxqpPdftMypHeaderVORowImpl.getAttribute("NumeroFt");
                   String strNombreCliente = (String)xxqpPdftMypHeaderVORowImpl.getAttribute("NombreDelCliente");
                   String strArticuloOracle = (String)xxqpPdftMypHeaderVORowImpl.getAttribute("ArticuloOracle");
                   if(null==numMypHeaderId){
                       numMypHeaderId = new oracle.jbo.domain.Number(0);
                   }
                   String strStatusFT = (String)masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1().getCurrentRow().getAttribute("Status");
                   System.out.println("strStatusFT:"+strStatusFT);
                   String strCorreos = masivoYPlatinumAMImpl.enviaCorreosReOn(iSpdfFile
                                                                         ,numMypHeaderId
                                                                         ,strStatusFT
                                                                         ,oracleNumeroFt
                                                                         ,pageContext
                                                                         ,strNombreCliente
                                                                         ,strArticuloOracle
                                                                         ,xxqpPdftMypHeaderVORowImpl
                                                                         ,statusMail
                                                                         ); 
                   System.out.println("strCorreos:"+strCorreos);
                
                 
                  
               } catch (IOException e) {
                  throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
               } catch (SQLException e) {
                   throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
               } catch (XDOException e) {
                   throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
               }finally{
            
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
                
                if(null!=bAiSxml){
                    try {
                        bAiSxml.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
               
               return;
               
           } /** END if("EnviarPorCorreoEvt".equals(strEventParam)){ **/
           
  }
  
            private void environmentCurrencyFormat(OAPageContext pageContext
                                                  ,OAWebBean webBean
                                                  ) {
             OATableBean ProcesosTmpBean = (OATableBean)webBean.findChildRecursive("ProcesosTmp");
             if(null!=ProcesosTmpBean){
               OAMessageTextInputBean PrecioBean = (OAMessageTextInputBean)ProcesosTmpBean.findChildRecursive("Precio");
               if(null!=PrecioBean){
                   PrecioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
               }
                 OAMessageTextInputBean PrecioProtonBean = (OAMessageTextInputBean)ProcesosTmpBean.findChildRecursive("PrecioProton");
                 if(null!=PrecioProtonBean){
                     PrecioProtonBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                 }
             }
             
                OATableBean OtrosProcesosTmpBean = (OATableBean)webBean.findChildRecursive("OtrosProcesosTmp");
                if(null!=OtrosProcesosTmpBean){
                  OAMessageTextInputBean OtrosPrecioBean = (OAMessageTextInputBean)OtrosProcesosTmpBean.findChildRecursive("OtrosPrecio");
                  if(null!=OtrosPrecioBean){
                      OtrosPrecioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                  }
                    OAMessageTextInputBean OtrosPrecioProtonBean = (OAMessageTextInputBean)OtrosProcesosTmpBean.findChildRecursive("OtrosPrecioProton");
                    if(null!=OtrosPrecioProtonBean){
                        OtrosPrecioProtonBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                    }
                }
                
                OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioVORN");
                if(null!=ReglasDeNegocioVORNBean){
                  OAMessageTextInputBean PrecioRegNegBean = (OAMessageTextInputBean)ReglasDeNegocioVORNBean.findChildRecursive("PrecioRegNeg");
                  if(null!=PrecioRegNegBean){
                      PrecioRegNegBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                  }
                }
               
               OAMessageTextInputBean textInputBean = null; 
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntregaLocal"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DRLocal"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DILocal"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntregaForaneo"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DRForaneo"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
                textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DIForaneo"); 
                if(null!=textInputBean){
                    textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                }
            } /** environmentCurrencyFormat **/
            

}

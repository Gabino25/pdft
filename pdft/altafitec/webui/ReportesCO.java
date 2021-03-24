/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.webui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.sql.SQLException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.common.AppsContext;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.server.AltaFichaTecnicaAMImpl;

/**
 * Controller for ...
 */
public class ReportesCO extends OAControllerImpl
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
      
      String strPuserPdft = null; 
      System.out.println("ReportesCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("ReportesCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("ReportesCO strPuserPdftId:"+strPuserPdftId);
      }
      
      if(null==strPuserPdft||null==strPuserPdftId||"".equals(strPuserPdft)||"".equals(strPuserPdftId)){
          if("Y".equals(pageContext.getParameter("pClienteExtern"))){
              pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/LoginPG" /*url*/
                                        ,null /*functionName*/
                                        ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                        ,null /*menuName*/
                                        ,null /*parameters*/
                                        ,false /*retainAM*/
                                        ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                        ,OAException.ERROR /*messagingLevel*/
                                        );
          }else{
              pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/LoginPdftPG" /*url*/
                                        ,null /*functionName*/
                                        ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                        ,null /*menuName*/
                                        ,null /*parameters*/
                                        ,false /*retainAM*/
                                        ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                        ,OAException.ERROR /*messagingLevel*/
                                        );  
          }
         return;
      }
      
      if(null!=NombreUsuarioEBSBean){
         /** NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName()); **/
          NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
      }
      /** java.lang.ClassCastException: oracle.apps.fnd.framework.webui.beans.nav.OALinkBean**/
      OALinkBean  oALinkBean = (OALinkBean)webBean.findChildRecursive("RegresarPantallaPrincipal");
      if("Y".equals(pageContext.getParameter("pClienteExtern"))){
          if(null==pageContext.getSessionValue("sHasLogin")||"".equals(pageContext.getSessionValue("sHasLogin"))){
              pageContext.putSessionValue("sHasLogin","Y");
          }
       
       /*
        if(null!=oALinkBean){
           oALinkBean.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/PortalPG");
       }
       */
       if(null!=NombreUsuarioEBSBean){
           NombreUsuarioEBSBean.setRendered(false);
       }
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
    String strEventParam =  pageContext.getParameter(this.EVENT_PARAM);
    System.out.println("strEventParam:"+strEventParam);
    OAMessageChoiceBean ReportePorBean = (OAMessageChoiceBean)webBean.findChildRecursive("ReportePor");
    OAMessageLovInputBean ClienteDesdeBean = (OAMessageLovInputBean)webBean.findChildRecursive("ClienteDesde");
    OAMessageLovInputBean ClienteHastaBean = (OAMessageLovInputBean)webBean.findChildRecursive("ClienteHasta");
    OAMessageDateFieldBean FechaDesdeBean = (OAMessageDateFieldBean)webBean.findChildRecursive("FechaDesde");
    OAMessageDateFieldBean FechaHastaBean  = (OAMessageDateFieldBean)webBean.findChildRecursive("FechaHasta");
    OAMessageTextInputBean FichaTecnicaDesdeBean = (OAMessageTextInputBean)webBean.findChildRecursive("FichaTecnicaDesde");
    OAMessageTextInputBean FichaTecnicaHastaBean = (OAMessageTextInputBean)webBean.findChildRecursive("FichaTecnicaHasta");
    OAMessageTextInputBean ProductoDesdeBean = (OAMessageTextInputBean)webBean.findChildRecursive("ProductoDesde");
    OAMessageTextInputBean ProductoHastaBean = (OAMessageTextInputBean)webBean.findChildRecursive("ProductoHasta");
    OAMessageChoiceBean CicloFacturacionDesdeBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacionDesde");
    OAMessageChoiceBean CicloFacturacionHastaBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacionHasta");
    OAMessageChoiceBean EstatusDesdeBean = (OAMessageChoiceBean)webBean.findChildRecursive("EstatusDesde");
    OAMessageChoiceBean EstatusHastaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EstatusHasta");
    OAMessageLovInputBean RazonSocialDesdeBean = (OAMessageLovInputBean)webBean.findChildRecursive("RazonSocialDesde");
    OAMessageLovInputBean RazonSocialHastaBean = (OAMessageLovInputBean)webBean.findChildRecursive("RazonSocialHasta");
      
    String strReportePorText = null; 
    String strReportePorValue = null; 
    String strClienteDesdeText = null; 
    String strClienteHastaText = null; 
    String strFechaDesdeText = null; 
    String strFechaHastaText = null; 
    oracle.jbo.domain.Date dateFechaDesde = null; 
    oracle.jbo.domain.Date dateFechaHasta = null; 
    String strFichaTecnicaDesdeText = null; 
    String strFichaTecnicaHastaText = null; 
    oracle.jbo.domain.Number numFichaTecnicaDesde = null; 
    oracle.jbo.domain.Number numFichaTecnicaHasta = null; 
    String strProductoDesdeText = null; 
    String strProductoHastaText = null; 
    String strCicloFacturacionDesdeValue = null; 
    String strCicloFacturacionHastaValue = null; 
    String strEstatusDesdeValue = null; 
    String strEstatusHastaValue = null; 
    String strRazonSocialDesdeText = null; 
    String strRazonSocialHastaText = null; 
      
     
    
    if(null!=ReportePorBean){
      if(null!=ReportePorBean.getValue(pageContext)){
          strReportePorValue = (String)ReportePorBean.getValue(pageContext);
      }
    }
    
    if(null!=ClienteDesdeBean){
        if(null!=ClienteDesdeBean.getValue(pageContext)){
            strClienteDesdeText = (String)ClienteDesdeBean.getValue(pageContext);
        }
    }
    
      if(null!=ClienteHastaBean){
          if(null!=ClienteHastaBean.getValue(pageContext)){
              strClienteHastaText = (String)ClienteHastaBean.getValue(pageContext);
          }
      }
      
      if(null!=FechaDesdeBean){
        if(null!=FechaDesdeBean.getValue(pageContext)){
            strFechaDesdeText = FechaDesdeBean.getValue(pageContext).toString();
        }
      } 
      
      if(null!=FechaHastaBean){
        if(null!=FechaHastaBean.getValue(pageContext)){
            strFechaHastaText = FechaHastaBean.getValue(pageContext).toString();
        }
      } 
      
      if(null!=FichaTecnicaDesdeBean){
        if(null!=FichaTecnicaDesdeBean.getValue(pageContext)){
            strFichaTecnicaDesdeText = (String)FichaTecnicaDesdeBean.getValue(pageContext);
        }
      }
      
      if(null!=FichaTecnicaHastaBean){
        if(null!=FichaTecnicaHastaBean.getValue(pageContext)){
            strFichaTecnicaHastaText = (String)FichaTecnicaHastaBean.getValue(pageContext);
        }
      }
      
      if(null!=ProductoDesdeBean){
        if(null!=ProductoDesdeBean.getValue(pageContext)){
            strProductoDesdeText = (String)ProductoDesdeBean.getValue(pageContext);
        }
      }
     
      if(null!=ProductoHastaBean){
        if(null!=ProductoHastaBean.getValue(pageContext)){
            strProductoHastaText = (String)ProductoHastaBean.getValue(pageContext);
        }
      } 
      
      if(null!=CicloFacturacionDesdeBean){
        if(null!=CicloFacturacionDesdeBean.getValue(pageContext)){
            strCicloFacturacionDesdeValue = (String)CicloFacturacionDesdeBean.getValue(pageContext);
        }
      }
      if(null!=CicloFacturacionHastaBean){
        if(null!=CicloFacturacionHastaBean.getValue(pageContext)){
            strCicloFacturacionHastaValue = (String)CicloFacturacionHastaBean.getValue(pageContext);
        }
      }
      
      if(null!=EstatusDesdeBean){
          if(null!=EstatusDesdeBean.getValue(pageContext)){
              strEstatusDesdeValue = (String)EstatusDesdeBean.getValue(pageContext);
          }
      }
      if(null!=EstatusHastaBean){
          if(null!=EstatusHastaBean.getValue(pageContext)){
              strEstatusHastaValue = (String)EstatusHastaBean.getValue(pageContext);
          }
      }
     
      if(null!=RazonSocialDesdeBean){
          if(null!=RazonSocialDesdeBean.getValue(pageContext)){
              strRazonSocialDesdeText = (String)RazonSocialDesdeBean.getValue(pageContext);
          }
      }
      
        if(null!=RazonSocialHastaBean){
            if(null!=RazonSocialHastaBean.getValue(pageContext)){
                strRazonSocialHastaText = (String)RazonSocialHastaBean.getValue(pageContext);
            }
        }
        
     System.out.println("strReportePorValue:"+strReportePorValue);
     System.out.println("strClienteDesdeText:"+strClienteDesdeText);
     System.out.println("strClienteHastaText:"+strClienteHastaText);
     System.out.println("strFechaDesdeText:"+strFechaDesdeText);
     System.out.println("strFechaHastaText:"+strFechaHastaText);
     System.out.println("strFichaTecnicaDesdeText:"+strFichaTecnicaDesdeText);
     System.out.println("strFichaTecnicaHastaText:"+strFichaTecnicaHastaText);
     System.out.println("strProductoDesde:"+strProductoDesdeText);
     System.out.println("strProductoHasta:"+strProductoHastaText);
     System.out.println("strCicloFacturacionDesdeValue:"+strCicloFacturacionDesdeValue);
     System.out.println("strCicloFacturacionHastaValue:"+strCicloFacturacionHastaValue);
     System.out.println("strEstatusDesdeValue:"+strEstatusDesdeValue);
     System.out.println("strEstatusHastaValue:"+strEstatusHastaValue);
     System.out.println("strRazonSocialDesdeText:"+strRazonSocialDesdeText);
     System.out.println("strRazonSocialHastaText:"+strRazonSocialHastaText);
      
      
     if(null!=strFechaDesdeText){
         dateFechaDesde = new oracle.jbo.domain.Date(strFechaDesdeText);
     } 
     if(null!=strFechaHastaText){
         dateFechaHasta = new oracle.jbo.domain.Date(strFechaHastaText);
     }
     
     if(null!=strFichaTecnicaDesdeText){
            try {
                numFichaTecnicaDesde = new oracle.jbo.domain.Number(strFichaTecnicaDesdeText);
            } catch (SQLException e) {
                throw new OAException("SQLException al covertir strFichaTecnicaDesdeText:"+strFichaTecnicaDesdeText,OAException.ERROR); 
            }
        }
     if(null!=strFichaTecnicaHastaText){
         try {
             numFichaTecnicaHasta = new oracle.jbo.domain.Number(strFichaTecnicaHastaText);
         } catch (SQLException e) {
             throw new OAException("SQLException al covertir strFichaTecnicaHastaText:"+strFichaTecnicaHastaText,OAException.ERROR); 
         }
     }
      
     AltaFichaTecnicaAMImpl altaFichaTecnicaAMImpl = (AltaFichaTecnicaAMImpl)pageContext.getApplicationModule(webBean);
     if("LimpiarEvt".equals(strEventParam)){
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/ReportesPG" /*url*/
                                   ,null /*functionName*/
                                   ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                   ,null /*menuName*/
                                   ,null /*parameters*/
                                   ,false /*retainAM*/
                                   ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                   ,OAException.ERROR /*messagingLevel*/
                                   );    
       return;
     } /** END if("LimpiarEvt".equals(strEventParam)){ **/
     if("EjecutarEvt".equals(strEventParam)){
      if(null!=altaFichaTecnicaAMImpl){
          String strXML = null;
          strXML = altaFichaTecnicaAMImpl.generaPdftReporte(strReportePorValue
                                                           ,strClienteDesdeText
                                                           ,strClienteHastaText
                                                           ,dateFechaDesde
                                                           ,dateFechaHasta
                                                           ,numFichaTecnicaDesde
                                                           ,numFichaTecnicaHasta
                                                           ,strProductoDesdeText
                                                           ,strProductoHastaText
                                                           ,strCicloFacturacionDesdeValue
                                                           ,strCicloFacturacionHastaValue
                                                           ,strEstatusDesdeValue
                                                           ,strEstatusHastaValue
                                                           ,strRazonSocialDesdeText
                                                           ,strRazonSocialHastaText
                                                           );
           if(null!=strXML){
            if(strXML.contains("Excepcion")){
              throw new OAException(strXML,OAException.ERROR); 
            }
           }                                                
                                                           
          DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
          HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
          String contentDisposition = "attachment;filename=ReportesPdftPor"+strReportePorValue+"_"+System.currentTimeMillis()+".xls";  
         /** String contentDisposition = "attachment;filename=ReportesPdftPor"+strReportePorValue+".pdf";  **/
          response.setHeader("Content-Disposition",contentDisposition);
         /** response.setContentType("application/pdf");  **/
          response.setContentType("application/MSEXCEL"); 
          ServletOutputStream os=null;
          try {
              os = response.getOutputStream();
              byte[] aByte = strXML.getBytes();
              ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
              ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
              AppsContext appsContext = ((OADBTransactionImpl)altaFichaTecnicaAMImpl.getOADBTransaction()).getAppsContext();
              Locale locale = ((OADBTransactionImpl)altaFichaTecnicaAMImpl.getOADBTransaction()).getUserLocale();
              TemplateHelper.processTemplate(appsContext, 
                                             AltaFichaTecnicaUtils.strShortApplication ,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                             "XXQP_PDFT_REPORTES", 
                                             locale.getLanguage(), 
                                             locale.getCountry(), 
                                             inputStream, 
                                           /**  TemplateHelper.OUTPUT_TYPE_PDF, **/
                                             TemplateHelper.OUTPUT_TYPE_EXCEL,
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
          
      }
     }
     
     if("RegresarPortalEvt".equals(strEventParam)){
        
         com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
         if(null!=pageContext.getSessionValue("sHasLogin")&&!"".equals(pageContext.getSessionValue("sHasLogin"))){
          parameters.put("pClienteExtern","Y");
         }
         if(null!=pageContext.getTransientSessionValue("tsUserPdft")&&!"".equals(pageContext.getTransientSessionValue("tsUserPdft"))){
          parameters.put("pUserPdft",pageContext.getTransientSessionValue("tsUserPdft"));
         }
         if(null!=pageContext.getTransientSessionValue("tsUserPdftId")&&!"".equals(pageContext.getTransientSessionValue("tsUserPdftId"))){
          parameters.put("pUserPdftId",pageContext.getTransientSessionValue("tsUserPdftId"));
         }
         
         if(null!=pageContext.getSessionValue("sHasLogin")&&!"".equals(pageContext.getSessionValue("sHasLogin"))){
             pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/PortalPG" /*url*/
                                       ,null /*functionName*/
                                       ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                       ,null /*menuName*/
                                       ,parameters /*parameters*/
                                       ,true /*retainAM*/
                                       ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                       ,OAException.ERROR /*messagingLevel*/
                                       );    
         }else{
             pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/PortalDeFichaTecnicaPG" /*url*/
                                       ,null /*functionName*/
                                       ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                       ,null /*menuName*/
                                       ,parameters /*parameters*/
                                       ,true /*retainAM*/
                                       ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                       ,OAException.ERROR /*messagingLevel*/
                                       );    
         }
         return;
     }
    
  }

}

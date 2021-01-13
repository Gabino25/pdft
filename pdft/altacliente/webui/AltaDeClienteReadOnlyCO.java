/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altacliente.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OATableLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;
import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAPageButtonBarBean;

import xxqp.oracle.apps.ar.pdft.altacliente.Utils;
import xxqp.oracle.apps.ar.pdft.altacliente.server.AltaDeClienteAMImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesContactosVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesDirFiscalVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesFactPagVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesPuntoRecoVORowImpl;

/**
 * Controller for ...
 */
public class AltaDeClienteReadOnlyCO extends OAControllerImpl
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
      OATableLayoutBean HeaderTabLayBean = (OATableLayoutBean)webBean.findChildRecursive("HeaderTabLay");
      OASubTabLayoutBean SubTabLayoutRNBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
      OAPageButtonBarBean PageButtonBarRNBean = (OAPageButtonBarBean)webBean.findChildRecursive("PageButtonBarRN");
      OAButtonBean ModificarBtnBean = (OAButtonBean)webBean.findChildRecursive("ModificarBtn");
      OALinkBean RegresarConsultaDeClientesBean = (OALinkBean)webBean.findChildRecursive("RegresarConsultaDeClientes");
      
      if(null!=NombreUsuarioEBSBean){
          NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
      }
      
      if(null!=RegresarConsultaDeClientesBean){
          RegresarConsultaDeClientesBean.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG");
      }
    
    AltaDeClienteAMImpl altaDeClienteAMImpl = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
    
    String strClientesHeaderId =  pageContext.getParameter("pClientesHeaderId"); 
    String strClienteExtern =  pageContext.getParameter("pClienteExtern");  
    String strOperatingUnit =  pageContext.getParameter("pOperatingUnit");  
      
    if(null==strClientesHeaderId||"".equals(strClientesHeaderId)){
      if(null!=HeaderTabLayBean)
          HeaderTabLayBean.setRendered(false);
      if(null!=SubTabLayoutRNBean)
          SubTabLayoutRNBean.setRendered(false);
      if(null!=PageButtonBarRNBean)
          PageButtonBarRNBean.setRendered(false);
        throw new OAException("Parametro pStrClientesHeaderId Vacio. Reportar falla al administrador del sistema.",OAException.ERROR);     
    }
     
      XxqpPdftClientesHeaderVORowImpl  xxqpPdftClientesHeaderVORowImpl = null; 
      XxqpPdftClientesDirFiscalVORowImpl xxqpPdftClientesDirFiscalVORowImpl= null;
      XxqpPdftClientesPuntoRecoVORowImpl xxqpPdftClientesPuntoRecoVORowImpl = null;
      XxqpPdftClientesContactosVORowImpl xxqpPdftClientesContactosVORowImpl = null; 
      XxqpPdftClientesFactPagVORowImpl xxqpPdftClientesFactPagVORowImpl = null; 
    if(!pageContext.isFormSubmission()){
        xxqpPdftClientesHeaderVORowImpl = altaDeClienteAMImpl.initClientesHeaderReOnVO(strClientesHeaderId);
        xxqpPdftClientesDirFiscalVORowImpl = altaDeClienteAMImpl.initClientesDirFiscalReOnVO(strClientesHeaderId,strOperatingUnit);
        xxqpPdftClientesPuntoRecoVORowImpl = altaDeClienteAMImpl.initClientesPuntoRecolecReOnVO(strClientesHeaderId);
        xxqpPdftClientesContactosVORowImpl = altaDeClienteAMImpl.initClientesContactosReOnVO(strClientesHeaderId);
        xxqpPdftClientesFactPagVORowImpl = altaDeClienteAMImpl.initClientesFactPagoReOnVO(strClientesHeaderId);
    }else{
    
    }
    if(null==xxqpPdftClientesHeaderVORowImpl){
        if(null!=HeaderTabLayBean)
            HeaderTabLayBean.setRendered(false);
        if(null!=SubTabLayoutRNBean)
            SubTabLayoutRNBean.setRendered(false);
        if(null!=PageButtonBarRNBean)
            PageButtonBarRNBean.setRendered(false);
          throw new OAException("No se pudo Inicializar el View Object PdftClientesHeaderVO. Reportar falla al administrador del sistema.",OAException.ERROR);
    }else{
       if(null!=xxqpPdftClientesHeaderVORowImpl.getAttribute1()){
        String strAttribute1 = xxqpPdftClientesHeaderVORowImpl.getAttribute1(); 
        if(strAttribute1.contains("Failed")||strAttribute1.contains("failed")){
            if(null!=HeaderTabLayBean)
                HeaderTabLayBean.setRendered(false);
            if(null!=SubTabLayoutRNBean)
                SubTabLayoutRNBean.setRendered(false);
            if(null!=PageButtonBarRNBean)
                PageButtonBarRNBean.setRendered(false);
              throw new OAException(strAttribute1,OAException.ERROR);

        }
       }
    } /** END if(null==xxqpPdftClientesHeaderVORowImpl){ **/
    
      if(null==xxqpPdftClientesDirFiscalVORowImpl){
          if(null!=HeaderTabLayBean)
              HeaderTabLayBean.setRendered(false);
          if(null!=SubTabLayoutRNBean)
              SubTabLayoutRNBean.setRendered(false);
          if(null!=PageButtonBarRNBean)
              PageButtonBarRNBean.setRendered(false);
            throw new OAException("No se pudo Inicializar el View Object PdftClientesDirFiscalVO. Reportar falla al administrador del sistema.",OAException.ERROR);
      }
      if(null==xxqpPdftClientesPuntoRecoVORowImpl){
          if(null!=HeaderTabLayBean)
              HeaderTabLayBean.setRendered(false);
          if(null!=SubTabLayoutRNBean)
              SubTabLayoutRNBean.setRendered(false);
          if(null!=PageButtonBarRNBean)
              PageButtonBarRNBean.setRendered(false);
            throw new OAException("No se pudo Inicializar el View Object PdftClientesPuntoRecoVO. Reportar falla al administrador del sistema.",OAException.ERROR);

      }
      if(null==xxqpPdftClientesContactosVORowImpl){
          if(null!=HeaderTabLayBean)
              HeaderTabLayBean.setRendered(false);
          if(null!=SubTabLayoutRNBean)
              SubTabLayoutRNBean.setRendered(false);
          if(null!=PageButtonBarRNBean)
              PageButtonBarRNBean.setRendered(false);
            throw new OAException("No se pudo Inicializar el View Object PdftClientesContactosVO. Reportar falla al administrador del sistema.",OAException.ERROR);

      }
      
      if(null==xxqpPdftClientesFactPagVORowImpl){
          if(null!=HeaderTabLayBean)
              HeaderTabLayBean.setRendered(false);
          if(null!=SubTabLayoutRNBean)
              SubTabLayoutRNBean.setRendered(false);
          if(null!=PageButtonBarRNBean)
              PageButtonBarRNBean.setRendered(false);
            throw new OAException("No se pudo Inicializar el View Object PdftClientesFactPagVO. Reportar falla al administrador del sistema.",OAException.ERROR);

      }
      
     if("Y".equals(strClienteExtern)){
        if(null!=ModificarBtnBean){
            ModificarBtnBean.setRendered(false);
        }
         if(null!=RegresarConsultaDeClientesBean){
             RegresarConsultaDeClientesBean.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG&pClienteExtern=Y");
         }
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
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    AltaDeClienteAMImpl altaDeClienteAMImpl = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
      
    if("ModificarEvt".equals(strEventParam)){
        oracle.jbo.domain.Number numClienteHeaderId = (oracle.jbo.domain.Number)altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1().getCurrentRow().getAttribute("Id");
        String strPrimOperatingUnit  = (String)altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1().getCurrentRow().getAttribute("PrimOperatingUnit");
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pClientesHeaderId",numClienteHeaderId.toString());
        parameters.put("pOperatingUnit",strPrimOperatingUnit);
        System.out.println("strPrimOperatingUnit:"+strPrimOperatingUnit);
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,parameters /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  ); 
        return;                           
    }
    if("RevisarPDFEvt".equals(strEventParam)){
        Utils.revisarPDF(altaDeClienteAMImpl,pageContext);
    }
    
  }

}

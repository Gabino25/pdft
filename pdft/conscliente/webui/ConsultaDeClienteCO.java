/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.conscliente.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAProcessingPage;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.nav.OALinkBean;

import xxqp.oracle.apps.ar.pdft.conscliente.server.ConsultaDeClienteAMImpl;

/**
 * Controller for ...
 */
public class ConsultaDeClienteCO extends OAControllerImpl
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
      if(null!=NombreUsuarioEBSBean){
          NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
      }
      
      OALinkBean RegresarPantallaPrincipalBean = (OALinkBean)webBean.findChildRecursive("RegresarPantallaPrincipal");
      if(null!=RegresarPantallaPrincipalBean)
          RegresarPantallaPrincipalBean.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/PortalDeFichaTecnicaPG");
      
      OAFormValueBean FvClienteExternBean = (OAFormValueBean)webBean.findChildRecursive("FvClienteExtern");
      
      String strClienteExtern = pageContext.getParameter("pClienteExtern"); 
      System.out.println("strClienteExtern:"+strClienteExtern);
      if("Y".equals(strClienteExtern)){
          if(null!=RegresarPantallaPrincipalBean)
              RegresarPantallaPrincipalBean.setDestination("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/PortalPG");
          if(null!=FvClienteExternBean)
              FvClienteExternBean.setValue(pageContext,"Y");
          if(null!=NombreUsuarioEBSBean)
              NombreUsuarioEBSBean.setRendered(false);
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
    String strNombreDelCliente = null; 
    String strRFC = null;
    String strRazonSocial = null;
    String strEstado = null;
    
    OAFormValueBean FvClienteExternBean = (OAFormValueBean)webBean.findChildRecursive("FvClienteExtern");
    String strFvClienteExtern = null; 
    if(null!=FvClienteExternBean){
       if(null!=FvClienteExternBean.getValue(pageContext)){
       strFvClienteExtern =  FvClienteExternBean.getValue(pageContext).toString();
       }
    }
      
    ConsultaDeClienteAMImpl consultaDeClienteAM = (ConsultaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
    
    OAMessageLovInputBean NombreDelClienteBean = (OAMessageLovInputBean)webBean.findChildRecursive("NombreDelCliente");
    if(null!=NombreDelClienteBean){
     if(null!=NombreDelClienteBean.getValue(pageContext)){
      strNombreDelCliente = NombreDelClienteBean.getValue(pageContext).toString();
     }
    }
    
    OAMessageLovInputBean RFCBean = (OAMessageLovInputBean)webBean.findChildRecursive("RFC");
    if(null!=RFCBean){
      if(null!=RFCBean.getValue(pageContext)){
        strRFC = RFCBean.getValue(pageContext).toString();
      }
    }
    
    OAMessageLovInputBean RazonSocialBean = (OAMessageLovInputBean)webBean.findChildRecursive("RazonSocial");
    if(null!=RazonSocialBean){
      if(null!=RazonSocialBean.getValue(pageContext)){
        strRazonSocial = RazonSocialBean.getValue(pageContext).toString();
      }
    }
    
    OAMessageLovInputBean EstadoBean = (OAMessageLovInputBean)webBean.findChildRecursive("Estado");
    if(null!=EstadoBean){
      if(null!=EstadoBean.getValue(pageContext)){
        strEstado = EstadoBean.getValue(pageContext).toString();
      }
    }
    
    System.out.println("strNombreDelCliente:"+strNombreDelCliente);
    System.out.println("strRFC:"+strRFC);
    System.out.println("strRazonSocial:"+strRazonSocial);
    System.out.println("strEstado:"+strEstado);
    
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    System.out.println("strEventParam:"+strEventParam);
    String strOperatingUnit = pageContext.getParameter("OperatingUnit");
    System.out.println("strOperatingUnit:"+strOperatingUnit);
      
    if("BuscarEvt".equals(strEventParam)){
    if(null!=consultaDeClienteAM){
        
      String getval[] = consultaDeClienteAM.validaClientesInfo(strNombreDelCliente
                                            ,strRFC
                                            ,strRazonSocial
                                            ,strEstado
                                            ,strOperatingUnit
                                              );
      if("2".equals(getval[1])){
          consultaDeClienteAM.cleanClientesInfoVO();
        throw new OAException(getval[0],OAException.ERROR);
      }else{
          consultaDeClienteAM.filterClientesInfoVO(strNombreDelCliente
                                                  ,strRFC
                                                  ,strRazonSocial
                                                  ,strEstado
                                                  ,strOperatingUnit
                                                  ); 
          throw new OAException(getval[0],OAException.INFORMATION);
      }
        
      
    }
    } /** END if("BuscarEvt".equals(strEventParam)){ **/
    
    if("LimpiarEvt".equals(strEventParam)){
      if(null!=consultaDeClienteAM){
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG" /*url*/
                                    ,null /*functionName*/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                    ,null /*menuName*/
                                    ,null /*parameters*/
                                    ,false /*retainAM*/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                    ,OAException.ERROR /*messagingLevel*/
                                    );
        if(1==2){
        consultaDeClienteAM.cleanClientesInfoVO(); 
        }
      }
    } /** END if("LimpiarEvt".equals(strEventParam)){ **/
    
    if("ConsultarTodoEvt".equals(strEventParam)){
        consultaDeClienteAM.ConsultarTodoClientesInfoVO();
    }/** END if("ConsultarTodoEvt".equals(strEventParam)){ **/
    
    if("NuevoEvt".equals(strEventParam)){
      pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClientePG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                ,null /*functionName*/
                                ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                ,null /*menuName*/
                                ,null /*parameters*/
                                ,false /*retainAM*/
                                ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                ,OAException.ERROR /*messagingLevel*/
                                );
    }
    
    if("KnownAsLinkEvt".equals(strEventParam)){
        String strPdftClientesHeaderID = pageContext.getParameter("pPdftClientesHeaderId");
        String strPartyId = pageContext.getParameter("pPartyId");
       
        System.out.println("strPdftClientesHeaderID:"+strPdftClientesHeaderID);
        System.out.println("strPartyId:"+strPartyId);
       
        pageContext.putSessionValue("sPartyId",strPartyId);
        pageContext.putSessionValue("sOperatingUnit",strOperatingUnit);
        
        if(null!=strFvClienteExtern){
        pageContext.putSessionValue("sClienteExtern",strFvClienteExtern);
        }
        
        OAProcessingPage oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.conscliente.webui.ClienteProcessCO"); 
        oAProcessingPage.setConciseMessage("Se recupera informacion del cliente"); 
        oAProcessingPage.setDetailedMessage("Si ya existe el cliente en el portal se recupera la informacion, de no ser asi crea informacion en el portal"); 
        oAProcessingPage.setProcessName("Interfaz Portal hacia Oracle AR"); 
        pageContext.putParameter("pPdftClientesHeaderId",strPdftClientesHeaderID);
        pageContext.putParameter("pOperatingUnit",strOperatingUnit);
        oAProcessingPage.setRetainAMValue(true);
        pageContext.forwardToProcessingPage(oAProcessingPage);  
        
        if("080120211110".equals("080120211111")){
        if(null!=strPdftClientesHeaderID&&!"".equals(strPdftClientesHeaderID)){
            com.sun.java.util.collections.HashMap parameters = new  com.sun.java.util.collections.HashMap();
            parameters.put("pClientesHeaderId",strPdftClientesHeaderID);
            parameters.put("pClienteExtern",strFvClienteExtern);
            parameters.put("pOperatingUnit",strOperatingUnit);
            
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                      ,null /*functionName*/
                                      ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                      ,null /*menuName*/
                                      ,parameters /*parameters*/
                                      ,false /*retainAM*/
                                      ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                      ,OAException.ERROR /*messagingLevel*/
                                      );
        }else{
        
        oAProcessingPage =  new  OAProcessingPage("xxqp.oracle.apps.ar.pdft.conscliente.webui.ClienteProcessCO"); 
        oAProcessingPage.setConciseMessage("This is the concise processing page message."); 
        oAProcessingPage.setDetailedMessage("This is the detailed message which should explain what's happening."); 
        oAProcessingPage.setProcessName("<Process Name>"); 
        pageContext.putParameter("pPdftClientesHeaderId",strPdftClientesHeaderID);
        pageContext.putParameter("pOperatingUnit",strOperatingUnit);
        oAProcessingPage.setRetainAMValue(true);
        pageContext.forwardToProcessingPage(oAProcessingPage);  
        } /** END if(08010211106==08010211107){ **/
        
        } /** END if(null!=strPdftClientesHeaderID&&!"".equals(strPdftClientesHeaderID)){ **/ 
         
    }
    
    
  }

}

/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.webui;

import java.util.Map;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageCheckBoxBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.server.AltaFichaTecnicaAMImpl;

/**
 * Controller for ...
 */
public class AltaFichaTecnicaCO extends OAControllerImpl
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
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAMessageChoiceBean EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
     
      String strPuserPdft = null; 
      System.out.println("AltaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("AltaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
          if(null!=NombreUsuarioEBSBean){
               NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
           }
          
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("AltaFichaTecnicaCO strPuserPdftId:"+strPuserPdftId);
          if(null!=EjecutivoBean){
                EjecutivoBean.setValue(pageContext,strPuserPdftId);
                EjecutivoBean.setReadOnly(true);
          }
      }
      
    AltaFichaTecnicaAMImpl altaFichaTecnicaAMImpl = (AltaFichaTecnicaAMImpl)pageContext.getApplicationModule(webBean);
    if(!pageContext.isFormSubmission()){
     if(null!=altaFichaTecnicaAMImpl){
         altaFichaTecnicaAMImpl.fillCampos(pageContext,webBean);
          /*environmentClientInfo(pageContext,webBean);*/ /** Si no se ejecuta aqui El objeto de visualización MasivoYPlatinumAM.MypHeaderTmpVO1 no incluyó registros. **/
          /** Se Pidio que no se llenen los campos 05082020 **/
     }
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
    AltaFichaTecnicaAMImpl altaFichaTecnicaAMImpl = (AltaFichaTecnicaAMImpl)pageContext.getApplicationModule(webBean); 
    OAMessageChoiceBean UnidadDeNegocioBean = (OAMessageChoiceBean)webBean.findChildRecursive("UnidadDeNegocio");
    String strUnidadDeNegocioValue = null; 
    String strUnidadDeNegocioText = null; 
    if(null!=UnidadDeNegocioBean){
        if(null!=UnidadDeNegocioBean.getValue(pageContext)){
            strUnidadDeNegocioValue = UnidadDeNegocioBean.getValue(pageContext).toString();
            strUnidadDeNegocioText =UnidadDeNegocioBean.getSelectionText(pageContext);
        }
    }
    
      String strFormulario = altaFichaTecnicaAMImpl.getFormularioByUnidadDeNegocio(strUnidadDeNegocioValue);
      System.out.println("strFormulario:"+strFormulario);
    
      OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
      String strEmpresaQueFacturaValue = null; 
      if(null!=EmpresaQueFacturaBean){
          if(null!=EmpresaQueFacturaBean.getValue(pageContext)){
              strEmpresaQueFacturaValue = EmpresaQueFacturaBean.getValue(pageContext).toString();
          }
      }
    
      OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacion");
      String strCicloFacturacionValue = null; 
      if(null!=CicloFacturacionBean){
          if(null!=CicloFacturacionBean.getValue(pageContext)){
              strCicloFacturacionValue = CicloFacturacionBean.getValue(pageContext).toString();
          }
      }
      
      OAMessageChoiceBean CurrencyBean = (OAMessageChoiceBean)webBean.findChildRecursive("Currency");
      String strCurrencyCodeValue = null; 
      if(null!=CurrencyBean){
          if(null!=CurrencyBean.getValue(pageContext)){
              strCurrencyCodeValue = CurrencyBean.getValue(pageContext).toString();
          }
      }
      
      OAMessageChoiceBean EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
      String strEjecutivoValue = null; 
      if(null!=EjecutivoBean){
          if(null!=EjecutivoBean.getValue(pageContext)){
              strEjecutivoValue = EjecutivoBean.getValue(pageContext).toString();
          }
      }
      
      OAFormValueBean PartyIdBean = (OAFormValueBean)webBean.findChildRecursive("PartyId");
      String strPartyId = null;
      if(null!=PartyIdBean){
       if(null!=PartyIdBean.getValue(pageContext)){
           strPartyId = PartyIdBean.getValue(pageContext).toString();
       }
      }
    
    System.out.println("strPartyId:"+strPartyId);
      
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    if("ContinuarEvt".equals(strEventParam)){
      if("MasivoYPlatinumPG".equals(strFormulario)
        ){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pUnidadDeNegocio",strUnidadDeNegocioValue);
          parameters.put("pEmpresaQueFactura",strEmpresaQueFacturaValue);
          parameters.put("pCicloFacturacion",strCicloFacturacionValue);
          parameters.put("pCurrencyCode",strCurrencyCodeValue);
          parameters.put("pPartyId",strPartyId);
          parameters.put("pEjecutivo",strEjecutivoValue);
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
      else if("BpoPG".equals(strFormulario)
             ){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pUnidadDeNegocio",strUnidadDeNegocioValue);
          parameters.put("pEmpresaQueFactura",strEmpresaQueFacturaValue);
          parameters.put("pCicloFacturacion",strCicloFacturacionValue);
          parameters.put("pCurrencyCode",strCurrencyCodeValue);
          parameters.put("pPartyId",strPartyId);
          parameters.put("pEjecutivo",strEjecutivoValue);
          
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
      else if("QP_LABS".equals(strFormulario)){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pUnidadDeNegocio",strUnidadDeNegocioValue);
          parameters.put("pEmpresaQueFactura",strEmpresaQueFacturaValue);
          parameters.put("pCicloFacturacion",strCicloFacturacionValue);
          parameters.put("pCurrencyCode",strCurrencyCodeValue);
          parameters.put("pPartyId",strPartyId);
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
      else{
        throw new OAException("La opcion:"+strUnidadDeNegocioText+" no tiene implementada a que pantallas debe dirijirse.",OAException.ERROR);
      } /** If Unidades de Negocio **/
      
    }
    
  }

    private void environmentClientInfo(OAPageContext pageContext, 
                                       OAWebBean webBean) {
    
        OAApplicationModuleImpl  oAApplicationModuleImpl = (OAApplicationModuleImpl)pageContext.getApplicationModule(webBean);
        String strPartyId = pageContext.getParameter("pPartyId");/*"6041";*/
        System.out.println("strPartyId:"+strPartyId);
        if(null==strPartyId){
         throw new OAException("No se encontro valor del parametro pPartyId.",OAException.ERROR); 
        }
        java.util.Map<String,String> map = AltaFichaTecnicaUtils.getClientInfo(strPartyId,oAApplicationModuleImpl);
        OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
        OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacion");
       
        
        if(null!=EmpresaQueFacturaBean){
          if(null!=map.get("EmpresaQueFactura"))
            EmpresaQueFacturaBean.setValue(pageContext,map.get("EmpresaQueFactura"));
        }
        
        if(null!=CicloFacturacionBean){
         if(null!=map.get("FrecuenciaFacturacion"))
             CicloFacturacionBean.setValue(pageContext,map.get("FrecuenciaFacturacion"));
        }
        
       
    }
    
    
}

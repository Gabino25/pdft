/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.ss.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.altafitec.server.AltaFichaTecnicaAMImpl;
import xxqp.oracle.apps.ar.pdft.ss.server.SsAMImpl;

/**
 * Controller for ...
 */
public class ConsultaFichaTecnicaCO extends OAControllerImpl
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
      System.out.println("ConsultaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("ConsultaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("ConsultaFichaTecnicaCO strPuserPdftId:"+strPuserPdftId);
      }
      
      if(null==strPuserPdft||null==strPuserPdftId||"".equals(strPuserPdft)||"".equals(strPuserPdftId)){
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/LoginPG" /*url*/
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
      String strEventParam  = pageContext.getParameter(this.EVENT_PARAM);
      SsAMImpl ssAMImpl = (SsAMImpl)pageContext.getApplicationModule(webBean);
      
        String strNoFichaTecnicaValue = null;
        String strStatusValue = null; 
        java.sql.Timestamp sqlTimestampFechaInicialOperacionValue = null; 
        java.sql.Timestamp sqlTimestampFechaCreacionValue = null; 
        String strFvUserIdValue = null; 
        String strNombreDelClienteValue = null; 
        String strUnidadDeNegocio = null; 
        String strEmpresaQueFactura = null;
        String strCicloFacturacion = null; 
        
        OAMessageTextInputBean  NoFichaTecnicaBean = (OAMessageTextInputBean)webBean.findChildRecursive("NoFichaTecnica"); 
        if(null!=NoFichaTecnicaBean){
          if(null!=NoFichaTecnicaBean.getValue(pageContext)){
              strNoFichaTecnicaValue = NoFichaTecnicaBean.getValue(pageContext).toString();
          }
        }
        
        OAMessageChoiceBean StatusBean = (OAMessageChoiceBean)webBean.findChildRecursive("pStatus"); 
        if(null!=StatusBean){
         if(null!=StatusBean.getValue(pageContext)){
             strStatusValue = StatusBean.getValue(pageContext).toString();
         }
        }
        
        OAMessageDateFieldBean  FechaInicialOperacionBean = (OAMessageDateFieldBean)webBean.findChildRecursive("pFechaInicialOperacion"); 
        if(null!=FechaInicialOperacionBean){
          if(null!=FechaInicialOperacionBean.getValue(pageContext)){
              sqlTimestampFechaInicialOperacionValue = (java.sql.Timestamp)FechaInicialOperacionBean.getValue(pageContext);
          }
        }
        
        OAMessageDateFieldBean  pFechaCreacionBean = (OAMessageDateFieldBean)webBean.findChildRecursive("pFechaCreacion"); 
        if(null!=pFechaCreacionBean){
          if(null!=pFechaCreacionBean.getValue(pageContext)){
              sqlTimestampFechaCreacionValue = (java.sql.Timestamp)pFechaCreacionBean.getValue(pageContext);
          }
        }
        
       OAFormValueBean FvUserIdBean = (OAFormValueBean)webBean.findChildRecursive("FvUserId");
       if(null!=FvUserIdBean){
        if(null!=FvUserIdBean.getValue(pageContext)){
           strFvUserIdValue = FvUserIdBean.getValue(pageContext).toString();
        }
       }
        
        OAMessageTextInputBean pNombreDelClienteBean = (OAMessageTextInputBean)webBean.findChildRecursive("pNombreDelCliente"); 
        if(null!=pNombreDelClienteBean){
         if(null!=pNombreDelClienteBean.getValue(pageContext)){
             strNombreDelClienteValue = pNombreDelClienteBean.getValue(pageContext).toString();
         }
        }
        
        OAMessageChoiceBean UnidadDeNegocioBean = (OAMessageChoiceBean)webBean.findChildRecursive("pUnidadDeNegocio"); 
        if(null!=UnidadDeNegocioBean){
         if(null!=UnidadDeNegocioBean.getValue(pageContext)){
             strUnidadDeNegocio = UnidadDeNegocioBean.getValue(pageContext).toString();
         }
        }
        
        OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("pEmpresaQueFactura"); 
        if(null!=EmpresaQueFacturaBean){
         if(null!=EmpresaQueFacturaBean.getValue(pageContext)){
             strEmpresaQueFactura = EmpresaQueFacturaBean.getValue(pageContext).toString();
         }
        }
        
        OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("pCicloFacturacion"); 
        if(null!=CicloFacturacionBean){
         if(null!=CicloFacturacionBean.getValue(pageContext)){
             strCicloFacturacion = CicloFacturacionBean.getValue(pageContext).toString();
         }
        }
        
        
        if("BuscarEvt".equals(strEventParam)){
        if(null!=ssAMImpl){
            ssAMImpl.initFichasTecnicasVO(strNoFichaTecnicaValue
                                                       ,strStatusValue
                                                       ,sqlTimestampFechaInicialOperacionValue
                                                       ,sqlTimestampFechaCreacionValue
                                                       ,strFvUserIdValue
                                                       ,strNombreDelClienteValue
                                                       ,strUnidadDeNegocio
                                                       ,strEmpresaQueFactura
                                                       ,strCicloFacturacion
                                                       ); 
        }
        return;
       } /** END if("BuscarEvt".equals(strEventParam)){ **/
       
        if("ConsultarEvt".equals(strEventParam)){
            if(null!=ssAMImpl){
                ssAMImpl.consultaFichaTecnica(pageContext);
            } 
            return;
        }
        
        if("LimpiarEvt".equals(strEventParam)){
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/ConsultaFichaTecnicaPG" /*url*/
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
        
        if("RegresarPortalEvt".equals(strEventParam)){
            com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
            parameters.put("pClienteExtern","Y");
            if(null!=pageContext.getTransientSessionValue("tsUserPdft")&&!"".equals(pageContext.getTransientSessionValue("tsUserPdft"))){
             parameters.put("pUserPdft",pageContext.getTransientSessionValue("tsUserPdft"));
            }
            if(null!=pageContext.getTransientSessionValue("tsUserPdftId")&&!"".equals(pageContext.getTransientSessionValue("tsUserPdftId"))){
             parameters.put("pUserPdftId",pageContext.getTransientSessionValue("tsUserPdftId"));
            }
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/ss/webui/PortalPG" /*url*/
                                      ,null /*functionName*/
                                      ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                      ,null /*menuName*/
                                      ,parameters /*parameters*/
                                      ,true /*retainAM*/
                                      ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                      ,OAException.ERROR /*messagingLevel*/
                                      );    
            return;
        }
        
  }

}

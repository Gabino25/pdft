/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.server.PortalDeFichaTecnicaAMImpl;

/**
 * Controller for ...
 */
public class LoginPdftCO extends OAControllerImpl
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
    System.out.println("LoginPdftCO processRequest");
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
      System.out.println("LoginPdftCO processFormRequest");
      
      OAMessageTextInputBean UsuarioBean = (OAMessageTextInputBean)webBean.findChildRecursive("Usuario");
      OAMessageTextInputBean ContraseniaBean = (OAMessageTextInputBean)webBean.findChildRecursive("Contrasenia");
      
      String strUsuario = null; 
      String strConstrasenia = null; 
      
      if(null!=UsuarioBean){
       if(null!=UsuarioBean.getValue(pageContext)){
         strUsuario =  UsuarioBean.getValue(pageContext).toString();
       }
      }
      
      if(null!=ContraseniaBean){
       if(null!=ContraseniaBean.getValue(pageContext)){
           strConstrasenia = (String)ContraseniaBean.getValue(pageContext);
       }else{
           strConstrasenia = pageContext.getParameter("Contrasenia");
       }
      }
      
      
      
      String strEventParam = pageContext.getParameter(this.EVENT_PARAM); 
      PortalDeFichaTecnicaAMImpl portalDeFichaTecnicaAMImpl = (PortalDeFichaTecnicaAMImpl)pageContext.getApplicationModule(webBean);
      
      if("IniciarSesionEvt".equals(strEventParam)){
         System.out.println("strUsuario:"+strUsuario);
         System.out.println("strConstrasenia:"+strConstrasenia);
         String strValidaUsuarioPassword = portalDeFichaTecnicaAMImpl.validaUsuarioPassword(strUsuario
                                                                         ,strConstrasenia
                                                                         ,pageContext
                                                                          );
         if(null!=strValidaUsuarioPassword){
          throw new OAException(strValidaUsuarioPassword,OAException.ERROR); 
         }else{
            
             String strUsuarioID = portalDeFichaTecnicaAMImpl.getUsuarioId(strUsuario
                                                                             ,strConstrasenia
                                                                             ,pageContext
                                                                              );
              System.out.println("LoginPdftCO strPuserPdftId:"+strUsuarioID);                                                                
             com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
             parameters.put("pUserPdft",strUsuario);
             parameters.put("pUserPdftId",strUsuarioID);
             pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/PortalDeFichaTecnicaPG" /*url*/
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
      }
      
  }

}

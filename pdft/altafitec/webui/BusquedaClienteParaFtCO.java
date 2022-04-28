/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import xxqp.oracle.apps.ar.pdft.altafitec.server.AltaFichaTecnicaAMImpl;

/**
 * Controller for ...
 */
public class BusquedaClienteParaFtCO extends OAControllerImpl
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
      System.out.println("BusquedaClienteParaFtCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
           System.out.println("BusquedaClienteParaFtCO strPuserPdft:"+strPuserPdft);
          if(null!=NombreUsuarioEBSBean){
               NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
           }
      }
     
      
    AltaFichaTecnicaAMImpl altaFichaTecnicaAMImpl = (AltaFichaTecnicaAMImpl)pageContext.getApplicationModule(webBean);
      
    if(!pageContext.isFormSubmission()){
    }else{
    } /** END if(!pageContext.isFormSubmission()){ **/
    
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
    String strNombreDeCliente = null; 
    String strRazonSocialSearch = null;
    String strRFC = null; 
    
    OAMessageLovInputBean NombreDeClienteBean = (OAMessageLovInputBean)webBean.findChildRecursive("NombreDeCliente");
     if(null!=NombreDeClienteBean){
      if(null!=NombreDeClienteBean.getValue(pageContext)){
       strNombreDeCliente = NombreDeClienteBean.getValue(pageContext).toString();
      }
     }
      
      OAMessageLovInputBean RazonSocialSearchBean = (OAMessageLovInputBean)webBean.findChildRecursive("RazonSocialSearch");
       if(null!=RazonSocialSearchBean){
        if(null!=RazonSocialSearchBean.getValue(pageContext)){
         strRazonSocialSearch = RazonSocialSearchBean.getValue(pageContext).toString();
        }
       }
       
      OAMessageLovInputBean RFCBean = (OAMessageLovInputBean)webBean.findChildRecursive("RFC");
      if(null!=RFCBean){
        if(null!=RFCBean.getValue(pageContext)){
          strRFC = RFCBean.getValue(pageContext).toString();
        }
      }
    
    System.out.println("strNombreDeCliente:"+strNombreDeCliente);
    System.out.println("strRazonSocialSearch:"+strRazonSocialSearch);
    System.out.println("strRFC:"+strRFC);
    
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    System.out.println("strEventParam:"+strEventParam);
    
    if("BuscarEvt".equals(strEventParam)){
    if(null!=altaFichaTecnicaAMImpl){
        altaFichaTecnicaAMImpl.initClientesInfoFiTecVO(strNombreDeCliente
                                                      ,strRazonSocialSearch
                                                      ,strRFC
                                                      );
     }
    } /** END if("BuscarEvt".equals(strEventParam)){ **/
    
    if("LimpiarEvt".equals(strEventParam)){
       /** if(null!=altaFichaTecnicaAMImpl){
            altaFichaTecnicaAMImpl.cleanClientesInfoFiTecVO();
        }
        **/
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaClienteParaFtPG" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,null /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );
     }
    
    if("NuevoEvt".equals(strEventParam)){
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClientePG" /*url*/
                                   ,null /*functionName*/
                                   ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                   ,null /*menuName*/
                                   ,null /*parameters*/
                                   ,false /*retainAM*/
                                   ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                   ,OAException.ERROR /*messagingLevel*/
                                   );
                                   
     } /** END if("NuevoEvt".equals(strEventParam)){ **/
     
      if("ContinuarEvt".equals(strEventParam)){
          if(null!=altaFichaTecnicaAMImpl){
             altaFichaTecnicaAMImpl.toAltaDeFichaTecnica(pageContext);
          }
      } /** END if("ContinuarEvt".equals(strEventParam)){ **/
      
  }

}

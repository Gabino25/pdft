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
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

/**
 * Controller for ...
 */
public class PortalDeFichaTecnicaCO extends OAControllerImpl
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
    String strPuserPdft =  pageContext.getParameter("pUserPdft");
    String strPuserPdftId =  pageContext.getParameter("pUserPdftId");
     
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
    
    if(null!=strPuserPdft&&!"".equals(strPuserPdft)){
      pageContext.putTransientSessionValue("tsUserPdft",strPuserPdft);
    }
      System.out.println("strPuserPdftId:"+strPuserPdftId);
      if(null!=strPuserPdftId&&!"".equals(strPuserPdftId)){
        pageContext.putTransientSessionValue("tsUserPdftId",strPuserPdftId);
      }
      
    if(null!=NombreUsuarioEBSBean){
        NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
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
    String strEventParam =pageContext.getParameter(this.EVENT_PARAM);
    System.out.println("strEventParam:"+strEventParam);
    if("AltaFichaTecnicaEvt".equals(strEventParam)){
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaClienteParaFtPG" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,null /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );
    }else if("AltaDeClienteEvt".equals(strEventParam)){
      pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClientePG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                ,null /*functionName*/
                                ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                ,null /*menuName*/
                                ,null /*parameters*/
                                ,false /*retainAM*/
                                ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                ,OAException.ERROR /*messagingLevel*/
                                );
    }else if("ConsultaFichaTecnicaEvt".equals(strEventParam)){
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaDeFichaTecnicaPG" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,null /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );
    }else if("ConsultaDeClienteEvt".equals(strEventParam)){
    
      pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG" /*url*/
                                ,null /*functionName*/
                                ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                ,null /*menuName*/
                                ,null /*parameters*/
                                ,false /*retainAM*/
                                ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                ,OAException.ERROR /*messagingLevel*/
                                );
    
    }else if("ReportesEvt".equals(strEventParam)){
    
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/webui/ReportesPG" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,null /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );
                                  
    }
    
      if("RegresarPaginaInicialEvt".equals(strEventParam)){
          pageContext.setForwardURL("OA.jsp?page=/oracle/apps/fnd/framework/navigate/webui/NewHomePG"
                                    ,null /**String functionName,**/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /**byte menuContextAction,**/
                                    ,null /** String menuName, **/
                                    ,null /** com.sun.java.util.collections.HashMap parameters, **/
                                    ,false /** boolean retainAM,**/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /**String addBreadCrumb, **/
                                    ,OAWebBeanConstants.IGNORE_MESSAGES /**byte messagingLevel**/
                                    );
      }
      
    
  }

}

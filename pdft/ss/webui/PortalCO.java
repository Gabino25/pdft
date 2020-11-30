/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.ss.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAFwkConstants;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

/**
 * Controller for ...
 */
public class PortalCO extends OAControllerImpl
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
    String strUserName =  pageContext.getUserName();
    pageContext.writeDiagnostics(this,"strUserName:"+strUserName,OAFwkConstants.STATEMENT);
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
    pageContext.writeDiagnostics(this,"strEventParam:"+strEventParam,OAFwkConstants.STATEMENT);
      if("ConsultaFichaTecnicaEvt".equals(strEventParam)){
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
          }else if("ConsultaDeClienteEvt".equals(strEventParam)){
              
            com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
            parameters.put("pClienteExtern","Y");
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG" /*url*/
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

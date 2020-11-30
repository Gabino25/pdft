/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.ss.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import xxqp.oracle.apps.ar.pdft.ss.server.SsAMImpl;

/**
 * Controller for ...
 */
public class LoginMgrCO extends OAControllerImpl
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
    SsAMImpl ssAMImpl = (SsAMImpl)pageContext.getApplicationModule(webBean);
      ssAMImpl.getXxqpPdftUsuariosRoVO1().executeQuery();  
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
      SsAMImpl ssAMImpl = (SsAMImpl)pageContext.getApplicationModule(webBean);
    if("CrearUsuarioEvt".equals(strEventParam)){
        ssAMImpl.createUserRecord();
        return;
    }
    if("GuardarEvt".equals(strEventParam)){
        ssAMImpl.saveUserRecord();
    }
  }

}

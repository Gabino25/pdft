/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;

/**
 * Controller for ...
 */
public class ConceptosCO extends OAControllerImpl
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
      System.out.println("Entra ConceptosCO processRequest");
      /********************************************************* 
      if(null==pageContext||null==webBean){
       return; 
      } 
      String strSvPartyId = (String)pageContext.getSessionValue("svPartyId"); 
      String strSearchText = pageContext.getParameter("searchText");
      System.out.println("strSvPartyId:"+strSvPartyId);
      System.out.println("strSearchText:"+strSearchText);
      MasivoYPlatinumAMImpl  masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
      masivoYPlatinumAMImpl.initConceptosLov(strSvPartyId,strSearchText);  
      *************************************************************/
      System.out.println("Sale ConceptosCO processRequest");
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
  }

}

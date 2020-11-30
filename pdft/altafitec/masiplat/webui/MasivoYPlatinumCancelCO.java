/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVORowImpl;

/**
 * Controller for ...
 */
public class MasivoYPlatinumCancelCO extends OAControllerImpl
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
    MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
    
    XxqpPdftMypHeaderVOImpl  xxqpPdftMypHeaderVOImpl =   masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1(); 
    XxqpPdftMypHeaderVORowImpl  xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)xxqpPdftMypHeaderVOImpl.getCurrentRow();  
    if(null==xxqpPdftMypHeaderVORowImpl){
        String strMyPHeaderId =  pageContext.getParameter("pMyPHeaderId");
        xxqpPdftMypHeaderVORowImpl = masivoYPlatinumAMImpl.initMypHeaderVO(strMyPHeaderId);
    }
    
    oracle.jbo.domain.Number numMasiYPlatHeaderId = xxqpPdftMypHeaderVORowImpl.getId();
    System.out.println("numMasiYPlatHeaderId:"+numMasiYPlatHeaderId);
    OAMessageTextInputBean RazonCancelacionBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonCancelacion");
    String strRazonCancelacion = null; 
    if(null!=RazonCancelacionBean){
      if(null!=RazonCancelacionBean.getValue(pageContext)){
          strRazonCancelacion = RazonCancelacionBean.getValue(pageContext).toString();
      }
    }
    
    if("AceptarEvt".equals(strEventParam)){
        xxqpPdftMypHeaderVORowImpl.setAttribute1(strRazonCancelacion);
        xxqpPdftMypHeaderVORowImpl.setStatus("CANCELADA");
        masivoYPlatinumAMImpl.getOADBTransaction().commit();
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pMyPHeaderId",numMasiYPlatHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,parameters /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  ); 
    }
  }

}

/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.BpoAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVORowImpl;

/**
 * Controller for ...
 */
public class BpoCancelCO extends OAControllerImpl
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
    BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftBpoHeaderVOImpl  xxqpPdftBpoHeaderVOImpl =  bpoAMImpl.getXxqpPdftBpoHeaderVO1();
    XxqpPdftBpoHeaderVORowImpl  xxqpPdftBpoHeaderVORowImpl = (XxqpPdftBpoHeaderVORowImpl)xxqpPdftBpoHeaderVOImpl.getCurrentRow(); 
    if(null==xxqpPdftBpoHeaderVORowImpl){
        String strBpoHeaderId = pageContext.getParameter("pBpoHeaderId");
        xxqpPdftBpoHeaderVORowImpl = bpoAMImpl.initBpoHeaderVO(strBpoHeaderId);
    }
    
    oracle.jbo.domain.Number numBpoHeaderId = xxqpPdftBpoHeaderVORowImpl.getId();
      System.out.println("numBpoHeaderId:"+numBpoHeaderId);
      OAMessageTextInputBean RazonCancelacionBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonCancelacion");
      String strRazonCancelacion = null; 
      if(null!=RazonCancelacionBean){
        if(null!=RazonCancelacionBean.getValue(pageContext)){
            strRazonCancelacion = RazonCancelacionBean.getValue(pageContext).toString();
        }
      }
      
      
    if("AceptarEvt".equals(strEventParam)){
        xxqpPdftBpoHeaderVORowImpl.setAttribute1(strRazonCancelacion);
        xxqpPdftBpoHeaderVORowImpl.setStatus("CANCELADA");
        bpoAMImpl.getOADBTransaction().commit();
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pBpoHeaderId",numBpoHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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

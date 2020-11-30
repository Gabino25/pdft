/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.QpLabsAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVORowImpl;

/**
 * Controller for ...
 */
public class QpLabsCancelCO extends OAControllerImpl
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
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftQplabsHeaderVOImpl xxqpPdftQplabsHeaderVOImpl = qpLabsAMImpl.getXxqpPdftQplabsHeaderVO1(); 
    XxqpPdftQplabsHeaderVORowImpl xxqpPdftQplabsHeaderVORowImpl = (XxqpPdftQplabsHeaderVORowImpl)xxqpPdftQplabsHeaderVOImpl.getCurrentRow(); 
    if(null==xxqpPdftQplabsHeaderVORowImpl){
        String strQplabsHeaderId = pageContext.getParameter("pQplabsHeaderId");
        xxqpPdftQplabsHeaderVORowImpl = qpLabsAMImpl.initQplabsHeaderVO(strQplabsHeaderId);
    }
    oracle.jbo.domain.Number numQpLabsHeaderId = xxqpPdftQplabsHeaderVORowImpl.getId(); 
    System.out.println("numQpLabsHeaderId:"+numQpLabsHeaderId);
    
    OAMessageTextInputBean RazonCancelacionBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonCancelacion");
    String strRazonCancelacion = null; 
    if(null!=RazonCancelacionBean){
      if(null!=RazonCancelacionBean.getValue(pageContext)){
           strRazonCancelacion = RazonCancelacionBean.getValue(pageContext).toString();
       }
    }
      
      if("AceptarEvt".equals(strEventParam)){ 
          xxqpPdftQplabsHeaderVORowImpl.setAttribute1(strRazonCancelacion);
          xxqpPdftQplabsHeaderVORowImpl.setStatus("CANCELADA");
          qpLabsAMImpl.getOADBTransaction().commit();
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pQplabsHeaderId",numQpLabsHeaderId.toString() );
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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

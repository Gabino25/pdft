/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.mgr.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.jbo.domain.Number;

import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.MgrAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.XxqpPdftDistribucionVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.XxqpPdftDistribucionVORowImpl;

/**
 * Controller for ...
 */
public class DistribucionFtCO extends OAControllerImpl
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
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftDistribucionVOImpl  xxqpPdftDistribucionVOImpl =   mgrAMImpl.getXxqpPdftDistribucionVO1();
    if(!pageContext.isFormSubmission()){
        xxqpPdftDistribucionVOImpl.executeQuery();
    }else{
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
    String rowRef =pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE); 
    String strEventParam =  pageContext.getParameter(EVENT_PARAM); 
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean);
    XxqpPdftDistribucionVOImpl xxqpPdftDistribucionVOImpl =mgrAMImpl.getXxqpPdftDistribucionVO1(); 
    XxqpPdftDistribucionVORowImpl xxqpPdftDistribucionVORowImpl = null; 
    
    OARow row = (OARow)mgrAMImpl.findRowByRef(rowRef); 
    
    if("DeleteEvt".equals(strEventParam)){
        System.out.println("row:"+row+","+row.getAttribute("Id"));
        oracle.jbo.domain.Number numId = (Number)row.getAttribute("Id"); 
        
      if(null!=row){
          row.remove();
          mgrAMImpl.getOADBTransaction().commit(); 
      }
    }
    
    if("GrabarEvt".equals(strEventParam)){
        mgrAMImpl.getOADBTransaction().commit(); 
    }
    
    if("CreateEvt".equals(strEventParam)){
        if(!xxqpPdftDistribucionVOImpl.isPreparedForExecution()){
            xxqpPdftDistribucionVOImpl.executeQuery();
        }
        xxqpPdftDistribucionVOImpl.first(); 
        xxqpPdftDistribucionVORowImpl = (XxqpPdftDistribucionVORowImpl)xxqpPdftDistribucionVOImpl.createRow(); 
        oracle.jbo.domain.Number numDistribucionID = mgrAMImpl.getOADBTransaction().getSequenceValue("XXQP_PDFT_DISTRIBUCION_S");
        xxqpPdftDistribucionVORowImpl.setId(numDistribucionID);
        xxqpPdftDistribucionVOImpl.insertRow(xxqpPdftDistribucionVORowImpl);
        mgrAMImpl.getOADBTransaction().commit(); 
    }
    
  }

}

/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.mgr.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.MgrAMImpl;

/**
 * Controller for ...
 */
public class AsignacionFormulariosCO extends OAControllerImpl
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
       
      if(null!=NombreUsuarioEBSBean){
           NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
       }
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean); 
    mgrAMImpl.initMgrCatalogosVO("UNIDAD_DE_NEGOCIO");
      
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
    System.out.println("strEventParam:"+strEventParam); 
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean); 
      
      if("GrabarEvt".equals(strEventParam)){
          mgrAMImpl.validaUniqueKey();
          mgrAMImpl.grabarInfo();
          mgrAMImpl.initMgrCatalogosVO("UNIDAD_DE_NEGOCIO");
          throw new OAException("Los Datos se guardaron correctamente",OAException.INFORMATION);
      }
      
      if("CancelarEvt".equals(strEventParam)){
          OAException descMesg = new OAException("Mensaje de confirmacion.");
          OAException instrMesg = new OAException("¿Desea salir de la pagina?");
          OADialogPage dialogPage =  new OADialogPage(OAException.WARNING, descMesg, instrMesg, "", "");
          dialogPage.setOkButtonToPost(true);
          dialogPage.setNoButtonToPost(true);
          dialogPage.setPostToCallingPage(true);

              // here we are setting names of buttons of dialog page 
           dialogPage.setOkButtonItemName("CancelYes");
           dialogPage.setNoButtonItemName("CancelNo");
              
           pageContext.redirectToDialogPage(dialogPage);
      } /** END if("CancelarEvt".equals(strEventParam)){ **/
      
      if(null!=pageContext.getParameter("CancelYes")){
          mgrAMImpl.getOADBTransaction().rollback();
          pageContext.setForwardURL("OAHOMEPAGE" /**String functionName,**/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /**byte menuContextAction,**/
                                    ,null /** String menuName, **/
                                    ,null /** com.sun.java.util.collections.HashMap parameters, **/
                                    ,false /** boolean retainAM,**/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /**String addBreadCrumb, **/
                                    ,OAWebBeanConstants.IGNORE_MESSAGES /**byte messagingLevel**/
                                    );
      }
        if(null!=pageContext.getParameter("CancelNo")){
         System.out.println("Permanecer en la pagina.");
        }
        
  }

}

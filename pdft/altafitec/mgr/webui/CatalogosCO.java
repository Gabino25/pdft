/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.mgr.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.MgrAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.XxqpPdftMgrCatalogosVORowImpl;

/**
 * Controller for ...
 */
public class CatalogosCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  private /*static*/ XxqpPdftMgrCatalogosVORowImpl GlobalxxqpPdftMgrCatalogosVORowImpl = null; 
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
       
      if(null!=NombreUsuarioEBSBean){
           NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
       }
    System.out.println("Process Request");
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
    String rowReference =  pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean); 
    String strCatalogoParam =  pageContext.getParameter("CatalogoLov");
    System.out.println("strCatalogoParam:"+strCatalogoParam);
    if(null!=strCatalogoParam&&!"".equals(strCatalogoParam)){
        mgrAMImpl.valoresValidos(strCatalogoParam);
    }
      
    OAMessageChoiceBean CatalogoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Catalogo");
    OAFormValueBean FvCatalogoBean = (OAFormValueBean)webBean.findChildRecursive("FvCatalogo");
    System.out.println("strEventParam:"+strEventParam);
    System.out.println("rowReference:"+rowReference);
   
    String strCatalogoValue = null; 
    String strFvCatalogoValue = null; 
    
      if(null!=FvCatalogoBean){
        if(null!=FvCatalogoBean.getValue(pageContext)){
         strFvCatalogoValue = FvCatalogoBean.getValue(pageContext).toString();
        }
      }
    
     if("lovPrepare".equals(strEventParam)){
         if(null!=mgrAMImpl){
                mgrAMImpl.validaNulos(GlobalxxqpPdftMgrCatalogosVORowImpl);
                mgrAMImpl.getOADBTransaction().commit();
            }
     }
    
    if("lovUpdate".equals(strEventParam)){
     if(null!=mgrAMImpl){
         mgrAMImpl.initMgrCatalogosVO(strFvCatalogoValue);
     }
    }
    
    if("CatalogoEvt".equals(strEventParam)){
     if(null!=mgrAMImpl){
         if(null!=CatalogoBean){
           if(null!=CatalogoBean.getValue(pageContext)){
               strCatalogoValue = CatalogoBean.getValue(pageContext).toString();
               if(null!=FvCatalogoBean){
                   FvCatalogoBean.setValue(pageContext,strCatalogoValue.toString());
               }
           }
         } /** END if(null!=CatalogoBean){ **/
        
         mgrAMImpl.initMgrCatalogosVO(strCatalogoValue);
     }
    }
    
  
    
    if("AgregaRegistroEvt".equals(strEventParam)){
      if(null!=mgrAMImpl){
         GlobalxxqpPdftMgrCatalogosVORowImpl = mgrAMImpl.agregaRegistro(strFvCatalogoValue);
      }
    }
   
      if("AgregaRegistroTmpEvt".equals(strEventParam)){
        if(null!=mgrAMImpl){
            mgrAMImpl.migraInformacion();
            mgrAMImpl.agregaRegistroTMp(strFvCatalogoValue);
        }
      }
      
    if("DeleteEvt".equals(strEventParam)){
     String strpCatalogoId = pageContext.getParameter("pCatalogoId");
     OARow currRow = (OARow)mgrAMImpl.findRowByRef(rowReference);
     currRow.remove();
        mgrAMImpl.getOADBTransaction().commit();
    }
    
    if("GrabarEvt".equals(strEventParam)){
        mgrAMImpl.validaUniqueKey();
        mgrAMImpl.grabarInfo();
        mgrAMImpl.initMgrCatalogosVO(strFvCatalogoValue);
        mgrAMImpl.validaUniqueKey();
        mgrAMImpl.initMgrCatalogosVO(strFvCatalogoValue);
    }
    
    if("CancelarEvt".equals(strEventParam)){
        OAException descMesg = new OAException("Mensaje de confirmacion.");
        OAException instrMesg = new OAException("¿Desea salir sin grabar los cambios?");
        OADialogPage dialogPage =  new OADialogPage(OAException.WARNING, descMesg, instrMesg, "", "");
        dialogPage.setOkButtonToPost(true);
        dialogPage.setNoButtonToPost(true);
        dialogPage.setPostToCallingPage(true);

            // here we are setting names of buttons of dialog page 
         dialogPage.setOkButtonItemName("CancelYes");
         dialogPage.setNoButtonItemName("CancelNo");
            
         pageContext.redirectToDialogPage(dialogPage);

        if (1==2){ /** Mudar a otro evento **/
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
    
    
    if("SalirEvt".equals(strEventParam)){
       
        mgrAMImpl.getOADBTransaction().rollback();
        pageContext.setForwardURL("OAHOMEPAGE" /**String functionName,**/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /**byte menuContextAction,**/
                                  ,null /** String menuName, **/
                                  ,null /** com.sun.java.util.collections.HashMap parameters, **/
                                  ,false /** boolean retainAM,**/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /**String addBreadCrumb, **/
                                  ,OAWebBeanConstants.IGNORE_MESSAGES /**byte messagingLevel**/
                                  );
    } /** END if("SalirEvt".equals(strEventParam)){ **/
    
    if("RegresarPaginaInicialEvt".equals(strEventParam)){
        OAException descMesg = new OAException("Mensaje de confirmacion.");
        OAException instrMesg = new OAException("¿Desea salir sin grabar los cambios?");
        OADialogPage dialogPage =  new OADialogPage(OAException.WARNING, descMesg, instrMesg, "", "");
        dialogPage.setOkButtonToPost(true);
        dialogPage.setNoButtonToPost(true);
        dialogPage.setPostToCallingPage(true);

            // here we are setting names of buttons of dialog page 
         dialogPage.setOkButtonItemName("CancelYes");
         dialogPage.setNoButtonItemName("CancelNo");
            
         pageContext.redirectToDialogPage(dialogPage);
         if(1==2){
        mgrAMImpl.getOADBTransaction().rollback();
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
    
    if("LookupCodeEvt".equals(strEventParam)){
         OARow currRow = (OARow)mgrAMImpl.findRowByRef(rowReference);
         if(null!=currRow.getAttribute("LookupCode")&&!"".equals(currRow.getAttribute("LookupCode"))){
         String strLookupCode = (String)currRow.getAttribute("LookupCode");
             currRow.setAttribute("Description",strLookupCode);
         }
     } 
     
  } /** END public void processFormRequest(OAPageContext pageContext, OAWebBean webBean) **/

}

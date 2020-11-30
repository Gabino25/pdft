package xxqp.oracle.apps.ar.pdft.altafitec.mgr.webui;

import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import xxqp.oracle.apps.ar.pdft.altafitec.mgr.server.MgrAMImpl;

public class PrepareMgrCatalogosVO extends OAControllerImpl{
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
   
    MgrAMImpl mgrAMImpl = (MgrAMImpl)pageContext.getApplicationModule(webBean); 
    mgrAMImpl.initMgrCatalogosLov();
    
    }
}

package xxqp.oracle.apps.ar.pdft.conscliente.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAFwkConstants;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;

import xxqp.oracle.apps.ar.pdft.conscliente.server.ConsultaDeClienteAMImpl;

public class ClienteProcessCO extends OAControllerImpl {
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processRequest(pageContext, webBean);
    } 
    public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processFormRequest(pageContext, webBean);
      
       String strPdftClientesHeaderID = pageContext.getParameter("pPdftClientesHeaderId");
       String strOperatingUnit = pageContext.getParameter("pOperatingUnit");
       System.out.println("strPdftClientesHeaderID:"+strPdftClientesHeaderID);
       System.out.println("strOperatingUnit:"+strOperatingUnit);
        
       String strPartyId = null;
       String strClienteExtern = null; 
       String strUserPdftId = null; 
       if(null!=pageContext.getSessionValue("sPartyId")){
        strPartyId = (String)pageContext.getSessionValue("sPartyId");
       }
        if(null!=pageContext.getSessionValue("sClienteExtern")){
         strClienteExtern = (String)pageContext.getSessionValue("sClienteExtern");
        }
        if(null!=pageContext.getSessionValue("sOperatingUnit")){
            strOperatingUnit = (String)pageContext.getSessionValue("sOperatingUnit");
        }
        if(null!=pageContext.getSessionValue("sUserPdftId")){
            strUserPdftId = (String)pageContext.getSessionValue("sUserPdftId");
        }
        System.out.println("strPartyId:"+strPartyId);
        System.out.println("strClienteExtern:"+strClienteExtern);
        System.out.println("strOperatingUnit:"+strOperatingUnit);
        System.out.println("strUserPdftId:"+strUserPdftId);
        
        pageContext.writeDiagnostics(this,"strPartyId:"+strPartyId,OAFwkConstants.STATEMENT);
        pageContext.writeDiagnostics(this,"strClienteExtern:"+strClienteExtern,OAFwkConstants.STATEMENT);
        pageContext.writeDiagnostics(this,"strOperatingUnit:"+strOperatingUnit,OAFwkConstants.STATEMENT);
        pageContext.writeDiagnostics(this,"strUserPdftId:"+strUserPdftId,OAFwkConstants.STATEMENT);
        
        ConsultaDeClienteAMImpl  consultaDeClienteAMImpl = (ConsultaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
        strPdftClientesHeaderID = consultaDeClienteAMImpl.callFromOracleToPdft(strPartyId,strOperatingUnit,strUserPdftId);
        System.out.println("strPdftClientesHeaderID:"+strPdftClientesHeaderID);
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pClientesHeaderId",strPdftClientesHeaderID);
        parameters.put("pClienteExtern",strClienteExtern);
        parameters.put("pOperatingUnit",strOperatingUnit);
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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


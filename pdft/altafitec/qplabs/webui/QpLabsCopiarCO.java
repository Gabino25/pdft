package xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.QpLabsAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsInfoOperVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsInfoOperVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsPrecClieVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.XxqpPdftQplabsPrecClieVORowImpl;

public class QpLabsCopiarCO extends OAControllerImpl {
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processRequest(pageContext, webBean);
    } 
    public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processFormRequest(pageContext, webBean);
      XxqpPdftQplabsHeaderVOImpl xxqpPdftQplabsHeaderVOImpl = null; 
      XxqpPdftQplabsHeaderVORowImpl xxqpPdftQplabsHeaderVORowImpl = null; 
      
      XxqpPdftQplabsInfoOperVOImpl xxqpPdftQplabsInfoOperVOImpl = null; 
      XxqpPdftQplabsInfoOperVORowImpl xxqpPdftQplabsInfoOperVORowImpl = null; 
      
      XxqpPdftQplabsPrecClieVOImpl xxqpPdftQplabsPrecClieVOImpl = null; 
      XxqpPdftQplabsPrecClieVORowImpl xxqpPdftQplabsPrecClieVORowImpl = null; 
        
      QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
      xxqpPdftQplabsHeaderVOImpl = qpLabsAMImpl.getXxqpPdftQplabsHeaderVO1();  
      xxqpPdftQplabsInfoOperVOImpl = qpLabsAMImpl.getXxqpPdftQplabsInfoOperVO1(); 
      xxqpPdftQplabsPrecClieVOImpl = qpLabsAMImpl.getXxqpPdftQplabsPrecClieVO1();
        
      OADBTransaction oADBTransaction = qpLabsAMImpl.getOADBTransaction();
      oracle.jbo.domain.Number numQpLabsHeaderId = null;
      
      if(null!=xxqpPdftQplabsHeaderVOImpl){
         RowSetIterator QplabsHeaderIterator = xxqpPdftQplabsHeaderVOImpl.createRowSetIterator(null);
         while(QplabsHeaderIterator.hasNext()){
             xxqpPdftQplabsHeaderVORowImpl = (XxqpPdftQplabsHeaderVORowImpl)QplabsHeaderIterator.next();
             XxqpPdftQplabsHeaderVORowImpl newPdftQplabsHeaderVORowImpl = null; 
             newPdftQplabsHeaderVORowImpl = (XxqpPdftQplabsHeaderVORowImpl)xxqpPdftQplabsHeaderVOImpl.createRow();
            
             numQpLabsHeaderId = oADBTransaction.getSequenceValue("XXQP_PDFT_QPLABS_HEADER_S");
             oracle.jbo.domain.Number numNumeroFt = oADBTransaction.getSequenceValue("XXQP_PDFT_NUMERO_FT_S");
             newPdftQplabsHeaderVORowImpl.setId(numQpLabsHeaderId);
             newPdftQplabsHeaderVORowImpl.setNumeroFt(numNumeroFt);
             newPdftQplabsHeaderVORowImpl.setNumeroFtReferencia(xxqpPdftQplabsHeaderVORowImpl.getNumeroFt());
             newPdftQplabsHeaderVORowImpl.setContratoFlag(xxqpPdftQplabsHeaderVORowImpl.getContratoFlag());
             newPdftQplabsHeaderVORowImpl.setContratoFileName(xxqpPdftQplabsHeaderVORowImpl.getContratoFileName());
             newPdftQplabsHeaderVORowImpl.setContratoContentType(xxqpPdftQplabsHeaderVORowImpl.getContratoContentType());
             newPdftQplabsHeaderVORowImpl.setContratoFile(xxqpPdftQplabsHeaderVORowImpl.getContratoFile());
             newPdftQplabsHeaderVORowImpl.setStatus("PRELIMINAR");
             newPdftQplabsHeaderVORowImpl.setUnidadDeNegocioC(xxqpPdftQplabsHeaderVORowImpl.getUnidadDeNegocioC());
             newPdftQplabsHeaderVORowImpl.setEmpresaQueFacturaC(xxqpPdftQplabsHeaderVORowImpl.getEmpresaQueFacturaC());
             newPdftQplabsHeaderVORowImpl.setFrecuenciaFacturacionC(xxqpPdftQplabsHeaderVORowImpl.getFrecuenciaFacturacionC());
             newPdftQplabsHeaderVORowImpl.setFechaInicialOperacion(xxqpPdftQplabsHeaderVORowImpl.getFechaInicialOperacion());
             newPdftQplabsHeaderVORowImpl.setPartyId(xxqpPdftQplabsHeaderVORowImpl.getPartyId());
             newPdftQplabsHeaderVORowImpl.setAttributeCategory(xxqpPdftQplabsHeaderVORowImpl.getAttributeCategory());
           /**  newPdftQplabsHeaderVORowImpl.setAttribute1(xxqpPdftQplabsHeaderVORowImpl.getAttribute1());  Razon de Cancelacion **/
             newPdftQplabsHeaderVORowImpl.setAttribute2(xxqpPdftQplabsHeaderVORowImpl.getAttribute2());
             newPdftQplabsHeaderVORowImpl.setAttribute3(xxqpPdftQplabsHeaderVORowImpl.getAttribute3());
             newPdftQplabsHeaderVORowImpl.setAttribute4(xxqpPdftQplabsHeaderVORowImpl.getAttribute4());
             newPdftQplabsHeaderVORowImpl.setAttribute5(xxqpPdftQplabsHeaderVORowImpl.getAttribute5());
             
            xxqpPdftQplabsHeaderVOImpl.insertRow(newPdftQplabsHeaderVORowImpl);
             oADBTransaction.commit();
         } /** END while(QplabsHeaderIterator.hasNext()){ **/
          QplabsHeaderIterator.closeRowSetIterator();
      } /** END if(null!=XxqpPdftQplabsHeaderVOImpl){ **/
      
      if(null!=xxqpPdftQplabsInfoOperVOImpl){
      System.out.println("X");
       RowSetIterator QplabsInfoOperIterator =   xxqpPdftQplabsInfoOperVOImpl.createRowSetIterator(null);
       while(QplabsInfoOperIterator.hasNext()){
           System.out.println("XX");
         xxqpPdftQplabsInfoOperVORowImpl = (XxqpPdftQplabsInfoOperVORowImpl)QplabsInfoOperIterator.next();
         XxqpPdftQplabsInfoOperVORowImpl newPdftQplabsInfoOperVORowImpl = null; 
         newPdftQplabsInfoOperVORowImpl = (XxqpPdftQplabsInfoOperVORowImpl)xxqpPdftQplabsInfoOperVOImpl.createRow();
         
           oracle.jbo.domain.Number numQlabsInformacionOeracionalId = oADBTransaction.getSequenceValue("XXQP_PDFT_QPLABS_INFO_OPER_S");
            newPdftQplabsInfoOperVORowImpl.setId(numQlabsInformacionOeracionalId);
            newPdftQplabsInfoOperVORowImpl.setQplabsHeaderId(numQpLabsHeaderId);
            newPdftQplabsInfoOperVORowImpl.setTipoServico(xxqpPdftQplabsInfoOperVORowImpl.getTipoServico());
            newPdftQplabsInfoOperVORowImpl.setNombreServicio(xxqpPdftQplabsInfoOperVORowImpl.getNombreServicio());
            newPdftQplabsInfoOperVORowImpl.setVolumenAproximado(xxqpPdftQplabsInfoOperVORowImpl.getVolumenAproximado());
            newPdftQplabsInfoOperVORowImpl.setComentariosAdicionales(xxqpPdftQplabsInfoOperVORowImpl.getComentariosAdicionales());
           newPdftQplabsInfoOperVORowImpl.setAttributeCategory(xxqpPdftQplabsInfoOperVORowImpl.getAttributeCategory());
           newPdftQplabsInfoOperVORowImpl.setAttribute1(xxqpPdftQplabsInfoOperVORowImpl.getAttribute1());
           newPdftQplabsInfoOperVORowImpl.setAttribute2(xxqpPdftQplabsInfoOperVORowImpl.getAttribute2());
           newPdftQplabsInfoOperVORowImpl.setAttribute3(xxqpPdftQplabsInfoOperVORowImpl.getAttribute3());
           newPdftQplabsInfoOperVORowImpl.setAttribute4(xxqpPdftQplabsInfoOperVORowImpl.getAttribute4());
           newPdftQplabsInfoOperVORowImpl.setAttribute5(xxqpPdftQplabsInfoOperVORowImpl.getAttribute5());
            
           xxqpPdftQplabsInfoOperVOImpl.insertRow(newPdftQplabsInfoOperVORowImpl);
           oADBTransaction.commit();
           
       } /** END while(QplabsInfoOperIterator.hasNext()){ **/
          QplabsInfoOperIterator.closeRowSetIterator();
      } /** END if(null!=xxqpPdftQplabsInfoOperVOImpl){ **/
      
      if(null!=xxqpPdftQplabsPrecClieVOImpl){
       RowSetIterator  QplabsPrecClieIterator =  xxqpPdftQplabsPrecClieVOImpl.createRowSetIterator(null);
       while(QplabsPrecClieIterator.hasNext()){
           xxqpPdftQplabsPrecClieVORowImpl = (XxqpPdftQplabsPrecClieVORowImpl)QplabsPrecClieIterator.next();
           XxqpPdftQplabsPrecClieVORowImpl newPdftQplabsPrecClieVORowImpl = null; 
           newPdftQplabsPrecClieVORowImpl = (XxqpPdftQplabsPrecClieVORowImpl)xxqpPdftQplabsPrecClieVOImpl.createRow();
           
           oracle.jbo.domain.Number numQplabsPreciosClientesID = oADBTransaction.getSequenceValue("XXQP_PDFT_QPLABS_PREC_CLIE_S");
           newPdftQplabsPrecClieVORowImpl.setId(numQplabsPreciosClientesID);
           newPdftQplabsPrecClieVORowImpl.setQplabsHeaderId(numQpLabsHeaderId);
           newPdftQplabsPrecClieVORowImpl.setConcepto(xxqpPdftQplabsPrecClieVORowImpl.getConcepto());
           newPdftQplabsPrecClieVORowImpl.setPrecio(xxqpPdftQplabsPrecClieVORowImpl.getPrecio());
           newPdftQplabsPrecClieVORowImpl.setAttributeCategory(xxqpPdftQplabsPrecClieVORowImpl.getAttributeCategory());
           newPdftQplabsPrecClieVORowImpl.setAttribute1(xxqpPdftQplabsPrecClieVORowImpl.getAttribute1());
           newPdftQplabsPrecClieVORowImpl.setAttribute2(xxqpPdftQplabsPrecClieVORowImpl.getAttribute2());
           newPdftQplabsPrecClieVORowImpl.setAttribute3(xxqpPdftQplabsPrecClieVORowImpl.getAttribute3());
           newPdftQplabsPrecClieVORowImpl.setAttribute4(xxqpPdftQplabsPrecClieVORowImpl.getAttribute4());
           newPdftQplabsPrecClieVORowImpl.setAttribute5(xxqpPdftQplabsPrecClieVORowImpl.getAttribute5());
           
           xxqpPdftQplabsPrecClieVOImpl.insertRow(newPdftQplabsPrecClieVORowImpl);
           
           oADBTransaction.commit();
           
       } /** END while(QplabsPrecClieIterator.hasNext()){ **/
       
        QplabsPrecClieIterator.closeRowSetIterator();
       
      } /** END if(null!=xxqpPdftQplabsPrecClieVOImpl){ **/
      
       com.sun.java.util.collections.HashMap parameters  = new com.sun.java.util.collections.HashMap();
       if(null!=numQpLabsHeaderId){
           System.out.println("numQpLabsHeaderId:"+numQpLabsHeaderId);
           parameters.put("pQplabsHeaderId",numQpLabsHeaderId.toString());
       }
       pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsUpdPG" /*url*/
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

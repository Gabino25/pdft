package xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.BpoAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPagoVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPagoVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPrecioVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPrecioVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRegNegVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRegNegVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioAdquVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioAdquVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioGyCVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioGyCVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioOperVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioOperVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoServicioVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoServicioVORowImpl;

public class BpoCopiarCO extends OAControllerImpl {
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processRequest(pageContext, webBean);
    } 
    public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processFormRequest(pageContext, webBean);
      
        String strPuserPdft = null; 
        System.out.println("BpoCopiarCO strPuserPdft:"+strPuserPdft);
        if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
            strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
            System.out.println("BpoCopiarCO strPuserPdft:"+strPuserPdft);
        }
        
        String strPuserPdftId = null; 
        if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
            strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
            System.out.println("BpoCopiarCO strPuserPdftId:"+strPuserPdftId);
        }
        
        if(null==strPuserPdft||null==strPuserPdftId||"".equals(strPuserPdft)||"".equals(strPuserPdftId)){
           pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/webui/LoginPdftPG" /*url*/
                                     ,null /*functionName*/
                                     ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                     ,null /*menuName*/
                                     ,null /*parameters*/
                                     ,false /*retainAM*/
                                     ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                     ,OAException.ERROR /*messagingLevel*/
                                     );
        return;
        }
      
        String strCambioDePrecio = null; 
        if(null!=pageContext.getSessionValue("sCambioDePrecio")){
          strCambioDePrecio = (String)pageContext.getSessionValue("sCambioDePrecio"); 
          pageContext.removeSessionValue("sCambioDePrecio");
        }
        
        System.out.println("strCambioDePrecio:"+strCambioDePrecio);
        
        XxqpPdftBpoHeaderVOImpl  xxqpPdftBpoHeaderVOImpl = null; 
        XxqpPdftBpoHeaderVORowImpl  xxqpPdftBpoHeaderVORowImpl = null; 
        
        XxqpPdftBpoPrecioVOImpl  xxqpPdftBpoPrecioVOImpl = null; 
        XxqpPdftBpoPrecioVORowImpl  xxqpPdftBpoPrecioVORowImpl  = null; 
        
        XxqpPdftBpoServicioVOImpl xxqpPdftBpoServicioVOImpl = null; 
        XxqpPdftBpoServicioVORowImpl xxqpPdftBpoServicioVORowImpl = null; 
        
        XxqpPdftBpoRequeAdicioGyCVOImpl  xxqpPdftBpoRequeAdicioGyCVOImpl = null; 
        XxqpPdftBpoRequeAdicioGyCVORowImpl  xxqpPdftBpoRequeAdicioGyCVORowImpl = null; 
        
        XxqpPdftBpoRequeAdicioOperVOImpl xxqpPdftBpoRequeAdicioOperVOImpl = null; 
        XxqpPdftBpoRequeAdicioOperVORowImpl xxqpPdftBpoRequeAdicioOperVORowImpl = null; 
        
        XxqpPdftBpoRequeAdicioAdquVOImpl xxqpPdftBpoRequeAdicioAdquVOImpl = null; 
        XxqpPdftBpoRequeAdicioAdquVORowImpl xxqpPdftBpoRequeAdicioAdquVORowImpl = null; 
        
        XxqpPdftBpoPagoVOImpl  xxqpPdftBpoPagoVOImpl = null; 
        XxqpPdftBpoPagoVORowImpl  xxqpPdftBpoPagoVORowImpl = null; 
        
        XxqpPdftBpoRegNegVOImpl xxqpPdftBpoRegNegVOImpl = null; 
        XxqpPdftBpoRegNegVORowImpl xxqpPdftBpoRegNegVORowImpl = null; 
        
        BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
        
        OADBTransaction oADBTransaction = bpoAMImpl.getOADBTransaction(); 
        
        oracle.jbo.domain.Number numBpoHeaderId = null; 
        
        xxqpPdftBpoHeaderVOImpl = bpoAMImpl.getXxqpPdftBpoHeaderVO1(); 
        xxqpPdftBpoPrecioVOImpl = bpoAMImpl.getXxqpPdftBpoPrecioVO1();
        xxqpPdftBpoServicioVOImpl = bpoAMImpl.getXxqpPdftBpoServicioVO1(); 
        xxqpPdftBpoRequeAdicioGyCVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioGyCVO1();
        xxqpPdftBpoRequeAdicioOperVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioOperVO1(); 
        xxqpPdftBpoRequeAdicioAdquVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioAdquVO1();
        xxqpPdftBpoPagoVOImpl = bpoAMImpl.getXxqpPdftBpoPagoVO1();
        xxqpPdftBpoRegNegVOImpl = bpoAMImpl.getXxqpPdftBpoRegNegVO1();
        
        if(null!=xxqpPdftBpoHeaderVOImpl){
         RowSetIterator  BpoHeaderIterator =  xxqpPdftBpoHeaderVOImpl.createRowSetIterator(null);
         while(BpoHeaderIterator.hasNext()){
              numBpoHeaderId = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_HEADER_S");
             oracle.jbo.domain.Number numNumeroFt = oADBTransaction.getSequenceValue("XXQP_PDFT_NUMERO_FT_S");
             xxqpPdftBpoHeaderVORowImpl = (XxqpPdftBpoHeaderVORowImpl)BpoHeaderIterator.next(); 
             XxqpPdftBpoHeaderVORowImpl newPdftBpoHeaderVORowImpl = null; 
             newPdftBpoHeaderVORowImpl = (XxqpPdftBpoHeaderVORowImpl)xxqpPdftBpoHeaderVOImpl.createRow();
             
             newPdftBpoHeaderVORowImpl.setId(numBpoHeaderId);
             newPdftBpoHeaderVORowImpl.setNumeroFt(numNumeroFt);
             if(null!=strCambioDePrecio&&"Y".equals(strCambioDePrecio)){
                 newPdftBpoHeaderVORowImpl.setStatus("CAMBIO_DE_PRECIO");
             }else{
                 newPdftBpoHeaderVORowImpl.setStatus("PRELIMINAR");
             }
             newPdftBpoHeaderVORowImpl.setNumeroFtReferencia(xxqpPdftBpoHeaderVORowImpl.getNumeroFt());
             newPdftBpoHeaderVORowImpl.setPartyId(xxqpPdftBpoHeaderVORowImpl.getPartyId());
             newPdftBpoHeaderVORowImpl.setEmpresaQueFacturaC(xxqpPdftBpoHeaderVORowImpl.getEmpresaQueFacturaC());
             newPdftBpoHeaderVORowImpl.setUnidadDeNegocioC(xxqpPdftBpoHeaderVORowImpl.getUnidadDeNegocioC());
             newPdftBpoHeaderVORowImpl.setFrecuenciaFacturacionC(xxqpPdftBpoHeaderVORowImpl.getFrecuenciaFacturacionC());
             newPdftBpoHeaderVORowImpl.setContratoFlag(xxqpPdftBpoHeaderVORowImpl.getContratoFlag());
             newPdftBpoHeaderVORowImpl.setContratoFileName(xxqpPdftBpoHeaderVORowImpl.getContratoFileName());
             newPdftBpoHeaderVORowImpl.setContratoContentType(xxqpPdftBpoHeaderVORowImpl.getContratoContentType());
             newPdftBpoHeaderVORowImpl.setContratoFile(xxqpPdftBpoHeaderVORowImpl.getContratoFile());
             newPdftBpoHeaderVORowImpl.setFechaInicialOperacion(xxqpPdftBpoHeaderVORowImpl.getFechaInicialOperacion());
             newPdftBpoHeaderVORowImpl.setAttributeCategory(xxqpPdftBpoHeaderVORowImpl.getAttributeCategory());
          /**   newPdftBpoHeaderVORowImpl.setAttribute1(xxqpPdftBpoHeaderVORowImpl.getAttribute1()); Razon de Cancelacion **/
             newPdftBpoHeaderVORowImpl.setAttribute2(xxqpPdftBpoHeaderVORowImpl.getAttribute2());
             newPdftBpoHeaderVORowImpl.setAttribute3(xxqpPdftBpoHeaderVORowImpl.getAttribute3());
             newPdftBpoHeaderVORowImpl.setAttribute4(xxqpPdftBpoHeaderVORowImpl.getAttribute4());
             newPdftBpoHeaderVORowImpl.setAttribute5(xxqpPdftBpoHeaderVORowImpl.getAttribute5());
          /**   newPdftBpoHeaderVORowImpl.setEjecutivo(xxqpPdftBpoHeaderVORowImpl.getEjecutivo());  010320211357 **/
             newPdftBpoHeaderVORowImpl.setEjecutivo(strPuserPdftId);
             newPdftBpoHeaderVORowImpl.setArticuloOracle(xxqpPdftBpoHeaderVORowImpl.getArticuloOracle()); /** 17042020 Cuando se realiza un cambio de precio debe permanecer el articulo Oracle **/
             
             xxqpPdftBpoHeaderVOImpl.insertRow(newPdftBpoHeaderVORowImpl);
             
             oADBTransaction.commit();
             
         } /**END while(BpoHeaderIterator.hasNext()){ **/
         BpoHeaderIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftBpoHeaderVOImpl){ **/
        
        if(null!=xxqpPdftBpoPrecioVOImpl){
           RowSetIterator BpoPrecioIterator =  xxqpPdftBpoPrecioVOImpl.createRowSetIterator(null);
           while(BpoPrecioIterator.hasNext()){
              xxqpPdftBpoPrecioVORowImpl = (XxqpPdftBpoPrecioVORowImpl)BpoPrecioIterator.next();
               XxqpPdftBpoPrecioVORowImpl newPdftBpoPrecioVORowImpl = null; 
               newPdftBpoPrecioVORowImpl = (XxqpPdftBpoPrecioVORowImpl)xxqpPdftBpoPrecioVOImpl.createRow();
               
               oracle.jbo.domain.Number numBpoPrecioId = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_PRECIO_S");
               newPdftBpoPrecioVORowImpl.setId(numBpoPrecioId);
               newPdftBpoPrecioVORowImpl.setBpoHeaderId(numBpoHeaderId);
               newPdftBpoPrecioVORowImpl.setConcepto(xxqpPdftBpoPrecioVORowImpl.getConcepto());
               newPdftBpoPrecioVORowImpl.setCantidad(xxqpPdftBpoPrecioVORowImpl.getCantidad());
               newPdftBpoPrecioVORowImpl.setPrecioUnitario(xxqpPdftBpoPrecioVORowImpl.getPrecioUnitario());
               newPdftBpoPrecioVORowImpl.setTotalConcepto(xxqpPdftBpoPrecioVORowImpl.getTotalConcepto());
               newPdftBpoPrecioVORowImpl.setAttributeCategory(xxqpPdftBpoPrecioVORowImpl.getAttributeCategory());
               newPdftBpoPrecioVORowImpl.setAttribute1(xxqpPdftBpoPrecioVORowImpl.getAttribute1());
               newPdftBpoPrecioVORowImpl.setAttribute2(xxqpPdftBpoPrecioVORowImpl.getAttribute2());
               newPdftBpoPrecioVORowImpl.setAttribute3(xxqpPdftBpoPrecioVORowImpl.getAttribute3());
               newPdftBpoPrecioVORowImpl.setAttribute4(xxqpPdftBpoPrecioVORowImpl.getAttribute4());
               newPdftBpoPrecioVORowImpl.setAttribute5(xxqpPdftBpoPrecioVORowImpl.getAttribute5());
               
               xxqpPdftBpoPrecioVOImpl.insertRow(newPdftBpoPrecioVORowImpl);
               oADBTransaction.commit();
           } /** END  while(BpoPrecioIterator.hasNext()){ **/
            BpoPrecioIterator.closeRowSetIterator();
        } /** END  if(null!=xxqpPdftBpoPrecioVOImpl){ **/
        
        if(null!=xxqpPdftBpoServicioVOImpl){
          RowSetIterator BpoServicioIterator =  xxqpPdftBpoServicioVOImpl.createRowSetIterator(null);
          while(BpoServicioIterator.hasNext()){
             xxqpPdftBpoServicioVORowImpl = (XxqpPdftBpoServicioVORowImpl)BpoServicioIterator.next();
             XxqpPdftBpoServicioVORowImpl newPdftBpoServicioVORowImpl=null; 
             newPdftBpoServicioVORowImpl = (XxqpPdftBpoServicioVORowImpl)xxqpPdftBpoServicioVOImpl.createRow();
             
              oracle.jbo.domain.Number numBpoServicioId  = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_SERVICIO_S"); 
              newPdftBpoServicioVORowImpl.setId(numBpoServicioId);
              newPdftBpoServicioVORowImpl.setBpoHeaderId(numBpoHeaderId);
              newPdftBpoServicioVORowImpl.setCobranza(xxqpPdftBpoServicioVORowImpl.getCobranza());
              newPdftBpoServicioVORowImpl.setVentas(xxqpPdftBpoServicioVORowImpl.getVentas());
              newPdftBpoServicioVORowImpl.setGestorAdministrativo(xxqpPdftBpoServicioVORowImpl.getGestorAdministrativo());
              newPdftBpoServicioVORowImpl.setTecnico(xxqpPdftBpoServicioVORowImpl.getTecnico());
              newPdftBpoServicioVORowImpl.setRecoleDocMater(xxqpPdftBpoServicioVORowImpl.getRecoleDocMater());
              newPdftBpoServicioVORowImpl.setEntregaADomicilio(xxqpPdftBpoServicioVORowImpl.getEntregaADomicilio());
              newPdftBpoServicioVORowImpl.setOtros(xxqpPdftBpoServicioVORowImpl.getOtros());
              newPdftBpoServicioVORowImpl.setFechaInicioServicio(xxqpPdftBpoServicioVORowImpl.getFechaInicioServicio());
              newPdftBpoServicioVORowImpl.setDiasSemanaLaborara(xxqpPdftBpoServicioVORowImpl.getDiasSemanaLaborara());
              newPdftBpoServicioVORowImpl.setNoHrsDiarias(xxqpPdftBpoServicioVORowImpl.getNoHrsDiarias());
              newPdftBpoServicioVORowImpl.setElMensajeroManejeC(xxqpPdftBpoServicioVORowImpl.getElMensajeroManejeC());
              newPdftBpoServicioVORowImpl.setHorarioDeTrabajo(xxqpPdftBpoServicioVORowImpl.getHorarioDeTrabajo());
              newPdftBpoServicioVORowImpl.setDireccionBase(xxqpPdftBpoServicioVORowImpl.getDireccionBase());
              newPdftBpoServicioVORowImpl.setObservaciones(xxqpPdftBpoServicioVORowImpl.getObservaciones());
              newPdftBpoServicioVORowImpl.setAttributeCategory(xxqpPdftBpoServicioVORowImpl.getAttributeCategory());
              newPdftBpoServicioVORowImpl.setAttribute1(xxqpPdftBpoServicioVORowImpl.getAttribute1());
              newPdftBpoServicioVORowImpl.setAttribute2(xxqpPdftBpoServicioVORowImpl.getAttribute2());
              newPdftBpoServicioVORowImpl.setAttribute3(xxqpPdftBpoServicioVORowImpl.getAttribute3());
              newPdftBpoServicioVORowImpl.setAttribute4(xxqpPdftBpoServicioVORowImpl.getAttribute4());
              newPdftBpoServicioVORowImpl.setAttribute5(xxqpPdftBpoServicioVORowImpl.getAttribute5());
              newPdftBpoServicioVORowImpl.setHoraInicio(xxqpPdftBpoServicioVORowImpl.getHoraInicio());
              newPdftBpoServicioVORowImpl.setHoraFinal(xxqpPdftBpoServicioVORowImpl.getHoraFinal());
              newPdftBpoServicioVORowImpl.setLunes(xxqpPdftBpoServicioVORowImpl.getLunes());
              newPdftBpoServicioVORowImpl.setMartes(xxqpPdftBpoServicioVORowImpl.getMartes());
              newPdftBpoServicioVORowImpl.setMiercoles(xxqpPdftBpoServicioVORowImpl.getMiercoles());
              newPdftBpoServicioVORowImpl.setJueves(xxqpPdftBpoServicioVORowImpl.getJueves());
              newPdftBpoServicioVORowImpl.setViernes(xxqpPdftBpoServicioVORowImpl.getViernes());
              newPdftBpoServicioVORowImpl.setSabado(xxqpPdftBpoServicioVORowImpl.getSabado());
              newPdftBpoServicioVORowImpl.setDomingo(xxqpPdftBpoServicioVORowImpl.getDomingo());
              
              xxqpPdftBpoServicioVOImpl.insertRow(newPdftBpoServicioVORowImpl);
              oADBTransaction.commit();
          } /** END while(BpoServicioIterator.hasNext()){ **/
         BpoServicioIterator.closeRowSetIterator();
        } /** END if(null!=XxqpPdftBpoServicioVOImpl){ **/
        
        
        if(null!=xxqpPdftBpoRequeAdicioGyCVOImpl){
          RowSetIterator   BpoRequeAdicioGyCIterator = xxqpPdftBpoRequeAdicioGyCVOImpl.createRowSetIterator(null);
          while(BpoRequeAdicioGyCIterator.hasNext()){
            xxqpPdftBpoRequeAdicioGyCVORowImpl = (XxqpPdftBpoRequeAdicioGyCVORowImpl)BpoRequeAdicioGyCIterator.next();
            XxqpPdftBpoRequeAdicioGyCVORowImpl newPdftBpoRequeAdicioGyCVORowImpl = null; 
            newPdftBpoRequeAdicioGyCVORowImpl = (XxqpPdftBpoRequeAdicioGyCVORowImpl)xxqpPdftBpoRequeAdicioGyCVOImpl.createRow();
              oracle.jbo.domain.Number numRequAdicioID = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_REQUE_ADICIO_S");
              newPdftBpoRequeAdicioGyCVORowImpl.setId(numRequAdicioID);
              newPdftBpoRequeAdicioGyCVORowImpl.setBpoHeaderId(numBpoHeaderId);
              newPdftBpoRequeAdicioGyCVORowImpl.setCategoria(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCategoria());
              newPdftBpoRequeAdicioGyCVORowImpl.setMoto125(xxqpPdftBpoRequeAdicioGyCVORowImpl.getMoto125());
              newPdftBpoRequeAdicioGyCVORowImpl.setCasco(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCasco());
              newPdftBpoRequeAdicioGyCVORowImpl.setCajaGrande(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaGrande());
              newPdftBpoRequeAdicioGyCVORowImpl.setCajaChicaRoja(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaChicaRoja());
              newPdftBpoRequeAdicioGyCVORowImpl.setCamisas(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCamisas());
              newPdftBpoRequeAdicioGyCVORowImpl.setImpermeable(xxqpPdftBpoRequeAdicioGyCVORowImpl.getImpermeable());
              newPdftBpoRequeAdicioGyCVORowImpl.setTarjetaGasolina(xxqpPdftBpoRequeAdicioGyCVORowImpl.getTarjetaGasolina());
              newPdftBpoRequeAdicioGyCVORowImpl.setPantalonVestir(xxqpPdftBpoRequeAdicioGyCVORowImpl.getPantalonVestir());
              newPdftBpoRequeAdicioGyCVORowImpl.setCajaNegra(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaNegra());
              newPdftBpoRequeAdicioGyCVORowImpl.setHielera(xxqpPdftBpoRequeAdicioGyCVORowImpl.getHielera());
              newPdftBpoRequeAdicioGyCVORowImpl.setMovil(xxqpPdftBpoRequeAdicioGyCVORowImpl.getMovil());
              newPdftBpoRequeAdicioGyCVORowImpl.setAutomovil(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAutomovil());
              newPdftBpoRequeAdicioGyCVORowImpl.setGuiaRoji(xxqpPdftBpoRequeAdicioGyCVORowImpl.getGuiaRoji());
              newPdftBpoRequeAdicioGyCVORowImpl.setOtros(xxqpPdftBpoRequeAdicioGyCVORowImpl.getOtros());
              newPdftBpoRequeAdicioGyCVORowImpl.setComentarios(xxqpPdftBpoRequeAdicioGyCVORowImpl.getComentarios());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttributeCategory(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttributeCategory());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttribute1(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttribute1());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttribute2(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttribute2());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttribute3(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttribute3());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttribute4(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttribute4());
              newPdftBpoRequeAdicioGyCVORowImpl.setAttribute5(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAttribute5());
              
            xxqpPdftBpoRequeAdicioGyCVOImpl.insertRow(newPdftBpoRequeAdicioGyCVORowImpl);
              oADBTransaction.commit();
          } /** END while(BpoRequeAdicioGyCIterator.hasNext()){ **/
           BpoRequeAdicioGyCIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftBpoRequeAdicioGyCVOImpl){ **/
        
        if(null!=xxqpPdftBpoRequeAdicioOperVOImpl){
           RowSetIterator BpoRequeAdicioOperIterator = xxqpPdftBpoRequeAdicioOperVOImpl.createRowSetIterator(null);
           while(BpoRequeAdicioOperIterator.hasNext()){
              xxqpPdftBpoRequeAdicioOperVORowImpl = (XxqpPdftBpoRequeAdicioOperVORowImpl)BpoRequeAdicioOperIterator.next();
              XxqpPdftBpoRequeAdicioOperVORowImpl newPdftBpoRequeAdicioOperVORowImpl = null; 
              newPdftBpoRequeAdicioOperVORowImpl = (XxqpPdftBpoRequeAdicioOperVORowImpl)xxqpPdftBpoRequeAdicioOperVOImpl.createRow();
               
               oracle.jbo.domain.Number numRequAdicioID = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_REQUE_ADICIO_S");
               newPdftBpoRequeAdicioOperVORowImpl.setId(numRequAdicioID);
               newPdftBpoRequeAdicioOperVORowImpl.setBpoHeaderId(numBpoHeaderId);
               newPdftBpoRequeAdicioOperVORowImpl.setCategoria(xxqpPdftBpoRequeAdicioOperVORowImpl.getCategoria());
               newPdftBpoRequeAdicioOperVORowImpl.setMoto125(xxqpPdftBpoRequeAdicioOperVORowImpl.getMoto125());
               newPdftBpoRequeAdicioOperVORowImpl.setCasco(xxqpPdftBpoRequeAdicioOperVORowImpl.getCasco());
               newPdftBpoRequeAdicioOperVORowImpl.setCajaGrande(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaGrande());
               newPdftBpoRequeAdicioOperVORowImpl.setCajaChicaRoja(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaChicaRoja());
               newPdftBpoRequeAdicioOperVORowImpl.setCamisas(xxqpPdftBpoRequeAdicioOperVORowImpl.getCamisas());
               newPdftBpoRequeAdicioOperVORowImpl.setImpermeable(xxqpPdftBpoRequeAdicioOperVORowImpl.getImpermeable());
               newPdftBpoRequeAdicioOperVORowImpl.setTarjetaGasolina(xxqpPdftBpoRequeAdicioOperVORowImpl.getTarjetaGasolina());
               newPdftBpoRequeAdicioOperVORowImpl.setPantalonVestir(xxqpPdftBpoRequeAdicioOperVORowImpl.getPantalonVestir());
               newPdftBpoRequeAdicioOperVORowImpl.setCajaNegra(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaNegra());
               newPdftBpoRequeAdicioOperVORowImpl.setHielera(xxqpPdftBpoRequeAdicioOperVORowImpl.getHielera());
               newPdftBpoRequeAdicioOperVORowImpl.setMovil(xxqpPdftBpoRequeAdicioOperVORowImpl.getMovil());
               newPdftBpoRequeAdicioOperVORowImpl.setAutomovil(xxqpPdftBpoRequeAdicioOperVORowImpl.getAutomovil());
               newPdftBpoRequeAdicioOperVORowImpl.setGuiaRoji(xxqpPdftBpoRequeAdicioOperVORowImpl.getGuiaRoji());
               newPdftBpoRequeAdicioOperVORowImpl.setOtros(xxqpPdftBpoRequeAdicioOperVORowImpl.getOtros());
               newPdftBpoRequeAdicioOperVORowImpl.setComentarios(xxqpPdftBpoRequeAdicioOperVORowImpl.getComentarios());
               newPdftBpoRequeAdicioOperVORowImpl.setAttributeCategory(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttributeCategory());
               newPdftBpoRequeAdicioOperVORowImpl.setAttribute1(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttribute1());
               newPdftBpoRequeAdicioOperVORowImpl.setAttribute2(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttribute2());
               newPdftBpoRequeAdicioOperVORowImpl.setAttribute3(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttribute3());
               newPdftBpoRequeAdicioOperVORowImpl.setAttribute4(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttribute4());
               newPdftBpoRequeAdicioOperVORowImpl.setAttribute5(xxqpPdftBpoRequeAdicioOperVORowImpl.getAttribute5());
               
               xxqpPdftBpoRequeAdicioOperVOImpl.insertRow(newPdftBpoRequeAdicioOperVORowImpl);
               oADBTransaction.commit();
           } /** END while(BpoRequeAdicioOperIterator.hasNext()){ **/
            BpoRequeAdicioOperIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftBpoRequeAdicioOperVOImpl){ **/
        
        if(null!=xxqpPdftBpoRequeAdicioAdquVOImpl){
         RowSetIterator  BpoRequeAdicioAdquIterator =  xxqpPdftBpoRequeAdicioAdquVOImpl.createRowSetIterator(null);
         while(BpoRequeAdicioAdquIterator.hasNext()){
             xxqpPdftBpoRequeAdicioAdquVORowImpl = (XxqpPdftBpoRequeAdicioAdquVORowImpl)BpoRequeAdicioAdquIterator.next();
             XxqpPdftBpoRequeAdicioAdquVORowImpl newPdftBpoRequeAdicioAdquVORowImpl= null; 
             newPdftBpoRequeAdicioAdquVORowImpl = (XxqpPdftBpoRequeAdicioAdquVORowImpl)xxqpPdftBpoRequeAdicioAdquVOImpl.createRow();
             
             oracle.jbo.domain.Number numRequAdicioID = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_REQUE_ADICIO_S");
             newPdftBpoRequeAdicioAdquVORowImpl.setId(numRequAdicioID);
             newPdftBpoRequeAdicioAdquVORowImpl.setBpoHeaderId(numBpoHeaderId);
             newPdftBpoRequeAdicioAdquVORowImpl.setCategoria(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCategoria());
             newPdftBpoRequeAdicioAdquVORowImpl.setMoto125(xxqpPdftBpoRequeAdicioAdquVORowImpl.getMoto125());
             newPdftBpoRequeAdicioAdquVORowImpl.setCasco(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCasco());
             newPdftBpoRequeAdicioAdquVORowImpl.setCajaGrande(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaGrande());
             newPdftBpoRequeAdicioAdquVORowImpl.setCajaChicaRoja(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaChicaRoja());
             newPdftBpoRequeAdicioAdquVORowImpl.setCamisas(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCamisas());
             newPdftBpoRequeAdicioAdquVORowImpl.setImpermeable(xxqpPdftBpoRequeAdicioAdquVORowImpl.getImpermeable());
             newPdftBpoRequeAdicioAdquVORowImpl.setTarjetaGasolina(xxqpPdftBpoRequeAdicioAdquVORowImpl.getTarjetaGasolina());
             newPdftBpoRequeAdicioAdquVORowImpl.setPantalonVestir(xxqpPdftBpoRequeAdicioAdquVORowImpl.getPantalonVestir());
             newPdftBpoRequeAdicioAdquVORowImpl.setCajaNegra(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaNegra());
             newPdftBpoRequeAdicioAdquVORowImpl.setHielera(xxqpPdftBpoRequeAdicioAdquVORowImpl.getHielera());
             newPdftBpoRequeAdicioAdquVORowImpl.setMovil(xxqpPdftBpoRequeAdicioAdquVORowImpl.getMovil());
             newPdftBpoRequeAdicioAdquVORowImpl.setAutomovil(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAutomovil());
             newPdftBpoRequeAdicioAdquVORowImpl.setGuiaRoji(xxqpPdftBpoRequeAdicioAdquVORowImpl.getGuiaRoji());
             newPdftBpoRequeAdicioAdquVORowImpl.setOtros(xxqpPdftBpoRequeAdicioAdquVORowImpl.getOtros());
             newPdftBpoRequeAdicioAdquVORowImpl.setComentarios(xxqpPdftBpoRequeAdicioAdquVORowImpl.getComentarios());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttributeCategory(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttributeCategory());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttribute1(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttribute1());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttribute2(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttribute2());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttribute3(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttribute3());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttribute4(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttribute4());
             newPdftBpoRequeAdicioAdquVORowImpl.setAttribute5(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAttribute5());
             
             xxqpPdftBpoRequeAdicioAdquVOImpl.insertRow(newPdftBpoRequeAdicioAdquVORowImpl);
             oADBTransaction.commit();
         } /** END while(BpoRequeAdicioAdquIterator.hasNext()){ **/
          BpoRequeAdicioAdquIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftBpoRequeAdicioAdquVOImpl){ **/
        
        if(null!=xxqpPdftBpoPagoVOImpl){
         RowSetIterator BpoPagoIterator =  xxqpPdftBpoPagoVOImpl.createRowSetIterator(null);
         while(BpoPagoIterator.hasNext()){
            xxqpPdftBpoPagoVORowImpl = (XxqpPdftBpoPagoVORowImpl)BpoPagoIterator.next();
            XxqpPdftBpoPagoVORowImpl newPdftBpoPagoVORowImpl = null; 
            newPdftBpoPagoVORowImpl = (XxqpPdftBpoPagoVORowImpl)xxqpPdftBpoPagoVOImpl.createRow();
            
             oracle.jbo.domain.Number numBpoPagoId = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_PAGO_S"); 
             newPdftBpoPagoVORowImpl.setId(numBpoPagoId);
             newPdftBpoPagoVORowImpl.setBpoHeaderId(numBpoHeaderId);
             newPdftBpoPagoVORowImpl.setEjecutivoDeVentas(xxqpPdftBpoPagoVORowImpl.getEjecutivoDeVentas());
             newPdftBpoPagoVORowImpl.setPlaza(xxqpPdftBpoPagoVORowImpl.getPlaza());
             newPdftBpoPagoVORowImpl.setSubtotal(xxqpPdftBpoPagoVORowImpl.getSubtotal());
             newPdftBpoPagoVORowImpl.setIva(xxqpPdftBpoPagoVORowImpl.getIva());
             newPdftBpoPagoVORowImpl.setTotal(xxqpPdftBpoPagoVORowImpl.getTotal());
             newPdftBpoPagoVORowImpl.setFormaDePagoC(xxqpPdftBpoPagoVORowImpl.getFormaDePagoC());
             newPdftBpoPagoVORowImpl.setDiaDePago(xxqpPdftBpoPagoVORowImpl.getDiaDePago());
             newPdftBpoPagoVORowImpl.setAttributeCategory(xxqpPdftBpoPagoVORowImpl.getAttributeCategory());
             newPdftBpoPagoVORowImpl.setAttribute1(xxqpPdftBpoPagoVORowImpl.getAttribute1());
             newPdftBpoPagoVORowImpl.setAttribute2(xxqpPdftBpoPagoVORowImpl.getAttribute2());
             newPdftBpoPagoVORowImpl.setAttribute3(xxqpPdftBpoPagoVORowImpl.getAttribute3());
             newPdftBpoPagoVORowImpl.setAttribute4(xxqpPdftBpoPagoVORowImpl.getAttribute4());
             newPdftBpoPagoVORowImpl.setAttribute5(xxqpPdftBpoPagoVORowImpl.getAttribute5());
             
             xxqpPdftBpoPagoVOImpl.insertRow(newPdftBpoPagoVORowImpl);
             oADBTransaction.commit();
             
         } /** END while(BpoPagoIterator.hasNext()){ **/
          BpoPagoIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftBpoPagoVOImpl){ **/
        
         if(null!=xxqpPdftBpoRegNegVOImpl){
                    int countRegNeg = 0;
                    int FetchedRowCount = 0; 
                    int RowCount = 0; 
                    System.out.println("FetchedRowCount:"+xxqpPdftBpoRegNegVOImpl.getFetchedRowCount());
                    System.out.println("RowCount:"+xxqpPdftBpoRegNegVOImpl.getRowCount());
                    FetchedRowCount = xxqpPdftBpoRegNegVOImpl.getFetchedRowCount();
                    RowCount = xxqpPdftBpoRegNegVOImpl.getRowCount();
                    XxqpPdftBpoRegNegVORowImpl newArraPdftBpoRegNegVORowImpl[] = new XxqpPdftBpoRegNegVORowImpl[RowCount]; 
                    RowSetIterator MypRegNegIterator =   xxqpPdftBpoRegNegVOImpl.createRowSetIterator(null);
                    while(MypRegNegIterator.hasNext()){
                      xxqpPdftBpoRegNegVORowImpl = (XxqpPdftBpoRegNegVORowImpl)MypRegNegIterator.next();
                      newArraPdftBpoRegNegVORowImpl[countRegNeg] = xxqpPdftBpoRegNegVORowImpl;
                      countRegNeg = countRegNeg +1;
                    } /** END while(MypOtrosProcesosIterator.hasNext()){ **/
                    MypRegNegIterator.closeRowSetIterator();
                    for(int i=0;i<newArraPdftBpoRegNegVORowImpl.length;i++){
                        XxqpPdftBpoRegNegVORowImpl newPdftBpoRegNegVORowImpl = null; 
                        newPdftBpoRegNegVORowImpl = (XxqpPdftBpoRegNegVORowImpl)xxqpPdftBpoRegNegVOImpl.createRow();
                         oracle.jbo.domain.Number numRegNegID = oADBTransaction.getSequenceValue("XXQP_PDFT_BPO_REG_NEG_S");
                         newPdftBpoRegNegVORowImpl.setId(numRegNegID);
                         newPdftBpoRegNegVORowImpl.setBpoHeaderId(numBpoHeaderId);
                         newPdftBpoRegNegVORowImpl.setEstadoCode(newArraPdftBpoRegNegVORowImpl[i].getEstadoCode());
                         newPdftBpoRegNegVORowImpl.setConceptoCode(newArraPdftBpoRegNegVORowImpl[i].getConceptoCode());
                         newPdftBpoRegNegVORowImpl.setPrecio(newArraPdftBpoRegNegVORowImpl[i].getPrecio());
                         xxqpPdftBpoRegNegVOImpl.insertRow(newPdftBpoRegNegVORowImpl);
                         oADBTransaction.commit();
                    }
                }
        
         com.sun.java.util.collections.HashMap parameters  = new com.sun.java.util.collections.HashMap();
         if(null!=numBpoHeaderId){
             System.out.println("numBpoHeaderId:"+numBpoHeaderId);
             parameters.put("pBpoHeaderId",numBpoHeaderId.toString());
         }
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoUpdPG" /*url*/
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

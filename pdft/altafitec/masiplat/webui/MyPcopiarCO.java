package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypCoberturaVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypCoberturaVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypComentsProcesosVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypComentsProcesosVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypDistribucionVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypDistribucionVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypGeneralVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypGeneralVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypOtrosProcesosVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypOtrosProcesosVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypProcesosVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypProcesosVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypRegNegVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypRegNegVORowImpl;

public class MyPcopiarCO extends OAControllerImpl{
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processRequest(pageContext, webBean);
    } 
    public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
    {
      super.processFormRequest(pageContext, webBean);
      
        String strPuserPdft = null; 
        System.out.println("MyPcopiarCO strPuserPdft:"+strPuserPdft);
        if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
            strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
            System.out.println("MyPcopiarCO strPuserPdft:"+strPuserPdft);
        }
        
        String strPuserPdftId = null; 
        if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
            strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
            System.out.println("MyPcopiarCO strPuserPdftId:"+strPuserPdftId);
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
       
      
        XxqpPdftMypHeaderVOImpl xxqpPdftMypHeaderVOImpl = null; 
        XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = null; 
        
        XxqpPdftMypGeneralVOImpl xxqpPdftMypGeneralVOImpl = null;
        XxqpPdftMypGeneralVORowImpl xxqpPdftMypGeneralVORowImpl = null;
        
        XxqpPdftMypCoberturaVOImpl xxqpPdftMypCoberturaVOImpl = null;
        XxqpPdftMypCoberturaVORowImpl xxqpPdftMypCoberturaVORowImpl = null;
        
        XxqpPdftMypDistribucionVOImpl xxqpPdftMypDistribucionVOImpl = null;
        XxqpPdftMypDistribucionVORowImpl xxqpPdftMypDistribucionVORowImpl = null;
        
        XxqpPdftMypProcesosVOImpl xxqpPdftMypProcesosVOImpl = null; 
        XxqpPdftMypProcesosVORowImpl xxqpPdftMypProcesosVORowImpl = null; 
        
        XxqpPdftMypOtrosProcesosVOImpl xxqpPdftMypOtrosProcesosVOImpl = null; 
        XxqpPdftMypOtrosProcesosVORowImpl xxqpPdftMypOtrosProcesosVORowImpl = null; 
        
        XxqpPdftMypComentsProcesosVOImpl xxqpPdftMypComentsProcesosVOImpl = null; 
        XxqpPdftMypComentsProcesosVORowImpl xxqpPdftMypComentsProcesosVORowImpl = null; 
       
        XxqpPdftMypRegNegVOImpl xxqpPdftMypRegNegVOImpl = null; 
        XxqpPdftMypRegNegVORowImpl xxqpPdftMypRegNegVORowImpl = null; 
        
        MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
        
        OADBTransaction oADBTransaction = masivoYPlatinumAMImpl.getOADBTransaction(); 
        
        xxqpPdftMypHeaderVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1(); 
        xxqpPdftMypGeneralVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypGeneralVO1();
        xxqpPdftMypCoberturaVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypCoberturaVO1();
        xxqpPdftMypDistribucionVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypDistribucionVO1(); 
        xxqpPdftMypProcesosVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypProcesosVO1();
        xxqpPdftMypOtrosProcesosVOImpl= masivoYPlatinumAMImpl.getXxqpPdftMypOtrosProcesosVO1();
        xxqpPdftMypComentsProcesosVOImpl =  masivoYPlatinumAMImpl.getXxqpPdftMypComentsProcesosVO1(); 
        xxqpPdftMypRegNegVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypRegNegVO1(); 
        
        oracle.jbo.domain.Number numMasiYPlatHeaderId = null; 
        
        if(null!=xxqpPdftMypHeaderVOImpl){
          RowSetIterator MypHeaderIterator =   xxqpPdftMypHeaderVOImpl.createRowSetIterator(null); 
          while(MypHeaderIterator.hasNext()){
              xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)MypHeaderIterator.next();
              XxqpPdftMypHeaderVORowImpl newPdftMypHeaderVORowImpl = null; 
              newPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)xxqpPdftMypHeaderVOImpl.createRow();
              numMasiYPlatHeaderId = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_HEADER_S");
              oracle.jbo.domain.Number numNumeroFt = oADBTransaction.getSequenceValue("XXQP_PDFT_NUMERO_FT_S");
              newPdftMypHeaderVORowImpl.setId(numMasiYPlatHeaderId);
              newPdftMypHeaderVORowImpl.setNumeroFt(numNumeroFt);
              newPdftMypHeaderVORowImpl.setRev(null);
              if(null!=strCambioDePrecio&&"Y".equals(strCambioDePrecio)){
                  newPdftMypHeaderVORowImpl.setStatus("CAMBIO_DE_PRECIO");
                  newPdftMypHeaderVORowImpl.setArticuloOracle(xxqpPdftMypHeaderVORowImpl.getArticuloOracle()); /** 17042020 Cuando se realiza un cambio de precio debe permanecer el articulo Oracle **/
              }else{
                  newPdftMypHeaderVORowImpl.setStatus("PRELIMINAR");
              }
              newPdftMypHeaderVORowImpl.setNumeroFtReferencia(xxqpPdftMypHeaderVORowImpl.getNumeroFt());
              newPdftMypHeaderVORowImpl.setPartyId(xxqpPdftMypHeaderVORowImpl.getPartyId());
              newPdftMypHeaderVORowImpl.setPdftClienteHeaderId(xxqpPdftMypHeaderVORowImpl.getPdftClienteHeaderId());
              newPdftMypHeaderVORowImpl.setEmpresaQueFacturaC(xxqpPdftMypHeaderVORowImpl.getEmpresaQueFacturaC());
              newPdftMypHeaderVORowImpl.setUnidadDeNegocioC(xxqpPdftMypHeaderVORowImpl.getUnidadDeNegocioC());
              newPdftMypHeaderVORowImpl.setFrecuenciaFacturacionC(xxqpPdftMypHeaderVORowImpl.getFrecuenciaFacturacionC());
              newPdftMypHeaderVORowImpl.setContratoFlag(xxqpPdftMypHeaderVORowImpl.getContratoFlag());
              newPdftMypHeaderVORowImpl.setContratoFileName(xxqpPdftMypHeaderVORowImpl.getContratoFileName());
              newPdftMypHeaderVORowImpl.setContratoContentType(xxqpPdftMypHeaderVORowImpl.getContratoContentType());
              newPdftMypHeaderVORowImpl.setContratoFile(xxqpPdftMypHeaderVORowImpl.getContratoFile());
              newPdftMypHeaderVORowImpl.setFechaInicialOperacion(xxqpPdftMypHeaderVORowImpl.getFechaInicialOperacion());
              newPdftMypHeaderVORowImpl.setAttributeCategory(xxqpPdftMypHeaderVORowImpl.getAttributeCategory());
             /** newPdftMypHeaderVORowImpl.setAttribute1(xxqpPdftMypHeaderVORowImpl.getAttribute1());   Razon de Cancelacion **/
              newPdftMypHeaderVORowImpl.setAttribute2(xxqpPdftMypHeaderVORowImpl.getAttribute2());
              newPdftMypHeaderVORowImpl.setAttribute3(xxqpPdftMypHeaderVORowImpl.getAttribute3());
              newPdftMypHeaderVORowImpl.setAttribute4(xxqpPdftMypHeaderVORowImpl.getAttribute4());
              newPdftMypHeaderVORowImpl.setAttribute5(xxqpPdftMypHeaderVORowImpl.getAttribute5());
             /**  newPdftMypHeaderVORowImpl.setEjecutivo(xxqpPdftMypHeaderVORowImpl.getEjecutivo());  010320211346 **/
              newPdftMypHeaderVORowImpl.setEjecutivo(strPuserPdftId);
               
                newPdftMypHeaderVORowImpl.setFileName1(xxqpPdftMypHeaderVORowImpl.getFileName1());
                newPdftMypHeaderVORowImpl.setContentType1(xxqpPdftMypHeaderVORowImpl.getContentType1());
                newPdftMypHeaderVORowImpl.setFile1(xxqpPdftMypHeaderVORowImpl.getFile1());
               newPdftMypHeaderVORowImpl.setFileName2(xxqpPdftMypHeaderVORowImpl.getFileName2());
               newPdftMypHeaderVORowImpl.setContentType2(xxqpPdftMypHeaderVORowImpl.getContentType2());
               newPdftMypHeaderVORowImpl.setFile2(xxqpPdftMypHeaderVORowImpl.getFile2());
               newPdftMypHeaderVORowImpl.setFileName3(xxqpPdftMypHeaderVORowImpl.getFileName3());
               newPdftMypHeaderVORowImpl.setContentType3(xxqpPdftMypHeaderVORowImpl.getContentType3());
               newPdftMypHeaderVORowImpl.setFile3(xxqpPdftMypHeaderVORowImpl.getFile3());
               
              xxqpPdftMypHeaderVOImpl.insertRow(newPdftMypHeaderVORowImpl);
              oADBTransaction.commit();
           } /** END while(MypHeaderIterator.hasNext()){ **/
          MypHeaderIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftMypHeaderVOImpl){ **/
        
        if(null!=xxqpPdftMypGeneralVOImpl){
         RowSetIterator  MypGeneralIterator = xxqpPdftMypGeneralVOImpl.createRowSetIterator(null);
         while(MypGeneralIterator.hasNext()){
             xxqpPdftMypGeneralVORowImpl = (XxqpPdftMypGeneralVORowImpl)MypGeneralIterator.next(); 
             XxqpPdftMypGeneralVORowImpl newPdftMypGeneralVORowImpl = null; 
             newPdftMypGeneralVORowImpl = (XxqpPdftMypGeneralVORowImpl)xxqpPdftMypGeneralVOImpl.createRow();
             oracle.jbo.domain.Number numGeneralId = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_GENERAL_S");
             newPdftMypGeneralVORowImpl.setId(numGeneralId);
             newPdftMypGeneralVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
             newPdftMypGeneralVORowImpl.setImpresor(xxqpPdftMypGeneralVORowImpl.getImpresor());
             newPdftMypGeneralVORowImpl.setNombreProducto(xxqpPdftMypGeneralVORowImpl.getNombreProducto());
             newPdftMypGeneralVORowImpl.setVolumenAprox(xxqpPdftMypGeneralVORowImpl.getVolumenAprox());
             newPdftMypGeneralVORowImpl.setPuntoDeRecoleccion(xxqpPdftMypGeneralVORowImpl.getPuntoDeRecoleccion());
             newPdftMypGeneralVORowImpl.setContactoParaCierre(xxqpPdftMypGeneralVORowImpl.getContactoParaCierre());
             newPdftMypGeneralVORowImpl.setPeriodicidadC(xxqpPdftMypGeneralVORowImpl.getPeriodicidadC());
             newPdftMypGeneralVORowImpl.setTipoProductoC(xxqpPdftMypGeneralVORowImpl.getTipoProductoC());
             newPdftMypGeneralVORowImpl.setPesoProductoC(xxqpPdftMypGeneralVORowImpl.getPesoProductoC());
             newPdftMypGeneralVORowImpl.setDimensionesC(xxqpPdftMypGeneralVORowImpl.getDimensionesC());
             newPdftMypGeneralVORowImpl.setTipoComisionC(xxqpPdftMypGeneralVORowImpl.getTipoComisionC());
             newPdftMypGeneralVORowImpl.setSeFacturaC(xxqpPdftMypGeneralVORowImpl.getSeFacturaC());
             newPdftMypGeneralVORowImpl.setFormatoParaCierreC(xxqpPdftMypGeneralVORowImpl.getFormatoParaCierreC());
             newPdftMypGeneralVORowImpl.setDiasHabilesPago(xxqpPdftMypGeneralVORowImpl.getDiasHabilesPago());
             newPdftMypGeneralVORowImpl.setDiasRecepcionFaturacion(xxqpPdftMypGeneralVORowImpl.getDiasRecepcionFaturacion());
             newPdftMypGeneralVORowImpl.setRequiereVoboC(xxqpPdftMypGeneralVORowImpl.getRequiereVoboC());
             newPdftMypGeneralVORowImpl.setTipoVobo(xxqpPdftMypGeneralVORowImpl.getTipoVobo());
             newPdftMypGeneralVORowImpl.setTipoDeEntregaC(xxqpPdftMypGeneralVORowImpl.getTipoDeEntregaC());
             newPdftMypGeneralVORowImpl.setPoliticaDeEntregaC(xxqpPdftMypGeneralVORowImpl.getPoliticaDeEntregaC());
             newPdftMypGeneralVORowImpl.setAttributeCategory(xxqpPdftMypGeneralVORowImpl.getAttributeCategory());
             newPdftMypGeneralVORowImpl.setAttribute1(xxqpPdftMypGeneralVORowImpl.getAttribute1());
             newPdftMypGeneralVORowImpl.setAttribute2(xxqpPdftMypGeneralVORowImpl.getAttribute2());
             newPdftMypGeneralVORowImpl.setAttribute3(xxqpPdftMypGeneralVORowImpl.getAttribute3());
             newPdftMypGeneralVORowImpl.setAttribute4(xxqpPdftMypGeneralVORowImpl.getAttribute4());
             newPdftMypGeneralVORowImpl.setAttribute5(xxqpPdftMypGeneralVORowImpl.getAttribute5());
             newPdftMypGeneralVORowImpl.setAcuseC(xxqpPdftMypGeneralVORowImpl.getAcuseC());
             newPdftMypGeneralVORowImpl.setOrdinarioC(xxqpPdftMypGeneralVORowImpl.getOrdinarioC());
             newPdftMypGeneralVORowImpl.setSemiAcuseC(xxqpPdftMypGeneralVORowImpl.getSemiAcuseC());
             newPdftMypGeneralVORowImpl.setLunes(xxqpPdftMypGeneralVORowImpl.getLunes());
             newPdftMypGeneralVORowImpl.setMartes(xxqpPdftMypGeneralVORowImpl.getMartes());
             newPdftMypGeneralVORowImpl.setMiercoles(xxqpPdftMypGeneralVORowImpl.getMiercoles());
             newPdftMypGeneralVORowImpl.setJueves(xxqpPdftMypGeneralVORowImpl.getJueves());
             newPdftMypGeneralVORowImpl.setViernes(xxqpPdftMypGeneralVORowImpl.getViernes());
             newPdftMypGeneralVORowImpl.setSabado(xxqpPdftMypGeneralVORowImpl.getSabado());
             newPdftMypGeneralVORowImpl.setDomingo(xxqpPdftMypGeneralVORowImpl.getDomingo());
             
             xxqpPdftMypGeneralVOImpl.insertRow(newPdftMypGeneralVORowImpl);
             oADBTransaction.commit();
         }
         MypGeneralIterator.closeRowSetIterator();
        }/** END if(null!=xxqpPdftMypGeneralVOImpl){ **/
        
        if(null!=xxqpPdftMypCoberturaVOImpl){
           RowSetIterator MypCoberturaIterator =  xxqpPdftMypCoberturaVOImpl.createRowSetIterator(null);
           while(MypCoberturaIterator.hasNext()){
              xxqpPdftMypCoberturaVORowImpl = (XxqpPdftMypCoberturaVORowImpl)MypCoberturaIterator.next();
               XxqpPdftMypCoberturaVORowImpl newPdftMypCoberturaVORowImpl = null; 
               newPdftMypCoberturaVORowImpl = (XxqpPdftMypCoberturaVORowImpl)xxqpPdftMypCoberturaVOImpl.createRow();
               oracle.jbo.domain.Number numIdCobertura = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_COBERTURA_S");
               newPdftMypCoberturaVORowImpl.setId(numIdCobertura);
               newPdftMypCoberturaVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
               newPdftMypCoberturaVORowImpl.setPlazaPropietariaC(xxqpPdftMypCoberturaVORowImpl.getPlazaPropietariaC());
             /**  newPdftMypCoberturaVORowImpl.setTipoCoberturaC(xxqpPdftMypCoberturaVORowImpl.getTipoCoberturaC());  **/
               newPdftMypCoberturaVORowImpl.setTcNacional(xxqpPdftMypCoberturaVORowImpl.getTcNacional());
               newPdftMypCoberturaVORowImpl.setTcRegional(xxqpPdftMypCoberturaVORowImpl.getTcRegional());
               newPdftMypCoberturaVORowImpl.setTcLocal(xxqpPdftMypCoberturaVORowImpl.getTcLocal() );
               newPdftMypCoberturaVORowImpl.setMencionarEstados(xxqpPdftMypCoberturaVORowImpl.getMencionarEstados());
               newPdftMypCoberturaVORowImpl.setEntregaLocal(xxqpPdftMypCoberturaVORowImpl.getEntregaLocal());
               newPdftMypCoberturaVORowImpl.setDrLocal(xxqpPdftMypCoberturaVORowImpl.getDrLocal());
               newPdftMypCoberturaVORowImpl.setDiLocal(xxqpPdftMypCoberturaVORowImpl.getDiLocal());
               newPdftMypCoberturaVORowImpl.setEntregaForaneo(xxqpPdftMypCoberturaVORowImpl.getEntregaForaneo());
               newPdftMypCoberturaVORowImpl.setDrForaneo(xxqpPdftMypCoberturaVORowImpl.getDrForaneo());
               newPdftMypCoberturaVORowImpl.setDiForaneo(xxqpPdftMypCoberturaVORowImpl.getDiForaneo());
               newPdftMypCoberturaVORowImpl.setAttributeCategory(xxqpPdftMypCoberturaVORowImpl.getAttributeCategory());
               newPdftMypCoberturaVORowImpl.setAttribute1(xxqpPdftMypCoberturaVORowImpl.getAttribute1());
               newPdftMypCoberturaVORowImpl.setAttribute2(xxqpPdftMypCoberturaVORowImpl.getAttribute2());
               newPdftMypCoberturaVORowImpl.setAttribute3(xxqpPdftMypCoberturaVORowImpl.getAttribute3());
               newPdftMypCoberturaVORowImpl.setAttribute4(xxqpPdftMypCoberturaVORowImpl.getAttribute4());
               newPdftMypCoberturaVORowImpl.setAttribute5(xxqpPdftMypCoberturaVORowImpl.getAttribute5());
               newPdftMypCoberturaVORowImpl.setComentarios(xxqpPdftMypCoberturaVORowImpl.getComentarios());
               newPdftMypCoberturaVORowImpl.setComentariosIlim(xxqpPdftMypCoberturaVORowImpl.getComentariosIlim());
               
               xxqpPdftMypCoberturaVOImpl.insertRow(newPdftMypCoberturaVORowImpl);
               oADBTransaction.commit();
               
           } /** END  while(MypCoberturaIterator.hasNext()){ **/
           MypCoberturaIterator.closeRowSetIterator();
            
        } /** END if(null!=xxqpPdftMypCoberturaVOImpl){ **/
        
        if(null!=xxqpPdftMypDistribucionVOImpl){
         RowSetIterator  MypDistribucionIterator =  xxqpPdftMypDistribucionVOImpl.createRowSetIterator(null);
         while(MypDistribucionIterator.hasNext()){
             xxqpPdftMypDistribucionVORowImpl  = (XxqpPdftMypDistribucionVORowImpl)MypDistribucionIterator.next();
             XxqpPdftMypDistribucionVORowImpl newPdftMypDistribucionVORowImpl = null; 
             newPdftMypDistribucionVORowImpl = (XxqpPdftMypDistribucionVORowImpl)xxqpPdftMypDistribucionVOImpl.createRow();
             oracle.jbo.domain.Number numDistribucionID = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_DISTRIBUCION_S");
             newPdftMypDistribucionVORowImpl.setId(numDistribucionID);
             newPdftMypDistribucionVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
             newPdftMypDistribucionVORowImpl.setDigitalizacionAcuses(xxqpPdftMypDistribucionVORowImpl.getDigitalizacionAcuses());
             newPdftMypDistribucionVORowImpl.setCapturaDevoluciones(xxqpPdftMypDistribucionVORowImpl.getCapturaDevoluciones());
             newPdftMypDistribucionVORowImpl.setReporteGps(xxqpPdftMypDistribucionVORowImpl.getReporteGps());
             newPdftMypDistribucionVORowImpl.setReporteRecepcion(xxqpPdftMypDistribucionVORowImpl.getReporteRecepcion());
             newPdftMypDistribucionVORowImpl.setCapturaAcuses(xxqpPdftMypDistribucionVORowImpl.getCapturaAcuses());
             newPdftMypDistribucionVORowImpl.setSeguimientoQuejas(xxqpPdftMypDistribucionVORowImpl.getSeguimientoQuejas());
             newPdftMypDistribucionVORowImpl.setProporcionamosInsumos(xxqpPdftMypDistribucionVORowImpl.getProporcionamosInsumos());
             newPdftMypDistribucionVORowImpl.setEtiquetado(xxqpPdftMypDistribucionVORowImpl.getEtiquetado());
             newPdftMypDistribucionVORowImpl.setEnsobretado(xxqpPdftMypDistribucionVORowImpl.getEnsobretado());
             newPdftMypDistribucionVORowImpl.setGeneracionAcuse(xxqpPdftMypDistribucionVORowImpl.getGeneracionAcuse());
             newPdftMypDistribucionVORowImpl.setDiasOperacionLocal(xxqpPdftMypDistribucionVORowImpl.getDiasOperacionLocal());
             newPdftMypDistribucionVORowImpl.setDiasOperacionForaneo(xxqpPdftMypDistribucionVORowImpl.getDiasOperacionForaneo());
             newPdftMypDistribucionVORowImpl.setCierreElectronico(xxqpPdftMypDistribucionVORowImpl.getCierreElectronico());
             newPdftMypDistribucionVORowImpl.setEnvioPiezasFisicas(xxqpPdftMypDistribucionVORowImpl.getEnvioPiezasFisicas());
             newPdftMypDistribucionVORowImpl.setComentariosDistribucion(xxqpPdftMypDistribucionVORowImpl.getComentariosDistribucion()); 
             newPdftMypDistribucionVORowImpl.setAttributeCategory(xxqpPdftMypDistribucionVORowImpl.getAttributeCategory());
             newPdftMypDistribucionVORowImpl.setAttribute1(xxqpPdftMypDistribucionVORowImpl.getAttribute1());
             newPdftMypDistribucionVORowImpl.setAttribute2(xxqpPdftMypDistribucionVORowImpl.getAttribute2());
             newPdftMypDistribucionVORowImpl.setAttribute3(xxqpPdftMypDistribucionVORowImpl.getAttribute3());
             newPdftMypDistribucionVORowImpl.setAttribute4(xxqpPdftMypDistribucionVORowImpl.getAttribute4());
             newPdftMypDistribucionVORowImpl.setAttribute5(xxqpPdftMypDistribucionVORowImpl.getAttribute5());
             newPdftMypDistribucionVORowImpl.setComentariosDistribucionIlim(xxqpPdftMypDistribucionVORowImpl.getComentariosDistribucionIlim());
             
             xxqpPdftMypDistribucionVOImpl.insertRow(newPdftMypDistribucionVORowImpl);
             oADBTransaction.commit();
         } /** END  while(MypDistribucionIterator.next()){ **/
         MypDistribucionIterator.closeRowSetIterator();
            
        } /** END if(null!=XxqpPdftMypDistribucionVOImpl){ **/
        
       
        if(null!=xxqpPdftMypProcesosVOImpl){
            int countProcesos = 0;
            int FetchedRowCount = 0; 
            int RowCount = 0; 
            System.out.println("FetchedRowCount:"+xxqpPdftMypProcesosVOImpl.getFetchedRowCount());
            System.out.println("RowCount:"+xxqpPdftMypProcesosVOImpl.getRowCount());
            RowCount = xxqpPdftMypProcesosVOImpl.getRowCount();
            FetchedRowCount = xxqpPdftMypProcesosVOImpl.getFetchedRowCount();
            XxqpPdftMypProcesosVORowImpl newArrayPdftMypProcesosVORowImpl[] = new XxqpPdftMypProcesosVORowImpl[RowCount];
           RowSetIterator MypProcesosIterator = xxqpPdftMypProcesosVOImpl.createRowSetIterator(null);
           while(MypProcesosIterator.hasNext()){
               
              xxqpPdftMypProcesosVORowImpl = (XxqpPdftMypProcesosVORowImpl)MypProcesosIterator.next();
               newArrayPdftMypProcesosVORowImpl[countProcesos]=xxqpPdftMypProcesosVORowImpl;
               countProcesos = countProcesos +1;
           } /**END while(MypProcesosIterator.hasNext()){ **/
            MypProcesosIterator.closeRowSetIterator();
            
            for(int i=0;i<newArrayPdftMypProcesosVORowImpl.length;i++){
                XxqpPdftMypProcesosVORowImpl newPdftMypProcesosVORowImpl = null; 
                newPdftMypProcesosVORowImpl = (XxqpPdftMypProcesosVORowImpl)xxqpPdftMypProcesosVOImpl.createRow();
                 oracle.jbo.domain.Number numProcesoDeCorreoDirectoID = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_PROCESOS_S");
                 newPdftMypProcesosVORowImpl.setId(numProcesoDeCorreoDirectoID);
                 newPdftMypProcesosVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
                 newPdftMypProcesosVORowImpl.setRegion("PROCESO");
                 newPdftMypProcesosVORowImpl.setProseso(newArrayPdftMypProcesosVORowImpl[i].getProseso());
                 newPdftMypProcesosVORowImpl.setSeleccionar(newArrayPdftMypProcesosVORowImpl[i].getSeleccionar());
                 newPdftMypProcesosVORowImpl.setPrecio(newArrayPdftMypProcesosVORowImpl[i].getPrecio());
                 newPdftMypProcesosVORowImpl.setAttributeCategory(newArrayPdftMypProcesosVORowImpl[i].getAttributeCategory());
                 newPdftMypProcesosVORowImpl.setAttribute1(newArrayPdftMypProcesosVORowImpl[i].getAttribute1());
                 newPdftMypProcesosVORowImpl.setAttribute2(newArrayPdftMypProcesosVORowImpl[i].getAttribute2());
                 newPdftMypProcesosVORowImpl.setAttribute3(newArrayPdftMypProcesosVORowImpl[i].getAttribute3());
                 newPdftMypProcesosVORowImpl.setAttribute4(newArrayPdftMypProcesosVORowImpl[i].getAttribute4());
                 newPdftMypProcesosVORowImpl.setAttribute5(newArrayPdftMypProcesosVORowImpl[i].getAttribute5());
                 
                 xxqpPdftMypProcesosVOImpl.insertRow(newPdftMypProcesosVORowImpl);
                 oADBTransaction.commit();
            }
            
        } /** END if(null!=xxqpPdftMypProcesosVOImpl){ **/
        
        if(null!=xxqpPdftMypOtrosProcesosVOImpl){
            int countOtrosProcesos = 0;
            int FetchedRowCount = 0; 
            int RowCount = 0; 
            System.out.println("FetchedRowCount:"+xxqpPdftMypOtrosProcesosVOImpl.getFetchedRowCount());
            System.out.println("RowCount:"+xxqpPdftMypOtrosProcesosVOImpl.getRowCount());
            FetchedRowCount = xxqpPdftMypOtrosProcesosVOImpl.getFetchedRowCount();
            RowCount = xxqpPdftMypOtrosProcesosVOImpl.getRowCount();
            XxqpPdftMypOtrosProcesosVORowImpl newArrayPdftMypOtrosProcesosVORowImpl[] = new XxqpPdftMypOtrosProcesosVORowImpl[RowCount]; 
          RowSetIterator MypOtrosProcesosIterator =   xxqpPdftMypOtrosProcesosVOImpl.createRowSetIterator(null);
          while(MypOtrosProcesosIterator.hasNext()){
              xxqpPdftMypOtrosProcesosVORowImpl = (XxqpPdftMypOtrosProcesosVORowImpl)MypOtrosProcesosIterator.next();
              newArrayPdftMypOtrosProcesosVORowImpl[countOtrosProcesos] = xxqpPdftMypOtrosProcesosVORowImpl;
              countOtrosProcesos = countOtrosProcesos +1;
          } /** END while(MypOtrosProcesosIterator.hasNext()){ **/
           MypOtrosProcesosIterator.closeRowSetIterator();
           
           for(int i=0;i<newArrayPdftMypOtrosProcesosVORowImpl.length;i++){
               XxqpPdftMypOtrosProcesosVORowImpl newPdftMypOtrosProcesosVORowImpl = null;
               newPdftMypOtrosProcesosVORowImpl = (XxqpPdftMypOtrosProcesosVORowImpl)xxqpPdftMypOtrosProcesosVOImpl.createRow();
               
               oracle.jbo.domain.Number numProcesoDeCorreoDirectoID = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_PROCESOS_S");
               newPdftMypOtrosProcesosVORowImpl.setId(numProcesoDeCorreoDirectoID);
               newPdftMypOtrosProcesosVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
               newPdftMypOtrosProcesosVORowImpl.setRegion("OTROS_PROCESOS");
               newPdftMypOtrosProcesosVORowImpl.setOtrosProcesos(newArrayPdftMypOtrosProcesosVORowImpl[i].getOtrosProcesos());
               newPdftMypOtrosProcesosVORowImpl.setSeleccionar(newArrayPdftMypOtrosProcesosVORowImpl[i].getSeleccionar());
               newPdftMypOtrosProcesosVORowImpl.setPrecio(newArrayPdftMypOtrosProcesosVORowImpl[i].getPrecio());
               newPdftMypOtrosProcesosVORowImpl.setAttributeCategory(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttributeCategory());
               newPdftMypOtrosProcesosVORowImpl.setAttribute1(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttribute1());
               newPdftMypOtrosProcesosVORowImpl.setAttribute2(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttribute2());
               newPdftMypOtrosProcesosVORowImpl.setAttribute3(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttribute3());
               newPdftMypOtrosProcesosVORowImpl.setAttribute4(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttribute4());
               newPdftMypOtrosProcesosVORowImpl.setAttribute5(newArrayPdftMypOtrosProcesosVORowImpl[i].getAttribute5());
               
               xxqpPdftMypOtrosProcesosVOImpl.insertRow(newPdftMypOtrosProcesosVORowImpl);
               oADBTransaction.commit();
           }
           
        } /** END if(null!=xxqpPdftMypOtrosProcesosVOImpl){ **/
      
        if(null!=xxqpPdftMypComentsProcesosVOImpl){
          RowSetIterator ComentsProcesosIterator =  xxqpPdftMypComentsProcesosVOImpl.createRowSetIterator(null);
          while(ComentsProcesosIterator.hasNext()){
              xxqpPdftMypComentsProcesosVORowImpl = (XxqpPdftMypComentsProcesosVORowImpl)ComentsProcesosIterator.next();
              XxqpPdftMypComentsProcesosVORowImpl newPdftMypComentsProcesosVORowImpl = null;
              newPdftMypComentsProcesosVORowImpl = (XxqpPdftMypComentsProcesosVORowImpl)xxqpPdftMypComentsProcesosVOImpl.createRow();
              oracle.jbo.domain.Number numProcesoDeCorreoDirectoID = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_PROCESOS_S");
              newPdftMypComentsProcesosVORowImpl.setId(numProcesoDeCorreoDirectoID);
              newPdftMypComentsProcesosVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
              newPdftMypComentsProcesosVORowImpl.setRegion("COMENTARIOS");
              newPdftMypComentsProcesosVORowImpl.setComentariosInstrucc(xxqpPdftMypComentsProcesosVORowImpl.getComentariosInstrucc());
              newPdftMypComentsProcesosVORowImpl.setComentariosInstruccIlim(xxqpPdftMypComentsProcesosVORowImpl.getComentariosInstruccIlim());
              xxqpPdftMypComentsProcesosVOImpl.insertRow(newPdftMypComentsProcesosVORowImpl);
              oADBTransaction.commit();
              
          }
            ComentsProcesosIterator.closeRowSetIterator();
        } /** END if(null!=xxqpPdftMypComentsProcesosVOImpl){ **/
      
        if(null!=xxqpPdftMypRegNegVOImpl){
            int countRegNeg = 0;
            int FetchedRowCount = 0; 
            int RowCount = 0; 
            System.out.println("FetchedRowCount:"+xxqpPdftMypRegNegVOImpl.getFetchedRowCount());
            System.out.println("RowCount:"+xxqpPdftMypRegNegVOImpl.getRowCount());
            FetchedRowCount = xxqpPdftMypRegNegVOImpl.getFetchedRowCount();
            RowCount = xxqpPdftMypRegNegVOImpl.getRowCount();
            XxqpPdftMypRegNegVORowImpl newArraPdftMypRegNegVORowImpl[] = new XxqpPdftMypRegNegVORowImpl[RowCount]; 
            RowSetIterator MypRegNegIterator =   xxqpPdftMypRegNegVOImpl.createRowSetIterator(null);
            while(MypRegNegIterator.hasNext()){
              xxqpPdftMypRegNegVORowImpl = (XxqpPdftMypRegNegVORowImpl)MypRegNegIterator.next();
              newArraPdftMypRegNegVORowImpl[countRegNeg] = xxqpPdftMypRegNegVORowImpl;
              countRegNeg = countRegNeg +1;
            } /** END while(MypOtrosProcesosIterator.hasNext()){ **/
            MypRegNegIterator.closeRowSetIterator();
            for(int i=0;i<newArraPdftMypRegNegVORowImpl.length;i++){
                XxqpPdftMypRegNegVORowImpl newPdftMypRegNegVORowImpl = null; 
                newPdftMypRegNegVORowImpl = (XxqpPdftMypRegNegVORowImpl)xxqpPdftMypRegNegVOImpl.createRow();
                 oracle.jbo.domain.Number numRegNegID = oADBTransaction.getSequenceValue("XXQP_PDFT_MYP_REG_NEG_S");
                 newPdftMypRegNegVORowImpl.setId(numRegNegID);
                 newPdftMypRegNegVORowImpl.setMypHeaderId(numMasiYPlatHeaderId);
                 newPdftMypRegNegVORowImpl.setEstadoCode(newArraPdftMypRegNegVORowImpl[i].getEstadoCode());
                 newPdftMypRegNegVORowImpl.setConceptoCode(newArraPdftMypRegNegVORowImpl[i].getConceptoCode());
                 newPdftMypRegNegVORowImpl.setPrecio(newArraPdftMypRegNegVORowImpl[i].getPrecio());
                 xxqpPdftMypRegNegVOImpl.insertRow(newPdftMypRegNegVORowImpl);
                 oADBTransaction.commit();
            }
        }
      
        com.sun.java.util.collections.HashMap parameters  = new com.sun.java.util.collections.HashMap();
        if(null!=numMasiYPlatHeaderId){
            System.out.println("numMasiYPlatHeaderId:"+numMasiYPlatHeaderId);
            parameters.put("pMyPHeaderId",numMasiYPlatHeaderId.toString());
        }
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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

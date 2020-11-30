/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAProcessingPage;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OABodyBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBeanMessage;
import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageCheckBoxBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageRadioGroupBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;

import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.fnd.framework.webui.beans.table.OATableBean;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.domain.BlobDomain;

import oracle.jbo.server.ApplicationModuleImpl;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.OtrosProcesosTmpVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.ProcesosTmpVOImpl;

/**
 * Controller for ...
 */
public class MasivoYPlatinumCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
 // private static int blockMultipleSubmits = 0;  
   private String strOaSelectedSubtabIdx0 = "N"; 
   private String strOaSelectedSubtabIdx1 = "N"; 
   private String strOaSelectedSubtabIdx2 = "N"; 
   private String strOaSelectedSubtabIdx3 = "N"; 
   private int gIntSelectedIndex = -1;
   
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
       super.processRequest(pageContext, webBean);
       OAWebBean body = pageContext.getRootWebBean(); 
       if (body instanceof OABodyBean){
           ((OABodyBean)body).setBlockOnEverySubmit(true); 
       }
      
      OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN");
      OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoExamine");
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OASubmitButtonBean ValidarBean = (OASubmitButtonBean)webBean.findChildRecursive("Validar");
      
      String strPUnidadDeNegocio = pageContext.getParameter("pUnidadDeNegocio");
      String strPEmpresaQueFactura = pageContext.getParameter("pEmpresaQueFactura");
      String strpCicloFacturacion = pageContext.getParameter("pCicloFacturacion");
      String strpEjecutivo = pageContext.getParameter("pEjecutivo");
      
      System.out.println("strpCicloFacturacion:"+strpCicloFacturacion);
      
      MasivoYPlatinumAMImpl  masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
      
      environmentCurrencyFormat(pageContext,webBean);
     
      if(null!=strPUnidadDeNegocio){
          String strCamposRequeridos  = masivoYPlatinumAMImpl.getCamposRequeridosByUnidadDeNegocio(strPUnidadDeNegocio);
          if("N".equals(strCamposRequeridos)){
              environmentNoRequiredFields(pageContext,webBean);
          }
        /**********************************************************************
         if(null!=strPUnidadDeNegocio){
         if("ME".equals(strPUnidadDeNegocio)
         ||"POP_DELIVERY".equals(strPUnidadDeNegocio)
         ||"E-COMMERCE".equals(strPUnidadDeNegocio)
         ||"FIELD_SERVICE".equals(strPUnidadDeNegocio)){
              environmentNoRequiredFields(pageContext,webBean);
          }
         } 
         *******************************************************************/
      } 
     
      
      if(null!=NombreUsuarioEBSBean){
          String strPuserPdft = null; 
          if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
              strPuserPdft =pageContext.getTransientSessionValue("tsUserPdft").toString();
          }else{
              strPuserPdft = "CAP CONS_ASTI";
          }
          //NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
           NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
      }
      
      if(null!=ContratoExamineBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "ContratoFileName"); 
          ContratoExamineBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
    
      
      
      if(null==PageLayoutRNBean){
       PageLayoutRNBean = pageContext.getPageLayoutBean();
      }
      if(null!=PageLayoutRNBean){
        if(null!=strPUnidadDeNegocio){
          String strUnidadNegocioM =  masivoYPlatinumAMImpl.getUnidadNegocioM(strPUnidadDeNegocio);
          PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strUnidadNegocioM);
          PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strUnidadNegocioM);
        }
      }
      
   String strOaSelectedSubtab =  pageContext.getParameter(OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX);
   System.out.println("strOaSelectedSubtab:"+strOaSelectedSubtab);
      OAFormValueBean FvOaSelectedSubtabIdx1Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx1");
      OAFormValueBean FvOaSelectedSubtabIdx2Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx2");
      OAFormValueBean FvOaSelectedSubtabIdx3Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx3");
      
   if("1".equals(strOaSelectedSubtab)){
       if(null!=FvOaSelectedSubtabIdx1Bean){
           FvOaSelectedSubtabIdx1Bean.setValue(pageContext,"Y");
       }
   }else if("2".equals(strOaSelectedSubtab)){
       if(null!=FvOaSelectedSubtabIdx2Bean){
           FvOaSelectedSubtabIdx2Bean.setValue(pageContext,"Y");
       }
   } else if("3".equals(strOaSelectedSubtab)){
       if(null!=FvOaSelectedSubtabIdx3Bean){
           FvOaSelectedSubtabIdx3Bean.setValue(pageContext,"Y");
       }
   }
   
   
   if(null!=FvOaSelectedSubtabIdx1Bean){
    if(null!=FvOaSelectedSubtabIdx1Bean.getValue(pageContext)){
        strOaSelectedSubtabIdx1 = FvOaSelectedSubtabIdx1Bean.getValue(pageContext).toString();
    }
   }
   
  if(null!=FvOaSelectedSubtabIdx2Bean){
   if(null!=FvOaSelectedSubtabIdx2Bean.getValue(pageContext)){
       strOaSelectedSubtabIdx2 = FvOaSelectedSubtabIdx2Bean.getValue(pageContext).toString();
   }
  }
  
  if(null!=FvOaSelectedSubtabIdx3Bean){
   if(null!=FvOaSelectedSubtabIdx3Bean.getValue(pageContext)){
       strOaSelectedSubtabIdx3 = FvOaSelectedSubtabIdx3Bean.getValue(pageContext).toString();
   }
  }
  
   if("Y".equals(strOaSelectedSubtabIdx1)
    &&"Y".equals(strOaSelectedSubtabIdx2)
    &&"Y".equals(strOaSelectedSubtabIdx3)
    ){
    if(null!=ValidarBean){
        ValidarBean.setDisabled(false);
     }
    }else{
        if(null!=ValidarBean){
            ValidarBean.setDisabled(true);
        }
    }
    
     
      if(!pageContext.isFormSubmission()){
          String lstrPartyID = null; 
          OAFormValueBean PartyIdBean = (OAFormValueBean)webBean.findChildRecursive("PartyId");
          if(null!=PartyIdBean){
           if(null!=PartyIdBean.getValue(pageContext)){
               lstrPartyID = PartyIdBean.getValue(pageContext).toString();
               masivoYPlatinumAMImpl.initEstadosLov(lstrPartyID,null);  
               masivoYPlatinumAMImpl.initConceptosLov(lstrPartyID,null);
           }
          }
      }
     
    if(!pageContext.isFormSubmission()){
       System.out.println("!pageContext.isFormSubmission():"+!pageContext.isFormSubmission());
        String strEnabledGrabar = pageContext.getParameter("pEnabledGrabar");   
        if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){
            OAButtonBean grabarBean = (OAButtonBean)webBean.findChildRecursive("Grabar"); 
            if(null!=grabarBean){
                grabarBean.setDisabled(false);
                grabarBean.setOnClick("this.disabled=true;");
            }
        }else{
            OAButtonBean grabarBean = (OAButtonBean)webBean.findChildRecursive("Grabar"); 
            if(null!=grabarBean){
                grabarBean.setDisabled(true);
            }
            
            System.out.println("initMypHeaderTmpVO"); 
            masivoYPlatinumAMImpl.initMypHeaderTmpVO();
            
          
            OAMessageChoiceBean UnidadDeNegocioBean = (OAMessageChoiceBean)webBean.findChildRecursive("UnidadDeNegocio");
            if(null!=UnidadDeNegocioBean){
                if(null!=strPUnidadDeNegocio&&!"".equals(strPUnidadDeNegocio))
                UnidadDeNegocioBean.setValue(pageContext,strPUnidadDeNegocio);
            }
            OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
            if(null!=EmpresaQueFacturaBean){
                if(null!=strPEmpresaQueFactura&&!"".equals(strPEmpresaQueFactura))
               EmpresaQueFacturaBean.setValue(pageContext,strPEmpresaQueFactura);
            }
            OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacion");
             if(null!=CicloFacturacionBean){
                 System.out.println("strpCicloFacturacion2:"+strpCicloFacturacion);
                 if(null!=strpCicloFacturacion&&!"".equals(strpCicloFacturacion))
               CicloFacturacionBean.setValue(pageContext,strpCicloFacturacion);
             }
            
            OAMessageChoiceBean EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
             if(null!=EjecutivoBean){
                 if(null!=strpEjecutivo&&!"".equals(strpEjecutivo))
               EjecutivoBean.setValue(pageContext,strpEjecutivo);
             }
             
            if(null!=masivoYPlatinumAMImpl){
                masivoYPlatinumAMImpl.fillCamposHead(pageContext,webBean);
            } 
            
            /* Se Pidio que no se llenen los campos 05082020
            environmentClientInfo(pageContext
                                 ,webBean
                                 ,strPEmpresaQueFactura
                                 ,strpCicloFacturacion
                                 );
            */
            /** Si no se ejecuta aqui El objeto de visualización MasivoYPlatinumAM.MypHeaderTmpVO1 no incluyó registros. **/
            
            System.out.println("initProcesosTmpVO");  
            masivoYPlatinumAMImpl.initProcesosTmpVO();
            System.out.println("initOtrosProcesosTmpVO"); 
            masivoYPlatinumAMImpl.initOtrosProcesosTmpVO();  
            
            
        } /** END if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){ **/
         
    }else{
        System.out.println("!pageContext.isFormSubmission():"+!pageContext.isFormSubmission());
    }
    
      OASubTabLayoutBean SubTabLayoutBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
      if(null!=SubTabLayoutBean){
        gIntSelectedIndex =  SubTabLayoutBean.getSelectedIndex(pageContext);
        System.out.println("gIntSelectedIndex:"+gIntSelectedIndex);
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
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    
    
    MasivoYPlatinumAMImpl  masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
    
    System.out.println("strEventParam:"+strEventParam);
    /************************** Reglas de Negocio *************
     **********************************************************
     **********************************************************/
    if("AgregarRegNegEvt".equals(strEventParam)){
        masivoYPlatinumAMImpl.createRowRegNeg();
        return;
    }
    if("DeleteRegNegEvt".equals(strEventParam)){
       String strDeleteRegNegId = pageContext.getParameter("pDeleteRegNegId");
       System.out.println("strDeleteRegNegId:"+strDeleteRegNegId);
       masivoYPlatinumAMImpl.deleteRowRegNeg(strDeleteRegNegId);
       return; 
    }
    
    if("lovPrepare".equals(strEventParam)){
      String lovInputSourceId = pageContext.getLovInputSourceId();
      String lstrPartyID = null; 
        OAFormValueBean PartyIdBean = (OAFormValueBean)webBean.findChildRecursive("PartyId");
        if(null!=PartyIdBean){
         if(null!=PartyIdBean.getValue(pageContext)){
             lstrPartyID = PartyIdBean.getValue(pageContext).toString();
         }
        }
      System.out.println("lovInputSourceId:"+lovInputSourceId);
      System.out.println("strPartyID:"+lstrPartyID);
      if("EstadoMeaning".equals(lovInputSourceId)){
         pageContext.putSessionValue("svPartyId",lstrPartyID);
       }  
        if("ConceptoMeaning".equals(lovInputSourceId)){
           pageContext.putSessionValue("svPartyId",lstrPartyID);
       }  
      return; 
    }
    
    /***************************************************************************
     ************** Header *****************************************************
     ***************************************************************************/
    String strContrato = null; 
    String strContratoExamineFileName = null; 
    String strContratoExamineContentType = null; 
    BlobDomain ContratoExamineByteStream = null;
    String strEstatusValue = null; 
    String strUnidadDeNegocioValue = null; 
    String strEmpresaQueFacturaValue = null; 
    String strCicloFacturacionValue = null; 
    String strFechaInicioOperacionValue = null; 
    String strPartyID = null; 
    String strEjecutivoValue = null; 
    String strValida = "Y";
      
    OAMessageCheckBoxBean ContratoBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Contrato");
    if(null!=ContratoBean){
      if(null!=ContratoBean.getValue(pageContext)){
          strContrato = ContratoBean.getValue(pageContext).toString();
      }
    }
    
    DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoExamine"); 
    if(null!=ContratoExamineUploadData){
      strContratoExamineFileName = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_NAME").toString();
      strContratoExamineContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
      ContratoExamineByteStream = (BlobDomain)ContratoExamineUploadData.selectValue(null,strContratoExamineFileName);
      System.out.println("strContratoExamineFileName:"+strContratoExamineFileName);
        if(null!=strContratoExamineFileName){
        masivoYPlatinumAMImpl.getMypHeaderTmpVO1().getCurrentRow().setAttribute("ContratoFileName",strContratoExamineFileName);
        if(null==ContratoExamineByteStream){
             ContratoExamineByteStream = (BlobDomain)masivoYPlatinumAMImpl.getMypHeaderTmpVO1().getCurrentRow().getAttribute("ContratoFile");
         }
        }
      /**  
      System.out.println("strContratoExamineContentType:"+strContratoExamineContentType);
      System.out.println("ContratoExamineByteStream:"+ContratoExamineByteStream);
      **/
      
    }
    
    
   
    OAMessageChoiceBean  EstatusBean = (OAMessageChoiceBean)webBean.findChildRecursive("Estatus");
    if(null!=EstatusBean){
     if(null!=EstatusBean.getValue(pageContext)){
      strEstatusValue = EstatusBean.getValue(pageContext).toString();
     }
    }
     
      OAMessageChoiceBean  EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
      if(null!=EjecutivoBean){
       if(null!=EjecutivoBean.getValue(pageContext)){
        strEjecutivoValue = EjecutivoBean.getValue(pageContext).toString();
       }
      } 
      
     strUnidadDeNegocioValue = getValueFromOAMessageChoiceBean(pageContext,webBean,"UnidadDeNegocio");
     
     if(null!=strUnidadDeNegocioValue){
     if("ME".equals(strUnidadDeNegocioValue)
     ||"POP_DELIVERY".equals(strUnidadDeNegocioValue)
     ||"E-COMMERCE".equals(strUnidadDeNegocioValue)
     ||"FIELD_SERVICE".equals(strUnidadDeNegocioValue)){
      strValida = "N";
      }
     } 
      
     strEmpresaQueFacturaValue = getValueFromOAMessageChoiceBean(pageContext,webBean,"EmpresaQueFactura");
     strCicloFacturacionValue =   getValueFromOAMessageChoiceBean(pageContext,webBean,"CicloFacturacion");
     
     OAMessageDateFieldBean FechaInicioOperacionBean = (OAMessageDateFieldBean)webBean.findChildRecursive("FechaInicioOperacion");
     if(null!=FechaInicioOperacionBean){
       if(null!=FechaInicioOperacionBean.getValue(pageContext)){
           strFechaInicioOperacionValue = FechaInicioOperacionBean.getValue(pageContext).toString();
       }
     } 
     
      OAFormValueBean PartyIdBean = (OAFormValueBean)webBean.findChildRecursive("PartyId");
      if(null!=PartyIdBean){
       if(null!=PartyIdBean.getValue(pageContext)){
           strPartyID = PartyIdBean.getValue(pageContext).toString();
       }
      }
     
     /**************************************************************************
      ********************** Cobertura *****************************************
      *************************************************************************/
    
    
     String strImpresorValue =null;  
     String strNombreProductoValue = null; 
     String strVolumenAproxValue = null; 
     String strTipoProductoValue =null; 
     String strPesoProductoValue = null; 
     String strPuntoDeRecoleccionValue =null; 
     String strContactoParaCierreValue = null; 
     String strPeriodicidadValue = null; 
     String strDimensionesValue = null;
     String strTipoComisionValue = null; 
     String strSeFacturaValue = null;  
     String strFormatoParaCierreValue = null;  
     String strDiasHabilesPagoValue = null; 
     String strDiasRecepcionDeFacturacionValue = null; 
     String strRequiereVoBoValue = null; 
     String strTipoVoBoValue = null;
     String strTipoDeEntregaValue = null; 
     String strPoliticaDeEntregaValue = null; 
     String strComentarios = null; 
     String strLunes = null; 
     String strMartes = null; 
     String strMiercoles = null; 
     String strJueves = null; 
     String strViernes = null;
     String strSabado = null; 
     String strDomingo = null; 
     String strAcuseValue = null; 
     String strOrdinarioValue = null; 
     String strSemiAcuseValue = null; 
     
      
    strImpresorValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Impresor");
    strNombreProductoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"NombreProducto");
    strVolumenAproxValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"VolumenAprox");
    strTipoProductoValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"TipoProducto");
    strPesoProductoValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"PesoProducto");
    strPuntoDeRecoleccionValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"PuntoDeRecoleccion");
    strContactoParaCierreValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"ContactoParaCierre");
    strPeriodicidadValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"Periodicidad");
    strDimensionesValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"Dimensiones");
    strTipoComisionValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"TipoComision");
    strSeFacturaValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"SeFactura");
    strFormatoParaCierreValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"FormatoParaCierre");
    strDiasHabilesPagoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiasHabilesPago");
    strDiasRecepcionDeFacturacionValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiasRecepcionDeFacturacion");
    strRequiereVoBoValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"RequiereVoBo");
    strTipoVoBoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"TipoVoBo");
    OAMessageRadioGroupBean  TipoDeEntregaBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("TipoDeEntrega");
    if(null!=TipoDeEntregaBean){
      if(null!=TipoDeEntregaBean.getValue(pageContext)){
          strTipoDeEntregaValue = TipoDeEntregaBean.getValue(pageContext).toString();
      }
    }
   
      strPoliticaDeEntregaValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"PoliticaDeEntrega");
      strComentarios = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Comentarios");
      strLunes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Lunes");
      strMartes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Martes");
      strMiercoles = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Miercoles");
      strJueves = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Jueves");
      strViernes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Viernes");
      strSabado = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Sabado");
      strDomingo = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Domingo");
      strAcuseValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"Acuse");
      strOrdinarioValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"Ordinario");
      strSemiAcuseValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"SemiAcuse");
      
      com.sun.java.util.collections.List listExcpt = new com.sun.java.util.collections.ArrayList(); 
     if("Y".equals(strRequiereVoBoValue)){
        if(null==strTipoVoBoValue||"".equals(strTipoVoBoValue)){
         listExcpt.add(new OAException("Si el valor de Requiere VoBo es Si el campo Tipo VoBo es obligatorio.",OAException.ERROR)); 
        }
     }
     
     if(listExcpt.size()>0&&"Y".equals(strValida)){
         OAException.raiseBundledOAException(listExcpt);
     }
     
      
   /**************************************************************************
    ********************** Distribucion *****************************************
    *************************************************************************/
     String strDigitalizacionDeAcusesValue = null; 
     String strCapturaDeDevolucionesValue = null; 
     String strReporteGPSValue = null; 
     String strReporteDeRecepcionValue = null; 
     String strCapturaDeAcusesValue = null; 
     String strSeguimientoDeQuejasValue = null; 
     String strProporcionamosInsumosValue = null;
     String strEtiquetadoValue = null; 
     String strEnsobretadoValue = null;
     String strGeneracionDeAcuseValue = null; 
     String strDiasOperacionLocalValue = null; 
     String strDiasOperacionForaneoValue = null; 
     String strCierreElectronicoValue = null; 
     String strEnvioPiezasFisicasValue = null; 
     String strComentariosSobreDistribucionValue = null; 
     
      
     
     strDigitalizacionDeAcusesValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"DigitalizacionDeAcuses");
     strCapturaDeDevolucionesValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"CapturaDeDevoluciones");
     strReporteGPSValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"ReporteGPS");
     strReporteDeRecepcionValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"ReporteDeRecepcion");
     strCapturaDeAcusesValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"CapturaDeAcuses");
     strSeguimientoDeQuejasValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"SeguimientoDeQuejas");
     strProporcionamosInsumosValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"ProporcionamosInsumos");
     strEtiquetadoValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Etiquetado");
     strEnsobretadoValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Ensobretado");
     strGeneracionDeAcuseValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GeneracionDeAcuse");
      
     strDiasOperacionLocalValue=this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiasOperacionLocal");
     strDiasOperacionForaneoValue=this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiasOperacionForaneo");
     strCierreElectronicoValue =this.getValueFromOAMessageTextInputBean(pageContext,webBean,"CierreElectronico");
     strEnvioPiezasFisicasValue =this.getValueFromOAMessageTextInputBean(pageContext,webBean,"EnvioPiezasFisicas");
     strComentariosSobreDistribucionValue=this.getValueFromOAMessageTextInputBean(pageContext,webBean,"ComentariosSobreDistribucion");
      
       
    /***************************************************************************
     *********************** General *******************************************
     **************************************************************************/             
            
   
     String strPlazaPropietariaValue = null; 
   /**  String strTipoCoberturaValue = null; **/
     String strTiCobNacional = null; 
     String strTiCobRegional = null; 
     String strTiCobLocal = null;
      
     String strMencionarEstadosValue = null; 
     String strEntregaLocalValue = null; 
     String strDRLocalValue = null; 
     String strDILocalValue = null; 
     String strEntregaForaneoValue = null; 
     String strDRForaneoValue = null; 
     String strDIForaneoValue = null; 
      
     strPlazaPropietariaValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"PlazaPropietaria");
    /** strTipoCoberturaValue = this.getValueFromOAMessageRadioGroupBean(pageContext,webBean,"TipoCobertura"); **/
      strTiCobNacional = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"TiCobNacional");
      strTiCobRegional = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"TiCobRegional");
      strTiCobLocal = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"TiCobLocal");
     strMencionarEstadosValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"MencionarEstados");
     strEntregaLocalValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"EntregaLocal");
     strDRLocalValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DRLocal");
     strDILocalValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DILocal");
     strEntregaForaneoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"EntregaForaneo");
     strDRForaneoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DRForaneo");
     strDIForaneoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DIForaneo");
     
   /****************************************************************************
    ****************** Procesos de Correo Directo ******************************
    ****************************************************************************/
    String strComentariosOInstrucciones = null; 
    strComentariosOInstrucciones = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"ComentariosOInstrucciones"); 
    
    if(3==gIntSelectedIndex){
       ProcesosTmpVOImpl procesosTmpVOImpl =  masivoYPlatinumAMImpl.getProcesosTmpVO1();
       OtrosProcesosTmpVOImpl otrosProcesosTmpVOImpl = masivoYPlatinumAMImpl.getOtrosProcesosTmpVO1();
       com.sun.java.util.collections.List listProcExcpt = new com.sun.java.util.collections.ArrayList(); 
       com.sun.java.util.collections.List listOtrosProcExcpt = new com.sun.java.util.collections.ArrayList(); 
        
        listProcExcpt = procesosTmpVOImpl.validaPrecios(); 
        if(listProcExcpt.size()>0&&"Y".equals(strValida)){
         OAException.raiseBundledOAException(listProcExcpt);
        }
        
        listOtrosProcExcpt = otrosProcesosTmpVOImpl.validaPrecios(); 
        if(listOtrosProcExcpt.size()>0&&"Y".equals(strValida)){
            OAException.raiseBundledOAException(listOtrosProcExcpt);
        }
        
    }
     
   /***************************************************************************
    ********* Almacenar Informacion *******************************************
    ***************************************************************************/
    
   
    if(null!=pageContext.getParameter("Validar")){
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pEnabledGrabar","Y");
        pageContext.forwardImmediatelyToCurrentPage(parameters
                                                   ,true
                                                   ,null);
    } 
   
    if("GrabarEvt".equals(strEventParam)){ 
    synchronized(this){
    StringBuilder strMasiYPlatHeaderId = new StringBuilder("");
    masivoYPlatinumAMImpl.fillHeader( strMasiYPlatHeaderId
                                    , strContrato
                                    , strContratoExamineFileName 
                                    , strContratoExamineContentType 
                                    , ContratoExamineByteStream 
                                    , strEstatusValue 
                                    , strUnidadDeNegocioValue 
                                    , strEmpresaQueFacturaValue  
                                    , strCicloFacturacionValue  
                                    , strFechaInicioOperacionValue 
                                    , strPartyID
                                    , strEjecutivoValue
                                    );
                                 
        masivoYPlatinumAMImpl.fillGeneral(strMasiYPlatHeaderId
                                        , strImpresorValue  
                                        , strNombreProductoValue 
                                        , strVolumenAproxValue 
                                        , strTipoProductoValue 
                                        , strPesoProductoValue 
                                        , strPuntoDeRecoleccionValue  
                                        , strContactoParaCierreValue
                                        , strPeriodicidadValue 
                                        , strDimensionesValue 
                                        , strTipoComisionValue 
                                        , strSeFacturaValue
                                        , strFormatoParaCierreValue 
                                        , strDiasHabilesPagoValue 
                                        , strDiasRecepcionDeFacturacionValue  
                                        , strRequiereVoBoValue 
                                        , strTipoVoBoValue 
                                        , strTipoDeEntregaValue
                                        , strPoliticaDeEntregaValue 
                                        , strLunes
                                        , strMartes
                                        , strMiercoles
                                        , strJueves
                                        , strViernes
                                        , strSabado
                                        , strDomingo
                                        , strAcuseValue
                                        , strOrdinarioValue
                                        , strSemiAcuseValue
                                        );    
           
        masivoYPlatinumAMImpl.fillCobertura(strMasiYPlatHeaderId
                                           ,strPlazaPropietariaValue
                                         /**  ,strTipoCoberturaValue **/
                                           ,strTiCobNacional
                                           ,strTiCobRegional
                                           ,strTiCobLocal
                                           ,strMencionarEstadosValue
                                           ,strEntregaLocalValue
                                           ,strDRLocalValue 
                                           ,strDILocalValue 
                                           ,strEntregaForaneoValue 
                                           ,strDRForaneoValue 
                                           ,strDIForaneoValue 
                                           ,strComentarios
                                         );    
        
        masivoYPlatinumAMImpl.fillDistribucion(strMasiYPlatHeaderId
                                             , strDigitalizacionDeAcusesValue 
                                             , strCapturaDeDevolucionesValue 
                                             , strReporteGPSValue 
                                             , strReporteDeRecepcionValue 
                                             , strCapturaDeAcusesValue 
                                             , strSeguimientoDeQuejasValue 
                                             , strProporcionamosInsumosValue 
                                             , strEtiquetadoValue 
                                             , strEnsobretadoValue 
                                             , strGeneracionDeAcuseValue 
                                             , strDiasOperacionLocalValue 
                                             , strDiasOperacionForaneoValue  
                                             , strCierreElectronicoValue
                                             , strEnvioPiezasFisicasValue 
                                             , strComentariosSobreDistribucionValue   
                                               );                                  
     
        if(null!=masivoYPlatinumAMImpl){
            masivoYPlatinumAMImpl.fillProcesosCorreoDirecto(strMasiYPlatHeaderId
                                                           ,strComentariosOInstrucciones
                                                           );
        }
        
       masivoYPlatinumAMImpl.fillReglasDeNegocio(strMasiYPlatHeaderId); 
        
      com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
      parameters.put("pMyPHeaderId",strMasiYPlatHeaderId.toString() );
      pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                ,null /*functionName*/
                                ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                ,null /*menuName*/
                                ,parameters /*parameters*/
                                ,false /*retainAM*/
                                ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                ,OAException.ERROR /*messagingLevel*/
                                ); 
                                
         } /** END synchronized(this){ **/                             
     } /** END if("".equals(strEventParam)){ **/       
               
  //  blockMultipleSubmits = 0;     
     
    
  }

    private String getValueFromOAMessageChoiceBean(OAPageContext pageContext, 
                                                   OAWebBean webBean, 
                                                   String pStrIdBean) {
        String strRetVal = null; 
        OAMessageChoiceBean temporalBean = (OAMessageChoiceBean)webBean.findChildRecursive(pStrIdBean); 
        if(null!=temporalBean){
          if(null!=temporalBean.getValue(pageContext)){
           strRetVal = temporalBean.getValue(pageContext).toString();
          }
        }
        return strRetVal;
    }

    private String getValueFromOAMessageTextInputBean(OAPageContext pageContext, 
                                                      OAWebBean webBean, 
                                                      String pStrIdBean) {
        String strRetVal = null; 
        OAMessageTextInputBean temporalBean = (OAMessageTextInputBean)webBean.findChildRecursive(pStrIdBean); 
        if(null!=temporalBean){
          if(null!=temporalBean.getValue(pageContext)){
           strRetVal = temporalBean.getValue(pageContext).toString();
          }
        }
        return strRetVal;                                                 
    }
    
    private String getValueFromOAMessageRadioGroupBean(OAPageContext pageContext, 
                                                   OAWebBean webBean, 
                                                   String pStrIdBean) {
        String strRetVal = null; 
        OAMessageRadioGroupBean temporalBean = (OAMessageRadioGroupBean)webBean.findChildRecursive(pStrIdBean); 
        if(null!=temporalBean){
          if(null!=temporalBean.getValue(pageContext)){
           strRetVal = temporalBean.getValue(pageContext).toString();
          }
        }
        return strRetVal;
    }
    
    private String getValueFromOAMessageCheckBoxBean(OAPageContext pageContext, 
                                                   OAWebBean webBean, 
                                                   String pStrIdBean) {
        String strRetVal = null; 
        OAMessageCheckBoxBean temporalBean = (OAMessageCheckBoxBean)webBean.findChildRecursive(pStrIdBean); 
        if(null!=temporalBean){
          if(null!=temporalBean.getValue(pageContext)){
           strRetVal = temporalBean.getValue(pageContext).toString();
          }
        }
        return strRetVal;
    }

    private void environmentCurrencyFormat(OAPageContext pageContext
                                          ,OAWebBean webBean
                                          ) {
     OATableBean ProcesosTmpBean = (OATableBean)webBean.findChildRecursive("ProcesosTmp");
     if(null!=ProcesosTmpBean){
       OAMessageTextInputBean PrecioBean = (OAMessageTextInputBean)ProcesosTmpBean.findChildRecursive("Precio");
       if(null!=PrecioBean){
           PrecioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
       }
     }
     
        OATableBean OtrosProcesosTmpBean = (OATableBean)webBean.findChildRecursive("OtrosProcesosTmp");
        if(null!=OtrosProcesosTmpBean){
          OAMessageTextInputBean OtrosPrecioBean = (OAMessageTextInputBean)OtrosProcesosTmpBean.findChildRecursive("OtrosPrecio");
          if(null!=OtrosPrecioBean){
              OtrosPrecioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
        }
        
        OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioTmpVORN");
        if(null!=ReglasDeNegocioVORNBean){
          OAMessageTextInputBean PrecioRegNegBean = (OAMessageTextInputBean)ReglasDeNegocioVORNBean.findChildRecursive("PrecioRegNeg");
          if(null!=PrecioRegNegBean){
              PrecioRegNegBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
        }
        
        OAMessageTextInputBean textInputBean = null; 
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntregaLocal"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DRLocal"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DILocal"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntregaForaneo"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DRForaneo"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
         textInputBean = (OAMessageTextInputBean)webBean.findChildRecursive("DIForaneo"); 
         if(null!=textInputBean){
             textInputBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
         }
     
    } /** environmentCurrencyFormat **/

    /**
     * 
     * @param pageContext
     * @param webBean
     */
    private void environmentClientInfo(OAPageContext pageContext, 
                                       OAWebBean webBean
                                       ,String fillEmpresa
                                       ,String fillFrecFact) {
        OAApplicationModuleImpl  oAApplicationModuleImpl = (OAApplicationModuleImpl)pageContext.getApplicationModule(webBean);
        String strPartyId = pageContext.getParameter("pPartyId");/*"6041";*/
        if(null==strPartyId){
         throw new OAException("No se encontro valor del parametro pPartyId.",OAException.ERROR); 
        }
        java.util.Map<String,String> map = AltaFichaTecnicaUtils.getClientInfo(strPartyId,oAApplicationModuleImpl);
        OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
        OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacion");
        OAMessageTextInputBean PuntoDeRecoleccionBean = (OAMessageTextInputBean)webBean.findChildRecursive("PuntoDeRecoleccion");
        OAMessageTextInputBean ContactoParaCierreBean = (OAMessageTextInputBean)webBean.findChildRecursive("ContactoParaCierre");
        OAMessageCheckBoxBean LunesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Lunes");
        OAMessageCheckBoxBean MartesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Martes");
        OAMessageCheckBoxBean MiercolesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Miercoles");
        OAMessageCheckBoxBean JuevesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Jueves");
        OAMessageCheckBoxBean ViernesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Viernes");
        OAMessageCheckBoxBean SabadoBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Sabado");
        OAMessageCheckBoxBean DomingoBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Domingo");
        
        if(null!=EmpresaQueFacturaBean){
          if(null!=map.get("EmpresaQueFactura")){
            if("".equals(fillEmpresa)||null==fillEmpresa){
                EmpresaQueFacturaBean.setValue(pageContext,map.get("EmpresaQueFactura"));
            }
          }
        }
        
        if(null!=CicloFacturacionBean){
         if(null!=map.get("FrecuenciaFacturacion")){
            if("".equals(fillFrecFact)||null==fillFrecFact){
                CicloFacturacionBean.setValue(pageContext,map.get("FrecuenciaFacturacion"));
            }
          }
        }
        
        /** 31072020
        if(null!=PuntoDeRecoleccionBean){
         if(null!=map.get("PuntoRecoleccion")){
             PuntoDeRecoleccionBean.setValue(pageContext,map.get("PuntoRecoleccion"));
         }
        }
        **/
        
        if(null!=ContactoParaCierreBean){
            if(null!=map.get("ContactoCierre"))
            ContactoParaCierreBean.setValue(pageContext,map.get("ContactoCierre"));
        }
        
        /**
        
        if(null!=LunesBean){
         if(null!=map.get("Lunes"))
             LunesBean.setValue(pageContext,map.get("Lunes"));
        }
        
        if(null!=MartesBean){
         if(null!=map.get("Martes"))
             MartesBean.setValue(pageContext,map.get("Martes"));
        }
        
        if(null!=MiercolesBean){
         if(null!=map.get("Miercoles"))
             MiercolesBean.setValue(pageContext,map.get("Miercoles"));
        }
        
        if(null!=JuevesBean){
         if(null!=map.get("Jueves"))
             JuevesBean.setValue(pageContext,map.get("Jueves"));
        }
        
        if(null!=ViernesBean){
         if(null!=map.get("Viernes"))
             ViernesBean.setValue(pageContext,map.get("Viernes"));
        }
        
        if(null!=SabadoBean){
         if(null!=map.get("Sabado"))
             SabadoBean.setValue(pageContext,map.get("Sabado"));
        }
        
        if(null!=DomingoBean){
         if(null!=map.get("Domingo"))
             DomingoBean.setValue(pageContext,map.get("Domingo"));
        }
        **/
        
        
    }

    private void environmentNoRequiredFields(OAPageContext pageContext, 
                                             OAWebBean webBean) {
       /** setNoRequiredInput("FechaInicioOperacion",webBean); NA 25072018 **/
        setNoRequiredInput("NombreProducto",webBean);
        setNoRequiredInput("VolumenAprox",webBean);
        setNoRequiredInput("TipoProducto",webBean);
        setNoRequiredInput("PesoProducto",webBean);
        /****/
        setNoRequiredInput("PuntoDeRecoleccion",webBean);
        setNoRequiredInput("ContactoParaCierre",webBean);
        setNoRequiredInput("Periodicidad",webBean);
        setNoRequiredInput("Dimensiones",webBean);
        setNoRequiredInput("TipoComision",webBean);
        /****/
        setNoRequiredInput("SeFactura",webBean);
        setNoRequiredInput("FormatoParaCierre",webBean);
        setNoRequiredInput("DiasHabilesPago",webBean);
        setNoRequiredInput("RequiereVoBo",webBean);
        /***/
        setNoRequiredInput("Acuse",webBean);
        setNoRequiredInput("Ordinario",webBean);
        setNoRequiredInput("SemiAcuse",webBean);
        /***/
         setNoRequiredInput("PlazaPropietaria",webBean);
        setNoRequiredInput("EntregaLocal",webBean);
        setNoRequiredInput("DRLocal",webBean);
        setNoRequiredInput("DILocal",webBean);
        /***/
         setNoRequiredInput("EntregaForaneo",webBean);
         setNoRequiredInput("DRForaneo",webBean);
         setNoRequiredInput("DIForaneo",webBean);
         /***/
         setNoRequiredInput("DiasOperacionLocal",webBean);
         setNoRequiredInput("DiasOperacionForaneo",webBean);
         setNoRequiredInput("CierreElectronico",webBean);
         setNoRequiredInput("EnvioPiezasFisicas",webBean);
        
        
    }
    
    private void setNoRequiredInput(String strID,OAWebBean webBean){
        OAWebBeanMessage tmpBean = (OAWebBeanMessage)webBean.findChildRecursive(strID);
        if(null!=tmpBean){
            tmpBean.setRequired("no");
        }
    }   
    
    
     
}

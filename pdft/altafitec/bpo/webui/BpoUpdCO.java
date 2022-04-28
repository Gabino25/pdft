/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.sql.SQLException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.common.AppsContext;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OABodyBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.OAWebBeanMessage;
import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.layout.OAPageLayoutBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;
import oracle.apps.fnd.framework.webui.beans.table.OATableBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.BpoAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.PrecioTmpVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPagoVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPagoVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoPrecioVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioAdquVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioAdquVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioGyCVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioGyCVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioOperVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoRequeAdicioOperVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoServicioVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.XxqpPdftBpoServicioVORowImpl;

/**
 * Controller for ...
 */
public class BpoUpdCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
  private int gIntSelectedIndex  = 0; 
    private  String gStrPdftBpoPrecioName = null;
  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      String strPuserPdft = null; 
      System.out.println("BpoUpdCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("BpoUpdCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("BpoUpdCO strPuserPdftId:"+strPuserPdftId);
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
    
      OAWebBean body = pageContext.getRootWebBean(); 
      if (body instanceof OABodyBean){
           ((OABodyBean)body).setBlockOnEverySubmit(true); 
       }
      
      
      
      environmentCurrencyFormat(pageContext,webBean);
      
      OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN"); 
      OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoFileUpload");
      OAMessageFileUploadBean Examine1Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload1");
      OAMessageFileUploadBean Examine2Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload2");
      OAMessageFileUploadBean Examine3Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload3");
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OASubTabLayoutBean subTabLayoutRNBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
      OATableBean PdftBpoPrecioBean = (OATableBean)webBean.findChildRecursive("PdftBpoPrecio");
      OAMessageTextInputBean ModificacionesRealizadasBean = (OAMessageTextInputBean)webBean.findChildRecursive("ModificacionesRealizadas");
      OAButtonBean  ProcesarBean = (OAButtonBean)webBean.findChildRecursive("Procesar");
      if(null!=ProcesarBean){
      ProcesarBean.setOnClick("this.disabled=true;this.value='Trabajando...';");
      }
      
      if(null!=PdftBpoPrecioBean){
       if(null!=PdftBpoPrecioBean.getName(pageContext)){
       gStrPdftBpoPrecioName = PdftBpoPrecioBean.getName(pageContext);
       }
      }
      
      if(null!=NombreUsuarioEBSBean){
         /**  NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName()); 110320211529 **/
          NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
       }
       
        if(null!=ContratoExamineBean){
            OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "ContratoFileName"); 
            ContratoExamineBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
        }
        
      if(null!=Examine1Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "FileName1"); 
          Examine1Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      
      if(null!=Examine2Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "FileName2"); 
          Examine2Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      
      if(null!=Examine3Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "FileName3"); 
          Examine3Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      
      if(null!=subTabLayoutRNBean){
        gIntSelectedIndex =  subTabLayoutRNBean.getSelectedIndex(pageContext);
      }
        
      BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
      
      System.out.println("bpoAMImpl"); 
      System.out.println(bpoAMImpl); 
      
      XxqpPdftBpoHeaderVORowImpl xxqpPdftBpoHeaderVORowImpl = null; 
      
      if(!pageContext.isFormSubmission()){
        if(null!=bpoAMImpl){
            String strBpoHeaderId = pageContext.getParameter("pBpoHeaderId");
            System.out.println("strBpoHeaderId:"+strBpoHeaderId);
            xxqpPdftBpoHeaderVORowImpl = bpoAMImpl.initBpoHeaderVO(strBpoHeaderId);
            bpoAMImpl.initBpoPrecioVO(strBpoHeaderId);
            bpoAMImpl.initBpoServicioVO(strBpoHeaderId);
            bpoAMImpl.intiAllBpoRequeAdicioVOS(strBpoHeaderId); 
            bpoAMImpl.initBpoPagoVO(strBpoHeaderId); 
            bpoAMImpl.initReglasDeNegocioVO(strBpoHeaderId);
            if(null!=ModificacionesRealizadasBean){
                if("CAMBIO_DE_PRECIO".equals(xxqpPdftBpoHeaderVORowImpl.getStatus())){
                    ModificacionesRealizadasBean.setRendered(true);
                }
            }
        }
      }else{
      
      }
      
      if(!pageContext.isFormSubmission()){
         String lstrPartyID = xxqpPdftBpoHeaderVORowImpl.getPartyId().toString();
         if(null!=lstrPartyID&&!"".equals(lstrPartyID)){
          bpoAMImpl.initEstadosLov(lstrPartyID,null);  
          bpoAMImpl.initConceptosLov(lstrPartyID,null);  
         }
      }
      
      environmentHorario(bpoAMImpl,pageContext,webBean);
      System.out.println(xxqpPdftBpoHeaderVORowImpl);
      if(null==xxqpPdftBpoHeaderVORowImpl){
       return;
      }
      String strUnidadDeNegocioC =  xxqpPdftBpoHeaderVORowImpl.getUnidadDeNegocioC(); 
      String strUnidadDeNegocioM = xxqpPdftBpoHeaderVORowImpl.getUnidadDeNegocioM();
      
      if(null==PageLayoutRNBean){
       PageLayoutRNBean = pageContext.getPageLayoutBean();
      }
      if(null!=PageLayoutRNBean){
        if(null!=strUnidadDeNegocioM){
          PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strUnidadDeNegocioM);
          PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strUnidadDeNegocioM);
        }
      }
      
      if(null!=strUnidadDeNegocioC){
          String strCamposRequeridos  = bpoAMImpl.getCamposRequeridosByUnidadDeNegocio(strUnidadDeNegocioC);
          if("N".equals(strCamposRequeridos)){
              environmentNoRequiredFields(pageContext,webBean);
          }
        /**********************************************************************
        if("EMERGENCIAS_PROGRAMADAS".equals(strPUnidadDeNegocio)){
           environmentNoRequiredFields(pageContext,webBean);
         }
         *******************************************************************/
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
    DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoFileUpload"); 
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM); 
    String strOaSelectedSubtab =  pageContext.getParameter(OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX);
    String rowReference =  pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
    String strSourceParam = pageContext.getParameter(this.SOURCE_PARAM);
    String strUnidadDeNegocioValue = null; 
    String strValida = null; 
      OAMessageChoiceBean UnidadDeNegocioBean = (OAMessageChoiceBean)webBean.findChildRecursive("UnidadDeNegocio");
      if(null!=UnidadDeNegocioBean){
        if(null!=UnidadDeNegocioBean.getValue(pageContext)){
          strUnidadDeNegocioValue = UnidadDeNegocioBean.getValue(pageContext).toString();
        }
      }
     
      if(null!=strUnidadDeNegocioValue){
      if("EMERGENCIAS_PROGRAMADAS".equals(strUnidadDeNegocioValue)
        ){
       strValida = "N";
       }
      }  
    
    System.out.println("strEventParam:"+strEventParam);  
    

    String strContratoFileUploadContentType = null; 
    if(null!=ContratoExamineUploadData){
    strContratoFileUploadContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
    }
      
    BpoAMImpl  bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
      
      fillHorarios(bpoAMImpl,pageContext,webBean);
      
      bpoAMImpl.fillSubTotal(); 
    
      XxqpPdftBpoHeaderVOImpl  xxqpPdftBpoHeaderVOImpl = null; 
      XxqpPdftBpoHeaderVORowImpl  xxqpPdftBpoHeaderVORowImpl = null; 
      XxqpPdftBpoServicioVORowImpl  xxqpPdftBpoServicioVORowImpl = null; 
      
      XxqpPdftBpoRequeAdicioGyCVOImpl XxqpPdftBpoRequeAdicioGyCVOImpl = null; 
      XxqpPdftBpoRequeAdicioGyCVORowImpl   xxqpPdftBpoRequeAdicioGyCVORowImpl = null; 
      
      XxqpPdftBpoRequeAdicioOperVOImpl  xxqpPdftBpoRequeAdicioOperVOImpl = null; 
      XxqpPdftBpoRequeAdicioOperVORowImpl  xxqpPdftBpoRequeAdicioOperVORowImpl = null;
      
      XxqpPdftBpoRequeAdicioAdquVOImpl xxqpPdftBpoRequeAdicioAdquVOImpl = null; 
      XxqpPdftBpoRequeAdicioAdquVORowImpl  xxqpPdftBpoRequeAdicioAdquVORowImpl = null; 
      
      xxqpPdftBpoServicioVORowImpl = (XxqpPdftBpoServicioVORowImpl)bpoAMImpl.getXxqpPdftBpoServicioVO1().getCurrentRow();
      
      xxqpPdftBpoHeaderVOImpl = bpoAMImpl.getXxqpPdftBpoHeaderVO1();
      RowSetIterator BpoHeaderIterator = xxqpPdftBpoHeaderVOImpl.createRowSetIterator(null);
      if(BpoHeaderIterator.hasNext()){
        xxqpPdftBpoHeaderVORowImpl = (XxqpPdftBpoHeaderVORowImpl)BpoHeaderIterator.next();
      }
      BpoHeaderIterator.closeRowSetIterator();
      
      /************************** Reglas de Negocio *************
       **********************************************************
       **********************************************************/
      if("AgregarRegNegEvt".equals(strEventParam)){
          bpoAMImpl.createRowRegNegUpd(xxqpPdftBpoHeaderVORowImpl.getId());
          return;
      }
      if("DeleteRegNegEvt".equals(strEventParam)){
         String strDeleteRegNegId = pageContext.getParameter("pDeleteRegNegId");
         System.out.println("strDeleteRegNegId:"+strDeleteRegNegId);
         bpoAMImpl.deleteRowRegNegUpd(strDeleteRegNegId);
         return; 
      }
      
      if("BorrarTodoEvt".equals(strEventParam)){
         bpoAMImpl.deleteAllRowRegNegUpd();
         return; 
      }
      
      if("ReglasDeNegocioLinesEvt".equals(strEventParam)){
          OAMessageTextInputBean ReglasDeNegocioLinesBean = (OAMessageTextInputBean)webBean.findChildRecursive("ReglasDeNegocioLines"); 
          String strReglasDeNegocioLines = null; 
          System.out.println("ReglasDeNegocioLinesBean:"+ReglasDeNegocioLinesBean);
          if(null!=ReglasDeNegocioLinesBean){
            strReglasDeNegocioLines = (String)ReglasDeNegocioLinesBean.getValue(pageContext);
          }
          System.out.println("strReglasDeNegocioLines:"+strReglasDeNegocioLines);
          if(null!=strReglasDeNegocioLines&&!"".equals(strReglasDeNegocioLines)){
            String arrayReglasDeNegocioLines[] = strReglasDeNegocioLines.split("\\r?\\n");
            com.sun.java.util.collections.List exceptions = new com.sun.java.util.collections.ArrayList();
              
            for(int i=0;i<arrayReglasDeNegocioLines.length;i++){
              if(null!=arrayReglasDeNegocioLines[i]&&!"".equals(arrayReglasDeNegocioLines[i])){
                  String strAttributes[] = arrayReglasDeNegocioLines[i].split(",");
                  String strIndicador = "Linea ("+(i+1)+"):";
                  String strMsg = "";
                  if(strAttributes.length!=3){
                      strMsg =strMsg+"No existen 3 atributos";
                  }else{
                      if(null==strAttributes[0]||"".equals(strAttributes[0])){
                          strMsg =strMsg+"El primer atributo esta vacio";
                      }
                      if(null==strAttributes[1]||"".equals(strAttributes[1])){
                          strMsg =strMsg+"El segundo atributo esta vacio";
                      }
                      if(null==strAttributes[2]||"".equals(strAttributes[2])){
                          strMsg =strMsg+"El tercer atributo esta vacio";
                      }
                      oracle.jbo.domain.Number nPrecio=null;
                      try {
                          nPrecio = new oracle.jbo.domain.Number(strAttributes[2]);
                      } catch (SQLException e) {
                           strMsg =strMsg+"El tercer atributo no es un numero";
                      }
                  }/** if(strAttributes.length!=3){ **/
                   if(null!=strMsg&&!"".equals(strMsg)){
                     exceptions.add(new OAException(strIndicador+strMsg,OAException.ERROR));
                   }  
                }
            }
            
              if(exceptions.size()>0){
              OAException.raiseBundledOAException(exceptions);
              }else{
                  for(int i=0;i<arrayReglasDeNegocioLines.length;i++){
                    if(null!=arrayReglasDeNegocioLines[i]&&!"".equals(arrayReglasDeNegocioLines[i])){
                        String strAttributes[] = arrayReglasDeNegocioLines[i].split(",");
                        bpoAMImpl.createRowRegNegUpd(strAttributes
                                                    ,xxqpPdftBpoHeaderVORowImpl.getId()
                                                    );
                    }
                  }
              }
            
          }
          return;
      }
      
      if("lovPrepare".equals(strEventParam)){
            String lovInputSourceId = pageContext.getLovInputSourceId();
            String lstrPartyID = null; 
            lstrPartyID = xxqpPdftBpoHeaderVORowImpl.getPartyId().toString();
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
      
      XxqpPdftBpoRequeAdicioGyCVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioGyCVO1();
      RowSetIterator GenteYCulturaIterator =  XxqpPdftBpoRequeAdicioGyCVOImpl.createRowSetIterator(null);
      if(GenteYCulturaIterator.hasNext()){
        xxqpPdftBpoRequeAdicioGyCVORowImpl = (XxqpPdftBpoRequeAdicioGyCVORowImpl)GenteYCulturaIterator.next();
      }
      GenteYCulturaIterator.closeRowSetIterator();
      
      if(null==xxqpPdftBpoRequeAdicioGyCVORowImpl){
        throw new OAException("No se pudo recuperar informacion de Requerimientos Adicionales.",OAException.ERROR); 
      }
      
      xxqpPdftBpoRequeAdicioOperVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioOperVO1(); 
      RowSetIterator OperacionesIterator = xxqpPdftBpoRequeAdicioOperVOImpl.createRowSetIterator(null);
      if(OperacionesIterator.hasNext()){
         xxqpPdftBpoRequeAdicioOperVORowImpl = (XxqpPdftBpoRequeAdicioOperVORowImpl)OperacionesIterator.next();
      }
      OperacionesIterator.closeRowSetIterator();
      
      xxqpPdftBpoRequeAdicioAdquVOImpl = bpoAMImpl.getXxqpPdftBpoRequeAdicioAdquVO1(); 
      RowSetIterator AdquisicionesIterator =  xxqpPdftBpoRequeAdicioAdquVOImpl.createRowSetIterator(null);
      if(AdquisicionesIterator.hasNext()){
         xxqpPdftBpoRequeAdicioAdquVORowImpl = (XxqpPdftBpoRequeAdicioAdquVORowImpl)AdquisicionesIterator.next();
      }
      AdquisicionesIterator.closeRowSetIterator();
      
      XxqpPdftBpoPagoVOImpl xxqpPdftBpoPagoVOImpl = bpoAMImpl.getXxqpPdftBpoPagoVO1();
      XxqpPdftBpoPagoVORowImpl xxqpPdftBpoPagoVORowImpl = null; 
      RowSetIterator BpoPagoIterator = xxqpPdftBpoPagoVOImpl.createRowSetIterator(null);
      if(BpoPagoIterator.hasNext()){
         xxqpPdftBpoPagoVORowImpl = (XxqpPdftBpoPagoVORowImpl)BpoPagoIterator.next();
      }
      BpoPagoIterator.closeRowSetIterator();
      //xxqpPdftBpoRequeAdicioGyCVORowImpl = (XxqpPdftBpoRequeAdicioGyCVORowImpl)bpoAMImpl.getXxqpPdftBpoRequeAdicioGyCVO1().getCurrentRow();
      //xxqpPdftBpoRequeAdicioOperVORowImpl = (XxqpPdftBpoRequeAdicioOperVORowImpl)bpoAMImpl.getXxqpPdftBpoRequeAdicioOperVO1().getCurrentRow();
      //xxqpPdftBpoRequeAdicioAdquVORowImpl = (XxqpPdftBpoRequeAdicioAdquVORowImpl)bpoAMImpl.getXxqpPdftBpoRequeAdicioAdquVO1().getCurrentRow();
      
      System.out.println("xxqpPdftBpoRequeAdicioGyCVORowImpl:"+xxqpPdftBpoRequeAdicioGyCVORowImpl);
      System.out.println("xxqpPdftBpoRequeAdicioOperVORowImpl:"+xxqpPdftBpoRequeAdicioOperVORowImpl);
      System.out.println("xxqpPdftBpoRequeAdicioAdquVORowImpl:"+xxqpPdftBpoRequeAdicioAdquVORowImpl);
      
      if("1".equals(strOaSelectedSubtab)){
      if("N".equals(xxqpPdftBpoServicioVORowImpl.getVentas())
        &&("N".equals(xxqpPdftBpoServicioVORowImpl.getCobranza()))
        &&("N".equals(xxqpPdftBpoServicioVORowImpl.getGestorAdministrativo() ))
        &&("N".equals(xxqpPdftBpoServicioVORowImpl.getTecnico() ))
        &&("N".equals(xxqpPdftBpoServicioVORowImpl.getRecoleDocMater() ))
        &&("N".equals(xxqpPdftBpoServicioVORowImpl.getEntregaADomicilio() ))
        &&(null==xxqpPdftBpoServicioVORowImpl.getOtros()||"".equals(xxqpPdftBpoServicioVORowImpl.getOtros()))  
        &&("Y".equals(strValida))
        ){
            throw new OAException("Seleccionar al menos uno de los 7 valores del Area de Perfil.",OAException.ERROR); 
        }
      }
     
      com.sun.java.util.collections.List listExc = new com.sun.java.util.collections.ArrayList();
      if("N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getMoto125())
        &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCasco())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaGrande())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaChicaRoja())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCamisas())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getImpermeable())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getTarjetaGasolina())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getPantalonVestir())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getCajaNegra())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getHielera())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getMovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getAutomovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getGuiaRoji())
          &&"N".equals(xxqpPdftBpoRequeAdicioGyCVORowImpl.getOtros())
        ){
            OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Gente y Cultura",OAException.ERROR);
                listExc.add(oAException);
        }
      
      if("N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getMoto125())
        &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getCasco())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaGrande())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaChicaRoja())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getCamisas())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getImpermeable())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getTarjetaGasolina())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getPantalonVestir())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getCajaNegra())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getHielera())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getMovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getAutomovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getGuiaRoji())
          &&"N".equals(xxqpPdftBpoRequeAdicioOperVORowImpl.getOtros())
        ){
            OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Operaciones",OAException.ERROR);
                listExc.add(oAException);
        }
        
      if("N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getMoto125())
        &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCasco())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaGrande())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaChicaRoja())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCamisas())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getImpermeable())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getTarjetaGasolina())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getPantalonVestir())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getCajaNegra())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getHielera())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getMovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getAutomovil())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getGuiaRoji())
          &&"N".equals(xxqpPdftBpoRequeAdicioAdquVORowImpl.getOtros())
        ){
            OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Adquisiciones",OAException.ERROR);
                listExc.add(oAException);
        }
        
      if(listExc.size()>0&&"Y".equals(strValida)){
          System.out.println("strOaSelectedSubtab:"+strOaSelectedSubtab);
          if("2".equals(strOaSelectedSubtab)){
          OAException.raiseBundledOAException(listExc);
          }
      }
      
    if("GrabarEvt".equals(strEventParam)){
        validarArchivos(pageContext,xxqpPdftBpoHeaderVORowImpl);
        /** No se estaba guardando el cambio de montos 03042018 **/
        if(null!=xxqpPdftBpoPagoVORowImpl.getSubtotal()
           &&!"".equals(xxqpPdftBpoPagoVORowImpl.getSubtotal())
           &&null!=xxqpPdftBpoPagoVORowImpl.getIva()
           &&!"".equals(xxqpPdftBpoPagoVORowImpl.getIva())
           ){
          xxqpPdftBpoPagoVORowImpl.setTotal(xxqpPdftBpoPagoVORowImpl.getSubtotal().add(xxqpPdftBpoPagoVORowImpl.getIva()));
          }
        
        bpoAMImpl.getOADBTransaction().commit();
        
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        oracle.jbo.domain.Number numBpoHeaderId =  xxqpPdftBpoHeaderVORowImpl.getId();
        parameters.put("pBpoHeaderId",numBpoHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,parameters /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  );     
            return;                       
    }/** END if("GrabarEvt".equals(strEventParam)){ **/
    
      if("RevisarPDFEvt".equals(strEventParam)){
          DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
          HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
          String contentDisposition = "attachment;filename=AltaFichaTecnicaBpo.pdf";
          response.setHeader("Content-Disposition",contentDisposition);
          response.setContentType("application/pdf");
          ServletOutputStream os=null;
         
              String strXML = null;
              strXML = bpoAMImpl.executeBpoGetInfo("N");
              ByteArrayInputStream bAIsXmlFile = null;
              ByteArrayOutputStream bAoSPdfFile = null;
              try {
                  os = response.getOutputStream();
                  byte[] aByte = strXML.getBytes();
                  bAIsXmlFile = new ByteArrayInputStream(aByte);
                  bAoSPdfFile = new ByteArrayOutputStream();
                  AppsContext appsContext = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getAppsContext();
                  Locale locale = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getUserLocale();
                  TemplateHelper.processTemplate(appsContext, 
                                                 AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                 "XXQP_PDFT_BPO", 
                                                 locale.getLanguage(), 
                                                 locale.getCountry(), 
                                                 bAIsXmlFile, 
                                                 TemplateHelper.OUTPUT_TYPE_PDF, 
                                                  null, 
                                                 bAoSPdfFile);

                                      byte[] b = bAoSPdfFile.toByteArray();
                                      response.setContentLength(b.length);
                                      os.write(b, 0, b.length);
                                      os.flush();
                                      os.close();
                 
              } catch (IOException e) {
                 throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
              } catch (SQLException e) {
                  throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
              } catch (XDOException e) {
                  throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
              } finally{
                 /** Cerrar en Cascada **/
                  if(null!=bAoSPdfFile){
                      try {
                          bAoSPdfFile.close();
                      } catch (IOException e) {
                         e.printStackTrace();
                      }
                  }
                  if(null!=bAIsXmlFile){
                      try {
                          bAIsXmlFile.close();
                      } catch (IOException e) {
                         e.printStackTrace();
                      }
                  }
              }


          
        } /** END if("RevisarPDFEvt".equals(strEventParam)){ **/
         
           if("ProcesarEvt".equals(strEventParam)){
               String strXML = null;
               oracle.jbo.domain.Number oracleNumeroFt = new oracle.jbo.domain.Number(0);
               oracle.jbo.domain.Number oracleNumeroFtRef = new oracle.jbo.domain.Number(0);
               String strNombreCliente = ""; 
               String strArticuloOracle ="";
               String strModificacion ="N";
               
               if(null!=xxqpPdftBpoHeaderVORowImpl.getNumeroFt()){
                   oracleNumeroFt = xxqpPdftBpoHeaderVORowImpl.getNumeroFt(); 
               }
               
               if(null!=xxqpPdftBpoHeaderVORowImpl.getNumeroFtReferencia()){
                   oracleNumeroFtRef = xxqpPdftBpoHeaderVORowImpl.getNumeroFtReferencia(); 
               }
               if(null!=xxqpPdftBpoHeaderVORowImpl.getNombreDelCliente()){
                   strNombreCliente = xxqpPdftBpoHeaderVORowImpl.getNombreDelCliente();
               }
               
               String strEstatusFTOld = xxqpPdftBpoHeaderVORowImpl.getStatus();
                xxqpPdftBpoHeaderVORowImpl.setStatus("ABIERTA");  /** Se solicito Cambiar el status antes de enviar el correo 25072018 **/
                String strPrefijo = bpoAMImpl.getPrefijoByUnidadDeNegocio(strUnidadDeNegocioValue);
                if(!"CAMBIO_DE_PRECIO".equals(strEstatusFTOld)){  /** 21042020 Que Permanezca el articulo Oracle**/
                    strArticuloOracle = strPrefijo+xxqpPdftBpoHeaderVORowImpl.getNumeroFt();
                    xxqpPdftBpoHeaderVORowImpl.setArticuloOracle(strPrefijo+xxqpPdftBpoHeaderVORowImpl.getNumeroFt()); 
                }else{
                 strModificacion = "Y";
                 strArticuloOracle = xxqpPdftBpoHeaderVORowImpl.getArticuloOracle();
                }
               validarArchivos(pageContext,xxqpPdftBpoHeaderVORowImpl);
                bpoAMImpl.getOADBTransaction().commit();          /** Se solicito Cambiar el status antes de enviar el correo 25072018 **/
               strXML = bpoAMImpl.executeBpoGetInfo(strModificacion);
               ByteArrayInputStream bAIsXmlFile = null;
               ByteArrayOutputStream bAoSPdfFile = null;
               InputStream iSPdfFile = null;
               try {
                   byte[] aByte = strXML.getBytes();
                   bAIsXmlFile = new ByteArrayInputStream(aByte);
                   bAoSPdfFile = new ByteArrayOutputStream();
                   AppsContext appsContext = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getAppsContext();
                   Locale locale = ((OADBTransactionImpl)bpoAMImpl.getOADBTransaction()).getUserLocale();
                   TemplateHelper.processTemplate(appsContext, 
                                                  AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                                  "XXQP_PDFT_BPO", 
                                                  locale.getLanguage(), 
                                                  locale.getCountry(), 
                                                  bAIsXmlFile, 
                                                  TemplateHelper.OUTPUT_TYPE_PDF, 
                                                   null, 
                                                  bAoSPdfFile);
                 
                   byte[] a2Byte =bAoSPdfFile.toByteArray(); 
                   iSPdfFile = new ByteArrayInputStream(a2Byte);
                  
                   System.out.println("oracleNumeroFtRef:"+oracleNumeroFtRef);
                   String strCorreos = bpoAMImpl.enviaCorreos(iSPdfFile
                                                             ,oracleNumeroFtRef
                                                             ,strEstatusFTOld
                                                             ,oracleNumeroFt
                                                             ,pageContext
                                                             ,strNombreCliente
                                                             ,strArticuloOracle
                                                             ,xxqpPdftBpoHeaderVORowImpl
                                                             ); 
                   System.out.println("strCorreos:"+strCorreos);
                   
                   xxqpPdftBpoHeaderVORowImpl.setStatus("ABIERTA");
                   bpoAMImpl.getOADBTransaction().commit();
                   
                   com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
                   oracle.jbo.domain.Number numBpoHeaderId =  xxqpPdftBpoHeaderVORowImpl.getId();
                   parameters.put("pBpoHeaderId",numBpoHeaderId.toString() );
                   pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                             ,null /*functionName*/
                                             ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                             ,null /*menuName*/
                                             ,parameters /*parameters*/
                                             ,false /*retainAM*/
                                             ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                             ,OAException.ERROR /*messagingLevel*/
                                             );     
                                             
                   return;                           
                  
               } catch (IOException e) {
                  throw new OAException("IOException al obtener el ServletOutputStream.",OAException.ERROR); 
               } catch (SQLException e) {
                   throw new OAException("SQLException al obtener el DataTemplate.",OAException.ERROR);
               } catch (XDOException e) {
                   throw new OAException("XDOException al obtener el DataTemplate.",OAException.ERROR);
               }finally{
                   /** Cerrar en Cascada **/
                    if(null!=iSPdfFile){
                        try {
                            iSPdfFile.close();
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                    }
                    if(null!=bAoSPdfFile){
                        try {
                            bAoSPdfFile.close();
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                    }
                    if(null!=bAIsXmlFile){
                        try {
                            bAIsXmlFile.close();
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                    }
               }
               
           } /** END  if("ProcesarEvt".equals(strEventParam)){ **/
         
           if("AgregarEvt".equals(strEventParam)){
               bpoAMImpl.insertXxqpPdftBpoPrecioVO();
               return;
           } /** END if("AgregarEvt".equals(strEventParam)){ **/
           
            System.out.println("rowReference:"+rowReference); 
          if("DeleteEvt".equals(strEventParam)){
            XxqpPdftBpoPrecioVOImpl xxqpPdftBpoPrecioVOImpl =  bpoAMImpl.getXxqpPdftBpoPrecioVO1();
            int rowCount =  xxqpPdftBpoPrecioVOImpl.getRowCount();
            int fetchedRowCount =  xxqpPdftBpoPrecioVOImpl.getFetchedRowCount();
            System.out.println("rowCount:"+rowCount);
            System.out.println("fetchedRowCount:"+fetchedRowCount);
            if(1==fetchedRowCount){
             throw new OAException("Al menos un registro es requerido.",OAException.ERROR); 
            }else{
             OARow currRow = (OARow)bpoAMImpl.findRowByRef(rowReference);
             currRow.remove();
             bpoAMImpl.getOADBTransaction().commit();
             return; 
            }
          } /** END if("DeleteEvt".equals(strEventParam)){ **/
          
           if(null!=gStrPdftBpoPrecioName){
            if("update".equals(strEventParam)&&gStrPdftBpoPrecioName.equals(strSourceParam)){
                bpoAMImpl.refreshXxqpPdftBpoPrecioVO();
            }else if(0==gIntSelectedIndex){
                bpoAMImpl.refreshXxqpPdftBpoPrecioVO();
            }
           } /** END if(null!=gStrPrecioTmpName){ **/
            
           if("RecalcularEvt".equals(strEventParam)){
               if("3".equals(strOaSelectedSubtab)){
                 if(null!=xxqpPdftBpoPagoVORowImpl.getSubtotal()
                    &&!"".equals(xxqpPdftBpoPagoVORowImpl.getSubtotal())
                    &&null!=xxqpPdftBpoPagoVORowImpl.getIva()
                    &&!"".equals(xxqpPdftBpoPagoVORowImpl.getIva())
                    ){
                   xxqpPdftBpoPagoVORowImpl.setTotal(xxqpPdftBpoPagoVORowImpl.getSubtotal().add(xxqpPdftBpoPagoVORowImpl.getIva()));
                   }
             } /** END if("3".equals(strOaSelectedSubtab)){ **/
           } /** END if("RecalcularEvt".equals(strEventParam)){ **/
           
      
  }

    private void environmentCurrencyFormat(OAPageContext pageContext, 
                                           OAWebBean webBean) {
        OATableBean PdftBpoPrecioBean = (OATableBean)webBean.findChildRecursive("PdftBpoPrecio");
        if(null!=PdftBpoPrecioBean){
          OAMessageTextInputBean PrecioUnitarioBean = (OAMessageTextInputBean)PdftBpoPrecioBean.findChildRecursive("PrecioUnitario");
          OAMessageTextInputBean TotalPorConceptoBean = (OAMessageTextInputBean)PdftBpoPrecioBean.findChildRecursive("TotalPorConcepto");  
          if(null!=PrecioUnitarioBean){
              PrecioUnitarioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          if(null!=TotalPorConceptoBean){
              TotalPorConceptoBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          
        }
        
        OAMessageTextInputBean SubtotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Subtotal");
        OAMessageTextInputBean IVABean = (OAMessageTextInputBean)webBean.findChildRecursive("IVA");
        OAMessageTextInputBean TotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Total");
        if(null!=SubtotalBean){
            SubtotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        if(null!=IVABean){
            IVABean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        if(null!=TotalBean){
            TotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        
        OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioBpoVORN");
        if(null!=ReglasDeNegocioVORNBean){
          OAMessageTextInputBean PrecioRegNegBean = (OAMessageTextInputBean)ReglasDeNegocioVORNBean.findChildRecursive("PrecioRegNeg");
          if(null!=PrecioRegNegBean){
              PrecioRegNegBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
        }
        
    }

    private void environmentHorario(BpoAMImpl bpoAMImpl, 
                                    OAPageContext pageContext, 
                                    OAWebBean webBean) {
    
    OAMessageChoiceBean HoraInicialBean = (OAMessageChoiceBean)webBean.findChildRecursive("HoraInicial"); 
    OAMessageChoiceBean MinutoInicialBean = (OAMessageChoiceBean)webBean.findChildRecursive("MinutoInicial"); 
    OAMessageChoiceBean HoraFinalBean = (OAMessageChoiceBean)webBean.findChildRecursive("HoraFinal"); 
    OAMessageChoiceBean MinutoFinalBean = (OAMessageChoiceBean)webBean.findChildRecursive("MinutoFinal"); 
    
    XxqpPdftBpoServicioVOImpl  xxqpPdftBpoServicioVOImpl = bpoAMImpl.getXxqpPdftBpoServicioVO1(); 
    XxqpPdftBpoServicioVORowImpl  xxqpPdftBpoServicioVORowImpl = null;
    RowSetIterator ServicioIterator = xxqpPdftBpoServicioVOImpl.createRowSetIterator(null); 
    while(ServicioIterator.hasNext()){
        xxqpPdftBpoServicioVORowImpl = (XxqpPdftBpoServicioVORowImpl)ServicioIterator.next();
        break;
    }
    ServicioIterator.closeRowSetIterator();
    
    if(null!=xxqpPdftBpoServicioVORowImpl){
     System.out.println("HoraInicio:"+xxqpPdftBpoServicioVORowImpl.getHoraInicio());
     System.out.println("HoraFinal:"+xxqpPdftBpoServicioVORowImpl.getHoraFinal());
     if(null!=xxqpPdftBpoServicioVORowImpl.getHoraInicio()&&null!=xxqpPdftBpoServicioVORowImpl.getHoraFinal()){
     String strHorarioInicial = xxqpPdftBpoServicioVORowImpl.getHoraInicio(); 
     String strHorarioFinal = xxqpPdftBpoServicioVORowImpl.getHoraFinal();
     String arrayHorarioInicial[] = strHorarioInicial.split(":");
     String arrayHorarioFinal[] = strHorarioFinal.split(":");
    
     if(null!=HoraInicialBean){
        HoraInicialBean.setText(pageContext,arrayHorarioInicial[0]);
     }
     if(null!=MinutoInicialBean){
        MinutoInicialBean.setText(pageContext,arrayHorarioInicial[1]);
     }
    
     if(null!=HoraFinalBean){
        HoraFinalBean.setText(pageContext,arrayHorarioFinal[0]);
     }
     if(null!=MinutoFinalBean){
        MinutoFinalBean.setText(pageContext,arrayHorarioFinal[1]);
     }
     
     }  /** END if(null!=xxqpPdftBpoServicioVORowImpl.getHoraInicio()&&null!=xxqpPdftBpoServicioVORowImpl.getHoraFinal()){ **/ 
    } /** END if(null!=xxqpPdftBpoServicioVORowImpl){ **/
    
    } /** END  private void environmentHorario **/
    
    private void fillHorarios(BpoAMImpl bpoAMImpl, 
                              OAPageContext pageContext, 
                              OAWebBean webBean) {
        
        String strHoraInicial = null; 
        String strMinutoInicial = null; 
        String strHoraFinal = null; 
        String strMinutoFinal = null; 
        String strHorarioInicial = null; 
        String strHorarioFinal = null; 
        OAMessageChoiceBean HoraInicialBean = (OAMessageChoiceBean)webBean.findChildRecursive("HoraInicial"); 
        OAMessageChoiceBean MinutoInicialBean = (OAMessageChoiceBean)webBean.findChildRecursive("MinutoInicial"); 
        OAMessageChoiceBean HoraFinalBean = (OAMessageChoiceBean)webBean.findChildRecursive("HoraFinal"); 
        OAMessageChoiceBean MinutoFinalBean = (OAMessageChoiceBean)webBean.findChildRecursive("MinutoFinal"); 
        
        if(null!=HoraInicialBean){
         if(null!=HoraInicialBean.getText(pageContext)){
             strHoraInicial = HoraInicialBean.getText(pageContext); 
         }
        }
        
        if(null!=MinutoInicialBean){
        if(null!=MinutoInicialBean.getText(pageContext)){
            strMinutoInicial = MinutoInicialBean.getText(pageContext); 
        }
        }
        
        if(null!=HoraFinalBean){
        if(null!=HoraFinalBean.getText(pageContext)){
            strHoraFinal = HoraFinalBean.getText(pageContext); 
        }
        }
        
        if(null!=MinutoFinalBean){
        if(null!=MinutoFinalBean.getText(pageContext)){
           strMinutoFinal = MinutoFinalBean.getText(pageContext); 
        }
        }
        
        if(null!=strHoraInicial&&null!=strMinutoInicial){
           strHorarioInicial = strHoraInicial+":"+strMinutoInicial+":00";
        }
        if(null!=strHoraFinal&&null!=strMinutoFinal){
           strHorarioFinal = strHoraFinal+":"+strMinutoFinal+":00";
        }
        
        XxqpPdftBpoServicioVOImpl  xxqpPdftBpoServicioVOImpl = bpoAMImpl.getXxqpPdftBpoServicioVO1(); 
        XxqpPdftBpoServicioVORowImpl  xxqpPdftBpoServicioVORowImpl = null;
        RowSetIterator ServicioIterator = xxqpPdftBpoServicioVOImpl.createRowSetIterator(null); 
        while(ServicioIterator.hasNext()){
            xxqpPdftBpoServicioVORowImpl = (XxqpPdftBpoServicioVORowImpl)ServicioIterator.next();
            break;
        }
        ServicioIterator.closeRowSetIterator();
        
        if(null!=xxqpPdftBpoServicioVORowImpl){
            xxqpPdftBpoServicioVORowImpl.setHoraInicio(strHorarioInicial);
            xxqpPdftBpoServicioVORowImpl.setHoraFinal(strHorarioFinal);
        }
        
    }
    
    private void environmentNoRequiredFields(OAPageContext pageContext, 
                                             OAWebBean webBean) {
       /** setNoRequiredInput("FechaInicioOperacion",webBean); NA 25072018 **/
        
        OATableBean  PdftBpoPrecioBean = (OATableBean)webBean.findChildRecursive("PdftBpoPrecio");
        setNoRequiredInput("Concepto",PdftBpoPrecioBean);
        setNoRequiredInput("Cantidad",PdftBpoPrecioBean);
        setNoRequiredInput("PrecioUnitario",PdftBpoPrecioBean);
       /****/
        setNoRequiredInput("NoHrsDiarias",webBean);
        setNoRequiredInput("FechaInicioDelServicio",webBean);
        setNoRequiredInput("ElMensajeroManeje",webBean);
        setNoRequiredInput("HoraInicial",webBean);
        setNoRequiredInput("MinutoInicial",webBean);
        setNoRequiredInput("HoraFinal",webBean);
        setNoRequiredInput("MinutoFinal",webBean);
        setNoRequiredInput("DireccionBase",webBean);
        /****/
        setNoRequiredInput("EjecutivoDeVentas",webBean);
        setNoRequiredInput("Plaza",webBean);
        setNoRequiredInput("FormaDePago",webBean);
        setNoRequiredInput("DiaDePago",webBean);
         
        
    }
    
    private void setNoRequiredInput(String strID,OAWebBean webBean){
        OAWebBeanMessage tmpBean = (OAWebBeanMessage)webBean.findChildRecursive(strID);
        if(null!=tmpBean){
            tmpBean.setRequired("no");
        }
    }

    /**
     * 13092021
     * getNamedDataObject solo aplica para archivos recien cargados, osea una actualizacion o modificacion de archivos
     * @param pageContext
     * @param pXxqpPdftBpoHeaderVORowImpl
     */
    private void validarArchivos(OAPageContext pageContext
                                ,XxqpPdftBpoHeaderVORowImpl pXxqpPdftBpoHeaderVORowImpl) {
        
            DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoFileUpload"); 
            String strContratoExamineContentType = null; 
            if(null!=ContratoExamineUploadData){
                strContratoExamineContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
                if(null!=pXxqpPdftBpoHeaderVORowImpl.getContratoFile()){
                  if(pXxqpPdftBpoHeaderVORowImpl.getContratoFile().getLength()>0){
                     System.out.println("Archivo Contrato Valido");
                      pXxqpPdftBpoHeaderVORowImpl.setContratoContentType(strContratoExamineContentType);
                  }else{
                      pXxqpPdftBpoHeaderVORowImpl.setContratoFileName(null);
                      pXxqpPdftBpoHeaderVORowImpl.setContratoContentType(null);
                  }
                }else{
                    pXxqpPdftBpoHeaderVORowImpl.setContratoFileName(null);
                    pXxqpPdftBpoHeaderVORowImpl.setContratoContentType(null);
                }
            }    
            
            DataObject FileUpload1Data =  pageContext.getNamedDataObject("FileUpload1"); 
            String strFileUpload1ContentType = null; 
            if(null!=FileUpload1Data){
                strFileUpload1ContentType = FileUpload1Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
                if(null!=pXxqpPdftBpoHeaderVORowImpl.getFile1()){
                  if(pXxqpPdftBpoHeaderVORowImpl.getFile1().getLength()>0){
                    System.out.println("Archivo File1 Valido");
                      pXxqpPdftBpoHeaderVORowImpl.setContentType1(strFileUpload1ContentType);
                  }else{
                      pXxqpPdftBpoHeaderVORowImpl.setFileName1(null);
                      pXxqpPdftBpoHeaderVORowImpl.setContentType1(null);
                  }
                }else{
                    pXxqpPdftBpoHeaderVORowImpl.setFileName1(null);
                    pXxqpPdftBpoHeaderVORowImpl.setContentType1(null);
                }
            }    
            
            DataObject FileUpload2Data =  pageContext.getNamedDataObject("FileUpload2"); 
            String strFileUpload2ContentType = null; 
            if(null!=FileUpload2Data){
                strFileUpload2ContentType = FileUpload2Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
                if(null!=pXxqpPdftBpoHeaderVORowImpl.getFile2()){
                  if(pXxqpPdftBpoHeaderVORowImpl.getFile2().getLength()>0){
                    System.out.println("Archivo File2 Valido");
                      pXxqpPdftBpoHeaderVORowImpl.setContentType2(strFileUpload2ContentType);
                  }else{
                      pXxqpPdftBpoHeaderVORowImpl.setFileName2(null);
                      pXxqpPdftBpoHeaderVORowImpl.setContentType2(null);
                  }
                }else{
                    pXxqpPdftBpoHeaderVORowImpl.setFileName2(null);
                    pXxqpPdftBpoHeaderVORowImpl.setContentType2(null);
                }
            }    
            
            DataObject FileUpload3Data =  pageContext.getNamedDataObject("FileUpload3"); 
            String strFileUpload3ContentType = null; 
            if(null!=FileUpload3Data){
                strFileUpload3ContentType = FileUpload3Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
                if(null!=pXxqpPdftBpoHeaderVORowImpl.getFile3()){
                  if(pXxqpPdftBpoHeaderVORowImpl.getFile3().getLength()>0){
                    System.out.println("Archivo File3 Valido");
                      pXxqpPdftBpoHeaderVORowImpl.setContentType3(strFileUpload3ContentType);
                  }else{
                      pXxqpPdftBpoHeaderVORowImpl.setFileName3(null);
                      pXxqpPdftBpoHeaderVORowImpl.setContentType3(null);
                  }
                }else{
                    pXxqpPdftBpoHeaderVORowImpl.setFileName3(null);
                    pXxqpPdftBpoHeaderVORowImpl.setContentType3(null);
                }
            }    
          
            
        }
    
}

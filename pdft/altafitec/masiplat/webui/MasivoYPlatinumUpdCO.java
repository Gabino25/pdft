/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.webui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import oracle.apps.fnd.common.AppsContext;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
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
import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.fnd.framework.webui.beans.table.OATableBean;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.delivery.DeliveryException;
import oracle.apps.xdo.delivery.DeliveryManager;
import oracle.apps.xdo.delivery.DeliveryPropertyDefinitions;
import oracle.apps.xdo.delivery.DeliveryRequest;
import oracle.apps.xdo.delivery.InvalidFactoryException;
import oracle.apps.xdo.delivery.UndefinedRequestTypeException;
import oracle.apps.xdo.oa.schema.server.TemplateHelper;
import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altafitec.AltaFichaTecnicaUtils;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.MasivoYPlatinumAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypOtrosProcesosVOImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server.XxqpPdftMypProcesosVOImpl;

/**
 * Controller for ...
 */
public class MasivoYPlatinumUpdCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  private int gIntSelectedIndex = -1; 
  
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      String strPuserPdft = null; 
      System.out.println("MasivoYPlatinumUpdCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("MasivoYPlatinumUpdCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("MasivoYPlatinumUpdCO strPuserPdftId:"+strPuserPdftId);
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
      
      OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN"); 
      OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoFileUpload");
      OAMessageFileUploadBean Examine1Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload1");
      OAMessageFileUploadBean Examine2Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload2");
      OAMessageFileUploadBean Examine3Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("FileUpload3");
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAMessageTextInputBean ModificacionesRealizadasBean = (OAMessageTextInputBean)webBean.findChildRecursive("ModificacionesRealizadas");
      OAButtonBean  ProcesarBean = (OAButtonBean)webBean.findChildRecursive("Procesar");
      if(null!=ProcesarBean){
      ProcesarBean.setOnClick("this.disabled=true;this.value='Trabajando...';");
      }
      
      environmentCurrencyFormat(pageContext,webBean);
      
      if(null!=ContratoExamineBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "ContratoFileName"); 
          ContratoExamineBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      
      if(null!=Examine1Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(Examine1Bean, "FileName1"); 
          Examine1Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      if(null!=Examine2Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(Examine2Bean, "FileName2"); 
          Examine2Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      if(null!=Examine3Bean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(Examine3Bean, "FileName3"); 
          Examine3Bean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      
      if(null!=NombreUsuarioEBSBean){
          /** NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName()); **/
          NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
      }
      
    
      MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
      String strMyPHeaderId =  pageContext.getParameter("pMyPHeaderId");
      System.out.println("strMyPHeaderId:"+strMyPHeaderId);
      XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = null;
      
      if(!pageContext.isFormSubmission()){
         xxqpPdftMypHeaderVORowImpl = masivoYPlatinumAMImpl.initMypHeaderVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypGeneralVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypCoberturaVO(strMyPHeaderId); 
          masivoYPlatinumAMImpl.initMypDistribucionVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypOtrosProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initMypComentsProcesosVO(strMyPHeaderId);
          masivoYPlatinumAMImpl.initReglasDeNegocioVO(strMyPHeaderId);
          if(null!=ModificacionesRealizadasBean){
              if("CAMBIO_DE_PRECIO".equals(xxqpPdftMypHeaderVORowImpl.getStatus())){
                  ModificacionesRealizadasBean.setRendered(true);
              }
          }
      }else{
      
      }    
      
      if(!pageContext.isFormSubmission()){
               String lstrPartyID = null; 
               lstrPartyID = xxqpPdftMypHeaderVORowImpl.getPartyId().toString();
               if(null!=lstrPartyID||!"".equals(lstrPartyID)){
               masivoYPlatinumAMImpl.initEstadosLov(lstrPartyID,null);  
               masivoYPlatinumAMImpl.initConceptosLov(lstrPartyID,null);
               }
      }
      
      if(null==xxqpPdftMypHeaderVORowImpl){
       throw new OAException("No se pudo recuperar el objecto xxqpPdftMypHeaderVORowImpl.",OAException.ERROR); 
      }
      
      String strUnidadDeNegocioC = xxqpPdftMypHeaderVORowImpl.getUnidadDeNegocioC();
      if(null!=strUnidadDeNegocioC){
          String strCamposRequeridos  = masivoYPlatinumAMImpl.getCamposRequeridosByUnidadDeNegocio(strUnidadDeNegocioC);
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
      
      String strPUnidadDeNegocioM = xxqpPdftMypHeaderVORowImpl.getUnidadDeNegocioM();
      if(null==PageLayoutRNBean){
       PageLayoutRNBean = pageContext.getPageLayoutBean();
      }
      if(null!=PageLayoutRNBean){
        if(null!=strPUnidadDeNegocioM){
          PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strPUnidadDeNegocioM);
          PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strPUnidadDeNegocioM);
        }
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
   
   String strUnidadDeNegocioValue = null; 
   String strValida = "Y";
        
   OAMessageChoiceBean UnidadDeNegocioBean = (OAMessageChoiceBean)webBean.findChildRecursive("UnidadDeNegocio");
    if(null!=UnidadDeNegocioBean){
     if(null!=UnidadDeNegocioBean.getValue(pageContext)){
         strUnidadDeNegocioValue = UnidadDeNegocioBean.getValue(pageContext).toString(); 
     }
    }
        
    if(null!=strUnidadDeNegocioValue){
    if("ME".equals(strUnidadDeNegocioValue)
    ||"POP_DELIVERY".equals(strUnidadDeNegocioValue)
    ||"E-COMMERCE".equals(strUnidadDeNegocioValue)
    ||"FIELD_SERVICE".equals(strUnidadDeNegocioValue)){
     strValida = "N";
     }
    }     
        
    DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoFileUpload"); 
    String strContratoFileUploadContentType = null; 
    if(null!=ContratoExamineUploadData){
      strContratoFileUploadContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
     }
        
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM); 
    MasivoYPlatinumAMImpl masivoYPlatinumAMImpl = (MasivoYPlatinumAMImpl)pageContext.getApplicationModule(webBean);
    
    System.out.println("strEventParam:"+strEventParam);
   
        
    XxqpPdftMypHeaderVOImpl xxqpPdftMypHeaderVOImpl = null; 
    XxqpPdftMypHeaderVORowImpl xxqpPdftMypHeaderVORowImpl = null; 
    xxqpPdftMypHeaderVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypHeaderVO1();
    RowSetIterator  MypHeaderIterator =   xxqpPdftMypHeaderVOImpl.createRowSetIterator(null);
    if(MypHeaderIterator.hasNext()){
       xxqpPdftMypHeaderVORowImpl = (XxqpPdftMypHeaderVORowImpl)MypHeaderIterator.next();
    }
        MypHeaderIterator.closeRowSetIterator();
    
    /************************** Reglas de Negocio *************
     **********************************************************
     **********************************************************/
    if("AgregarRegNegEvt".equals(strEventParam)){
        masivoYPlatinumAMImpl.createRowRegNegUpd(xxqpPdftMypHeaderVORowImpl.getId());
        return;
    }
    if("DeleteRegNegEvt".equals(strEventParam)){
       String strDeleteRegNegId = pageContext.getParameter("pDeleteRegNegId");
       System.out.println("strDeleteRegNegId:"+strDeleteRegNegId);
       masivoYPlatinumAMImpl.deleteRowRegNegUpd(strDeleteRegNegId);
       return; 
    }
    
        if("BorrarTodoEvt".equals(strEventParam)){
           masivoYPlatinumAMImpl.deleteAllRowRegNegUpd();
           return; 
        }
    
        if("lovPrepare".equals(strEventParam)){
          String lovInputSourceId = pageContext.getLovInputSourceId();
          String lstrPartyID = null; 
          lstrPartyID = xxqpPdftMypHeaderVORowImpl.getPartyId().toString();
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
                       masivoYPlatinumAMImpl.createRowRegNegUpd(strAttributes,xxqpPdftMypHeaderVORowImpl.getId());
                   }
                 }
             }
           
         }
         return;
     }
    
        
    if(3==gIntSelectedIndex){
       XxqpPdftMypProcesosVOImpl  xxqpPdftMypProcesosVOImpl =  masivoYPlatinumAMImpl.getXxqpPdftMypProcesosVO1(); 
       XxqpPdftMypOtrosProcesosVOImpl xxqpPdftMypOtrosProcesosVOImpl = masivoYPlatinumAMImpl.getXxqpPdftMypOtrosProcesosVO1();
        com.sun.java.util.collections.List listProcExcpt = new com.sun.java.util.collections.ArrayList(); 
        com.sun.java.util.collections.List listOtrosProcExcpt = new com.sun.java.util.collections.ArrayList(); 
        listProcExcpt = xxqpPdftMypProcesosVOImpl.validaPrecios();
        if(listProcExcpt.size()>0&&"Y".equals(strValida)){
         OAException.raiseBundledOAException(listProcExcpt);
        }
        listOtrosProcExcpt = xxqpPdftMypOtrosProcesosVOImpl.validaPrecios(); 
        if(listOtrosProcExcpt.size()>0&&"Y".equals(strValida)){
            OAException.raiseBundledOAException(listOtrosProcExcpt);
        }
    }    
        
    if("GrabarEvt".equals(strEventParam)){
       
        validarArchivos(pageContext,xxqpPdftMypHeaderVORowImpl);
        
        masivoYPlatinumAMImpl.salvarTransaccion(); 
        oracle.jbo.domain.Number numMasiYPlatHeaderId = xxqpPdftMypHeaderVORowImpl.getId();
        com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
        parameters.put("pMyPHeaderId",numMasiYPlatHeaderId.toString() );
        pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                  ,null /*functionName*/
                                  ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                  ,null /*menuName*/
                                  ,parameters /*parameters*/
                                  ,false /*retainAM*/
                                  ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                  ,OAException.ERROR /*messagingLevel*/
                                  ); 
         return; 
    } /** END if("GrabarEvt".equals(strEventParam)){ **/
    if("RevisarPDFEvt".equals(strEventParam)){
        DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");
        HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
        String contentDisposition = "attachment;filename=AltaFichaTecnicaMyP.pdf";
        response.setHeader("Content-Disposition",contentDisposition);
        response.setContentType("application/pdf");
        ServletOutputStream os=null;
       
         String strXML = null;
            strXML = masivoYPlatinumAMImpl.executeMypGetInfo("N");
            try {
                os = response.getOutputStream();
                byte[] aByte = strXML.getBytes();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
                ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
                AppsContext appsContext = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getAppsContext();
                Locale locale = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getUserLocale();
                TemplateHelper.processTemplate(appsContext, 
                                               AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                               "XXQP_PDFT_MYP", 
                                               locale.getLanguage(), 
                                               locale.getCountry(), 
                                               inputStream, 
                                               TemplateHelper.OUTPUT_TYPE_PDF, 
                                                null, 
                                               pdfFile);

                                    byte[] b = pdfFile.toByteArray();
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
            }
        } /** END if("RevisarPDFEvt".equals(strEventParam)){ **/
      if("ProcesarEvt".equals(strEventParam)){
            String strXML = null;
            oracle.jbo.domain.Number oracleNumeroFt = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number oracleNumeroFtRef = new oracle.jbo.domain.Number(0);
            String strNombreDelCliente = "";
            String strArticuloOracle ="";
            String strModificacion ="N";
            
            String strEstatusFTOld = xxqpPdftMypHeaderVORowImpl.getStatus();
            if(null!=xxqpPdftMypHeaderVORowImpl.getNumeroFt()){
            oracleNumeroFt = xxqpPdftMypHeaderVORowImpl.getNumeroFt(); 
            }
            if(null!=xxqpPdftMypHeaderVORowImpl.getNumeroFtReferencia()){
                oracleNumeroFtRef = xxqpPdftMypHeaderVORowImpl.getNumeroFtReferencia(); 
            }
            if(null!=xxqpPdftMypHeaderVORowImpl.getNombreDelCliente()){
                strNombreDelCliente = xxqpPdftMypHeaderVORowImpl.getNombreDelCliente();
            }
            
            System.out.println("oracleNumeroFtRef:"+oracleNumeroFtRef);
            System.out.println("strEstatusFT:"+strEstatusFTOld);
            
            /* strXML = masivoYPlatinumAMImpl.executeMypGetInfo(); 31072020 Poque se pone aqui? **/
            
            xxqpPdftMypHeaderVORowImpl.setStatus("ABIERTA");  /** Se solicito Cambiar el status antes de enviar el correo 25072018 **/
            String strPrefijo = masivoYPlatinumAMImpl.getPrefijoByUnidadDeNegocio(strUnidadDeNegocioValue);
            if(!"CAMBIO_DE_PRECIO".equals(strEstatusFTOld)){  /** 21042020 Que Permanezca el articulo Oracle**/
             strArticuloOracle = strPrefijo+xxqpPdftMypHeaderVORowImpl.getNumeroFt(); 
             xxqpPdftMypHeaderVORowImpl.setArticuloOracle(strArticuloOracle);
            }else{
                strModificacion = "Y";
                strArticuloOracle = xxqpPdftMypHeaderVORowImpl.getArticuloOracle();
            }
            validarArchivos(pageContext,xxqpPdftMypHeaderVORowImpl);
            masivoYPlatinumAMImpl.salvarTransaccion();  /** Se solicito Cambiar el status antes de enviar el correo 25072018 **/
            /* strXML = masivoYPlatinumAMImpl.executeMypGetInfo(); 21072020  porque se pone aqui?**/
             strXML = masivoYPlatinumAMImpl.executeMypGetInfo(strModificacion);
            try {
                byte[] aByte = strXML.getBytes();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(aByte);
                ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
                AppsContext appsContext = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getAppsContext();
                Locale locale = ((OADBTransactionImpl)masivoYPlatinumAMImpl.getOADBTransaction()).getUserLocale();
                TemplateHelper.processTemplate(appsContext, 
                                               AltaFichaTecnicaUtils.strShortApplication,//XxGQRecibosConstants.XXGQ_APP_SHORT_CUSTOM, 
                                               "XXQP_PDFT_MYP", 
                                               locale.getLanguage(), 
                                               locale.getCountry(), 
                                               inputStream, 
                                               TemplateHelper.OUTPUT_TYPE_PDF, 
                                                null, 
                                               pdfFile);
                if(null!=inputStream){
                inputStream.close();
                }
                
                byte[] a2Byte =pdfFile.toByteArray(); 
                InputStream inputStream2 = new ByteArrayInputStream(a2Byte);
                
                String strCorreos = masivoYPlatinumAMImpl.enviaCorreos(inputStream2
                                                                      ,oracleNumeroFtRef
                                                                      ,strEstatusFTOld
                                                                      ,oracleNumeroFt
                                                                      ,pageContext
                                                                      ,strNombreDelCliente
                                                                      ,strArticuloOracle
                                                                      ,xxqpPdftMypHeaderVORowImpl
                                                                      ); 
                System.out.println("strCorreos:"+strCorreos);
                
                if(null!=pdfFile){
                    pdfFile.close();
                }
                
                if(null!=inputStream2){
                    inputStream2.close();
                }
                
                xxqpPdftMypHeaderVORowImpl.setStatus("ABIERTA");
                masivoYPlatinumAMImpl.salvarTransaccion(); 
                oracle.jbo.domain.Number numMasiYPlatHeaderId = xxqpPdftMypHeaderVORowImpl.getId();
                com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
                parameters.put("pMyPHeaderId",numMasiYPlatHeaderId.toString() );
                pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumReOnPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
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
            }
            
    
        } /** END if("ProcesarEvt".equals(strEventParam)){ **/

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
              OAMessageTextInputBean PrecioProtonBean = (OAMessageTextInputBean)ProcesosTmpBean.findChildRecursive("PrecioProton");
              if(null!=PrecioBean){
                  PrecioProtonBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
              }
          }
          
             OATableBean OtrosProcesosTmpBean = (OATableBean)webBean.findChildRecursive("OtrosProcesosTmp");
             if(null!=OtrosProcesosTmpBean){
               OAMessageTextInputBean OtrosPrecioBean = (OAMessageTextInputBean)OtrosProcesosTmpBean.findChildRecursive("OtrosPrecio");
               if(null!=OtrosPrecioBean){
                   OtrosPrecioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
               }
                 OAMessageTextInputBean OtrosPrecioProtonBean = (OAMessageTextInputBean)OtrosProcesosTmpBean.findChildRecursive("OtrosPrecioProton");
                 if(null!=OtrosPrecioProtonBean){
                     OtrosPrecioProtonBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
                 }
             }
          
             OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioVORN");
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

    private void validarArchivos(OAPageContext pageContext
                                ,XxqpPdftMypHeaderVORowImpl pXxqpPdftMypHeaderVORowImpl) {
    
    DataObject ContratoExamineUploadData =  pageContext.getNamedDataObject("ContratoFileUpload"); 
    String strContratoExamineContentType = null; 
    if(null!=ContratoExamineUploadData){
        strContratoExamineContentType = ContratoExamineUploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
    }    
    
    DataObject FileUpload1Data =  pageContext.getNamedDataObject("FileUpload1"); 
    String strFileUpload1ContentType = null; 
    if(null!=FileUpload1Data){
        strFileUpload1ContentType = FileUpload1Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
    }    
    
    DataObject FileUpload2Data =  pageContext.getNamedDataObject("FileUpload2"); 
    String strFileUpload2ContentType = null; 
    if(null!=FileUpload2Data){
        strFileUpload2ContentType = FileUpload2Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
    }    
    
    DataObject FileUpload3Data =  pageContext.getNamedDataObject("FileUpload3"); 
    String strFileUpload3ContentType = null; 
    if(null!=FileUpload3Data){
        strFileUpload3ContentType = FileUpload3Data.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
    }    
        
    
      if(null!=pXxqpPdftMypHeaderVORowImpl.getContratoFile()){
        if(pXxqpPdftMypHeaderVORowImpl.getContratoFile().getLength()>0){
          System.out.println("Archivo Contrato Valido");
            pXxqpPdftMypHeaderVORowImpl.setContratoContentType(strContratoExamineContentType);
        }else{
            pXxqpPdftMypHeaderVORowImpl.setContratoFileName(null);
            pXxqpPdftMypHeaderVORowImpl.setContratoContentType(null);
        }
      }else{
          pXxqpPdftMypHeaderVORowImpl.setContratoFileName(null);
          pXxqpPdftMypHeaderVORowImpl.setContratoContentType(null);
      }
    
        if(null!=pXxqpPdftMypHeaderVORowImpl.getFile1()){
          if(pXxqpPdftMypHeaderVORowImpl.getFile1().getLength()>0){
            System.out.println("Archivo File1 Valido");
              pXxqpPdftMypHeaderVORowImpl.setContentType1(strFileUpload1ContentType);
          }else{
              pXxqpPdftMypHeaderVORowImpl.setFileName1(null);
              pXxqpPdftMypHeaderVORowImpl.setContentType1(null);
          }
        }else{
            pXxqpPdftMypHeaderVORowImpl.setFileName1(null);
            pXxqpPdftMypHeaderVORowImpl.setContentType1(null);
        }
        
        if(null!=pXxqpPdftMypHeaderVORowImpl.getFile2()){
          if(pXxqpPdftMypHeaderVORowImpl.getFile2().getLength()>0){
            System.out.println("Archivo File2 Valido");
              pXxqpPdftMypHeaderVORowImpl.setContentType2(strFileUpload2ContentType);
          }else{
              pXxqpPdftMypHeaderVORowImpl.setFileName2(null);
              pXxqpPdftMypHeaderVORowImpl.setContentType2(null);
          }
        }else{
            pXxqpPdftMypHeaderVORowImpl.setFileName2(null);
            pXxqpPdftMypHeaderVORowImpl.setContentType2(null);
        }
    
        if(null!=pXxqpPdftMypHeaderVORowImpl.getFile3()){
          if(pXxqpPdftMypHeaderVORowImpl.getFile3().getLength()>0){
            System.out.println("Archivo File3 Valido");
              pXxqpPdftMypHeaderVORowImpl.setContentType3(strFileUpload3ContentType);
          }else{
              pXxqpPdftMypHeaderVORowImpl.setFileName3(null);
              pXxqpPdftMypHeaderVORowImpl.setContentType3(null);
          }
        }else{
            pXxqpPdftMypHeaderVORowImpl.setFileName3(null);
            pXxqpPdftMypHeaderVORowImpl.setContentType3(null);
        }
        
    }
}

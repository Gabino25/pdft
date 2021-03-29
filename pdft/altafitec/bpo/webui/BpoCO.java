/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.bpo.webui;

import java.sql.SQLException;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
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
import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageRadioGroupBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;

import oracle.apps.fnd.framework.webui.beans.table.OATableBean;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.domain.BlobDomain;

import oracle.jbo.domain.Date;

import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.BpoAMImpl;
import xxqp.oracle.apps.ar.pdft.altafitec.bpo.server.PrecioTmpVOImpl;

/**
 * Controller for ...
 */
public class BpoCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
  
  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   private  String gStrPrecioTmpName = null;
    private String gStrOaSelectedSubtabIdx0 = "N"; 
    private String gStrOaSelectedSubtabIdx1 = "N"; 
    private String gStrOaSelectedSubtabIdx2 = "N"; 
    private String gStrOaSelectedSubtabIdx3 = "N"; 
    private int gIntSelectedIndex  = 0; 
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    
      String strPuserPdft = null; 
      System.out.println("BpoCO strPuserPdft:"+strPuserPdft);
      if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
          strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
          System.out.println("BpoCO strPuserPdft:"+strPuserPdft);
      }
      
      String strPuserPdftId = null; 
      if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
          strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
          System.out.println("BpoCO strPuserPdftId:"+strPuserPdftId);
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
    
    BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
    OAButtonBean grabarBean = (OAButtonBean)webBean.findChildRecursive("Grabar"); 
    OATableBean PrecioTmpBean = (OATableBean)webBean.findChildRecursive("PrecioTmp");
    OAFormValueBean FvOaSelectedSubtabIdx0Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx0");
    OAFormValueBean FvOaSelectedSubtabIdx1Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx1");
    OAFormValueBean FvOaSelectedSubtabIdx2Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx2");
    OAFormValueBean FvOaSelectedSubtabIdx3Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx3");
    OASubmitButtonBean ValidarBean = (OASubmitButtonBean)webBean.findChildRecursive("Validar");
    OAMessageTextInputBean SubtotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Subtotal"); 
    OAMessageTextInputBean IVABean = (OAMessageTextInputBean)webBean.findChildRecursive("IVA");
    OAMessageStyledTextBean TotalBean = (OAMessageStyledTextBean)webBean.findChildRecursive("Total");
    OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoExamine");
    OAMessageFileUploadBean Examine1Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("Examine1");
    OAMessageFileUploadBean Examine2Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("Examine2");
    OAMessageFileUploadBean Examine3Bean = (OAMessageFileUploadBean)webBean.findChildRecursive("Examine3");
    OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
    OASubTabLayoutBean subTabLayoutRNBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
    OAPageLayoutBean PageLayoutRNBean = (OAPageLayoutBean)webBean.findChildRecursive("PageLayoutRN");
    OAMessageChoiceBean EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
    
      String strPUnidadDeNegocio = pageContext.getParameter("pUnidadDeNegocio");
      String strPEmpresaQueFactura = pageContext.getParameter("pEmpresaQueFactura");
      String strpCicloFacturacion = pageContext.getParameter("pCicloFacturacion");
      String strpEjecutivo = pageContext.getParameter("pEjecutivo");
      
      environmentCurrencyFormat(pageContext,webBean);
      
      if(null!=strPUnidadDeNegocio){
          String strCamposRequeridos  = bpoAMImpl.getCamposRequeridosByUnidadDeNegocio(strPUnidadDeNegocio);
          if("N".equals(strCamposRequeridos)){
              environmentNoRequiredFields(pageContext,webBean);
          }
        /**********************************************************************
        if("EMERGENCIAS_PROGRAMADAS".equals(strPUnidadDeNegocio)){
           environmentNoRequiredFields(pageContext,webBean);
         }
         *******************************************************************/
      } 
     
      if(null!=EjecutivoBean){
            EjecutivoBean.setValue(pageContext,strPuserPdftId);
            EjecutivoBean.setReadOnly(true);
      }
      
    if(null!=NombreUsuarioEBSBean){
        /** NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());  110320211539 **/
         NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
     }
        
      if(null==PageLayoutRNBean){
       PageLayoutRNBean = pageContext.getPageLayoutBean();
      }
      if(null!=PageLayoutRNBean){
        if(null!=strPUnidadDeNegocio){
          String strUnidadNegocioM =  bpoAMImpl.getUnidadNegocioM(strPUnidadDeNegocio);
          PageLayoutRNBean.setWindowTitle("Detalle de Ficha Tecnica > "+strUnidadNegocioM);
          PageLayoutRNBean.setTitle("Detalle de Ficha Tecnica > "+strUnidadNegocioM);
        }
      }
        
    String strOaSelectedSubtab =  pageContext.getParameter(OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX);
    String strEnabledGrabar = pageContext.getParameter("pEnabledGrabar");  
    
    if(null!=subTabLayoutRNBean){
      gIntSelectedIndex =  subTabLayoutRNBean.getSelectedIndex(pageContext);
      System.out.println("gIntSelectedIndex:"+gIntSelectedIndex);
      if(null==strOaSelectedSubtab){
       if(-1!=gIntSelectedIndex){
           strOaSelectedSubtab = ""+gIntSelectedIndex;
       }
      }
    } /** END if(null!=SubTabLayoutBean){ **/  
      
    System.out.println("metodo processRequest");
      
      if("0".equals(strOaSelectedSubtab)){
          if(null!=FvOaSelectedSubtabIdx0Bean){
              FvOaSelectedSubtabIdx0Bean.setValue(pageContext,"Y");
          }
      }else if("1".equals(strOaSelectedSubtab)){
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
    
      if(null!=FvOaSelectedSubtabIdx0Bean){
       if(null!=FvOaSelectedSubtabIdx0Bean.getValue(pageContext)){
           gStrOaSelectedSubtabIdx0 = FvOaSelectedSubtabIdx0Bean.getValue(pageContext).toString();
       }
      }
      
      if(null!=FvOaSelectedSubtabIdx1Bean){
       if(null!=FvOaSelectedSubtabIdx1Bean.getValue(pageContext)){
           gStrOaSelectedSubtabIdx1 = FvOaSelectedSubtabIdx1Bean.getValue(pageContext).toString();
       }
      }
      
      if(null!=FvOaSelectedSubtabIdx2Bean){
      if(null!=FvOaSelectedSubtabIdx2Bean.getValue(pageContext)){
          gStrOaSelectedSubtabIdx2 = FvOaSelectedSubtabIdx2Bean.getValue(pageContext).toString();
      }
      }
      
      if(null!=FvOaSelectedSubtabIdx3Bean){
      if(null!=FvOaSelectedSubtabIdx3Bean.getValue(pageContext)){
          gStrOaSelectedSubtabIdx3 = FvOaSelectedSubtabIdx3Bean.getValue(pageContext).toString();
      }
      }  
     
      System.out.println("gStrOaSelectedSubtabIdx0:"+gStrOaSelectedSubtabIdx0);
      System.out.println("gStrOaSelectedSubtabIdx1:"+gStrOaSelectedSubtabIdx1);
      System.out.println("gStrOaSelectedSubtabIdx2:"+gStrOaSelectedSubtabIdx2);
      System.out.println("gStrOaSelectedSubtabIdx3:"+gStrOaSelectedSubtabIdx3);
     
      if("Y".equals(gStrOaSelectedSubtabIdx0)
       &&"Y".equals(gStrOaSelectedSubtabIdx1)
       &&"Y".equals(gStrOaSelectedSubtabIdx2)
       &&"Y".equals(gStrOaSelectedSubtabIdx3)
       ){
       if(null!=ValidarBean){
           ValidarBean.setDisabled(false);
        }
       }else{
           if(null!=ValidarBean){
               ValidarBean.setDisabled(true);
           }
       }
       
   
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
      
      if(!pageContext.isFormSubmission()){
            String lstrPartyID = null; 
            OAFormValueBean PartyIdBean = (OAFormValueBean)webBean.findChildRecursive("PartyId");
            if(null!=PartyIdBean){
             if(null!=PartyIdBean.getValue(pageContext)){
                 lstrPartyID = PartyIdBean.getValue(pageContext).toString();
                 bpoAMImpl.initEstadosLov(lstrPartyID,null);  
                 bpoAMImpl.initConceptosLov(lstrPartyID,null);  
             }
            }
      }
      
    if(!pageContext.isFormSubmission()){
        if("3".equals(strOaSelectedSubtab)){
            oracle.jbo.domain.Number numSubTotal = bpoAMImpl.obtieneSubtotal(); 
            oracle.jbo.domain.Number numIva = numSubTotal.multiply(0.16d);
            if(null!=SubtotalBean){
                SubtotalBean.setValue(pageContext,numSubTotal);
            }
            if(null!=IVABean){
                IVABean.setValue(pageContext,numIva);
            }
            
            String strSubtotalValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Subtotal");
            String strIVAValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"IVA");
             if(null!=strSubtotalValue&&!"".equals(strSubtotalValue)
             &&(null!=strIVAValue&&!"".equals(strIVAValue))
             ){
              Double douSubtotal = new Double(strSubtotalValue); 
              Double douIVA = new Double(strIVAValue); 
                 if(null!=TotalBean){
                     TotalBean.setValue(pageContext,(douSubtotal+douIVA));
                 }
             }
             
        } /** END if("3".equals(strOaSelectedSubtab)){ **/
        
        
    
        if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){
            if(null!=grabarBean){
                grabarBean.setDisabled(false);
                grabarBean.setOnClick("this.disabled=true;");
            } /** END if(null!=grabarBean){ **/
        }else{
            if(null!=grabarBean){
                grabarBean.setDisabled(true);
            } /** END if(null!=grabarBean){ **/
            if(null!=PrecioTmpBean){
             if(null!=PrecioTmpBean.getName(pageContext)){
             gStrPrecioTmpName = PrecioTmpBean.getName(pageContext);
             }
            }
            
            bpoAMImpl.insertInitPrecioTmpRowVo();
            
            bpoAMImpl.initBpoHeaderTmpVO();
            
           
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
               if(null!=strpCicloFacturacion&&!"".equals(strpCicloFacturacion))
               CicloFacturacionBean.setValue(pageContext,strpCicloFacturacion);
             }
            
             
            if(null!=bpoAMImpl){
                bpoAMImpl.fillCamposHead(pageContext,webBean);
            }   
            
             
            
        }/** END if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){ **/
        
    }else{
    
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
    String strSourceParam = pageContext.getParameter(this.SOURCE_PARAM);
    String rowReference =  pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
      
    String strOaSelectedSubtab =  pageContext.getParameter(OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX);
      String strValida = "Y";
    
    BpoAMImpl bpoAMImpl = (BpoAMImpl)pageContext.getApplicationModule(webBean);
    
    OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
    OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
    
      System.out.println("strEventParam:"+strEventParam);
      /************************** Reglas de Negocio *************
       **********************************************************
       **********************************************************/
      if("AgregarRegNegEvt".equals(strEventParam)){
          bpoAMImpl.createRowRegNeg();
          return;
      }
      if("DeleteRegNegEvt".equals(strEventParam)){
         String strDeleteRegNegId = pageContext.getParameter("pDeleteRegNegId");
         System.out.println("strDeleteRegNegId:"+strDeleteRegNegId);
         bpoAMImpl.deleteRowRegNeg(strDeleteRegNegId);
         return; 
      }
      if("BorrarTodoEvt".equals(strEventParam)){
          bpoAMImpl.deleteAllRowRegNeg();
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
                        bpoAMImpl.createRowRegNeg(strAttributes);
                    }
                  }
              }
            
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
      String strExamine1FileName = null; 
      String strExamine1ContentType = null; 
      BlobDomain Examine1ByteStream = null;
      String strExamine2FileName = null; 
      String strExamine2ContentType = null; 
      BlobDomain Examine2ByteStream = null;
      String strExamine3FileName = null; 
      String strExamine3ContentType = null; 
      BlobDomain Examine3ByteStream = null;
      String strEstatusValue = null; 
      String strUnidadDeNegocioValue = null; 
      String strEmpresaQueFacturaValue = null; 
      String strCicloFacturacionValue = null; 
      String strFechaInicioOperacionValue = null; 
      String strPartyID = null; 
      String strEjecutivoValue = null; 
      String strArticuloOracle = null; 
        
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
        if(null==ContratoExamineByteStream){
            ContratoExamineByteStream = (BlobDomain)bpoAMImpl.getBpoHeaderTmpVO1().getCurrentRow().getAttribute("ContratoFile");
        }
      }
      
      DataObject Examine1UploadData =  pageContext.getNamedDataObject("Examine1"); 
      if(null!=Examine1UploadData){
        strExamine1FileName = Examine1UploadData.selectValue(null,"UPLOAD_FILE_NAME").toString();
        strExamine1ContentType = Examine1UploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
        Examine1ByteStream = (BlobDomain)Examine1UploadData.selectValue(null,strExamine1FileName);
        if(null==Examine1ByteStream){
            Examine1ByteStream = (BlobDomain)bpoAMImpl.getBpoHeaderTmpVO1().getCurrentRow().getAttribute("File1");
        }
      }
      DataObject Examine2UploadData =  pageContext.getNamedDataObject("Examine2"); 
      if(null!=Examine2UploadData){
        strExamine2FileName = Examine2UploadData.selectValue(null,"UPLOAD_FILE_NAME").toString();
        strExamine2ContentType = Examine2UploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
        Examine2ByteStream = (BlobDomain)Examine2UploadData.selectValue(null,strExamine2FileName);
        if(null==Examine2ByteStream){
            Examine2ByteStream = (BlobDomain)bpoAMImpl.getBpoHeaderTmpVO1().getCurrentRow().getAttribute("File2");
        }
      }
      DataObject Examine3UploadData =  pageContext.getNamedDataObject("Examine3"); 
      if(null!=Examine3UploadData){
        strExamine3FileName = Examine3UploadData.selectValue(null,"UPLOAD_FILE_NAME").toString();
        strExamine3ContentType = Examine3UploadData.selectValue(null,"UPLOAD_FILE_MIME_TYPE").toString();
        Examine3ByteStream = (BlobDomain)Examine3UploadData.selectValue(null,strExamine3FileName);
        if(null==Examine3ByteStream){
            Examine3ByteStream = (BlobDomain)bpoAMImpl.getBpoHeaderTmpVO1().getCurrentRow().getAttribute("File3");
        }
      }
      
      OAMessageChoiceBean  EstatusBean = (OAMessageChoiceBean)webBean.findChildRecursive("Estatus");
      if(null!=EstatusBean){
       if(null!=EstatusBean.getValue(pageContext)){
        strEstatusValue = EstatusBean.getValue(pageContext).toString();
       }
      }
        
        
       strUnidadDeNegocioValue = getValueFromOAMessageChoiceBean(pageContext,webBean,"UnidadDeNegocio");
       strEmpresaQueFacturaValue = getValueFromOAMessageChoiceBean(pageContext,webBean,"EmpresaQueFactura");
       strCicloFacturacionValue =   getValueFromOAMessageChoiceBean(pageContext,webBean,"CicloFacturacion");
       
       strEjecutivoValue =   getValueFromOAMessageChoiceBean(pageContext,webBean,"Ejecutivo");
       
      if(null!=strUnidadDeNegocioValue){
      if("EMERGENCIAS_PROGRAMADAS".equals(strUnidadDeNegocioValue)
        ){
       strValida = "N";
       }
      } 
      
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
        
       OAMessageLovInputBean articuloOracleBean = (OAMessageLovInputBean)webBean.findChildRecursive("ArticuloOracle"); 
       if(null!=articuloOracleBean){
         if(null!=articuloOracleBean.getValue(pageContext)){
           strArticuloOracle = articuloOracleBean.getValue(pageContext).toString(); 
         }
       }
      
       System.out.println("rowReference:"+rowReference); 
      if("DeleteEvt".equals(strEventParam)){
        PrecioTmpVOImpl precioTmpVOImpl =  bpoAMImpl.getPrecioTmpVO1();
        int rowCount =  precioTmpVOImpl.getRowCount();
        int fetchedRowCount =  precioTmpVOImpl.getFetchedRowCount();
        System.out.println("rowCount:"+rowCount);
        System.out.println("fetchedRowCount:"+fetchedRowCount);
        if(1==fetchedRowCount){
         throw new OAException("Al menos un registro es requerido.",OAException.ERROR); 
        }else{
         OARow currRow = (OARow)bpoAMImpl.findRowByRef(rowReference);
         currRow.remove();
         return; 
        }
      } /** END if("DeleteEvt".equals(strEventParam)){ **/
       
      /*************************************************************************
       ************** Precio ***************************************************
       *************************************************************************/
     
      /*************************************************************************
       ************** Servicio *************************************************
       *************************************************************************/
       String strCobranzaValue = null; 
       String strVentasValue = null; 
       String strGestorAdministrativoValue = null; 
       String strTecnicoValue = null;
       String strRecoleDocumMaterValue = null; 
       String strEntregaAdomicilioValue = null; 
       String strOtrosValue = null; 
       String strOtrosIlimitadoValue = null; 
      /** java.sql.Timestamp  datFechaInicioDelServicioValue = null;  **/
       String strFechaInicioDelServicioValue = null;
       String strDiasSemanaLaboraraValue = null; 
       String strNoHrsDiariasValue = null; 
       String strElMensajeroManejeValue = null; 
       String strHorarioDeTrabajoValue = null; 
       String strDireccionBaseValue = null; 
       String strObservacionesValue = null; 
       String strDireccionBaseIlimitadoValue = null; 
       String strObservacionesIlimitadoValue = null; 
       String strHoraInicial = null; 
       String strMinutoInicial = null; 
       String strHoraFinal = null; 
       String strMinutoFinal = null; 
       String strHorarioInicial = null; 
       String strHorarioFinal = null; 
      String strLunes = null; 
      String strMartes = null; 
      String strMiercoles = null; 
      String strJueves = null; 
      String strViernes = null;
      String strSabado = null; 
      String strDomingo = null; 
       
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
      
      strLunes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Lunes");
      strMartes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Martes");
      strMiercoles = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Miercoles");
      strJueves = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Jueves");
      strViernes = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Viernes");
      strSabado = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Sabado");
      strDomingo = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Domingo");
       
       
       strCobranzaValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Cobranza"); 
       strVentasValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Ventas"); 
       strGestorAdministrativoValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GestorAdministrativo"); 
       strTecnicoValue =this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"Tecnico");    
       strRecoleDocumMaterValue =this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"RecoleDocumMater");   
       strEntregaAdomicilioValue = this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"EntregaAdomicilio"); 
       strOtrosValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Otros"); 
       strOtrosIlimitadoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"OtrosIlim"); 
       System.out.println("gIntSelectedIndex:"+gIntSelectedIndex);
      System.out.println("strCobranzaValue:"+strCobranzaValue);
      System.out.println("strVentasValue:"+strVentasValue);
      System.out.println("strGestorAdministrativoValue:"+strGestorAdministrativoValue);
      System.out.println("strTecnicoValue:"+strTecnicoValue);
      System.out.println("strRecoleDocumMaterValue:"+strRecoleDocumMaterValue);
      System.out.println("strEntregaAdomicilioValue:"+strEntregaAdomicilioValue);
      System.out.println("strOtrosValue:"+strOtrosValue);
      
      if("1".equals(strOaSelectedSubtab)){
       if(strCobranzaValue.equals("N")
       &&(strVentasValue.equals("N"))
       &&(strGestorAdministrativoValue.equals("N"))
       &&(strTecnicoValue.equals("N"))
       &&(strRecoleDocumMaterValue.equals("N"))
       &&(strEntregaAdomicilioValue.equals("N"))
       &&(null==strOtrosValue||"".equals(strOtrosValue))
       &&("Y".equals(strValida))
        ){
         throw new OAException("Seleccionar al menos uno de los 7 valores del Area de Perfil.",OAException.ERROR); 
        }
       } /** END if(1==gIntSelectedIndex){  **/
       
        OAMessageDateFieldBean FechaInicioDelServicioBean = (OAMessageDateFieldBean)webBean.findChildRecursive("FechaInicioDelServicio");
        if(null!=FechaInicioDelServicioBean){
          if(null!=FechaInicioDelServicioBean.getValue(pageContext)){
              strFechaInicioDelServicioValue = FechaInicioDelServicioBean.getValue(pageContext).toString();
          }
        } 
     /**  datFechaInicioDelServicioValue = this.getValueFromOAMessageDateFieldBean(pageContext,webBean,"FechaInicioDelServicio"); **/
       System.out.println("datFechaInicioDelServicioValue:"+strFechaInicioDelServicioValue);
       strDiasSemanaLaboraraValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiasDeLaSemanaQueLaborara"); 
       strNoHrsDiariasValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"NoHrsDiarias");
       strElMensajeroManejeValue = this.getValueFromOAMessageRadioGroupBean(pageContext,webBean,"ElMensajeroManeje");
       strHorarioDeTrabajoValue = this.getValueFromOAMessageChoiceBean(pageContext,webBean,"HorarioDeTrabajo"); 
       strDireccionBaseValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DireccionBase"); 
       strObservacionesValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Observaciones");
      strDireccionBaseIlimitadoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DireccionBaseIlim"); 
      strObservacionesIlimitadoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"ObservacionesIlim");
      /*************************************************************************
       ************** Requerimientos Adicionales********************************
       *************************************************************************/
       String strGyCMoto125Value = null; 
       String strGyCCascoValue = null; 
       String strGyCCajaGrandeValue = null; 
       String strGyCCajaChicaRojaValue = null; 
       String strGyCCamisasValue = null; 
       String strGyCImpermeableValue = null; 
       String strGyCTarjetaGasolinaValue = null; 
       String strGyCPantalonVestirValue = null; 
       String strGyCCajaNegraValue = null; 
       String strGyCHieleraValue = null; 
       String strGyCMovilValue = null; 
       String strGyCAutomovilValue = null; 
       String strGyCGuiaRojiValue = null; 
       String strGyCOtrosValue = null; 
       String strGyCComentariosValue = null; 
       
      strGyCMoto125Value=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCMoto125");
      strGyCCascoValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCCasco");
      strGyCCajaGrandeValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCCajaGrande");
      strGyCCajaChicaRojaValue =this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCCajaChicaRoja");
      strGyCCamisasValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCCamisas");
      strGyCImpermeableValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCImpermeable");
      strGyCTarjetaGasolinaValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCTarjetaGasolina");
      strGyCPantalonVestirValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCPantalonVestir");
      strGyCCajaNegraValue= this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCCajaNegra");
      strGyCHieleraValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCHielera");
      strGyCMovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCMovil");
      strGyCAutomovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCAutomovil");
      strGyCGuiaRojiValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCGuiaRoji");
      strGyCOtrosValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"GyCOtros");
      strGyCComentariosValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"GyCComentarios");
      
      com.sun.java.util.collections.List listExc = new com.sun.java.util.collections.ArrayList();
      
      if("N".equals(strGyCMoto125Value)
       &&"N".equals(strGyCCascoValue)
       &&"N".equals(strGyCCajaGrandeValue)
       &&"N".equals(strGyCCajaChicaRojaValue)
       &&"N".equals(strGyCCamisasValue)
       &&"N".equals(strGyCImpermeableValue)
       &&"N".equals(strGyCTarjetaGasolinaValue)
       &&"N".equals(strGyCPantalonVestirValue)
       &&"N".equals(strGyCCajaNegraValue)
       &&"N".equals(strGyCHieleraValue)
       &&"N".equals(strGyCMovilValue)
       &&"N".equals(strGyCAutomovilValue)
       &&"N".equals(strGyCGuiaRojiValue)
       &&"N".equals(strGyCOtrosValue)
       ){
           OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Gente y Cultura",OAException.ERROR);
               listExc.add(oAException);
       }
      
      
        String strOperMoto125Value = null; 
        String strOperCascoValue = null; 
        String strOperCajaGrandeValue = null; 
        String strOperCajaChicaRojaValue = null; 
        String strOperCamisasValue = null; 
        String strOperImpermeableValue = null; 
        String strOperTarjetaGasolinaValue = null; 
        String strOperPantalonVestirValue = null; 
        String strOperCajaNegraValue = null; 
        String strOperHieleraValue = null; 
        String strOperMovilValue = null; 
        String strOperAutomovilValue = null; 
        String strOperGuiaRojiValue = null; 
        String strOperOtrosValue = null; 
        String strOperComentariosValue = null; 
        String strOperComentariosIlimitadoValue = null; 
      
      strOperMoto125Value=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperMoto125");
           strOperCascoValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperCasco");
           strOperCajaGrandeValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperCajaGrande");
           strOperCajaChicaRojaValue =this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperCajaChicaRoja");
           strOperCamisasValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperCamisas");
           strOperImpermeableValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperImpermeable");
           strOperTarjetaGasolinaValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperTarjetaGasolina");
           strOperPantalonVestirValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperPantalonVestir");
           strOperCajaNegraValue= this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperCajaNegra");
           strOperHieleraValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperHielera");
           strOperMovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperMovil");
           strOperAutomovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperAutomovil");
           strOperGuiaRojiValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperGuiaRoji");
           strOperOtrosValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"OperOtros");
           strOperComentariosValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"OperComentarios");
           strOperComentariosIlimitadoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"OperComentariosIlim");
      
      if("N".equals(strOperMoto125Value)
       &&"N".equals(strOperCascoValue)
       &&"N".equals(strOperCajaGrandeValue)
       &&"N".equals(strOperCajaChicaRojaValue)
       &&"N".equals(strOperCamisasValue)
       &&"N".equals(strOperImpermeableValue)
       &&"N".equals(strOperTarjetaGasolinaValue)
       &&"N".equals(strOperPantalonVestirValue)
       &&"N".equals(strOperCajaNegraValue)
       &&"N".equals(strOperHieleraValue)
       &&"N".equals(strOperMovilValue)
       &&"N".equals(strOperAutomovilValue)
       &&"N".equals(strOperGuiaRojiValue)
       &&"N".equals(strOperOtrosValue)
       ){
           OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Operaciones",OAException.ERROR);
               listExc.add(oAException);
       }
       
        String strAdquMoto125Value = null; 
        String strAdquCascoValue = null; 
        String strAdquCajaGrandeValue = null; 
        String strAdquCajaChicaRojaValue = null; 
        String strAdquCamisasValue = null; 
        String strAdquImpermeableValue = null; 
        String strAdquTarjetaGasolinaValue = null; 
        String strAdquPantalonVestirValue = null; 
        String strAdquCajaNegraValue = null; 
        String strAdquHieleraValue = null; 
        String strAdquMovilValue = null; 
        String strAdquAutomovilValue = null; 
        String strAdquGuiaRojiValue = null; 
        String strAdquOtrosValue = null; 
        String strAdquComentariosValue = null; 
        
            strAdquMoto125Value=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquMoto125");
            strAdquCascoValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquCasco");
            strAdquCajaGrandeValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquCajaGrande");
            strAdquCajaChicaRojaValue =this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquCajaChicaRoja");
            strAdquCamisasValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquCamisas");
            strAdquImpermeableValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquImpermeable");
            strAdquTarjetaGasolinaValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquTarjetaGasolina");
            strAdquPantalonVestirValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquPantalonVestir");
            strAdquCajaNegraValue= this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquCajaNegra");
            strAdquHieleraValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquHielera");
            strAdquMovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquMovil");
            strAdquAutomovilValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquAutomovil");
            strAdquGuiaRojiValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquGuiaRoji");
            strAdquOtrosValue=this.getValueFromOAMessageCheckBoxBean(pageContext,webBean,"AdquOtros");
            strAdquComentariosValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"AdquComentarios");
       
              if("N".equals(strAdquMoto125Value)
               &&"N".equals(strAdquCascoValue)
               &&"N".equals(strAdquCajaGrandeValue)
               &&"N".equals(strAdquCajaChicaRojaValue)
               &&"N".equals(strAdquCamisasValue)
               &&"N".equals(strAdquImpermeableValue)
               &&"N".equals(strAdquTarjetaGasolinaValue)
               &&"N".equals(strAdquPantalonVestirValue)
               &&"N".equals(strAdquCajaNegraValue)
               &&"N".equals(strAdquHieleraValue)
               &&"N".equals(strAdquMovilValue)
               &&"N".equals(strAdquAutomovilValue)
               &&"N".equals(strAdquGuiaRojiValue)
               &&"N".equals(strAdquOtrosValue)
               ){
                   OAException oAException = new OAException("Al menos una opcion debe estar seleccionada en el apartado Adquisiciones",OAException.ERROR);
                       listExc.add(oAException);
               }
    
    /***********************************************************************************
     
        if(null!=strGyCMoto125Value&&!"N".equals(strGyCMoto125Value)
          ||(null!=strOperMoto125Value&&!"N".equals(strOperMoto125Value))
            ||(null!=strAdquMoto125Value&&!"N".equals(strAdquMoto125Value))
          ){
          }else{
          OAException oAException = new OAException("Moto 125:Debe estar seleccionado",OAException.ERROR);
              listExc.add(oAException);
          }
       
      if(null!=strGyCCascoValue&&!"N".equals(strGyCCascoValue)
        ||(null!=strOperCascoValue&&!"N".equals(strOperCascoValue))
          ||(null!=strAdquCascoValue&&!"N".equals(strAdquCascoValue))
        ){
        }else{
        OAException oAException = new OAException("Casco:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCCajaGrandeValue&&!"N".equals(strGyCCajaGrandeValue)
        ||(null!=strOperCajaGrandeValue&&!"N".equals(strOperCajaGrandeValue))
          ||(null!=strAdquCajaGrandeValue&&!"N".equals(strAdquCajaGrandeValue))
        ){
        }else{
        OAException oAException = new OAException("Caja Grande:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCCajaChicaRojaValue&&!"N".equals(strGyCCajaChicaRojaValue)
        ||(null!=strOperCajaChicaRojaValue&&!"N".equals(strOperCajaChicaRojaValue))
          ||(null!=strAdquCajaChicaRojaValue&&!"N".equals(strAdquCajaChicaRojaValue))
        ){
        }else{
        OAException oAException = new OAException("Caja Chica Roja:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        } 
        
      if(null!=strGyCCamisasValue&&!"N".equals(strGyCCamisasValue)
        ||(null!=strOperCamisasValue&&!"N".equals(strOperCamisasValue))
          ||(null!=strAdquCamisasValue&&!"N".equals(strAdquCamisasValue))
        ){
        }else{
        OAException oAException = new OAException("Camisas:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCImpermeableValue&&!"N".equals(strGyCImpermeableValue)
        ||(null!=strOperImpermeableValue&&!"N".equals(strOperImpermeableValue))
          ||(null!=strAdquImpermeableValue&&!"N".equals(strAdquImpermeableValue))
        ){
        }else{
        OAException oAException = new OAException("Impermeable:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCTarjetaGasolinaValue&&!"N".equals(strGyCTarjetaGasolinaValue)
        ||(null!=strOperTarjetaGasolinaValue&&!"N".equals(strOperTarjetaGasolinaValue))
          ||(null!=strAdquTarjetaGasolinaValue&&!"N".equals(strAdquTarjetaGasolinaValue))
        ){
        }else{
        OAException oAException = new OAException("Tarjeta Gasolina:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCPantalonVestirValue&&!"N".equals(strGyCPantalonVestirValue)
        ||(null!=strOperPantalonVestirValue&&!"N".equals(strOperPantalonVestirValue))
          ||(null!=strAdquPantalonVestirValue&&!"N".equals(strAdquPantalonVestirValue))
        ){
        }else{
        OAException oAException = new OAException("Pantalon Vestir:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCCajaNegraValue&&!"N".equals(strGyCCajaNegraValue)
        ||(null!=strOperCajaNegraValue&&!"N".equals(strOperCajaNegraValue))
          ||(null!=strAdquCajaNegraValue&&!"N".equals(strAdquCajaNegraValue))
        ){
        }else{
        OAException oAException = new OAException("Caja Negra:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCHieleraValue&&!"N".equals(strGyCHieleraValue)
        ||(null!=strOperHieleraValue&&!"N".equals(strOperHieleraValue))
          ||(null!=strAdquHieleraValue&&!"N".equals(strAdquHieleraValue))
        ){
        }else{
        OAException oAException = new OAException("Hielera:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCMovilValue&&!"N".equals(strGyCMovilValue)
        ||(null!=strOperMovilValue&&!"N".equals(strOperMovilValue))
          ||(null!=strAdquMovilValue&&!"N".equals(strAdquMovilValue))
        ){
        }else{
        OAException oAException = new OAException("Movil:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCAutomovilValue&&!"N".equals(strGyCAutomovilValue)
        ||(null!=strOperAutomovilValue&&!"N".equals(strOperAutomovilValue))
          ||(null!=strAdquAutomovilValue&&!"N".equals(strAdquAutomovilValue))
        ){
        }else{
        OAException oAException = new OAException("Automovil:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
       
      if(null!=strGyCGuiaRojiValue&&!"N".equals(strGyCGuiaRojiValue)
        ||(null!=strOperGuiaRojiValue&&!"N".equals(strOperGuiaRojiValue))
          ||(null!=strAdquGuiaRojiValue&&!"N".equals(strAdquGuiaRojiValue))
        ){
        }else{
        OAException oAException = new OAException("GuiaRoji:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
        
      if(null!=strGyCOtrosValue&&!"N".equals(strGyCOtrosValue)
        ||(null!=strOperOtrosValue&&!"N".equals(strOperOtrosValue))
          ||(null!=strAdquOtrosValue&&!"N".equals(strAdquOtrosValue))
        ){
        }else{
        OAException oAException = new OAException("Otros:Debe estar seleccionado",OAException.ERROR);
            listExc.add(oAException);
        }
     **********************************************************************************************/   
        
        if(listExc.size()>0&&"Y".equals(strValida)){
            System.out.println("strOaSelectedSubtab:"+strOaSelectedSubtab);
            if("2".equals(strOaSelectedSubtab)){
            OAException.raiseBundledOAException(listExc);
            }
        }
       
      /*************************************************************************
       *********************** Pago ********************************************
       *************************************************************************/
       
       OAMessageChoiceBean EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
       
       OAMessageTextInputBean EjecutivoDeVentasBean = (OAMessageTextInputBean)webBean.findChildRecursive("EjecutivoDeVentas");
       if(null!=EjecutivoDeVentasBean){
         if(null!=EjecutivoBean){
          if(null!=EjecutivoBean.getSelectionText(pageContext)){
              /** Se puede hacer ya que no se estan modificando condicionales solo valores **/
              EjecutivoDeVentasBean.setValue(pageContext,EjecutivoBean.getSelectionText(pageContext).toString());
          }
         }
       }
       
       String strEjecutivoDeVentasValue = null; 
       String strPlazaValue = null; 
       String strSubtotalValue = null; 
       String strIVAValue = null; 
       String strTotalValue = null; 
       String strFormaDePagoValue = null; 
       String strDiaDePagoValue = null; 
       
       
     
      
      OAMessageTextInputBean PlazaBean = (OAMessageTextInputBean)webBean.findChildRecursive("Plaza");
      if(null!=PlazaBean){
       if(null!=EmpresaQueFacturaBean){
        if(null!=EmpresaQueFacturaBean.getSelectionText(pageContext)){
            PlazaBean.setValue(pageContext,EmpresaQueFacturaBean.getSelectionText(pageContext));
        }
       }
      }
       
      strEjecutivoDeVentasValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"EjecutivoDeVentas");
      strPlazaValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Plaza");
      strSubtotalValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"Subtotal");
      strIVAValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"IVA");
      strTotalValue = this.getValueFromOAMessageStyledTextBean(pageContext,webBean,"Total");
      strFormaDePagoValue = this.getValueFromOAMessageRadioGroupBean(pageContext,webBean,"FormaDePago");
      strDiaDePagoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"DiaDePago");
      
       
      System.out.println("strEventParam:"+strEventParam); 
      System.out.println("strSourceParam:"+strSourceParam);
     
      
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
        if(null!=bpoAMImpl){
        synchronized(this){
           StringBuilder strBpoHeaderId = new StringBuilder("");
                      bpoAMImpl.fillHeader( strBpoHeaderId
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
                                           , strArticuloOracle
                                           , strExamine1FileName 
                                           , strExamine1ContentType 
                                           , Examine1ByteStream 
                                           , strExamine2FileName 
                                           , strExamine2ContentType 
                                           , Examine2ByteStream 
                                           , strExamine3FileName 
                                           , strExamine3ContentType 
                                           , Examine3ByteStream 
                                           );
                      
                      bpoAMImpl.fillPrecio(strBpoHeaderId);  
                      
                      bpoAMImpl.fillServicio(strBpoHeaderId
                                            ,strCobranzaValue
                                            ,strVentasValue 
                                            ,strGestorAdministrativoValue
                                            ,strTecnicoValue 
                                            ,strRecoleDocumMaterValue 
                                            ,strEntregaAdomicilioValue 
                                            ,strOtrosValue 
                                            /**,datFechaInicioDelServicioValue **/
                                            ,strFechaInicioDelServicioValue
                                            ,strDiasSemanaLaboraraValue  
                                            ,strNoHrsDiariasValue 
                                            ,strElMensajeroManejeValue 
                                            ,strHorarioDeTrabajoValue  
                                            ,strDireccionBaseValue 
                                            ,strObservacionesValue  
                                            ,strHorarioInicial
                                            ,strHorarioFinal
                                            ,strLunes
                                            ,strMartes
                                            ,strMiercoles
                                            ,strJueves
                                            ,strViernes
                                            ,strSabado
                                            ,strDomingo
                                            ,strOtrosIlimitadoValue
                                            ,strDireccionBaseIlimitadoValue 
                                            ,strObservacionesIlimitadoValue 
                                            ); 
                    bpoAMImpl.fillRequeAdicio(strBpoHeaderId
                                             ,"GYC"
                                             ,strGyCMoto125Value  
                                             ,strGyCCascoValue 
                                             ,strGyCCajaGrandeValue 
                                             ,strGyCCajaChicaRojaValue 
                                             ,strGyCCamisasValue
                                             ,strGyCImpermeableValue  
                                             ,strGyCTarjetaGasolinaValue 
                                             ,strGyCPantalonVestirValue 
                                             ,strGyCCajaNegraValue 
                                             ,strGyCHieleraValue  
                                             ,strGyCMovilValue 
                                             ,strGyCAutomovilValue 
                                             ,strGyCGuiaRojiValue  
                                             ,strGyCOtrosValue 
                                             ,strGyCComentariosValue 
                                             ,null
                                             );  
                    bpoAMImpl.fillRequeAdicio(strBpoHeaderId
                                             ,"OPER"
                                             ,strOperMoto125Value  
                                             ,strOperCascoValue 
                                             ,strOperCajaGrandeValue 
                                             ,strOperCajaChicaRojaValue 
                                             ,strOperCamisasValue
                                             ,strOperImpermeableValue  
                                             ,strOperTarjetaGasolinaValue 
                                             ,strOperPantalonVestirValue 
                                             ,strOperCajaNegraValue 
                                             ,strOperHieleraValue  
                                             ,strOperMovilValue 
                                             ,strOperAutomovilValue 
                                             ,strOperGuiaRojiValue  
                                             ,strOperOtrosValue 
                                             ,strOperComentariosValue 
                                             ,strOperComentariosIlimitadoValue
                                             );  
                    bpoAMImpl.fillRequeAdicio(strBpoHeaderId
                                             ,"ADQU"
                                             ,strAdquMoto125Value  
                                             ,strAdquCascoValue 
                                             ,strAdquCajaGrandeValue 
                                             ,strAdquCajaChicaRojaValue 
                                             ,strAdquCamisasValue
                                             ,strAdquImpermeableValue  
                                             ,strAdquTarjetaGasolinaValue 
                                             ,strAdquPantalonVestirValue 
                                             ,strAdquCajaNegraValue 
                                             ,strAdquHieleraValue  
                                             ,strAdquMovilValue 
                                             ,strAdquAutomovilValue 
                                             ,strAdquGuiaRojiValue  
                                             ,strAdquOtrosValue 
                                             ,strAdquComentariosValue 
                                             ,null
                                             );      
                    
                       bpoAMImpl.fillPago(strBpoHeaderId
                                        , strEjecutivoDeVentasValue
                                        , strPlazaValue 
                                        , strSubtotalValue 
                                        , strIVAValue 
                                        , strTotalValue 
                                        , strFormaDePagoValue 
                                        , strDiaDePagoValue 
                                        );                           
                     
             bpoAMImpl.fillReglasDeNegocio(strBpoHeaderId); 
                     
            com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
            parameters.put("pBpoHeaderId",strBpoHeaderId.toString() );
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoUpdPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                      ,null /*functionName*/
                                      ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                      ,null /*menuName*/
                                      ,parameters /*parameters*/
                                      ,false /*retainAM*/
                                      ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                      ,OAException.ERROR /*messagingLevel*/
                                      );     
            return;
          } /** END synchronized(this){ **/
         
        } /** END  if(null!=bpoAMImpl){ **/                               
       
       } /**END if("GrabarEvt".equals(strEventParam)){**/
       
       if("AgregarEvt".equals(strEventParam)){
           bpoAMImpl.insertPrecioTmpRowVo();
           return;
       }
      
      if(null!=gStrPrecioTmpName){
       if("update".equals(strEventParam)&&gStrPrecioTmpName.equals(strSourceParam)){
           bpoAMImpl.refreshPrecioTmpVO();
       }else if(0==gIntSelectedIndex){
           bpoAMImpl.refreshPrecioTmpVO();
       }
    } /** END if(null!=gStrPrecioTmpName){ **/
      
        
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
    
    private String getValueFromOAMessageStyledTextBean(OAPageContext pageContext, 
                                                      OAWebBean webBean, 
                                                      String pStrIdBean) {
        String strRetVal = null; 
        OAMessageStyledTextBean temporalBean = (OAMessageStyledTextBean)webBean.findChildRecursive(pStrIdBean); 
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
    
    
    private java.sql.Timestamp getValueFromOAMessageDateFieldBean(OAPageContext pageContext, 
                                                   OAWebBean webBean, 
                                                   String pStrIdBean) {
        java.sql.Timestamp datRetVal = null; 
        OAMessageDateFieldBean temporalBean = (OAMessageDateFieldBean)webBean.findChildRecursive(pStrIdBean); 
        if(null!=temporalBean){
          if(null!=temporalBean.getValue(pageContext)){
           datRetVal = (java.sql.Timestamp)temporalBean.getValue(pageContext);
          }
        }
        return datRetVal;
    }

    private void environmentCurrencyFormat(OAPageContext pageContext, 
                                           OAWebBean webBean) {
        OATableBean PrecioTmpBean = (OATableBean)webBean.findChildRecursive("PrecioTmp");
        if(null!=PrecioTmpBean){
          OAMessageTextInputBean PrecioUnitarioBean = (OAMessageTextInputBean)PrecioTmpBean.findChildRecursive("PrecioUnitario");
          OAMessageTextInputBean TotalPorConceptoBean = (OAMessageTextInputBean)PrecioTmpBean.findChildRecursive("TotalPorConcepto");  
          if(null!=PrecioUnitarioBean){
              PrecioUnitarioBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          if(null!=TotalPorConceptoBean){
              TotalPorConceptoBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
          
        }
        
        OAMessageTextInputBean SubtotalBean = (OAMessageTextInputBean)webBean.findChildRecursive("Subtotal");
        OAMessageTextInputBean IVABean = (OAMessageTextInputBean)webBean.findChildRecursive("IVA");
        OAMessageStyledTextBean TotalBean = (OAMessageStyledTextBean)webBean.findChildRecursive("Total");
        if(null!=SubtotalBean){
            SubtotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        if(null!=IVABean){
            IVABean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        if(null!=TotalBean){
            TotalBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
        }
        
        OATableBean ReglasDeNegocioVORNBean = (OATableBean)webBean.findChildRecursive("ReglasDeNegocioTmpVORN");
        if(null!=ReglasDeNegocioVORNBean){
          OAMessageTextInputBean PrecioRegNegBean = (OAMessageTextInputBean)ReglasDeNegocioVORNBean.findChildRecursive("PrecioRegNeg");
          if(null!=PrecioRegNegBean){
              PrecioRegNegBean.setAttributeValue(OAWebBeanConstants.CURRENCY_CODE,"USD");
          }
        }
        
    }
    
    private void environmentNoRequiredFields(OAPageContext pageContext, 
                                             OAWebBean webBean) {
       /** setNoRequiredInput("FechaInicioOperacion",webBean);  NA 25072018 **/
        
        OATableBean  PrecioTmpBean = (OATableBean)webBean.findChildRecursive("PrecioTmp");
        setNoRequiredInput("Concepto",PrecioTmpBean);
        setNoRequiredInput("Cantidad",PrecioTmpBean);
        setNoRequiredInput("PrecioUnitario",PrecioTmpBean);
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
    
    
    
}

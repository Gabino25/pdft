/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altafitec.qplabs.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OABodyBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageCheckBoxBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;

import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageRadioGroupBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.domain.BlobDomain;

import xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server.QpLabsAMImpl;

/**
 * Controller for ...
 */
public class QpLabsCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    OAWebBean body = pageContext.getRootWebBean(); 
     if (body instanceof OABodyBean){
        ((OABodyBean)body).setBlockOnEverySubmit(true); 
     }
    
     
        
    OAMessageFileUploadBean ContratoExamineBean = (OAMessageFileUploadBean)webBean.findChildRecursive("ContratoExamine");
    OAButtonBean grabarBean = (OAButtonBean)webBean.findChildRecursive("Grabar"); 
    OASubmitButtonBean ValidarBean = (OASubmitButtonBean)webBean.findChildRecursive("Validar");
    OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
       
    if(null!=NombreUsuarioEBSBean){
         NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
     }
       
    String strEnabledGrabar = pageContext.getParameter("pEnabledGrabar");  
      
    QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
    
      if(null!=ContratoExamineBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(ContratoExamineBean, "ContratoFileName"); 
          ContratoExamineBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }   
      
     
    
    if(!pageContext.isFormSubmission()){
        
        if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){
        if(null!=grabarBean){
            grabarBean.setDisabled(false);
            grabarBean.setOnClick("this.disabled=true;");
        }
        }
        else{
            if(null!=grabarBean){
                grabarBean.setDisabled(true);
            }
            
            qpLabsAMImpl.initQplabsHeaderTmpVO();
            
            String strPUnidadDeNegocio = pageContext.getParameter("pUnidadDeNegocio");
            String strPEmpresaQueFactura = pageContext.getParameter("pEmpresaQueFactura");
            String strpCicloFacturacion = pageContext.getParameter("pCicloFacturacion");
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
            
            
            if(null!=qpLabsAMImpl){
                qpLabsAMImpl.fillCamposHead(pageContext,webBean);
                qpLabsAMImpl.agregarPreciosClientesFila(); 
            } 
            
        } /** END if(null!=strEnabledGrabar&&"Y".equals(strEnabledGrabar)){ **/
        
        
        
    }else{
    
    } /** END if(!pageContext.isFormSubmission()){ **/
    
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
      QpLabsAMImpl qpLabsAMImpl = (QpLabsAMImpl)pageContext.getApplicationModule(webBean);
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
            ContratoExamineByteStream = (oracle.jbo.domain.BlobDomain)qpLabsAMImpl.getQplabsHeaderTmpVO1().getCurrentRow().getAttribute("ContratoFile");
        } /** END if(null==ContratoExamineByteStream){ **/
      } /** END if(null!=ContratoExamineUploadData){ **/
      
      OAMessageChoiceBean  EstatusBean = (OAMessageChoiceBean)webBean.findChildRecursive("Estatus");
      if(null!=EstatusBean){
       if(null!=EstatusBean.getValue(pageContext)){
        strEstatusValue = EstatusBean.getValue(pageContext).toString();
       }
      }
        
        
       strUnidadDeNegocioValue = getValueFromOAMessageChoiceBean(pageContext,webBean,"UnidadDeNegocio");
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
      
      /***************************************************************************
       ************** Informacion Operativa **************************************
       ***************************************************************************/
       String strTipoDeServicioValue = null; 
       String strNombreDeServicioValue = null; 
       String strVolumenAproximadoValue = null; 
       String strComentariosAdicionalesValue = null; 
       
       strTipoDeServicioValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"TipoDeServicio");
       strNombreDeServicioValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"NombreDeServicio");
       strVolumenAproximadoValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"VolumenAproximado");
       strComentariosAdicionalesValue = this.getValueFromOAMessageTextInputBean(pageContext,webBean,"ComentariosAdicionales");
      
        
        
      if(null!=pageContext.getParameter("Validar")){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pEnabledGrabar","Y");
          pageContext.forwardImmediatelyToCurrentPage(parameters
                                                     ,true
                                                     ,null);
      }  
      
      /***************************************************************************
       ********* Almacenar Informacion *******************************************
       ***************************************************************************/
       
       if("GrabarEvt".equals(strEventParam)){
        synchronized(this){
         if(null!=qpLabsAMImpl){
             StringBuilder strQpLabsHeaderId = new StringBuilder("");
                        qpLabsAMImpl.fillHeader( strQpLabsHeaderId
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
                                             );
                        qpLabsAMImpl.fillInformacionOperativa(strQpLabsHeaderId
                                                            , strTipoDeServicioValue 
                                                            , strNombreDeServicioValue 
                                                            , strVolumenAproximadoValue 
                                                            , strComentariosAdicionalesValue 
                                                             ); 
                        qpLabsAMImpl.fillPreciosClientes(strQpLabsHeaderId);                                      
                        
             com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
             parameters.put("pQplabsHeaderId",strQpLabsHeaderId.toString() );
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
        } /** END synchronized(this){ **/
       } /** END if("GrabarEvt".equals(strEventParam)){ **/
       
        if("AgregarOtraFilaEvt".equals(strEventParam)){
        
            if(null!=qpLabsAMImpl){
                qpLabsAMImpl.agregarPreciosClientesFila(); 
            }
        
        } /** END if("AgregarOtraFilaEvt".equals(strEventParam)){ **/
        
  } /** END public void processFormRequest(OAPageContext pageContext, OAWebBean webBean) **/
  
  
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

}

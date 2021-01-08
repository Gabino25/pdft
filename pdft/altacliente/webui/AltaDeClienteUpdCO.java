/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altacliente.webui;

import java.sql.SQLException;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;

import xxqp.oracle.apps.ar.pdft.altacliente.server.AltaDeClienteAMImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesDirFiscalVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesDirFiscalVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesFactPagVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesFactPagVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesHeaderVORowImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesPuntoRecoVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.XxqpPdftClientesPuntoRecoVORowImpl;

/**
 * Controller for ...
 */
public class AltaDeClienteUpdCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   private int gIntSelectedIndex = 0; 
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAMessageFileUploadBean CedulaUploadPBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaUploadP");
      OAMessageFileUploadBean CedulaUploadSBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaUploadS");
      OASubTabLayoutBean SubTabLayoutRNBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayoutRN");
    
      AltaDeClienteAMImpl altaDeClienteAMImpl = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
     
      if(null!=NombreUsuarioEBSBean){
          NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
      }
      if(null!=CedulaUploadPBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(CedulaUploadPBean, "PrimCedulaFileName"); 
          CedulaUploadPBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
      if(null!=CedulaUploadSBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(CedulaUploadSBean, "SecCedulaFileName"); 
          CedulaUploadSBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }
    
      if(null!=SubTabLayoutRNBean){
        gIntSelectedIndex =  SubTabLayoutRNBean.getSelectedIndex(pageContext);
        System.out.println("Process Request gIntSelectedIndex:"+gIntSelectedIndex);
      }  
      
      
      String strClientesHeaderId =  pageContext.getParameter("pClientesHeaderId");  
        
      XxqpPdftClientesHeaderVORowImpl xxqpPdftClientesHeaderVORowImpl = null; 
      if(!pageContext.isFormSubmission()){
          xxqpPdftClientesHeaderVORowImpl = altaDeClienteAMImpl.initClientesHeaderReOnVO(strClientesHeaderId);
          altaDeClienteAMImpl.initClientesDirFiscalReOnVO(strClientesHeaderId);
          altaDeClienteAMImpl.initClientesPuntoRecolecReOnVO(strClientesHeaderId);
          altaDeClienteAMImpl.initClientesContactosReOnVO(strClientesHeaderId);
          altaDeClienteAMImpl.initClientesFactPagoReOnVO(strClientesHeaderId);
          String strEmpresaQueFacturaValue = null; 
          strEmpresaQueFacturaValue = ""+xxqpPdftClientesHeaderVORowImpl.getEmpresaQueFacturaC();  
          System.out.println("strEmpresaQueFacturaValue:"+strEmpresaQueFacturaValue);
          altaDeClienteAMImpl.initLegalEntityVO(strEmpresaQueFacturaValue);
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
    AltaDeClienteAMImpl altaDeClienteAMImpl = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
    String strEventParam = pageContext.getParameter(this.EVENT_PARAM);
    
    OAMessageFileUploadBean CedulaUploadPBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaUploadP");
    OAMessageFileUploadBean CedulaUploadSBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaUploadS");
    DataObject CedulaUploadPUploadData =  pageContext.getNamedDataObject("CedulaUploadP"); 
    DataObject CedulaUploadSUploadData =  pageContext.getNamedDataObject("CedulaUploadS"); 
      
    String StrFileMimeTypeP  = null; 
    String StrFileMimeTypeS  = null; 
      
      if(null!=CedulaUploadPUploadData){
          StrFileMimeTypeP = (String)CedulaUploadPUploadData.selectValue(pageContext.getRenderingContext(),"UPLOAD_FILE_MIME_TYPE");
      }
       
      
      
      if(null!=CedulaUploadSUploadData){
          StrFileMimeTypeS = (String)CedulaUploadSUploadData.selectValue(pageContext.getRenderingContext(),"UPLOAD_FILE_MIME_TYPE");
      }
      System.out.println("StrFileMimeTypeP:"+StrFileMimeTypeP);
      System.out.println("StrFileMimeTypeS:"+StrFileMimeTypeS);
      
        
    String strCedulaUploadPContentType = null; 
    String strCedulaUploadSContentType = null; 
    if(null!=CedulaUploadPBean){
        strCedulaUploadPContentType = CedulaUploadPBean.getFileContentType();
    }
      if(null!=CedulaUploadSBean){
          strCedulaUploadSContentType = CedulaUploadSBean.getFileContentType();  
      } 
    
    System.out.println("strCedulaUploadPContentType:"+strCedulaUploadPContentType);
    System.out.println("strCedulaUploadSContentType:"+strCedulaUploadSContentType);
    
    System.out.println("Process Form Request gIntSelectedIndex:"+gIntSelectedIndex);
    
     XxqpPdftClientesFactPagVOImpl xxqpPdftClientesFactPagVOImpl = null; 
     XxqpPdftClientesFactPagVORowImpl xxqpPdftClientesFactPagVORowImpl = null; 
     xxqpPdftClientesFactPagVOImpl = altaDeClienteAMImpl.getXxqpPdftClientesFactPagVO1();
      
     RowSetIterator FactPagoIterator = xxqpPdftClientesFactPagVOImpl.createRowSetIterator(null); 
     if(FactPagoIterator.hasNext()){
        xxqpPdftClientesFactPagVORowImpl = (XxqpPdftClientesFactPagVORowImpl)FactPagoIterator.next();
     } 
      FactPagoIterator.closeRowSetIterator();
      
      String strCondicionesDePagoValue = xxqpPdftClientesFactPagVORowImpl.getCondicionesDePagoC();
      String strObservaciones = xxqpPdftClientesFactPagVORowImpl.getObservaciones();
      String strRequiereFacturaValue = xxqpPdftClientesFactPagVORowImpl.getRequiereFacturaC();
      String strMotivo = xxqpPdftClientesFactPagVORowImpl.getMotivo();
      String strContratoValue = xxqpPdftClientesFactPagVORowImpl.getContratoC();
      String strVigenciaDeContrato = xxqpPdftClientesFactPagVORowImpl.getVigenciaContrato();
      String strDiasNatDeCredito = xxqpPdftClientesFactPagVORowImpl.getDiasNatDeCredito();
      String strUtilizaPortalC = xxqpPdftClientesFactPagVORowImpl.getUtilizaPortalC();
      String strPortalLink = xxqpPdftClientesFactPagVORowImpl.getPortalLink();
      
      if(1==gIntSelectedIndex){
        XxqpPdftClientesPuntoRecoVOImpl xxqpPdftClientesPuntoRecoVOImpl = altaDeClienteAMImpl.getXxqpPdftClientesPuntoRecoVO1();
        XxqpPdftClientesPuntoRecoVORowImpl  xxqpPdftClientesPuntoRecoVORowImpl = null; 
        RowSetIterator iterator = xxqpPdftClientesPuntoRecoVOImpl.createRowSetIterator(null);
        if(iterator.hasNext()){
           xxqpPdftClientesPuntoRecoVORowImpl = (XxqpPdftClientesPuntoRecoVORowImpl)iterator.next();
        }
        iterator.closeRowSetIterator();
        if("N".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimEntregaEnQp())){
            com.sun.java.util.collections.List listExcpt = new com.sun.java.util.collections.ArrayList();
            if(null==xxqpPdftClientesPuntoRecoVORowImpl.getPrimNombre()||"".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimNombre())){
                listExcpt.add(new OAException("Campo Nombre es obligatorio.",OAException.ERROR));
            }
            /*************************************************************************************
             19052018 se pidio ocultar el campo 
            if(null==xxqpPdftClientesPuntoRecoVORowImpl.getPrimContacto()||"".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimContacto())){
                listExcpt.add(new OAException("Campo Contacto es obligatorio.",OAException.ERROR));
            }
            *************************************************************************************/
            if(null==xxqpPdftClientesPuntoRecoVORowImpl.getPrimDireccion()||"".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimDireccion())){
                listExcpt.add(new OAException("Campo Direccion es obligatorio.",OAException.ERROR));
            }
            if(null==xxqpPdftClientesPuntoRecoVORowImpl.getPrimDia()||"".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimDia())){
                listExcpt.add(new OAException("Campo Dia es obligatorio.",OAException.ERROR));
            }
            if(null==xxqpPdftClientesPuntoRecoVORowImpl.getPrimHorario()||"".equals(xxqpPdftClientesPuntoRecoVORowImpl.getPrimHorario())){
                listExcpt.add(new OAException("Campo Hora es obligatorio.",OAException.ERROR));
            }
            if(listExcpt.size()>0){
            OAException.raiseBundledOAException(listExcpt);
            }  
        }
      }
      
      if(3==gIntSelectedIndex){
          com.sun.java.util.collections.List  listExceptions = new com.sun.java.util.collections.ArrayList();
          
        if("CONTADO".equals(strCondicionesDePagoValue)){
          if(null==strObservaciones||"".equals(strObservaciones)){
            listExceptions.add(new OAException("Si se selecciona Contado, el campo Observaciones es obligatorio.",OAException.ERROR));
          }
        }else if("CREDITO".equals(strCondicionesDePagoValue)){
          System.out.println("Si se selecciona Credito, el campo Observaciones es opcional.");
          if(null==strDiasNatDeCredito||"".equals(strDiasNatDeCredito)){
              listExceptions.add(new OAException("Si se selecciona Credito, el campo Dias Naturales de Credito es obligatorio.",OAException.ERROR));
          }
        }
        
          if("Y".equals(strRequiereFacturaValue)){
             System.out.println("Si se Requiere Factura, el campo Motivo es opcional.");
          }else if("N".equals(strRequiereFacturaValue)){
              if(null==strMotivo||"".equals(strMotivo)){
                  listExceptions.add(new OAException("Si no se Requiere Factura, el campo Motivo es obligatorio.",OAException.ERROR));
              }
          }
          
          if("Y".equals(strContratoValue)){
           if(null==strVigenciaDeContrato||"".equals(strVigenciaDeContrato)){
               listExceptions.add(new OAException("Si se Requiere Contrato, el campo Vigencia de Contrato es obligatorio.",OAException.ERROR));
           }
          }else if("N".equals(strContratoValue)){
              System.out.println("Si no se Requiere Contrato, el campo Vigencia de Contrato es opcional.");
          } 
          
          if("Y".equals(strUtilizaPortalC)){
           if(null==strPortalLink||"".equals(strPortalLink)){
               listExceptions.add(new OAException("Si se Utiliza Portal, el campo Portal de Facturas (Link) es obligatorio.",OAException.ERROR));
           }
          }else if("N".equals(strUtilizaPortalC)){
              System.out.println("Si no se Utiliza Portal, el campo Portal de Facturas (Link) es opcional.");
          } 
          
           if(listExceptions.size()>0){
            OAException.raiseBundledOAException(listExceptions);
           }
      } /** END if(3==gIntSelectedIndex){**/
      
    
    
    if(null!=altaDeClienteAMImpl){
        com.sun.java.util.collections.List listValidaClientesContactosVO = new com.sun.java.util.collections.ArrayList(); 
        if(2==gIntSelectedIndex){
        listValidaClientesContactosVO = altaDeClienteAMImpl.validaClientesContactosVO();
         if(listValidaClientesContactosVO.size()>0){
            OAException.raiseBundledOAException(listValidaClientesContactosVO);
         }
        } /** END  if(2==gIntSelectedIndex){ **/
    }
    
     String strEmpresaQueFacturaValue = null; 
     String strEmpresaQueFacturaText = null;
     
         OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
         if(null!=EmpresaQueFacturaBean){
          if(null!=EmpresaQueFacturaBean.getValue(pageContext)){
          strEmpresaQueFacturaValue = EmpresaQueFacturaBean.getValue(pageContext).toString(); 
          strEmpresaQueFacturaText = EmpresaQueFacturaBean.getSelectionText(pageContext).toString();
          }
         }
         
    
         if("LegalEntityEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){
            OAMessageChoiceBean OperatingUnitPBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitP");
             if(null!=OperatingUnitPBean){
               OperatingUnitPBean.setValue(pageContext,null);
              }
             OAMessageChoiceBean OperatingUnitSBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitS");
              if(null!=OperatingUnitSBean){
                OperatingUnitSBean.setValue(pageContext,null);
               } 
             altaDeClienteAMImpl.initLegalEntityVO(strEmpresaQueFacturaValue);
             
          return;
         }
         
    if("GrabarEvt".equals(strEventParam)){
            XxqpPdftClientesDirFiscalVOImpl xqpPdftClientesDirFiscalVOImpl =  altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1(); 
            XxqpPdftClientesDirFiscalVORowImpl xqpPdftClientesDirFiscalVORowImpl = null; 
            
            RowSetIterator ClientesDFIterator = xqpPdftClientesDirFiscalVOImpl.createRowSetIterator(null);
            if(ClientesDFIterator.hasNext()){
                xqpPdftClientesDirFiscalVORowImpl = (XxqpPdftClientesDirFiscalVORowImpl)ClientesDFIterator.next();
            }
            ClientesDFIterator.closeRowSetIterator();
            
            if(null==xqpPdftClientesDirFiscalVORowImpl){
              throw new OAException("No se pudo recuperar el XxqpPdftClientesDirFiscalVORow.",OAException.ERROR); 
            }
        
            if(null!=StrFileMimeTypeP&&!"".equals(StrFileMimeTypeP)){
            if(null!=altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1().getCurrentRow()){
            altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1().getCurrentRow().setAttribute("PrimCedulaContentType",StrFileMimeTypeP);
            }else{
                xqpPdftClientesDirFiscalVORowImpl.setPrimCedulaContentType(StrFileMimeTypeP);
            }
            }
            if(null!=StrFileMimeTypeS&&!"".equals(StrFileMimeTypeS)){
            if(null!=altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1().getCurrentRow()){
            altaDeClienteAMImpl.getXxqpPdftClientesDirFiscalVO1().getCurrentRow().setAttribute("SecCedulaContentType",StrFileMimeTypeS);
            }else{
                xqpPdftClientesDirFiscalVORowImpl.setSecCedulaContentType(StrFileMimeTypeS);
            }
            }
        
            altaDeClienteAMImpl.getOADBTransaction().commit();
            
            oracle.jbo.domain.Number numClienteHeaderId = (oracle.jbo.domain.Number)altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1().getCurrentRow().getAttribute("Id");
            oracle.jbo.domain.Number numPartyId = null;
            try {
                numPartyId = new oracle.jbo.domain.Number(altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1().getCurrentRow().getAttribute("Attribute2"));
            } catch (SQLException sqle) {
                throw new OAException("SQLException  al obtener PartyId desde xxqp_pdft_clientes_header:"+sqle.getMessage(),OAException.ERROR); 
            }
            altaDeClienteAMImpl.callUpdFromPdftToOracle(numPartyId,numClienteHeaderId); 
            
            com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
            parameters.put("pClientesHeaderId",numClienteHeaderId.toString() );
            pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                      ,null /*functionName*/
                                      ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                      ,null /*menuName*/
                                      ,parameters /*parameters*/
                                      ,false /*retainAM*/
                                      ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                      ,OAException.ERROR /*messagingLevel*/
                                      ); 
           return;
    } /** END  if("GrabarEvt".equals(strEventParam)){ **/
    
      if("RegresarPaginaAnteriorEvt".equals(strEventParam)){
          OAException descMesg = new OAException("Mensaje de confirmacion.");
          OAException instrMesg = new OAException("¿Desea salir sin grabar los cambios?");
          OADialogPage dialogPage =  new OADialogPage(OAException.WARNING, descMesg, instrMesg, "", "");
          dialogPage.setOkButtonToPost(true);
          dialogPage.setNoButtonToPost(true);
          dialogPage.setPostToCallingPage(true);

              // here we are setting names of buttons of dialog page 
           dialogPage.setOkButtonItemName("CancelYes");
           dialogPage.setNoButtonItemName("CancelNo");
              
           pageContext.redirectToDialogPage(dialogPage);
                          
      } /** END  if("RegresarPaginaAnteriorEvt".equals(strEventParam)){ **/
      
      if(null!=pageContext.getParameter("CancelYes")){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap(); 
          oracle.jbo.domain.Number numClienteHeaderId = (oracle.jbo.domain.Number)altaDeClienteAMImpl.getXxqpPdftClientesHeaderVO1().getCurrentRow().getAttribute("Id");
          parameters.put("pClientesHeaderId",numClienteHeaderId.toString());
          altaDeClienteAMImpl.getOADBTransaction().rollback();
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                    ,null /*functionName*/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                    ,null /*menuName*/
                                    ,parameters /*parameters*/
                                    ,false /*retainAM*/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                    ,OAException.ERROR /*messagingLevel*/
                                    ); 
            return;                        
      }
     if(null!=pageContext.getParameter("CancelNo")){
      System.out.println("Permanecer en la pagina.");
     }
  
  }
  

}

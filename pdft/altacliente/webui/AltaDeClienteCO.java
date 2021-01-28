/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package xxqp.oracle.apps.ar.pdft.altacliente.webui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OANLSServices;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADataBoundValueViewObject;
import oracle.apps.fnd.framework.webui.OADateValidater;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OABodyBean;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;

import oracle.apps.fnd.framework.webui.beans.form.OAFormValueBean;
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.layout.OASubTabLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageCheckBoxBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageDateFieldBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageFileUploadBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageRadioGroupBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;

import oracle.apps.fnd.framework.webui.beans.nav.OAButtonBean;

import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;

import oracle.cabo.style.CSSStyle;
import oracle.cabo.ui.beans.layout.PageLayoutBean;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.BlobDomain;

import xxqp.oracle.apps.ar.pdft.altacliente.server.AltaDeClienteAMImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.CedulasTmpVOImpl;
import xxqp.oracle.apps.ar.pdft.altacliente.server.CedulasTmpVORowImpl;

/**
 * Controller for ...
 */
public class AltaDeClienteCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

    private String gStrOaSelectedSubtabIdx0 = "N"; 
    private String gStrOaSelectedSubtabIdx1 = "N"; 
    private String gStrOaSelectedSubtabIdx2 = "N"; 
    private String gStrOaSelectedSubtabIdx3 = "N"; 
    private int gIntSelectedIndex  = -1; 
    
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
        
      OAMessageStyledTextBean NombreUsuarioEBSBean = (OAMessageStyledTextBean)webBean.findChildRecursive("NombreUsuarioEBS");
      OAMessageChoiceBean  EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
      OAMessageFileUploadBean CedulaExaminePBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaExamineP");
      OAMessageFileUploadBean CedulaExamineSBean = (OAMessageFileUploadBean)webBean.findChildRecursive("CedulaExamineS");
      OAButtonBean GrabarButtonBean = (OAButtonBean)webBean.findChildRecursive("GrabarButton"); 
      OASubmitButtonBean ValidarBean = (OASubmitButtonBean)webBean.findChildRecursive("Validar");
      OAFormValueBean FvOaSelectedSubtabIdx0Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx0");
      OAFormValueBean FvOaSelectedSubtabIdx1Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx1");
      OAFormValueBean FvOaSelectedSubtabIdx2Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx2");
      OAFormValueBean FvOaSelectedSubtabIdx3Bean = (OAFormValueBean)webBean.findChildRecursive("FvOaSelectedSubtabIdx3");
      OASubTabLayoutBean SubTabLayoutBean = (OASubTabLayoutBean)webBean.findChildRecursive("SubTabLayout");
      OAAdvancedTableBean advContactosTmpBean =  (OAAdvancedTableBean)webBean.findChildRecursive("AdvContactosTmp");
      if(null!=advContactosTmpBean){
         OAMessageTextInputBean CumpleaniosBean =   (OAMessageTextInputBean)advContactosTmpBean.findChildRecursive("Cumpleanios");
         System.out.println(CumpleaniosBean);
          CSSStyle cssDDMM = new CSSStyle();
          //cssDDMM.setProperty("background-color","blue");
          CumpleaniosBean.setInlineStyle(cssDDMM);
          OANLSServices nls = pageContext.getOANLSServices();
          /*oracle.cabo.ui.validate.Formatter formatter = new OADateValidater(nls.getUserJavaDateFormat() + " HH:mm",
                                  nls.getUserRRRRJavaDateFormat() + " HH:mm");*/
           oracle.cabo.ui.validate.Formatter formatter = new OADateValidater("dd-MMMMM","dd-MMMMM");                       
           System.out.println(nls.getUserJavaDateFormat() + " HH:mm");  
          System.out.println(nls.getUserJavaDateFormat() + " HH:mm");  
          CumpleaniosBean.setAttributeValue(ON_SUBMIT_VALIDATER_ATTR, formatter);
      }
      
      if(null!=SubTabLayoutBean){
        gIntSelectedIndex =  SubTabLayoutBean.getSelectedIndex(pageContext);
        System.out.println("gIntSelectedIndex:"+gIntSelectedIndex);
      }
      
      String strOaSelectedSubtab =  pageContext.getParameter(OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX);
      System.out.println("strOaSelectedSubtab:"+strOaSelectedSubtab);
      if(null==strOaSelectedSubtab){
         if(-1!=gIntSelectedIndex){
          strOaSelectedSubtab = ""+gIntSelectedIndex;
         }
      }
      
      String strpEnabledGrabar = pageContext.getParameter("pEnabledGrabar");
      
      /*** 210120211558
      if(null!=NombreUsuarioEBSBean){
          NombreUsuarioEBSBean.setValue(pageContext,pageContext.getUserName());
      }
      **/
      
       String strPuserPdft = null; 
       System.out.println("AltaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
       if(null!=pageContext.getTransientSessionValue("tsUserPdft")){
           strPuserPdft = pageContext.getTransientSessionValue("tsUserPdft").toString();
           System.out.println("AltaFichaTecnicaCO strPuserPdft:"+strPuserPdft);
           if(null!=NombreUsuarioEBSBean){
                NombreUsuarioEBSBean.setValue(pageContext,strPuserPdft);
            }
           
       }
       
       String strPuserPdftId = null; 
       if(null!=pageContext.getTransientSessionValue("tsUserPdftId")){
           strPuserPdftId = pageContext.getTransientSessionValue("tsUserPdftId").toString();
           System.out.println("AltaFichaTecnicaCO strPuserPdftId:"+strPuserPdftId);
           if(null!=EjecutivoBean){
                 EjecutivoBean.setValue(pageContext,strPuserPdftId);
                 EjecutivoBean.setReadOnly(true);
           }
       }
      
      if(null!=CedulaExaminePBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(CedulaExaminePBean, "PcedulaFileName"); 
          CedulaExaminePBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }   
      if(null!=CedulaExamineSBean){
          OADataBoundValueViewObject displayNameBoundValue =    new OADataBoundValueViewObject(CedulaExamineSBean, "ScedulaFileName"); 
          CedulaExamineSBean.setAttributeValue(DOWNLOAD_FILE_NAME,displayNameBoundValue); 
      }   
      
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
      
      AltaDeClienteAMImpl altaDeClienteAM = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
      System.out.println("gStrOaSelectedSubtabIdx0:"+gStrOaSelectedSubtabIdx0);
      System.out.println("gStrOaSelectedSubtabIdx1:"+gStrOaSelectedSubtabIdx1);
      System.out.println("gStrOaSelectedSubtabIdx2:"+gStrOaSelectedSubtabIdx2);
      System.out.println("gStrOaSelectedSubtabIdx3:"+gStrOaSelectedSubtabIdx3);
      
      altaDeClienteAM.getDiasDeSemanaVO1().executeQuery();
      
     if(!pageContext.isFormSubmission()){
         
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
          } /** END          if("Y".equals(gStrOaSelectedSubtabIdx1)&&"Y".equals(gStrOaSelectedSubtabIdx2)&&"Y".equals(gStrOaSelectedSubtabIdx3)**/
          
          
          
          if(null!=strpEnabledGrabar&&"Y".equals(strpEnabledGrabar)){
             if(null!=GrabarButtonBean){
                 GrabarButtonBean.setDisabled(false);
                 GrabarButtonBean.setOnClick("this.disabled=true;");
             }
          }else{
              if(null!=GrabarButtonBean){
                  GrabarButtonBean.setDisabled(true);
              }
          } /** END if(null!=strpEnabledGrabar&&"Y".equals(strpEnabledGrabar)){ **/
         
         if(null!=altaDeClienteAM){
             altaDeClienteAM.initContactosTmpVO(); 
             altaDeClienteAM.initCedulasTmpVO();
         }
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
    
    System.out.println("gIntSelectedIndex:"+gIntSelectedIndex);
      
    AltaDeClienteAMImpl altaDeClienteAM = (AltaDeClienteAMImpl)pageContext.getApplicationModule(webBean);
    
    String strNombreCLiente = null; 
    String strGiroEmpresarialValue = null; 
    String strGiroEmpresarialText= null; 
    
    String strEmpresaQueFacturaValue = null; 
    String strEmpresaQueFacturaText = null; 
    
    String strComentarios = null; 
    String strRFC = null; 
    String strRazonSocial = null; 
    
    String strTipoOperativoValue = null; 
    String strTipoOperativoText = null;
    
    String strTipoAdministrativoValue = null; 
    String strTipoAdministrativoText = null;
    
    String strTipoComercialValue = null; 
    String strTipoComercialText = null; 
    String strEjecutivoValue = null; 
    String strEjecutivoText = null; 
    
    OAMessageTextInputBean NombreClienteBean = (OAMessageTextInputBean)webBean.findChildRecursive("NombreCliente"); 
    if(null!=NombreClienteBean){
     if(null!=NombreClienteBean.getValue(pageContext)){
     strNombreCLiente = NombreClienteBean.getValue(pageContext).toString(); 
     }
    }
    
    OAMessageChoiceBean GiroEmpresarialBean = (OAMessageChoiceBean)webBean.findChildRecursive("GiroEmpresarial");
    if(null!=GiroEmpresarialBean){
     if(null!=GiroEmpresarialBean.getValue(pageContext)){
     strGiroEmpresarialValue = GiroEmpresarialBean.getValue(pageContext).toString(); 
     strGiroEmpresarialText = GiroEmpresarialBean.getSelectionText(pageContext).toString();
     }
    }
    
    OAMessageChoiceBean EmpresaQueFacturaBean = (OAMessageChoiceBean)webBean.findChildRecursive("EmpresaQueFactura");
    if(null!=EmpresaQueFacturaBean){
     if(null!=EmpresaQueFacturaBean.getValue(pageContext)){
     strEmpresaQueFacturaValue = EmpresaQueFacturaBean.getValue(pageContext).toString(); 
     strEmpresaQueFacturaText = EmpresaQueFacturaBean.getSelectionText(pageContext).toString();
     }
    }
    
    
    OAMessageTextInputBean ComentariosBean = (OAMessageTextInputBean)webBean.findChildRecursive("Comentarios"); 
    if(null!=ComentariosBean){
     if(null!=ComentariosBean.getValue(pageContext)){
     strComentarios = ComentariosBean.getValue(pageContext).toString();
     }
    }
    
      OAMessageTextInputBean RFCBean = (OAMessageTextInputBean)webBean.findChildRecursive("RFC"); 
      if(null!=RFCBean){
       if(null!=RFCBean.getValue(pageContext)){
       strRFC = RFCBean.getValue(pageContext).toString(); 
       }
      }
      
      OAMessageTextInputBean RazonSocialBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonSocial"); 
      if(null!=RazonSocialBean){
       if(null!=RazonSocialBean.getValue(pageContext)){
       strRazonSocial = RazonSocialBean.getValue(pageContext).toString();
       }
      }  
    
    OAMessageChoiceBean TipoOperativoBean = (OAMessageChoiceBean)webBean.findChildRecursive("TipoOperativo");
    if(null!=TipoOperativoBean){
     if(null!=TipoOperativoBean.getValue(pageContext)){
     strTipoOperativoValue = TipoOperativoBean.getValue(pageContext).toString(); 
     strTipoOperativoText = TipoOperativoBean.getSelectionText(pageContext).toString();
     }
    }
    
      
      
    OAMessageChoiceBean TipoAdministrativoBean = (OAMessageChoiceBean)webBean.findChildRecursive("TipoAdministrativo");
    if(null!=TipoAdministrativoBean){
     if(null!= TipoAdministrativoBean.getValue(pageContext)){
     strTipoAdministrativoValue = TipoAdministrativoBean.getValue(pageContext).toString(); 
     strTipoAdministrativoText = TipoAdministrativoBean.getSelectionText(pageContext).toString();
     }
    }
    
      
    OAMessageChoiceBean TipoComercialBean = (OAMessageChoiceBean)webBean.findChildRecursive("TipoComercial");
    if(null!=TipoComercialBean){
     if(null!=TipoComercialBean.getValue(pageContext)){
     strTipoComercialValue = TipoComercialBean.getValue(pageContext).toString(); 
     strTipoComercialText = TipoComercialBean.getSelectionText(pageContext).toString();
     }
    } 
    
      OAMessageChoiceBean  EjecutivoBean = (OAMessageChoiceBean)webBean.findChildRecursive("Ejecutivo");
      if(null!=EjecutivoBean){
       if(null!=EjecutivoBean.getValue(pageContext)){
        strEjecutivoValue = EjecutivoBean.getValue(pageContext).toString();
        strEjecutivoText = EjecutivoBean.getSelectionText(pageContext).toString();
       }
      } 
      
    System.out.println("strNombreCLiente:"+strNombreCLiente);
    
    System.out.println("strGiroEmpresarialValue:"+strGiroEmpresarialValue);
    System.out.println("strGiroEmpresarialText:"+strGiroEmpresarialText);
    
    System.out.println("strEmpresaQueFacturaValue:"+strEmpresaQueFacturaValue);
    System.out.println("strEmpresaQueFacturaText:"+strEmpresaQueFacturaText);
    
    System.out.println("strComentarios:"+strComentarios);
    System.out.println("strRFC:"+strRFC);
    System.out.println("strRazonSocial:"+strRazonSocial);
      
    System.out.println("strTipoOperativoValue:"+strTipoOperativoValue);
    System.out.println("strTipoOperativoText:"+strTipoOperativoText);
     
    System.out.println("strTipoAdministrativoValue:"+strTipoAdministrativoValue);
    System.out.println("strTipoAdministrativoText:"+strTipoAdministrativoText);
     
    System.out.println("strTipoComercialValue:"+strTipoComercialValue);
    System.out.println("strTipoComercialText:"+strTipoComercialText);

     if("LegalEntityEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){
        OAMessageChoiceBean OperatingUnitPBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitP");
         if(null!=OperatingUnitPBean){
           OperatingUnitPBean.setValue(pageContext,null);
          }
         OAMessageChoiceBean OperatingUnitSBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitS");
          if(null!=OperatingUnitSBean){
            OperatingUnitSBean.setValue(pageContext,null);
           } 
         altaDeClienteAM.initLegalEntityVO(strEmpresaQueFacturaValue);
         
      return;
     }
    /****************************************************************************
     ******************** Direccion Fiscal **************************************
     ****************************************************************************/
     
     String strRFCP = null; 
     String strRazonSocialP = null; 
     String strDireccionP = null; 
     String strEntreCallesP = null; 
     String strColoniaP = null; 
     String strCiudadOMunicipioP = null; 
     String strEstadoPValue = null; 
     String strEstadoPText = null;
     String strCPPValue = null; 
     String strCPPText = null;
     String strCedulaP = null; 
     String strNumeroExtP = null; 
     String strNumeroIntP = null; 
     
    String strRFCS = null; 
    String strRazonSocialS = null; 
    String strDireccionS = null; 
    String strEntreCallesS = null; 
    String strColoniaS = null; 
    String strCiudadOMunicipioS = null; 
    String strEstadoSValue = null; 
    String strEstadoSText = null;
    String strCPSValue = null; 
    String strCPSText = null;
    String strCedulaS = null; 
    String strNumeroExtS = null; 
    String strNumeroIntS = null; 
      
    String strOperatingUnitPValue = null; 
    String strOperatingUnitSValue = null; 
    
      OAMessageChoiceBean OperatingUnitPBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitP");
      if(null!=OperatingUnitPBean){
       if(null!=OperatingUnitPBean.getValue(pageContext)){
           strOperatingUnitPValue = OperatingUnitPBean.getValue(pageContext).toString();
       }
      }
      
      OAMessageChoiceBean OperatingUnitSBean = (OAMessageChoiceBean)webBean.findChildRecursive("OperatingUnitS");
      if(null!=OperatingUnitSBean){
       if(null!=OperatingUnitSBean.getValue(pageContext)){
           strOperatingUnitSValue = OperatingUnitSBean.getValue(pageContext).toString();
       }
      }
      
     OAMessageTextInputBean RFCPBean = (OAMessageTextInputBean)webBean.findChildRecursive("RFCP"); 
     if(null!=RFCPBean){
      if(null!=RFCPBean.getValue(pageContext)){
          strRFCP = RFCPBean.getValue(pageContext).toString();
      }
     }
     
     OAMessageTextInputBean RazonSocialPBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonSocialP"); 
     if(null!=RazonSocialPBean){
         if(null!=RazonSocialPBean.getValue(pageContext)){
             strRazonSocialP = RazonSocialPBean.getValue(pageContext).toString();
         }
     }
     
     OAMessageTextInputBean DireccionPBean = (OAMessageTextInputBean)webBean.findChildRecursive("DireccionP"); 
     if(null!=DireccionPBean){
         if(null!=DireccionPBean.getValue(pageContext)){
             strDireccionP = DireccionPBean.getValue(pageContext).toString();
         }
     }
     
      OAMessageTextInputBean NumeroExtPBean = (OAMessageTextInputBean)webBean.findChildRecursive("NumeroExtP"); 
      if(null!=NumeroExtPBean){
          if(null!=NumeroExtPBean.getValue(pageContext)){
              strNumeroExtP = NumeroExtPBean.getValue(pageContext).toString();
          }
      }
      
      OAMessageTextInputBean NumeroIntPBean = (OAMessageTextInputBean)webBean.findChildRecursive("NumeroIntP"); 
      if(null!=NumeroIntPBean){
          if(null!=NumeroIntPBean.getValue(pageContext)){
              strNumeroIntP = NumeroIntPBean.getValue(pageContext).toString();
          }
      }
      
     OAMessageTextInputBean EntreCallesPBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntreCallesP"); 
     if(null!=EntreCallesPBean){
         if(null!=EntreCallesPBean.getValue(pageContext)){
             strEntreCallesP = EntreCallesPBean.getValue(pageContext).toString();
         }
     }
     
     OAMessageTextInputBean ColoniaPBean = (OAMessageTextInputBean)webBean.findChildRecursive("ColoniaP"); 
     if(null!=ColoniaPBean){
         if(null!=ColoniaPBean.getValue(pageContext)){
             strColoniaP = ColoniaPBean.getValue(pageContext).toString();
         }
     }
     
     OAMessageTextInputBean CiudadOMunicipioPBean = (OAMessageTextInputBean)webBean.findChildRecursive("CiudadOMunicipioP"); 
     if(null!=CiudadOMunicipioPBean){
         if(null!=CiudadOMunicipioPBean.getValue(pageContext)){
             strCiudadOMunicipioP = CiudadOMunicipioPBean.getValue(pageContext).toString();
         }
     }     
     
    
    OAMessageChoiceBean EstadoPBean = (OAMessageChoiceBean)webBean.findChildRecursive("EstadoP");
    if(null!=EstadoPBean){
     if(null!=EstadoPBean.getValue(pageContext)){
     strEstadoPValue = EstadoPBean.getValue(pageContext).toString(); 
     strEstadoPText = EstadoPBean.getSelectionText(pageContext).toString();
     }
    }      
    
    
    
    OAMessageTextInputBean CPPBean = (OAMessageTextInputBean)webBean.findChildRecursive("CPP");
    if(null!=CPPBean){
     if(null!=CPPBean.getValue(pageContext)){
     strCPPValue = CPPBean.getValue(pageContext).toString();  
     }
    }      
   
    OAMessageCheckBoxBean CedulaPBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("CedulaP");
    if(null!=CedulaPBean){
        if(null!=CedulaPBean.getValue(pageContext)){
         strCedulaP = CedulaPBean.getValue(pageContext).toString();
        }
    }
    
    
    OAMessageTextInputBean RFCSBean = (OAMessageTextInputBean)webBean.findChildRecursive("RFCS"); 
    if(null!=RFCSBean){
     if(null!=RFCSBean.getValue(pageContext)){
         strRFCS = RFCSBean.getValue(pageContext).toString();
     }
    }
    
    OAMessageTextInputBean RazonSocialSBean = (OAMessageTextInputBean)webBean.findChildRecursive("RazonSocialS"); 
    if(null!=RazonSocialSBean){
        if(null!=RazonSocialSBean.getValue(pageContext)){
            strRazonSocialS = RazonSocialSBean.getValue(pageContext).toString();
        }
    }
    
    OAMessageTextInputBean DireccionSBean = (OAMessageTextInputBean)webBean.findChildRecursive("DireccionS"); 
    if(null!=DireccionSBean){
        if(null!=DireccionSBean.getValue(pageContext)){
            strDireccionS = DireccionSBean.getValue(pageContext).toString();
        }
    }
    
      OAMessageTextInputBean NumeroExtSBean = (OAMessageTextInputBean)webBean.findChildRecursive("NumeroExtS"); 
      if(null!=NumeroExtSBean){
          if(null!=NumeroExtSBean.getValue(pageContext)){
              strNumeroExtS = NumeroExtSBean.getValue(pageContext).toString();
          }
      }
      
      OAMessageTextInputBean NumeroIntSBean = (OAMessageTextInputBean)webBean.findChildRecursive("NumeroIntS"); 
      if(null!=NumeroIntSBean){
          if(null!=NumeroIntSBean.getValue(pageContext)){
              strNumeroIntS = NumeroIntSBean.getValue(pageContext).toString();
          }
      }
    
    OAMessageTextInputBean EntreCallesSBean = (OAMessageTextInputBean)webBean.findChildRecursive("EntreCallesS"); 
    if(null!=EntreCallesSBean){
        if(null!=EntreCallesSBean.getValue(pageContext)){
            strEntreCallesS = EntreCallesSBean.getValue(pageContext).toString();
        }
    }
    
    OAMessageTextInputBean ColoniaSBean = (OAMessageTextInputBean)webBean.findChildRecursive("ColoniaS"); 
    if(null!=ColoniaSBean){
        if(null!=ColoniaSBean.getValue(pageContext)){
            strColoniaS = ColoniaSBean.getValue(pageContext).toString();
        }
    }
    
    OAMessageTextInputBean CiudadOMunicipioSBean = (OAMessageTextInputBean)webBean.findChildRecursive("CiudadOMunicipioS"); 
    if(null!=CiudadOMunicipioSBean){
        if(null!=CiudadOMunicipioSBean.getValue(pageContext)){
            strCiudadOMunicipioS = CiudadOMunicipioSBean.getValue(pageContext).toString();
        }
    }     
    
    
    OAMessageChoiceBean EstadoSBean = (OAMessageChoiceBean)webBean.findChildRecursive("EstadoS");
     if(null!=EstadoSBean){
      if(null!=EstadoSBean.getValue(pageContext)){
      strEstadoSValue = EstadoSBean.getValue(pageContext).toString(); 
      strEstadoSText = EstadoSBean.getSelectionText(pageContext).toString();
     }
    }
    
    OAMessageTextInputBean CPSBean = (OAMessageTextInputBean)webBean.findChildRecursive("CPS");
     if(null!=CPSBean){
      if(null!=CPSBean.getValue(pageContext)){
      strCPSValue = CPSBean.getValue(pageContext).toString(); 
     }
    }
    
      OAMessageCheckBoxBean CedulaSBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("CedulaS");
      if(null!=CedulaSBean){
          if(null!=CedulaSBean.getValue(pageContext)){
           strCedulaS = CedulaSBean.getValue(pageContext).toString();
          }
      }
    
      if("RFCEvt".equals(pageContext.getParameter(this.EVENT_PARAM))
         ||0==gIntSelectedIndex){
          System.out.println("Evento RFCEvt");
          String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\.+|\\,+|\\-+|" +
          "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
          "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\\"+";
           Pattern pattern = Pattern.compile(REG_EXP);
           if(null!=strRFCP&&!"".equals(strRFCP)){
           Matcher matcher = pattern.matcher(strRFC);
           if(matcher.find()){
            throw new OAException("El RFC contiene caractares especiales",OAException.ERROR); 
            }
            if(strRFCP.contains(" ")){
                throw new OAException("El RFC contiene espacios",OAException.ERROR); 
            }
           }
           
          com.sun.java.util.collections.List listValidaClienteRFCRazonSocial = new com.sun.java.util.collections.ArrayList(); 
          listValidaClienteRFCRazonSocial = altaDeClienteAM.validaClienteRFCRazonSocial(strNombreCLiente
                                                                                       ,strRFCP
                                                                                       ,strRazonSocialP
                                                                                       ,strRFC
                                                                                       );
          if(listValidaClienteRFCRazonSocial.size()>0){
          OAException.raiseBundledOAException(listValidaClienteRFCRazonSocial);
          }
      } /**    if("RFCPEvt".equals(pageContext.getParameter(this.EVENT_PARAM)) ||0==gIntSelectedIndex){  **/
    
      System.out.println("strRFCP:"+strRFCP);
      if("RFCPEvt".equals(pageContext.getParameter(this.EVENT_PARAM))
         ||0==gIntSelectedIndex){
          System.out.println("Evento RFCP");
          String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\.+|\\,+|\\-+|" +
          "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
          "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\\"+";
           Pattern pattern = Pattern.compile(REG_EXP);
           if(null!=strRFCP&&!"".equals(strRFCP)){
           Matcher matcher = pattern.matcher(strRFCP);
           if(matcher.find()){
            throw new OAException("El RFC primario contiene caractares especiales",OAException.ERROR); 
            }
            if(strRFCP.contains(" ")){
                throw new OAException("El RFC primario contiene espacios",OAException.ERROR); 
            }
           }
           
          com.sun.java.util.collections.List listValidaClienteRFCRazonSocial = new com.sun.java.util.collections.ArrayList(); 
          listValidaClienteRFCRazonSocial = altaDeClienteAM.validaClienteRFCRazonSocial(strNombreCLiente
                                                                                       ,strRFCP
                                                                                       ,strRazonSocialP
                                                                                       ,strRFC
                                                                                       );
          if(listValidaClienteRFCRazonSocial.size()>0){
          OAException.raiseBundledOAException(listValidaClienteRFCRazonSocial);
          }
      } /**    if("RFCPEvt".equals(pageContext.getParameter(this.EVENT_PARAM)) ||0==gIntSelectedIndex){  **/
      
       System.out.println("strRFCS:"+strRFCS);
       if("RFCSEvt".equals(pageContext.getParameter(this.EVENT_PARAM))
          ||0==gIntSelectedIndex){
           System.out.println("Evento RFCS");
           String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\.+|\\,+|\\-+|" +
           "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
           "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\\"+";
            Pattern pattern = Pattern.compile(REG_EXP);
            if(null!=strRFCS&&!"".equals(strRFCS)){
            Matcher matcher = pattern.matcher(strRFCS);
            if(matcher.find()){
             throw new OAException("El RFC secundario contiene caractares especiales",OAException.ERROR); 
             }
             if(strRFCS.contains(" ")){
                 throw new OAException("El RFC secundario contiene espacios",OAException.ERROR); 
             }
            }
            
           com.sun.java.util.collections.List listValidaClienteRFCRazonSocial = new com.sun.java.util.collections.ArrayList(); 
           listValidaClienteRFCRazonSocial = altaDeClienteAM.validaClienteRFCRazonSocial(strNombreCLiente
                                                                                        ,strRFCS
                                                                                        ,strRazonSocialS
                                                                                        ,strRFC
                                                                                        );
           if(listValidaClienteRFCRazonSocial.size()>0){
           OAException.raiseBundledOAException(listValidaClienteRFCRazonSocial);
           }
       }
      
      System.out.println("strRazonSocialP:"+strRazonSocialP);
      System.out.println("strDireccionP:"+strDireccionP);
      System.out.println("strEntreCallesP:"+strEntreCallesP);
      System.out.println("strColoniaP:"+strColoniaP);
      System.out.println("strCiudadOMunicipioP:"+strCiudadOMunicipioP);
      System.out.println("strEstadoPValue:"+strEstadoPValue);
      System.out.println("strEstadoPText:"+strEstadoPText);
      System.out.println("strCPPValue:"+strCPPValue);
      System.out.println("strCPPText:"+strCPPText);
      System.out.println("strCedulaP:"+strCedulaP);
     
     
      System.out.println("strRFCS:"+strRFCS);
      System.out.println("strRazonSocialS:"+strRazonSocialS);
      System.out.println("strDireccionS:"+strDireccionS);
      System.out.println("strEntreCallesS:"+strEntreCallesS);
      System.out.println("strColoniaS:"+strColoniaS);
      System.out.println("strCiudadOMunicipioS:"+strCiudadOMunicipioS);
      System.out.println("strEstadoSValue:"+strEstadoSValue);
      System.out.println("strEstadoSText:"+strEstadoSText);
      System.out.println("strCPSValue:"+strCPSValue);
      System.out.println("strCPSText:"+strCPSText);
      System.out.println("strCedulaS:"+strCedulaS
      );
    
    /****************************************************************************
     ******************** Punto De Recoleccion **************************************
     ****************************************************************************/
    
     String strEntregaEnQPP = null; 
     String strNombreP = null;
     String strContactoP = null; 
     String strPuDeReDireccionP = null; 
     String strDiaP = null; 
     String strHoraP = null;
     
    String strEntregaEnQPS = null; 
    String strNombreS = null;
    String strContactoS = null; 
    String strPuDeReDireccionS = null; 
    String strDiaS = null; 
    String strHoraS = null;
    
     OAMessageChoiceBean EntregaEnQPPBean = (OAMessageChoiceBean)webBean.findChildRecursive("EntregaEnQPP"); 
     if(null!=EntregaEnQPPBean){
         if(null!=EntregaEnQPPBean.getValue(pageContext)){
             strEntregaEnQPP = EntregaEnQPPBean.getValue(pageContext).toString();
         }
     }     
    
    OAMessageTextInputBean NombrePBean = (OAMessageTextInputBean)webBean.findChildRecursive("NombreP"); 
    if(null!=NombrePBean){
        if(null!=NombrePBean.getValue(pageContext)){
            strNombreP = NombrePBean.getValue(pageContext).toString();
        }
    }     
    
    OAMessageTextInputBean ContactoPBean = (OAMessageTextInputBean)webBean.findChildRecursive("ContactoP"); 
    if(null!=ContactoPBean){
        if(null!=ContactoPBean.getValue(pageContext)){
            strContactoP = ContactoPBean.getValue(pageContext).toString();
        }
    }  
    
    OAMessageTextInputBean PuDeReDireccionPBean = (OAMessageTextInputBean)webBean.findChildRecursive("PuDeReDireccionP"); 
    if(null!=PuDeReDireccionPBean){
        if(null!=PuDeReDireccionPBean.getValue(pageContext)){
            strPuDeReDireccionP = PuDeReDireccionPBean.getValue(pageContext).toString();
        }
    }     
    
    OAMessageTextInputBean DiaPBean = (OAMessageTextInputBean)webBean.findChildRecursive("DiaP"); 
    if(null!=DiaPBean){
        if(null!=DiaPBean.getValue(pageContext)){
            strDiaP = DiaPBean.getValue(pageContext).toString();
        }
    }     
    
    OAMessageTextInputBean HoraPBean = (OAMessageTextInputBean)webBean.findChildRecursive("HoraP"); 
    if(null!=HoraPBean){
        if(null!=HoraPBean.getValue(pageContext)){
            strHoraP = HoraPBean.getValue(pageContext).toString();
        }
    }    
    
    OAMessageChoiceBean EntregaEnQPSBean = (OAMessageChoiceBean)webBean.findChildRecursive("EntregaEnQPS"); 
    if(null!=EntregaEnQPSBean){
        if(null!=EntregaEnQPSBean.getValue(pageContext)){
            strEntregaEnQPS = EntregaEnQPSBean.getValue(pageContext).toString();
        }
    }     
    
    OAMessageTextInputBean NombreSBean = (OAMessageTextInputBean)webBean.findChildRecursive("NombreS");
    if(null!=NombreSBean){
       if(null!=NombreSBean.getValue(pageContext)){
           strNombreS = NombreSBean.getValue(pageContext).toString();
       }
    }
    
    OAMessageTextInputBean ContactoSBean = (OAMessageTextInputBean)webBean.findChildRecursive("ContactoS");
    if(null!=ContactoSBean){
       if(null!=ContactoSBean.getValue(pageContext)){
           strContactoS = ContactoSBean.getValue(pageContext).toString();
       }
    }
    
    OAMessageTextInputBean PuDeReDireccionSBean = (OAMessageTextInputBean)webBean.findChildRecursive("PuDeReDireccionS");
    if(null!=PuDeReDireccionSBean){
       if(null!=PuDeReDireccionSBean.getValue(pageContext)){
           strPuDeReDireccionS = PuDeReDireccionSBean.getValue(pageContext).toString();
       }
    }
    
    OAMessageTextInputBean DiaSBean = (OAMessageTextInputBean)webBean.findChildRecursive("DiaS");
    if(null!=DiaSBean){
       if(null!=DiaSBean.getValue(pageContext)){
           strDiaS = DiaSBean.getValue(pageContext).toString();
       }
    }
    
    OAMessageTextInputBean HoraSBean = (OAMessageTextInputBean)webBean.findChildRecursive("HoraS");
    if(null!=HoraSBean){
       if(null!=HoraSBean.getValue(pageContext)){
           strHoraS = HoraSBean.getValue(pageContext).toString();
       }
    }
    
    System.out.println("strEntregaEnQPP:"+strEntregaEnQPP);
    System.out.println("strNombreP:"+strNombreP);
    System.out.println("strContactoP:"+strContactoP);
    System.out.println("strPuDeReDireccionP:"+strPuDeReDireccionP);
    System.out.println("strDiaP:"+strDiaP);
    System.out.println("strHoraP:"+strHoraP);
    
    System.out.println("strEntregaEnQPS:"+strEntregaEnQPS);
    System.out.println("strNombreS:"+strNombreS);
    System.out.println("strContactoS:"+strContactoS);
    System.out.println("strPuDeReDireccionS:"+strPuDeReDireccionS);
    System.out.println("strDiaS:"+strDiaS);
    System.out.println("strHoraS:"+strHoraS);
    
    if(1==gIntSelectedIndex){
      if("N".equals(strEntregaEnQPP)){
      com.sun.java.util.collections.List listExcpt = new com.sun.java.util.collections.ArrayList();
      if(null==strNombreP||"".equals(strNombreP)){
          listExcpt.add(new OAException("Campo Nombre es obligatorio.",OAException.ERROR));
      }
      /***************************************** 19052018 se pidio ocultar el campo
      if(null==strContactoP||"".equals(strContactoP)){
          listExcpt.add(new OAException("Campo Contacto es obligatorio.",OAException.ERROR));
      }
      *********************************************************************************/
      if(null==strPuDeReDireccionP||"".equals(strPuDeReDireccionP)){
          listExcpt.add(new OAException("Campo Direccion es obligatorio.",OAException.ERROR));
      }
      if(null==strDiaP||"".equals(strDiaP)){
          listExcpt.add(new OAException("Campo Dia es obligatorio.",OAException.ERROR));
      }
      if(null==strHoraP||"".equals(strHoraP)){
          listExcpt.add(new OAException("Campo Hora es obligatorio.",OAException.ERROR));
      }
      if(listExcpt.size()>0){
      OAException.raiseBundledOAException(listExcpt);
      }
      /******************************************************************************
       CedulasTmpVOImpl cedulasTmpVOImpl = altaDeClienteAM.getCedulasTmpVO1();
        CedulasTmpVORowImpl cedulasTmpVORowImpl = null; 
       RowSetIterator iterator = cedulasTmpVOImpl.createRowSetIterator(null);
       if(iterator.hasNext()){
           cedulasTmpVORowImpl = (CedulasTmpVORowImpl)iterator.next(); 
           cedulasTmpVORowImpl.setEntregaEnQpPisRequired("yes");
       }
        iterator.closeRowSetIterator();
        return;
        ****************************************/
      } /** END if("Y".equals(strEntregaEnQPP)){ **/
    }
    
    /****************************************************************************
     ******************** Contactos **************************************
     ****************************************************************************/
     
     if(null!=altaDeClienteAM){
       altaDeClienteAM.displayInfoContactosTmpVO();
     }
    
    /***************************************************************************
     ********************* Facturacion y Pago **********************************
     ***************************************************************************/
     
     String strCondicionesDePagoValue = null; 
     String strCondicionesDePagoText = null; 
      String strTipoDePagoValue = null; 
      String strTipoDePagoText = null;
      String strObservaciones = null; 
      String strRequiereAdendasValue = null; 
      String strRequiereAdendasText = null;
      String strRequiereFacturaValue = null; 
      String strRequiereFacturaText = null;
      String strMotivo = null;
      String strCicloFacturacionValue = null; 
      String strCicloFacturacionText = null; 
      String strMetodoDePagoValue = null; 
      String strMetodoDePagoText = null;
      String strUsoDelCFDIValue = null; 
      String strUsoDelCFDIText = null; 
      String strUtilizaPortalValue = null; 
      String strUtilizaPortalText = null;
      String strNumCuenta = null; 
      String strPortalDeFacturas = null; 
      String strNombreBanco = null; 
      String strOrdenDeCompraValue = null; 
      String strOrdenDeCompraText = null;
      String strDiasNaturales = null; 
      String strContratoValue = null; 
      String strContratoText = null;
      String strDiasRecepcion = null; 
      String strVigenciaDeContrato = null; 
      String strLunes = null; 
      String strMartes = null; 
      String strMiercoles = null; 
      String strJueves = null; 
      String strViernes = null; 
      String strSabado = null; 
      String strDomingo = null; 
      
     
     OAMessageRadioGroupBean CondicionesDePagoBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("CondicionesDePago");
     if(null!=CondicionesDePagoBean){
      if(null!=CondicionesDePagoBean.getValue(pageContext)){
        strCondicionesDePagoValue = CondicionesDePagoBean.getValue(pageContext).toString();
        strCondicionesDePagoText = CondicionesDePagoBean.getSelectionText(pageContext).toString();
      }  
     }
    
      
      OAMessageRadioGroupBean TipoDePagoBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("TipoDePago");
      if(null!=TipoDePagoBean){
       if(null!=TipoDePagoBean.getValue(pageContext)){
         strTipoDePagoValue = TipoDePagoBean.getValue(pageContext).toString();
         strTipoDePagoText = TipoDePagoBean.getSelectionText(pageContext).toString();
       }  
      }
      
      
      OAMessageTextInputBean ObservacionesBean = (OAMessageTextInputBean)webBean.findChildRecursive("Observaciones");
      if(null!=ObservacionesBean){
         if(null!=ObservacionesBean.getValue(pageContext)){
             strObservaciones = ObservacionesBean.getValue(pageContext).toString();
         }
      }
      
      
      OAMessageRadioGroupBean RequiereAdendasBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("RequiereAdendas");
      if(null!=RequiereAdendasBean){
       if(null!=RequiereAdendasBean.getValue(pageContext)){
         strRequiereAdendasValue = RequiereAdendasBean.getValue(pageContext).toString();
         strRequiereAdendasText = RequiereAdendasBean.getSelectionText(pageContext).toString();
       }  
      }
      
      
      OAMessageRadioGroupBean RequiereFacturaBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("RequiereFactura");
      if(null!=RequiereFacturaBean){
       if(null!=RequiereFacturaBean.getValue(pageContext)){
         strRequiereFacturaValue = RequiereFacturaBean.getValue(pageContext).toString();
         strRequiereFacturaText = RequiereFacturaBean.getSelectionText(pageContext).toString();
       }  
      }
      
     
      OAMessageTextInputBean MotivoBean = (OAMessageTextInputBean)webBean.findChildRecursive("Motivo");
      if(null!=MotivoBean){
         if(null!=MotivoBean.getValue(pageContext)){
             strMotivo = MotivoBean.getValue(pageContext).toString();
         }
      }
      
      
      OAMessageChoiceBean CicloFacturacionBean = (OAMessageChoiceBean)webBean.findChildRecursive("CicloFacturacion");
       if(null!=CicloFacturacionBean){
        if(null!=CicloFacturacionBean.getValue(pageContext)){
        strCicloFacturacionValue = CicloFacturacionBean.getValue(pageContext).toString(); 
        strCicloFacturacionText = CicloFacturacionBean.getSelectionText(pageContext).toString();
       }
      }
      
     
      OAMessageRadioGroupBean MetodoDePagoBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("MetodoDePago");
      if(null!=MetodoDePagoBean){
       if(null!=MetodoDePagoBean.getValue(pageContext)){
         strMetodoDePagoValue = MetodoDePagoBean.getValue(pageContext).toString();
         strMetodoDePagoText = MetodoDePagoBean.getSelectionText(pageContext).toString();
       }  
      } 
      
      
      OAMessageChoiceBean UsoDelCFDIBean = (OAMessageChoiceBean)webBean.findChildRecursive("UsoDelCFDI");
       if(null!=UsoDelCFDIBean){
        if(null!=UsoDelCFDIBean.getValue(pageContext)){
        strUsoDelCFDIValue = UsoDelCFDIBean.getValue(pageContext).toString(); 
        strUsoDelCFDIText = UsoDelCFDIBean.getSelectionText(pageContext).toString();
       }
      }
      
      
      OAMessageRadioGroupBean UtilizaPortalBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("UtilizaPortal");
      if(null!=UtilizaPortalBean){
       if(null!=UtilizaPortalBean.getValue(pageContext)){
         strUtilizaPortalValue = UtilizaPortalBean.getValue(pageContext).toString();
         strUtilizaPortalText = UtilizaPortalBean.getSelectionText(pageContext).toString();
       }  
      } 
     
      
      OAMessageTextInputBean NumCuentaBean = (OAMessageTextInputBean)webBean.findChildRecursive("NumCuenta");
      if(null!=NumCuentaBean){
         if(null!=NumCuentaBean.getValue(pageContext)){
             strNumCuenta = NumCuentaBean.getValue(pageContext).toString();
         }
      }
     
     
      OAMessageTextInputBean PortalDeFacturasBean = (OAMessageTextInputBean)webBean.findChildRecursive("PortalDeFacturas");
      if(null!=PortalDeFacturasBean){
         if(null!=PortalDeFacturasBean.getValue(pageContext)){
             strPortalDeFacturas = PortalDeFacturasBean.getValue(pageContext).toString();
         }
      } 
      
      
      OAMessageTextInputBean NombreBancoBean = (OAMessageTextInputBean)webBean.findChildRecursive("NombreBanco");
      if(null!=NombreBancoBean){
         if(null!=NombreBancoBean.getValue(pageContext)){
             strNombreBanco = NombreBancoBean.getValue(pageContext).toString();
         }
      } 
      
     
      OAMessageRadioGroupBean OrdenDeCompraBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("OrdenDeCompra");
      if(null!=OrdenDeCompraBean){
       if(null!=OrdenDeCompraBean.getValue(pageContext)){
         strOrdenDeCompraValue = OrdenDeCompraBean.getValue(pageContext).toString();
         strOrdenDeCompraText = OrdenDeCompraBean.getSelectionText(pageContext).toString();
       }  
      } 
      
      
      OAMessageChoiceBean DiasNaturalesBean = (OAMessageChoiceBean)webBean.findChildRecursive("DiasNaturales");
      if(null!=DiasNaturalesBean){
         if(null!=DiasNaturalesBean.getValue(pageContext)){
             strDiasNaturales = DiasNaturalesBean.getValue(pageContext).toString();
         }
      } 
      
      
      OAMessageRadioGroupBean ContratoBean = (OAMessageRadioGroupBean)webBean.findChildRecursive("Contrato");
      if(null!=ContratoBean){
       if(null!=ContratoBean.getValue(pageContext)){
         strContratoValue = ContratoBean.getValue(pageContext).toString();
         strContratoText = ContratoBean.getSelectionText(pageContext).toString();
       }  
      }  
      
      
      OAMessageTextInputBean DiasRecepcionBean = (OAMessageTextInputBean)webBean.findChildRecursive("DiasRecepcion");
      if(null!=DiasRecepcionBean){
         if(null!=DiasRecepcionBean.getValue(pageContext)){
             strDiasRecepcion = DiasRecepcionBean.getValue(pageContext).toString();
         }
      } 
      
      
      OAMessageTextInputBean VigenciaDeContratoBean = (OAMessageTextInputBean)webBean.findChildRecursive("VigenciaDeContrato");
      if(null!=VigenciaDeContratoBean){
         if(null!=VigenciaDeContratoBean.getValue(pageContext)){
             strVigenciaDeContrato = VigenciaDeContratoBean.getValue(pageContext).toString();
         }
      } 
      
    OAMessageCheckBoxBean LunesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Lunes"); 
    if(null!=LunesBean&&null!=LunesBean.getValue(pageContext)){
      strLunes = LunesBean.getValue(pageContext).toString(); 
    }
     OAMessageCheckBoxBean MartesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Martes"); 
     if(null!=MartesBean&&null!=MartesBean.getValue(pageContext)){
       strMartes = MartesBean.getValue(pageContext).toString(); 
     }
     OAMessageCheckBoxBean MiercolesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Miercoles"); 
     if(null!=MiercolesBean&&null!=MiercolesBean.getValue(pageContext)){
       strMiercoles = MiercolesBean.getValue(pageContext).toString(); 
     }
     OAMessageCheckBoxBean JuevesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Jueves"); 
     if(null!=JuevesBean&&null!=JuevesBean.getValue(pageContext)){
       strJueves = JuevesBean.getValue(pageContext).toString(); 
     }
     OAMessageCheckBoxBean ViernesBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Viernes"); 
     if(null!=ViernesBean&&null!=ViernesBean.getValue(pageContext)){
       strViernes = ViernesBean.getValue(pageContext).toString(); 
     }
     OAMessageCheckBoxBean SabadoBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Sabado"); 
     if(null!=SabadoBean&&null!=SabadoBean.getValue(pageContext)){
       strSabado = SabadoBean.getValue(pageContext).toString(); 
     }
     OAMessageCheckBoxBean DomingoBean = (OAMessageCheckBoxBean)webBean.findChildRecursive("Domingo"); 
     if(null!=DomingoBean&&null!=DomingoBean.getValue(pageContext)){
       strDomingo = DomingoBean.getValue(pageContext).toString(); 
     }
     
    System.out.println("strCondicionesDePagoValue:"+strCondicionesDePagoValue);
    System.out.println("strCondicionesDePagoText:"+strCondicionesDePagoText);
    System.out.println("strTipoDePagoValue:"+strTipoDePagoValue);
    System.out.println("strTipoDePagoText:"+strTipoDePagoText);
    System.out.println("strObservaciones:"+strObservaciones);
    
    System.out.println("strRequiereAdendasValue:"+strRequiereAdendasValue);
    System.out.println("strRequiereAdendasText:"+strRequiereAdendasText);
    
    System.out.println("strRequiereFacturaValue:"+strRequiereFacturaValue);
    System.out.println("strRequiereFacturaText:"+strRequiereFacturaText);
    
    System.out.println("strMotivo:"+strMotivo);
    
    System.out.println("strCicloFacturacionValue:"+strCicloFacturacionValue);
    System.out.println("strCicloFacturacionText:"+strCicloFacturacionText);
      
    System.out.println("strMetodoDePagoValue:"+strMetodoDePagoValue);
    System.out.println("strMetodoDePagoText:"+strMetodoDePagoText);  
    
    System.out.println("strUsoDelCFDIValue:"+strUsoDelCFDIValue);
    System.out.println("strUsoDelCFDIText:"+strUsoDelCFDIText);  
      
    System.out.println("strUtilizaPortalValue:"+strUtilizaPortalValue);
    System.out.println("strUtilizaPortalText:"+strUtilizaPortalText); 
     
    System.out.println("strNumCuenta:"+strNumCuenta);
     
    System.out.println("strPortalDeFacturas:"+strPortalDeFacturas);
      
    System.out.println("strNombreBanco:"+strNombreBanco);
      
    System.out.println("strOrdenDeCompraValue:"+strOrdenDeCompraValue);
    System.out.println("strOrdenDeCompraText:"+strOrdenDeCompraText); 
      
    System.out.println("strDiasNaturales:"+strDiasNaturales);
      
    System.out.println("strContratoValue:"+strContratoValue);
    System.out.println("strContratoText:"+strContratoText); 
      
    System.out.println("strDiasRecepcion:"+strDiasRecepcion);
      
    System.out.println("strVigenciaDeContrato:"+strVigenciaDeContrato);
     
     
     if(3==gIntSelectedIndex){
         com.sun.java.util.collections.List  listExceptions = new com.sun.java.util.collections.ArrayList();
       if("CONTADO".equals(strCondicionesDePagoValue)){
         if(null==strObservaciones||"".equals(strObservaciones)){
             listExceptions.add(new OAException("Si se selecciona Contado, el campo Observaciones es obligatorio.",OAException.ERROR));
         }
       }else if("CREDITO".equals(strCondicionesDePagoValue)){
         System.out.println("Si se selecciona Credito, el campo Observaciones es opcional.");
         if(null==strDiasNaturales||"".equals(strDiasNaturales)){
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
       
         if("Y".equals(strUtilizaPortalValue)){
          if(null==strPortalDeFacturas||"".equals(strPortalDeFacturas)){
              listExceptions.add(new OAException("Si se Utiliza Portal, el campo Portal de Facturas (Link) es obligatorio.",OAException.ERROR));
          }
         }else if("N".equals(strUtilizaPortalValue)){
             System.out.println("Si no se Utiliza Portal, el campo Portal de Facturas (Link) es opcional.");
         } 
         
        if(listExceptions.size()>0){
         OAException.raiseBundledOAException(listExceptions);
        }
       
     } /** END if(3==gIntSelectedIndex){**/
     
     if(0==gIntSelectedIndex){
         com.sun.java.util.collections.List  listExceptions = new com.sun.java.util.collections.ArrayList();
       if(null!=strRFCP&&!"".equals(strRFCP)
        &&(null!=strRFCS&&!"".equals(strRFCS))){
         if(strRFCP.equals(strRFCS)){
           listExceptions.add(new OAException("El RFC capturado esta repetido.",OAException.ERROR)); 
         }
       }
         if(null!=strRazonSocialP&&!"".equals(strRazonSocialP)
          &&(null!=strRazonSocialS&&!"".equals(strRazonSocialS))){
           if(strRazonSocialP.equals(strRazonSocialS)){
             listExceptions.add(new OAException("La Razon Social capturada esta repetida.",OAException.ERROR)); 
           }
         }
         
         if(listExceptions.size()>0){
          OAException.raiseBundledOAException(listExceptions);
         }
         
     }
     
     
      if(null!=pageContext.getParameter("Validar")){
          com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
          parameters.put("pEnabledGrabar","Y");
          pageContext.forwardImmediatelyToCurrentPage(parameters
                                                     ,true
                                                     ,null);
      }  
      
      if("LimpiarEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){
          pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClientePG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                    ,null /*functionName*/
                                    ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                    ,null /*menuName*/
                                    ,null /*parameters*/
                                    ,false /*retainAM*/
                                    ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                    ,OAException.ERROR /*messagingLevel*/
                                    );
         return;
      } /** END if("LimpiarEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){ **/
      
      if(null!=altaDeClienteAM){
          com.sun.java.util.collections.List listValidaClienteRFCRazonSocial = new com.sun.java.util.collections.ArrayList(); 
          listValidaClienteRFCRazonSocial = altaDeClienteAM.validaClienteRFCRazonSocial(strNombreCLiente
                                                                                       ,strRFCP
                                                                                       ,strRazonSocialP
                                                                                       ,strRFC
                                                                                       );
          if(listValidaClienteRFCRazonSocial.size()>0){
          OAException.raiseBundledOAException(listValidaClienteRFCRazonSocial);
          }
          com.sun.java.util.collections.List listValidaContactosTmpVO = new com.sun.java.util.collections.ArrayList(); 
          if(2==gIntSelectedIndex){
          listValidaContactosTmpVO = altaDeClienteAM.validaContactosTmpVO();
          if(listValidaContactosTmpVO.size()>0){
              OAException.raiseBundledOAException(listValidaContactosTmpVO);
          }
          }
      } /** END Validaciones **/
      
    /***************************************************************************
     ************ Almacenar Informacion ****************************************
     ***************************************************************************/
     if("GrabarEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){
     synchronized(this){
     if(null!=altaDeClienteAM){
     
        StringBuilder sbHeaderId = new StringBuilder("");
       altaDeClienteAM.fillClientesHeader(sbHeaderId
                                        ,strNombreCLiente 
                                        ,strGiroEmpresarialValue  
                                        ,strGiroEmpresarialText  
                                        ,strEmpresaQueFacturaValue  
                                        ,strEmpresaQueFacturaText  
                                        ,strComentarios 
                                        ,strTipoOperativoValue 
                                        ,strTipoOperativoText 
                                        ,strTipoAdministrativoValue 
                                        ,strTipoAdministrativoText 
                                        ,strTipoComercialValue  
                                        ,strTipoComercialText 
                                        ,strRFC
                                        ,strRazonSocial
                                        ,strEjecutivoValue
                                       );
       
         DataObject CedulaExaminePUploadData =  pageContext.getNamedDataObject("CedulaExamineP"); 
         String CedulaExaminePFileName = null; 
         String CedulaExaminePContentType = null; 
         BlobDomain CedulaExaminePByteStream = null;
         
         System.out.println("CedulaExaminePUploadData:"+CedulaExaminePUploadData);
         if(null!=CedulaExaminePUploadData){
         CedulaExaminePFileName = (String)CedulaExaminePUploadData.selectValue(null, "UPLOAD_FILE_NAME"); 
         System.out.println("CedulaExaminePFileName:"+CedulaExaminePFileName);
         CedulaExaminePContentType = (String) CedulaExaminePUploadData.selectValue(null, "UPLOAD_FILE_MIME_TYPE");  
         System.out.println("CedulaExaminePContentType:"+CedulaExaminePContentType);
          CedulaExaminePByteStream = (BlobDomain)CedulaExaminePUploadData.selectValue(null, CedulaExaminePFileName);
          if(null==CedulaExaminePByteStream){
              CedulaExaminePByteStream = (BlobDomain)altaDeClienteAM.getCedulasTmpVO1().getCurrentRow().getAttribute("PcedulaFile");
          }
         }
         
         DataObject CedulaExamineSUploadData =  pageContext.getNamedDataObject("CedulaExamineS"); 
         String CedulaExamineSFileName = null; 
         String CedulaExamineSContentType = null; 
         BlobDomain CedulaExamineSByteStream = null;
         
         if(null!=CedulaExamineSUploadData){
         CedulaExamineSFileName = (String)CedulaExamineSUploadData.selectValue(null, "UPLOAD_FILE_NAME"); 
         System.out.println("CedulaExamineSFileName:"+CedulaExamineSFileName);
         CedulaExamineSContentType = (String) CedulaExamineSUploadData.selectValue(null, "UPLOAD_FILE_MIME_TYPE");  
         System.out.println("CedulaExamineSContentType:"+CedulaExamineSContentType);
          CedulaExamineSByteStream = (BlobDomain)CedulaExamineSUploadData.selectValue(null, CedulaExamineSFileName);
          if(null==CedulaExamineSByteStream){
              CedulaExamineSByteStream = (BlobDomain)altaDeClienteAM.getCedulasTmpVO1().getCurrentRow().getAttribute("ScedulaFile");
          }
         }
         
         altaDeClienteAM.fillDireccionFiscal(sbHeaderId.toString() 
                                             ,strRFCP 
                                             ,strRazonSocialP 
                                             ,strDireccionP  
                                             ,strNumeroExtP
                                             ,strNumeroIntP
                                             ,strEntreCallesP  
                                             ,strColoniaP  
                                             ,strCiudadOMunicipioP 
                                             ,strEstadoPValue  
                                             ,strEstadoPText 
                                             ,strCPPValue  
                                             ,strCPPText 
                                             ,strCedulaP 
                                             ,strRFCS  
                                             ,strRazonSocialS  
                                             ,strDireccionS  
                                             ,strNumeroExtS
                                             ,strNumeroIntS
                                             ,strEntreCallesS  
                                             ,strColoniaS  
                                             ,strCiudadOMunicipioS  
                                             ,strEstadoSValue  
                                             ,strEstadoSText
                                             ,strCPSValue  
                                             ,strCPSText 
                                             ,strCedulaS
                                             ,CedulaExaminePFileName
                                             ,CedulaExaminePContentType
                                             ,CedulaExaminePByteStream
                                             ,CedulaExamineSFileName
                                             ,CedulaExamineSContentType
                                             ,CedulaExamineSByteStream
                                             ,strOperatingUnitPValue
                                             ,strOperatingUnitSValue
                                            );
         
         altaDeClienteAM.fillPuntoDeRecoleccion(  sbHeaderId.toString() 
                                                 ,strEntregaEnQPP 
                                                 ,strNombreP 
                                                 ,strContactoP 
                                                 ,strPuDeReDireccionP 
                                                 ,strDiaP 
                                                 ,strHoraP 
                                                 ,strEntregaEnQPS
                                                 ,strNombreS
                                                 ,strContactoS 
                                                 ,strPuDeReDireccionS 
                                                 ,strDiaS  
                                                 ,strHoraS 
                                                );  
         
         altaDeClienteAM.fillContactos(sbHeaderId.toString());
         
         altaDeClienteAM.fillFacturacionPago(sbHeaderId.toString()
                                            ,strCondicionesDePagoValue 
                                            ,strCondicionesDePagoText
                                            ,strTipoDePagoValue 
                                            ,strTipoDePagoText 
                                            ,strObservaciones 
                                            ,strRequiereAdendasValue 
                                            ,strRequiereAdendasText 
                                            ,strRequiereFacturaValue 
                                            ,strRequiereFacturaText 
                                            ,strMotivo 
                                            ,strCicloFacturacionValue 
                                            ,strCicloFacturacionText 
                                            ,strMetodoDePagoValue 
                                            ,strMetodoDePagoText 
                                            ,strUsoDelCFDIValue 
                                            ,strUsoDelCFDIText 
                                            ,strUtilizaPortalValue 
                                            ,strUtilizaPortalText 
                                            ,strNumCuenta 
                                            ,strPortalDeFacturas 
                                            ,strNombreBanco 
                                            ,strOrdenDeCompraValue 
                                            ,strOrdenDeCompraText 
                                            ,strDiasNaturales 
                                            ,strContratoValue 
                                            ,strContratoText 
                                            ,strDiasRecepcion 
                                            ,strVigenciaDeContrato
                                            ,strLunes
                                            ,strMartes
                                            ,strMiercoles
                                            ,strJueves
                                            ,strViernes
                                            ,strSabado
                                            ,strDomingo
                                            );
         
         String[] fromPdftToOracle = altaDeClienteAM.callFromPdftToOracle();
         if(null!=fromPdftToOracle[1]){
          if(!"2".equals(fromPdftToOracle[1])){
              xxqp.oracle.apps.ar.pdft.altacliente.Utils.enviarPDFConCedula(altaDeClienteAM,pageContext,"CREATE");
          }
         }
         
         com.sun.java.util.collections.HashMap parameters = new com.sun.java.util.collections.HashMap();
         parameters.put("pClientesHeaderId",sbHeaderId.toString() );
         pageContext.setForwardURL("OA.jsp?page=/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG&"+OASubTabLayoutBean.OA_SELECTED_SUBTAB_IDX+"=0" /*url*/
                                   ,null /*functionName*/
                                   ,OAWebBeanConstants.KEEP_MENU_CONTEXT /*menuContextAction*/
                                   ,null /*menuName*/
                                   ,parameters /*parameters*/
                                   ,false /*retainAM*/
                                   ,OAWebBeanConstants.ADD_BREAD_CRUMB_NO /*addBreadCrumb*/
                                   ,OAException.ERROR /*messagingLevel*/
                                   );
                                   
      
       } /** END if(null!=altaDeClienteAM){ **/
      } /** END synchronized(this){ **/
     } /** END if("GrabarEvt".equals(pageContext.getParameter(this.EVENT_PARAM))){ **/
      
   
      
  }

}

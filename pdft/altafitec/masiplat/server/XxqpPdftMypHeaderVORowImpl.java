package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;

import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.schema.server.XxqpPdftMypHeaderEOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftMypHeaderVORowImpl extends OAViewRowImpl {
    public static final int ID = 0;
    public static final int NUMEROFT = 1;
    public static final int REV = 2;
    public static final int STATUS = 3;
    public static final int NUMEROFTREFERENCIA = 4;
    public static final int PARTYID = 5;
    public static final int PDFTCLIENTEHEADERID = 6;
    public static final int EMPRESAQUEFACTURAC = 7;
    public static final int UNIDADDENEGOCIOC = 8;
    public static final int FRECUENCIAFACTURACIONC = 9;
    public static final int CONTRATOFLAG = 10;
    public static final int CONTRATOFILENAME = 11;
    public static final int CONTRATOCONTENTTYPE = 12;
    public static final int CONTRATOFILE = 13;
    public static final int FECHAINICIALOPERACION = 14;
    public static final int CREATEDBY = 15;
    public static final int CREATIONDATE = 16;
    public static final int LASTUPDATEDBY = 17;
    public static final int LASTUPDATEDATE = 18;
    public static final int LASTUPDATELOGIN = 19;
    public static final int ATTRIBUTECATEGORY = 20;
    public static final int ATTRIBUTE1 = 21;
    public static final int ATTRIBUTE2 = 22;
    public static final int ATTRIBUTE3 = 23;
    public static final int ATTRIBUTE4 = 24;
    public static final int ATTRIBUTE5 = 25;
    public static final int NOMBREDELCLIENTE = 26;
    public static final int EJECUTIVO = 27;
    public static final int UNIDADDENEGOCIOM = 28;
    public static final int ARTICULOORACLE = 29;
    public static final int FACTACC = 30;
    public static final int FILENAME1 = 31;
    public static final int CONTENTTYPE1 = 32;
    public static final int FILE1 = 33;
    public static final int FILENAME2 = 34;
    public static final int CONTENTTYPE2 = 35;
    public static final int FILE2 = 36;
    public static final int FILENAME3 = 37;
    public static final int CONTENTTYPE3 = 38;
    public static final int FILE3 = 39;
    public static final int MODIFREALIZ = 40;
    public static final int STATUSMAIL = 41;
    public static final int CURRENCYCODE = 42;
    public static final int RAZONSOCIAL = 43;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftMypHeaderVORowImpl() {
    }

    /**Gets XxqpPdftMypHeaderEO entity object.
     */
    public XxqpPdftMypHeaderEOImpl getXxqpPdftMypHeaderEO() {
        return (XxqpPdftMypHeaderEOImpl)getEntity(0);
    }

    /**Gets the attribute value for ID using the alias name Id
     */
    public Number getId() {
        return (Number) getAttributeInternal(ID);
    }

    /**Sets <code>value</code> as attribute value for ID using the alias name Id
     */
    public void setId(Number value) {
        setAttributeInternal(ID, value);
    }

    /**Gets the attribute value for NUMERO_FT using the alias name NumeroFt
     */
    public Number getNumeroFt() {
        return (Number) getAttributeInternal(NUMEROFT);
    }

    /**Sets <code>value</code> as attribute value for NUMERO_FT using the alias name NumeroFt
     */
    public void setNumeroFt(Number value) {
        setAttributeInternal(NUMEROFT, value);
    }

    /**Gets the attribute value for REV using the alias name Rev
     */
    public Number getRev() {
        return (Number) getAttributeInternal(REV);
    }

    /**Sets <code>value</code> as attribute value for REV using the alias name Rev
     */
    public void setRev(Number value) {
        setAttributeInternal(REV, value);
    }

    /**Gets the attribute value for STATUS using the alias name Status
     */
    public String getStatus() {
        return (String) getAttributeInternal(STATUS);
    }

    /**Sets <code>value</code> as attribute value for STATUS using the alias name Status
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**Gets the attribute value for NUMERO_FT_REFERENCIA using the alias name NumeroFtReferencia
     */
    public Number getNumeroFtReferencia() {
        return (Number) getAttributeInternal(NUMEROFTREFERENCIA);
    }

    /**Sets <code>value</code> as attribute value for NUMERO_FT_REFERENCIA using the alias name NumeroFtReferencia
     */
    public void setNumeroFtReferencia(Number value) {
        setAttributeInternal(NUMEROFTREFERENCIA, value);
    }

    /**Gets the attribute value for PARTY_ID using the alias name PartyId
     */
    public Number getPartyId() {
        return (Number) getAttributeInternal(PARTYID);
    }

    /**Sets <code>value</code> as attribute value for PARTY_ID using the alias name PartyId
     */
    public void setPartyId(Number value) {
        setAttributeInternal(PARTYID, value);
    }

    /**Gets the attribute value for PDFT_CLIENTE_HEADER_ID using the alias name PdftClienteHeaderId
     */
    public Number getPdftClienteHeaderId() {
        return (Number) getAttributeInternal(PDFTCLIENTEHEADERID);
    }

    /**Sets <code>value</code> as attribute value for PDFT_CLIENTE_HEADER_ID using the alias name PdftClienteHeaderId
     */
    public void setPdftClienteHeaderId(Number value) {
        setAttributeInternal(PDFTCLIENTEHEADERID, value);
    }

    /**Gets the attribute value for EMPRESA_QUE_FACTURA_C using the alias name EmpresaQueFacturaC
     */
    public String getEmpresaQueFacturaC() {
        return (String) getAttributeInternal(EMPRESAQUEFACTURAC);
    }

    /**Sets <code>value</code> as attribute value for EMPRESA_QUE_FACTURA_C using the alias name EmpresaQueFacturaC
     */
    public void setEmpresaQueFacturaC(String value) {
        setAttributeInternal(EMPRESAQUEFACTURAC, value);
    }

    /**Gets the attribute value for UNIDAD_DE_NEGOCIO_C using the alias name UnidadDeNegocioC
     */
    public String getUnidadDeNegocioC() {
        return (String) getAttributeInternal(UNIDADDENEGOCIOC);
    }

    /**Sets <code>value</code> as attribute value for UNIDAD_DE_NEGOCIO_C using the alias name UnidadDeNegocioC
     */
    public void setUnidadDeNegocioC(String value) {
        setAttributeInternal(UNIDADDENEGOCIOC, value);
    }

    /**Gets the attribute value for FRECUENCIA_FACTURACION_C using the alias name FrecuenciaFacturacionC
     */
    public String getFrecuenciaFacturacionC() {
        return (String) getAttributeInternal(FRECUENCIAFACTURACIONC);
    }

    /**Sets <code>value</code> as attribute value for FRECUENCIA_FACTURACION_C using the alias name FrecuenciaFacturacionC
     */
    public void setFrecuenciaFacturacionC(String value) {
        setAttributeInternal(FRECUENCIAFACTURACIONC, value);
    }

    /**Gets the attribute value for CONTRATO_FLAG using the alias name ContratoFlag
     */
    public String getContratoFlag() {
        return (String) getAttributeInternal(CONTRATOFLAG);
    }

    /**Sets <code>value</code> as attribute value for CONTRATO_FLAG using the alias name ContratoFlag
     */
    public void setContratoFlag(String value) {
        setAttributeInternal(CONTRATOFLAG, value);
    }

    /**Gets the attribute value for CONTRATO_FILE_NAME using the alias name ContratoFileName
     */
    public String getContratoFileName() {
        return (String) getAttributeInternal(CONTRATOFILENAME);
    }

    /**Sets <code>value</code> as attribute value for CONTRATO_FILE_NAME using the alias name ContratoFileName
     */
    public void setContratoFileName(String value) {
        setAttributeInternal(CONTRATOFILENAME, value);
    }

    /**Gets the attribute value for CONTRATO_CONTENT_TYPE using the alias name ContratoContentType
     */
    public String getContratoContentType() {
        return (String) getAttributeInternal(CONTRATOCONTENTTYPE);
    }

    /**Sets <code>value</code> as attribute value for CONTRATO_CONTENT_TYPE using the alias name ContratoContentType
     */
    public void setContratoContentType(String value) {
        setAttributeInternal(CONTRATOCONTENTTYPE, value);
    }

    /**Gets the attribute value for CONTRATO_FILE using the alias name ContratoFile
     */
    public BlobDomain getContratoFile() {
        return (BlobDomain) getAttributeInternal(CONTRATOFILE);
    }

    /**Sets <code>value</code> as attribute value for CONTRATO_FILE using the alias name ContratoFile
     */
    public void setContratoFile(BlobDomain value) {
        if(null!=value){
         this.setContratoFlag("Y");
        }else{
         this.setContratoFlag("N");
        }
        setAttributeInternal(CONTRATOFILE, value);
    }

    /**Gets the attribute value for FECHA_INICIAL_OPERACION using the alias name FechaInicialOperacion
     */
    public Date getFechaInicialOperacion() {
        return (Date) getAttributeInternal(FECHAINICIALOPERACION);
    }

    /**Sets <code>value</code> as attribute value for FECHA_INICIAL_OPERACION using the alias name FechaInicialOperacion
     */
    public void setFechaInicialOperacion(Date value) {
        setAttributeInternal(FECHAINICIALOPERACION, value);
    }

    /**Gets the attribute value for CREATED_BY using the alias name CreatedBy
     */
    public Number getCreatedBy() {
        return (Number) getAttributeInternal(CREATEDBY);
    }

    /**Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy
     */
    public void setCreatedBy(Number value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**Gets the attribute value for CREATION_DATE using the alias name CreationDate
     */
    public Date getCreationDate() {
        return (Date) getAttributeInternal(CREATIONDATE);
    }

    /**Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy
     */
    public Number getLastUpdatedBy() {
        return (Number) getAttributeInternal(LASTUPDATEDBY);
    }

    /**Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy
     */
    public void setLastUpdatedBy(Number value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate
     */
    public Date getLastUpdateDate() {
        return (Date) getAttributeInternal(LASTUPDATEDATE);
    }

    /**Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate
     */
    public void setLastUpdateDate(Date value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**Gets the attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin
     */
    public Number getLastUpdateLogin() {
        return (Number) getAttributeInternal(LASTUPDATELOGIN);
    }

    /**Sets <code>value</code> as attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin
     */
    public void setLastUpdateLogin(Number value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**Gets the attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory
     */
    public String getAttributeCategory() {
        return (String) getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**Gets the attribute value for ATTRIBUTE1 using the alias name Attribute1
     */
    public String getAttribute1() {
        return (String) getAttributeInternal(ATTRIBUTE1);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE1 using the alias name Attribute1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**Gets the attribute value for ATTRIBUTE2 using the alias name Attribute2
     */
    public String getAttribute2() {
        return (String) getAttributeInternal(ATTRIBUTE2);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE2 using the alias name Attribute2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**Gets the attribute value for ATTRIBUTE3 using the alias name Attribute3
     */
    public String getAttribute3() {
        return (String) getAttributeInternal(ATTRIBUTE3);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE3 using the alias name Attribute3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**Gets the attribute value for ATTRIBUTE4 using the alias name Attribute4
     */
    public String getAttribute4() {
        return (String) getAttributeInternal(ATTRIBUTE4);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE4 using the alias name Attribute4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**Gets the attribute value for ATTRIBUTE5 using the alias name Attribute5
     */
    public String getAttribute5() {
        return (String) getAttributeInternal(ATTRIBUTE5);
    }

    /**Sets <code>value</code> as attribute value for ATTRIBUTE5 using the alias name Attribute5
     */
    public void setAttribute5(String value) {
        setAttributeInternal(ATTRIBUTE5, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case ID:
            return getId();
        case NUMEROFT:
            return getNumeroFt();
        case REV:
            return getRev();
        case STATUS:
            return getStatus();
        case NUMEROFTREFERENCIA:
            return getNumeroFtReferencia();
        case PARTYID:
            return getPartyId();
        case PDFTCLIENTEHEADERID:
            return getPdftClienteHeaderId();
        case EMPRESAQUEFACTURAC:
            return getEmpresaQueFacturaC();
        case UNIDADDENEGOCIOC:
            return getUnidadDeNegocioC();
        case FRECUENCIAFACTURACIONC:
            return getFrecuenciaFacturacionC();
        case CONTRATOFLAG:
            return getContratoFlag();
        case CONTRATOFILENAME:
            return getContratoFileName();
        case CONTRATOCONTENTTYPE:
            return getContratoContentType();
        case CONTRATOFILE:
            return getContratoFile();
        case FECHAINICIALOPERACION:
            return getFechaInicialOperacion();
        case CREATEDBY:
            return getCreatedBy();
        case CREATIONDATE:
            return getCreationDate();
        case LASTUPDATEDBY:
            return getLastUpdatedBy();
        case LASTUPDATEDATE:
            return getLastUpdateDate();
        case LASTUPDATELOGIN:
            return getLastUpdateLogin();
        case ATTRIBUTECATEGORY:
            return getAttributeCategory();
        case ATTRIBUTE1:
            return getAttribute1();
        case ATTRIBUTE2:
            return getAttribute2();
        case ATTRIBUTE3:
            return getAttribute3();
        case ATTRIBUTE4:
            return getAttribute4();
        case ATTRIBUTE5:
            return getAttribute5();
        case NOMBREDELCLIENTE:
            return getNombreDelCliente();
        case EJECUTIVO:
            return getEjecutivo();
        case UNIDADDENEGOCIOM:
            return getUnidadDeNegocioM();
        case ARTICULOORACLE:
            return getArticuloOracle();
        case FACTACC:
            return getFactacc();
        case FILENAME1:
            return getFileName1();
        case CONTENTTYPE1:
            return getContentType1();
        case FILE1:
            return getFile1();
        case FILENAME2:
            return getFileName2();
        case CONTENTTYPE2:
            return getContentType2();
        case FILE2:
            return getFile2();
        case FILENAME3:
            return getFileName3();
        case CONTENTTYPE3:
            return getContentType3();
        case FILE3:
            return getFile3();
        case MODIFREALIZ:
            return getModifRealiz();
        case STATUSMAIL:
            return getStatusMail();
        case CURRENCYCODE:
            return getCurrencyCode();
        case RAZONSOCIAL:
            return getRazonSocial();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case ID:
            setId((Number)value);
            return;
        case NUMEROFT:
            setNumeroFt((Number)value);
            return;
        case REV:
            setRev((Number)value);
            return;
        case STATUS:
            setStatus((String)value);
            return;
        case NUMEROFTREFERENCIA:
            setNumeroFtReferencia((Number)value);
            return;
        case PARTYID:
            setPartyId((Number)value);
            return;
        case PDFTCLIENTEHEADERID:
            setPdftClienteHeaderId((Number)value);
            return;
        case EMPRESAQUEFACTURAC:
            setEmpresaQueFacturaC((String)value);
            return;
        case UNIDADDENEGOCIOC:
            setUnidadDeNegocioC((String)value);
            return;
        case FRECUENCIAFACTURACIONC:
            setFrecuenciaFacturacionC((String)value);
            return;
        case CONTRATOFLAG:
            setContratoFlag((String)value);
            return;
        case CONTRATOFILENAME:
            setContratoFileName((String)value);
            return;
        case CONTRATOCONTENTTYPE:
            setContratoContentType((String)value);
            return;
        case CONTRATOFILE:
            setContratoFile((BlobDomain)value);
            return;
        case FECHAINICIALOPERACION:
            setFechaInicialOperacion((Date)value);
            return;
        case CREATEDBY:
            setCreatedBy((Number)value);
            return;
        case CREATIONDATE:
            setCreationDate((Date)value);
            return;
        case LASTUPDATEDBY:
            setLastUpdatedBy((Number)value);
            return;
        case LASTUPDATEDATE:
            setLastUpdateDate((Date)value);
            return;
        case LASTUPDATELOGIN:
            setLastUpdateLogin((Number)value);
            return;
        case ATTRIBUTECATEGORY:
            setAttributeCategory((String)value);
            return;
        case ATTRIBUTE1:
            setAttribute1((String)value);
            return;
        case ATTRIBUTE2:
            setAttribute2((String)value);
            return;
        case ATTRIBUTE3:
            setAttribute3((String)value);
            return;
        case ATTRIBUTE4:
            setAttribute4((String)value);
            return;
        case ATTRIBUTE5:
            setAttribute5((String)value);
            return;
        case NOMBREDELCLIENTE:
            setNombreDelCliente((String)value);
            return;
        case EJECUTIVO:
            setEjecutivo((String)value);
            return;
        case UNIDADDENEGOCIOM:
            setUnidadDeNegocioM((String)value);
            return;
        case ARTICULOORACLE:
            setArticuloOracle((String)value);
            return;
        case FACTACC:
            setFactacc((String)value);
            return;
        case FILENAME1:
            setFileName1((String)value);
            return;
        case CONTENTTYPE1:
            setContentType1((String)value);
            return;
        case FILE1:
            setFile1((BlobDomain)value);
            return;
        case FILENAME2:
            setFileName2((String)value);
            return;
        case CONTENTTYPE2:
            setContentType2((String)value);
            return;
        case FILE2:
            setFile2((BlobDomain)value);
            return;
        case FILENAME3:
            setFileName3((String)value);
            return;
        case CONTENTTYPE3:
            setContentType3((String)value);
            return;
        case FILE3:
            setFile3((BlobDomain)value);
            return;
        case MODIFREALIZ:
            setModifRealiz((ClobDomain)value);
            return;
        case STATUSMAIL:
            setStatusMail((String)value);
            return;
        case CURRENCYCODE:
            setCurrencyCode((String)value);
            return;
        case RAZONSOCIAL:
            setRazonSocial((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for the calculated attribute NombreDelCliente
     */
    public String getNombreDelCliente() {
        return (String) getAttributeInternal(NOMBREDELCLIENTE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute NombreDelCliente
     */
    public void setNombreDelCliente(String value) {
        setAttributeInternal(NOMBREDELCLIENTE, value);
    }

    /**Gets the attribute value for the calculated attribute Ejecutivo
     */
    public String getEjecutivo() {
        return (String) getAttributeInternal(EJECUTIVO);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute Ejecutivo
     */
    public void setEjecutivo(String value) {
        setAttributeInternal(EJECUTIVO, value);
    }

    /**Gets the attribute value for the calculated attribute UnidadDeNegocioM
     */
    public String getUnidadDeNegocioM() {
        return (String) getAttributeInternal(UNIDADDENEGOCIOM);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute UnidadDeNegocioM
     */
    public void setUnidadDeNegocioM(String value) {
        setAttributeInternal(UNIDADDENEGOCIOM, value);
    }

    /**Gets the attribute value for the calculated attribute ArticuloOracle
     */
    public String getArticuloOracle() {
        return (String) getAttributeInternal(ARTICULOORACLE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute ArticuloOracle
     */
    public void setArticuloOracle(String value) {
        setAttributeInternal(ARTICULOORACLE, value);
    }

    /**Gets the attribute value for the calculated attribute Factacc
     */
    public String getFactacc() {
        return (String) getAttributeInternal(FACTACC);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute Factacc
     */
    public void setFactacc(String value) {
        setAttributeInternal(FACTACC, value);
    }


    /**Gets the attribute value for FILE_NAME1 using the alias name FileName1
     */
    public String getFileName1() {
        return (String) getAttributeInternal(FILENAME1);
    }

    /**Sets <code>value</code> as attribute value for FILE_NAME1 using the alias name FileName1
     */
    public void setFileName1(String value) {
        setAttributeInternal(FILENAME1, value);
    }

    /**Gets the attribute value for CONTENT_TYPE1 using the alias name ContentType1
     */
    public String getContentType1() {
        return (String) getAttributeInternal(CONTENTTYPE1);
    }

    /**Sets <code>value</code> as attribute value for CONTENT_TYPE1 using the alias name ContentType1
     */
    public void setContentType1(String value) {
        setAttributeInternal(CONTENTTYPE1, value);
    }

    /**Gets the attribute value for FILE1 using the alias name File1
     */
    public BlobDomain getFile1() {
        return (BlobDomain) getAttributeInternal(FILE1);
    }

    /**Sets <code>value</code> as attribute value for FILE1 using the alias name File1
     */
    public void setFile1(BlobDomain value) {
        setAttributeInternal(FILE1, value);
    }

    /**Gets the attribute value for FILE_NAME2 using the alias name FileName2
     */
    public String getFileName2() {
        return (String) getAttributeInternal(FILENAME2);
    }

    /**Sets <code>value</code> as attribute value for FILE_NAME2 using the alias name FileName2
     */
    public void setFileName2(String value) {
        setAttributeInternal(FILENAME2, value);
    }

    /**Gets the attribute value for CONTENT_TYPE2 using the alias name ContentType2
     */
    public String getContentType2() {
        return (String) getAttributeInternal(CONTENTTYPE2);
    }

    /**Sets <code>value</code> as attribute value for CONTENT_TYPE2 using the alias name ContentType2
     */
    public void setContentType2(String value) {
        setAttributeInternal(CONTENTTYPE2, value);
    }

    /**Gets the attribute value for FILE2 using the alias name File2
     */
    public BlobDomain getFile2() {
        return (BlobDomain) getAttributeInternal(FILE2);
    }

    /**Sets <code>value</code> as attribute value for FILE2 using the alias name File2
     */
    public void setFile2(BlobDomain value) {
        setAttributeInternal(FILE2, value);
    }

    /**Gets the attribute value for FILE_NAME3 using the alias name FileName3
     */
    public String getFileName3() {
        return (String) getAttributeInternal(FILENAME3);
    }

    /**Sets <code>value</code> as attribute value for FILE_NAME3 using the alias name FileName3
     */
    public void setFileName3(String value) {
        setAttributeInternal(FILENAME3, value);
    }

    /**Gets the attribute value for CONTENT_TYPE3 using the alias name ContentType3
     */
    public String getContentType3() {
        return (String) getAttributeInternal(CONTENTTYPE3);
    }

    /**Sets <code>value</code> as attribute value for CONTENT_TYPE3 using the alias name ContentType3
     */
    public void setContentType3(String value) {
        setAttributeInternal(CONTENTTYPE3, value);
    }

    /**Gets the attribute value for FILE3 using the alias name File3
     */
    public BlobDomain getFile3() {
        return (BlobDomain) getAttributeInternal(FILE3);
    }

    /**Sets <code>value</code> as attribute value for FILE3 using the alias name File3
     */
    public void setFile3(BlobDomain value) {
        setAttributeInternal(FILE3, value);
    }

    /**Gets the attribute value for the calculated attribute ModifRealiz
     */
    public ClobDomain getModifRealiz() {
        return (ClobDomain) getAttributeInternal(MODIFREALIZ);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute ModifRealiz
     */
    public void setModifRealiz(ClobDomain value) {
        setAttributeInternal(MODIFREALIZ, value);
    }

    /**Gets the attribute value for the calculated attribute StatusMail
     */
    public String getStatusMail() {
        return (String) getAttributeInternal(STATUSMAIL);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute StatusMail
     */
    public void setStatusMail(String value) {
        setAttributeInternal(STATUSMAIL, value);
    }

    /**Gets the attribute value for the calculated attribute CurrencyCode
     */
    public String getCurrencyCode() {
        return (String) getAttributeInternal(CURRENCYCODE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute CurrencyCode
     */
    public void setCurrencyCode(String value) {
        setAttributeInternal(CURRENCYCODE, value);
    }

    /**Gets the attribute value for the calculated attribute RazonSocial
     */
    public String getRazonSocial() {
        return (String) getAttributeInternal(RAZONSOCIAL);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute RazonSocial
     */
    public void setRazonSocial(String value) {
        setAttributeInternal(RAZONSOCIAL, value);
    }
}

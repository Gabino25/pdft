package xxqp.oracle.apps.ar.pdft.altacliente.schema.server;

import oracle.apps.fnd.framework.server.OAEntityDefImpl;
import oracle.apps.fnd.framework.server.OAEntityImpl;

import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftClientesFactPagEOImpl extends OAEntityImpl {
    public static final int ID = 0;
    public static final int HEADERID = 1;
    public static final int CONDICIONESDEPAGOC = 2;
    public static final int OBSERVACIONES = 3;
    public static final int TIPODEPAGOC = 4;
    public static final int REQUIEREADENDASC = 5;
    public static final int REQUIEREFACTURAC = 6;
    public static final int MOTIVO = 7;
    public static final int CICLODEFACTURACIONC = 8;
    public static final int USODELCFDIC = 9;
    public static final int METODODEPAGOC = 10;
    public static final int NUMERODECUENTA = 11;
    public static final int NOMBREDELBANCO = 12;
    public static final int DIASNATDECREDITO = 13;
    public static final int DIASRECEPCIONFACTUR = 14;
    public static final int UTILIZAPORTALC = 15;
    public static final int PORTALLINK = 16;
    public static final int ORDENDECOMPRAC = 17;
    public static final int CONTRATOC = 18;
    public static final int VIGENCIACONTRATO = 19;
    public static final int CREATEDBY = 20;
    public static final int CREATIONDATE = 21;
    public static final int LASTUPDATEDBY = 22;
    public static final int LASTUPDATEDATE = 23;
    public static final int LASTUPDATELOGIN = 24;
    public static final int ATTRIBUTECATEGORY = 25;
    public static final int ATTRIBUTE1 = 26;
    public static final int ATTRIBUTE2 = 27;
    public static final int ATTRIBUTE3 = 28;
    public static final int ATTRIBUTE4 = 29;
    public static final int ATTRIBUTE5 = 30;
    public static final int DOMINGO = 31;
    public static final int JUEVES = 32;
    public static final int LUNES = 33;
    public static final int MARTES = 34;
    public static final int MIERCOLES = 35;
    public static final int SABADO = 36;
    public static final int VIERNES = 37;
    public static final int CEENVIOFACTURAS = 38;
    public static final int XXQPPDFTCLIENTESHEADEREO = 39;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftClientesFactPagEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("xxqp.oracle.apps.ar.pdft.altacliente.schema.server.XxqpPdftClientesFactPagEO");
        }
        return mDefinitionObject;
    }

    /**Gets the attribute value for Id, using the alias name Id
     */
    public Number getId() {
        return (Number)getAttributeInternal(ID);
    }

    /**Sets <code>value</code> as the attribute value for Id
     */
    public void setId(Number value) {
        setAttributeInternal(ID, value);
    }

    /**Gets the attribute value for HeaderId, using the alias name HeaderId
     */
    public Number getHeaderId() {
        return (Number)getAttributeInternal(HEADERID);
    }

    /**Sets <code>value</code> as the attribute value for HeaderId
     */
    public void setHeaderId(Number value) {
        setAttributeInternal(HEADERID, value);
    }

    /**Gets the attribute value for CondicionesDePagoC, using the alias name CondicionesDePagoC
     */
    public String getCondicionesDePagoC() {
        return (String)getAttributeInternal(CONDICIONESDEPAGOC);
    }

    /**Sets <code>value</code> as the attribute value for CondicionesDePagoC
     */
    public void setCondicionesDePagoC(String value) {
        setAttributeInternal(CONDICIONESDEPAGOC, value);
    }


    /**Gets the attribute value for Observaciones, using the alias name Observaciones
     */
    public String getObservaciones() {
        return (String)getAttributeInternal(OBSERVACIONES);
    }

    /**Sets <code>value</code> as the attribute value for Observaciones
     */
    public void setObservaciones(String value) {
        setAttributeInternal(OBSERVACIONES, value);
    }

    /**Gets the attribute value for TipoDePagoC, using the alias name TipoDePagoC
     */
    public String getTipoDePagoC() {
        return (String)getAttributeInternal(TIPODEPAGOC);
    }

    /**Sets <code>value</code> as the attribute value for TipoDePagoC
     */
    public void setTipoDePagoC(String value) {
        setAttributeInternal(TIPODEPAGOC, value);
    }


    /**Gets the attribute value for RequiereAdendasC, using the alias name RequiereAdendasC
     */
    public String getRequiereAdendasC() {
        return (String)getAttributeInternal(REQUIEREADENDASC);
    }

    /**Sets <code>value</code> as the attribute value for RequiereAdendasC
     */
    public void setRequiereAdendasC(String value) {
        setAttributeInternal(REQUIEREADENDASC, value);
    }


    /**Gets the attribute value for RequiereFacturaC, using the alias name RequiereFacturaC
     */
    public String getRequiereFacturaC() {
        return (String)getAttributeInternal(REQUIEREFACTURAC);
    }

    /**Sets <code>value</code> as the attribute value for RequiereFacturaC
     */
    public void setRequiereFacturaC(String value) {
        setAttributeInternal(REQUIEREFACTURAC, value);
    }


    /**Gets the attribute value for Motivo, using the alias name Motivo
     */
    public String getMotivo() {
        return (String)getAttributeInternal(MOTIVO);
    }

    /**Sets <code>value</code> as the attribute value for Motivo
     */
    public void setMotivo(String value) {
        setAttributeInternal(MOTIVO, value);
    }

    /**Gets the attribute value for CicloDeFacturacionC, using the alias name CicloDeFacturacionC
     */
    public String getCicloDeFacturacionC() {
        return (String)getAttributeInternal(CICLODEFACTURACIONC);
    }

    /**Sets <code>value</code> as the attribute value for CicloDeFacturacionC
     */
    public void setCicloDeFacturacionC(String value) {
        setAttributeInternal(CICLODEFACTURACIONC, value);
    }


    /**Gets the attribute value for UsoDelCfdiC, using the alias name UsoDelCfdiC
     */
    public String getUsoDelCfdiC() {
        return (String)getAttributeInternal(USODELCFDIC);
    }

    /**Sets <code>value</code> as the attribute value for UsoDelCfdiC
     */
    public void setUsoDelCfdiC(String value) {
        setAttributeInternal(USODELCFDIC, value);
    }


    /**Gets the attribute value for MetodoDePagoC, using the alias name MetodoDePagoC
     */
    public String getMetodoDePagoC() {
        return (String)getAttributeInternal(METODODEPAGOC);
    }

    /**Sets <code>value</code> as the attribute value for MetodoDePagoC
     */
    public void setMetodoDePagoC(String value) {
        setAttributeInternal(METODODEPAGOC, value);
    }


    /**Gets the attribute value for NumeroDeCuenta, using the alias name NumeroDeCuenta
     */
    public String getNumeroDeCuenta() {
        return (String)getAttributeInternal(NUMERODECUENTA);
    }

    /**Sets <code>value</code> as the attribute value for NumeroDeCuenta
     */
    public void setNumeroDeCuenta(String value) {
        setAttributeInternal(NUMERODECUENTA, value);
    }

    /**Gets the attribute value for NombreDelBanco, using the alias name NombreDelBanco
     */
    public String getNombreDelBanco() {
        return (String)getAttributeInternal(NOMBREDELBANCO);
    }

    /**Sets <code>value</code> as the attribute value for NombreDelBanco
     */
    public void setNombreDelBanco(String value) {
        setAttributeInternal(NOMBREDELBANCO, value);
    }

    /**Gets the attribute value for DiasNatDeCredito, using the alias name DiasNatDeCredito
     */
    public String getDiasNatDeCredito() {
        return (String)getAttributeInternal(DIASNATDECREDITO);
    }

    /**Sets <code>value</code> as the attribute value for DiasNatDeCredito
     */
    public void setDiasNatDeCredito(String value) {
        setAttributeInternal(DIASNATDECREDITO, value);
    }

    /**Gets the attribute value for DiasRecepcionFactur, using the alias name DiasRecepcionFactur
     */
    public String getDiasRecepcionFactur() {
        return (String)getAttributeInternal(DIASRECEPCIONFACTUR);
    }

    /**Sets <code>value</code> as the attribute value for DiasRecepcionFactur
     */
    public void setDiasRecepcionFactur(String value) {
        setAttributeInternal(DIASRECEPCIONFACTUR, value);
    }

    /**Gets the attribute value for UtilizaPortalC, using the alias name UtilizaPortalC
     */
    public String getUtilizaPortalC() {
        return (String)getAttributeInternal(UTILIZAPORTALC);
    }

    /**Sets <code>value</code> as the attribute value for UtilizaPortalC
     */
    public void setUtilizaPortalC(String value) {
        setAttributeInternal(UTILIZAPORTALC, value);
    }


    /**Gets the attribute value for PortalLink, using the alias name PortalLink
     */
    public String getPortalLink() {
        return (String)getAttributeInternal(PORTALLINK);
    }

    /**Sets <code>value</code> as the attribute value for PortalLink
     */
    public void setPortalLink(String value) {
        setAttributeInternal(PORTALLINK, value);
    }

    /**Gets the attribute value for OrdenDeCompraC, using the alias name OrdenDeCompraC
     */
    public String getOrdenDeCompraC() {
        return (String)getAttributeInternal(ORDENDECOMPRAC);
    }

    /**Sets <code>value</code> as the attribute value for OrdenDeCompraC
     */
    public void setOrdenDeCompraC(String value) {
        setAttributeInternal(ORDENDECOMPRAC, value);
    }


    /**Gets the attribute value for ContratoC, using the alias name ContratoC
     */
    public String getContratoC() {
        return (String)getAttributeInternal(CONTRATOC);
    }

    /**Sets <code>value</code> as the attribute value for ContratoC
     */
    public void setContratoC(String value) {
        setAttributeInternal(CONTRATOC, value);
    }


    /**Gets the attribute value for VigenciaContrato, using the alias name VigenciaContrato
     */
    public String getVigenciaContrato() {
        return (String)getAttributeInternal(VIGENCIACONTRATO);
    }

    /**Sets <code>value</code> as the attribute value for VigenciaContrato
     */
    public void setVigenciaContrato(String value) {
        setAttributeInternal(VIGENCIACONTRATO, value);
    }

    /**Gets the attribute value for CreatedBy, using the alias name CreatedBy
     */
    public Number getCreatedBy() {
        return (Number)getAttributeInternal(CREATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for CreatedBy
     */
    public void setCreatedBy(Number value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**Gets the attribute value for CreationDate, using the alias name CreationDate
     */
    public Date getCreationDate() {
        return (Date)getAttributeInternal(CREATIONDATE);
    }

    /**Sets <code>value</code> as the attribute value for CreationDate
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**Gets the attribute value for LastUpdatedBy, using the alias name LastUpdatedBy
     */
    public Number getLastUpdatedBy() {
        return (Number)getAttributeInternal(LASTUPDATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdatedBy
     */
    public void setLastUpdatedBy(Number value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**Gets the attribute value for LastUpdateDate, using the alias name LastUpdateDate
     */
    public Date getLastUpdateDate() {
        return (Date)getAttributeInternal(LASTUPDATEDATE);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateDate
     */
    public void setLastUpdateDate(Date value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**Gets the attribute value for LastUpdateLogin, using the alias name LastUpdateLogin
     */
    public Number getLastUpdateLogin() {
        return (Number)getAttributeInternal(LASTUPDATELOGIN);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateLogin
     */
    public void setLastUpdateLogin(Number value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**Gets the attribute value for AttributeCategory, using the alias name AttributeCategory
     */
    public String getAttributeCategory() {
        return (String)getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**Sets <code>value</code> as the attribute value for AttributeCategory
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**Gets the attribute value for Attribute1, using the alias name Attribute1
     */
    public String getAttribute1() {
        return (String)getAttributeInternal(ATTRIBUTE1);
    }

    /**Sets <code>value</code> as the attribute value for Attribute1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**Gets the attribute value for Attribute2, using the alias name Attribute2
     */
    public String getAttribute2() {
        return (String)getAttributeInternal(ATTRIBUTE2);
    }

    /**Sets <code>value</code> as the attribute value for Attribute2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**Gets the attribute value for Attribute3, using the alias name Attribute3
     */
    public String getAttribute3() {
        return (String)getAttributeInternal(ATTRIBUTE3);
    }

    /**Sets <code>value</code> as the attribute value for Attribute3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**Gets the attribute value for Attribute4, using the alias name Attribute4
     */
    public String getAttribute4() {
        return (String)getAttributeInternal(ATTRIBUTE4);
    }

    /**Sets <code>value</code> as the attribute value for Attribute4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**Gets the attribute value for Attribute5, using the alias name Attribute5
     */
    public String getAttribute5() {
        return (String)getAttributeInternal(ATTRIBUTE5);
    }

    /**Sets <code>value</code> as the attribute value for Attribute5
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
        case HEADERID:
            return getHeaderId();
        case CONDICIONESDEPAGOC:
            return getCondicionesDePagoC();
        case OBSERVACIONES:
            return getObservaciones();
        case TIPODEPAGOC:
            return getTipoDePagoC();
        case REQUIEREADENDASC:
            return getRequiereAdendasC();
        case REQUIEREFACTURAC:
            return getRequiereFacturaC();
        case MOTIVO:
            return getMotivo();
        case CICLODEFACTURACIONC:
            return getCicloDeFacturacionC();
        case USODELCFDIC:
            return getUsoDelCfdiC();
        case METODODEPAGOC:
            return getMetodoDePagoC();
        case NUMERODECUENTA:
            return getNumeroDeCuenta();
        case NOMBREDELBANCO:
            return getNombreDelBanco();
        case DIASNATDECREDITO:
            return getDiasNatDeCredito();
        case DIASRECEPCIONFACTUR:
            return getDiasRecepcionFactur();
        case UTILIZAPORTALC:
            return getUtilizaPortalC();
        case PORTALLINK:
            return getPortalLink();
        case ORDENDECOMPRAC:
            return getOrdenDeCompraC();
        case CONTRATOC:
            return getContratoC();
        case VIGENCIACONTRATO:
            return getVigenciaContrato();
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
        case DOMINGO:
            return getDomingo();
        case JUEVES:
            return getJueves();
        case LUNES:
            return getLunes();
        case MARTES:
            return getMartes();
        case MIERCOLES:
            return getMiercoles();
        case SABADO:
            return getSabado();
        case VIERNES:
            return getViernes();
        case CEENVIOFACTURAS:
            return getCeEnvioFacturas();
        case XXQPPDFTCLIENTESHEADEREO:
            return getXxqpPdftClientesHeaderEO();
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
        case HEADERID:
            setHeaderId((Number)value);
            return;
        case CONDICIONESDEPAGOC:
            setCondicionesDePagoC((String)value);
            return;
        case OBSERVACIONES:
            setObservaciones((String)value);
            return;
        case TIPODEPAGOC:
            setTipoDePagoC((String)value);
            return;
        case REQUIEREADENDASC:
            setRequiereAdendasC((String)value);
            return;
        case REQUIEREFACTURAC:
            setRequiereFacturaC((String)value);
            return;
        case MOTIVO:
            setMotivo((String)value);
            return;
        case CICLODEFACTURACIONC:
            setCicloDeFacturacionC((String)value);
            return;
        case USODELCFDIC:
            setUsoDelCfdiC((String)value);
            return;
        case METODODEPAGOC:
            setMetodoDePagoC((String)value);
            return;
        case NUMERODECUENTA:
            setNumeroDeCuenta((String)value);
            return;
        case NOMBREDELBANCO:
            setNombreDelBanco((String)value);
            return;
        case DIASNATDECREDITO:
            setDiasNatDeCredito((String)value);
            return;
        case DIASRECEPCIONFACTUR:
            setDiasRecepcionFactur((String)value);
            return;
        case UTILIZAPORTALC:
            setUtilizaPortalC((String)value);
            return;
        case PORTALLINK:
            setPortalLink((String)value);
            return;
        case ORDENDECOMPRAC:
            setOrdenDeCompraC((String)value);
            return;
        case CONTRATOC:
            setContratoC((String)value);
            return;
        case VIGENCIACONTRATO:
            setVigenciaContrato((String)value);
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
        case DOMINGO:
            setDomingo((String)value);
            return;
        case JUEVES:
            setJueves((String)value);
            return;
        case LUNES:
            setLunes((String)value);
            return;
        case MARTES:
            setMartes((String)value);
            return;
        case MIERCOLES:
            setMiercoles((String)value);
            return;
        case SABADO:
            setSabado((String)value);
            return;
        case VIERNES:
            setViernes((String)value);
            return;
        case CEENVIOFACTURAS:
            setCeEnvioFacturas((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the associated entity XxqpPdftClientesHeaderEOImpl
     */
    public XxqpPdftClientesHeaderEOImpl getXxqpPdftClientesHeaderEO() {
        return (XxqpPdftClientesHeaderEOImpl)getAttributeInternal(XXQPPDFTCLIENTESHEADEREO);
    }

    /**Sets <code>value</code> as the associated entity XxqpPdftClientesHeaderEOImpl
     */
    public void setXxqpPdftClientesHeaderEO(XxqpPdftClientesHeaderEOImpl value) {
        setAttributeInternal(XXQPPDFTCLIENTESHEADEREO, value);
    }

    /**Gets the attribute value for Domingo, using the alias name Domingo
     */
    public String getDomingo() {
        return (String)getAttributeInternal(DOMINGO);
    }

    /**Sets <code>value</code> as the attribute value for Domingo
     */
    public void setDomingo(String value) {
        setAttributeInternal(DOMINGO, value);
    }

    /**Gets the attribute value for Jueves, using the alias name Jueves
     */
    public String getJueves() {
        return (String)getAttributeInternal(JUEVES);
    }

    /**Sets <code>value</code> as the attribute value for Jueves
     */
    public void setJueves(String value) {
        setAttributeInternal(JUEVES, value);
    }

    /**Gets the attribute value for Lunes, using the alias name Lunes
     */
    public String getLunes() {
        return (String)getAttributeInternal(LUNES);
    }

    /**Sets <code>value</code> as the attribute value for Lunes
     */
    public void setLunes(String value) {
        setAttributeInternal(LUNES, value);
    }

    /**Gets the attribute value for Martes, using the alias name Martes
     */
    public String getMartes() {
        return (String)getAttributeInternal(MARTES);
    }

    /**Sets <code>value</code> as the attribute value for Martes
     */
    public void setMartes(String value) {
        setAttributeInternal(MARTES, value);
    }

    /**Gets the attribute value for Miercoles, using the alias name Miercoles
     */
    public String getMiercoles() {
        return (String)getAttributeInternal(MIERCOLES);
    }

    /**Sets <code>value</code> as the attribute value for Miercoles
     */
    public void setMiercoles(String value) {
        setAttributeInternal(MIERCOLES, value);
    }

    /**Gets the attribute value for Sabado, using the alias name Sabado
     */
    public String getSabado() {
        return (String)getAttributeInternal(SABADO);
    }

    /**Sets <code>value</code> as the attribute value for Sabado
     */
    public void setSabado(String value) {
        setAttributeInternal(SABADO, value);
    }

    /**Gets the attribute value for Viernes, using the alias name Viernes
     */
    public String getViernes() {
        return (String)getAttributeInternal(VIERNES);
    }

    /**Sets <code>value</code> as the attribute value for Viernes
     */
    public void setViernes(String value) {
        setAttributeInternal(VIERNES, value);
    }

    /**Gets the attribute value for CeEnvioFacturas, using the alias name CeEnvioFacturas
     */
    public String getCeEnvioFacturas() {
        return (String)getAttributeInternal(CEENVIOFACTURAS);
    }

    /**Sets <code>value</code> as the attribute value for CeEnvioFacturas
     */
    public void setCeEnvioFacturas(String value) {
        setAttributeInternal(CEENVIOFACTURAS, value);
    }

    /**Creates a Key object based on given key constituents
     */
    public static Key createPrimaryKey(Number id) {
        return new Key(new Object[]{id});
    }
}

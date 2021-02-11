package xxqp.oracle.apps.ar.pdft.altacliente.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;

import xxqp.oracle.apps.ar.pdft.altacliente.schema.server.XxqpPdftClientesFactPagEOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftClientesFactPagVORowImpl extends OAViewRowImpl {
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
    public static final int NOMBREDELBANCOD = 39;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftClientesFactPagVORowImpl() {
    }

    /**Gets XxqpPdftClientesFactPagEO entity object.
     */
    public XxqpPdftClientesFactPagEOImpl getXxqpPdftClientesFactPagEO() {
        return (XxqpPdftClientesFactPagEOImpl)getEntity(0);
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

    /**Gets the attribute value for HEADER_ID using the alias name HeaderId
     */
    public Number getHeaderId() {
        return (Number) getAttributeInternal(HEADERID);
    }

    /**Sets <code>value</code> as attribute value for HEADER_ID using the alias name HeaderId
     */
    public void setHeaderId(Number value) {
        setAttributeInternal(HEADERID, value);
    }

    /**Gets the attribute value for CONDICIONES_DE_PAGO_C using the alias name CondicionesDePagoC
     */
    public String getCondicionesDePagoC() {
        return (String) getAttributeInternal(CONDICIONESDEPAGOC);
    }

    /**Sets <code>value</code> as attribute value for CONDICIONES_DE_PAGO_C using the alias name CondicionesDePagoC
     */
    public void setCondicionesDePagoC(String value) {
        setAttributeInternal(CONDICIONESDEPAGOC, value);
    }


    /**Gets the attribute value for OBSERVACIONES using the alias name Observaciones
     */
    public String getObservaciones() {
        return (String) getAttributeInternal(OBSERVACIONES);
    }

    /**Sets <code>value</code> as attribute value for OBSERVACIONES using the alias name Observaciones
     */
    public void setObservaciones(String value) {
        setAttributeInternal(OBSERVACIONES, value);
    }

    /**Gets the attribute value for TIPO_DE_PAGO_C using the alias name TipoDePagoC
     */
    public String getTipoDePagoC() {
        return (String) getAttributeInternal(TIPODEPAGOC);
    }

    /**Sets <code>value</code> as attribute value for TIPO_DE_PAGO_C using the alias name TipoDePagoC
     */
    public void setTipoDePagoC(String value) {
        setAttributeInternal(TIPODEPAGOC, value);
    }


    /**Gets the attribute value for REQUIERE_ADENDAS_C using the alias name RequiereAdendasC
     */
    public String getRequiereAdendasC() {
        return (String) getAttributeInternal(REQUIEREADENDASC);
    }

    /**Sets <code>value</code> as attribute value for REQUIERE_ADENDAS_C using the alias name RequiereAdendasC
     */
    public void setRequiereAdendasC(String value) {
        setAttributeInternal(REQUIEREADENDASC, value);
    }


    /**Gets the attribute value for REQUIERE_FACTURA_C using the alias name RequiereFacturaC
     */
    public String getRequiereFacturaC() {
        return (String) getAttributeInternal(REQUIEREFACTURAC);
    }

    /**Sets <code>value</code> as attribute value for REQUIERE_FACTURA_C using the alias name RequiereFacturaC
     */
    public void setRequiereFacturaC(String value) {
        setAttributeInternal(REQUIEREFACTURAC, value);
    }


    /**Gets the attribute value for MOTIVO using the alias name Motivo
     */
    public String getMotivo() {
        return (String) getAttributeInternal(MOTIVO);
    }

    /**Sets <code>value</code> as attribute value for MOTIVO using the alias name Motivo
     */
    public void setMotivo(String value) {
        setAttributeInternal(MOTIVO, value);
    }

    /**Gets the attribute value for CICLO_DE_FACTURACION_C using the alias name CicloDeFacturacionC
     */
    public String getCicloDeFacturacionC() {
        return (String) getAttributeInternal(CICLODEFACTURACIONC);
    }

    /**Sets <code>value</code> as attribute value for CICLO_DE_FACTURACION_C using the alias name CicloDeFacturacionC
     */
    public void setCicloDeFacturacionC(String value) {
        setAttributeInternal(CICLODEFACTURACIONC, value);
    }


    /**Gets the attribute value for USO_DEL_CFDI_C using the alias name UsoDelCfdiC
     */
    public String getUsoDelCfdiC() {
        return (String) getAttributeInternal(USODELCFDIC);
    }

    /**Sets <code>value</code> as attribute value for USO_DEL_CFDI_C using the alias name UsoDelCfdiC
     */
    public void setUsoDelCfdiC(String value) {
        setAttributeInternal(USODELCFDIC, value);
    }


    /**Gets the attribute value for METODO_DE_PAGO_C using the alias name MetodoDePagoC
     */
    public String getMetodoDePagoC() {
        return (String) getAttributeInternal(METODODEPAGOC);
    }

    /**Sets <code>value</code> as attribute value for METODO_DE_PAGO_C using the alias name MetodoDePagoC
     */
    public void setMetodoDePagoC(String value) {
        setAttributeInternal(METODODEPAGOC, value);
    }


    /**Gets the attribute value for NUMERO_DE_CUENTA using the alias name NumeroDeCuenta
     */
    public String getNumeroDeCuenta() {
        return (String) getAttributeInternal(NUMERODECUENTA);
    }

    /**Sets <code>value</code> as attribute value for NUMERO_DE_CUENTA using the alias name NumeroDeCuenta
     */
    public void setNumeroDeCuenta(String value) {
        setAttributeInternal(NUMERODECUENTA, value);
    }

    /**Gets the attribute value for NOMBRE_DEL_BANCO using the alias name NombreDelBanco
     */
    public String getNombreDelBanco() {
        return (String) getAttributeInternal(NOMBREDELBANCO);
    }

    /**Sets <code>value</code> as attribute value for NOMBRE_DEL_BANCO using the alias name NombreDelBanco
     */
    public void setNombreDelBanco(String value) {
        setAttributeInternal(NOMBREDELBANCO, value);
    }

    /**Gets the attribute value for DIAS_NAT_DE_CREDITO using the alias name DiasNatDeCredito
     */
    public String getDiasNatDeCredito() {
        return (String) getAttributeInternal(DIASNATDECREDITO);
    }

    /**Sets <code>value</code> as attribute value for DIAS_NAT_DE_CREDITO using the alias name DiasNatDeCredito
     */
    public void setDiasNatDeCredito(String value) {
        setAttributeInternal(DIASNATDECREDITO, value);
    }

    /**Gets the attribute value for DIAS_RECEPCION_FACTUR using the alias name DiasRecepcionFactur
     */
    public String getDiasRecepcionFactur() {
        return (String) getAttributeInternal(DIASRECEPCIONFACTUR);
    }

    /**Sets <code>value</code> as attribute value for DIAS_RECEPCION_FACTUR using the alias name DiasRecepcionFactur
     */
    public void setDiasRecepcionFactur(String value) {
        setAttributeInternal(DIASRECEPCIONFACTUR, value);
    }

    /**Gets the attribute value for UTILIZA_PORTAL_C using the alias name UtilizaPortalC
     */
    public String getUtilizaPortalC() {
        return (String) getAttributeInternal(UTILIZAPORTALC);
    }

    /**Sets <code>value</code> as attribute value for UTILIZA_PORTAL_C using the alias name UtilizaPortalC
     */
    public void setUtilizaPortalC(String value) {
        setAttributeInternal(UTILIZAPORTALC, value);
    }


    /**Gets the attribute value for PORTAL_LINK using the alias name PortalLink
     */
    public String getPortalLink() {
        return (String) getAttributeInternal(PORTALLINK);
    }

    /**Sets <code>value</code> as attribute value for PORTAL_LINK using the alias name PortalLink
     */
    public void setPortalLink(String value) {
        setAttributeInternal(PORTALLINK, value);
    }

    /**Gets the attribute value for ORDEN_DE_COMPRA_C using the alias name OrdenDeCompraC
     */
    public String getOrdenDeCompraC() {
        return (String) getAttributeInternal(ORDENDECOMPRAC);
    }

    /**Sets <code>value</code> as attribute value for ORDEN_DE_COMPRA_C using the alias name OrdenDeCompraC
     */
    public void setOrdenDeCompraC(String value) {
        setAttributeInternal(ORDENDECOMPRAC, value);
    }


    /**Gets the attribute value for CONTRATO_C using the alias name ContratoC
     */
    public String getContratoC() {
        return (String) getAttributeInternal(CONTRATOC);
    }

    /**Sets <code>value</code> as attribute value for CONTRATO_C using the alias name ContratoC
     */
    public void setContratoC(String value) {
        setAttributeInternal(CONTRATOC, value);
    }


    /**Gets the attribute value for VIGENCIA_CONTRATO using the alias name VigenciaContrato
     */
    public String getVigenciaContrato() {
        return (String) getAttributeInternal(VIGENCIACONTRATO);
    }

    /**Sets <code>value</code> as attribute value for VIGENCIA_CONTRATO using the alias name VigenciaContrato
     */
    public void setVigenciaContrato(String value) {
        setAttributeInternal(VIGENCIACONTRATO, value);
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
        case NOMBREDELBANCOD:
            return getNombreDelBancoD();
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
        case NOMBREDELBANCOD:
            setNombreDelBancoD((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for DOMINGO using the alias name Domingo
     */
    public String getDomingo() {
        return (String) getAttributeInternal(DOMINGO);
    }

    /**Sets <code>value</code> as attribute value for DOMINGO using the alias name Domingo
     */
    public void setDomingo(String value) {
        setAttributeInternal(DOMINGO, value);
    }

    /**Gets the attribute value for JUEVES using the alias name Jueves
     */
    public String getJueves() {
        return (String) getAttributeInternal(JUEVES);
    }

    /**Sets <code>value</code> as attribute value for JUEVES using the alias name Jueves
     */
    public void setJueves(String value) {
        setAttributeInternal(JUEVES, value);
    }

    /**Gets the attribute value for LUNES using the alias name Lunes
     */
    public String getLunes() {
        return (String) getAttributeInternal(LUNES);
    }

    /**Sets <code>value</code> as attribute value for LUNES using the alias name Lunes
     */
    public void setLunes(String value) {
        setAttributeInternal(LUNES, value);
    }

    /**Gets the attribute value for MARTES using the alias name Martes
     */
    public String getMartes() {
        return (String) getAttributeInternal(MARTES);
    }

    /**Sets <code>value</code> as attribute value for MARTES using the alias name Martes
     */
    public void setMartes(String value) {
        setAttributeInternal(MARTES, value);
    }

    /**Gets the attribute value for MIERCOLES using the alias name Miercoles
     */
    public String getMiercoles() {
        return (String) getAttributeInternal(MIERCOLES);
    }

    /**Sets <code>value</code> as attribute value for MIERCOLES using the alias name Miercoles
     */
    public void setMiercoles(String value) {
        setAttributeInternal(MIERCOLES, value);
    }

    /**Gets the attribute value for SABADO using the alias name Sabado
     */
    public String getSabado() {
        return (String) getAttributeInternal(SABADO);
    }

    /**Sets <code>value</code> as attribute value for SABADO using the alias name Sabado
     */
    public void setSabado(String value) {
        setAttributeInternal(SABADO, value);
    }

    /**Gets the attribute value for VIERNES using the alias name Viernes
     */
    public String getViernes() {
        return (String) getAttributeInternal(VIERNES);
    }

    /**Sets <code>value</code> as attribute value for VIERNES using the alias name Viernes
     */
    public void setViernes(String value) {
        setAttributeInternal(VIERNES, value);
    }

    /**Gets the attribute value for CE_ENVIO_FACTURAS using the alias name CeEnvioFacturas
     */
    public String getCeEnvioFacturas() {
        return (String) getAttributeInternal(CEENVIOFACTURAS);
    }

    /**Sets <code>value</code> as attribute value for CE_ENVIO_FACTURAS using the alias name CeEnvioFacturas
     */
    public void setCeEnvioFacturas(String value) {
        setAttributeInternal(CEENVIOFACTURAS, value);
    }

    /**Gets the attribute value for the calculated attribute NombreDelBancoD
     */
    public String getNombreDelBancoD() {
        return (String) getAttributeInternal(NOMBREDELBANCOD);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute NombreDelBancoD
     */
    public void setNombreDelBancoD(String value) {
        setAttributeInternal(NOMBREDELBANCOD, value);
    }
}

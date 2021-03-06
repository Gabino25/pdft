package xxqp.oracle.apps.ar.pdft.altafitec.bpo.schema.server;

import oracle.apps.fnd.framework.server.OAEntityDefImpl;
import oracle.apps.fnd.framework.server.OAEntityImpl;

import oracle.jbo.Key;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftBpoServicioEOImpl extends OAEntityImpl {
    public static final int ID = 0;
    public static final int BPOHEADERID = 1;
    public static final int COBRANZA = 2;
    public static final int VENTAS = 3;
    public static final int GESTORADMINISTRATIVO = 4;
    public static final int TECNICO = 5;
    public static final int RECOLEDOCMATER = 6;
    public static final int ENTREGAADOMICILIO = 7;
    public static final int OTROS = 8;
    public static final int FECHAINICIOSERVICIO = 9;
    public static final int ELMENSAJEROMANEJEC = 10;
    public static final int DIASSEMANALABORARA = 11;
    public static final int NOHRSDIARIAS = 12;
    public static final int HORARIODETRABAJO = 13;
    public static final int DIRECCIONBASE = 14;
    public static final int OBSERVACIONES = 15;
    public static final int CREATEDBY = 16;
    public static final int CREATIONDATE = 17;
    public static final int LASTUPDATEDBY = 18;
    public static final int LASTUPDATEDATE = 19;
    public static final int LASTUPDATELOGIN = 20;
    public static final int ATTRIBUTECATEGORY = 21;
    public static final int ATTRIBUTE1 = 22;
    public static final int ATTRIBUTE2 = 23;
    public static final int ATTRIBUTE3 = 24;
    public static final int ATTRIBUTE4 = 25;
    public static final int ATTRIBUTE5 = 26;
    public static final int HORAINICIO = 27;
    public static final int HORAFINAL = 28;
    public static final int DOMINGO = 29;
    public static final int JUEVES = 30;
    public static final int LUNES = 31;
    public static final int MARTES = 32;
    public static final int MIERCOLES = 33;
    public static final int SABADO = 34;
    public static final int VIERNES = 35;
    public static final int OTROSILIM = 36;
    public static final int DIRECCIONBASEILIM = 37;
    public static final int OBSERVACIONESILIM = 38;
    public static final int XXQPPDFTBPOHEADEREO = 39;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftBpoServicioEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("xxqp.oracle.apps.ar.pdft.altafitec.bpo.schema.server.XxqpPdftBpoServicioEO");
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

    /**Gets the attribute value for BpoHeaderId, using the alias name BpoHeaderId
     */
    public Number getBpoHeaderId() {
        return (Number)getAttributeInternal(BPOHEADERID);
    }

    /**Sets <code>value</code> as the attribute value for BpoHeaderId
     */
    public void setBpoHeaderId(Number value) {
        setAttributeInternal(BPOHEADERID, value);
    }

    /**Gets the attribute value for Cobranza, using the alias name Cobranza
     */
    public String getCobranza() {
        return (String)getAttributeInternal(COBRANZA);
    }

    /**Sets <code>value</code> as the attribute value for Cobranza
     */
    public void setCobranza(String value) {
        setAttributeInternal(COBRANZA, value);
    }

    /**Gets the attribute value for Ventas, using the alias name Ventas
     */
    public String getVentas() {
        return (String)getAttributeInternal(VENTAS);
    }

    /**Sets <code>value</code> as the attribute value for Ventas
     */
    public void setVentas(String value) {
        setAttributeInternal(VENTAS, value);
    }

    /**Gets the attribute value for GestorAdministrativo, using the alias name GestorAdministrativo
     */
    public String getGestorAdministrativo() {
        return (String)getAttributeInternal(GESTORADMINISTRATIVO);
    }

    /**Sets <code>value</code> as the attribute value for GestorAdministrativo
     */
    public void setGestorAdministrativo(String value) {
        setAttributeInternal(GESTORADMINISTRATIVO, value);
    }

    /**Gets the attribute value for Tecnico, using the alias name Tecnico
     */
    public String getTecnico() {
        return (String)getAttributeInternal(TECNICO);
    }

    /**Sets <code>value</code> as the attribute value for Tecnico
     */
    public void setTecnico(String value) {
        setAttributeInternal(TECNICO, value);
    }

    /**Gets the attribute value for RecoleDocMater, using the alias name RecoleDocMater
     */
    public String getRecoleDocMater() {
        return (String)getAttributeInternal(RECOLEDOCMATER);
    }

    /**Sets <code>value</code> as the attribute value for RecoleDocMater
     */
    public void setRecoleDocMater(String value) {
        setAttributeInternal(RECOLEDOCMATER, value);
    }

    /**Gets the attribute value for EntregaADomicilio, using the alias name EntregaADomicilio
     */
    public String getEntregaADomicilio() {
        return (String)getAttributeInternal(ENTREGAADOMICILIO);
    }

    /**Sets <code>value</code> as the attribute value for EntregaADomicilio
     */
    public void setEntregaADomicilio(String value) {
        setAttributeInternal(ENTREGAADOMICILIO, value);
    }

    /**Gets the attribute value for Otros, using the alias name Otros
     */
    public String getOtros() {
        return (String)getAttributeInternal(OTROS);
    }

    /**Sets <code>value</code> as the attribute value for Otros
     */
    public void setOtros(String value) {
        setAttributeInternal(OTROS, value);
    }

    /**Gets the attribute value for FechaInicioServicio, using the alias name FechaInicioServicio
     */
    public Date getFechaInicioServicio() {
        return (Date)getAttributeInternal(FECHAINICIOSERVICIO);
    }

    /**Sets <code>value</code> as the attribute value for FechaInicioServicio
     */
    public void setFechaInicioServicio(Date value) {
        setAttributeInternal(FECHAINICIOSERVICIO, value);
    }

    /**Gets the attribute value for ElMensajeroManejeC, using the alias name ElMensajeroManejeC
     */
    public String getElMensajeroManejeC() {
        return (String)getAttributeInternal(ELMENSAJEROMANEJEC);
    }

    /**Sets <code>value</code> as the attribute value for ElMensajeroManejeC
     */
    public void setElMensajeroManejeC(String value) {
        setAttributeInternal(ELMENSAJEROMANEJEC, value);
    }

    /**Gets the attribute value for DiasSemanaLaborara, using the alias name DiasSemanaLaborara
     */
    public String getDiasSemanaLaborara() {
        return (String)getAttributeInternal(DIASSEMANALABORARA);
    }

    /**Sets <code>value</code> as the attribute value for DiasSemanaLaborara
     */
    public void setDiasSemanaLaborara(String value) {
        setAttributeInternal(DIASSEMANALABORARA, value);
    }

    /**Gets the attribute value for NoHrsDiarias, using the alias name NoHrsDiarias
     */
    public String getNoHrsDiarias() {
        return (String)getAttributeInternal(NOHRSDIARIAS);
    }

    /**Sets <code>value</code> as the attribute value for NoHrsDiarias
     */
    public void setNoHrsDiarias(String value) {
        setAttributeInternal(NOHRSDIARIAS, value);
    }

    /**Gets the attribute value for HorarioDeTrabajo, using the alias name HorarioDeTrabajo
     */
    public String getHorarioDeTrabajo() {
        return (String)getAttributeInternal(HORARIODETRABAJO);
    }

    /**Sets <code>value</code> as the attribute value for HorarioDeTrabajo
     */
    public void setHorarioDeTrabajo(String value) {
        setAttributeInternal(HORARIODETRABAJO, value);
    }

    /**Gets the attribute value for DireccionBase, using the alias name DireccionBase
     */
    public String getDireccionBase() {
        return (String)getAttributeInternal(DIRECCIONBASE);
    }

    /**Sets <code>value</code> as the attribute value for DireccionBase
     */
    public void setDireccionBase(String value) {
        setAttributeInternal(DIRECCIONBASE, value);
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
        case BPOHEADERID:
            return getBpoHeaderId();
        case COBRANZA:
            return getCobranza();
        case VENTAS:
            return getVentas();
        case GESTORADMINISTRATIVO:
            return getGestorAdministrativo();
        case TECNICO:
            return getTecnico();
        case RECOLEDOCMATER:
            return getRecoleDocMater();
        case ENTREGAADOMICILIO:
            return getEntregaADomicilio();
        case OTROS:
            return getOtros();
        case FECHAINICIOSERVICIO:
            return getFechaInicioServicio();
        case ELMENSAJEROMANEJEC:
            return getElMensajeroManejeC();
        case DIASSEMANALABORARA:
            return getDiasSemanaLaborara();
        case NOHRSDIARIAS:
            return getNoHrsDiarias();
        case HORARIODETRABAJO:
            return getHorarioDeTrabajo();
        case DIRECCIONBASE:
            return getDireccionBase();
        case OBSERVACIONES:
            return getObservaciones();
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
        case HORAINICIO:
            return getHoraInicio();
        case HORAFINAL:
            return getHoraFinal();
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
        case OTROSILIM:
            return getOtrosIlim();
        case DIRECCIONBASEILIM:
            return getDireccionBaseIlim();
        case OBSERVACIONESILIM:
            return getObservacionesIlim();
        case XXQPPDFTBPOHEADEREO:
            return getXxqpPdftBpoHeaderEO();
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
        case BPOHEADERID:
            setBpoHeaderId((Number)value);
            return;
        case COBRANZA:
            setCobranza((String)value);
            return;
        case VENTAS:
            setVentas((String)value);
            return;
        case GESTORADMINISTRATIVO:
            setGestorAdministrativo((String)value);
            return;
        case TECNICO:
            setTecnico((String)value);
            return;
        case RECOLEDOCMATER:
            setRecoleDocMater((String)value);
            return;
        case ENTREGAADOMICILIO:
            setEntregaADomicilio((String)value);
            return;
        case OTROS:
            setOtros((String)value);
            return;
        case FECHAINICIOSERVICIO:
            setFechaInicioServicio((Date)value);
            return;
        case ELMENSAJEROMANEJEC:
            setElMensajeroManejeC((String)value);
            return;
        case DIASSEMANALABORARA:
            setDiasSemanaLaborara((String)value);
            return;
        case NOHRSDIARIAS:
            setNoHrsDiarias((String)value);
            return;
        case HORARIODETRABAJO:
            setHorarioDeTrabajo((String)value);
            return;
        case DIRECCIONBASE:
            setDireccionBase((String)value);
            return;
        case OBSERVACIONES:
            setObservaciones((String)value);
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
        case HORAINICIO:
            setHoraInicio((String)value);
            return;
        case HORAFINAL:
            setHoraFinal((String)value);
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
        case OTROSILIM:
            setOtrosIlim((ClobDomain)value);
            return;
        case DIRECCIONBASEILIM:
            setDireccionBaseIlim((ClobDomain)value);
            return;
        case OBSERVACIONESILIM:
            setObservacionesIlim((ClobDomain)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the associated entity XxqpPdftBpoHeaderEOImpl
     */
    public XxqpPdftBpoHeaderEOImpl getXxqpPdftBpoHeaderEO() {
        return (XxqpPdftBpoHeaderEOImpl)getAttributeInternal(XXQPPDFTBPOHEADEREO);
    }

    /**Sets <code>value</code> as the associated entity XxqpPdftBpoHeaderEOImpl
     */
    public void setXxqpPdftBpoHeaderEO(XxqpPdftBpoHeaderEOImpl value) {
        setAttributeInternal(XXQPPDFTBPOHEADEREO, value);
    }

    /**Gets the attribute value for HoraInicio, using the alias name HoraInicio
     */
    public String getHoraInicio() {
        return (String)getAttributeInternal(HORAINICIO);
    }

    /**Sets <code>value</code> as the attribute value for HoraInicio
     */
    public void setHoraInicio(String value) {
        setAttributeInternal(HORAINICIO, value);
    }

    /**Gets the attribute value for HoraFinal, using the alias name HoraFinal
     */
    public String getHoraFinal() {
        return (String)getAttributeInternal(HORAFINAL);
    }

    /**Sets <code>value</code> as the attribute value for HoraFinal
     */
    public void setHoraFinal(String value) {
        setAttributeInternal(HORAFINAL, value);
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

    /**Gets the attribute value for OtrosIlim, using the alias name OtrosIlim
     */
    public ClobDomain getOtrosIlim() {
        return (ClobDomain)getAttributeInternal(OTROSILIM);
    }

    /**Sets <code>value</code> as the attribute value for OtrosIlim
     */
    public void setOtrosIlim(ClobDomain value) {
        setAttributeInternal(OTROSILIM, value);
    }

    /**Gets the attribute value for DireccionBaseIlim, using the alias name DireccionBaseIlim
     */
    public ClobDomain getDireccionBaseIlim() {
        return (ClobDomain)getAttributeInternal(DIRECCIONBASEILIM);
    }

    /**Sets <code>value</code> as the attribute value for DireccionBaseIlim
     */
    public void setDireccionBaseIlim(ClobDomain value) {
        setAttributeInternal(DIRECCIONBASEILIM, value);
    }

    /**Gets the attribute value for ObservacionesIlim, using the alias name ObservacionesIlim
     */
    public ClobDomain getObservacionesIlim() {
        return (ClobDomain)getAttributeInternal(OBSERVACIONESILIM);
    }

    /**Sets <code>value</code> as the attribute value for ObservacionesIlim
     */
    public void setObservacionesIlim(ClobDomain value) {
        setAttributeInternal(OBSERVACIONESILIM, value);
    }

    /**Creates a Key object based on given key constituents
     */
    public static Key createPrimaryKey(Number id) {
        return new Key(new Object[]{id});
    }
}

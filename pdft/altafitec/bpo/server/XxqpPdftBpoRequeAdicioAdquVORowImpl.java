package xxqp.oracle.apps.ar.pdft.altafitec.bpo.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;

import xxqp.oracle.apps.ar.pdft.altafitec.bpo.schema.server.XxqpPdftBpoRequeAdicioEOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftBpoRequeAdicioAdquVORowImpl extends OAViewRowImpl {
    public static final int ID = 0;
    public static final int BPOHEADERID = 1;
    public static final int CATEGORIA = 2;
    public static final int MOTO125 = 3;
    public static final int CASCO = 4;
    public static final int CAJAGRANDE = 5;
    public static final int CAJACHICAROJA = 6;
    public static final int CAMISAS = 7;
    public static final int IMPERMEABLE = 8;
    public static final int TARJETAGASOLINA = 9;
    public static final int PANTALONVESTIR = 10;
    public static final int CAJANEGRA = 11;
    public static final int HIELERA = 12;
    public static final int MOVIL = 13;
    public static final int AUTOMOVIL = 14;
    public static final int GUIAROJI = 15;
    public static final int OTROS = 16;
    public static final int COMENTARIOS = 17;
    public static final int CREATEDBY = 18;
    public static final int CREATIONDATE = 19;
    public static final int LASTUPDATEDBY = 20;
    public static final int LASTUPDATEDATE = 21;
    public static final int LASTUPDATELOGIN = 22;
    public static final int ATTRIBUTECATEGORY = 23;
    public static final int ATTRIBUTE1 = 24;
    public static final int ATTRIBUTE2 = 25;
    public static final int ATTRIBUTE3 = 26;
    public static final int ATTRIBUTE4 = 27;
    public static final int ATTRIBUTE5 = 28;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftBpoRequeAdicioAdquVORowImpl() {
    }

    /**Gets XxqpPdftBpoRequeAdicioEO entity object.
     */
    public XxqpPdftBpoRequeAdicioEOImpl getXxqpPdftBpoRequeAdicioEO() {
        return (XxqpPdftBpoRequeAdicioEOImpl)getEntity(0);
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

    /**Gets the attribute value for BPO_HEADER_ID using the alias name BpoHeaderId
     */
    public Number getBpoHeaderId() {
        return (Number) getAttributeInternal(BPOHEADERID);
    }

    /**Sets <code>value</code> as attribute value for BPO_HEADER_ID using the alias name BpoHeaderId
     */
    public void setBpoHeaderId(Number value) {
        setAttributeInternal(BPOHEADERID, value);
    }

    /**Gets the attribute value for CATEGORIA using the alias name Categoria
     */
    public String getCategoria() {
        return (String) getAttributeInternal(CATEGORIA);
    }

    /**Sets <code>value</code> as attribute value for CATEGORIA using the alias name Categoria
     */
    public void setCategoria(String value) {
        setAttributeInternal(CATEGORIA, value);
    }

    /**Gets the attribute value for MOTO_125 using the alias name Moto125
     */
    public String getMoto125() {
        return (String) getAttributeInternal(MOTO125);
    }

    /**Sets <code>value</code> as attribute value for MOTO_125 using the alias name Moto125
     */
    public void setMoto125(String value) {
        setAttributeInternal(MOTO125, value);
    }

    /**Gets the attribute value for CASCO using the alias name Casco
     */
    public String getCasco() {
        return (String) getAttributeInternal(CASCO);
    }

    /**Sets <code>value</code> as attribute value for CASCO using the alias name Casco
     */
    public void setCasco(String value) {
        setAttributeInternal(CASCO, value);
    }

    /**Gets the attribute value for CAJA_GRANDE using the alias name CajaGrande
     */
    public String getCajaGrande() {
        return (String) getAttributeInternal(CAJAGRANDE);
    }

    /**Sets <code>value</code> as attribute value for CAJA_GRANDE using the alias name CajaGrande
     */
    public void setCajaGrande(String value) {
        setAttributeInternal(CAJAGRANDE, value);
    }

    /**Gets the attribute value for CAJA_CHICA_ROJA using the alias name CajaChicaRoja
     */
    public String getCajaChicaRoja() {
        return (String) getAttributeInternal(CAJACHICAROJA);
    }

    /**Sets <code>value</code> as attribute value for CAJA_CHICA_ROJA using the alias name CajaChicaRoja
     */
    public void setCajaChicaRoja(String value) {
        setAttributeInternal(CAJACHICAROJA, value);
    }

    /**Gets the attribute value for CAMISAS using the alias name Camisas
     */
    public String getCamisas() {
        return (String) getAttributeInternal(CAMISAS);
    }

    /**Sets <code>value</code> as attribute value for CAMISAS using the alias name Camisas
     */
    public void setCamisas(String value) {
        setAttributeInternal(CAMISAS, value);
    }

    /**Gets the attribute value for IMPERMEABLE using the alias name Impermeable
     */
    public String getImpermeable() {
        return (String) getAttributeInternal(IMPERMEABLE);
    }

    /**Sets <code>value</code> as attribute value for IMPERMEABLE using the alias name Impermeable
     */
    public void setImpermeable(String value) {
        setAttributeInternal(IMPERMEABLE, value);
    }

    /**Gets the attribute value for TARJETA_GASOLINA using the alias name TarjetaGasolina
     */
    public String getTarjetaGasolina() {
        return (String) getAttributeInternal(TARJETAGASOLINA);
    }

    /**Sets <code>value</code> as attribute value for TARJETA_GASOLINA using the alias name TarjetaGasolina
     */
    public void setTarjetaGasolina(String value) {
        setAttributeInternal(TARJETAGASOLINA, value);
    }

    /**Gets the attribute value for PANTALON_VESTIR using the alias name PantalonVestir
     */
    public String getPantalonVestir() {
        return (String) getAttributeInternal(PANTALONVESTIR);
    }

    /**Sets <code>value</code> as attribute value for PANTALON_VESTIR using the alias name PantalonVestir
     */
    public void setPantalonVestir(String value) {
        setAttributeInternal(PANTALONVESTIR, value);
    }

    /**Gets the attribute value for CAJA_NEGRA using the alias name CajaNegra
     */
    public String getCajaNegra() {
        return (String) getAttributeInternal(CAJANEGRA);
    }

    /**Sets <code>value</code> as attribute value for CAJA_NEGRA using the alias name CajaNegra
     */
    public void setCajaNegra(String value) {
        setAttributeInternal(CAJANEGRA, value);
    }

    /**Gets the attribute value for HIELERA using the alias name Hielera
     */
    public String getHielera() {
        return (String) getAttributeInternal(HIELERA);
    }

    /**Sets <code>value</code> as attribute value for HIELERA using the alias name Hielera
     */
    public void setHielera(String value) {
        setAttributeInternal(HIELERA, value);
    }

    /**Gets the attribute value for MOVIL using the alias name Movil
     */
    public String getMovil() {
        return (String) getAttributeInternal(MOVIL);
    }

    /**Sets <code>value</code> as attribute value for MOVIL using the alias name Movil
     */
    public void setMovil(String value) {
        setAttributeInternal(MOVIL, value);
    }

    /**Gets the attribute value for AUTOMOVIL using the alias name Automovil
     */
    public String getAutomovil() {
        return (String) getAttributeInternal(AUTOMOVIL);
    }

    /**Sets <code>value</code> as attribute value for AUTOMOVIL using the alias name Automovil
     */
    public void setAutomovil(String value) {
        setAttributeInternal(AUTOMOVIL, value);
    }

    /**Gets the attribute value for GUIA_ROJI using the alias name GuiaRoji
     */
    public String getGuiaRoji() {
        return (String) getAttributeInternal(GUIAROJI);
    }

    /**Sets <code>value</code> as attribute value for GUIA_ROJI using the alias name GuiaRoji
     */
    public void setGuiaRoji(String value) {
        setAttributeInternal(GUIAROJI, value);
    }

    /**Gets the attribute value for OTROS using the alias name Otros
     */
    public String getOtros() {
        return (String) getAttributeInternal(OTROS);
    }

    /**Sets <code>value</code> as attribute value for OTROS using the alias name Otros
     */
    public void setOtros(String value) {
        setAttributeInternal(OTROS, value);
    }

    /**Gets the attribute value for COMENTARIOS using the alias name Comentarios
     */
    public String getComentarios() {
        return (String) getAttributeInternal(COMENTARIOS);
    }

    /**Sets <code>value</code> as attribute value for COMENTARIOS using the alias name Comentarios
     */
    public void setComentarios(String value) {
        setAttributeInternal(COMENTARIOS, value);
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
        case BPOHEADERID:
            return getBpoHeaderId();
        case CATEGORIA:
            return getCategoria();
        case MOTO125:
            return getMoto125();
        case CASCO:
            return getCasco();
        case CAJAGRANDE:
            return getCajaGrande();
        case CAJACHICAROJA:
            return getCajaChicaRoja();
        case CAMISAS:
            return getCamisas();
        case IMPERMEABLE:
            return getImpermeable();
        case TARJETAGASOLINA:
            return getTarjetaGasolina();
        case PANTALONVESTIR:
            return getPantalonVestir();
        case CAJANEGRA:
            return getCajaNegra();
        case HIELERA:
            return getHielera();
        case MOVIL:
            return getMovil();
        case AUTOMOVIL:
            return getAutomovil();
        case GUIAROJI:
            return getGuiaRoji();
        case OTROS:
            return getOtros();
        case COMENTARIOS:
            return getComentarios();
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
        case CATEGORIA:
            setCategoria((String)value);
            return;
        case MOTO125:
            setMoto125((String)value);
            return;
        case CASCO:
            setCasco((String)value);
            return;
        case CAJAGRANDE:
            setCajaGrande((String)value);
            return;
        case CAJACHICAROJA:
            setCajaChicaRoja((String)value);
            return;
        case CAMISAS:
            setCamisas((String)value);
            return;
        case IMPERMEABLE:
            setImpermeable((String)value);
            return;
        case TARJETAGASOLINA:
            setTarjetaGasolina((String)value);
            return;
        case PANTALONVESTIR:
            setPantalonVestir((String)value);
            return;
        case CAJANEGRA:
            setCajaNegra((String)value);
            return;
        case HIELERA:
            setHielera((String)value);
            return;
        case MOVIL:
            setMovil((String)value);
            return;
        case AUTOMOVIL:
            setAutomovil((String)value);
            return;
        case GUIAROJI:
            setGuiaRoji((String)value);
            return;
        case OTROS:
            setOtros((String)value);
            return;
        case COMENTARIOS:
            setComentarios((String)value);
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
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }
}

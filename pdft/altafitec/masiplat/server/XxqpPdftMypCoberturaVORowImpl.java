package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;

import xxqp.oracle.apps.ar.pdft.altafitec.masiplat.schema.server.XxqpPdftMypCoberturaEOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftMypCoberturaVORowImpl extends OAViewRowImpl {
    public static final int ID = 0;
    public static final int MYPHEADERID = 1;
    public static final int PLAZAPROPIETARIAC = 2;
    public static final int MENCIONARESTADOS = 3;
    public static final int ENTREGALOCAL = 4;
    public static final int DRLOCAL = 5;
    public static final int DILOCAL = 6;
    public static final int ENTREGAFORANEO = 7;
    public static final int DRFORANEO = 8;
    public static final int DIFORANEO = 9;
    public static final int CREATEDBY = 10;
    public static final int CREATIONDATE = 11;
    public static final int LASTUPDATEDBY = 12;
    public static final int LASTUPDATEDATE = 13;
    public static final int LASTUPDATELOGIN = 14;
    public static final int ATTRIBUTECATEGORY = 15;
    public static final int ATTRIBUTE1 = 16;
    public static final int ATTRIBUTE2 = 17;
    public static final int ATTRIBUTE3 = 18;
    public static final int ATTRIBUTE4 = 19;
    public static final int ATTRIBUTE5 = 20;
    public static final int TCLOCAL = 21;
    public static final int TCNACIONAL = 22;
    public static final int TCREGIONAL = 23;
    public static final int COMENTARIOS = 24;

    /**This is the default constructor (do not remove)
     */
    public XxqpPdftMypCoberturaVORowImpl() {
    }

    /**Gets XxqpPdftMypCoberturaEO entity object.
     */
    public XxqpPdftMypCoberturaEOImpl getXxqpPdftMypCoberturaEO() {
        return (XxqpPdftMypCoberturaEOImpl)getEntity(0);
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

    /**Gets the attribute value for MYP_HEADER_ID using the alias name MypHeaderId
     */
    public Number getMypHeaderId() {
        return (Number) getAttributeInternal(MYPHEADERID);
    }

    /**Sets <code>value</code> as attribute value for MYP_HEADER_ID using the alias name MypHeaderId
     */
    public void setMypHeaderId(Number value) {
        setAttributeInternal(MYPHEADERID, value);
    }

    /**Gets the attribute value for PLAZA_PROPIETARIA_C using the alias name PlazaPropietariaC
     */
    public String getPlazaPropietariaC() {
        return (String) getAttributeInternal(PLAZAPROPIETARIAC);
    }

    /**Sets <code>value</code> as attribute value for PLAZA_PROPIETARIA_C using the alias name PlazaPropietariaC
     */
    public void setPlazaPropietariaC(String value) {
        setAttributeInternal(PLAZAPROPIETARIAC, value);
    }


    /**Gets the attribute value for MENCIONAR_ESTADOS using the alias name MencionarEstados
     */
    public String getMencionarEstados() {
        return (String) getAttributeInternal(MENCIONARESTADOS);
    }

    /**Sets <code>value</code> as attribute value for MENCIONAR_ESTADOS using the alias name MencionarEstados
     */
    public void setMencionarEstados(String value) {
        setAttributeInternal(MENCIONARESTADOS, value);
    }

    /**Gets the attribute value for ENTREGA_LOCAL using the alias name EntregaLocal
     */
    public Number getEntregaLocal() {
        return (Number) getAttributeInternal(ENTREGALOCAL);
    }

    /**Sets <code>value</code> as attribute value for ENTREGA_LOCAL using the alias name EntregaLocal
     */
    public void setEntregaLocal(Number value) {
        setAttributeInternal(ENTREGALOCAL, value);
    }

    /**Gets the attribute value for DR_LOCAL using the alias name DrLocal
     */
    public Number getDrLocal() {
        return (Number) getAttributeInternal(DRLOCAL);
    }

    /**Sets <code>value</code> as attribute value for DR_LOCAL using the alias name DrLocal
     */
    public void setDrLocal(Number value) {
        setAttributeInternal(DRLOCAL, value);
    }

    /**Gets the attribute value for DI_LOCAL using the alias name DiLocal
     */
    public Number getDiLocal() {
        return (Number) getAttributeInternal(DILOCAL);
    }

    /**Sets <code>value</code> as attribute value for DI_LOCAL using the alias name DiLocal
     */
    public void setDiLocal(Number value) {
        setAttributeInternal(DILOCAL, value);
    }

    /**Gets the attribute value for ENTREGA_FORANEO using the alias name EntregaForaneo
     */
    public Number getEntregaForaneo() {
        return (Number) getAttributeInternal(ENTREGAFORANEO);
    }

    /**Sets <code>value</code> as attribute value for ENTREGA_FORANEO using the alias name EntregaForaneo
     */
    public void setEntregaForaneo(Number value) {
        setAttributeInternal(ENTREGAFORANEO, value);
    }

    /**Gets the attribute value for DR_FORANEO using the alias name DrForaneo
     */
    public Number getDrForaneo() {
        return (Number) getAttributeInternal(DRFORANEO);
    }

    /**Sets <code>value</code> as attribute value for DR_FORANEO using the alias name DrForaneo
     */
    public void setDrForaneo(Number value) {
        setAttributeInternal(DRFORANEO, value);
    }

    /**Gets the attribute value for DI_FORANEO using the alias name DiForaneo
     */
    public Number getDiForaneo() {
        return (Number) getAttributeInternal(DIFORANEO);
    }

    /**Sets <code>value</code> as attribute value for DI_FORANEO using the alias name DiForaneo
     */
    public void setDiForaneo(Number value) {
        setAttributeInternal(DIFORANEO, value);
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
        case MYPHEADERID:
            return getMypHeaderId();
        case PLAZAPROPIETARIAC:
            return getPlazaPropietariaC();
        case MENCIONARESTADOS:
            return getMencionarEstados();
        case ENTREGALOCAL:
            return getEntregaLocal();
        case DRLOCAL:
            return getDrLocal();
        case DILOCAL:
            return getDiLocal();
        case ENTREGAFORANEO:
            return getEntregaForaneo();
        case DRFORANEO:
            return getDrForaneo();
        case DIFORANEO:
            return getDiForaneo();
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
        case TCLOCAL:
            return getTcLocal();
        case TCNACIONAL:
            return getTcNacional();
        case TCREGIONAL:
            return getTcRegional();
        case COMENTARIOS:
            return getComentarios();
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
        case MYPHEADERID:
            setMypHeaderId((Number)value);
            return;
        case PLAZAPROPIETARIAC:
            setPlazaPropietariaC((String)value);
            return;
        case MENCIONARESTADOS:
            setMencionarEstados((String)value);
            return;
        case ENTREGALOCAL:
            setEntregaLocal((Number)value);
            return;
        case DRLOCAL:
            setDrLocal((Number)value);
            return;
        case DILOCAL:
            setDiLocal((Number)value);
            return;
        case ENTREGAFORANEO:
            setEntregaForaneo((Number)value);
            return;
        case DRFORANEO:
            setDrForaneo((Number)value);
            return;
        case DIFORANEO:
            setDiForaneo((Number)value);
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
        case TCLOCAL:
            setTcLocal((String)value);
            return;
        case TCNACIONAL:
            setTcNacional((String)value);
            return;
        case TCREGIONAL:
            setTcRegional((String)value);
            return;
        case COMENTARIOS:
            setComentarios((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for TC_LOCAL using the alias name TcLocal
     */
    public String getTcLocal() {
        return (String) getAttributeInternal(TCLOCAL);
    }

    /**Sets <code>value</code> as attribute value for TC_LOCAL using the alias name TcLocal
     */
    public void setTcLocal(String value) {
        setAttributeInternal(TCLOCAL, value);
    }

    /**Gets the attribute value for TC_NACIONAL using the alias name TcNacional
     */
    public String getTcNacional() {
        return (String) getAttributeInternal(TCNACIONAL);
    }

    /**Sets <code>value</code> as attribute value for TC_NACIONAL using the alias name TcNacional
     */
    public void setTcNacional(String value) {
        setAttributeInternal(TCNACIONAL, value);
    }

    /**Gets the attribute value for TC_REGIONAL using the alias name TcRegional
     */
    public String getTcRegional() {
        return (String) getAttributeInternal(TCREGIONAL);
    }

    /**Sets <code>value</code> as attribute value for TC_REGIONAL using the alias name TcRegional
     */
    public void setTcRegional(String value) {
        setAttributeInternal(TCREGIONAL, value);
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
}

package xxqp.oracle.apps.ar.pdft.altacliente.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;

import xxqp.oracle.apps.ar.pdft.altacliente.schema.server.XxqpPdftClientesHeaderEOImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftClientesHeaderVORowImpl extends OAViewRowImpl {
    public static final int ID = 0;
    public static final int NOMBRECLIENTE = 1;
    public static final int GIROEMPRESARIALC = 2;
    public static final int EMPRESAQUEFACTURAC = 3;
    public static final int TIPOOPERATIVOC = 4;
    public static final int TIPOADMINISTRATIVOC = 5;
    public static final int TIPOCOMERCIALC = 6;
    public static final int COMENTARIOS = 7;
    public static final int CREATEDBY = 8;
    public static final int CREATIONDATE = 9;
    public static final int LASTUPDATEDBY = 10;
    public static final int LASTUPDATEDATE = 11;
    public static final int LASTUPDATELOGIN = 12;
    public static final int ATTRIBUTECATEGORY = 13;
    public static final int ATTRIBUTE1 = 14;
    public static final int ATTRIBUTE2 = 15;
    public static final int ATTRIBUTE3 = 16;
    public static final int ATTRIBUTE4 = 17;
    public static final int ATTRIBUTE5 = 18;
    public static final int PARTYID = 19;
    public static final int RFC = 20;
    public static final int RAZONSOCIAL = 21;

    /**This is the default constructor (do not remove)
     */
  public XxqpPdftClientesHeaderVORowImpl()
  {
  }

  /**Gets XxqpPdftClientesHeaderEO entity object.
   */
  public XxqpPdftClientesHeaderEOImpl getXxqpPdftClientesHeaderEO()
  {
    return (XxqpPdftClientesHeaderEOImpl)getEntity(0);
  }

  /**Gets the attribute value for ID using the alias name Id
   */
  public Number getId()
  {
    return (Number) getAttributeInternal(ID);
  }

  /**Sets <code>value</code> as attribute value for ID using the alias name Id
   */
  public void setId(Number value)
  {
    setAttributeInternal(ID, value);
  }

  /**Gets the attribute value for NOMBRE_CLIENTE using the alias name NombreCliente
   */
  public String getNombreCliente()
  {
    return (String) getAttributeInternal(NOMBRECLIENTE);
  }

  /**Sets <code>value</code> as attribute value for NOMBRE_CLIENTE using the alias name NombreCliente
   */
  public void setNombreCliente(String value)
  {
    setAttributeInternal(NOMBRECLIENTE, value);
  }

  /**Gets the attribute value for GIRO_EMPRESARIAL_C using the alias name GiroEmpresarialC
   */
  public String getGiroEmpresarialC()
  {
    return (String) getAttributeInternal(GIROEMPRESARIALC);
  }

  /**Sets <code>value</code> as attribute value for GIRO_EMPRESARIAL_C using the alias name GiroEmpresarialC
   */
  public void setGiroEmpresarialC(String value)
  {
    setAttributeInternal(GIROEMPRESARIALC, value);
  }


    /**Gets the attribute value for EMPRESA_QUE_FACTURA_C using the alias name EmpresaQueFacturaC
     */
  public String getEmpresaQueFacturaC()
  {
    return (String) getAttributeInternal(EMPRESAQUEFACTURAC);
  }

  /**Sets <code>value</code> as attribute value for EMPRESA_QUE_FACTURA_C using the alias name EmpresaQueFacturaC
   */
  public void setEmpresaQueFacturaC(String value)
  {
    setAttributeInternal(EMPRESAQUEFACTURAC, value);
  }


    /**Gets the attribute value for TIPO_OPERATIVO_C using the alias name TipoOperativoC
     */
  public String getTipoOperativoC()
  {
    return (String) getAttributeInternal(TIPOOPERATIVOC);
  }

  /**Sets <code>value</code> as attribute value for TIPO_OPERATIVO_C using the alias name TipoOperativoC
   */
  public void setTipoOperativoC(String value)
  {
    setAttributeInternal(TIPOOPERATIVOC, value);
  }


    /**Gets the attribute value for TIPO_ADMINISTRATIVO_C using the alias name TipoAdministrativoC
     */
  public String getTipoAdministrativoC()
  {
    return (String) getAttributeInternal(TIPOADMINISTRATIVOC);
  }

  /**Sets <code>value</code> as attribute value for TIPO_ADMINISTRATIVO_C using the alias name TipoAdministrativoC
   */
  public void setTipoAdministrativoC(String value)
  {
    setAttributeInternal(TIPOADMINISTRATIVOC, value);
  }


    /**Gets the attribute value for TIPO_COMERCIAL_C using the alias name TipoComercialC
     */
  public String getTipoComercialC()
  {
    return (String) getAttributeInternal(TIPOCOMERCIALC);
  }

  /**Sets <code>value</code> as attribute value for TIPO_COMERCIAL_C using the alias name TipoComercialC
   */
  public void setTipoComercialC(String value)
  {
    setAttributeInternal(TIPOCOMERCIALC, value);
  }


    /**Gets the attribute value for COMENTARIOS using the alias name Comentarios
     */
  public String getComentarios()
  {
    return (String) getAttributeInternal(COMENTARIOS);
  }

  /**Sets <code>value</code> as attribute value for COMENTARIOS using the alias name Comentarios
   */
  public void setComentarios(String value)
  {
    setAttributeInternal(COMENTARIOS, value);
  }

  /**Gets the attribute value for CREATED_BY using the alias name CreatedBy
   */
  public Number getCreatedBy()
  {
    return (Number) getAttributeInternal(CREATEDBY);
  }

  /**Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy
   */
  public void setCreatedBy(Number value)
  {
    setAttributeInternal(CREATEDBY, value);
  }

  /**Gets the attribute value for CREATION_DATE using the alias name CreationDate
   */
  public Date getCreationDate()
  {
    return (Date) getAttributeInternal(CREATIONDATE);
  }

  /**Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate
   */
  public void setCreationDate(Date value)
  {
    setAttributeInternal(CREATIONDATE, value);
  }

  /**Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy
   */
  public Number getLastUpdatedBy()
  {
    return (Number) getAttributeInternal(LASTUPDATEDBY);
  }

  /**Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy
   */
  public void setLastUpdatedBy(Number value)
  {
    setAttributeInternal(LASTUPDATEDBY, value);
  }

  /**Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate
   */
  public Date getLastUpdateDate()
  {
    return (Date) getAttributeInternal(LASTUPDATEDATE);
  }

  /**Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate
   */
  public void setLastUpdateDate(Date value)
  {
    setAttributeInternal(LASTUPDATEDATE, value);
  }

  /**Gets the attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin
   */
  public Number getLastUpdateLogin()
  {
    return (Number) getAttributeInternal(LASTUPDATELOGIN);
  }

  /**Sets <code>value</code> as attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin
   */
  public void setLastUpdateLogin(Number value)
  {
    setAttributeInternal(LASTUPDATELOGIN, value);
  }

  /**Gets the attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory
   */
  public String getAttributeCategory()
  {
    return (String) getAttributeInternal(ATTRIBUTECATEGORY);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE_CATEGORY using the alias name AttributeCategory
   */
  public void setAttributeCategory(String value)
  {
    setAttributeInternal(ATTRIBUTECATEGORY, value);
  }

  /**Gets the attribute value for ATTRIBUTE1 using the alias name Attribute1
   */
  public String getAttribute1()
  {
    return (String) getAttributeInternal(ATTRIBUTE1);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE1 using the alias name Attribute1
   */
  public void setAttribute1(String value)
  {
    setAttributeInternal(ATTRIBUTE1, value);
  }

  /**Gets the attribute value for ATTRIBUTE2 using the alias name Attribute2
   */
  public String getAttribute2()
  {
    return (String) getAttributeInternal(ATTRIBUTE2);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE2 using the alias name Attribute2
   */
  public void setAttribute2(String value)
  {
    setAttributeInternal(ATTRIBUTE2, value);
  }

  /**Gets the attribute value for ATTRIBUTE3 using the alias name Attribute3
   */
  public String getAttribute3()
  {
    return (String) getAttributeInternal(ATTRIBUTE3);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE3 using the alias name Attribute3
   */
  public void setAttribute3(String value)
  {
    setAttributeInternal(ATTRIBUTE3, value);
  }

  /**Gets the attribute value for ATTRIBUTE4 using the alias name Attribute4
   */
  public String getAttribute4()
  {
    return (String) getAttributeInternal(ATTRIBUTE4);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE4 using the alias name Attribute4
   */
  public void setAttribute4(String value)
  {
    setAttributeInternal(ATTRIBUTE4, value);
  }

  /**Gets the attribute value for ATTRIBUTE5 using the alias name Attribute5
   */
  public String getAttribute5()
  {
    return (String) getAttributeInternal(ATTRIBUTE5);
  }

  /**Sets <code>value</code> as attribute value for ATTRIBUTE5 using the alias name Attribute5
   */
  public void setAttribute5(String value)
  {
    setAttributeInternal(ATTRIBUTE5, value);
  }

  /**getAttrInvokeAccessor: generated method. Do not modify.
   */
  protected Object getAttrInvokeAccessor(int index, 
                                         AttributeDefImpl attrDef) throws Exception
  {
        switch (index) {
        case ID:
            return getId();
        case NOMBRECLIENTE:
            return getNombreCliente();
        case GIROEMPRESARIALC:
            return getGiroEmpresarialC();
        case EMPRESAQUEFACTURAC:
            return getEmpresaQueFacturaC();
        case TIPOOPERATIVOC:
            return getTipoOperativoC();
        case TIPOADMINISTRATIVOC:
            return getTipoAdministrativoC();
        case TIPOCOMERCIALC:
            return getTipoComercialC();
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
        case PARTYID:
            return getPartyId();
        case RFC:
            return getRfc();
        case RAZONSOCIAL:
            return getRazonSocial();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

  /**setAttrInvokeAccessor: generated method. Do not modify.
   */
  protected void setAttrInvokeAccessor(int index, Object value, 
                                       AttributeDefImpl attrDef) throws Exception
  {
        switch (index) {
        case ID:
            setId((Number)value);
            return;
        case NOMBRECLIENTE:
            setNombreCliente((String)value);
            return;
        case GIROEMPRESARIALC:
            setGiroEmpresarialC((String)value);
            return;
        case EMPRESAQUEFACTURAC:
            setEmpresaQueFacturaC((String)value);
            return;
        case TIPOOPERATIVOC:
            setTipoOperativoC((String)value);
            return;
        case TIPOADMINISTRATIVOC:
            setTipoAdministrativoC((String)value);
            return;
        case TIPOCOMERCIALC:
            setTipoComercialC((String)value);
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
        case PARTYID:
            setPartyId((Number)value);
            return;
        case RFC:
            setRfc((String)value);
            return;
        case RAZONSOCIAL:
            setRazonSocial((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
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

    /**Gets the attribute value for RFC using the alias name Rfc
     */
    public String getRfc() {
        return (String) getAttributeInternal(RFC);
    }

    /**Sets <code>value</code> as attribute value for RFC using the alias name Rfc
     */
    public void setRfc(String value) {
        setAttributeInternal(RFC, value);
    }

    /**Gets the attribute value for RAZON_SOCIAL using the alias name RazonSocial
     */
    public String getRazonSocial() {
        return (String) getAttributeInternal(RAZONSOCIAL);
    }

    /**Sets <code>value</code> as attribute value for RAZON_SOCIAL using the alias name RazonSocial
     */
    public void setRazonSocial(String value) {
        setAttributeInternal(RAZONSOCIAL, value);
    }
}

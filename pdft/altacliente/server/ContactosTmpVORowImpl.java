package xxqp.oracle.apps.ar.pdft.altacliente.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ContactosTmpVORowImpl extends OAViewRowImpl {
    public static final int RECORDID = 0;
    public static final int TIPODECONTACTO = 1;
    public static final int TIPODECONTACTOCODE = 2;
    public static final int NOMBRE = 3;
    public static final int DIRECCION = 4;
    public static final int TELEFONO = 5;
    public static final int CORREOELECTRONICO = 6;
    public static final int PUESTO = 7;
    public static final int NUMEROCELULAR = 8;
    public static final int CUMPLEANIOS = 9;
    public static final int ISREQUIRED = 10;

    /**This is the default constructor (do not remove)
     */
  public ContactosTmpVORowImpl()
  {
  }

  /**Gets the attribute value for the calculated attribute RecordId
   */
  public Number getRecordId()
  {
    return (Number) getAttributeInternal(RECORDID);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute RecordId
   */
  public void setRecordId(Number value)
  {
    setAttributeInternal(RECORDID, value);
  }

  /**Gets the attribute value for the calculated attribute TipoDeContacto
   */
  public String getTipoDeContacto()
  {
    return (String) getAttributeInternal(TIPODECONTACTO);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute TipoDeContacto
   */
  public void setTipoDeContacto(String value)
  {
    setAttributeInternal(TIPODECONTACTO, value);
  }

  /**Gets the attribute value for the calculated attribute Nombre
   */
  public String getNombre()
  {
    return (String) getAttributeInternal(NOMBRE);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute Nombre
   */
  public void setNombre(String value)
  {
    setAttributeInternal(NOMBRE, value);
  }

  /**Gets the attribute value for the calculated attribute Direccion
   */
  public String getDireccion()
  {
    return (String) getAttributeInternal(DIRECCION);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute Direccion
   */
  public void setDireccion(String value)
  {
    setAttributeInternal(DIRECCION, value);
  }

  /**Gets the attribute value for the calculated attribute Telefono
   */
  public String getTelefono()
  {
    return (String) getAttributeInternal(TELEFONO);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute Telefono
   */
  public void setTelefono(String value)
  {
    setAttributeInternal(TELEFONO, value);
  }

  /**Gets the attribute value for the calculated attribute CorreoElectronico
   */
  public String getCorreoElectronico()
  {
    return (String) getAttributeInternal(CORREOELECTRONICO);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute CorreoElectronico
   */
  public void setCorreoElectronico(String value)
  {
    setAttributeInternal(CORREOELECTRONICO, value);
  }

  /**Gets the attribute value for the calculated attribute Puesto
   */
  public String getPuesto()
  {
    return (String) getAttributeInternal(PUESTO);
  }

  /**Sets <code>value</code> as the attribute value for the calculated attribute Puesto
   */
  public void setPuesto(String value)
  {
    setAttributeInternal(PUESTO, value);
  }

  /**getAttrInvokeAccessor: generated method. Do not modify.
   */
  protected Object getAttrInvokeAccessor(int index, 
                                         AttributeDefImpl attrDef) throws Exception
  {
        switch (index) {
        case RECORDID:
            return getRecordId();
        case TIPODECONTACTO:
            return getTipoDeContacto();
        case TIPODECONTACTOCODE:
            return getTipoDeContactoCode();
        case NOMBRE:
            return getNombre();
        case DIRECCION:
            return getDireccion();
        case TELEFONO:
            return getTelefono();
        case CORREOELECTRONICO:
            return getCorreoElectronico();
        case PUESTO:
            return getPuesto();
        case NUMEROCELULAR:
            return getNumeroCelular();
        case CUMPLEANIOS:
            return getCumpleAnios();
        case ISREQUIRED:
            return getIsRequired();
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
        case TIPODECONTACTOCODE:
            setTipoDeContactoCode((String)value);
            return;
        case NOMBRE:
            setNombre((String)value);
            return;
        case DIRECCION:
            setDireccion((String)value);
            return;
        case TELEFONO:
            setTelefono((String)value);
            return;
        case CORREOELECTRONICO:
            setCorreoElectronico((String)value);
            return;
        case PUESTO:
            setPuesto((String)value);
            return;
        case NUMEROCELULAR:
            setNumeroCelular((String)value);
            return;
        case CUMPLEANIOS:
            setCumpleAnios((String)value);
            return;
        case ISREQUIRED:
            setIsRequired((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for the calculated attribute IsRequired
     */
    public String getIsRequired() {
        return (String) getAttributeInternal(ISREQUIRED);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute IsRequired
     */
    public void setIsRequired(String value) {
        setAttributeInternal(ISREQUIRED, value);
    }

    /**Gets the attribute value for the calculated attribute TipoDeContactoCode
     */
    public String getTipoDeContactoCode() {
        return (String) getAttributeInternal(TIPODECONTACTOCODE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute TipoDeContactoCode
     */
    public void setTipoDeContactoCode(String value) {
        setAttributeInternal(TIPODECONTACTOCODE, value);
    }

    /**Gets the attribute value for the calculated attribute NumeroCelular
     */
    public String getNumeroCelular() {
        return (String) getAttributeInternal(NUMEROCELULAR);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute NumeroCelular
     */
    public void setNumeroCelular(String value) {
        setAttributeInternal(NUMEROCELULAR, value);
    }

    /**Gets the attribute value for the calculated attribute CumpleAnios
     */
    public String getCumpleAnios() {
        return (String) getAttributeInternal(CUMPLEANIOS);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute CumpleAnios
     */
    public void setCumpleAnios(String value) {
        setAttributeInternal(CUMPLEANIOS, value);
    }
}

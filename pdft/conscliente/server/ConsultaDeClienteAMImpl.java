package xxqp.oracle.apps.ar.pdft.conscliente.server;

import java.sql.SQLException;
import java.sql.Types;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.server.OADBTransaction;

import oracle.jdbc.OracleCallableStatement;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ConsultaDeClienteAMImpl extends OAApplicationModuleImpl
{
  /**This is the default constructor (do not remove)
   */
  public ConsultaDeClienteAMImpl()
  {
  }

  /**Sample main for debugging Business Components code using the tester.
   */
  public static void main(String[] args)
  {
    launchTester("xxqp.oracle.apps.ar.pdft.conscliente.server", /* package name */
      "ConsultaDeClienteAMLocal" /* Configuration Name */);
  }

  /**Container's getter for ClientesInfoVO1
   */
  public ClientesInfoVOImpl getClientesInfoVO1()
  {
    return (ClientesInfoVOImpl)findViewObject("ClientesInfoVO1");
  }

  /**Container's getter for NombreDelClienteVO1
   */
  public NombreDelClienteVOImpl getNombreDelClienteVO1()
  {
    return (NombreDelClienteVOImpl)findViewObject("NombreDelClienteVO1");
  }

  /**Container's getter for RfcVO1
   */
  public RfcVOImpl getRfcVO1()
  {
    return (RfcVOImpl)findViewObject("RfcVO1");
  }

  /**Container's getter for RazonSocialVO1
   */
  public RazonSocialVOImpl getRazonSocialVO1()
  {
    return (RazonSocialVOImpl)findViewObject("RazonSocialVO1");
  }

  /**Container's getter for EstadoVO1
   */
  public EstadoVOImpl getEstadoVO1()
  {
    return (EstadoVOImpl)findViewObject("EstadoVO1");
  }

  public void filterClientesInfoVO(String pStrNombreDelCliente
                                 , String pStrRFC
                                 , String pStrRazonSocial
                                 , String pStrEstado
                                 , String pOperatingUnit)
  {
    ClientesInfoVOImpl  clientesInfoVO =getClientesInfoVO1();
    if(null!=clientesInfoVO){
      clientesInfoVO.filterConsultaCliente(pStrNombreDelCliente
                                          ,pStrRFC
                                          ,pStrRazonSocial
                                          ,pStrEstado
                                          ,pOperatingUnit
                                          );
    }
  }

  public void cleanClientesInfoVO()
  {
    ClientesInfoVOImpl  clientesInfoVO =getClientesInfoVO1();
    if(null!=clientesInfoVO){
      clientesInfoVO.cleanConsultaCliente();
    }
  }

    public String callFromOracleToPdft(String pStrPartyId
                                      ,String pOperatingUnit
                                      ,String pUserPdftId
                                      ) {
        String strErrmsg = null; 
        String strErrcode = null;
        String strClientesHeaderId= null;
        String strCallableStmt = " BEGIN " +
                                 " APPS.XXQP_PDFT_CLIENTES_FOTP_PKG.from_oracle_to_pdft ( PSO_ERRMSG                       => :1\n" + 
                                 "                                                      , PSO_ERRCODE                      => :2\n" + 
                                 "                                                      , pni_party_id                     => :3\n" + 
                                 "                                                      , pni_operating_unit               => :4\n" + 
                                 "                                                      , pni_operating_unit               => :5\n" + 
                                 "                                                      , pno_clientes_header_id           => :6 ); " +
                                 " END;";
        OADBTransaction oadbtransaction = (OADBTransaction)getTransaction();
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.setDouble(3,new Double(pStrPartyId));
            oraclecallablestatement.setDouble(4,new Double(pOperatingUnit));
            oraclecallablestatement.setString(5,pUserPdftId);
            oraclecallablestatement.registerOutParameter(6,Types.DOUBLE);
            oraclecallablestatement.execute();
            strErrmsg = oraclecallablestatement.getString(1); 
            strErrcode = oraclecallablestatement.getString(2); 
            strClientesHeaderId = ""+oraclecallablestatement.getDouble(5);
            System.out.println("strErrmsg:"+strErrmsg);
            System.out.println("strErrcode:"+strErrcode);
            System.out.println("strClientesHeaderId:"+strClientesHeaderId);
        } catch (SQLException sqle) {
            throw new OAException("SQLException en el metodo callFromOracleToPdft:"+sqle.getMessage()+", "+sqle.getErrorCode(),OAException.ERROR); 
        }
        
        if(null!=strErrmsg){
          throw new OAException(strErrmsg,OAException.ERROR);
        }
        
        return strClientesHeaderId;
        
    }

    public void ConsultarTodoClientesInfoVO() {
        ClientesInfoVOImpl  clientesInfoVO =getClientesInfoVO1();
        if(null!=clientesInfoVO){
            clientesInfoVO.executeQuery();
        }
    }

    public String[] validaClientesInfo(String pNombreDelCliente
                                 , String pRFC
                                 , String pRazonSocial
                                 , String pEstado
                                 , String pOperatingUnit
                                 ) {
        String[] retval = new String[2];
        String strErrmsg = null; 
        String strErrcode = null;
        String strCallableStmt = " BEGIN " +
                                 " APPS.XXQP_PDFT_CLIENTES_FOTP_PKG.valida_clientes ( PSO_ERRMSG                       => :1\n" + 
                                 "                                                  , PSO_ERRCODE                      => :2\n" + 
                                 "                                                  , psi_nombre_cliente               => :3\n" + 
                                 "                                                  , psi_rfc                          => :4\n" + 
                                 "                                                  , psi_razon_social                 => :5\n" +
                                 "                                                  , psi_estado                       => :6\n" +
                                 "                                                  , psi_operating_unit               => :7\n" +
                                 "                                                    ); " +
                                 " END;";
        OADBTransaction oadbtransaction = (OADBTransaction)getTransaction();
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.setString(3,pNombreDelCliente);
            oraclecallablestatement.setString(4,pRFC);
            oraclecallablestatement.setString(5,pRazonSocial);
            oraclecallablestatement.setString(6,pEstado);
            oraclecallablestatement.setString(7,pOperatingUnit);
            oraclecallablestatement.execute();
            strErrmsg = oraclecallablestatement.getString(1); 
            strErrcode = oraclecallablestatement.getString(2); 
            System.out.println("strErrmsg:"+strErrmsg);
            System.out.println("strErrcode:"+strErrcode);
            retval[0] = strErrmsg; 
            retval[1] = strErrcode; 
        } catch (SQLException sqle) {
            retval[0] = "SQLException en el metodo callFromOracleToPdft:"+sqle.getMessage()+", "+sqle.getErrorCode(); 
            retval[1] = "2"; 
             
        }
        
        return retval;
    }
}

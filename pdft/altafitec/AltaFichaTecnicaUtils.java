package xxqp.oracle.apps.ar.pdft.altafitec;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.Types;

import java.util.Map;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.server.OADBTransaction;

import oracle.jbo.domain.Number;

import oracle.jdbc.OracleCallableStatement;

public class AltaFichaTecnicaUtils {

public static final  String  strShortApplication = "XXQP";


    public static java.util.Map<String,String> getClientInfo(String strPartyId, 
                                     OAApplicationModuleImpl oAApplicationModuleImpl) {
        java.util.Map<String,String> retval = new  java.util.HashMap<String,String>(); 
        
        String strEmpresaQueFactura = null; 
        String strFrecuenciaFacturacion = null; 
        String strPuntoRecoleccion = null; 
        String strContactoCierre = null; 
        String strLunes = null; 
        String strMartes = null;
        String strMiercoles = null; 
        String strJueves = null; 
        String strViernes = null; 
        String strSabado = null; 
        String strDomingo = null; 
        
        String strCallableStmt = " BEGIN \n" + 
                                 " APPS.XXQP_PDFT_BPO_PKG.GET_CLIENT_INFO (  PSO_ERRMSG                  => :1\n" + 
                                 "                                         , PSO_ERRCOD                  => :2\n" + 
                                 "                                         , PNI_PARTY_ID                => :3\n" + 
                                 "                                         , PSO_EMPR_QUE_FACT_C         => :4\n" + 
                                 "                                         , pso_frecuencia_facturacion  => :5"+
                                 "                                         , pso_punto_recoleccion       => :6"+
                                 "                                         , pso_contacto_cierre         => :7"+
                                 "                                         , pso_lunes                   => :8"+
                                 "                                         , pso_martes                  => :9"+
                                 "                                         , pso_miercoles               => :10"+
                                 "                                         , pso_jueves                  => :11"+
                                 "                                         , pso_viernes                 => :12"+
                                 "                                         , pso_sabado                  => :13"+
                                 "                                         , pso_domingo                 => :14"+
                                 "                                         );\n" + 
                                 " END;  ";                             
        OADBTransaction oadbtransaction = (OADBTransaction)oAApplicationModuleImpl.getTransaction();   
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.setDouble(3,new Double(strPartyId));
            oraclecallablestatement.registerOutParameter(4,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(5,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(6,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(7,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(8,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(9,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(10,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(11,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(12,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(13,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(14,Types.VARCHAR);
            oraclecallablestatement.execute();
            strEmpresaQueFactura = oraclecallablestatement.getString(4);
            strFrecuenciaFacturacion = oraclecallablestatement.getString(5);
            strPuntoRecoleccion = oraclecallablestatement.getString(6);
            strContactoCierre = oraclecallablestatement.getString(7);
            strLunes = oraclecallablestatement.getString(8);
            strMartes = oraclecallablestatement.getString(9);
            strMiercoles = oraclecallablestatement.getString(10);
            strJueves = oraclecallablestatement.getString(11);
            strViernes = oraclecallablestatement.getString(12);
            strSabado = oraclecallablestatement.getString(13);
            strDomingo = oraclecallablestatement.getString(14);
            
            retval.put("EmpresaQueFactura",strEmpresaQueFactura);
            retval.put("FrecuenciaFacturacion",strFrecuenciaFacturacion);
            retval.put("PuntoRecoleccion",strPuntoRecoleccion);
            retval.put("ContactoCierre",strContactoCierre);
            retval.put("Lunes",strLunes);
            retval.put("Martes",strMartes);
            retval.put("Miercoles",strMiercoles);
            retval.put("Jueves",strJueves);
            retval.put("Viernes",strViernes);
            retval.put("Sabado",strSabado);
            retval.put("Domingo",strDomingo);
            
        } catch (SQLException sqle) {
          System.out.println("SQLException:"+sqle.getMessage());
        }
        
        return retval; 
    }
    
    public static String executeMypGetInfo(OAApplicationModuleImpl oAApplicationModuleImpl
                                          ,oracle.jbo.domain.Number pNumMypHeaderId
                                          ){
        String retval = null; 
        String strCallableStmt = " BEGIN \n" + 
                                 "  APPS.XXQP_PDFT_MYP_PKG.GET_INFO ( PSO_ERRMSG             => :1\n" + 
                                 "                                  , PSO_ERRCOD             => :2\n" + 
                                 "                                  , PCO_INFO               => :3\n" + 
                                 "                                  , PNI_MYP_HEADER_ID      => :4\n" + 
                                 "                                  );\n" + 
                                 " END; \n";
        OADBTransaction oadbtransaction = (OADBTransaction)oAApplicationModuleImpl.getTransaction();
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        oracle.jbo.domain.Number numMypHeaderId = pNumMypHeaderId;
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(3,Types.CLOB);
            oraclecallablestatement.setDouble(4,numMypHeaderId.doubleValue());
            oraclecallablestatement.execute();
            java.sql.Clob retvalClob = oraclecallablestatement.getClob(3);
            java.io.Reader reader =retvalClob.getCharacterStream();
            java.io.BufferedReader bufferReader = new java.io.BufferedReader(reader);
            String retvalxml = "";
            String line = null; 
            while((line = bufferReader.readLine())!=null){
                retvalxml = retvalxml+line;
            }
            
            System.out.println(retvalxml);
            retval = retvalxml;
            bufferReader.close();
            reader.close();
                        
        } catch (SQLException e) {
            System.out.println("SQLException en el metodo executeMypGetInfo:"+e.getErrorCode()+", "+e.getMessage());
            throw new OAException("SQLException en el metodo executeMypGetInfo:"+e.getErrorCode(),OAException.ERROR); 
        }catch (IOException ioe) {
            System.out.println("IOException en el metodo executeMypGetInfo:"+ioe.getMessage());
            throw new OAException("IOException en el metodo executeMypGetInfo:"+ioe.getMessage(),OAException.ERROR);
        }
        
        return retval;
        
    }


    public static String executeBpoGetInfo(OAApplicationModuleImpl oAApplicationModuleImpl, 
                                           Number pNumBpoHeaderId) {
        String retval = null; 
        String strCallableStmt = " BEGIN \n" + 
                                 "  APPS.XXQP_PDFT_BPO_PKG.GET_INFO ( PSO_ERRMSG         => :1\n" + 
                                 "                                  , PSO_ERRCOD         => :2\n" + 
                                 "                                  , PCO_INFO           => :3\n" + 
                                 "                                  , PNI_BPO_HEADER_ID  => :4 "+
                                 "                                  );\n" + 
                                 " END; \n";
        OADBTransaction oadbtransaction = (OADBTransaction)oAApplicationModuleImpl.getTransaction();
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        oracle.jbo.domain.Number numBpoHeaderId = pNumBpoHeaderId;
        System.out.println("numBpoHeaderId:"+numBpoHeaderId);
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(3,Types.CLOB);
            oraclecallablestatement.setDouble(4,numBpoHeaderId.doubleValue());
            oraclecallablestatement.execute();
            java.sql.Clob retvalClob = oraclecallablestatement.getClob(3);
            java.io.Reader reader =retvalClob.getCharacterStream();
            java.io.BufferedReader bufferReader = new java.io.BufferedReader(reader);
            String retvalxml = "";
            String line = null; 
            while((line = bufferReader.readLine())!=null){
                retvalxml = retvalxml+line;
            }
            System.out.println("retvalxml:"+retvalxml);
            retval = retvalxml;
            bufferReader.close();
            reader.close();
                        
        } catch (SQLException e) {
            System.out.println("SQLException en el metodo executeBpoGetInfo:"+e.getErrorCode());
            throw new OAException("SQLException en el metodo executeBpoGetInfo:"+e.getErrorCode(),OAException.ERROR); 
        }catch (IOException ioe) {
            System.out.println("IOException en el metodo executeBpoGetInfo"+ioe.getMessage());
            throw new OAException("IOException en el metodo executeBpoGetInfo:"+ioe.getMessage(),OAException.ERROR);
        }
        
        return retval;
        
    }

    public static String executeQplabsGetInfo(OAApplicationModuleImpl oAApplicationModuleImpl, 
                                              Number pNumQplabsHeaderId) {
        String retval = null; 
        String strCallableStmt = " BEGIN \n" + 
                                 "  APPS.XXQP_PDFT_QPLABS_PKG.GET_INFO ( PSO_ERRMSG            => :1\n" + 
                                 "                                     , PSO_ERRCOD            => :2\n" + 
                                 "                                     , PCO_INFO              => :3\n" + 
                                 "                                     , PNI_QPLABS_HEADER_ID  => :4 "+
                                 "                                     );\n" + 
                                 " END; \n";
        OADBTransaction oadbtransaction = (OADBTransaction)oAApplicationModuleImpl.getTransaction();
        OracleCallableStatement oraclecallablestatement =  (OracleCallableStatement)oadbtransaction.createCallableStatement(strCallableStmt, 1);
        oracle.jbo.domain.Number numQpLabsHeaderId = pNumQplabsHeaderId;
        System.out.println("numQpLabsHeaderId:"+numQpLabsHeaderId);
        try {
            oraclecallablestatement.registerOutParameter(1,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(2,Types.VARCHAR);
            oraclecallablestatement.registerOutParameter(3,Types.CLOB);
            oraclecallablestatement.setDouble(4,numQpLabsHeaderId.doubleValue());
            oraclecallablestatement.execute();
            java.sql.Clob retvalClob = oraclecallablestatement.getClob(3);
            java.io.Reader reader =retvalClob.getCharacterStream();
            java.io.BufferedReader bufferReader = new java.io.BufferedReader(reader);
            String retvalxml = "";
            String line = null; 
            while((line = bufferReader.readLine())!=null){
                retvalxml = retvalxml+line;
            }
            System.out.println("retvalxml:"+retvalxml);
            retval = retvalxml;
            bufferReader.close();
            reader.close();
                        
        } catch (SQLException e) {
            System.out.println("SQLException en el metodo executeQplabsGetInfo:"+e.getErrorCode());
            throw new OAException("SQLException en el metodo executeQplabsGetInfo:"+e.getErrorCode(),OAException.ERROR); 
        }catch (IOException ioe) {
            System.out.println("IOException en el metodo executeQplabsGetInfo"+ioe.getMessage());
            throw new OAException("IOException en el metodo executeQplabsGetInfo:"+ioe.getMessage(),OAException.ERROR);
        }
        
        return retval;                                         
    }
    
}

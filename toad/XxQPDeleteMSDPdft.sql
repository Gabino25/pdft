
DECLARE
BEGIN
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/webui/PortalDeFichaTecnicaPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/webui/LoginPdftPG');
    
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClientePG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteReadOnlyPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altacliente/webui/AltaDeClienteUpdPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altacliente/webui/HzCountryRN');
    
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/conscliente/webui/ConsultaDeClientePG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/conscliente/webui/EstadoRN');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/conscliente/webui/NombreDelClienteRN');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/conscliente/webui/RazonSocialRN');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/conscliente/webui/RfcRN');
    
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoReOnPG');
     jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoUpdPG');
     jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/bpo/webui/BpoCancelPG');
    
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumReOnPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumUpdPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/masiplat/webui/MasivoYPlatinumCancelPG');
    
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsReOnPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsUpdPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/qplabs/webui/QpLabsCancelPG');
      
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/webui/AltaFichaTecnicaPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaClienteParaFtPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/webui/BusquedaDeFichaTecnicaPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/webui/ReportesPG');
        
     jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/mgr/webui/CatalogosPG');
      jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/altafitec/mgr/webui/DistribucionFtPG');
      
     jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/ss/webui/LoginMgrPG');
      
   commit;
   /********************************************************************/
 exception when others then 
  dbms_output.put_line('sqlerrm'||sqlerrm||','||sqlcode); 
END;
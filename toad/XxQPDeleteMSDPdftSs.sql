
DECLARE
BEGIN
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/ss/webui/PortalPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/ss/webui/ConsultaFichaTecnicaPG');
    jdr_utils.deleteDocument('/xxqp/oracle/apps/ar/pdft/ss/webui/LoginPG');
     
   commit;
   /********************************************************************/
 exception when others then 
  dbms_output.put_line('sqlerrm'||sqlerrm||','||sqlcode); 
END;
CREATE OR REPLACE PACKAGE BODY XXQP_PDFT_CUSTOMER_PKG AS 


   CURSOR getHeaderInfo(CUR_CUSTOMER_ID  NUMBER) IS
        SELECT  ID                     
  ,NOMBRE_CLIENTE         
  ,GIRO_EMPRESARIAL_C     
  ,EMPRESA_QUE_FACTURA_C  
  ,TIPO_OPERATIVO_C       
  ,TIPO_ADMINISTRATIVO_C  
  ,TIPO_COMERCIAL_C       
  ,COMENTARIOS            
  ,CREATED_BY             
  ,CREATION_DATE          
  ,LAST_UPDATED_BY        
  ,LAST_UPDATE_DATE       
  ,LAST_UPDATE_LOGIN      
  ,ATTRIBUTE_CATEGORY     
  ,ATTRIBUTE1             
  ,ATTRIBUTE2             
  ,ATTRIBUTE3             
  ,ATTRIBUTE4             
  ,ATTRIBUTE5             
  ,PARTY_ID     
    FROM  XXQP_PDFT_CLIENTES_HEADER  
 WHERE ID = CUR_CUSTOMER_ID;
    
   /**Funcion de reemplazo**/
FUNCTION replace_char_esp(p_cadena IN VARCHAR2)
      RETURN VARCHAR2 IS
      v_cadena       VARCHAR2(4000);
   BEGIN
      v_cadena := REPLACE(p_cadena, CHR(38), CHR(38) || 'amp;');
      v_cadena := REPLACE(v_cadena, CHR(50081), CHR(38)||'#225;'); /*  v_cadena := REPLACE(v_cadena, CHR(50081), 'HR(38)||'acute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50089), CHR(38)||'#233;'); /*  v_cadena := REPLACE(v_cadena, CHR(50089), CHR(38)||'acute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50093), CHR(38)||'#237;'); /*  v_cadena := REPLACE(v_cadena, CHR(50093), CHR(38)||'iacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50099), CHR(38)||'#243;'); /*  v_cadena := REPLACE(v_cadena, CHR(50099), cHR(38)||'oacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50106), CHR(38)||'#250;'); /*  v_cadena := REPLACE(v_cadena, CHR(50106), CHR(38)||'uacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50049), CHR(38)||'#193;'); /*  v_cadena := REPLACE(v_cadena, CHR(50049), CHR(38)||'Aacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50057), CHR(38)||'#201;'); /*  v_cadena := REPLACE(v_cadena, CHR(50057), CHR(38)||'Eacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50061), CHR(38)||'#205;'); /*  v_cadena := REPLACE(v_cadena, CHR(50061), CHR(38)||'Iacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50067), CHR(38)||'#211;');   /* v_cadena := REPLACE(v_cadena, CHR(50067), CHR(38)||'Oacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50074), CHR(38)||'#218;'); /*  v_cadena := REPLACE(v_cadena, CHR(50074), CHR(38)||'Uacute;'); */
      v_cadena := REPLACE(v_cadena, CHR(50065), CHR(38)||'#209;');   /* v_cadena := REPLACE(v_cadena, CHR(50065), CHR(38)||'Ntilde;'); */
      v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'#241;');   /* v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'ntilde'); */
      v_cadena := REPLACE(v_cadena, CHR(49844), '');
      v_cadena := REPLACE(v_cadena, CHR(50090), '');
      v_cadena := REPLACE(v_cadena, CHR(50056), 'E');
      RETURN v_cadena;
   END replace_char_esp;
 

PROCEDURE MAIN(PSO_ERRMSG               OUT VARCHAR2
                       ,PSO_ERRCOD                 OUT VARCHAR2 
                       ,PNI_CUSTOMER_ID          IN NUMBER 
                       ,PSI_OPERATING_UNIT       IN VARCHAR2
                       ) IS 
 
  lc_info clob := '';          
  ls_errmsg   varchar2(32767); 
  ls_errcod   varchar2(32767);            
  
 v_clob_len    number; 
 v_length_clob   number := 0; 
 v_offset        number :=1;
 v_char          char(1);                         
                      
 begin 
 
    get_info(pso_errmsg              => ls_errmsg
             ,pso_errcod                  => ls_errcod
             ,pco_info                      => lc_info
             ,pni_customer_id          => pni_customer_id
             ,psi_operating_unit       => psi_operating_unit
           );
           
    fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info));   
     v_length_clob := dbms_lob.getlength(lc_info); 
     v_offset := 1;
     while(v_offset<=v_length_clob) LOOP
        v_char := dbms_lob.substr(lc_info, 1, v_offset);
        IF (v_char = CHR(10))
        then
        fnd_file.new_line(fnd_file.output, 1);
        else
        fnd_file.put(fnd_file.output, v_char);
        end if;
        v_offset := v_offset + 1;
     END LOOP;    
       
 exception when others then 
  pso_errmsg := 'Excepcion procedimiento main:'||sqlerrm||', '||sqlcode;
  pso_errcod  := '2';
  end main;
 
 PROCEDURE GET_INFO(PSO_ERRMSG                    OUT VARCHAR2
                            ,PSO_ERRCOD                     OUT VARCHAR2
                            ,PCO_INFO                         OUT CLOB
                            ,PNI_CUSTOMER_ID             IN NUMBER
                            ,PSI_OPERATING_UNIT       IN VARCHAR2
                           ) IS 
  
  lc_info clob := '';      
  HeaderInforec getHeaderInfo%ROWTYPE;
  
  begin 
     pso_errmsg := null; 
     pso_errcod := '0';
     
     lc_info := lc_info||'<XXQP_PDFT_CUSTOMER>';     
     OPEN getHeaderInfo(pni_customer_id);
     LOOP
        FETCH getHeaderInfo INTO HeaderInforec;
        EXIT WHEN getHeaderInfo%NOTFOUND;
        
         lc_info := lc_info||'<NOMBRE_CLIENTE>'||replace_char_esp(HeaderInforec.nombre_cliente)||'</NOMBRE_CLIENTE>';
        
     END LOOP;
     CLOSE getHeaderInfo;
     lc_info := lc_info||'</XXQP_PDFT_CUSTOMER>'; 
     pco_info := lc_info;                     
   exception when others then 
  pso_errmsg := 'Excepcion procedimiento main:'||sqlerrm||', '||sqlcode;
  pso_errcod  := '2';
  end get_info;
END XXQP_PDFT_CUSTOMER_PKG;
/


CREATE OR REPLACE PACKAGE BODY XXQP_PDFT_CUSTOMER_PKG AS 


 CURSOR getHeaderInfo(CUR_CUSTOMER_ID NUMBER) IS
 SELECT ID 
 ,XPCH.NOMBRE_CLIENTE 
 ,XPCH.GIRO_EMPRESARIAL_C 
 ,XPCH.EMPRESA_QUE_FACTURA_C 
 ,XPCH.TIPO_OPERATIVO_C 
 ,XPCH.TIPO_ADMINISTRATIVO_C 
 ,XPCH.TIPO_COMERCIAL_C 
 ,XPCH.COMENTARIOS 
 ,XPCH.CREATED_BY 
 ,XPCH.CREATION_DATE 
 ,XPCH.LAST_UPDATED_BY 
 ,XPCH.LAST_UPDATE_DATE 
 ,XPCH.LAST_UPDATE_LOGIN 
 ,XPCH.ATTRIBUTE_CATEGORY 
 ,XPCH.ATTRIBUTE1 
 ,XPCH.ATTRIBUTE2 
 ,XPCH.ATTRIBUTE3 
 ,XPCH.ATTRIBUTE4 
 ,XPCH.ATTRIBUTE5 
 ,XPCH.PARTY_ID 
 ,XPCH.RFC 
 ,(select description meaning
 from xxqp_pdft_mgr_catalogos
where lookup_type = 'GIRO_EMPRESARIAL'
 and lookup_code =GIRO_EMPRESARIAL_C ) GIRO_EMPRESARIAL_M
 ,(select LEGAL_ENTITY_IDENTIFIER||' - '||NAME /*LEGAL_ENTITY_NAME*/meaning
 from xle_entity_profiles
 where LEGAL_ENTITY_ID = XPCH.EMPRESA_QUE_FACTURA_C ) EMPRESA_QUE_FACTURA_M
 FROM XXQP_PDFT_CLIENTES_HEADER XPCH
 WHERE XPCH.ID = CUR_CUSTOMER_ID;
 
 /**Funcion de reemplazo**/
FUNCTION replace_char_esp(p_cadena IN VARCHAR2)
 RETURN VARCHAR2 IS
 v_cadena VARCHAR2(4000);
 BEGIN
 v_cadena := REPLACE(p_cadena, CHR(38), CHR(38) || 'amp;');
 v_cadena := REPLACE(v_cadena, CHR(50081), CHR(38)||'#225;'); /* v_cadena := REPLACE(v_cadena, CHR(50081), 'HR(38)||'acute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50089), CHR(38)||'#233;'); /* v_cadena := REPLACE(v_cadena, CHR(50089), CHR(38)||'acute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50093), CHR(38)||'#237;'); /* v_cadena := REPLACE(v_cadena, CHR(50093), CHR(38)||'iacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50099), CHR(38)||'#243;'); /* v_cadena := REPLACE(v_cadena, CHR(50099), cHR(38)||'oacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50106), CHR(38)||'#250;'); /* v_cadena := REPLACE(v_cadena, CHR(50106), CHR(38)||'uacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50049), CHR(38)||'#193;'); /* v_cadena := REPLACE(v_cadena, CHR(50049), CHR(38)||'Aacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50057), CHR(38)||'#201;'); /* v_cadena := REPLACE(v_cadena, CHR(50057), CHR(38)||'Eacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50061), CHR(38)||'#205;'); /* v_cadena := REPLACE(v_cadena, CHR(50061), CHR(38)||'Iacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50067), CHR(38)||'#211;'); /* v_cadena := REPLACE(v_cadena, CHR(50067), CHR(38)||'Oacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50074), CHR(38)||'#218;'); /* v_cadena := REPLACE(v_cadena, CHR(50074), CHR(38)||'Uacute;'); */
 v_cadena := REPLACE(v_cadena, CHR(50065), CHR(38)||'#209;'); /* v_cadena := REPLACE(v_cadena, CHR(50065), CHR(38)||'Ntilde;'); */
 v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'#241;'); /* v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'ntilde'); */
 v_cadena := REPLACE(v_cadena, CHR(49844), '');
 v_cadena := REPLACE(v_cadena, CHR(50090), '');
 v_cadena := REPLACE(v_cadena, CHR(50056), 'E');
 RETURN v_cadena;
 END replace_char_esp;
 

PROCEDURE MAIN(PSO_ERRMSG OUT VARCHAR2
 ,PSO_ERRCOD OUT VARCHAR2 
 ,PNI_CUSTOMER_ID IN NUMBER 
 ,PSI_OPERATING_UNIT IN VARCHAR2
 ) IS 
 
 lc_info clob := ''; 
 ls_errmsg varchar2(32767); 
 ls_errcod varchar2(32767); 
 
 v_clob_len number; 
 v_length_clob number := 0; 
 v_offset number :=1;
 v_char char(1); 
 
 begin 
 
 get_info(pso_errmsg => ls_errmsg
 ,pso_errcod => ls_errcod
 ,pco_info => lc_info
 ,pni_customer_id => pni_customer_id
 ,psi_operating_unit => psi_operating_unit
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
 pso_errcod := '2';
 end main;
 
 PROCEDURE GET_INFO(PSO_ERRMSG OUT VARCHAR2
 ,PSO_ERRCOD OUT VARCHAR2
 ,PCO_INFO OUT CLOB
 ,PNI_CUSTOMER_ID IN NUMBER
 ,PSI_OPERATING_UNIT IN VARCHAR2
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
 lc_info := lc_info||'<RFC>'||replace_char_esp(HeaderInforec.rfc)||'</RFC>';
 lc_info := lc_info||'<GIRO_EMPRESARIAL>'||replace_char_esp(HeaderInforec.giro_empresarial_m)||'</GIRO_EMPRESARIAL>';
 lc_info := lc_info||'<EMPRESA_QUE_FACTURA>'||replace_char_esp(HeaderInforec.empresa_que_factura_m)||'</EMPRESA_QUE_FACTURA>';
 lc_info := lc_info||'<TIPO_OPERATIVO>'||replace_char_esp(HeaderInforec.TIPO_OPERATIVO_C)||'</TIPO_OPERATIVO>';
 lc_info := lc_info||'<TIPO_ADMINISTRATIVO>'||replace_char_esp(HeaderInforec.TIPO_ADMINISTRATIVO_C)||'</TIPO_ADMINISTRATIVO>';
 lc_info := lc_info||'<TIPO_COMERCIAL>'||replace_char_esp(HeaderInforec.TIPO_COMERCIAL_C)||'</TIPO_COMERCIAL>';
 lc_info := lc_info||'<COMENTARIOS>'||replace_char_esp(HeaderInforec.COMENTARIOS)||'</COMENTARIOS>';
 
 
 
 END LOOP;
 CLOSE getHeaderInfo;
 lc_info := lc_info||'</XXQP_PDFT_CUSTOMER>'; 
 pco_info := lc_info; 
 exception when others then 
 pso_errmsg := 'Excepcion procedimiento main:'||sqlerrm||', '||sqlcode;
 pso_errcod := '2';
 end get_info;
END APPS.XXQP_PDFT_CUSTOMER_PKG;
/


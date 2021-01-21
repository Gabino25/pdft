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
 ,XPCH.RAZON_SOCIAL
 ,(select description meaning
 from xxqp_pdft_mgr_catalogos
where lookup_type = 'GIRO_EMPRESARIAL'
 and lookup_code =GIRO_EMPRESARIAL_C ) GIRO_EMPRESARIAL_M
 ,(select LEGAL_ENTITY_IDENTIFIER||' - '||NAME /*LEGAL_ENTITY_NAME*/meaning
 from xle_entity_profiles
 where LEGAL_ENTITY_ID = XPCH.EMPRESA_QUE_FACTURA_C ) EMPRESA_QUE_FACTURA_M
 ,nvl((select usuario
 from XXQP_PDFT_USUARIOS_RO
 where id = XPCH.ejecutivo),'NA') ejecutivo_m
 FROM XXQP_PDFT_CLIENTES_HEADER XPCH
 WHERE XPCH.ID = CUR_CUSTOMER_ID;
 

 CURSOR getDirFiscalInfo(CUR_HEADER_ID NUMBER
 ,CUR_OPERATING_UNIT VARCHAR2) IS
 SELECT XPCDF.ID 
,XPCDF.HEADER_ID 
,XPCDF.PRIM_RFC 
,XPCDF.PRIM_RAZON_SOCIAL 
,XPCDF.PRIM_DIRECCION 
,XPCDF.PRIM_ENTRE_CALLES 
,XPCDF.PRIM_COLONIA 
,XPCDF.PRIM_CIUDAD_O_MPO 
,XPCDF.PRIM_ESTADO_C 
,XPCDF.PRIM_CODIGO_POSTAL_C 
,XPCDF.PRIM_CEDULA 
,XPCDF.PRIM_CEDULA_FILE 
,XPCDF.SEC_RFC 
,XPCDF.SEC_RAZON_SOCIAL 
,XPCDF.SEC_DIRECCION 
,XPCDF.SEC_ENTRE_CALLES 
,XPCDF.SEC_COLONIA 
,XPCDF.SEC_CIUDAD_O_MPO 
,XPCDF.SEC_ESTADO_C 
,XPCDF.SEC_CODIGO_POSTAL_C 
,XPCDF.SEC_CEDULA 
,XPCDF.SEC_CEDULA_FILE 
,XPCDF.CREATED_BY 
,XPCDF.CREATION_DATE 
,XPCDF.LAST_UPDATED_BY 
,XPCDF.LAST_UPDATE_DATE 
,XPCDF.LAST_UPDATE_LOGIN 
,XPCDF.ATTRIBUTE_CATEGORY 
,XPCDF.ATTRIBUTE1 
,XPCDF.ATTRIBUTE2 
,XPCDF.ATTRIBUTE3 
,XPCDF.ATTRIBUTE4 
,XPCDF.ATTRIBUTE5 
,XPCDF.PRIM_CEDULA_FILE_NAME 
,XPCDF.PRIM_CEDULA_CONTENT_TYPE 
,XPCDF.SEC_CEDULA_FILE_NAME 
,XPCDF.SEC_CEDULA_CONTENT_TYPE 
,XPCDF.PRIM_OPERATING_UNIT 
,XPCDF.SEC_OPERATING_UNIT 
,XPCDF.PRIM_NUMERO_EXT 
,XPCDF.SEC_NUMERO_EXT 
,XPCDF.PRIM_NUMERO_INT 
,XPCDF.SEC_NUMERO_INT 
 FROM XXQP_PDFT_CLIENTES_DIR_FISCAL XPCDF 
 WHERE XPCDF.HEADER_ID = CUR_HEADER_ID
 AND XPCDF.PRIM_OPERATING_UNIT = CUR_OPERATING_UNIT;


 CURSOR getPuntoRecoInfo(CUR_HEADER_ID NUMBER) IS
 SELECT XPCPR.ID 
 ,XPCPR.HEADER_ID 
 ,XPCPR.PRIM_ENTREGA_EN_QP 
 ,XPCPR.PRIM_NOMBRE 
 ,XPCPR.PRIM_CONTACTO 
 ,XPCPR.PRIM_DIRECCION 
 ,XPCPR.PRIM_DIA 
 ,XPCPR.PRIM_HORARIO 
 ,XPCPR.SEC_ENTREGA_EN_QP 
 ,XPCPR.SEC_NOMBRE 
 ,XPCPR.SEC_CONTACTO 
 ,XPCPR.SEC_DIRECCION 
 ,XPCPR.SEC_DIA 
 ,XPCPR.SEC_HORARIO 
 ,XPCPR.CREATED_BY 
 ,XPCPR.CREATION_DATE 
 ,XPCPR.LAST_UPDATED_BY 
 ,XPCPR.LAST_UPDATE_DATE 
 ,XPCPR.LAST_UPDATE_LOGIN 
 ,XPCPR.ATTRIBUTE_CATEGORY 
 ,XPCPR.ATTRIBUTE1 
 ,XPCPR.ATTRIBUTE2 
 ,XPCPR.ATTRIBUTE3 
 ,XPCPR.ATTRIBUTE4 
 ,XPCPR.ATTRIBUTE5 
 ,decode(XPCPR.PRIM_ENTREGA_EN_QP,'Y','Si','N','No') PRIM_ENTREGA_EN_QPD
 FROM XXQP_PDFT_CLIENTES_PUNTO_RECO XPCPR
 WHERE XPCPR.HEADER_ID = CUR_HEADER_ID;
 
 CURSOR getContactosInfo(CUR_HEADER_ID NUMBER) IS
 SELECT XPCC.ID 
 ,XPCC.HEADER_ID 
 ,XPCC.TIPO_CONTACTO 
 ,XPCC.NOMBRE 
 ,XPCC.DIRECCION 
 ,XPCC.TELEFONO 
 ,XPCC.CORREO_ELECTRONICO 
 ,XPCC.PUESTO 
 ,XPCC.CREATED_BY 
 ,XPCC.CREATION_DATE 
 ,XPCC.LAST_UPDATED_BY 
 ,XPCC.LAST_UPDATE_DATE 
 ,XPCC.LAST_UPDATE_LOGIN 
 ,XPCC.ATTRIBUTE_CATEGORY 
 ,XPCC.ATTRIBUTE1 
 ,XPCC.ATTRIBUTE2 
 ,XPCC.ATTRIBUTE3 
 ,XPCC.ATTRIBUTE4 
 ,XPCC.ATTRIBUTE5 
 ,XPCC.NUMERO_CELULAR 
 FROM XXQP_PDFT_CLIENTES_CONTACTOS XPCC
WHERE XPCC.HEADER_ID = CUR_HEADER_ID
 ORDER BY XPCC.TIPO_CONTACTO ASC; 




 CURSOR getFacPagInfo(CUR_HEADER_ID NUMBER) IS
 SELECT XPCFP.ID 
 ,XPCFP.HEADER_ID 
 ,XPCFP.CONDICIONES_DE_PAGO_C 
 ,XPCFP.OBSERVACIONES 
 ,XPCFP.TIPO_DE_PAGO_C 
 ,XPCFP.REQUIERE_ADENDAS_C 
 ,XPCFP.REQUIERE_FACTURA_C 
 ,XPCFP.MOTIVO 
 ,XPCFP.CICLO_DE_FACTURACION_C 
 ,XPCFP.USO_DEL_CFDI_C 
 ,XPCFP.METODO_DE_PAGO_C 
 ,XPCFP.NUMERO_DE_CUENTA 
 ,XPCFP.NOMBRE_DEL_BANCO 
 ,XPCFP.DIAS_NAT_DE_CREDITO 
 ,XPCFP.DIAS_RECEPCION_FACTUR 
 ,XPCFP.UTILIZA_PORTAL_C 
 ,XPCFP.PORTAL_LINK 
 ,XPCFP.ORDEN_DE_COMPRA_C 
 ,XPCFP.CONTRATO_C 
 ,XPCFP.VIGENCIA_CONTRATO 
 ,XPCFP.CREATED_BY 
 ,XPCFP.CREATION_DATE 
 ,XPCFP.LAST_UPDATED_BY 
 ,XPCFP.LAST_UPDATE_DATE 
 ,XPCFP.LAST_UPDATE_LOGIN 
 ,XPCFP.ATTRIBUTE_CATEGORY 
 ,XPCFP.ATTRIBUTE1 
 ,XPCFP.ATTRIBUTE2 
 ,XPCFP.ATTRIBUTE3 
 ,XPCFP.ATTRIBUTE4 
 ,XPCFP.ATTRIBUTE5 
 ,XPCFP.LUNES 
 ,XPCFP.MARTES 
 ,XPCFP.MIERCOLES 
 ,XPCFP.JUEVES 
 ,XPCFP.VIERNES 
 ,XPCFP.SABADO 
 ,XPCFP.DOMINGO 
 ,decode(XPCFP.CONDICIONES_DE_PAGO_C,'CONTADO','(PUE) Contado','CREDITO','(PPD) Cr'||chr(50089)||'dito') CONDICIONES_DE_PAGO_D
 ,decode(XPCFP.TIPO_DE_PAGO_C,'UNA_EXHIBICION','Una Exhibicion','PARCIALIDADES','Parcialidades') TIPO_DE_PAGO_D
 ,(select description meaning
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'CICLO_DE_FACTURACION'
 and lookup_code = XPCFP.CICLO_DE_FACTURACION_C
 ) CICLO_DE_FACTURACION_D
 ,(select T.FLEX_VALUE_MEANING||' '||T.DESCRIPTION meaning
 FROM FND_FLEX_VALUES_TL T, FND_FLEX_VALUES B
 WHERE B.FLEX_VALUE_ID = T.FLEX_VALUE_ID AND T.LANGUAGE = 'ESA'
 and b.FLEX_VALUE_SET_ID in (select flex_value_set_id 
 from FND_FLEX_VALUE_SETS
 where flex_value_set_name = 'QP_SAT_FORMA_PAGO'
 )
 and T.FLEX_VALUE_MEANING = XPCFP.METODO_DE_PAGO_C
 ) METODO_DE_PAGO_D
 ,(select t.name 
 from ra_terms t
 where t.term_id = XPCFP.DIAS_NAT_DE_CREDITO
 ) TERMINOS_DE_PAGOS
 ,DECODE(XPCFP.UTILIZA_PORTAL_C,'Y','Si','N','No') UTILIZA_PORTAL_D
 ,DECODE(XPCFP.ORDEN_DE_COMPRA_C,'Y','Si','N','No') ORDEN_DE_COMPRA_D
 ,DECODE(XPCFP.CONTRATO_C,'Y','Si','N','No') CONTRATO_D
 ,DECODE(XPCFP.REQUIERE_ADENDAS_C,'Y','Si','N','No') REQUIERE_ADENDAS_D
 ,DECODE(XPCFP.REQUIERE_FACTURA_C,'Y','Si','N','No') REQUIERE_FACTURA_D
 ,(select T.FLEX_VALUE_MEANING||' - '||T.DESCRIPTION meaning
 FROM FND_FLEX_VALUES_TL T, FND_FLEX_VALUES B
 WHERE B.FLEX_VALUE_ID = T.FLEX_VALUE_ID AND T.LANGUAGE = 'ESA'
 and b.FLEX_VALUE_SET_ID in (select flex_value_set_id 
 from FND_FLEX_VALUE_SETS
 where flex_value_set_name = 'QP_SAT_USO_CFDI'
 )
 AND T.FLEX_VALUE_MEANING = XPCFP.USO_DEL_CFDI_C
 ) USO_DEL_CFDI_D
 FROM XXQP_PDFT_CLIENTES_FACT_PAG XPCFP
WHERE XPCFP.HEADER_ID = CUR_HEADER_ID;

 
 
 
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
 ,PSI_MOVIMIENTO IN VARCHAR2 DEFAULT 'CONSULTA'
 ) IS 
 
 lc_info clob := ''; 
 HeaderInforec getHeaderInfo%ROWTYPE;
 DirFiscalInforec getDirFiscalInfo%ROWTYPE;
 PuntoRecoInforec getPuntoRecoInfo%ROWTYPE;
 ContactosInforec getContactosInfo%ROWTYPE;
 FacPagInforec getFacPagInfo%ROWTYPE;
 
 ls_ou_name varchar2(2000); 
 ls_dias_recepcion varchar2(2000); 
 
 begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 
 select distinct HROUTL_OU.name OU_NAME
 into ls_ou_name
 from XLE_ENTITY_PROFILES LEP,
 XLE_REGISTRATIONS REG,
 HR_LOCATIONS_ALL HRL,
 HZ_PARTIES HZP,
 FND_TERRITORIES_VL TER,
 HR_OPERATING_UNITS HRO,
 HR_ALL_ORGANIZATION_UNITS_TL HROUTL_BG,
 HR_ALL_ORGANIZATION_UNITS_TL HROUTL_OU,
 HR_ORGANIZATION_UNITS GLOPERATINGUNITSEO,
 GL_LEGAL_ENTITIES_BSVS GLEV
 where LEP.TRANSACTING_ENTITY_FLAG = 'Y'
 and LEP.PARTY_ID = HZP.PARTY_ID
 and LEP.LEGAL_ENTITY_ID = REG.SOURCE_ID
 and REG.SOURCE_TABLE = 'XLE_ENTITY_PROFILES'
 and HRL.LOCATION_ID = REG.LOCATION_ID
 and REG.IDENTIFYING_FLAG = 'Y'
 and TER.TERRITORY_CODE = HRL.COUNTRY
 and LEP.LEGAL_ENTITY_ID = HRO.DEFAULT_LEGAL_CONTEXT_ID
 and GLOPERATINGUNITSEO.ORGANIZATION_ID = HRO.ORGANIZATION_ID
 and HROUTL_BG.ORGANIZATION_ID = HRO.BUSINESS_GROUP_ID
 and HROUTL_OU.ORGANIZATION_ID = HRO.ORGANIZATION_ID
 and GLEV.LEGAL_ENTITY_ID = LEP.LEGAL_ENTITY_ID
 AND HROUTL_OU.ORGANIZATION_ID = PSI_OPERATING_UNIT; 

 
 
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
 lc_info := lc_info||'<RAZON_SOCIAL>'||replace_char_esp(HeaderInforec.RAZON_SOCIAL)||'</RAZON_SOCIAL>';
 lc_info := lc_info||'<CREATION_DATE>'||to_char(HeaderInforec.creation_date,'DD/MM/YYYY')||'</CREATION_DATE>';
 lc_info := lc_info||'<MOVIMIENTO>'||PSI_MOVIMIENTO||'</MOVIMIENTO>';
 lc_info := lc_info||'<EJECUTIVA_COMERCIAL>'||replace_char_esp(HeaderInforec.EJECUTIVO_M)||'</EJECUTIVA_COMERCIAL>';
 
 OPEN getDirFiscalInfo(HeaderInforec.id,PSI_OPERATING_UNIT);
 LOOP
 FETCH getDirFiscalInfo INTO DirFiscalInforec;
 EXIT WHEN getDirFiscalInfo%NOTFOUND;
 
 lc_info := lc_info||'<PRIM_UNIDAD_OPERATIVA>'||replace_char_esp(ls_ou_name)||'</PRIM_UNIDAD_OPERATIVA>';
 lc_info := lc_info||'<PRIM_DIRECCION>'||replace_char_esp(DirFiscalInforec.PRIM_DIRECCION)||'</PRIM_DIRECCION>';
 lc_info := lc_info||'<PRIM_NUMERO_INT>'||replace_char_esp(DirFiscalInforec.PRIM_NUMERO_INT)||'</PRIM_NUMERO_INT>';
 lc_info := lc_info||'<PRIM_NUMERO_EXT>'||replace_char_esp(DirFiscalInforec.PRIM_NUMERO_EXT)||'</PRIM_NUMERO_EXT>';
 lc_info := lc_info||'<PRIM_ENTRE_CALLES>'||replace_char_esp(DirFiscalInforec.PRIM_ENTRE_CALLES)||'</PRIM_ENTRE_CALLES>';
 lc_info := lc_info||'<PRIM_COLONIA>'||replace_char_esp(DirFiscalInforec.PRIM_COLONIA)||'</PRIM_COLONIA>';
 lc_info := lc_info||'<PRIM_CIUDAD_O_MPO>'||replace_char_esp(DirFiscalInforec.PRIM_CIUDAD_O_MPO)||'</PRIM_CIUDAD_O_MPO>';
 lc_info := lc_info||'<PRIM_ESTADO_C>'||replace_char_esp(DirFiscalInforec.PRIM_ESTADO_C)||'</PRIM_ESTADO_C>';
 lc_info := lc_info||'<PRIM_CODIGO_POSTAL_C>'||replace_char_esp(DirFiscalInforec.PRIM_CODIGO_POSTAL_C)||'</PRIM_CODIGO_POSTAL_C>';
 
 
 lc_info := lc_info||'<SEC_UNIDAD_OPERATIVA>'||replace_char_esp(ls_ou_name)||'</SEC_UNIDAD_OPERATIVA>';
 lc_info := lc_info||'<SEC_DIRECCION>'||replace_char_esp(DirFiscalInforec.SEC_DIRECCION)||'</SEC_DIRECCION>';
 lc_info := lc_info||'<SEC_NUMERO_INT>'||replace_char_esp(DirFiscalInforec.SEC_NUMERO_INT)||'</SEC_NUMERO_INT>';
 lc_info := lc_info||'<SEC_NUMERO_EXT>'||replace_char_esp(DirFiscalInforec.SEC_NUMERO_EXT)||'</SEC_NUMERO_EXT>';
 lc_info := lc_info||'<SEC_ENTRE_CALLES>'||replace_char_esp(DirFiscalInforec.SEC_ENTRE_CALLES)||'</SEC_ENTRE_CALLES>';
 lc_info := lc_info||'<SEC_COLONIA>'||replace_char_esp(DirFiscalInforec.SEC_COLONIA)||'</SEC_COLONIA>';
 lc_info := lc_info||'<SEC_CIUDAD_O_MPO>'||replace_char_esp(DirFiscalInforec.SEC_CIUDAD_O_MPO)||'</SEC_CIUDAD_O_MPO>';
 lc_info := lc_info||'<SEC_ESTADO_C>'||replace_char_esp(DirFiscalInforec.SEC_ESTADO_C)||'</SEC_ESTADO_C>';
 lc_info := lc_info||'<SEC_CODIGO_POSTAL_C>'||replace_char_esp(DirFiscalInforec.SEC_CODIGO_POSTAL_C)||'</SEC_CODIGO_POSTAL_C>';
 
 exit; 
 END LOOP;
 CLOSE getDirFiscalInfo;
 
 
 OPEN getPuntoRecoInfo(HeaderInforec.id);
 LOOP
 FETCH getPuntoRecoInfo INTO PuntoRecoInforec;
 EXIT WHEN getPuntoRecoInfo%NOTFOUND;
 
 lc_info := lc_info||'<PRIM_PR_ENTREGA_EN_QPD>'||replace_char_esp(PuntoRecoInforec.PRIM_ENTREGA_EN_QPD)||'</PRIM_PR_ENTREGA_EN_QPD>';
 lc_info := lc_info||'<PRIM_PR_NOMBRE>'||replace_char_esp(PuntoRecoInforec.PRIM_NOMBRE)||'</PRIM_PR_NOMBRE>';
 lc_info := lc_info||'<PRIM_PR_DIRECCION>'||replace_char_esp(PuntoRecoInforec.PRIM_DIRECCION)||'</PRIM_PR_DIRECCION>';
 lc_info := lc_info||'<PRIM_PR_DIA>'||replace_char_esp(PuntoRecoInforec.PRIM_DIA)||'</PRIM_PR_DIA>';
 lc_info := lc_info||'<PRIM_PR_HORARIO>'||replace_char_esp(PuntoRecoInforec.PRIM_HORARIO)||'</PRIM_PR_HORARIO>';
 
 lc_info := lc_info||'<SEC_PR_ENTREGA_EN_QPD>'||replace_char_esp(PuntoRecoInforec.PRIM_ENTREGA_EN_QPD)||'</SEC_PR_ENTREGA_EN_QPD>';
 lc_info := lc_info||'<SEC_PR_NOMBRE>'||replace_char_esp(PuntoRecoInforec.PRIM_NOMBRE)||'</SEC_PR_NOMBRE>';
 lc_info := lc_info||'<SEC_PR_DIRECCION>'||replace_char_esp(PuntoRecoInforec.PRIM_DIRECCION)||'</SEC_PR_DIRECCION>';
 lc_info := lc_info||'<SEC_PR_DIA>'||replace_char_esp(PuntoRecoInforec.PRIM_DIA)||'</SEC_PR_DIA>';
 lc_info := lc_info||'<SEC_PR_HORARIO>'||replace_char_esp(PuntoRecoInforec.PRIM_HORARIO)||'</SEC_PR_HORARIO>';
 
 END LOOP;
 CLOSE getPuntoRecoInfo;
 
 
 
 OPEN getContactosInfo(HeaderInforec.id);
 LOOP
 FETCH getContactosInfo INTO ContactosInforec;
 EXIT WHEN getContactosInfo%NOTFOUND;
 
 lc_info := lc_info||'<CONTACTO>'; 
 lc_info := lc_info||'<TIPO_CONTACTO>'||replace_char_esp(ContactosInforec.TIPO_CONTACTO)||'</TIPO_CONTACTO>';
 lc_info := lc_info||'<NOMBRE>'||replace_char_esp(ContactosInforec.NOMBRE)||'</NOMBRE>';
 lc_info := lc_info||'<DIRECCION>'||replace_char_esp(ContactosInforec.DIRECCION)||'</DIRECCION>';
 lc_info := lc_info||'<TELEFONO>'||replace_char_esp(ContactosInforec.TELEFONO)||'</TELEFONO>';
 lc_info := lc_info||'<CORREO_ELECTRONICO>'||replace_char_esp(ContactosInforec.CORREO_ELECTRONICO)||'</CORREO_ELECTRONICO>';
 lc_info := lc_info||'<PUESTO>'||replace_char_esp(ContactosInforec.PUESTO)||'</PUESTO>';
 lc_info := lc_info||'<NUMERO_CELULAR>'||replace_char_esp(ContactosInforec.NUMERO_CELULAR)||'</NUMERO_CELULAR>';
 lc_info := lc_info||'</CONTACTO>'; 
 
 END LOOP;
 CLOSE getContactosInfo;
 
 lc_info := lc_info||chr(10); 
 
 OPEN getFacPagInfo(HeaderInforec.id);
 LOOP
 FETCH getFacPagInfo INTO FacPagInforec;
 EXIT WHEN getFacPagInfo%NOTFOUND;
 
 if FacPagInforec.LUNES = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Lunes, ';
 end if; 
 
 if FacPagInforec.MARTES = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Martes, ';
 end if; 
 
 if FacPagInforec.MIERCOLES = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Miercoles, ';
 end if; 
 
 if FacPagInforec.JUEVES = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Jueves, ';
 end if; 
 
 if FacPagInforec.VIERNES = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Viernes, ';
 end if; 
 
 if FacPagInforec.SABADO = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Sabado, ';
 end if; 
 
 if FacPagInforec.DOMINGO = 'Y' then 
 ls_dias_recepcion := ls_dias_recepcion||'Domingo';
 end if; 
 
 lc_info := lc_info||'<CONDICIONES_DE_PAGO_D>'||replace_char_esp(FacPagInforec.CONDICIONES_DE_PAGO_D)||'</CONDICIONES_DE_PAGO_D>';
 lc_info := lc_info||'<TIPO_DE_PAGO_D>'||replace_char_esp(FacPagInforec.TIPO_DE_PAGO_D)||'</TIPO_DE_PAGO_D>';
 lc_info := lc_info||'<CICLO_DE_FACTURACION_D>'||replace_char_esp(FacPagInforec.CICLO_DE_FACTURACION_D)||'</CICLO_DE_FACTURACION_D>';
 lc_info := lc_info||'<METODO_DE_PAGO_D>'||replace_char_esp(FacPagInforec.METODO_DE_PAGO_D)||'</METODO_DE_PAGO_D>';
 lc_info := lc_info||'<TERMINOS_DE_PAGOS>'||replace_char_esp(FacPagInforec.TERMINOS_DE_PAGOS)||'</TERMINOS_DE_PAGOS>';
 lc_info := lc_info||'<NUMERO_DE_CUENTA>'||replace_char_esp(FacPagInforec.NUMERO_DE_CUENTA)||'</NUMERO_DE_CUENTA>';
 lc_info := lc_info||'<NOMBRE_DEL_BANCO>'||replace_char_esp(FacPagInforec.NOMBRE_DEL_BANCO)||'</NOMBRE_DEL_BANCO>';
 lc_info := lc_info||'<DIAS_DE_RECEPCION>'||replace_char_esp(ls_dias_recepcion)||'</DIAS_DE_RECEPCION>';
 lc_info := lc_info||'<UTILIZA_PORTAL_D>'||replace_char_esp(FacPagInforec.utiliza_portal_d)||'</UTILIZA_PORTAL_D>';
 lc_info := lc_info||'<PORTAL_LINK>'||replace_char_esp(FacPagInforec.portal_link)||'</PORTAL_LINK>';
 lc_info := lc_info||'<ORDEN_DE_COMPRA_D>'||replace_char_esp(FacPagInforec.ORDEN_DE_COMPRA_D)||'</ORDEN_DE_COMPRA_D>';
 lc_info := lc_info||'<CONTRATO_D>'||replace_char_esp(FacPagInforec.CONTRATO_D)||'</CONTRATO_D>';
 lc_info := lc_info||'<VIGENCIA_CONTRATO>'||replace_char_esp(FacPagInforec.VIGENCIA_CONTRATO)||'</VIGENCIA_CONTRATO>';
 lc_info := lc_info||'<REQUIERE_ADENDAS_D>'||replace_char_esp(FacPagInforec.REQUIERE_ADENDAS_D)||'</REQUIERE_ADENDAS_D>';
 lc_info := lc_info||'<REQUIERE_FACTURA_D>'||replace_char_esp(FacPagInforec.REQUIERE_FACTURA_D)||'</REQUIERE_FACTURA_D>';
 lc_info := lc_info||'<USO_DEL_CFDI_D>'||replace_char_esp(FacPagInforec.USO_DEL_CFDI_D)||'</USO_DEL_CFDI_D>';
 
 
 END LOOP;
 CLOSE getFacPagInfo;
 
 
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


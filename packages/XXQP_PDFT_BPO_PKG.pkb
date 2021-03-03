CREATE OR REPLACE package body xxqp_pdft_bpo_pkg is 

/** 15042020 se agrega nvl al nombre del cliente **/
/** 17042020 se crea concurrente para debuguear pdf **/
/** 24042020 se re-direcciona a la vista custom **/

gs_currency_format varchar2(2000) := '$999,999,999,999,999,990.99'; 

 CURSOR get_bpo_head_info (cur_bpo_header_id number) IS
 SELECT bpo_head.ID, 
 bpo_head.NUMERO_FT, 
 bpo_head.REV, 
 bpo_head.STATUS, 
 decode(bpo_head.STATUS,'PRELIMINAR','En captura',
 'ABIERTA','Activa' ,
 'CERRADA','Cerrada',
 'CANCELADA','Cancelada' ) STATUS_MEANING,
 bpo_head.NUMERO_FT_REFERENCIA, 
 bpo_head.PARTY_ID, 
 bpo_head.PDFT_CLIENTE_HEADER_ID, 
 bpo_head.EMPRESA_QUE_FACTURA_C, 
 ( select LEGAL_ENTITY_IDENTIFIER||' - '||NAME /*LEGAL_ENTITY_NAME*/meaning
 from xle_entity_profiles
 where LEGAL_ENTITY_ID = bpo_head.EMPRESA_QUE_FACTURA_C
 ) EMPRESA_QUE_FACTURA_M,
 bpo_head.UNIDAD_DE_NEGOCIO_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'UNIDAD_DE_NEGOCIO'
 and lookup_code = bpo_head.UNIDAD_DE_NEGOCIO_C ) UNIDAD_DE_NEGOCIO_M,
 bpo_head.FRECUENCIA_FACTURACION_C, 
 bpo_head.CONTRATO_FLAG, 
 bpo_head.CONTRATO_FILE_NAME, 
 bpo_head.CONTRATO_CONTENT_TYPE, 
 bpo_head.CONTRATO_FILE, 
 bpo_head.FECHA_INICIAL_OPERACION, 
 bpo_head.CREATED_BY, 
 bpo_head.CREATION_DATE, 
 bpo_head.LAST_UPDATED_BY, 
 bpo_head.LAST_UPDATE_DATE, 
 bpo_head.LAST_UPDATE_LOGIN, 
 bpo_head.ATTRIBUTE_CATEGORY, 
 bpo_head.ATTRIBUTE1, 
 bpo_head.ATTRIBUTE2, 
 bpo_head.ATTRIBUTE3, 
 bpo_head.ATTRIBUTE4, 
 bpo_head.ATTRIBUTE5,
(select nvl(p.known_as,p.party_name) /* 15042020 */ 
 from xxqp_pdft_clientes_info_v p /** 24042020 **/
 where p.party_id = bpo_head.party_id) nombre_del_cliente,
 (select usuario 
 from XXQP_PDFT_USUARIOS_RO
 where id = bpo_head.ejecutivo ) ejecutivo,
 bpo_head.articulo_oracle
FROM XXQP_PDFT_BPO_HEADER bpo_head
where bpo_head.id =cur_bpo_header_id; /* 52*/


 CURSOR get_precio_info ( cur_bpo_header_id number) IS
 select bpo_precio.concepto
 ,bpo_precio.cantidad 
 ,bpo_precio.precio_unitario
 ,bpo_precio.total_concepto
 from XXQP_PDFT_BPO_PRECIO bpo_precio
 where bpo_precio.bpo_header_id = cur_bpo_header_id;
 
 

 CURSOR get_bpo_serv_info(cur_bpo_header_id number) IS
 SELECT bpo_serv.ID, 
 bpo_serv.BPO_HEADER_ID, 
 bpo_serv.COBRANZA, 
 bpo_serv.VENTAS, 
 bpo_serv.GESTOR_ADMINISTRATIVO, 
 bpo_serv.TECNICO, 
 bpo_serv.RECOLE_DOC_MATER, 
 bpo_serv.ENTREGA_A_DOMICILIO, 
 bpo_serv.OTROS, 
 bpo_serv.FECHA_INICIO_SERVICIO, 
 bpo_serv.EL_MENSAJERO_MANEJE_C, 
 bpo_serv.DIAS_SEMANA_LABORARA, 
 bpo_serv.NO_HRS_DIARIAS, 
 bpo_serv.HORARIO_DE_TRABAJO, 
 bpo_serv.DIRECCION_BASE, 
 bpo_serv.OBSERVACIONES, 
 bpo_serv.CREATED_BY, 
 bpo_serv.CREATION_DATE, 
 bpo_serv.LAST_UPDATED_BY, 
 bpo_serv.LAST_UPDATE_DATE, 
 bpo_serv.LAST_UPDATE_LOGIN, 
 bpo_serv.ATTRIBUTE_CATEGORY, 
 bpo_serv.ATTRIBUTE1, 
 bpo_serv.ATTRIBUTE2, 
 bpo_serv.ATTRIBUTE3, 
 bpo_serv.ATTRIBUTE4, 
 bpo_serv.ATTRIBUTE5, 
 substr(hora_inicio,0 ,instr(hora_inicio,':',1,1)-1 )
 ||':'||substr(replace(substr(hora_inicio,instr(hora_inicio,':',1,1)+1, instr(hora_inicio,':',1,2)- instr(hora_inicio,':',1,1)-1 ),'0','00'),1,2)
 ||'-'||
 substr(HORA_FINAL,0 ,instr(HORA_FINAL,':',1,1)-1 )
 ||':'||substr(replace(substr(HORA_FINAL,instr(HORA_FINAL,':',1,1)+1, instr(HORA_FINAL,':',1,2)- instr(HORA_FINAL,':',1,1)-1 ),'0','00'),1,2) HORARIO_DE_TRABAJO_V2, 
 decode(bpo_serv.LUNES,'Y','L','')||','||
 decode(bpo_serv.MARTES,'Y','M','')||','||
 decode(bpo_serv.MIERCOLES,'Y','M','')||','||
 decode(bpo_serv.JUEVES,'Y','J','')||','||
 decode(bpo_serv.VIERNES,'Y','V','')||','||
 decode(bpo_serv.SABADO,'Y','S','')||','||
 decode(bpo_serv.DOMINGO,'Y','D','') DIAS_SEMANA_LABORARA_V2
FROM XXQP_PDFT_BPO_SERVICIO bpo_serv
 where bpo_serv.bpo_header_id = cur_bpo_header_id; /*52*/
 
 
 CURSOR get_bpo_gyc_info (cur_bpo_header_id number) IS
 SELECT bpo_gyc.ID, 
 bpo_gyc.BPO_HEADER_ID, 
 bpo_gyc.CATEGORIA, 
 bpo_gyc.MOTO_125, 
 bpo_gyc.CASCO, 
 bpo_gyc.CAJA_GRANDE, 
 bpo_gyc.CAJA_CHICA_ROJA, 
 bpo_gyc.CAMISAS, 
 bpo_gyc.IMPERMEABLE, 
 bpo_gyc.TARJETA_GASOLINA, 
 bpo_gyc.PANTALON_VESTIR, 
 bpo_gyc.CAJA_NEGRA, 
 bpo_gyc.HIELERA, 
 bpo_gyc.MOVIL, 
 bpo_gyc.AUTOMOVIL, 
 bpo_gyc.GUIA_ROJI, 
 bpo_gyc.OTROS, 
 bpo_gyc.COMENTARIOS, 
 bpo_gyc.CREATED_BY, 
 bpo_gyc.CREATION_DATE, 
 bpo_gyc.LAST_UPDATED_BY, 
 bpo_gyc.LAST_UPDATE_DATE, 
 bpo_gyc.LAST_UPDATE_LOGIN, 
 bpo_gyc.ATTRIBUTE_CATEGORY, 
 bpo_gyc.ATTRIBUTE1, 
 bpo_gyc.ATTRIBUTE2, 
 bpo_gyc.ATTRIBUTE3, 
 bpo_gyc.ATTRIBUTE4, 
 bpo_gyc.ATTRIBUTE5
FROM XXQP_PDFT_BPO_REQUE_ADICIO bpo_gyc
WHERE bpo_gyc.CATEGORIA='GYC'
 and bpo_gyc.bpo_header_id = cur_bpo_header_id; /*52*/
 
 
 CURSOR get_bpo_oper_info (cur_bpo_header_id number) IS
 SELECT bpo_oper.ID, 
 bpo_oper.BPO_HEADER_ID, 
 bpo_oper.CATEGORIA, 
 bpo_oper.MOTO_125, 
 bpo_oper.CASCO, 
 bpo_oper.CAJA_GRANDE, 
 bpo_oper.CAJA_CHICA_ROJA, 
 bpo_oper.CAMISAS, 
 bpo_oper.IMPERMEABLE, 
 bpo_oper.TARJETA_GASOLINA, 
 bpo_oper.PANTALON_VESTIR, 
 bpo_oper.CAJA_NEGRA, 
 bpo_oper.HIELERA, 
 bpo_oper.MOVIL, 
 bpo_oper.AUTOMOVIL, 
 bpo_oper.GUIA_ROJI, 
 bpo_oper.OTROS, 
 bpo_oper.COMENTARIOS, 
 bpo_oper.CREATED_BY, 
 bpo_oper.CREATION_DATE, 
 bpo_oper.LAST_UPDATED_BY, 
 bpo_oper.LAST_UPDATE_DATE, 
 bpo_oper.LAST_UPDATE_LOGIN, 
 bpo_oper.ATTRIBUTE_CATEGORY, 
 bpo_oper.ATTRIBUTE1, 
 bpo_oper.ATTRIBUTE2, 
 bpo_oper.ATTRIBUTE3, 
 bpo_oper.ATTRIBUTE4, 
 bpo_oper.ATTRIBUTE5
FROM XXQP_PDFT_BPO_REQUE_ADICIO bpo_oper
WHERE bpo_oper.CATEGORIA = 'OPER'
and bpo_oper.bpo_header_id = cur_bpo_header_id; /*52*/

 CURSOR get_bpo_adqu_info (cur_bpo_header_id number) IS
 SELECT bpo_adqu.ID, 
 bpo_adqu.BPO_HEADER_ID, 
 bpo_adqu.CATEGORIA, 
 bpo_adqu.MOTO_125, 
 bpo_adqu.CASCO, 
 bpo_adqu.CAJA_GRANDE, 
 bpo_adqu.CAJA_CHICA_ROJA, 
 bpo_adqu.CAMISAS, 
 bpo_adqu.IMPERMEABLE, 
 bpo_adqu.TARJETA_GASOLINA, 
 bpo_adqu.PANTALON_VESTIR, 
 bpo_adqu.CAJA_NEGRA, 
 bpo_adqu.HIELERA, 
 bpo_adqu.MOVIL, 
 bpo_adqu.AUTOMOVIL, 
 bpo_adqu.GUIA_ROJI, 
 bpo_adqu.OTROS, 
 bpo_adqu.COMENTARIOS, 
 bpo_adqu.CREATED_BY, 
 bpo_adqu.CREATION_DATE, 
 bpo_adqu.LAST_UPDATED_BY, 
 bpo_adqu.LAST_UPDATE_DATE, 
 bpo_adqu.LAST_UPDATE_LOGIN, 
 bpo_adqu.ATTRIBUTE_CATEGORY, 
 bpo_adqu.ATTRIBUTE1, 
 bpo_adqu.ATTRIBUTE2, 
 bpo_adqu.ATTRIBUTE3, 
 bpo_adqu.ATTRIBUTE4, 
 bpo_adqu.ATTRIBUTE5
FROM XXQP_PDFT_BPO_REQUE_ADICIO bpo_adqu
WHERE bpo_adqu.CATEGORIA='ADQU'
and bpo_adqu.bpo_header_id = cur_bpo_header_id; /*52*/

 CURSOR get_bpo_pago_info(cur_bpo_header_id number) IS
 SELECT bpo_pago.ID, 
 bpo_pago.BPO_HEADER_ID, 
 bpo_pago.EJECUTIVO_DE_VENTAS, 
 bpo_pago.PLAZA, 
 bpo_pago.SUBTOTAL, 
 bpo_pago.IVA, 
 bpo_pago.TOTAL, 
 bpo_pago.FORMA_DE_PAGO_C, 
 bpo_pago.DIA_DE_PAGO, 
 bpo_pago.CREATED_BY, 
 bpo_pago.CREATION_DATE, 
 bpo_pago.LAST_UPDATED_BY, 
 bpo_pago.LAST_UPDATE_DATE, 
 bpo_pago.LAST_UPDATE_LOGIN, 
 bpo_pago.ATTRIBUTE_CATEGORY, 
 bpo_pago.ATTRIBUTE1, 
 bpo_pago.ATTRIBUTE2, 
 bpo_pago.ATTRIBUTE3, 
 bpo_pago.ATTRIBUTE4, 
 bpo_pago.ATTRIBUTE5
FROM XXQP_PDFT_BPO_PAGO bpo_pago
where bpo_pago.bpo_header_id = cur_bpo_header_id; /*52*/ 

 CURSOR getRNInfo(cur_bpo_header_id number) IS
 select (select description
 from fnd_lookup_values
 where lookup_type='XXFT_CONCEPTO_1'
 and language='ESA'
 and lookup_code = XPMRN.ESTADO_CODE
 ) RN_CONCEPTO1,
 (select description
 from fnd_lookup_values
 where lookup_type='XXFT_CONCEPTO_2'
 and language='ESA'
 and lookup_code = XPMRN.CONCEPTO_CODE
 ) RN_CONCEPTO2,
 XPMRN.PRECIO
 from XXQP_PDFT_BPO_REG_NEG XPMRN
 where XPMRN.BPO_HEADER_ID = cur_bpo_header_id; 

/**Funcion de reemplazo**/
/** https://dev.w3.org/html5/html-author/charref **/ 
FUNCTION replace_char_esp(p_cadena IN VARCHAR2)
 RETURN VARCHAR2 IS
 v_cadena VARCHAR2(4000);
 BEGIN
 v_cadena := REPLACE(p_cadena, CHR(38), CHR(38) || 'amp;');
 v_cadena := REPLACE(v_cadena, CHR(60), CHR(38)||'#60;'); /* < */
 v_cadena := REPLACE(v_cadena, CHR(62), CHR(38)||'#62;'); /* > */
 v_cadena := REPLACE(v_cadena, CHR(64), CHR(38)||'#64;'); /* arroba */
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
 v_cadena := REPLACE(v_cadena, CHR(50065), CHR(38)||'#241;'); /* v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'ntilde'); */
 v_cadena := REPLACE(v_cadena, CHR(49844), '');
 v_cadena := REPLACE(v_cadena, CHR(50090), '');
 v_cadena := REPLACE(v_cadena, CHR(50056), 'E');
 RETURN v_cadena;
 END replace_char_esp;

 procedure main(pso_errmsg out varchar2
 ,pso_errcod out varchar2 
 ,pni_numero_ft in number
 ) is 
 
 
 bpo_head_info_rec get_bpo_head_info%ROWTYPE;
 bpo_serv_info_rec get_bpo_serv_info%ROWTYPE;
 bpo_gyc_info_rec get_bpo_gyc_info%ROWTYPE;
 bpo_oper_info_rec get_bpo_oper_info%ROWTYPE;
 bpo_adqu_info_rec get_bpo_adqu_info%ROWTYPE;
 bpo_pago_info_rec get_bpo_pago_info%ROWTYPE; 
 RNInfo_rec getRNInfo%ROWTYPE;
 
 ln_bpo_header_id number ; 
 lc_info clob := ''; 
 ls_errmsg varchar2(2000); 
 ls_errcod varchar2(2000); 
 
 v_new_xml_data varchar2(32767); 
 v_iterations number;
 v_clob_len number; 
 v_chunk_length number := 32767;
 v_length_clob number := 0; 
 v_offset number :=1;
 v_char char(1); 
 begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 
 select id
 into ln_bpo_header_id
 from XXQP_PDFT_BPO_HEADER
 WHERE NUMERO_FT = pni_numero_ft; 
 
 get_info(pso_errmsg => ls_errmsg
 ,pso_errcod => ls_errcod
 ,pco_info => lc_info
 ,pni_bpo_header_id => ln_bpo_header_id
 );
 
 fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info)); 
 fnd_file.put_line(fnd_file.log,'dbms_lob.instr(lc_info):'||dbms_lob.instr(lc_info,'<G_REGNEG>')); 
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
 
 
 procedure get_info(pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pco_info out clob
 ,pni_bpo_header_id in number
 ) is
 lc_info clob := ''; 
 
 bpo_head_info_rec get_bpo_head_info%ROWTYPE;
 precio_info_rec get_precio_info%ROWTYPE;
 bpo_serv_info_rec get_bpo_serv_info%ROWTYPE;
 bpo_gyc_info_rec get_bpo_gyc_info%ROWTYPE;
 bpo_oper_info_rec get_bpo_oper_info%ROWTYPE;
 bpo_adqu_info_rec get_bpo_adqu_info%ROWTYPE;
 bpo_pago_info_rec get_bpo_pago_info%ROWTYPE;
 RNInfo_rec getRNInfo%ROWTYPE;
 
 ln_sum1 number := 0; 
 ln_sum2 number := 0; 
 ln_sum3 number := 0; 
 
 ls_regneg_precio varchar2(2000); 
 
 begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 
 
 lc_info := lc_info||'<XXQP_PDFT_BPO>'; 
 OPEN get_bpo_head_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_head_info INTO bpo_head_info_rec;
 EXIT WHEN get_bpo_head_info%NOTFOUND;
 lc_info := lc_info||'<UNIDAD_DE_NEGOCIO>'||replace_char_esp(bpo_head_info_rec.UNIDAD_DE_NEGOCIO_M)||'</UNIDAD_DE_NEGOCIO>'; 
 lc_info := lc_info||'<NOMBRE_CLIENTE>'||replace_char_esp(bpo_head_info_rec.nombre_del_cliente)||'</NOMBRE_CLIENTE>'; 
 lc_info := lc_info||'<EMPRESA_QUE_FACTURA>'||replace_char_esp(bpo_head_info_rec.empresa_que_factura_m)||'</EMPRESA_QUE_FACTURA>'; 
 lc_info := lc_info||'<EJECUTIVO>'||replace_char_esp(bpo_head_info_rec.EJECUTIVO)||'</EJECUTIVO>'; 
 lc_info := lc_info||'<NUMERO_FT>'||bpo_head_info_rec.NUMERO_FT||'</NUMERO_FT>'; 
 lc_info := lc_info||'<STATUS_MEANING>'||replace_char_esp(bpo_head_info_rec.STATUS_MEANING)||'</STATUS_MEANING>'; 
 lc_info := lc_info||'<CREATION_DATE>'||TO_CHAR(bpo_head_info_rec.creation_date,'DD-MON-YYYY', 'NLS_DATE_LANGUAGE = SPANISH')||'</CREATION_DATE>'; 
 lc_info := lc_info||'<ARTICULO_ORACLE>'||bpo_head_info_rec.ARTICULO_ORACLE||'</ARTICULO_ORACLE>';
 fnd_file.put_line(fnd_file.log,'<ARTICULO_ORACLE>'||bpo_head_info_rec.ARTICULO_ORACLE||'</ARTICULO_ORACLE>');

 END LOOP;
 CLOSE get_bpo_head_info;
 
 
 ln_sum1 := 0; 
 ln_sum2 := 0; 
 ln_sum3 := 0; 
 
 OPEN get_precio_info(pni_bpo_header_id);
 LOOP
 FETCH get_precio_info INTO precio_info_rec;
 EXIT WHEN get_precio_info%NOTFOUND;
 lc_info := lc_info||'<G_PRECIO>'; 
 lc_info := lc_info||'<CONCEPTO>'||precio_info_rec.concepto||'</CONCEPTO>'; 
 lc_info := lc_info||'<CANTIDAD>'||precio_info_rec.cantidad||'</CANTIDAD>'; 
 lc_info := lc_info||'<PRECIO_UNITARIO>'||trim(to_char(precio_info_rec.precio_unitario,gs_currency_format))||'</PRECIO_UNITARIO>'; 
 lc_info := lc_info||'<TOTAL_CONCEPTO>'||trim(to_char(precio_info_rec.total_concepto,gs_currency_format))||'</TOTAL_CONCEPTO>'; 
 lc_info := lc_info||'</G_PRECIO>'; 
 ln_sum1 := nvl(ln_sum1,0)+precio_info_rec.cantidad; 
 ln_sum2 := nvl(ln_sum2,0)+precio_info_rec.precio_unitario; 
 ln_sum3 := nvl(ln_sum3,0)+precio_info_rec.total_concepto; 
 
 
 END LOOP;
 CLOSE get_precio_info;
 
 lc_info := lc_info||'<SUM1>'||ln_sum1||'</SUM1>'; 
 lc_info := lc_info||'<SUM2>'||trim(to_char(ln_sum2,gs_currency_format))||'</SUM2>'; 
 lc_info := lc_info||'<SUM3>'||trim(to_char(ln_sum3,gs_currency_format))||'</SUM3>'; 
 
 OPEN get_bpo_serv_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_serv_info INTO bpo_serv_info_rec;
 EXIT WHEN get_bpo_serv_info%NOTFOUND;
 
 if 'Y' = bpo_serv_info_rec.COBRANZA then 
 lc_info := lc_info||'<COBRANZA>'||'Cobranza.'||'</COBRANZA>'; 
 end if; 
 
 if 'Y' = bpo_serv_info_rec.GESTOR_ADMINISTRATIVO then 
 lc_info := lc_info||'<GESTOR_ADMINISTRATIVO>'||'Gestor Administrativo.'||'</GESTOR_ADMINISTRATIVO>'; 
 end if; 
 
 if 'Y' = bpo_serv_info_rec.VENTAS then 
 lc_info := lc_info||'<VENTAS>'||'Ventas.'||'</VENTAS>'; 
 end if; 
 
 if 'Y' = bpo_serv_info_rec.RECOLE_DOC_MATER then 
 lc_info := lc_info||'<RECOLE_DOC_MATER>'||'Recoleccion documentos y/o materiales.'||'</RECOLE_DOC_MATER>'; 
 end if; 
 
 if 'Y' = bpo_serv_info_rec.ENTREGA_A_DOMICILIO then 
 lc_info := lc_info||'<ENTREGA_A_DOMICILIO>'||'Entrega a domicilio.'||'</ENTREGA_A_DOMICILIO>'; 
 end if; 
 
 
 if 'Y' = bpo_serv_info_rec.TECNICO then 
 lc_info := lc_info||'<TECNICO>'||'Tecnico.'||'</TECNICO>'; 
 end if; 
 
 lc_info := lc_info||'<OTROS>'||bpo_serv_info_rec.OTROS||'</OTROS>'; 
 lc_info := lc_info||'<FECHA_INICIO_SERVICIO>'|| replace(to_char(bpo_serv_info_rec.FECHA_INICIO_SERVICIO,'DD Month YYYY', 'NLS_DATE_LANGUAGE = SPANISH'),' ',' ')||'</FECHA_INICIO_SERVICIO>';
 lc_info := lc_info||'<EL_MENSAJERO_MANEJE>'||bpo_serv_info_rec.el_mensajero_maneje_c||'</EL_MENSAJERO_MANEJE>'; 
 lc_info := lc_info||'<DIAS_SEMANA_LABORARA>'||bpo_serv_info_rec.DIAS_SEMANA_LABORARA_V2||'</DIAS_SEMANA_LABORARA>'; 
 lc_info := lc_info||'<NO_HRS_DIARIAS>'||bpo_serv_info_rec.NO_HRS_DIARIAS||'</NO_HRS_DIARIAS>'; 
 lc_info := lc_info||'<HORARIO_DE_TRABAJO>'||bpo_serv_info_rec.HORARIO_DE_TRABAJO_V2||'</HORARIO_DE_TRABAJO>'; 
 lc_info := lc_info||'<DIRECCION_BASE>'||replace_char_esp(bpo_serv_info_rec.DIRECCION_BASE)||'</DIRECCION_BASE>'; 
 lc_info := lc_info||'<OBSERVACIONES>'||replace_char_esp(bpo_serv_info_rec.OBSERVACIONES)||'</OBSERVACIONES>'; 
 
 
 END LOOP;
 CLOSE get_bpo_serv_info;
 
 OPEN get_bpo_gyc_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_gyc_info INTO bpo_gyc_info_rec;
 EXIT WHEN get_bpo_gyc_info%NOTFOUND;
 lc_info := lc_info||'<GYC_MOTO_125>'||bpo_gyc_info_rec.MOTO_125||'</GYC_MOTO_125>'; 
 lc_info := lc_info||'<GYC_CASCO>'||bpo_gyc_info_rec.CASCO||'</GYC_CASCO>'; 
 lc_info := lc_info||'<GYC_CAJA_GRANDE>'||bpo_gyc_info_rec.CAJA_GRANDE||'</GYC_CAJA_GRANDE>'; 
 lc_info := lc_info||'<GYC_CAJA_CHICA_ROJA>'||bpo_gyc_info_rec.CAJA_CHICA_ROJA||'</GYC_CAJA_CHICA_ROJA>'; 
 lc_info := lc_info||'<GYC_CAMISAS>'||bpo_gyc_info_rec.CAMISAS||'</GYC_CAMISAS>'; 
 lc_info := lc_info||'<GYC_IMPERMEABLE>'||bpo_gyc_info_rec.IMPERMEABLE||'</GYC_IMPERMEABLE>'; 
 lc_info := lc_info||'<GYC_TARJETA_GASOLINA>'||bpo_gyc_info_rec.TARJETA_GASOLINA||'</GYC_TARJETA_GASOLINA>'; 
 lc_info := lc_info||'<GYC_PANTALON_VESTIR>'||bpo_gyc_info_rec.PANTALON_VESTIR||'</GYC_PANTALON_VESTIR>'; 
 lc_info := lc_info||'<GYC_CAJA_NEGRA>'||bpo_gyc_info_rec.CAJA_NEGRA||'</GYC_CAJA_NEGRA>'; 
 lc_info := lc_info||'<GYC_HIELERA>'||bpo_gyc_info_rec.HIELERA||'</GYC_HIELERA>'; 
 lc_info := lc_info||'<GYC_MOVIL>'||bpo_gyc_info_rec.MOVIL||'</GYC_MOVIL>'; 
 lc_info := lc_info||'<GYC_AUTOMOVIL>'||bpo_gyc_info_rec.AUTOMOVIL||'</GYC_AUTOMOVIL>'; 
 lc_info := lc_info||'<GYC_GUIA_ROJI>'||bpo_gyc_info_rec.GUIA_ROJI||'</GYC_GUIA_ROJI>'; 
 lc_info := lc_info||'<GYC_OTROS>'||bpo_gyc_info_rec.OTROS||'</GYC_OTROS>'; 
 lc_info := lc_info||'<GYC_COMENTARIOS>'||bpo_gyc_info_rec.COMENTARIOS||'</GYC_COMENTARIOS>'; 
 
 END LOOP;
 CLOSE get_bpo_gyc_info;
 
 OPEN get_bpo_oper_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_oper_info INTO bpo_oper_info_rec;
 EXIT WHEN get_bpo_oper_info%NOTFOUND;
 
 lc_info := lc_info||'<OPER_MOTO_125>'||bpo_OPER_info_rec.MOTO_125||'</OPER_MOTO_125>'; 
 lc_info := lc_info||'<OPER_CASCO>'||bpo_OPER_info_rec.CASCO||'</OPER_CASCO>'; 
 lc_info := lc_info||'<OPER_CAJA_GRANDE>'||bpo_OPER_info_rec.CAJA_GRANDE||'</OPER_CAJA_GRANDE>'; 
 lc_info := lc_info||'<OPER_CAJA_CHICA_ROJA>'||bpo_OPER_info_rec.CAJA_CHICA_ROJA||'</OPER_CAJA_CHICA_ROJA>'; 
 lc_info := lc_info||'<OPER_CAMISAS>'||bpo_OPER_info_rec.CAMISAS||'</OPER_CAMISAS>'; 
 lc_info := lc_info||'<OPER_IMPERMEABLE>'||bpo_OPER_info_rec.IMPERMEABLE||'</OPER_IMPERMEABLE>'; 
 lc_info := lc_info||'<OPER_TARJETA_GASOLINA>'||bpo_OPER_info_rec.TARJETA_GASOLINA||'</OPER_TARJETA_GASOLINA>'; 
 lc_info := lc_info||'<OPER_PANTALON_VESTIR>'||bpo_OPER_info_rec.PANTALON_VESTIR||'</OPER_PANTALON_VESTIR>'; 
 lc_info := lc_info||'<OPER_CAJA_NEGRA>'||bpo_OPER_info_rec.CAJA_NEGRA||'</OPER_CAJA_NEGRA>'; 
 lc_info := lc_info||'<OPER_HIELERA>'||bpo_OPER_info_rec.HIELERA||'</OPER_HIELERA>'; 
 lc_info := lc_info||'<OPER_MOVIL>'||bpo_OPER_info_rec.MOVIL||'</OPER_MOVIL>'; 
 lc_info := lc_info||'<OPER_AUTOMOVIL>'||bpo_OPER_info_rec.AUTOMOVIL||'</OPER_AUTOMOVIL>'; 
 lc_info := lc_info||'<OPER_GUIA_ROJI>'||bpo_OPER_info_rec.GUIA_ROJI||'</OPER_GUIA_ROJI>'; 
 lc_info := lc_info||'<OPER_OTROS>'||bpo_OPER_info_rec.OTROS||'</OPER_OTROS>'; 
 lc_info := lc_info||'<OPER_COMENTARIOS>'||bpo_OPER_info_rec.COMENTARIOS||'</OPER_COMENTARIOS>'; 
 
 END LOOP;
 CLOSE get_bpo_oper_info;
 
 OPEN get_bpo_adqu_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_adqu_info INTO bpo_adqu_info_rec;
 EXIT WHEN get_bpo_adqu_info%NOTFOUND;
 
 lc_info := lc_info||'<ADQU_MOTO_125>'||bpo_ADQU_info_rec.MOTO_125||'</ADQU_MOTO_125>'; 
 lc_info := lc_info||'<ADQU_CASCO>'||bpo_ADQU_info_rec.CASCO||'</ADQU_CASCO>'; 
 lc_info := lc_info||'<ADQU_CAJA_GRANDE>'||bpo_ADQU_info_rec.CAJA_GRANDE||'</ADQU_CAJA_GRANDE>'; 
 lc_info := lc_info||'<ADQU_CAJA_CHICA_ROJA>'||bpo_ADQU_info_rec.CAJA_CHICA_ROJA||'</ADQU_CAJA_CHICA_ROJA>'; 
 lc_info := lc_info||'<ADQU_CAMISAS>'||bpo_ADQU_info_rec.CAMISAS||'</ADQU_CAMISAS>'; 
 lc_info := lc_info||'<ADQU_IMPERMEABLE>'||bpo_ADQU_info_rec.IMPERMEABLE||'</ADQU_IMPERMEABLE>'; 
 lc_info := lc_info||'<ADQU_TARJETA_GASOLINA>'||bpo_ADQU_info_rec.TARJETA_GASOLINA||'</ADQU_TARJETA_GASOLINA>'; 
 lc_info := lc_info||'<ADQU_PANTALON_VESTIR>'||bpo_ADQU_info_rec.PANTALON_VESTIR||'</ADQU_PANTALON_VESTIR>'; 
 lc_info := lc_info||'<ADQU_CAJA_NEGRA>'||bpo_ADQU_info_rec.CAJA_NEGRA||'</ADQU_CAJA_NEGRA>'; 
 lc_info := lc_info||'<ADQU_HIELERA>'||bpo_ADQU_info_rec.HIELERA||'</ADQU_HIELERA>'; 
 lc_info := lc_info||'<ADQU_MOVIL>'||bpo_ADQU_info_rec.MOVIL||'</ADQU_MOVIL>'; 
 lc_info := lc_info||'<ADQU_AUTOMOVIL>'||bpo_ADQU_info_rec.AUTOMOVIL||'</ADQU_AUTOMOVIL>'; 
 lc_info := lc_info||'<ADQU_GUIA_ROJI>'||bpo_ADQU_info_rec.GUIA_ROJI||'</ADQU_GUIA_ROJI>'; 
 lc_info := lc_info||'<ADQU_OTROS>'||bpo_ADQU_info_rec.OTROS||'</ADQU_OTROS>'; 
 lc_info := lc_info||'<ADQU_COMENTARIOS>'||bpo_ADQU_info_rec.COMENTARIOS||'</ADQU_COMENTARIOS>'; 
 
 END LOOP;
 CLOSE get_bpo_adqu_info;
 
 
 OPEN get_bpo_pago_info(pni_bpo_header_id);
 LOOP
 FETCH get_bpo_pago_info INTO bpo_pago_info_rec;
 EXIT WHEN get_bpo_pago_info%NOTFOUND;
 lc_info := lc_info||'<EJECUTIVO_DE_VENTAS>'||bpo_pago_info_rec.EJECUTIVO_DE_VENTAS||'</EJECUTIVO_DE_VENTAS>'; 
 lc_info := lc_info||'<PLAZA>'||bpo_pago_info_rec.PLAZA||'</PLAZA>'; 
 lc_info := lc_info||'<SUBTOTAL>'||trim(to_char(bpo_pago_info_rec.SUBTOTAL,gs_currency_format))||'</SUBTOTAL>'; 
 lc_info := lc_info||'<IVA>'||trim(to_char(bpo_pago_info_rec.IVA,gs_currency_format))||'</IVA>'; 
 lc_info := lc_info||'<TOTAL>'||trim(to_char(bpo_pago_info_rec.TOTAL,gs_currency_format))||'</TOTAL>'; 
 lc_info := lc_info||'<FORMA_DE_PAGO>'||bpo_pago_info_rec.FORMA_DE_PAGO_C||'</FORMA_DE_PAGO>'; 
 lc_info := lc_info||'<DIA_DE_PAGO>'||replace_char_esp(bpo_pago_info_rec.DIA_DE_PAGO)||'</DIA_DE_PAGO>'; 
 
 END LOOP;
 CLOSE get_bpo_pago_info;
 
 begin 
 OPEN getRNInfo(pni_bpo_header_id);
 LOOP
 FETCH getRNInfo INTO RNInfo_rec;
 EXIT WHEN getRNInfo%NOTFOUND;
 lc_info := lc_info||'<G_REGNEG>'; 
 lc_info := lc_info||'<RN_CONCEPTO1>'||RNInfo_rec.RN_CONCEPTO1||'</RN_CONCEPTO1>'; 
 lc_info := lc_info||'<RN_CONCEPTO2>'||RNInfo_rec.RN_CONCEPTO2||'</RN_CONCEPTO2>'; 
 ls_regneg_precio :=trim(to_char(RNInfo_rec.PRECIO,gs_currency_format));
 lc_info := lc_info||'<RN_PRECIO>'||ls_regneg_precio||'</RN_PRECIO>'; 
 lc_info := lc_info||'</G_REGNEG>'||CHR(10); 
 END LOOP;
 CLOSE getRNInfo;
 exception when others then 
 pso_errmsg := 'Excepcion Paquete APPS.xxqp_pdft_myp_pkg metodo getRNInfo:'||sqlerrm||', '||sqlcode||',RNInfo_rec.PRECIO:'||RNInfo_rec.PRECIO;
 pso_errcod := 2; 
 fnd_file.put_line(fnd_file.log,pso_errmsg);
 return;
 
 end; 
 
 lc_info := lc_info||'</XXQP_PDFT_BPO>'; 
 
 pco_info := lc_info;
 
 exception when others then 
 pso_errmsg := 'Excepcion procedimiento main:'||sqlerrm||', '||sqlcode;
 pso_errcod := '2';
 end get_info;
 
 procedure get_client_info(pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pni_party_id in number
 ,pso_empr_que_fact_c out varchar2
 ,pso_frecuencia_facturacion out varchar2
 ,pso_punto_recoleccion out varchar2
 ,pso_contacto_cierre out varchar2
 ,pso_lunes out varchar2
 ,pso_martes out varchar2 
 ,pso_miercoles out varchar2
 ,pso_jueves out varchar2 
 ,pso_viernes out varchar2 
 ,pso_sabado out varchar2 
 ,pso_domingo out varchar2 
 ) is
 ln_pdft_cliente_hdr_id number; 
 begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 pso_empr_que_fact_c := null; 
 pso_frecuencia_facturacion := null; 
 pso_punto_recoleccion := null; 
 pso_contacto_cierre := null; 
 
 
 select to_number(attribute2)
 into ln_pdft_cliente_hdr_id
 from hz_parties
 where party_id = pni_party_id; 
 
 if ln_pdft_cliente_hdr_id is null then 
 return; 
 end if; 
 
 
 begin
 select empresa_que_factura_c 
 into pso_empr_que_fact_c
 from XXQP_PDFT_CLIENTES_HEADER
 where id = ln_pdft_cliente_hdr_id; 
 exception when others then 
 pso_empr_que_fact_c := null; 
 end; 
 
 begin 
 select CICLO_DE_FACTURACION_C
 ,lunes
 ,martes
 ,miercoles
 ,jueves
 ,viernes
 ,sabado
 ,domingo
 into pso_frecuencia_facturacion
 ,pso_lunes
 ,pso_martes
 ,pso_miercoles
 ,pso_jueves
 ,pso_viernes
 ,pso_sabado
 ,pso_domingo
 from XXQP_PDFT_CLIENTES_FACT_PAG
 where HEADER_ID = ln_pdft_cliente_hdr_id; 
 exception when others then 
 pso_frecuencia_facturacion := null; 
 end; 
 
 begin
 select decode(prim_entrega_en_qp,'Y','QP','N',prim_nombre||','|| prim_direccion) 
 into pso_punto_recoleccion
 from XXQP_PDFT_CLIENTES_PUNTO_RECO
 where HEADER_ID = ln_pdft_cliente_hdr_id; 
 
 exception when others then 
 pso_punto_recoleccion := null; 
 end; 
 
 begin 
 select nombre
 into pso_contacto_cierre
 from XXQP_PDFT_CLIENTES_CONTACTOS
 where tipo_contacto = 'Cierre y Seguimiento'
 and HEADER_ID = ln_pdft_cliente_hdr_id; 
 
 exception when others then 
 pso_contacto_cierre := null; 
 end; 
 
 exception when others then 
 pso_errmsg := 'Excepcion metodo get_client_info:'||sqlerrm; 
 pso_errcod := '0';
 end get_client_info; 
 
 
 end APPS.xxqp_pdft_bpo_pkg;
/


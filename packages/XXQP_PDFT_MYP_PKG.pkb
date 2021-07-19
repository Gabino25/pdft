CREATE OR REPLACE package body xxqp_pdft_myp_pkg is 

/** 15042020 se agrega nvl al nombre del cliente **/
/** 17042020 se agrega nvl al nombre del cliente en obtiene reportes **/
/** 24042020 se re-direcciona a la vista custom **/
/** 03052022 se filtra nombre de cliente tambien por numeros **/
/** 16072021 se deja de concatenar en el procedimiento reportes y se usa la libreria dbms_lob , create, update y close **/
/** 16072021 se agregan 2 nuevos campos PRECIO_CD y PRECIO_PROTON_CD **/ 
/** 19072021 se muestran los campos Precios PROTON en el pdf **/

/** gs_currency_format varchar2(2000) := '$999,999,999,999,999,999.99'; **/
 gs_currency_format varchar2(2000) := '$999,999,999,999,999,999.99'; 


CURSOR get_myp_head_info (cur_myp_header_id number) IS
 SELECT myp_head.ID, 
 myp_head.NUMERO_FT, 
 myp_head.REV, 
 myp_head.STATUS, 
 decode(myp_head.STATUS,'PRELIMINAR','En captura',
 'ABIERTA','Activa' ,
 'CERRADA','Cerrada',
 'CANCELADA','Cancelada',
 'CAMBIO_DE_PRECIO','Cambio De Precio' ) STATUS_MEANING,
 myp_head.NUMERO_FT_REFERENCIA, 
 myp_head.PARTY_ID, 
 myp_head.PDFT_CLIENTE_HEADER_ID, 
 myp_head.EMPRESA_QUE_FACTURA_C, 
 ( select LEGAL_ENTITY_IDENTIFIER||' - '||NAME /*LEGAL_ENTITY_NAME*/meaning
 from xle_entity_profiles
 where LEGAL_ENTITY_ID = myp_head.EMPRESA_QUE_FACTURA_C
 ) EMPRESA_QUE_FACTURA_M,
 myp_head.UNIDAD_DE_NEGOCIO_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'UNIDAD_DE_NEGOCIO'
 and lookup_code = myp_head.UNIDAD_DE_NEGOCIO_C ) UNIDAD_DE_NEGOCIO_M,
 myp_head.FRECUENCIA_FACTURACION_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'CICLO_DE_FACTURACION'
 and lookup_code = myp_head.FRECUENCIA_FACTURACION_C ) FRECUENCIA_FACTURACION_M,
 myp_head.CONTRATO_FLAG, 
 myp_head.CONTRATO_FILE_NAME, 
 myp_head.CONTRATO_CONTENT_TYPE, 
 myp_head.CONTRATO_FILE, 
 myp_head.FECHA_INICIAL_OPERACION, 
 myp_head.CREATED_BY, 
 myp_head.CREATION_DATE, 
 myp_head.LAST_UPDATED_BY, 
 myp_head.LAST_UPDATE_DATE, 
 myp_head.LAST_UPDATE_LOGIN, 
 myp_head.ATTRIBUTE_CATEGORY, 
 myp_head.ATTRIBUTE1, 
 myp_head.ATTRIBUTE2, 
 myp_head.ATTRIBUTE3, 
 myp_head.ATTRIBUTE4, 
 myp_head.ATTRIBUTE5,
 (select p.party_name /** 15042020 **/
 from hz_parties p /**24042020**/
 where p.party_id = myp_head.party_id) nombre_del_cliente,
 (select p.party_name 
 from hz_parties p
 where p.party_id = myp_head.party_id) razon_social,
/** (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'MGR_PDFT_USUARIOS'
 and lookup_code = myp_head.ejecutivo) ejecutivo **/
 (select usuario 
 from XXQP_PDFT_USUARIOS_RO
 where id = myp_head.ejecutivo ) ejecutivo, 
 myp_head.ARTICULO_ORACLE,
 myp_head.MODIF_REALIZ
FROM XXQP_PDFT_MYP_HEADER myp_head
where myp_head.id = cur_myp_header_id;


CURSOR get_myp_gral_info (cur_myp_header_id number) IS
 SELECT myp_gral.ID, 
 myp_gral.MYP_HEADER_ID, 
 myp_gral.IMPRESOR, 
 myp_gral.NOMBRE_PRODUCTO, 
 myp_gral.VOLUMEN_APROX, 
 myp_gral.PUNTO_DE_RECOLECCION, 
 myp_gral.CONTACTO_PARA_CIERRE, 
 myp_gral.PERIODICIDAD_C,
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'PERIODICIDAD'
 and lookup_code = myp_gral.PERIODICIDAD_C ) PERIODICIDAD_M, 
 myp_gral.TIPO_PRODUCTO_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'TIPO_DE_PRODUCTO'
 and lookup_code = myp_gral.TIPO_PRODUCTO_C ) TIPO_PRODUCTO_M,
 myp_gral.PESO_PRODUCTO_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'PESO_PRODUCTO'
 and lookup_code = myp_gral.PESO_PRODUCTO_C ) PESO_PRODUCTO_M,
 myp_gral.DIMENSIONES_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'DIMENSIONES'
 and lookup_code = myp_gral.DIMENSIONES_C) DIMENSIONES_M,
 myp_gral.TIPO_COMISION_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'TIPO_COMISION'
 and lookup_code = myp_gral.TIPO_COMISION_C ) TIPO_COMISION_M,
 myp_gral.SE_FACTURA_C, 
 decode(myp_gral.SE_FACTURA_C
 ,'Y','Si'
 ,'N','No'
 ) SE_FACTURA_M,
 myp_gral.FORMATO_PARA_CIERRE_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'FORMATO_CIERRE'
 and lookup_code =myp_gral.FORMATO_PARA_CIERRE_C ) FORMATO_PARA_CIERRE_M,
 myp_gral.DIAS_HABILES_PAGO, 
 myp_gral.DIAS_RECEPCION_FATURACION, 
 decode(myp_gral.lunes,'Y','L','N','')||','||
 decode(myp_gral.martes,'Y','M','N','')||','||
 decode(myp_gral.miercoles,'Y','M','N','')||','||
 decode(myp_gral.jueves,'Y','J','N','')||','||
 decode(myp_gral.viernes,'Y','V','N','')||','||
 decode(myp_gral.sabado,'Y','S','N','')||','||
 decode(myp_gral.domingo,'Y','D','N','') DIAS_RECEPCION,
 myp_gral.REQUIERE_VOBO_C, 
 decode(myp_gral.REQUIERE_VOBO_C
 ,'Y','Si'
 ,'N','No'
 ) REQUIERE_VOBO_M,
 myp_gral.TIPO_VOBO, 
 myp_gral.TIPO_DE_ENTREGA_C, 
 decode(TIPO_DE_ENTREGA_C
 ,'ACUSE','Acuse'
 , 'ORDINARIO' ,'Ordinario' 
 ,'SEMI_ACUSE' ,'Semi Acuse' 
 ) TIPO_DE_ENTREGA_M,
 myp_gral.POLITICA_DE_ENTREGA_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'POLITICA_DE_ENTREGA'
 and lookup_code = myp_gral.POLITICA_DE_ENTREGA_C ) POLITICA_DE_ENTREGA_M,
 myp_gral.CREATED_BY, 
 myp_gral.CREATION_DATE, 
 myp_gral.LAST_UPDATED_BY, 
 myp_gral.LAST_UPDATE_DATE, 
 myp_gral.LAST_UPDATE_LOGIN, 
 myp_gral.ATTRIBUTE_CATEGORY, 
 myp_gral.ATTRIBUTE1, 
 myp_gral.ATTRIBUTE2, 
 myp_gral.ATTRIBUTE3, 
 myp_gral.ATTRIBUTE4, 
 myp_gral.ATTRIBUTE5,
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'MYP_ACUSE'
 and lookup_code = myp_gral.ACUSE_C ) ACUSE_M,
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'MYP_ORDINARIO'
 and lookup_code = myp_gral.ORDINARIO_C ) ORDINARIO_M,
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'MYP_SEMI_ACUSE'
 and lookup_code = myp_gral.SEMI_ACUSE_C ) SEMI_ACUSE_M
FROM XXQP_PDFT_MYP_GENERAL myp_gral
 where myp_gral.MYP_HEADER_ID = cur_myp_header_id; 

 CURSOR get_myp_cob_info (cur_myp_header_id number) IS
 SELECT myp_cob.ID, 
 myp_cob.MYP_HEADER_ID, 
 myp_cob.PLAZA_PROPIETARIA_C, 
 (select description 
 from xxqp_pdft_mgr_catalogos
 where lookup_type = 'PLAZA_PROPIETARIA'
 and lookup_code = myp_cob.PLAZA_PROPIETARIA_C ) PLAZA_PROPIETARIA_M,
 /** myp_cob.TIPO_COBERTURA_C, **/
 myp_cob.TC_NACIONAL,
 myp_cob.ENTREGA_LOCAL, 
 myp_cob.DR_LOCAL, 
 myp_cob.DI_LOCAL, 
 myp_cob.TC_REGIONAL,
 myp_cob.TC_LOCAL,
 myp_cob.MENCIONAR_ESTADOS,
 myp_cob.ENTREGA_FORANEO, 
 myp_cob.DR_FORANEO, 
 myp_cob.DI_FORANEO, 
 myp_cob.CREATED_BY, 
 myp_cob.CREATION_DATE, 
 myp_cob.LAST_UPDATED_BY, 
 myp_cob.LAST_UPDATE_DATE, 
 myp_cob.LAST_UPDATE_LOGIN, 
 myp_cob.ATTRIBUTE_CATEGORY, 
 myp_cob.ATTRIBUTE1, 
 myp_cob.ATTRIBUTE2, 
 myp_cob.ATTRIBUTE3, 
 myp_cob.ATTRIBUTE4, 
 myp_cob.ATTRIBUTE5, 
 myp_cob.comentarios,
 myp_cob.comentarios_ilim
FROM XXQP_PDFT_MYP_COBERTURA myp_cob
where myp_cob.MYP_HEADER_ID = cur_myp_header_id; 

 CURSOR get_myp_dist_info (cur_myp_header_id number) IS
 SELECT myp_dist.ID, 
 myp_dist.MYP_HEADER_ID, 
 decode(myp_dist.DIGITALIZACION_ACUSES,'Y','X',null) DIGITALIZACION_ACUSES, 
 decode(myp_dist.CAPTURA_DEVOLUCIONES,'Y','X',null) CAPTURA_DEVOLUCIONES,
 decode(myp_dist.REPORTE_GPS,'Y','X',null) REPORTE_GPS,
 decode(myp_dist.REPORTE_RECEPCION,'Y','X',null) REPORTE_RECEPCION, 
 decode(myp_dist.CAPTURA_ACUSES,'Y','X',null) CAPTURA_ACUSES,
 decode(myp_dist.SEGUIMIENTO_QUEJAS,'Y','X',null) SEGUIMIENTO_QUEJAS,
 decode(myp_dist.PROPORCIONAMOS_INSUMOS,'Y','X',null) PROPORCIONAMOS_INSUMOS,
 decode(myp_dist.ETIQUETADO,'Y','X',null) ETIQUETADO,
 decode(myp_dist.ENSOBRETADO,'Y','X',null) ENSOBRETADO,
 decode(myp_dist.GENERACION_ACUSE,'Y','X',null) GENERACION_ACUSE,
 myp_dist.DIAS_OPERACION_LOCAL, 
 myp_dist.DIAS_OPERACION_FORANEO, 
 myp_dist.CIERRE_ELECTRONICO, 
 myp_dist.ENVIO_PIEZAS_FISICAS, 
 myp_dist.COMENTARIOS_DISTRIBUCION, 
 myp_dist.CREATED_BY, 
 myp_dist.CREATION_DATE, 
 myp_dist.LAST_UPDATED_BY, 
 myp_dist.LAST_UPDATE_DATE, 
 myp_dist.LAST_UPDATE_LOGIN, 
 myp_dist.ATTRIBUTE_CATEGORY, 
 myp_dist.ATTRIBUTE1, 
 myp_dist.ATTRIBUTE2, 
 myp_dist.ATTRIBUTE3, 
 myp_dist.ATTRIBUTE4, 
 myp_dist.ATTRIBUTE5,
 myp_dist.COMENTARIOS_DISTRIBUCION_ILIM
FROM XXQP_PDFT_MYP_DISTRIBUCION myp_dist
where myp_dist.MYP_HEADER_ID = cur_myp_header_id; 

 CURSOR get_myp_proc_info (cur_myp_header_id number) IS
 SELECT myp_proc.ID, 
 myp_proc.MYP_HEADER_ID, 
 myp_proc.REGION, 
 myp_proc.PROSESO, 
 myp_proc.OTROS_PROCESOS, 
 decode(myp_proc.SELECCIONAR,'Y','X',null) SELECCIONAR,
 myp_proc.PRECIO, 
 myp_proc.PRECIO_PROTON, 
 myp_proc.COMENTARIOS_INSTRUCC, 
 myp_proc.CREATED_BY, 
 myp_proc.CREATION_DATE, 
 myp_proc.LAST_UPDATED_BY, 
 myp_proc.LAST_UPDATE_DATE, 
 myp_proc.LAST_UPDATE_LOGIN, 
 myp_proc.ATTRIBUTE_CATEGORY, 
 myp_proc.ATTRIBUTE1, 
 myp_proc.ATTRIBUTE2, 
 myp_proc.ATTRIBUTE3, 
 myp_proc.ATTRIBUTE4, 
 myp_proc.ATTRIBUTE5
FROM XXQP_PDFT_MYP_PROCESOS myp_proc
WHERE myp_proc.REGION = 'PROCESO'
and myp_proc.MYP_HEADER_ID = cur_myp_header_id; 
 
 
CURSOR get_myp_oproc_info (cur_myp_header_id number) IS
 SELECT myp_oproc.ID, 
 myp_oproc.MYP_HEADER_ID, 
 myp_oproc.REGION, 
 myp_oproc.PROSESO, 
 myp_oproc.OTROS_PROCESOS, 
 decode(myp_oproc.SELECCIONAR,'Y','X',null) SELECCIONAR , 
 myp_oproc.PRECIO, 
 myp_oproc.PRECIO_PROTON, 
 myp_oproc.COMENTARIOS_INSTRUCC, 
 myp_oproc.CREATED_BY, 
 myp_oproc.CREATION_DATE, 
 myp_oproc.LAST_UPDATED_BY, 
 myp_oproc.LAST_UPDATE_DATE, 
 myp_oproc.LAST_UPDATE_LOGIN, 
 myp_oproc.ATTRIBUTE_CATEGORY, 
 myp_oproc.ATTRIBUTE1, 
 myp_oproc.ATTRIBUTE2, 
 myp_oproc.ATTRIBUTE3, 
 myp_oproc.ATTRIBUTE4, 
 myp_oproc.ATTRIBUTE5
FROM XXQP_PDFT_MYP_PROCESOS myp_oproc
WHERE myp_oproc.REGION = 'OTROS_PROCESOS'
 and myp_oproc.MYP_HEADER_ID = cur_myp_header_id; 
 
 CURSOR get_myp_instr_info (cur_myp_header_id number) IS
 SELECT myp_instr.ID, 
 myp_instr.MYP_HEADER_ID, 
 myp_instr.REGION, 
 myp_instr.PROSESO, 
 myp_instr.OTROS_PROCESOS, 
 myp_instr.SELECCIONAR, 
 myp_instr.PRECIO, 
 myp_instr.COMENTARIOS_INSTRUCC, 
 myp_instr.CREATED_BY, 
 myp_instr.CREATION_DATE, 
 myp_instr.LAST_UPDATED_BY, 
 myp_instr.LAST_UPDATE_DATE, 
 myp_instr.LAST_UPDATE_LOGIN, 
 myp_instr.ATTRIBUTE_CATEGORY, 
 myp_instr.ATTRIBUTE1, 
 myp_instr.ATTRIBUTE2, 
 myp_instr.ATTRIBUTE3, 
 myp_instr.ATTRIBUTE4, 
 myp_instr.ATTRIBUTE5,
 myp_instr.COMENTARIOS_INSTRUCC_ILIM
FROM XXQP_PDFT_MYP_PROCESOS myp_instr
WHERE myp_instr.REGION = 'COMENTARIOS'
 and myp_instr.MYP_HEADER_ID = cur_myp_header_id;
 
 
 CURSOR getRNInfo(cur_myp_header_id number) IS
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
 from XXQP_PDFT_MYP_REG_NEG XPMRN
 where XPMRN.MYP_HEADER_ID = cur_myp_header_id; 
 
 
 CURSOR get_info_reportes_v1 (cur_cliente_desde varchar2
 ,cur_cliente_hasta varchar2
 ,cur_fecha_desde date 
 ,cur_fecha_hasta date 
 ,cur_ficha_tecnica_desde number
 ,cur_ficha_tecnica_hasta number 
 ,cur_fre_fact_desde varchar2
 ,cur_fre_fact_hasta varchar2
 ,cur_status_desde varchar2
 ,cur_status_hasta varchar2
 ,cur_raz_soc_desde varchar2
 ,cur_raz_soc_hasta varchar2
 ) return rep_info_typ is 
 select ID,
 NUMERO_FT,
 REV,
 STATUS,
 STATUS_M,
 NUMERO_FT_REFERENCIA,
 PARTY_ID,
 PDFT_CLIENTE_HEADER_ID,
 EMPRESA_QUE_FACTURA_C,
 EMPRESA_QUE_FACTURA_M,
 EMPRESA_QUE_FACTURA_M_BKP,
 UNIDAD_DE_NEGOCIO_C,
 FRECUENCIA_FACTURACION_C,
 FRECUENCIA_FACTURACION_M,
 FECHA_INICIAL_OPERACION,
 CREATED_BY,
 CREATION_DATE,
 LAST_UPDATED_BY,
 LAST_UPDATE_DATE,
 LAST_UPDATE_LOGIN,
 ATTRIBUTE_CATEGORY,
 ATTRIBUTE1,
 ATTRIBUTE2,
 ATTRIBUTE3,
 ATTRIBUTE4,
 ATTRIBUTE5,
 NOMBRE_DEL_CLIENTE,
 PARTY_NAME,
 EJECUTIVO,
 UNIDAD_DE_NEGOCIO_M,
 CONTRATO_FLAG,
 ARTICULO_ORACLE,
 PRECIO,
 CONCEPTO,
 PRODUCTO,
 DIAS_DE_CREDITO,
 CNT_REG_NEG,
 PRECIO_CD,
 PRECIO_PROTON_CD
from XXQP_PDFT_REPORTES_V
where (upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'A') and nvl(upper(substr(cur_cliente_hasta,0,1)),'Z')
 OR upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'1') and nvl(upper(substr(cur_cliente_hasta,0,1)),'9') )
 and trunc(creation_date) between nvl(cur_fecha_desde,to_date ('01/01/0001','DD/MM/YYYY')) and nvl(cur_fecha_hasta,to_date ('31/12/4712','DD/MM/YYYY'))
 and NUMERO_FT between nvl(cur_ficha_tecnica_desde,0) and nvl(cur_ficha_tecnica_hasta,9999999999999999999999999999)
 and upper(substr(FRECUENCIA_FACTURACION_C,0,1)) between nvl(upper(substr(cur_fre_fact_desde,0,1)),'A') and nvl(upper(substr(cur_fre_fact_hasta,0,1)),'Z')
 and upper(substr(STATUS,0,1)) between nvl(upper(substr(cur_status_desde,0,1)),'A') and nvl(upper(substr(cur_status_hasta,0,1)),'Z')
 and (upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'A') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'Z')
 OR upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'1') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'9')
 )
order by NOMBRE_DEL_CLIENTE asc;

CURSOR get_info_reportes_v2 (cur_cliente_desde varchar2
 ,cur_cliente_hasta varchar2
 ,cur_fecha_desde date 
 ,cur_fecha_hasta date 
 ,cur_ficha_tecnica_desde number
 ,cur_ficha_tecnica_hasta number 
 ,cur_fre_fact_desde varchar2
 ,cur_fre_fact_hasta varchar2
 ,cur_status_desde varchar2
 ,cur_status_hasta varchar2
 ,cur_raz_soc_desde varchar2
 ,cur_raz_soc_hasta varchar2
 ) return rep_info_typ is 
 SELECT ID,
 NUMERO_FT,
 REV,
 STATUS,
 STATUS_M,
 NUMERO_FT_REFERENCIA,
 PARTY_ID,
 PDFT_CLIENTE_HEADER_ID,
 EMPRESA_QUE_FACTURA_C,
 EMPRESA_QUE_FACTURA_M,
 EMPRESA_QUE_FACTURA_M_BKP,
 UNIDAD_DE_NEGOCIO_C,
 FRECUENCIA_FACTURACION_C,
 FRECUENCIA_FACTURACION_M,
 FECHA_INICIAL_OPERACION,
 CREATED_BY,
 CREATION_DATE,
 LAST_UPDATED_BY,
 LAST_UPDATE_DATE,
 LAST_UPDATE_LOGIN,
 ATTRIBUTE_CATEGORY,
 ATTRIBUTE1,
 ATTRIBUTE2,
 ATTRIBUTE3,
 ATTRIBUTE4,
 ATTRIBUTE5,
 NOMBRE_DEL_CLIENTE,
 PARTY_NAME,
 EJECUTIVO,
 UNIDAD_DE_NEGOCIO_M,
 CONTRATO_FLAG,
 ARTICULO_ORACLE,
 PRECIO,
 CONCEPTO,
 PRODUCTO,
 DIAS_DE_CREDITO,
 CNT_REG_NEG,
 PRECIO_CD,
 PRECIO_PROTON_CD
from XXQP_PDFT_REPORTES_V
where (upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'A') and nvl(upper(substr(cur_cliente_hasta,0,1)),'Z')
 OR upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'1') and nvl(upper(substr(cur_cliente_hasta,0,1)),'9'))
 and trunc(creation_date) between nvl(cur_fecha_desde,to_date ('01/01/0001','DD/MM/YYYY')) and nvl(cur_fecha_hasta,to_date ('31/12/4712','DD/MM/YYYY'))
 and NUMERO_FT between nvl(cur_ficha_tecnica_desde,0) and nvl(cur_ficha_tecnica_hasta,9999999999999999999999999999)
 and upper(substr(FRECUENCIA_FACTURACION_C,0,1)) between nvl(upper(substr(cur_fre_fact_desde,0,1)),'A') and nvl(upper(substr(cur_fre_fact_hasta,0,1)),'Z')
 and upper(substr(STATUS,0,1)) between nvl(upper(substr(cur_status_desde,0,1)),'A') and nvl(upper(substr(cur_status_hasta,0,1)),'Z')
 and (upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'A') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'Z')
 OR upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'1') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'9')
 )
order by PRODUCTO asc;
 
 CURSOR get_info_reportes_v3 (cur_cliente_desde varchar2
 ,cur_cliente_hasta varchar2
 ,cur_fecha_desde date 
 ,cur_fecha_hasta date 
 ,cur_ficha_tecnica_desde number
 ,cur_ficha_tecnica_hasta number 
 ,cur_fre_fact_desde varchar2
 ,cur_fre_fact_hasta varchar2
 ,cur_status_desde varchar2
 ,cur_status_hasta varchar2
 ,cur_raz_soc_desde varchar2
 ,cur_raz_soc_hasta varchar2
 ) return rep_info_typ is 
 SELECT ID,
 NUMERO_FT,
 REV,
 STATUS,
 STATUS_M,
 NUMERO_FT_REFERENCIA,
 PARTY_ID,
 PDFT_CLIENTE_HEADER_ID,
 EMPRESA_QUE_FACTURA_C,
 EMPRESA_QUE_FACTURA_M,
 EMPRESA_QUE_FACTURA_M_BKP,
 UNIDAD_DE_NEGOCIO_C,
 FRECUENCIA_FACTURACION_C,
 FRECUENCIA_FACTURACION_M,
 FECHA_INICIAL_OPERACION,
 CREATED_BY,
 CREATION_DATE,
 LAST_UPDATED_BY,
 LAST_UPDATE_DATE,
 LAST_UPDATE_LOGIN,
 ATTRIBUTE_CATEGORY,
 ATTRIBUTE1,
 ATTRIBUTE2,
 ATTRIBUTE3,
 ATTRIBUTE4,
 ATTRIBUTE5,
 NOMBRE_DEL_CLIENTE,
 PARTY_NAME,
 EJECUTIVO,
 UNIDAD_DE_NEGOCIO_M,
 CONTRATO_FLAG,
 ARTICULO_ORACLE,
 PRECIO,
 CONCEPTO,
 PRODUCTO,
 DIAS_DE_CREDITO,
 CNT_REG_NEG,
 PRECIO_CD,
 PRECIO_PROTON_CD
from XXQP_PDFT_REPORTES_V
where (upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'A') and nvl(upper(substr(cur_cliente_hasta,0,1)),'Z')
 OR upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'1') and nvl(upper(substr(cur_cliente_hasta,0,1)),'9'))
 and trunc(creation_date) between nvl(cur_fecha_desde,to_date ('01/01/0001','DD/MM/YYYY')) and nvl(cur_fecha_hasta,to_date ('31/12/4712','DD/MM/YYYY'))
 and NUMERO_FT between nvl(cur_ficha_tecnica_desde,0) and nvl(cur_ficha_tecnica_hasta,9999999999999999999999999999)
 and upper(substr(FRECUENCIA_FACTURACION_C,0,1)) between nvl(upper(substr(cur_fre_fact_desde,0,1)),'A') and nvl(upper(substr(cur_fre_fact_hasta,0,1)),'Z')
 and upper(substr(STATUS,0,1)) between nvl(upper(substr(cur_status_desde,0,1)),'A') and nvl(upper(substr(cur_status_hasta,0,1)),'Z')
 and (upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'A') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'Z')
 OR upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'1') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'9')
 )
order by NUMERO_FT asc;

CURSOR get_info_reportes_v4 (cur_cliente_desde varchar2
 ,cur_cliente_hasta varchar2
 ,cur_fecha_desde date 
 ,cur_fecha_hasta date 
 ,cur_ficha_tecnica_desde number
 ,cur_ficha_tecnica_hasta number 
 ,cur_fre_fact_desde varchar2
 ,cur_fre_fact_hasta varchar2
 ,cur_status_desde varchar2
 ,cur_status_hasta varchar2
 ,cur_raz_soc_desde varchar2
 ,cur_raz_soc_hasta varchar2
 ) return rep_info_typ is 
 SELECT ID,
 NUMERO_FT,
 REV,
 STATUS,
 STATUS_M,
 NUMERO_FT_REFERENCIA,
 PARTY_ID,
 PDFT_CLIENTE_HEADER_ID,
 EMPRESA_QUE_FACTURA_C,
 EMPRESA_QUE_FACTURA_M,
 EMPRESA_QUE_FACTURA_M_BKP,
 UNIDAD_DE_NEGOCIO_C,
 FRECUENCIA_FACTURACION_C,
 FRECUENCIA_FACTURACION_M,
 FECHA_INICIAL_OPERACION,
 CREATED_BY,
 CREATION_DATE,
 LAST_UPDATED_BY,
 LAST_UPDATE_DATE,
 LAST_UPDATE_LOGIN,
 ATTRIBUTE_CATEGORY,
 ATTRIBUTE1,
 ATTRIBUTE2,
 ATTRIBUTE3,
 ATTRIBUTE4,
 ATTRIBUTE5,
 NOMBRE_DEL_CLIENTE,
 PARTY_NAME,
 EJECUTIVO,
 UNIDAD_DE_NEGOCIO_M,
 CONTRATO_FLAG,
 ARTICULO_ORACLE,
 PRECIO,
 CONCEPTO,
 PRODUCTO,
 DIAS_DE_CREDITO,
 CNT_REG_NEG,
 PRECIO_CD,
 PRECIO_PROTON_CD
from XXQP_PDFT_REPORTES_V
where (upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'A') and nvl(upper(substr(cur_cliente_hasta,0,1)),'Z')
 OR upper(substr(NOMBRE_DEL_CLIENTE,0,1)) between nvl(upper(substr(cur_cliente_desde,0,1)),'1') and nvl(upper(substr(cur_cliente_hasta,0,1)),'9'))
 and trunc(creation_date) between nvl(cur_fecha_desde,to_date ('01/01/0001','DD/MM/YYYY')) and nvl(cur_fecha_hasta,to_date ('31/12/4712','DD/MM/YYYY'))
 and NUMERO_FT between nvl(cur_ficha_tecnica_desde,0) and nvl(cur_ficha_tecnica_hasta,9999999999999999999999999999)
 and upper(substr(FRECUENCIA_FACTURACION_C,0,1)) between nvl(upper(substr(cur_fre_fact_desde,0,1)),'A') and nvl(upper(substr(cur_fre_fact_hasta,0,1)),'Z')
 and upper(substr(STATUS,0,1)) between nvl(upper(substr(cur_status_desde,0,1)),'A') and nvl(upper(substr(cur_status_hasta,0,1)),'Z')
 and ( 
 upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'A') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'Z')
 OR upper(substr(PARTY_NAME,0,1)) between nvl(upper(substr(cur_raz_soc_desde,0,1)),'1') and nvl(upper(substr(cur_raz_soc_hasta,0,1)),'9')
 )
order by FRECUENCIA_FACTURACION_M asc;

 
/** https://dev.w3.org/html5/html-author/charref **/ 
/**Funcion de reemplazo**/
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
 v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'#241;'); /* v_cadena := REPLACE(v_cadena, CHR(50097), CHR(38)||'ntilde'); */
 v_cadena := REPLACE(v_cadena, CHR(49844), '');
 v_cadena := REPLACE(v_cadena, CHR(50090), '');
 v_cadena := REPLACE(v_cadena, CHR(50056), 'E');
 RETURN v_cadena;
 END replace_char_esp;
 
procedure main (pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pni_numero_ft in number) is 
 

 myp_head_info_rec get_myp_head_info%ROWTYPE;
 myp_gral_info_rec get_myp_gral_info%ROWTYPE;
 myp_cob_info_rec get_myp_cob_info%ROWTYPE;
 myp_dist_info_rec get_myp_dist_info%ROWTYPE;
 myp_proc_info_rec get_myp_proc_info%ROWTYPE; 
 myp_oproc_info_rec get_myp_oproc_info%ROWTYPE; 
 myp_instr_info_rec get_myp_instr_info%ROWTYPE; 
 
 ln_proc_count number:=0; 
 ln_proc_precio_sub number:=0; 
 ln_oproc_count number:=0; 
 ln_oproc_precio_sub number:=0; 
 
 ln_myp_header_id number := 152;
 lc_info clob := ''; 
 ls_errmsg varchar2(2000); 
 ls_errcod varchar2(2000); 
 v_new_xml_data varchar2(32767); 
 v_iterations number;
 v_clob_len number; 
 v_chunk_length number := 32767;
 v_length_clob number := 0; 
 v_offset number :=1;
 v_char char(10); /** PL/SQL: error : character string buffer too small num?rico o de valor, -6502 **/
 ln_instr_clob number := 0;
 ls_substr_clob varchar2(20000) :=0;
begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 
 select id
 into ln_myp_header_id
 from XXQP_PDFT_MYP_HEADER
 WHERE NUMERO_FT = pni_numero_ft; 
 
 get_info(pso_errmsg => ls_errmsg
 ,pso_errcod => ls_errcod
 ,pco_info => lc_info
 ,pni_myp_header_id => ln_myp_header_id
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
 
 
 /*
 fnd_file.put_line(fnd_file.output,lc_info); 
 */ 
 
exception when others then 
 pso_errmsg := 'Excepcion Paquete APPS.xxqp_pdft_myp_pkg metodo main:'||sqlerrm||', '||sqlcode;
 pso_errcod := 2; 
end main; 

procedure get_info(pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pco_info out clob
 ,pni_myp_header_id in number
 ,psi_modif in varchar2 default 'N'
 ) is 
 lc_info clob := ''; 
 myp_head_info_rec get_myp_head_info%ROWTYPE;
 myp_gral_info_rec get_myp_gral_info%ROWTYPE;
 myp_cob_info_rec get_myp_cob_info%ROWTYPE;
 myp_dist_info_rec get_myp_dist_info%ROWTYPE;
 myp_proc_info_rec get_myp_proc_info%ROWTYPE; 
 myp_oproc_info_rec get_myp_oproc_info%ROWTYPE; 
 myp_instr_info_rec get_myp_instr_info%ROWTYPE; 
 RNInfo_rec getRNInfo%ROWTYPE;
 
 ln_proc_count number:=0; 
 ln_proc_precio_sub number:=0; 
 ln_proc_precio_proton_sub number := 0; 
 ln_oproc_count number:=0; 
 ln_oproc_precio_sub number:=0; 
 ln_oproc_precio_proton_sub number := 0; 
 ln_myp_header_id number := pni_myp_header_id; 
 ls_regneg_precio varchar2(200); 
 v_length_clob number := 0; 
 v_offset number :=1;
 v_char VARCHAR2(10); /** 1 to 10 por acentos **/
 lc_clob_tmp clob :='';
 ln_nth number := 0; 
 v_offsetTmp number := 0;
 ln_instr_clob number := 0; 
 ln_instr_clob_tmp number := 0;
 ls_substr_clob varchar2(32767) := 0; 
begin 
 pso_errmsg := null; 
 pso_errcod := '0';
 
 fnd_file.put_line(fnd_file.log,'050320211619');
 DBMS_LOB.CREATETEMPORARY(lob_loc  => lc_info    
                                                     ,cache   => true     
                                                     ,dur       => dbms_lob.call
                                                     );
                                                     
  DBMS_LOB.OPEN(lob_loc    => lc_info
                            ,open_mode  => DBMS_LOB.LOB_READWRITE
                            );
 dbms_lob.append(lc_info,'<XXQP_PDFT_MYP>'); 
 
 fnd_file.put_line(fnd_file.log,'050320211617');
 OPEN get_myp_head_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_head_info INTO myp_head_info_rec;
 EXIT WHEN get_myp_head_info%NOTFOUND;
 
 dbms_lob.append(lc_info,'<NOMBRE_CLIENTE>'||replace_char_esp(myp_head_info_rec.razon_social)||'</NOMBRE_CLIENTE>');
 dbms_lob.append(lc_info,'<FECHA_INICIAL_OPERACION>'||myp_head_info_rec.fecha_inicial_operacion||'</FECHA_INICIAL_OPERACION>');
 dbms_lob.append(lc_info,'<EMPRESA_QUE_FACTURA>'||replace_char_esp(myp_head_info_rec.empresa_que_factura_m)||'</EMPRESA_QUE_FACTURA>');
 dbms_lob.append(lc_info,'<UNIDAD_DE_NEGOCIO>'||myp_head_info_rec.unidad_de_negocio_m||'</UNIDAD_DE_NEGOCIO>');
 dbms_lob.append(lc_info,'<HEAD_PERIODICIDAD>'||myp_head_info_rec.FRECUENCIA_FACTURACION_M||'</HEAD_PERIODICIDAD>');
 dbms_lob.append(lc_info,'<EJECUTIVO>'||myp_head_info_rec.EJECUTIVO||'</EJECUTIVO>');
 dbms_lob.append(lc_info,'<STATUS_MEANING>'||myp_head_info_rec.STATUS_MEANING||'</STATUS_MEANING>');
 dbms_lob.append(lc_info,'<NUMERO_FT>'||myp_head_info_rec.NUMERO_FT||'</NUMERO_FT>');
 dbms_lob.append(lc_info,'<CREATION_DATE>'||myp_head_info_rec.CREATION_DATE||'</CREATION_DATE>');
 dbms_lob.append(lc_info,'<ARTICULO_ORACLE>'||myp_head_info_rec.ARTICULO_ORACLE||'</ARTICULO_ORACLE>');
 
 if 'Y' = psi_modif or myp_head_info_rec.status in ('CAMBIO_DE_PRECIO') then 
 dbms_lob.append(lc_info,'<CAMBIO>Y</CAMBIO>');
 
 APPS.xxqp_pdft_myp_pkg.split_ilim(PCI_CLOB_REP => lc_info
 ,PCI_CLOB_ALIM => myp_head_info_rec.MODIF_REALIZ
 ,PSI_NODE => 'MODIF_REALIZ_R'
 ,PSI_NODE_CHILD => 'MODIF_REALIZ'
 );
 
 else
 dbms_lob.append(lc_info,'<ALTA>Y</ALTA>');
 end if; 
 
 
 END LOOP;
 CLOSE get_myp_head_info;
 
 fnd_file.put_line(fnd_file.log,'050320211618');
 
 
 OPEN get_myp_gral_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_gral_info INTO myp_gral_info_rec;
 EXIT WHEN get_myp_gral_info%NOTFOUND;
 dbms_lob.append(lc_info,'<TIPO_PRODUCTO>'||replace_char_esp(myp_gral_info_rec.tipo_producto_m)||'</TIPO_PRODUCTO>');
 dbms_lob.append(lc_info,'<IMPRESOR>'||myp_gral_info_rec.impresor||'</IMPRESOR>');
 dbms_lob.append(lc_info,'<PUNTO_DE_RECOLECCION>'||myp_gral_info_rec.punto_de_recoleccion||'</PUNTO_DE_RECOLECCION>');
 dbms_lob.append(lc_info,'<CONTACTO_PARA_CIERRE>'||myp_gral_info_rec.contacto_para_cierre||'</CONTACTO_PARA_CIERRE>');
 dbms_lob.append(lc_info,'<NOMBRE_PRODUCTO>'||myp_gral_info_rec.nombre_producto||'</NOMBRE_PRODUCTO>');
 dbms_lob.append(lc_info,'<VOLUMEN_APORX>'||myp_gral_info_rec.volumen_aprox||'</VOLUMEN_APORX>');
 dbms_lob.append(lc_info,'<PERIODICIDAD>'||myp_gral_info_rec.periodicidad_m||'</PERIODICIDAD>'); 
 dbms_lob.append(lc_info,'<SE_FACTURA>'||myp_gral_info_rec.se_factura_m||'</SE_FACTURA>');
 dbms_lob.append(lc_info,'<FORMATO_PARA_CIERRE>'||replace_char_esp(myp_gral_info_rec.formato_para_cierre_m)||'</FORMATO_PARA_CIERRE>');
 dbms_lob.append(lc_info,'<DIAS_HABILES_PAGO>'||myp_gral_info_rec.dias_habiles_pago||'</DIAS_HABILES_PAGO>');
 dbms_lob.append(lc_info,'<DIAS_RECEPCION_FATURACION>'||myp_gral_info_rec.dias_recepcion||'</DIAS_RECEPCION_FATURACION>');
 dbms_lob.append(lc_info,'<REQUIERE_VOBO>'||myp_gral_info_rec.REQUIERE_VOBO_m||'</REQUIERE_VOBO>');
 dbms_lob.append(lc_info,'<TIPO_VOBO>'||myp_gral_info_rec.TIPO_VOBO||'</TIPO_VOBO>');
 dbms_lob.append(lc_info,'<TIPO_DE_ENTREGA>'||myp_gral_info_rec.TIPO_DE_ENTREGA_c||'</TIPO_DE_ENTREGA>');
 dbms_lob.append(lc_info,'<POLITICA_DE_ENTREGA>'||myp_gral_info_rec.POLITICA_DE_ENTREGA_m||'</POLITICA_DE_ENTREGA>');
 dbms_lob.append(lc_info,'<TIPO_COMISION>'||myp_gral_info_rec.TIPO_COMISION_m||'</TIPO_COMISION>');
 dbms_lob.append(lc_info,'<PESO_PRODUCTO>'||myp_gral_info_rec.PESO_PRODUCTO_m||'</PESO_PRODUCTO>');
 dbms_lob.append(lc_info,'<DIMENSIONES>'||myp_gral_info_rec.DIMENSIONES_m||'</DIMENSIONES>');
 dbms_lob.append(lc_info,'<ACUSE>'||nvl(myp_gral_info_rec.ACUSE_M,'N/A')||'</ACUSE>');
 dbms_lob.append(lc_info,'<ORDINARIO>'||nvl(myp_gral_info_rec.ORDINARIO_M,'N/A')||'</ORDINARIO>');
 dbms_lob.append(lc_info,'<SEMI_ACUSE>'||nvl(myp_gral_info_rec.SEMI_ACUSE_M,'N/A')||'</SEMI_ACUSE>');
 
 END LOOP;
 CLOSE get_myp_gral_info;
 
 
 OPEN get_myp_cob_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_cob_info INTO myp_cob_info_rec;
 EXIT WHEN get_myp_cob_info%NOTFOUND;
dbms_lob.append(lc_info,'<PLAZA_PROPIETARIA>'||myp_cob_info_rec.PLAZA_PROPIETARIA_M||'</PLAZA_PROPIETARIA>');
 /** lc_info := lc_info||'<TIPO_COBERTURA>'||myp_cob_info_rec.TIPO_COBERTURA_C||'</TIPO_COBERTURA>'; **/
 dbms_lob.append(lc_info,'<TC_NACIONAL>'||myp_cob_info_rec.TC_NACIONAL||'</TC_NACIONAL>'); 
 dbms_lob.append(lc_info,'<TC_REGIONAL>'||myp_cob_info_rec.TC_REGIONAL||'</TC_REGIONAL>'); 
 dbms_lob.append(lc_info,'<TC_LOCAL>'||myp_cob_info_rec.TC_LOCAL||'</TC_LOCAL>'); 
-- lc_info := lc_info||'<MENCIONAR_ESTADOS>'||'<![CDATA' || '['|| 'Hola<br/>Hola' || ']' || ']>'||'</MENCIONAR_ESTADOS>';
 /** dbms_lob.append(lc_info,'<MENCIONAR_ESTADOS>'||myp_cob_info_rec.MENCIONAR_ESTADOS||'</MENCIONAR_ESTADOS>'); **/
 xxqp_pdft_myp_pkg.split_varchar2(PCI_CLOB_REP => lc_info
 ,PSI_VARCHAR_ALIM => myp_cob_info_rec.MENCIONAR_ESTADOS
 ,PSI_NODE => 'MEN_EST_R'
 ,PSI_NODE_CHILD => 'MEN_EST'
 );
 dbms_lob.append(lc_info,'<ENTREGA_LOCAL>'||trim(to_char(myp_cob_info_rec.ENTREGA_LOCAL,gs_currency_format))||'</ENTREGA_LOCAL>');
 dbms_lob.append(lc_info,'<ENTREGA_FORANEO>'||trim(to_char(myp_cob_info_rec.ENTREGA_FORANEO,gs_currency_format))||'</ENTREGA_FORANEO>');
 dbms_lob.append(lc_info,'<DR_LOCAL>'||trim(to_char(myp_cob_info_rec.DR_LOCAL,gs_currency_format))||'</DR_LOCAL>');
 dbms_lob.append(lc_info,'<DR_FORANEO>'||trim(to_char(myp_cob_info_rec.DR_FORANEO,gs_currency_format))||'</DR_FORANEO>');
 dbms_lob.append(lc_info,'<DI_LOCAL>'||trim(to_char(myp_cob_info_rec.DI_LOCAL,gs_currency_format))||'</DI_LOCAL>');
 dbms_lob.append(lc_info,'<DI_FORANEO>'||trim(to_char(myp_cob_info_rec.DI_FORANEO,gs_currency_format))||'</DI_FORANEO>'); 
 fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info));
 
 xxqp_pdft_myp_pkg.split_ilim(PCI_CLOB_REP => lc_info
 ,PCI_CLOB_ALIM => myp_cob_info_rec.comentarios_ilim
 ,PSI_NODE => 'COB_COM_R'
 ,PSI_NODE_CHILD => 'COB_COM'
 );
 fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info));
--/** dbms_lob.append(lc_info,myp_cob_info_rec.comentarios_ilim); **/
--v_length_clob := dbms_lob.getlength(myp_cob_info_rec.comentarios_ilim);
-- fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(v_length_clob):'||v_length_clob);
--v_offset := 1;
--v_offsetTmp := 1;
-- DBMS_LOB.CREATETEMPORARY( lob_loc => lc_clob_tmp , cache => true , dur => dbms_lob.call);
-- DBMS_LOB.OPEN( lob_loc => lc_clob_tmp , open_mode => DBMS_LOB.LOB_READWRITE);
-- while(v_offset<=v_length_clob) LOOP
-- v_char := dbms_lob.substr(myp_cob_info_rec.comentarios_ilim, 1, v_offset);
-- fnd_file.put_line(fnd_file.log,'v_char:'||v_char);
-- dbms_lob.append(lc_clob_tmp,replace_char_esp(v_char));
-- v_offset := v_offset + 1;
-- if v_char = chr(10) then 
-- ln_nth := ln_nth+1;
-- fnd_file.put_line(fnd_file.log,'v_offsetTmp:'||v_offsetTmp||', ln_nth:'||ln_nth||', ln_instr_clob_tmp:'||ln_instr_clob_tmp);
-- ln_instr_clob := dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth);
-- fnd_file.put_line(fnd_file.log,'dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth):'||dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth));
-- ls_substr_clob := dbms_lob.substr(lc_clob_tmp,ln_instr_clob-ln_instr_clob_tmp-1,v_offsetTmp);
-- fnd_file.put_line(fnd_file.log,'dbms_lob.substr(lc_clob_tmp,ln_instr_clob,v_offsetTmp):'||ls_substr_clob);
-- dbms_lob.append(lc_info,'<COBERTURA_COMENTARIOS>'); 
-- dbms_lob.append(lc_info,'<COB_COM>'||ls_substr_clob||'</COB_COM>'); 
-- dbms_lob.append(lc_info,'</COBERTURA_COMENTARIOS>');
-- v_offsetTmp := ln_instr_clob+1;
-- ln_instr_clob_tmp := ln_instr_clob;
-- 
-- end if; 
-- 
-- 
-- END LOOP; 
-- 
-- if (v_offset-1) = v_length_clob AND ln_nth>0 then 
-- fnd_file.put_line(fnd_file.log,'v_offsetTmp:'||v_offsetTmp);
-- -- fnd_file.put_line(fnd_file.log,'v_offset-ln_instr_clob_tmp-1:'||v_offset-ln_instr_clob_tmp-1);
-- ls_substr_clob := dbms_lob.substr(lc_clob_tmp,v_offset-ln_instr_clob_tmp,v_offsetTmp);
-- fnd_file.put_line(fnd_file.log,'v_offset = v_length_clob:'||ls_substr_clob);
-- dbms_lob.append(lc_info,'<COBERTURA_COMENTARIOS>'); 
-- dbms_lob.append(lc_info,'<COB_COM>'||ls_substr_clob||'</COB_COM>'); 
-- dbms_lob.append(lc_info,'</COBERTURA_COMENTARIOS>');
-- end if;
 
 
-- for idx in ( SELECT LEVEL AS id, REGEXP_SUBSTR(lc_clob_tmp, '[^'||chr(10)||']+', 1, LEVEL) AS data
-- FROM dual
-- CONNECT BY REGEXP_SUBSTR(lc_clob_tmp, '[^'||chr(10)||']+', 1, LEVEL) IS NOT NULL) loop
-- dbms_lob.append(lc_info,'<COBERTURA_COMENTARIOS>'); 
-- lc_info := lc_info||'<COB_COM>'||idx.data||'</COB_COM>';
-- dbms_lob.append(lc_info,'</COBERTURA_COMENTARIOS>');
-- end loop; 
 
 END LOOP;
 CLOSE get_myp_cob_info;

 fnd_file.put_line(fnd_file.log,'040320211642');
 
 OPEN get_myp_dist_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_dist_info INTO myp_dist_info_rec;
 EXIT WHEN get_myp_dist_info%NOTFOUND;
 dbms_lob.append(lc_info,'<DIGITALIZACION_ACUSES>'||myp_dist_info_rec.DIGITALIZACION_ACUSES||'</DIGITALIZACION_ACUSES>'); 
 dbms_lob.append(lc_info,'<CAPTURA_DEVOLUCIONES>'||myp_dist_info_rec.CAPTURA_DEVOLUCIONES||'</CAPTURA_DEVOLUCIONES>'); 
 dbms_lob.append(lc_info,'<REPORTE_GPS>'||myp_dist_info_rec.REPORTE_GPS||'</REPORTE_GPS>');
 dbms_lob.append(lc_info,'<REPORTE_RECEPCION>'||myp_dist_info_rec.REPORTE_RECEPCION||'</REPORTE_RECEPCION>'); 
 dbms_lob.append(lc_info,'<CAPTURA_ACUSES>'||myp_dist_info_rec.CAPTURA_ACUSES||'</CAPTURA_ACUSES>'); 
 dbms_lob.append(lc_info,'<SEGUIMIENTO_QUEJAS>'||myp_dist_info_rec.SEGUIMIENTO_QUEJAS||'</SEGUIMIENTO_QUEJAS>');
 dbms_lob.append(lc_info,'<PROPORCIONAMOS_INSUMOS>'||myp_dist_info_rec.PROPORCIONAMOS_INSUMOS||'</PROPORCIONAMOS_INSUMOS>');
 dbms_lob.append(lc_info,'<ETIQUETADO>'||myp_dist_info_rec.ETIQUETADO||'</ETIQUETADO>');
 dbms_lob.append(lc_info,'<ENSOBRETADO>'||myp_dist_info_rec.ENSOBRETADO||'</ENSOBRETADO>');
 dbms_lob.append(lc_info,'<GENERACION_ACUSE>'||myp_dist_info_rec.GENERACION_ACUSE||'</GENERACION_ACUSE>');
 dbms_lob.append(lc_info,'<DIAS_OPERACION_LOCAL>'||myp_dist_info_rec.DIAS_OPERACION_LOCAL||'</DIAS_OPERACION_LOCAL>');
 dbms_lob.append(lc_info,'<DIAS_OPERACION_FORANEO>'||myp_dist_info_rec.DIAS_OPERACION_FORANEO||'</DIAS_OPERACION_FORANEO>');
 dbms_lob.append(lc_info,'<CIERRE_ELECTRONICO>'||myp_dist_info_rec.CIERRE_ELECTRONICO||'</CIERRE_ELECTRONICO>');
 dbms_lob.append(lc_info,'<ENVIO_PIEZAS_FISICAS>'||myp_dist_info_rec.ENVIO_PIEZAS_FISICAS||'</ENVIO_PIEZAS_FISICAS>'); 
 
 /*dbms_lob.append(lc_info,'<COMENTARIOS_DISTRIBUCION>'||replace_char_esp(myp_dist_info_rec.COMENTARIOS_DISTRIBUCION)||'</COMENTARIOS_DISTRIBUCION>');*/
 xxqp_pdft_myp_pkg.split_ilim(PCI_CLOB_REP => lc_info
 ,PCI_CLOB_ALIM => myp_dist_info_rec.COMENTARIOS_DISTRIBUCION_ILIM
 ,PSI_NODE => 'COM_DIST_R'
 ,PSI_NODE_CHILD => 'COM_DIST'
 );
 END LOOP;
 CLOSE get_myp_dist_info;

 fnd_file.put_line(fnd_file.log,'040320211643');
 
 OPEN get_myp_proc_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_proc_info INTO myp_proc_info_rec;
 EXIT WHEN get_myp_proc_info%NOTFOUND;
 ln_proc_count :=get_myp_proc_info%ROWCOUNT;
 dbms_lob.append(lc_info,'<NOMBRE_PROCESO'||ln_proc_count||'>'||myp_proc_info_rec.PROSESO||'</NOMBRE_PROCESO'||ln_proc_count||'>');
 dbms_lob.append(lc_info,'<SELECCIONAR'||ln_proc_count||'>'||myp_proc_info_rec.SELECCIONAR||'</SELECCIONAR'||ln_proc_count||'>');
 dbms_lob.append(lc_info,'<PRECIO'||ln_proc_count||'>'||trim(to_char(myp_proc_info_rec.PRECIO,gs_currency_format))||'</PRECIO'||ln_proc_count||'>');
 dbms_lob.append(lc_info,'<PRECIO_PROTON'||ln_proc_count||'>'||trim(to_char(myp_proc_info_rec.PRECIO_PROTON,gs_currency_format))||'</PRECIO_PROTON'||ln_proc_count||'>');
 
 ln_proc_precio_sub := nvl(ln_proc_precio_sub,0) + nvl(myp_proc_info_rec.PRECIO,0);
  ln_proc_precio_proton_sub := nvl(ln_proc_precio_proton_sub,0) + nvl(myp_proc_info_rec.PRECIO_PROTON,0);
 END LOOP;
 dbms_lob.append(lc_info,'<SUB_PRECIO>'||trim(to_char(ln_proc_precio_sub,gs_currency_format))||'</SUB_PRECIO>');
  dbms_lob.append(lc_info,'<SUB_PRECIO_PROTON>'||trim(to_char(ln_proc_precio_proton_sub,gs_currency_format))||'</SUB_PRECIO_PROTON>');
 CLOSE get_myp_proc_info;
 
 
 OPEN get_myp_oproc_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_oproc_info INTO myp_oproc_info_rec;
 EXIT WHEN get_myp_oproc_info%NOTFOUND;
 ln_oproc_count :=get_myp_oproc_info%ROWCOUNT;
 dbms_lob.append(lc_info,'<O_NOMBRE_PROCESO'||ln_oproc_count||'>'||myp_oproc_info_rec.OTROS_PROCESOS||'</O_NOMBRE_PROCESO'||ln_oproc_count||'>');
 dbms_lob.append(lc_info,'<O_SELECCIONAR'||ln_oproc_count||'>'||myp_oproc_info_rec.SELECCIONAR||'</O_SELECCIONAR'||ln_oproc_count||'>');
 dbms_lob.append(lc_info,'<O_PRECIO'||ln_oproc_count||'>'||trim(to_char(myp_oproc_info_rec.PRECIO,gs_currency_format))||'</O_PRECIO'||ln_oproc_count||'>');
dbms_lob.append(lc_info,'<O_PRECIO_PROTON'||ln_oproc_count||'>'||trim(to_char(myp_oproc_info_rec.PRECIO_PROTON,gs_currency_format))||'</O_PRECIO_PROTON'||ln_oproc_count||'>');

 ln_oproc_precio_sub := nvl(ln_oproc_precio_sub,0) + nvl(myp_oproc_info_rec.PRECIO,0);
 ln_oproc_precio_proton_sub := nvl(ln_oproc_precio_proton_sub,0) + nvl(myp_oproc_info_rec.PRECIO_PROTON,0);
 
 END LOOP;
 dbms_lob.append(lc_info,'<O_SUB_PRECIO>'||trim(to_char(ln_oproc_precio_sub,gs_currency_format))||'</O_SUB_PRECIO>');
 dbms_lob.append(lc_info,'<O_SUB_PRECIO_PROTON>'||trim(to_char(ln_oproc_precio_proton_sub,gs_currency_format))||'</O_SUB_PRECIO_PROTON>');
 
 CLOSE get_myp_oproc_info;
 
 fnd_file.put_line(fnd_file.log,'040320211628');
 
 OPEN get_myp_instr_info(ln_myp_header_id);
 LOOP
 FETCH get_myp_instr_info INTO myp_instr_info_rec;
 EXIT WHEN get_myp_instr_info%NOTFOUND;
 
 fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info));
 APPS.xxqp_pdft_myp_pkg.split_ilim(PCI_CLOB_REP => lc_info
 ,PCI_CLOB_ALIM => myp_instr_info_rec.COMENTARIOS_INSTRUCC_ILIM
 ,PSI_NODE => 'COM_INST_R'
 ,PSI_NODE_CHILD => 'COM_INST'
 );
 fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(lc_info):'||dbms_lob.getlength(lc_info)); 
 
 END LOOP;
 CLOSE get_myp_instr_info;
 
 begin 
 OPEN getRNInfo(ln_myp_header_id);
 LOOP
 FETCH getRNInfo INTO RNInfo_rec;
 EXIT WHEN getRNInfo%NOTFOUND;
 dbms_lob.append(lc_info,'<G_REGNEG>'); 
 dbms_lob.append(lc_info,'<RN_CONCEPTO1>'||RNInfo_rec.RN_CONCEPTO1||'</RN_CONCEPTO1>'); 
 dbms_lob.append(lc_info,'<RN_CONCEPTO2>'||RNInfo_rec.RN_CONCEPTO2||'</RN_CONCEPTO2>'); 
 ls_regneg_precio :=trim(to_char(RNInfo_rec.PRECIO,gs_currency_format));
 dbms_lob.append(lc_info,'<RN_PRECIO>'||ls_regneg_precio||'</RN_PRECIO>'); 
 dbms_lob.append(lc_info,'</G_REGNEG>'||CHR(10)); 
 END LOOP;
 CLOSE getRNInfo;
 exception when others then 
 pso_errmsg := 'Excepcion Paquete APPS.xxqp_pdft_myp_pkg metodo getRNInfo:'||sqlerrm||', '||sqlcode||',RNInfo_rec.PRECIO:'||RNInfo_rec.PRECIO;
 pso_errcod := 2; 
 fnd_file.put_line(fnd_file.log,pso_errmsg);
 return;
            
           end; 
 
dbms_lob.append(lc_info,'</XXQP_PDFT_MYP>'); 
  DBMS_LOB.CLOSE(lob_loc    => lc_info);
 pco_info := lc_info;
 exception when others then 
 fnd_file.put_line(fnd_file.log,'Excepcion Paquete APPS.xxqp_pdft_myp_pkg metodo get_info:'||sqlerrm||', '||sqlcode);
 pso_errmsg := 'Excepcion Paquete APPS.xxqp_pdft_myp_pkg metodo get_info:'||sqlerrm||', '||sqlcode;
 pso_errcod := 2; 
end get_info; 



procedure obtiene_reportes ( pso_errmsg out varchar2 
 ,pso_errcod out varchar2
 ,psi_tipo_reporte in varchar2
 ,psi_cliente_desde in varchar2 
 ,psi_cliente_hasta in varchar2
 ,PDI_FECHA_DESDE in date 
 ,PDI_FECHA_HASTA in date 
 ,PNI_FICHA_TECNICA_DESDE in number 
 ,PNI_FICHA_TECNICA_HASTA in number
 ,PSI_PRODUCTO_DESDE in varchar2
 ,PSI_PRODUCTO_HASTA in varchar2
 ,PSI_FRE_FACT_DESDE IN VARCHAR2
 ,PSI_FRE_FACT_HASTA IN VARCHAR2
 ,PSI_STATUS_DESDE IN VARCHAR2
 ,PSI_STATUS_HASTA IN VARCHAR2
 ,PSI_RAZ_SOC_DESDE IN VARCHAR2 
 ,PSI_RAZ_SOC_HASTA IN VARCHAR2
 ,pco_info out clob 
 ) is 
 

 
  info_reportes_rec        rep_info_typ;
   lc_clob_tmp clob ;     
  
  /**************************************************************
  (1) Tipo Reporte Por Cliente 
  (4) Tipo Reporte Por Frecuencia de Facturacion 
  **************************************************************/                                         
     
begin 

   DBMS_LOB.CREATETEMPORARY(lob_loc  => lc_clob_tmp    
                                                     ,cache   => true     
                                                     ,dur       => dbms_lob.call
                                                     );
                                                     
  DBMS_LOB.OPEN(lob_loc    => lc_clob_tmp
                            ,open_mode  => DBMS_LOB.LOB_READWRITE
                            );
                    
  dbms_lob.append(lc_clob_tmp,'<REPORTES_PDFT>'); 
  dbms_lob.append(lc_clob_tmp,'<TIPO_REPORTE>'||psi_tipo_reporte||'</TIPO_REPORTE>'); 
   
   dbms_output.put_line('Comienza Procedimiento OBTIENE_REPORTES'); 
  
  if 'POR_CLIENTE' = psi_tipo_reporte then 
    open get_info_reportes_v1 (psi_cliente_desde
                                    ,psi_cliente_hasta
                                    ,pdi_fecha_desde
                                    ,pdi_fecha_hasta
                                    ,pni_ficha_tecnica_desde
                                    ,pni_ficha_tecnica_hasta
                                    ,psi_fre_fact_desde
                                    ,psi_fre_fact_hasta
                                    ,psi_status_desde
                                    ,psi_status_hasta
                                    ,psi_raz_soc_desde
                                    ,psi_raz_soc_hasta
                                    ); 
      LOOP
      FETCH get_info_reportes_v1 INTO info_reportes_rec;
      EXIT WHEN get_info_reportes_v1%NOTFOUND;
      dbms_output.put_line('Entra Loop Numero Ft:'||info_reportes_rec.NUMERO_FT);
      
        dbms_lob.append(lc_clob_tmp,'<PDFT_RECORD>'); 
        
       dbms_lob.append(lc_clob_tmp,'<CREATION_DATE>'||to_char(info_reportes_rec.creation_date,'YYYY/MM/DD')||'</CREATION_DATE>');  /** (1)  (4)**/
         dbms_output.put_line('*');
        dbms_lob.append(lc_clob_tmp,'<NUMERO_FT>'||to_char(info_reportes_rec.NUMERO_FT)||'</NUMERO_FT>');  /** (1)  (4)**/
         dbms_output.put_line('**');
        dbms_lob.append(lc_clob_tmp,'<REV>'||to_char(info_reportes_rec.REV)||'</REV>');  /** (1)  (4) **/
         dbms_output.put_line('***');
        dbms_lob.append(lc_clob_tmp,'<ARTICULO_ORACLE>'||replace_char_esp(info_reportes_rec.articulo_oracle)||'</ARTICULO_ORACLE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<NOMBRE_DEL_CLIENTE>'||replace_char_esp(info_reportes_rec.NOMBRE_DEL_CLIENTE)||'</NOMBRE_DEL_CLIENTE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<PRECIO>'||to_char(info_reportes_rec.precio)||'</PRECIO>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
        
        if info_reportes_rec.CNT_REG_NEG > 0 then 
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>SI</REGLA_NEG>'); 
        else
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>NO</REGLA_NEG>');  
        end if; 
        
        dbms_lob.append(lc_clob_tmp,'<CONCEPTO>'||to_char(info_reportes_rec.concepto)||'</CONCEPTO>');
        dbms_lob.append(lc_clob_tmp,'<PRODUCTO>'||to_char(info_reportes_rec.producto)||'</PRODUCTO>');
         dbms_lob.append(lc_clob_tmp,'<DIAS_DE_CREDITO>'||to_char(info_reportes_rec.dias_de_credito)||'</DIAS_DE_CREDITO>');
         dbms_output.put_line('****');
        dbms_lob.append(lc_clob_tmp,'<EMPRESA_QUE_FACTURA_M>'||replace_char_esp(info_reportes_rec.EMPRESA_QUE_FACTURA_M)||'</EMPRESA_QUE_FACTURA_M>');  /** (1) **/
        dbms_output.put_line('*****');
        dbms_lob.append(lc_clob_tmp,'<FRECUENCIA_FACTURACION_M>'||replace_char_esp(info_reportes_rec.FRECUENCIA_FACTURACION_M)||'</FRECUENCIA_FACTURACION_M>');  /** (1) (4) **/
          dbms_output.put_line('******');
        dbms_lob.append(lc_clob_tmp,'<UNIDAD_DE_NEGOCIO_M>'||replace_char_esp(info_reportes_rec.UNIDAD_DE_NEGOCIO_M)||'</UNIDAD_DE_NEGOCIO_M>');  /** (1)  (4) **/
         dbms_output.put_line('*******');
        dbms_lob.append(lc_clob_tmp,'<EJECUTIVO>'||replace_char_esp(info_reportes_rec.EJECUTIVO)||'</EJECUTIVO>');  /** (1) **/
         dbms_output.put_line('********');
        dbms_lob.append(lc_clob_tmp,'<STATUS_M>'||info_reportes_rec.STATUS_M||'</STATUS_M>');  /** (1) **/
         dbms_output.put_line('*********');
         dbms_lob.append(lc_clob_tmp,'<FECHA_INICIAL_OPERACION>'||to_char(info_reportes_rec.FECHA_INICIAL_OPERACION,'YYYY/MM/DD')||'</FECHA_INICIAL_OPERACION>');  /** (4) **/
           dbms_output.put_line('**********');
            dbms_lob.append(lc_clob_tmp,'<CONTRATO>'||info_reportes_rec.contrato_flag||'</CONTRATO>');  /** (4) **/
          
          dbms_lob.append(lc_clob_tmp,'<PRECIO_CD>'||to_char(info_reportes_rec.precio_cd)||'</PRECIO_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
          dbms_lob.append(lc_clob_tmp,'<PRECIO_PROTON_CD>'||to_char(info_reportes_rec.precio_proton_cd)||'</PRECIO_PROTON_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
     
            
        dbms_lob.append(lc_clob_tmp,'</PDFT_RECORD>'); 
   END LOOP;
    dbms_output.put_line('Total Registros:'||get_info_reportes_v1%ROWCOUNT);
   CLOSE get_info_reportes_v1;
  end if; 
  
  
   if 'POR_PRODUCTO' = psi_tipo_reporte then 
    open get_info_reportes_v2 (psi_cliente_desde
                                    ,psi_cliente_hasta
                                    ,pdi_fecha_desde
                                    ,pdi_fecha_hasta
                                    ,pni_ficha_tecnica_desde
                                    ,pni_ficha_tecnica_hasta
                                    ,psi_fre_fact_desde
                                    ,psi_fre_fact_hasta
                                    ,psi_status_desde
                                    ,psi_status_hasta
                                    ,psi_raz_soc_desde
                                    ,psi_raz_soc_hasta
                                    ); 
      LOOP
      FETCH get_info_reportes_v2 INTO info_reportes_rec;
      EXIT WHEN get_info_reportes_v2%NOTFOUND;
      dbms_output.put_line('Entra Loop Numero Ft:'||info_reportes_rec.NUMERO_FT);
      
        dbms_lob.append(lc_clob_tmp,'<PDFT_RECORD>'); 
        
        dbms_lob.append(lc_clob_tmp,'<CREATION_DATE>'||to_char(info_reportes_rec.creation_date,'YYYY/MM/DD')||'</CREATION_DATE>');  /** (1)  (4)**/
         dbms_output.put_line('*');
        dbms_lob.append(lc_clob_tmp,'<NUMERO_FT>'||to_char(info_reportes_rec.NUMERO_FT)||'</NUMERO_FT>');  /** (1)  (4)**/
         dbms_output.put_line('**');
        dbms_lob.append(lc_clob_tmp,'<REV>'||to_char(info_reportes_rec.REV)||'</REV>');  /** (1)  (4) **/
         dbms_output.put_line('***');
        dbms_lob.append(lc_clob_tmp,'<ARTICULO_ORACLE>'||replace_char_esp(info_reportes_rec.articulo_oracle)||'</ARTICULO_ORACLE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<NOMBRE_DEL_CLIENTE>'||replace_char_esp(info_reportes_rec.NOMBRE_DEL_CLIENTE)||'</NOMBRE_DEL_CLIENTE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<PRECIO>'||to_char(info_reportes_rec.precio)||'</PRECIO>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
       
       if info_reportes_rec.CNT_REG_NEG > 0 then 
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>SI</REGLA_NEG>'); 
        else
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>NO</REGLA_NEG>');  
        end if; 
       
        dbms_lob.append(lc_clob_tmp,'<CONCEPTO>'||to_char(info_reportes_rec.concepto)||'</CONCEPTO>');
        dbms_lob.append(lc_clob_tmp,'<PRODUCTO>'||to_char(info_reportes_rec.producto)||'</PRODUCTO>');
         dbms_lob.append(lc_clob_tmp,'<DIAS_DE_CREDITO>'||to_char(info_reportes_rec.dias_de_credito)||'</DIAS_DE_CREDITO>');
         dbms_output.put_line('****');
        dbms_lob.append(lc_clob_tmp,'<EMPRESA_QUE_FACTURA_M>'||replace_char_esp(info_reportes_rec.EMPRESA_QUE_FACTURA_M)||'</EMPRESA_QUE_FACTURA_M>');  /** (1) **/
        dbms_output.put_line('*****');
        dbms_lob.append(lc_clob_tmp,'<FRECUENCIA_FACTURACION_M>'||replace_char_esp(info_reportes_rec.FRECUENCIA_FACTURACION_M)||'</FRECUENCIA_FACTURACION_M>');  /** (1) (4) **/
          dbms_output.put_line('******');
        dbms_lob.append(lc_clob_tmp,'<UNIDAD_DE_NEGOCIO_M>'||replace_char_esp(info_reportes_rec.UNIDAD_DE_NEGOCIO_M)||'</UNIDAD_DE_NEGOCIO_M>');  /** (1)  (4) **/
         dbms_output.put_line('*******');
        dbms_lob.append(lc_clob_tmp,'<EJECUTIVO>'||replace_char_esp(info_reportes_rec.EJECUTIVO)||'</EJECUTIVO>');  /** (1) **/
         dbms_output.put_line('********');
        dbms_lob.append(lc_clob_tmp,'<STATUS_M>'||info_reportes_rec.STATUS_M||'</STATUS_M>');  /** (1) **/
         dbms_output.put_line('*********');
         dbms_lob.append(lc_clob_tmp,'<FECHA_INICIAL_OPERACION>'||to_char(info_reportes_rec.FECHA_INICIAL_OPERACION,'YYYY/MM/DD')||'</FECHA_INICIAL_OPERACION>');  /** (4) **/
           dbms_output.put_line('**********');
            dbms_lob.append(lc_clob_tmp,'<CONTRATO>'||info_reportes_rec.contrato_flag||'</CONTRATO>');  /** (4) **/
          dbms_lob.append(lc_clob_tmp,'<PRECIO_CD>'||to_char(info_reportes_rec.precio_cd)||'</PRECIO_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
          dbms_lob.append(lc_clob_tmp,'<PRECIO_PROTON_CD>'||to_char(info_reportes_rec.precio_proton_cd)||'</PRECIO_PROTON_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
     
            
        dbms_lob.append(lc_clob_tmp,'</PDFT_RECORD>'); 
   END LOOP;
    dbms_output.put_line('Total Registros:'||get_info_reportes_v2%ROWCOUNT);
   CLOSE get_info_reportes_v2;
  end if; 
  
  
    if 'POR_FICHA_TECNICA' = psi_tipo_reporte then 
    open get_info_reportes_v3 (psi_cliente_desde
                                    ,psi_cliente_hasta
                                    ,pdi_fecha_desde
                                    ,pdi_fecha_hasta
                                    ,pni_ficha_tecnica_desde
                                    ,pni_ficha_tecnica_hasta
                                    ,psi_fre_fact_desde
                                    ,psi_fre_fact_hasta
                                    ,psi_status_desde
                                    ,psi_status_hasta
                                    ,psi_raz_soc_desde
                                    ,psi_raz_soc_hasta
                                    ); 
      LOOP
      FETCH get_info_reportes_v3 INTO info_reportes_rec;
      EXIT WHEN get_info_reportes_v3%NOTFOUND;
      dbms_output.put_line('Entra Loop Numero Ft:'||info_reportes_rec.NUMERO_FT);
      
        dbms_lob.append(lc_clob_tmp,'<PDFT_RECORD>'); 
        
        dbms_lob.append(lc_clob_tmp,'<CREATION_DATE>'||to_char(info_reportes_rec.creation_date,'YYYY/MM/DD')||'</CREATION_DATE>');  /** (1)  (4)**/
         dbms_output.put_line('*');
        dbms_lob.append(lc_clob_tmp,'<NUMERO_FT>'||to_char(info_reportes_rec.NUMERO_FT)||'</NUMERO_FT>');  /** (1)  (4)**/
         dbms_output.put_line('**');
        dbms_lob.append(lc_clob_tmp,'<REV>'||to_char(info_reportes_rec.REV)||'</REV>');  /** (1)  (4) **/
         dbms_output.put_line('***');
        dbms_lob.append(lc_clob_tmp,'<ARTICULO_ORACLE>'||replace_char_esp(info_reportes_rec.articulo_oracle)||'</ARTICULO_ORACLE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<NOMBRE_DEL_CLIENTE>'||replace_char_esp(info_reportes_rec.NOMBRE_DEL_CLIENTE)||'</NOMBRE_DEL_CLIENTE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<PRECIO>'||to_char(info_reportes_rec.precio)||'</PRECIO>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
        
         if info_reportes_rec.CNT_REG_NEG > 0 then 
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>SI</REGLA_NEG>'); 
        else
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>NO</REGLA_NEG>');  
        end if; 
        
        dbms_lob.append(lc_clob_tmp,'<CONCEPTO>'||to_char(info_reportes_rec.concepto)||'</CONCEPTO>');
        dbms_lob.append(lc_clob_tmp,'<PRODUCTO>'||to_char(info_reportes_rec.producto)||'</PRODUCTO>');
         dbms_lob.append(lc_clob_tmp,'<DIAS_DE_CREDITO>'||to_char(info_reportes_rec.dias_de_credito)||'</DIAS_DE_CREDITO>');
         dbms_output.put_line('****');
        dbms_lob.append(lc_clob_tmp,'<EMPRESA_QUE_FACTURA_M>'||replace_char_esp(info_reportes_rec.EMPRESA_QUE_FACTURA_M)||'</EMPRESA_QUE_FACTURA_M>');  /** (1) **/
        dbms_output.put_line('*****');
        dbms_lob.append(lc_clob_tmp,'<FRECUENCIA_FACTURACION_M>'||replace_char_esp(info_reportes_rec.FRECUENCIA_FACTURACION_M)||'</FRECUENCIA_FACTURACION_M>');  /** (1) (4) **/
          dbms_output.put_line('******');
        dbms_lob.append(lc_clob_tmp,'<UNIDAD_DE_NEGOCIO_M>'||replace_char_esp(info_reportes_rec.UNIDAD_DE_NEGOCIO_M)||'</UNIDAD_DE_NEGOCIO_M>');  /** (1)  (4) **/
         dbms_output.put_line('*******');
        dbms_lob.append(lc_clob_tmp,'<EJECUTIVO>'||replace_char_esp(info_reportes_rec.EJECUTIVO)||'</EJECUTIVO>');  /** (1) **/
         dbms_output.put_line('********');
        dbms_lob.append(lc_clob_tmp,'<STATUS_M>'||info_reportes_rec.STATUS_M||'</STATUS_M>');  /** (1) **/
         dbms_output.put_line('*********');
         dbms_lob.append(lc_clob_tmp,'<FECHA_INICIAL_OPERACION>'||to_char(info_reportes_rec.FECHA_INICIAL_OPERACION,'YYYY/MM/DD')||'</FECHA_INICIAL_OPERACION>');  /** (4) **/
           dbms_output.put_line('**********');
            dbms_lob.append(lc_clob_tmp,'<CONTRATO>'||info_reportes_rec.contrato_flag||'</CONTRATO>');  /** (4) **/
           dbms_lob.append(lc_clob_tmp,'<PRECIO_CD>'||to_char(info_reportes_rec.precio_cd)||'</PRECIO_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
          dbms_lob.append(lc_clob_tmp,'<PRECIO_PROTON_CD>'||to_char(info_reportes_rec.precio_proton_cd)||'</PRECIO_PROTON_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
     
           
        dbms_lob.append(lc_clob_tmp,'</PDFT_RECORD>'); 
   END LOOP;
    dbms_output.put_line('Total Registros:'||get_info_reportes_v3%ROWCOUNT);
   CLOSE get_info_reportes_v3;
  end if; 
  
     if 'POR_FRECUENCIA_FACTURACION' = psi_tipo_reporte then 
    open get_info_reportes_v4 (psi_cliente_desde
                                    ,psi_cliente_hasta
                                    ,pdi_fecha_desde
                                    ,pdi_fecha_hasta
                                    ,pni_ficha_tecnica_desde
                                    ,pni_ficha_tecnica_hasta
                                    ,psi_fre_fact_desde
                                    ,psi_fre_fact_hasta
                                    ,psi_status_desde
                                    ,psi_status_hasta
                                    ,psi_raz_soc_desde
                                    ,psi_raz_soc_hasta
                                    ); 
      LOOP
      FETCH get_info_reportes_v4 INTO info_reportes_rec;
      EXIT WHEN get_info_reportes_v4%NOTFOUND;
      dbms_output.put_line('Entra Loop Numero Ft:'||info_reportes_rec.NUMERO_FT);
      
        dbms_lob.append(lc_clob_tmp,'<PDFT_RECORD>'); 
        
        dbms_lob.append(lc_clob_tmp,'<CREATION_DATE>'||to_char(info_reportes_rec.creation_date,'YYYY/MM/DD')||'</CREATION_DATE>');  /** (1)  (4)**/
         dbms_output.put_line('*');
        dbms_lob.append(lc_clob_tmp,'<NUMERO_FT>'||to_char(info_reportes_rec.NUMERO_FT)||'</NUMERO_FT>');  /** (1)  (4)**/
         dbms_output.put_line('**');
        dbms_lob.append(lc_clob_tmp,'<REV>'||to_char(info_reportes_rec.REV)||'</REV>');  /** (1)  (4) **/
         dbms_output.put_line('***');
        dbms_lob.append(lc_clob_tmp,'<ARTICULO_ORACLE>'||replace_char_esp(info_reportes_rec.articulo_oracle)||'</ARTICULO_ORACLE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<NOMBRE_DEL_CLIENTE>'||replace_char_esp(info_reportes_rec.NOMBRE_DEL_CLIENTE)||'</NOMBRE_DEL_CLIENTE>');  /** (1) (4) **/
        dbms_lob.append(lc_clob_tmp,'<PRECIO>'||to_char(info_reportes_rec.precio)||'</PRECIO>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
        
         if info_reportes_rec.CNT_REG_NEG > 0 then 
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>SI</REGLA_NEG>'); 
        else
          dbms_lob.append(lc_clob_tmp,'<REGLA_NEG>NO</REGLA_NEG>');  
        end if; 
        
        dbms_lob.append(lc_clob_tmp,'<CONCEPTO>'||to_char(info_reportes_rec.concepto)||'</CONCEPTO>');
        dbms_lob.append(lc_clob_tmp,'<PRODUCTO>'||to_char(info_reportes_rec.producto)||'</PRODUCTO>');
         dbms_lob.append(lc_clob_tmp,'<DIAS_DE_CREDITO>'||to_char(info_reportes_rec.dias_de_credito)||'</DIAS_DE_CREDITO>');
         dbms_output.put_line('****');
        dbms_lob.append(lc_clob_tmp,'<EMPRESA_QUE_FACTURA_M>'||replace_char_esp(info_reportes_rec.EMPRESA_QUE_FACTURA_M)||'</EMPRESA_QUE_FACTURA_M>');  /** (1) **/
        dbms_output.put_line('*****');
        dbms_lob.append(lc_clob_tmp,'<FRECUENCIA_FACTURACION_M>'||replace_char_esp(info_reportes_rec.FRECUENCIA_FACTURACION_M)||'</FRECUENCIA_FACTURACION_M>');  /** (1) (4) **/
          dbms_output.put_line('******');
        dbms_lob.append(lc_clob_tmp,'<UNIDAD_DE_NEGOCIO_M>'||replace_char_esp(info_reportes_rec.UNIDAD_DE_NEGOCIO_M)||'</UNIDAD_DE_NEGOCIO_M>');  /** (1)  (4) **/
         dbms_output.put_line('*******');
        dbms_lob.append(lc_clob_tmp,'<EJECUTIVO>'||replace_char_esp(info_reportes_rec.EJECUTIVO)||'</EJECUTIVO>');  /** (1) **/
         dbms_output.put_line('********');
        dbms_lob.append(lc_clob_tmp,'<STATUS_M>'||info_reportes_rec.STATUS_M||'</STATUS_M>');  /** (1) **/
         dbms_output.put_line('*********');
         dbms_lob.append(lc_clob_tmp,'<FECHA_INICIAL_OPERACION>'||to_char(info_reportes_rec.FECHA_INICIAL_OPERACION,'YYYY/MM/DD')||'</FECHA_INICIAL_OPERACION>');  /** (4) **/
           dbms_output.put_line('**********');
            dbms_lob.append(lc_clob_tmp,'<CONTRATO>'||info_reportes_rec.contrato_flag||'</CONTRATO>');  /** (4) **/
           dbms_lob.append(lc_clob_tmp,'<PRECIO_CD>'||to_char(info_reportes_rec.precio_cd)||'</PRECIO_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
          dbms_lob.append(lc_clob_tmp,'<PRECIO_PROTON_CD>'||to_char(info_reportes_rec.precio_proton_cd)||'</PRECIO_PROTON_CD>');  /** (1) (4) **/ /** Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:ORA-06502: PL/SQL: error num?rico o de valor, -6502 **/
            
            
        dbms_lob.append(lc_clob_tmp,'</PDFT_RECORD>'); 
   END LOOP;
    dbms_output.put_line('Total Registros:'||get_info_reportes_v4%ROWCOUNT);
   CLOSE get_info_reportes_v4;
  end if;    
 
  dbms_lob.append(lc_clob_tmp,'</REPORTES_PDFT>'); 
   DBMS_LOB.CLOSE(lob_loc    => lc_clob_tmp);
   pco_info := lc_clob_tmp;
   
   dbms_output.put_line('Finaliza Procedimiento OBTIENE_REPORTES'); 
   
exception when others then 
  pso_errmsg := 'Excepcion Paquete xxqp_pdft_myp_pkg metodo obtiene_reportes:'||sqlerrm||', '||sqlcode;
  pso_errcod  := 2; 
end obtiene_reportes; 


procedure split_ilim(PCI_CLOB_REP  IN OUT NOCOPY CLOB
                            ,PCI_CLOB_ALIM IN CLOB
                             ,PSI_NODE            IN VARCHAR2
                            ,PSI_NODE_CHILD  IN VARCHAR2
                            ) IS 

v_char_before VARCHAR2(10):=''; /** 1 to 10 por acentos **/
v_length_clob  number:=0 ;
v_offset         number := 1;
 v_char          VARCHAR2(10); /** 1 to 10 por acentos **/
v_offsetTmp number := 1;
lc_clob_tmp  clob;
ln_nth          number := 0; 
 ln_instr_clob  number := 0; 
 ln_instr_clob_tmp number := 0;
 ls_substr_clob varchar2(32767) := ''; 
 ln_length_char number := 0; 
 ln_length_char_tot  number := 0; 
 
 BEGIN 
  fnd_file.put_line(fnd_file.log,'Comienza split_ilim');
  
  v_length_clob := dbms_lob.getlength(PCI_CLOB_ALIM);
  fnd_file.put_line(fnd_file.log,'dbms_lob.getlength(v_length_clob):'||v_length_clob);
  v_offset := 1;
  v_offsetTmp := 1;
  DBMS_LOB.CREATETEMPORARY(lob_loc  => lc_clob_tmp    
                                                     ,cache   => true     
                                                     ,dur       => dbms_lob.call
                                                     );
                                                     
  DBMS_LOB.OPEN(lob_loc    => lc_clob_tmp
                            ,open_mode  => DBMS_LOB.LOB_READWRITE
                            );
  
   while(v_offset<=v_length_clob) LOOP
        v_char := dbms_lob.substr(PCI_CLOB_ALIM, 1, v_offset);
        fnd_file.put_line(fnd_file.log,'v_char:'||v_char);
        v_char := replace_char_esp(v_char);
        ln_length_char := length(v_char); 
        if ln_length_char > 1 then 
         ln_length_char_tot := ln_length_char_tot+ln_length_char-1;
        end if; 
        
       /* dbms_lob.append(lc_clob_tmp,replace_char_esp(v_char));  */
        dbms_lob.append(lc_clob_tmp,v_char);
         v_offset := v_offset + 1;
        if v_char = chr(10) then 
         ln_nth := ln_nth+1;
         fnd_file.put_line(fnd_file.log,'v_offsetTmp:'||v_offsetTmp||', ln_nth:'||ln_nth||', ln_instr_clob_tmp:'||ln_instr_clob_tmp);
         ln_instr_clob := dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth);
         fnd_file.put_line(fnd_file.log,'dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth):'||dbms_lob.instr(lc_clob_tmp,chr(10),1,ln_nth));
         ls_substr_clob := dbms_lob.substr(lc_clob_tmp,ln_instr_clob-ln_instr_clob_tmp-1,v_offsetTmp);
         fnd_file.put_line(fnd_file.log,'dbms_lob.substr(lc_clob_tmp,ln_instr_clob,v_offsetTmp):'||ls_substr_clob);
         fnd_file.put_line(fnd_file.log,'length(ls_substr_clob):'||length(ls_substr_clob));
         if ls_substr_clob is not null  then 
         dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
         dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>'||ls_substr_clob||'</'||PSI_NODE_CHILD||'>');     
         dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
         else
        dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
         dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>&#160;</'||PSI_NODE_CHILD||'>');     
         dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
         end if; 
         
         v_offsetTmp := ln_instr_clob+1;
         ln_instr_clob_tmp := ln_instr_clob;
        
--        elsif  v_char = chr(10) and v_char_before = chr(10) then
--            fnd_file.put_line(fnd_file.log,'Salto de linea solo detectado'); 
--          dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
--         dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>&#160;</'||PSI_NODE_CHILD||'>');  
--       --  dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>a</'||PSI_NODE_CHILD||'>');     
--          dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
        end if; 
   
       v_char_before :=  v_char;   
   
   END LOOP;
   
     if (v_offset-1) = v_length_clob AND ln_nth>0  then 
           fnd_file.put_line(fnd_file.log,'v_offsetTmp:'||v_offsetTmp);
         --  fnd_file.put_line(fnd_file.log,'v_offset-ln_instr_clob_tmp-1:'||v_offset-ln_instr_clob_tmp-1);
           ls_substr_clob := dbms_lob.substr(lc_clob_tmp,v_offset+ln_length_char_tot-ln_instr_clob_tmp,v_offsetTmp);
             fnd_file.put_line(fnd_file.log,'v_offset = v_length_clob:'||ls_substr_clob);
           dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
         dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>'||ls_substr_clob||'</'||PSI_NODE_CHILD||'>');     
         dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
   
     elsif (v_offset-1) = v_length_clob AND ln_nth=0 AND lc_clob_tmp is not null then      
          dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
          dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>'||lc_clob_tmp||'</'||PSI_NODE_CHILD||'>');     
          dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
        end if;
  
   DBMS_LOB.CLOSE(lob_loc    => lc_clob_tmp);
  
  fnd_file.put_line(fnd_file.log,'Finaliza split_ilim');
 EXCEPTION WHEN OTHERS THEN 
  fnd_file.put_line(fnd_file.log,'Exception split_ilim:'||sqlerrm||','||sqlcode);
 END split_ilim; 

 procedure split_varchar2_bkp(PCI_CLOB_REP    IN OUT NOCOPY CLOB
                                     ,PSI_VARCHAR_ALIM   IN VARCHAR2
                                     ,PSI_NODE           IN VARCHAR2
                                    ,PSI_NODE_CHILD  IN VARCHAR2
                                     ) IS 
 ls_split_vachar  varchar2(32767);
 BEGIN 
    fnd_file.put_line(fnd_file.log,'Comienza split_varchar2');
    
      ls_split_vachar := replace_char_esp(PSI_VARCHAR_ALIM);
              for idx in ( SELECT LEVEL AS id, REGEXP_SUBSTR(ls_split_vachar, '[^'||chr(10)||']+', 1, LEVEL) AS data
                                FROM dual
                           CONNECT BY REGEXP_SUBSTR(ls_split_vachar, '[^'||chr(10)||']+', 1, LEVEL) IS NOT NULL) loop
                  
               dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
               dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>'||idx.data||'</'||PSI_NODE_CHILD||'>');     
               dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
              end loop; 
    
     fnd_file.put_line(fnd_file.log,'Finaliza split_varchar2'); 
 EXCEPTION WHEN OTHERS THEN 
  fnd_file.put_line(fnd_file.log,'Exception split_varchar2:'||sqlerrm||','||sqlcode);
 END split_varchar2_bkp;                                    
 
 
  procedure split_varchar2(PCI_CLOB_REP    IN OUT NOCOPY CLOB
                                     ,PSI_VARCHAR_ALIM   IN VARCHAR2
                                     ,PSI_NODE           IN VARCHAR2
                                    ,PSI_NODE_CHILD  IN VARCHAR2
                                     ) is              
                                                            
  ls_split_vachar  varchar2(32767);
  
 BEGIN 
    fnd_file.put_line(fnd_file.log,'Comienza split_varchar2'); 
      ls_split_vachar := replace_char_esp(PSI_VARCHAR_ALIM);
      ls_split_vachar := replace(ls_split_vachar,chr(10),'&#10;');
    dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE||'>');     
    dbms_lob.append(PCI_CLOB_REP,'<'||PSI_NODE_CHILD||'>'||ls_split_vachar||'</'||PSI_NODE_CHILD||'>');     
    dbms_lob.append(PCI_CLOB_REP,'</'||PSI_NODE||'>');
   fnd_file.put_line(fnd_file.log,'Finaliza split_varchar2'); 
 EXCEPTION WHEN OTHERS THEN 
  fnd_file.put_line(fnd_file.log,'Exception split_varchar2:'||sqlerrm||','||sqlcode);
 END split_varchar2;                   

end  xxqp_pdft_myp_pkg;
/


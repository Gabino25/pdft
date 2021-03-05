CREATE OR REPLACE package xxqp_pdft_myp_pkg is 

/** 15042020 se agrega nvl al nombre del cliente **/
/** 17042020 se agrega nvl al nombre del cliente en obtiene reportes **/
/** 16072020 se agrega parametro extra y se crea un concurrente **/

type rep_info_typ is record (
 ID XXQP_PDFT_REPORTES_V.ID%TYPE
 ,NUMERO_FT XXQP_PDFT_REPORTES_V.NUMERO_FT%TYPE
 ,REV XXQP_PDFT_REPORTES_V.REV%TYPE
 ,STATUS XXQP_PDFT_REPORTES_V.STATUS%TYPE
 ,STATUS_M XXQP_PDFT_REPORTES_V.STATUS_M%TYPE
 ,NUMERO_FT_REFERENCIA XXQP_PDFT_REPORTES_V.NUMERO_FT_REFERENCIA%TYPE
 ,PARTY_ID XXQP_PDFT_REPORTES_V.PARTY_ID%TYPE
 ,PDFT_CLIENTE_HEADER_ID XXQP_PDFT_REPORTES_V.PDFT_CLIENTE_HEADER_ID%TYPE
 ,EMPRESA_QUE_FACTURA_C XXQP_PDFT_REPORTES_V.EMPRESA_QUE_FACTURA_C%TYPE
 ,EMPRESA_QUE_FACTURA_M XXQP_PDFT_REPORTES_V.EMPRESA_QUE_FACTURA_M%TYPE
 ,EMPRESA_QUE_FACTURA_M_BKP XXQP_PDFT_REPORTES_V.EMPRESA_QUE_FACTURA_M_BKP%TYPE
 ,UNIDAD_DE_NEGOCIO_C XXQP_PDFT_REPORTES_V.UNIDAD_DE_NEGOCIO_C%TYPE
 ,FRECUENCIA_FACTURACION_C XXQP_PDFT_REPORTES_V.FRECUENCIA_FACTURACION_C%TYPE
 ,FRECUENCIA_FACTURACION_M XXQP_PDFT_REPORTES_V.FRECUENCIA_FACTURACION_M%TYPE
 ,FECHA_INICIAL_OPERACION XXQP_PDFT_REPORTES_V.FECHA_INICIAL_OPERACION%TYPE
 ,CREATED_BY XXQP_PDFT_REPORTES_V.CREATED_BY%TYPE
 ,CREATION_DATE XXQP_PDFT_REPORTES_V.CREATION_DATE%TYPE
 ,LAST_UPDATED_BY XXQP_PDFT_REPORTES_V.LAST_UPDATED_BY%TYPE
 ,LAST_UPDATE_DATE XXQP_PDFT_REPORTES_V.LAST_UPDATE_DATE%TYPE
 ,LAST_UPDATE_LOGIN XXQP_PDFT_REPORTES_V.LAST_UPDATE_LOGIN%TYPE
 ,ATTRIBUTE_CATEGORY XXQP_PDFT_REPORTES_V.ATTRIBUTE_CATEGORY%TYPE
 ,ATTRIBUTE1 XXQP_PDFT_REPORTES_V.ATTRIBUTE1%TYPE
 ,ATTRIBUTE2 XXQP_PDFT_REPORTES_V.ATTRIBUTE2%TYPE
 ,ATTRIBUTE3 XXQP_PDFT_REPORTES_V.ATTRIBUTE3%TYPE
 ,ATTRIBUTE4 XXQP_PDFT_REPORTES_V.ATTRIBUTE4%TYPE
 ,ATTRIBUTE5 XXQP_PDFT_REPORTES_V.ATTRIBUTE5%TYPE
 ,NOMBRE_DEL_CLIENTE XXQP_PDFT_REPORTES_V.NOMBRE_DEL_CLIENTE%TYPE
 ,PARTY_NAME XXQP_PDFT_REPORTES_V.PARTY_NAME%TYPE
 ,EJECUTIVO XXQP_PDFT_REPORTES_V.EJECUTIVO%TYPE
 ,UNIDAD_DE_NEGOCIO_M XXQP_PDFT_REPORTES_V.UNIDAD_DE_NEGOCIO_M%TYPE
 ,CONTRATO_FLAG XXQP_PDFT_REPORTES_V.CONTRATO_FLAG%TYPE
 ,ARTICULO_ORACLE XXQP_PDFT_REPORTES_V.ARTICULO_ORACLE%TYPE
 ,PRECIO XXQP_PDFT_REPORTES_V.PRECIO%TYPE
 ,CONCEPTO XXQP_PDFT_REPORTES_V.CONCEPTO%TYPE
 ,PRODUCTO XXQP_PDFT_REPORTES_V.PRODUCTO%TYPE
 ,DIAS_DE_CREDITO XXQP_PDFT_REPORTES_V.DIAS_DE_CREDITO%TYPE
 ,CNT_REG_NEG XXQP_PDFT_REPORTES_V.CNT_REG_NEG%TYPE
);

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
 ) return rep_info_typ;

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
 ) return rep_info_typ;
 
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
 ) return rep_info_typ;

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
 ) return rep_info_typ;


procedure main (pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pni_numero_ft in number
 ); 
 

procedure get_info(pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pco_info out clob
 ,pni_myp_header_id in number
 ,psi_modif in varchar2 default 'N'
 );
 
procedure obtiene_reportes ( pso_errmsg out varchar2 
 ,pso_errcod out varchar2
 ,psi_tipo_reporte in varchar2
 ,psi_cliente_desde in varchar2 
 ,psi_cliente_hasta in varchar2
 ,pdi_fecha_desde in date 
 ,pdi_fecha_hasta in date 
 ,pni_ficha_tecnica_desde in number 
 ,pni_ficha_tecnica_hasta in number
 ,psi_producto_desde in varchar2
 ,psi_producto_hasta in varchar2
 ,psi_fre_fact_desde in varchar2
 ,psi_fre_fact_hasta in varchar2
 ,psi_status_desde in varchar2
 ,psi_status_hasta in varchar2
 ,psi_raz_soc_desde in varchar2 
 ,psi_raz_soc_hasta in varchar2
 ,pco_info out clob 
 );
 
end APPS.xxqp_pdft_myp_pkg;
/


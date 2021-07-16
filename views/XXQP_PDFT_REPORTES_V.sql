DROP VIEW APPS.XXQP_PDFT_REPORTES_V;

/* Formatted on 16/07/2021 01:26:50 p.m. (QP5 v5.149.1003.31008) */
CREATE OR REPLACE FORCE VIEW APPS.XXQP_PDFT_REPORTES_V
(
   ID,
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
)
AS
   SELECT myp_head.ID,
          myp_head.NUMERO_FT,
          myp_head.REV,
          myp_head.STATUS,
          DECODE (myp_head.STATUS,
                  'PRELIMINAR', 'Preliminar',
                  'ABIERTA', 'Abierta',
                  'CERRADA', 'Cerrada',
                  'CANCELADA', 'Cancelada')
             STATUS_M,
          myp_head.NUMERO_FT_REFERENCIA,
          myp_head.PARTY_ID,
          myp_head.PDFT_CLIENTE_HEADER_ID,
          myp_head.EMPRESA_QUE_FACTURA_C,
          (SELECT LEGAL_ENTITY_IDENTIFIER || ' - ' || NAME /*LEGAL_ENTITY_NAME*/
                                                          meaning
             FROM xle_entity_profiles
            WHERE LEGAL_ENTITY_ID = myp_head.EMPRESA_QUE_FACTURA_C)
             EMPRESA_QUE_FACTURA_M,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'EMPRESA_QUE_FACTURA'
                  AND lookup_code = myp_head.EMPRESA_QUE_FACTURA_C)
             EMPRESA_QUE_FACTURA_M_BKP,
          myp_head.UNIDAD_DE_NEGOCIO_C,
          myp_head.FRECUENCIA_FACTURACION_C,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'CICLO_DE_FACTURACION'
                  AND lookup_code = myp_head.FRECUENCIA_FACTURACION_C)
             FRECUENCIA_FACTURACION_M,
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
          (SELECT NVL (p.known_as, p.party_name)              /** 17042020 **/
             FROM xxqp_pdft_clientes_info_v p                 /** 24042020 **/
            WHERE p.party_id = myp_head.party_id)
             nombre_del_cliente,
          (SELECT p.party_name
             FROM hz_parties p
            WHERE p.party_id = myp_head.party_id)
             party_name,
          (SELECT usuario
             FROM XXQP_PDFT_USUARIOS_RO
            WHERE id = myp_head.ejecutivo)
             ejecutivo,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'UNIDAD_DE_NEGOCIO'
                  AND lookup_code = myp_head.UNIDAD_DE_NEGOCIO_C)
             UNIDAD_DE_NEGOCIO_M,
          DECODE (myp_head.contrato_flag,  'Y', 'Si',  'N', 'No')
             contrato_flag,
          myp_head.articulo_oracle,
          NVL ( (SELECT NVL (xpmc.entrega_local, 0)
                   FROM XXQP_PDFT_MYP_COBERTURA xpmc
                  WHERE xpmc.myp_header_id = myp_head.id),
               0)
             precio,
          (SELECT nombre_producto
             FROM XXQP_PDFT_MYP_GENERAL xpmg
            WHERE xpmg.myp_header_id = myp_head.id)
             concepto,
          (SELECT xpmc.description
             FROM XXQP_PDFT_MYP_GENERAL xpmg, xxqp_pdft_mgr_catalogos xpmc
            WHERE     xpmc.lookup_type = 'TIPO_DE_PRODUCTO'
                  AND xpmg.tipo_producto_c = xpmc.lookup_code
                  AND xpmg.myp_header_id = myp_head.id)
             producto,
          NVL ( (SELECT DIAS_HABILES_PAGO
                   FROM XXQP_PDFT_MYP_GENERAL xpmg
                  WHERE xpmg.myp_header_id = myp_head.id),
               '')
             dias_de_credito,
          (SELECT COUNT (1)
             FROM XXQP_PDFT_MYP_REG_NEG XPMRN
            WHERE XPMRN.MYP_HEADER_ID = myp_head.id)
             CNT_REG_NEG,
           ( SELECT NVL(PRECIO,0) 
              FROM XXQP_PDFT_MYP_PROCESOS_V1 XPMP
             WHERE XPMP.NUMERO_FT  = myp_head.NUMERO_FT
             ) PRECIO_CD,
             ( SELECT NVL(PRECIO_PROTON,0) 
              FROM XXQP_PDFT_MYP_PROCESOS_V1 XPMP
             WHERE XPMP.NUMERO_FT  = myp_head.NUMERO_FT
             ) PRECIO_PROTON_CD
     FROM XXQP_PDFT_MYP_HEADER myp_head
   UNION           /******************** Union ******************************/
   SELECT bpo_head.ID,
          bpo_head.NUMERO_FT,
          bpo_head.REV,
          bpo_head.STATUS,
          DECODE (bpo_head.STATUS,
                  'PRELIMINAR', 'Preliminar',
                  'ABIERTA', 'Abierta',
                  'CERRADA', 'Cerrada',
                  'CANCELADA', 'Cancelada')
             STATUS_M,
          bpo_head.NUMERO_FT_REFERENCIA,
          bpo_head.PARTY_ID,
          bpo_head.PDFT_CLIENTE_HEADER_ID,
          bpo_head.EMPRESA_QUE_FACTURA_C,
          (SELECT LEGAL_ENTITY_IDENTIFIER || ' - ' || NAME /*LEGAL_ENTITY_NAME*/
                                                          meaning
             FROM xle_entity_profiles
            WHERE LEGAL_ENTITY_ID = bpo_head.EMPRESA_QUE_FACTURA_C)
             EMPRESA_QUE_FACTURA_M,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'EMPRESA_QUE_FACTURA'
                  AND lookup_code = bpo_head.EMPRESA_QUE_FACTURA_C)
             EMPRESA_QUE_FACTURA_M_BKP,
          bpo_head.UNIDAD_DE_NEGOCIO_C,
          bpo_head.FRECUENCIA_FACTURACION_C,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'CICLO_DE_FACTURACION'
                  AND lookup_code = bpo_head.FRECUENCIA_FACTURACION_C)
             FRECUENCIA_FACTURACION_M,
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
          (SELECT NVL (p.known_as, p.party_name)              /** 17042020 **/
             FROM xxqp_pdft_clientes_info_v p                 /** 24042020 **/
            WHERE p.party_id = bpo_head.party_id)
             nombre_del_cliente,
          (SELECT p.party_name
             FROM hz_parties p
            WHERE p.party_id = bpo_head.party_id)
             party_name,
          (SELECT usuario
             FROM XXQP_PDFT_USUARIOS_RO
            WHERE id = bpo_head.ejecutivo)
             ejecutivo,
          (SELECT description
             FROM xxqp_pdft_mgr_catalogos
            WHERE lookup_type = 'UNIDAD_DE_NEGOCIO'
                  AND lookup_code = bpo_head.UNIDAD_DE_NEGOCIO_C)
             UNIDAD_DE_NEGOCIO_M,
          DECODE (bpo_head.contrato_flag,  'Y', 'Si',  'N', 'No')
             contrato_flag,
          bpo_head.articulo_oracle,
          NVL ( (SELECT NVL (xpbp.precio_unitario, 0)
                   FROM XXQP_PDFT_BPO_PRECIO xpbp
                  WHERE xpbp.bpo_header_id = bpo_head.id AND ROWNUM = 1),
               0)
             precio,
          (SELECT xpbp.concepto
             FROM XXQP_PDFT_BPO_PRECIO xpbp
            WHERE xpbp.bpo_header_id = bpo_head.id AND ROWNUM = 1)
             concepto,
          (SELECT xpbp.concepto
             FROM XXQP_PDFT_BPO_PRECIO xpbp
            WHERE xpbp.bpo_header_id = bpo_head.id AND ROWNUM = 1)
             producto,
          NVL ( (SELECT dia_de_pago
                   FROM XXQP_PDFT_BPO_PAGO xpbp
                  WHERE xpbp.bpo_header_id = bpo_head.id),
               '')
             dias_de_credito,
          (SELECT COUNT (1)
             FROM XXQP_PDFT_BPO_REG_NEG XPBRN
            WHERE XPBRN.BPO_HEADER_ID = bpo_head.id)
             CNT_REG_NEG,
           0 PRECIO_CD,
           0 PRECIO_PROTON_CD
     FROM XXQP_PDFT_BPO_HEADER bpo_head;

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER DROP PRIMARY KEY CASCADE;

DROP TABLE APPS.XXQP_PDFT_MYP_HEADER CASCADE CONSTRAINTS;

CREATE TABLE APPS.XXQP_PDFT_MYP_HEADER
(
  ID                        NUMBER,
  NUMERO_FT                 NUMBER,
  REV                       NUMBER,
  STATUS                    VARCHAR2(30 BYTE),
  NUMERO_FT_REFERENCIA      NUMBER,
  PARTY_ID                  NUMBER,
  PDFT_CLIENTE_HEADER_ID    NUMBER,
  EMPRESA_QUE_FACTURA_C     VARCHAR2(30 BYTE),
  UNIDAD_DE_NEGOCIO_C       VARCHAR2(30 BYTE),
  FRECUENCIA_FACTURACION_C  VARCHAR2(30 BYTE),
  CONTRATO_FLAG             VARCHAR2(10 BYTE),
  CONTRATO_FILE_NAME        VARCHAR2(256 BYTE),
  CONTRATO_CONTENT_TYPE     VARCHAR2(256 BYTE),
  CONTRATO_FILE             BLOB,
  FECHA_INICIAL_OPERACION   DATE,
  CREATED_BY                NUMBER(15)          NOT NULL,
  CREATION_DATE             DATE                NOT NULL,
  LAST_UPDATED_BY           NUMBER(15)          NOT NULL,
  LAST_UPDATE_DATE          DATE                NOT NULL,
  LAST_UPDATE_LOGIN         NUMBER(15),
  ATTRIBUTE_CATEGORY        VARCHAR2(250 BYTE),
  ATTRIBUTE1                VARCHAR2(2000 BYTE),
  ATTRIBUTE2                VARCHAR2(2000 BYTE),
  ATTRIBUTE3                VARCHAR2(2000 BYTE),
  ATTRIBUTE4                VARCHAR2(2000 BYTE),
  ATTRIBUTE5                VARCHAR2(2000 BYTE),
  EJECUTIVO                 VARCHAR2(50 BYTE),
  ARTICULO_ORACLE           VARCHAR2(20 BYTE)
);


CREATE UNIQUE INDEX APPS.XXQP_PDFT_MYP_HEADER_ID_PK ON APPS.XXQP_PDFT_MYP_HEADER (ID);


ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD (CONSTRAINT XXQP_PDFT_MYP_HEADER_ID_PK PRIMARY KEY(ID) USING INDEX APPS.XXQP_PDFT_MYP_HEADER_ID_PK);

GRANT SELECT ON APPS.XXQP_PDFT_MYP_HEADER TO PREFACTURA;
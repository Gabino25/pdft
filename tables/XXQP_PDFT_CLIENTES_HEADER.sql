ALTER TABLE APPS.XXQP_PDFT_CLIENTES_HEADER
 DROP PRIMARY KEY CASCADE;

DROP TABLE APPS.XXQP_PDFT_CLIENTES_HEADER CASCADE CONSTRAINTS;

CREATE TABLE APPS.XXQP_PDFT_CLIENTES_HEADER
(
  ID                     NUMBER,
  NOMBRE_CLIENTE         VARCHAR2(250 BYTE),
  GIRO_EMPRESARIAL_C     VARCHAR2(250 BYTE),
  EMPRESA_QUE_FACTURA_C  VARCHAR2(250 BYTE),
  TIPO_OPERATIVO_C       VARCHAR2(250 BYTE),
  TIPO_ADMINISTRATIVO_C  VARCHAR2(250 BYTE),
  TIPO_COMERCIAL_C       VARCHAR2(250 BYTE),
  COMENTARIOS            VARCHAR2(1000 BYTE),
  CREATED_BY             NUMBER(15)             NOT NULL,
  CREATION_DATE          DATE                   NOT NULL,
  LAST_UPDATED_BY        NUMBER(15)             NOT NULL,
  LAST_UPDATE_DATE       DATE                   NOT NULL,
  LAST_UPDATE_LOGIN      NUMBER(15),
  ATTRIBUTE_CATEGORY     VARCHAR2(250 BYTE),
  ATTRIBUTE1             VARCHAR2(2000 BYTE),
  ATTRIBUTE2             VARCHAR2(2000 BYTE),
  ATTRIBUTE3             VARCHAR2(2000 BYTE),
  ATTRIBUTE4             VARCHAR2(2000 BYTE),
  ATTRIBUTE5             VARCHAR2(2000 BYTE),
  PARTY_ID               NUMBER
)
TABLESPACE APPS_TS_TX_DATA
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          128K
            NEXT             128K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE UNIQUE INDEX APPS.PDFT_CLIENTES_HEADER_ID_PK ON APPS.XXQP_PDFT_CLIENTES_HEADER
(ID)
LOGGING
TABLESPACE APPS_TS_TX_DATA
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          128K
            NEXT             128K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


ALTER TABLE APPS.XXQP_PDFT_CLIENTES_HEADER ADD (
  CONSTRAINT PDFT_CLIENTES_HEADER_ID_PK
  PRIMARY KEY
  (ID)
  USING INDEX APPS.PDFT_CLIENTES_HEADER_ID_PK);
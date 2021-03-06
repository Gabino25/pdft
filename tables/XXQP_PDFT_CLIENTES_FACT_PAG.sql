DROP TABLE APPS.XXQP_PDFT_CLIENTES_FACT_PAG CASCADE CONSTRAINTS;

CREATE TABLE APPS.XXQP_PDFT_CLIENTES_FACT_PAG
(
  ID                      NUMBER,
  HEADER_ID               NUMBER,
  CONDICIONES_DE_PAGO_C   VARCHAR2(50 BYTE),
  OBSERVACIONES           VARCHAR2(200 BYTE),
  TIPO_DE_PAGO_C          VARCHAR2(50 BYTE),
  REQUIERE_ADENDAS_C      VARCHAR2(20 BYTE),
  REQUIERE_FACTURA_C      VARCHAR2(20 BYTE),
  MOTIVO                  VARCHAR2(150 BYTE),
  CICLO_DE_FACTURACION_C  VARCHAR2(250 BYTE),
  USO_DEL_CFDI_C          VARCHAR2(250 BYTE),
  METODO_DE_PAGO_C        VARCHAR2(50 BYTE),
  NUMERO_DE_CUENTA        VARCHAR2(11 BYTE),
  NOMBRE_DEL_BANCO        VARCHAR2(35 BYTE),
  DIAS_NAT_DE_CREDITO     VARCHAR2(35 BYTE),
  DIAS_RECEPCION_FACTUR   VARCHAR2(35 BYTE),
  UTILIZA_PORTAL_C        VARCHAR2(20 BYTE),
  PORTAL_LINK             VARCHAR2(200 BYTE),
  ORDEN_DE_COMPRA_C       VARCHAR2(20 BYTE),
  CONTRATO_C              VARCHAR2(20 BYTE),
  VIGENCIA_CONTRATO       VARCHAR2(30 BYTE),
  CREATED_BY              NUMBER(15)            NOT NULL,
  CREATION_DATE           DATE                  NOT NULL,
  LAST_UPDATED_BY         NUMBER(15)            NOT NULL,
  LAST_UPDATE_DATE        DATE                  NOT NULL,
  LAST_UPDATE_LOGIN       NUMBER(15),
  ATTRIBUTE_CATEGORY      VARCHAR2(250 BYTE),
  ATTRIBUTE1              VARCHAR2(2000 BYTE),
  ATTRIBUTE2              VARCHAR2(2000 BYTE),
  ATTRIBUTE3              VARCHAR2(2000 BYTE),
  ATTRIBUTE4              VARCHAR2(2000 BYTE),
  ATTRIBUTE5              VARCHAR2(2000 BYTE),
  LUNES                   VARCHAR2(20 BYTE),
  MARTES                  VARCHAR2(20 BYTE),
  MIERCOLES               VARCHAR2(20 BYTE),
  JUEVES                  VARCHAR2(20 BYTE),
  VIERNES                 VARCHAR2(20 BYTE),
  SABADO                  VARCHAR2(20 BYTE),
  DOMINGO                 VARCHAR2(20 BYTE)
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


ALTER TABLE APPS.XXQP_PDFT_CLIENTES_FACT_PAG ADD (
  CONSTRAINT XXQP_PDFT_CLIENTES_FACT_PAG_FK 
  FOREIGN KEY (HEADER_ID) 
  REFERENCES APPS.XXQP_PDFT_CLIENTES_HEADER (ID));

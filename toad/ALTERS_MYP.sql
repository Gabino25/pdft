select * from XXQP_PDFT_MYP_COBERTURA

/** FROM VARCHAR2(4000) TO CLOB **/
ALTER TABLE APPS.XXQP_PDFT_MYP_COBERTURA MODIFY COMENTARIOS CLOB; /** ORA-22858: modificacion no valida del tipo de dato **/

ALTER TABLE APPS.XXQP_PDFT_MYP_COBERTURA ADD COMENTARIOS_ILIM CLOB;

/***************************************************/
/** FROM VARCHAR2(30) TO VARCHAR2(4000) **/
ALTER TABLE APPS.XXQP_PDFT_MYP_PROCESOS MODIFY OTROS_PROCESOS VARCHAR2(4000);

ALTER TABLE APPS.XXQP_PDFT_MYP_PROCESOS MODIFY COMENTARIOS_INSTRUCC CLOB;  /** ORA-22858: modificacion no valida del tipo de dato **/

ALTER TABLE APPS.XXQP_PDFT_MYP_PROCESOS ADD COMENTARIOS_INSTRUCC_ILIM CLOB;

/******************************************************/

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE_NAME1 VARCHAR2(500 BYTE); 

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD CONTENT_TYPE1 VARCHAR2(500 BYTE);      

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE1 BLOB;

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE_NAME2 VARCHAR2(500 BYTE);

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD CONTENT_TYPE2 VARCHAR2(500 BYTE); 

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE2 BLOB; 

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE_NAME3 VARCHAR2(500 BYTE); 

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD CONTENT_TYPE3 VARCHAR2(500 BYTE); 

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD FILE3 BLOB;

ALTER TABLE APPS.XXQP_PDFT_MYP_HEADER ADD MODIF_REALIZ CLOB;  


XXQP_PDFT_MYP_PKG
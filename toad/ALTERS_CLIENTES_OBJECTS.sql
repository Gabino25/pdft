ALTER TABLE APPS.XXQP_PDFT_CLIENTES_HEADER ADD  PARTY_ID NUMBER;

ALTER TABLE APPS.XXQP_PDFT_CLIENTES_HEADER ADD CONSTRAINT XXQP_PDFT_CLIENTES_HEADER_U2 UNIQUE(NOMBRE_CLIENTE);

ALTER TABLE APPS.XXQP_PDFT_CLIENTES_HEADER ADD CONSTRAINT XXQP_PDFT_CLIENTES_HEADER_U3 UNIQUE(PARTY_ID);
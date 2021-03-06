CREATE OR REPLACE PACKAGE APPS.XXQP_PDFT_CUSTOMER_PKG AS 
PROCEDURE MAIN(PSO_ERRMSG               OUT VARCHAR2
                          ,PSO_ERRCOD                 OUT VARCHAR2 
                          ,PNI_CUSTOMER_ID          IN NUMBER 
                          ,PSI_OPERATING_UNIT       IN VARCHAR2
                         ); 
 
 PROCEDURE GET_INFO(PSO_ERRMSG                    OUT VARCHAR2
                            ,PSO_ERRCOD                     OUT VARCHAR2
                            ,PCO_INFO                         OUT CLOB
                            ,PNI_CUSTOMER_ID              IN NUMBER
                            ,PSI_OPERATING_UNIT       IN VARCHAR2
                            ,PSI_MOVIMIENTO             IN VARCHAR2 DEFAULT 'CONSULTA'
                           ); 
END XXQP_PDFT_CUSTOMER_PKG;
/


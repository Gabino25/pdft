CREATE OR REPLACE package xxqp_pdft_bpo_pkg is 

/** 15042020 se agrega nvl al nombre del cliente **/
/** 17042020 se crea concurrente para debuguear pdf **/

 procedure main(pso_errmsg out varchar2
 ,pso_errcod out varchar2 
 ,pni_numero_ft in number 
 ); 
 
 procedure get_info(pso_errmsg out varchar2
 ,pso_errcod out varchar2
 ,pco_info out clob
 ,pni_bpo_header_id in number
 ); 

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
 ); 
 
 end APPS.xxqp_pdft_bpo_pkg; 
/


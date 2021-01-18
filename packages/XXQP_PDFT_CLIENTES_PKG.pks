CREATE OR REPLACE package APPS.xxqp_pdft_clientes_pkg is 

 procedure from_pdft_to_oracle(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 );
 procedure call_create_cust_account(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pno_party_id out number 
 ,pno_cust_account_id out number
 ); 
 
 procedure call_create_location(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pno_location_id out number
 ); 
 
 procedure call_create_location_sec(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pno_location_id out number
 ); 
 procedure call_create_party_site( pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_party_id in number 
 ,pni_location_id_prim in number
 ,pno_party_site_id out number
 ); 
 
 procedure call_create_party_site_sec( pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_party_id in number 
 ,pni_location_id_sec in number
 ,pno_party_site_id out number
 ); 
 
 procedure call_attached_documents( pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pni_cust_account_id in number
 ); 
 
 procedure call_attached_documents_sec( pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pni_cust_account_id in number
 ); 
 
 procedure create_account_contact(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pni_cust_party_id in number
 ,pni_cust_account_id in number
 ,psi_tipo_informacion in varchar2
 ); 
 
 procedure call_create_cust_acct_site(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cust_account_id in number
 ,pni_party_site_id in number
 ,pni_org_id in number
 ,psi_metodo_de_pago in varchar2 
 ,psi_numero_cuenta in varchar2 
 ,psi_uso_del_cdfi in varchar2 
 ,pno_cust_acct_site_id out number 
 ); 
 procedure from_oracle_to_pdft(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ); 

 procedure from_pdft_to_oracle_sec(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 );
 
 procedure call_create_cust_account_sec(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ,pno_party_id out number 
 ,pno_cust_account_id out number
 ); 
 
end xxqp_pdft_clientes_pkg;
/


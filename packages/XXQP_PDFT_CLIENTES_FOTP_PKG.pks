CREATE OR REPLACE package APPS.XXQP_PDFT_CLIENTES_FOTP_PKG is 
  procedure from_oracle_to_pdft(pso_errmsg                    out varchar2
                                              ,pso_errcode                    out varchar2
                                              ,pni_party_id                    in  number
                                              ,pno_clientes_header_id    out number
                                              ); 
   
  procedure val_insert_mgr_catalogos( psi_lookup_type  in varchar2
                                                       ,psi_lookup_code  in varchar2  );                                            
  end  XXQP_PDFT_CLIENTES_FOTP_PKG;
/


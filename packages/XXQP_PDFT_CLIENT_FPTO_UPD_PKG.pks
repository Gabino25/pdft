CREATE OR REPLACE package APPS.XXQP_PDFT_CLIENT_FPTO_UPD_PKG is 

procedure upd_from_pdft_to_oracle(pso_errmsg                    out varchar2
                                                    ,pso_errcode                    out varchar2
                                                    ,pni_party_id                    in  number
                                                    ,pni_cliente_header_id      in  number
                                                     );  
                                                     
 procedure call_upd_location_prim(pso_errmsg                    out varchar2
                                                  ,pso_errcode                   out varchar2
                                                  ,pni_cliente_header_id      in   number
                                                   );
 
  procedure call_upd_location_sec(pso_errmsg                    out varchar2
                                                ,pso_errcode                   out varchar2
                                                ,pni_cliente_header_id      in   number
                                                );                                                  
                                                                                                          
end XXQP_PDFT_CLIENT_FPTO_UPD_PKG;
/


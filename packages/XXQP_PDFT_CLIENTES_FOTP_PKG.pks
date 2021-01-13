CREATE OR REPLACE package APPS.XXQP_PDFT_CLIENTES_FOTP_PKG is 
  procedure rollback_trx(pni_header_id  in number);
  
  procedure valida_clientes(pso_errmsg                    out varchar2
                                      ,pso_errcode                    out varchar2
                                      ,psi_nombre_cliente          in   varchar2
                                      ,psi_rfc                            in   varchar2
                                      ,psi_razon_social              in   varchar2
                                      ,psi_estado                      in   varchar2
                                      ,psi_operating_unit           in   varchar2
                                        );
                                        
  procedure from_oracle_to_pdft(pso_errmsg                    out varchar2
                                              ,pso_errcode                    out varchar2
                                              ,pni_party_id                    in  number
                                              ,pni_operating_unit           in number
                                              ,pno_clientes_header_id    out number
                                              );
                                               
  procedure populate_header(pso_errmsg                    out varchar2
                                          ,pso_errcode                    out varchar2
                                          ,pni_party_id                    in  number
                                          ,pni_operating_unit           in number
                                          ,pno_clientes_header_id    out number
                                            );           
   
   procedure upd_header(pso_errmsg                    out varchar2
                                     ,pso_errcode                    out varchar2
                                     ,pni_party_id                    in  number
                                     ,pni_operating_unit           in number
                                     ,pni_clientes_header_id    in  number
                                     );                                                                              
  
  procedure populate_sites( pso_errmsg                    out varchar2
                                        ,pso_errcode                    out varchar2
                                        ,pni_party_id                    in  number
                                        ,pni_operating_unit           in number
                                        ,pni_clientes_header_id    in number
                                        ,pno_dir_fiscal_id             out number
                                        );  
  
    procedure populate_punto_rec( pso_errmsg                    out varchar2
                                                 ,pso_errcode                    out varchar2
                                                 ,pni_party_id                    in  number
                                                 ,pni_operating_unit           in number
                                                 ,pni_clientes_header_id    in number
                                                 ,pno_punto_rec_id            out number
                                                 );  
    
     procedure populate_contacts( pso_errmsg                    out varchar2
                                                ,pso_errcode                    out varchar2
                                                ,pni_party_id                    in  number
                                                ,pni_operating_unit           in number
                                                ,pni_clientes_header_id    in number
                                                );
                                                
   procedure populate_fact_pag( pso_errmsg                    out varchar2
                                                ,pso_errcode                    out varchar2
                                                ,pni_party_id                    in  number
                                                ,pni_operating_unit           in number
                                                ,pni_clientes_header_id    in number
                                                ,pno_fact_pag_id             out number
                                                );
                                                                                      
  procedure val_insert_mgr_catalogos( psi_lookup_type  in varchar2
                                                       ,psi_lookup_code  in varchar2  );      
                                                                                             
  end  XXQP_PDFT_CLIENTES_FOTP_PKG;
/


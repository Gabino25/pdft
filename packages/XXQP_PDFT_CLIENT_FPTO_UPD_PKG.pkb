CREATE OR REPLACE package body APPS.XXQP_PDFT_CLIENT_FPTO_UPD_PKG is 

CURSOR get_client_head_info (CUR_CLIENTE_HEADER_ID  number) IS
     SELECT   xpch.ID                   
                  ,xpch.NOMBRE_CLIENTE        
                  ,xpch.GIRO_EMPRESARIAL_C     
                  ,xpch.EMPRESA_QUE_FACTURA_C  
                  ,xpch.TIPO_OPERATIVO_C       
                  ,xpch.TIPO_ADMINISTRATIVO_C   
                  ,xpch.TIPO_COMERCIAL_C       
                  ,xpch.COMENTARIOS           
                  ,xpch.CREATED_BY             
                  ,xpch.CREATION_DATE          
                  ,xpch.LAST_UPDATED_BY        
                  ,xpch.LAST_UPDATE_DATE       
                  ,xpch.LAST_UPDATE_LOGIN      
                  ,xpch.ATTRIBUTE_CATEGORY     
                  ,xpch.ATTRIBUTE1           
                  ,xpch.ATTRIBUTE2        party_id  
                  ,xpch.ATTRIBUTE3        cust_account_id    
                  ,xpch.ATTRIBUTE4            
                  ,xpch.ATTRIBUTE5        
                  ,xpch.RFC    
                  ,xpch.RAZON_SOCIAL
                  ,xpcdf.PRIM_RFC                
                  ,xpcdf.PRIM_RAZON_SOCIAL        
                  ,xpcdf.PRIM_DIRECCION            
                  ,xpcdf.PRIM_ENTRE_CALLES        
                  ,xpcdf.PRIM_COLONIA             
                  ,xpcdf.PRIM_CIUDAD_O_MPO         
                  ,xpcdf.PRIM_ESTADO_C             
                  ,xpcdf.PRIM_CODIGO_POSTAL_C    
                  ,xpcdf.PRIM_CEDULA             
                  ,xpcdf.PRIM_CEDULA_FILE        
                  ,xpcdf.SEC_RFC                 
                  ,xpcdf.SEC_RAZON_SOCIAL        
                  ,xpcdf.SEC_DIRECCION             
                  ,xpcdf.SEC_ENTRE_CALLES       
                  ,xpcdf.SEC_COLONIA              
                  ,xpcdf.SEC_CIUDAD_O_MPO     
                  ,xpcdf.SEC_ESTADO_C            
                  ,xpcdf.SEC_CODIGO_POSTAL_C      
                  ,xpcdf.SEC_CEDULA    
                  ,xpcfp.ID                                            xpcfp_ID                     
                    ,xpcfp.HEADER_ID                            xpcfp_HEADER_ID 
                    ,xpcfp.CONDICIONES_DE_PAGO_C  
                    ,xpcfp.OBSERVACIONES          
                    ,xpcfp.TIPO_DE_PAGO_C         
                    ,xpcfp.REQUIERE_ADENDAS_C     
                    ,xpcfp.REQUIERE_FACTURA_C     
                    ,xpcfp.MOTIVO                 
                    ,xpcfp.CICLO_DE_FACTURACION_C 
                    ,xpcfp.USO_DEL_CFDI_C         
                    ,xpcfp.METODO_DE_PAGO_C       
                    ,xpcfp.NUMERO_DE_CUENTA       
                    ,xpcfp.NOMBRE_DEL_BANCO       
                    ,xpcfp.DIAS_NAT_DE_CREDITO    
                    ,xpcfp.DIAS_RECEPCION_FACTUR  
                    ,xpcfp.UTILIZA_PORTAL_C       
                    ,xpcfp.PORTAL_LINK            
                    ,xpcfp.ORDEN_DE_COMPRA_C      
                    ,xpcfp.CONTRATO_C             
                    ,xpcfp.VIGENCIA_CONTRATO      
                    ,xpcfp.ATTRIBUTE_CATEGORY     xpcfp_ATTRIBUTE_CATEGORY 
                    ,xpcfp.ATTRIBUTE1                     xpcfp_ATTRIBUTE1          
                    ,xpcfp.ATTRIBUTE2                     xpcfp_ATTRIBUTE2         
                    ,xpcfp.ATTRIBUTE3                     xpcfp_ATTRIBUTE3         
                    ,xpcfp.ATTRIBUTE4                     xpcfp_ATTRIBUTE4   
                    ,xpcfp.ATTRIBUTE5                     xpcfp_ATTRIBUTE5                      
       FROM XXQP_PDFT_CLIENTES_HEADER  xpch
               ,XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
               ,XXQP_PDFT_CLIENTES_FACT_PAG xpcfp
     WHERE 1=1
     and  xpcdf.header_id = xpch.id 
     and  xpcfp.header_id = xpch.id 
     and  xpch.ID = CUR_CLIENTE_HEADER_ID; 
     
procedure upd_from_pdft_to_oracle(pso_errmsg                    out varchar2
                                                    ,pso_errcode                    out varchar2
                                                    ,pni_party_id                    in  number
                                                    ,pni_cliente_header_id      in  number
                                                     ) is
ln_application_id                                     number; 
ln_resp_id                                              number; 

ls_statement                                           varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
ls_errmsg    varchar2(2000); 
ls_errcod     varchar2(2000); 
 ln_object_version_number      number;
 ls_return_status                                 varchar2(2000) := null; 
  ln_msg_count                                    number := null; 
  ls_msg_data                                     varchar2(2000) := null; 
  
 LT_CUST_ACCOUNT_REC_TYPE           HZ_CUST_ACCOUNT_V2PUB.CUST_ACCOUNT_REC_TYPE;
  LT_CUSTOMER_PROFILE_REC_TYPE     HZ_CUSTOMER_PROFILE_V2PUB.CUSTOMER_PROFILE_REC_TYPE;

              
begin 
pso_errmsg := null; 
pso_errcode := '0';

XXQP_PDFT_CLIENT_FPTO_UPD_PKG.call_upd_location_prim(pso_errmsg                   => ls_errmsg
                                                                                        ,pso_errcode                  => ls_errcod
                                                                                        ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                        );

XXQP_PDFT_CLIENT_FPTO_UPD_PKG.call_upd_location_sec(pso_errmsg                   => ls_errmsg
                                                                                      ,pso_errcode                  => ls_errcod
                                                                                      ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                      );
  

 XXQP_PDFT_CLIENT_FPTO_UPD_PKG.call_upd_organization(pso_errmsg                   => ls_errmsg
                                                                                      ,pso_errcode                  => ls_errcod
                                                                                      ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                      );                                                    
  
 XXQP_PDFT_CLIENT_FPTO_UPD_PKG.call_upd_account(pso_errmsg                   => ls_errmsg
                                                                                  ,pso_errcode                  => ls_errcod
                                                                                  ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                   );                                                                                        
                                                                                     

exception when others then 
pso_errmsg := 'Excepcion al llamar al metodo upd_from_pdft_to_oracle.'||sqlerrm; 
pso_errcode := '2';
end upd_from_pdft_to_oracle; 


 procedure call_upd_organization(pso_errmsg                    out varchar2
                                                  ,pso_errcode                   out varchar2
                                                  ,pni_cliente_header_id      in   number
                                                  ) is 
   
   client_head_info_rec             get_client_head_info%ROWTYPE;
   ln_application_id                                     number; 
ln_resp_id                                              number; 

ls_statement                                           varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
ls_errmsg    varchar2(2000); 
ls_errcod     varchar2(2000); 
 ln_object_version_number      number;
 ls_return_status                                 varchar2(2000) := null; 
  ln_msg_count                                    number := null; 
  ls_msg_data                                     varchar2(2000) := null; 
  
 LT_ORGANIZATION_REC_TYPE             HZ_PARTY_V2PUB.ORGANIZATION_REC_TYPE;
  LT_PARTY_REC_TYPE                          HZ_PARTY_V2PUB.PARTY_REC_TYPE; 
                                                  
 ln_org_party_obj_version_num                 number;                  
ln_org_profile_id                                     number; 
ls_org_return_status                               varchar2(2000); 
ln_org_msg_count                                   number; 
ls_org_msg_data                                     varchar2(2000);     
                                                  
 begin 
  
        if 1 =1 then 
        lt_party_rec_type.party_id                   :=null;   /**  NUMBER,          **/
        lt_party_rec_type.party_number               :=null;   /**  VARCHAR2(30),    **/
        lt_party_rec_type.validated_flag             :=null;   /**  VARCHAR2(1),     **/
        lt_party_rec_type.orig_system_reference      :=null;   /**  VARCHAR2(240),   **/
        lt_party_rec_type.orig_system                :=null;   /**  VARCHAR2(30),    **/
        lt_party_rec_type.status                     :=null;   /**  VARCHAR2(1),     **/
        lt_party_rec_type.category_code              :=null;   /**  VARCHAR2(30),    **/
        lt_party_rec_type.salutation                 :=null;   /**  VARCHAR2(60),    **/
        lt_party_rec_type.attribute_category         :=null;   /**  VARCHAR2(30),    **/
        lt_party_rec_type.attribute1                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute2                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute3                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute4                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute5                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute6                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute7                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute8                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute9                 :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute10                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute11                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute12                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute13                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute14                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute15                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute16                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute17                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute18                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute19                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute20                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute21                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute22                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute23                :=null;   /**  VARCHAR2(150),   **/
        lt_party_rec_type.attribute24                :=null;   /**  VARCHAR2(150)    **/
       end if; 


   if 1 =1 then 
    lt_organization_rec_type.organization_name              := null;   /** VARCHAR2(360),                                      **/
    lt_organization_rec_type.duns_number_c                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.enquiry_duns                   := null;   /** VARCHAR2(15),                                       **/
    lt_organization_rec_type.ceo_name                       := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.ceo_title                      := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.principal_name                 := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.principal_title                := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.legal_status                   := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.control_yr                     := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.employees_total                := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.hq_branch_ind                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.branch_flag                    := null;   /** VARCHAR2(1),                                        **/
    lt_organization_rec_type.oob_ind                        := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.line_of_business               := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.cong_dist_code                 := null;   /** VARCHAR2(2),                                        **/
    lt_organization_rec_type.sic_code                       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.import_ind                     := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.export_ind                     := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.labor_surplus_ind              := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.debarment_ind                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.minority_owned_ind             := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.minority_owned_type            := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.woman_owned_ind                := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.disadv_8a_ind                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.small_bus_ind                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.rent_own_ind                   := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.debarments_count               := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.debarments_date                := null;   /** DATE,                                               **/
    lt_organization_rec_type.failure_score                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_natnl_percentile := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.failure_score_override_code    := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.global_failure_score           := null;   /** VARCHAR2(5),                                        **/
    lt_organization_rec_type.db_rating                      := null;   /** VARCHAR2(5),                                        **/
    lt_organization_rec_type.credit_score                   := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary        := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.paydex_score                   := null;   /** VARCHAR2(3),                                        **/
    lt_organization_rec_type.paydex_three_months_ago        := null;   /** VARCHAR2(3),                                        **/
    lt_organization_rec_type.paydex_norm                    := null;   /** VARCHAR2(3),                                        **/
    lt_organization_rec_type.best_time_contact_begin        := null;   /** DATE,                                               **/
    lt_organization_rec_type.best_time_contact_end          := null;   /** DATE,                                               **/
    lt_organization_rec_type.organization_name_phonetic     := null;   /** VARCHAR2(320),                                      **/
    lt_organization_rec_type.tax_reference                  := null;   /** VARCHAR2(50),                                       **/
    lt_organization_rec_type.gsa_indicator_flag             := null;   /** VARCHAR2(1),                                        **/
    lt_organization_rec_type.jgzz_fiscal_code               := null;   /** VARCHAR2(20),                                       **/
    lt_organization_rec_type.analysis_fy                    := null;   /** VARCHAR2(5),                                        **/
    lt_organization_rec_type.fiscal_yearend_month           := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.curr_fy_potential_revenue      := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.next_fy_potential_revenue      := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.year_established               := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.mission_statement              := null;   /** VARCHAR2(2000),                                     **/
    lt_organization_rec_type.organization_type              := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.business_scope                 := null;   /** VARCHAR2(20),                                       **/
    lt_organization_rec_type.corporation_class              := null;   /** VARCHAR2(60),                                       **/
    lt_organization_rec_type.known_as                       := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.known_as2                      := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.known_as3                      := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.known_as4                      := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.known_as5                      := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.local_bus_iden_type            := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.local_bus_identifier           := null;   /** VARCHAR2(60),                                       **/
    lt_organization_rec_type.pref_functional_currency       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.registration_type              := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.total_employees_text           := null;   /** VARCHAR2(60),                                       **/
    lt_organization_rec_type.total_employees_ind            := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.total_emp_est_ind              := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.total_emp_min_ind              := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.parent_sub_ind                 := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.incorp_year                    := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.sic_code_type                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.public_private_ownership_flag  := null;   /** VARCHAR2(1),                                        **/
    lt_organization_rec_type.internal_flag                  := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.local_activity_code_type       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.local_activity_code            := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.emp_at_primary_adr             := null;   /** VARCHAR2(10),                                       **/
    lt_organization_rec_type.emp_at_primary_adr_text        := null;   /** VARCHAR2(12),                                       **/
    lt_organization_rec_type.emp_at_primary_adr_est_ind     := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.emp_at_primary_adr_min_ind     := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.high_credit                    := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.avg_high_credit                := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.total_payments                 := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.credit_score_class             := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.credit_score_natl_percentile   := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.credit_score_incd_default      := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.credit_score_age               := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.credit_score_date              := null;   /** DATE,                                               **/
    lt_organization_rec_type.credit_score_commentary2       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary3       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary4       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary5       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary6       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary7       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary8       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary9       := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.credit_score_commentary10      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_class            := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.failure_score_incd_default     := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.failure_score_age              := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.failure_score_date             := null;   /** DATE,                                               **/
    lt_organization_rec_type.failure_score_commentary2      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary3      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary4      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary5      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary6      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary7      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary8      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary9      := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.failure_score_commentary10     := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.maximum_credit_recommendation  := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.maximum_credit_currency_code   := null;   /** VARCHAR2(240),                                      **/
    lt_organization_rec_type.displayed_duns_party_id        := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.content_source_type            := null;   /** VARCHAR2(30) := G_MISS_CONTENT_SOURCE_TYPE,         **/
    lt_organization_rec_type.content_source_number          := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.attribute_category             := null;   /** VARCHAR2(30),                                       **/
    lt_organization_rec_type.attribute1                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute2                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute3                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute4                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute5                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute6                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute7                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute8                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute9                     := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute10                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute11                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute12                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute13                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute14                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute15                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute16                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute17                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute18                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute19                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.attribute20                    := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.created_by_module              := null;   /** VARCHAR2(150),                                      **/
    lt_organization_rec_type.application_id                 := null;   /** NUMBER,                                             **/
    lt_organization_rec_type.do_not_confuse_with            := null;   /** VARCHAR2(255),                                      **/
    lt_organization_rec_type.actual_content_source          := null;   /** VARCHAR2(30) := G_SST_SOURCE_TYPE,                  **/
    lt_organization_rec_type.home_country                   := null;   /** VARCHAR2(2),                                        **/
    lt_organization_rec_type.party_rec                      := lt_party_rec_type;   /** PARTY_REC_TYPE:= G_MISS_PARTY_REC                   **/

    end if; 
    
 
  OPEN get_client_head_info(pni_cliente_header_id);
    LOOP
       FETCH get_client_head_info INTO client_head_info_rec;
       EXIT WHEN get_client_head_info%NOTFOUND;
       
        -- 180120211234  lt_organization_rec_type.organization_name := client_head_info_rec.prim_razon_social;
        -- 180120211234  lt_organization_rec_type.known_as  := client_head_info_rec.nombre_cliente;
          lt_organization_rec_type.organization_name := client_head_info_rec.razon_social;  /** Column organization_name must have a value**/
          /** lt_organization_rec_type.known_as := client_head_info_rec.razon_social; 270120211455 Bruno Solo Maneja Razones Sociales **/
         /**  lt_organization_rec_type.created_by_module := 'TCA_V2_API'; **/
            
          lt_party_rec_type.attribute_category := 'QPN'; 
          /* lt_party_rec_type.attribute1 := client_head_info_rec.prim_rfc;  140120211233 **/
          lt_party_rec_type.attribute1 := client_head_info_rec.rfc;
        /*  lt_party_rec_type.attribute2 := pni_cliente_header_id; 270120201 no hacer esto **/
            
          lt_organization_rec_type.party_rec := lt_party_rec_type;
         
     lt_PARTY_REC_TYPE.party_id := client_head_info_rec.party_id; 
   /** lt_PARTY_REC_TYPE.attribute2 := pni_cliente_header_id; 2701202011501 no hacer este mapeo **/
    lt_ORGANIZATION_REC_TYPE.party_rec := lt_PARTY_REC_TYPE;

SELECT object_version_number
     INTO ln_org_party_obj_version_num
     FROM hz_parties
    WHERE party_id =client_head_info_rec.party_id;

   hz_party_v2pub.update_organization( p_init_msg_list                               =>  FND_API.G_TRUE
                                                         ,p_organization_rec                         => lt_ORGANIZATION_REC_TYPE
                                                         ,p_party_object_version_number      => ln_org_party_obj_version_num /** IN OUT NOCOPY **/
                                                         ,x_profile_id                                    => ln_org_profile_id
                                                         ,x_return_status                              => ls_org_return_status
                                                         ,x_msg_count                                  => ln_org_msg_count
                                                         ,x_msg_data                                    =>ls_org_msg_data
                                                        );
                                                        
            
        IF ls_org_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
                                                       
          update  XXQP_PDFT_CLIENTES_HEADER xpch
              set   xpch.attribute1 = 'Update of Organization is Successful '
              where xpch.id =  pni_cliente_header_id; 
             
          commit; 
  
        ELSE
            ROLLBACK;
            
            dbms_output.put_line('ls_org_msg_data:'||ls_org_msg_data);
            
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_org_msg_data := ls_org_msg_data||'*'||oe_msg_pub.get( p_msg_index => i
                                                                                         , p_encoded => 'F');
      
            END LOOP;
            
             update  XXQP_PDFT_CLIENTES_HEADER xpch
              set   xpch.attribute1 = 'Update of Organization failed:'||ls_org_msg_data
            where xpch.id =  pni_cliente_header_id; 
           
            commit; 
          
        END IF;                                              

 END LOOP;
 
 exception when others then 
 pso_errmsg := 'Excepcion al llamar al metodo call_upd_organization.'||sqlerrm; 
 pso_errcode := '2';
 end call_upd_organization; 
                                                                                               
  
 procedure call_upd_account(pso_errmsg                    out varchar2
                                         ,pso_errcode                   out varchar2
                                         ,pni_cliente_header_id      in   number
                                          ) is 
   
   client_head_info_rec             get_client_head_info%ROWTYPE;
   ln_application_id                                     number; 
ln_resp_id                                              number; 

ls_statement                                           varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
ls_errmsg    varchar2(2000); 
ls_errcod     varchar2(2000); 
 ln_object_version_number      number;
 ls_return_status                                 varchar2(2000) := null; 
  ln_msg_count                                    number := null; 
  ls_msg_data                                     varchar2(2000) := null; 
  
 LT_CUST_ACCOUNT_REC_TYPE           HZ_CUST_ACCOUNT_V2PUB.CUST_ACCOUNT_REC_TYPE;
   
  begin 
  
  if 1 =1 then 
  
    lt_cust_account_rec_type.cust_account_id                  :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.account_number                   :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.attribute_category               :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.attribute1                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute2                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute3                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute4                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute5                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute6                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute7                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute8                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute9                       :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute10                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute11                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute12                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute13                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute14                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute15                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute16                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute17                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute18                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute19                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.attribute20                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute_category        :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.global_attribute1                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute2                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute3                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute4                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute5                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute6                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute7                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute8                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute9                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute10               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute11               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute12               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute13               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute14               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute15               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute16               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute17               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute18               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute19               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.global_attribute20               :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.orig_system_reference            :=null;     /**  VARCHAR2(240),      **/
    lt_cust_account_rec_type.orig_system                      :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.status                           :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.customer_type                    :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.customer_class_code              :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.primary_salesrep_id              :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.sales_channel_code               :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.order_type_id                    :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.price_list_id                    :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.tax_code                         :=null;     /**  VARCHAR2(50),       **/
    lt_cust_account_rec_type.fob_point                        :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.freight_term                     :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.ship_partial                     :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.ship_via                         :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.warehouse_id                     :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.tax_header_level_flag            :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.tax_rounding_rule                :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.coterminate_day_month            :=null;     /**  VARCHAR2(6),        **/
    lt_cust_account_rec_type.primary_specialist_id            :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.secondary_specialist_id          :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.account_liable_flag              :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.current_balance                  :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.account_established_date         :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.account_termination_date         :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.account_activation_date          :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.department                       :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.held_bill_expiration_date        :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.hold_bill_flag                   :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.realtime_rate_flag               :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.acct_life_cycle_status           :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.account_name                     :=null;     /**  VARCHAR2(240),      **/
    lt_cust_account_rec_type.deposit_refund_method            :=null;     /**  VARCHAR2(20),       **/
    lt_cust_account_rec_type.dormant_account_flag             :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.npa_number                       :=null;     /**  VARCHAR2(60),       **/
    lt_cust_account_rec_type.suspension_date                  :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.source_code                      :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.comments                         :=null;     /**  VARCHAR2(240),      **/
    lt_cust_account_rec_type.dates_negative_tolerance         :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.dates_positive_tolerance         :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.date_type_preference             :=null;     /**  VARCHAR2(20),       **/
    lt_cust_account_rec_type.over_shipment_tolerance          :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.under_shipment_tolerance         :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.over_return_tolerance            :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.under_return_tolerance           :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.item_cross_ref_pref              :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.ship_sets_include_lines_flag     :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.arrivalsets_include_lines_flag   :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.sched_date_push_flag             :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.invoice_quantity_rule            :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.pricing_event                    :=null;     /**  VARCHAR2(30),       **/
    lt_cust_account_rec_type.status_update_date               :=null;     /**  DATE,               **/
    lt_cust_account_rec_type.autopay_flag                     :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.notify_flag                      :=null;     /**  VARCHAR2(1),        **/
    lt_cust_account_rec_type.last_batch_id                    :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.selling_party_id                 :=null;     /**  NUMBER,             **/
    lt_cust_account_rec_type.created_by_module                :=null;     /**  VARCHAR2(150),      **/
    lt_cust_account_rec_type.application_id                   :=null;     /**  NUMBER              **/

  end if; 
  
  SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';
   
        FND_GLOBAL.APPS_INITIALIZE(user_id               => nvl(fnd_profile.value('USER_ID'),0) -- 1951 --nvl(fnd_profile.value('USER_ID'),0)
                                                     ,resp_id              => ln_resp_id
                                                     ,resp_appl_id      => ln_application_id
                                                    /*   ,security_group_id in number default 0, */
                                                    /*   ,server_id in number default -1   */
                                                     );
                                                     
    mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
      
      
        OPEN get_client_head_info(pni_cliente_header_id);
    LOOP
       FETCH get_client_head_info INTO client_head_info_rec;
       EXIT WHEN get_client_head_info%NOTFOUND;
       
         fnd_msg_pub.DELETE_MSG (NULL); 
         fnd_msg_pub.initialize;   
                  
 
          lt_cust_account_rec_type.cust_account_id :=  to_number(client_head_info_rec.cust_account_id);
         /* lt_cust_account_rec_type.account_name := 'Cuenta'||upper(replace(client_head_info_rec.nombre_cliente,' ','_')); -- client_head_info_rec.prim_razon_social; */
        /*  lt_cust_account_rec_type.account_number :=  TO_CHAR( HZ_ACCOUNT_NUM_S.NEXTVAL ); ** client_head_info_rec.prim_rfc;  Column account_number must have a value. must have unique ** */
          lt_cust_account_rec_type.attribute_category := 'QPN';
          lt_cust_account_rec_type.attribute1 := nvl(client_head_info_rec.prim_rfc,client_head_info_rec.rfc);
          lt_cust_account_rec_type.attribute5 := client_head_info_rec.METODO_DE_PAGO_C;
          lt_cust_account_rec_type.attribute6 := client_head_info_rec.USO_DEL_CFDI_C;
          lt_cust_account_rec_type.orig_system_reference := nvl(client_head_info_rec.prim_rfc,client_head_info_rec.rfc);
         /* lt_cust_account_rec_type._by_module := 'TCA_V2_API'; */
          
          select object_version_number 
            into ln_object_version_number
            from XXQP_HzPuiAccountTableVO
           where cust_account_id = to_number(client_head_info_rec.cust_account_id);
          
          HZ_CUST_ACCOUNT_V2PUB.update_cust_account(p_init_msg_list                          =>  FND_API.G_TRUE
                                                                                  ,p_cust_account_rec                   => lt_cust_account_rec_type
                                                                                  ,p_object_version_number          => ln_object_version_number 
                                                                                  ,x_return_status                         => ls_return_status
                                                                                  ,x_msg_count                            => ln_msg_count
                                                                                  ,x_msg_data                              => ls_msg_data
                                                                                  );
          
          IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
                                                       
          update  XXQP_PDFT_CLIENTES_HEADER xpch
              set   xpch.attribute1 = 'Update of Account is Successful '
              where xpch.id =  pni_cliente_header_id; 
             
          commit; 
  
        ELSE
            ROLLBACK;
            
            dbms_output.put_line('ls_msg_data:'||ls_msg_data);
            
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := ls_msg_data||'*'||oe_msg_pub.get( p_msg_index => i
                                                                                         , p_encoded => 'F');
      
            END LOOP;
            
             update  XXQP_PDFT_CLIENTES_HEADER xpch
              set   xpch.attribute1 = 'Update of Account failed:'||ls_msg_data
            where xpch.id =  pni_cliente_header_id; 
           
            commit; 
          
        END IF;          
        
       END LOOP; 
        
  exception when others then 
 pso_errmsg := 'Excepcion al llamar al metodo call_upd_account.'||sqlerrm; 
 pso_errcode := '2';
 end call_upd_account;                                            
                                          

  procedure call_upd_location_prim(pso_errmsg                    out varchar2
                                                   ,pso_errcode                   out varchar2
                                                   ,pni_cliente_header_id      in   number
                                                     ) is 
  CURSOR get_dir_fiscal_info (cur_cliente_id   number) IS
select    xpcdf.ID                        
            ,xpcdf.HEADER_ID                 
            ,xpcdf.PRIM_RFC                  
            ,xpcdf.PRIM_RAZON_SOCIAL         
            ,xpcdf.PRIM_DIRECCION            
            ,xpcdf.PRIM_ENTRE_CALLES         
            ,xpcdf.PRIM_COLONIA              
            ,xpcdf.PRIM_CIUDAD_O_MPO         
            ,xpcdf.PRIM_ESTADO_C             
            ,xpcdf.PRIM_CODIGO_POSTAL_C      
            ,xpcdf.PRIM_CEDULA               
            ,xpcdf.PRIM_CEDULA_FILE          
            ,xpcdf.CREATED_BY                
            ,xpcdf.CREATION_DATE             
            ,xpcdf.LAST_UPDATED_BY           
            ,xpcdf.LAST_UPDATE_DATE          
            ,xpcdf.LAST_UPDATE_LOGIN         
            ,xpcdf.ATTRIBUTE_CATEGORY        
            ,xpcdf.ATTRIBUTE1                
            ,xpcdf.ATTRIBUTE2    location_id            
            ,xpcdf.ATTRIBUTE3                
            ,xpcdf.ATTRIBUTE4                
            ,xpcdf.ATTRIBUTE5                
            ,xpcdf.PRIM_CEDULA_FILE_NAME     
            ,xpcdf.PRIM_CEDULA_CONTENT_TYPE  
            ,xpcdf.PRIM_NUMERO_EXT 
            ,xpcdf.PRIM_NUMERO_INT 
          from XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
  where xpcdf.header_id =  cur_cliente_id; 
  
dir_fiscal_info_rec         get_dir_fiscal_info%ROWTYPE;

lt_location_rec_type       hz_location_v2pub.LOCATION_REC_TYPE;


ln_location_id      number :=null; 
ls_return_status  varchar2(2000) := null; 
ln_msg_count     number := null; 
ls_msg_data       varchar2(2000) := null; 

ln_application_id   number := null; 
ln_resp_id            number := null; 

ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
ln_object_version_number    number;
                                            
  begin 
   pso_errmsg := null; 
 pso_errcode := '0';
 if 1 =1 then 
 lt_location_rec_type.location_id                   := null; /**  NUMBER,                                           **/
lt_location_rec_type.orig_system_reference         := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.orig_system                         := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.country                       := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.address1                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address2                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address3                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address4                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.city                          := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.postal_code                   := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.state                         := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.province                      := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.county                        := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.address_key                   := null; /**  VARCHAR2(500),                                    **/
lt_location_rec_type.address_style                 := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.validated_flag                := null; /**  VARCHAR2(1),                                      **/
lt_location_rec_type.address_lines_phonetic        := null; /**  VARCHAR2(560),                                    **/
lt_location_rec_type.po_box_number                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.house_number                  := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street_suffix                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street                        := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street_number                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.floor                         := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.suite                         := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.postal_plus4_code             := null; /**  VARCHAR2(10),                                     **/
lt_location_rec_type.position                      := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.location_directions           := null; /**  VARCHAR2(640),                                    **/
lt_location_rec_type.address_effective_date        := null; /**  DATE,                                             **/
lt_location_rec_type.address_expiration_date       := null; /**  DATE,                                             **/
lt_location_rec_type.clli_code                     := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.language                      := null; /**  VARCHAR2(4) ,                                     **/
lt_location_rec_type.short_description             := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.description                   := null; /**  VARCHAR2(2000),                                   **/
lt_location_rec_type.geometry                      := null; /**  mdsys.sdo_geometry := hz_geometry_default,        **/
lt_location_rec_type.geometry_status_code          := null; /**  VARCHAR2(30) := geometry_status_code_default,     **/
lt_location_rec_type.loc_hierarchy_id              := null; /**  NUMBER,                                           **/
lt_location_rec_type.sales_tax_geocode             := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.sales_tax_inside_city_limits  := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.fa_location_id                := null; /**  NUMBER,                                           **/
lt_location_rec_type.content_source_type           := null; /**  VARCHAR2(30) := g_miss_content_source_type,       **/
lt_location_rec_type.attribute_category            := null; /**  VARCHAR2(30) ,                                    **/
lt_location_rec_type.attribute1                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute2                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute3                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute4                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute5                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute6                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute7                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute8                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute9                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute10                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute11                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute12                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute13                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute14                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute15                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute16                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute17                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute18                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute19                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute20                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.timezone_id                   := null; /**  NUMBER,                                           **/
lt_location_rec_type.created_by_module             := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.application_id                := null; /**  NUMBER,                                           **/
lt_location_rec_type.actual_content_source         := null; /**  VARCHAR2(30),                                     **/
-- Bug 2670546                := null; /**                                                    **/
lt_location_rec_type.delivery_point_code           := null; /**  VARCHAR2(50)                                      **/
end if; 

 SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';
   
   
     OPEN get_dir_fiscal_info(pni_cliente_header_id);
   LOOP
      FETCH get_dir_fiscal_info INTO dir_fiscal_info_rec;
      EXIT WHEN get_dir_fiscal_info%NOTFOUND;
      
        FND_GLOBAL.APPS_INITIALIZE(user_id               => dir_fiscal_info_rec.created_by
                                                     ,resp_id              => ln_resp_id
                                                     ,resp_appl_id      => ln_application_id
                                                    /*   ,security_group_id in number default 0, */
                                                    /*   ,server_id in number default -1   */
                                                     );
                                  
     mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
      fnd_msg_pub.DELETE_MSG (NULL); 
     fnd_msg_pub.initialize; 
     
     dbms_output.put_line('Language:'||userenv('LANG')); 
     
     select object_version_number
        into ln_object_version_number
        from hz_locations            
       where location_id = to_number(dir_fiscal_info_rec.location_id);       
     
     lt_location_rec_type.location_id :=  to_number(dir_fiscal_info_rec.location_id);       
     lt_location_rec_type.country := 'MX';
     lt_location_rec_type.address1 := dir_fiscal_info_rec.prim_direccion; 
     lt_location_rec_type.address2 := dir_fiscal_info_rec.prim_numero_ext;
     lt_location_rec_type.address3 := dir_fiscal_info_rec.prim_numero_int;
     lt_location_rec_type.address4 := dir_fiscal_info_rec.PRIM_COLONIA;
     lt_location_rec_type.city := dir_fiscal_info_rec.PRIM_CIUDAD_O_MPO;
     lt_location_rec_type.province :=  dir_fiscal_info_rec.PRIM_CIUDAD_O_MPO;
     lt_location_rec_type.postal_code := dir_fiscal_info_rec.PRIM_CODIGO_POSTAL_C;
     lt_location_rec_type.state := dir_fiscal_info_rec.PRIM_ESTADO_C;
     lt_location_rec_type.created_by_module := 'BO_API';
     
     hz_location_v2pub.update_location(  p_init_msg_list             => FND_API.G_TRUE
                                                       ,p_location_rec                =>  lt_location_rec_type
                                                       ,p_object_version_number => ln_object_version_number
                                                       ,x_return_status            => ls_return_status
                                                       ,x_msg_count                => ln_msg_count 
                                                       ,x_msg_data                 => ls_msg_data 
                                                      );
  
         IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Creation of Location is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_location_id:'||ln_location_id);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
                                                       
          update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute1 = 'Update of Location Prim is Successful '
              where xpcdf.header_id =  pni_cliente_header_id; 
             
          commit; 
  
        ELSE
            DBMS_OUTPUT.put_line ('Creation of Location failed:'||ls_msg_data);
            ROLLBACK;
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;
            
             update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute1 = 'Creation of Location Sec Failed:'||ls_msg_data
            where xpcdf.header_id =  pni_cliente_header_id; 
           
            commit; 
          
        END IF;

      
      
   END LOOP;
   CLOSE get_dir_fiscal_info;
   
 
 exception when others then 
  pso_errmsg := 'Excepcion al llamar call_upd_location_prim:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_upd_location_prim;
 
  procedure call_upd_location_sec(pso_errmsg                    out varchar2
                                                ,pso_errcode                   out varchar2
                                                ,pni_cliente_header_id      in   number
                                                ) is 
  CURSOR get_dir_fiscal_info (cur_cliente_id   number) IS
select    xpcdf.ID                        
            ,xpcdf.HEADER_ID                 
            ,xpcdf.SEC_RFC                   
            ,xpcdf.SEC_RAZON_SOCIAL          
            ,xpcdf.SEC_DIRECCION             
            ,xpcdf.SEC_ENTRE_CALLES          
            ,xpcdf.SEC_COLONIA               
            ,xpcdf.SEC_CIUDAD_O_MPO          
            ,xpcdf.SEC_ESTADO_C              
            ,xpcdf.SEC_CODIGO_POSTAL_C       
            ,xpcdf.SEC_CEDULA                
            ,xpcdf.SEC_CEDULA_FILE           
            ,xpcdf.CREATED_BY                
            ,xpcdf.CREATION_DATE             
            ,xpcdf.LAST_UPDATED_BY           
            ,xpcdf.LAST_UPDATE_DATE          
            ,xpcdf.LAST_UPDATE_LOGIN         
            ,xpcdf.ATTRIBUTE_CATEGORY        
            ,xpcdf.ATTRIBUTE1                
            ,xpcdf.ATTRIBUTE2                
            ,xpcdf.ATTRIBUTE3                
            ,xpcdf.ATTRIBUTE4    location_id              
            ,xpcdf.ATTRIBUTE5                
            ,xpcdf.SEC_CEDULA_FILE_NAME      
            ,xpcdf.SEC_CEDULA_CONTENT_TYPE   
            ,xpcdf.SEC_NUMERO_EXT
            ,xpcdf.SEC_NUMERO_INT
    from XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
  where xpcdf.header_id =  cur_cliente_id; 
  
dir_fiscal_info_rec         get_dir_fiscal_info%ROWTYPE;

lt_location_rec_type       hz_location_v2pub.LOCATION_REC_TYPE;


ln_location_id      number :=null; 
ls_return_status  varchar2(2000) := null; 
ln_msg_count     number := null; 
ls_msg_data       varchar2(2000) := null; 

ln_application_id   number := null; 
ln_resp_id            number := null; 

ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';

ln_object_version_number   number; 
                                            
  begin 
   pso_errmsg := null; 
 pso_errcode := '0';
 if 1 =1 then 
 lt_location_rec_type.location_id                   := null; /**  NUMBER,                                           **/
lt_location_rec_type.orig_system_reference         := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.orig_system                         := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.country                       := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.address1                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address2                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address3                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.address4                      := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.city                          := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.postal_code                   := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.state                         := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.province                      := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.county                        := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.address_key                   := null; /**  VARCHAR2(500),                                    **/
lt_location_rec_type.address_style                 := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.validated_flag                := null; /**  VARCHAR2(1),                                      **/
lt_location_rec_type.address_lines_phonetic        := null; /**  VARCHAR2(560),                                    **/
lt_location_rec_type.po_box_number                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.house_number                  := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street_suffix                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street                        := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.street_number                 := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.floor                         := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.suite                         := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.postal_plus4_code             := null; /**  VARCHAR2(10),                                     **/
lt_location_rec_type.position                      := null; /**  VARCHAR2(50),                                     **/
lt_location_rec_type.location_directions           := null; /**  VARCHAR2(640),                                    **/
lt_location_rec_type.address_effective_date        := null; /**  DATE,                                             **/
lt_location_rec_type.address_expiration_date       := null; /**  DATE,                                             **/
lt_location_rec_type.clli_code                     := null; /**  VARCHAR2(60),                                     **/
lt_location_rec_type.language                      := null; /**  VARCHAR2(4) ,                                     **/
lt_location_rec_type.short_description             := null; /**  VARCHAR2(240),                                    **/
lt_location_rec_type.description                   := null; /**  VARCHAR2(2000),                                   **/
lt_location_rec_type.geometry                      := null; /**  mdsys.sdo_geometry := hz_geometry_default,        **/
lt_location_rec_type.geometry_status_code          := null; /**  VARCHAR2(30) := geometry_status_code_default,     **/
lt_location_rec_type.loc_hierarchy_id              := null; /**  NUMBER,                                           **/
lt_location_rec_type.sales_tax_geocode             := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.sales_tax_inside_city_limits  := null; /**  VARCHAR2(30),                                     **/
lt_location_rec_type.fa_location_id                := null; /**  NUMBER,                                           **/
lt_location_rec_type.content_source_type           := null; /**  VARCHAR2(30) := g_miss_content_source_type,       **/
lt_location_rec_type.attribute_category            := null; /**  VARCHAR2(30) ,                                    **/
lt_location_rec_type.attribute1                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute2                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute3                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute4                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute5                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute6                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute7                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute8                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute9                    := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute10                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute11                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute12                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute13                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute14                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute15                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute16                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute17                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute18                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute19                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.attribute20                   := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.timezone_id                   := null; /**  NUMBER,                                           **/
lt_location_rec_type.created_by_module             := null; /**  VARCHAR2(150),                                    **/
lt_location_rec_type.application_id                := null; /**  NUMBER,                                           **/
lt_location_rec_type.actual_content_source         := null; /**  VARCHAR2(30),                                     **/
-- Bug 2670546                := null; /**                                                    **/
lt_location_rec_type.delivery_point_code           := null; /**  VARCHAR2(50)                                      **/
end if; 

 SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';
   
   
     OPEN get_dir_fiscal_info(pni_cliente_header_id);
   LOOP
      FETCH get_dir_fiscal_info INTO dir_fiscal_info_rec;
      EXIT WHEN get_dir_fiscal_info%NOTFOUND;
      
        FND_GLOBAL.APPS_INITIALIZE(user_id               => dir_fiscal_info_rec.created_by
                                                     ,resp_id              => ln_resp_id
                                                     ,resp_appl_id      => ln_application_id
                                                    /*   ,security_group_id in number default 0, */
                                                    /*   ,server_id in number default -1   */
                                                     );
                                  
     mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
      fnd_msg_pub.DELETE_MSG (NULL); 
     fnd_msg_pub.initialize; 
     
     dbms_output.put_line('Language:'||userenv('LANG')); 
   
   if(    dir_fiscal_info_rec.sec_direccion is not null 
    and  dir_fiscal_info_rec.sec_COLONIA is not null
    and  dir_fiscal_info_rec.sec_CIUDAD_O_MPO is not null
    and dir_fiscal_info_rec.sec_CODIGO_POSTAL_C is not null
    and dir_fiscal_info_rec.sec_ESTADO_C is not null) then
                                
    
     select object_version_number
        into ln_object_version_number
        from hz_locations            
       where location_id = to_number(dir_fiscal_info_rec.location_id);       
     
     lt_location_rec_type.location_id :=  to_number(dir_fiscal_info_rec.location_id);       
     lt_location_rec_type.country := 'MX';
     lt_location_rec_type.address1 := dir_fiscal_info_rec.sec_direccion; 
     lt_location_rec_type.address2 := dir_fiscal_info_rec.sec_numero_ext;
     lt_location_rec_type.address3 := dir_fiscal_info_rec.sec_numero_int;
     lt_location_rec_type.address4 := dir_fiscal_info_rec.sec_COLONIA;
     lt_location_rec_type.city := dir_fiscal_info_rec.sec_CIUDAD_O_MPO;
       lt_location_rec_type.province := dir_fiscal_info_rec.sec_CIUDAD_O_MPO;
     lt_location_rec_type.postal_code := dir_fiscal_info_rec.sec_CODIGO_POSTAL_C;
     lt_location_rec_type.state := dir_fiscal_info_rec.sec_ESTADO_C;
     lt_location_rec_type.created_by_module := 'BO_API';
     
     hz_location_v2pub.update_location(  p_init_msg_list            => FND_API.G_TRUE
                                                       ,p_location_rec             =>  lt_location_rec_type
                                                       ,p_object_version_number  => ln_object_version_number
                                                       ,x_return_status           => ls_return_status
                                                       ,x_msg_count               => ln_msg_count 
                                                       ,x_msg_data                 => ls_msg_data 
                                                      );
  
         IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Update of Location is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_location_id:'||ln_location_id);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
         
           update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute3= 'Update of Location Sec is Successful'
                    ,xpcdf.attribute4 = ln_location_id
              where xpcdf.header_id =  pni_cliente_header_id; 
             
          commit;                                            

        ELSE
            DBMS_OUTPUT.put_line ('Update of Location failed:'||ls_msg_data);
            ROLLBACK;
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;
         
         update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute3 = 'Update of Location Failed:'||ls_msg_data
            where xpcdf.header_id =  pni_cliente_header_id; 
           
            commit; 
            
        END IF;
 
     
      end if; 
      
      
   END LOOP;
   CLOSE get_dir_fiscal_info;
   
 
 exception when others then 
  pso_errmsg := 'Excepcion al llamar call_upd_location_sec:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_upd_location_sec;       

end XXQP_PDFT_CLIENT_FPTO_UPD_PKG;
/


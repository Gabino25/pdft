CREATE OR REPLACE package body xxqp_pdft_clientes_pkg is 

 procedure from_pdft_to_oracle(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_cliente_header_id in number
 ) is 
 ls_errmsg varchar2(2000) := null; 
 ls_errcode varchar2(2000) := '0'; 
 le_from_pdft_to_oracle exception;
 ln_party_id number; 
 ln_cust_account_id number;
 ln_party_id_sec number; 
 ln_cust_account_id_sec number;
 
 ln_location_id_prim number; 
 ln_location_id_sec number;
 ln_party_site_id_prim number;
 ln_party_site_id_sec number;
 
 ln_prim_operating_unit number; 
 ln_sec_operating_unit number; 
 
 
 ls_metodo_de_pago varchar2(2000); 
ls_numero_cuenta varchar2(2000); 
ls_uso_del_cfdi varchar2(2000); 

ln_cust_acct_site_id_prim number; 
ln_cust_acct_site_id_sec number; 
 
 begin 
 pso_errmsg := null; 
 pso_errcode := '0';

 select prim_operating_unit 
 ,sec_operating_unit 
 into ln_prim_operating_unit
 ,ln_sec_operating_unit
 from XXQP_PDFT_CLIENTES_DIR_FISCAL
 where header_id = pni_cliente_header_id; 
 

 
 select METODO_DE_PAGO_C
 ,NUMERO_DE_CUENTA
 ,USO_DEL_CFDI_C
 into ls_metodo_de_pago
 ,ls_numero_cuenta
 ,ls_uso_del_cfdi
 from XXQP_PDFT_CLIENTES_FACT_PAG
 where header_id = pni_cliente_header_id; 
 
 
 insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ls_metodo_de_pago:'||ls_metodo_de_pago); 
 insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ls_numero_cuenta:'||ls_numero_cuenta); 
 insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ls_uso_del_cfdi:'||ls_uso_del_cfdi); 
 commit; 
 
 APPS.xxqp_pdft_clientes_pkg.call_create_location(pso_errmsg => ls_errmsg
 ,pso_errcode => ls_errcode
 ,pni_cliente_header_id => pni_cliente_header_id
 ,pno_location_id => ln_location_id_prim
 ); 
 
 if ls_errmsg is not null then 
 pso_errmsg := 'Ocurrio una excepcion al llamar a call_create_location.'||ls_errmsg;
 pso_errcode := ls_errcode; 
 raise le_from_pdft_to_oracle; 
 end if; 
 
 xxqp_pdft_clientes_pkg.call_create_location_sec(pso_errmsg => ls_errmsg
 ,pso_errcode => ls_errcode
 ,pni_cliente_header_id => pni_cliente_header_id
 ,pno_location_id => ln_location_id_sec
 ); 
 if ls_errmsg is not null then 
 pso_errmsg := 'Ocurrio una excepcion al llamar a call_create_location_sec.'||ls_errmsg;
 pso_errcode := ls_errcode; 
 raise le_from_pdft_to_oracle; 
 end if; 
 
 if ln_location_id_prim is not null then 
 
 APPS.xxqp_pdft_clientes_pkg.call_create_cust_account(pso_errmsg                   => ls_errmsg
                                                                         ,pso_errcode                   => ls_errcode
                                                                         ,pni_cliente_header_id     => pni_cliente_header_id
                                                                         ,pno_party_id                  => ln_party_id 
                                                                         ,pno_cust_account_id          => ln_cust_account_id
                                                                        ); 
     if ls_errmsg is not null then 
      pso_errmsg := 'Ocurrio una excepcion al llamar a call_create_cust_account.'||ls_errmsg;
      pso_errcode := ls_errcode; 
      raise le_from_pdft_to_oracle; 
     end if; 
   

   
      if ln_party_id is not null 
     and  ln_location_id_prim is not null then
   xxqp_pdft_clientes_pkg.call_create_party_site( pso_errmsg                     =>ls_errmsg
                                                                      ,pso_errcode                    =>ls_errcode
                                                                      ,pni_party_id                    => ln_party_id
                                                                      ,pni_location_id_prim        => ln_location_id_prim
                                                                      ,pno_party_site_id            => ln_party_site_id_prim
                                                                      ); 
    
      if ls_errmsg is not null then 
    pso_errmsg := 'Ocurrio una excepcion al llamar a call_create_party_site.'||ls_errmsg;
    pso_errcode := ls_errcode; 
    raise le_from_pdft_to_oracle; 
    end if;    
   end if; 
   
      xxqp_pdft_clientes_pkg.call_attached_documents( pso_errmsg                    => ls_errmsg
                                                                           ,pso_errcode                   => ls_errcode
                                                                           ,pni_cliente_header_id      => pni_cliente_header_id
                                                                           ,pni_cust_account_id        => ln_cust_account_id
                                                                            );
                                          
     if ls_errmsg is not null then 
     pso_errmsg := 'Ocurrio una excepcion al llamar a call_attached_documents.'||ls_errmsg;
     pso_errcode := ls_errcode; 
     raise le_from_pdft_to_oracle; 
    end if;         
   
   
     xxqp_pdft_clientes_pkg.call_create_cust_acct_site(pso_errmsg                   => ls_errmsg
                                                                         ,pso_errcode                   => ls_errcode
                                                                         ,pni_cust_account_id        => ln_cust_account_id
                                                                         ,pni_party_site_id            => ln_party_site_id_prim
                                                                         ,pni_org_id                     => ln_prim_operating_unit
                                                                         ,psi_metodo_de_pago      => ls_metodo_de_pago          
                                                                        ,psi_numero_cuenta         => ls_numero_cuenta                   
                                                                        ,psi_uso_del_cdfi              => ls_uso_del_cfdi       
                                                                        ,pno_cust_acct_site_id      => ln_cust_acct_site_id_prim                 
                                                                         ); 
      
       xxqp_pdft_clientes_pkg.create_account_contact(pso_errmsg                    => ls_errmsg
                                                                     ,pso_errcode                    => ls_errcode
                                                                     ,pni_cliente_header_id      => pni_cliente_header_id
                                                                     ,pni_cust_party_id           => ln_party_id
                                                                     ,pni_cust_account_id       => ln_cust_account_id
                                                                     ,psi_tipo_informacion      =>'PRIM'
                                                                      );
 
      if ls_errmsg is not null then 
         pso_errmsg := 'Ocurrio una excepcion al llamar a create_account_contact.'||ls_errmsg;
         pso_errcode := ls_errcode; 
         raise le_from_pdft_to_oracle; 
        end if;                                                                   
  
   end if; /** END   if ln_location_id_prim is not null then  **/
                                                                         
                                                                         
   /**********************************************************
   Informacion Secundaria 
   ***********************************************************/
     if ln_location_id_sec is not null then 
   
     xxqp_pdft_clientes_pkg.call_create_cust_account_sec(pso_errmsg                    => ls_errmsg
                                                                                ,pso_errcode                   => ls_errcode
                                                                                ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                ,pno_party_id                  => ln_party_id_sec
                                                                                ,pno_cust_account_id       => ln_cust_account_id_sec
                                                                                ); 
   
                                                                             
 

   
    if ln_party_id_sec is not null  
      and ln_location_id_sec is not null then   
    xxqp_pdft_clientes_pkg.call_create_party_site_sec( pso_errmsg                   => ls_errmsg
                                                                             ,pso_errcode                  => ls_errcode
                                                                             ,pni_party_id                  => ln_party_id_sec
                                                                             ,pni_location_id_sec       => ln_location_id_sec
                                                                             ,pno_party_site_id           => ln_party_site_id_sec
                                                                            );
      if ls_errmsg is not null then 
     pso_errmsg := 'Ocurrio una excepcion al llamar a call_create_party_site_sec.'||ls_errmsg;
     pso_errcode := ls_errcode; 
     raise le_from_pdft_to_oracle; 
    end if;            
   end if; 
                                                        
                                                         
 
 xxqp_pdft_clientes_pkg.call_attached_documents_sec( pso_errmsg                    => ls_errmsg
                                                                           ,pso_errcode                   => ls_errcode
                                                                           ,pni_cliente_header_id      => pni_cliente_header_id
                                                                           ,pni_cust_account_id        => ln_cust_account_id_sec
                                                                            );
                                          
     if ls_errmsg is not null then 
     pso_errmsg := 'Ocurrio una excepcion al llamar a call_attached_documents.'||ls_errmsg;
     pso_errcode := ls_errcode; 
     raise le_from_pdft_to_oracle; 
    end if;                                         
    
        
    xxqp_pdft_clientes_pkg.create_account_contact(pso_errmsg                    => ls_errmsg
                                                                     ,pso_errcode                    => ls_errcode
                                                                     ,pni_cliente_header_id      => pni_cliente_header_id
                                                                     ,pni_cust_party_id           => ln_party_id_sec
                                                                     ,pni_cust_account_id       => ln_cust_account_id_sec
                                                                     ,psi_tipo_informacion      => 'SEC'
                                                                      );
 
  if ls_errmsg is not null then 
     pso_errmsg := 'Ocurrio una excepcion al llamar a create_account_contact.'||ls_errmsg;
     pso_errcode := ls_errcode; 
     raise le_from_pdft_to_oracle; 
    end if;
    
  
   if ln_sec_operating_unit is not null and ln_party_site_id_sec is not null then  
    xxqp_pdft_clientes_pkg.call_create_cust_acct_site(pso_errmsg                 => ls_errmsg
                                                                         ,pso_errcode                   => ls_errcode
                                                                         ,pni_cust_account_id        => ln_cust_account_id_sec
                                                                         ,pni_party_site_id            => ln_party_site_id_sec
                                                                         ,pni_org_id                      => ln_sec_operating_unit
                                                                         ,psi_metodo_de_pago      => ls_metodo_de_pago          
                                                                        ,psi_numero_cuenta         => ls_numero_cuenta                   
                                                                        ,psi_uso_del_cdfi              => ls_uso_del_cfdi        
                                                                        ,pno_cust_acct_site_id      => ln_cust_acct_site_id_sec    
                                                                         );                                                       
    
                                                                                          
   end if; 
   
   end if; /**  if ln_location_id_sec is not null then  **/
   
  
 exception when le_from_pdft_to_oracle then 
 pso_errmsg := pso_errmsg; 
 pso_errcode := pso_errcode; 
 rollback; 
 insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'le_from_pdft_to_oracle:'||pso_errmsg);    
 commit; 
  when others then 
  pso_errmsg := 'Excepcion al llamar from_pdft_to_oracle:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  rollback;
  insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'others:'||pso_errmsg);    
  commit; 
 
 end from_pdft_to_oracle; 
 
 procedure call_create_cust_account(pso_errmsg                    out varchar2
                                                     ,pso_errcode                   out varchar2
                                                     ,pni_cliente_header_id      in   number
                                                     ,pno_party_id                  out number 
                                                      ,pno_cust_account_id       out number
                                                    ) is 
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
                  ,xpch.ATTRIBUTE2            
                  ,xpch.ATTRIBUTE3            
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
     
   client_head_info_rec             get_client_head_info%ROWTYPE;
   
   
   
  LT_CUST_ACCOUNT_REC_TYPE           HZ_CUST_ACCOUNT_V2PUB.CUST_ACCOUNT_REC_TYPE;
  LT_ORGANIZATION_REC_TYPE             HZ_PARTY_V2PUB.ORGANIZATION_REC_TYPE;
  LT_PARTY_REC_TYPE                          HZ_PARTY_V2PUB.PARTY_REC_TYPE; 
  LT_CUSTOMER_PROFILE_REC_TYPE     HZ_CUSTOMER_PROFILE_V2PUB.CUSTOMER_PROFILE_REC_TYPE;
  
  ln_cust_acccount_id                             number := null; 
  ls_account_number                              varchar2(2000) := null; 
  ln_party_id                                         number := null; 
  ls_party_number                                varchar2(2000) := null; 
  ln_profile_id                                      number := null; 
  ls_return_status                                 varchar2(2000) := null; 
  ln_msg_count                                    number := null; 
  ls_msg_data                                     varchar2(2000) := null; 
 
 ln_application_id   number;
ln_resp_id            number;

ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';         
lb_fnd_profile_save   boolean;                             
                      
 begin 
  pso_errmsg := null; 
 pso_errcode := '0';
 
 /**
  for idx in (SELECT DISTINCT PROFILE_OPTION_NAME
     FROM fnd_profile_options_tl
    WHERE user_profile_option_name in ('HZ: Generate Party Number','HZ: Generate Party Site Number')
    ) LOOP
    
    lb_fnd_profile_save := FND_PROFILE.SAVE( X_NAME            => idx.PROFILE_OPTION_NAME
                                                                  , X_VALUE           => 'Y'    -- Change here
                                                                  , X_LEVEL_NAME  => 'USER'
                                                                  , X_LEVEL_VALUE => nvl(fnd_global.user_id,fnd_profile.value('USER_ID'))
                                                                  ); 
                  
   END LOOP;            
   **/
 
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

   if 1 =1 then 
    lt_customer_profile_rec_type.cust_account_profile_id            :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.cust_account_id                    :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.status                             :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.collector_id                       :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_analyst_id                  :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_checking                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.next_credit_review_date            :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.tolerance                          :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.discount_terms                     :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.dunning_letters                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.interest_charges                   :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.send_statements                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.credit_balance_statements          :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.credit_hold                        :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.profile_class_id                   :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.site_use_id                        :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_rating                      :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.risk_code                          :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.standard_terms                     :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.override_terms                     :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.dunning_letter_set_id              :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.interest_period_days               :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.payment_grace_days                 :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.discount_grace_days                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.statement_cycle_id                 :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.account_status                     :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.percent_collectable                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.autocash_hierarchy_id              :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.attribute_category                 :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.attribute1                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute2                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute3                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute4                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute5                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute6                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute7                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute8                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute9                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute10                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute11                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute12                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute13                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute14                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute15                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.auto_rec_incl_disputed_flag        :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.tax_printing_option                :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.charge_on_finance_charge_flag      :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.grouping_rule_id                   :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.clearing_days                      :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.jgzz_attribute_category            :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.jgzz_attribute1                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute2                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute3                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute4                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute5                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute6                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute7                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute8                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute9                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute10                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute11                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute12                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute13                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute14                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute15                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute1                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute2                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute3                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute4                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute5                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute6                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute7                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute8                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute9                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute10                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute11                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute12                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute13                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute14                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute15                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute16                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute17                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute18                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute19                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute20                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute_category          :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.cons_inv_flag                      :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.cons_inv_type                      :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.autocash_hierarchy_id_for_adr      :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.lockbox_matching_option            :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.created_by_module                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.application_id                     :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.review_cycle                       :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.last_credit_review_date            :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.party_id                           :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_classification              :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.cons_bill_level                    :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.late_charge_calculation_trx        :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.credit_items_flag                  :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.disputed_transactions_flag         :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.late_charge_type                   :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.late_charge_term_id                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.interest_calculation_period        :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.hold_charged_invoices_flag         :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.message_text_id                    :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.multiple_interest_rates_flag       :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.charge_begin_date                  :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.automatch_set_id                   :=null;    /** NUMBER             **/
   end if; 
   
 
 
  OPEN get_client_head_info(pni_cliente_header_id);
    LOOP
       FETCH get_client_head_info INTO client_head_info_rec;
       EXIT WHEN get_client_head_info%NOTFOUND;
       
       if 1 =1 then 
          fnd_msg_pub.DELETE_MSG (NULL); 
          fnd_msg_pub.initialize;   
          
           execute immediate ls_statement;
          lt_cust_account_rec_type.account_name := 'Cuenta'||upper(replace(client_head_info_rec.nombre_cliente,' ','_')); -- client_head_info_rec.prim_razon_social;
          lt_cust_account_rec_type.account_number :=  TO_CHAR( HZ_ACCOUNT_NUM_S.NEXTVAL ); /** client_head_info_rec.prim_rfc;  Column account_number must have a value. must have unique **/
          lt_cust_account_rec_type.attribute_category := 'QPN';
          lt_cust_account_rec_type.attribute1 := nvl(client_head_info_rec.prim_rfc,client_head_info_rec.rfc);
          lt_cust_account_rec_type.attribute5 := client_head_info_rec.METODO_DE_PAGO_C;
          lt_cust_account_rec_type.attribute6 := client_head_info_rec.USO_DEL_CFDI_C;
          lt_cust_account_rec_type.orig_system_reference := nvl(client_head_info_rec.prim_rfc,client_head_info_rec.rfc);
          lt_cust_account_rec_type.created_by_module := 'TCA_V2_API';
           
        -- 180120211234  lt_organization_rec_type.organization_name := client_head_info_rec.prim_razon_social;
        -- 180120211234  lt_organization_rec_type.known_as  := client_head_info_rec.nombre_cliente;
          lt_organization_rec_type.organization_name := client_head_info_rec.nombre_cliente;  /** Column organization_name must have a value**/
           lt_organization_rec_type.known_as := client_head_info_rec.razon_social;
          lt_organization_rec_type.created_by_module := 'TCA_V2_API';
            
          lt_party_rec_type.attribute_category := 'QPN'; 
          /* lt_party_rec_type.attribute1 := client_head_info_rec.prim_rfc;  140120211233 **/
          lt_party_rec_type.attribute1 := client_head_info_rec.rfc;
          lt_party_rec_type.attribute2 := pni_cliente_header_id;
            
          lt_organization_rec_type.party_rec := lt_party_rec_type;
            
           
          HZ_CUST_ACCOUNT_V2PUB.create_cust_account ( p_init_msg_list                        =>  FND_API.G_TRUE
                                                                                   ,p_cust_account_rec                 =>  LT_CUST_ACCOUNT_REC_TYPE
                                                                                   ,p_organization_rec                  =>  LT_ORGANIZATION_REC_TYPE
                                                                                   ,p_customer_profile_rec            =>  LT_CUSTOMER_PROFILE_REC_TYPE
                                                                                   ,p_create_profile_amt               =>  FND_API.G_FALSE
                                                                                   ,x_cust_account_id                    => ln_cust_acccount_id
                                                                                   ,x_account_number                   => ls_account_number
                                                                                   ,x_party_id                               => ln_party_id
                                                                                   ,x_party_number                      => ls_party_number
                                                                                   ,x_profile_id                            => ln_profile_id
                                                                                   ,x_return_status                       => ls_return_status
                                                                                   ,x_msg_count                          => ln_msg_count
                                                                                   ,x_msg_data                           => ls_msg_data
                                                                                 );
         
           IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Creation of Cust Account is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_cust_acccount_id:'||ln_cust_acccount_id);  
           dbms_output.put_line('ls_account_number:'||ls_account_number);  
           dbms_output.put_line('ln_party_id:'||ln_party_id);  
           dbms_output.put_line('ls_party_number:'||ls_party_number);  
           dbms_output.put_line('ln_profile_id:'||ln_profile_id);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
         pno_party_id := ln_party_id; 
         pno_cust_account_id := ln_cust_acccount_id;
         
          update XXQP_PDFT_CLIENTES_HEADER
              set  attribute1 = 'Creation of Cust Account is Successful '
                   ,attribute2 = ln_party_id
                   ,attribute3 = ln_cust_acccount_id
                   ,party_id = ln_party_id
             where id = pni_cliente_header_id;
           commit; 
                                                        

        ELSE
            DBMS_OUTPUT.put_line ('Creation of Cust Account failed:'||ls_msg_data);
            ROLLBACK;
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := ls_msg_data||oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;
             update XXQP_PDFT_CLIENTES_HEADER
              set  attribute1 = 'Creation of Cust Account Failed:'||ls_msg_data 
               where id = pni_cliente_header_id;
           commit; 
           
           pso_errmsg := 'Excepcion al llamar HZ_CUST_ACCOUNT_V2PUB.create_cust_account Parametros ClienteHeaderId:'||pni_cliente_header_id;
           pso_errcode := '2';
        END IF;
        
         end if; 
       
     END LOOP;
 CLOSE get_client_head_info;
 
 
 exception when others then 
 pso_errmsg := 'Excepcion al llamar call_create_cust_account:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
 end call_create_cust_account; 
 
                                              
 
  procedure call_create_location(pso_errmsg                    out varchar2
                                             ,pso_errcode                   out varchar2
                                             ,pni_cliente_header_id      in   number
                                             ,pno_location_id               out number
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
            ,xpcdf.ATTRIBUTE4                
            ,xpcdf.ATTRIBUTE5                
            ,xpcdf.PRIM_CEDULA_FILE_NAME     
            ,xpcdf.PRIM_CEDULA_CONTENT_TYPE  
            ,xpcdf.SEC_CEDULA_FILE_NAME      
            ,xpcdf.SEC_CEDULA_CONTENT_TYPE   
            ,xpcdf.PRIM_NUMERO_EXT 
            ,xpcdf.PRIM_NUMERO_INT 
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
     
     hz_location_v2pub.create_location(  p_init_msg_list            => FND_API.G_TRUE
                                                       ,p_location_rec             =>  lt_location_rec_type
                                                       ,x_location_id               => ln_location_id
                                                       ,x_return_status           => ls_return_status
                                                       ,x_msg_count               => ln_msg_count 
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
         
         pno_location_id :=  ln_location_id;
                                                       
          update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute1 = 'Creation of Location Prim is Successful '
                    ,xpcdf.attribute2 = ln_location_id
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
  pso_errmsg := 'Excepcion al llamar call_create_location:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_create_location;
 
  procedure call_create_location_sec(pso_errmsg                    out varchar2
                                             ,pso_errcode                   out varchar2
                                             ,pni_cliente_header_id      in   number
                                             ,pno_location_id               out number
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
            ,xpcdf.ATTRIBUTE4                
            ,xpcdf.ATTRIBUTE5                
            ,xpcdf.PRIM_CEDULA_FILE_NAME     
            ,xpcdf.PRIM_CEDULA_CONTENT_TYPE  
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
     
     hz_location_v2pub.create_location(  p_init_msg_list            => FND_API.G_TRUE
                                                       ,p_location_rec             =>  lt_location_rec_type
                                                       ,x_location_id               => ln_location_id
                                                       ,x_return_status           => ls_return_status
                                                       ,x_msg_count               => ln_msg_count 
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
         
         pno_location_id  := ln_location_id;
         
           update  XXQP_PDFT_CLIENTES_DIR_FISCAL xpcdf
              set   xpcdf.attribute3= 'Creation of Location Sec is Successful'
                    ,xpcdf.attribute4 = ln_location_id
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
              set   xpcdf.attribute3 = 'Creation of Location Failed:'||ls_msg_data
            where xpcdf.header_id =  pni_cliente_header_id; 
           
            commit; 
            
        END IF;
 
     
      end if; 
      
      
   END LOOP;
   CLOSE get_dir_fiscal_info;
   
 
 exception when others then 
  pso_errmsg := 'Excepcion al llamar call_create_location:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_create_location_sec;        
  
   procedure call_create_party_site( pso_errmsg                    out varchar2
                                                   ,pso_errcode                   out varchar2
                                                   ,pni_party_id                    in  number 
                                                   ,pni_location_id_prim        in number
                                                   ,pno_party_site_id            out number
                                                   ) is 
  lt_party_site_rec_type                 hz_party_site_v2pub.PARTY_SITE_REC_TYPE;


 ln_party_site_id            number := null; 
 ls_party_site_number   varchar2(2000) := null; 
 ls_return_status           varchar2(2000) := null; 
 ln_msg_count              number := null; 
 ls_msg_data                varchar2(2000) := null; 

 ln_application_id   number := null; 
 ln_resp_id            number := null; 

 ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
 
   begin
    pso_errmsg := null; 
 pso_errcode := '0'; 
 
  if 1 =1 then 
 lt_party_site_rec_type.party_site_id                    := null; /** NUMBER,         **/
lt_party_site_rec_type.party_id                         := null; /** NUMBER,         **/
lt_party_site_rec_type.location_id                      := null; /** NUMBER,         **/
lt_party_site_rec_type.party_site_number                := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.orig_system_reference            := null; /** VARCHAR2(240),  **/
lt_party_site_rec_type.orig_system                      := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.mailstop                         := null; /** VARCHAR2(60),   **/
lt_party_site_rec_type.identifying_address_flag         := null; /** VARCHAR2(1),    **/
lt_party_site_rec_type.status                           := null; /** VARCHAR2(1),    **/
lt_party_site_rec_type.party_site_name                  := null; /** VARCHAR2(240),  **/
lt_party_site_rec_type.attribute_category               := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.attribute1                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute2                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute3                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute4                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute5                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute6                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute7                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute8                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute9                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute10                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute11                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute12                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute13                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute14                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute15                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute16                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute17                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute18                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute19                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute20                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.language                         := null; /** VARCHAR2(4),    **/
lt_party_site_rec_type.addressee                        := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.created_by_module                := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.application_id                   := null; /** NUMBER,         **/
lt_party_site_rec_type.global_location_number           := null; /** VARCHAR2(40),   **/
lt_party_site_rec_type.duns_number_c                    := null; /** VARCHAR2(30)    **/
 end if; 

 SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';

  FND_GLOBAL.APPS_INITIALIZE(user_id           =>  nvl(fnd_profile.value('USER_ID'),0)
                                 ,resp_id           => ln_resp_id
                                 ,resp_appl_id      => ln_application_id
                              /*   ,security_group_id in number default 0, */
                              /*   ,server_id in number default -1   */
                                  );
                                  
                                  
    mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
      fnd_msg_pub.DELETE_MSG (NULL); 
     fnd_msg_pub.initialize;                                 

lt_party_site_rec_type.party_id      := pni_party_id;
lt_party_site_rec_type.location_id   := pni_location_id_prim;
 lt_party_site_rec_type.identifying_address_flag := 'Y'; 
lt_party_site_rec_type.created_by_module        := 'BO_API';

 hz_party_site_v2pub.create_party_site ( p_init_msg_list                  =>  FND_API.G_FALSE
                                                           ,p_party_site_rec                => lt_party_site_rec_type
                                                           ,x_party_site_id                  => ln_party_site_id
                                                           ,x_party_site_number         =>  ls_party_site_number
                                                           ,x_return_status                 => ls_return_status
                                                           ,x_msg_count                    => ln_msg_count
                                                           ,x_msg_data                      => ls_msg_data
                                                        ); 
  
     IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Creation of Party Site is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_party_site_id:'||ln_party_site_id);  
            dbms_output.put_line('ls_party_site_number:'||ls_party_site_number);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
         insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'Creation of Party Site is Successful ');
        insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'ln_party_site_id:'||ln_party_site_id);  
        insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'ls_party_site_number:'||ls_party_site_number);  
        insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'ls_return_status:'||ls_return_status);  
        insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'ln_msg_count:'||ln_msg_count);  
       insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'ls_msg_data:'||ls_msg_data);  
         commit; 
         
         pno_party_site_id :=  ln_party_site_id;
                                                       

        ELSE
            DBMS_OUTPUT.put_line ('Creation of Party Site failed:'||ls_msg_data);
            ROLLBACK;
             insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'Creation of Party Site failed:'||ls_msg_data); 
             commit; 
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;

          

        END IF;
        
    exception when others then 
  pso_errmsg := 'Excepcion al llamar call_create_party_site:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_create_party_site;       
    
    procedure call_create_party_site_sec( pso_errmsg                    out varchar2
                                                           ,pso_errcode                   out varchar2
                                                           ,pni_party_id                    in  number 
                                                           ,pni_location_id_sec        in number
                                                           ,pno_party_site_id            out number
                                                           ) is 
   lt_party_site_rec_type                 hz_party_site_v2pub.PARTY_SITE_REC_TYPE;


 ln_party_site_id            number := null; 
 ls_party_site_number   varchar2(2000) := null; 
 ls_return_status           varchar2(2000) := null; 
 ln_msg_count              number := null; 
 ls_msg_data                varchar2(2000) := null; 

 ln_application_id   number := null; 
 ln_resp_id            number := null; 

 ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
 
   begin                                                         
      pso_errmsg := null; 
 pso_errcode := '0'; 
 
  if 1 =1 then 
 lt_party_site_rec_type.party_site_id                    := null; /** NUMBER,         **/
lt_party_site_rec_type.party_id                         := null; /** NUMBER,         **/
lt_party_site_rec_type.location_id                      := null; /** NUMBER,         **/
lt_party_site_rec_type.party_site_number                := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.orig_system_reference            := null; /** VARCHAR2(240),  **/
lt_party_site_rec_type.orig_system                      := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.mailstop                         := null; /** VARCHAR2(60),   **/
lt_party_site_rec_type.identifying_address_flag         := null; /** VARCHAR2(1),    **/
lt_party_site_rec_type.status                           := null; /** VARCHAR2(1),    **/
lt_party_site_rec_type.party_site_name                  := null; /** VARCHAR2(240),  **/
lt_party_site_rec_type.attribute_category               := null; /** VARCHAR2(30),   **/
lt_party_site_rec_type.attribute1                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute2                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute3                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute4                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute5                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute6                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute7                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute8                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute9                       := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute10                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute11                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute12                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute13                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute14                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute15                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute16                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute17                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute18                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute19                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.attribute20                      := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.language                         := null; /** VARCHAR2(4),    **/
lt_party_site_rec_type.addressee                        := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.created_by_module                := null; /** VARCHAR2(150),  **/
lt_party_site_rec_type.application_id                   := null; /** NUMBER,         **/
lt_party_site_rec_type.global_location_number           := null; /** VARCHAR2(40),   **/
lt_party_site_rec_type.duns_number_c                    := null; /** VARCHAR2(30)    **/
 end if; 

 SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';

  FND_GLOBAL.APPS_INITIALIZE(user_id           =>  3519
                                 ,resp_id           => ln_resp_id
                                 ,resp_appl_id      => ln_application_id
                              /*   ,security_group_id in number default 0, */
                              /*   ,server_id in number default -1   */
                                  );
                                  
                                  
    mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
      fnd_msg_pub.DELETE_MSG (NULL); 
     fnd_msg_pub.initialize;                                 

lt_party_site_rec_type.party_id      := pni_party_id;
lt_party_site_rec_type.location_id   := pni_location_id_sec;
/** lt_party_site_rec_type.identifying_address_flag := 'Y'; **/
lt_party_site_rec_type.created_by_module        := 'BO_API';

 hz_party_site_v2pub.create_party_site ( p_init_msg_list                  =>  FND_API.G_FALSE
                                                           ,p_party_site_rec                => lt_party_site_rec_type
                                                           ,x_party_site_id                  => ln_party_site_id
                                                           ,x_party_site_number         =>  ls_party_site_number
                                                           ,x_return_status                 => ls_return_status
                                                           ,x_msg_count                    => ln_msg_count
                                                           ,x_msg_data                      => ls_msg_data
                                                        ); 
  
     IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Creation of Party Site is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_party_site_id:'||ln_party_site_id);  
            dbms_output.put_line('ls_party_site_number:'||ls_party_site_number);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
                   
         pno_party_site_id :=  ln_party_site_id;                                     

        ELSE
            DBMS_OUTPUT.put_line ('Creation of Party Site failed:'||ls_msg_data);
            ROLLBACK;
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;

          

        END IF;
        
    exception when others then 
  pso_errmsg := 'Excepcion al llamar call_create_party_site_sec:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
  end call_create_party_site_sec;                                                             
                                                       
   procedure call_attached_documents( pso_errmsg                    out varchar2
                                                        ,pso_errcode                   out varchar2
                                                         ,pni_cliente_header_id      in   number
                                                        ,pni_cust_account_id        in  number
                                                          ) is 
    CURSOR get_dir_fiscal_info(cur_cliente_header_id   number) IS
      select  prim_cedula_file
          ,prim_cedula_file_name
          ,prim_cedula_content_type 
          ,sec_cedula_file
          ,sec_cedula_file_name
          ,sec_cedula_content_type 
   from XXQP_PDFT_CLIENTES_DIR_FISCAL
 where header_id = cur_cliente_header_id
  order by id desc; 
     dir_fiscal_info_rec        get_dir_fiscal_info%ROWTYPE;
     
ls_errmg            varchar2(2000); 
ls_errcode          varchar2(2000);

ln_media_id       number;

lr_rowid                     rowid;
ln_document_id          number; 
ln_short_datatype_id   number;
ln_category_id            number;

ls_description fnd_documents_tl.description%type:= 'Customer Invoice Attachment';

ln_attached_document_id    number;
ls_entity_name                   varchar2(2000) := 'AR_CUSTOMERS';
ls_pk1_value                       varchar2(2000) := null;  
ln_seq_num                       number;
begin 


ls_pk1_value := pni_cust_account_id;

  OPEN get_dir_fiscal_info(pni_cliente_header_id);
     LOOP
        FETCH get_dir_fiscal_info INTO dir_fiscal_info_rec;
        EXIT WHEN get_dir_fiscal_info%NOTFOUND;
        
       if 1 =1   then  
        SELECT MAX (file_id) + 1
           INTO ln_media_id
         FROM fnd_lobs;
      
        INSERT INTO fnd_lobs  (FILE_ID            
                                        ,FILE_NAME          
                                        ,FILE_CONTENT_TYPE  
                                        ,FILE_DATA          
                                        ,UPLOAD_DATE        
                                        ,EXPIRATION_DATE    
                                        ,PROGRAM_NAME       
                                        ,PROGRAM_TAG        
                                        ,LANGUAGE           
                                        ,ORACLE_CHARSET     
                                        ,FILE_FORMAT        
                                         )
                            VALUES (ln_media_id
                                        ,dir_fiscal_info_rec.prim_cedula_file_name
                                        ,dir_fiscal_info_rec.prim_cedula_content_type
                                        ,dir_fiscal_info_rec.prim_cedula_file
                                        ,SYSDATE
                                        ,NULL
                                        ,'FNDATTCH'
                                        ,NULL
                                        ,'ESA'
                                        ,'UTF8'
                                        , 'binary'
                                        );
         commit;
         
       
       SELECT fnd_documents_s.NEXTVAL
                  ,apps.fnd_attached_documents_s.NEXTVAL
          INTO ln_document_id
                 ,ln_attached_document_id
        FROM DUAL;
        
        SELECT DATATYPE_ID
           INTO ln_short_datatype_id
          FROM apps.fnd_document_datatypes
        WHERE NAME = 'FILE'
             and language = 'ESA';
             
        SELECT category_id
           INTO ln_category_id
          FROM apps.fnd_document_categories_vl
        WHERE USER_NAME = 'Miscellaneous';
              
      
           fnd_documents_pkg.insert_row(X_Rowid                       => lr_rowid                  /**  IN OUT NOCOPY VARCHAR2,  **/              
                                                        ,X_document_id              => ln_document_id               /**  IN OUT NOCOPY NUMBER,    **/
                                                        ,X_creation_date             => SYSDATE               /**  DATE,                    **/
                                                        ,X_created_by                 => nvl(fnd_profile.value('USER_ID'),0)              /**  NUMBER,                  **/
                                                        ,X_last_update_date        => SYSDATE                 /**  DATE,                    **/
                                                        ,X_last_updated_by         => nvl(fnd_profile.value('USER_ID'),0)                   /**  NUMBER,                  **/
                                                        ,X_last_update_login       => nvl(fnd_profile.value('LOGIN_ID'),0)                   /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_datatype_id                => ln_short_datatype_id              /**  NUMBER,                  **/
                                                        ,X_category_id                => ln_category_id              /**  NUMBER,                  **/
                                                        ,X_security_type              => 1              /**  NUMBER,                  **/
                                                        ,X_security_id                  => fnd_profile.value('ORG_ID')            /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_publish_flag                => 'Y'             /**  VARCHAR2,                **/
                                                        ,X_image_type                 => null               /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_storage_type              => null                /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_usage_type                => 'S'                /**  VARCHAR2,                **/
                                                        ,X_start_date_active         => null               /**  DATE DEFAULT NULL,       **/
                                                        ,X_end_date_active          => null               /**  DATE DEFAULT NULL,       **/
                                                        ,X_request_id                  => null             /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_application_id   => null                /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_id                    => null            /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_update_date     => null                 /**  DATE DEFAULT NULL,       **/
                                                        ,X_language                        => 'ESA'          /**  VARCHAR2,                **/
                                                        ,X_description                     => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_file_name                       => dir_fiscal_info_rec.prim_cedula_file_name         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_media_id                        =>ln_media_id       /**  IN OUT NOCOPY NUMBER,    **/
                                                        ,X_Attribute_Category         => null              /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute1                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute2                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute3                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute4                      => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute5                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute6                      => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute7                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute8                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute9                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute10                    => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute11                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute12                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute13                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute14                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute15                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_create_doc                    => null           /**  VARCHAR2 DEFAULT 'N',    **/
                                                        ,X_url                                => null      /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_title                              => null      /**  VARCHAR2 DEFAULT NULL    **/
                                                        );
         
        dbms_output.put_line('lr_rowid:'||lr_rowid); 
         commit;
         
         fnd_documents_pkg.insert_tl_row (x_document_id => ln_document_id,
                                                         x_creation_date => SYSDATE,
                                                         x_created_by => nvl(fnd_profile.value('USER_ID'),0)    ,
                                                         x_last_update_date => SYSDATE,
                                                         x_last_updated_by => nvl(fnd_profile.value('USER_ID'),0)    ,
                                                         x_last_update_login => nvl(fnd_profile.VALUE('LOGIN_ID'),0),
                                                        x_language => 'US',
                                                        x_description => ls_description
                                                         );
                                                         commit;
          
         SELECT NVL (MAX (seq_num), 0) + 10
            INTO ln_seq_num
           FROM fnd_attached_documents
         WHERE pk1_value = ls_pk1_value 
              AND entity_name = ls_entity_name;
          
          dbms_output.put_line('ln_seq_num:'||ln_seq_num);                                                       
           
         fnd_attached_documents_pkg.Insert_Row(X_Rowid                             => lr_rowid  /** IN OUT NOCOPY VARCHAR2,  **/      
                                                                                   ,X_attached_document_id             => ln_attached_document_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_document_id                      => ln_document_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_creation_date                    => sysdate  /** DATE,                    **/
                                                                                   ,X_created_by                       => nvl(fnd_profile.value('USER_ID'),0)   /** NUMBER,                  **/
                                                                                   ,X_last_update_date                 => sysdate  /** DATE,                    **/
                                                                                   ,X_last_updated_by                  => nvl(fnd_profile.value('USER_ID'),0)   /** NUMBER,                  **/
                                                                                   ,X_last_update_login                => nvl(fnd_profile.VALUE('LOGIN_ID'),0)  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_seq_num                          => ln_seq_num  /** NUMBER,                  **/
                                                                                   ,X_entity_name                      => ls_entity_name  /** VARCHAR2,                **/
                                                                                   ,X_column1                          => null  /** VARCHAR2,                **/
                                                                                   ,X_pk1_value                        => ls_pk1_value  /** VARCHAR2,                **/
                                                                                   ,X_pk2_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk3_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk4_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk5_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_automatically_added_flag         => 'N'  /** VARCHAR2,                **/
                                                                                   ,X_request_id                       => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_application_id           => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_id                       => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_update_date              => null  /** DATE DEFAULT NULL,       **/
                                                                                   ,X_Attribute_Category               => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute1                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute2                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute3                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute4                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute5                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute6                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute7                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute8                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute9                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute10                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute11                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute12                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute13                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute14                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute15                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   /*  columns necessary for creating a document on the fly */
                                                                                   ,X_datatype_id                      => ln_short_datatype_id  /** NUMBER,                  **/ 
                                                                                   ,X_category_id                      => ln_category_id  /** NUMBER,                  **/
                                                                                   ,X_security_type                    => 1  /** NUMBER,                  **/
                                                                                   ,X_security_id                      =>  fnd_profile.value('ORG_ID')   /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_publish_flag                     => 'Y'  /** VARCHAR2,                **/
                                                                                   ,X_image_type                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_storage_type                     => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_usage_type                       => 'S'  /** VARCHAR2,                **/
                                                                                   ,X_language                         => 'ESA'  /** VARCHAR2,                **/
                                                                                   ,X_description                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_file_name                        => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_media_id                         => ln_media_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_doc_attribute_Category           => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute1                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute2                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute3                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute4                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute5                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute6                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute7                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute8                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute9                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute10                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute11                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute12                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute13                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute14                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute15                  => null  /** VARCHAR2,                **/
                                                                                   ,X_create_doc                       => null  /** VARCHAR2 DEFAULT 'N',    **/
                                                                                   ,X_url                              => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_title                                   => null  /** VARCHAR2 DEFAULT NULL    **/
                                                                                   );        
            commit;                                                                                                             
         
      end if;      
        
     END LOOP;
     CLOSE get_dir_fiscal_info;
   exception when others then 
    pso_errmsg := 'Excepcion al llamar call_attached_documents:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
   end call_attached_documents;       
   
    procedure call_attached_documents_sec( pso_errmsg                    out varchar2
                                                        ,pso_errcode                   out varchar2
                                                         ,pni_cliente_header_id      in   number
                                                        ,pni_cust_account_id        in  number
                                                          ) is 
    CURSOR get_dir_fiscal_info(cur_cliente_header_id   number) IS
      select  prim_cedula_file
          ,prim_cedula_file_name
          ,prim_cedula_content_type 
          ,sec_cedula_file
          ,sec_cedula_file_name
          ,sec_cedula_content_type 
   from XXQP_PDFT_CLIENTES_DIR_FISCAL
 where header_id = cur_cliente_header_id
  order by id desc; 
     dir_fiscal_info_rec        get_dir_fiscal_info%ROWTYPE;
     
ls_errmg            varchar2(2000); 
ls_errcode          varchar2(2000);

ln_media_id       number;

lr_rowid                     rowid;
ln_document_id          number; 
ln_short_datatype_id   number;
ln_category_id            number;

ls_description fnd_documents_tl.description%type:= 'Customer Invoice Attachment';

ln_attached_document_id    number;
ls_entity_name                   varchar2(2000) := 'AR_CUSTOMERS';
ls_pk1_value                       varchar2(2000) := null;  
ln_seq_num                       number;
begin 


ls_pk1_value := pni_cust_account_id;

  OPEN get_dir_fiscal_info(pni_cliente_header_id);
     LOOP
        FETCH get_dir_fiscal_info INTO dir_fiscal_info_rec;
        EXIT WHEN get_dir_fiscal_info%NOTFOUND;
        
       if   dir_fiscal_info_rec.sec_cedula_file_name is not null
      and dir_fiscal_info_rec.sec_cedula_content_type is not null 
      and dir_fiscal_info_rec.sec_cedula_file is not null    then  
        SELECT MAX (file_id) + 1
           INTO ln_media_id
         FROM fnd_lobs;
      
        INSERT INTO fnd_lobs  (FILE_ID            
                                        ,FILE_NAME          
                                        ,FILE_CONTENT_TYPE  
                                        ,FILE_DATA          
                                        ,UPLOAD_DATE        
                                        ,EXPIRATION_DATE    
                                        ,PROGRAM_NAME       
                                        ,PROGRAM_TAG        
                                        ,LANGUAGE           
                                        ,ORACLE_CHARSET     
                                        ,FILE_FORMAT        
                                         )
                            VALUES (ln_media_id
                                        ,dir_fiscal_info_rec.sec_cedula_file_name
                                        ,dir_fiscal_info_rec.sec_cedula_content_type
                                        ,dir_fiscal_info_rec.sec_cedula_file
                                        ,SYSDATE
                                        ,NULL
                                        ,'FNDATTCH'
                                        ,NULL
                                        ,'ESA'
                                        ,'UTF8'
                                        , 'binary'
                                        );
         commit;
         
       
       SELECT fnd_documents_s.NEXTVAL
                  ,apps.fnd_attached_documents_s.NEXTVAL
          INTO ln_document_id
                 ,ln_attached_document_id
        FROM DUAL;
        
        SELECT DATATYPE_ID
           INTO ln_short_datatype_id
          FROM apps.fnd_document_datatypes
        WHERE NAME = 'FILE'
             and language = 'ESA';
             
        SELECT category_id
           INTO ln_category_id
          FROM apps.fnd_document_categories_vl
        WHERE USER_NAME = 'Miscellaneous';
              
      
           fnd_documents_pkg.insert_row(X_Rowid                       => lr_rowid                  /**  IN OUT NOCOPY VARCHAR2,  **/              
                                                        ,X_document_id              => ln_document_id               /**  IN OUT NOCOPY NUMBER,    **/
                                                        ,X_creation_date             => SYSDATE               /**  DATE,                    **/
                                                        ,X_created_by                 => nvl(fnd_profile.value('USER_ID'),0)              /**  NUMBER,                  **/
                                                        ,X_last_update_date        => SYSDATE                 /**  DATE,                    **/
                                                        ,X_last_updated_by         => nvl(fnd_profile.value('USER_ID'),0)                   /**  NUMBER,                  **/
                                                        ,X_last_update_login       => nvl(fnd_profile.value('LOGIN_ID'),0)                   /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_datatype_id                => ln_short_datatype_id              /**  NUMBER,                  **/
                                                        ,X_category_id                => ln_category_id              /**  NUMBER,                  **/
                                                        ,X_security_type              => 1              /**  NUMBER,                  **/
                                                        ,X_security_id                  => fnd_profile.value('ORG_ID')            /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_publish_flag                => 'Y'             /**  VARCHAR2,                **/
                                                        ,X_image_type                 => null               /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_storage_type              => null                /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_usage_type                => 'S'                /**  VARCHAR2,                **/
                                                        ,X_start_date_active         => null               /**  DATE DEFAULT NULL,       **/
                                                        ,X_end_date_active          => null               /**  DATE DEFAULT NULL,       **/
                                                        ,X_request_id                  => null             /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_application_id   => null                /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_id                    => null            /**  NUMBER DEFAULT NULL,     **/
                                                        ,X_program_update_date     => null                 /**  DATE DEFAULT NULL,       **/
                                                        ,X_language                        => 'ESA'          /**  VARCHAR2,                **/
                                                        ,X_description                     => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_file_name                       => dir_fiscal_info_rec.sec_cedula_file_name         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_media_id                        =>ln_media_id       /**  IN OUT NOCOPY NUMBER,    **/
                                                        ,X_Attribute_Category         => null              /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute1                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute2                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute3                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute4                      => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute5                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute6                      => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute7                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute8                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute9                      => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute10                    => null         /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute11                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute12                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute13                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute14                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_Attribute15                    => null          /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_create_doc                    => null           /**  VARCHAR2 DEFAULT 'N',    **/
                                                        ,X_url                                => null      /**  VARCHAR2 DEFAULT NULL,   **/
                                                        ,X_title                              => null      /**  VARCHAR2 DEFAULT NULL    **/
                                                        );
         
        dbms_output.put_line('lr_rowid:'||lr_rowid); 
         commit;
         
         fnd_documents_pkg.insert_tl_row (x_document_id => ln_document_id,
                                                         x_creation_date => SYSDATE,
                                                         x_created_by => nvl(fnd_profile.value('USER_ID'),0)    ,
                                                         x_last_update_date => SYSDATE,
                                                         x_last_updated_by => nvl(fnd_profile.value('USER_ID'),0)    ,
                                                         x_last_update_login => nvl(fnd_profile.VALUE('LOGIN_ID'),0),
                                                        x_language => 'US',
                                                        x_description => ls_description
                                                         );
                                                         commit;
          
         SELECT NVL (MAX (seq_num), 0) + 10
            INTO ln_seq_num
           FROM fnd_attached_documents
         WHERE pk1_value = ls_pk1_value 
              AND entity_name = ls_entity_name;
          
          dbms_output.put_line('ln_seq_num:'||ln_seq_num);                                                       
           
         fnd_attached_documents_pkg.Insert_Row(X_Rowid                             => lr_rowid  /** IN OUT NOCOPY VARCHAR2,  **/      
                                                                                   ,X_attached_document_id             => ln_attached_document_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_document_id                      => ln_document_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_creation_date                    => sysdate  /** DATE,                    **/
                                                                                   ,X_created_by                       => nvl(fnd_profile.value('USER_ID'),0)   /** NUMBER,                  **/
                                                                                   ,X_last_update_date                 => sysdate  /** DATE,                    **/
                                                                                   ,X_last_updated_by                  => nvl(fnd_profile.value('USER_ID'),0)   /** NUMBER,                  **/
                                                                                   ,X_last_update_login                => nvl(fnd_profile.VALUE('LOGIN_ID'),0)  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_seq_num                          => ln_seq_num  /** NUMBER,                  **/
                                                                                   ,X_entity_name                      => ls_entity_name  /** VARCHAR2,                **/
                                                                                   ,X_column1                          => null  /** VARCHAR2,                **/
                                                                                   ,X_pk1_value                        => ls_pk1_value  /** VARCHAR2,                **/
                                                                                   ,X_pk2_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk3_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk4_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_pk5_value                        => null  /** VARCHAR2,                **/
                                                                                   ,X_automatically_added_flag         => 'N'  /** VARCHAR2,                **/
                                                                                   ,X_request_id                       => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_application_id           => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_id                       => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_program_update_date              => null  /** DATE DEFAULT NULL,       **/
                                                                                   ,X_Attribute_Category               => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute1                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute2                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute3                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute4                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute5                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute6                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute7                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute8                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute9                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute10                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute11                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute12                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute13                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute14                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_Attribute15                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   /*  columns necessary for creating a document on the fly */
                                                                                   ,X_datatype_id                      => ln_short_datatype_id  /** NUMBER,                  **/ 
                                                                                   ,X_category_id                      => ln_category_id  /** NUMBER,                  **/
                                                                                   ,X_security_type                    => 1  /** NUMBER,                  **/
                                                                                   ,X_security_id                      =>  fnd_profile.value('ORG_ID')   /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_publish_flag                     => 'Y'  /** VARCHAR2,                **/
                                                                                   ,X_image_type                       => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_storage_type                     => null  /** NUMBER DEFAULT NULL,     **/
                                                                                   ,X_usage_type                       => 'S'  /** VARCHAR2,                **/
                                                                                   ,X_language                         => 'ESA'  /** VARCHAR2,                **/
                                                                                   ,X_description                      => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_file_name                        => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_media_id                         => ln_media_id  /** IN OUT NOCOPY NUMBER,    **/
                                                                                   ,X_doc_attribute_Category           => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute1                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute2                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute3                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute4                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute5                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute6                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute7                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute8                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute9                   => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute10                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute11                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute12                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute13                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute14                  => null  /** VARCHAR2,                **/
                                                                                   ,X_doc_attribute15                  => null  /** VARCHAR2,                **/
                                                                                   ,X_create_doc                       => null  /** VARCHAR2 DEFAULT 'N',    **/
                                                                                   ,X_url                              => null  /** VARCHAR2 DEFAULT NULL,   **/
                                                                                   ,X_title                                   => null  /** VARCHAR2 DEFAULT NULL    **/
                                                                                   );        
            commit;                                                                                                             
         
      end if;      
        
     END LOOP;
     CLOSE get_dir_fiscal_info;
   exception when others then 
    pso_errmsg := 'Excepcion al llamar call_attached_documents:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
   end call_attached_documents_sec;       
   
    procedure create_account_contact(pso_errmsg                    out varchar2
                                                     ,pso_errcode                   out varchar2
                                                     ,pni_cliente_header_id      in   number
                                                     ,pni_cust_party_id            in   number
                                                     ,pni_cust_account_id        in   number
                                                     ,psi_tipo_informacion       in   varchar2
                                                      ) is 
     CURSOR get_contactos_info (cur_cliente_header_id number ) IS
        select contactos.ID 
          ,contactos.HEADER_ID 
          ,contactos.TIPO_CONTACTO
          ,decode(contactos.TIPO_CONTACTO
                     ,'Cierre y Seguimiento', 'CIERRE_Y_SEGUIMIENTO'
                     ,'Cobranza','COBRANZA'
                     ,'Gerencial','GERENCIAL'
                     ,'Directriz','DIRECTRIZ'
                     ,'Auditoria','AUDITORIA'
                     ,'Devoluciones','DEVOLUCIONES'
                    ) CODE_TIPO_CONTACTO
          ,contactos.NOMBRE
          ,contactos.DIRECCION 
          ,contactos.TELEFONO 
          ,contactos.CORREO_ELECTRONICO 
          ,contactos.PUESTO
          ,contactos.CREATED_BY  
    from XXQP_PDFT_CLIENTES_CONTACTOS contactos
     where contactos.HEADER_ID = cur_cliente_header_id  ;
       
       contactos_info_rec               get_contactos_info%ROWTYPE;
       
       
 ln_application_id   number := null; 
 ln_resp_id            number := null; 

 ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
 
 le_create_account_contact    exception; 
 ls_errmsg   varchar2(2000);
 ls_errcode    varchar2(2000);
 
 /**** call create person ***************************************/
 lt_person_rec_type                  hz_party_v2pub.person_rec_type;
 ln_per_party_id                                 number; 
 ls_per_party_number                   varchar2(2000);
 ln_per_profile_id                         number;
 ls_per_return_status                   varchar2(2000); 
 ln_per_msg_count                      number; 
 ls_per_msg_data                        varchar2(2000);

/**** call create org contact  ***********************************/
lt_ORG_CONTACT_REC_TYPE                                HZ_PARTY_CONTACT_V2PUB.ORG_CONTACT_REC_TYPE;
ln_org_contact_id                                 NUMBER;
ln_org_party_rel_id                                   NUMBER;
ln_org_party_id                                       NUMBER;
ls_org_party_number                                   VARCHAR2(2000);
ls_org_return_status                                  VARCHAR2(2000);
ln_org_msg_count                                      NUMBER;
ls_org_msg_data                                       VARCHAR2(2000);
 
/***** cal create cust account role *************************************************************/
lt_CUST_ACCOUNT_ROLE_REC_TYPE        hz_cust_account_role_v2pub.CUST_ACCOUNT_ROLE_REC_TYPE; 
ln_cust_account_role_id                           number; 
ls_acc_role_return_status                        varchar2(2000); 
ln_acc_role_msg_count                           number; 
ls_acc_role_msg_data                            varchar2(2000);
                                                          
/** call create contact point   **/
 lt_contact_point_rec_type      hz_contact_point_v2pub.contact_point_rec_type; 
 lt_email_rec_type                 hz_contact_point_v2pub.email_rec_type;
 lt_phone_rec_type               hz_contact_point_v2pub.phone_rec_type;
 ln_point_contact_point_id     number; 
ls_point_return_status          varchar2(2000); 
ln_point_msg_count             number; 
ls_point_msg_data               varchar2(2000); 
                                                   
                                                 
  begin 
   pso_errmsg := null; 
   pso_errcode := null; 
   
   
  SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';

  FND_GLOBAL.APPS_INITIALIZE(user_id           => nvl(fnd_profile.value('USER_ID'),0)
                                 ,resp_id           => ln_resp_id
                                 ,resp_appl_id      => ln_application_id
                              /*   ,security_group_id in number default 0, */
                              /*   ,server_id in number default -1   */
                                  );
                                  
     mo_global.init (p_appl_short_name => 'S');
      execute immediate ls_statement;
      
    OPEN get_contactos_info(pni_cliente_header_id);
       LOOP
          FETCH get_contactos_info INTO contactos_info_rec;
          EXIT WHEN get_contactos_info%NOTFOUND;
          
           lt_person_rec_type.person_first_name := contactos_info_rec.NOMBRE;
         /**  lt_person_rec_type.person_last_name := contactos_info_rec.NOMBRE; **/
           lt_person_rec_type.party_rec.orig_system := 'USER_ENTERED';
           lt_person_rec_type.party_rec.orig_system_reference := 'PDFT:'||psi_tipo_informacion||contactos_info_rec.id; --<<Must be unique>>
           lt_person_rec_type.party_rec.status := 'A';
           lt_person_rec_type.created_by_module := 'BO_API';
           
            fnd_msg_pub.DELETE_MSG (NULL); 
           fnd_msg_pub.initialize; 
     
        if contactos_info_rec.NOMBRE is not null then
        
            hz_party_v2pub.create_person ( p_init_msg_list                    => fnd_api.g_true
                                                           ,p_person_rec                      => lt_person_rec_type
                                                           ,x_party_id                           => ln_per_party_id
                                                           ,x_party_number                  => ls_per_party_number
                                                           ,x_profile_id                         => ln_per_profile_id
                                                           ,x_return_status                   => ls_per_return_status
                                                           ,x_msg_count                       => ln_per_msg_count
                                                           ,x_msg_data                        => ls_per_msg_data
                                                          ); 
                                                          
            IF ls_per_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;

            dbms_output.put_line('ln_party_id:'||ln_per_party_id);  
            dbms_output.put_line('ls_return_status:'||ls_per_return_status);  
            dbms_output.put_line('ln_msg_count:'||ln_per_msg_count);  
            dbms_output.put_line('ls_msg_data:'||ls_per_msg_data);  
            update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                 set attribute1 = 'Creation of Person is Successful '
                     ,attribute2 = ln_per_party_id
             where contactos.HEADER_ID = pni_cliente_header_id
                 and contactos.id =contactos_info_rec.id;
             commit;
                                                        
             
             
                            lt_ORG_CONTACT_REC_TYPE.department_code := 'ACCOUNTING';
                            lt_ORG_CONTACT_REC_TYPE.job_title :=contactos_info_rec.tipo_contacto; /** 'Accountant'**/
                            lt_ORG_CONTACT_REC_TYPE.decision_maker_flag := 'Y';
                            lt_ORG_CONTACT_REC_TYPE.job_title_code := contactos_info_rec.code_tipo_contacto; /**'ACCOUNTANT'**/
                            lt_ORG_CONTACT_REC_TYPE.created_by_module := 'BO_API';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.subject_id := ln_per_party_id;                       --<< this is party id of the contact created in call_create_person >>
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.subject_type := 'PERSON';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.subject_table_name := 'HZ_PARTIES';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.object_id := pni_cust_party_id;                                --<< this is hz_parties.party_id of the Customer in call_create_cust_account >>
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.object_type := 'ORGANIZATION';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.object_table_name := 'HZ_PARTIES';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.relationship_code := 'CONTACT_OF';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.relationship_type := 'CONTACT';
                            lt_ORG_CONTACT_REC_TYPE.party_rel_rec.start_date := SYSDATE;
                                   
                             fnd_msg_pub.DELETE_MSG (NULL); 
                             fnd_msg_pub.initialize; 
                            hz_party_contact_v2pub.create_org_contact(    p_init_msg_list => FND_API.G_TRUE
                                                                                            ,p_org_contact_rec  => lt_ORG_CONTACT_REC_TYPE
                                                                                            ,x_org_contact_id   => ln_org_contact_id
                                                                                            ,x_party_rel_id => ln_org_party_rel_id
                                                                                            ,x_party_id      => ln_org_party_id
                                                                                            ,x_party_number  => ls_org_party_number
                                                                                            ,x_return_status    => ls_org_return_status
                                                                                            ,x_msg_count        => ln_org_msg_count
                                                                                            ,x_msg_data          =>ls_org_msg_data
                                                                                            );

                                 if ls_org_return_status = fnd_api.g_ret_sts_success THEN
                                       COMMIT;
                                        DBMS_OUTPUT.PUT_LINE('Creation of Org Contact is Successful ');
                                        DBMS_OUTPUT.PUT_LINE('Output information ....');
                                        dbms_output.put_line('ln_org_contact_id:'||ln_org_contact_id);  
                                        dbms_output.put_line('ln_party_rel_id:'||ln_org_party_rel_id);  
                                        dbms_output.put_line('ln_org_party_id:'||ln_org_party_id);  
                                        dbms_output.put_line('ls_org_party_number:'||ls_org_party_number);  
                                        dbms_output.put_line('ls_org_return_status:'||ls_org_return_status);  
                                        dbms_output.put_line('ln_org_msg_count:'||ln_org_msg_count);  
                                        dbms_output.put_line('ls_org_msg_data:'||ls_org_msg_data);  
                                          
                                         update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                             set attribute1 = 'Creation of Org Contact is Successful '
                                                 ,attribute3 = ln_org_party_id
                                         where contactos.HEADER_ID = pni_cliente_header_id
                                         and contactos.id =contactos_info_rec.id;
                                         commit;                                          
                                         
                                         
                                           lt_CUST_ACCOUNT_ROLE_REC_TYPE.party_id := ln_org_party_id; --<<this is the value of ln_party_id which gets generated from the  call_create_org_contact >>
                                           lt_CUST_ACCOUNT_ROLE_REC_TYPE.cust_account_id := pni_cust_account_id; --<<value for hz_cust_accounts_all.cust_account_id of the Organization party>>
                                        --   lt_CUST_ACCOUNT_ROLE_REC_TYPE.cust_acct_site_id := 2248; --<<To create contact at site level, if not to create contact at customer levl, we need to comment this line>>
                                         /** p_cr_cust_acc_role_rec.primary_flag := 'Y'; **/
                                           lt_CUST_ACCOUNT_ROLE_REC_TYPE.role_type := 'CONTACT';
                                           lt_CUST_ACCOUNT_ROLE_REC_TYPE.created_by_module := 'HZ_CPUI';

                                           
                                            fnd_msg_pub.DELETE_MSG (NULL); 
                                            fnd_msg_pub.initialize; 
                                         hz_cust_account_role_v2pub.create_cust_account_role (
                                                                                                                         p_init_msg_list                         =>  FND_API.G_TRUE
                                                                                                                        ,p_cust_account_role_rec           =>  lt_CUST_ACCOUNT_ROLE_REC_TYPE
                                                                                                                        ,x_cust_account_role_id             => ln_cust_account_role_id
                                                                                                                        ,x_return_status                        => ls_acc_role_return_status
                                                                                                                        ,x_msg_count                           => ln_acc_role_msg_count
                                                                                                                        ,x_msg_data                             => ls_acc_role_msg_data
                                                                                                                    );
                                                                                                                    
                                          IF ls_acc_role_return_status = fnd_api.g_ret_sts_success THEN
                                                    COMMIT;
                                                    DBMS_OUTPUT.PUT_LINE('Creation of Cust Account Role is Successful ');
                                                    DBMS_OUTPUT.PUT_LINE('Output information ....');
                                                    dbms_output.put_line('ln_cust_account_role_id:'||ln_cust_account_role_id);  
                                                 dbms_output.put_line('ls_acc_role_return_status:'||ls_acc_role_return_status);  
                                                 dbms_output.put_line('ln_acc_role_msg_count:'||ln_acc_role_msg_count);  
                                                 dbms_output.put_line('ls_acc_role_msg_data:'||ls_acc_role_msg_data);  
                                                  
                                                   update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 = 'Creation of Org Contact is Successful '
                                                         ,attribute4 = ln_cust_account_role_id
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;                 
                                                 
                                                 
                                                   lt_contact_point_rec_type.contact_point_type := 'PHONE';
                                                   lt_contact_point_rec_type.contact_point_purpose := 'BUSINESS';
                                                   lt_contact_point_rec_type.created_by_module := 'TCA_V1_API';
                                                   lt_contact_point_rec_type.status := 'A';
                                                    lt_contact_point_rec_type.owner_table_name := 'HZ_PARTIES';
                                                   lt_contact_point_rec_type.owner_table_id := ln_org_party_id;  --<<this is the value of ln_party_id which gets generated from the  call_create_org_contact >>
                                                   lt_phone_rec_type.phone_area_code := null;
                                                   lt_phone_rec_type.phone_number := contactos_info_rec.TELEFONO; /** 567890 **/
                                                   lt_phone_rec_type.phone_extension := null;
                                                   lt_phone_rec_type.phone_line_type := 'MOBILE';
                                                    lt_email_rec_type.email_format := null;
                                                   lt_email_rec_type.email_address := null; /** 'shailender@OracleAppsDNA.com' **/
                                                 
                                                    fnd_msg_pub.DELETE_MSG (NULL); 
                                                    fnd_msg_pub.initialize; 
                                                   hz_contact_point_v2pub.create_contact_point (p_init_msg_list          => fnd_api.g_true
                                                                                                                     ,p_contact_point_rec      => lt_contact_point_rec_type
                                                                                                                     ,p_email_rec              => lt_email_rec_type
                                                                                                                     ,p_phone_rec              => lt_phone_rec_type
                                                                                                                     ,x_contact_point_id       => ln_point_contact_point_id
                                                                                                                     ,x_return_status          => ls_point_return_status
                                                                                                                     ,x_msg_count              => ln_point_msg_count
                                                                                                                     ,x_msg_data               => ls_point_msg_data
                                                                                                                     );
                                                 if    ls_point_return_status =     fnd_api.g_ret_sts_success THEN
                                                  insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ln_point_contact_point_id PHONE:'||ln_point_contact_point_id); 
                                                 commit; 
                                                  update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 = 'Creation of Contact Point is Successful '
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;  
                                                 
                                                 else 
                                                 rollback; 
                                                 update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 = 'Creation of Contact Point Failed '
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;  
                                                 end if; 
                                                 
                                                 
                                                 lt_contact_point_rec_type.contact_point_type := 'EMAIL';
                                                   lt_contact_point_rec_type.contact_point_purpose := 'BUSINESS';
                                                   lt_contact_point_rec_type.created_by_module := 'TCA_V1_API';
                                                   lt_contact_point_rec_type.status := 'A';
                                                    lt_contact_point_rec_type.owner_table_name := 'HZ_PARTIES';
                                                   lt_contact_point_rec_type.owner_table_id := ln_org_party_id;  --<<this is the value of ln_party_id which gets generated from the  call_create_org_contact >>
                                                   lt_phone_rec_type.phone_area_code := null;
                                                   lt_phone_rec_type.phone_number :=null; /** 567890 **/
                                                   lt_phone_rec_type.phone_extension := null;
                                                   lt_phone_rec_type.phone_line_type := null;
                                                    lt_email_rec_type.email_format := 'MAILHTML';
                                                   lt_email_rec_type.email_address := contactos_info_rec.CORREO_ELECTRONICO; /** 'shailender@OracleAppsDNA.com' **/
                                                 
                                                    fnd_msg_pub.DELETE_MSG (NULL); 
                                                    fnd_msg_pub.initialize; 
                                                   hz_contact_point_v2pub.create_contact_point (p_init_msg_list          => fnd_api.g_true
                                                                                                                     ,p_contact_point_rec      => lt_contact_point_rec_type
                                                                                                                     ,p_email_rec              => lt_email_rec_type
                                                                                                                     ,p_phone_rec              => lt_phone_rec_type
                                                                                                                     ,x_contact_point_id       => ln_point_contact_point_id
                                                                                                                     ,x_return_status          => ls_point_return_status
                                                                                                                     ,x_msg_count              => ln_point_msg_count
                                                                                                                     ,x_msg_data               => ls_point_msg_data
                                                                                                                     );
                                                 if    ls_point_return_status =     fnd_api.g_ret_sts_success THEN
                                                  insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ln_point_contact_point_id EMAIL:'||ln_point_contact_point_id); 
                                                 commit; 
                                                  update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 = 'Creation of Contact Point is Successful '
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;  
                                                 
                                                 else 
                                                 rollback; 
                                                 update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 = 'Creation of Contact Point Failed '
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;  
                                                 end if; 
                                                                        

                                                ELSE
                                                  
                                              /**   ls_acc_role_msg_data := ''; **/
                                                ROLLBACK;
                                                FOR i IN 1 .. ln_acc_role_msg_count
                                                LOOP

                                                  ls_acc_role_msg_data := ls_acc_role_msg_data||oe_msg_pub.get( p_msg_index => i
                                                                                                                                    , p_encoded => 'F');
                                                                       
                                                END LOOP;
                                                
                                                 update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                                     set attribute1 ='Creation of Cust Account Role Failed:'||ls_acc_role_msg_data
                                                 where contactos.HEADER_ID = pni_cliente_header_id
                                                 and contactos.id =contactos_info_rec.id;
                                                 commit;
                                                 
                                                 ls_errmsg := 'Creation of Cust Account Role failed:'||ls_acc_role_msg_data;
                                                 raise le_create_account_contact; 

                                                END IF;                                                                                                       
                                                                                                                    

                                ELSE
                                    DBMS_OUTPUT.put_line ('Creation of Org Contact failed:'||ls_org_msg_data);
                                 /**    ls_org_msg_data := ''; **/
                                    ROLLBACK;
                                    FOR i IN 1 .. ln_org_msg_count
                                    LOOP

                                      ls_org_msg_data := ls_org_msg_data||oe_msg_pub.get( p_msg_index => i
                                                                                                                        , p_encoded => 'F');
                                                           
                                    END LOOP;
                                    
                                     update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                                         set attribute1 ='Creation of Org Contact Failed:'||ls_org_msg_data
                                     where contactos.HEADER_ID = pni_cliente_header_id
                                     and contactos.id =contactos_info_rec.id;
                                     commit;
                                     
                                     ls_errmsg := 'Creation of Org Contact failed:'||ls_org_msg_data||'. Parameters HeaderId:'||pni_cliente_header_id||', contactoId:'||contactos_info_rec.id;
                                     raise le_create_account_contact; 

                                END IF; /** END  if ls_org_return_status = fnd_api.g_ret_sts_success THEN **/
             

        ELSE
          /**  ls_per_msg_data := ''; **/
            ROLLBACK;
            FOR i IN 1 .. ln_per_msg_count
            LOOP

              ls_per_msg_data := ls_per_msg_data||oe_msg_pub.get( p_msg_index => i
                                                                                                , p_encoded => 'F');
                                   
            END LOOP;
            
             update XXQP_PDFT_CLIENTES_CONTACTOS contactos
                 set attribute1 ='Creation of Person Failed:'||ls_per_msg_data
             where contactos.HEADER_ID = pni_cliente_header_id
             and contactos.id =contactos_info_rec.id;
             commit;
             
             ls_errmsg := 'Creation of Person failed:'||ls_per_msg_data;
             raise le_create_account_contact; 
            
        END IF;       /** END IF ls_per_return_status = fnd_api.g_ret_sts_success THEN**/                 
        
        
          
       end if; /** END if contactos_info_rec.NOMBRE is not null then **/
         
     END LOOP;
       CLOSE get_contactos_info;
       
   
  exception when le_create_account_contact then 
  pso_errmsg := ls_errmsg;
   pso_errcode := '2';
  when others then 
    pso_errmsg := 'Excepcion al llamar call_attached_documents:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '2';
  end create_account_contact;            
  
  
                                               
 procedure call_create_cust_acct_site(pso_errmsg                    out varchar2
                                                     ,pso_errcode                   out varchar2
                                                       ,pni_cust_account_id        in number
                                                       ,pni_party_site_id            in number
                                                       ,pni_org_id                     in number
                                                       ,psi_metodo_de_pago       in varchar2       
                                                       ,psi_numero_cuenta         in varchar2                 
                                                       ,psi_uso_del_cdfi             in varchar2       
                                                       ,pno_cust_acct_site_id     out number 
                                                      ) is   
   lt_cust_acct_site_rec_type    hz_cust_account_site_v2pub.cust_acct_site_rec_type; 

    ls_return_status           varchar2(2000) := null; 
    ln_msg_count              number := null; 
    ls_msg_data                varchar2(2000) := null; 

    ln_application_id   number := null; 
    ln_resp_id            number := null; 

    ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';

    ln_cust_acct_site_id   number := null; 
                                                   
  begin 
   pso_errmsg := null; 
   pso_errcode := '0';
  
 
   if 1 =1 then 
    lt_cust_acct_site_rec_type.cust_acct_site_id                 := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.cust_account_id                   := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.party_site_id                     := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.attribute_category                := null;     /**  VARCHAR2(30)    **/
    lt_cust_acct_site_rec_type.attribute1                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute2                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute3                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute4                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute5                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute6                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute7                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute8                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute9                        := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute10                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute11                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute12                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute13                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute14                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute15                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute16                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute17                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute18                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute19                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.attribute20                       := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute_category         := null;     /**  VARCHAR2(30)    **/
    lt_cust_acct_site_rec_type.global_attribute1                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute2                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute3                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute4                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute5                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute6                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute7                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute8                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute9                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute10                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute11                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute12                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute13                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute14                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute15                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute16                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute17                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute18                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute19                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.global_attribute20                := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.orig_system_reference             := null;     /**  VARCHAR2(240)   **/
    lt_cust_acct_site_rec_type.orig_system                       := null;     /**  VARCHAR2(30)    **/
    lt_cust_acct_site_rec_type.status                            := null;     /**  VARCHAR2(1)     **/
    lt_cust_acct_site_rec_type.customer_category_code            := null;     /**  VARCHAR2(30)    **/
    lt_cust_acct_site_rec_type.language                          := null;     /**  VARCHAR2(4)     **/
    lt_cust_acct_site_rec_type.key_account_flag                  := null;     /**  VARCHAR2(1)     **/
    lt_cust_acct_site_rec_type.tp_header_id                      := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.ece_tp_location_code              := null;     /**  VARCHAR2(40)    **/
    lt_cust_acct_site_rec_type.primary_specialist_id             := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.secondary_specialist_id           := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.territory_id                      := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.territory                         := null;     /**  VARCHAR2(30)    **/
    lt_cust_acct_site_rec_type.translated_customer_name          := null;     /**  VARCHAR2(50)    **/
    lt_cust_acct_site_rec_type.created_by_module                 := null;     /**  VARCHAR2(150)   **/
    lt_cust_acct_site_rec_type.application_id                    := null;     /**  NUMBER          **/
    lt_cust_acct_site_rec_type.org_id                            := null;     /**  NUMBER * Bug 3456489 **/

 end if; 

 SELECT application_id, responsibility_id
    INTO ln_application_id, ln_resp_id
    FROM fnd_responsibility
   WHERE responsibility_key = 'RECEIVABLES_MANAGER';

 

  FND_GLOBAL.APPS_INITIALIZE(user_id           =>  nvl(fnd_profile.value('USER_ID'),0)
                                 ,resp_id           => ln_resp_id
                                 ,resp_appl_id      => ln_application_id
                              /*   ,security_group_id in number default 0, */
                              /*   ,server_id in number default -1   */
                                  );
lt_cust_acct_site_rec_type.cust_account_id := pni_cust_account_id; 
lt_cust_acct_site_rec_type.party_site_id     := pni_party_site_id; 
lt_cust_acct_site_rec_type.created_by_module        := 'TCA_V2_API';
lt_cust_acct_site_rec_type.org_id := pni_org_id;                                  
lt_cust_acct_site_rec_type.attribute1 := psi_metodo_de_pago  ;       
lt_cust_acct_site_rec_type.attribute2   := psi_numero_cuenta ;           
lt_cust_acct_site_rec_type.attribute3   := psi_uso_del_cdfi ; 
                                                       
                                  
    mo_global.init ( 'AR' ) ;
   mo_global.set_org_context ( lt_cust_acct_site_rec_type.org_id, NULL, 'AR' ) ;
   fnd_global.set_nls_context ( 'AMERICAN' ) ;
   mo_global.set_policy_context ( 'S', lt_cust_acct_site_rec_type.org_id ) ;

      
      fnd_msg_pub.DELETE_MSG (NULL); 
     fnd_msg_pub.initialize;                                 



 hz_cust_account_site_v2pub.create_cust_acct_site (  p_init_msg_list                  =>  FND_API.G_FALSE              
                                                                            ,p_cust_acct_site_rec           => lt_cust_acct_site_rec_type
                                                                            ,x_cust_acct_site_id    => ln_cust_acct_site_id
                                                                            ,x_return_status         => ls_return_status
                                                                            ,x_msg_count             => ln_msg_count 
                                                                            ,x_msg_data              => ls_msg_data
                                                                        ) ;



     IF ls_return_status = fnd_api.g_ret_sts_success THEN
         
            DBMS_OUTPUT.PUT_LINE('Creation of Cust Account  Site is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_cust_acct_site_id:'||ln_cust_acct_site_id);  
           pno_cust_acct_site_id := ln_cust_acct_site_id;
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
           insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'Creation of Cust Account  Site is Successful ');    
           insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ln_cust_acct_site_id:'||ln_cust_acct_site_id);   
           insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ls_return_status:'||ls_return_status);   
           insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ln_msg_count:'||ln_msg_count);   
           insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'ls_msg_data:'||ls_msg_data);                                             
            
            COMMIT;
             
        ELSE
            DBMS_OUTPUT.put_line ('Creation of Party Site failed:'||ls_msg_data);
            ROLLBACK;
             insert into xxqp_pdft_debug values (xxqp_pdft_debug_s.nextval,'Creation of Cust Account Site failed:'||ls_msg_data); 
             commit; 
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;

        END IF;
        
  exception  when others then 
    pso_errmsg := 'Excepcion al llamar call_create_cust_acct_site:'||sqlerrm||', '||sqlcode; 
   pso_errcode := '2';
  end call_create_cust_acct_site;                                                                  
  
   
 procedure from_oracle_to_pdft(pso_errmsg                    out varchar2
                                              ,pso_errcode                   out varchar2
                                              ) is 
 begin 
 pso_errmsg := null; 
 pso_errcode := '0';
 exception when others then 
  pso_errmsg := 'Excepcion al llamar from_oracle_to_pdft:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '2';
 end from_oracle_to_pdft; 
 
 
  procedure from_pdft_to_oracle_sec(pso_errmsg                    out varchar2
                                                    ,pso_errcode                   out varchar2
                                                    ,pni_cliente_header_id      in   number
                                                   ) is 
  
  ls_errmsg                  varchar2(2000)   := null; 
  ls_errcode                 varchar2(2000)   := null;
  ln_party_id                 number := null;
  ln_cust_account_id      number := null;                                              
  begin 
   pso_errmsg := null; 
  pso_errcode := '0';
  
   xxqp_pdft_clientes_pkg.call_create_cust_account_sec(pso_errmsg                    => ls_errmsg
                                                                                ,pso_errcode                   => ls_errcode
                                                                                ,pni_cliente_header_id     => pni_cliente_header_id
                                                                                ,pno_party_id                  => ln_party_id 
                                                                                ,pno_cust_account_id       => ln_cust_account_id
                                                                                ); 
  
  exception when others then 
   pso_errmsg := 'Excepcion al llamar from_pdft_to_oracle_sec:'||sqlerrm||', '||sqlcode; 
   pso_errcode := '2';
  end from_pdft_to_oracle_sec;                     
  
   procedure call_create_cust_account_sec(pso_errmsg                    out varchar2
                                                     ,pso_errcode                   out varchar2
                                                     ,pni_cliente_header_id      in   number
                                                     ,pno_party_id                  out number 
                                                      ,pno_cust_account_id       out number
                                                    ) is 
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
                  ,xpch.ATTRIBUTE2            
                  ,xpch.ATTRIBUTE3            
                  ,xpch.ATTRIBUTE4            
                  ,xpch.ATTRIBUTE5            
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
     
   client_head_info_rec             get_client_head_info%ROWTYPE;
   
   
   
  LT_CUST_ACCOUNT_REC_TYPE           HZ_CUST_ACCOUNT_V2PUB.CUST_ACCOUNT_REC_TYPE;
  LT_ORGANIZATION_REC_TYPE             HZ_PARTY_V2PUB.ORGANIZATION_REC_TYPE;
  LT_PARTY_REC_TYPE                          HZ_PARTY_V2PUB.PARTY_REC_TYPE; 
  LT_CUSTOMER_PROFILE_REC_TYPE     HZ_CUSTOMER_PROFILE_V2PUB.CUSTOMER_PROFILE_REC_TYPE;
  
  ln_cust_acccount_id                             number := null; 
  ls_account_number                              varchar2(2000) := null; 
  ln_party_id                                         number := null; 
  ls_party_number                                varchar2(2000) := null; 
  ln_profile_id                                      number := null; 
  ls_return_status                                 varchar2(2000) := null; 
  ln_msg_count                                    number := null; 
  ls_msg_data                                     varchar2(2000) := null; 
 
 ln_application_id   number;
ln_resp_id            number;

ls_statement varchar2(2000) := 'alter session set nls_language=''AMERICAN''';         
                                                   
 begin 
  pso_errmsg := null; 
 pso_errcode := '0';
 
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

   if 1 =1 then 
    lt_customer_profile_rec_type.cust_account_profile_id            :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.cust_account_id                    :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.status                             :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.collector_id                       :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_analyst_id                  :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_checking                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.next_credit_review_date            :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.tolerance                          :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.discount_terms                     :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.dunning_letters                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.interest_charges                   :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.send_statements                    :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.credit_balance_statements          :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.credit_hold                        :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.profile_class_id                   :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.site_use_id                        :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_rating                      :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.risk_code                          :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.standard_terms                     :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.override_terms                     :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.dunning_letter_set_id              :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.interest_period_days               :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.payment_grace_days                 :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.discount_grace_days                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.statement_cycle_id                 :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.account_status                     :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.percent_collectable                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.autocash_hierarchy_id              :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.attribute_category                 :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.attribute1                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute2                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute3                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute4                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute5                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute6                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute7                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute8                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute9                         :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute10                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute11                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute12                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute13                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute14                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.attribute15                        :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.auto_rec_incl_disputed_flag        :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.tax_printing_option                :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.charge_on_finance_charge_flag      :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.grouping_rule_id                   :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.clearing_days                      :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.jgzz_attribute_category            :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.jgzz_attribute1                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute2                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute3                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute4                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute5                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute6                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute7                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute8                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute9                    :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute10                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute11                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute12                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute13                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute14                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.jgzz_attribute15                   :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute1                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute2                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute3                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute4                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute5                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute6                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute7                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute8                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute9                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute10                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute11                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute12                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute13                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute14                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute15                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute16                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute17                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute18                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute19                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute20                 :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.global_attribute_category          :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.cons_inv_flag                      :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.cons_inv_type                      :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.autocash_hierarchy_id_for_adr      :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.lockbox_matching_option            :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.created_by_module                  :=null;    /** VARCHAR2(150),     **/
    lt_customer_profile_rec_type.application_id                     :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.review_cycle                       :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.last_credit_review_date            :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.party_id                           :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.credit_classification              :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.cons_bill_level                    :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.late_charge_calculation_trx        :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.credit_items_flag                  :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.disputed_transactions_flag         :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.late_charge_type                   :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.late_charge_term_id                :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.interest_calculation_period        :=null;    /** VARCHAR2(30),      **/
    lt_customer_profile_rec_type.hold_charged_invoices_flag         :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.message_text_id                    :=null;    /** NUMBER,            **/
    lt_customer_profile_rec_type.multiple_interest_rates_flag       :=null;    /** VARCHAR2(1),       **/
    lt_customer_profile_rec_type.charge_begin_date                  :=null;    /** DATE,              **/
    lt_customer_profile_rec_type.automatch_set_id                   :=null;    /** NUMBER             **/
   end if; 
   
 
 
  OPEN get_client_head_info(pni_cliente_header_id);
    LOOP
       FETCH get_client_head_info INTO client_head_info_rec;
       EXIT WHEN get_client_head_info%NOTFOUND;
       
       if 1 =1 then 
          fnd_msg_pub.DELETE_MSG (NULL); 
          fnd_msg_pub.initialize;   
          
           execute immediate ls_statement;
          lt_cust_account_rec_type.account_name := client_head_info_rec.sec_razon_social;
          lt_cust_account_rec_type.account_number :=  client_head_info_rec.sec_rfc; /** Column account_number must have a value. must have unique **/
          lt_cust_account_rec_type.attribute_category := 'QPN';
          lt_cust_account_rec_type.attribute1 := client_head_info_rec.sec_rfc;
          lt_cust_account_rec_type.attribute5 := client_head_info_rec.METODO_DE_PAGO_C;
          lt_cust_account_rec_type.attribute6 := client_head_info_rec.USO_DEL_CFDI_C;
          lt_cust_account_rec_type.orig_system_reference := client_head_info_rec.sec_rfc;
          lt_cust_account_rec_type.created_by_module := 'TCA_V2_API';
           
          lt_organization_rec_type.organization_name := client_head_info_rec.sec_razon_social;
          lt_organization_rec_type.known_as  := client_head_info_rec.nombre_cliente;
          lt_organization_rec_type.created_by_module := 'TCA_V2_API';
            
          lt_party_rec_type.attribute_category := 'QPN'; 
          lt_party_rec_type.attribute1 := client_head_info_rec.sec_rfc; 
          lt_party_rec_type.attribute2 := pni_cliente_header_id;
            
          lt_organization_rec_type.party_rec := lt_party_rec_type;
            
           
          HZ_CUST_ACCOUNT_V2PUB.create_cust_account ( p_init_msg_list                        =>  FND_API.G_TRUE
                                                                                   ,p_cust_account_rec                 =>  LT_CUST_ACCOUNT_REC_TYPE
                                                                                   ,p_organization_rec                  =>  LT_ORGANIZATION_REC_TYPE
                                                                                   ,p_customer_profile_rec            =>  LT_CUSTOMER_PROFILE_REC_TYPE
                                                                                   ,p_create_profile_amt               =>  FND_API.G_FALSE
                                                                                   ,x_cust_account_id                    => ln_cust_acccount_id
                                                                                   ,x_account_number                   => ls_account_number
                                                                                   ,x_party_id                               => ln_party_id
                                                                                   ,x_party_number                      => ls_party_number
                                                                                   ,x_profile_id                            => ln_profile_id
                                                                                   ,x_return_status                       => ls_return_status
                                                                                   ,x_msg_count                          => ln_msg_count
                                                                                   ,x_msg_data                           => ls_msg_data
                                                                                 );
         
           IF ls_return_status = fnd_api.g_ret_sts_success THEN
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Creation of Cust Account is Successful ');
            DBMS_OUTPUT.PUT_LINE('Output information ....');
            dbms_output.put_line('ln_cust_acccount_id:'||ln_cust_acccount_id);  
           dbms_output.put_line('ls_account_number:'||ls_account_number);  
           dbms_output.put_line('ln_party_id:'||ln_party_id);  
           dbms_output.put_line('ls_party_number:'||ls_party_number);  
           dbms_output.put_line('ln_profile_id:'||ln_profile_id);  
         dbms_output.put_line('ls_return_status:'||ls_return_status);  
         dbms_output.put_line('ln_msg_count:'||ln_msg_count);  
         dbms_output.put_line('ls_msg_data:'||ls_msg_data);  
         
         pno_party_id := ln_party_id; 
         pno_cust_account_id := ln_cust_acccount_id;
         
          update XXQP_PDFT_CLIENTES_HEADER
              set  attribute1 = 'Creation of Cust Account  Sec is Successful '
                   ,attribute4 = ln_party_id
                   ,attribute5 = ln_cust_acccount_id
             where id = pni_cliente_header_id;
           commit; 
                                                        

        ELSE
            DBMS_OUTPUT.put_line ('Creation of Cust Account failed:'||ls_msg_data);
            ROLLBACK;
            FOR i IN 1 .. ln_msg_count
            LOOP

              ls_msg_data := ls_msg_data||oe_msg_pub.get( p_msg_index => i
                                                             , p_encoded => 'F');
              dbms_output.put_line( i|| ') '|| ls_msg_data);

            END LOOP;
             update XXQP_PDFT_CLIENTES_HEADER
              set  attribute1 = 'Creation of Cust Account Sec Failed:'||ls_msg_data 
               where id = pni_cliente_header_id;
           commit; 
           
           pso_errmsg := 'Excepcion al llamar HZ_CUST_ACCOUNT_V2PUB.create_cust_account Parametros ClienteHeaderId:'||pni_cliente_header_id;
           pso_errcode := '2';
        END IF;
        
         end if; 
       
     END LOOP;
 CLOSE get_client_head_info;
 
 
 exception when others then 
 pso_errmsg := 'Excepcion al llamar call_create_cust_account:'||sqlerrm||', '||sqlcode; 
  pso_errcode := '0';
 end call_create_cust_account_sec;                      
                                                   
end xxqp_pdft_clientes_pkg;
/


CREATE OR REPLACE package body      XXQP_PDFT_CLIENT_FPTO_UPD_PKG is 
procedure upd_from_pdft_to_oracle(pso_errmsg                    out varchar2
                                                    ,pso_errcode                    out varchar2
                                                    ,pni_party_id                    in  number
                                                    ,pni_cliente_header_id      in  number
                                                     ) is 
lt_PARTY_REC_TYPE                                hz_party_v2pub.PARTY_REC_TYPE;                        
lt_ORGANIZATION_REC_TYPE                   hz_party_v2pub.ORGANIZATION_REC_TYPE;     
ln_org_party_obj_version_num                 number;                  
ln_org_profile_id                                     number; 
ls_org_return_status                               varchar2(2000); 
ln_org_msg_count                                   number; 
ls_org_msg_data                                     varchar2(2000);     

ln_application_id                                     number; 
ln_resp_id                                              number; 

ls_statement                                           varchar2(2000) := 'alter session set nls_language=''AMERICAN''';
ls_errmsg    varchar2(2000); 
ls_errcod     varchar2(2000); 
              
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
      
 fnd_msg_pub.DELETE_MSG (NULL); 
 fnd_msg_pub.initialize;   
          

lt_PARTY_REC_TYPE.party_id := pni_party_id; 
lt_PARTY_REC_TYPE.attribute2 := pni_cliente_header_id; 
lt_ORGANIZATION_REC_TYPE.party_rec := lt_PARTY_REC_TYPE;

SELECT object_version_number
     INTO ln_org_party_obj_version_num
     FROM hz_parties
    WHERE party_id =lt_PARTY_REC_TYPE.party_id
      AND status ='A';

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
            
            FOR i IN 1 .. ln_org_msg_count
            LOOP

              ls_org_msg_data := ls_org_msg_data||'*'||oe_msg_pub.get( p_msg_index => i
                                                                                                      , p_encoded => 'F');
      
            END LOOP;
            
             update  XXQP_PDFT_CLIENTES_HEADER xpch
              set   xpch.attribute1 = 'Update of Organization failed:'||ls_org_msg_data
            where xpch.id =  pni_cliente_header_id; 
           
            commit; 
          
        END IF;                                                      

exception when others then 
pso_errmsg := 'Excepcion al llamar al metodo upd_from_pdft_to_oracle.'||sqlerrm; 
pso_errcode := '2';
end upd_from_pdft_to_oracle; 

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


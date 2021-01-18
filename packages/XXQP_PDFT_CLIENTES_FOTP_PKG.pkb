CREATE OR REPLACE package body XXQP_PDFT_CLIENTES_FOTP_PKG is 

/** 24042020 se invierten p.party_name p.know_as **/

TYPE PartySeacrhTyp IS REF CURSOR;
TYPE AccountSeacrhTyp IS REF CURSOR;
TYPE SiteSeacrhTyp IS REF CURSOR;
 

 CURSOR get_contactos_tmp_info IS
 select 1 record_id
 , 'Cierre y Seguimiento' tipo_de_contacto 
 from dual
 union 
 select 2 record_id
 ,'Cobranza' tipo_de_contacto
 from dual
 union
 select 3 record_id
 ,'Gerencial' tipo_de_contacto
 from dual
 union
 select 4 record_id
 ,'Directriz' tipo_de_contacto
 from dual
 union
 select 5 record_id
 ,'Auditoria' tipo_de_contacto
 from dual
 union
 select 6 record_id
 ,'Devoluciones' tipo_de_contacto
 from dual;
 

 procedure valida_clientes(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,psi_nombre_cliente in varchar2
 ,psi_rfc in varchar2
 ,psi_razon_social in varchar2
 ,psi_estado in varchar2
 ,psi_operating_unit in varchar2
 ) is 
 ls_stmt_selectr VARCHAR2(32767);
 ls_stmt_where VARCHAR2(32767);
 ls_acc_select VARCHAR2(32767);
 ls_acc_where VARCHAR2(32767);
 ls_sites_select VARCHAR2(32767);
 ls_sites_where VARCHAR2(32767);
 
 l_PartySeacrhCur PartySeacrhTyp;
 l_AccountSeacrhCur AccountSeacrhTyp;
 l_SitesSeacrhCur SiteSeacrhTyp; 
 
 ln_count number := 0; 
 
 begin 
 pso_errmsg := 'NO EXISTEN CLIENTES';
 pso_errcode := '2';
 
 ls_stmt_where := 'WHERE 1=1';
 
 if psi_nombre_cliente is not null then 
 ls_stmt_where:= ls_stmt_where||' AND PARTY_NAME LIKE :nombreCliente';
 else
 ls_stmt_where:= ls_stmt_where||' AND PARTY_NAME = nvl(:nombreCliente,PARTY_NAME)';
 end if; 
 
 if psi_rfc is not null then 
 ls_stmt_where:= ls_stmt_where||' AND RFC LIKE :rfc';
 else
 ls_stmt_where:= ls_stmt_where||' AND RFC = nvl(:rfc,RFC)';
 end if; 
 
 if psi_razon_social is not null then 
 ls_stmt_where:= ls_stmt_where||' AND PARTY_NAME LIKE :razon_social';
 else
 ls_stmt_where:= ls_stmt_where||' AND PARTY_NAME = nvl(:razon_social,PARTY_NAME)';
 end if; 
 
 if psi_estado is not null then 
 ls_stmt_where:= ls_stmt_where||' AND STATE LIKE :estado';
 else
 ls_stmt_where:= ls_stmt_where||' AND STATE = nvl(:estado,STATE)';
 end if; 
 
 ls_stmt_selectr := 'SELECT COUNT(1) FROM XXQP_HzPuiPartySearchRltsVO '||ls_stmt_where;
 
 OPEN l_PartySeacrhCur FOR ls_stmt_selectr USING psi_nombre_cliente,psi_rfc,psi_razon_social,psi_estado;
 -- Fetch rows from result set one at a time:
 LOOP
 FETCH l_PartySeacrhCur INTO ln_count;
 EXIT WHEN l_PartySeacrhCur%NOTFOUND;
 END LOOP;
 -- Close cursor:
 CLOSE l_PartySeacrhCur;
 
 if ln_count = 0 then 
 return;
 end if; 
 
 pso_errmsg := 'NO EXISTEN CLIENTES CON CUENTA';
 pso_errcode := '2';
 
 
 ls_acc_where := ' where parties.party_id = acc.party_id';
 
 if psi_nombre_cliente is not null then 
 ls_acc_where:= ls_acc_where||' AND parties.PARTY_NAME LIKE :nombreCliente';
 else
 ls_acc_where:= ls_acc_where||' AND parties.PARTY_NAME = nvl(:nombreCliente,parties.PARTY_NAME)';
 end if; 
 
 if psi_rfc is not null then 
 ls_acc_where:= ls_acc_where||' AND parties.RFC LIKE :rfc';
 else
 ls_acc_where:= ls_acc_where||' AND parties.RFC = nvl(:rfc,parties.RFC)';
 end if; 
 
 if psi_razon_social is not null then 
 ls_acc_where:= ls_acc_where||' AND parties.PARTY_NAME LIKE :razon_social';
 else
 ls_acc_where:= ls_acc_where||' AND parties.PARTY_NAME = nvl(:razon_social,parties.PARTY_NAME)';
 end if; 
 
 if psi_estado is not null then 
 ls_acc_where:= ls_acc_where||' AND parties.STATE LIKE :estado';
 else
 ls_acc_where:= ls_acc_where||' AND parties.STATE = nvl(:estado,parties.STATE)';
 end if; 
 
 
 ls_acc_select := ' select COUNT(1)
 from XXQP_HzPuiAccountTableVO acc
 ,XXQP_HzPuiPartySearchRltsVO parties '||ls_acc_where;
 
 
 OPEN l_AccountSeacrhCur FOR ls_acc_select USING psi_nombre_cliente,psi_rfc,psi_razon_social,psi_estado;
 -- Fetch rows from result set one at a time:
 LOOP
 FETCH l_AccountSeacrhCur INTO ln_count;
 EXIT WHEN l_AccountSeacrhCur%NOTFOUND;
 END LOOP;
 -- Close cursor:
 CLOSE l_AccountSeacrhCur;
 
 if ln_count = 0 then 
 return;
 end if; 
 
 
 pso_errmsg := 'NO EXISTE INFORMACION DE SUCURSALES PARA LA UNIDAD OPERATIVA SELLECCIONADA';
 pso_errcode := '2'; 
 
 ls_sites_where := 'where parties.party_id = acc.party_id
 and sites.cust_account_id = acc.cust_account_id 
 and sites.org_id =:operating_unit';
 
 if psi_nombre_cliente is not null then 
 ls_sites_where:= ls_sites_where||' AND parties.PARTY_NAME LIKE :nombreCliente';
 else
 ls_sites_where:= ls_sites_where||' AND parties.PARTY_NAME = nvl(:nombreCliente,parties.PARTY_NAME)';
 end if; 
 
 if psi_rfc is not null then 
 ls_sites_where:= ls_sites_where||' AND parties.RFC LIKE :rfc';
 else
 ls_sites_where:= ls_sites_where||' AND parties.RFC = nvl(:rfc,parties.RFC)';
 end if; 
 
 if psi_razon_social is not null then 
 ls_sites_where:= ls_sites_where||' AND parties.PARTY_NAME LIKE :razon_social';
 else
 ls_sites_where:= ls_sites_where||' AND parties.PARTY_NAME = nvl(:razon_social,parties.PARTY_NAME)';
 end if; 
 
 if psi_estado is not null then 
 ls_sites_where:= ls_sites_where||' AND parties.STATE LIKE :estado';
 else
 ls_sites_where:= ls_sites_where||' AND parties.STATE = nvl(:estado,parties.STATE)';
 end if; 
 
 
 ls_sites_select := ' select count(1)
 from XXQP_HzPuiAccountTableVO acc
 ,XXQP_HzPuiPartySearchRltsVO parties
 ,XXQP_HzPuiAcctSitesTableVO sites '||ls_sites_where;
 
 
 OPEN l_SitesSeacrhCur FOR ls_sites_select USING psi_operating_unit,psi_nombre_cliente,psi_rfc,psi_razon_social,psi_estado;
 -- Fetch rows from result set one at a time:
 LOOP
 FETCH l_SitesSeacrhCur INTO ln_count;
 EXIT WHEN l_SitesSeacrhCur%NOTFOUND;
 END LOOP;
 -- Close cursor:
 CLOSE l_SitesSeacrhCur;
 
 if ln_count = 0 then 
 return;
 end if; 
 
 
 pso_errmsg := 'SE ENCONTRO LA INFORMACION CORRECTAMENTE';
 pso_errcode := '0';
 
 
 exception when others then 
 pso_errmsg := 'Exception Others valida_clientes:'||sqlerrm; 
 pso_errcode := '2';
 end valida_clientes; 
 
 procedure rollback_trx(pni_header_id in number) is 
 
 ln_clientes_header_id number := pni_header_id;
 
 begin 
 delete from XXQP_PDFT_CLIENTES_DIR_FISCAL
where header_id = ln_clientes_header_id; 

delete from XXQP_PDFT_CLIENTES_PUNTO_RECO
 where header_id = ln_clientes_header_id; 
 
delete from XXQP_PDFT_CLIENTES_CONTACTOS
 where header_id = ln_clientes_header_id; 

delete from XXQP_PDFT_CLIENTES_FACT_PAG
 where header_id = ln_clientes_header_id; 
 commit; 
delete from XXQP_PDFT_CLIENTES_HEADER
 where id = ln_clientes_header_id; 
 commit; 
 exception when others then 
 null; 
 end rollback_trx; 
 
 procedure from_oracle_to_pdft(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_party_id in number
 ,pni_operating_unit in number
 ,pno_clientes_header_id out number
 ) is 
 
 CURSOR get_organization_info (cur_party_id number) IS
 select hp.party_id
 ,hp.known_as
 ,hp.party_name
 ,hp.attribute_category
 ,hp.attribute1
 ,hp.attribute2
 from hz_parties hp
 , hz_organization_profiles hop
 where 1=1
 and hp.party_type in ('ORGANIZATION')
 and hop.party_id = hp.party_id
 and trunc(sysdate) between nvl(hop.effective_start_date,trunc(sysdate)) and nvl(hop.effective_end_date,to_date ('31/12/4712','DD/MM/YYYY'))
 and hp.party_id = cur_party_id; 
 
 CURSOR get_location_info(cur_party_id number) IS
 select /** hpsu.party_site_use_id, hpsu.party_site_id, hpsu.site_use_type, hpsu.primary_per_type ,hpsu.status,hpsu.object_version_number **/
 /** hzp.party_site_id, hzp.party_id, hzp.location_id, hzp.party_site_number,hzp.status,hzp.party_site_name,hzp.object_version_number **/
 hl.location_id, hl.address1, hl.address2, hl.address3,hl.address4,hl.city, hl.state, hl.postal_code, hl.country, hl.object_version_number, hl.created_by_module,hzp.PARTY_SITE_ID
from hz_party_site_uses hpsu
 , hz_party_sites hzp
 , hz_locations hl
 where hpsu.party_site_id = hzp.party_site_id
 and hzp.party_id = cur_party_id
 and hpsu.site_use_type = 'BILL_TO'
 and hl.location_id = hzp.location_id
 and hzp.IDENTIFYING_ADDRESS_FLAG = 'Y';
 
 
CURSOR get_cust_account_info (cur_party_id number) IS
 select hca.cust_account_id, hca.party_id, hca.account_number, hca.account_name,hca.attribute_category,hca.attribute1,hca.attribute2,hca.attribute3,hca.attribute4,hca.attribute5,hca.attribute6,hca.orig_system_reference
 from hz_cust_accounts hca
 where party_id = cur_party_id; 
 
 
 
 
 
 
 organization_info_rec get_organization_info%ROWTYPE;
 location_info_rec get_location_info%ROWTYPE; 
 cust_account_info_rec get_cust_account_info%ROWTYPE;
 contactos_tmp_info_rec get_contactos_tmp_info%ROWTYPE;
 
 
 ln_clientes_header_id number; 
 ln_dir_fiscal_id number;
 ln_punto_reco_id number; 
 ln_clientes_contactos_id number;
 ln_clientes_fact_pag_id number; 
 
 
 le_from_oracle_to_pdft exception;
 
 ln_prim_org_id number; 
 ln_legal_entity_id number; 
 
 ls_errmsg varchar2(2000); 
 ls_errcod varchar2(2000);
 ln_xxclient_id number; 
 
 begin 
 pso_errmsg := null; 
 pso_errcode := '0';
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(1) -> pni_party_id:'||pni_party_id||' pni_operating_unit:'||pni_operating_unit); 
 commit;
 /**********************************************************
 Comienza Inicializacion 
 *********************************************************/
 begin 
 
 select id 
 into ln_xxclient_id
 from XXQP_PDFT_CLIENTES_HEADER
 where party_id = pni_party_id; 
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(1.1) ->ln_xxclient_id:'||ln_xxclient_id); 
 commit;
 
 
 APPS.XXQP_PDFT_CLIENTES_FOTP_PKG.upd_header(pso_errmsg => ls_errmsg
 ,pso_errcode => ls_errcod
 ,pni_party_id => pni_party_id
 ,pni_operating_unit => pni_operating_unit
 ,pni_clientes_header_id => ln_xxclient_id
 ); 
 
 pno_clientes_header_id := ln_xxclient_id;
 
 exception when no_data_found then 
 
 XXQP_PDFT_CLIENTES_FOTP_PKG. populate_header(pso_errmsg => ls_errmsg
 ,pso_errcode => ls_errcod
 ,pni_party_id => pni_party_id
 ,pni_operating_unit => pni_operating_unit
 ,pno_clientes_header_id => ln_clientes_header_id
 ); 
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(1.2) -> ln_clientes_header_id:'||ln_clientes_header_id); 
 commit;
 
 pno_clientes_header_id := ln_clientes_header_id;
 
 
 end; 
 
 
 return; 
 
 
 
 

 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6):'); 
 
 /*********************************************************
 Finaliza Inicializacion 
 *********************************************************/
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6)pni_party_id:'||pni_party_id); 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6)ln_clientes_header_id:'||ln_clientes_header_id); 

 OPEN get_organization_info(pni_party_id);
 LOOP
 FETCH get_organization_info INTO organization_info_rec;
 EXIT WHEN get_organization_info%NOTFOUND;
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6.1)'); 
 
 
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6.3)'); 
 commit;
 
 END LOOP;
 CLOSE get_organization_info;
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(7):'); 
 commit; 
 
 OPEN get_cust_account_info(pni_party_id);
 LOOP
 FETCH get_cust_account_info INTO cust_account_info_rec;
 EXIT WHEN get_cust_account_info%NOTFOUND;
 
 update XXQP_PDFT_CLIENTES_FACT_PAG 
 set USO_DEL_CFDI_C = cust_account_info_rec.attribute6
 ,METODO_DE_PAGO_C = cust_account_info_rec.attribute5 
       where  HEADER_ID = ln_clientes_header_id
          and id = ln_clientes_fact_pag_id; 
           commit; 
           
      END LOOP;
      CLOSE get_cust_account_info;
      
      insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(8)->pni_party_id:'||pni_party_id); 
      commit; 
   
    OPEN get_location_info(pni_party_id);
   LOOP
      FETCH get_location_info INTO location_info_rec;
      EXIT WHEN get_location_info%NOTFOUND;
      
      
      XXQP_PDFT_CLIENTES_FOTP_PKG.val_insert_mgr_catalogos( psi_lookup_type  => 'ESTADO'
                                                                                               ,psi_lookup_code => location_info_rec.state  );
      XXQP_PDFT_CLIENTES_FOTP_PKG.val_insert_mgr_catalogos( psi_lookup_type  => 'CODIGO_POSTAL'
                                                                                               ,psi_lookup_code =>  location_info_rec.postal_code
                                                                                                );                                                    
      update XXQP_PDFT_CLIENTES_DIR_FISCAL 
           set PRIM_RFC = nvl(organization_info_rec.attribute1,cust_account_info_rec.attribute1)
               ,PRIM_RAZON_SOCIAL = organization_info_rec.party_name
               ,PRIM_DIRECCION  = location_info_rec.address1
               ,PRIM_COLONIA = location_info_rec.address4  
               ,PRIM_CIUDAD_O_MPO = location_info_rec.city  
               ,PRIM_ESTADO_C = location_info_rec.state  
               ,PRIM_CODIGO_POSTAL_C = location_info_rec.postal_code
               ,PRIM_NUMERO_INT = location_info_rec.address2
               ,PRIM_NUMERO_EXT =  location_info_rec.address3
            where HEADER_ID = ln_clientes_header_id
               and id = ln_dir_fiscal_id; 
               commit; 
             
             insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(8.1) -> cust_account_id:'||cust_account_info_rec.cust_account_id||', party_site_id:'||location_info_rec.party_site_id); 
             insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(8.1) -> location_id:'||location_info_rec.location_id); 
             commit;   
               
           if cust_account_info_rec.cust_account_id is not null then 
              select org_id 
                 into ln_prim_org_id 
               from HZ_CUST_ACCT_SITES
             where cust_account_id = cust_account_info_rec.cust_account_id
                and PARTY_SITE_ID = location_info_rec.party_site_id
                and org_id = pni_operating_unit;
               
                update XXQP_PDFT_CLIENTES_DIR_FISCAL 
           set PRIM_OPERATING_UNIT = ln_prim_org_id
            where HEADER_ID = ln_clientes_header_id
               and id = ln_dir_fiscal_id; 
               commit; 
               
          end if;     
      
       
   END LOOP;
   CLOSE get_location_info;
    insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(9):'); 
    commit; 
   
  

 XXQP_PDFT_CLIENT_FPTO_UPD_PKG.upd_from_pdft_to_oracle(pso_errmsg                    => ls_errmsg
                                                                                            ,pso_errcode                   => ls_errcod
                                                                                            ,pni_party_id                   => pni_party_id
                                                                                            ,pni_cliente_header_id     => ln_clientes_header_id
                                                                                            );       

  
  exception when le_from_oracle_to_pdft then 
     pso_errmsg := 'Exception le_from_oracle_to_pdft'||pso_errmsg;
     pso_errcode := '2';
      insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(Exception le_from_oracle_to_pdft) -> pni_party_id:'||pni_party_id); 
       insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,pso_errmsg); 
             commit;   
    rollback_trx(ln_clientes_header_id);
  
   when others then 
   pso_errmsg := 'Excepcion metodo from_oracle_to_pdft'||sqlerrm; 
  pso_errcode := '2';
    insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(Exception others) -> pni_party_id:'||pni_party_id); 
       insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,pso_errmsg); 
             commit;   
  rollback_trx(ln_clientes_header_id);
  
  end  from_oracle_to_pdft; 
  
  
  procedure populate_header(pso_errmsg                    out varchar2
                                          ,pso_errcode                    out varchar2
                                          ,pni_party_id                    in  number
                                          ,pni_operating_unit           in number
                                          ,pno_clientes_header_id    out number
                                            ) is 
  
   CURSOR get_parties_info(cur_party_id  number) IS
      select party_name
               ,rfc
               ,known_as
        from XXQP_HzPuiPartySearchRltsVO
       where party_id = cur_party_id;
    
    parties_info_rec get_parties_info%ROWTYPE;                                          
                                            
  ln_clientes_header_id  number; 
  ln_legal_entity_id        number; 
  ln_location_id             number;
  ls_errmsg                  varchar2(2000); 
  ls_errcod                   varchar2(2000); 
  ln_dir_fiscal_id           number; 
  ln_punto_rec_id          number; 
  ln_val_client_contacts  number; 
  ln_fact_pag_id            number; 
  
  begin 
  
     OPEN get_parties_info(pni_party_id);
     LOOP
        FETCH get_parties_info INTO parties_info_rec;
        EXIT WHEN get_parties_info%NOTFOUND;
        
         ln_clientes_header_id := XXQP_PDFT_CLIENTES_HEADER_S.nextval; 
         
              select distinct  LEP.LEGAL_ENTITY_ID
                 into ln_legal_entity_id
                 from XLE_ENTITY_PROFILES LEP,
                         XLE_REGISTRATIONS REG,
                         HR_LOCATIONS_ALL HRL,
                         HZ_PARTIES HZP,
                         FND_TERRITORIES_VL TER,
                         HR_OPERATING_UNITS HRO,
                         HR_ALL_ORGANIZATION_UNITS_TL HROUTL_BG,
                         HR_ALL_ORGANIZATION_UNITS_TL HROUTL_OU,
                         HR_ORGANIZATION_UNITS GLOPERATINGUNITSEO,
                         GL_LEGAL_ENTITIES_BSVS GLEV
            where LEP.TRANSACTING_ENTITY_FLAG      = 'Y'
            and LEP.PARTY_ID                       = HZP.PARTY_ID
            and LEP.LEGAL_ENTITY_ID                = REG.SOURCE_ID
            and REG.SOURCE_TABLE                   = 'XLE_ENTITY_PROFILES'
            and HRL.LOCATION_ID                    = REG.LOCATION_ID
            and REG.IDENTIFYING_FLAG               = 'Y'
            and TER.TERRITORY_CODE                 = HRL.COUNTRY
            and LEP.LEGAL_ENTITY_ID                = HRO.DEFAULT_LEGAL_CONTEXT_ID
            and GLOPERATINGUNITSEO.ORGANIZATION_ID = HRO.ORGANIZATION_ID
            and HROUTL_BG.ORGANIZATION_ID          = HRO.BUSINESS_GROUP_ID
            and HROUTL_OU.ORGANIZATION_ID          = HRO.ORGANIZATION_ID
            and GLEV.LEGAL_ENTITY_ID               = LEP.LEGAL_ENTITY_ID
            and HROUTL_OU.ORGANIZATION_ID = pni_operating_unit;

         
    
    insert into XXQP_PDFT_CLIENTES_HEADER (ID                     /** NUMBER,                             **/
                                                                  ,NOMBRE_CLIENTE         /** VARCHAR2(250 BYTE)     NOT NULL,    **/ 
                                                                  ,GIRO_EMPRESARIAL_C     /** VARCHAR2(250 BYTE),                 **/
                                                                  ,EMPRESA_QUE_FACTURA_C  /** VARCHAR2(250 BYTE),**/
                                                                  ,TIPO_OPERATIVO_C       /** VARCHAR2(250 BYTE),**/
                                                                  ,TIPO_ADMINISTRATIVO_C  /** VARCHAR2(250 BYTE),**/
                                                                  ,TIPO_COMERCIAL_C       /** VARCHAR2(250 BYTE),**/
                                                                  ,COMENTARIOS            /** VARCHAR2(1000 BYTE),**/
                                                                  ,CREATED_BY             /** NUMBER(15)             NOT NULL,**/
                                                                  ,CREATION_DATE          /** DATE                   NOT NULL,**/
                                                                  ,LAST_UPDATED_BY        /** NUMBER(15)             NOT NULL,**/
                                                                  ,LAST_UPDATE_DATE       /** DATE                   NOT NULL,**/
                                                                  ,LAST_UPDATE_LOGIN      /** NUMBER(15),**/
                                                                  ,ATTRIBUTE_CATEGORY     /** VARCHAR2(250 BYTE),**/
                                                                  ,ATTRIBUTE1             /** VARCHAR2(2000 BYTE),**/
                                                                  ,ATTRIBUTE2             /** VARCHAR2(2000 BYTE),**/
                                                                  ,ATTRIBUTE3             /** VARCHAR2(2000 BYTE),**/
                                                                  ,ATTRIBUTE4             /** VARCHAR2(2000 BYTE),**/
                                                                  ,ATTRIBUTE5             /** VARCHAR2(2000 BYTE),**/
                                                                  ,PARTY_ID               /** NUMBER,**/
                                                                  ,RFC                    /** VARCHAR2(50 BYTE)      NOT NULL**/
                                                                  ,RAZON_SOCIAL  /** VARCHAR2(500 BYTE) NOT NULL **/
                                                                  ) values (
                                                                                 ln_clientes_header_id                  /** ID                     **/                                                                                                                                                                            
                                                                                ,parties_info_rec.party_name    /** NOMBRE_CLIENTE         **/                                                                                                                                                                            
                                                                                ,null     /** GIRO_EMPRESARIAL_C     **/                                                                                                                                                                            
                                                                                ,ln_legal_entity_id     /** EMPRESA_QUE_FACTURA_C  **/                                                                                                                                                                            
                                                                                ,null     /** TIPO_OPERATIVO_C       **/                                                                                                                                                                            
                                                                                ,null     /** TIPO_ADMINISTRATIVO_C  **/                                                                                                                                                                            
                                                                                ,null     /** TIPO_COMERCIAL_C       **/                                                                                                                                                                            
                                                                                ,null     /** COMENTARIOS            **/                                                                                                                                                                            
                                                                                ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
                                                                                ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
                                                                                ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
                                                                                ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
                                                                                ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/                                                                                                                                                                            
                                                                                ,null     /** ATTRIBUTE_CATEGORY     **/                                                                                                                                                                            
                                                                                ,'Created from_oracle_to_pdft'     /** ATTRIBUTE1             **/                                                                                                                                                                            
                                                                                ,null     /** ATTRIBUTE2             **/                                                                                                                                                                            
                                                                                ,null     /** ATTRIBUTE3             **/                                                                                                                                                                            
                                                                                ,null     /** ATTRIBUTE4             **/                                                                                                                                                                            
                                                                                ,null     /** ATTRIBUTE5             **/
                                                                                ,pni_party_id   /*** PARTY_ID **/
                                                                                ,parties_info_rec.rfc /** RFC NOT NULL **/
                                                                                ,parties_info_rec.known_as /** RAZON_SOCIAL **/
                                                                                );       
                                                                    commit;       
   
        
     END LOOP;
     CLOSE get_parties_info;
  
     begin 
     
       select id
          into ln_dir_fiscal_id
         from XXQP_PDFT_CLIENTES_DIR_FISCAL
      where header_id = ln_clientes_header_id
          and prim_operating_unit =  pni_operating_unit;
           
     exception when no_data_found then 
     
      XXQP_PDFT_CLIENTES_FOTP_PKG.populate_sites( pso_errmsg                   => ls_errmsg
                                                                               ,pso_errcode                  => ls_errcod
                                                                               ,pni_party_id                  => pni_party_id
                                                                               ,pni_operating_unit         => pni_operating_unit
                                                                               ,pni_clientes_header_id   => ln_clientes_header_id
                                                                               ,pno_dir_fiscal_id            => ln_dir_fiscal_id
                                                                               );   
     
     end; 
     
     begin 
     
      select id 
         into ln_punto_rec_id
        from XXQP_PDFT_CLIENTES_PUNTO_RECO
       where header_id = ln_clientes_header_id; 
     
     exception when no_data_found then 
      XXQP_PDFT_CLIENTES_FOTP_PKG.populate_punto_rec( 
                                                                               pso_errmsg                   => ls_errmsg
                                                                               ,pso_errcode                  => ls_errcod
                                                                               ,pni_party_id                  => pni_party_id
                                                                               ,pni_operating_unit         => pni_operating_unit
                                                                               ,pni_clientes_header_id   => ln_clientes_header_id
                                                                               ,pno_punto_rec_id           => ln_punto_rec_id
                                                                               );
     end; 
     
       
     select count(1) 
        into ln_val_client_contacts
       from XXQP_PDFT_CLIENTES_CONTACTOS
     where header_id = ln_clientes_header_id; 
     
     if 0 = ln_val_client_contacts then 
          XXQP_PDFT_CLIENTES_FOTP_PKG.populate_contacts( 
                                                                                       pso_errmsg                   => ls_errmsg
                                                                                       ,pso_errcode                  => ls_errcod
                                                                                       ,pni_party_id                  => pni_party_id
                                                                                       ,pni_operating_unit         => pni_operating_unit
                                                                                       ,pni_clientes_header_id   => ln_clientes_header_id
                                                                                       );
     end if; 
     
     begin 
     
     select id 
       into ln_fact_pag_id
       from XXQP_PDFT_CLIENTES_FACT_PAG
     where header_id = ln_clientes_header_id; 
     
     exception when no_data_found then 
        XXQP_PDFT_CLIENTES_FOTP_PKG.populate_fact_pag( 
                                                                                        pso_errmsg                   => ls_errmsg
                                                                                       ,pso_errcode                  => ls_errcod
                                                                                       ,pni_party_id                  => pni_party_id
                                                                                       ,pni_operating_unit         => pni_operating_unit
                                                                                       ,pni_clientes_header_id   => ln_clientes_header_id
                                                                                       ,pno_fact_pag_id            => ln_fact_pag_id
                                                                                       );
     end; 
     
     
       
   pno_clientes_header_id := ln_clientes_header_id; 
  exception when others then 
     pso_errmsg := 'Exception when others populate_header:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
  end populate_header; 
  
   procedure upd_header(pso_errmsg                    out varchar2
                                     ,pso_errcode                    out varchar2
                                     ,pni_party_id                    in  number
                                     ,pni_operating_unit           in number
                                     ,pni_clientes_header_id    in  number
                                     ) is 
  
  ln_legal_entity_id      number; 
  ln_dir_fiscal_id          number; 
  ls_errmsg                  varchar2(2000); 
  ls_errcod                   varchar2(2000); 
    
  begin 
   
       select distinct  LEP.LEGAL_ENTITY_ID
                 into ln_legal_entity_id
                 from XLE_ENTITY_PROFILES LEP,
                         XLE_REGISTRATIONS REG,
                         HR_LOCATIONS_ALL HRL,
                         HZ_PARTIES HZP,
                         FND_TERRITORIES_VL TER,
                         HR_OPERATING_UNITS HRO,
                         HR_ALL_ORGANIZATION_UNITS_TL HROUTL_BG,
                         HR_ALL_ORGANIZATION_UNITS_TL HROUTL_OU,
                         HR_ORGANIZATION_UNITS GLOPERATINGUNITSEO,
                         GL_LEGAL_ENTITIES_BSVS GLEV
            where LEP.TRANSACTING_ENTITY_FLAG      = 'Y'
            and LEP.PARTY_ID                       = HZP.PARTY_ID
            and LEP.LEGAL_ENTITY_ID                = REG.SOURCE_ID
            and REG.SOURCE_TABLE                   = 'XLE_ENTITY_PROFILES'
            and HRL.LOCATION_ID                    = REG.LOCATION_ID
            and REG.IDENTIFYING_FLAG               = 'Y'
            and TER.TERRITORY_CODE                 = HRL.COUNTRY
            and LEP.LEGAL_ENTITY_ID                = HRO.DEFAULT_LEGAL_CONTEXT_ID
            and GLOPERATINGUNITSEO.ORGANIZATION_ID = HRO.ORGANIZATION_ID
            and HROUTL_BG.ORGANIZATION_ID          = HRO.BUSINESS_GROUP_ID
            and HROUTL_OU.ORGANIZATION_ID          = HRO.ORGANIZATION_ID
            and GLEV.LEGAL_ENTITY_ID               = LEP.LEGAL_ENTITY_ID
            and HROUTL_OU.ORGANIZATION_ID = pni_operating_unit;
 
    update XXQP_PDFT_CLIENTES_HEADER
         set EMPRESA_QUE_FACTURA_C = ln_legal_entity_id
     where id = pni_clientes_header_id; 
     
      begin 
     
       select id
          into ln_dir_fiscal_id
         from XXQP_PDFT_CLIENTES_DIR_FISCAL
      where header_id = pni_clientes_header_id
          and prim_operating_unit =  pni_operating_unit;
           
     exception when no_data_found then 
     
      XXQP_PDFT_CLIENTES_FOTP_PKG.populate_sites( pso_errmsg                   => ls_errmsg
                                                                               ,pso_errcode                  => ls_errcod
                                                                               ,pni_party_id                  => pni_party_id
                                                                               ,pni_operating_unit         => pni_operating_unit
                                                                               ,pni_clientes_header_id   => pni_clientes_header_id
                                                                               ,pno_dir_fiscal_id            => ln_dir_fiscal_id
                                                                               );   
     
     end; 
    
     
   exception when others then 
     pso_errmsg := 'Exception when others populate_header:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
  end upd_header; 
  
  
   procedure populate_sites( pso_errmsg                    out varchar2
                                        ,pso_errcode                    out varchar2
                                        ,pni_party_id                    in  number
                                        ,pni_operating_unit           in number
                                        ,pni_clientes_header_id    in number
                                        ,pno_dir_fiscal_id             out number
                                        ) is
   
     CURSOR get_sites_info(cur_party_id            number
                                      ,cur_operating_unit   number) IS
     select sites.location_id
              ,parties.party_name
              ,sites.address
      from XXQP_HzPuiAccountTableVO acc
             ,XXQP_HzPuiPartySearchRltsVO parties
            ,XXQP_HzPuiAcctSitesTableVO sites
      where parties.party_id = acc.party_id
          and sites.cust_account_id = acc.cust_account_id 
          and sites.org_id =cur_operating_unit
          and  parties.party_id = cur_party_id ; 
           
    sites_info_rec get_sites_info%ROWTYPE;
    
    ln_dir_fiscal_id  number; 
   
   begin 
   
        OPEN get_sites_info(pni_party_id
                                    ,pni_operating_unit
                                    );
        LOOP
           FETCH get_sites_info INTO sites_info_rec;
           EXIT WHEN get_sites_info%NOTFOUND;
           
   
      insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'populate_sites(1)-> location_id:'||sites_info_rec.location_id); 
      
      /*
       update XXQP_PDFT_CLIENTES_DIR_FISCAL 
           set PRIM_RFC = nvl(organization_info_rec.attribute1,cust_account_info_rec.attribute1)
               ,PRIM_RAZON_SOCIAL = organization_info_rec.party_name
               ,PRIM_DIRECCION  = location_info_rec.address1
               ,PRIM_COLONIA = location_info_rec.address4  
               ,PRIM_CIUDAD_O_MPO = location_info_rec.city  
               ,PRIM_ESTADO_C = location_info_rec.state  
               ,PRIM_CODIGO_POSTAL_C = location_info_rec.postal_code
               ,PRIM_NUMERO_INT = location_info_rec.address2
               ,PRIM_NUMERO_EXT =  location_info_rec.address3
            where HEADER_ID = ln_clientes_header_id
               and id = ln_dir_fiscal_id; 
              */
            
          
           
              ln_dir_fiscal_id   := XXQP_PDFT_CLIENTES_DIR_FISC_S.nextval;
             insert into XXQP_PDFT_CLIENTES_DIR_FISCAL values (ln_dir_fiscal_id          /** ID                        **/
                                                                                    ,pni_clientes_header_id          /** HEADER_ID                 **/
                                                                                    ,null        /** PRIM_RFC                  **/
                                                                                    ,sites_info_rec.party_name          /** PRIM_RAZON_SOCIAL         **/
                                                                                    ,null       /** PRIM_DIRECCION            **/
                                                                                    ,null          /** PRIM_ENTRE_CALLES         **/
                                                                                    ,null       /** PRIM_COLONIA              **/
                                                                                    ,null      /** PRIM_CIUDAD_O_MPO         **/
                                                                                    ,null         /** PRIM_ESTADO_C             **/
                                                                                    ,null      /** PRIM_CODIGO_POSTAL_C      **/
                                                                                    ,null          /** PRIM_CEDULA               **/
                                                                                    ,null          /** PRIM_CEDULA_FILE          **/
                                                                                    ,null          /** SEC_RFC                   **/
                                                                                    ,null          /** SEC_RAZON_SOCIAL          **/
                                                                                    ,null          /** SEC_DIRECCION             **/
                                                                                    ,null          /** SEC_ENTRE_CALLES          **/
                                                                                    ,null          /** SEC_COLONIA               **/
                                                                                    ,null          /** SEC_CIUDAD_O_MPO          **/
                                                                                    ,null          /** SEC_ESTADO_C              **/
                                                                                    ,null          /** SEC_CODIGO_POSTAL_C       **/
                                                                                    ,null          /** SEC_CEDULA                **/
                                                                                    ,null          /** SEC_CEDULA_FILE           **/
                                                                                    ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
                                                                                    ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
                                                                                    ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
                                                                                    ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
                                                                                    ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/        
                                                                                    ,null          /** ATTRIBUTE_CATEGORY        **/
                                                                                    ,null          /** ATTRIBUTE1                **/
                                                                                    ,null          /** ATTRIBUTE2                **/
                                                                                    ,null          /** ATTRIBUTE3                **/
                                                                                    ,null          /** ATTRIBUTE4                **/
                                                                                    ,null          /** ATTRIBUTE5                **/
                                                                                    ,null          /** PRIM_CEDULA_FILE_NAME     **/
                                                                                    ,null          /** PRIM_CEDULA_CONTENT_TYPE  **/
                                                                                    ,null          /** SEC_CEDULA_FILE_NAME      **/
                                                                                    ,null          /** SEC_CEDULA_CONTENT_TYPE   **/
                                                                                    ,pni_operating_unit          /** PRIM_OPERATING_UNIT   **/
                                                                                    ,null          /** SEC_OPERATING_UNIT   **/
                                                                                    ,null          /** PRIM_NUMERO_EXT   **/
                                                                                    ,null            /** SEC_NUMERO_EXT   **/
                                                                                    ,null            /**  PRIM_NUMERO_INT   **/       
                                                                                    ,null            /** SEC_NUMERO_INT  **/
                                                                                    );
       
             commit;
       
       
           for idx in ( select address1
                                   ,address2
                                   ,address3
                                   ,address4
                                   ,city
                                   ,state
                                   ,postal_code
                             from hz_locations 
             where location_id = sites_info_rec.location_id ) loop
              update XXQP_PDFT_CLIENTES_DIR_FISCAL
                 set  PRIM_DIRECCION = idx.address1
                      ,PRIM_COLONIA =  idx.address4
                      ,PRIM_CIUDAD_O_MPO = idx.city
                      ,PRIM_ESTADO_C = idx.state
                      ,PRIM_CODIGO_POSTAL_C = idx.postal_code
                      ,PRIM_NUMERO_INT = idx.address2
                     ,PRIM_NUMERO_EXT =  idx.address3
               where id =  ln_dir_fiscal_id; 
              
               commit; 
                        
           end loop; 
       
             
        END LOOP;
        CLOSE get_sites_info;
        
        pno_dir_fiscal_id := ln_dir_fiscal_id; 
   
   exception when others then 
     pso_errmsg := 'Exception when others populate_sites:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
   end populate_sites; 
  
   
   procedure populate_punto_rec( pso_errmsg                    out varchar2
                                                 ,pso_errcode                    out varchar2
                                                 ,pni_party_id                    in  number
                                                 ,pni_operating_unit           in number
                                                 ,pni_clientes_header_id    in number
                                                 ,pno_punto_rec_id            out number
                                                 )  is
  ln_punto_reco_id    number;
   begin 
   
       insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(2):'); 
          
   ln_punto_reco_id := XXQP_PDFT_CLIENTES_PUNT_RECO_S.nextval;
   
insert into XXQP_PDFT_CLIENTES_PUNTO_RECO values(   ln_punto_reco_id     /** ID                  **/
                                                                                    ,pni_clientes_header_id     /** HEADER_ID           **/
                                                                                    ,null     /** PRIM_ENTREGA_EN_QP  **/
                                                                                    ,null     /** PRIM_NOMBRE         **/
                                                                                    ,null     /** PRIM_CONTACTO       **/
                                                                                    ,null     /** PRIM_DIRECCION      **/
                                                                                    ,null     /** PRIM_DIA            **/
                                                                                    ,null     /** PRIM_HORARIO        **/
                                                                                    ,null     /** SEC_ENTREGA_EN_QP   **/
                                                                                    ,null     /** SEC_NOMBRE          **/
                                                                                    ,null     /** SEC_CONTACTO        **/
                                                                                    ,null     /** SEC_DIRECCION       **/
                                                                                    ,null     /** SEC_DIA             **/
                                                                                    ,null     /** SEC_HORARIO         **/
                                                                                    ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
                                                                                    ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
                                                                                    ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
                                                                                    ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
                                                                                    ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/        
                                                                                    ,null     /** ATTRIBUTE_CATEGORY  **/
                                                                                    ,null     /** ATTRIBUTE1          **/
                                                                                    ,null     /** ATTRIBUTE2          **/
                                                                                    ,null     /** ATTRIBUTE3          **/
                                                                                    ,null     /** ATTRIBUTE4          **/
                                                                                    ,null     /** ATTRIBUTE5          **/
                                                                                    );
   
   commit; 
   
    exception when others then 
     pso_errmsg := 'Exception when others populate_punto_rec:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
   end populate_punto_rec; 
                                                 
   
   procedure populate_contacts( pso_errmsg                    out varchar2
                                                ,pso_errcode                    out varchar2
                                                ,pni_party_id                    in  number
                                                ,pni_operating_unit           in number
                                                ,pni_clientes_header_id    in number
                                                ) is 
  
  contactos_tmp_info_rec      get_contactos_tmp_info%ROWTYPE;
  ln_clientes_contactos_id      number; 
 
 begin 
   
           insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(3):'); 
     
     
    OPEN get_contactos_tmp_info;
   LOOP
      FETCH get_contactos_tmp_info INTO contactos_tmp_info_rec;
      EXIT WHEN get_contactos_tmp_info%NOTFOUND;
      
      ln_clientes_contactos_id    := XXQP_PDFT_CLIENTES_CONTACTOS_S.nextval; 
      
      insert into XXQP_PDFT_CLIENTES_CONTACTOS 
      values (ln_clientes_contactos_id         /** ID                  **/
                ,pni_clientes_header_id            /** HEADER_ID           **/
                ,contactos_tmp_info_rec.tipo_de_contacto      /** TIPO_CONTACTO       **/
                ,null      /** NOMBRE              **/
                ,null      /** DIRECCION           **/
                ,null      /** TELEFONO            **/
                ,null      /** CORREO_ELECTRONICO  **/
                ,null      /** PUESTO              **/
                ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
                ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
                ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
                ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
                ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/       
                ,null      /** ATTRIBUTE_CATEGORY  **/
                ,null      /** ATTRIBUTE1          **/
                ,null      /** ATTRIBUTE2          **/
                ,null      /** ATTRIBUTE3          **/
                ,null      /** ATTRIBUTE4          **/
                ,null      /** ATTRIBUTE5          **/
                ,null     /** NUMERO_CELULAR **/
                );
          
      commit;      
     
   END LOOP;
   CLOSE get_contactos_tmp_info;
  
   
   exception when others then 
     pso_errmsg := 'Exception when others populate_contacts:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
   end populate_contacts;                                             
   
   procedure populate_fact_pag( pso_errmsg                    out varchar2
                                                ,pso_errcode                    out varchar2
                                                ,pni_party_id                    in  number
                                                ,pni_operating_unit           in number
                                                ,pni_clientes_header_id    in number
                                                ,pno_fact_pag_id             out number
                                                ) is 
   ln_clientes_fact_pag_id  number;  
   begin 
    
         insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(4):'); 
     
    ln_clientes_fact_pag_id   := XXQP_PDFT_CLIENTES_FACT_PAG_S.nextval; 
   
insert into XXQP_PDFT_CLIENTES_FACT_PAG values (
                                                                                  ln_clientes_fact_pag_id       /** ID                      **/
                                                                                , pni_clientes_header_id       /** HEADER_ID               **/
                                                                                , null       /** CONDICIONES_DE_PAGO_C   **/
                                                                                , null       /** OBSERVACIONES           **/
                                                                                , null       /** TIPO_DE_PAGO_C          **/
                                                                                , null       /** REQUIERE_ADENDAS_C      **/
                                                                                , null       /** REQUIERE_FACTURA_C      **/
                                                                                , null       /** MOTIVO                  **/
                                                                                , null       /** CICLO_DE_FACTURACION_C  **/
                                                                                , null      /** USO_DEL_CFDI_C          **/
                                                                                , null      /** METODO_DE_PAGO_C        **/
                                                                                , null       /** NUMERO_DE_CUENTA        **/
                                                                                , null       /** NOMBRE_DEL_BANCO        **/
                                                                                , null       /** DIAS_NAT_DE_CREDITO     **/
                                                                                , null       /** DIAS_RECEPCION_FACTUR   **/
                                                                                , null       /** UTILIZA_PORTAL_C        **/
                                                                                , null       /** PORTAL_LINK             **/
                                                                                , null       /** ORDEN_DE_COMPRA_C       **/
                                                                                , null       /** CONTRATO_C              **/
                                                                                , null       /** VIGENCIA_CONTRATO       **/
                                                                                ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
                                                                                ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
                                                                                ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
                                                                                ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
                                                                                ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/  
                                                                                , null       /** ATTRIBUTE_CATEGORY      **/
                                                                                , null       /** ATTRIBUTE1              **/
                                                                                , null       /** ATTRIBUTE2              **/
                                                                                , null       /** ATTRIBUTE3              **/
                                                                                , null       /** ATTRIBUTE4              **/
                                                                                , null       /** ATTRIBUTE5              **/
                                                                                , null /** LUNES **/
                                                                                ,null 
                                                                                ,null
                                                                                ,null
                                                                                ,null
                                                                                ,null
                                                                                ,null
                                                                                ); 

      commit;  
    
    exception when others then 
      pso_errmsg := 'Exception when others populate_contacts:'||sqlerrm||', '||sqlcode;
     pso_errcode := '2';
    end populate_fact_pag; 
    
   
  procedure val_insert_mgr_catalogos( psi_lookup_type  in varchar2
                                                       ,psi_lookup_code  in varchar2  )
  is 
   ln_max_numero_linea    number; 
   ls_insert_flag                varchar2(2000); 
  begin
  
      select max(NUMERO_LINEA)
       into ln_max_numero_linea
       from xxqp_pdft_mgr_catalogos where  lookup_type = psi_lookup_type; 
      
    select 'Y'
      into ls_insert_flag
   from xxqp_pdft_mgr_catalogos
  where lookup_type = psi_lookup_type
    and lookup_code =psi_lookup_code; 
    
  exception when no_data_found then 
  
     if ln_max_numero_linea is not null then
     insert into xxqp_pdft_mgr_catalogos values 
       (XXQP_PDFT_MGR_CATALOGOS_S.nextval         /** ID                  **/
        ,ln_max_numero_linea         /** NUMERO_LINEA       ORA-01400: no se puede realizar una insercion NULL  **/
        ,psi_lookup_type         /** LOOKUP_TYPE         **/
        ,psi_lookup_code         /** LOOKUP_CODE         **/
        ,psi_lookup_code         /** DESCRIPTION         **/
        ,'Y'         /** STATUS              **/
         ,nvl(fnd_profile.value('USER_ID'),0)      /** CREATED_BY             **/                                                                                                                                                                            
        ,sysdate                                             /** CREATION_DATE          **/                                                                                                                                                                            
        ,nvl(fnd_profile.value('USER_ID'),0)      /** LAST_UPDATED_BY        **/                                                                                                                                                                            
        ,sysdate                                             /** LAST_UPDATE_DATE       **/                                                                                                                                                                            
        ,nvl(fnd_profile.value('LOGIN_ID'),0)      /** LAST_UPDATE_LOGIN      **/  
        ,null         /** ATTRIBUTE_CATEGORY  **/
        ,'FROM_ORACLE_AR'         /** ATTRIBUTE1          **/
        ,null         /** ATTRIBUTE2          **/
        ,null         /** ATTRIBUTE3          **/
        ,null         /** ATTRIBUTE4          **/
        ,null         /** ATTRIBUTE5          **/
        );
     commit;   
   end if; 
  
  when others then 
   null; 
  end; 
   
  
  end  XXQP_PDFT_CLIENTES_FOTP_PKG;
/


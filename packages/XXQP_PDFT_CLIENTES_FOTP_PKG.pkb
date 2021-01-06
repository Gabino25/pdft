CREATE OR REPLACE package body XXQP_PDFT_CLIENTES_FOTP_PKG is 

/** 24042020 se invierten p.party_name p.know_as **/

 procedure from_oracle_to_pdft(pso_errmsg out varchar2
 ,pso_errcode out varchar2
 ,pni_party_id in number
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
 
 begin 
 pso_errmsg := null; 
 pso_errcode := '0';
 
 /**********************************************************
 Comienza Inicializacion 
 *********************************************************/
 
 ln_clientes_header_id := XXQP_PDFT_CLIENTES_HEADER_S.nextval; 
 
 insert into XXQP_PDFT_CLIENTES_HEADER values (
 ln_clientes_header_id /** ID **/ 
 ,null /** NOMBRE_CLIENTE **/ 
 ,null /** GIRO_EMPRESARIAL_C **/ 
 ,null /** EMPRESA_QUE_FACTURA_C **/ 
 ,null /** TIPO_OPERATIVO_C **/ 
 ,null /** TIPO_ADMINISTRATIVO_C **/ 
 ,null /** TIPO_COMERCIAL_C **/ 
 ,null /** COMENTARIOS **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 ,null /** ATTRIBUTE_CATEGORY **/ 
 ,'Created from_oracle_to_pdft' /** ATTRIBUTE1 **/ 
 ,null /** ATTRIBUTE2 **/ 
 ,null /** ATTRIBUTE3 **/ 
 ,null /** ATTRIBUTE4 **/ 
 ,null /** ATTRIBUTE5 **/
 ); 
 commit; 
 
 ln_dir_fiscal_id := XXQP_PDFT_CLIENTES_DIR_FISC_S.nextval;
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(1):'); 
 
 insert into XXQP_PDFT_CLIENTES_DIR_FISCAL values (ln_dir_fiscal_id /** ID **/
 ,ln_clientes_header_id /** HEADER_ID **/
 ,null /** PRIM_RFC **/
 ,null /** PRIM_RAZON_SOCIAL **/
 ,null /** PRIM_DIRECCION **/
 ,null /** PRIM_ENTRE_CALLES **/
 ,null /** PRIM_COLONIA **/
 ,null /** PRIM_CIUDAD_O_MPO **/
 ,null /** PRIM_ESTADO_C **/
 ,null /** PRIM_CODIGO_POSTAL_C **/
 ,null /** PRIM_CEDULA **/
 ,null /** PRIM_CEDULA_FILE **/
 ,null /** SEC_RFC **/
 ,null /** SEC_RAZON_SOCIAL **/
 ,null /** SEC_DIRECCION **/
 ,null /** SEC_ENTRE_CALLES **/
 ,null /** SEC_COLONIA **/
 ,null /** SEC_CIUDAD_O_MPO **/
 ,null /** SEC_ESTADO_C **/
 ,null /** SEC_CODIGO_POSTAL_C **/
 ,null /** SEC_CEDULA **/
 ,null /** SEC_CEDULA_FILE **/
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 ,null /** ATTRIBUTE_CATEGORY **/
 ,null /** ATTRIBUTE1 **/
 ,null /** ATTRIBUTE2 **/
 ,null /** ATTRIBUTE3 **/
 ,null /** ATTRIBUTE4 **/
 ,null /** ATTRIBUTE5 **/
 ,null /** PRIM_CEDULA_FILE_NAME **/
 ,null /** PRIM_CEDULA_CONTENT_TYPE **/
 ,null /** SEC_CEDULA_FILE_NAME **/
 ,null /** SEC_CEDULA_CONTENT_TYPE **/
 ,null /** PRIM_OPERATING_UNIT **/
 ,null /** SEC_OPERATING_UNIT **/
 ,null /** PRIM_NUMERO_EXT **/
 ,null /** SEC_NUMERO_EXT **/
 ,null /** PRIM_NUMERO_INT **/ 
 ,null /** SEC_NUMERO_INT **/
 );
 
 commit;
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(2):'); 
 
 ln_punto_reco_id := XXQP_PDFT_CLIENTES_PUNT_RECO_S.nextval;
 
insert into XXQP_PDFT_CLIENTES_PUNTO_RECO values( ln_punto_reco_id /** ID **/
 ,ln_clientes_header_id /** HEADER_ID **/
 ,null /** PRIM_ENTREGA_EN_QP **/
 ,null /** PRIM_NOMBRE **/
 ,null /** PRIM_CONTACTO **/
 ,null /** PRIM_DIRECCION **/
 ,null /** PRIM_DIA **/
 ,null /** PRIM_HORARIO **/
 ,null /** SEC_ENTREGA_EN_QP **/
 ,null /** SEC_NOMBRE **/
 ,null /** SEC_CONTACTO **/
 ,null /** SEC_DIRECCION **/
 ,null /** SEC_DIA **/
 ,null /** SEC_HORARIO **/
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 ,null /** ATTRIBUTE_CATEGORY **/
 ,null /** ATTRIBUTE1 **/
 ,null /** ATTRIBUTE2 **/
 ,null /** ATTRIBUTE3 **/
 ,null /** ATTRIBUTE4 **/
 ,null /** ATTRIBUTE5 **/
 );
 
 commit; 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(3):'); 
 OPEN get_contactos_tmp_info;
 LOOP
 FETCH get_contactos_tmp_info INTO contactos_tmp_info_rec;
 EXIT WHEN get_contactos_tmp_info%NOTFOUND;
 
 ln_clientes_contactos_id := XXQP_PDFT_CLIENTES_CONTACTOS_S.nextval; 
 
 insert into XXQP_PDFT_CLIENTES_CONTACTOS 
 values (ln_clientes_contactos_id /** ID **/
 ,ln_clientes_header_id /** HEADER_ID **/
 ,contactos_tmp_info_rec.tipo_de_contacto /** TIPO_CONTACTO **/
 ,null /** NOMBRE **/
 ,null /** DIRECCION **/
 ,null /** TELEFONO **/
 ,null /** CORREO_ELECTRONICO **/
 ,null /** PUESTO **/
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 ,null /** ATTRIBUTE_CATEGORY **/
 ,null /** ATTRIBUTE1 **/
 ,null /** ATTRIBUTE2 **/
 ,null /** ATTRIBUTE3 **/
 ,null /** ATTRIBUTE4 **/
 ,null /** ATTRIBUTE5 **/
 ,null /** NUMERO_CELULAR **/
 );
 
 commit; 
 
 END LOOP;
 CLOSE get_contactos_tmp_info;

 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(4):'); 
 
 ln_clientes_fact_pag_id := XXQP_PDFT_CLIENTES_FACT_PAG_S.nextval; 
 
insert into XXQP_PDFT_CLIENTES_FACT_PAG values (
 ln_clientes_fact_pag_id /** ID **/
 , ln_clientes_header_id /** HEADER_ID **/
 , null /** CONDICIONES_DE_PAGO_C **/
 , null /** OBSERVACIONES **/
 , null /** TIPO_DE_PAGO_C **/
 , null /** REQUIERE_ADENDAS_C **/
 , null /** REQUIERE_FACTURA_C **/
 , null /** MOTIVO **/
 , null /** CICLO_DE_FACTURACION_C **/
 , null /** USO_DEL_CFDI_C **/
 , null /** METODO_DE_PAGO_C **/
 , null /** NUMERO_DE_CUENTA **/
 , null /** NOMBRE_DEL_BANCO **/
 , null /** DIAS_NAT_DE_CREDITO **/
 , null /** DIAS_RECEPCION_FACTUR **/
 , null /** UTILIZA_PORTAL_C **/
 , null /** PORTAL_LINK **/
 , null /** ORDEN_DE_COMPRA_C **/
 , null /** CONTRATO_C **/
 , null /** VIGENCIA_CONTRATO **/
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 , null /** ATTRIBUTE_CATEGORY **/
 , null /** ATTRIBUTE1 **/
 , null /** ATTRIBUTE2 **/
 , null /** ATTRIBUTE3 **/
 , null /** ATTRIBUTE4 **/
 , null /** ATTRIBUTE5 **/
 , null /** LUNES **/
 ,null 
 ,null
 ,null
 ,null
 ,null
 ,null
 ); 

 commit; 
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
 
 begin 
 update XXQP_PDFT_CLIENTES_HEADER
 set nombre_cliente = nvl(organization_info_rec.party_name,organization_info_rec.known_as) /** 24042020 **/
 ,attribute1 = 'Created from_oracle_to_pdft' 
 ,attribute2 = pni_party_id
 where id = ln_clientes_header_id; 
 pno_clientes_header_id := ln_clientes_header_id; 
 commit; 
 exception when others then 
 ls_errmsg := sqlerrm||', '||sqlcode; 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(6.2)'||ls_errmsg); 
 commit; 
 end; 
 
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
 where HEADER_ID = ln_clientes_header_id
 and id = ln_clientes_fact_pag_id; 
 commit; 
 
 END LOOP;
 CLOSE get_cust_account_info;
 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(8):'); 
 commit; 
 
 OPEN get_location_info(pni_party_id);
 LOOP
 FETCH get_location_info INTO location_info_rec;
 EXIT WHEN get_location_info%NOTFOUND;
 
 
 XXQP_PDFT_CLIENTES_FOTP_PKG.val_insert_mgr_catalogos( psi_lookup_type => 'ESTADO'
 ,psi_lookup_code => location_info_rec.state );
 APPS.XXQP_PDFT_CLIENTES_FOTP_PKG.val_insert_mgr_catalogos( psi_lookup_type => 'CODIGO_POSTAL'
 ,psi_lookup_code => location_info_rec.postal_code
 ); 
 update XXQP_PDFT_CLIENTES_DIR_FISCAL 
 set PRIM_RFC = nvl(organization_info_rec.attribute1,cust_account_info_rec.attribute1)
 ,PRIM_RAZON_SOCIAL = organization_info_rec.party_name
 ,PRIM_DIRECCION = location_info_rec.address1||location_info_rec.address2
 ,PRIM_COLONIA = location_info_rec.address4 
 ,PRIM_CIUDAD_O_MPO = location_info_rec.city 
 ,PRIM_ESTADO_C = location_info_rec.state 
 ,PRIM_CODIGO_POSTAL_C = location_info_rec.postal_code
 where HEADER_ID = ln_clientes_header_id
 and id = ln_dir_fiscal_id; 
 commit; 
 
 if cust_account_info_rec.cust_account_id is not null then 
 select org_id 
 into ln_prim_org_id 
 from HZ_CUST_ACCT_SITES
 where cust_account_id = cust_account_info_rec.cust_account_id
 and PARTY_SITE_ID = location_info_rec.PARTY_SITE_ID;
 
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
 
 begin 
 select distinct LEP.LEGAL_ENTITY_ID
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
where LEP.TRANSACTING_ENTITY_FLAG = 'Y'
and LEP.PARTY_ID = HZP.PARTY_ID
and LEP.LEGAL_ENTITY_ID = REG.SOURCE_ID
and REG.SOURCE_TABLE = 'XLE_ENTITY_PROFILES'
and HRL.LOCATION_ID = REG.LOCATION_ID
and REG.IDENTIFYING_FLAG = 'Y'
and TER.TERRITORY_CODE = HRL.COUNTRY
and LEP.LEGAL_ENTITY_ID = HRO.DEFAULT_LEGAL_CONTEXT_ID
and GLOPERATINGUNITSEO.ORGANIZATION_ID = HRO.ORGANIZATION_ID
and HROUTL_BG.ORGANIZATION_ID = HRO.BUSINESS_GROUP_ID
and HROUTL_OU.ORGANIZATION_ID = HRO.ORGANIZATION_ID
and GLEV.LEGAL_ENTITY_ID = LEP.LEGAL_ENTITY_ID
and HROUTL_OU.ORGANIZATION_ID = ln_prim_org_id;
exception when others then 
 ls_errmsg := sqlerrm||', '||sqlcode; 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(9.1) ln_prim_org_id:'||ln_prim_org_id||', pni_party_id:'||pni_party_id); 
 commit; 
end; 

 
 insert into xxqp_pdft_debug values(xxqp_pdft_debug_s.nextval,'from_oracle_to_pdft(10):'); 
 
 if ln_legal_entity_id is not null then 
 update XXQP_PDFT_CLIENTES_HEADER
 set empresa_que_factura_c = ln_legal_entity_id
 where id = ln_clientes_header_id; 
 
 commit; 
 
 end if; 
 

 XXQP_PDFT_CLIENT_FPTO_UPD_PKG.upd_from_pdft_to_oracle(pso_errmsg => ls_errmsg
 ,pso_errcode => ls_errcod
 ,pni_party_id => pni_party_id
 ,pni_cliente_header_id => ln_clientes_header_id
 ); 

 
 exception when le_from_oracle_to_pdft then 
 pso_errmsg := 'Exception le_from_oracle_to_pdft'||pso_errmsg;
 pso_errcode := '2';
 
 when others then 
 pso_errmsg := 'Excepcion metodo from_oracle_to_pdft'||sqlerrm; 
 pso_errcode := '2';
 end from_oracle_to_pdft; 
 
 
 
 procedure val_insert_mgr_catalogos( psi_lookup_type in varchar2
 ,psi_lookup_code in varchar2 )
 is 
 ln_max_numero_linea number; 
 ls_insert_flag varchar2(2000); 
 begin
 
 select max(NUMERO_LINEA)
 into ln_max_numero_linea
 from xxqp_pdft_mgr_catalogos where lookup_type = psi_lookup_type; 
 
 select 'Y'
 into ls_insert_flag
 from xxqp_pdft_mgr_catalogos
 where lookup_type = psi_lookup_type
 and lookup_code =psi_lookup_code; 
 
 exception when no_data_found then 
 
 if ln_max_numero_linea is not null then
 insert into xxqp_pdft_mgr_catalogos values 
 (XXQP_PDFT_MGR_CATALOGOS_S.nextval /** ID **/
 ,ln_max_numero_linea /** NUMERO_LINEA ORA-01400: no se puede realizar una insercion NULL **/
 ,psi_lookup_type /** LOOKUP_TYPE **/
 ,psi_lookup_code /** LOOKUP_CODE **/
 ,psi_lookup_code /** DESCRIPTION **/
 ,'Y' /** STATUS **/
 ,nvl(fnd_profile.value('USER_ID'),0) /** CREATED_BY **/ 
 ,sysdate /** CREATION_DATE **/ 
 ,nvl(fnd_profile.value('USER_ID'),0) /** LAST_UPDATED_BY **/ 
 ,sysdate /** LAST_UPDATE_DATE **/ 
 ,nvl(fnd_profile.value('LOGIN_ID'),0) /** LAST_UPDATE_LOGIN **/ 
 ,null /** ATTRIBUTE_CATEGORY **/
 ,'FROM_ORACLE_AR' /** ATTRIBUTE1 **/
 ,null /** ATTRIBUTE2 **/
 ,null /** ATTRIBUTE3 **/
 ,null /** ATTRIBUTE4 **/
 ,null /** ATTRIBUTE5 **/
 );
 commit; 
 end if; 
 
 when others then 
 null; 
 end; 
 
 end XXQP_PDFT_CLIENTES_FOTP_PKG;
/


alter session set nls_language='AMERICAN'

'/oracle/apps/ar/cusstd/contact/webui/ArAcctContactCreatePG'
'/oracle/apps/ar/hz/components/account/contact/webui/HzPuiAcctContactCreateRN'

 begin
 jdr_utils.printDocument('/oracle/apps/ar/hz/components/party/person/webui/HzPuiQuickCreatePersonSpecial');
end; 


oracle.apps.ar.hz.components.contact.server.HzPuiJobTitleCodeVO


/oracle/apps/ar/hz/components/contact/server

select lookup_code,
       meaning
from ar_lookups
where lookup_type = 'RESPONSIBILITY'
AND
ENABLED_FLAG = 'Y' AND TRUNC(SYSDATE) BETWEEN TRUNC(NVL(START_DATE_ACTIVE,SYSDATE)) AND TRUNC(NVL(END_DATE_ACTIVE,SYSDATE))

*********************************************************************
 
  select * from XXQP_PDFT_CLIENTES_CONTACTOS
  order by id desc

select * from hz_org_contacts
order by org_contact_id desc 

select  org_contact_id, party_relationship_id, department_code, title, job_title, job_title_code ,creation_date
from hz_org_contacts
order by org_contact_id desc
 
select * from hz_contact_points
 order by CONTACT_POINT_ID  desc
 
 select * from hz_parties
 where party_type = 'PERSON'
 order by party_id desc

2653564

 select * from hz_parties
 where party_type = 'ORGANIZATION'
 order by party_id desc
  
  2661564
  
 2647564
 
 select * from hz_cust_accounts_all
 order by cust_account_id desc
 
 
CIERRE_Y_SEGUIMIENTO  -> Cierre y Seguimiento
COBRANZA                       -> Cobranza
GERENCIAL                      -> Gerencial
DIRECTRIZ                     -> Directriz
AUDITORIA                    -> Auditoria
DEVOLUCIONES             -> Devoluciones
 








select * from XXQP_PDFT_CLIENTES_HEADER

where nombre_cliente like '%AXTEL%'


delete from XXQP_PDFT_CLIENTES_CONTACTOS; 

delete from XXQP_PDFT_CLIENTES_DIR_FISCAL;

delete from XXQP_PDFT_CLIENTES_FACT_PAG;

delete from XXQP_PDFT_CLIENTES_PUNTO_RECO;

delete from XXQP_PDFT_CLIENTES_HEADER;

alter session set nls_language='AMERICAN'

select * from XXQP_PDFT_CLIENTES_INFO_V;

Attribute2


select * from hz_parties
where attribute2 is not null

update hz_parties
   set ATTRIBUTE1 = null /** RFC NO ACTUALIZAR **/
      ,attribute2 = null
   where attribute2 is not null

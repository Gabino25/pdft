CREATE OR REPLACE function APPS.xxqp_pdft_bpo_status_mail(PSI_NUMERO_FT         NUMBER
                                                                              ,PSI_NUMERO_FT_REF  NUMBER
                                                                             ) return varchar2 is 
retval varchar2(200);
LS_STATUS_NFT_REF     VARCHAR2(200);
begin 
retval := 'NA';
  IF PSI_NUMERO_FT_REF IS NULL THEN 
   RETURN 'ALTA';
  ELSE
   SELECT STATUS 
      INTO LS_STATUS_NFT_REF
     FROM  XXQP_PDFT_BPO_HEADER
    WHERE NUMERO_FT = PSI_NUMERO_FT_REF;
    
     IF LS_STATUS_NFT_REF IN ('CERRADA') THEN 
        RETURN 'MODIFICACION';
     ELSE
        RETURN 'ALTA';
     END IF; 
     
 END IF; 
 RETURN RETVAL;
exception when others then 
  RETURN 'NA';
end xxqp_pdft_bpo_status_mail;
/


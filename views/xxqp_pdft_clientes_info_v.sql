/* Formatted on 2021/01/06 14:18 (Formatter Plus v4.8.8) */
CREATE OR REPLACE FORCE VIEW apps.xxqp_pdft_clientes_info_v (party_id,
                                                             party_name,
                                                             person_first_name,
                                                             person_last_name,
                                                             party_type,
                                                             type_lookup,
                                                             party_number,
                                                             tax_reference,
                                                             taxpayer_id,
                                                             duns_number,
                                                             known_as,
                                                             known_as2,
                                                             known_as3,
                                                             known_as4,
                                                             known_as5,
                                                             organization_name_phonetic,
                                                             person_first_name_phonetic,
                                                             person_last_name_phonetic,
                                                             location_id,
                                                             country,
                                                             primary_phone_contact_pt_id,
                                                             primary_phone_country_code,
                                                             primary_phone_area_code,
                                                             primary_phone_number,
                                                             primary_phone_line_type,
                                                             primary_phone_extension,
                                                             email,
                                                             primary_url,
                                                             certification_level_meaning,
                                                             registry_meaning,
                                                             party_status,
                                                             city,
                                                             jgzz_fiscal_code,
                                                             razon_social,
                                                             state,
                                                             attribute_category,
                                                             attribute1,
                                                             attribute2
                                                            )
AS
   SELECT p.party_id, p.party_name, p.person_first_name, p.person_last_name,
          p.party_type, partytype.meaning type_lookup, p.party_number,
          p.tax_reference, NVL (p.attribute1, p.jgzz_fiscal_code) taxpayer_id,
          p.duns_number_c duns_number, NVL (p.party_name,
                                            p.known_as) known_as,
                                                                 /** 24042020 buscar por razon social **/
                                                                 p.known_as2,
          p.known_as3, p.known_as4, p.known_as5, p.organization_name_phonetic,
          p.person_first_name_phonetic, p.person_last_name_phonetic,
          ps.location_id, terr.territory_short_name country,
          p.primary_phone_contact_pt_id, p.primary_phone_country_code,
          p.primary_phone_area_code, p.primary_phone_number,
          p.primary_phone_line_type, p.primary_phone_extension,
          p.email_address email, p.url primary_url,
          certification.meaning certification_level_meaning,
          registrystatus.meaning registry_meaning, p.status party_status,
          hl.city, NVL (cust.attribute1, p.jgzz_fiscal_code) jgzz_fiscal_code,
          p.party_name razon_social, hl.state, p.attribute_category,
          p.attribute1, p.attribute2
     FROM hz_parties p,
          hz_party_sites ps,
          hz_locations hl,
          fnd_territories_vl terr,
          fnd_lookup_values partytype,
          fnd_lookup_values certification,
          fnd_lookup_values registrystatus,
          hz_cust_accounts cust
    WHERE 1 = 1
      /** and p.party_type IN ('PERSON', 'ORGANIZATION') **/
      AND cust.party_id = p.party_id
      AND registrystatus.lookup_code = p.status
      AND ps.location_id = hl.location_id
      AND ps.party_id(+) = p.party_id
      AND ps.identifying_address_flag(+) = 'Y'
      AND terr.territory_code(+) = p.country
      AND partytype.view_application_id = 222                       /** AR **/
      AND partytype.lookup_type = 'PARTY_TYPE'
      AND partytype.LANGUAGE = USERENV ('LANG')
      AND partytype.lookup_code = p.party_type
      AND certification.view_application_id(+) = 222                /** AR **/
      AND certification.lookup_type(+) = 'HZ_PARTY_CERT_LEVEL'
      AND certification.LANGUAGE(+) = USERENV ('LANG')
      AND certification.lookup_code(+) = p.certification_level
      AND registrystatus.lookup_type = 'HZ_CPUI_REGISTRY_STATUS'
      AND registrystatus.LANGUAGE = USERENV ('LANG')
      AND cust.status = 'A'
      AND p.status = 'A'              /** 15042020 Valida Clientes Activos **/
      AND EXISTS (
             SELECT 1   /** 17042020 Valida que los clientes tengan cuenta **/
               FROM hz_cust_accounts hca, hz_customer_profiles hcp
              WHERE hcp.cust_account_id = hca.cust_account_id
                AND p.party_id = hca.party_id
                AND cust.cust_account_id = hca.cust_account_id
                AND EXISTS (
                       SELECT 1
/** 17042020 Valida que la sucursal de la cuenta tenga proposito de negocio **/
                       FROM   hz_cust_site_uses_all hcsua,
                              hz_cust_acct_sites_all hcasa
                        WHERE hcsua.site_use_code IN ('BILL_TO', 'SHIP_TO')
                          AND hcsua.cust_acct_site_id =
                                                       hcasa.cust_acct_site_id
                          /** AND ps.party_site_id = hcasa.party_site_id  30062020 1872 Registros Vs 1897 Registros **/
                          AND hcasa.cust_account_id = hca.cust_account_id));


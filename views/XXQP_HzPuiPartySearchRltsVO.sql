/* Formatted on 07/01/2021 05:39:12 p.m. (QP5 v5.149.1003.31008) */
CREATE OR REPLACE VIEW  XXQP_HzPuiPartySearchRltsVO AS
SELECT *
  FROM (SELECT p.party_id,
               p.party_name,
               p.person_first_name,
               p.person_last_name,
               p.party_type,
               partytype.meaning type_lookup,
               p.party_number,
               p.tax_reference,
               p.jgzz_fiscal_code taxpayer_id,
               p.duns_number_c duns_number,
               p.known_as,
               p.known_as2,
               p.known_as3,
               p.known_as4,
               p.known_as5,
               p.organization_name_phonetic,
               p.person_first_name_phonetic,
               p.person_last_name_phonetic,
               ps.location_id,
               terr.territory_short_name country,
               p.primary_phone_contact_pt_id,
               p.primary_phone_country_code,
               p.primary_phone_area_code,
               p.primary_phone_number,
               p.primary_phone_line_type,
               p.primary_phone_extension,
               p.email_address email,
               p.url primary_url,
               certification.meaning certification_level_meaning,
               registrystatus.meaning registry_meaning,
               p.status party_status,
               nvl(p.attribute1, p.jgzz_fiscal_code) rfc,
               p.state
          FROM hz_parties p,
               hz_party_sites ps,
               fnd_territories_vl terr,
               fnd_lookup_values partytype,
               fnd_lookup_values certification,
               fnd_lookup_values registrystatus
         WHERE     p.party_type IN ('PERSON', 'ORGANIZATION')
               AND registrystatus.lookup_code = p.status
               AND ps.party_id(+) = p.party_id
               AND ps.identifying_address_flag(+) = 'Y'
               AND terr.territory_code(+) = p.country
               AND partytype.view_application_id = 222
               AND partytype.lookup_type = 'PARTY_TYPE'
               AND partytype.language = USERENV ('LANG')
               AND partytype.lookup_code = p.party_type
               AND certification.view_application_id(+) = 222
               AND certification.lookup_type(+) = 'HZ_PARTY_CERT_LEVEL'
               AND certification.language(+) = USERENV ('LANG')
               AND certification.lookup_code(+) = p.certification_level
               AND registrystatus.lookup_type = 'HZ_CPUI_REGISTRY_STATUS'
               AND registrystatus.language = USERENV ('LANG')) QRSLT
 WHERE ( party_type = 'ORGANIZATION' AND  party_status = 'A')
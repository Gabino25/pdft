/* Formatted on 08/01/2021 08:43:25 a.m. (QP5 v5.149.1003.31008) */
  CREATE OR REPLACE VIEW XXQP_HzPuiAccountTableVO AS
  SELECT *
    FROM (SELECT a.cust_account_id,
                 a.account_number,
                 a.account_name,
                 HZ_UTILITY_V2PUB.
                  get_lookupmeaning ('FND_LOOKUP_VALUES',
                                     'CUSTOMER CLASS',
                                     a.customer_class_code)
                    AS CUSTOMER_CLASS_CODE,
                 a.account_established_date,
                 a.primary_salesrep_id,
                 HZ_UTILITY_V2PUB.
                  get_lookupmeaning ('SO_LOOKUPS',
                                     'SALES_CHANNEL',
                                     a.sales_channel_code)
                    AS SALES_CHANNEL_CODE,
                 HZ_UTILITY_V2PUB.
                  get_lookupmeaning ('FND_LOOKUP_VALUES',
                                     'CUSTOMER_TYPE',
                                     a.customer_type)
                    AS CUSTOMER_TYPE,
                 a.orig_system_reference,
                 p.standard_terms,
                 a.party_id,
                 a.status,
                 (SELECT rt.name
                    FROM RA_TERMS_TL rt
                   WHERE rt.TERM_ID = p.STANDARD_TERMS
                         AND rt.LANGUAGE = USERENV ('LANG'))
                    term_name,
                 (SELECT hcpc.name
                    FROM HZ_CUST_PROFILE_CLASSES hcpc
                   WHERE p.PROFILE_CLASS_ID = hcpc.PROFILE_CLASS_ID)
                    profile_class_name,
                 HZ_UTILITY_V2PUB.
                  get_lookupmeaning ('FND_LOOKUP_VALUES',
                                     'HZ_CPUI_REGISTRY_STATUS',
                                     a.status)
                    AS ACCOUNT_MEANING
            FROM hz_cust_accounts a, hz_customer_profiles p
           WHERE p.cust_account_id = a.cust_account_id AND site_use_id IS NULL) QRSLT
   WHERE (STATUS = 'A')
ORDER BY account_number
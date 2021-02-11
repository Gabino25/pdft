 CREATE OR REPLACE VIEW XXQP_BanksLovVO AS
  SELECT CEBank.Bank_Party_Id Bank_Party_Id,
               CEBank.BANK_NAME Bank_Name,
               CEBank.Bank_Number Bank_Number,
               CEBank.Bank_Institution_Type Bank_Institution_Type,
               CEBank.Bank_Name_Alt Bank_Name_Alt,
               CEBank.short_bank_name Short_Bank_Name,
               CEBank.description Description,
               CEBank.end_date End_Date,
               CEBank.ADDRESS_line1 Address1,
               CEBank.ADDRESS_line2 Address2,
               CEBank.ADDRESS_line3 Address3,
               CEBank.City City,
               CEBank.State State,
               CEBank.zip Zipcode,
               CEBank.country Country,
               1 CREATED_BY,
               SYSDATE CREATION_DATE,
               1 LAST_UPDATED_BY,
               SYSDATE LAST_UPDATE_DATE,
               1 LAST_UPDATE_LOGIN,
               1 OBJECT_VERSION_NUMBER,
               CEBank.HOME_COUNTRY Home_Country,
               flp.meaning bank_institute_type_display,
               fnd_ter.territory_short_name Home_country_display
          FROM ce_banks_v cebank, fnd_lookups flp, fnd_territories_vl fnd_ter
         WHERE     flp.lookup_type = 'IBY_BANK_INSTITUTION_TYPE'
               AND flp.lookup_code = cebank.bank_institution_type
               AND cebank.home_country = fnd_ter.territory_code
               AND CEBank.end_date IS NULL;

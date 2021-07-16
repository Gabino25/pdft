DROP VIEW APPS.XXQP_PDFT_MYP_PROCESOS_V1;

/* Formatted on 16/07/2021 05:35:54 p.m. (QP5 v5.149.1003.31008) */
CREATE OR REPLACE FORCE VIEW APPS.XXQP_PDFT_MYP_PROCESOS_V1
(
   PRECIO,
   PRECIO_PROTON,
   NUMERO_FT
)
AS
     SELECT NVL (SUM (precio), 0) precio,
            NVL (SUM (precio_proton), 0) precio_proton,
            XPMH.NUMERO_FT
       FROM (SELECT NVL (precio, 0) precio,
                    NVL (precio_proton, 0) precio_proton,
                    MYP_HEADER_ID
               FROM XXQP_PDFT_MYP_PROCESOS) XPMP,
            XXQP_PDFT_MYP_HEADER XPMH
      WHERE XPMH.ID = XPMP.MYP_HEADER_ID
   GROUP BY XPMH.NUMERO_FT;

package xxqp.oracle.apps.ar.pdft.altafitec.masiplat.server;

import com.sun.java.util.collections.List;

import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAViewObjectImpl;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftMypOtrosProcesosVOImpl extends OAViewObjectImpl {
    /**This is the default constructor (do not remove)
     */
    public XxqpPdftMypOtrosProcesosVOImpl() {
    }

    void initByMyPHeaderId(Number pNumMyPHeaderId) {
        // 2. Create a view criteria row set for this view object
        ViewCriteria vc = this.createViewCriteria(); 
        // 3. Use the view criteria to create one or more view criteria rows
        ViewCriteriaRow vcr1 = vc.createViewCriteriaRow(); 
        // 4. Set attribute values to filter on in appropriate view criteria rows
        vcr1.setAttribute("MypHeaderId",pNumMyPHeaderId); 
        // 5. Add the view criteria rows to the view critera row set 
        vc.add(vcr1); 
        // 6. Apply the view criteria to the view object
        this.applyViewCriteria(vc); 
        // 7. Execute the query
        this.executeQuery();
    }
    
    public List validaPrecios() {
     com.sun.java.util.collections.List retval = new com.sun.java.util.collections.ArrayList();
        XxqpPdftMypOtrosProcesosVORowImpl  xxqpPdftMypOtrosProcesosTmpVORowImpl = null; 
     RowSetIterator iterator = this.createRowSetIterator(null); 
     while(iterator.hasNext()){
         xxqpPdftMypOtrosProcesosTmpVORowImpl = (XxqpPdftMypOtrosProcesosVORowImpl)iterator.next(); 
         if(null!=xxqpPdftMypOtrosProcesosTmpVORowImpl.getSeleccionar()){
          if("Y".equals(xxqpPdftMypOtrosProcesosTmpVORowImpl.getSeleccionar())){
           if(null==xxqpPdftMypOtrosProcesosTmpVORowImpl.getPrecio()){
               retval.add(new OAException("El Precio Es Obligatorio.",OAException.ERROR)); 
           }else{
            oracle.jbo.domain.Number numPrecio = xxqpPdftMypOtrosProcesosTmpVORowImpl.getPrecio();
            if(numPrecio.compareTo(0)==0){
                retval.add(new OAException("El Precio Es Obligatorio para esta opcion:"+xxqpPdftMypOtrosProcesosTmpVORowImpl.getOtrosProcesos(),OAException.ERROR)); 
            }
           }
           if(null==xxqpPdftMypOtrosProcesosTmpVORowImpl.getOtrosProcesos()){
             retval.add(new OAException("El Nombre de Proceso es Obligatorio.",OAException.ERROR));
           }
          }else{
              if(null!=xxqpPdftMypOtrosProcesosTmpVORowImpl.getPrecio()){
                  retval.add(new OAException("Seleccionar este precio:"+xxqpPdftMypOtrosProcesosTmpVORowImpl.getPrecio(),OAException.ERROR));
              }
              if(null!=xxqpPdftMypOtrosProcesosTmpVORowImpl.getOtrosProcesos()){
                  retval.add(new OAException("Seleccionar este proceso:"+xxqpPdftMypOtrosProcesosTmpVORowImpl.getOtrosProcesos(),OAException.ERROR));
              }
          }
         }
     } /** END  while(iterator.hasNext()){ **/
     
     iterator.closeRowSetIterator();
     return retval; 
    }
    
}

package xxqp.oracle.apps.ar.pdft.altafitec.qplabs.server;

import oracle.apps.fnd.framework.server.OAViewObjectImpl;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxqpPdftQplabsHeaderVOImpl extends OAViewObjectImpl {
    /**This is the default constructor (do not remove)
     */
    public XxqpPdftQplabsHeaderVOImpl() {
    }

    void initByQplabsHeaderId(Number pNumQplabsHeaderId) {
        // 2. Create a view criteria row set for this view object
        ViewCriteria vc = this.createViewCriteria(); 
        // 3. Use the view criteria to create one or more view criteria rows
        ViewCriteriaRow vcr1 = vc.createViewCriteriaRow(); 
        // 4. Set attribute values to filter on in appropriate view criteria rows
        vcr1.setAttribute("Id",pNumQplabsHeaderId); 
        // 5. Add the view criteria rows to the view critera row set 
        vc.add(vcr1); 
        // 6. Apply the view criteria to the view object
        this.applyViewCriteria(vc); 
        // 7. Execute the query
        this.executeQuery();
    }
}

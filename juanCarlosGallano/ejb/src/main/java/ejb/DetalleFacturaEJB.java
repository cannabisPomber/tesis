package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.DetalleFactura;
import entities.Usuario;

@Stateless
@LocalBean
public class DetalleFacturaEJB extends GenericDaoImpl<DetalleFactura, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();
	    
    public List<DetalleFactura> findAll() {
    	TypedQuery<DetalleFactura> query = em.createQuery("select c from detalle_factura c", DetalleFactura.class);
    	detalleFacturas= query.getResultList();
    	return detalleFacturas;
    }
    
    public DetalleFactura findDetalleFactura (Long idDetalleFactura){
    	try {
    		   Query query = em.createQuery("select u from detalle_factura u where u.idDetalleFactura = :idDetalleFactura");
    		   query.setParameter("idDetalleFactura", idDetalleFactura);
    		   
    		   
    		   return (DetalleFactura) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

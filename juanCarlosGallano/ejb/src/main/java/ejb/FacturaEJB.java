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

import entities.Factura;
import entities.Usuario;

@Stateless
@LocalBean
public class FacturaEJB extends GenericDaoImpl<Factura, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<Factura> facturas = new ArrayList<Factura>();
	    
    public List<Factura> findAll() {
    	TypedQuery<Factura> query = em.createQuery("select c from factura c", Factura.class);
    	facturas= query.getResultList();
    	return facturas;
    }
    
    public Factura findFactura (Long idFactura){
    	try {
    		   Query query = em.createQuery("select u from factura u where u.idFactura = :idFactura");
    		   query.setParameter("idFactura", idFactura);
    		   
    		   
    		   return (Factura) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

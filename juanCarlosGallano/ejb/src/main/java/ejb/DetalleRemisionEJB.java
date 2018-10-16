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

import entities.DetalleRemision;

@Stateless
@LocalBean
public class DetalleRemisionEJB extends GenericDaoImpl<DetalleRemision, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<DetalleRemision> detalleRemisiones = new ArrayList<DetalleRemision>();
	
	   
	   public DetalleRemision findIdDetalleRemision(Long id){
		   try {
		   Query query = em.createQuery("select u from detalle_remision u where u.idDetalleRemision = :id");
		   query.setParameter("id", id);
		   
		   
		   return (DetalleRemision) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<DetalleRemision> findAll() {
	    	TypedQuery<DetalleRemision> query = em.createQuery("select u from detalle_remision u", DetalleRemision.class);
	    	detalleRemisiones = query.getResultList();
	    	return detalleRemisiones;
	   }
	   
	   
	   public List<DetalleRemision> findIdRemision(Long idRemision) {
	    	TypedQuery<DetalleRemision> query = em.createQuery("select u from detalle_remision u where u.remision.idRemision = :id", DetalleRemision.class);
	    	query.setParameter("id", idRemision);
	    	detalleRemisiones = query.getResultList();
	    	return detalleRemisiones;
	   }
	   
}


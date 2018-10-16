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

import entities.DetallePagoContado;

@Stateless
@LocalBean
public class DetallePagoContadoEJB extends GenericDaoImpl<DetallePagoContado, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<DetallePagoContado> detallePagoComprass = new ArrayList<DetallePagoContado>();
	   
	   public DetallePagoContado findIdDetallePagoContado(Long id){
		   try {
		   Query query = em.createQuery("select u from detallePagoCompras u where u.idDetallePagoContado = :id");
		   query.setParameter("id", id);
		   
		   
		   return (DetallePagoContado) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<DetallePagoContado> findAll() {
	    	TypedQuery<DetallePagoContado> query = em.createQuery("select u from detallePagoCompras u", DetallePagoContado.class);
	    	detallePagoComprass = query.getResultList();
	    	return detallePagoComprass;
	   }
	   
}

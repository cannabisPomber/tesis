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

import entities.Barrio;

@Stateless
@LocalBean
public class BarrioEJB extends GenericDaoImpl<Barrio, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Barrio> barrios = new ArrayList<Barrio>();
	   
	   public Barrio findIdBarrio(Long id){
		   try {
		   Query query = em.createQuery("select u from barrio u where u.idBarrio = :id");
		   query.setParameter("id", id);
		   
		   
		   return (Barrio) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<Barrio> findAll() {
	    	TypedQuery<Barrio> query = em.createQuery("select u from barrio u", Barrio.class);
	    	barrios = query.getResultList();
	    	return barrios;
	   }
	   
	   public List<Barrio> findAllActivo() {
		   Query query = em.createQuery("select u from barrio u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
	    	barrios = query.getResultList();
	    	return barrios;
	   }
}

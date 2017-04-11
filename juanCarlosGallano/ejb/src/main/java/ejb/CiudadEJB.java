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
import entities.Ciudad;

@Stateless
@LocalBean
public class CiudadEJB extends GenericDaoImpl<Ciudad, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Ciudad> ciudades = new ArrayList<Ciudad>();
	
	   
	   public Ciudad findIdCiudad(Long id){
		   try {
		   Query query = em.createQuery("select u from ciudad u where u.idCiudad = :id");
		   query.setParameter("id", id);
		   
		   
		   return (Ciudad) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<Ciudad> findAll() {
	    	TypedQuery<Ciudad> query = em.createQuery("select u from ciudad u", Ciudad.class);
	    	ciudades = query.getResultList();
	    	return ciudades;
	   }
	   
	   public List<Ciudad> findAllActivo() {
		   Query query = em.createQuery("select u from ciudad u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
		   ciudades = query.getResultList();
	    	return ciudades;
	   }
}

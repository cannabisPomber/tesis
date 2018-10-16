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

import entities.Grupo;
import entities.Remision;
import entities.Usuario;

@Stateless
@LocalBean
public class RemisionEJB extends GenericDaoImpl<Remision, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Remision> remisions = new ArrayList<Remision>();
	
	public List<Remision> findAll() {
    	TypedQuery<Remision> query = em.createQuery("select c from remision c", Remision.class);
    	remisions = query.getResultList();
    	return remisions;
    }
	
	public List<Remision> findAllActivo() {
    	TypedQuery<Remision> query = em.createQuery("select c from remision c where c.fechaDevolucion is null ", Remision.class);
    	remisions = query.getResultList();
    	return remisions;
    }
	
	public Remision findRemisionId (Long idRemision){
    		try {
    		   Query query = em.createQuery("select u from remision u where u.idRemision = :idRemision");
    		   query.setParameter("idRemision", idRemision);
    		   
    		   
    		   return (Remision) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

}



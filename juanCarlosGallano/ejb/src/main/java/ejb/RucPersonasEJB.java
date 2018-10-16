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

import entities.RucPersonas;
@Stateless
@LocalBean
public class RucPersonasEJB extends GenericDaoImpl<RucPersonas, Serializable> {
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<RucPersonas> rucPersonas = new ArrayList<RucPersonas>();
	
	/*public RucPersonas findRucPersonas(String nombreRucPersonas){
		   try {
		   Query query = em.createQuery("select u from rucPersonas u where u.nombreRucPersonas like '' ");
		   query.setParameter("nombre", nombreRucPersonas);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public RucPersonas findIdRucPersonas(Long id){
		   try {
		   Query query = em.createQuery("select u from ruc_personas u where u.idRucPersonas = :id");
		   query.setParameter("id", id);
		   
		   
		   return (RucPersonas) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<RucPersonas> findAll() {
	    	TypedQuery<RucPersonas> query = em.createQuery("select u from tipo_venta u", RucPersonas.class);
	    	rucPersonas = query.getResultList();
	    	return rucPersonas;
	   }
	   
	   public List<RucPersonas> findPorRucPersonas(String ruc){
		   try {
		   Query query = em.createQuery("select u from ruc_personas u where u.ruc = :ruc");
		   query.setParameter("ruc", ruc.trim());
		   
		   
		   return (List<RucPersonas>) query.getResultList();
		   }
		   catch (Exception ex){
			   return null;
		   }
	   }
	   
	   public List<RucPersonas> findPorNombrePersonas(String nombre){
		   try {
		   Query query = em.createQuery("select u from ruc_personas u where u.persona like :nombre");
		   query.setParameter("nombre", "%"+nombre.trim().toUpperCase()+"%");
		   
		   
		   return (List<RucPersonas>) query.getResultList();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
}



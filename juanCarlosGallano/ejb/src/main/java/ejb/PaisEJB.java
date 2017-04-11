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

import entities.Ciudad;
import entities.Pais;

@Stateless
@LocalBean
public class PaisEJB extends GenericDaoImpl<Pais, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Pais> paises = new ArrayList<Pais>();
	
	   
	   public Pais findIdPais(Long id){
		   try {
		   Query query = em.createQuery("select u from pais u where u.idPais = :id");
		   query.setParameter("id", id);
		   
		   
		   return (Pais) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<Pais> findAll() {
	    	TypedQuery<Pais> query = em.createQuery("select u from pais u", Pais.class);
	    	paises = query.getResultList();
	    	return paises;
	   }
	   
	   public List<Pais> findAllActivo() {
		   Query query = em.createQuery("select u from pais u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
		   
		   return (List<Pais>)query.getResultList();
	   }
	   
}

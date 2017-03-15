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
import entities.Usuario;

@Stateless
@LocalBean
public class GrupoEJB extends GenericDaoImpl<Grupo, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<Grupo> grupos = new ArrayList<Grupo>();
	    
    public List<Grupo> findAll() {
    	TypedQuery<Grupo> query = em.createQuery("select c from grupo c", Grupo.class);
    	grupos= query.getResultList();
    	return grupos;
    }
    
    public Grupo findGrupo (Long idGrupo){
    	try {
    		   Query query = em.createQuery("select u from grupo u where u.idGrupo = :idGrupo");
    		   query.setParameter("idGrupo", idGrupo);
    		   
    		   
    		   return (Grupo) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

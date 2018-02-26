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

import entities.Rol;
import entities.Usuario;

@Stateless
@LocalBean
public class RolEJB extends GenericDaoImpl<Rol, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<Rol> rols = new ArrayList<Rol>();
	    
    public List<Rol> findAll() {
    	TypedQuery<Rol> query = em.createQuery("select c from rol c", Rol.class);
    	rols= query.getResultList();
    	return rols;
    }
    
    public Rol findRol (Long idRol){
    	try {
    		   Query query = em.createQuery("select u from rol u where u.idRol = :idRol");
    		   query.setParameter("idRol", idRol);
    		   
    		   
    		   return (Rol) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}


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
import entities.Marca;

@Stateless
@LocalBean
public class MarcaEJB extends GenericDaoImpl<Marca, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Marca> marcas = new ArrayList<Marca>();
	
	public List<Marca> findAll() {
    	TypedQuery<Marca> query = em.createQuery("select c from marca c", Marca.class);
    	marcas = query.getResultList();
    	return marcas;
    }
	
	public List<Marca> findAllActivo() {
    	TypedQuery<Marca> query = em.createQuery("select c from marca c where c.estado = 'Activo'", Marca.class);
    	marcas = query.getResultList();
    	return marcas;
    }
	
	public Marca findMarcaId (Long idMarca){
    	try {
    		   Query query = em.createQuery("select u from marca u where u.idMarca = :idMarca");
    		   query.setParameter("idMarca", idMarca);
    		   
    		   
    		   return (Marca) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

}

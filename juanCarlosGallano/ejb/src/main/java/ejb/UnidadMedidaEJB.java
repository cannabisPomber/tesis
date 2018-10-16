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

import entities.UnidadMedida;

@Stateless
@LocalBean
public class UnidadMedidaEJB extends GenericDaoImpl<UnidadMedida, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<UnidadMedida> unidadMedidas = new ArrayList<UnidadMedida>();
	   
	   public UnidadMedida findIdUnidadMedida(Long id){
		   try {
		   Query query = em.createQuery("select u from unidad_medida u where u.id = :id");
		   query.setParameter("id", id);
		   
		   
		   return (UnidadMedida) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<UnidadMedida> findAll() {
	    	TypedQuery<UnidadMedida> query = em.createQuery("select u from unidad_medida u", UnidadMedida.class);
	    	unidadMedidas = query.getResultList();
	    	return unidadMedidas;
	   }
	   
	   public List<UnidadMedida> findAllActivo() {
		   Query query = em.createQuery("select u from unidad_medida u where u.estado = :estado");
		   query.setParameter("estado", "ACTIVO");
	    	unidadMedidas = query.getResultList();
	    	return unidadMedidas;
	   }
}


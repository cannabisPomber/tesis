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

import entities.TipoPaletizacion;

@Stateless
@LocalBean
public class TipoPaletizacionEJB extends GenericDaoImpl<TipoPaletizacion, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<TipoPaletizacion> tipoPaletizacions = new ArrayList<TipoPaletizacion>();
	   
	   public TipoPaletizacion findIdTipoPaletizacion(Long id){
		   try {
		   Query query = em.createQuery("select u from tipoPaletizacion u where u.idTipoPaletizacion = :id");
		   query.setParameter("id", id);
		   
		   
		   return (TipoPaletizacion) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<TipoPaletizacion> findAll() {
	    	TypedQuery<TipoPaletizacion> query = em.createQuery("select u from tipoPaletizacion u", TipoPaletizacion.class);
	    	tipoPaletizacions = query.getResultList();
	    	return tipoPaletizacions;
	   }
	   
	   public List<TipoPaletizacion> findAllActivo() {
		   Query query = em.createQuery("select u from tipoPaletizacion u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
	    	tipoPaletizacions = query.getResultList();
	    	return tipoPaletizacions;
	   }
}

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
import entities.MarcaTarjeta;

@Stateless
@LocalBean
public class MarcaTarjetaEJB extends GenericDaoImpl<MarcaTarjeta, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<MarcaTarjeta> marcaTarjetas = new ArrayList<MarcaTarjeta>();
	
	   
	   public MarcaTarjeta findIdMarcaTarjeta(Long id){
		   try {
		   Query query = em.createQuery("select u from marca_tarjeta u where u.idTarjeta = :id");
		   query.setParameter("id", id);
		   
		   
		   return (MarcaTarjeta) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<MarcaTarjeta> findAll() {
		   TypedQuery<MarcaTarjeta> query = em.createQuery("select u from marca_tarjeta u", MarcaTarjeta.class);
	    	marcaTarjetas = query.getResultList();
	    	return marcaTarjetas;
	   }
	   
	   public List<MarcaTarjeta> findAllActivo() {
		   Query query = em.createQuery("select u from marca_tarjeta u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
		   marcaTarjetas = query.getResultList();
	    	return marcaTarjetas;
	   }
}


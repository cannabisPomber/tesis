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

import entities.CotizacionDetalle;
import entities.CotizacionDetalle;


@Stateless
@LocalBean
public class CotizacionDetalleEJB extends GenericDaoImpl<CotizacionDetalle, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<CotizacionDetalle> CotizacionDetalles = new ArrayList<CotizacionDetalle>();
	
	   
	   public CotizacionDetalle findIdCotizacionDetalle(Long id){
		   try {
		   Query query = em.createQuery("select u from cotizacion_detalle u where u.idCotizacionDetalle = :id");
		   query.setParameter("id", id);
		   
		   
		   return (CotizacionDetalle) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<CotizacionDetalle> findCotizacionDetalleByCabecera(Long id){
		   try {
		   Query query = em.createQuery("select u from cotizacion_detalle u where u.cotizacionCabecera.idCotizacionCabecera = :id");
		   query.setParameter("id", id);
		   
		   
		   return query.getResultList();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<CotizacionDetalle> findAll() {
	    	TypedQuery<CotizacionDetalle> query = em.createQuery("select u from cotizacion_detalle u", CotizacionDetalle.class);
	    	CotizacionDetalles = query.getResultList();
	    	return CotizacionDetalles;
	   }
	   
	   
	   public List<CotizacionDetalle> findDetalleCotizacion(Long idCotizacionCabecera) {
	    	TypedQuery<CotizacionDetalle> query = em.createQuery("select u from cotizacion_detalle u where u.cotizacionCabecera.idCotizacionCabecera = :idCotizacion", CotizacionDetalle.class);
	    	query.setParameter("idCotizacion", idCotizacionCabecera);
	    	CotizacionDetalles = query.getResultList();
	    	return CotizacionDetalles;
	   }
	   
}



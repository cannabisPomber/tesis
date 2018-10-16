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
import entities.Caja;
import entities.CajaDetalle;

@Stateless
@LocalBean
public class CajaDetalleEJB extends GenericDaoImpl<CajaDetalle, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<CajaDetalle> cajaDetalles = new ArrayList<CajaDetalle>();
	
	public List<CajaDetalle> findAll() {
    	TypedQuery<CajaDetalle> query = em.createQuery("select c from caja_detalle c", CajaDetalle.class);
    	cajaDetalles = query.getResultList();
    	return cajaDetalles;
    }
	
	public List<CajaDetalle> findAllActivo() {
    	TypedQuery<CajaDetalle> query = em.createQuery("select c from caja_detalle c where c.estado = 'Activo'", CajaDetalle.class);
    	cajaDetalles = query.getResultList();
    	return cajaDetalles;
    }
	
	public List<CajaDetalle> findDetalleCaja(Long idCaja) {
		//Query query = em.createQuery("select u from cajaDetalle u where u.caja.idCaja = :idCaja");
		Query query = em.createQuery("select c from caja_detalle c where c.caja.idCaja = :id");
    	query.setParameter("id", idCaja);
    	cajaDetalles = (List<CajaDetalle>)query.getResultList();
    	return cajaDetalles;
    }
	
	public CajaDetalle findCajaDetalleId (Long idCajaDetalle){
    	try {
    		   Query query = em.createQuery("select u from caja_detalle u where u.idCajaDetalle = :idCajaDetalle");
    		   query.setParameter("idCajaDetalle", idCajaDetalle);
    		   
    		   
    		   return (CajaDetalle) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

}


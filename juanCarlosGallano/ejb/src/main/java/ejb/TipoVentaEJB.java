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

import entities.TipoVenta;
@Stateless
@LocalBean
public class TipoVentaEJB extends GenericDaoImpl<TipoVenta, Serializable> {
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<TipoVenta> tipoVentas = new ArrayList<TipoVenta>();
	
	/*public Usuario findTipoVentaNombre(String nombreTipoVenta){
		   try {
		   Query query = em.createQuery("select u from tipoVenta u where u.nombreTipoVenta like '' ");
		   query.setParameter("nombre", nombreTipoVenta);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public TipoVenta findIdTipoVenta(Long id){
		   try {
		   Query query = em.createQuery("select u from tipo_venta u where u.idTipoVenta = :id");
		   query.setParameter("id", id);
		   
		   
		   return (TipoVenta) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<TipoVenta> findAll() {
	    	TypedQuery<TipoVenta> query = em.createQuery("select u from tipo_venta u", TipoVenta.class);
	    	tipoVentas = query.getResultList();
	    	return tipoVentas;
	   }
	   
	   public List<TipoVenta> findAllActivo() {
	    	TypedQuery<TipoVenta> query = em.createQuery("select u from tipo_venta u where u.estado ='ACTIVO'", TipoVenta.class);
	    	tipoVentas = query.getResultList();
	    	return tipoVentas;
	   }
}


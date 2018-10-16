package ejb;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import entities.OrdenCompra;

@Stateless
@LocalBean
public class OrdenCompraEJB extends GenericDaoImpl<OrdenCompra, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<OrdenCompra> ordenCompras = new ArrayList<OrdenCompra>();
	
	public List<OrdenCompra> findAll() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	
	public List<OrdenCompra> findAllConfirmado() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'CONFIRMADO'", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	
	public List<OrdenCompra> findAllRecepcioando() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'RECEPCIONADO'", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	public List<OrdenCompra> findAllConfirmadoPendiente() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'CONFIRMADO' or c.estado = 'PENDIENTE'", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	
	public List<OrdenCompra> findAllActivo() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'ACTIVO'", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	
	public List<OrdenCompra> findAllCerrado() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'CERRADO'", OrdenCompra.class);
    	ordenCompras = query.getResultList();
    	return ordenCompras;
    }
	
	public OrdenCompra findOrdenCompraId (Long idOrdenCompra){
    	try {
    		   Query query = em.createQuery("select u from orden_compra u where u.idOrdenCompra = :idOrdenCompra");
    		   query.setParameter("idOrdenCompra", idOrdenCompra);
    		   
    		   
    		   return (OrdenCompra) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	
	public void editarOrdenCompraRecepcionado(Long idOrdenCompra, Long idUsuarioRecepcion){
		    try {
		        Query query = em.createNativeQuery("update orden_compra  "
		                + "set estado = 'RECEPCIONADO', "
		                + "fecha_recepcion = current_timestamp, "
		                + "id_usuario_recepcion = ?"
		                + "WHERE id_orden_compra = ?; ");
		        query.setParameter(1, idUsuarioRecepcion);
		        query.setParameter(2, idOrdenCompra);
		        int v = query.executeUpdate();
		        //return (OrdenCompra) query.getSingleResult();
		    } catch (javax.persistence.NoResultException ex) {
		    	System.out.println("No se pudo Actualizar Correctamente Orden de Compra Recepcionado...");
		    }
	}
	
	public void cerrarOrdenCompraRecepcionado(Long idOrdenCompra){
	    try {
	        Query query = em.createNativeQuery("update orden_compra  "
	                + "set estado = 'CERRADO' "
	                + "WHERE id_orden_compra = ?;");
	        query.setParameter(1, idOrdenCompra);
	        int v = query.executeUpdate();
	    } catch (javax.persistence.NoResultException ex) {
	    	System.out.println("No se pudo Actualizar Correctamente Orden de Compra Recepcionado...");
	    }
	}
}
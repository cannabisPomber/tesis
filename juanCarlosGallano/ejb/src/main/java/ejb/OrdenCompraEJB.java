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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import entities.OrdenCompra;

@Stateless
@LocalBean
public class OrdenCompraEJB extends GenericDaoImpl<OrdenCompra, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<OrdenCompra> orden_compras = new ArrayList<OrdenCompra>();
	
	public List<OrdenCompra> findAll() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c", OrdenCompra.class);
    	orden_compras = query.getResultList();
    	return orden_compras;
    }
	
	public List<OrdenCompra> findAllConfirmado() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'Confirmado'", OrdenCompra.class);
    	orden_compras = query.getResultList();
    	return orden_compras;
    }
	public List<OrdenCompra> findAllConfirmadoPendiente() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'Confirmado' or c.estado = 'Pendiente'", OrdenCompra.class);
    	orden_compras = query.getResultList();
    	return orden_compras;
    }
	
	public List<OrdenCompra> findAllActivo() {
    	TypedQuery<OrdenCompra> query = em.createQuery("select c from orden_compra c where c.estado = 'Activo'", OrdenCompra.class);
    	orden_compras = query.getResultList();
    	return orden_compras;
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
}
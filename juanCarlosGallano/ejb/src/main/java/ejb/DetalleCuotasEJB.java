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

import entities.DetalleCuotas;
import entities.Factura;

@Stateless
@LocalBean
public class DetalleCuotasEJB extends GenericDaoImpl<DetalleCuotas, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<DetalleCuotas> cuotas = new ArrayList<DetalleCuotas>();
	
	   
	   public DetalleCuotas findIdDetalleCuotas(Long id){
		   try {
		   Query query = em.createQuery("select u from detalle_cuotas u where u.idDetalleCuota = :id");
		   query.setParameter("id", id);
		   
		   
		   return (DetalleCuotas) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<DetalleCuotas> findAll() {
	    	TypedQuery<DetalleCuotas> query = em.createQuery("select u from detalle_cuotas u", DetalleCuotas.class);
	    	cuotas = query.getResultList();
	    	return cuotas;
	   }
	   
	   public List<DetalleCuotas> findAllPendiente() {
	    	TypedQuery<DetalleCuotas> query = em.createQuery("select u from detalle_cuotas u where u.fechaPago is null order by u.fechaVencimiento", DetalleCuotas.class);
	    	cuotas = query.getResultList();
	    	return cuotas;
	   }
	   
	   public List<DetalleCuotas> findAllPagado() {
		   Query query = em.createQuery("select u from detalle_cuotas u where u.fechaPago is not null");
		   cuotas = query.getResultList();
	    	return cuotas;
	   }
	   
	   //Lista todas las facturas que tengan registro en 
	   public List<Factura>  findAllFacturaCredito(){
		   
		   List<Factura> listFacturaCredito;
		   Query query = em.createQuery("select distinct(u.factura) from detalle_cuotas u where u.fechaPago is  null");
		   listFacturaCredito = query.getResultList();
		   return listFacturaCredito;
	   }
	   
	 //Lista todas las facturas que tengan registro en 
	   public List<DetalleCuotas>  findDetalleCuotaFactura(Long idFactura){
		   try{
		   List<DetalleCuotas> listDetalleCredito;
		   Query query = em.createQuery("select u from detalle_cuotas u where u.fechaPago is null and u.factura.idFactura = :nroFactura");
		   query.setParameter("nroFactura", idFactura);
		   listDetalleCredito = query.getResultList();
		   return listDetalleCredito;
		   } catch (Exception ex){
			   System.out.println("Error de busqueda detalle cuotas:" + ex.getMessage());
			   return null;
		   }
	   }
}

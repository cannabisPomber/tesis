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

import entities.DetallePresupuesto;

@Stateless
@LocalBean
public class DetallePresupuestoEJB extends GenericDaoImpl<DetallePresupuesto, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<DetallePresupuesto> detallePresupuesto = new ArrayList<DetallePresupuesto>();
	    
    public List<DetallePresupuesto> findAll() {
    	TypedQuery<DetallePresupuesto> query = em.createQuery("select c from detalle_presupuesto c", DetallePresupuesto.class);
    	detallePresupuesto= query.getResultList();
    	return detallePresupuesto;
    }
    
    public DetallePresupuesto findDetallePresupuesto (Long idDetallePresupuesto){
    	try {
    		   Query query = em.createQuery("select u from detalle_presupuesto u where u.idDetallePresupuesto = :idDetallePresupuesto");
    		   query.setParameter("idDetallePresupuesto", idDetallePresupuesto);
    		   
    		   
    		   return (DetallePresupuesto) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
    
    public List<DetallePresupuesto> DetallePresupuesto (Long idPedido){
    	try {
    		   Query query = em.createQuery("select u from detalle_presupuesto u where u.pedido.idPedido = :idPedido");
    		   query.setParameter("idPedido", idPedido);
    		   
    		   
    		   return (List<DetallePresupuesto>) query.getResultList();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

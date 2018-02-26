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

import entities.StockDetalle;
import entities.StockDetalle;

@Stateless
@LocalBean
public class StockDetalleEJB extends GenericDaoImpl<StockDetalle, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<StockDetalle> cajas = new ArrayList<StockDetalle>();
	
	public List<StockDetalle> findAll() {
    	TypedQuery<StockDetalle> query = em.createQuery("select c from stock_detalle c", StockDetalle.class);
    	cajas = query.getResultList();
    	return cajas;
    }
	
	public List<StockDetalle> findAllActivo() {
    	TypedQuery<StockDetalle> query = em.createQuery("select c from stock_detalle c where c.estado = 'Activo'", StockDetalle.class);
    	cajas = query.getResultList();
    	return cajas;
    }
	
	public StockDetalle findStockDetalleId (Long idStockDetalle){
    		try {
    		   Query query = em.createQuery("select u from stock_detalle u where u.idStockDetalle = :idStockDetalle");
    		   query.setParameter("idStockDetalle", idStockDetalle);
    		   
    		   
    		   return (StockDetalle) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	/*public StockDetalle findProductoStockDetalle (Long idProducto){
		try {
		   Query query = em.createQuery("select u from stock_detalle u where u.idStockDetalle = :idStockDetalle");
		   query.setParameter("idProducto", idProducto);
		   
		   
		   return (StockDetalle) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	}*/

}

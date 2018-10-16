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

import entities.DetalleOrdenCompra;

@Stateless
@LocalBean
public class DetalleOrdenCompraEJB extends GenericDaoImpl<DetalleOrdenCompra, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<DetalleOrdenCompra> detalleOrdenCompras = new ArrayList<DetalleOrdenCompra>();
	    
    public List<DetalleOrdenCompra> findAll() {
    	TypedQuery<DetalleOrdenCompra> query = em.createQuery("select c from detalle_orden_compra c", DetalleOrdenCompra.class);
    	detalleOrdenCompras= query.getResultList();
    	return detalleOrdenCompras;
    }
    
    public DetalleOrdenCompra findDetalleOrdenCompra (Long idDetalleOrdenCompra){
    	try {
    		   Query query = em.createQuery("select u from detalle_orden_compra u where u.idDetalleOrdenCompra = :idDetalleOrdenCompra");
    		   query.setParameter("idDetalleOrdenCompra", idDetalleOrdenCompra);
    		   
    		   
    		   return (DetalleOrdenCompra) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
    
    public List<DetalleOrdenCompra> DetalleOrdenCompra (Long idOrdenCompra){
    	try {
    		   Query query = em.createQuery("select u from detalle_orden_compra u where u.ordenCompra.idOrdenCompra = :idOrdenCompra");
    		   query.setParameter("idOrdenCompra", idOrdenCompra);
    		   
    		   
    		   return (List<DetalleOrdenCompra>) query.getResultList();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
    
    public void deleteConstraintUniqueProducto(){
    	
    	//Borrar constraint unique de id producto que no se sabe por que  genera
    	try {
 		   Query query = em.createNativeQuery("ALTER TABLE detalle_orden_compra DROP CONSTRAINT :parametro ");
 		   query.setParameter("parametro", "uk_fmhbywwawj5f33fu2fw5w41xc");
 		   
 		   
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 		   }
    	
    }
}

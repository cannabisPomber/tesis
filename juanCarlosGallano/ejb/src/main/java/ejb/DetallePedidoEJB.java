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

import entities.DetallePedido;

@Stateless
@LocalBean
public class DetallePedidoEJB extends GenericDaoImpl<DetallePedido, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();
	    
    public List<DetallePedido> findAll() {
    	TypedQuery<DetallePedido> query = em.createQuery("select c from detalle_pedido c", DetallePedido.class);
    	detallePedidos= query.getResultList();
    	return detallePedidos;
    }
    
    public DetallePedido findDetallePedido (Long idDetallePedido){
    	try {
    		   Query query = em.createQuery("select u from detalle_pedido u where u.idDetallePedido = :idDetallePedido");
    		   query.setParameter("idDetallePedido", idDetallePedido);
    		   
    		   
    		   return (DetallePedido) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
    
    public List<DetallePedido> DetallePedido (Long idPedido){
    	try {
    		   Query query = em.createQuery("select u from detalle_pedido u where u.pedido.idPedido = :idPedido");
    		   query.setParameter("idPedido", idPedido);
    		   
    		   
    		   return (List<DetallePedido>) query.getResultList();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

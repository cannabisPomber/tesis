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

import entities.Deposito;
import entities.DetalleDeposito;


@Stateless
@LocalBean
public class DetalleDepositoEJB extends GenericDaoImpl<DetalleDeposito, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<DetalleDeposito> detalleDeposito = new ArrayList<DetalleDeposito>();
	
	public DetalleDeposito findIdDetalleDeposito(Long id){
		   try {
		   Query query = em.createQuery("select u from detalle_deposito u where u.idDetalleDeposito = :id");
		   query.setParameter("id", id);
		   
		   
		   return (DetalleDeposito) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<DetalleDeposito> findAll() {
	    	TypedQuery<DetalleDeposito> query = em.createQuery("select u from detalle_deposito u", DetalleDeposito.class);
	    	detalleDeposito = query.getResultList();
	    	return detalleDeposito;
	   }
	   
	   
	   public DetalleDeposito findDeposito(Long idProducto, Long idDeposito){
		   try {
		   Query query = em.createQuery("select u from detalle_deposito u where u.producto.idProducto = :idProducto and u.deposito.idDeposito = :idDepo");
		   query.setParameter("idProducto", idProducto);
		   query.setParameter("idDepo", idDeposito);
		   
		   
		   return (DetalleDeposito) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<DetalleDeposito> findDetalleDeposito(Long idDeposito){
		   
		   Query query = em.createQuery("select u from detalle_deposito u where u.deposito.idDeposito = :idDeposito");
		   query.setParameter("idDeposito", idDeposito);
		   
		   return query.getResultList();
	   }

}

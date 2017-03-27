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

import entities.FormaPago;

@Stateless
@LocalBean
public class FormaPagoEJB  extends GenericDaoImpl<FormaPago, Serializable>{
	
@PersistenceContext(unitName="primary")
EntityManager em;
public List<FormaPago> FormaPagos = new ArrayList<FormaPago>();
	
	/*public Usuario findFormaPagoNombre(String nombreFormaPago){
		   try {
		   Query query = em.createQuery("select u from FormaPago u where u.nombreFormaPago like '' ");
		   query.setParameter("nombre", nombreFormaPago);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public FormaPago findIdFormaPago(Long id){
		   try {
		   Query query = em.createQuery("select u from tipo_venta u where u.idFormaPago = :id");
		   query.setParameter("id", id);
		   
		   
		   return (FormaPago) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<FormaPago> findAll() {
	    	TypedQuery<FormaPago> query = em.createQuery("select u from forma_pago u", FormaPago.class);
	    	FormaPagos = query.getResultList();
	    	return FormaPagos;
	   }

}

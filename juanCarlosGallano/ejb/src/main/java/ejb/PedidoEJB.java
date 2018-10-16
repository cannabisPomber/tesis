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

import entities.Pedido;

@Stateless
@LocalBean
public class PedidoEJB extends GenericDaoImpl<Pedido, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Pedido> pedidos = new ArrayList<Pedido>();
	
	public List<Pedido> findAll() {
    	TypedQuery<Pedido> query = em.createQuery("select c from pedido c", Pedido.class);
    	pedidos = query.getResultList();
    	return pedidos;
    }
	
	public List<Pedido> findAllActivo() {
    	TypedQuery<Pedido> query = em.createQuery("select c from pedido c where c.estado = 'ACTIVO'", Pedido.class);
    	pedidos = query.getResultList();
    	return pedidos;
    }
	
	public Pedido findPedidoId (Long idPedido){
    	try {
    		   Query query = em.createQuery("select u from pedido u where u.idPedido = :idPedido");
    		   query.setParameter("idPedido", idPedido);
    		   
    		   
    		   return (Pedido) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

	public List<Pedido> anularPedido (Date fechaInicio, Date fechaFin){
		
		
		Calendar calendar = Calendar.getInstance();     
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(calendar.getTime());
		try {
			Date today = dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
 		   Query query = em.createQuery("select u from pedido u where u.fecha >=  :fechaIni and u.fecha <=  :fechaFi");
 		   query.setParameter("fechaIni", fechaInicio);
 		  query.setParameter("fechaFi", fechaFin);
 		   
 		   
 		   return (List<Pedido>) query.getResultList();
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 			   return null;
 		   }*/
			
		Date inicioTimespamp = new Date(fechaInicio.getTime() - (86400000*30));
		Date finTimespamp = new Date(fechaFin.getTime() - (86400000*30));


		Query q = em.createQuery("Select m from pedido m " 
		    + "where m.fecha <= :inicio and m.fecha >= :fin");
		q.setParameter("inicio", inicioTimespamp ); 
		q.setParameter("fin", finTimespamp);

		List<Pedido> results = (List<Pedido>) q.getResultList();
		return results;
		
	}
}




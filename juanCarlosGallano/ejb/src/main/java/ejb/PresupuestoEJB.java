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

import entities.Presupuesto;

@Stateless
@LocalBean
public class PresupuestoEJB extends GenericDaoImpl<Presupuesto, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	
	public List<Presupuesto> findAll() {
    	TypedQuery<Presupuesto> query = em.createQuery("select c from presupuesto c", Presupuesto.class);
    	presupuestos = query.getResultList();
    	return presupuestos;
    }
	
	public List<Presupuesto> findAllActivo() {
    	TypedQuery<Presupuesto> query = em.createQuery("select c from presupuesto c where c.estado = 'Activo'", Presupuesto.class);
    	presupuestos = query.getResultList();
    	return presupuestos;
    }
	
	public Presupuesto findPresupuestoId (Long idPresupuesto){
    	try {
    		   Query query = em.createQuery("select u from presupuesto u where u.idPresupuesto = :idPresupuesto");
    		   query.setParameter("idPresupuesto", idPresupuesto);
    		   
    		   
    		   return (Presupuesto) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

	public List<Presupuesto> anularPresupuesto (Date fechaInicio, Date fechaFin){
		
		
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
 		   Query query = em.createQuery("select u from presupuesto u where u.fecha >=  :fechaIni and u.fecha <=  :fechaFi");
 		   query.setParameter("fechaIni", fechaInicio);
 		  query.setParameter("fechaFi", fechaFin);
 		   
 		   
 		   return (List<Presupuesto>) query.getResultList();
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 			   return null;
 		   }*/
			
		Date inicioTimespamp = new Date(fechaInicio.getTime() - (86400000*30));
		Date finTimespamp = new Date(fechaFin.getTime() - (86400000*30));


		Query q = em.createQuery("Select m from presupuesto m " 
		    + "where m.fecha <= :inicio and m.fecha >= :fin");
		q.setParameter("inicio", inicioTimespamp ); 
		q.setParameter("fin", finTimespamp);

		List<Presupuesto> results = (List<Presupuesto>) q.getResultList();
		return results;
		
	}
}


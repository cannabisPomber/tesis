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

import entities.Grupo;
import entities.Caja;
import entities.Usuario;

@Stateless
@LocalBean
public class CajaEJB extends GenericDaoImpl<Caja, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Caja> cajas = new ArrayList<Caja>();
	
	public List<Caja> findAll() {
    	TypedQuery<Caja> query = em.createQuery("select c from caja c", Caja.class);
    	cajas = query.getResultList();
    	return cajas;
    }
	
	public List<Caja> findAllActivo() {
    	TypedQuery<Caja> query = em.createQuery("select c from caja c where c.estado = 'Activo'", Caja.class);
    	cajas = query.getResultList();
    	return cajas;
    }

	
	public Caja findCajaId (Long idCaja){
    		try {
    		   Query query = em.createQuery("select u from caja u where u.idCaja = :idCaja");
    		   query.setParameter("idCaja", idCaja);
    		   
    		   
    		   return (Caja) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	public List<Caja> findCajaPuestoVenta (Long idPuestoVenta){
		try {
		   Query query = em.createQuery("select u from caja u where u.idPuestoVenta = :idPuestoVenta and u.fechaFin is null");
		   query.setParameter("idPuestoVenta", idPuestoVenta);
		   
		   
		   return (List<Caja>)query.getResultList();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	}
	
	public Caja cajaAbierta(Usuario user){
		try {
 		   Query query = em.createQuery("select u from caja u where u.usuario.idUsuario = :idUser and u.fechaFin is null");
 		   query.setParameter("idUser", user.getIdUsuario());
 		   
 		   
 		   return (Caja) query.getSingleResult();
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 			   return null;
 		   }
	}
	
	
	public Caja cajaAbiertaTesoreria(){
		try {
 		   Query query = em.createQuery("select u from caja u where u.puestoVenta.cajaTesoreria = TRUE and u.fechaFin is null");
 		   
 		   
 		   return (Caja) query.getSingleResult();
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 			   return null;
 		   }
	}
	
	//Retorna Caja Abierta del dia
	public Caja cajaAbiertaSinUser(){
		try {
 		   Query query = em.createQuery("select u from caja u where u.fechaFin is null");
 		   
 		   
 		   return (Caja) query.getSingleResult();
 		   }
 		   catch (Exception ex){
 			   return null;
 		   }
	}
	
	public void cerrarCaja(Caja cajaCerrar){
		    try {
		        Query query = em.createNativeQuery("update caja  "
		                + "set fecha_fin = ? "
		                + "WHERE id_caja = ?;");
		        query.setParameter(1, cajaCerrar.getFechaFin());
		        query.setParameter(2, cajaCerrar.getIdCaja());
		        int v = query.executeUpdate();
		    } catch (javax.persistence.NoResultException ex) {
		    	System.out.println("No se pudo Actualizar Correctamente Caja...");
		    }
	}

}


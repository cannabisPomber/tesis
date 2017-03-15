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
	
	public Caja cajaAbierta(Usuario user){
		try {
 		   Query query = em.createQuery("select u from caja u where u.usuario.idUsuario = :idUser and u.fechaFin = null");
 		   query.setParameter("idUser", user.getIdUsuario());
 		   
 		   
 		   return (Caja) query.getSingleResult();
 		   }
 		   catch (Exception ex){
 			   System.out.println(" Error " + ex.getMessage());
 			   return null;
 		   }
	}

}


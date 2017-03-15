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

import entities.ArqueoCaja;

@Stateless
@LocalBean
public class ArqueoCajaEJB extends GenericDaoImpl<ArqueoCaja, Serializable>{

	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<ArqueoCaja> arqueoCajas = new ArrayList<ArqueoCaja>();
	
	public List<ArqueoCaja> findAll() {
    	TypedQuery<ArqueoCaja> query = em.createQuery("select c from arqueoCaja c", ArqueoCaja.class);
    	arqueoCajas = query.getResultList();
    	return arqueoCajas;
    }
	//No apica estado a arqueo caja
	public List<ArqueoCaja> findAllActivo() {
    	TypedQuery<ArqueoCaja> query = em.createQuery("select c from arqueoCaja c where c.estado = 'Activo'", ArqueoCaja.class);
    	arqueoCajas = query.getResultList();
    	return arqueoCajas;
    }
	
	public ArqueoCaja findArqueoCajaId (Long idArqueoCaja){
    	try {
    		   Query query = em.createQuery("select u from arqueoCaja u where u.idArqueoCaja = :idArqueoCaja");
    		   query.setParameter("idArqueoCaja", idArqueoCaja);
    		   
    		   
    		   return (ArqueoCaja) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

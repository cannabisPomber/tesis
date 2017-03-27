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

import entities.Moneda;


@Stateless
@LocalBean
public class MonedaEJB extends GenericDaoImpl<Moneda, Serializable> {

	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	
	
public List<Moneda> monedas = new ArrayList<Moneda>();
	
	public List<Moneda> findAll() {
    	TypedQuery<Moneda> query = em.createQuery("select c from moneda c", Moneda.class);
    	monedas = query.getResultList();
    	return monedas;
    }
	
	public Moneda findMonedaId (Long idMoneda){
    	try {
    		   Query query = em.createQuery("select u from moneda u where u.idMoneda = :idMoneda");
    		   query.setParameter("idMoneda", idMoneda);
    		   
    		   
    		   return (Moneda) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}

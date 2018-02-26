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

import entities.Lote;

@Stateless
@LocalBean
public class LoteEJB extends GenericDaoImpl<Lote, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Lote> loteList = new ArrayList<Lote>();
	
	public List<Lote> findAll() {
    	TypedQuery<Lote> query = em.createQuery("select c from lote c", Lote.class);
    	loteList = query.getResultList();
    	return loteList;
    }
	
	public Lote findLoteId (Long idLote){
    	try {
    		   Query query = em.createQuery("select u from lote u where u.idLote = :idLote");
    		   query.setParameter("idLote", idLote);
    		   
    		   
    		   return (Lote) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}
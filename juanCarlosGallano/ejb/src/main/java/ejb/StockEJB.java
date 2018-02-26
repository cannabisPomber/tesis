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

import entities.Stock;

@Stateless
@LocalBean
public class StockEJB extends GenericDaoImpl<Stock, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Stock> stocks = new ArrayList<Stock>();
	
	public List<Stock> findAll() {
    	TypedQuery<Stock> query = em.createQuery("select c from stock c", Stock.class);
    	stocks = query.getResultList();
    	return stocks;
    }
	
	
	public Stock findStockId (Long idStock){
    	try {
    		   Query query = em.createQuery("select u from stock u where u.idStock = :idStock");
    		   query.setParameter("idStock", idStock);
    		   
    		   
    		   return (Stock) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	public Stock findStockCodigoBarra (String codBarra){
    	try {
    		   Query query = em.createQuery("select u from stock u where u.codigoBarra = :codigoBarra");
    		   query.setParameter("codigoBarra", codBarra);
    		   
    		   
    		   return (Stock) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}
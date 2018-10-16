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
@Stateless
@LocalBean
public class DepositoEJB extends GenericDaoImpl<Deposito, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Deposito> depositos = new ArrayList<Deposito>();
	
	/*public Usuario findDepositoNombre(String nombreDeposito){
		   try {
		   Query query = em.createQuery("select u from deposito u where u.nombreDeposito like '' ");
		   query.setParameter("nombre", nombreDeposito);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public Deposito findIdDeposito(Long id){
		   try {
		   Query query = em.createQuery("select u from deposito u where u.idDeposito = :id");
		   query.setParameter("id", id);
		   
		   
		   return (Deposito) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<Deposito> findAll() {
	    	TypedQuery<Deposito> query = em.createQuery("select u from deposito u", Deposito.class);
	    	depositos = query.getResultList();
	    	return depositos;
	   }
	   
	   public List<Deposito> findAllDepositoEmpresa(Long idEmpresa) {
		   try {
	    	TypedQuery<Deposito> query = em.createQuery("select u from deposito u  where u.empresa.idEmpresa = :id", Deposito.class);
	    	query.setParameter("id", idEmpresa);
	    	depositos = query.getResultList();
	    	return depositos;
		   }  catch (Exception ex){
			   return null;
		   }
	   }
	   
	   public List<Deposito> findAllActivo() {
	    	TypedQuery<Deposito> query = em.createQuery("select u from deposito u where u.estado = 'ACTIVO'", Deposito.class);
	    	depositos = query.getResultList();
	    	return depositos;
	   }
}

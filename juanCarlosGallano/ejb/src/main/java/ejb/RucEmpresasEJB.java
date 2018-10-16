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

import entities.RucEmpresas;
@Stateless
@LocalBean
public class RucEmpresasEJB extends GenericDaoImpl<RucEmpresas, Serializable> {
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<RucEmpresas> rucEmpresas = new ArrayList<RucEmpresas>();
	
	/*public RucEmpresas findRucEmpresas(String nombreRucEmpresas){
		   try {
		   Query query = em.createQuery("select u from rucEmpresas u where u.nombreRucEmpresas like '' ");
		   query.setParameter("nombre", nombreRucEmpresas);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public RucEmpresas findIdRucEmpresas(Long id){
		   try {
		   Query query = em.createQuery("select u from ruc_empresas u where u.idRucEmpresas = :id");
		   query.setParameter("id", id);
		   
		   
		   return (RucEmpresas) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public RucEmpresas findRucEmpresas(String ruc){
		   try {
		   Query query = em.createQuery("select u from ruc_empresas u where u.ruc = :id");
		   query.setParameter("ruc", ruc);
		   
		   
		   return (RucEmpresas) query.getSingleResult();
		   }
		   catch (Exception ex){
			   return null;
		   }
	   }
	   
	   public List<RucEmpresas> findAll() {
	    	TypedQuery<RucEmpresas> query = em.createQuery("select u from tipo_venta u", RucEmpresas.class);
	    	rucEmpresas = query.getResultList();
	    	return rucEmpresas;
	   }
	   
}

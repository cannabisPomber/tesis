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
import entities.Proveedor;

@Stateless
@LocalBean
public class ProveedorEJB extends GenericDaoImpl<Proveedor, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	private List<Proveedor> proveedorList = new ArrayList<Proveedor>();
	
	public Proveedor findProveedorId(Long id){
		try {
			   Query query = em.createQuery("select u from proveedor u where u.idProveedor = :id");
			   query.setParameter("id", id);
			   
			   
			   return (Proveedor) query.getSingleResult();
			   }
			   catch (Exception ex){
				   System.out.println(" Error " + ex.getMessage());
				   return null;
			   }
	}
	
	public Proveedor findProveedorRucCedula(String rucCedula){
		try {
			   Query query = em.createQuery("select u from proveedor u where u.rucCedula like '%ruc%'");
			   query.setParameter("ruc", rucCedula);
			   
			   
			   return (Proveedor) query.getSingleResult();
			   }
			   catch (Exception ex){
				   System.out.println(" Error " + ex.getMessage());
				   return null;
			   }
	}
	
	public List<Proveedor> findAll() {
    	TypedQuery<Proveedor> query = em.createQuery("select u from proveedor u", Proveedor.class);
    	proveedorList = query.getResultList();
    	return proveedorList;
   }

}

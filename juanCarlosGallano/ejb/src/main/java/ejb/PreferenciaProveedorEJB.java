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

import entities.PreferenciaProveedor;

@Stateless
@LocalBean
public class PreferenciaProveedorEJB extends GenericDaoImpl<PreferenciaProveedor, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<PreferenciaProveedor> preferenciaProveedors = new ArrayList<PreferenciaProveedor>();
	
	public List<PreferenciaProveedor> findAll() {
    	TypedQuery<PreferenciaProveedor> query = em.createQuery("select c from preferencia_proveedor c", PreferenciaProveedor.class);
    	preferenciaProveedors = query.getResultList();
    	return preferenciaProveedors;
    }
	
	
	public PreferenciaProveedor findPreferenciaProveedorId (Long idPreferenciaProveedor){
    	try {
    		   Query query = em.createQuery("select u from preferencia_proveedor u where u.idPreferenciaProveedor = :idPreferenciaProveedor");
    		   query.setParameter("idPreferenciaProveedor", idPreferenciaProveedor);
    		   
    		   
    		   return (PreferenciaProveedor) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	public PreferenciaProveedor findProducto (Long idProducto){
    	try {
    		   Query query = em.createQuery("select u from preferencia_proveedor u where u.producto.idProducto = :idProducto");
    		   query.setParameter("idProducto", idProducto);
    		   
    		   
    		   return (PreferenciaProveedor) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

}


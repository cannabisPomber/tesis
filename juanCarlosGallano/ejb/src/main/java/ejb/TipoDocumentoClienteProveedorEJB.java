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
import entities.TipoDocumentoClienteProveedor;
import entities.Usuario;

@Stateless
@LocalBean
public class TipoDocumentoClienteProveedorEJB extends GenericDaoImpl<TipoDocumentoClienteProveedor, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<TipoDocumentoClienteProveedor> tipoDocumentoClienteProveedors = new ArrayList<TipoDocumentoClienteProveedor>();
	
	public List<TipoDocumentoClienteProveedor> findAll() {
    	TypedQuery<TipoDocumentoClienteProveedor> query = em.createQuery("select c from tipo_documento_cliente_proveedor c", TipoDocumentoClienteProveedor.class);
    	tipoDocumentoClienteProveedors = query.getResultList();
    	return tipoDocumentoClienteProveedors;
    }
	
	
	public TipoDocumentoClienteProveedor findTipoDocumentoClienteProveedorId (Long idTipoDocumentoClienteProveedor){
    		try {
    		   Query query = em.createQuery("select u from tipo_documento_cliente_proveedor u where u.idTipoDocumentoClienteProveedor = :idTipoDocumentoClienteProveedor");
    		   query.setParameter("idTipoDocumentoClienteProveedor", idTipoDocumentoClienteProveedor);
    		   
    		   
    		   return (TipoDocumentoClienteProveedor) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	public TipoDocumentoClienteProveedor findTipoDocumentoClienteProveedorCliente (Long idCliente){
		try {
		   Query query = em.createQuery("select u from tipo_documento_cliente_proveedor u where u.cliente.idCliente = :idCliente");
		   query.setParameter("idCliente", idCliente);
		   
		   
		   return (TipoDocumentoClienteProveedor) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	}
	
	public TipoDocumentoClienteProveedor findTipoDocumentoClienteRUC (String ruc){
		try {
		   Query query = em.createQuery("select u from tipo_documento_cliente_proveedor u where u.numeroDocumento = :ruc");
		   query.setParameter("ruc", ruc);
		   
		   
		   return (TipoDocumentoClienteProveedor) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	}
	
	public TipoDocumentoClienteProveedor findTipoDocumentoClienteProveedorProveedor (Long idProveedor){
		try {
		   Query query = em.createQuery("select u from tipo_documento_cliente_proveedor u where u.proveedor.idProveedor = :idProveedor");
		   query.setParameter("idProveedor", idProveedor);
		   
		   
		   return (TipoDocumentoClienteProveedor) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	}
	

}


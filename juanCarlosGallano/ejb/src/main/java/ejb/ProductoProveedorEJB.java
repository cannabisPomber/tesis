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

import entities.ProductoProveedor;
import entities.Proveedor;

@Stateless
@LocalBean
public class ProductoProveedorEJB extends GenericDaoImpl<ProductoProveedor, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<ProductoProveedor> productoProveedors = new ArrayList<ProductoProveedor>();
	   
	   public ProductoProveedor findIdProductoProveedor(Long id){
		   try {
		   Query query = em.createQuery("select u from producto_proveedor u where u.idProductoProveedor = :id");
		   query.setParameter("id", id);
		   
		   
		   return (ProductoProveedor) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<ProductoProveedor> findAll() {
	    	TypedQuery<ProductoProveedor> query = em.createQuery("select u from producto_proveedor u", ProductoProveedor.class);
	    	 
	    	return query.getResultList();
	   }
	   
	   public List<ProductoProveedor> findAllActivo() {
		   Query query = em.createQuery("select u from producto_proveedor u where u.estado = :estado");
		   query.setParameter("estado", "Activo");
	    	return query.getResultList();
	   }
	   
	   public ProductoProveedor findProductoProveedorPrecioMenor(Long idProducto){
		   try {
		   Query query = em.createQuery("select u from producto_proveedor u where u.producto.idProducto = :idProducto order by u.precioCompra ASC");
		   query.setParameter("idProducto", idProducto);
		   
		   query.setMaxResults(1);
		   
		   
		   return (ProductoProveedor) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   //Busca lista de proveedores por producto  
	   public List<ProductoProveedor> findProveedor (Long idProducto){
		   try {
		   Query query = em.createQuery("select u from producto_proveedor u where u.producto.idProducto = :idProducto and u.estado = 'Activo'");
		   query.setParameter("idProducto", idProducto);
		   
		   
		   return (List<ProductoProveedor>) query.getResultList();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   	}
	   }
	   
}
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

import entities.Producto;
import entities.ProductoProveedor;
import entities.Usuario;

@Stateless
@LocalBean
public class ProductoEJB extends GenericDaoImpl<Producto, Serializable> {

	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<Producto> productos = new ArrayList<Producto>();
	
	 public List<Producto> findAll() {
	    	TypedQuery<Producto> query = em.createQuery("select c from producto c", Producto.class);
	    	productos = query.getResultList();
	    	return productos;
	   }
	 
	 public List<Producto> findAllActivo() {
	    	TypedQuery<Producto> query = em.createQuery("select c from producto c where c.estado = 'ACTIVO'", Producto.class);
	    	productos = query.getResultList();
	    	return productos;
	   }
	 
	 public Producto findIdProducto(Long id){
		   try {
		   Query query = em.createQuery("select u from producto u where u.idProducto = :id");
		   query.setParameter("id", id);
		   
		   Producto prueba = (Producto) query.getSingleResult();
		   return prueba;
//		   return (Producto) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	 
	 //Listado de productos que poseen proveedor
	 public List<Producto> findProductoConProveedor(){
		  
		    	TypedQuery<Producto> query = em.createQuery("select u.producto from producto_proveedor u", Producto.class);
		    	 
		    	return query.getResultList();

	 }
	 
	 public Producto findProductoCodigoBarra(String codBarra){
		  try {
		   Query query = em.createQuery("select u from producto u where u.codigoBarra = :codBarra");
		   query.setParameter("codBarra", codBarra);
		   
		   Producto prueba = (Producto) query.getSingleResult();
		   return prueba;
//		   return (Producto) query.getSingleResult();
		   }
		   catch (Exception ex){
			   return null;
		   }
	   }
	 
	 public void eliminarProducto( Producto pro){
		   em.createQuery("delete from product where idProducto = : id").setParameter("id", pro.getIdProducto()).executeUpdate();
	   }
}

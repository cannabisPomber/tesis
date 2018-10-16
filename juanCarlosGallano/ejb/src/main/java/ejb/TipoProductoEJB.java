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

import entities.TipoProducto;
@Stateless
@LocalBean
public class TipoProductoEJB extends GenericDaoImpl<TipoProducto, Serializable> {
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<TipoProducto> tipoProductos = new ArrayList<TipoProducto>();
	
	/*public Usuario findTipoProductoNombre(String nombreTipoProducto){
		   try {
		   Query query = em.createQuery("select u from deposito u where u.nombreTipoProducto like '' ");
		   query.setParameter("nombre", nombreTipoProducto);
		   
		   return (Usuario) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }*/
	   
	   public TipoProducto findIdTipoProducto(Long id){
		   try {
		   Query query = em.createQuery("select u from tipo_producto u where u.idTipoProducto = :id");
		   query.setParameter("id", id);
		   
		   
		   return (TipoProducto) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
	   }
	   
	   public List<TipoProducto> findAll() {
	    	TypedQuery<TipoProducto> query = em.createQuery("select u from tipo_producto u", TipoProducto.class);
	    	tipoProductos = query.getResultList();
	    	return tipoProductos;
	   }
	   
	   public List<TipoProducto> findAllActivo() {
	    	TypedQuery<TipoProducto> query = em.createQuery("select u from tipo_producto u where u.estado = 'ACTIVO'", TipoProducto.class);
	    	tipoProductos = query.getResultList();
	    	return tipoProductos;
	   }
}

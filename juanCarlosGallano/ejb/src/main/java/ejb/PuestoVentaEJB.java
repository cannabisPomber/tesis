

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
import entities.PuestoVenta;
import entities.Usuario;

@Stateless
@LocalBean
public class PuestoVentaEJB extends GenericDaoImpl<PuestoVenta, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<PuestoVenta> puesto_ventas = new ArrayList<PuestoVenta>();
	
	public List<PuestoVenta> findAll() {
    	TypedQuery<PuestoVenta> query = em.createQuery("select c from puesto_venta c", PuestoVenta.class);
    	puesto_ventas = query.getResultList();
    	return puesto_ventas;
    }
	
	public List<PuestoVenta> findAllNoAasignado() {
    	TypedQuery<PuestoVenta> query = em.createQuery("select c from puesto_venta c where c.asignado = FALSE", PuestoVenta.class);
    	puesto_ventas = query.getResultList();
    	return puesto_ventas;
    }
	
	public PuestoVenta findPuestoVentaId (Long idPuestoVenta){
    		try {
    		   Query query = em.createQuery("select u from puesto_venta u where u.idPuestoVenta = :idPuestoVenta");
    		   query.setParameter("idPuestoVenta", idPuestoVenta);
    		   
    		   
    		   return (PuestoVenta) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
	
	
	/*public PuestoVenta findPuestoVentaUsuario (Long idUsuario){
		try {
		   Query query = em.createQuery("select u from puesto_venta u where u.usuarioAsignado.idUsuario = :idUsuario");
		   query.setParameter("idUsuario", idUsuario);
		   
		   
		   return (PuestoVenta) query.getSingleResult();
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
}*/
}



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

import entities.Cliente;

@Stateless
@LocalBean
public class ClienteEJB extends GenericDaoImpl<Cliente, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	private List<Cliente> clientes = new ArrayList<Cliente>();

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
	
	//Busqueda por cedula de iddentidad
	public Cliente findClienteCedula(String cedula){
		try{
			Query query = em.createQuery("SELECT c FROM cliente c WHERE c.cedula =:cedula ");
			   query.setParameter("cedula", cedula);
			   
			   
			   return (Cliente) query.getSingleResult();

		} catch (Exception ex){
			return null;
		}
	}
	public Cliente findClienteRuc(String ruc){
		try{
			Query query = em.createQuery("SELECT c FROM cliente c WHERE c.ruc =:ruc ");
			   query.setParameter("ruc", ruc);
			   
			   
			   return (Cliente) query.getSingleResult();

		} catch (Exception ex){
			return null;
		}
	}
	
	public Cliente findClienteIdCliente(Long idCliente){
		try{
			Query query = em.createQuery("SELECT c FROM cliente c WHERE c.idCliente =:idCliente ");
			   query.setParameter("idCliente", idCliente);
			   
			   
			   return (Cliente) query.getSingleResult();

		} catch (Exception ex){
			return null;
		}
	}
	
	public List<Cliente> findAll() {
    	TypedQuery<Cliente> query = em.createQuery("select u from cliente u", Cliente.class);
    	clientes = query.getResultList();
    	return clientes;
   }

	public List<Cliente> findAllActivo() {
    	TypedQuery<Cliente> query = em.createQuery("select u from cliente u where u.estado = 'Activo'", Cliente.class);
    	clientes = query.getResultList();
    	return clientes;
   }
}

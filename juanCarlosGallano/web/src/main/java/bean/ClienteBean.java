package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import ejb.ClienteEJB;
import entities.Cliente;

@ManagedBean(name ="clienteBean")
@SessionScoped 
public class ClienteBean {
	@EJB
	ClienteEJB clienteEjb;
	
	private Cliente clienteEdicion;
	private List<Cliente> clientes = new ArrayList<Cliente>();
	public Cliente getClienteEdicion() {
		return clienteEdicion;
	}
	public void setClienteEdicion(Cliente clienteEdicion) {
		this.clienteEdicion = clienteEdicion;
	}
	public List<Cliente> getClientes() {
		return clienteEjb.findAll();
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
}

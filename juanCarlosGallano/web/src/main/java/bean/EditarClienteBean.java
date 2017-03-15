package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.ClienteEJB;
import entities.Cliente;
@ManagedBean (name = "editarClienteBean")
@ViewScoped
public class EditarClienteBean {
	@EJB
	ClienteEJB clienteEjb;
	
	private Long idCliente;
	private Cliente clienteEdicion;
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Cliente getClienteEdicion() {
		return clienteEdicion;
	}
	public void setClienteEdicion(Cliente clienteEdicion) {
		this.clienteEdicion = clienteEdicion;
	}
	
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idCliente = Long.parseLong(params.get("idCliente"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idCliente != null){
				clienteEdicion = clienteEjb.findClienteIdCliente(idCliente);
			} else {
				clienteEdicion = new Cliente();
			}
		}
	}
	
	public void guardarCliente(){
		// Debe persistir el usuario
		if(clienteEdicion.getIdCliente() == null){
			clienteEdicion = clienteEjb.create(clienteEdicion);
			 FacesContext.getCurrentInstance().addMessage("Cliente Creado", new FacesMessage("Nuevo Cliente Creado."));
			 clienteEdicion = new Cliente();
		} else {
			// Modificar usuario
			clienteEdicion = clienteEjb.update(clienteEdicion);
			FacesContext.getCurrentInstance().addMessage("Cliente Modificado", new FacesMessage("Cliente Modificado."));
		}
	}
}

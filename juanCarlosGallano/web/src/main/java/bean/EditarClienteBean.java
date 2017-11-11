package bean;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.event.CaptureEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import ejb.ClienteEJB;
import entities.Cliente;
@ManagedBean (name = "editarClienteBean")
@ViewScoped
public class EditarClienteBean {
	@EJB
	ClienteEJB clienteEjb;
	
	private Long idCliente;
	private Cliente clienteEdicion;
	
	private Boolean rucoCedula;
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
			if (idCliente != null){
				clienteEdicion = clienteEjb.findClienteIdCliente(idCliente);
			} else {
				clienteEdicion = new Cliente();
			}
			
			GeoApiContext context = new GeoApiContext.Builder()
				    .apiKey("AIzaSyDp4nE1vThZ0FdRKKzUBXFMJqALl587DKI")
				    .build();
				GeocodingResult[] results = null;
				try {
					results = GeocodingApi.geocode(context,
					    "La nueva Olla").await();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error mapa la nueva olla :" + e.getMessage());
				}
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				System.out.println(gson.toJson(results[0].geometry.location));
		}
	}
	
	
	public Boolean getRucoCedula() {
		return rucoCedula;
	}
	public void setRucoCedula(Boolean rucoCedula) {
		this.rucoCedula = rucoCedula;
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

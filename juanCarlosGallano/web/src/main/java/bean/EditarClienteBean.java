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

import ejb.ClienteEJB;
import entities.Cliente;
@ManagedBean (name = "editarClienteBean")
@ViewScoped
public class EditarClienteBean {
	@EJB
	ClienteEJB clienteEjb;
	
	//Valores para Foto
	private String filename;
	private Long idCliente;
	private Cliente clienteEdicion;
	private byte[] data;
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
	
	
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idCliente = Long.parseLong(params.get("idCliente"));
				filename = "";
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
	private String getRandomImage(){
		int i = (int) (Math.random()*1000000);
		return String.valueOf(i);
	}
	public String getFilename() {
		return filename;
	}
	public void oncapture(CaptureEvent captureEvent){
		filename = getRandomImage();
		data = captureEvent.getData();
		System.out.println("cargado Byte de imagen");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String newFilename = externalContext.getRealPath(" ") + File.separator + "resources" + File.separator + "demo" 
				+ File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";
		
		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream ( new File(newFilename));
			imageOutput.write(data, 0 ,data.length);
			imageOutput.close();
			System.out.println("Filename Cargado..........");
		} catch (IOException e) {
			// TODO: handle exception
			throw new FacesException("Error al escribir imagen capturada");
		}
	}
}

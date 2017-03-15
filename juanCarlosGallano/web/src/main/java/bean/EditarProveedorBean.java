package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.ProveedorEJB;
import entities.Deposito;
import entities.Proveedor;

@ManagedBean (name = "editarProveedorBean")
@ViewScoped
public class EditarProveedorBean {
	
	@EJB
	ProveedorEJB proveedorEjb;
	
	private Long idProveedor;
	private Proveedor proveedorEdicion;
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public Proveedor getProveedorEdicion() {
		return proveedorEdicion;
	}
	public void setProveedorEdicion(Proveedor proveedorEdicion) {
		this.proveedorEdicion = proveedorEdicion;
	}
	
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idProveedor = Long.parseLong(params.get("idProveedor"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idProveedor != null){
				proveedorEdicion = proveedorEjb.findProveedorId(idProveedor);
			} else {
				proveedorEdicion = new Proveedor();
			}
		}
	}
	
	public void guardarProveedor(){
		// Debe persistir el usuario
		if(proveedorEdicion.getIdProveedor() == null){
			proveedorEdicion = proveedorEjb.create(proveedorEdicion);
			 FacesContext.getCurrentInstance().addMessage("Proveedor Creado", new FacesMessage("Nuevo Proveedor Creado."));
			 proveedorEdicion = new Proveedor();
		} else {
			// Modificar usuario
			proveedorEdicion = proveedorEjb.update(proveedorEdicion);
			FacesContext.getCurrentInstance().addMessage("Proveedor Modificado", new FacesMessage("Proveedor Modificado."));
		}
	}
	
	

}

package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.FormaPagoEJB;
import entities.FormaPago;

@ManagedBean (name = "editarFormaPagoBean")
@ViewScoped
public class EditarFormaPagoBean {
	private Long idFormaPago;
	private FormaPago formaPagoEdicion;
	
	@EJB
	FormaPagoEJB formaPagoEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idFormaPago = Long.parseLong(params.get("idFormaPago"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idFormaPago != null){
				formaPagoEdicion = formaPagoEJB.findIdFormaPago(idFormaPago);
			} else {
				formaPagoEdicion = new FormaPago();
			}
		}
	}

	public Long getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Long idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public FormaPago getFormaPagoEdicion() {
		return formaPagoEdicion;
	}

	public void setFormaPagoEdicion(FormaPago formaPagoEdicion) {
		this.formaPagoEdicion = formaPagoEdicion;
	}
	public void guardarFormaPago(){
		// Debe persistir el usuario
		if(formaPagoEdicion.getIdFormaPago() == null){
			formaPagoEdicion = formaPagoEJB.create(formaPagoEdicion);
			 FacesContext.getCurrentInstance().addMessage("FormaPago Creado", new FacesMessage("Nuevo FormaPago Creado."));
			 formaPagoEdicion = new FormaPago();
		} else {
			// Modificar usuario
			formaPagoEdicion = formaPagoEJB.update(formaPagoEdicion);
			FacesContext.getCurrentInstance().addMessage("FormaPago Modificado", new FacesMessage("FormaPago Modificado."));
		}
	}

}

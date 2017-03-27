package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.MonedaEJB;
import entities.Moneda;

@ManagedBean (name = "editarMonedaBean")
@ViewScoped
public class EditarMonedaBean {
	
	private Long idMoneda;
	private Moneda monedaEdicion;
	
	@EJB
	MonedaEJB monedaEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idMoneda = Long.parseLong(params.get("idMoneda"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idMoneda != null){
				monedaEdicion = monedaEJB.findMonedaId(idMoneda);
			} else {
				monedaEdicion = new Moneda();
			}
		}
	}

	public Long getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}

	public Moneda getMonedaEdicion() {
		return monedaEdicion;
	}

	public void setMonedaEdicion(Moneda monedaEdicion) {
		this.monedaEdicion = monedaEdicion;
	}
	public void guardarMoneda(){
		// Debe persistir el usuario
		if(monedaEdicion.getIdMoneda() == null){
			monedaEdicion = monedaEJB.create(monedaEdicion);
			 FacesContext.getCurrentInstance().addMessage("Moneda Creado", new FacesMessage("Nuevo Moneda Creado."));
			 monedaEdicion = new Moneda();
		} else {
			// Modificar usuario
			monedaEdicion = monedaEJB.update(monedaEdicion);
			FacesContext.getCurrentInstance().addMessage("Moneda Modificado", new FacesMessage("Moneda Modificado."));
		}
	}

}


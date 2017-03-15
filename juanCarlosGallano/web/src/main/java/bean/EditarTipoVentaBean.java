package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.TipoVentaEJB;
import entities.TipoVenta;
@ManagedBean (name = "editarTipoVentaBean")
@ViewScoped
public class EditarTipoVentaBean {
	private Long idTipoVenta;
	private TipoVenta tipoVentaEdicion;
	
	@EJB
	TipoVentaEJB tipoVentaEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idTipoVenta = Long.parseLong(params.get("idTipoVenta"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idTipoVenta != null){
				tipoVentaEdicion = tipoVentaEJB.findIdTipoVenta(idTipoVenta);
			} else {
				tipoVentaEdicion = new TipoVenta();
			}
		}
	}

	public Long getIdTipoVenta() {
		return idTipoVenta;
	}

	public void setIdTipoVenta(Long idTipoVenta) {
		this.idTipoVenta = idTipoVenta;
	}

	public TipoVenta getTipoVentaEdicion() {
		return tipoVentaEdicion;
	}

	public void setTipoVentaEdicion(TipoVenta tipoVentaEdicion) {
		this.tipoVentaEdicion = tipoVentaEdicion;
	}
	public void guardarTipoVenta(){
		// Debe persistir el usuario
		if(tipoVentaEdicion.getIdTipoVenta() == null){
			tipoVentaEdicion = tipoVentaEJB.create(tipoVentaEdicion);
			 FacesContext.getCurrentInstance().addMessage("TipoVenta Creado", new FacesMessage("Nuevo TipoVenta Creado."));
			 tipoVentaEdicion = new TipoVenta();
		} else {
			// Modificar usuario
			tipoVentaEdicion = tipoVentaEJB.update(tipoVentaEdicion);
			FacesContext.getCurrentInstance().addMessage("TipoVenta Modificado", new FacesMessage("TipoVenta Modificado."));
		}
	}

}

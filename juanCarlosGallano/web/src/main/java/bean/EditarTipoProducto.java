package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.TipoProductoEJB;
import entities.TipoProducto;

@ManagedBean (name = "editarTipoProductoBean")
@ViewScoped
public class EditarTipoProducto {
	private Long idTipoProducto;
	private TipoProducto tipoProductoEdicion;
	
	@EJB
	TipoProductoEJB tipoProductoEjb;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idTipoProducto = Long.parseLong(params.get("idTipoProducto"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idTipoProducto != null){
				tipoProductoEdicion = tipoProductoEjb.findIdTipoProducto(idTipoProducto);
			} else {
				tipoProductoEdicion = new TipoProducto();
			}
		}
	}

	public Long getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(Long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}

	public TipoProducto getTipoProductoEdicion() {
		return tipoProductoEdicion;
	}

	public void setTipoProductoEdicion(TipoProducto tipoProductoEdicion) {
		this.tipoProductoEdicion = tipoProductoEdicion;
	}
	public void guardarTipoProducto(){
		// Debe persistir el usuario
		if(tipoProductoEdicion.getIdTipoProducto() == null){
			tipoProductoEdicion = tipoProductoEjb.create(tipoProductoEdicion);
			 FacesContext.getCurrentInstance().addMessage("Tipo Producto Creado", new FacesMessage("Nuevo Tipo Producto Creado."));
			 tipoProductoEdicion = new TipoProducto();
		} else {
			// Modificar usuario
			tipoProductoEdicion = tipoProductoEjb.update(tipoProductoEdicion);
			FacesContext.getCurrentInstance().addMessage("Tipo Producto Modificado", new FacesMessage("Tipo Producto Modificado."));
		}
	}

}

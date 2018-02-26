package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.UnidadMedidaEJB;
import entities.UnidadMedida;

@ManagedBean (name = "editarUnidadMedidaBean")
@ViewScoped
public class EditarUnidadMedidaBean {
	
	private Long idUnidadMedida;
	private UnidadMedida unidadMedidaEdicion;
	
	@EJB
	UnidadMedidaEJB unidadMedidaEjb;
	
	public EditarUnidadMedidaBean(){
		
	}

	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idUnidadMedida = Long.parseLong(params.get("idUnidadMedida"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idUnidadMedida != null){
				unidadMedidaEdicion = unidadMedidaEjb.findIdUnidadMedida(idUnidadMedida);
			} else {
				unidadMedidaEdicion = new UnidadMedida();
			}
		}
		
		
	}
	
	public void guardarUnidadMedida(){
		// Debe persistir el usuario
		if(unidadMedidaEdicion.getId() == null){
			unidadMedidaEdicion = unidadMedidaEjb.create(unidadMedidaEdicion);
			 FacesContext.getCurrentInstance().addMessage("UnidadMedida Creado", new FacesMessage("Nuevo Unidad de Medida Creado."));
			 unidadMedidaEdicion = new UnidadMedida();
		} else {
			// Modificar usuario
			unidadMedidaEdicion = unidadMedidaEjb.update(unidadMedidaEdicion);
			FacesContext.getCurrentInstance().addMessage("UnidadMedida Modificado", new FacesMessage("Unidad de Medida Modificado."));
		}
	}

	public Long getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(Long idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public UnidadMedida getUnidadMedidaEdicion() {
		return unidadMedidaEdicion;
	}

	public void setUnidadMedidaEdicion(UnidadMedida unidadMedidaEdicion) {
		this.unidadMedidaEdicion = unidadMedidaEdicion;
	}
	
	
}

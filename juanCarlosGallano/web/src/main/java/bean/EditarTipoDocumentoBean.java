package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.TipoDocumentoEJB;
import entities.Grupo;
import entities.TipoDocumento;

@ManagedBean (name = "editarTipoDocumentoBean")
@ViewScoped
public class EditarTipoDocumentoBean {
	
	private Long idTipoDocumento;
	private TipoDocumento tipoDocumentoEdicion;
	
	@EJB
	TipoDocumentoEJB tipoDocumentoEjb;
	
	public EditarTipoDocumentoBean(){
		
	}

	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idTipoDocumento = Long.parseLong(params.get("idTipoDocumento"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idTipoDocumento != null){
				System.out.println("Tipo Documento Buscado: " + idTipoDocumento);
				tipoDocumentoEdicion = tipoDocumentoEjb.findTipoDocumentoId(idTipoDocumento);
			} else {
				tipoDocumentoEdicion = new TipoDocumento();
			}
		}
		
		
	}
	
	public void guardarTipoDocumento(){
		// Debe persistir el usuario
		if(tipoDocumentoEdicion.getId() == null){
			tipoDocumentoEdicion = tipoDocumentoEjb.create(tipoDocumentoEdicion);
			 FacesContext.getCurrentInstance().addMessage("TipoDocumento Creado", new FacesMessage("Nuevo TipoDocumento Creado."));
			 tipoDocumentoEdicion = new TipoDocumento();
		} else {
			// Modificar usuario
			tipoDocumentoEdicion = tipoDocumentoEjb.update(tipoDocumentoEdicion);
			FacesContext.getCurrentInstance().addMessage("TipoDocumento Modificado", new FacesMessage("TipoDocumento Modificado."));
		}
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public TipoDocumento getTipoDocumentoEdicion() {
		return tipoDocumentoEdicion;
	}

	public void setTipoDocumentoEdicion(TipoDocumento tipoDocumentoEdicion) {
		this.tipoDocumentoEdicion = tipoDocumentoEdicion;
	}
	
	
}
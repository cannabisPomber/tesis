package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.GrupoEJB;
import entities.Grupo;

@ManagedBean (name = "editarGrupoBean")
@ViewScoped
public class EditarGrupoBean {
	private Long idGrupo;
	private Grupo grupoEdicion;
	@EJB
	GrupoEJB grupoEjb;
	
	public EditarGrupoBean (){
		
	}
	// Proceso inicial del Bean
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idGrupo = Long.parseLong(params.get("idGrupo"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idGrupo != null){
				grupoEdicion = grupoEjb.findGrupo(idGrupo);
			} else {
				grupoEdicion = new Grupo();
			}
		}
		
		
	}
	public Grupo getGrupoEdicion() {
		return grupoEdicion;
	}
	public void setGrupoEdicion(Grupo grupoEdicion) {
		this.grupoEdicion = grupoEdicion;
	}
	
	public void guardarGrupo(){
		// Debe persistir el usuario
		if(grupoEdicion.getIdGrupo() == null){
			grupoEdicion = grupoEjb.create(grupoEdicion);
			 FacesContext.getCurrentInstance().addMessage("Grupo Creado", new FacesMessage("Nuevo Grupo Creado."));
			 grupoEdicion = new Grupo();
		} else {
			// Modificar usuario
			grupoEdicion = grupoEjb.update(grupoEdicion);
			FacesContext.getCurrentInstance().addMessage("Grupo Modificado", new FacesMessage("Grupo Modificado."));
		}
	}
	
}

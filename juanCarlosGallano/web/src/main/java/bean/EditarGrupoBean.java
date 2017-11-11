package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import ejb.GrupoEJB;
import ejb.RolEJB;
import entities.Grupo;
import entities.Rol;

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
	public void init() throws IOException{
		//verificar que pertenezca al grupo Admin
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		String grupoUsuario = (String)session.getAttribute("grupo");
		if (!grupoUsuario.equals("admin")){
				//Si no pertenece al grupo envia al menu principal
				FacesContext.getCurrentInstance().getExternalContext().responseSendError(401, "You are not authorized.");
		}
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
	
	public Long getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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

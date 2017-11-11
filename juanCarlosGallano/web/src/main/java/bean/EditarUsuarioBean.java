package bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ejb.GrupoEJB;
import ejb.UsuarioEJB;
import entities.Grupo;
import entities.Usuario;


@ManagedBean (name ="editarUsuarioBean")
@ViewScoped
public class EditarUsuarioBean {
	
	private Long idUsuario;
	@EJB
	UsuarioEJB usuarioEjb;
	
	@EJB
	GrupoEJB grupoEjb;
	private Usuario usuarioEdit;
	
	private Grupo grupoEdit;
	// eleccion de grupo para usuario;
	private List<Grupo> grupos;
	
	private Long idGrupo;
	
	public EditarUsuarioBean(){
		
	}
	//Metodo que inicializa la edicion de usuario
	public void init() throws IOException{
		//verificar que pertenezca al grupo Admin
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		String grupoUsuario = (String)session.getAttribute("grupo");
		if (!grupoUsuario.equals("admin")){
				//Si no pertenece al grupo envia al menu principal
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
		if (!FacesContext.getCurrentInstance().isPostback()){
		grupoEdit = new Grupo();
		grupos = grupoEjb.findAll();
		Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try{
			idUsuario = Long.parseLong(params.get("idUsuario"));
		} catch (Exception ex){
			System.out.println("Se tiene un error");
		}
		if (idUsuario != null){
			usuarioEdit = usuarioEjb.findIdUsuario(idUsuario);
			grupoEdit = usuarioEjb.usuarioFindGrupo(usuarioEdit);
			idGrupo = grupoEdit.getIdGrupo();
			//grupoEdit = new Grupo();
		} else {
			usuarioEdit = new Usuario();
		}
		}	
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Usuario getUsuarioEdit() {
		return usuarioEdit;
	}
	public void setUsuarioEdit(Usuario usuarioEdit) {
		this.usuarioEdit = usuarioEdit;
	}
	
	public void guardarUsuario(){
		// Debe persistir el usuario
		if(usuarioEdit.getIdUsuario() == null){
//			usuarioEdit.setGrupo(grupoEdit);
			Grupo grupo = grupoEjb.findGrupo(idGrupo);
			usuarioEdit.setGrupo(grupo);
			usuarioEdit = usuarioEjb.create(usuarioEdit);
			 FacesContext.getCurrentInstance().addMessage("Usuario Creado", new FacesMessage("Nuevo Usuario Creado."));
			 usuarioEdit = new Usuario();
		} else {
			// Modificar usuario
//			usuarioEdit.setGrupo(grupoEdit);
			Grupo grupo = grupoEjb.findGrupo(idGrupo);
			usuarioEdit.setGrupo(grupo);			
			usuarioEdit = usuarioEjb.update(usuarioEdit);
			FacesContext.getCurrentInstance().addMessage("Usuario Modificado", new FacesMessage("Usuario Modificado."));
		}
	}
	public List<Grupo> getGrupos() {
		return grupoEjb.findAll();
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	public Grupo getGrupoEdit() {
		return grupoEdit;
	}
	public void setGrupoEdit(Grupo grupoEdit) {
		this.grupoEdit = grupoEdit;
	}
	public Long getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	
	
	
}

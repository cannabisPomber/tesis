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

import ejb.EmpresaEJB;
import ejb.GrupoEJB;
import ejb.UsuarioEJB;
import entities.Empresa;
import entities.Grupo;
import entities.Usuario;


@ManagedBean (name ="editarUsuarioBean")
@ViewScoped
public class EditarUsuarioBean {
	
	private Long idUsuario;
	@EJB
	UsuarioEJB usuarioEjb;
	
	@EJB
	EmpresaEJB empresaEjb;
	
	@EJB
	GrupoEJB grupoEjb;
	private Usuario usuarioEdit;
	
	private Grupo grupoEdit;
	// eleccion de grupo para usuario;
	private List<Grupo> grupos;
	
	
	
	private Long idGrupo;
	
	//Sucursal
	private Long idEmpresa;
	private Empresa empresaEdit;
	// eleccion de sucursal para usuario;
	private List<Empresa> empresas;
	
	public EditarUsuarioBean(){
		
	}
	//Metodo que inicializa la edicion de usuario
	public void init() throws IOException{
		
		if (!FacesContext.getCurrentInstance().isPostback()){
		grupoEdit = new Grupo();
		grupos = grupoEjb.findAll();
		empresas = empresaEjb.findAll();
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
			idEmpresa = usuarioEdit.getEmpresa().getIdEmpresa();
			//grupoEdit = new Grupo();
		} else {
			usuarioEdit = new Usuario();
			idGrupo = null;
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
			empresaEdit = empresaEjb.findEmpresaId(idEmpresa);
			usuarioEdit.setGrupo(grupo);
			usuarioEdit.setEmpresa(empresaEdit);
			usuarioEdit = usuarioEjb.create(usuarioEdit);
			idGrupo = null;
			idEmpresa = null;
			usuarioEdit = new Usuario();
			 FacesContext.getCurrentInstance().addMessage("Usuario Creado", new FacesMessage("Nuevo Usuario Creado."));
			 usuarioEdit = new Usuario();
		} else {
			// Modificar usuario
//			usuarioEdit.setGrupo(grupoEdit);
			Grupo grupo = grupoEjb.findGrupo(idGrupo);
			empresaEdit = empresaEjb.findEmpresaId(idEmpresa);
			usuarioEdit.setGrupo(grupo);	
			usuarioEdit.setEmpresa(empresaEdit);
			usuarioEdit = usuarioEjb.update(usuarioEdit);
			idGrupo = null;
			idEmpresa = null;
			usuarioEdit = new Usuario();
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
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	public Empresa getEmpresaEdit() {
		return empresaEdit;
	}
	public void setEmpresaEdit(Empresa empresaEdit) {
		this.empresaEdit = empresaEdit;
	}
	
	
	
	
}

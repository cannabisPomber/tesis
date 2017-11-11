package bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import ejb.GrupoEJB;
import ejb.UsuarioEJB;
import entities.Grupo;
import entities.Usuario;

@ManagedBean(name ="usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable{
	@EJB
	UsuarioEJB usuarioEjb;
	
	@EJB
	GrupoEJB grupoEjb;
	Usuario user = new Usuario();
	//objeto usuario modificacion
	Usuario userVista;
	Grupo grupo;
	List<Usuario> listUsuarios;
	HttpSession session;
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	// Init para listar los usuarios en pagina
	public void initUsuario() throws IOException {
		if (!FacesContext.getCurrentInstance().isPostback()){
			
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				this.user = new Usuario();
				this.userVista = new Usuario();
				user.setUsuario("");
				user.setPassword("");
				this.listUsuarios = new ArrayList<Usuario>();
				this.listUsuarios = allUsuarios();
		}
	}
	public void init(){
		//if (!FacesContext.getCurrentInstance().isPostback()){
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			this.user = new Usuario();
			user.setUsuario("");
			user.setPassword("");
			RequestContext.getCurrentInstance().update("loginForm:dlgLogin");
			RequestContext.getCurrentInstance().execute("PF('dlgLogin').show();");
		//}
	}
	public String verificarUsuario(){
		//Usuario usuarioValidado = new Usuario();
		user.setUsuario(user.getUsuario().trim());
		user.setPassword(user.getPassword().trim());
		if ((user = usuarioEjb.findUsuario(user)) != null){
	        session.setAttribute("usuario", user.getUsuario());
	        session.setAttribute("idUsuario", Long.toString(user.getIdUsuario()) );
	        //Cargando Valor de Grupo en sesion
	        session.setAttribute("grupo", user.getGrupo().getNombreGrupo());
			 
			/*FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put("usuario", user.getUsuario());*/
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage("Usuario invalido", new FacesMessage("Usuario o contrasena invalida"));
			return "login";
		}
		
	}
	
	// Salir de usuario
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.user = new Usuario();
		return "/login.xhtml?faces-redirect=true";
	}
	
	
	public List<Usuario> allUsuarios (){
		System.out.println("tratando de recuperar todos los usuarios.");
		this.listUsuarios = usuarioEjb.findAll();
		return this.listUsuarios;
	}

	public List<Usuario> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List<Usuario> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}
	
	public String usuarioLogueado() throws IOException{
		/*System.out.println("logueado :"+(String) ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getAttribute("usuario"));
		return (String) ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getAttribute("usuario");*/
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();      
        //System.out.println("usuario logueado :" + session.getAttribute("usuario"));
		if (request.isUserInRole("Administrador")) {
		     System.out.println("Ingreso como Administrador situado en login.html");
		  } else{
			  System.out.println("Ingreso como Otro situado en login.html");
		  }
		try {
			userVista = usuarioEjb.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
		}
		catch (Exception ex){
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		}
		/*grupo = userVista.getGrupo();
		grupo = grupoEjb.findGrupo(grupo.getIdGrupo());
		System.out.println("grupo " +  grupo.getIdGrupo());*/ 
		return (String)session.getAttribute("usuario");
		/*Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		System.out.println("usuario :" + params.get("usuario"));
		return params.get("usuario");*/
	}
	
	public String getGrupoUsuario() throws IOException{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();
		return (String)session.getAttribute("grupo");
	}
	public Usuario getUserVista() {
		return userVista;
	}

	public void setUserVista(Usuario userVista) {
		this.userVista = userVista;
	}
	
	public void eliminarUsuario(Usuario user){
		usuarioEjb.delete(user);
		this.listUsuarios = usuarioEjb.findAll();
		
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	
}

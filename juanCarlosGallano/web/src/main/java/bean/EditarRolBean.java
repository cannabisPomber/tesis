package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.RolEJB;
import entities.Rol;

@ManagedBean (name = "editarRolBean")
@ViewScoped
public class EditarRolBean {
	private Long idRol;
	private Rol rolEdicion;
	@EJB
	RolEJB rolEjb;
	
	public EditarRolBean (){
		
	}
	// Proceso inicial del Bean
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idRol = Long.parseLong(params.get("idRol"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idRol != null){
				rolEdicion = rolEjb.findRol(idRol);
			} else {
				rolEdicion = new Rol();
			}
		}
		
		
	}
	public Rol getRolEdicion() {
		return rolEdicion;
	}
	public void setRolEdicion(Rol rolEdicion) {
		this.rolEdicion = rolEdicion;
	}
	
	public void guardarRol(){
		// Debe persistir el usuario
		if(rolEdicion.getIdRol() == null){
			rolEdicion = rolEjb.create(rolEdicion);
			 FacesContext.getCurrentInstance().addMessage("Rol Creado", new FacesMessage("Nuevo Rol Creado."));
			 rolEdicion = new Rol();
		} else {
			// Modificar usuario
			rolEdicion = rolEjb.update(rolEdicion);
			FacesContext.getCurrentInstance().addMessage("Rol Modificado", new FacesMessage("Rol Modificado."));
		}
	}
	
}


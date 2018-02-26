package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.RolEJB;
import entities.Rol;

@ManagedBean (name = "rolBean")
@ViewScoped
public class RolBean {
	@EJB
	RolEJB rolEJB;
	
	private Rol rolEdicion;
	
	private List<Rol> rolList = new ArrayList<Rol>();

	public List<Rol> getRolList() {
		return rolEJB.findAll();
	}

	public void setRolList(List<Rol> rolList) {
		this.rolList = rolList;
	}

	public Rol getRolEdicion() {
		return rolEdicion;
	}

	public void setRolEdicion(Rol rolEdicion) {
		this.rolEdicion = rolEdicion;
	}
	
	public void eliminarRol (Rol rol){
		rolEJB.delete(rol);
	}
	
	
}

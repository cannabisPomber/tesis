package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.GrupoEJB;
import entities.Grupo;

@ManagedBean (name = "grupoBean")
@ViewScoped
public class GrupoBean {
	@EJB
	GrupoEJB grupoEJB;
	
	private Grupo grupoEdicion;
	
	private List<Grupo> grupoList = new ArrayList<Grupo>();

	public List<Grupo> getGrupoList() {
		return grupoEJB.findAll();
	}

	public void setGrupoList(List<Grupo> grupoList) {
		this.grupoList = grupoList;
	}

	public Grupo getGrupoEdicion() {
		return grupoEdicion;
	}

	public void setGrupoEdicion(Grupo grupoEdicion) {
		this.grupoEdicion = grupoEdicion;
	}
	
	public void eliminarGrupo (Grupo grupo){
		grupoEJB.delete(grupo);
	}
	
	
}

package bean;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.PaisEJB;
import entities.Pais;


@ManagedBean (name = "paisBean")
@ViewScoped
public class PaisBean {
	
	@EJB
	PaisEJB paisEJB;
	
	private Pais paisEdicion;
	
	private List<Pais> paisList = new ArrayList<Pais>();

	public Pais getPaisEdicion() {
		return paisEdicion;
	}

	public void setPaisEdicion(Pais paisEdicion) {
		this.paisEdicion = paisEdicion;
	}

	public List<Pais> getPaisList() {
		return paisEJB.findAll();
	}

	public void setPaisList(List<Pais> grupoList) {
		this.paisList = grupoList;
	}
	
	public void eliminarPais (Pais dep){
		paisEJB.delete(dep);
	}
	
}
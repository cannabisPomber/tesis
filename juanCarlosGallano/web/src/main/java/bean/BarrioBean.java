package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.BarrioEJB;
import entities.Barrio;


@ManagedBean (name = "barrioBean")
@ViewScoped
public class BarrioBean {
	
	@EJB
	BarrioEJB barrioEJB;
	
	private Barrio barrioEdicion;
	
	private List<Barrio> barrioList = new ArrayList<Barrio>();

	public Barrio getBarrioEdicion() {
		return barrioEdicion;
	}

	public void setBarrioEdicion(Barrio barrioEdicion) {
		this.barrioEdicion = barrioEdicion;
	}

	public List<Barrio> getBarrioList() {
		return barrioEJB.findAll();
	}

	public void setBarrioList(List<Barrio> grupoList) {
		this.barrioList = grupoList;
	}
	
	public void eliminarBarrio (Barrio dep){
		barrioEJB.delete(dep);
	}
	
}
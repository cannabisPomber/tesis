package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.MonedaEJB;
import entities.Moneda;

@ManagedBean (name = "monedaBean")
@ViewScoped
public class MonedaBean {
	
	@EJB
	MonedaEJB monedaEJB;
	
	private Moneda monedaEdicion;
	
	private List<Moneda> monedaList = new ArrayList<Moneda>();

	public Moneda getMonedaEdicion() {
		return monedaEdicion;
	}

	public void setMonedaEdicion(Moneda monedaEdicion) {
		this.monedaEdicion = monedaEdicion;
	}

	public List<Moneda> getMonedaList() {
		return monedaEJB.findAll();
	}

	public void setMonedaList(List<Moneda> grupoList) {
		this.monedaList = grupoList;
	}
	
	public void eliminarMoneda (Moneda dep){
		monedaEJB.delete(dep);
	}
	
}

package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.CiudadEJB;
import entities.Ciudad;


@ManagedBean (name = "ciudadBean")
@ViewScoped
public class CiudadBean {
	
	@EJB
	CiudadEJB ciudadEJB;
	
	private Ciudad ciudadEdicion;
	
	private List<Ciudad> ciudadList = new ArrayList<Ciudad>();

	public Ciudad getCiudadEdicion() {
		return ciudadEdicion;
	}

	public void setCiudadEdicion(Ciudad ciudadEdicion) {
		this.ciudadEdicion = ciudadEdicion;
	}

	public List<Ciudad> getCiudadList() {
		return ciudadEJB.findAll();
	}

	public void setCiudadList(List<Ciudad> grupoList) {
		this.ciudadList = grupoList;
	}
	
	public void eliminarCiudad (Ciudad dep){
		ciudadEJB.delete(dep);
	}
	
}
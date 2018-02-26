package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.UnidadMedidaEJB;
import entities.UnidadMedida;


@ManagedBean (name = "unidadMedidaBean")
@ViewScoped
public class UnidadMedidaBean {
	
	@EJB
	UnidadMedidaEJB unidadMedidaEJB;
	
	private UnidadMedida unidadMedidaEdicion;
	
	private List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();

	public UnidadMedida getUnidadMedidaEdicion() {
		return unidadMedidaEdicion;
	}

	public void setUnidadMedidaEdicion(UnidadMedida unidadMedidaEdicion) {
		this.unidadMedidaEdicion = unidadMedidaEdicion;
	}

	public List<UnidadMedida> getUnidadMedidaList() {
		unidadMedidaList = unidadMedidaEJB.findAll();
		return unidadMedidaList;
	}

	public void setUnidadMedidaList(List<UnidadMedida> grupoList) {
		this.unidadMedidaList = grupoList;
	}
	
	public void eliminarUnidadMedida (UnidadMedida dep){
		unidadMedidaEJB.delete(dep);
	}
	
}

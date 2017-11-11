package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.TipoVentaEJB;
import entities.TipoVenta;

@ManagedBean (name = "tipoVentaBean")
@ViewScoped
public class TipoVentaBean {
	
	@EJB
	TipoVentaEJB tipoVentaEJB;
	
	private TipoVenta tipoVentaEdicion;
	
	private List<TipoVenta> tipoVentaList = new ArrayList<TipoVenta>();

	public TipoVenta getTipoVentaEdicion() {
		return tipoVentaEdicion;
	}

	public void setTipoVentaEdicion(TipoVenta tipoVentaEdicion) {
		this.tipoVentaEdicion = tipoVentaEdicion;
	}

	public List<TipoVenta> getTipoVentaList() {
		tipoVentaList = tipoVentaEJB.findAll();
		return tipoVentaList;
	}

	public void setTipoVentaList(List<TipoVenta> grupoList) {
		this.tipoVentaList = grupoList;
	}
	
	public void eliminarTipoVenta (TipoVenta dep){
		tipoVentaEJB.delete(dep);
	}
}

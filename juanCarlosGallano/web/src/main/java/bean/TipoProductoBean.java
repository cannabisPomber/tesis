package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.TipoProductoEJB;
import entities.TipoProducto;

@ManagedBean (name = "tipoProductoBean")
@ViewScoped
public class TipoProductoBean {
	
	@EJB
	TipoProductoEJB tipoProductoEJB;
	
	private TipoProducto tipoProductoEdicion;
	
	private List<TipoProducto> tipoProductoList = new ArrayList<TipoProducto>();

	public TipoProducto getTipoProductoEdicion() {
		return tipoProductoEdicion;
	}

	public void setTipoProductoEdicion(TipoProducto tipoProductoEdicion) {
		this.tipoProductoEdicion = tipoProductoEdicion;
	}

	public List<TipoProducto> getTipoProductoList() {
		return tipoProductoEJB.findAll();
	}

	public void setTipoProductoList(List<TipoProducto> grupoList) {
		this.tipoProductoList = grupoList;
	}
	
	public void eliminarTipoProducto (TipoProducto dep){
		tipoProductoEJB.delete(dep);
	}
}

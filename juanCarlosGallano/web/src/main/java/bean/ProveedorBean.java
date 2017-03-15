package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.ProveedorEJB;
import entities.Proveedor;

@ManagedBean(name="proveedorBean")
@ViewScoped
public class ProveedorBean {
	
	@EJB
	ProveedorEJB proveedorEjb;
	
	private Proveedor proveedorEdicion;
	private List<Proveedor> proveedorList = new ArrayList<Proveedor>();
	public Proveedor getProveedorEdicion() {
		return proveedorEdicion;
	}
	public void setProveedorEdicion(Proveedor proveedorEdicion) {
		this.proveedorEdicion = proveedorEdicion;
	}
	public List<Proveedor> getProveedorList() {
		return proveedorEjb.findAll();
	}
	public void setProveedorList(List<Proveedor> proveedorList) {
		this.proveedorList = proveedorList;
	}
}

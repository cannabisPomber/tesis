package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.EmpresaEJB;
import entities.Empresa;
@ManagedBean (name = "sucursalBean")
@ViewScoped
public class SucursalBean {
	
	@EJB
	EmpresaEJB sucursalEJB;
	
	private List<Empresa> sucursalList = new ArrayList<Empresa>();
	private Empresa sucursalEdicion;

	public List<Empresa> getSucursalList() {
		return sucursalEJB.findAll();
	}

	public void setSucursalList(List<Empresa> sucursalList) {
		this.sucursalList = sucursalList;
	}

	public Empresa getSucursalEdicion() {
		return sucursalEdicion;
	}

	public void setSucursalEdicion(Empresa sucursalEdicion) {
		this.sucursalEdicion = sucursalEdicion;
	}
	
	public void eliminarSucursal (Empresa mar){
		sucursalEJB.delete(mar);
	}
}

package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.PuestoVentaEJB;
import entities.PuestoVenta;

@ManagedBean (name = "puestoVentaBean")
@ViewScoped
public class PuestoVentaBean {
	
	@EJB
	PuestoVentaEJB puestoVentaEJB;
	
	private PuestoVenta puestoVentaEdicion;
	
	private List<PuestoVenta> puestoVentaList = new ArrayList<PuestoVenta>();

	public List<PuestoVenta> getPuestoVentaList() {
		return puestoVentaEJB.findAll();
	}

	public void setPuestoVentaList(List<PuestoVenta> puestoVentaList) {
		this.puestoVentaList = puestoVentaList;
	}

	public PuestoVenta getPuestoVentaEdicion() {
		return puestoVentaEdicion;
	}

	public void setPuestoVentaEdicion(PuestoVenta puestoVentaEdicion) {
		this.puestoVentaEdicion = puestoVentaEdicion;
	}
	
	public void eliminarPuestoVenta (PuestoVenta puestoVenta){
		puestoVentaEJB.delete(puestoVenta);
	}
	

}

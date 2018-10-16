package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.OrdenCompraEJB;
import ejb.PresupuestoEJB;
import entities.OrdenCompra;
import entities.Presupuesto;

@ManagedBean (name = "presupuestoBean")
@ViewScoped
public class PresupuestoBean {
	
	@EJB
	OrdenCompraEJB ordenCompraEJB;
	
	private List<OrdenCompra> presupuestoList = new ArrayList<OrdenCompra>();
	private OrdenCompra ordenCompraEdicion;

	public List<OrdenCompra> getPresupuestoList() {
		return ordenCompraEJB.findAll();
	}

	public void setPresupuestoList(List<OrdenCompra> presupuestoList) {
		this.presupuestoList = presupuestoList;
	}

	public OrdenCompra getPresupuestoEdicion() {
		return ordenCompraEdicion;
	}

	public void setPresupuestoEdicion(OrdenCompra presupuestoEdicion) {
		this.ordenCompraEdicion = presupuestoEdicion;
	}
	
	public void eliminarPresupuesto (OrdenCompra mar){
		ordenCompraEJB.delete(mar);
	}
}


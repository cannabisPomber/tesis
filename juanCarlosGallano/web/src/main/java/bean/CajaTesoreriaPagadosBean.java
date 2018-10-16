package bean;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.CajaEJB;
import ejb.DetalleOrdenCompraEJB;
import ejb.OrdenCompraEJB;
import entities.Caja;
import entities.DetalleOrdenCompra;
import entities.OrdenCompra;


@ManagedBean (name = "cajaTesoreriaPagadosBean")
@ViewScoped
public class CajaTesoreriaPagadosBean {
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	OrdenCompraEJB	ordenCompraEJB;
	
	@EJB
	DetalleOrdenCompraEJB	detOrdenCompraEJB;
	
	private List<OrdenCompra> listOrdenCompraPagados;
	
	private List<DetalleOrdenCompra> listDetalleOrdenCompraPagados;

	public List<OrdenCompra> getListOrdenCompraPagados() {
		
		listOrdenCompraPagados = ordenCompraEJB.findAllCerrado();
		System.out.println("Cargando Orden de CompraCerrados:" + listOrdenCompraPagados.size());
		return listOrdenCompraPagados;
	}

	public void setListOrdenCompraPagados(List<OrdenCompra> listOrdenCompraPagados) {
		this.listOrdenCompraPagados = listOrdenCompraPagados;
	}

	public List<DetalleOrdenCompra> getListDetalleOrdenCompraPagados() {
		return listDetalleOrdenCompraPagados;
	}

	public void setListDetalleOrdenCompraPagados(List<DetalleOrdenCompra> listDetalleOrdenCompraPagados) {
		this.listDetalleOrdenCompraPagados = listDetalleOrdenCompraPagados;
	}

	
	
}

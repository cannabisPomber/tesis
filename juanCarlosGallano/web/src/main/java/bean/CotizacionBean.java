package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.CotizacionCabeceraEJB;
import ejb.PedidoEJB;
import entities.Pedido;

@ManagedBean (name = "cotizacionBean")
@ViewScoped
public class CotizacionBean {
	
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	CotizacionCabeceraEJB cotizacionCabeceraEJB;
	
	private Pedido pedidoEdicion;
	
	private List<Pedido> pedidoList = new ArrayList<Pedido>();

	public Pedido getPedidoEdicion() {
		return pedidoEdicion;
	}

	public void setPedidoEdicion(Pedido pedidoEdicion) {
		this.pedidoEdicion = pedidoEdicion;
	}

	public List<Pedido> getPedidoList() {
		pedidoList = pedidoEJB.findAllActivo();
		return pedidoList;
	}

	public void setPedidoList(List<Pedido> pedidoList) {
		this.pedidoList = pedidoList;
	}

	
	

}

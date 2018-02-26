package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.PedidoEJB;
import entities.Pedido;


@ManagedBean (name = "pedidoBean")
@ViewScoped
public class PedidoBean {
	
	@EJB
	PedidoEJB pedidoEJB;
	
	private Pedido pedidoEdicion;
	
	private List<Pedido> pedidoList = new ArrayList<Pedido>();

	public Pedido getPedidoEdicion() {
		return pedidoEdicion;
	}

	public void setPedidoEdicion(Pedido pedidoEdicion) {
		this.pedidoEdicion = pedidoEdicion;
	}

	public List<Pedido> getPedidoList() {
		pedidoList=pedidoEJB.findAll();
		return pedidoList;
	}

	public void setPedidoList(List<Pedido> grupoList) {
		this.pedidoList = grupoList;
	}
	
	public void eliminarPedido (Pedido dep){
		pedidoEJB.delete(dep);
	}
	
}

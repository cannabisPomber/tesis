package bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.PedidoEJB;
import entities.Deposito;
import entities.Pedido;

@ManagedBean (name = "anularPedidoBean")
@ViewScoped
public class AnularPedido {
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private List<Pedido> listPedidos;
	
	
	@EJB
	PedidoEJB pedidoEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			
		}
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void buscarPedido(){
		//Seteando hora
		fechaFin.setHours(23);
		fechaFin.setMinutes(59);
		fechaFin.setSeconds(59);
		//Seteando Hora
		fechaFin.setHours(00);
		fechaFin.setMinutes(01);
		fechaFin.setSeconds(01);
		listPedidos = pedidoEJB.anularPedido(fechaInicio, fechaFin);
		System.out.println("Pedidos a Anular " + listPedidos.size());
	}
}

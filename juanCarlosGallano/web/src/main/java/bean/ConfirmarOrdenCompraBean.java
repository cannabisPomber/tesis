package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.DetalleOrdenCompraEJB;
import ejb.OrdenCompraEJB;
import ejb.UsuarioEJB;
import entities.DetalleOrdenCompra;
import entities.OrdenCompra;

@ManagedBean (name = "confirmarOrdenCompraBean")
@ViewScoped
public class ConfirmarOrdenCompraBean {
	
	@EJB
	OrdenCompraEJB ordenCompraEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	DetalleOrdenCompraEJB detOrdenCompraEJB;
	
	private List<OrdenCompra> listOrdenCompraActivos;
	
	private List<OrdenCompra> listOrdenCompraConfirmados;
	
	private List<DetalleOrdenCompra> listDetOrdenCompra;
	
	private OrdenCompra ordenCompraSelected;
	
	private Long idOrdenCompraSelected;
	private Long costoDetalle;
	
	public void init(){
		listOrdenCompraActivos = new ArrayList<OrdenCompra>();
		listOrdenCompraConfirmados = new ArrayList<OrdenCompra>();
		listOrdenCompraActivos = ordenCompraEJB.findAllActivo();
	}

	public List<OrdenCompra> getListOrdenCompraActivos() {
		return listOrdenCompraActivos;
	}

	public void setListOrdenCompraActivos(List<OrdenCompra> listOrdenCompraActivos) {
		this.listOrdenCompraActivos = listOrdenCompraActivos;
	}

	public List<OrdenCompra> getListOrdenCompraConfirmados() {
		return listOrdenCompraConfirmados;
	}

	public void setListOrdenCompraConfirmados(List<OrdenCompra> listOrdenCompraConfirmados) {
		this.listOrdenCompraConfirmados = listOrdenCompraConfirmados;
	}
	
	public OrdenCompra getOrdenCompraSelected() {
		return ordenCompraSelected;
	}

	public void setOrdenCompraSelected(OrdenCompra ordenCompraSelected) {
		this.ordenCompraSelected = ordenCompraSelected;
	}
	

	public List<DetalleOrdenCompra> getListDetOrdenCompra() {
		return listDetOrdenCompra;
	}

	public void setListDetOrdenCompra(List<DetalleOrdenCompra> listDetOrdenCompra) {
		this.listDetOrdenCompra = listDetOrdenCompra;
	}

	public Long getIdOrdenCompraSelected() {
		return idOrdenCompraSelected;
	}

	public void setIdOrdenCompraSelected(Long idOrdenCompraSelected) {
		this.idOrdenCompraSelected = idOrdenCompraSelected;
	}

	public Long getCostoDetalle() {
		return costoDetalle;
	}

	public void setCostoDetalle(Long costoDetalle) {
		this.costoDetalle = costoDetalle;
	}

	public void confirmarOrdenCompra(String user){
		if(listOrdenCompraConfirmados.size() == 0){
			FacesContext.getCurrentInstance().addMessage("Confirmado Correctamente Orden de Compra Seleccionados", new FacesMessage("No Se ha seleccionado Orden de Compra"));
		} else {
			for (Iterator iterator = listOrdenCompraConfirmados.iterator(); iterator.hasNext();) {
				OrdenCompra ordenCompra = (OrdenCompra) iterator.next();
				//actualizando las ordenes de compra a estado Confirmado
				ordenCompra.setEstado("CONFIRMADO");
				ordenCompra.setFechaAprobacion(new Date());
				ordenCompra.setUsuarioAprobacion(usuarioEJB.findUserUsuario(user));
				ordenCompraEJB.update(ordenCompra);
				limpiar();
				
			}
			FacesContext.getCurrentInstance().addMessage("Confirmado Correctamente Orden de Compra Seleccionados", new FacesMessage("Orden de Compra Creado.Modificado"));
		}
	}
	public void buscarDetalleOrdenCompra(){
		//CArgando Detalle de Orden deCompra en lista
		listDetOrdenCompra = detOrdenCompraEJB.DetalleOrdenCompra(ordenCompraEJB.findOrdenCompraId(idOrdenCompraSelected).getIdOrdenCompra());
	}
	public Long costoProducto(Long precioCompra, Long cantidad){
		costoDetalle = precioCompra*cantidad;
		return costoDetalle;
	}
	public void limpiar(){
		listOrdenCompraActivos = new ArrayList<OrdenCompra>();
	}
	
	public void anularOrdenCompra(){
		//Anular Orden de Compra en Confirmar Orden de compra
		if(listOrdenCompraConfirmados.size() == 0){
			FacesContext.getCurrentInstance().addMessage("Confirmado Correctamente Orden de Compra Seleccionados", new FacesMessage("No Se ha seleccionado Orden de Compra"));
		} else {
			for (Iterator iterator = listOrdenCompraConfirmados.iterator(); iterator.hasNext();) {
				OrdenCompra ordenCompraAnular = (OrdenCompra) iterator.next();
				ordenCompraAnular.setEstado("ANULADO");
				ordenCompraAnular = ordenCompraEJB.update(ordenCompraAnular);
			}
			FacesContext.getCurrentInstance().addMessage("Anulado Orden de compra Seleccionados", new FacesMessage("Se han Anulado Ordenes de compra seleccionados"));
		}
			
		
	}
}
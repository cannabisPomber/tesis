package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.OrdenCompraEJB;
import entities.OrdenCompra;

@ManagedBean (name = "editarOrdenCompraBean")
@ViewScoped
public class EditarOrdenCompraBean {

	private Long idOrdenCompra;
	private OrdenCompra ordenCompraEdicion;
	//Cargando Cantidad de Pedido Orden de Compra
	private Long cantidadOrdenCompra;
	
	@EJB
	OrdenCompraEJB ordenCompraEjb;
	
	public EditarOrdenCompraBean(){
		
	}

	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idOrdenCompra = Long.parseLong(params.get("idOrdenCompra"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idOrdenCompra != null){
				ordenCompraEdicion = ordenCompraEjb.findOrdenCompraId(idOrdenCompra);
			} else {
				ordenCompraEdicion = new OrdenCompra();
			}
		}
		
		
	}
	
	public void guardarOrdenCompra(){
		// Debe persistir el usuario
		if(ordenCompraEdicion.getIdOrdenCompra() == null){
			ordenCompraEdicion = ordenCompraEjb.create(ordenCompraEdicion);
			 FacesContext.getCurrentInstance().addMessage("OrdenCompra Creado", new FacesMessage("Nuevo OrdenCompra Creado."));
			 ordenCompraEdicion = new OrdenCompra();
		} else {
			// Modificar usuario
			ordenCompraEdicion = ordenCompraEjb.update(ordenCompraEdicion);
			FacesContext.getCurrentInstance().addMessage("OrdenCompra Modificado", new FacesMessage("OrdenCompra Modificado."));
		}
	}

	public Long getIdOrdenCompra() {
		return idOrdenCompra;
	}

	public void setIdOrdenCompra(Long idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public OrdenCompra getOrdenCompraEdicion() {
		return ordenCompraEdicion;
	}

	public void setOrdenCompraEdicion(OrdenCompra ordenCompraEdicion) {
		this.ordenCompraEdicion = ordenCompraEdicion;
	}
	public String costoProducto(Long precioCompra){
		Long costo = precioCompra*cantidadOrdenCompra;
		return costo.toString();
	}

	public Long getCantidadOrdenCompra() {
		return cantidadOrdenCompra;
	}

	public void setCantidadOrdenCompra(Long cantidadOrdenCompra) {
		this.cantidadOrdenCompra = cantidadOrdenCompra;
	}
	
}

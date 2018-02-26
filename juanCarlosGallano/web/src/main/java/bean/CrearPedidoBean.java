package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import ejb.DetallePedidoEJB;
import ejb.PedidoEJB;
import ejb.ProductoEJB;
import ejb.UsuarioEJB;
import entities.Pedido;
import entities.Deposito;
import entities.DetallePedido;
import entities.Producto;


@ManagedBean (name = "crearPedidoBean")
@ViewScoped
public class CrearPedidoBean {
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB 
	DetallePedidoEJB detallePedidoEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	private Long idPedido;
	// Campos para detalle de pedido
	private Long cantidad = (long) 0;
	
	private Boolean disabledVista;
	
	private Long idProducto;
	
	private Pedido pedidoEdicion;
	
	private DetallePedido detallePedidoEdicion;
	
	private List<DetallePedido> listDetallePedido;
	
	private List<Producto> listProducto;
	
	private List<Pedido> pedidoList = new ArrayList<Pedido>();

	public Pedido getCrearPedidoEdicion() {
		return pedidoEdicion;
	}

	public void setCrearPedidoEdicion(Pedido pedidoEdicion) {
		this.pedidoEdicion = pedidoEdicion;
	}

	public List<Pedido> getCrearPedidoList() {
		return pedidoEJB.findAll();
	}

	public void setCrearPedidoList(List<Pedido> grupoList) {
		this.pedidoList = grupoList;
	}
	
	public void eliminarCrearPedido (Pedido dep){
		pedidoEJB.delete(dep);
	}

	public Pedido getPedidoEdicion() {
		return pedidoEdicion;
	}
	
	public void guardarDetallePedido(){
		//guardando cantidad de pedido y producto en detalle
		detallePedidoEdicion.setProducto(productoEJB.findIdProducto(idProducto));
		detallePedidoEdicion.setCantidad(cantidad);
		listDetallePedido.add(detallePedidoEdicion);
		
		//Limipiando objeto detalle panel Edicion
		detallePedidoEdicion = new DetallePedido();
		cantidad = (long) 0;
		RequestContext.getCurrentInstance().update("templateForm:formEditPedido:productoPedido");
		//RequestContext.getCurrentInstance().reset("templateForm:formEditPedido:panelCargarPedido");
	}
	public void guardarPedido(){
		
		// Debe persistir el usuario
				pedidoEdicion.setFecha(new Date());
				pedidoEdicion.setEstado("Activo");
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpSession session = request.getSession();      
				pedidoEdicion.setUsuario(usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario"))).getUsuario());
				
				if(pedidoEdicion.getIdPedido() == null){
					pedidoEdicion = pedidoEJB.create(pedidoEdicion);
					 FacesContext.getCurrentInstance().addMessage("Pedido Creado", new FacesMessage("Nuevo Pedido Creado."));
				} else {
					// Modificar usuario
					pedidoEdicion = pedidoEJB.update(pedidoEdicion);
					FacesContext.getCurrentInstance().addMessage("Pedido Modificado", new FacesMessage("Pedido Modificado."));
				}
				//Cargar Detalle pedido
				for (int i = 0; i < listDetallePedido.size(); i++) {
					listDetallePedido.get(i).setPedido(pedidoEdicion);
				}
				//Persistiendo Detalle
				for (int i = 0; i < listDetallePedido.size(); i++) {
					detallePedidoEJB.create(listDetallePedido.get(i));
				}
				
				//Luego de guardar los datos
				 pedidoEdicion = new Pedido();
				 listDetallePedido = new ArrayList<DetallePedido>();
				 
	}
	
	public void anularPedido(){
		//Anulando Cabecera de Pedido
		//Estado Anulado de Pedido
		pedidoEdicion.setEstado("Anulado");
		pedidoEJB.update(pedidoEdicion);
		FacesContext.getCurrentInstance().addMessage("Pedido Modificado", new FacesMessage("Pedido Anulado."));
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    try {
			ec.redirect(ec.getRequestContextPath() + "/protected/anularPedido.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			
			listProducto = productoEJB.findAllActivo();
			detallePedidoEdicion = new DetallePedido();
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idPedido = Long.parseLong(params.get("idPedidoParam"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idPedido != null){
				pedidoEdicion = pedidoEJB.findPedidoId(idPedido);
				//listDetallePedido = detallePedidoEJB.DetallePedido(idPedido);
				listDetallePedido = detallePedidoEJB.DetallePedido(pedidoEdicion.getIdPedido());
				System.out.println("CArgo detalle de Pedidos? :" + listDetallePedido.size());
				disabledVista = true;
			} else {
				pedidoEdicion = new Pedido();
				listDetallePedido = new ArrayList<DetallePedido>();
				disabledVista = false;
			}
		}
	}

	public void setPedidoEdicion(Pedido pedidoEdicion) {
		this.pedidoEdicion = pedidoEdicion;
	}

	public List<DetallePedido> getListDetallePedido() {
		return listDetallePedido;
	}

	public void setListDetallePedido(List<DetallePedido> listDetallePedido) {
		this.listDetallePedido = listDetallePedido;
	}

	public List<Producto> getListProducto() {
		return listProducto;
	}

	public void setListProducto(List<Producto> listProducto) {
		this.listProducto = listProducto;
	}

	public List<Pedido> getPedidoList() {
		return pedidoList;
	}

	public void setPedidoList(List<Pedido> pedidoList) {
		this.pedidoList = pedidoList;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public DetallePedido getDetallePedidoEdicion() {
		return detallePedidoEdicion;
	}

	public void setDetallePedidoEdicion(DetallePedido detallePedidoEdicion) {
		this.detallePedidoEdicion = detallePedidoEdicion;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
	public void eliminarDetallePedido(DetallePedido det){
		if(listDetallePedido.remove(det)){
			FacesContext.getCurrentInstance().addMessage("Eliminado Correctamente Detalle Pedido", new FacesMessage("Detalle Pedido Eliminado"));
		}
	}

	public Boolean getDisabledVista() {
		return disabledVista;
	}

	public void setDisabledVista(Boolean disabledVista) {
		this.disabledVista = disabledVista;
	}
	
}

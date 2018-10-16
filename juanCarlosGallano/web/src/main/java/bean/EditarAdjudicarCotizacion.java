package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import ejb.CotizacionCabeceraEJB;
import ejb.CotizacionDetalleEJB;
import ejb.DetallePedidoEJB;
import ejb.PedidoEJB;
import ejb.ProveedorEJB;
import ejb.UsuarioEJB;
import entities.CotizacionCabecera;
import entities.CotizacionDetalle;
import entities.DetalleOrdenCompra;
import entities.DetallePedido;
import entities.OrdenCompra;
import entities.Pedido;
import entities.Producto;
import entities.ProductoProveedor;
import entities.Proveedor;

@ManagedBean (name = "editarAdjudicarCotizacion")
@ViewScoped
public class EditarAdjudicarCotizacion {
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	DetallePedidoEJB detPedidoEJB;
	
	@EJB
	ProveedorEJB proveedorEJB;
	
	@EJB
	CotizacionCabeceraEJB cotizacionEJB;
	
	@EJB
	CotizacionDetalleEJB cotizacionDetEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	HttpSession session;
	
	private Long idPedidoEdicion;
	
	private Long precioAdjudicado;
	
	private Pedido pedido;
	
	private Boolean disableCotizacionCabecera;
	
	private CotizacionDetalle detCotizacionEditar;
	
	private CotizacionCabecera cotizacionSeleccionada;
	
	/*private List<Producto> listProductosCotizacion;
	
	private List<Producto> listProductosCotizacionSelect;*/
	
	private List<OrdenCompra> listOrdenCompraGenerado;
	
	private List<DetalleOrdenCompra> listDetalleOrdenCompraGenerado;
	
	private List<CotizacionCabecera> listCotizacionActualizada;
	
	private List<CotizacionCabecera> listCotizacionActualizadaSeleccionada;
	
	private List<CotizacionDetalle> listCotizacionDetalleSeleccionada;
	
	private List<CotizacionDetalle> listDetalleCotizacion;
	
	private Map<Long, Long> preciosProveedorRecibido;
	
	private List<Long> listadoPreciosProveedor;
	
	private List<DetallePedido> listDetallePedido;
	
	private CotizacionCabecera cotizacionCab;
	
	private CotizacionDetalle cotizacionDet;
	
	private List<CotizacionDetalle> listCotizacionDetalle;
	
	private Proveedor proveedor;
	
	private List<Proveedor> listProveedor;
	
	private List<Proveedor> listProveedorSeleccionado;

	public Long getIdPedidoEdicion() {
		return idPedidoEdicion;
	}

	public void setIdPedidoEdicion(Long idPedidoEdicion) {
		this.idPedidoEdicion = idPedidoEdicion;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Boolean getDisableCotizacionCabecera() {
		return disableCotizacionCabecera;
	}

	public void setDisableCotizacionCabecera(Boolean disableCotizacionCabecera) {
		this.disableCotizacionCabecera = disableCotizacionCabecera;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<DetallePedido> getListDetallePedido() {
		return listDetallePedido;
	}

	public void setListDetallePedido(List<DetallePedido> listDetallePedido) {
		this.listDetallePedido = listDetallePedido;
	}

	public List<CotizacionCabecera> getListCotizacionActualizada() {
		return listCotizacionActualizada;
	}

	public void setListCotizacionActualizada(List<CotizacionCabecera> listCotizacionActualizada) {
		this.listCotizacionActualizada = listCotizacionActualizada;
	}

	public CotizacionCabecera getCotizacionCab() {
		return cotizacionCab;
	}

	public void setCotizacionCab(CotizacionCabecera cotizacionCab) {
		this.cotizacionCab = cotizacionCab;
	}

	
	public List<CotizacionDetalle> getListCotizacionDetalleSeleccionada() {
		return listCotizacionDetalleSeleccionada;
	}

	public void setListCotizacionDetalleSeleccionada(List<CotizacionDetalle> listCotizacionDetalleSeleccionada) {
		this.listCotizacionDetalleSeleccionada = listCotizacionDetalleSeleccionada;
	}


	public CotizacionDetalle getDetCotizacionEditar() {
		return detCotizacionEditar;
	}

	public void setDetCotizacionEditar(CotizacionDetalle detCotizacionEditar) {
		this.detCotizacionEditar = detCotizacionEditar;
	}

	public CotizacionDetalle getCotizacionDet() {
		return cotizacionDet;
	}

	public void setCotizacionDet(CotizacionDetalle cotizacionDet) {
		this.cotizacionDet = cotizacionDet;
	}


	public List<CotizacionDetalle> getListCotizacionDetalle() {
		return listCotizacionDetalle;
	}

	public void setListCotizacionDetalle(List<CotizacionDetalle> listCotizacionDetalle) {
		this.listCotizacionDetalle = listCotizacionDetalle;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<Proveedor> getListProveedor() {
		return listProveedor;
	}

	public void setListProveedor(List<Proveedor> listProveedor) {
		this.listProveedor = listProveedor;
	}
	
	
	public List<Proveedor> getListProveedorSeleccionado() {
		return listProveedorSeleccionado;
	}

	public void setListProveedorSeleccionado(List<Proveedor> listProveedorSeleccionado) {
		this.listProveedorSeleccionado = listProveedorSeleccionado;
	}
	
	

	public Map<Long, Long> getPreciosProveedorRecibido() {
		return preciosProveedorRecibido;
	}

	public void setPreciosProveedorRecibido(Map<Long, Long> preciosProveedorRecibido) {
		this.preciosProveedorRecibido = preciosProveedorRecibido;
	}

	public List<Long> getListadoPreciosProveedor() {
		return listadoPreciosProveedor;
	}

	public List<CotizacionCabecera> getListCotizacionActualizadaSeleccionada() {
		return listCotizacionActualizadaSeleccionada;
	}

	public void setListCotizacionActualizadaSeleccionada(List<CotizacionCabecera> listCotizacionActualizadaSeleccionada) {
		this.listCotizacionActualizadaSeleccionada = listCotizacionActualizadaSeleccionada;
	}

	public void setListadoPreciosProveedor(List<Long> listadoPreciosProveedor) {
		this.listadoPreciosProveedor = listadoPreciosProveedor;
	}

	public List<CotizacionDetalle> getListDetalleCotizacion() {
		return listDetalleCotizacion;
	}

	public void setListDetalleCotizacion(List<CotizacionDetalle> listDetalleCotizacion) {
		this.listDetalleCotizacion = listDetalleCotizacion;
	}
	
	

	public Long getPrecioAdjudicado() {
		return precioAdjudicado;
	}

	public void setPrecioAdjudicado(Long precioAdjudicado) {
		this.precioAdjudicado = precioAdjudicado;
	}

	public CotizacionCabecera getCotizacionSeleccionada() {
		return cotizacionSeleccionada;
	}

	public void setCotizacionSeleccionada(CotizacionCabecera cotizacionSeleccionada) {
		this.cotizacionSeleccionada = cotizacionSeleccionada;
	}

	public List<OrdenCompra> getListOrdenCompraGenerado() {
		return listOrdenCompraGenerado;
	}

	public void setListOrdenCompraGenerado(List<OrdenCompra> listOrdenCompraGenerado) {
		this.listOrdenCompraGenerado = listOrdenCompraGenerado;
	}

	public List<DetalleOrdenCompra> getListDetalleOrdenCompraGenerado() {
		return listDetalleOrdenCompraGenerado;
	}

	public void setListDetalleOrdenCompraGenerado(List<DetalleOrdenCompra> listDetalleOrdenCompraGenerado) {
		this.listDetalleOrdenCompraGenerado = listDetalleOrdenCompraGenerado;
	}

	public void init (){
		if (!FacesContext.getCurrentInstance().isPostback()){
			//Buscar usuario logueado
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession();
			
			listProveedorSeleccionado = new ArrayList<Proveedor>();
			listCotizacionActualizada = new ArrayList<CotizacionCabecera>();
			listProveedor = proveedorEJB.findAll();
			listCotizacionActualizada = cotizacionEJB.findAllActualizado();
			listCotizacionActualizadaSeleccionada = new ArrayList<CotizacionCabecera>();
			listCotizacionDetalleSeleccionada = new ArrayList<CotizacionDetalle>();
			listDetalleCotizacion = new ArrayList<CotizacionDetalle>();
			listOrdenCompraGenerado = new ArrayList<OrdenCompra>();
			listDetalleOrdenCompraGenerado = new ArrayList<DetalleOrdenCompra>();
			disableCotizacionCabecera = false;
		}
	
	}
	
	public void mostrarDetalleCotizacion(){
		//limpiar detalle Cotizacion
		listDetalleCotizacion = new ArrayList<CotizacionDetalle>();
		//metodo para mostrar detalle de cotizacion Seleccionada
		if(listCotizacionActualizadaSeleccionada.size() > 0){
			//Si se ha seleccionado una cotizacion
			//listDetalleCotizacion = cotizacionDetEJB.findDetalleCotizacion(listCotizacionActualizadaSeleccionada.get(0).getIdCotizacionCabecera());
			for (Iterator iterator = listCotizacionActualizadaSeleccionada.iterator(); iterator.hasNext();) {
				CotizacionCabecera cotizacionCabecera = (CotizacionCabecera) iterator.next();
				 	//.add(cotizacionDetEJB.findCotizacionDetalleByCabecera(cotizacionCabecera.getIdCotizacionCabecera())); listDetalleCotizacion
					for (Iterator iterator2 = cotizacionDetEJB.findCotizacionDetalleByCabecera(cotizacionCabecera.getIdCotizacionCabecera()).iterator(); iterator2.hasNext();) {
						CotizacionDetalle cotizacionDet = (CotizacionDetalle) iterator2.next();
						//agregando a  lista detalle a seleccionar
						listDetalleCotizacion.add(cotizacionDet);
					}
				}
			disableCotizacionCabecera = true;
			RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:checkboxDetalleCotizacion");
			RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:checkboxCotizacion");
			
		} else {
			listDetalleCotizacion = new ArrayList<CotizacionDetalle>();
			//update
			RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:checkboxDetalleCotizacion");
			FacesContext.getCurrentInstance().addMessage("Debe Seleccionar una Sola Cotización.", new FacesMessage("Debe Seleccionar una Sola Cotización."));
		}
	}
	
	public void crearOrdenesCompraSeleccionados(){
		
		//Verificar que se haya seleccionado un solo proveedor
		//crea ordenes de compra de detalles seleccionados
		
		//metodo para mostrar detalle de cotizacion Seleccionada
		if(listCotizacionDetalleSeleccionada.size() > 0){
			//Si se ha seleccionado una cotizacion detalle
			//Verificar que se elija un solo proveedor
			//listar los productos
			List<Producto> listaProductosSeleccionados = new ArrayList<Producto>();
			//REcorrer lista proveedores seleccionados
			for (Iterator iterator = listCotizacionDetalleSeleccionada.iterator(); iterator.hasNext();) {
				CotizacionDetalle detCotizacionSeleccionado = (CotizacionDetalle) iterator.next();
				listaProductosSeleccionados.add(detCotizacionSeleccionado.getIdProducto());
			}
			//lista unica de productos
			HashMap<Long, Integer> cantidadProducto = new HashMap<Long , Integer>();
			
			for (Iterator iterator = listaProductosSeleccionados.iterator(); iterator.hasNext();) {
				Producto productoPivot = (Producto) iterator.next();
				//verificar si hay productos duplicados seleccionados
				if(!cantidadProducto.containsKey(productoPivot.getIdProducto())){
					cantidadProducto.put(productoPivot.getIdProducto(), 1);
				} else {
					cantidadProducto.put(productoPivot.getIdProducto(), 2);
				}
				
			}
			//si hay algun proveedor duplicado no permitir
			boolean permitirNoDuplicadoProveedor=true;
			for (Long key : cantidadProducto.keySet()) {
			    Integer value = cantidadProducto.get(key);
			    //Si algun detalle fue seleccionado 2 veces para un producto
			    if(value == 2){
			    	permitirNoDuplicadoProveedor = false;
			    }
			}
			if(!permitirNoDuplicadoProveedor){
				listCotizacionDetalleSeleccionada = new ArrayList<CotizacionDetalle>();
				FacesContext.getCurrentInstance().addMessage("Debe Seleccionar un solo proveedor para un producto.", new FacesMessage("Debe Seleccionar un solo proveedor para un producto."));
			} else{
				//Cargar Tab
				List<Proveedor> listadoProveedoresSeleccionados = obtenerProveedoresSeleccionados();
				
				List<OrdenCompra> listOrdenCompraAdjudicar = new ArrayList<OrdenCompra>();
				List<DetalleOrdenCompra> listDetalleOrdenCompraAdjudicar = new ArrayList<DetalleOrdenCompra>();
				//crear Ordenes de Compra y detalles
				for (Iterator iterator = listadoProveedoresSeleccionados.iterator(); iterator.hasNext();) {
					Proveedor proveedor = (Proveedor) iterator.next();
					OrdenCompra oc = new OrdenCompra();
					oc = new OrdenCompra();
					oc.setEstado("Activo");
					oc.setFechaPedido(new Date());
					oc.setProveedor(proveedor);
					oc.setUsuarioPedido(usuarioEJB.findUserUsuario("HDB"));
					listOrdenCompraAdjudicar.add(oc);
					for (Iterator iterator2 = listCotizacionDetalleSeleccionada.iterator(); iterator2.hasNext();) {
						CotizacionDetalle cotizacionDet = (CotizacionDetalle) iterator2.next();
						//cargar detalle de orden de compra
						//Si pertenece a ese proveedor
						if(cotizacionDet.getIdProveedor().getIdProveedor() == proveedor.getIdProveedor()){
							DetalleOrdenCompra det = new DetalleOrdenCompra();
							det.setCantidad(cotizacionDet.getCantidad());
							det.setOrdenCompra(oc);
							det.setPrecioCompra(cotizacionDet.getPrecioCotizacion());
							det.setProducto(cotizacionDet.getIdProducto());
							listDetalleOrdenCompraAdjudicar.add(det);
						}
					}
				}
				listOrdenCompraGenerado = listOrdenCompraAdjudicar;
				listDetalleOrdenCompraGenerado = listDetalleOrdenCompraAdjudicar;
				this.setListDetalleOrdenCompraGenerado(listDetalleOrdenCompraAdjudicar);
				listDetalleOrdenCompraAdjudicar = null;
				listOrdenCompraAdjudicar = null;
				RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:panelGenerarOrdenCompra");
				RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:tabDetalleOrdenCompra");
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage("Debe Seleccionar al menos una Cotización.", new FacesMessage("Debe Seleccionar al menos una Cotización."));
		}
		
		
	}
	 
	public void limpiarDetallesSeleccionados(){
		//limpiar detalles de seleccion cabecera detalle
		listDetalleCotizacion = new ArrayList<CotizacionDetalle>();
		RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:checkboxDetalleCotizacion");
		disableCotizacionCabecera = false;
		RequestContext.getCurrentInstance().update("templateForm:formEditarAdjudicacion:checkboxCotizacion");
		
		
	}
	
	 public void onTabChange(OrdenCompra ordenCompra) {
		 	List<DetalleOrdenCompra> listAuxDetalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		 	for (Iterator iterator = listDetalleOrdenCompraGenerado.iterator(); iterator.hasNext();) {
				DetalleOrdenCompra detalleOrdenCompraAux = (DetalleOrdenCompra) iterator.next();
				if(detalleOrdenCompraAux.getOrdenCompra().getProveedor().getIdProveedor() == ordenCompra.getProveedor().getIdProveedor()){
					listAuxDetalleOrdenCompra.add(detalleOrdenCompraAux);
				}
			}
		 	listDetalleOrdenCompraGenerado = listAuxDetalleOrdenCompra;
 }
	 
	 public void limpiar(){
		 
		 	//limpiar detalles seleccionados.lista de detalles.
		 	listCotizacionActualizada = cotizacionEJB.findAllActualizado();
			listCotizacionActualizadaSeleccionada = new ArrayList<CotizacionCabecera>();
			listCotizacionDetalleSeleccionada = new ArrayList<CotizacionDetalle>();
			listDetalleCotizacion = new ArrayList<CotizacionDetalle>();
			listOrdenCompraGenerado = new ArrayList<OrdenCompra>();
			listDetalleOrdenCompraGenerado = new ArrayList<DetalleOrdenCompra>();
			disableCotizacionCabecera = false;
	 }
	 
	 public List<Proveedor> obtenerProveedoresSeleccionados(){
		 
		 	//REcorrer lista proveedores seleccionados
		 	List<Proveedor> listProveedoresOrdenCompraGenerar = new ArrayList<Proveedor>();
			for (Iterator iterator = listCotizacionDetalleSeleccionada.iterator(); iterator.hasNext();) {
				CotizacionDetalle detCotizacionSeleccionado = (CotizacionDetalle) iterator.next();
				listProveedoresOrdenCompraGenerar.add(detCotizacionSeleccionado.getIdProveedor());
			}
			//lista unica de productos
			HashMap<Long, Integer> cantidadProducto = new HashMap<Long , Integer>();
			
			for (Iterator iterator = listProveedoresOrdenCompraGenerar.iterator(); iterator.hasNext();) {
				Proveedor productoPivot = (Proveedor) iterator.next();
				//verificar si hay productos duplicados seleccionados
				if(!cantidadProducto.containsKey(productoPivot.getIdProveedor())){
					cantidadProducto.put(productoPivot.getIdProveedor(), 1);
				} else {
					cantidadProducto.put(productoPivot.getIdProveedor(), 2);
				}
				
			}
			//Recorrer y cargar los proveedores unicos
			listProveedoresOrdenCompraGenerar = new ArrayList<Proveedor>();
			for (Long key : cantidadProducto.keySet()) {
			    //Si proveedor es unico fue 1
				 Integer value = cantidadProducto.get(key);
			    listProveedoresOrdenCompraGenerar.add(proveedorEJB.findProveedorId(key));
			}
		 return listProveedoresOrdenCompraGenerar;
	 }
}

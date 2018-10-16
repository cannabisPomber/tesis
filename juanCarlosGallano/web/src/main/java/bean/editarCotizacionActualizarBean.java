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
import entities.DetallePedido;
import entities.Pedido;
import entities.Proveedor;

@ManagedBean (name = "editarCotizacionActualizarBean")
@ViewScoped
public class editarCotizacionActualizarBean {
	
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
	
	private CotizacionDetalle detCotizacionEditar;
	
	private CotizacionCabecera cotizacionSeleccionada;
	
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

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<DetallePedido> getListDetallePedido() {
		return listDetallePedido;
	}

	public void setListDetallePedido(List<DetallePedido> listDetallePedido) {
		this.listDetallePedido = listDetallePedido;
	}

	public CotizacionCabecera getCotizacionCab() {
		return cotizacionCab;
	}

	public void setCotizacionCab(CotizacionCabecera cotizacionCab) {
		this.cotizacionCab = cotizacionCab;
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

	public void init (){
		if (!FacesContext.getCurrentInstance().isPostback()){
			//Buscar usuario logueado
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession();
			
			listProveedorSeleccionado = new ArrayList<Proveedor>();
			listProveedor = proveedorEJB.findAll();
			
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				cotizacionSeleccionada = cotizacionEJB.findCotizacionCabecera(Long.parseLong(params.get("idCotizacion")));
				preciosProveedorRecibido = new HashMap<Long, Long>();
				listCotizacionDetalle = cotizacionDetEJB.findDetalleCotizacion(cotizacionSeleccionada.getIdCotizacionCabecera());
				listadoPreciosProveedor = new ArrayList<Long>(listCotizacionDetalle.size());
				
				//Cargar Detalle en Hash Map
				for (Iterator iterator = listCotizacionDetalle.iterator(); iterator.hasNext();) {
					CotizacionDetalle cotizacionDetalle = (CotizacionDetalle) iterator.next();
					//Cargar detalle con cantidad 0 en ID
					preciosProveedorRecibido.put(cotizacionDetalle.getIdDetalleCabecera(), 0l);
				}
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			
	}
	
	}
	
	public void cargarCotizacion(){
		
		if(listProveedorSeleccionado.size() == 0){
			//Error debe Elegir proveedores
			FacesContext.getCurrentInstance().addMessage("No ha seleccionado Proveedores.", new FacesMessage("No ha seleccionado Proveedores."));
		} else {
			//cargar Cotizacion
			
			//crear cotizacion.
			CotizacionCabecera cotCabecera = new CotizacionCabecera();
			cotCabecera.setEstado("ACTIVO");
			cotCabecera.setFechaCargaCotizacion(new Date());
			cotCabecera.setUsuarioCargarCotizacion(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
			cotizacionEJB.create(cotCabecera);
			//Crear Detalle
			//Recorrer lista de proveedores
			for (Iterator iterator = listProveedorSeleccionado.iterator(); iterator.hasNext();) {
				Proveedor proveedorCot = (Proveedor) iterator.next();
				//Recorrer Detalle Pedido
				for (Iterator iterator2 = listDetallePedido.iterator(); iterator2.hasNext();) {
					DetallePedido detPedido = (DetallePedido) iterator2.next();
					
					//Crear Cotizacion Detalle
					CotizacionDetalle cotDetalle = new CotizacionDetalle();
					cotDetalle.setIdProducto(detPedido.getProducto());
					cotDetalle.setIdProveedor(proveedorCot);
					cotDetalle.setCotizacionCabecera(cotCabecera);
					cotDetalle.setCantidad(0l);
					cotDetalle.setPrecioCotizacion(0l);
					//GuardarDetalleCotizacion
					cotizacionDetEJB.create(cotDetalle);
				}
			}
		}
		//Modificar Pedido
		pedido.setEstado("Cerrado");
		pedidoEJB.update(pedido);
		FacesContext.getCurrentInstance().addMessage("Creado Cotizacion.", new FacesMessage("Creado Cotizacion."));
		
	}
	
	
	public void anularPedido(){
		
	}

	public Long mostrarPrecioProveedor(Long idDetalle){
		//Mostrar Cantidad de Hashmap
		return preciosProveedorRecibido.get(idDetalle);
	}
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Detalle Editado", ((CotizacionDetalle) event.getObject()).getIdProducto().getNombreProducto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
     
    
	
	public void modificarMapPrecioProveedor(Long idDetalleCotizacion, ValueChangeEvent e){
		
		//Modificar campo de map de precios
		preciosProveedorRecibido.put(idDetalleCotizacion, (Long)e.getNewValue());
	}
	
	//Metodo para edicion de Celda
	/* public void onCellEdit(CellEditEvent event) {
		 
		 System.out.println("CELL EDIT Ingreso");
		 Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	 }*/
	
	public void guardarCotizacionActualizada(){
		//Actualizar estado Cotizacion
		cotizacionSeleccionada.setEstado("ACTUALIZADO");
		cotizacionEJB.update(cotizacionSeleccionada);
		//Guardar la cotizacion Detalle
		for (Iterator iterator = listCotizacionDetalle.iterator(); iterator.hasNext();) {
			CotizacionDetalle cotizacionDetalle = (CotizacionDetalle) iterator.next();
			//actualizar Detalle.
			cotizacionDetEJB.update(cotizacionDetalle);
		}
		FacesContext.getCurrentInstance().addMessage("Modificado Cotizacion.", new FacesMessage("Modificado Cotizacion."));
		 /*FacesContext context = FacesContext.getCurrentInstance();
		    HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
		    String contextPath = origRequest.getContextPath();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/editarActualizacionCotizacion.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	 
}

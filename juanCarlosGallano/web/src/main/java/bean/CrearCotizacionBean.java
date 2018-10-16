package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@ManagedBean (name = "crearCotizacionBean")
@ViewScoped
public class CrearCotizacionBean {
	
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
	
	private Pedido pedido;
	
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
				pedido = pedidoEJB.findPedidoId(Long.parseLong(params.get("idPedido")));
				
				listDetallePedido = detPedidoEJB.DetallePedido(pedido.getIdPedido());
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
			cotCabecera.setUsuarioCotizacion(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
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
					cotDetalle.setCantidad(detPedido.getCantidad());
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

}

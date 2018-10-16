package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UpdateModelException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.primefaces.context.RequestContext;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import ejb.DepositoEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleOrdenCompraEJB;
import ejb.LoteEJB;
import ejb.OrdenCompraEJB;
import ejb.ProductoEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.CajaDetalle;
import entities.Deposito;
import entities.DetalleDeposito;
import entities.DetalleOrdenCompra;
import entities.Lote;
import entities.OrdenCompra;
import entities.Producto;
import entities.Stock;
import entities.StockDetalle;

@ManagedBean (name = "recibirPedidoBean")
@ViewScoped
public class RecibirPedidoBean {
	
	@EJB
	OrdenCompraEJB ordenCompraEJB;
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	DepositoEJB depositoEJB;
	
	@EJB
	DetalleDepositoEJB detalleDepositoEJB;
	
	@EJB
	DetalleOrdenCompraEJB detOrdenCompraEJB;
	
	@EJB
	StockEJB stockEJB;
	
	@EJB
	LoteEJB loteEJB;
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	CajaDetalleEJB cajaDetalleEJB;
	
	@EJB
	StockDetalleEJB stockDetalleEJB;
	
	@Inject
	UsuarioBean usuarioBean;
	
	HttpSession session;
	
	private OrdenCompra ordenCompraSeleccionado;
	
	private List<OrdenCompra> listOrdenCompraConfirmado;
	
	private List<OrdenCompra> listOrdenCompraSeleccionado;
	
	private List<DetalleOrdenCompra> listDetOrdenCompra;
	
	private List<DetalleOrdenCompra> listDetOc;
	//Para guadar el producto buscado por codigo de barra
	
	//Caja abierta y detalle
	private Caja cajaAbierta;
	private CajaDetalle cajaAbiertaDetalle;
	//private List<CajaDetalle> listCajaDetalle;
	//Deposito Seleccionado para guardar Stock
	private Deposito deposito;
	private Long idDepositoSeleccionado;
	//Lista de depositos Activos
	private List<Deposito> listDeposito;
	private Producto producto;
	
	private Date fechaVencimientoProducto;
	//Para seleccionar Id a ser focus en acciones
	private String focusProperty;
	//Orden  de Compra seleccionado
	private Long idOrdenCompraSelected;
	//Producto a recepcionar
	private Long idProductoSelected;
	
	//String numero de orden de compra
	private String nroFactura;
	
	private Long costoDetalle;
	private String codigoBarraProducto;
	private Long cantidadRecibida;
	private Long  cantidadPedido;
	private Long importeOrdenCompra;
	private Long importeFactura;
	
	public void init(){
		//Eliminar constraint unique de idProducto
		try{
			detOrdenCompraEJB.deleteConstraintUniqueProducto();
		} catch (Exception ex){
			System.out.println("Error al borrar Constraint: " + ex.getMessage());
		}
		if (!FacesContext.getCurrentInstance().isPostback()){
			
			
			listOrdenCompraConfirmado = new ArrayList<OrdenCompra>();
			listOrdenCompraSeleccionado = new ArrayList<OrdenCompra>();
			listDetOc = new ArrayList<DetalleOrdenCompra>();
			listDeposito = new ArrayList<Deposito>();
			try {
				String usuarioLogueado = usuarioBean.usuarioLogueado();
				listDeposito = depositoEJB.findAllDepositoEmpresa(usuarioEJB.findUserUsuario(usuarioLogueado).getEmpresa().getIdEmpresa());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Cargando Listas de Orden de Compra Activos
			listOrdenCompraConfirmado = ordenCompraEJB.findAllConfirmadoPendiente();
			//Modificar si su detalle ha sido recepcionado
			verificarRecepcion();
			deposito = new Deposito();
			fechaVencimientoProducto = new Date();
			importeOrdenCompra = (long) 0;
			importeFactura = (long) 0;
		}
	}

	public List<OrdenCompra> getListOrdenCompraActivos() {
		return listOrdenCompraConfirmado;
	}

	public void setListOrdenCompraActivos(List<OrdenCompra> listOrdenCompraConfirmado) {
		this.listOrdenCompraConfirmado = listOrdenCompraConfirmado;
	}

	public List<OrdenCompra> getListOrdenCompraConfirmados() {
		return listOrdenCompraSeleccionado;
	}

	public void setListOrdenCompraConfirmados(List<OrdenCompra> listOrdenCompraSeleccionado) {
		this.listOrdenCompraSeleccionado = listOrdenCompraSeleccionado;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setCostoDetalle(Long costoDetalle) {
		this.costoDetalle = costoDetalle;
	}
	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public List<Deposito> getListDeposito() {
		return listDeposito;
	}

	public void setListDeposito(List<Deposito> listDeposito) {
		this.listDeposito = listDeposito;
	}

	public String getCodigoBarraProducto() {
		return codigoBarraProducto;
	}

	public Long getCantidadPedido() {
		return cantidadPedido;
	}

	public void setCantidadPedido(Long cantidadPedido) {
		this.cantidadPedido = cantidadPedido;
	}

	public OrdenCompra getOrdenCompraSeleccionado() {
		return ordenCompraSeleccionado;
	}

	public void setOrdenCompraSeleccionado(OrdenCompra ordenCompraSeleccionado) {
		this.ordenCompraSeleccionado = ordenCompraSeleccionado;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public void setCodigoBarraProducto(String codigoBarraProducto) {
		this.codigoBarraProducto = codigoBarraProducto;
	}

	

	public Long getImporteOrdenCompra() {
		return importeOrdenCompra;
	}

	public void setImporteOrdenCompra(Long importeOrdenCompra) {
		this.importeOrdenCompra = importeOrdenCompra;
	}

	public Long getImporteFactura() {
		return importeFactura;
	}

	public void setImporteFactura(Long importeFactura) {
		this.importeFactura = importeFactura;
	}

	public void buscarDetalleOrdenCompra(){
		
		//VERIFICAR SI EXISTE CAJA ABIERTA
		cajaAbierta = new Caja();
		cajaAbierta = verificarCaja();
		if(cajaAbierta != null){
			//CArgando Detalle de Orden deCompra en lista
			//EXISTE CAJA ABIERTA PERMITE BUSQUEDA
			focusProperty = "cantidadRecibidaDetalle";
			ordenCompraSeleccionado = ordenCompraEJB.findOrdenCompraId(idOrdenCompraSelected);
			listDetOrdenCompra = detOrdenCompraEJB.DetalleOrdenCompra(ordenCompraEJB.findOrdenCompraId(idOrdenCompraSelected).getIdOrdenCompra());
			
		} else{
			//SE DEBE ABRIR UNA CAJA PARA RECIBIR UN PEDIDO
			FacesContext.getCurrentInstance().addMessage("No se puede Recepcionar Pedido", new FacesMessage("No Se Encontro Caja Abierta"));
		}
		
		
		
	}
	
	public List<DetalleOrdenCompra> getListDetOc() {
		return listDetOc;
	}

	public void setListDetOc(List<DetalleOrdenCompra> listDetOc) {
		this.listDetOc = listDetOc;
	}

	public String getFocusProperty() {
		return focusProperty;
	}

	public void setFocusProperty(String focusProperty) {
		this.focusProperty = focusProperty;
	}

	public Long costoProducto(Long precioCompra, Long cantidad){
		costoDetalle = precioCompra*cantidad;
		return costoDetalle;
	}
	public void limpiar(){
		listOrdenCompraConfirmado = new ArrayList<OrdenCompra>();
		listOrdenCompraSeleccionado = new ArrayList<OrdenCompra>();
		listDetOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		listOrdenCompraConfirmado = ordenCompraEJB.findAllConfirmadoPendiente();
		//Modificar si su detalle ha sido recepcionado
		verificarRecepcion();
		RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido");
	}
	public List<OrdenCompra> getListOrdenCompraConfirmado() {
		return listOrdenCompraConfirmado;
	}

	public Long getIdDepositoSeleccionado() {
		return idDepositoSeleccionado;
	}

	public void setIdDepositoSeleccionado(Long idDepositoSeleccionado) {
		this.idDepositoSeleccionado = idDepositoSeleccionado;
	}

	public void setListOrdenCompraConfirmado(List<OrdenCompra> listOrdenCompraConfirmado) {
		this.listOrdenCompraConfirmado = listOrdenCompraConfirmado;
	}

	public List<OrdenCompra> getListOrdenCompraSeleccionado() {
		return listOrdenCompraSeleccionado;
	}

	public void setListOrdenCompraSeleccionado(List<OrdenCompra> listOrdenCompraSeleccionado) {
		this.listOrdenCompraSeleccionado = listOrdenCompraSeleccionado;
	}

	public Long getCantidadRecibida() {
		return cantidadRecibida;
	}

	public Date getFechaVencimientoProducto() {
		return fechaVencimientoProducto;
	}

	public void setFechaVencimientoProducto(Date fechaVencimientoProducto) {
		this.fechaVencimientoProducto = fechaVencimientoProducto;
	}

	public void setCantidadRecibida(Long cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	//Mostrar Detalle de Orden de Compra
	public void mostrarDetalleOrdenCompra(){
		//creando Detalle de Orden de compra
		//listDetOc = detOrdenCompraEJB.DetalleOrdenCompra(idOrdenCompraSelected);
		//ordenCompraSeleccionado = ordenCompraEJB.findOrdenCompraId(idOrdenCompraSelected);
		focusProperty = "inputCodBarra";
	}

	//Busca la cantidad de producto seleccionado que se ingreso por codigo de barra
	public void buscarCantidadPedido(){
		//buscar pedido por producto y a su vez por Codigo de Barra
		//Buscando producto
		producto = productoEJB.findProductoCodigoBarra(codigoBarraProducto);
		boolean encontroDetalle = false;
		if(producto != null){
			//buscar producto en detalle de Orden de compra
			for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
				DetalleOrdenCompra detalleOrdenCompra = (DetalleOrdenCompra) iterator.next();
				//Si el Codigo de Barra buscado 
				if (detalleOrdenCompra.getProducto().getIdProducto().equals(producto.getIdProducto())){
					//Cargar cantidad pedida automaticamente
					focusProperty = "inputCantidad";
					//cantidadRecibida = (long) 0;
					cantidadPedido = detalleOrdenCompra.getCantidad();
					encontroDetalle = true;
					//Si encontro actualizar panel de Detalle
					RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido:panelDetallePedido");
				}
				
			}
			if (!encontroDetalle){
				//Si no se encontro producto en detalle
				FacesContext.getCurrentInstance().addMessage("Producto no hallado", new FacesMessage("Producto no Existe en Orden de Compra."));
				//Limpiando Codigo de Barra
				codigoBarraProducto = "";
				encontroDetalle = false;
				//Colocando focus en Codigo de Barra
				focusProperty = "inputCodBarra";
				RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido:panelDetallePedido");
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("Producto no hallado", new FacesMessage("No Se Encontro Producto. Con ese Codigo de Barra"));
			// Coloca a Cero la cantidad Recibida si no se encuentra producto y setea codigo de barra
			codigoBarraProducto = "";
			//cantidadRecibida = (long) 0;
			cantidadPedido =  (long) 0;
			//Colocando focus en Codigo de Barra
			focusProperty = "inputCodBarra";
			RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido:panelDetallePedido");
		}
	}
	//Verifica la cantidad de producto ingresada
	public void verificarCantidadPedido(){
		if (cantidadPedido !=0){
			//Si la Cantidad Ingresada es correcta
			if (cantidadRecibida <= cantidadPedido){
				//Focus en calendar de fechaVencimiento
				focusProperty = "fechaVencimiento";
				RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido:panelDetallePedido");
			} else {
				//Focus en cantidad pedido
				focusProperty = "inputCantidad";
				RequestContext.getCurrentInstance().update("templateForm:formRecibirPedido:panelDetallePedido");
				cantidadRecibida = null;
				FacesContext.getCurrentInstance().addMessage("Producto no hallado", new FacesMessage("Cantidad Recibida excede cantidad pedido."));
			}
		}
	}

	public Long getIdProductoSelected() {
		return idProductoSelected;
	}

	public void setIdProductoSelected(Long idProductoSelected) {
		this.idProductoSelected = idProductoSelected;
	}
	
	public void recepcionarProductos(){
		boolean permitirDialog = true;
		//Verificar fecha de Vencimiento
		for (Iterator iterator = listDetOrdenCompra.iterator(); iterator.hasNext();) {
			DetalleOrdenCompra detalleOrdenCompraVencimiento = (DetalleOrdenCompra) iterator.next();
			if(detalleOrdenCompraVencimiento.getVencimiento().before(new Date())){
				permitirDialog= false;
			}
		}
		if(permitirDialog){
			//abrir dialog
			RequestContext.getCurrentInstance().execute("PF('recepcionDialog').show()");
			//Sirve para impactar en Stock y Caja por productos ingresados 
			//Verificando los valores ingresados para recepcionar Productos
			//Verificar cantidad ingresada y fecha de vencimiento
			
			//Sirve para mostrar si la orden de compra se entrega en su totalidad
			Boolean ordenCompraCompleto = true;
			Boolean permitirGuardado = true;
			Date fechaSistema = new Date();
			for (Iterator iterator = listDetOrdenCompra.iterator(); iterator.hasNext();) {
				DetalleOrdenCompra detOc = (DetalleOrdenCompra) iterator.next();
					importeOrdenCompra = importeOrdenCompra +detOc.getCantidadRecibida()*detOc.getPrecioCompra();
					
					if (!detOc.getCantidad().equals(detOc.getCantidadRecibida())){
						//Cantidades distintas debe guardarse como pendiente orden de compra
						ordenCompraCompleto = false;
						detOc.setEstado("PENDIENTE");
					} else {
						detOc.setEstado("RECEPCIONADO");
					}
					//Si la fecha es mayor a la fecha del sistema
					if (detOc.getVencimiento().before(fechaSistema)){
						//Mostrar Mensaje de Error
						permitirGuardado = false;
						FacesContext.getCurrentInstance().addMessage("Fecha de Vencimiento Invalida", new FacesMessage("Fecha de Vencimiento Invalida."));
					}
			}
			//Si las fechas de vencimiento son mayores a la fechas actuales
			if (permitirGuardado){
				if (!ordenCompraCompleto){
					ordenCompraSeleccionado.setEstado("PENDIENTE");
					//ordenCompraSeleccionado= ordenCompraEJB.update(ordenCompraSeleccionado);
				} else {
					//Si el orden de compra se entrego al 100% guardar como cerrado
					ordenCompraSeleccionado.setEstado("RECEPCIONADO");
					//ordenCompraSeleccionado= ordenCompraEJB.update(ordenCompraSeleccionado);
				}
			} else {
				FacesContext.getCurrentInstance().addMessage("Fecha de Vencimiento Invalida", new FacesMessage("No se Permite Recepcionar. Modificar fechas de Vencimiento"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("Fecha de Vencimiento Invalida", new FacesMessage("No se Permite Recepcionar. Modificar fechas de Vencimiento"));
		}
	}
	
	
	//Dependiendo del estado del Orden de Compra cierra o modifica de diferente forma
	//Realiza impacto en stock
	public void cerrarRecepcion(){
		
		Long montoCompra = (long) 0;
		listDetOc = listDetOrdenCompra;
		List<DetalleOrdenCompra> listDetModificar = new ArrayList<DetalleOrdenCompra>();
		//Si es de estado Cerrado debe terminar el flujo 
		//Obtener usuario Logueado
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();
		
		//Calculando Monto de Orden de Compra
		
		for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
			DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
			//CalcuLANDO monto a Debitar en caja
			montoCompra = montoCompra + detalle.getPrecioCompra()*detalle.getCantidadRecibida();
			
		}
		
		//Verificando que haya el importe necesario en caja
		//REFRESH DE CAJA
		cajaAbierta = cajaEJB.cajaAbiertaTesoreria();	
		//RESTAR MONTO EN CAJA
		if(cajaAbierta != null){
			if(cajaAbierta.getMontoCaja() < montoCompra){
				
				//NO SE PUEDE REALIZAR VENTA POR QUE NO EXISTE MONTO EN CAJA
				FacesContext.getCurrentInstance().addMessage("No existe monto suficiente en caja", new FacesMessage("No existe monto suficiente"));
				FacesContext.getCurrentInstance().addMessage("No se puede Recepcionar Producto", new FacesMessage("No existe monto suficiente"));
				limpiar();
			}else {
				//Si existe monto en Caja Tesoreria
				//Verificar si se entrego Completamente y hacer las acciones
				if(ordenCompraSeleccionado.getEstado() == "PENDIENTE"|| ordenCompraSeleccionado.getEstado() == "RECEPCIONADO"){
					//Si es que es Pendiente se debe crear otra orden de compra con productos sobrantes
					if(ordenCompraSeleccionado.getEstado() == "PENDIENTE"){
						//Si no se entrega en su totalidad
						//Crear nueva orden de compra para guardar como pendiente
						OrdenCompra ordenCompraPendiente = new OrdenCompra();
						ordenCompraPendiente.setIdOrdenCompra(null);
						ordenCompraPendiente.setFechaRecepcion(null);
						ordenCompraPendiente.setNroFacturaProveedor("");
						ordenCompraPendiente.setUsuarioRecepcion(null);
						ordenCompraPendiente.setFechaAprobacion(ordenCompraSeleccionado.getFechaAprobacion());
						ordenCompraPendiente.setProveedor(ordenCompraSeleccionado.getProveedor());
						ordenCompraPendiente.setFechaPedido(ordenCompraSeleccionado.getFechaPedido());
						ordenCompraPendiente.setUsuarioAprobacion(ordenCompraSeleccionado.getUsuarioAprobacion());
						ordenCompraPendiente.setEstado("PENDIENTE");
						ordenCompraEJB.create(ordenCompraPendiente);
						//Guardando Ordenes de Compra a mantener pendiente
						
						System.out.println("--------//------------//------------");
						for (Iterator iterator2 = listDetOc.iterator(); iterator2.hasNext();) {
							DetalleOrdenCompra detalleAModificar = (DetalleOrdenCompra) iterator2.next();
							System.out.println("Detalle a Guardar ...." + detalleAModificar.getProducto().getMarca()+ " "+detalleAModificar.getProducto().getNombreProducto() + "Cantidad: "+ detalleAModificar.getCantidad() +"Cantidad Recibida: "+ detalleAModificar.getCantidadRecibida());
							
							if(detalleAModificar.getCantidad()-detalleAModificar.getCantidadRecibida()>0){
								detalleAModificar.setIdDetalleOrdenCompra(ordenCompraPendiente.getIdOrdenCompra());
								detalleAModificar.setCantidad(detalleAModificar.getCantidad()-detalleAModificar.getCantidadRecibida());
								detalleAModificar.setCantidadRecibida(0l);
								detalleAModificar.setEstado("PENDIENTE");
								listDetModificar.add(detalleAModificar);
							}
							
						}
						//persistiendo orden compra pendiente
						for (Iterator iterator = listDetModificar.iterator(); iterator.hasNext();) {
							DetalleOrdenCompra detOrdenCompraPendiente = (DetalleOrdenCompra) iterator.next();
							detOrdenCompraEJB.update(detOrdenCompraPendiente);
						}
						//Cargando Stock 
						ordenCompraEJB.editarOrdenCompraRecepcionado(ordenCompraSeleccionado.getIdOrdenCompra(), usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")).getIdUsuario());
						
						for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
							DetalleOrdenCompra detOc = (DetalleOrdenCompra) iterator.next();
							detOc.setEstado("ENTREGADO");
							detOrdenCompraEJB.update(detOc);
						}
						//Cargar en Stock Detalle y deposito
						cargarStock(listDetOc);
					}  
					
					
					
						
					} else {
						//Si se entrega Totalmente
						//Cargar stock
						//Modificando Cabecera de Orden de Compra
						ordenCompraEJB.editarOrdenCompraRecepcionado(ordenCompraSeleccionado.getIdOrdenCompra(), usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")).getIdUsuario());
						
						for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
							DetalleOrdenCompra detOc = (DetalleOrdenCompra) iterator.next();
							detOc.setEstado("ENTREGADO");
							detOrdenCompraEJB.update(detOc);
						}
						//Cargar en Stock Detalle y deposito
						cargarStock(listDetOc);
					}
				}
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Abrir Caja en Tesoreria.", null);
			facesContext.addMessage("Abrir Caja en Tesoreria.", facesMessage);
		}
		
	}
	
	
	public Caja verificarCaja(){
		//Busca si existe una caja abierte en la fecha actual
		//return cajaEJB.cajaAbiertaSinUser();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();
		return cajaEJB.cajaAbierta(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
	}

	public Caja getCajaAbierta() {
		return cajaAbierta;
	}

	public void setCajaAbierta(Caja cajaAbierta) {
		this.cajaAbierta = cajaAbierta;
	}

	public CajaDetalle getCajaAbiertaDetalle() {
		return cajaAbiertaDetalle;
	}

	public void setCajaAbiertaDetalle(CajaDetalle cajaAbiertaDetalle) {
		this.cajaAbiertaDetalle = cajaAbiertaDetalle;
	}
	
	
	public Boolean debitarCaja(){
		return false;
	}
	
	public void verificarRecepcion(){
		//leer cada orden de Compra y Verificar que haya sido recepcionado
		Boolean recepcionado = false;
		//int posicionOrdenCompraRecepcionado;
		List<Integer> posicionOrdenCompraRecepcionado = new ArrayList<Integer>();
		for (int i = 0; i < listOrdenCompraConfirmado.size(); i++) {
			OrdenCompra ordenCompraVerificar = listOrdenCompraConfirmado.get(i);
			//Verificar si el detalle ha sido recepcionado
			List<DetalleOrdenCompra> listDetalles = new ArrayList<DetalleOrdenCompra>();
			listDetalles = detOrdenCompraEJB.DetalleOrdenCompra(ordenCompraVerificar.getIdOrdenCompra());
			for (int j = 0; j < listDetalles.size(); j++) {
				DetalleOrdenCompra detalleOrdenCompraVerificar = listDetalles.get(j);
			
				if (detalleOrdenCompraVerificar.getEstado() != null){
					if(detalleOrdenCompraVerificar.getEstado().equals("RECEPCIONADO")){
						recepcionado = true;
					}
				}
				
			}
			//Si fue recepcionado guardar posicion
			if (recepcionado){
				posicionOrdenCompraRecepcionado.add(i);
			}
			recepcionado= false;
		}
		
		//Modificar Ordenes de Compra Recepcionados
		for (int j = 0; j < listOrdenCompraConfirmado.size(); j++) {
			OrdenCompra ordenCompraModificar = listOrdenCompraConfirmado.get(j);
			for (Iterator iterator = posicionOrdenCompraRecepcionado.iterator(); iterator.hasNext();) {
				Integer posicion = (Integer) iterator.next();
				//Si la Orden de Compra ha sido Recepcionada
				if(j == posicion){
					//Modificar Orden Compa
					ordenCompraModificar.setEstado("RECEPCIONADO");
					listOrdenCompraConfirmado.set(j, ordenCompraModificar);
				}
			}
		}
	}
	
	public void cargarStock(List<DetalleOrdenCompra> listProductosCargar){
		
		//CArgar en Stock y detalle
		//ademas de detalle deposito
		for (Iterator iterator = listProductosCargar.iterator(); iterator.hasNext();) {
			DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
			if(idDepositoSeleccionado != null){
				//Buscar o cargar stock
				Stock stock = stockEJB.findStockCodigoBarra(detalle.getProducto().getCodigoBarra());
				//Si no existe Stock crear uno nuevo
				if(stock == null){
					//Crea uno nuevo
					stock = new Stock();
					//stock.setCantidadStock(detalle.getCantidadRecibida());
					//Si el producto posee producto unitario guardar unico
					if(detalle.getProducto().getProductoUnitario() !=null){ 
						//Cargar stock de unitario
						stock = stockEJB.findStockCodigoBarra(detalle.getProducto().getProductoUnitario().getCodigoBarra());
						//Si no hay ningun registro de stock
						if (stock == null){
							stock = new Stock();
						}
						Long totalUnitario = detalle.getCantidad()*(detalle.getProducto().getTipoProducto().getCantidad());
						stock.setCantidadStock(totalUnitario); 
						stock.setProducto(detalle.getProducto().getProductoUnitario());
						stock.setCodigoBarra(detalle.getProducto().getProductoUnitario().getCodigoBarra());
						stockEJB.create(stock);
					} else {
						//Sino posee es un producto unitario
						stock.setCantidadStock(detalle.getCantidadRecibida());
						stock.setProducto(detalle.getProducto());
						stock.setCodigoBarra(detalle.getProducto().getCodigoBarra());
						stockEJB.create(stock);
						stock = new Stock();
					}
				} else {
					
					//Si no es un registro nuevo
					//Si posee producto unitario
					if(detalle.getProducto().getProductoUnitario() !=null){ 
						//Cargar stock de unitario
						stock = stockEJB.findStockCodigoBarra(detalle.getProducto().getProductoUnitario().getCodigoBarra());
						//Modificando campo que guarda la cantidad unica
						stock.setCantidadStock(stock.getCantidadStock()+ (detalle.getCantidadRecibida()*detalle.getProducto().getTipoProducto().getCantidad()));
						stock.setProducto(detalle.getProducto());
						stock.setCodigoBarra(detalle.getProducto().getCodigoBarra());
						stockEJB.update(stock);
						stock = new Stock();
					} else {
						//si es producto unitario
						stock.setCantidadStock(stock.getCantidadStock() + detalle.getCantidadRecibida());
						stock.setProducto(detalle.getProducto());
						stock.setCodigoBarra(detalle.getProducto().getCodigoBarra());
						stockEJB.update(stock);
						stock = new Stock();
					}
					
					
				}
				StockDetalle stockDetalle = new StockDetalle();
				stockDetalle.setFechaIngreso(new Date());
				//Cargar fecha vencimiento
				stockDetalle.setFechaVencimiento(detalle.getVencimiento());
				if(detalle.getProducto().getProductoUnitario() !=null){ 
					//Cargando datos de cantidad recibida y restante
					//Se carga datos de nuevo stock y lote
					stockDetalle.setProducto(detalle.getProducto().getProductoUnitario());
					stockDetalle.setCantidadRecibida(detalle.getCantidad()*detalle.getProducto().getTipoProducto().getCantidad());
					stockDetalle.setCantidadRestante(detalle.getCantidad()*detalle.getProducto().getTipoProducto().getCantidad());
					
				} else {
					//Si es unitario
					//Cargando datos de cantidad recibida y restante
					stockDetalle.setProducto(detalle.getProducto());
					stockDetalle.setCantidadRecibida(detalle.getCantidad());
					stockDetalle.setCantidadRestante(detalle.getCantidad());
				}
				
				stockDetalle.setEstado("ACTIVO");
				stockDetalle.setDeposito(depositoEJB.findIdDeposito(idDepositoSeleccionado));
				stockDetalle.setIdOrdenCompra(detalle.getOrdenCompra());
				String hash = "";
				
				
				//Generando Hash Lote
				RandomStringGenerator randomStringGenerator =
				        new RandomStringGenerator.Builder()
				                .withinRange('0', 'z')
				                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
				                .build();
				hash = randomStringGenerator.generate(6);
				stockDetalle.setHashLote(hash.toUpperCase());
				hash = null;
				
				
				
				//Persistir Stock Detalle
				stockDetalleEJB.create(stockDetalle);
				DetalleDeposito detalleDeposito = new DetalleDeposito();
				if(detalle.getProducto().getProductoUnitario() != null){
					detalleDeposito = detalleDepositoEJB.findDeposito(detalle.getProducto().getProductoUnitario().getIdProducto(), idDepositoSeleccionado);
					if(detalleDeposito == null){
						//Crear nuevo registro
						detalleDeposito = new DetalleDeposito();
						detalleDeposito.setCantidadProducto(detalle.getCantidadRecibida()*detalle.getProducto().getTipoProducto().getCantidad());
						detalleDeposito.setProducto(detalle.getProducto().getProductoUnitario());
						detalleDeposito.setDeposito(depositoEJB.findIdDeposito(idDepositoSeleccionado));
						detalleDepositoEJB.create(detalleDeposito);
					} else {
						//Modificar detalle deposito
						detalleDeposito.setCantidadProducto(detalleDeposito.getCantidadProducto()+ detalle.getCantidadRecibida()*detalle.getProducto().getTipoProducto().getCantidad());
						detalleDeposito.setProducto(detalle.getProducto());
						detalleDeposito.setDeposito(depositoEJB.findIdDeposito(idDepositoSeleccionado));
						detalleDeposito = detalleDepositoEJB.update(detalleDeposito);
					}
				} else {
					//si es unitario
					//Buscar deposito por id Producto
					detalleDeposito = detalleDepositoEJB.findDeposito(detalle.getProducto().getIdProducto(), idDepositoSeleccionado);
					if(detalleDeposito == null){
						//Crear nuevo registro
						detalleDeposito = new DetalleDeposito();
						detalleDeposito.setCantidadProducto(detalle.getCantidadRecibida());
						detalleDeposito.setProducto(detalle.getProducto());
						detalleDeposito.setDeposito(depositoEJB.findIdDeposito(idDepositoSeleccionado));
						detalleDepositoEJB.create(detalleDeposito);
					} else {
						//Modificar detalle deposito
						detalleDeposito.setCantidadProducto(detalleDeposito.getCantidadProducto()+ detalle.getCantidadRecibida());
						detalleDeposito.setProducto(detalle.getProducto());
						detalleDeposito.setDeposito(depositoEJB.findIdDeposito(idDepositoSeleccionado));
						detalleDeposito = detalleDepositoEJB.update(detalleDeposito);
					}
				}
				
				ordenCompraSeleccionado =  ordenCompraEJB.findOrdenCompraId(ordenCompraSeleccionado.getIdOrdenCompra());
				ordenCompraSeleccionado.setFechaRecepcion(new Date());
				ordenCompraSeleccionado.setUsuarioRecepcion(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
				ordenCompraSeleccionado.setEstado("RECEPCIONADO");
				ordenCompraEJB.update(ordenCompraSeleccionado);
				FacesContext.getCurrentInstance().addMessage("Recepcionado Exitosamente", new FacesMessage("Recepcionado Exitosamente"));
				
			} else {
				FacesContext.getCurrentInstance().addMessage("No se ha seleccionado Deposito.", new FacesMessage("No se ha seleccionado Deposito."));
			}
			limpiar();
		}	
		
	}
}

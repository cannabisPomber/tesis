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
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			listOrdenCompraConfirmado = new ArrayList<OrdenCompra>();
			listOrdenCompraSeleccionado = new ArrayList<OrdenCompra>();
			listDetOc = new ArrayList<DetalleOrdenCompra>();
			listDeposito = new ArrayList<Deposito>();
			listDeposito = depositoEJB.findAllActivo();
			//Cargando Listas de Orden de Compra Activos
			listOrdenCompraConfirmado = ordenCompraEJB.findAllConfirmadoPendiente();		
			deposito = new Deposito();
			fechaVencimientoProducto = new Date();
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

	public void confirmarOrdenCompra(String user){
		if(listOrdenCompraSeleccionado.size() == 0){
			FacesContext.getCurrentInstance().addMessage("Confirmado Correctamente Orden de Compra Seleccionados", new FacesMessage("No Se ha seleccionado Orden de Compra"));
		} else {
			for (Iterator iterator = listOrdenCompraSeleccionado.iterator(); iterator.hasNext();) {
				OrdenCompra ordenCompra = (OrdenCompra) iterator.next();
				//actualizando las ordenes de compra a estado Confirmado
				ordenCompra.setEstado("Confirmado");
				ordenCompra.setFechaAprobacion(new Date());
				ordenCompra.setUsuarioAprobacion(usuarioEJB.findUserUsuario(user));
				ordenCompraEJB.update(ordenCompra);
				limpiar();
				
			}
			FacesContext.getCurrentInstance().addMessage("Confirmado Correctamente Orden de Compra Seleccionados", new FacesMessage("Orden de Compra Creado.Modificado"));
		}
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
		//Sirve para impactar en Stock y Caja por productos ingresados 
		//Verificando los valores ingresados para recepcionar Productos
		//Verificar cantidad ingresada y fecha de vencimiento
		
		//Sirve para mostrar si la orden de compra se entrega en su totalidad
		Boolean ordenCompraCompleto = true;
		Boolean permitirGuardado = true;
		Date fechaSistema = new Date();
		for (Iterator iterator = listDetOrdenCompra.iterator(); iterator.hasNext();) {
			DetalleOrdenCompra detOc = (DetalleOrdenCompra) iterator.next();
			
				if (!detOc.getCantidad().equals(detOc.getCantidadRecibida())){
					//Cantidades distintas debe guardarse como pendiente orden de compra
					ordenCompraCompleto = false;
					detOc.setEstado("PENDIENTE");
				} else {
					detOc.setEstado("ENTREGADO");
				}
				//Si la fecha es mayor a la fecha del sistema
				if (fechaVencimientoProducto.before(fechaSistema)){
					//Mostrar Mensaje de Error
					permitirGuardado = false;
					FacesContext.getCurrentInstance().addMessage("Fecha de Vencimiento Invalida", new FacesMessage("Fecha de Vencimiento Invalida."));
				}
		}
		//Si las fechas de vencimiento son mayores a la fechas actuales
		if (permitirGuardado){
			if (!ordenCompraCompleto){
				ordenCompraSeleccionado.setEstado("Pendiente");
			} else {
				//Si el orden de compra se entrego al 100% guardar como cerrado
				ordenCompraSeleccionado.setEstado("Cerrado");
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("Fecha de Vencimiento Invalida", new FacesMessage("No se Permite Recepcionar. Modificar fechas de Vencimiento"));
		}
	}
	
	
	//Dependiendo del estado del Orden de Compra cierra o modifica de diferente forma
	//Realiza impacto en stock
	public void cerrarRecepcion(){
		//Se almacenara monto de compra
		Long montoCompra = (long) 0;
		listDetOc = listDetOrdenCompra;
		//Si es de estado Cerrado debe terminar el flujo 
		//Obtener usuario Logueado
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession();
		//Si es un tipo de Orden de Compra Cerrado o pendiente
		if(ordenCompraSeleccionado.getEstado() == "Pendiente"|| ordenCompraSeleccionado.getEstado() == "Cerrado"){
			//Si es que es Pendiente se debe crear otra orden de compra con productos sobrantes
			if(ordenCompraSeleccionado.getEstado() == "Pendiente"){
				//Cerrando Orden de Compra actual
				ordenCompraSeleccionado.setEstado("Cerrado");
				ordenCompraSeleccionado.setFechaRecepcion(new Date());
				ordenCompraSeleccionado.setUsuarioRecepcion(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
				ordenCompraSeleccionado = ordenCompraEJB.update(ordenCompraSeleccionado);
				
				//Crear nueva orden de compra para guardar como pendiente
				OrdenCompra ordenCompraPendiente = new OrdenCompra();
				ordenCompraPendiente = ordenCompraSeleccionado;
				//ordenCompraPendiente.setIdOrdenCompra(null);
				ordenCompraPendiente.setFechaRecepcion(null);
				ordenCompraPendiente.setNroFacturaProveedor("");
				ordenCompraPendiente.setUsuarioRecepcion(null);
				ordenCompraPendiente.setEstado("Pendiente");
				ordenCompraEJB.create(ordenCompraPendiente);
				//Guardando Ordenes de Compra a mantener pendiente
			
				//Calculando monto de compra y lista de Detalle pendientes
				List<DetalleOrdenCompra> listDetalleOrdenCompraPendientes = new ArrayList<DetalleOrdenCompra>();
				for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
					DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
					montoCompra = detalle.getPrecioCompra()*detalle.getCantidadRecibida();
					if (detalle.getEstado().equals("PENDIENTE")){
						//Cargando Lista Detalle de Orden de Compra Pendiente
						detalle.setOrdenCompra(ordenCompraPendiente);
						detalle.setIdDetalleOrdenCompra(null);
						listDetalleOrdenCompraPendientes.add(detalle);
					}
				}
				
				//REFRESH DE CAJA
				cajaAbierta = cajaEJB.findCajaId(cajaAbierta.getIdCaja());
				//RESTAR MONTO EN CAJA
				if(cajaAbierta.getMontoCaja() > montoCompra){
					//Se puede restar monto de caja
					for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
						DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
						//Guardando en Stock Los entregados en su totalidad
						if(detalle.getEstado().equals("CERRADO")){
							StockDetalle stockDetalle = new StockDetalle();
							//Se carga datos de nuevo stock y lote
							stockDetalle.setProducto(detalle.getProducto());
							stockDetalle.setFechaIngreso(new Date());
							//Cargar fecha vencimiento
							stockDetalle.setFechaVencimiento(fechaVencimientoProducto);
							//Cargando datos de cantidad recibida y restante
							stockDetalle.setCantidadRecibida(detalle.getCantidad());
							stockDetalle.setCantidadRestante(detalle.getCantidad());
							stockDetalle.setEstado("activo");
							stockDetalle.setIdOrdenCompra(detalle.getOrdenCompra());
							String hash = "";
							
							
							//Generando Hash Lote
							RandomStringGenerator randomStringGenerator =
							        new RandomStringGenerator.Builder()
							                .withinRange('0', 'z')
							                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
							                .build();
							hash = randomStringGenerator.generate(6);
							System.out.println("HASH Generado    :" + hash.toUpperCase());
							stockDetalle.setHashLote(hash.toUpperCase());
							hash = null;
							
							
							
							//Persistir Stock Detalle
							stockDetalleEJB.create(stockDetalle);
						}
					}
					//GUARDANDO ORDEN DE COMPRA PENDIENTE
					for (Iterator iterator = listDetalleOrdenCompraPendientes.iterator(); iterator.hasNext();) {
						DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
						detOrdenCompraEJB.update(detalle);
					}
					
					//RESTAR EN CAJA DETALLE
					cajaAbierta.setMontoCaja(cajaAbierta.getMontoCaja() - montoCompra);
					cajaAbiertaDetalle = new CajaDetalle();
					cajaAbiertaDetalle.setMontoEgreso(montoCompra);
					cajaAbiertaDetalle.setNroFacturaProveedor(ordenCompraSeleccionado.getNroFacturaProveedor());
					cajaAbiertaDetalle.setCaja(cajaAbierta);
					cajaAbiertaDetalle.setFechaHora(new Date());
					//REGISTRANDO EN CAJA
					cajaEJB.update(cajaAbierta);
					cajaDetalleEJB.create(cajaAbiertaDetalle);
				} else {
					//NO SE PUEDE REALIZAR VENTA POR QUE NO EXISTE MONTO EN CAJA
					FacesContext.getCurrentInstance().addMessage("No existe monto suficiente en caja para compra", new FacesMessage("No existe monto suficiente"));
				}
			} else {
				
				
				
				
				//modificando orden de compra
				ordenCompraSeleccionado.setEstado("Cerrado");
				ordenCompraSeleccionado.setFechaRecepcion(new Date());
				System.out.println("Usuario logueado :" + (String)session.getAttribute("usuario"));
				ordenCompraSeleccionado.setUsuarioRecepcion(usuarioEJB.findUserUsuario((String)session.getAttribute("usuario")));
				ordenCompraSeleccionado = ordenCompraEJB.update(ordenCompraSeleccionado);
				for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
					DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
					//CalcuLANDO monto a Debitar en caja
					montoCompra = detalle.getPrecioCompra()*detalle.getCantidadRecibida();
					//FacesContext.getCurrentInstance().addMessage("Registrado en Stock", new FacesMessage("Registrado en Stock ingreso de producto"));
				}
				//REFRESH DE CAJA
				cajaAbierta = cajaEJB.findCajaId(cajaAbierta.getIdCaja());
				//RESTAR MONTO EN CAJA
				if(cajaAbierta.getMontoCaja() > montoCompra){
					//Se puede restar monto de caja
					try{
					//Cargar en Stock detalle
					for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
						DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
						StockDetalle stockDetalle = new StockDetalle();
						//Se carga datos de nuevo stock y lote
						stockDetalle.setProducto(detalle.getProducto());
						stockDetalle.setFechaIngreso(new Date());
						//Cargar fecha vencimiento
						stockDetalle.setFechaVencimiento(fechaVencimientoProducto);
						//Cargando datos de cantidad recibida y restante
						stockDetalle.setCantidadRecibida(detalle.getCantidad());
						stockDetalle.setCantidadRestante(detalle.getCantidad());
						stockDetalle.setEstado("activo");
						stockDetalle.setIdOrdenCompra(detalle.getOrdenCompra());
						String hash = "";
						
						
						//Generando Hash Lote
						RandomStringGenerator randomStringGenerator =
						        new RandomStringGenerator.Builder()
						                .withinRange('0', 'z')
						                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
						                .build();
						hash = randomStringGenerator.generate(6);
						System.out.println("HASH Generado    :" + hash.toUpperCase());
						stockDetalle.setHashLote(hash.toUpperCase());
						hash = null;
						
						
						
						//Persistir Stock Detalle
						stockDetalleEJB.create(stockDetalle);
					}
				
				//RESTAR EN CAJA DETALLE
				cajaAbierta.setMontoCaja(cajaAbierta.getMontoCaja() - montoCompra);
				cajaAbiertaDetalle = new CajaDetalle();
				cajaAbiertaDetalle.setMontoEgreso(montoCompra);
				cajaAbiertaDetalle.setNroFacturaProveedor(ordenCompraSeleccionado.getNroFacturaProveedor());
				cajaAbiertaDetalle.setCaja(cajaAbierta);
				cajaAbiertaDetalle.setFechaHora(new Date());
				//REGISTRANDO EN CAJA
				cajaEJB.update(cajaAbierta);
				cajaDetalleEJB.create(cajaAbiertaDetalle);
				}catch (Exception ex){
					FacesContext.getCurrentInstance().addMessage("Error al registrar en Stock", new FacesMessage("Error en Stock ingreso de producto"));
				}
			} else {
				//NO SE PUEDE REALIZAR VENTA POR QUE NO EXISTE MONTO EN CAJA
				FacesContext.getCurrentInstance().addMessage("No existe monto suficiente en caja para compra", new FacesMessage("No existe monto suficiente en Caja"));
			}
				
			
			//Persistir en Stock el detalle
			//listDetOc = listDetOrdenCompra;
			//Lista de detalle de Orden de compra que no se entregaron completamente
			//List<DetalleOrdenCompra> listDetalleAModificar = new ArrayList<DetalleOrdenCompra>();
			//List<DetalleOrdenCompra> listDetalleCerrar = new ArrayList<DetalleOrdenCompra>();
			/*for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
				DetalleOrdenCompra detalle = (DetalleOrdenCompra) iterator.next();
				//Registro de detalle no entregado en su totalidadq
				DetalleOrdenCompra detalleNoEntregado = new DetalleOrdenCompra();
				try {
					//Creando nuevo en Stock Detalle 
					StockDetalle stockDetalle = new StockDetalle();
					//Se carga datos de nuevo stock y lote
					stockDetalle.setProducto(detalle.getProducto());
					stockDetalle.setFechaIngreso(new Date());
					//Cargar fecha vencimiento
					stockDetalle.setFechaVencimiento(fechaVencimientoProducto);
					//Cargando datos de cantidad recibida y restante
					stockDetalle.setCantidadRecibida(detalle.getCantidad());
					stockDetalle.setCantidadRestante(detalle.getCantidad());
					stockDetalle.setEstado("activo");
					stockDetalle.setIdOrdenCompra(detalle.getOrdenCompra());
					String hash = "";
					
					
					//Generando Hash Lote
					RandomStringGenerator randomStringGenerator =
					        new RandomStringGenerator.Builder()
					                .withinRange('0', 'z')
					                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
					                .build();
					hash = randomStringGenerator.generate(6);
					System.out.println("HASH Generado    :" + hash.toUpperCase());
					stockDetalle.setHashLote(hash.toUpperCase());
					hash = null;
					
					
					//Persistir Stock Detalle
					stockDetalleEJB.create(stockDetalle);
					
					
					//Restando cantidad de detalle Orden de Compra
					//Si sobran productos
					//Creando DEtalle de orden de compra a Cerrar si ingresan productos
					DetalleOrdenCompra detalleOcACerrar = new DetalleOrdenCompra();
					if ((detalle.getCantidad()-detalle.getCantidadRecibida()) > 0){
						//Se copia el detalle a cerrar
						detalleOcACerrar = detalle;
						//modificando detalle a cerrar
						detalleOcACerrar.setCantidad(detalle.getCantidadRecibida());
						detalleOcACerrar.setCantidadRecibida(detalle.getCantidadRecibida());
						
						listDetalleCerrar.add(detalleOcACerrar);
					} else {
						//Detalle de producto es Cero si se entrego en su totalidad
						
						listDetalleCerrar = null;
						
						detalleNoEntregado = detalle;
						detalleNoEntregado.setCantidad((long) 0);
						detalleNoEntregado.setIdDetalleOrdenCompra(null);
						//agregando a lista de modificados
						listDetalleAModificar.add(detalleNoEntregado);
						//Borrando objeto
						detalleNoEntregado = null;
					}
					
					//Modificando detalle de Ordenes de Compra que no se entregaron en su totalidad
					System.out.println("--------//------------//------------");
					for (Iterator iterator2 = listDetalleCerrar.iterator(); iterator2.hasNext();) {
						DetalleOrdenCompra DetalleAGuardar = (DetalleOrdenCompra) iterator2.next();
						System.out.println("Detalle a Guardar ...." + DetalleAGuardar.getProducto().getMarca()+ " "+DetalleAGuardar.getProducto().getNombreProducto() + "Cantidad: "+ DetalleAGuardar.getCantidad() +"Cantidad Recibida: "+ DetalleAGuardar.getCantidadRecibida());
						detOrdenCompraEJB.update(DetalleAGuardar);
					}
					
					
				} catch(Exception ex){
					System.out.println("Error al cargar Stock detalle" + ex.getMessage() +ex.getStackTrace());
				}
				
				
				
				
				//Buscando deposito seleccionado
				deposito = depositoEJB.findIdDeposito(idDepositoSeleccionado);
				//Agregar Stock a deposito
				if (deposito != null){
					//busqueda de detalle de producto
					DetalleDeposito detalleDepositoACargar = detalleDepositoEJB.findDeposito(detalle.getProducto().getIdProducto(), deposito.getIdDeposito());
					try{
						if (detalleDepositoACargar != null){
							//Si existe detalle de deposito para ese producto
							//Suma cantidad a registro
							detalleDepositoACargar.setCantidadProducto(detalleDepositoACargar.getCantidadProducto()+detalle.getCantidad());
							//update de detalle deposito
							detalleDepositoACargar = detalleDepositoEJB.update(detalleDepositoACargar);
							
						}else {
							//si no existe se crea uno nuevo
							detalleDepositoACargar = new DetalleDeposito();
							detalleDepositoACargar.setProducto(detalle.getProducto());
							detalleDepositoACargar.setDeposito(deposito);
							detalleDepositoACargar.setCantidadProducto(detalle.getCantidad());
							
							//Create de deposito
							detalleDepositoEJB.create(detalleDepositoACargar);
						}
					}catch(Exception ex){
						System.out.println("Error al persistir Detalle deposito :" + ex.getMessage() + ex.getStackTrace());
						FacesContext.getCurrentInstance().addMessage("Error guardado deposito", new FacesMessage("Error al persistir Detalle deposito :" + ex.getMessage()));
					}
					
					FacesContext.getCurrentInstance().addMessage("Registrado en Stock", new FacesMessage("Registrado en Stock ingreso de producto"));
					
					
					
					//Persistir en Caja el importe de Compra
				
				
				
				} else {
					//Si deposito es null mostrar mensaje y no permitir recepcion
					FacesContext.getCurrentInstance().addMessage("Seleccion Deposito", new FacesMessage("Seleccionar Deposito a reponer para continuar"));
				}
				
			}*/
		}
			
		}
	}
	
	
	public Caja verificarCaja(){
		//Busca si existe una caja abierte en la fecha actual
		return cajaEJB.cajaAbiertaSinUser();
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
}

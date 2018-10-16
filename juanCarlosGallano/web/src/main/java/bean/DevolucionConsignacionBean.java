package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import ejb.DepositoEJB;
import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleFacturaEJB;
import ejb.DetallePagoContadoEJB;
import ejb.DetalleRemisionEJB;
import ejb.EmpresaEJB;
import ejb.FacturaEJB;
import ejb.FormaPagoEJB;
import ejb.MarcaTarjetaEJB;
import ejb.ProductoEJB;
import ejb.PuestoVentaEJB;
import ejb.RemisionEJB;
import ejb.RucPersonasEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.CajaDetalle;
import entities.Deposito;
import entities.DetalleFactura;
import entities.DetallePagoContado;
import entities.DetalleRemision;
import entities.Factura;
import entities.FormaPago;
import entities.MarcaTarjeta;
import entities.PuestoVenta;
import entities.Remision;
import entities.Stock;
import entities.StockDetalle;
import entities.Usuario;

@ManagedBean (name = "devolucionConsignacionBean")
@ViewScoped
public class DevolucionConsignacionBean {
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	PuestoVentaEJB puestoVentaEJB;
	
	@EJB
	DetalleCuotasEJB detalleCuotaEJB;
	
	@EJB
	EmpresaEJB sucursalEJB;
	
	@EJB
	StockEJB stockEJB;
	
	@EJB
	StockDetalleEJB stockDetalleEJB;
	
	@EJB
	DetalleDepositoEJB detalleDepositoEJB;
	
	@EJB
	FacturaEJB facturaEJB;
	
	
	@EJB
	DetalleFacturaEJB detalleFacturaEJB;
	
	@Inject
	UsuarioBean usuarioBean;
	
	@Inject
	ReportBean report;
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	RucPersonasEJB rucPersonasEJB;
	
	@EJB
	CajaDetalleEJB detCajaEJB;
	
	@EJB
	DetallePagoContadoEJB detPagoContadoEJB;
	@EJB
	FormaPagoEJB formaPagoEJB;
	
	@EJB
	MarcaTarjetaEJB marcaTarjetaEJB;
	
	@EJB
	RemisionEJB remisionEJB;
	
	@EJB
	DetalleRemisionEJB detalleRemisionEJB;
	
	@EJB
	DepositoEJB depositoEJB;
	
	private List<Remision> listRemisiones;
	private Remision remisionSeleccionada;
	private List<FormaPago> listFormaPago;
	private FormaPago formaPagoSeleccionado;
	private Long idFormaPagoSeleccionado;
	private List<MarcaTarjeta> listMarcaTarjetas;
	private Long idMarcaTarjeta;
	
	private Caja cajaUsuario;
	
	private List<DetalleRemision> listDetalleRemision;
	private DetalleRemision detRemision;

	private Boolean entregadoTotalidad;
	private Long montoTotalConsignacion;
	//para guardar el montoTotal
	private Long montoTemporalConsignacion;
	private Long montoTotalIva10;
	private Long montoTotalIva5;
	private Long montoTotalsinIva;
	private String numeroTarjeta;
	private String numeroVoucher;
	private Usuario usuarioLogueado;
	private List<DetallePagoContado> listDetPagoContado;
	private DetallePagoContado detPagoSelect;
	
	private String numeroFactura;
	private Long pagoRecibido;
	private Long vuelto;

	public List<Remision> getListRemisiones() {
		return listRemisiones;
	}



	public Long getMontoTotalIva10() {
		return montoTotalIva10;
	}



	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}



	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}



	public Long getMontoTemporalConsignacion() {
		return montoTemporalConsignacion;
	}



	public void setMontoTemporalConsignacion(Long montoTemporalConsignacion) {
		this.montoTemporalConsignacion = montoTemporalConsignacion;
	}



	public List<MarcaTarjeta> getListMarcaTarjetas() {
		return listMarcaTarjetas;
	}



	public void setListMarcaTarjetas(List<MarcaTarjeta> listMarcaTarjetas) {
		this.listMarcaTarjetas = listMarcaTarjetas;
	}



	public Long getIdMarcaTarjeta() {
		return idMarcaTarjeta;
	}



	public void setIdMarcaTarjeta(Long idMarcaTarjeta) {
		this.idMarcaTarjeta = idMarcaTarjeta;
	}



	public Long getIdFormaPagoSeleccionado() {
		return idFormaPagoSeleccionado;
	}



	public Caja getCajaUsuario() {
		return cajaUsuario;
	}



	public void setCajaUsuario(Caja cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}



	public List<DetallePagoContado> getListDetPagoContado() {
		return listDetPagoContado;
	}



	public void setListDetPagoContado(List<DetallePagoContado> listDetPagoContado) {
		this.listDetPagoContado = listDetPagoContado;
	}



	public DetallePagoContado getDetPagoSelect() {
		return detPagoSelect;
	}



	public void setDetPagoSelect(DetallePagoContado detPagoSelect) {
		this.detPagoSelect = detPagoSelect;
	}



	public Long getPagoRecibido() {
		return pagoRecibido;
	}



	public void setPagoRecibido(Long pagoRecibido) {
		this.pagoRecibido = pagoRecibido;
	}



	public Long getVuelto() {
		return vuelto;
	}



	public void setVuelto(Long vuelto) {
		this.vuelto = vuelto;
	}



	public void setIdFormaPagoSeleccionado(Long idFormaPagoSeleccionado) {
		this.idFormaPagoSeleccionado = idFormaPagoSeleccionado;
	}



	public void setMontoTotalIva10(Long montoTotalIva10) {
		this.montoTotalIva10 = montoTotalIva10;
	}



	public FormaPago getFormaPagoSeleccionado() {
		return formaPagoSeleccionado;
	}



	public void setFormaPagoSeleccionado(FormaPago formaPagoSeleccionado) {
		this.formaPagoSeleccionado = formaPagoSeleccionado;
	}



	public Long getMontoTotalIva5() {
		return montoTotalIva5;
	}



	public void setMontoTotalIva5(Long montoTotalIva5) {
		this.montoTotalIva5 = montoTotalIva5;
	}



	public Long getMontoTotalsinIva() {
		return montoTotalsinIva;
	}



	public List<FormaPago> getListFormaPago() {
		return listFormaPago;
	}



	public void setListFormaPago(List<FormaPago> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}



	public void setMontoTotalsinIva(Long montoTotalsinIva) {
		this.montoTotalsinIva = montoTotalsinIva;
	}



	public void setListRemisiones(List<Remision> listRemisiones) {
		this.listRemisiones = listRemisiones;
	}



	public Remision getRemisionSeleccionada() {
		return remisionSeleccionada;
	}



	public void setRemisionSeleccionada(Remision remisionSeleccionada) {
		this.remisionSeleccionada = remisionSeleccionada;
	}



	public List<DetalleRemision> getListDetalleRemision() {
		return listDetalleRemision;
	}



	public void setListDetalleRemision(List<DetalleRemision> listDetalleRemision) {
		this.listDetalleRemision = listDetalleRemision;
	}



	public String getNumeroFactura() {
		return numeroFactura;
	}



	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}



	public Boolean getEntregadoTotalidad() {
		return entregadoTotalidad;
	}

	

	public Long getMontoTotalConsignacion() {
		return montoTotalConsignacion;
	}



	public void setMontoTotalConsignacion(Long montoTotalConsignacion) {
		this.montoTotalConsignacion = montoTotalConsignacion;
	}



	public void setEntregadoTotalidad(Boolean entregadoTotalidad) {
		this.entregadoTotalidad = entregadoTotalidad;
	}



	public DetalleRemision getDetRemision() {
		return detRemision;
	}



	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}



	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}



	public String getNumeroVoucher() {
		return numeroVoucher;
	}



	public void setNumeroVoucher(String numeroVoucher) {
		this.numeroVoucher = numeroVoucher;
	}



	public void setDetRemision(DetalleRemision detRemision) {
		this.detRemision = detRemision;
	}



	public void init(){
		String user;
		try {
			user = usuarioBean.usuarioLogueado();
		
			usuarioLogueado = usuarioEJB.findUserUsuario(user);
			cajaUsuario = cajaEJB.cajaAbierta(usuarioLogueado);
			listRemisiones = remisionEJB.findAllActivo();
			
			if (cajaUsuario != null || cajaUsuario.getPuestoVenta().getCajaTesoreria()){
			
				if (!FacesContext.getCurrentInstance().isPostback()){
					entregadoTotalidad = false;
					montoTotalConsignacion = (long) 0;
					montoTotalIva10 = 0l;
					montoTotalIva5 = 0l;
					montoTotalsinIva = 0l;
					montoTemporalConsignacion = 0l;
					listFormaPago = formaPagoEJB.findAll();
					listMarcaTarjetas = marcaTarjetaEJB.findAll();
					numeroTarjeta = "";
					numeroVoucher = "";
					numeroFactura = "";
					listDetPagoContado = new ArrayList<DetallePagoContado>();
					
				}
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuario no posee Caja Abierta. o es de Tipo Tesoreria.", null);
				facesContext.addMessage("Usuario no posee Caja Abierta o es de Tipo Tesoreria.", facesMessage);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Ingresar.", null);
			facesContext.addMessage("Error al Ingresar.", facesMessage);
		}
		
	}
	
	public void cargarDetalleRemision(){
		//cargar datos de detalle remision
		if(remisionSeleccionada != null){
			listDetalleRemision = detalleRemisionEJB.findIdRemision(remisionSeleccionada.getIdRemision());
			for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
				DetalleRemision detalleRemision = (DetalleRemision) iterator.next();
				detalleRemision.setCantidadRecibida((long) 0);
			}
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar Remision a entregar.", null);
			facesContext.addMessage("Seleccionar Remision a entregar.", facesMessage);
		}
		RequestContext.getCurrentInstance().update("templateForm:formDevolucionConsignacion:panelDetalleConsignacion");
	}

	public void devolverConsignacion(){
		//codigo para devolver consignacion y facturar
		//calcular el total y si se entrego en su totalidad
		entregadoTotalidad = true;
		for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
			DetalleRemision detalleRemision = (DetalleRemision) iterator.next();
			if(detalleRemision.getCantidad() > detalleRemision.getCantidadRecibida() ){
				entregadoTotalidad = false;
			} 
		}
		//Calcular Monto Total de Consignacion
		for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
			DetalleRemision detalleRemision = (DetalleRemision) iterator.next();
			//Total de Venta
			montoTotalConsignacion =+ ((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida()))*(detalleRemision.getPrecioUnitario());
			//(1+(detalleFactura.getProducto().getIvaProducto()/100))
			//Si es Exento
			if(detalleRemision.getProducto().getIvaProducto().equals(0)){
				montoTotalsinIva = montoTotalsinIva + ((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida()))*(detalleRemision.getPrecioUnitario());
			} else{
			
				montoTotalsinIva= montoTotalsinIva + redondeo((float)((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida())*(detalleRemision.getPrecioUnitario())/(1+(detalleRemision.getProducto().getIvaProducto()/100))));
				//Si posee Iva 10
				if(detalleRemision.getProducto().getIvaProducto() >= 10){
					montoTotalIva10 = montoTotalIva10 + ((detalleRemision.getCantidadRecibida())*(detalleRemision.getPrecioUnitario())-
						redondeo((float)((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida())*(detalleRemision.getPrecioUnitario())/(1+(detalleRemision.getProducto().getIvaProducto()/100)))));
				}
				if(detalleRemision.getProducto().getIvaProducto() <= 5){
					montoTotalIva5 = montoTotalIva5 + ((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida())*(detalleRemision.getPrecioUnitario())-
							redondeo((float)((detalleRemision.getCantidad() -detalleRemision.getCantidadRecibida())*(detalleRemision.getPrecioUnitario())/(1+(detalleRemision.getProducto().getIvaProducto()/100)))));
				} 
			}
			
		}
		montoTemporalConsignacion = montoTotalConsignacion;
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("templateForm:facturarConsignacion");
		requestContext.execute("PF('facturarVentaConsignacionDialog').show()");
	}
	
	
	public Long redondeo(Float num){
		long iPart =num.longValue();
		double fPart = num - iPart;
		if(fPart > 0.5){
			return (iPart+1);
		} else {
			return iPart;
		}
	}
	
	public void cargarVuelto(){
		//Si el pago es mayor al importe
		montoTemporalConsignacion = montoTotalConsignacion;
		if(pagoRecibido > 0 && pagoRecibido != null){	
		
			if(pagoRecibido >= montoTemporalConsignacion){
				vuelto = pagoRecibido-montoTemporalConsignacion;
				RequestContext.getCurrentInstance().update("templateForm:vuelto");
			} else {
				//Mostrar mensaje de importe insuficiente
				//vuelto = (long) 0;
				//pagoRecibido = (long) 0;
				//vuelto = pagoRecibido-montoTotalConsignacion;
				vuelto = 0l;
				montoTemporalConsignacion = montoTemporalConsignacion-pagoRecibido;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Adicionar Otra Forma de Pago.", null);
				facesContext.addMessage("Pago es menor a Importe.", facesMessage);
			}
			agregarDetallePago();
			pagoRecibido = (long) 0;
			idFormaPagoSeleccionado = null;
			RequestContext.getCurrentInstance().update("templateForm:panelPagoConsignacion");
			RequestContext.getCurrentInstance().update("templateForm:vuelto");
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Pago recibido debe ser mayor a 0.", null);
			facesContext.addMessage("Pago es menor a Importe.", facesMessage);
		}
	}
	
	
	public void agregarDetallePago(){
		
		detPagoSelect = new DetallePagoContado();
		if(idFormaPagoSeleccionado == null){
			idFormaPagoSeleccionado = (long) 1;
		}
		if(idFormaPagoSeleccionado == 1){
			detPagoSelect.setCuota(vuelto);
			detPagoSelect.setMonto(pagoRecibido);
			detPagoSelect.setTipoPago(formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado).getNombreFormaPago());
			//detPagoSelect.setTipoPago("Efectivo");
		} else {
			detPagoSelect.setTipoPago(formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado).getNombreFormaPago());
			detPagoSelect.setMarcaTarjeta(marcaTarjetaEJB.findIdMarcaTarjeta(idMarcaTarjeta).getMarca());
			detPagoSelect.setNumeroVoucher(numeroVoucher);
			detPagoSelect.setVuelto(0l);
			detPagoSelect.setMarcaTarjeta(numeroTarjeta);
			detPagoSelect.setMonto(pagoRecibido);
		}
		
		
		detPagoSelect.setMonto(pagoRecibido);
		listDetPagoContado.add(detPagoSelect);
		RequestContext.getCurrentInstance().update("templateForm:panelPagoConsignacion");
		detPagoSelect = new DetallePagoContado();
		FacesContext.getCurrentInstance().addMessage("Almacenado Detalle Pago.", new FacesMessage("Almacenado Detalle Pago"));
		
	}
	
	public void agregarPago(){
		remisionSeleccionada.setUsuarioCierre(usuarioLogueado);
		remisionSeleccionada.setFechaDevolucion(new Date());
		remisionEJB.update(remisionSeleccionada);
		//Crear Factura contado y cargar en caja
		Factura facturaConsignacion = new Factura();
		List<DetalleFactura> listDetalleFacturaConsignacion = new ArrayList<DetalleFactura>();
		facturaConsignacion.setCliente(remisionSeleccionada.getCliente());
		facturaConsignacion.setFecha(new Date());
		facturaConsignacion.setFechaDevolucion(new Date());
		facturaConsignacion.setMontoTotal(montoTotalConsignacion);
		facturaConsignacion.setUsuario(usuarioLogueado);
		facturaConsignacion.setIdTipoPago(1);
		buscarSiguienteNroFactura();
		facturaConsignacion.setNroFactura(numeroFactura);
		facturaEJB.create(facturaConsignacion);
		
		//Cargar Detalle PAgo contado
		long sumaPago = 0l;
		for (Iterator iterator = listDetPagoContado.iterator(); iterator.hasNext();) {
			DetallePagoContado detPagoContado = (DetallePagoContado) iterator.next();
			detPagoContado.setFactura(facturaConsignacion);
			//detPagoContado.setTipoPago(formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado).getNombreFormaPago());
			sumaPago = sumaPago +detPagoContado.getMonto();
			detPagoContadoEJB.create(detPagoContado);
			
		}
		//persistir detalle de factura
		
		for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
			DetalleRemision detRemision = (DetalleRemision) iterator.next();
			DetalleFactura detFacturaConsignacion = new DetalleFactura();
			detFacturaConsignacion.setCantidad(detRemision.getCantidadRecibida());
			detFacturaConsignacion.setCodigoBarra(detRemision.getProducto().getCodigoBarra());
			detFacturaConsignacion.setFactura(facturaConsignacion);
			detFacturaConsignacion.setPrecioUnitario(detRemision.getPrecioUnitario());
			detFacturaConsignacion.setProducto(detRemision.getProducto());
			detFacturaConsignacion.setImporte(montoTotalConsignacion);
			detalleFacturaEJB.create(detFacturaConsignacion);
			listDetalleFacturaConsignacion.add(detFacturaConsignacion);
			
		}
		//Debe impactar Stock de los productos devueltos.
		devolucionStock();
		
		//Cargar en Caja
		cajaUsuario.setMontoCaja(cajaUsuario.getMontoCaja()+pagoRecibido);
		CajaDetalle cajaDetalleConsignacion = new CajaDetalle();
		cajaDetalleConsignacion.setCaja(cajaUsuario);
		cajaDetalleConsignacion.setFechaHora(new Date());
		cajaDetalleConsignacion.setMontoIngreso(pagoRecibido);
		buscarSiguienteNroFactura();
		cajaDetalleConsignacion.setNroFacturaProveedor(numeroFactura);
		cajaDetalleConsignacion.setObservacion("Venta Consignacion");
		FacesContext.getCurrentInstance().addMessage("Finalizado Correctamente.", new FacesMessage("Finalizado Correctamente."));
	}
	
	public void buscarSiguienteNroFactura(){
		//buscar siguiente numero de factura dependiendo el usuario logueado
		PuestoVenta puestoVentaUsuario = new PuestoVenta();
		puestoVentaUsuario = cajaUsuario.getPuestoVenta();
		if(puestoVentaUsuario != null){
			//Retornar numero de factura
			String sigNroFactura = puestoVentaUsuario.getNroSucursal()+"-"+puestoVentaUsuario.getNroPuestoVenta()+"-"+siguienteNroAsignado(puestoVentaUsuario.getNroSecuencia());
			this.numeroFactura = sigNroFactura;
		}
		
	}
	
	
	//Generar siguiente numero de factura
	public String siguienteNroAsignado(String nroSecuencia){
		
		//Leer String de numero de secuencia para generar el siguiente
		Long numero = Long.parseLong(nroSecuencia);
		numero = numero + 1;
		String nroFactura = numero.toString();
		String nroFacturaResul = "";
		//Completar con 00
		if(nroSecuencia.equals("9999998")){
			//Mensaje y evitar que se facture
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Finalizando numero de Factura.", null);
			facesContext.addMessage("Finalizando numero de Factura.", facesMessage);
		}
		for (int i = 0; i < (7-nroFactura.length()); i++) {
			nroFacturaResul = nroFacturaResul+"0";
		}
		nroFacturaResul = nroFacturaResul + nroFactura;
		return nroFacturaResul;
	}
	
	public void devolucionStock(){
		//devolver la cantidad de productos
		
		for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
			DetalleRemision detRemision = (DetalleRemision) iterator.next();
			
				//Long cantidadDevolver = detRemision.getCantidad() - detRemision.getCantidadRecibida();
				Long cantidadDevolver = detRemision.getCantidadRecibida();
				if(cantidadDevolver >0){
					//Si se debe hacer devolucion a Stock
					Stock stockproducto = stockEJB.findStockCodigoBarra(detRemision.getProducto().getCodigoBarra());
					if (stockproducto != null){
						stockproducto.setCantidadStock(stockproducto.getCantidadStock()+cantidadDevolver);
						stockproducto = stockEJB.update(stockproducto);
						//Cargar en detalle
						//Seleccionar deposito
						List<Deposito> listDepositoDisponible = depositoEJB.findAllDepositoEmpresa(usuarioLogueado.getEmpresa().getIdEmpresa());
						if (!listDepositoDisponible.isEmpty()){
							//Si halla depositos
							StockDetalle stockDetalle = new StockDetalle();
							stockDetalle.setCantidadRecibida(cantidadDevolver);
							stockDetalle.setCantidadRestante(cantidadDevolver);
							stockDetalle.setDeposito(listDepositoDisponible.get(0));
							//Agregando fecha de vencimiento igual al producto mas antiguo
							try {
							stockDetalle.setFechaVencimiento(stockDetalleEJB.findStockAntiguoVencimiento(detRemision.getProducto().getIdProducto()).getFechaVencimiento());
							} catch (Exception ex){
								//Error al buscar ultimo stock activo
								System.out.println("Error al buscar ultima fecha de vencimiento.");
								FacesContext facesContext = FacesContext.getCurrentInstance();
								FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al buscar ultima fecha de vencimiento.", null);
								facesContext.addMessage("Error al buscar ultima fecha de vencimiento.", facesMessage);
							}
							stockDetalle.setFechaIngreso(new Date());
							stockDetalle.setHashLote("");
							stockDetalle.setEstado("ACTIVO");
							//No se puede insertar  por id Orden Compra
							//stockDetalle.setIdOrdenCompra(idOrdenCompra);
							stockDetalleEJB.create(stockDetalle);
						} else {
							FacesContext facesContext = FacesContext.getCurrentInstance();
							FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Empresa no posee Depositos.", null);
							facesContext.addMessage("Empresa no posee Depositos.", facesMessage);
						}
						
						
					}
				}
		}
		agregarPago();
	}
	
	public void crearNuevaRemision(){
		Remision renovacionRemision =  new Remision();
		renovacionRemision.setCliente(remisionSeleccionada.getCliente());
		renovacionRemision.setClienteNombre(remisionSeleccionada.getCliente().getNombre());
		renovacionRemision.setDiasConsignacion(remisionSeleccionada.getDiasConsignacion());
		renovacionRemision.setFechaRemision(new Date());
		renovacionRemision.setRuc(remisionSeleccionada.getRuc());
		renovacionRemision.setUsuarioCreacion(usuarioLogueado);
		remisionEJB.create(renovacionRemision);
		
		//cargar Detalle
		for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
			DetalleRemision detRemision = (DetalleRemision) iterator.next();
			
				Long cantidadDevolver = detRemision.getCantidad() - detRemision.getCantidadRecibida();
				if(cantidadDevolver >0){
					DetalleRemision detNuevaRemision = new DetalleRemision();
					detNuevaRemision.setCantidad(cantidadDevolver);
					detNuevaRemision.setCantidadRecibida(cantidadDevolver);
					detNuevaRemision.setRemision(renovacionRemision);
					detNuevaRemision.setPrecioUnitario(detRemision.getPrecioUnitario());
					detNuevaRemision.setProducto(detRemision.getProducto());
					detalleRemisionEJB.create(detNuevaRemision);
					}
			}
		agregarPago();
		}
	
}

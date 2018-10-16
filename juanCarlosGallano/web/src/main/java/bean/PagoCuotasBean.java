package bean;

import java.io.IOException;
import java.math.BigDecimal;
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
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleFacturaEJB;
import ejb.DetallePagoContadoEJB;
import ejb.EmpresaEJB;
import ejb.FacturaEJB;
import ejb.FormaPagoEJB;
import ejb.MarcaTarjetaEJB;
import ejb.ProductoEJB;
import ejb.PuestoVentaEJB;
import ejb.RucPersonasEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.CajaDetalle;
import entities.DetalleCuotas;
import entities.DetallePagoContado;
import entities.Empresa;
import entities.Factura;
import entities.FormaPago;
import entities.MarcaTarjeta;
import entities.PuestoVenta;
import entities.Usuario;

@ManagedBean (name = "pagoCuotasBean")
@ViewScoped
public class PagoCuotasBean {
	
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
	CajaDetalleEJB cajaDetalleEJB;
	
	@EJB
	CajaDetalleEJB detalleCajaEJB;
	
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
	
	private Long idCuotaPagada;
	private String numeroSequencia;
	private String sigNumeroFactura;
	private Integer cuotaPagada;
	private Caja cajaUsuario;
	private Long idSucursal;
	private Empresa sucursalSelect;
	private Usuario usuarioVentaCredito;
	private List<Factura> listFacturaCredito;
	private List<Factura> listFacturaCreditoSeleccionada;
	private Factura facturaSeleccionada;
	private List<DetalleCuotas> listDetalleCuotasFactura;
	
	private List<MarcaTarjeta> listMarcaTarjeta;
	private List<DetallePagoContado> listDetallePagoContado;
	private String inputClienteCredito;
	private String inputRucCredito;
	private BigDecimal importeMontoInteres;
	private Long vuelto;
	private Long pagoCredito;
	private List<FormaPago> listFormaPago;
	private FormaPago formaPagoSeleccionado;
	private Long idFormaPagoSeleccionado;
	private List<MarcaTarjeta> listMarcaTarjetas;
	private Long idMarcaTarjeta;
	private String numeroTarjeta;
	private String numeroVoucher;
	private String operadorTarjeta;

	private String focusProperty;
	
	public List<Factura> getListFacturaCredito() {
		return listFacturaCredito;
	}

	public FormaPago getFormaPagoSeleccionado() {
		return formaPagoSeleccionado;
	}

	public void setFormaPagoSeleccionado(FormaPago formaPagoSeleccionado) {
		this.formaPagoSeleccionado = formaPagoSeleccionado;
	}

	public List<Factura> getListFacturaCreditoSeleccionada() {
		return listFacturaCreditoSeleccionada;
	}

	

	public Integer getCuotaPagada() {
		return cuotaPagada;
	}

	public void setCuotaPagada(Integer cuotaPagada) {
		this.cuotaPagada = cuotaPagada;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	

	public Long getIdCuotaPagada() {
		return idCuotaPagada;
	}

	public void setIdCuotaPagada(Long idCuotaPagada) {
		this.idCuotaPagada = idCuotaPagada;
	}

	public Empresa getSucursalSelect() {
		return sucursalSelect;
	}

	public void setSucursalSelect(Empresa sucursalSelect) {
		this.sucursalSelect = sucursalSelect;
	}

	public String getNumeroSequencia() {
		return numeroSequencia;
	}

	public void setNumeroSequencia(String numeroSequencia) {
		this.numeroSequencia = numeroSequencia;
	}

	public String getSigNumeroFactura() {
		return sigNumeroFactura;
	}

	public void setSigNumeroFactura(String sigNumeroFactura) {
		this.sigNumeroFactura = sigNumeroFactura;
	}

	public String getFocusProperty() {
		return focusProperty;
	}

	public void setFocusProperty(String focusProperty) {
		this.focusProperty = focusProperty;
	}

	public Usuario getUsuarioVentaCredito() {
		return usuarioVentaCredito;
	}

	public void setUsuarioVentaCredito(Usuario usuarioVentaCredito) {
		this.usuarioVentaCredito = usuarioVentaCredito;
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

	public String getOperadorTarjeta() {
		return operadorTarjeta;
	}

	public void setOperadorTarjeta(String operadorTarjeta) {
		this.operadorTarjeta = operadorTarjeta;
	}

	public BigDecimal getImporteMontoInteres() {
		return importeMontoInteres;
	}

	public void setImporteMontoInteres(BigDecimal importeMontoInteres) {
		this.importeMontoInteres = importeMontoInteres;
	}

	public List<DetallePagoContado> getListDetallePagoContado() {
		return listDetallePagoContado;
	}

	public void setListDetallePagoContado(List<DetallePagoContado> listDetallePagoContado) {
		this.listDetallePagoContado = listDetallePagoContado;
	}

	public void setListFacturaCreditoSeleccionada(List<Factura> listFacturaCreditoSeleccionada) {
		this.listFacturaCreditoSeleccionada = listFacturaCreditoSeleccionada;
	}

	public void setListFacturaCredito(List<Factura> listFacturaCredito) {
		this.listFacturaCredito = listFacturaCredito;
	}

	public Long getVuelto() {
		return vuelto;
	}

	public void setVuelto(Long vuelto) {
		this.vuelto = vuelto;
	}

	public Long getPagoCredito() {
		return pagoCredito;
	}

	public void setPagoCredito(Long pagoCredito) {
		this.pagoCredito = pagoCredito;
	}

	public String getInputClienteCredito() {
		return inputClienteCredito;
	}

	public void setInputClienteCredito(String inputClienteCredito) {
		this.inputClienteCredito = inputClienteCredito;
	}

	public String getInputRucCredito() {
		return inputRucCredito;
	}

	public Long getIdFormaPagoSeleccionado() {
		return idFormaPagoSeleccionado;
	}

	public List<MarcaTarjeta> getListMarcaTarjeta() {
		return listMarcaTarjeta;
	}

	public void setListMarcaTarjeta(List<MarcaTarjeta> listMarcaTarjeta) {
		this.listMarcaTarjeta = listMarcaTarjeta;
	}

	public void setIdFormaPagoSeleccionado(Long idFormaPagoSeleccionado) {
		this.idFormaPagoSeleccionado = idFormaPagoSeleccionado;
	}

	public void setInputRucCredito(String inputRucCredito) {
		this.inputRucCredito = inputRucCredito;
	}

	public Factura getFacturaSeleccionada() {
		return facturaSeleccionada;
	}

	public void setFacturaSeleccionada(Factura facturaSeleccionada) {
		this.facturaSeleccionada = facturaSeleccionada;
	}

	public List<DetalleCuotas> getListDetalleCuotasFactura() {
		return listDetalleCuotasFactura;
	}

	public void setListDetalleCuotasFactura(List<DetalleCuotas> listDetalleCuotasFactura) {
		this.listDetalleCuotasFactura = listDetalleCuotasFactura;
	}

	public List<FormaPago> getListFormaPago() {
		return listFormaPago;
	}

	public void setListFormaPago(List<FormaPago> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}

	public void init(){
		try{
		String usuarioLogueado = usuarioBean.usuarioLogueado();
		usuarioVentaCredito = usuarioEJB.findUserUsuario(usuarioLogueado);
		sucursalSelect = usuarioVentaCredito.getEmpresa();
		idSucursal = sucursalSelect.getIdEmpresa();
		cajaUsuario = cajaEJB.cajaAbierta(usuarioVentaCredito);
		} catch(Exception ex) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Usuario no tiene abierta una caja.", null);
			facesContext.addMessage("Usuario no tiene abierta una caja.", facesMessage);
		}
		if (!FacesContext.getCurrentInstance().isPostback()){
			cuotaPagada = 0;
			numeroSequencia = "";
			
			listDetallePagoContado = new ArrayList<DetallePagoContado>();
			focusProperty = "templateForm:selectTipoPago";
			//inicializar lista de facturas con detalle de cuotas
			listFacturaCredito = detalleCuotaEJB.findAllFacturaCredito();
			inputClienteCredito = "";
			inputRucCredito = "";
			vuelto = (long) 0;
			pagoCredito = 0l;
			listMarcaTarjeta = marcaTarjetaEJB.findAll();
		}
	}
	
	public void buscarCliente(){
		
	}

	public void cargarDetalleCuotas(){
		//cargar datos de las cuotas del cliente
		listDetalleCuotasFactura = detalleCuotaEJB.findDetalleCuotaFactura(facturaSeleccionada.getIdFactura());
		boolean pagado = true;
		if(!listDetalleCuotasFactura.isEmpty()){
			for (Iterator it  = listDetalleCuotasFactura.iterator(); it.hasNext();) {
				DetalleCuotas detalleCuotas = (DetalleCuotas) it.next();
				if(detalleCuotas.getFechaPago() == null){
					pagado = false;
				}
			}
			if (pagado){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura Completamente Pagada.", null);
				facesContext.addMessage("Factura Completamente Pagada", facesMessage);
			} else{
				RequestContext.getCurrentInstance().update("templateForm:formPagoCuotas:datosDetalleCuota");
			}
		}else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No Posee Detalle Cuotas.", null);
			facesContext.addMessage("No Posee Detalle Cuotas", facesMessage);
		}
		
		
	}
	
	public void abrirDialogPagoCuota(){
		// abrir dialog de pago para cargar detalle pago
		//cargar detalle pago con id factura
		listFormaPago = formaPagoEJB.findAll();
		formaPagoSeleccionado = formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado);
		vuelto = (long) 0;
		pagoCredito = (long) 0;
		List<DetalleCuotas> listaCuotasPedientes = detalleCuotaEJB.findAllPendiente();
		DetalleCuotas cuotaAPagar = new DetalleCuotas();
		if(!listaCuotasPedientes.isEmpty()){
			// cargando cuota a pagar
			cuotaAPagar = listaCuotasPedientes.get(0);
			importeMontoInteres = cuotaAPagar.getMontoPago();
		}
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('facturarCreditoDialog').show()");
		focusProperty = "templateForm:selectTipoPago";
		requestContext.update("templateForm:formPagoCuotas:focusID");
		requestContext.update("templateForm:panelPagoConsignacion");
		
	}
	
	public void cargarVuelto(){
		//Si el pago es mayor al importe
		if(listDetallePagoContado.isEmpty()){
			if(pagoCredito > 0 && pagoCredito != null){	
			
				if(pagoCredito >= importeMontoInteres.longValue()){
					vuelto = pagoCredito-importeMontoInteres.longValue();
					RequestContext.getCurrentInstance().update("templateForm:vuelto");
				} else {
					//Mostrar mensaje de importe insuficiente
					vuelto = pagoCredito-importeMontoInteres.longValue();
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Adicionar Otra Forma de Pago.", null);
					facesContext.addMessage("Pago es menor a Importe.", facesMessage);
				}
				agregarDetallePago();
				
				//vuelto = (long) 0;
				//pagoCredito = (long) 0;
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Pago recibido debe ser mayor a 0.", null);
				facesContext.addMessage("Pago es menor a Importe.", facesMessage);
			}
		
		} else {
			//si la lista no esta vacia
			if(pagoCredito > 0 && pagoCredito != null){	
				//recorrer detalleCuotas
				Long sumPagos = 0l;
				for (Iterator iterator = listDetallePagoContado.iterator(); iterator.hasNext();) {
					DetalleCuotas detPagoContadoActual = (DetalleCuotas) iterator.next();
					sumPagos = sumPagos + detPagoContadoActual.getMontoPago().longValue();
				}
				if(sumPagos< importeMontoInteres.longValue()){
					vuelto = pagoCredito-importeMontoInteres.longValue();
					RequestContext.getCurrentInstance().update("templateForm:vuelto");
				} else{
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se necesita Adicionar Otra Forma de Pago.", null);
					facesContext.addMessage("No se necesita Adicionar Otra Forma de Pago.", facesMessage);
				}
			}
			else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Pago recibido debe ser mayor a 0.", null);
				facesContext.addMessage("Pago es menor a Importe.", facesMessage);
			}
		}
	}
	
	public void agregarPago(){
		
		//Modificar Detalle Pago dependiendo de
		boolean pagar = true;
		for (Iterator iterator = listDetalleCuotasFactura.iterator(); iterator.hasNext();) {
			DetalleCuotas detalleCuotaPagar = (DetalleCuotas) iterator.next();
			//Recorriendo para pagar cuota
			if(detalleCuotaPagar.getFechaPago() == null && pagar){
				if(cuotaPagada == 0){
					cuotaPagada=1;
				}
				detalleCuotaPagar.setFechaPago(new Date());
				pagar = false;
				//Cargar  a Caja 
				idCuotaPagada = detalleCuotaPagar.getIdDetalleCuota();
				cajaUsuario.setMontoCaja(cajaUsuario.getMontoCaja()+(detalleCuotaPagar.getMontoPago().longValue()));
				cajaEJB.update(cajaUsuario);
				//agregar detalle caja
				CajaDetalle cajaDetalleCredito = new CajaDetalle();
				cajaDetalleCredito.setCaja(cajaUsuario);
				cajaDetalleCredito.setMontoIngreso((detalleCuotaPagar.getMontoPago().longValue()));
				cajaDetalleCredito.setFechaHora(new Date());
				//buscar numeroFactura
				buscarSiguienteNroFactura();
				cajaDetalleCredito.setNroFacturaProveedor(sigNumeroFactura);
				cajaDetalleCredito.setObservacion("PAGO CUOTA CREDITO");
				cajaDetalleEJB.update(cajaDetalleCredito);
				
				//Persistir Cuota a Pagar
				detalleCuotaEJB.update(detalleCuotaPagar);
				
			}else {
				cuotaPagada =+1;
			}
			
		}
		try {
			exportarVentaCreditoPDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		limpiar();
		
		
		
	}
	
	
	public void agregarDetallePago(){
		DetallePagoContado detPagoSelect = new DetallePagoContado();
		if(idFormaPagoSeleccionado == null){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar Tipo de Pago.", null);
			facesContext.addMessage("Seleccionar Tipo de Pago.", facesMessage);
		} else {
			if(idFormaPagoSeleccionado == 1){
				if(pagoCredito > 0){
					detPagoSelect.setCuota((long) 0);
					detPagoSelect.setMonto(pagoCredito);
					detPagoSelect.setVuelto(vuelto);
					detPagoSelect.setTipoPago(formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado).getNombreFormaPago());
					listDetallePagoContado.add(detPagoSelect);
				}
			} else {
				if(numeroVoucher != null ){
					detPagoSelect.setTipoPago(formaPagoEJB.findIdFormaPago(idFormaPagoSeleccionado).getNombreFormaPago());
					detPagoSelect.setMarcaTarjeta(marcaTarjetaEJB.findIdMarcaTarjeta(idMarcaTarjeta).getMarca());
					detPagoSelect.setNumeroVoucher(numeroVoucher);
					detPagoSelect.setMarcaTarjeta(numeroTarjeta);
					detPagoSelect.setMonto(pagoCredito);
					detPagoSelect.setVuelto(0l);
					listDetallePagoContado.add(detPagoSelect);
				} else {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Numero de Voucher es Necesario.", null);
					facesContext.addMessage("Numero de Voucher es Necesario.", facesMessage);
				}
			}
		}
		
		RequestContext.getCurrentInstance().update("templateForm:panelPago");
	}
	
	public void buscarSiguienteNroFactura(){
		//buscar siguiente numero de factura dependiendo el usuario logueado
		PuestoVenta puestoVentaUsuario = new PuestoVenta();
		puestoVentaUsuario = cajaUsuario.getPuestoVenta();
		if(puestoVentaUsuario != null){
			//Retornar numero de factura
			numeroSequencia = siguienteNroAsignado(puestoVentaUsuario.getNroSecuencia());
			sigNumeroFactura = puestoVentaUsuario.getNroSucursal()+"-"+puestoVentaUsuario.getNroPuestoVenta()+"-"+siguienteNroAsignado(puestoVentaUsuario.getNroSecuencia());
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
		if(nroSecuencia.equals("9999999")){
			//Mensaje y evitar que se facture
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Finalizando numero de Factura.Crear otro Puesto Venta.", null);
			facesContext.addMessage("Finalizando numero de Factura.Crear otro Puesto Venta", facesMessage);
		}
		for (int i = 0; i < (7-nroFactura.length()); i++) {
			nroFacturaResul = nroFacturaResul+"0";
		}
		nroFacturaResul = nroFacturaResul + nroFactura;
		return nroFacturaResul;
	}
	
	public void exportarVentaCreditoPDF() throws IOException{
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametrosVentaCredito();
		String nombreReporte = "/reportes/Pago_Cuota.jrxml";
		constructor.generarReporte(parameters, nombreReporte);
	}
	
	public Map<String, Object> obtenerParametrosVentaCredito(){
		
		String filename = "ticket_factura_credito.pdf";
		String jasperPath = "resource/reports/FacturaVenta.jasper";
		String cuotasTotal = Integer.toString(listDetalleCuotasFactura.size());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("RUC", sucursalSelect.getRuc());
		parametros.put("SUCURSAL", sucursalSelect.getNombreEmpresa());
		parametros.put("DIRECCION", sucursalSelect.getDireccion());
		parametros.put("CAJA_ID", cajaUsuario.getIdCaja().toString());
		parametros.put("CAJERO", usuarioVentaCredito.getUsuario());
		parametros.put("ID_CUOTA", (int) (long)idCuotaPagada);
		parametros.put("NRO_CUOTA_PAGAR", cuotaPagada);
		parametros.put("NRO_CUOTA_TOTAL", listDetalleCuotasFactura.size());
		return parametros;
	}
	public void limpiar(){
		listDetalleCuotasFactura = new ArrayList<DetalleCuotas>();
		pagoCredito =0l;
		vuelto = 0l;
		listDetallePagoContado = new ArrayList<DetallePagoContado>();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('facturarVentaConsignacionDialog').hide()");
		requestContext.update("templateForm:formPagoCuotas");
	}
}

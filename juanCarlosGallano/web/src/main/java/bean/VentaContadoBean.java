package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import ejb.ClienteEJB;
import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleFacturaEJB;
import ejb.DetallePagoContadoEJB;
import ejb.DetalleRemisionEJB;

//import com.googlecode.gmaps4jsf.component.map.Map;

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
import ejb.TipoDocumentoClienteProveedorEJB;
import ejb.TipoDocumentoEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.CajaDetalle;
import entities.Cliente;
import entities.Deposito;
import entities.DetalleCuotas;
import entities.DetalleDeposito;
import entities.DetalleFactura;
import entities.DetallePagoContado;
import entities.DetalleRemision;
import entities.Empresa;
import entities.Factura;
import entities.FormaPago;
import entities.MarcaTarjeta;
import entities.Producto;
import entities.PuestoVenta;
import entities.Remision;
import entities.RucPersonas;
import entities.Stock;
import entities.StockDetalle;
import entities.TipoDocumentoClienteProveedor;
import entities.Usuario;
/*
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;*/

@ManagedBean (name = "ventaContadoBean")
@ViewScoped
public class VentaContadoBean {
	
	private Factura factura;
	
	private DetalleFactura detalleFactura;
	
	private List<DetalleFactura> listDetalleFactura;
	
	private List<DetalleCuotas> listDetalleCuotas;
	
	//private DetalleFactura detalleFacturaSelect;
	
	private List<DetallePagoContado> listaPagos;
	
	private List<Producto> listProducto;
	
	private Long cantidad;
	
	private Long cantidad1;
	
	private Long precioUnitario;
	
	private Producto producto;
	
	private List<Empresa> listSucursales;
	
	private Empresa sucursalSelect;
	
	private Long idSucursal;
	
	private Date fechaVenta;
	
	private String tipoFacturacion;
	
	private String tipoPago;
	
	private String numeroFactura;
	
	private String codigoBarra;
	
	private String componentFocus;
	
	private String tipoPagoSeleccionado;
	
	private Float ivaProducto;
	
	private Caja cajaUsuario;
	
	//para Consignacion
	private Remision remision;
	
	private Date fechaVencimientoConsignacion;
	
	private List<DetalleRemision> listDetalleRemision;

	private DetalleRemision detRemision;
	private Stock stockProducto;
	
	private Boolean crearPedidoAutomatico;
	
	private String rucCliente;
	
	private String razonSocial;
	
	private List<RucPersonas> listRucPersonas;
	
	private List<RucPersonas> listSelectCliente;
	
	private List<String> listTipoPago;
	private List<String> listTipoFacturacion;
	
	//PArametro de tipo de venta 
	//puede ser contado, credito o consignacion
	private String tipoVentaParam;
	private List<FormaPago> listFormaPago;
	private FormaPago formaPagoSeleccionado;
	private Long idFormaPagoSeleccionado;
	private String numeroTarjeta;
	private String numeroVoucher;
	
	//Usuario Logueado para cargar empresa
	private String usuarioLogueado;
	private Usuario usuario;
	
	private Long importeTotal;
	private Long importeTotalIVA;
	private Long importeTotalIVAImporte;
	private Long importeTotalIVA5;
	private Long importeTotalIVA10;
	private Long importeTotalSinIva10;
	private Long importeTotalSinIva5;
	private Long importeTotalExentas;
	private Long montoTemporalContado;
	
	private Long iva;
	private Long importe;
	
	private List<DetallePagoContado> listDetPagoContado;
	private DetallePagoContado detPagoSelect;
	private List<MarcaTarjeta> listMarcaTarjetas;
	private Long idMarcaTarjeta;
	private Long pagoRecibido;
	private Long vuelto;
	
	private String cicloFacturacion;
	private String diasConsignacion;
	private Long interes;
	private Long cuotas;
	private Long montoMensualCuota;
	
	private String sequenciaFactura;
	
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
	MarcaTarjetaEJB marcaTarjetaEJB;
	
	@EJB
	RucPersonasEJB rucPersonasEJB;
	
	@EJB
	CajaDetalleEJB detCajaEJB;
	
	@EJB
	DetallePagoContadoEJB detPagoContadoEJB;
	
	@EJB
	ClienteEJB	clienteEJB;
	
	@EJB
	RemisionEJB remisionEJB;
	
	@EJB
	FormaPagoEJB formaPagoEJB;
	
	@EJB
	DetalleRemisionEJB detalleRemisionEJB;
	
	@EJB
	TipoDocumentoClienteProveedorEJB tipoDocumentoClienteEJB;
	
	@EJB
	TipoDocumentoEJB tipoDocumentoEJB;

	@Inject
	ImpactarStockBean impactarStockBean;

	public List<Producto> getListProducto() {
		listProducto = productoEJB.findAll();
		return listProducto;
	}

	public void setListProducto(List<Producto> listProducto) {
		this.listProducto = listProducto;
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

	public Long getImporteTotalSinIva10() {
		return importeTotalSinIva10;
	}

	public void setImporteTotalSinIva10(Long importeTotalSinIva10) {
		this.importeTotalSinIva10 = importeTotalSinIva10;
	}

	public Long getImporteTotalSinIva5() {
		return importeTotalSinIva5;
	}

	public void setImporteTotalSinIva5(Long importeTotalSinIva5) {
		this.importeTotalSinIva5 = importeTotalSinIva5;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public Long getImporteTotalIVA5() {
		return importeTotalIVA5;
	}

	public Long getMontoTemporalContado() {
		return montoTemporalContado;
	}

	public List<FormaPago> getListFormaPago() {
		return listFormaPago;
	}

	public void setListFormaPago(List<FormaPago> listFormaPago) {
		this.listFormaPago = listFormaPago;
	}

	public FormaPago getFormaPagoSeleccionado() {
		return formaPagoSeleccionado;
	}

	public void setFormaPagoSeleccionado(FormaPago formaPagoSeleccionado) {
		this.formaPagoSeleccionado = formaPagoSeleccionado;
	}

	public Long getIdFormaPagoSeleccionado() {
		return idFormaPagoSeleccionado;
	}

	public void setIdFormaPagoSeleccionado(Long idFormaPagoSeleccionado) {
		this.idFormaPagoSeleccionado = idFormaPagoSeleccionado;
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

	public void setMontoTemporalContado(Long montoTemporalContado) {
		this.montoTemporalContado = montoTemporalContado;
	}

	public void setImporteTotalIVA5(Long importeTotalIVA5) {
		this.importeTotalIVA5 = importeTotalIVA5;
	}

	public Long getImporteTotalIVA10() {
		return importeTotalIVA10;
	}

	public Long getImporteTotalExentas() {
		return importeTotalExentas;
	}

	public void setImporteTotalExentas(Long importeTotalExentas) {
		this.importeTotalExentas = importeTotalExentas;
	}

	public void setImporteTotalIVA10(Long importeTotalIVA10) {
		this.importeTotalIVA10 = importeTotalIVA10;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getSequenciaFactura() {
		return sequenciaFactura;
	}

	public void setSequenciaFactura(String sequenciaFactura) {
		this.sequenciaFactura = sequenciaFactura;
	}

	public Long getCantidad1() {
		return cantidad1;
	}

	public void setCantidad1(Long cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Producto getProducto() {
		return producto;
	}

	public Date getFechaVencimientoConsignacion() {
		return fechaVencimientoConsignacion;
	}

	public void setFechaVencimientoConsignacion(Date fechaVencimientoConsignacion) {
		this.fechaVencimientoConsignacion = fechaVencimientoConsignacion;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getIdRemisionDetalle() {
		return detRemision.getIdRemisionDetalle();
	}

	public void setIdRemisionDetalle(Long idRemisionDetalle) {
		detRemision.setIdRemisionDetalle(idRemisionDetalle);
	}

	

	public DetalleRemision getDetRemision() {
		return detRemision;
	}

	public void setDetRemision(DetalleRemision detRemision) {
		this.detRemision = detRemision;
	}

	public Factura getFactura() {
		return factura;
	}

	public DetallePagoContado getDetPagoSelect() {
		return detPagoSelect;
	}

	public void setDetPagoSelect(DetallePagoContado detPagoSelect) {
		this.detPagoSelect = detPagoSelect;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public DetalleFactura getDetalleFactura() {
		return detalleFactura;
	}

	public void setDetalleFactura(DetalleFactura detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

	public String getDiasConsignacion() {
		return diasConsignacion;
	}

	public void setDiasConsignacion(String diasConsignacion) {
		this.diasConsignacion = diasConsignacion;
	}

	public List<DetalleFactura> getListDetalleFactura() {
		return listDetalleFactura;
	}

	public void setListDetalleFactura(List<DetalleFactura> listDetalleFactura) {
		this.listDetalleFactura = listDetalleFactura;
	}

	public List<Empresa> getListSucursales() {
		return listSucursales;
	}

	public void setListSucursales(List<Empresa> listSucursales) {
		this.listSucursales = listSucursales;
	}

	public Boolean getCrearPedidoAutomatico() {
		return crearPedidoAutomatico;
	}

	public void setCrearPedidoAutomatico(Boolean crearPedidoAutomatico) {
		this.crearPedidoAutomatico = crearPedidoAutomatico;
	}

	public Empresa getSucursalSelect() {
		return sucursalSelect;
	}

	public void setSucursalSelect(Empresa sucursalSelect) {
		this.sucursalSelect = sucursalSelect;
	}
	
	
	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	
	public String getRucCliente() {
		return rucCliente;
	}


	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}


	public List<RucPersonas> getListSelectCliente() {
		return listSelectCliente;
	}

	public void setListSelectCliente(List<RucPersonas> listSelectCliente) {
		this.listSelectCliente = listSelectCliente;
	}

	public String getTipoFacturacion() {
		return tipoFacturacion;
	}

	public void setTipoFacturacion(String tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public String getCicloFacturacion() {
		return cicloFacturacion;
	}

	public void setCicloFacturacion(String cicloFacturacion) {
		this.cicloFacturacion = cicloFacturacion;
	}


	public Long getInteres() {
		return interes;
	}

	public void setInteres(Long interes) {
		this.interes = interes;
	}

	public Long getCuotas() {
		return cuotas;
	}

	public void setCuotas(Long cuotas) {
		this.cuotas = cuotas;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public Remision getRemision() {
		return remision;
	}

	public void setRemision(Remision remision) {
		this.remision = remision;
	}

	public List<DetalleRemision> getListDetalleRemision() {
		return listDetalleRemision;
	}

	public void setListDetalleRemision(List<DetalleRemision> listDetalleRemision) {
		this.listDetalleRemision = listDetalleRemision;
	}

	public List<RucPersonas> getListRucPersonas() {
		return listRucPersonas;
	}

	public void setListRucPersonas(List<RucPersonas> listRucPersonas) {
		this.listRucPersonas = listRucPersonas;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Long getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public void init(){
		componentFocus = "templateForm:formVentaContado:codBarra";
		listTipoFacturacion = new ArrayList<String>();
		listTipoPago = new ArrayList<String>();
		listSelectCliente = new ArrayList<RucPersonas>();
		listTipoFacturacion.add("Ticket");
		listTipoFacturacion.add("Factura");
		listTipoPago.add("Efectivo");
		listTipoPago.add("Débito");
		listTipoPago.add("Crédito");
		try {
			usuarioLogueado = usuarioBean.usuarioLogueado();
			usuario = usuarioEJB.findUserUsuario(usuarioLogueado);
			sucursalSelect = usuario.getEmpresa();
			idSucursal = sucursalSelect.getIdEmpresa();
			cajaUsuario = cajaEJB.cajaAbierta(usuario);
			detPagoSelect = new DetallePagoContado();
			if (!FacesContext.getCurrentInstance().isPostback()){
				Map<String,String> params =
		                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				try{
					tipoVentaParam = params.get("paramTipoVenta");
				} catch(Exception ex){
					System.out.println(" PArametro necesario de tipo venta Nulo");
				}
				factura = new Factura();
				importeTotal = (long) 0;
				importeTotalIVA = (long) 0;
				importeTotalIVAImporte = (long) 0;
				importeTotalIVA10 = (long) 0;
				importeTotalIVA5 = (long) 0;
				importeTotalSinIva10 = (long) 0;
				importeTotalSinIva5 = (long) 0;
				importeTotalIVA5 = (long) 0;
				importeTotalExentas = (long) 0;
				precioUnitario = (long) 0;
				montoTemporalContado = 0l;
				listFormaPago = formaPagoEJB.findAll();
				listMarcaTarjetas = marcaTarjetaEJB.findAll();
				iva = (long) 0;
				importe = (long) 0;
				//DAtos a cargar para generar compra contado
				numeroFactura = "";
				sequenciaFactura ="";
				buscarSiguienteNroFactura();
				listSucursales = sucursalEJB.findAll();
				fechaVenta = new Date();
				producto = new Producto();
				listDetalleFactura = new ArrayList<DetalleFactura>();
				listDetPagoContado = new ArrayList<DetallePagoContado>();
				listDetalleCuotas = new ArrayList<DetalleCuotas>();
				crearPedidoAutomatico = false;
				
				//cuotas = (long) 1;
			}
			
			
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage("No existe Usuario", new FacesMessage("No existe Usuario."));
		}
		
	}
	

	public Caja getCajaUsuario() {
		return cajaUsuario;
	}

	public void setCajaUsuario(Caja cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//Cuando se seleccione una sucursal actualizara objeto
	public void modificadoSucursal(){
		sucursalSelect = sucursalEJB.findEmpresaId(idSucursal);
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getComponentFocus() {
		return componentFocus;
	}

	public void setComponentFocus(String componentFocus) {
		this.componentFocus = componentFocus;
	}

	
	public Float getIvaProducto() {
		return ivaProducto;
	}

	public void setIvaProducto(Float ivaProducto) {
		this.ivaProducto = ivaProducto;
	}


	public Long getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Long importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Long getImporteTotalIVA() {
		return importeTotalIVA;
	}

	public List<String> getListTipoPago() {
		return listTipoPago;
	}

	public void setListTipoPago(List<String> listTipoPago) {
		this.listTipoPago = listTipoPago;
	}

	public List<String> getListTipoFacturacion() {
		return listTipoFacturacion;
	}

	public void setListTipoFacturacion(List<String> listTipoFacturacion) {
		this.listTipoFacturacion = listTipoFacturacion;
	}

	public void setImporteTotalIVA(Long importeTotalIVA) {
		this.importeTotalIVA = importeTotalIVA;
	}

	public Long getImporteTotalIVAImporte() {
		return importeTotalIVAImporte;
	}

	public void setImporteTotalIVAImporte(Long importeTotalIVAImporte) {
		this.importeTotalIVAImporte = importeTotalIVAImporte;
	}

	public List<DetallePagoContado> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(List<DetallePagoContado> listaPagos) {
		this.listaPagos = listaPagos;
	}
	
	
	public String getTipoVentaParam() {
		return tipoVentaParam;
	}

	public void setTipoVentaParam(String tipoVentaParam) {
		this.tipoVentaParam = tipoVentaParam;
	}

	public Long getPagoRecibido() {
		return pagoRecibido;
	}

	public List<DetalleCuotas> getListDetalleCuotas() {
		return listDetalleCuotas;
	}

	public void setListDetalleCuotas(List<DetalleCuotas> listDetalleCuotas) {
		this.listDetalleCuotas = listDetalleCuotas;
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

	public List<DetallePagoContado> getListDetPagoContado() {
		return listDetPagoContado;
	}

	public void setListDetPagoContado(List<DetallePagoContado> listDetPagoContado) {
		this.listDetPagoContado = listDetPagoContado;
	}

	public Stock getStockProducto() {
		return stockProducto;
	}

	public String getTipoPagoSeleccionado() {
		return tipoPagoSeleccionado;
	}

	public Long getMontoMensualCuota() {
		return montoMensualCuota;
	}

	public void setMontoMensualCuota(Long montoMensualCuota) {
		this.montoMensualCuota = montoMensualCuota;
	}

	public void setTipoPagoSeleccionado(String tipoPagoSeleccionado) {
		this.tipoPagoSeleccionado = tipoPagoSeleccionado;
	}

	public void setStockProducto(Stock stockProducto) {
		this.stockProducto = stockProducto;
	}

	public void cargarProducto(){
		try{
			producto = productoEJB.findProductoCodigoBarra(codigoBarra);
			//Luego de esto Verificar Stock minimo si  coinciden para realizar una venta
			ivaProducto = producto.getIvaProducto();
			precioUnitario = producto.getPrecioUnitario();
			RequestContext.getCurrentInstance().update("templateForm:formVentaContado:iva");
			RequestContext.getCurrentInstance().update("templateForm:formVentaContado:precioUnitario");
		} catch (Exception ex){
			FacesContext.getCurrentInstance().addMessage("No se ha Encontrado Producto", new FacesMessage("No se ha Encontrado Producto."));
		}
	}
	
	/*public void cargarVuelto(){
		//Si el pago es mayor al importe
		if(pagoRecibido > 0 && pagoRecibido != null){	
		
			if(pagoRecibido >= importeTotalIVAImporte){
				vuelto = pagoRecibido-importeTotalIVAImporte;
				RequestContext.getCurrentInstance().update("templateForm:vuelto");
			} else {
				//Mostrar mensaje de importe insuficiente
				//vuelto = (long) 0;
				//pagoRecibido = (long) 0;
				vuelto = pagoRecibido-importeTotalIVAImporte;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Adicionar Otra Forma de Pago.", null);
				facesContext.addMessage("Pago es menor a Importe.", facesMessage);
			}
			agregarDetallePago();
			pagoRecibido = (long) 0;
			RequestContext.getCurrentInstance().update("templateForm:panelMontoPago");
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Pago recibido debe ser mayor a 0.", null);
			facesContext.addMessage("Pago es menor a Importe.", facesMessage);
		}
	}*/
	
	public void cargarListaVentaContado(){
		//Revisar que CodBarra no haya sido cargado recientemente
		Boolean cargadoDetalle = false;
		DetalleFactura detalleFacturaCargar = new DetalleFactura();
		DetalleFactura detalleFactura = new DetalleFactura();
		Long importeProducto = (long) 0;
		Long ivaImporteProducto = (long) 0;
		//Si detalle factura no esta vacio
		if(!listDetalleFactura.isEmpty()){
			for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
				DetalleFactura detalleFacturaBuscar = (DetalleFactura) iterator.next();
				//Verificar si tiene producto Unitario
				Stock verificarStock;
				if(detalleFacturaBuscar.getProducto().getProductoUnitario() != null){
					verificarStock = stockEJB.findStockCodigoBarra(detalleFacturaBuscar.getProducto().getProductoUnitario().getCodigoBarra());
				} else {
					//Verificar si existe stock de producto
					verificarStock = stockEJB.findStockCodigoBarra(detalleFacturaBuscar.getProducto().getCodigoBarra());
				}
				//Si existe Stock de producto
				if(verificarStock != null){
					//Verificando que stock de producto sea mayor a 0
					if(verificarStock.getCantidadStock() > 0){
						if (codigoBarra.equals(detalleFacturaBuscar.getCodigoBarra())){
							//Existe en lista
							cargadoDetalle = true;
							//Se guarda Detalle para agregar
							detalleFacturaCargar = detalleFacturaBuscar;
						}
					} else {
						//Buscar Equivalente
						FacesContext.getCurrentInstance().addMessage("Producto sin existencia.", new FacesMessage("Producto sin existencia."));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage("No existe en Stock.", new FacesMessage("No existe en Stock."));
				}
			}
			//Si se cargara duplicado en lista detalle
			if (cargadoDetalle){
				for (int i = 0; i < listDetalleFactura.size(); i++) {
					//Se agrega Codigo de nueva cantidad
					if(codigoBarra.equals(listDetalleFactura.get(i).getCodigoBarra())){
						detalleFactura = listDetalleFactura.get(i);
						detalleFactura.setCantidad(detalleFactura.getCantidad()+cantidad);
						if(verificarStock(detalleFactura)){
							listDetalleFactura.set(i, detalleFactura);
							ivaImporteProducto =  redondeo((float) (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()-(detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())/(1+(detalleFactura.getProducto().getIvaProducto()/100))));
							importeProducto =  redondeo((float) ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) /(1+(detalleFactura.getProducto().getIvaProducto()/100))));
							if(detalleFactura.getProducto().getIvaProducto() >=10){
								importeTotalIVA10 =+ ivaImporteProducto;
								importeTotalSinIva10 =+ importeProducto;
							}
							if(detalleFactura.getProducto().getIvaProducto() <=5 && detalleFactura.getProducto().getIvaProducto() >0){
								importeTotalIVA5 =+ ivaImporteProducto;
								importeTotalSinIva5 =+ importeProducto;
							}
							if(detalleFactura.getProducto().getIvaProducto() ==0){
								importeTotalExentas=+ (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad());
							}
							//ivaImporteProducto =  redondeo((float) (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()-(detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())/(1+(detalleFactura.getProducto().getIvaProducto()/100))));
							//importeProducto =  redondeo((float) ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) /(1+(detalleFactura.getProducto().getIvaProducto()/100))));
							importeTotalIVAImporte =  importeProducto + ivaImporteProducto;
							importeTotal = importeProducto;
							importeTotalIVA = ivaImporteProducto;
						}
					}
				}
			} else {
				//Si no se encuentra Coincidencia se carga uno nuevo
				//Si no hay ninguno carga un item nuevo
				detalleFactura.setFactura(factura);
				detalleFactura.setCantidad(cantidad);
				detalleFactura.setProducto(producto);
				detalleFactura.setCodigoBarra(producto.getCodigoBarra());
				detalleFactura.setPrecioUnitario(producto.getPrecioUnitario());
				detalleFactura.setImporte(producto.getPrecioUnitario()*cantidad);
				if(verificarStock(detalleFactura)){
					this.listDetalleFactura.add(detalleFactura);
					//System.out.println("Resul IVA:" + ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())*(1+(detalleFactura.getProducto().getIvaProducto()/100)) - detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()));
					//System.out.println("Resul importe:"+ ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) - ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())*(1+(detalleFactura.getProducto().getIvaProducto()/100)) - detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())));
					ivaImporteProducto =  redondeo((float) (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()-(detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())/(1+(detalleFactura.getProducto().getIvaProducto()/100))));
					importeProducto =  redondeo((float) ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) /(1+(detalleFactura.getProducto().getIvaProducto()/100))));
					if(detalleFactura.getProducto().getIvaProducto() >=10){
						importeTotalIVA10 =+ ivaImporteProducto;
						importeTotalSinIva10 =+ importeProducto;
					}
					if(detalleFactura.getProducto().getIvaProducto() <=5 && detalleFactura.getProducto().getIvaProducto() >0){
						importeTotalIVA5 =+ ivaImporteProducto;
						importeTotalSinIva5 =+ importeProducto;
					}
					if(detalleFactura.getProducto().getIvaProducto() ==0){
						importeTotalExentas=+ (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad());
					}
					importeTotalIVAImporte = importeTotalIVAImporte + importeProducto + ivaImporteProducto;
					importeTotal = importeTotal + importeProducto;
					importeTotalIVA = importeTotalIVA + ivaImporteProducto;
				}
			}
		} else {
			//Si no hay ninguno carga un item nuevo
			detalleFactura.setFactura(factura);detalleFactura.getCantidad();
			detalleFactura.setCantidad(cantidad);
			detalleFactura.setProducto(producto);
			detalleFactura.setCodigoBarra(producto.getCodigoBarra());
			detalleFactura.setPrecioUnitario(producto.getPrecioUnitario());
			detalleFactura.setImporte(producto.getPrecioUnitario()*cantidad);
			if(verificarStock(detalleFactura)){
				this.listDetalleFactura.add(detalleFactura);
				//System.out.println("Resul IVA:" + ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())*(1+(detalleFactura.getProducto().getIvaProducto()/100)) - detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()));
				//System.out.println("Resul importe:"+ ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) - ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())*(1+(detalleFactura.getProducto().getIvaProducto()/100)) - detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())));
				ivaImporteProducto =  redondeo((float) (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()-(detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad())/(1+(detalleFactura.getProducto().getIvaProducto()/100))));
				importeProducto =  redondeo((float) ((detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad()) /(1+(detalleFactura.getProducto().getIvaProducto()/100))));
				if(detalleFactura.getProducto().getIvaProducto() >=10){
					importeTotalIVA10 =+ ivaImporteProducto;
					importeTotalSinIva10 =+ importeProducto;
				}
				if(detalleFactura.getProducto().getIvaProducto() <=5 && detalleFactura.getProducto().getIvaProducto() >0){
					importeTotalIVA5 =+ ivaImporteProducto;
					importeTotalSinIva5 =+ importeProducto;
				}
				if(detalleFactura.getProducto().getIvaProducto() ==0){
					importeTotalExentas=+ (detalleFactura.getPrecioUnitario()*detalleFactura.getCantidad());
				}
				importeTotalIVAImporte = importeTotalIVAImporte + importeProducto + ivaImporteProducto;
				importeTotal = importeTotal + importeProducto;
				importeTotalIVA = importeTotalIVA + ivaImporteProducto;
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Producto no se halla en Stock.", null);
				facesContext.addMessage("Producto no se halla en Stock.", facesMessage);
			}
		}
		detalleFactura = new DetalleFactura(); 
		cantidad = (long) 0;
		codigoBarra = "";
		precioUnitario = (long) 0;
		importeProducto = (long) 0;
		componentFocus = "templateForm:formVentaContado:codBarra";
		ivaImporteProducto = (long) 0;
		RequestContext.getCurrentInstance().update("templateForm:formVentaContado:panelListadoVenta");
		RequestContext.getCurrentInstance().update("templateForm:formVentaContado:panelMontoTotalVenta");
		RequestContext.getCurrentInstance().update("templateForm:formVentaContado:panelCargaProducto");
		
	}
	
	public void editarDetalleFactura(DetalleFactura fac){
		codigoBarra = fac.getCodigoBarra();
		cantidad = fac.getCantidad();
		ivaProducto = fac.getProducto().getIvaProducto();
		listDetalleFactura.remove(fac);
		importeTotalIVAImporte = importeTotalIVAImporte - fac.getImporte();
		importeTotal = (long) (importeTotal - ((fac.getPrecioUnitario()*cantidad) - (fac.getPrecioUnitario()*cantidad)*(1+(fac.getProducto().getIvaProducto()/100)) - fac.getPrecioUnitario()*cantidad));
		importeTotalIVA = (long) (importeTotalIVA - (fac.getPrecioUnitario()*cantidad)*(1+(fac.getProducto().getIvaProducto()/100)) - fac.getPrecioUnitario()*cantidad);
		if(ivaProducto == 0){
			importeTotalExentas = importeTotalExentas - (fac.getPrecioUnitario()*cantidad);
		} else {
			if(ivaProducto == 10){
				importeTotalSinIva10 =(long) (- (fac.getPrecioUnitario()*cantidad)/(1+(fac.getProducto().getIvaProducto()/100)));
				importeTotalIVA10 =(long) - (fac.getPrecioUnitario()*cantidad -(fac.getPrecioUnitario()*cantidad)/(1+(fac.getProducto().getIvaProducto()/100)));
			} else {
				importeTotalSinIva5 =(long) (- (fac.getPrecioUnitario()*cantidad)/(1+(fac.getProducto().getIvaProducto()/100)));
				importeTotalIVA5 =(long) - (fac.getPrecioUnitario()*cantidad -(fac.getPrecioUnitario()*cantidad)/(1+(fac.getProducto().getIvaProducto()/100)));
			}
		}
	}
	
	public String generarSiguienteNroFactura(){
		//Metodo que mostrarar siguiente numero de factura disponible
		//modificar numero de factura
		/*String nroFacturaResul = "";
		//Dividir numero de Factura
		String[] camposFac = new String[3];
		camposFac = nroFactura.split("-");
		//Modificando numero de factura
		Long sigNumero = Long.decode(camposFac[2])+1;
		//Cargar String
		nroFactura = sigNumero.toString();
		//Completar con 00
		for (int i = 0; i < (7-nroFactura.length()); i++) {
			nroFacturaResul = nroFacturaResul+"0";
		}
		nroFacturaResul = nroFacturaResul + nroFactura;
		//Calcular para los campos
		return camposFac[0] +"-" +camposFac[1] + "-" +nroFacturaResul;*/
		return "";
	}
	
	//Metodo para Redondear
	public double redondear(double numero)
	{
		importe = (long) (Math.rint(numero*1000)/1000);
		//importeTotal = (long) (importeTotal + Math.rint(numero*1000)/1000);
	      return Math.round(importe);
	}
	
	public double redondearIva(double numero)
	{
		iva = (long) (Math.rint(numero*1000)/1000);
		//importeTotalIVA = (long) (importeTotalIVA +Math.rint(numero*1000)/1000);
	      return Math.round(iva);
	}

	public String getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(String usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
	public void buscarSiguienteNroFactura(){
		//buscar siguiente numero de factura dependiendo el usuario logueado
		PuestoVenta puestoVentaUsuario = new PuestoVenta();
		puestoVentaUsuario = cajaUsuario.getPuestoVenta();
		if(puestoVentaUsuario != null){
			//Retornar numero de factura
			sequenciaFactura = siguienteNroAsignado(puestoVentaUsuario.getNroSecuencia());
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
	
	//Venta Contado de prueba para pedido Automatico
	public void cargarVentaContado(){
		//Verificar Lista de Detalle de para venta Contado
		for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
			DetalleFactura detalleFactura = (DetalleFactura) iterator.next();
			
			//Verificando Cantidad de producto
			
		}
		
		
		
	}
	
	//Agregar Pago por problemas en detalle de pago
	public void agregarPago(){
		Long sumaPagos = (long) 0;
		//Verificar la cantidad que se carga en detalle venta
		for (Iterator iterator = listDetPagoContado.iterator(); iterator.hasNext();) {
			DetallePagoContado detallePagoContado = (DetallePagoContado) iterator.next();
			
			//Sumando importe de venta
			
			sumaPagos = sumaPagos  + detallePagoContado.getMonto();
		}
		if(sumaPagos < importeTotalIVAImporte){
			//No permitir que proceda a pago
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Monto pago es menor a importe Venta.", null);
			facesContext.addMessage("Monto pago es menor a importe Venta.", facesMessage);
		} else {
			//debitar de stock y deposito la cantidad producto
			
			for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
				DetalleFactura detFactura = (DetalleFactura) iterator.next();
				//impactarStock();
				impactarStockBean.restarStock(detFactura.getProducto().getIdProducto(), detFactura.getCantidad());
			}
			//Sumando Importe a la caja
			cajaUsuario.setMontoCaja(cajaUsuario.getMontoCaja()+importeTotalIVAImporte);
			//Actualizando montoCaja
			cajaEJB.update(cajaUsuario);
			//Cargando detalle
			CajaDetalle detalleContado = new CajaDetalle();
			detalleContado.setCaja(cajaUsuario);
			detalleContado.setMontoIngreso(importeTotalIVAImporte);
			detalleContado.setNroFacturaProveedor(numeroFactura);
			detalleContado.setFechaHora(new Date());
			detCajaEJB.update(detalleContado);
			
			//creando nueva factura cabecera
			factura.setFecha(new Date());
			factura.setMontocobrado(vuelto);
			factura.setNroFactura(numeroFactura);
			factura.setUsuario(usuarioEJB.findUserUsuario(usuarioLogueado));
			factura.setCliente(clienteEJB.findClienteIdCliente((long) 6));
			factura.setIdTipoPago(0);
			factura.setMontoTotal(importeTotalIVAImporte);
			facturaEJB.create(factura);
			//Guardar Detalle Factura
			for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
				DetalleFactura detFactura = (DetalleFactura) iterator.next();
				detFactura.setFactura(factura);
				detalleFacturaEJB.create(detFactura);
				
			}
			//Guardando ultimo numero Factura
			PuestoVenta puestoVentaUsuario = new PuestoVenta();
			puestoVentaUsuario = cajaUsuario.getPuestoVenta();
			puestoVentaUsuario.setNroSecuencia(sequenciaFactura);
			puestoVentaUsuario = puestoVentaEJB.update(puestoVentaUsuario);
			
			//Calcular efectivo y vuelto
			for (Iterator iterator = listDetPagoContado.iterator(); iterator.hasNext();) {
				DetallePagoContado detallePagoContado2 = (DetallePagoContado) iterator.next();
				//Recorrer detalle pagos y calcular efectivo y vuelto
				pagoRecibido=+detallePagoContado2.getMonto();
				if(detallePagoContado2.getVuelto() != null){
					vuelto=+detallePagoContado2.getVuelto();
				} else {
					vuelto=+0l;
				}
			}
			
			
			RequestContext requestContext = RequestContext.getCurrentInstance();
			//requestContext.execute("PF('facturarVentaContado2Dialog').hide()");
			try {
				exportarVentaContadoPDF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error al cargar reporte de venta Contado:" + e.getMessage() +" "+ e.getStackTrace());
			}
			//requestContext.update("templateForm:formVentaContado");
			FacesContext.getCurrentInstance().addMessage("Finalizada Venta.", new FacesMessage("Finalizada Venta."));
			limpiarFacturacion();
			
			
		}
		
		
	}
	
	
	
	private void limpiarFacturacion() {
		// TODO Auto-generated method stub
		/*RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('procesarVentaDialog').hide()");
		requestContext.update("templateForm:formVentaContado");*/
		limpiar();
		
		
	}
	
	public void limpiar(){
		importe = (long) 0;
		importeTotal = 0l;
		importeTotalIVA10 = 0l;
		importeTotalIVA5 = 0l;
		importeTotalSinIva10 = 0l;
		importeTotalSinIva5 = 0l;
		importeTotalIVA = (long) 0;
		importeTotalIVAImporte = (long) 0;
		listDetalleFactura = new ArrayList<DetalleFactura>();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("templateForm:formVentaContado");
		
	}

	public void efectuarPago(){
		//debe usar el objeto Lista pago.
		//cargar en detalle de caja
		//aumentar monto de caja
		//debitar de stock la cantidad de producto
		//realizar 
	}
	
	public void actualizarDialog(){
		RequestContext.getCurrentInstance().update("templateForm:panelProcesarVenta");
		pagoRecibido = (long) 0;
		vuelto = (long) 0;
		componentFocus = "templateForm:totalRecibido";
		detPagoSelect =  new DetallePagoContado();
		RequestContext.getCurrentInstance().update("templateForm:formVentaContado:focusID");
	}
	
	public void exportarPDF() throws IOException{
		
		
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametros();
		String nombreReporte = "/reportes/FacturaVenta.jrxml";
		
		//parameters.put("idHabCaja", idCajaHabilitacion);
		
		constructor.generarReporte(parameters, nombreReporte);
		/*report.printPDF();*/
		/*//Cargando lista de productos para prueba
		List<Producto> listProductos = new ArrayList<Producto>();
		listProductos = productoEJB.findAll();
		 HashMap<String, Object> parameters = new HashMap<String, Object>();
		 for (Iterator iterator = listProductos.iterator(); iterator.hasNext();) {
			Producto producto = (Producto) iterator.next();
			 parameters.put("id_producto", producto.getIdProducto());
		}
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/prueba_A4.jasper"));
		try {
			//Verificar este punto 
			//JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, new JRBeanCollectionDataSource(listProductos));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition", "attachment;filename=jsfReporte.pdf");
			ServletOutputStream stream = response.getOutputStream();
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
			
			stream.flush();
			stream.close();
			
			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void exportarVentaContadoPDF() throws IOException{
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametrosVentaContado();
		String nombreReporte = "/reportes/FacturaVenta.jrxml";
		constructor.generarReporte(parameters, nombreReporte);
	}
	
	public void exportarVentaCreditoPDF() throws IOException{
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametrosVentaContado();
		String nombreReporte = "/reportes/Nota_Remision_2.jrxml";
		constructor.generarReporte(parameters, nombreReporte);
	}
	
	public void exportarRemisionPDF() throws IOException{
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametrosRemision();
		String nombreReporte = "/reportes/Nota_Remision_2.jrxml";
		constructor.generarReporte(parameters, nombreReporte);
	}
	
	public Map<String, Object> obtenerParametrosRemision(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id_Remision", (int) (long)remision.getIdRemision());
		
		return parametros;
		
	}
	
	public Map<String, Object> obtenerParametros(){
		String filename = "ticket_factura_contado.pdf";
		String jasperPath = "resource/reports/FacturaVenta.jasper";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("RUC", "123");
		parametros.put("SUCURSAL", "MARIANO ROQUE ALONSO");
		parametros.put("DIRECCION", "DIRECCION");
		parametros.put("DIRECCION_MATRIZ", "DIRECCION");
		parametros.put("TIMBRADO_NRO", "TIMBRADO");
		parametros.put("TIMBRADO_DESDE", "TIMBRADO DESDE");
		parametros.put("TIMBRADO_HASTA", "TIMBRADO HASTA");
		parametros.put("TIPO_VENTA", "CONTADO");
		parametros.put("CAJA_ID", " CAJA ID");
		parametros.put("CAJERO", "PUTAZO");
		parametros.put("FACTURA_FECHA", "FACTURA FECHA");
		parametros.put("FACTURA_ID", 1);
		parametros.put("FACTURA_NRO", "FACTURA NRO");
		parametros.put("IVA10", "IVA10");
		parametros.put("IVA5", "IVA5");
		parametros.put("MONTO_SIN_IVA5", "MONTO SIN IVA 5");
		parametros.put("MONTO_SIN_IVA10", "MONTO SIN IVA 10");
		parametros.put("MONTO_EXCENTOS", "MONTO EXENTOS");
		parametros.put("MONTO_PAGAR", "MONTO PAGAR");
		parametros.put("CLIENTE_RUC", "CLIENTE RUC");
		parametros.put("CLIENTE_NOMBRE", "CLIENTE NOMBRE");
		parametros.put("CLIENTE_DIR", "CLIENTE DIR");
		return parametros;
	}
	
	
	public Map<String, Object> obtenerParametrosVentaContado(){
		if(!listSelectCliente.isEmpty()){
			razonSocial = listSelectCliente.get(0).getPersona();
			rucCliente = listSelectCliente.get(0).getRuc();
		}
		String filename = "ticket_factura_contado.pdf";
		String jasperPath = "resource/reports/FacturaVenta.jasper";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("RUC", sucursalSelect.getRuc());
		parametros.put("SUCURSAL", sucursalSelect.getNombreEmpresa().trim());
		parametros.put("DIRECCION", sucursalSelect.getDireccion().trim());
		parametros.put("DIRECCION_MATRIZ", sucursalSelect.getDireccion().trim());
		parametros.put("TIMBRADO_NRO", sucursalSelect.getTimbrado().trim());
		parametros.put("TIMBRADO_DESDE", "2018-07-07"); //sucursalSelect.getInicioTimbrado().toString().trim());
		parametros.put("TIMBRADO_HASTA", sucursalSelect.getVencimientoTimbrado().toString().trim());
		parametros.put("TIPO_VENTA", "CONTADO");
		parametros.put("CAJA_ID", cajaUsuario.getIdCaja().toString().trim());
		parametros.put("CAJERO", cajaUsuario.getUsuario().getUsuario().trim());
		parametros.put("FACTURA_FECHA", factura.getFecha().toString().trim());
		parametros.put("FACTURA_ID", (int) (long)factura.getIdFactura());
		parametros.put("FACTURA_NRO", factura.getNroFactura().trim());
		parametros.put("IVA10", importeTotalIVA10.toString().trim()); // sumar iva 10 e iva 5
		parametros.put("IVA5", importeTotalIVA5.toString().trim()); // sumar iva 10 e iva 5
		parametros.put("MONTO_SIN_IVA5", importeTotalSinIva5.toString().trim()); // suma costo  iva 10
		parametros.put("MONTO_SIN_IVA10", importeTotalSinIva10.toString().trim()); //suma costo  iva 5
		parametros.put("MONTO_EXENTOS", importeTotalExentas.toString().trim()); // suma exento
		parametros.put("MONTO_PAGAR", importeTotalIVAImporte.toString().trim());
		parametros.put("CLIENTE_RUC", rucCliente.trim());
		parametros.put("CLIENTE_NOMBRE", razonSocial.trim());
		parametros.put("CLIENTE_DIR", factura.getCliente().getDireccion().trim());
		parametros.put("VUELTO", vuelto.toString().trim());
		parametros.put("EFECTIVO", pagoRecibido.toString().trim());
		return parametros;
	}
	
//Debe Cambiar por la Entidad Creada
	/*public void agregarDetallePago(){
		String idTipoVentaParam = "CONTADO";
		if(idTipoVentaParam.equals("CONTADO")){
			detPagoSelect.setCuota((long) 0);
			detPagoSelect.setVuelto(vuelto);
			//detPagoSelect.setTipoPago("Efectivo");
		}
		if(tipoPagoSeleccionado != ""){
			tipoPagoSeleccionado ="Efectivo";
			detPagoSelect.setTipoPago(tipoPagoSeleccionado);
			
		}
		
		detPagoSelect.setMonto(pagoRecibido);
		listDetPagoContado.add(detPagoSelect);
		RequestContext.getCurrentInstance().update("templateForm:panelProcesarVenta");
		//RequestContext.getCurrentInstance().update("templateForm:tablePagos");
		detPagoSelect = new DetallePagoContado();
		FacesContext.getCurrentInstance().addMessage("Almacenado Detalle Pago.", new FacesMessage("Almacenado Detalle Pago"));
		
	}*/
	
	
	//Sirve para recorrer de forma recursiva
	//Stock Detalle e ir restando de cada uno de los registros de stock y cerrar
	public void restarStockDetalle(Long idProducto, Long cantidadRestar){
		//Condicion que permitira que no sea recursivo eterno
		while (cantidadRestar>0){
		
			StockDetalle stockDet = stockDetalleEJB.findStockAntiguoVencimiento(idProducto);
			//Si es igual a la cantidad a restar
			if(stockDet.getCantidadRestante() == cantidadRestar){
				//Si cumple solo impacta en un registro 
				//estadoCerrado de Registro
				stockDet.setEstado("CERRADO");
				stockDet.setCantidadRestante((long) 0);
				stockDetalleEJB.update(stockDet);
				
				//Restar de Deposito Detalle
				DetalleDeposito detDeposito = new DetalleDeposito();
				detDeposito = detalleDepositoEJB.findDeposito(idProducto, stockDet.getDeposito().getIdDeposito());
				if (detDeposito.getCantidadProducto()>= cantidadRestar){
					detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
					detalleDepositoEJB.update(detDeposito);
				} else {
					//REvisar si no hay cantidad en deposito
				}
				
				
				break;
			}
			//Si la cantidad a restar de registro es menor
			if(stockDet.getCantidadRestante() > cantidadRestar){
				//Se mantiene activo, se modifica cantidad restante
				stockDet.setCantidadRestante((stockDet.getCantidadRestante()-cantidadRestar));
				stockDetalleEJB.update(stockDet);
				
				//Restar de Deposito Detalle
				DetalleDeposito detDeposito = new DetalleDeposito();
				detDeposito = detalleDepositoEJB.findDeposito(idProducto, stockDet.getDeposito().getIdDeposito());
				if (detDeposito.getCantidadProducto()>= cantidadRestar){
					detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
					detalleDepositoEJB.update(detDeposito);
				} else {
					//REvisar si no hay cantidad en deposito
				}
				break;
			} else{
				//Si es mayor a la cantidad restante debe recursar en otro registro
				stockDet.setEstado("CERRADO");
				stockDet.setCantidadRestante((long) 0);
				stockDetalleEJB.update(stockDet);
				//Restar de Deposito Detalle
				DetalleDeposito detDeposito = new DetalleDeposito();
				detDeposito = detalleDepositoEJB.findDeposito(stockDet.getProducto().getIdProducto(), stockDet.getDeposito().getIdDeposito());
				if (detDeposito.getCantidadProducto()>= cantidadRestar){
					detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
					detalleDepositoEJB.update(detDeposito);
				} else {
					//REvisar si no hay cantidad en deposito
				}
				cantidadRestar = cantidadRestar-stockDet.getCantidadRestante();
			}
			
		} 
	}
		//Metodo para verificar Stock de producto
		public boolean verificarStock(DetalleFactura detFactura){
			//Avisar que stock llega a minimo o 0 si se realiza la venta
			Stock stockProducto = stockEJB.findStockCodigoBarra(detFactura.getProducto().getCodigoBarra());
			//Verificar que tenga producto unitario
			if (stockProducto == null){
				if(detFactura.getProducto().getProductoUnitario() != null){
					stockProducto = stockEJB.findStockCodigoBarra(detFactura.getProducto().getProductoUnitario().getCodigoBarra());
				} else {
					return false;
				}
			}
			//Si existe Stock de producto
			if(stockProducto != null){
				//Verificar si llega a stock minimo
				if((stockProducto.getCantidadStock()-detFactura.getCantidad()) < stockProducto.getProducto().getCantidadMinima() ){
					//Verificar si es mayor a 0
					if(stockProducto.getCantidadStock()-detFactura.getCantidad()>=0){
						//Menos de STock minimo
						crearPedidoAutomatico = true;
						FacesContext.getCurrentInstance().addMessage("Stock producto por debajo del mínimo.", new FacesMessage("Stock producto por debajo del mínimo."));
						return true;
					} else {
						if(stockProducto.getCantidadStock()-detFactura.getCantidad()<0){
							FacesContext.getCurrentInstance().addMessage("Stock insuficiente para venta producto.", new FacesMessage("Stock insuficiente para venta producto."));
							return false;
						}
					}
					
				} else {
					//si sobrepasa el stock minimo permitira
					return true;
				}
			} 
			return false;
		}
		
		//Retorna el valor correcto si la parte decimal supera 0.5
		public Long redondeo(Float num){
			long iPart =num.longValue();
			double fPart = num - iPart;
			if(fPart > 0.5){
				return (iPart+1);
			} else {
				return iPart;
			}
		}
		
		public void buscarCliente(){
			//Para hacer busqueda de cliente
		}
		
		//para busqueda de cliente en ventas
		public void cargarCliente(){
			listRucPersonas = new ArrayList<RucPersonas>();
			listSelectCliente = new ArrayList<RucPersonas>();
			//Buscar personas por campo ingresado ruc
			if(rucCliente.equals("") || rucCliente == null){
				if(razonSocial.equals("") || razonSocial == null){
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cargar campos de busqueda.", null);
					facesContext.addMessage("Cargar campos de busqueda.", facesMessage);
				} else{
					listRucPersonas = rucPersonasEJB.findPorNombrePersonas(razonSocial);
				}
			} else {
				//buscar por RUC
				listRucPersonas = rucPersonasEJB.findPorRucPersonas(rucCliente);
			}
			if(listRucPersonas.isEmpty()){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se Encontro Cliente.", null);
				facesContext.addMessage("No se Encontro Cliente.", facesMessage);
			}
			RequestContext.getCurrentInstance().update("templateForm:panelGuardarCliente");
		}
		
		public void limpiarCliente(){
			rucCliente = "";
			razonSocial = "";
			listRucPersonas = new ArrayList<RucPersonas>();
			listSelectCliente = new ArrayList<RucPersonas>();
			RequestContext.getCurrentInstance().update("templateForm:panelGuardarCliente");
		}
		
		public void pagoVentaContado(){
			
			//controlar cuadros de dialogos y abrir dialog de venta
			//vierficar que se haya seleccionado un cliente
			if(listSelectCliente.size() > 1){
				//Si se selecciono mas de un cliente o ninguno
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar un solo Cliente.", null);
				facesContext.addMessage("Seleccionar un solo Cliente.", facesMessage);
			} else {
				//Si se selecciono cliente o ninguno
				if(listSelectCliente.size()==1){
					this.razonSocial = listSelectCliente.get(0).getPersona();
					this.rucCliente = listSelectCliente.get(0).getRuc();
				} else {
					if(razonSocial.equals("") || razonSocial == null){
						this.razonSocial = "SIN NOMBRE";
						this.rucCliente = "";
						//listTipoFacturacion.remove("Factura");
					} else {
						//listTipoFacturacion.remove("Ticket");
					}
				}
				RequestContext requestContext = RequestContext.getCurrentInstance();
				//requestContext.execute("PF('procesarVentaDialog').show()");
				requestContext.execute("PF('facturarVentaContado2Dialog').show()");
				requestContext.execute("PF('buscarClienteDialog').hide()");		
				//requestContext.update("templateForm:panelProcesarVenta");
				
			}
		}
		private void impactarStock(){
			for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
				DetalleFactura detFactura = (DetalleFactura) iterator.next();
				
				//buscar si posee producto unitario
				if(detFactura.getProducto().getProductoUnitario() != null){
					//Si posee producto unitario
					stockProducto = stockEJB.findStockCodigoBarra(detFactura.getProducto().getProductoUnitario().getCodigoBarra());
					if (stockProducto == null){
						FacesContext.getCurrentInstance().addMessage("No se Encontro Stock Producto", new FacesMessage("No se Encontro Stock Producto"));
					}
					//Si existe Stock de producto
					stockProducto.setCantidadStock(stockProducto.getCantidadStock()-(detFactura.getCantidad()*detFactura.getProducto().getProductoUnitario().getTipoProducto().getCantidad()));
					stockEJB.update(stockProducto);
					//Restar de Detalle de Stock
					//Cerrar Detalle de Stock en cantidad Restante
					//Restar tambien de
					restarStockDetalle(detFactura.getProducto().getProductoUnitario().getIdProducto(), (detFactura.getCantidad()*detFactura.getProducto().getProductoUnitario().getTipoProducto().getCantidad()));
					
				} else {
				
					stockProducto = stockEJB.findStockCodigoBarra(detFactura.getProducto().getCodigoBarra());
					//Si existe Stock de producto
					stockProducto.setCantidadStock(stockProducto.getCantidadStock()-detFactura.getCantidad());
					stockEJB.update(stockProducto);
					//Restar de Detalle de Stock
					//Cerrar Detalle de Stock en cantidad Restante
					//Restar tambien de
					restarStockDetalle(detFactura.getProducto().getIdProducto(), detFactura. getCantidad());
				}
				
			}
		}
		
		public void limpiarCredito(){
			listDetalleCuotas = new ArrayList<DetalleCuotas>();
			cicloFacturacion = "";
			interes = (long) 0;
			cuotas = (long) 0;
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.update("templateForm:panelCrearVentaCredito");
			//requestContext.execute("PF('VentaCreditoDialog').hide()");
		}
		
		public void crearVentaCredito(){
			
			if (!listDetalleCuotas.isEmpty()){
				for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
					DetalleFactura detFactura = (DetalleFactura) iterator.next();
					//impactarStock();
					impactarStockBean.restarStock(detFactura.getProducto().getIdProducto(), detFactura.getCantidad());
				}
				
				//creando nueva factura cabecera
				factura.setFecha(new Date());
				factura.setFechaCredito(new Date());
				factura.setMontocobrado(importeTotalIVAImporte);
				factura.setNroFactura(numeroFactura);
				factura.setUsuario(usuarioEJB.findUserUsuario(usuarioLogueado));
				factura.setCliente(clienteEJB.findClienteIdCliente((long) 6));
				factura.setIdTipoPago(0);
				factura.setMontoTotal(importeTotalIVAImporte);
				//factura.setEstado("PENDIENTE");
				facturaEJB.create(factura);
				//Guardar Detalle Factura
				for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
					DetalleFactura detFactura = (DetalleFactura) iterator.next();
					detFactura.setFactura(factura);
					detalleFacturaEJB.create(detFactura);
					
				}
				//Crear registros de venta credito
				for (Iterator iterator = listDetalleCuotas.iterator(); iterator.hasNext();) {
					//creando Detalle Cuotas
					DetalleCuotas detallecuota = (DetalleCuotas) iterator.next();
					detallecuota.setFactura(factura);
					detallecuota = detalleCuotaEJB.create(detallecuota);
				}
				limpiarCredito();
				limpiar();
				RequestContext requestContext = RequestContext.getCurrentInstance();
				requestContext.execute("PF('VentaCreditoDialog').hide()");
				FacesContext.getCurrentInstance().addMessage("Guardado Factura Credito.", new FacesMessage("Guardado Factura Credito."));
			} else {
				RequestContext requestContext = RequestContext.getCurrentInstance();
				FacesContext.getCurrentInstance().addMessage("Sin Lineas de Cuotas.", new FacesMessage("Sin Lineas de Cuotas."));
			}
		}
		
		public void cargarVentaCredito(){
			if(listSelectCliente.isEmpty()){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se Selecciono Cliente.", null);
				facesContext.addMessage("No se Selecciono Cliente.", facesMessage);
			} else {
				if(listSelectCliente.size() >1){
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar un solo Cliente.", null);
					facesContext.addMessage("Seleccionar un solo Cliente.", facesMessage);
				} else {
					this.rucCliente = listSelectCliente.get(0).getRuc();
					this.razonSocial = listSelectCliente.get(0).getPersona();
					RequestContext requestContext = RequestContext.getCurrentInstance();
					requestContext.execute("PF('buscarClienteDialog').show()");
					requestContext = RequestContext.getCurrentInstance();
					requestContext.execute("PF('buscarClienteDialog').hide()");	
					requestContext.execute("PF('VentaCreditoDialog').show()");
					
				}
			}
			
		}
		
		
		public void cargarCuotasCredito(){
			Long montoInteres;
			DetalleCuotas detCuota =  new DetalleCuotas();
			detCuota.setInteres(interes);
			//Calculo de interes y cuotas
			//cargar monto cuotas pasa interes a mensuales
			//Double multiInteres = (double) (interes/1200);
			BigDecimal p100 = new BigDecimal(100);
			BigDecimal p12 = new BigDecimal(12);
			BigDecimal pPxInteres = new BigDecimal(interes);
			BigDecimal pInteres = pPxInteres.divide(p100);
			BigDecimal pCuota = new BigDecimal(cuotas);
			BigDecimal pTiempo = pCuota.divide(p12,2, RoundingMode.HALF_UP);
			System.out.println("Valor de Tiempo REdondeado:" + pTiempo);
			BigDecimal pCapital = new BigDecimal(importeTotalIVAImporte);  
			BigDecimal pInteresSimple = new BigDecimal(0); 
			pInteresSimple = pInteres.multiply(pTiempo).multiply(pCapital);
			//montoInteres = (long) (importeTotalIVAImporte*(interes/100)*(cuotas/12));
			//montoMensualCuota = (importeTotalIVAImporte+montoInteres)/cuotas;
			BigDecimal pImporteTotalIVAImporte = new BigDecimal(importeTotalIVAImporte);
			BigDecimal pMontoMensualCuota = (pImporteTotalIVAImporte.add(pInteresSimple)).divide(pCuota);
			detCuota.setMontoPago(pMontoMensualCuota);
			for (int i = 1; i <= cuotas; i++) {
				//Cargando Todas las cuotas en detalle
				detCuota =  new DetalleCuotas();
				detCuota.setInteres(interes);
				detCuota.setMontoPago(pMontoMensualCuota);
				detCuota.setFechaVencimiento(retornarFechaInicial(i));
				listDetalleCuotas.add(detCuota);
			}
			
			RequestContext.getCurrentInstance().update("templateForm:panelDetalleCuotas");
			
		}
		
		
		public Date retornarFechaInicial(Integer cantCuota){
			//avanzar al siguiente mes y usar la fecha de ciclo
			// 5 15 u 21
			Date fechaActual = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaActual);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			month++;
			if(month !=13){
				month = month+cantCuota;
			} else {
				//Si es igual a 12 el mes pasar a anho 1
				year = year +1;
				month = 1;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString;
			if (month < 10){
				dateInString = cicloFacturacion+"-"+"0"+month+"-"+year;
			} else {
				dateInString = cicloFacturacion+"-"+month+"-"+year;
			}
			Date dateVencimiento = new Date();
			try {
				dateVencimiento = sdf.parse(dateInString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dateVencimiento;

		}
		
		
		public void cargarVentaConsignacion(){
			// CREAR REMISION Y ABRIR DIALOG
			remision = new Remision();
			listDetalleRemision = new ArrayList<DetalleRemision>();
			detRemision = new DetalleRemision();
			fechaVencimientoConsignacion = new Date();
			//RECORRER DETALLE DE FACTURA 
			if(listSelectCliente.isEmpty()){
				//No puede ser vacio cliente seleccionado
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar al menos un Cliente.", null);
				facesContext.addMessage("Seleccionar al menos un Cliente.", facesMessage);
			} else{
				if(listSelectCliente.size() == 2){
					// no puede seleccionarse mas de un cliente
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar un solo Cliente.", null);
					facesContext.addMessage("Seleccionar un solo Cliente.", facesMessage);
				} else {
					//Si se selecciono un Cliente
					for (Iterator iterator = listDetalleFactura.iterator(); iterator.hasNext();) {
						DetalleFactura detFacturaConsignacion = (DetalleFactura) iterator.next();
						detRemision.setCantidad(detFacturaConsignacion.getCantidad());
						//Si no posee producto unitario
						if(detFacturaConsignacion.getProducto().getProductoUnitario() == null){
							detRemision.setPrecioUnitario(detFacturaConsignacion.getProducto().getPrecioUnitario());
							detRemision.setProducto(detFacturaConsignacion.getProducto());
						} else {
							//Si posee producto unitario
							detRemision.setPrecioUnitario(detFacturaConsignacion.getProducto().getProductoUnitario().getPrecioUnitario());
							detRemision.setProducto(detFacturaConsignacion.getProducto().getProductoUnitario());
						}
						listDetalleRemision.add(detRemision);
					}
					remision.setClienteNombre(listSelectCliente.get(0).getPersona());
					remision.setRuc(listSelectCliente.get(0).getRuc());
					remision.setFechaRemision(new Date());
					remision.setUsuarioCreacion(usuario);
					
					// imprimir reporte de remision
					
					//ABRIR DIALOG DE CONSIGNACION
					RequestContext requestContext = RequestContext.getCurrentInstance();
					requestContext.update("templateForm:panelVentaConsignacion");
					requestContext.execute("PF('buscarClienteDialog').hide()");	
					requestContext.execute("PF('VentaConsignacionDialog').show()");
				}
			}
			
			
			//CREAR REMISION A PARTIR DE DETALLE
			
			//ABRIR CUADRO DE DIALOGO DE REMISION
			
		}
		//Metodo para cargar la fecha de devolucion maxima de consignacion
		public void cargarFechaConsignacion(){
			if(diasConsignacion != null){
			fechaVencimientoConsignacion = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaVencimientoConsignacion);
			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(diasConsignacion));
			fechaVencimientoConsignacion = cal.getTime();
			}
		}
		
		public void crearVentaConsignacion(){
			//Metodo para limpiar factura y crear remision y remision detalle y ademas limpiar la pantalla
			//crear remision.
			if(diasConsignacion != null){
				try {
					//Buscar Cliente ingresado.
					//Si no se Encuentra Crear uno nuevo
					busquedaClienteCredito();
					remision.setDiasConsignacion(Long.parseLong(diasConsignacion));
					remisionEJB.create(remision);
					//Creando Detalle Remision
					for (Iterator iterator = listDetalleRemision.iterator(); iterator.hasNext();) {
						DetalleRemision detalleGuardarRemision = (DetalleRemision) iterator.next();
						detalleGuardarRemision.setRemision(remision);
						detalleRemisionEJB.create(detalleGuardarRemision);
						
					}
					//limpiando Factura y detalle y sumatorias de venta
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Creada nueva remision de consignacion.", null);
					facesContext.addMessage("Creada nueva remision de consignacion.", facesMessage);
					//Cargar remision
					exportarRemisionPDF();
					limpiarVentaConsignacion();
				} catch (Exception ex){
					System.out.println(ex.getStackTrace());
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar Remision." + ex.getStackTrace(), null);
					facesContext.addMessage("Error al guardar Remision."+ ex.getStackTrace(), facesMessage);
				}
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Se debe Cargar Dias de Consignacion.", null);
				facesContext.addMessage("Se debe Cargar Dias de Consignacion.", facesMessage);
			}
			
		}
		
		public void limpiarVentaConsignacion(){
			//Limpiando Factura y detalle factura
			factura = new Factura();
			limpiarFacturacion();
			codigoBarra="";
			cantidad = (long) 0;
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('VentaConsignacionDialog').hide()");
			requestContext.update("templateForm:formVentaContado:panelCargaProducto");
		}
		//Realizar busqueda de clientes que se ingresaron en local
		public void busquedaClienteCredito(){
			Cliente clienteSeleccionado = new Cliente();
			try{
				clienteSeleccionado = tipoDocumentoClienteEJB.findTipoDocumentoClienteRUC(remision.getRuc()).getCliente();
			} catch (Exception ex){
				clienteSeleccionado = null;
			}
			if(clienteSeleccionado == null){
				//No encontro cliente en listado de clientes locales
				//Agregar nuevo cliente
				clienteSeleccionado = new Cliente();
				clienteSeleccionado.setNombre(remision.getClienteNombre());
				clienteSeleccionado.setApellido(remision.getClienteNombre());
				clienteSeleccionado.setEstado("ACTIVO");
				clienteEJB.create(clienteSeleccionado);
				//aGREGANDO A TIPO DE DOCUMENTO Y LA RELACION
				TipoDocumentoClienteProveedor tipoDocumento = new TipoDocumentoClienteProveedor();
				tipoDocumento.setCliente(clienteSeleccionado);
				tipoDocumento.setNumeroDocumento(remision.getRuc());
				tipoDocumento.setTipoDocumento(tipoDocumentoEJB.findTipoDocumentoRuc("RUC"));
				tipoDocumentoClienteEJB.create(tipoDocumento);
				remision.setCliente(clienteSeleccionado);
			} else {
				remision.setCliente(clienteSeleccionado);
			}
		}
		
		
		public void cargarVuelto(){
			//Si el pago es mayor al importe
			montoTemporalContado = importeTotalIVAImporte;
			if(pagoRecibido > 0 && pagoRecibido != null){	
			
				if(pagoRecibido >= montoTemporalContado){
					vuelto = pagoRecibido-montoTemporalContado;
					//RequestContext.getCurrentInstance().update("templateForm:vuelto");
				} else {
					//Mostrar mensaje de importe insuficiente
					//vuelto = (long) 0;
					//pagoRecibido = (long) 0;
					//vuelto = pagoRecibido-montoTotalConsignacion;
					vuelto = 0l;
					montoTemporalContado = montoTemporalContado-pagoRecibido;
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Adicionar Otra Forma de Pago.", null);
					facesContext.addMessage("Pago es menor a Importe.", facesMessage);
				}
				agregarDetallePago();
				pagoRecibido = (long) 0;
				idFormaPagoSeleccionado = null;
				RequestContext.getCurrentInstance().update("templateForm:panelProcesarVenta");
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
			
			
			//detPagoSelect.setMonto(pagoRecibido);
			listDetPagoContado.add(detPagoSelect);
			RequestContext.getCurrentInstance().update("templateForm:panelPago");
			detPagoSelect = new DetallePagoContado();
			FacesContext.getCurrentInstance().addMessage("Almacenado Detalle Pago.", new FacesMessage("Almacenado Detalle Pago"));
			
		}
		
		public void limpiarVentaContado(){
			//limpiando detalle cuotas
			listDetalleCuotas = new ArrayList<DetalleCuotas>();
			pagoRecibido = 0l;
			vuelto=0l;
			numeroTarjeta="";
			numeroVoucher="";
			idMarcaTarjeta = null;
			limpiar();
			
		}
}

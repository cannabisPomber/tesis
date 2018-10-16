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
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
import entities.DetalleOrdenCompra;
import entities.OrdenCompra;

@ManagedBean (name = "cajaTesoriaBean")
@ViewScoped
public class cajaTesoriaBean {

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
	
	private Boolean mostrarOrdenCompra;
	
	private List<OrdenCompra> listOrdenCompraPago;
	
	private List<DetalleOrdenCompra> listDetalleOrdenCompraPago;
	
	private Long idOrdenCompraSeleccionado;
	
	private OrdenCompra ordenCompraPagar;
	
	private Long idDetalleOrdenCompraSeleccionado;
	
	private Caja cajaSeleccionada;
	
	private CajaDetalle cajaDetalleSeleccionado;
	
	private Date fechaPago;
	
	private Long importeProveedor;
	
	private Long importeViewOrdenCompra;
	
	
	public void init(){
		
		if (!FacesContext.getCurrentInstance().isPostback()){
			listOrdenCompraPago = new ArrayList<OrdenCompra>();
			listDetalleOrdenCompraPago = new ArrayList<DetalleOrdenCompra>();
			listOrdenCompraPago = ordenCompraEJB.findAllRecepcioando();
			ordenCompraPagar = new OrdenCompra();
			importeProveedor = (long) 0;
			importeViewOrdenCompra = (long) 0;
			fechaPago = new Date();
			cajaSeleccionada = new Caja();
			cajaSeleccionada = cajaEJB.cajaAbiertaTesoreria();
			if(cajaSeleccionada.getIdCaja() == null){
				FacesContext.getCurrentInstance().addMessage("Abrir Caja de Tesoreria", new FacesMessage("Orden de Compra no hallado."));
			} else {
				//Verificar que usuario logueado sea el que abrio la caja
				try {
					if (!cajaSeleccionada.getUsuario().getUsuario().equals(usuarioBean.usuarioLogueado())){
						//si usuario de caja es igual a usuario logueado
						FacesContext.getCurrentInstance().addMessage("Usuario no pertenece a Caja Tesoreria.", new FacesMessage("Usuario no pertenece a Caja Tesoreria."));
						cajaSeleccionada = new Caja();
						System.out.println("No Existe Caja Seleccionada");
					} else {
						System.out.println("Ëxiste Caja Seleccionada: " + cajaSeleccionada.getIdCaja());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//mostrarOrdenCompra = false;
			//verificarRecepcion();
		}
	}
	

	public void verificarRecepcion(){
		//leer cada orden de Compra y Verificar que haya sido recepcionado
		Boolean recepcionado = false;
		//int posicionOrdenCompraRecepcionado;
		List<Integer> posicionOrdenCompraRecepcionado = new ArrayList<Integer>();
		for (int i = 0; i < listOrdenCompraPago.size(); i++) {
			OrdenCompra ordenCompraVerificar = listOrdenCompraPago.get(i);
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
		for (int j = 0; j < listOrdenCompraPago.size(); j++) {
			OrdenCompra ordenCompraModificar = listOrdenCompraPago.get(j);
			for (Iterator iterator = posicionOrdenCompraRecepcionado.iterator(); iterator.hasNext();) {
				Integer posicion = (Integer) iterator.next();
				//Si la Orden de Compra ha sido Recepcionada
				if(j == posicion){
					//Modificar Orden Compa
					ordenCompraModificar.setEstado("RECEPCIONADO");
					listOrdenCompraPago.set(j, ordenCompraModificar);
				}
			}
		}
	}


	public List<OrdenCompra> getListOrdenCompraPago() {
		return listOrdenCompraPago;
	}


	public void setListOrdenCompraPago(List<OrdenCompra> listOrdenCompraPago) {
		this.listOrdenCompraPago = listOrdenCompraPago;
	}


	public Long getImporteViewOrdenCompra() {
		return importeViewOrdenCompra;
	}


	public void setImporteViewOrdenCompra(Long importeViewOrdenCompra) {
		this.importeViewOrdenCompra = importeViewOrdenCompra;
	}


	public List<DetalleOrdenCompra> getListDetalleOrdenCompraPago() {
		return listDetalleOrdenCompraPago;
	}


	public void setListDetalleOrdenCompraPago(List<DetalleOrdenCompra> listDetalleOrdenCompraPago) {
		this.listDetalleOrdenCompraPago = listDetalleOrdenCompraPago;
	}


	public Long getIdOrdenCompraSeleccionado() {
		return idOrdenCompraSeleccionado;
	}


	public void setIdOrdenCompraSeleccionado(Long idOrdenCompraSeleccionado) {
		this.idOrdenCompraSeleccionado = idOrdenCompraSeleccionado;
	}


	public Long getIdDetalleOrdenCompraSeleccionado() {
		return idDetalleOrdenCompraSeleccionado;
	}


	public void setIdDetalleOrdenCompraSeleccionado(Long idDetalleOrdenCompraSeleccionado) {
		this.idDetalleOrdenCompraSeleccionado = idDetalleOrdenCompraSeleccionado;
	}


	public Caja getCajaSeleccionada() {
		return cajaSeleccionada;
	}


	public Date getFechaPago() {
		return fechaPago;
	}


	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}


	public Long getImporteProveedor() {
		return importeProveedor;
	}


	public void setImporteProveedor(Long importeProveedor) {
		this.importeProveedor = importeProveedor;
	}


	public void setCajaSeleccionada(Caja cajaSeleccionada) {
		this.cajaSeleccionada = cajaSeleccionada;
	}


	public CajaDetalle getCajaDetalleSeleccionado() {
		return cajaDetalleSeleccionado;
	}


	public void setCajaDetalleSeleccionado(CajaDetalle cajaDetalleSeleccionado) {
		this.cajaDetalleSeleccionado = cajaDetalleSeleccionado;
	}
	
	public OrdenCompra getOrdenCompraPagar() {
		return ordenCompraPagar;
	}


	public void setOrdenCompraPagar(OrdenCompra ordenCompraPagar) {
		this.ordenCompraPagar = ordenCompraPagar;
	}


	public Boolean getMostrarOrdenCompra() {
		return mostrarOrdenCompra;
	}


	public void setMostrarOrdenCompra(Boolean mostrarOrdenCompra) {
		this.mostrarOrdenCompra = mostrarOrdenCompra;
	}


	public void pagoOrdenCompra(Long idOrdenCompraPagar){
		mostrarOrdenCompra = false;
		//Buscando Orden de Compra
		this.ordenCompraPagar = ordenCompraEJB.findOrdenCompraId(idOrdenCompraPagar);
		fechaPago = new Date();
		if(cajaSeleccionada != null){
			if (ordenCompraPagar.getIdOrdenCompra() != null){
				//CArgando Orden de Compra en vista
				mostrarOrdenCompra = true;
				RequestContext.getCurrentInstance().update("templateForm:formCajaTesoreria:panelOrdenCompraPago");
			} else {
				//Error no encontro Orden De Compra
				FacesContext.getCurrentInstance().addMessage("Orden de Compra no hallado", new FacesMessage("Orden de Compra no hallado."));
			}
		} else {
			mostrarOrdenCompra = false;
			FacesContext.getCurrentInstance().addMessage("Usuario no Pertenece a tesoreria.", new FacesMessage("Usuario no Pertenece a tesoreria."));
		}
	}
	
	
	public void cargarDetalle(Long idOrdenCompraPagar){
		
		this.importeViewOrdenCompra = (long) 0;
		//Buscando Orden de Compra
				OrdenCompra ordenCompraSeleccionado = ordenCompraEJB.findOrdenCompraId(idOrdenCompraPagar);
				if (ordenCompraSeleccionado.getIdOrdenCompra() != null){
					//Cargar Lista de Detalle
					listDetalleOrdenCompraPago = new ArrayList<DetalleOrdenCompra>();
					listDetalleOrdenCompraPago = detOrdenCompraEJB.DetalleOrdenCompra(ordenCompraSeleccionado.getIdOrdenCompra());
					for (Iterator iterator = listDetalleOrdenCompraPago.iterator(); iterator.hasNext();) {
						DetalleOrdenCompra detalleOrdenCompra = (DetalleOrdenCompra) iterator.next();
						this.importeViewOrdenCompra = importeViewOrdenCompra + (detalleOrdenCompra.getCantidadRecibida()*detalleOrdenCompra.getPrecioCompra());
					}
					RequestContext.getCurrentInstance().update("templateForm:detallePedidoDialog");
					
				} else {
					
				}
	}
	
	public Long obtenerImporteOrdenCompra(){
		//Obteniendo Importe de Orden de Compra
		listDetalleOrdenCompraPago = new ArrayList<DetalleOrdenCompra>();
		Long importeOrdenCompra = (long) 0;
		listDetalleOrdenCompraPago = detOrdenCompraEJB.DetalleOrdenCompra(this.ordenCompraPagar.getIdOrdenCompra());
		for (Iterator iterator = listDetalleOrdenCompraPago.iterator(); iterator.hasNext();) {
			
			DetalleOrdenCompra detalleOrdenCompra = (DetalleOrdenCompra) iterator.next();
			importeOrdenCompra = importeOrdenCompra + (detalleOrdenCompra.getCantidadRecibida()*detalleOrdenCompra.getPrecioCompra());
			
		}
		return importeOrdenCompra;
	}
	
	//Se realiza pago a proveedor de productos
	public void pagoProveedor(){
		if(ordenCompraPagar.getNroFacturaProveedor() != null || ordenCompraPagar.getNroFacturaProveedor() != ""){
			if (importeProveedor != null){
				
				//Si se cargan todos Los datos Correctos
				//debitar de Caja Tesoreria
				Caja cajaTesoreria = cajaEJB.cajaAbiertaTesoreria();
				//RESTAR MONTO EN CAJA
				if(cajaTesoreria.getMontoCaja() >= importeProveedor){
					//importe se puede debitar de caja
					CajaDetalle cajaDetalleTesoreria = new CajaDetalle();
					cajaDetalleTesoreria.setFechaHora(new Date());
					cajaDetalleTesoreria.setMontoEgreso(importeProveedor);
					cajaDetalleTesoreria.setNroFacturaProveedor(ordenCompraPagar.getNroFacturaProveedor());
					cajaDetalleTesoreria.setCaja(cajaTesoreria);
					cajaDetalleEJB.create(cajaDetalleTesoreria);
					//Restando monto de Caja
					cajaTesoreria.setMontoCaja(cajaTesoreria.getMontoCaja()-importeProveedor);
					cajaEJB.update(cajaTesoreria);
					//Editar Orden de Compra
					ordenCompraEJB.cerrarOrdenCompraRecepcionado(ordenCompraPagar.getIdOrdenCompra());
					FacesContext.getCurrentInstance().addMessage("Debitado Correctamente.", new FacesMessage("Debitado Correctamente."));
					limpiar();
					RequestContext.getCurrentInstance().update("templateForm:formCajaTesoreria:panelTesoreriaCaja");
					RequestContext.getCurrentInstance().update("templateForm:formCajaTesoreria:panelTesoreriaPago");
					RequestContext.getCurrentInstance().update("templateForm:formCajaTesoreria:panelOrdenCompraPago");
					FacesContext.getCurrentInstance().addMessage("Debitado de Caja Tesorería.", new FacesMessage("Debitado de Caja Tesorería."));
				} else {
					FacesContext.getCurrentInstance().addMessage("Saldo Insuficiente en Tesorería.", new FacesMessage("Saldo Insuficiente en Tesorería."));
				}
				
			} else {
				FacesContext.getCurrentInstance().addMessage("Cargar Importe Proveedor.", new FacesMessage("Cargar Importe Proveedor."));
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage("Numero de Factura Inválida.", new FacesMessage("Numero de Factura Inválida."));
		}
		
	}
	
	public void limpiar(){
		listOrdenCompraPago = new ArrayList<OrdenCompra>();
		listDetalleOrdenCompraPago = new ArrayList<DetalleOrdenCompra>();
		listOrdenCompraPago = ordenCompraEJB.findAllRecepcioando();
		ordenCompraPagar = new OrdenCompra();
		importeProveedor = (long) 0;
		mostrarOrdenCompra = false;
	}
}

package bean;

import java.util.ArrayList;
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

import ejb.CajaEJB;
import ejb.DepositoEJB;
import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.EmpresaEJB;
import ejb.ProductoEJB;
import ejb.PuestoVentaEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.Deposito;
import entities.DetalleDeposito;
import entities.Empresa;
import entities.Producto;
import entities.Stock;
import entities.Usuario;

@ManagedBean (name = "controlStockManualBean")
@ViewScoped
public class ControlStockManualBean {
	
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
	CajaEJB cajaEJB;
	
	@EJB
	DepositoEJB depositoEJB;
	
	@Inject
	ImpactarStockBean impactarStockBean;
	
	@Inject
	UsuarioBean usuarioBean;
	
	private Long cantidadExistente;
	private Caja cajaUsuario;
	private Long idSucursal;
	private Empresa sucursalSelect;
	private Usuario usuarioVentaCredito;
	
	private Long idProducto;
	
	private List<Deposito> listDepositos;
	//private List<Deposito> listDepositoEmpresa;
	private List<DetalleDeposito> listDetalleDeposito;
	private List<Stock> listStockEmpresa;
	
	private HashMap<Long, Long> cantidadProductoDeposito = new HashMap<Long, Long>();
	private HashMap<Long, Long> cantidadProductoModificado = new HashMap<Long, Long>();
	public Caja getCajaUsuario() {
		return cajaUsuario;
	}


	public void setCajaUsuario(Caja cajaUsuario) {
		this.cajaUsuario = cajaUsuario;
	}


	public Long getIdSucursal() {
		return idSucursal;
	}


	public HashMap<Long, Long> getCantidadProductoDeposito() {
		return cantidadProductoDeposito;
	}


	public void setCantidadProductoDeposito(HashMap<Long, Long> cantidadProductoDeposito) {
		this.cantidadProductoDeposito = cantidadProductoDeposito;
	}


	public Long getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}


	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}


	public Empresa getSucursalSelect() {
		return sucursalSelect;
	}


	public HashMap<Long, Long> getCantidadProductoModificado() {
		return cantidadProductoModificado;
	}


	public void setCantidadProductoModificado(HashMap<Long, Long> cantidadProductoModificado) {
		this.cantidadProductoModificado = cantidadProductoModificado;
	}


	public Long getCantidadExistente() {
		return cantidadExistente;
	}


	public void setCantidadExistente(Long cantidadExistente) {
		this.cantidadExistente = cantidadExistente;
	}


	public void setSucursalSelect(Empresa sucursalSelect) {
		this.sucursalSelect = sucursalSelect;
	}


	public Usuario getUsuarioVentaCredito() {
		return usuarioVentaCredito;
	}


	public void setUsuarioVentaCredito(Usuario usuarioVentaCredito) {
		this.usuarioVentaCredito = usuarioVentaCredito;
	}


	public List<Stock> getListStockEmpresa() {
		return listStockEmpresa;
	}


	public void setListStockEmpresa(List<Stock> listStockEmpresa) {
		this.listStockEmpresa = listStockEmpresa;
	}

	
	public List<Deposito> getListDepositos() {
		return listDepositos;
	}


	public void setListDepositos(List<Deposito> listDepositos) {
		this.listDepositos = listDepositos;
	}


	public List<DetalleDeposito> getListDetalleDeposito() {
		return listDetalleDeposito;
	}


	public void setListDetalleDeposito(List<DetalleDeposito> listDetalleDeposito) {
		this.listDetalleDeposito = listDetalleDeposito;
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
				cantidadExistente = 0l;
				idProducto = 0l;
				//buscar listado de stock de las empresa y sus depositos
				listStockEmpresa = new ArrayList<Stock>();
				listDepositos = depositoEJB.findAllDepositoEmpresa(sucursalSelect.getIdEmpresa());
				if(listDepositos == null){
					//No se puede buscar productos si sucursal no esta ligada a depositos
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sucursal de usuario no tiene asignado Depositos.", null);
					facesContext.addMessage("Sucursal de usuario no tiene asiganda Depositos.", facesMessage);
				} else {
					//sumar la cantidad por deposito
					//comparar los productos con la cantidad en depositos
					for (Iterator iterator = listDepositos.iterator(); iterator.hasNext();) {
						Deposito deposito = (Deposito) iterator.next();
						//Sumar cantidad de productos por depositos
						listDetalleDeposito = detalleDepositoEJB.findDetalleDeposito(deposito.getIdDeposito());
						for (Iterator it = listDetalleDeposito.iterator(); it.hasNext();) {
							DetalleDeposito detDeposito = (DetalleDeposito) it.next();
							//Si existe
							if(cantidadProductoDeposito.containsKey(detDeposito.getProducto().getIdProducto())){
								cantidadProductoDeposito.put(detDeposito.getProducto().getIdProducto(), cantidadProductoDeposito.get(detDeposito.getProducto().getIdProducto())+detDeposito.getCantidadProducto());
							} else{
								//si no existe aun cargar Cero
								cantidadProductoDeposito.put(detDeposito.getProducto().getIdProducto(), (long) 0);
							}
								
						}
						
					}
					
				}
				//Cargar lista de Stock 
				/*for (Map.Entry<Long, Long> entry : cantidadProductoDeposito.entrySet()) {
					Stock stockSeleccionado = stockEJB.findStockCodigoBarra(productoEJB.findIdProducto(entry.getKey()).getCodigoBarra());
					
					if(stockSeleccionado != null){
						//stock encontrado en los depositos
						listStockEmpresa.add(stockSeleccionado);
					}
				}*/
				//Cargar otro Hash para modificar datos
				cantidadProductoModificado = cantidadProductoDeposito;
			}
				
		}
		//fin init
		
		
		public void ajustarStock(){
			try{
				//Debe ajustar la cabecera  de stock segun los datos ingresados
				for (Iterator iterator = listStockEmpresa.iterator(); iterator.hasNext();) {
					Stock stockModificado = (Stock) iterator.next();
					
					stockModificado.setCantidadStock(stockModificado.getCantidadExistente());
					stockEJB.update(stockModificado);
				}
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado Cambios en Stock.", null);
				facesContext.addMessage("Guardado Cambios en Stock.", facesMessage);
				limpiar();
			} catch(Exception ex){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error Cambios en Stock.", null);
				facesContext.addMessage("Error Cambios en Stock.", facesMessage);
			}
			
		}
		
		public void generarReporte(){
			
			//cantidadProductoModificado.put(idProducto.getIdStock(), cantidadExistente);
			for (Map.Entry<Long, Long> entry : cantidadProductoDeposito.entrySet()) {
				System.out.println("Valores : "+ entry.getValue());
				
			}
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado.", null);
			facesContext.addMessage("Guardado.", facesMessage);
		}
		
		public void cargarListaStock(){
			//Sirve para cargar la lista de Stock a modificar
			//Usar HashMAp para ver datos de productos agregados
			//Editar Valor de Hashmap
			//cantidadProductoModificado = cantidadProductoDeposito;
			if(idProducto != null && cantidadExistente != null){
				
				
				for (Map.Entry<Long, Long> entry : cantidadProductoModificado.entrySet()) {
					//Buscar en HasHmap si existe el prodcto
					if (entry.getKey().equals(idProducto)){
						cantidadProductoDeposito.put(entry.getKey(), cantidadExistente);
							Stock stockSeleccionado = stockEJB.findStockCodigoBarra(productoEJB.findIdProducto(entry.getKey()).getCodigoBarra());
							stockSeleccionado.setCantidadExistente(cantidadExistente);
							if(stockSeleccionado != null){
								//stock encontrado en los depositos
								listStockEmpresa.add(stockSeleccionado);
							}
					}
				}
				RequestContext.getCurrentInstance().update("templateForm:formControlStock:panelListadoStock");
			} else{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Id Producto o Cantidad Existente nulos.", null);
				facesContext.addMessage("Id Producto o Cantidad Existente nulos.", facesMessage);
			}
			
			
		}
		
		public void cargarIdProducto(){
			this.setIdProducto(idProducto);
		}
		
		public void limpiar(){
			//limpiar vista de control de stock manual
			listStockEmpresa = new ArrayList<Stock>();
			idProducto = 0l;
			cantidadExistente = 0l;
			RequestContext.getCurrentInstance().update("templateForm:formControlStock:panelListadoStock");
		}
}

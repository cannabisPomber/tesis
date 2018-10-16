package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.MarcaEJB;
import ejb.ProductoEJB;
import ejb.ProveedorEJB;
import ejb.TipoProductoEJB;
import ejb.UnidadMedidaEJB;
import entities.Marca;
import entities.Producto;
import entities.Proveedor;
import entities.TipoProducto;
import entities.UnidadMedida;

@ManagedBean (name = "editarProductoBean")
@ViewScoped
public class EditarProductoBean {
	@EJB
	ProductoEJB productoEjb;
	
	@EJB
	MarcaEJB marcaEjb;
	
	@EJB
	ProveedorEJB proveedorEjb;
	
	@EJB
	TipoProductoEJB tipoProductoEjb;
	
	@EJB
	UnidadMedidaEJB unidadMedidaEjb;
	
	private Long idProducto;
	private Producto productoEdicion;
	
	private List<Marca> marcas;
	private Long idMarca;
	private Long idTipoProducto;
	private Long idTipoUnidadMedida;
	private Long idProductoUnitario;
	private List<TipoProducto> tiposProductos;
	private List<UnidadMedida> unidadMedidaList;
	private List<Proveedor> proveedores;
	private List<Proveedor> proveedorProducto;
	//Lista de productos activos
	private List<Producto> listProductos;
	
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Producto getProductoEdicion() {
		return productoEdicion;
	}
	public void setProductoEdicion(Producto productoEdicion) {
		this.productoEdicion = productoEdicion;
	}
	
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			marcas = marcaEjb.findAllActivo();
			tiposProductos = tipoProductoEjb.findAllActivo();
			unidadMedidaList = unidadMedidaEjb.findAllActivo();
			proveedores = proveedorEjb.findAll();
			listProductos = productoEjb.findAllActivo();
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idProducto = Long.parseLong(params.get("idProducto"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idProducto != null){
				productoEdicion = productoEjb.findIdProducto(idProducto);
				idMarca = productoEdicion.getMarca().getIdMarca();
				idTipoProducto = productoEdicion.getTipoProducto().getIdTipoProducto();
				idTipoUnidadMedida = productoEdicion.getUnidadMedida().getId();
				if(productoEdicion.getProductoUnitario()!= null){
					idProductoUnitario = productoEdicion.getProductoUnitario().getIdProducto();
				} else {
					idProductoUnitario = (long) 0;
				}
			} else {
				productoEdicion = new Producto();
			}
		}
	}
	
	public void guardarProducto(){
		//Verificar duplicado de codigo de barra
		if(!verificarCodigoBarra()){
		// Debe persistir el usuario
			if(productoEdicion.getIdProducto() == null){
				productoEdicion.setUnidadMedida(unidadMedidaEjb.findIdUnidadMedida(idTipoUnidadMedida));
				productoEdicion.setMarca(marcaEjb.findMarcaId(idMarca));
				if(idProductoUnitario != null){
					productoEdicion.setProductoUnitario(productoEjb.findIdProducto(idProductoUnitario));
				}
				productoEdicion.setTipoProducto(tipoProductoEjb.findIdTipoProducto(idTipoProducto));
				productoEdicion = productoEjb.create(productoEdicion);
				idTipoUnidadMedida = null;
				idTipoProducto = null;
				idProductoUnitario = null;
				idMarca=null;
				 FacesContext.getCurrentInstance().addMessage("Producto Creado", new FacesMessage("Nuevo Producto Creado."));
				 productoEdicion = new Producto();
				 
				 // Crear cabecera de producto y detalle
			} else {
				// Modificar Producto
				productoEdicion.setUnidadMedida(unidadMedidaEjb.findIdUnidadMedida(idTipoUnidadMedida));
				productoEdicion.setMarca(marcaEjb.findMarcaId(idMarca));
				productoEdicion.setTipoProducto(tipoProductoEjb.findIdTipoProducto(idTipoProducto));
				if(idProductoUnitario != null){
					productoEdicion.setProductoUnitario(productoEjb.findIdProducto(idProductoUnitario));
				}
				productoEdicion = productoEjb.update(productoEdicion);
				idTipoUnidadMedida = null;
				idTipoProducto = null;
				idProductoUnitario = null;
				idMarca=null;
				FacesContext.getCurrentInstance().addMessage("Producto Modificado", new FacesMessage("Producto Modificado."));
				// Crear cabecera de producto y detalle
			}
		}
	}
	public List<Marca> getMarcas() {
		return marcas;
	}
	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	public List<TipoProducto> getTiposProductos() {
		return tiposProductos;
	}
	public void setTiposProductos(List<TipoProducto> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}
	public List<Proveedor> getProveedores() {
		return proveedores;
	}
	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}
	public List<Proveedor> getProveedorProducto() {
		return proveedorProducto;
	}
	public void setProveedorProducto(List<Proveedor> proveedorProducto) {
		this.proveedorProducto = proveedorProducto;
	}
	public Long getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}
	public Long getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(Long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public Long getIdTipoUnidadMedida() {
		return idTipoUnidadMedida;
	}
	public void setIdTipoUnidadMedida(Long idTipoUnidadMedida) {
		this.idTipoUnidadMedida = idTipoUnidadMedida;
	}
	public List<UnidadMedida> getUnidadMedidaList() {
		return unidadMedidaList;
	}
	public void setUnidadMedidaList(List<UnidadMedida> unidadMedidaList) {
		this.unidadMedidaList = unidadMedidaList;
	}
	public List<Producto> getListProductos() {
		return listProductos;
	}
	public void setListProductos(List<Producto> listProductos) {
		this.listProductos = listProductos;
	}
	public Long getIdProductoUnitario() {
		return idProductoUnitario;
	}
	public void setIdProductoUnitario(Long idProductoUnitario) {
		this.idProductoUnitario = idProductoUnitario;
	}
	
	
	public boolean verificarCodigoBarra(){
		boolean duplicadoCodigoBarra = false;
		//metodo para consultar si codigo de barra es duplicado.
		Producto verificarProducto = productoEjb.findProductoCodigoBarra(productoEdicion.getCodigoBarra());
		if (verificarProducto != null){
			duplicadoCodigoBarra= true;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Codigo Barra Duplicado. Verificar.", null);
			facesContext.addMessage("Codigo Barra Duplicado. Verificar.", facesMessage);
			return duplicadoCodigoBarra;
		} else {
			return duplicadoCodigoBarra;
		}
	}
	
}

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
	private List<TipoProducto> tiposProductos;
	private List<UnidadMedida> unidadMedidaList;
	private List<Proveedor> proveedores;
	private List<Proveedor> proveedorProducto;
	
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
			marcas = marcaEjb.findAll();
			tiposProductos = tipoProductoEjb.findAll();
			unidadMedidaList = unidadMedidaEjb.findAllActivo();
			proveedores = proveedorEjb.findAll();
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
			} else {
				productoEdicion = new Producto();
			}
		}
	}
	
	public void guardarProducto(){
		// Debe persistir el usuario
		if(productoEdicion.getIdProducto() == null){
			System.out.println(" unidad de medida: " + idTipoUnidadMedida);
			productoEdicion.setUnidadMedida(unidadMedidaEjb.findIdUnidadMedida(idTipoUnidadMedida));
			productoEdicion.setMarca(marcaEjb.findMarcaId(idMarca));
			productoEdicion.setTipoProducto(tipoProductoEjb.findIdTipoProducto(idTipoProducto));
			productoEdicion = productoEjb.create(productoEdicion);
			 FacesContext.getCurrentInstance().addMessage("Producto Creado", new FacesMessage("Nuevo Producto Creado."));
			 productoEdicion = new Producto();
			 
			 // Crear cabecera de producto y detalle
		} else {
			// Modificar Producto
			productoEdicion.setUnidadMedida(unidadMedidaEjb.findIdUnidadMedida(idTipoUnidadMedida));
			productoEdicion.setMarca(marcaEjb.findMarcaId(idMarca));
			productoEdicion.setTipoProducto(tipoProductoEjb.findIdTipoProducto(idTipoProducto));
			productoEdicion = productoEjb.update(productoEdicion);
			FacesContext.getCurrentInstance().addMessage("Producto Modificado", new FacesMessage("Producto Modificado."));
			// Crear cabecera de producto y detalle
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
	
	
}

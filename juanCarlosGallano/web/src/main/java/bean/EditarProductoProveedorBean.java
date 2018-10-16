package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.ProductoEJB;
import ejb.ProductoProveedorEJB;
import ejb.ProveedorEJB;
import entities.Producto;
import entities.ProductoProveedor;
import entities.Proveedor;

@ManagedBean (name = "editarProductoProveedorBean")
@ViewScoped
public class EditarProductoProveedorBean {
	private Long idProductoProveedor;
	private ProductoProveedor productoProveedorEdicion;
	
	private List<Producto> listProducto;
	private List<Proveedor> listProveedor;
	
	private Long idProveedor;
	private Long idProducto;
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	ProveedorEJB proveedorEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			listProducto = productoEJB.findAllActivo();
			listProveedor = proveedorEJB.findAllActivo();
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idProductoProveedor = Long.parseLong(params.get("idProductoProveedor"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idProductoProveedor != null){
				productoProveedorEdicion = productoProveedorEJB.findIdProductoProveedor(idProductoProveedor);
				idProducto = productoProveedorEdicion.getProducto().getIdProducto();
				idProveedor = productoProveedorEdicion.getProveedor().getIdProveedor();
			} else {
				productoProveedorEdicion = new ProductoProveedor();
			}
		}
	}

	public Long getIdProductoProveedor() {
		return idProductoProveedor;
	}

	public void setIdProductoProveedor(Long idProductoProveedor) {
		this.idProductoProveedor = idProductoProveedor;
	}

	public ProductoProveedor getProductoProveedorEdicion() {
		return productoProveedorEdicion;
	}

	public void setProductoProveedorEdicion(ProductoProveedor productoProveedorEdicion) {
		this.productoProveedorEdicion = productoProveedorEdicion;
	}
	public void guardarProductoProveedor(){
		// Debe persistir el usuario
		if(productoProveedorEdicion.getIdProductoProveedor() == null){
			productoProveedorEdicion.setProducto(productoEJB.findIdProducto(idProducto));
			productoProveedorEdicion.setProveedor(proveedorEJB.findProveedorId(idProveedor));
			productoProveedorEdicion = productoProveedorEJB.create(productoProveedorEdicion);
			
			//limpiar campos
			idProducto = null;
			idProveedor = null;
			productoProveedorEdicion = new ProductoProveedor();
			 FacesContext.getCurrentInstance().addMessage("ProductoProveedor Creado", new FacesMessage("Nuevo ProductoProveedor Creado."));
			 productoProveedorEdicion = new ProductoProveedor();
		} else {
			productoProveedorEdicion.setProducto(productoEJB.findIdProducto(idProducto));
			productoProveedorEdicion.setProveedor(proveedorEJB.findProveedorId(idProveedor));
			productoProveedorEdicion = productoProveedorEJB.update(productoProveedorEdicion);
			idProducto = null;
			idProveedor = null;
			productoProveedorEdicion = new ProductoProveedor();
			FacesContext.getCurrentInstance().addMessage("ProductoProveedor Modificado", new FacesMessage("ProductoProveedor Modificado."));
		}
	}

	public List<Producto> getListProducto() {
		return listProducto;
	}

	public void setListProducto(List<Producto> listProducto) {
		this.listProducto = listProducto;
	}

	public List<Proveedor> getListProveedor() {
		return listProveedor;
	}

	public void setListProveedor(List<Proveedor> listProveedor) {
		this.listProveedor = listProveedor;
	}

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
	

}


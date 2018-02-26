package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.ProductoProveedorEJB;
import entities.ProductoProveedor;

@ManagedBean (name = "productoProveedorBean")
@ViewScoped
public class ProductoProveedorBean {
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	private ProductoProveedor ProductoProveedorEdicion;
	
	private List<ProductoProveedor> productoProveedorList = new ArrayList<ProductoProveedor>();

	public ProductoProveedor getProductoProveedorEdicion() {
		return ProductoProveedorEdicion;
	}

	public void setProductoProveedorEdicion(ProductoProveedor ProductoProveedorEdicion) {
		this.ProductoProveedorEdicion = ProductoProveedorEdicion;
	}

	public List<ProductoProveedor> getProductoProveedorList() {
		return productoProveedorEJB.findAll();
	}

	public void setProductoProveedorList(List<ProductoProveedor> grupoList) {
		this.productoProveedorList = grupoList;
	}
	
	public void eliminarProductoProveedor (ProductoProveedor dep){
		productoProveedorEJB.delete(dep);
	}
	
}

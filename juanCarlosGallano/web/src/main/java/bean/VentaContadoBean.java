package bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.ProductoEJB;
import entities.Producto;

@ManagedBean (name = "ventaContadoBean")
@ViewScoped
public class VentaContadoBean {
	
	private List<Producto> listProducto;
	
	private Long cantidad;
	
	private Long cantidad1;
	
	private Producto producto;
	
	@EJB
	ProductoEJB productoEJB;

	public List<Producto> getListProducto() {
		listProducto = productoEJB.findAll();
		return listProducto;
	}

	public void setListProducto(List<Producto> listProducto) {
		this.listProducto = listProducto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
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

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	

}

package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ejb.ProductoEJB;
import entities.Producto;

@ManagedBean(name ="productoBean")
@SessionScoped
public class ProductoBean {
	
	@EJB
	ProductoEJB productoEjb;
	
	private Producto productoEdicion;
	private List<Producto> productoList = new ArrayList<Producto>();
	public Producto getProductoEdicion() {
		return productoEdicion;
	}
	public void setProductoEdicion(Producto productoEdicion) {
		this.productoEdicion = productoEdicion;
	}
	public List<Producto> getProductoList() {
		return productoEjb.findAll();
	}
	public void setProductoList(List<Producto> productoList) {
		this.productoList = productoList;
	}

}

package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ejb.PreferenciaProveedorEJB;
import ejb.ProductoEJB;
import ejb.ProductoProveedorEJB;
import ejb.ProveedorEJB;
import entities.PreferenciaProveedor;
import entities.Producto;
import entities.ProductoProveedor;
import entities.Proveedor;

@ManagedBean (name = "preferenciaProveedorBean")
@ViewScoped
public class PreferenciaProveedorBean {
	
	@EJB
	PreferenciaProveedorEJB preferenciaProveedorEJB;
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	@EJB
	ProveedorEJB proveedorEJB;
	
	@EJB
	ProductoEJB productoEJB;
	
	private Boolean mostrarProveedorPreferido;
	
	private PreferenciaProveedor preferenciaProveedor;
	
	private ProductoProveedor productoProveedor;
	
	private Proveedor proveedor;
	
	private Producto producto;
	
	private List<Producto> listProductosActivos;
	
	private List<ProductoProveedor> listProductoProveedor;
	
	private List<ProductoProveedor> listProductoProveedorFiltered;
	
	private Long idProducto;
	
	private Long idProductoProveedor;
	
	private Long idPreferenciaProveedor;

	public PreferenciaProveedorEJB getPreferenciaProveedorEJB() {
		return preferenciaProveedorEJB;
	}

	public void setPreferenciaProveedorEJB(PreferenciaProveedorEJB preferenciaProveedorEJB) {
		this.preferenciaProveedorEJB = preferenciaProveedorEJB;
	}

	public PreferenciaProveedor getPreferenciaProveedor() {
		return preferenciaProveedor;
	}

	public void setPreferenciaProveedor(PreferenciaProveedor preferenciaProveedor) {
		this.preferenciaProveedor = preferenciaProveedor;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<Producto> getListProductosActivos() {
		return listProductosActivos;
	}

	public void setListProductosActivos(List<Producto> listProductosActivos) {
		this.listProductosActivos = listProductosActivos;
	}

	public List<ProductoProveedor> getListProductoProveedor() {
		return listProductoProveedor;
	}

	public void setListProductoProveedor(List<ProductoProveedor> listProductoProveedor) {
		this.listProductoProveedor = listProductoProveedor;
	}
	
	public ProductoProveedor getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedor productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdProductoProveedor() {
		return idProductoProveedor;
	}

	public void setIdProductoProveedor(Long idProductoProveedor) {
		this.idProductoProveedor = idProductoProveedor;
	}

	public Long getIdPreferenciaProveedor() {
		return idPreferenciaProveedor;
	}

	public void setIdPreferenciaProveedor(Long idPreferenciaProveedor) {
		this.idPreferenciaProveedor = idPreferenciaProveedor;
	}

	public Boolean getMostrarProveedorPreferido() {
		return mostrarProveedorPreferido;
	}

	public void setMostrarProveedorPreferido(Boolean mostrarProveedorPreferido) {
		this.mostrarProveedorPreferido = mostrarProveedorPreferido;
	}

	public List<ProductoProveedor> getlistProductoProveedorFiltered() {
		return listProductoProveedorFiltered;
	}

	public void setlistProductoProveedorFiltered(List<ProductoProveedor> listProductoProveedorFiltered) {
		this.listProductoProveedorFiltered = listProductoProveedorFiltered;
	}

	public void init(){
		listProductosActivos = productoEJB.findAllActivo();
		productoProveedor = new ProductoProveedor();
		if (!FacesContext.getCurrentInstance().isPostback()){
			listProductoProveedor = new ArrayList<ProductoProveedor>();
			listProductoProveedorFiltered = new ArrayList<ProductoProveedor>();
			//listProductoProveedor = productoProveedorEJB.findAll();
			producto = new Producto();
			proveedor = new Proveedor();
			mostrarProveedorPreferido = false;
			
		}
	}
	public void guardar(Long idProductoProveedor){
		//GuardandoDatos
		
		//Guardando Proveedor Preferencial
		try {
			
			//Si existe Proveedor
			if(mostrarProveedorPreferido){
				//Si se eligio proveedor
				if (idProductoProveedor != null){
					preferenciaProveedor = preferenciaProveedorEJB.findProducto(productoProveedor.getProducto().getIdProducto());
					//Si no existe proveedor perefencial nuevo sino edita
					if(preferenciaProveedor != null){
						//Edita
						preferenciaProveedor.setProducto(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProducto());
						preferenciaProveedor.setProveedor(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProveedor());
						preferenciaProveedor = preferenciaProveedorEJB.update(preferenciaProveedor);
					} else {
						//Cargando uno nuevo
						preferenciaProveedor = new PreferenciaProveedor();
						preferenciaProveedor.setProducto(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProducto());
						preferenciaProveedor.setProveedor(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProveedor());
						preferenciaProveedor = preferenciaProveedorEJB.create(preferenciaProveedor);
					}
					FacesContext.getCurrentInstance().addMessage("Cargado Preferencia Proveedor.", new FacesMessage("Cargado Preferencia Proveedor."));
				} else {
					FacesContext.getCurrentInstance().addMessage("No se Eligió Proveedor.", new FacesMessage("No se Eligió Proveedor."));
				}
			} else {
				//Si no existe proveedor preferido Nuevo
				//Si eligio proveedor
				if (idProductoProveedor != null){
					if(preferenciaProveedor == null){
						//Cargando uno nuevo
						preferenciaProveedor = new PreferenciaProveedor();
						preferenciaProveedor.setProducto(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProducto());
						preferenciaProveedor.setProveedor(productoProveedorEJB.findIdProductoProveedor(idProductoProveedor).getProveedor());
						preferenciaProveedor = preferenciaProveedorEJB.create(preferenciaProveedor);
						FacesContext.getCurrentInstance().addMessage("Cargado Preferencia Proveedor.", new FacesMessage("Cargado Preferencia Proveedor."));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage("No se Eligió Proveedor.", new FacesMessage("No se Eligió Proveedor."));
				}
				
			}
			//preferenciaProveedorEJB.create(t)
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void cargaProductoProveedor(){
		//Verificando que ya no exista proveedor preferido en producto
		try {
			preferenciaProveedor = preferenciaProveedorEJB.findProducto(idProducto);
		} catch (Exception e) {
			preferenciaProveedor = null;
			mostrarProveedorPreferido = false;
			System.out.println("no se encontro producto :" + e.getMessage());
		}
		if (preferenciaProveedor != null) {
			mostrarProveedorPreferido = true;
			FacesContext.getCurrentInstance().addMessage("Producto ya posee Proveedor Preferencial.", new FacesMessage("Producto ya posee Proveedor Preferencial."));
		}
		//cargando lista de proveedores
		listProductoProveedor = productoProveedorEJB.findProveedor(idProducto);
		RequestContext.getCurrentInstance().update("templateForm:formEditPreferenciaProveedor:panelSeleccionProveedor");
		RequestContext.getCurrentInstance().update("templateForm:formEditPreferenciaProveedor:panelProveedorPreferencia");
	}

}

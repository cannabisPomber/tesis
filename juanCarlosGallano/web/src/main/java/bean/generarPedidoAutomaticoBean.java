package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.DetalleOrdenCompraEJB;
import ejb.DetallePedidoEJB;
import ejb.DetallePresupuestoEJB;
import ejb.OrdenCompraEJB;
import ejb.PedidoEJB;
import ejb.PresupuestoEJB;
import ejb.ProductoEJB;
import ejb.ProductoProveedorEJB;
import entities.DetalleOrdenCompra;
import entities.DetallePedido;
import entities.DetallePresupuesto;
import entities.OrdenCompra;
import entities.Pedido;
import entities.Presupuesto;
import entities.Producto;
import entities.ProductoProveedor;

@ManagedBean (name = "generarPedidoAutomaticoBean")
@ViewScoped
public class generarPedidoAutomaticoBean {
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	DetallePedidoEJB detallePedidoEJB;
	
	@EJB
	PresupuestoEJB presupuestoEJB;
	
	@EJB
	DetallePresupuestoEJB detallePresupuestoEJB;
	
	@EJB
	OrdenCompraEJB ordenCompraEJB;

	@EJB
	DetalleOrdenCompraEJB detalleOrdenCompraEJB;
	
	private String codBarra;
	
	private Long reposicion;
	
	private Boolean mostrarProveedor;
	
	private Producto producto;
	
	private ProductoProveedor proveedorProducto;
	
	private Pedido pedido;
	private DetallePedido detallePedido;
	
	private Presupuesto presupuesto;
	private DetallePresupuesto detallePresupuesto;
	
	
	private OrdenCompra ordenCompra;
	private DetalleOrdenCompra detalleOrdenCompra;
	
	public void init(){
		mostrarProveedor = false;
		reposicion = (long) 0;
		producto = new Producto();
	}
	
	
	public void buscarProducto(){
		
		producto = productoEJB.findProductoCodigoBarra(codigoBarraProducto);
		if (producto == null) {
			
			 FacesContext.getCurrentInstance().addMessage("No se Encontro Producto", new FacesMessage("No Existe Producto."));
			 mostrarProveedor = false;
		} else {
			//Cargando el ProductoProveedor con el precio  minimo
			System.out.println("producto Encontrado :" + producto.getNombreProducto() + " " + producto.getMarca().getNombreMarca());
			proveedorProducto = productoProveedorEJB.findProductoProveedorPrecioMenor(producto.getIdProducto());
			if (proveedorProducto == null) {
				System.out.println("proveedor no encontrado:");
				 FacesContext.getCurrentInstance().addMessage("No se Encontro Proveedor de Producto", new FacesMessage("No Existe Proveedor."));
				 mostrarProveedor = false;
			} else {
				//Mostrar Proveedor con menor precio
				mostrarProveedor = true;
				System.out.println("proveedor encontrado:" + mostrarProveedor);
				//CArgar Cantidad a REponer
				reposicion = (producto.getCantidadMaxima() - producto.getCantidadMaxima());
			}
		}
		
	}
	
	private String codigoBarraProducto;


	public String getCodigoBarraProducto() {
		return codigoBarraProducto;
	}

	public void setCodigoBarraProducto(String codigoBarraProducto) {
		this.codigoBarraProducto = codigoBarraProducto;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public Boolean getMostrarProveedor() {
		return mostrarProveedor;
	}

	public void setMostrarProveedor(Boolean mostrarProveedor) {
		this.mostrarProveedor = mostrarProveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public ProductoProveedor getProveedorProducto() {
		return proveedorProducto;
	}

	public void setProveedorProducto(ProductoProveedor proveedorProducto) {
		this.proveedorProducto = proveedorProducto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public DetallePedido getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public DetallePresupuesto getDetallePresupuesto() {
		return detallePresupuesto;
	}

	public void setDetallePresupuesto(DetallePresupuesto detallePresupuesto) {
		this.detallePresupuesto = detallePresupuesto;
	}

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public DetalleOrdenCompra getDetalleOrdenCompra() {
		return detalleOrdenCompra;
	}

	public void setDetalleOrdenCompra(DetalleOrdenCompra detalleOrdenCompra) {
		this.detalleOrdenCompra = detalleOrdenCompra;
	}


	public Long getReposicion() {
		return reposicion;
	}


	public void setReposicion(Long reposicion) {
		this.reposicion = reposicion;
	}
	
	
	
	

}

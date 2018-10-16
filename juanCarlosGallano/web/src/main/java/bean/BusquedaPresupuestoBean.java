package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import ejb.DetalleOrdenCompraEJB;
import ejb.DetallePedidoEJB;
import ejb.DetallePresupuestoEJB;
import ejb.OrdenCompraEJB;
import ejb.PedidoEJB;
import ejb.PresupuestoEJB;
import ejb.ProductoEJB;
import ejb.ProductoProveedorEJB;
import ejb.ProveedorEJB;
import ejb.UsuarioEJB;
import entities.DetalleOrdenCompra;
import entities.DetallePedido;
import entities.DetallePresupuesto;
import entities.OrdenCompra;
import entities.Pedido;
import entities.Presupuesto;
import entities.Producto;
import entities.ProductoProveedor;
import entities.Proveedor;

@ManagedBean (name = "busquedaPresupuestoBean")
@ViewScoped
public class BusquedaPresupuestoBean {
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	ProveedorEJB proveedorEJB;
	
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
	//PAra buscar usuario logueado por String de user
	@EJB
	UsuarioEJB usuarioEJB;
	
	private String codBarra;
	
	private Long reposicion;
	
	
	
	private Producto producto;
	
	private ProductoProveedor proveedorProducto;
	//Listado de Pedidos
	private List<Pedido> listPedidosActivos;
	private List<Pedido> listPedidosActivosSelection;
	//Listado de Productos
	private List<Producto> listProducto;
	private Pedido pedido;
	private List<DetallePedido> detallePedido;
	//List Cantidad de Productos
	private ArrayList<List<String>> listCantidadProductos;
	
	//Lista de Proveedores y Producto
	private List<ProductoProveedor> listProductoProveedores;
	private Long idProductoProveedorSeleccionado;
	//Cargando Cantidad de Pedido Orden de Compra
	private Long cantidadOrdenCompra;
	//boolean para mostrar panel de busqueda de proveedores
	private Boolean mostrarProveedor;
	
	private Presupuesto presupuesto;
	private DetallePresupuesto detallePresupuesto;
	
	//Lista de Ordenes de Compra a Generar
	private List<OrdenCompra> listOrdenCompraGenerado;
	private List<DetalleOrdenCompra> listDetOc;
	
	
	private OrdenCompra ordenCompra;
	private DetalleOrdenCompra detalleOrdenCompra;
	
	
	
	public void init(){
		
		try{
			detalleOrdenCompraEJB.deleteConstraintUniqueProducto();
		} catch (Exception ex){
			System.out.println("Error al borrar Constraint: " + ex.getMessage());
		}
		listPedidosActivos = pedidoEJB.findAllActivo();
		if (!FacesContext.getCurrentInstance().isPostback()){
			
			
			listPedidosActivosSelection = new ArrayList<Pedido>();
			listProducto = new ArrayList<Producto>();
			listProductoProveedores = new ArrayList<ProductoProveedor>();
			listOrdenCompraGenerado = new ArrayList<OrdenCompra>();
			listDetOc = new ArrayList<DetalleOrdenCompra>();
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


	public List<DetallePedido> getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(List<DetallePedido> detallePedido) {
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


	public List<Pedido> getListPedidosActivos() {
		return listPedidosActivos;
	}


	public void setListPedidosActivos(List<Pedido> listPedidosActivos) {
		this.listPedidosActivos = listPedidosActivos;
	}


	public List<Pedido> getListPedidosActivosSelection() {
		return listPedidosActivosSelection;
	}


	public Long getCantidadOrdenCompra() {
		return cantidadOrdenCompra;
	}

	public void setCantidadOrdenCompra(Long cantidadOrdenCompra) {
		this.cantidadOrdenCompra = cantidadOrdenCompra;
	}

	public void setListPedidosActivosSelection(List<Pedido> listPedidosActivosSelection) {
		this.listPedidosActivosSelection = listPedidosActivosSelection;
	}

	public List<Producto> getListProducto() {
		return listProducto;
	}

	public void setListProducto(List<Producto> listProducto) {
		this.listProducto = listProducto;
	}

	public ArrayList<List<String>> getListCantidadProductos() {
		return listCantidadProductos;
	}

	public void setListCantidadProductos(ArrayList<List<String>> listCantidadProductos) {
		this.listCantidadProductos = listCantidadProductos;
	}
	
	

	public List<ProductoProveedor> getListProductoProveedores() {
		return listProductoProveedores;
	}

	public void setListProductoProveedores(List<ProductoProveedor> listProductoProveedores) {
		this.listProductoProveedores = listProductoProveedores;
	}
	

	public Long getIdProductoProveedorSeleccionado() {
		return idProductoProveedorSeleccionado;
	}

	public void setIdProductoProveedorSeleccionado(Long idProductoProveedorSeleccionado) {
		this.idProductoProveedorSeleccionado = idProductoProveedorSeleccionado;
	}
	

	public List<OrdenCompra> getListOrdenCompraGenerado() {
		return listOrdenCompraGenerado;
	}

	public void setListOrdenCompraGenerado(List<OrdenCompra> listOrdenCompraGenerado) {
		this.listOrdenCompraGenerado = listOrdenCompraGenerado;
	}
	
	public List<DetalleOrdenCompra> getListDetOc() {
		return listDetOc;
	}

	public void setListDetOc(List<DetalleOrdenCompra> listDetOc) {
		this.listDetOc = listDetOc;
	}

	//Mostrar Productos Pedidos
	public void mostrarProductos(){
		listCantidadProductos = new ArrayList<List<String>>();
		for (int i = 0; i < listPedidosActivosSelection.size(); i++) {
			//Cargando Detalle de un pedido 
			detallePedido = new ArrayList<DetallePedido>();
			detallePedido = detallePedidoEJB.DetallePedido(listPedidosActivosSelection.get(i).getIdPedido());
			List<String> listDatosProducto = new ArrayList<String>();
			//Cargando Detalles en Array
			for (int j = 0; j < detallePedido.size(); j++){
				if(listCantidadProductos.size()==0){
					//Si aun no hay cargados productos 
					//cargando Id Producto
					listDatosProducto.add(detallePedido.get(j).getProducto().getIdProducto().toString());
					listDatosProducto.add(detallePedido.get(j).getCantidad().toString());
					listDatosProducto.add(detallePedido.get(j).getProducto().getNombreProducto()
								+" "+detallePedido.get(j).getProducto().getMarca().getNombreMarca()
								+" "+detallePedido.get(j).getProducto().getTipoProducto().getNombreTipoProducto()
								+" "+detallePedido.get(j).getProducto().getUnidadMedida().getDimension()
								+detallePedido.get(j).getProducto().getUnidadMedida().getUnidad());
					listCantidadProductos.add(listDatosProducto);
					listDatosProducto = new ArrayList<String>();
				} else {
					//Buscar id Producto
					boolean coincidencia = false;
					for (int j2 = 0; j2 < listCantidadProductos.size(); j2++) {
						//Buscando por IdProducto
						//Si encuentra modificar cantidad
						if(listCantidadProductos.get(j2).get(0).equals(detallePedido.get(j).getProducto().getIdProducto().toString())){
							//Si ya existe ese tipo producto modificar la cantidad
							Long nuevaCantidad = (detallePedido.get(j).getCantidad()+ Long.parseLong(listCantidadProductos.get(j2).get(1)));
							listCantidadProductos.get(j2).set(1, nuevaCantidad.toString());
							coincidencia = true;
						}
					}
					//Si no se encontro Producto.Cargar uno nuevo
					if(!coincidencia){
						listDatosProducto.add(detallePedido.get(j).getProducto().getIdProducto().toString());
						listDatosProducto.add(detallePedido.get(j).getCantidad().toString());
						listDatosProducto.add(detallePedido.get(j).getProducto().getNombreProducto()
									+" "+detallePedido.get(j).getProducto().getMarca().getNombreMarca()
									+" "+detallePedido.get(j).getProducto().getTipoProducto().getNombreTipoProducto()
									+" "+detallePedido.get(j).getProducto().getUnidadMedida().getDimension()
									+detallePedido.get(j).getProducto().getUnidadMedida().getUnidad());
						listCantidadProductos.add(listDatosProducto);
						listDatosProducto = new ArrayList<String>();
					}
				}
				
			}
			
		}
	}
	//Mostrara en pantalla a los proveedores para dicho producto
	public void seleccionarProveedor(String idProducto, String cantidad){
		listProductoProveedores = null;
		listProductoProveedores = productoProveedorEJB.findProveedor(Long.parseLong(idProducto));
		this.setListProductoProveedores(listProductoProveedores);
		System.out.println("Lista de proveedores :" + listProductoProveedores.size());
		if(listProductoProveedores.size()>0){
			cantidadOrdenCompra = Long.parseLong(cantidad);
			mostrarProveedor = true;
		} else {
			FacesContext.getCurrentInstance().addMessage("Producto no Posee Proveedor", new FacesMessage("Producto sin Proveedor."));
			mostrarProveedor = false;
		}
	}
	
	public void cargarOrdenCompra(Long idProveedor, Long idProducto, Long cantidadCompra, Long precioCompra,String userLogueado){
		OrdenCompra oc = new OrdenCompra();
		DetalleOrdenCompra det = new DetalleOrdenCompra();
		//Cargar en Lista
		if (listOrdenCompraGenerado.size() == 0){
			//Crear nueva Orden de Compra
			oc = new OrdenCompra();
			oc.setEstado("Activo");
			oc.setFechaPedido(new Date());
			oc.setProveedor(proveedorEJB.findProveedorId(idProveedor));
			oc.setUsuarioPedido(usuarioEJB.findUserUsuario(userLogueado));
			//Crear Detalle
			det = new DetalleOrdenCompra();
			det.setCantidad(cantidadCompra);
			det.setOrdenCompra(oc);
			det.setPrecioCompra(precioCompra);
			det.setProducto(productoEJB.findIdProducto(idProducto));
			//Cargando primer detalle y orden de compra
			oc.setListaDetalleOrdenCompra(new ArrayList<DetalleOrdenCompra>());
			//oc.getListaDetalleOrdenCompra().add(det);
			listDetOc.add(det);
			oc.setListaDetalleOrdenCompra(listDetOc);
			listOrdenCompraGenerado.add(oc);
			oc= new OrdenCompra();
		} else {
			//si la lisa no esta vacia
			//busca si ya no hay proveedor con orden de compra
			Boolean existeProveedor = false;
			Boolean existeProducto = false;
			for (int i = 0; i < listOrdenCompraGenerado.size(); i++) {
				//Si ya existe ese proveedor en la lista agregar 
				if (listOrdenCompraGenerado.get(i).getProveedor().getIdProveedor().equals(idProveedor)){
					oc = listOrdenCompraGenerado.get(i);
					existeProveedor = true;
					break;
				}
			}
			//Si no existe en proveedor en la lista de Orden de compra agregar
			if (!existeProveedor){
				//Crear nueva Orden de Compra
				oc = new OrdenCompra();
				oc.setEstado("ACTIVO");
				oc.setFechaPedido(new Date());
				oc.setProveedor(proveedorEJB.findProveedorId(idProveedor));
				oc.setUsuarioPedido(usuarioEJB.findUserUsuario(userLogueado));
				//si existe agrega al detalle
				det = new DetalleOrdenCompra();
				det.setCantidad(cantidadCompra);
				det.setOrdenCompra(oc);
				det.setPrecioCompra(precioCompra);
				det.setProducto(productoEJB.findIdProducto(idProducto));
				listDetOc = new ArrayList<DetalleOrdenCompra>();
				listDetOc.add(det);
				oc.setListaDetalleOrdenCompra(listDetOc);
				listOrdenCompraGenerado.add(oc);
			} else {
				// si Existe proveedor
				
				//si existe agrega al detalle
				det = new DetalleOrdenCompra();
				det.setCantidad(cantidadCompra);
				det.setOrdenCompra(oc);
				det.setPrecioCompra(precioCompra);
				det.setProducto(productoEJB.findIdProducto(idProducto));
				//obteniendo detalle para agregar uno nuevo
				listDetOc = oc.getListaDetalleOrdenCompra();
				for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
					DetalleOrdenCompra controlarDetalleOc = (DetalleOrdenCompra) iterator.next();
					if(controlarDetalleOc.getProducto().getIdProducto().equals(idProducto)){
						//Si ya existe producto en lista
						existeProducto = true;
					}
					
				}
				if (!existeProducto){
					listDetOc.add(det);
					oc.setListaDetalleOrdenCompra(listDetOc);
				} else {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se puede duplicar producto en Orden de Compra.", null);
					facesContext.addMessage("No se puede duplicar producto en Orden de Compra.", facesMessage);
				}
			}
		}	
		
	}
	
	public String costoProducto(Long precioCompra){
		Long costo = precioCompra*cantidadOrdenCompra;
		return costo.toString();
	}
	
	public void guardarOrdenCompra(){
		try{
		//persistir orden de compra y detalle
		for (Iterator<OrdenCompra> iterator = listOrdenCompraGenerado.iterator(); iterator.hasNext();) {
			OrdenCompra oCompra = iterator.next();
			//oCompra.setListaDetalleOrdenCompra(listDetOc);
			oCompra.setEstado("ACTIVO");
			ordenCompraEJB.create(oCompra);
		}
//		for(OrdenCompra oc: listOrdenCompraGenerado){
//			oc.set
//		}
		//persistiendo detalle de orden de compra
//		for (int j = 0; j < listDetOc.size(); j++) {
//			detalleOrdenCompraEJB.create(listDetOc.get(j));
//		}
		
		// cambiar estado de los pedidos a cerrado
		 for (int k = 0; k < listPedidosActivosSelection.size(); k++) {
			 Pedido ped = listPedidosActivosSelection.get(k);
			 ped.setEstado("CERRADO");
			 ped = pedidoEJB.update(ped);
		}
		 //borrando listas de tabView
		 listOrdenCompraGenerado = new ArrayList<OrdenCompra>();
		 listDetOc = new ArrayList<DetalleOrdenCompra>();
		 listPedidosActivos = new ArrayList<Pedido>();
		 listCantidadProductos = new ArrayList<List<String>>();
		 listProductoProveedores = new ArrayList<ProductoProveedor>();
		 FacesContext.getCurrentInstance().addMessage("Generado Correctamente Orden de Compra", new FacesMessage("Nuevo Orden de Compra Creado."));
		} catch (Exception ex){
			//FacesContext.getCurrentInstance().addMessage("Generado Correctamente Orden de Compra", new FacesMessage("Nuevo Orden de Compra Creado."));
		}
	}
	
	public void anularPedido(){
		//Anula pedido desde busqueda de presupuesto
		for (Iterator iterator = listPedidosActivosSelection.iterator(); iterator.hasNext();) {
			Pedido pedidoAnular = (Pedido) iterator.next();
			pedidoAnular.setEstado("ANULADO");
			pedidoAnular =pedidoEJB.update(pedidoAnular);
			
		}
		FacesContext.getCurrentInstance().addMessage("Pedidos Seleccionados Anulados", new FacesMessage("Pedidos Seleccionados Anulados."));
	}
	
	
	 public void onTabChange(OrdenCompra ordenCompra) {
		 	List<DetalleOrdenCompra> listAuxDetalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
		 	for (Iterator iterator = listDetOc.iterator(); iterator.hasNext();) {
				DetalleOrdenCompra detalleOrdenCompraAux = (DetalleOrdenCompra) iterator.next();
				if(detalleOrdenCompraAux.getOrdenCompra().getProveedor().getIdProveedor() == ordenCompra.getProveedor().getIdProveedor()){
					listAuxDetalleOrdenCompra.add(detalleOrdenCompraAux);
				}
			}
		 	listDetOc = listAuxDetalleOrdenCompra;
    }
	 
	 public void limpiar(){
		 	listPedidosActivos = pedidoEJB.findAllActivo();
		 	listPedidosActivosSelection = new ArrayList<Pedido>();
			listProducto = new ArrayList<Producto>();
			listProductoProveedores = new ArrayList<ProductoProveedor>();
			listOrdenCompraGenerado = new ArrayList<OrdenCompra>();
			listDetOc = new ArrayList<DetalleOrdenCompra>();
			listCantidadProductos = new ArrayList<List<String>>();
	 }
}

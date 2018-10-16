package bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import ejb.DepositoEJB;
import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleOrdenCompraEJB;
import ejb.DetallePedidoEJB;
import ejb.EmpresaEJB;
import ejb.OrdenCompraEJB;
import ejb.PedidoEJB;
import ejb.PreferenciaProveedorEJB;
import ejb.ProductoEJB;
import ejb.ProductoProveedorEJB;
import ejb.PuestoVentaEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;
import entities.Deposito;
import entities.DetalleDeposito;
import entities.DetalleOrdenCompra;
import entities.DetallePedido;
import entities.Empresa;
import entities.OrdenCompra;
import entities.Pedido;
import entities.PreferenciaProveedor;
import entities.Producto;
import entities.Proveedor;
import entities.Stock;
import entities.Usuario;

@ManagedBean (name = "pedidoAutomaticoBean")
@ViewScoped
public class PedidoAutomaticoBean {
	
	private Date horarioHabitual;
	
	private List<Stock> listStock;
	
	private List<Producto> listProductos;
	
	private List<Producto> listProductosPedidoAutomatico;
	
	private List<Deposito> listDepositos;
	
	private List<DetalleDeposito> listDetalleDeposito;
	
	private List<OrdenCompra> listCabecerasOrdenesCompra;
	private Usuario usuarioLogueado;
	
	private Empresa sucursalUsuario;
	
	private HashMap<Long, Long> cantidadProductoDeposito = new HashMap<Long, Long>();
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	PedidoEJB pedidoEJB;
	
	@EJB
	DetallePedidoEJB detallePedidoEJB;
	
	@EJB
	ProductoProveedorEJB productoProveedorEJB;
	
	@EJB
	PreferenciaProveedorEJB preferenciaProveedorEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	PuestoVentaEJB puestoVentaEJB;
	
	@EJB
	EmpresaEJB sucursalEJB;
	
	@EJB
	OrdenCompraEJB ordenCompraEJB;
	
	@EJB
	DetalleOrdenCompraEJB detOrdenCompraEJB;
	
	@EJB
	StockEJB stockEJB;
	
	@EJB
	DepositoEJB depositoEJB;
	
	@EJB
	DetalleDepositoEJB detalleDepositoEJB;
	
	//obtener la sucursal y la liste depositos.
	
	//obtener productos con proveedor
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			listProductosPedidoAutomatico = new ArrayList<Producto>();
			listCabecerasOrdenesCompra = new ArrayList<OrdenCompra>();
			HttpSession session;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession(); 
			usuarioLogueado = usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
			listProductos = productoEJB.findProductoConProveedor();
			if (usuarioLogueado != null) {
				horarioHabitual = new Date();
				sucursalUsuario = usuarioLogueado.getEmpresa();
				//obtener listado de depositos
				listDepositos = depositoEJB.findAllDepositoEmpresa(sucursalUsuario.getIdEmpresa());
				if(listDepositos == null){
					//No se puede buscar productos si sucursal no esta ligada a depositos
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sucursal de usuario no tiene asignadp Depositos.", null);
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
					//Cargando productos restantes
					for (Iterator iterator = listProductos.iterator(); iterator.hasNext();) {
						Producto prod = (Producto) iterator.next();
						if(prod.getProductoUnitario() == null){
							if(!cantidadProductoDeposito.containsKey(prod.getIdProducto())){
								cantidadProductoDeposito.put(prod.getIdProducto(), (long) 0);
							}
						}
					}
					//Verificando las cantidades con la cantidad minima
					for (Map.Entry<Long, Long> entry : cantidadProductoDeposito.entrySet()) {
						Producto productoSeleccionado = productoEJB.findIdProducto(entry.getKey());
						
						
						//Si la sumatoria de cantidades es menor a cantidad minima
						if(productoSeleccionado.getCantidadMinima() > entry.getValue().longValue()){
							//Es menor que cantidad minima debe hacerse pedido automatico
							listProductosPedidoAutomatico.add(productoSeleccionado);
						}
					}
					
				}
			}
			
		}
		
	}

	public List<Stock> getListStock() {
		return listStock;
	}

	public List<OrdenCompra> getListCabecerasOrdenesCompra() {
		return listCabecerasOrdenesCompra;
	}

	public void setListCabecerasOrdenesCompra(List<OrdenCompra> listCabecerasOrdenesCompra) {
		this.listCabecerasOrdenesCompra = listCabecerasOrdenesCompra;
	}

	public Date getHorarioHabitual() {
		return horarioHabitual;
	}

	public void setHorarioHabitual(Date horarioHabitual) {
		this.horarioHabitual = horarioHabitual;
	}

	public void setListStock(List<Stock> listStock) {
		this.listStock = listStock;
	}

	public List<Producto> getListProductos() {
		return listProductos;
	}

	public void setListProductos(List<Producto> listProductos) {
		this.listProductos = listProductos;
	}

	public List<Producto> getListProductosPedidoAutomatico() {
		return listProductosPedidoAutomatico;
	}

	public void setListProductosPedidoAutomatico(List<Producto> listProductosPedidoAutomatico) {
		this.listProductosPedidoAutomatico = listProductosPedidoAutomatico;
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

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Empresa getSucursalUsuario() {
		return sucursalUsuario;
	}

	public void setSucursalUsuario(Empresa sucursalUsuario) {
		this.sucursalUsuario = sucursalUsuario;
	}

	public HashMap<Long, Long> getCantidadProductoDeposito() {
		return cantidadProductoDeposito;
	}

	public void setCantidadProductoDeposito(HashMap<Long, Long> cantidadProductoDeposito) {
		this.cantidadProductoDeposito = cantidadProductoDeposito;
	}
	
	public Long getStock(Long idProductoSeleccionado){
		return cantidadProductoDeposito.get(idProductoSeleccionado);
	}
	
	public void pedidoAutomatico(){
		
		// Clase en la que está el código a ejecutar 
	     TimerTask timerTask = new TimerTask() 
	     { 
	         public void run()  
	         { 
	             // Aquí el código que queremos ejecutar. 
	        	 System.out.println(" AQUI VOY A REALIZAR PEDIDO AUTOMATICO DE PRODUCTOS cada 60 segundos");
	        	 //Calendar calHorario = new GregorianCalendar(); 
	        	 // si la hora seleccionada es la hora del sistema
	        	 Calendar calHorarioHabitual = Calendar.getInstance();
	        	 calHorarioHabitual.setTime(horarioHabitual);
	        	 System.out.println("Horario Elegido:" + horarioHabitual.toString());
	        	 Calendar calHoy = Calendar.getInstance();
	        	 calHoy.setTime(new Date());
	        	 
	        	 int minutoHabitual = calHorarioHabitual.get(Calendar.MINUTE);
	        	 int minutohoy = calHoy.get(Calendar.MINUTE);
	        	 int horaHabitual = calHorarioHabitual.get(Calendar.HOUR);
	        	 int horahoy = calHoy.get(Calendar.HOUR);
	        	 if (horaHabitual == horahoy && minutoHabitual == minutohoy){

		        		 System.out.println("GENERANDO PEDIDO...........");
		        		 crearPedidoOrdenCompra();
	        	 }
	        	 
	         } 
	     }; 

	      // Aquí se pone en marcha el timer cada segundo. 
	     Timer timer = new Timer(); 
	     //Verificar que sea el horario.
	     
	     // Dentro de 0 milisegundos avísame cada 1000 milisegundos 
	     timer.scheduleAtFixedRate(timerTask, 0, 60000);
	}
	
	public boolean getProveedorPreferido(Long idProducto){
		
		//busca si tiene proveedor preferido ese producto
		if(idProducto != null){
			PreferenciaProveedor prefProducto = preferenciaProveedorEJB.findPreferenciaProveedorId(idProducto);
			if(prefProducto == null){
				return  false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public void crearPedidoOrdenCompra(){
		//lista de proveedores de los productos que no tienen stock
		List<PreferenciaProveedor> listaProveedoresAutomaticos = new ArrayList<PreferenciaProveedor>();
		List<DetallePedido> listDetallePedidoAutomatico;
		
		//crear Pedido
		//para todos los productos
		Pedido pedidoAutomatico = new Pedido();
		pedidoAutomatico.setEstado("CERRADO");
		pedidoAutomatico.setFecha(new Date());
		pedidoAutomatico.setUsuario(usuarioLogueado.getUsuario());
		pedidoEJB.create(pedidoAutomatico);
		
		
		
		//recorrer listado de productos y crear pedidos
		for (Iterator iterator = listProductosPedidoAutomatico.iterator(); iterator.hasNext();) {
			Producto producto = (Producto) iterator.next();
			
			DetallePedido detPedido = new DetallePedido();
			detPedido.setCantidad(producto.getCantidadMaxima()-producto.getCantidadMinima());
			detPedido.setPedido(pedidoAutomatico);
			detPedido.setProducto(producto);
			detallePedidoEJB.create(detPedido);
			
			//Cargar lista de proveedores automaticos
			//buscar proveedor Automatico
			PreferenciaProveedor provPref = new PreferenciaProveedor();
			provPref = preferenciaProveedorEJB.findProducto(producto.getIdProducto());
			//Revisar si existe preferencia proveedor
			if (provPref != null){
				//cargar lista de proveedores automaticos
				listaProveedoresAutomaticos.add(provPref);
			}
			
			
			
		}
		
		// una vez cargados la lista de proveedores automaticos crear las ordenes de compra y detalle
		for (Iterator iterator = listProductosPedidoAutomatico.iterator(); iterator.hasNext();) {
			Producto productoOrdenCompra = (Producto) iterator.next();
			
			//Proveedor proveedorAutomatico = new Proveedor();
			//DetalleOrdenCompra detalleCargarAutomatico = new DetalleOrdenCompra();
			//List<OrdenCompra> listCabecerasOrdenCompra = new ArrayList<OrdenCompra>();
			//List<DetalleOrdenCompra> listDetalleOrdenCompraAutomatico = new ArrayList<DetalleOrdenCompra>();
			
			
			for (Iterator iterator2 = listaProveedoresAutomaticos.iterator(); iterator2.hasNext();) {
				PreferenciaProveedor proveedorOrdenCompra = (PreferenciaProveedor) iterator2.next();
				
				//si es su proveedor preferido
				System.out.println("Verificar: "+ proveedorOrdenCompra.getProducto().getIdProducto() + " ---" + productoOrdenCompra.getIdProducto() );
				if(proveedorOrdenCompra.getProducto().equals(productoOrdenCompra)){
					OrdenCompra ordenCompraAutomatico = new OrdenCompra();
					//Si coincide el producto con la pref proveedor
						
						ordenCompraAutomatico.setProveedor(proveedorOrdenCompra.getProveedor());
						ordenCompraAutomatico.setEstado("CONFIRMADO");
						ordenCompraAutomatico.setFechaAprobacion(new Date());
						ordenCompraAutomatico.setFechaPedido(new Date());
						ordenCompraAutomatico.setUsuarioPedido(usuarioLogueado);
						ordenCompraAutomatico.setUsuarioAprobacion(usuarioLogueado);
						listCabecerasOrdenesCompra.add(ordenCompraAutomatico);
				}
			}
			//una vez creadas las cabeceras de las ordenes de compras cargar los detalles.
			//lista de ordenes de compras creadas de productos
			System.out.println("Cuantos ordenes de compras hay:"+ listCabecerasOrdenesCompra.size());
			
			List<DetalleOrdenCompra> listDetalleOrdenCompraAutomatico = new ArrayList<DetalleOrdenCompra>();
			for (Iterator itOrdenCompra = listCabecerasOrdenesCompra.iterator(); itOrdenCompra.hasNext();) {
				OrdenCompra ordenCompraAutomatico = (OrdenCompra) itOrdenCompra.next();
				
				for (Iterator it = listaProveedoresAutomaticos.iterator(); it.hasNext();) {
					PreferenciaProveedor busqPrefProveedor = (PreferenciaProveedor) it.next();
					//Verificar si esta en la lista de proveedores automaticos
					if(ordenCompraAutomatico.getProveedor().equals(busqPrefProveedor.getProveedor())){
						//Recorrer los productos y crear el detalle
						for (Iterator itProducto = listProductosPedidoAutomatico.iterator(); itProducto.hasNext();) {
							Producto productoADetalle = (Producto) itProducto.next();
							
							if(busqPrefProveedor.getProducto().equals(productoADetalle)){
								
								//Cargar el Detalle a la ordenCompra
								DetalleOrdenCompra detOrdenCompra = new DetalleOrdenCompra();
								detOrdenCompra.setCantidad(productoADetalle.getCantidadMaxima()-productoADetalle.getCantidadMaxima());
								detOrdenCompra.setOrdenCompra(ordenCompraAutomatico);
								detOrdenCompra.setProducto(productoADetalle);
								detOrdenCompra.setPrecioCompra(productoADetalle.getPrecioUnitario());
								detOrdenCompra.setEstado("");
								listDetalleOrdenCompraAutomatico.add(detOrdenCompra);
							}
						
						}
						
					}
					
					
				}
				ordenCompraAutomatico.setListaDetalleOrdenCompra(listDetalleOrdenCompraAutomatico);
				listDetalleOrdenCompraAutomatico = new ArrayList<DetalleOrdenCompra>();
			}
			
			
			//Crear Ordenes de Compra y detalle
			for (Iterator itCrearOrdenCompra = listCabecerasOrdenesCompra.iterator(); itCrearOrdenCompra.hasNext();) {
				OrdenCompra ordenCompraGuardar = (OrdenCompra) itCrearOrdenCompra.next();
				
				ordenCompraEJB.create(ordenCompraGuardar);
				//crear detalles
				for (Iterator iterator2 = ordenCompraGuardar.getListaDetalleOrdenCompra().iterator(); iterator2.hasNext();) {
					DetalleOrdenCompra detGuardar = (DetalleOrdenCompra) iterator2.next();
					detGuardar.setOrdenCompra(ordenCompraGuardar);
					detOrdenCompraEJB.create(detGuardar);
				}
				
			}
		}
		
		
		
	}
	
	

}

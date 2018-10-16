package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.DetalleCuotasEJB;
import ejb.DetalleDepositoEJB;
import ejb.DetalleFacturaEJB;
import ejb.EmpresaEJB;
import ejb.FacturaEJB;
import ejb.ProductoEJB;
import ejb.PuestoVentaEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import ejb.UsuarioEJB;

@ManagedBean (name = "devolucionConsignacionBean")
@ViewScoped
public class devolucionConsignacion {
	
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	PuestoVentaEJB puestoVentaEJB;
	
	@EJB
	EmpresaEJB sucursalEJB;
	
	@EJB
	StockEJB stockEJB;
	
	@EJB
	StockDetalleEJB stockDetalleEJB;
	
	@EJB
	DetalleDepositoEJB detalleDepositoEJB;
	
	@EJB
	FacturaEJB facturaEJB;
	
	
	@EJB
	DetalleFacturaEJB detalleFacturaEJB;
	
	public void init(){
		
		
	}

}

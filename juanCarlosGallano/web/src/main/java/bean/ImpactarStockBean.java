package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.DetalleDepositoEJB;
import ejb.ProductoEJB;
import ejb.StockDetalleEJB;
import ejb.StockEJB;
import entities.DetalleDeposito;
import entities.Producto;
import entities.Stock;
import entities.StockDetalle;

@ManagedBean (name = "impactarStockBean")
@ViewScoped
public class ImpactarStockBean {
	
	@EJB
	ProductoEJB productoEJB;
	
	@EJB
	StockEJB stockEJB;
	
	@EJB
	StockDetalleEJB stockDetalleEJB;
	
	@EJB
	DetalleDepositoEJB detalleDepositoEJB;
	//servira para guardar metodo de impactar Stock
	
	public void restarStock(Long idProducto, Long cantidad){
		
		Stock stockProducto;
		Producto productoImpactar = productoEJB.findIdProducto(idProducto);
		if(productoImpactar == null){
			FacesContext.getCurrentInstance().addMessage("No se Encontro Stock Producto", new FacesMessage("No se Encontro Stock Producto"));
		} else {
			if(productoImpactar.getProductoUnitario() != null){
				//Si posee producto unitario
				stockProducto = stockEJB.findStockCodigoBarra(productoImpactar.getProductoUnitario().getCodigoBarra());
				if (stockProducto == null){
					FacesContext.getCurrentInstance().addMessage("No se Encontro Stock Producto", new FacesMessage("No se Encontro Stock Producto"));
				}
				//Si existe Stock de producto
				stockProducto.setCantidadStock(stockProducto.getCantidadStock()-(cantidad*productoImpactar.getProductoUnitario().getTipoProducto().getCantidad()));
				stockEJB.update(stockProducto);
				//Restar de Detalle de Stock
				//Cerrar Detalle de Stock en cantidad Restante
				//Restar tambien de
				restarStockDetalle(productoImpactar.getProductoUnitario().getIdProducto(), (cantidad*productoImpactar.getProductoUnitario().getTipoProducto().getCantidad()));
				
			} else {
			
				stockProducto = stockEJB.findStockCodigoBarra(productoImpactar.getCodigoBarra());
				//Si existe Stock de producto
				stockProducto.setCantidadStock(stockProducto.getCantidadStock()-cantidad);
				stockEJB.update(stockProducto);
				//Restar de Detalle de Stock
				//Cerrar Detalle de Stock en cantidad Restante
				//Restar tambien de
				restarStockDetalle(productoImpactar.getIdProducto(), cantidad);
			}
		}
		
		
	}
	
	
	//Sirve para recorrer de forma recursiva
	//Stock Detalle e ir restando de cada uno de los registros de stock y cerrar
		public void restarStockDetalle(Long idProducto, Long cantidadRestar){
			//Condicion que permitira que no sea recursivo eterno
			while (cantidadRestar>0){
			
				StockDetalle stockDet = stockDetalleEJB.findStockAntiguoVencimiento(idProducto);
				//Si es igual a la cantidad a restar
				if(stockDet.getCantidadRestante() == cantidadRestar){
					//Si cumple solo impacta en un registro 
					//estadoCerrado de Registro
					stockDet.setEstado("CERRADO");
					stockDet.setCantidadRestante((long) 0);
					stockDetalleEJB.update(stockDet);
					
					//Restar de Deposito Detalle
					DetalleDeposito detDeposito = new DetalleDeposito();
					detDeposito = detalleDepositoEJB.findDeposito(idProducto, stockDet.getDeposito().getIdDeposito());
					if (detDeposito.getCantidadProducto()>= cantidadRestar){
						detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
						detalleDepositoEJB.update(detDeposito);
					} else {
						//REvisar si no hay cantidad en deposito
					}
					
					
					break;
				}
				//Si la cantidad a restar de registro es menor
				if(stockDet.getCantidadRestante() > cantidadRestar){
					//Se mantiene activo, se modifica cantidad restante
					stockDet.setCantidadRestante((stockDet.getCantidadRestante()-cantidadRestar));
					stockDetalleEJB.update(stockDet);
					
					//Restar de Deposito Detalle
					DetalleDeposito detDeposito = new DetalleDeposito();
					detDeposito = detalleDepositoEJB.findDeposito(idProducto, stockDet.getDeposito().getIdDeposito());
					if (detDeposito.getCantidadProducto()>= cantidadRestar){
						detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
						detalleDepositoEJB.update(detDeposito);
					} else {
						//REvisar si no hay cantidad en deposito
					}
					break;
				} else{
					//Si es mayor a la cantidad restante debe recursar en otro registro
					stockDet.setEstado("CERRADO");
					stockDet.setCantidadRestante((long) 0);
					stockDetalleEJB.update(stockDet);
					//Restar de Deposito Detalle
					DetalleDeposito detDeposito = new DetalleDeposito();
					detDeposito = detalleDepositoEJB.findDeposito(stockDet.getProducto().getIdProducto(), stockDet.getDeposito().getIdDeposito());
					if (detDeposito.getCantidadProducto()>= cantidadRestar){
						detDeposito.setCantidadProducto((detDeposito.getCantidadProducto()-cantidadRestar));
						detalleDepositoEJB.update(detDeposito);
					} else {
						//REvisar si no hay cantidad en deposito
					}
					cantidadRestar = cantidadRestar-stockDet.getCantidadRestante();
				}
				
			} 
		}
}

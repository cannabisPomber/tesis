package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.ProductoEJB;
import entities.Marca;
import entities.Producto;

@Named ("productoConverter")
@FacesConverter(forClass = Producto.class)
public class ProductoConverter {
	@EJB
	ProductoEJB productoEjb;
	
	Producto producto;
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		// TODO Auto-generated method stub
		//String convertedValue = null;
	    if ( newValue == null ) {
	        return newValue;
	    }
	    // Since this is only a String to String conversion,
	    // this conversion does not throw ConverterException.
	    try {
	    	producto = productoEjb.findIdProducto(Long.valueOf(newValue));
	    	if (producto == null){
	    		throw new ConverterException("No se encontro Producto asociado al valor");
	    	}
	    	return producto;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Producto asociado al valor");
	    }
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Producto){
			producto = (Producto) arg2;
			//System.out.println(" Id de producto converter " + producto.getIdProducto().toString());
			try { 
				String productoString = String.valueOf(producto.getIdProducto());
				return productoString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}

}
}

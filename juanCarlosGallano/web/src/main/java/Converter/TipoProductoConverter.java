package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.TipoProductoEJB;
import entities.TipoProducto;
@Named ("tipoProductoConverter")
@FacesConverter(forClass = TipoProducto.class) 
public class TipoProductoConverter implements Converter {
	@EJB
	TipoProductoEJB tipoProductoEjb;
	
	TipoProducto tipoProducto;
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		// TODO Auto-generated method stub
		//String convertedValue = null;
	    if ( newValue == null ) {
	        return newValue;
	    }
	    // Since this is only a String to String conversion,
	    // this conversion does not throw ConverterException.
	    try {
	    	tipoProducto = tipoProductoEjb.findIdTipoProducto(Long.valueOf(newValue));
	    	if (tipoProducto == null){
	    		throw new ConverterException("No se encontro TipoProducto asociado al valor");
	    	}
	    	return tipoProducto;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro TipoProducto asociado al valor");
	    }
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof TipoProducto){
			tipoProducto = (TipoProducto) arg2;
			//System.out.println(" Id de tipoProducto converter " + tipoProducto.getIdTipoProducto().toString());
			try { 
				String tipoProductoString = String.valueOf(tipoProducto.getIdTipoProducto());
				return tipoProductoString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}
}

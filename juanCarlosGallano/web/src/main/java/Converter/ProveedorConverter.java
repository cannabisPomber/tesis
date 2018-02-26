package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.ProveedorEJB;
import entities.Proveedor;
@Named ("proveedorConverter")
@FacesConverter(forClass = Proveedor.class) 
public class ProveedorConverter implements Converter{
	
	@EJB
	ProveedorEJB proveedorEjb;
	
	Proveedor proveedor;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		// TODO Auto-generated method stub
		//String convertedValue = null;
	    if ( newValue == null ) {
	        return newValue;
	    }
	    // Since this is only a String to String conversion,
	    // this conversion does not throw ConverterException.
	    try {
	    	proveedor = proveedorEjb.findProveedorId(Long.valueOf(newValue));
	    	if (proveedor == null){
	    		throw new ConverterException("No se encontro Proveedor asociado al valor");
	    	}
	    	return proveedor;
	    } catch (Exception ex){
	    	System.out.println( "Excepcion" + ex.getMessage());
	    	throw new ConverterException("No se encontro Proveedor asociado al valor");
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Proveedor){
			proveedor = (Proveedor) arg2;
			//System.out.println(" Id de proveedor converter " + proveedor.getIdProveedor().toString());
			try { 
				String proveedorString = String.valueOf(proveedor.getIdProveedor());
				return proveedorString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}

}
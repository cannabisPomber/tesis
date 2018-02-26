package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.RolEJB;
import entities.Rol;
@Named ("rolConverter")
@FacesConverter(forClass = Rol.class) 
public class RolConverter implements Converter{
	
	@EJB
	RolEJB rolEjb;
	
	Rol rol;
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
	    	rol = rolEjb.findRol(Long.valueOf(newValue));
	    	if (rol == null){
	    		throw new ConverterException("No se encontro Rol asociado al valor");
	    	}
	    	return rol;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Rol asociado al valor");
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Rol){
			rol = (Rol) arg2;
			//System.out.println(" Id de rol converter " + rol.getIdRol().toString());
			try { 
				String rolString = String.valueOf(rol.getIdRol());
				return rolString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}

}


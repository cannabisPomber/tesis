package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.UnidadMedidaEJB;
import entities.UnidadMedida;

@Named ("unidadMedidaConverter")
@FacesConverter(forClass = UnidadMedida.class) 
public class UnidadMedidaConverter implements Converter {
	
	@EJB
	UnidadMedidaEJB unidadMedidaEjb;
	
	UnidadMedida unidadMedida;
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		// TODO Auto-generated method stub
		//String convertedValue = null;
	    if ( newValue == null ) {
	        return newValue;
	    }
	    // Since this is only a String to String conversion,
	    // this conversion does not throw ConverterException.
	    try {
	    	unidadMedida = unidadMedidaEjb.findIdUnidadMedida(Long.valueOf(newValue));
	    	if (unidadMedida == null){
	    		throw new ConverterException("No se encontro UnidadMedida asociado al valor");
	    	}
	    	return unidadMedida;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro UnidadMedida asociado al valor");
	    }
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof UnidadMedida){
			unidadMedida = (UnidadMedida) arg2;
			//System.out.println(" Id de unidadMedida converter " + unidadMedida.getIdUnidadMedida().toString());
			try { 
				String unidadMedidaString = String.valueOf(unidadMedida.getId());
				return unidadMedidaString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}

}
}

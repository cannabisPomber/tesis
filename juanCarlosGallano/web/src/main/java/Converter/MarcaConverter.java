package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.MarcaEJB;
import entities.Marca;

@Named ("marcaConverter")
@FacesConverter(forClass = Marca.class) 
public class MarcaConverter implements Converter {
	
	@EJB
	MarcaEJB marcaEjb;
	
	Marca marca;
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		// TODO Auto-generated method stub
		//String convertedValue = null;
	    if ( newValue == null ) {
	        return newValue;
	    }
	    // Since this is only a String to String conversion,
	    // this conversion does not throw ConverterException.
	    try {
	    	marca = marcaEjb.findMarcaId(Long.valueOf(newValue));
	    	if (marca == null){
	    		throw new ConverterException("No se encontro Marca asociado al valor");
	    	}
	    	return marca;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Marca asociado al valor");
	    }
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Marca){
			marca = (Marca) arg2;
			//System.out.println(" Id de marca converter " + marca.getIdMarca().toString());
			try { 
				String marcaString = String.valueOf(marca.getIdMarca());
				return marcaString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}

}
}

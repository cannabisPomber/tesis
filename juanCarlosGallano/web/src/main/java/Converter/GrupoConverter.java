  package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.GrupoEJB;
import entities.Grupo;
@Named ("grupoConverter")
@FacesConverter(forClass = Grupo.class) 
public class GrupoConverter implements Converter{
	
	@EJB
	GrupoEJB grupoEjb;
	
	Grupo grupo;
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
	    	grupo = grupoEjb.findGrupo(Long.valueOf(newValue));
	    	if (grupo == null){
	    		throw new ConverterException("No se encontro Grupo asociado al valor");
	    	}
	    	return grupo;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Grupo asociado al valor");
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Grupo){
			grupo = (Grupo) arg2;
			//System.out.println(" Id de grupo converter " + grupo.getIdGrupo().toString());
			try { 
				String grupoString = String.valueOf(grupo.getIdGrupo());
				return grupoString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}

}

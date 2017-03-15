package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.UsuarioEJB;
import entities.Usuario;
import entities.Usuario;
@Named ("usuarioConverter")
@FacesConverter (forClass = Usuario.class)
public class usuarioConverter implements Converter{

	@EJB
	UsuarioEJB usuarioEjb;
	
	Usuario usuario;
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
	    	usuario = usuarioEjb.findIdUsuario(Long.valueOf(newValue));
	    	if (usuario == null){
	    		throw new ConverterException("No se encontro Usuario asociado al valor");
	    	}
	    	return usuario;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Usuario asociado al valor");
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Usuario){
			usuario = (Usuario) arg2;
			//System.out.println(" Id de usuario converter " + usuario.getIdUsuario().toString());
			try { 
				String usuarioString = String.valueOf(usuario.getIdUsuario());
				return usuarioString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}

}

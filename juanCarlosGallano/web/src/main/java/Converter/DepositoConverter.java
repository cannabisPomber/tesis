package Converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import ejb.DepositoEJB;
import entities.Deposito;
@Named ("depositoConverter")
@FacesConverter(forClass = Deposito.class) 
public class DepositoConverter implements Converter{
	
	@EJB
	DepositoEJB depositoEjb;
	
	Deposito deposito;
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
	    	deposito = depositoEjb.findIdDeposito(Long.valueOf(newValue));
	    	if (deposito == null){
	    		throw new ConverterException("No se encontro Deposito asociado al valor");
	    	}
	    	return deposito;
	    } catch (Exception ex){
	    	System.out.println( " Excepcion " + ex.getMessage());
	    	throw new ConverterException("No se encontro Deposito asociado al valor");
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		if (arg2 instanceof Deposito){
			deposito = (Deposito) arg2;
			//System.out.println(" Id de deposito converter " + deposito.getIdDeposito().toString());
			try { 
				String depositoString = String.valueOf(deposito.getIdDeposito());
				return depositoString;
			} catch (Exception ex) {
				return "";
			}
			 
		} else {
			return "";
		}
	}

}

package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ejb.PersonaEJB;
import entities.Persona;


@ManagedBean(name="persona")
@SessionScoped
public class PersonaBean {
	
	@EJB
	PersonaEJB PersonaEjb;	
	
	private List<Persona> PersonaList = new ArrayList<Persona>();

	/**
	 * This method uses ejb to get the list of the Personas
	 * @return returns the Persona list
	 */
	public List<Persona> getPersonaList() {
		PersonaList = PersonaEjb.findAll();
		return PersonaList;
	}

	public void setPersonaList(List<Persona> PersonaList) {
		this.PersonaList = PersonaList;
	}	
}

package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Persona;

/**
 * Session Bean implementation class PersonaEJB
 */
@Stateless
@LocalBean
public class PersonaEJB extends GenericDaoImpl<Persona, Serializable>{

	@PersistenceContext(unitName="primary")
    EntityManager em;
   
    public List<Persona> Personas = new ArrayList<Persona>();
    
    public List<Persona> findAll() {
    	TypedQuery<Persona> query = em.createQuery("select c from Persona c", Persona.class);
    	Personas = query.getResultList();
    	return Personas;
   }


}

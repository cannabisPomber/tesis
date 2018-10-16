package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity (name = "ruc_personas")
@NamedQuery(name="RucPersonas.findAll", query="SELECT u FROM ruc_personas u")
public class RucPersonas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name="id_ruc_personas_generator", sequenceName = "id_ruc_personas_seq", allocationSize=1)
	@Column(name = "id_ruc_personas")
	private Long idRucPersonas;
	
	@Column(name = "ruc", length = 100, nullable = false)
	private String ruc;
	
	@Column(name = "persona", length = 250, nullable = false)
	private String persona;

	public Long getIdRucPersonas() {
		return idRucPersonas;
	}

	public void setIdRucPersonas(Long idRucPersonas) {
		this.idRucPersonas = idRucPersonas;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRucPersonas == null) ? 0 : idRucPersonas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RucPersonas other = (RucPersonas) obj;
		if (idRucPersonas == null) {
			if (other.getIdRucPersonas()!= null)
				return false;
		} else if (!idRucPersonas.equals(other.idRucPersonas))
			return false;
		return true;
	}

	
	
	

}


package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
@Entity (name = "barrio")
@NamedQuery(name="Barrio.findAll", query="SELECT u FROM barrio u")
public class Barrio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_barrio")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBarrio;
	
	@Column (name = "codigo_barrio", length = 5)
	@NotNull
	private String codBarrio;
	
	@Column (name = "nombre_barrio", length = 60)
	@NotNull
	private String nombreBarrio;
	
	@Column (name = "estado", length = 20)
	private String estado;
	
	@ManyToOne
	@JoinColumn(name = "id_ciudad")
	private Ciudad ciudad;
	

	public Long getIdBarrio() {
		return idBarrio;
	}

	public void setIdBarrio(Long idBarrio) {
		this.idBarrio = idBarrio;
	}

	public String getCodBarrio() {
		return codBarrio;
	}

	public void setCodBarrio(String codBarrio) {
		this.codBarrio = codBarrio;
	}

	public String getNombreBarrio() {
		return nombreBarrio;
	}

	public void setNombreBarrio(String nombreBarrio) {
		this.nombreBarrio = nombreBarrio;
	}
	

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBarrio == null) ? 0 : idBarrio.hashCode());
		return result;
	}
	

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Barrio other = (Barrio) obj;
		if (idBarrio == null) {
			if (other.idBarrio != null)
				return false;
		} else if (!idBarrio.equals(other.idBarrio))
			return false;
		return true;
	}
}
package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity (name = "marca")
@NamedQuery(name="Marca.findAll", query="SELECT u FROM marca u")
public class Marca implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_marca")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idMarca;
	
	@Column (name = "nombre_marca")
	@NotNull
	private String nombreMarca;
	
	@Column (name = "estado_marca")
	@NotNull
	private String estadoMarca;
	public Long getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(long idMarca) {
		this.idMarca = idMarca;
	}
	public String getNombreMarca() {
		return nombreMarca;
	}
	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}
	public String getEstadoMarca() {
		return estadoMarca;
	}
	public void setEstadoMarca(String estadoMarca) {
		this.estadoMarca = estadoMarca;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idMarca ^ (idMarca >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marca other = (Marca) obj;
		if (idMarca != other.idMarca)
			return false;
		return true;
	}
	
}

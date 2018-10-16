package entities;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity (name = "marca_tarjeta")
@NamedQuery(name="MarcaTarjeta.findAll", query="SELECT p FROM marca_tarjeta p")
public class MarcaTarjeta implements Serializable {
	
	@Id
	@Column(name="id_tarjeta")
	private Long idTarjeta;
	
	@Column(name="marca")
	private String marca;
	
	@Column(name="estado")
	private String estado;

	public Long getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(Long idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTarjeta == null) ? 0 : idTarjeta.hashCode());
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
		MarcaTarjeta other = (MarcaTarjeta) obj;
		if (idTarjeta == null) {
			if (other.idTarjeta != null)
				return false;
		} else if (!idTarjeta.equals(other.idTarjeta))
			return false;
		return true;
	}
	
	

}


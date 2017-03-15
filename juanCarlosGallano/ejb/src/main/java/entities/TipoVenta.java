package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
@Entity (name = "tipo_venta")
public class TipoVenta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_venta")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTipoVenta;
	
	@Column (name = "nombre_tipo_venta")
	@NotNull
	private String nombreTipoVenta;

	public Long getIdTipoVenta() {
		return idTipoVenta;
	}

	public void setIdTipoVenta(Long idTipoVenta) {
		this.idTipoVenta = idTipoVenta;
	}

	public String getNombreTipoVenta() {
		return nombreTipoVenta;
	}

	public void setNombreTipoVenta(String nombreTipoVenta) {
		this.nombreTipoVenta = nombreTipoVenta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idTipoVenta ^ (idTipoVenta >>> 32));
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
		TipoVenta other = (TipoVenta) obj;
		if (idTipoVenta != other.idTipoVenta)
			return false;
		return true;
	}

}

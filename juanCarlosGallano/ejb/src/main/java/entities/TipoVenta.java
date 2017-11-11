package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
@Entity (name = "tipo_venta")
public class TipoVenta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@Id
	@Column(name="id_tipo_venta")
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_venta_generator")
	@SequenceGenerator(name="tipo_venta_generator", sequenceName = "tipo_venta_seq", allocationSize=1)
	@Column(name = "id_tipo_venta", updatable = false, nullable = false)
	private Long idTipoVenta;
	
	@Column (name = "nombre_tipo_venta")
	@NotNull
	private String nombreTipoVenta;
	
	@Column (name = "estado_tipo_venta")
	private String estado;

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
	
	
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

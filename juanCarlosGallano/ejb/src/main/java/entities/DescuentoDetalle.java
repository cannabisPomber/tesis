package entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "descuento_detalle")
@NamedQuery(name="DescuentoDetalle.findAll", query="SELECT p FROM descuento_detalle p")
public class DescuentoDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "descuento_detalle_generator")
	@SequenceGenerator(name="descuento_detalle_generator", sequenceName = "descuento_detalle_seq", allocationSize=1)
	@Column (name = "id_descuento")
	private Integer idDescuento;
	
	@Column (name = "porcentaje" , nullable = true)
	private Float porcentaje;
	
	@Column (name = "fecha_descuento")
	private Timestamp fechaDescuento;
	
	@Column (name = "monto_descuento" , nullable = true)
	private Long montoDescuento;

	public Integer getIdDescuento() {
		return idDescuento;
	}

	public void setIdDescuento(Integer idDescuento) {
		this.idDescuento = idDescuento;
	}

	public Float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Timestamp getFechaDescuento() {
		return fechaDescuento;
	}

	public void setFechaDescuento(Timestamp fechaDescuento) {
		this.fechaDescuento = fechaDescuento;
	}

	public Long getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(Long montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDescuento == null) ? 0 : idDescuento.hashCode());
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
		DescuentoDetalle other = (DescuentoDetalle) obj;
		if (idDescuento == null) {
			if (other.idDescuento != null)
				return false;
		} else if (!idDescuento.equals(other.idDescuento))
			return false;
		return true;
	}
	
	
}

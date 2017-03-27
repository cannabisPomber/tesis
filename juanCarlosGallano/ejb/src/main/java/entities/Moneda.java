package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity (name = "moneda")
@NamedQuery(name="Moneda.findAll", query="SELECT u FROM moneda u")
public class Moneda implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_moneda")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idMoneda;
	
	@Column(name="nombre_moneda", nullable = false)
	private String nombreMoneda;
	@Column(name="abreviatura_moneda", nullable = false)
	private String abreviaturaMoneda;
	
	@Column(name="cambio", nullable = false)
	private Double cambio;
	
	@Column(name="fecha_cotizacion", nullable = true)
	private Date fechaCotizacion;

	public Long getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getNombreMoneda() {
		return nombreMoneda;
	}

	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}

	public String getAbreviaturaMoneda() {
		return abreviaturaMoneda;
	}

	public void setAbreviaturaMoneda(String abreviaturaMoneda) {
		this.abreviaturaMoneda = abreviaturaMoneda;
	}

	public Double getCambio() {
		return cambio;
	}

	public void setCambio(Double cambio) {
		this.cambio = cambio;
	}

	public Date getFechaCotizacion() {
		return fechaCotizacion;
	}

	public void setFechaCotizacion(Date fechaCotizacion) {
		this.fechaCotizacion = fechaCotizacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMoneda == null) ? 0 : idMoneda.hashCode());
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
		Moneda other = (Moneda) obj;
		if (idMoneda == null) {
			if (other.idMoneda != null)
				return false;
		} else if (!idMoneda.equals(other.idMoneda))
			return false;
		return true;
	}
	
	
	

}

package entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "caja")
@NamedQuery(name="Caja.findAll", query="SELECT p FROM caja p")
public class Caja {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "id_caja")
	private Long idCaja;
	
	@Column (name = "fecha_inicio", nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Column (name = "fecha_fin", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	@Column (name = "monto_caja", nullable = false)
	private Long montoCaja;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	public Long getIdCaja() {
		return idCaja;
	}

	public void setIdCaja(Long idCaja) {
		this.idCaja = idCaja;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getMontoCaja() {
		return montoCaja;
	}

	public void setMontoCaja(Long montoCaja) {
		this.montoCaja = montoCaja;
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCaja == null) ? 0 : idCaja.hashCode());
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
		Caja other = (Caja) obj;
		if (idCaja == null) {
			if (other.idCaja != null)
				return false;
		} else if (!idCaja.equals(other.idCaja))
			return false;
		return true;
	}
	
	
	
}

package entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity (name = "cotizacion_cabecera")
@NamedQuery(name="CotizacionCabecera.findAll", query="SELECT p FROM cotizacion_cabecera p")
public class CotizacionCabecera implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Cotizacion_generator")
	@SequenceGenerator(name="id_Cotizacion_generator", sequenceName = "id_Cotizacion_seq", allocationSize=1)
	@Column (name = "id_Cotizacion_Cabecera")
	private Long idCotizacionCabecera;
	
	@Column (name = "fecha_cotizacion", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaCotizacion;
	
	@Column (name = "fecha_carga_cotizacion", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaCargaCotizacion;
	
	@Column (name = "fecha_adjudicar_cotizacion", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaAdjudicarCotizacion;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_cotizacion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioCotizacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_carga_cotizacion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioCargarCotizacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_adjudica_cotizacion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioAdjudicarCotizacion;
	
	@Column (name = "estado_cotizacion", nullable = false)
	private String estado;

	public Long getIdCotizacionCabecera() {
		return idCotizacionCabecera;
	}

	public void setIdCotizacionCabecera(Long idCotizacionCabecera) {
		this.idCotizacionCabecera = idCotizacionCabecera;
	}

	public Date getFechaCotizacion() {
		return fechaCotizacion;
	}

	public void setFechaCotizacion(Date fechaCotizacion) {
		this.fechaCotizacion = fechaCotizacion;
	}

	public Date getFechaCargaCotizacion() {
		return fechaCargaCotizacion;
	}

	public void setFechaCargaCotizacion(Date fechaCargaCotizacion) {
		this.fechaCargaCotizacion = fechaCargaCotizacion;
	}

	public Date getFechaAdjudicarCotizacion() {
		return fechaAdjudicarCotizacion;
	}

	public void setFechaAdjudicarCotizacion(Date fechaAdjudicarCotizacion) {
		this.fechaAdjudicarCotizacion = fechaAdjudicarCotizacion;
	}

	public Usuario getUsuarioCotizacion() {
		return usuarioCotizacion;
	}

	public void setUsuarioCotizacion(Usuario usuarioCotizacion) {
		this.usuarioCotizacion = usuarioCotizacion;
	}

	public Usuario getUsuarioCargarCotizacion() {
		return usuarioCargarCotizacion;
	}

	public void setUsuarioCargarCotizacion(Usuario usuarioCargarCotizacion) {
		this.usuarioCargarCotizacion = usuarioCargarCotizacion;
	}

	public Usuario getUsuarioAdjudicarCotizacion() {
		return usuarioAdjudicarCotizacion;
	}

	public void setUsuarioAdjudicarCotizacion(Usuario usuarioAdjudicarCotizacion) {
		this.usuarioAdjudicarCotizacion = usuarioAdjudicarCotizacion;
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
		int result = 1;
		result = prime * result + ((idCotizacionCabecera == null) ? 0 : idCotizacionCabecera.hashCode());
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
		CotizacionCabecera other = (CotizacionCabecera) obj;
		if (idCotizacionCabecera == null) {
			if (other.idCotizacionCabecera != null)
				return false;
		} else if (!idCotizacionCabecera.equals(other.idCotizacionCabecera))
			return false;
		return true;
	}
	
	

}

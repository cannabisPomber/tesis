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
@Entity (name = "caja_detalle")
@NamedQuery(name="CajaDetalle.findAll", query="SELECT p FROM caja_detalle p")
public class CajaDetalle implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caja_detalle_generator")
	@SequenceGenerator(name="caja_detalle_generator", sequenceName = "caja_detalle_seq", allocationSize=1)
	@Column (name = "id_caja_detalle")
	private Long idCajaDetalle;
	
	@Column (name = "observacion", nullable = true)
	private String observacion;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_caja", nullable = false)
	private Caja caja;
	
	@Column (name = "monto_ingreso", nullable = true)
	private Long montoIngreso;
	
	@Column (name = "monto_egreso", nullable = true)
	private Long montoEgreso;
	
	@Column (name = "nro_factura_proveedor", nullable = true)
	private String nroFacturaProveedor;
	
	@Column (name = "monto_perdida", nullable = true)
	private Long montoPerdida;
	
	@Column (name = "monto_retirado", nullable = true)
	private Long montoRetirado;
	
	@Column (name = "fecha_hora", nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaHora;

	public Long getIdCajaDetalle() {
		return idCajaDetalle;
	}

	public void setIdCajaDetalle(Long idCajaDetalle) {
		this.idCajaDetalle = idCajaDetalle;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Long getMontoIngreso() {
		return montoIngreso;
	}

	public void setMontoIngreso(Long montoIngreso) {
		this.montoIngreso = montoIngreso;
	}

	public Long getMontoEgreso() {
		return montoEgreso;
	}

	public void setMontoEgreso(Long montoEgreso) {
		this.montoEgreso = montoEgreso;
	}

	public String getNroFacturaProveedor() {
		return nroFacturaProveedor;
	}

	public void setNroFacturaProveedor(String nroFacturaProveedor) {
		this.nroFacturaProveedor = nroFacturaProveedor;
	}

	public Long getMontoPerdida() {
		return montoPerdida;
	}

	public void setMontoPerdida(Long montoPerdida) {
		this.montoPerdida = montoPerdida;
	}

	public Long getMontoRetirado() {
		return montoRetirado;
	}

	public void setMontoRetirado(Long montoRetirado) {
		this.montoRetirado = montoRetirado;
	}
	

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCajaDetalle == null) ? 0 : idCajaDetalle.hashCode());
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
		CajaDetalle other = (CajaDetalle) obj;
		if (idCajaDetalle == null) {
			if (other.idCajaDetalle != null)
				return false;
		} else if (!idCajaDetalle.equals(other.idCajaDetalle))
			return false;
		return true;
	}
	
	
	

}

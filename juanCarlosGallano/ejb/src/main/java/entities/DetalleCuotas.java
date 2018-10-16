package entities;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity (name =  "detalle_cuotas")
@NamedQuery(name="DetalleCuotas.findAll", query="SELECT p FROM detalle_cuotas p")
public class DetalleCuotas implements Serializable  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DetalleCuota_generator")
	@SequenceGenerator(name="DetalleCuota_generator", sequenceName = "DetalleCuota_seq", allocationSize=1)
	@Column (name = "id_detalle_cuotas")
	private Long idDetalleCuota;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_factura", nullable = false)
	private Factura factura;
	
	@Column (name = "monto_pago", nullable = false)
	private BigDecimal montoPago;
	
	@Column (name = "fecha_vencimiento", nullable = false)
	@Temporal (TemporalType.DATE)
	private Date fechaVencimiento;
	
	@Column (name = "fecha_pago", nullable = true)
	@Temporal (TemporalType.DATE)
	private Date fechaPago;
	
	@Column (name = "interes", nullable = true)
	private Long interes;

	public Long getIdDetalleCuota() {
		return idDetalleCuota;
	}

	public void setIdDetalleCuota(Long idDetalleCuota) {
		this.idDetalleCuota = idDetalleCuota;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}


	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Long getInteres() {
		return interes;
	}

	public void setInteres(Long interes) {
		this.interes = interes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleCuota == null) ? 0 : idDetalleCuota.hashCode());
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
		DetalleCuotas other = (DetalleCuotas) obj;
		if (idDetalleCuota == null) {
			if (other.idDetalleCuota != null)
				return false;
		} else if (!idDetalleCuota.equals(other.idDetalleCuota))
			return false;
		return true;
	}
	
	

}

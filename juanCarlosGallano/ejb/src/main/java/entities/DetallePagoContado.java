package entities;

import java.io.Serializable;

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

@Entity (name = "detalle_pago_contado")
@NamedQuery(name="DetallePagoContado.findAll", query="SELECT p FROM detalle_pago_contado p")

public class DetallePagoContado implements Serializable {
	
	@Id
	@Column(name="id_detalle_pago_contado")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_pago_contado_generator")
	@SequenceGenerator(name="detalle_pago_contado_generator", sequenceName = "detalle_pago_contado_seq", allocationSize=1)
	private Long idDetallePagoContado;
	
	@Column(name="tipo_pago")
	private String tipoPago;
	
	@Column(name="tipo_facturacion")
	private String tipoFacturacion;
	
	@Column(name="monto")
	private Long monto;
	
	@Column(name="vuelto")
	private Long vuelto;
	
	@Column(name="numero_tarjeta", nullable = true)
	private String numeroTarjeta;
	
	@Column(name="numero_voucher", nullable = true)
	private String numeroVoucher;
	
	@Column(name="marca_tarjeta", nullable = true)
	private String marcaTarjeta;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idFactura", nullable = false)
	private Factura factura;
	
	@Column(name = "cuota")
	private Long cuota;
	

	public Long getIdDetallePagoContado() {
		return idDetallePagoContado;
	}

	public void setIdDetallePagoContado(Long idDetallePagoContado) {
		this.idDetallePagoContado = idDetallePagoContado;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Long getMonto() {
		return monto;
	}

	public void setMonto(Long monto) {
		this.monto = monto;
	}

	public Long getCuota() {
		return cuota;
	}

	public void setCuota(Long cuota) {
		this.cuota = cuota;
	}

	public String getTipoFacturacion() {
		return tipoFacturacion;
	}

	public void setTipoFacturacion(String tipoFacturacion) {
		this.tipoFacturacion = tipoFacturacion;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	

	public String getNumeroVoucher() {
		return numeroVoucher;
	}

	public void setNumeroVoucher(String numeroVoucher) {
		this.numeroVoucher = numeroVoucher;
	}

	public String getMarcaTarjeta() {
		return marcaTarjeta;
	}

	public void setMarcaTarjeta(String marcaTarjeta) {
		this.marcaTarjeta = marcaTarjeta;
	}

	public Long getVuelto() {
		return vuelto;
	}

	public void setVuelto(Long vuelto) {
		this.vuelto = vuelto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetallePagoContado == null) ? 0 : idDetallePagoContado.hashCode());
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
		DetallePagoContado other = (DetallePagoContado) obj;
		if (idDetallePagoContado == null) {
			if (other.idDetallePagoContado != null)
				return false;
		} else if (!idDetallePagoContado.equals(other.idDetallePagoContado))
			return false;
		return true;
	}
	
	

}

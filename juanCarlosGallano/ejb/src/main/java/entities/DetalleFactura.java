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

@Entity (name  = "detalle_factura")
@NamedQuery(name="DetalleFactura.findAll", query="SELECT p FROM detalle_factura p")
public class DetalleFactura implements Serializable {

	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id_detalle_factura")
	private Long idDetalleFactura;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "nroFactura", nullable = false)
	private Factura factura; 
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@Column (name = "codigo_barra" , nullable = false)
	private String codigoBarra;
	
	@Column (name = "monto_iva" , nullable = false)
	private Double montoIva;

	public Long getIdDetalleFactura() {
		return idDetalleFactura;
	}

	public void setIdDetalleFactura(Long idDetalleFactura) {
		this.idDetalleFactura = idDetalleFactura;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Double getMontoIva() {
		return montoIva;
	}

	public void setMontoIva(Double montoIva) {
		this.montoIva = montoIva;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idDetalleFactura == null) ? 0 : idDetalleFactura.hashCode());
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
		DetalleFactura other = (DetalleFactura) obj;
		if (idDetalleFactura == null) {
			if (other.idDetalleFactura != null)
				return false;
		} else if (!idDetalleFactura.equals(other.idDetalleFactura))
			return false;
		return true;
	}
	
	 
	
}

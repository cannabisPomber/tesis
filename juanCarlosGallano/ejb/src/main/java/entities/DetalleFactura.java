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

@Entity (name  = "detalle_factura")
@NamedQuery(name="DetalleFactura.findAll", query="SELECT p FROM detalle_factura p")
public class DetalleFactura implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_factura_generator")
	@SequenceGenerator(name="detalle_factura_generator", sequenceName = "detalle_factura_seq", allocationSize=1)
	@Column (name = "id_detalle_factura")
	private Long idDetalleFactura;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idFactura", nullable = false)
	private Factura factura; 
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@Column (name = "codigo_barra" , nullable = true)
	private String codigoBarra;
	
	// Se obtiene de la suma de cantidad * precio + (cantidad*precio)*montoIVA
	@Column (name = "importe" , nullable = false)
	private Double importe;
	
	@Column (name = "precio_unitario" , nullable = false)
	private Double precioUnitario;
	
	@Column (name = "cantidad" , nullable = false)
	private Double cantidad;

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


	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
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

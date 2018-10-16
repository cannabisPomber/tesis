package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity (name = "detalle_orden_compra")
@NamedQuery(name="DetalleOrdenCompra.findAll", query="SELECT p FROM detalle_orden_compra p")
public class DetalleOrdenCompra implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_orden_compra_generator")
	@SequenceGenerator(name="detalle_orden_compra_generator", sequenceName = "detalle_orden_compra_seq", allocationSize=1)
	@Column (name = "id_detalle_orden_compra")
	private Long idDetalleOrdenCompra;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
	@JoinColumn(name = "id_orden_compra", nullable = false)
	private OrdenCompra ordenCompra;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@Column (name = "cantidad" ,  nullable = true)
	private Long cantidad;
	
	@Column (name = "cantidad_recibida" ,  nullable = true)
	private Long cantidadRecibida;
	
	@Column (name = "precio_compra" ,  nullable = false)
	private Long precioCompra;
	
	@Column (name = "fecha_vencimiento" , nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date vencimiento;
	
	@Column (name = "estado" ,  nullable = true)
	private String estado;

	public Long getIdDetalleOrdenCompra() {
		return idDetalleOrdenCompra;
	}

	public void setIdDetalleOrdenCompra(Long idDetalleOrdenCompra) {
		this.idDetalleOrdenCompra = idDetalleOrdenCompra;
	}

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	
	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Long precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Long getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(Long cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
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
		result = prime
				* result
				+ ((idDetalleOrdenCompra == null) ? 0 : idDetalleOrdenCompra
						.hashCode());
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
		DetalleOrdenCompra other = (DetalleOrdenCompra) obj;
		if (idDetalleOrdenCompra == null) {
			if (other.idDetalleOrdenCompra != null)
				return false;
		} else if (!idDetalleOrdenCompra.equals(other.idDetalleOrdenCompra))
			return false;
		return true;
	}

	
	
}

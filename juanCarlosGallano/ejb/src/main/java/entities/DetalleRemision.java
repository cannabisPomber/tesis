package entities;

import javax.enterprise.inject.Default;
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

@Entity (name =  "detalle_remision")
@NamedQuery(name="DetalleRemision.findAll", query="SELECT p FROM detalle_remision p")
public class DetalleRemision {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DetalleRemision_generator")
	@SequenceGenerator(name="DetalleRemision_generator", sequenceName = "DetalleRemision_seq", allocationSize=1)
	@Column (name = "id_remision_detalle")
	private Long idRemisionDetalle;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_remision", nullable = false ,referencedColumnName = "id_remision")
	private Remision remision;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_producto", nullable = false ,referencedColumnName = "id_producto")
	private Producto producto;
	@Column(name = "cantidad")
	private Long cantidad;
	
	@Column(name = "cantidad_recibida", nullable = true)
	private Long cantidadRecibida;
	@Column(name = "precio_unitario")
	private Long precioUnitario;
	public Long getIdRemisionDetalle() {
		return idRemisionDetalle;
	}
	public void setIdRemisionDetalle(Long idRemisionDetalle) {
		this.idRemisionDetalle = idRemisionDetalle;
	}
	public Remision getRemision() {
		return remision;
	}
	public void setRemision(Remision remision) {
		this.remision = remision;
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
	public Long getPrecioUnitario() {
		return precioUnitario;
	}
	
	public Long getCantidadRecibida() {
		return cantidadRecibida;
	}
	public void setCantidadRecibida(Long cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}
	public void setPrecioUnitario(Long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRemisionDetalle == null) ? 0 : idRemisionDetalle.hashCode());
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
		DetalleRemision other = (DetalleRemision) obj;
		if (idRemisionDetalle == null) {
			if (other.idRemisionDetalle != null)
				return false;
		} else if (!idRemisionDetalle.equals(other.idRemisionDetalle))
			return false;
		return true;
	}
	
	

}

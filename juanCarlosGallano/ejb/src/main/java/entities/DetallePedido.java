package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity (name = "detalle_pedido")
@NamedQuery(name="DetallePedido.findAll", query="SELECT p FROM detalle_pedido p")
public class DetallePedido {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id_detalle_pedido")
	private Long idDetallePedido;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_pedido", nullable = false)
	private Pedido pedido;
	
	@Column (name = "cantidad" , nullable = false)
	private Long cantidad;
	
	@Column (name = "cantidad_ofrecida" , nullable = true)
	private Long cantidad_ofrecida;
	
	@Column (name = "precio" , nullable = false)
	private Long precio;
	
	@Column (name = "precio_ofrecido" , nullable = true)
	private Long precio_ofrecido;

	public Long getIdDetallePedido() {
		return idDetallePedido;
	}

	public void setIdDetallePedido(Long idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCantidad_ofrecida() {
		return cantidad_ofrecida;
	}

	public void setCantidad_ofrecida(Long cantidad_ofrecida) {
		this.cantidad_ofrecida = cantidad_ofrecida;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public Long getPrecio_ofrecido() {
		return precio_ofrecido;
	}

	public void setPrecio_ofrecido(Long precio_ofrecido) {
		this.precio_ofrecido = precio_ofrecido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDetallePedido == null) ? 0 : idDetallePedido.hashCode());
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
		DetallePedido other = (DetallePedido) obj;
		if (idDetallePedido == null) {
			if (other.idDetallePedido != null)
				return false;
		} else if (!idDetallePedido.equals(other.idDetallePedido))
			return false;
		return true;
	}
	
	

}

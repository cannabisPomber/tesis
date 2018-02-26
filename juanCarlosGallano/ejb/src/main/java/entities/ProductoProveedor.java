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
import javax.validation.constraints.NotNull;
@Entity (name = "producto_proveedor")
@NamedQuery(name="ProductoProveedor.findAll", query="SELECT u FROM producto_proveedor u")
public class ProductoProveedor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_producto_proveedor")
	private Long idProductoProveedor;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_proveedor", nullable = false)
	private Proveedor proveedor;
	
	@Column(name = "precio_compra", nullable =  false)
	private Long precioCompra;
	
	@Column(name = "estado", nullable =  false)
	private String estado;

	public Long getIdProductoProveedor() {
		return idProductoProveedor;
	}

	public void setIdProductoProveedor(Long idProductoProveedor) {
		this.idProductoProveedor = idProductoProveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Long precio_compra) {
		this.precioCompra = precio_compra;
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
		result = prime * result + ((idProductoProveedor == null) ? 0 : idProductoProveedor.hashCode());
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
		ProductoProveedor other = (ProductoProveedor) obj;
		if (idProductoProveedor == null) {
			if (other.idProductoProveedor != null)
				return false;
		} else if (!idProductoProveedor.equals(other.idProductoProveedor))
			return false;
		return true;
	}
	
	

}

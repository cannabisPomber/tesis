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

import entities.Producto;
import entities.Proveedor;
@Entity (name = "preferencia_proveedor")
@NamedQuery(name="PreferenciaProveedor.findAll", query="SELECT u FROM preferencia_proveedor u")
public class PreferenciaProveedor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_preferencia_proveedor")
	private Long idPreferenciaProveedor;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_proveedor", nullable = false)
	private Proveedor proveedor;
	

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPreferenciaProveedor == null) ? 0 : idPreferenciaProveedor.hashCode());
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
		PreferenciaProveedor other = (PreferenciaProveedor) obj;
		if (idPreferenciaProveedor == null) {
			if (other.idPreferenciaProveedor != null)
				return false;
		} else if (!idPreferenciaProveedor.equals(other.idPreferenciaProveedor))
			return false;
		return true;
	}


}


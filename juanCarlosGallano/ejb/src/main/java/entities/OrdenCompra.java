package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity (name = "orden_compra")
@NamedQuery(name="OrdenCompra.findAll", query="SELECT p FROM orden_compra p")
public class OrdenCompra implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id_orden_compra")
	private Long idOrdenCompra;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_pedido", nullable = false)
	private Pedido pedido;
	
	@Column (name = "estado" , nullable = false)
	private String estado;
	
	@Column (name = "nro_factura_proveedor" ,  nullable = true)
	private String nroFacturaProveedor;

	public Long getIdOrdenCompra() {
		return idOrdenCompra;
	}

	public void setIdOrdenCompra(Long idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNroFacturaProveedor() {
		return nroFacturaProveedor;
	}

	public void setNroFacturaProveedor(String nroFacturaProveedor) {
		this.nroFacturaProveedor = nroFacturaProveedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idOrdenCompra == null) ? 0 : idOrdenCompra.hashCode());
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
		OrdenCompra other = (OrdenCompra) obj;
		if (idOrdenCompra == null) {
			if (other.idOrdenCompra != null)
				return false;
		} else if (!idOrdenCompra.equals(other.idOrdenCompra))
			return false;
		return true;
	}
	
	

}

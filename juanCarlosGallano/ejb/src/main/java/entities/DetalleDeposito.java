package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "detalle_deposito")
@NamedQuery(name="DetalleDeposito.findAll", query="SELECT p FROM detalle_deposito p")
public class DetalleDeposito implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle_deposito")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_deposito_generator")
	@SequenceGenerator(name="detalle_deposito_generator", sequenceName = "detalle_deposito_seq", allocationSize=1)
	private Long idDetalleDeposito;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_deposito", nullable = false)
	private Deposito deposito;
	
	@Column(name="cantidad_producto")
	private Long cantidadProducto;

	public Long getIdDetalleDeposito() {
		return idDetalleDeposito;
	}

	public void setIdDetalleDeposito(Long idDetalleDeposito) {
		this.idDetalleDeposito = idDetalleDeposito;
	}

	

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Long getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(Long cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleDeposito == null) ? 0 : idDetalleDeposito.hashCode());
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
		DetalleDeposito other = (DetalleDeposito) obj;
		if (idDetalleDeposito == null) {
			if (other.idDetalleDeposito != null)
				return false;
		} else if (!idDetalleDeposito.equals(other.idDetalleDeposito))
			return false;
		return true;
	}
	
	

}


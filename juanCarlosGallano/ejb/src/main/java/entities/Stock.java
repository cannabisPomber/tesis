package entities;

import java.io.Serializable;
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

@Entity (name = "stock")
@NamedQuery(name="Stock.findAll", query="SELECT p FROM stock p")
public class Stock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_stock")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_generator")
	@SequenceGenerator(name="stock_generator", sequenceName = "stock_seq", allocationSize=1)
	private Long idStock;
	
	@Column(name = "codigo_barrra")
	private String codigoBarra;
	
	
	private Producto producto;
	@Column(name = "cantidad_stock")
	private Long cantidadStock;
	
	

	public Long getIdStock() {
		return idStock;
	}

	public void setIdStock(Long idStock) {
		this.idStock = idStock;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(Long cantidadStock) {
		this.cantidadStock = cantidadStock;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idStock == null) ? 0 : idStock.hashCode());
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
		Stock other = (Stock) obj;
		if (idStock == null) {
			if (other.idStock != null)
				return false;
		} else if (!idStock.equals(other.idStock))
			return false;
		return true;
	}
	
	
}

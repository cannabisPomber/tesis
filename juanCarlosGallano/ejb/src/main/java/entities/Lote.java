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

@Entity (name = "lote")
@NamedQuery(name="Lote.findAll", query="SELECT p FROM lote p")
public class Lote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_lote")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lote_generator")
	@SequenceGenerator(name="lote_generator", sequenceName = "lote_seq", allocationSize=1)
	private Long idLote;

	@Column (name = "fecha_vencimiento" , nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaVencimiento;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
	@JoinColumn(name = "id_stock", nullable = false)
	private Stock stock;
	
	@Column (name = "cantidad_vendida" , nullable = false)
	private Long cantidadVendida;

	public Long getIdLote() {
		return idLote;
	}

	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Long getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(Long cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLote == null) ? 0 : idLote.hashCode());
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
		Lote other = (Lote) obj;
		if (idLote == null) {
			if (other.idLote != null)
				return false;
		} else if (!idLote.equals(other.idLote))
			return false;
		return true;
	}
	
	

}

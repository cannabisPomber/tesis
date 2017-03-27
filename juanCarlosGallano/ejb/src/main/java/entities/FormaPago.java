package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
@Entity (name = "forma_pago")
@NamedQuery(name="FormaPago.findAll", query="SELECT u FROM forma_pago u")
public class FormaPago implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_forma_pago")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idFormaPago;
	
	@Column(name ="descripcion", length = 60)
	@NotNull
	private String descripcion;
	@Column(name ="abreviatura", length = 5)
	@NotNull
	private String abreviatura;

	public Long getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Long idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFormaPago == null) ? 0 : idFormaPago.hashCode());
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
		FormaPago other = (FormaPago) obj;
		if (idFormaPago == null) {
			if (other.idFormaPago != null)
				return false;
		} else if (!idFormaPago.equals(other.idFormaPago))
			return false;
		return true;
	}
	
	
}

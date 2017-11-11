package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forma_pago_generator")
	@SequenceGenerator(name="forma_pago_generator", sequenceName = "forma_pago_seq", allocationSize=1)
	private Long idFormaPago;
	
	@Column(name ="nombre", length = 60)
	@NotNull
	private String nombreFormaPago;
	
	@Column(name ="descripcion", length = 60)
	private String descripcion;
	
	@Column (name = "estado_forma_pago",  length = 30)
	@NotNull
	private String estadoFormaPago;

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
	

	public String getEstadoFormaPago() {
		return estadoFormaPago;
	}

	public void setEstadoFormaPago(String estadoFormaPago) {
		this.estadoFormaPago = estadoFormaPago;
	}
	
	public String getNombreFormaPago() {
		return nombreFormaPago;
	}

	public void setNombreFormaPago(String nombreFormaPago) {
		this.nombreFormaPago = nombreFormaPago;
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

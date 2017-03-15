package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity (name = "deposito")
@NamedQuery(name="Deposito.findAll", query="SELECT u FROM deposito u")
public class Deposito implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_deposito")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idDeposito;
	
	@Column (name = "nombre_deposito")
	@NotNull
	private String nombreDeposito;
	
	@Column (name = "estadoDeposito")
	@NotNull
	private String estado;

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public String getNombreDeposito() {
		return nombreDeposito;
	}

	public void setNombreDeposito(String nombreDeposito) {
		this.nombreDeposito = nombreDeposito;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idDeposito ^ (idDeposito >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deposito other = (Deposito) obj;
		if (idDeposito != other.idDeposito)
			return false;
		return true;
	}
	
}

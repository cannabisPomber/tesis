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

@Entity (name = "arqueo_caja")
@NamedQuery(name="ArqueoCaja.findAll", query="SELECT p FROM arqueo_caja p")
public class ArqueoCaja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "id_arqueo_caja")
	private Long idArqueoCaja;
	
	@Column (name = "monto_faltante", nullable= false)
	private Long montoFaltante;
	
	@Column (name = "monto_sobrante", nullable= false)
	private Long montoSobrante;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_caja", nullable = false)
	private Caja caja;

	public Long getIdArqueoCaja() {
		return idArqueoCaja;
	}

	public void setIdArqueoCaja(Long idArqueoCaja) {
		this.idArqueoCaja = idArqueoCaja;
	}

	public Long getMontoFaltante() {
		return montoFaltante;
	}

	public void setMontoFaltante(Long montoFaltante) {
		this.montoFaltante = montoFaltante;
	}

	public Long getMontoSobrante() {
		return montoSobrante;
	}

	public void setMontoSobrante(Long montoSobrante) {
		this.montoSobrante = montoSobrante;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idArqueoCaja == null) ? 0 : idArqueoCaja.hashCode());
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
		ArqueoCaja other = (ArqueoCaja) obj;
		if (idArqueoCaja == null) {
			if (other.idArqueoCaja != null)
				return false;
		} else if (!idArqueoCaja.equals(other.idArqueoCaja))
			return false;
		return true;
	}
	
}

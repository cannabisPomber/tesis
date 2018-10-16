package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity (name = "ruc_empresas")
@NamedQuery(name="RucEmpresas.findAll", query="SELECT u FROM ruc_empresas u")
public class RucEmpresas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name="id_ruc_empresa_generator", sequenceName = "id_ruc_empresa_seq", allocationSize=1)
	@Column(name = "id_ruc_empresa")
	private Long idRucEmpresas;
	
	@Column(name = "ruc", length = 60, nullable = false)
	private String ruc;
	
	@Column(name = "empresa", length = 250, nullable = false)
	private String empresa;

	public Long getIdRucEmpresas() {
		return idRucEmpresas;
	}

	public void setIdRucEmpresas(Long idRucEmpresas) {
		this.idRucEmpresas = idRucEmpresas;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRucEmpresas == null) ? 0 : idRucEmpresas.hashCode());
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
		RucEmpresas other = (RucEmpresas) obj;
		if (idRucEmpresas == null) {
			if (other.idRucEmpresas != null)
				return false;
		} else if (!idRucEmpresas.equals(other.idRucEmpresas))
			return false;
		return true;
	}
	
	

}

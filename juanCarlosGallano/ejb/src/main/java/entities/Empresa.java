package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "empresa")
@NamedQuery(name="empresa.findAll", query="SELECT u FROM empresa u")
public class Empresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_generator")
	@SequenceGenerator(name="empresa_generator", sequenceName = "empresa_seq", allocationSize=1)
	@Column(name = "id_empresa")
	private Long idEmpresa;
	
	@Column(name = "nombre_empresa", nullable = false, length = 100)
	private String nombreEmpresa;
	
	@Column(name = "ruc", nullable = false, length = 20)
	private String ruc;
	
	@Column(name = "timbrado", nullable = false, length = 20)
	private String timbrado;
	
	@Column(name = "fecha_inicio_timbrado", nullable = false)
	@Temporal (TemporalType.DATE)
	private Date inicioTimbrado;
	
	@Column(name = "fecha_venc_timbrado", nullable = false)
	@Temporal (TemporalType.DATE)
	private Date vencimientoTimbrado;
	
	@Column(name = "telefono_empresa", nullable = false, length = 100)
	private String telefono;
	
	@Column(name = "local", nullable = false, length = 100)
	private String local;
	
	@Column(name = "direccion", nullable = false, length = 150)
	private String direccion;
	

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getInicioTimbrado() {
		return inicioTimbrado;
	}

	public void setInicioTimbrado(Date inicioTimbrado) {
		this.inicioTimbrado = inicioTimbrado;
	}

	public Date getVencimientoTimbrado() {
		return vencimientoTimbrado;
	}

	public void setVencimientoTimbrado(Date vencimientoTimbrado) {
		this.vencimientoTimbrado = vencimientoTimbrado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
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
		Empresa other = (Empresa) obj;
		if (idEmpresa == null) {
			if (other.idEmpresa != null)
				return false;
		} else if (!idEmpresa.equals(other.idEmpresa))
			return false;
		return true;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	
	
	
	
	

}

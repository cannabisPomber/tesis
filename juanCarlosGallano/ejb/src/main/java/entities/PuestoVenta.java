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
import javax.persistence.SequenceGenerator;

@Entity (name =  "puesto_venta")
@NamedQuery(name="PuestoVenta.findAll", query="SELECT p FROM puesto_venta p")
public class PuestoVenta {
	
	
private static final long serialVersionUID = 1L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "puesto_venta_generator")
	@SequenceGenerator(name="puesto_venta_generator", sequenceName = "puesto_venta_seq", allocationSize=1)
	@Column (name = "id_puesto_venta")
	private Long idPuestoVenta;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa sucursal;
	
	@Column (name = "nombre_puesto_venta", length = 60)
	private String nombrePuestoVenta;
	
	@Column (name = "nro_sucursal", length = 3)
	private String nroSucursal;
	
	@Column (name = "nro_puesto_venta", length = 3)
	private String nroPuestoVenta;
	
	@Column (name = "nro_secuencia", length = 7)
	private String nroSecuencia;
	
	@Column (name = "asignado", nullable = true)
	private Boolean asignado;
	
	@Column (name = "caja_tesoreria", nullable = true)
	private Boolean cajaTesoreria;
	

	public Long getIdPuestoVenta() {
		return idPuestoVenta;
	}

	public void setIdPuestoVenta(Long idPuestoVenta) {
		this.idPuestoVenta = idPuestoVenta;
	}

	public Empresa getSucursal() {
		return sucursal;
	}

	public void setSucursal(Empresa sucursal) {
		this.sucursal = sucursal;
	}

	public String getNombrePuestoVenta() {
		return nombrePuestoVenta;
	}

	public void setNombrePuestoVenta(String nombrePuestoVenta) {
		this.nombrePuestoVenta = nombrePuestoVenta;
	}

	public String getNroSucursal() {
		return nroSucursal;
	}

	public void setNroSucursal(String nroSucursal) {
		this.nroSucursal = nroSucursal;
	}

	public String getNroPuestoVenta() {
		return nroPuestoVenta;
	}

	public void setNroPuestoVenta(String nroPuestoVenta) {
		this.nroPuestoVenta = nroPuestoVenta;
	}

	public String getNroSecuencia() {
		return nroSecuencia;
	}

	public void setNroSecuencia(String nroSecuencia) {
		this.nroSecuencia = nroSecuencia;
	}

	public Boolean getAsignado() {
		return asignado;
	}

	public void setAsignado(Boolean asignado) {
		this.asignado = asignado;
	}

	public Boolean getCajaTesoreria() {
		return cajaTesoreria;
	}

	public void setCajaTesoreria(Boolean cajaTesoreria) {
		this.cajaTesoreria = cajaTesoreria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPuestoVenta == null) ? 0 : idPuestoVenta.hashCode());
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
		PuestoVenta other = (PuestoVenta) obj;
		if (idPuestoVenta == null) {
			if (other.idPuestoVenta != null)
				return false;
		} else if (!idPuestoVenta.equals(other.idPuestoVenta))
			return false;
		return true;
	}
	
	

}

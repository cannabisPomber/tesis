package entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "remision")
@NamedQuery(name="Remision.findAll", query="SELECT p FROM remision p")
public class Remision implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_remision")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remision_generator")
	@SequenceGenerator(name="remision_generator", sequenceName = "remision_seq", allocationSize=1)
	private Long idRemision;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_cliente", nullable = true)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_creacion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioCreacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_recepcion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioCierre;
	
	@Column (name = "fecha_remision", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaRemision;
	
	@Column (name = "ruc", nullable = true)
	private String ruc;
	
	@Column (name = "dias_consignacion", nullable = true)
	private Long diasConsignacion;
	
	@Column (name = "cliente_nombre", nullable = true)
	private String clienteNombre;
	
	@Column (name = "fecha_devolucion", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaDevolucion;

	public Long getIdRemision() {
		return idRemision;
	}

	public void setIdRemision(Long idRemision) {
		this.idRemision = idRemision;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Usuario getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Usuario usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Long getDiasConsignacion() {
		return diasConsignacion;
	}

	public void setDiasConsignacion(Long diasConsignacion) {
		this.diasConsignacion = diasConsignacion;
	}

	public Usuario getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(Usuario usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

	public Date getFechaRemision() {
		return fechaRemision;
	}

	public void setFechaRemision(Date fechaRemision) {
		this.fechaRemision = fechaRemision;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	
	
	

}

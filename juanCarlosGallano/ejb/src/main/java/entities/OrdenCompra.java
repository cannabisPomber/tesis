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

@Entity (name = "orden_compra")
@NamedQuery(name="OrdenCompra.findAll", query="SELECT p FROM orden_compra p")
public class OrdenCompra implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orden_compra_generator")
	@SequenceGenerator(name="orden_compra_generator", sequenceName = "orden_compra_seq", allocationSize=1)
	@Column (name = "id_orden_compra")
	private Long idOrdenCompra;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_proveedor", nullable = false)
	private Proveedor proveedor;
	
	@Column (name = "fecha_pedido" , nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaPedido;
	
	@Column (name = "fecha_aprobacion" , nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaAprobacion;
	
	@Column (name = "fecha_recepcion" , nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaRecepcion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_pedido", nullable = true,referencedColumnName = "id_usuario")
	private Usuario usuarioPedido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprobacion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioAprobacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_recepcion", nullable = true ,referencedColumnName = "id_usuario")
	private Usuario usuarioRecepcion;
	
	@Column (name = "estado" , nullable = false)
	private String estado;
	
	@Column (name = "nro_factura_proveedor" , nullable = true)
	private String nroFacturaProveedor;
	
	@OneToMany(mappedBy = "ordenCompra",cascade = CascadeType.ALL)
	private List<DetalleOrdenCompra> listaDetalleOrdenCompra;
	

	public Long getIdOrdenCompra() {
		return idOrdenCompra;
	}

	public void setIdOrdenCompra(Long idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public List<DetalleOrdenCompra> getListaDetalleOrdenCompra() {
		return listaDetalleOrdenCompra;
	}

	public void setListaDetalleOrdenCompra(List<DetalleOrdenCompra> listaDetalleOrdenCompra) {
		this.listaDetalleOrdenCompra = listaDetalleOrdenCompra;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	

	public Usuario getUsuarioPedido() {
		return usuarioPedido;
	}

	public void setUsuarioPedido(Usuario usuarioPedido) {
		this.usuarioPedido = usuarioPedido;
	}

	public Usuario getUsuarioAprobacion() {
		return usuarioAprobacion;
	}

	public void setUsuarioAprobacion(Usuario usuarioAprobacion) {
		this.usuarioAprobacion = usuarioAprobacion;
	}

	public Usuario getUsuarioRecepcion() {
		return usuarioRecepcion;
	}

	public void setUsuarioRecepcion(Usuario usuarioRecepcion) {
		this.usuarioRecepcion = usuarioRecepcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idOrdenCompra == null) ? 0 : idOrdenCompra.hashCode());
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
		OrdenCompra other = (OrdenCompra) obj;
		if (idOrdenCompra == null) {
			if (other.idOrdenCompra != null)
				return false;
		} else if (!idOrdenCompra.equals(other.idOrdenCompra))
			return false;
		return true;
	}
	
	

}

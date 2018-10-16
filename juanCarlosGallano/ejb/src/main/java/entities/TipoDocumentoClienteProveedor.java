package entities;

import java.io.Serializable;

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

@Entity (name = "tipo_documento_cliente_proveedor")
@NamedQuery(name="TipoDocumentoClienteProveedor.findAll", query="SELECT p FROM tipo_documento_cliente_proveedor p")
public class TipoDocumentoClienteProveedor implements Serializable{
	
	@Id
	@Column(name="id_tipo_documento_cliente_proveedor")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_documento_cliente_proveedor_generator")
	@SequenceGenerator(name="tipo_documento_cliente_proveedor_generator", sequenceName = "tipo_documento_cliente_proveedor_seq", allocationSize=1)
	private Long idTipoDocumentoClienteProveedor;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_tipo_documento", nullable = false)
	private TipoDocumento tipoDocumento;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = true)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "id_proveedor", nullable = true)
	private Proveedor proveedor;
	
	@Column(name= "numero_documento", nullable = false)
	private String numeroDocumento;

	public Long getIdTipoDocumentoClienteProveedor() {
		return idTipoDocumentoClienteProveedor;
	}

	public void setIdTipoDocumentoClienteProveedor(Long idTipoDocumentoClienteProveedor) {
		this.idTipoDocumentoClienteProveedor = idTipoDocumentoClienteProveedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoDocumentoClienteProveedor == null) ? 0 : idTipoDocumentoClienteProveedor.hashCode());
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
		TipoDocumentoClienteProveedor other = (TipoDocumentoClienteProveedor) obj;
		if (idTipoDocumentoClienteProveedor == null) {
			if (other.idTipoDocumentoClienteProveedor != null)
				return false;
		} else if (!idTipoDocumentoClienteProveedor.equals(other.idTipoDocumentoClienteProveedor))
			return false;
		return true;
	}
	
	
	
}

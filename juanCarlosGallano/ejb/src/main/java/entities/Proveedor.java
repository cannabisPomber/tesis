package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity (name = "proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM proveedor p")
public class Proveedor {
	@Id
	@Column(name="id_proveedor")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proveedor_generator")
	@SequenceGenerator(name="proveedor_generator", sequenceName = "proveedor_seq", allocationSize=1)
	private Long idProveedor;
	
	@Column (name = "nombre_empresa")
	@NotNull
	private String nombreEmpresa;
	@Column (name = "telefono_empresa")
	@NotNull
	private String telefonoEmpresa;
	@Column (name = "contacto")
	private String contacto;
	@Column (name = "telefono_contacto")
	private String telefonoContacto;
	
	@Column (name = "direccion_proveedor", nullable = true)
	private String direccionProveedor;
	@Column (name = "estado")
	private String estado;
	
	@Column (name = "latitud" , nullable = false)
	private Float latitud;
	
	@Column (name = "longitud" , nullable = false)
	private Float longitud;
	@ManyToMany
	private List<Producto> productos;
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getTelefonoEmpresa() {
		return telefonoEmpresa;
	}
	public void setTelefonoEmpresa(String telefonoEmpresa) {
		this.telefonoEmpresa = telefonoEmpresa;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getTelefonoContacto() {
		return telefonoContacto;
	}
	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public Float getLatitud() {
		return latitud;
	}
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	public Float getLongitud() {
		return longitud;
	}
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}
	
	public String getDireccionProveedor() {
		return direccionProveedor;
	}
	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProveedor == null) ? 0 : idProveedor.hashCode());
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
		Proveedor other = (Proveedor) obj;
		if (idProveedor == null) {
			if (other.idProveedor != null)
				return false;
		} else if (!idProveedor.equals(other.idProveedor))
			return false;
		return true;
	}
	
	

}

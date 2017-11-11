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
	
	@Column (name = "ruc_cedula")
	@NotNull
	private String rucCedula;
	
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
	@Column (name = "estado")
	private String estado;
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
	public String getRucCedula() {
		return rucCedula;
	}
	public void setRucCedula(String rucCedula) {
		this.rucCedula = rucCedula;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	

}

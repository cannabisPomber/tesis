package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity (name = "cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT p FROM cliente p")
public class Cliente implements Serializable{
	
	@Id
	@Column(name="id_cliente")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_generator")
	@SequenceGenerator(name="cliente_generator", sequenceName = "cliente_seq", allocationSize=1)
	private Long idCliente;
	
	@Column (name = "nombre")
	@NotNull
	private String nombre;
	@Column (name = "apellido")
	@NotNull
	private String apellido;
	
	
	@Column (name = "email" , nullable = true)
	private String email;
	
	@Column (name = "telefono" , nullable = true)
	private String telefono;
	
	@Column (name = "estado" , nullable = false)
	private String estado;
	
	@Column (name = "latitud" , nullable = true)
	private Float latitud;
	
	@Column (name = "longitud" , nullable = true)
	private Float longitud;
	
	@Column (name = "direccion" , nullable = true)
	private String direccion;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}
	
	
	

}

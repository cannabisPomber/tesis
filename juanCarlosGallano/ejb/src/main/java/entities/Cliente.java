package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity (name = "cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT p FROM cliente p")
public class Cliente implements Serializable{
	
	@Id
	@Column(name="id_cliente")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCliente;
	
	
	//Falta Tipo de Documnento
	@Column (name = "ruc", nullable = false)
	private String ruc;
	@Column (name = "cedula", nullable = true)
	private String cedula;
	@Column (name = "nombre")
	@NotNull
	private String nombre;
	@Column (name = "apellido")
	@NotNull
	private String apellido;
	
	@Column (name = "direccion" , nullable = false)
	private String direccion;
	
	@Column (name = "email" , nullable = false)
	private String email;
	
	@Column (name = "telefono" , nullable = false)
	private String telefono;
	
	@Column (name = "estado" , nullable = false)
	private String estado;

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	
	
	

}

package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity (name = "rol")
@NamedQuery(name="Rol.findAll", query="SELECT u FROM rol u")
public class Rol implements Serializable{
	
	@Id
	@Column(name="id_rol")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_generator")
	@SequenceGenerator(name="rol_generator", sequenceName = "rol_seq", allocationSize=1)
	private Long idRol;
	
	@Column(name="cod_rol", nullable = false , length= 4)
	private String codRol;
	
	@Column(name="nombre_rol", nullable = false , length= 60)
	private String nombreRol;
	@Column(name="estado", nullable = true , length= 20)
	private String estado;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rol_grupo", joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id_rol"), inverseJoinColumns = @JoinColumn(name = "grupo_id", referencedColumnName = "id_grupo"))
	private List<Grupo>listGrupos;
	
	
	public Long getIdRol() {
		return idRol;
	}
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	public String getCodRol() {
		return codRol;
	}
	public void setCodRol(String codRol) {
		this.codRol = codRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public List<Grupo> getListGrupos() {
		return listGrupos;
	}
	public void setListGrupos(List<Grupo> listGrupos) {
		this.listGrupos = listGrupos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codRol == null) ? 0 : codRol.hashCode());
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
		Rol other = (Rol) obj;
		if (codRol == null) {
			if (other.codRol != null)
				return false;
		} else if (!codRol.equals(other.codRol))
			return false;
		return true;
	}
	
	
	

}

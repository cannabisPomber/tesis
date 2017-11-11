package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity (name = "grupo")
@NamedQuery(name="Grupo.findAll", query="SELECT u FROM grupo u")
public class Grupo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_grupo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_generator")
	@SequenceGenerator(name="grupo_generator", sequenceName = "grupo_seq", allocationSize=1)
	private Long idGrupo;
	
	@Column(name="nombre_grupo")
	@NotNull
	private String nombreGrupo;
	
	@Column(name="descripcion_grupo")
	@NotNull
	private String descripcionGrupo;
	
	@Column(name="estado")
	@NotNull
	private String estado;
	
	@ManyToMany(mappedBy="listGrupos")
	private List<Rol> listRol;
	
	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

	public List<Rol> getListRol() {
		return listRol;
	}

	public void setListRol(List<Rol> listRol) {
		this.listRol = listRol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idGrupo ^ (idGrupo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		if (idGrupo != other.idGrupo)
			return false;
		return true;
	}
	

}

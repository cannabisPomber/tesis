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

@Entity (name = "unidad_medida")
@NamedQuery(name = "UnidadMedida.findAll", query = "SELECT u FROM unidad_medida u")
public class UnidadMedida implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidad_medida_generator")
	@SequenceGenerator(name="unidad_medida_generator", sequenceName = "unidad_medida_seq", allocationSize=1)
	@Column(name = "id_unidad_medida")
	private Long id;
	
	
	@Column(name ="unidad", nullable = false) 
	private  String unidad;
	
	@Column(name ="dimension", nullable = false) 
	private  long dimension;
	@Column(name = "estado", nullable= false)
	private String estado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public long getDimension() {
		return dimension;
	}
	public void setDimension(long dimension) {
		this.dimension = dimension;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		UnidadMedida other = (UnidadMedida) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}

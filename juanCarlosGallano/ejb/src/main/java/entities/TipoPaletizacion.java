package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "tipo_paletizacion")
public class TipoPaletizacion implements Serializable{
	
	@Id
	@Column(name = "id_tipo_paletizacion")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idTipoPaletizacion;
	@Column(name = "cantidad_agrupada", nullable= false)
	private String cantidadAgrupada;
	@Column(name = "estado", nullable= false)
	private String estado;
	public long getIdTipoPaletizacion() {
		return idTipoPaletizacion;
	}
	public void setIdTipoPaletizacion(long idTipoPaletizacion) {
		this.idTipoPaletizacion = idTipoPaletizacion;
	}
	public String getCantidadAgrupada() {
		return cantidadAgrupada;
	}
	public void setCantidadAgrupada(String cantidadAgrupada) {
		this.cantidadAgrupada = cantidadAgrupada;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTipoPaletizacion ^ (idTipoPaletizacion >>> 32));
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
		TipoPaletizacion other = (TipoPaletizacion) obj;
		if (idTipoPaletizacion != other.idTipoPaletizacion)
			return false;
		return true;
	}
	
	
	
	
	

}

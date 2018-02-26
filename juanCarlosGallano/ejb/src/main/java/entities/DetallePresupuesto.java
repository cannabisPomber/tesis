package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class DetallePresupuesto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_presupuesto_generator")
	@SequenceGenerator(name="detalle_presupuesto_generator", sequenceName = "detalle_presupuesto_seq", allocationSize=1)
	@Column (name = "id_detalle_presupuesto")
	private Long idDetallePresupuesto;
	
	
	private Long cantidad;
	
	private ProductoProveedor productoProveedor;
	
	private Presupuesto presupuesto;

	public Long getIdDetallePresupuesto() {
		return idDetallePresupuesto;
	}

	public void setIdDetallePresupuesto(Long idDetallePresupuesto) {
		this.idDetallePresupuesto = idDetallePresupuesto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public ProductoProveedor getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedor productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetallePresupuesto == null) ? 0 : idDetallePresupuesto.hashCode());
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
		DetallePresupuesto other = (DetallePresupuesto) obj;
		if (idDetallePresupuesto == null) {
			if (other.idDetallePresupuesto != null)
				return false;
		} else if (!idDetallePresupuesto.equals(other.idDetallePresupuesto))
			return false;
		return true;
	}
	
	
	

}

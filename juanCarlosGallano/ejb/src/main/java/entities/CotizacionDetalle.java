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

@Entity (name = "cotizacion_detalle")
@NamedQuery(name="CotizacionDetalle.findAll", query="SELECT p FROM cotizacion_detalle p")
public class CotizacionDetalle implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Cotizacion_Detalle_generator")
	@SequenceGenerator(name="id_Cotizacion_Detalle_generator", sequenceName = "id_Cotizacion_Detalle_seq", allocationSize=1)
	@Column (name = "id_Cotizacion_Detalle_Cabecera")
	private Long idDetalleCabecera;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_producto", nullable = false)
	private Producto idProducto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_proveedor", nullable = false)
	private Proveedor idProveedor;
	
	@Column (name = "cantidad" , nullable = false)
	private Long cantidad;
	
	@Column (name = "precio_cotizacion" , nullable = false)
	private Long precioCotizacion;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cotizacion_cabecera", nullable = false)
	private CotizacionCabecera cotizacionCabecera;

	public Long getIdDetalleCabecera() {
		return idDetalleCabecera;
	}

	public void setIdDetalleCabecera(Long idDetalleCabecera) {
		this.idDetalleCabecera = idDetalleCabecera;
	}

	public Producto getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Producto idProducto) {
		this.idProducto = idProducto;
	}

	public Proveedor getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Proveedor idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getPrecioCotizacion() {
		return precioCotizacion;
	}

	public void setPrecioCotizacion(Long precioCotizacion) {
		this.precioCotizacion = precioCotizacion;
	}

	public CotizacionCabecera getCotizacionCabecera() {
		return cotizacionCabecera;
	}

	public void setCotizacionCabecera(CotizacionCabecera cotizacionCabecera) {
		this.cotizacionCabecera = cotizacionCabecera;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleCabecera == null) ? 0 : idDetalleCabecera.hashCode());
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
		CotizacionDetalle other = (CotizacionDetalle) obj;
		if (idDetalleCabecera == null) {
			if (other.idDetalleCabecera != null)
				return false;
		} else if (!idDetalleCabecera.equals(other.idDetalleCabecera))
			return false;
		return true;
	}
	
	

}

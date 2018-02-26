package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "presupuesto")
@NamedQuery(name = "Presupuesto.findAll", query = "SELECT u FROM presupuesto u")
public class Presupuesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presupuesto_generator")
	@SequenceGenerator(name="presupuesto_generator", sequenceName = "presupuesto_seq", allocationSize=1)
	@Column (name = "id_presupuesto")
	private Long idPresupuesto;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 
	@JoinColumn(name = "id_pedido", nullable = false)
	private Pedido pedido;
	
	@ManyToOne(optional=false) 
	@JoinColumn(name = "id_orden_compra", nullable = false)
	private OrdenCompra ordenCompra;
	
	
	@Column (name = "fecha_aprobacion" , nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaAprobacion;


	public Long getIdPresupuesto() {
		return idPresupuesto;
	}


	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	


	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}


	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	


	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}


	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPresupuesto == null) ? 0 : idPresupuesto.hashCode());
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
		Presupuesto other = (Presupuesto) obj;
		if (idPresupuesto == null) {
			if (other.idPresupuesto != null)
				return false;
		} else if (!idPresupuesto.equals(other.idPresupuesto))
			return false;
		return true;
	}
	
	
}

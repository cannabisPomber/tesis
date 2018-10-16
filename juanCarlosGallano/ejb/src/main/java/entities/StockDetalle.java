package entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name = "stock_detalle")
@NamedQuery(name="stockDetalle.findAll", query="SELECT p FROM stock_detalle p")
public class StockDetalle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_stock_detalle")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_detalle_generator")
	@SequenceGenerator(name="stock_detalle_generator", sequenceName = "stock_detalle_seq", allocationSize=1)
	private Long idStockDetalle;
	
	@Column(name = "hash_lote",length = 6 , nullable = true)
	private String hashLote;
	
	@Column (name = "fecha_ingreso" , nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaIngreso;
	
	@Column (name = "fecha_vencimiento" , nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaVencimiento;
	
	@Column (name = "cantidad_recibida" , nullable = false)
	private Long cantidadRecibida;
	
	@Column (name = "cantidad_restante" , nullable = false)
	private Long cantidadRestante;
	
	@Column (name = "estado" , nullable = false)
	private String estado;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden_compra", nullable = true)
	private OrdenCompra idOrdenCompra;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_deposito", nullable = true)
	private Deposito deposito;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
	private Producto producto;

	public Long getIdStockDetalle() {
		return idStockDetalle;
	}

	public void setIdStockDetalle(Long idStockDetalle) {
		this.idStockDetalle = idStockDetalle;
	}

	public String getHashLote() {
		return hashLote;
	}

	public void setHashLote(String hashLote) {
		this.hashLote = hashLote;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Long getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(Long cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	public Long getCantidadRestante() {
		return cantidadRestante;
	}

	public void setCantidadRestante(Long cantidadRestante) {
		this.cantidadRestante = cantidadRestante;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public OrdenCompra getIdOrdenCompra() {
		return idOrdenCompra;
	}

	public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idStockDetalle == null) ? 0 : idStockDetalle.hashCode());
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
		StockDetalle other = (StockDetalle) obj;
		if (idStockDetalle == null) {
			if (other.idStockDetalle != null)
				return false;
		} else if (!idStockDetalle.equals(other.idStockDetalle))
			return false;
		return true;
	}
	
	

}



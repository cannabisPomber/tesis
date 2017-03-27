package entities;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity (name =  "factura")
@NamedQuery(name="Factura.findAll", query="SELECT p FROM factura p")
public class Factura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "nroFactura")
	private Long nroFactura;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	@Column (name = "fecha", nullable = false)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column (name = "fecha_reserva", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaReserva;
	
	@Column (name = "fecha_devolucion", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaDevolucion;
	
	@Column (name = "fecha_credito", nullable = true)
	@Temporal (TemporalType.TIMESTAMP)
	private Date fechaCredito;
	
	@Column (name = "monto_total", nullable = false)
	private Long montoTotal;
	
	@Column (name = "monto_cobrado", nullable = true)
	private Long montocobrado;
	
	@Column (name = "interes", nullable = true)
	private Long interes;
	
	@Column (name = "senha", nullable = true)
	private Long senha; 
	
	@Column (name = "id_tipo_pago", nullable = false)
	private Integer idTipoPago;
	
	@Column (name = "usuario", nullable = false)
	private String usuario;

	public Long getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(Long nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Date getFechaCredito() {
		return fechaCredito;
	}

	public void setFechaCredito(Date fechaCredito) {
		this.fechaCredito = fechaCredito;
	}

	public Long getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Long montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Long getMontocobrado() {
		return montocobrado;
	}

	public void setMontocobrado(Long montocobrado) {
		this.montocobrado = montocobrado;
	}

	public Long getInteres() {
		return interes;
	}

	public void setInteres(Long interes) {
		this.interes = interes;
	}

	public Long getSenha() {
		return senha;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}

	public Integer getIdTipoPago() {
		return idTipoPago;
	}

	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nroFactura == null) ? 0 : nroFactura.hashCode());
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
		Factura other = (Factura) obj;
		if (nroFactura == null) {
			if (other.nroFactura != null)
				return false;
		} else if (!nroFactura.equals(other.nroFactura))
			return false;
		return true;
	}
	
	
	

}

package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "producto")
@NamedQuery(name = "Producto.findAll", query = "SELECT u FROM producto u")
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_producto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_generator")
	@SequenceGenerator(name="producto_generator", sequenceName = "producto_seq", allocationSize=1)
	private Long idProducto;

	@Column(name = "nombre_producto")
	@NotNull
	private String nombreProducto;

	@Column(name = "estado")
	@NotNull
	private String estado;

	@Column(name = "cantidad_minima")
	@NotNull
	private Long cantidadMinima;
	
	@Column(name ="codigo_barra", nullable = false)
	private String codigoBarra;
	
	
	@Column(name = "cantidad_maxima")
	@NotNull
	private Long cantidadMaxima;

	@Column(name = "precio_unitario")
	@NotNull
	private Long precioUnitario;
	
	@Column(name = "iva_producto")
	@NotNull
	private Float ivaProducto;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_marca", nullable = false)
	private Marca marca;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_tipo_producto", nullable = false)
	private TipoProducto tipoProducto;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_unidad_medida", nullable = false)
	private UnidadMedida unidadMedida;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_producto_unitario", nullable = true)
	private Producto producto;

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	
	

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getCantidadMinima() {
		return cantidadMinima;
	}

	public void setCantidadMinima(Long cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	public Long getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
	

	public Long getCantidadMaxima() {
		return cantidadMaxima;
	}

	public void setCantidadMaxima(Long cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}

	
	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Float getIvaProducto() {
		return ivaProducto;
	}

	public void setIvaProducto(Float ivaProducto) {
		this.ivaProducto = ivaProducto;
	}
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idProducto ^ (idProducto >>> 32));
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
		Producto other = (Producto) obj;
		if (idProducto != other.idProducto)
			return false;
		return true;
	}

}

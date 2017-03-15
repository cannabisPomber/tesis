package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.MarcaEJB;
import entities.Marca;

@ManagedBean (name = "marcaBean")
@ViewScoped
public class MarcaBean {
	@EJB
	MarcaEJB marcaEJB;
	
	private List<Marca> marcaList = new ArrayList<Marca>();
	private Marca marcaEdicion;

	public List<Marca> getMarcaList() {
		return marcaEJB.findAll();
	}

	public void setMarcaList(List<Marca> marcaList) {
		this.marcaList = marcaList;
	}

	public Marca getMarcaEdicion() {
		return marcaEdicion;
	}

	public void setMarcaEdicion(Marca marcaEdicion) {
		this.marcaEdicion = marcaEdicion;
	}
	
	public void eliminarMarca (Marca mar){
		marcaEJB.delete(mar);
	}
}

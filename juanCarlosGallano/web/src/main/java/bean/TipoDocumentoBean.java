package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.TipoDocumentoEJB;
import entities.TipoDocumento;

@ManagedBean (name = "tipoDocumentoBean")
@ViewScoped
public class TipoDocumentoBean {
	@EJB
	TipoDocumentoEJB tipoDocumentoEJB;
	
	private List<TipoDocumento> tipoDocumentoList = new ArrayList<TipoDocumento>();
	private TipoDocumento tipoDocumentoEdicion;

	public List<TipoDocumento> getTipoDocumentoList() {
		tipoDocumentoList = tipoDocumentoEJB.findAll();
		return tipoDocumentoList;
	}

	public void setTipoDocumentoList(List<TipoDocumento> tipoDocumentoList) {
		this.tipoDocumentoList = tipoDocumentoList;
	}

	public TipoDocumento getTipoDocumentoEdicion() {
		return tipoDocumentoEdicion;
	}

	public void setTipoDocumentoEdicion(TipoDocumento tipoDocumentoEdicion) {
		this.tipoDocumentoEdicion = tipoDocumentoEdicion;
	}
	
	public void eliminarTipoDocumento (TipoDocumento mar){
		tipoDocumentoEJB.delete(mar);
	}
}

package bean;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.CotizacionCabeceraEJB;
import ejb.PedidoEJB;
import ejb.UsuarioEJB;
import entities.CotizacionCabecera;
import entities.Pedido;

@ManagedBean (name = "adjudicarCotizacionBean")
@ViewScoped
public class AdjudicarCotizacionBean {
	
	
	
	@EJB
	CotizacionCabeceraEJB cotizacionCabeceraEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	private CotizacionCabecera cotizacionCab;
	
	private List<CotizacionCabecera> cotizacionList = new ArrayList<CotizacionCabecera>();

	public CotizacionCabecera getCotizacionCab() {
		return cotizacionCab;
	}

	public void setCotizacionCab(CotizacionCabecera cotizacionCab) {
		this.cotizacionCab = cotizacionCab;
	}

	public List<CotizacionCabecera> getCotizacionList() {
		cotizacionList = cotizacionCabeceraEJB.findAllActualizado();
		return cotizacionList;
	}

	public void setCotizacionList(List<CotizacionCabecera> cotizacionList) {
		this.cotizacionList = cotizacionList;
	}
	
	public String mostrarUsuario(Long idUsuario){
		return usuarioEJB.findIdUsuario(idUsuario).getUsuario();
	}

}


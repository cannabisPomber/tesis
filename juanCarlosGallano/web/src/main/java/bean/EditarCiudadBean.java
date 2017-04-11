package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.CiudadEJB;
import ejb.PaisEJB;
import entities.Ciudad;
import entities.Pais;

@ManagedBean (name = "editarCiudadBean")
@ViewScoped
public class EditarCiudadBean {
	
	private Long idCiudad;
	private Ciudad ciudadEdicion;
	
	private List<Pais> paises;
	
	@EJB
	CiudadEJB ciudadEJB;
	
	@EJB
	PaisEJB paisEJB;
	
	public void init(){
		paises = paisEJB.findAllActivo();
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idCiudad = Long.parseLong(params.get("idCiudad"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idCiudad != null){
				ciudadEdicion = ciudadEJB.findIdCiudad(idCiudad);
			} else {
				ciudadEdicion = new Ciudad();
			}
		}
	}

	public Long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

	public Ciudad getCiudadEdicion() {
		return ciudadEdicion;
	}

	public void setCiudadEdicion(Ciudad ciudadEdicion) {
		this.ciudadEdicion = ciudadEdicion;
	}
	public void guardarCiudad(){
		// Debe persistir el usuario
		if(ciudadEdicion.getIdCiudad() == null){
			ciudadEdicion = ciudadEJB.create(ciudadEdicion);
			 FacesContext.getCurrentInstance().addMessage("Ciudad Creado", new FacesMessage("Nuevo Ciudad Creado."));
			 ciudadEdicion = new Ciudad();
		} else {
			// Modificar usuario
			ciudadEdicion = ciudadEJB.update(ciudadEdicion);
			FacesContext.getCurrentInstance().addMessage("Ciudad Modificado", new FacesMessage("Ciudad Modificado."));
		}
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

}

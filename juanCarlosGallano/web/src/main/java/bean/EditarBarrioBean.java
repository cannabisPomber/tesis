package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.BarrioEJB;
import ejb.CiudadEJB;
import ejb.PaisEJB;
import entities.Barrio;
import entities.Ciudad;
import entities.Pais;

@ManagedBean (name = "editarBarrioBean")
@ViewScoped
public class EditarBarrioBean {
	
	private Long idBarrio;
	private Barrio barrioEdicion;
	
	private List<Ciudad> ciudades;
	
	@EJB
	BarrioEJB barrioEJB;
	
	@EJB
	CiudadEJB ciudadEJB;
	
	public void init(){
		ciudades = ciudadEJB.findAllActivo();
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idBarrio = Long.parseLong(params.get("idBarrio"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idBarrio != null){
				barrioEdicion = barrioEJB.findIdBarrio(idBarrio);
			} else {
				barrioEdicion = new Barrio();
			}
		}
	}

	public Long getIdBarrio() {
		return idBarrio;
	}

	public void setIdBarrio(Long idBarrio) {
		this.idBarrio = idBarrio;
	}

	public Barrio getBarrioEdicion() {
		return barrioEdicion;
	}

	public void setBarrioEdicion(Barrio barrioEdicion) {
		this.barrioEdicion = barrioEdicion;
	}
	public void guardarBarrio(){
		// Debe persistir el usuario
		if(barrioEdicion.getIdBarrio() == null){
			barrioEdicion = barrioEJB.create(barrioEdicion);
			 FacesContext.getCurrentInstance().addMessage("Barrio Creado", new FacesMessage("Nuevo Barrio Creado."));
			 barrioEdicion = new Barrio();
		} else {
			// Modificar usuario
			barrioEdicion = barrioEJB.update(barrioEdicion);
			FacesContext.getCurrentInstance().addMessage("Barrio Modificado", new FacesMessage("Barrio Modificado."));
		}
	}

	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}


}

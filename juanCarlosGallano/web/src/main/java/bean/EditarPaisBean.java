package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.PaisEJB;
import entities.Pais;
@ManagedBean (name = "editarPaisBean")
@ViewScoped
public class EditarPaisBean {
	private Long idPais;
	private Pais paisEdicion;
	
	@EJB
	PaisEJB paisEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idPais = Long.parseLong(params.get("idPais"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idPais != null){
				paisEdicion = paisEJB.findIdPais(idPais);
			} else {
				paisEdicion = new Pais();
			}
		}
	}

	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public Pais getPaisEdicion() {
		return paisEdicion;
	}

	public void setPaisEdicion(Pais paisEdicion) {
		this.paisEdicion = paisEdicion;
	}
	public void guardarPais(){
		// Debe persistir el usuario
		if(paisEdicion.getIdPais() == null){
			paisEdicion = paisEJB.create(paisEdicion);
			 FacesContext.getCurrentInstance().addMessage("Pais Creado", new FacesMessage("Nuevo Pais Creado."));
			 paisEdicion = new Pais();
			 System.out.println("Cantidad de paises :" + paisEJB.findAll().size());
		} else {
			// Modificar usuario
			paisEdicion = paisEJB.update(paisEdicion);
			FacesContext.getCurrentInstance().addMessage("Pais Modificado", new FacesMessage("Pais Modificado."));
		}
	}

}

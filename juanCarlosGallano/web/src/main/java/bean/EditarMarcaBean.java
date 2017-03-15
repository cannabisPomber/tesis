package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.MarcaEJB;
import entities.Grupo;
import entities.Marca;

@ManagedBean (name = "editarMarcaBean")
@ViewScoped
public class EditarMarcaBean {
	
	private Long idMarca;
	private Marca marcaEdicion;
	
	@EJB
	MarcaEJB marcaEjb;
	
	public EditarMarcaBean(){
		
	}

	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idMarca = Long.parseLong(params.get("idMarca"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idMarca != null){
				marcaEdicion = marcaEjb.findMarcaId(idMarca);
			} else {
				marcaEdicion = new Marca();
			}
		}
		
		
	}
	
	public void guardarMarca(){
		// Debe persistir el usuario
		if(marcaEdicion.getIdMarca() == null){
			marcaEdicion = marcaEjb.create(marcaEdicion);
			 FacesContext.getCurrentInstance().addMessage("Marca Creado", new FacesMessage("Nuevo Marca Creado."));
			 marcaEdicion = new Marca();
		} else {
			// Modificar usuario
			marcaEdicion = marcaEjb.update(marcaEdicion);
			FacesContext.getCurrentInstance().addMessage("Marca Modificado", new FacesMessage("Marca Modificado."));
		}
	}

	public Long getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}

	public Marca getMarcaEdicion() {
		return marcaEdicion;
	}

	public void setMarcaEdicion(Marca marcaEdicion) {
		this.marcaEdicion = marcaEdicion;
	}
	
	
}

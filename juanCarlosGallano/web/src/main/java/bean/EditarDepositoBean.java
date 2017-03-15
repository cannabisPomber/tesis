package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.DepositoEJB;
import entities.Deposito;
import entities.Grupo;

@ManagedBean (name = "editarDepositoBean")
@ViewScoped
public class EditarDepositoBean {
	
	private Long idDeposito;
	private Deposito depositoEdicion;
	
	@EJB
	DepositoEJB depositoEJB;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idDeposito = Long.parseLong(params.get("idDeposito"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idDeposito != null){
				depositoEdicion = depositoEJB.findIdDeposito(idDeposito);
			} else {
				depositoEdicion = new Deposito();
			}
		}
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public Deposito getDepositoEdicion() {
		return depositoEdicion;
	}

	public void setDepositoEdicion(Deposito depositoEdicion) {
		this.depositoEdicion = depositoEdicion;
	}
	public void guardarDeposito(){
		// Debe persistir el usuario
		if(depositoEdicion.getIdDeposito() == null){
			depositoEdicion = depositoEJB.create(depositoEdicion);
			 FacesContext.getCurrentInstance().addMessage("Deposito Creado", new FacesMessage("Nuevo Deposito Creado."));
			 depositoEdicion = new Deposito();
		} else {
			// Modificar usuario
			depositoEdicion = depositoEJB.update(depositoEdicion);
			FacesContext.getCurrentInstance().addMessage("Deposito Modificado", new FacesMessage("Deposito Modificado."));
		}
	}

}

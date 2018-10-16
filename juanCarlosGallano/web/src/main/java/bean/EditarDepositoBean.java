package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.DepositoEJB;
import ejb.EmpresaEJB;
import entities.Deposito;
import entities.Empresa;
import entities.Grupo;

@ManagedBean (name = "editarDepositoBean")
@ViewScoped
public class EditarDepositoBean {
	
	private Long idDeposito;
	private Deposito depositoEdicion;
	
	private Long idEmpresa;
	private Empresa empresaEdit;
	// eleccion de sucursal para usuario;
	private List<Empresa> empresas;
	
	@EJB
	DepositoEJB depositoEJB;
	
	@EJB
	EmpresaEJB empresaEjb;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			empresas = empresaEjb.findAll();
			try{
				idDeposito = Long.parseLong(params.get("idDeposito"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idDeposito != null){
				depositoEdicion = depositoEJB.findIdDeposito(idDeposito);
				idEmpresa = depositoEdicion.getEmpresa().getIdEmpresa();
				empresaEdit = empresaEjb.findEmpresaId(idEmpresa);
			} else {
				depositoEdicion = new Deposito();
				empresaEdit = new Empresa();
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
	
	
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Empresa getEmpresaEdit() {
		return empresaEdit;
	}

	public void setEmpresaEdit(Empresa empresaEdit) {
		this.empresaEdit = empresaEdit;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public void guardarDeposito(){
		// Debe persistir el usuario
		if(depositoEdicion.getIdDeposito() == null){
			depositoEdicion.setEmpresa(empresaEjb.findEmpresaId(idEmpresa));
			depositoEdicion = depositoEJB.create(depositoEdicion);
			 FacesContext.getCurrentInstance().addMessage("Deposito Creado", new FacesMessage("Nuevo Deposito Creado."));
			 depositoEdicion = new Deposito();
		} else {
			// Modificar usuario
			depositoEdicion.setEmpresa(empresaEjb.findEmpresaId(idEmpresa));
			depositoEdicion = depositoEJB.update(depositoEdicion);
			FacesContext.getCurrentInstance().addMessage("Deposito Modificado", new FacesMessage("Deposito Modificado."));
		}
	}

}

package bean;

import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ejb.EmpresaEJB;
import entities.Empresa;

@ManagedBean (name = "mostrarEmpresaBean")
@ViewScoped
public class MostrarEmpresaBean {
	@EJB
	EmpresaEJB empresaEJB;
	
	private Empresa empresa;
	private Long idEmpresa;
	
	private String campoFactura1;
	
	private String campoFactura2;
	
	private String campoFactura3;
	
	private String numeroFactura;
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idEmpresa = Long.parseLong(params.get("idEmpresa"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idEmpresa == null){
				empresa = new Empresa();
				campoFactura1 = "";
				campoFactura2 ="";
				campoFactura3 ="";
			} else {
				//Si Existe Empresa
				empresa = empresaEJB.findEmpresaId(idEmpresa);
			}
			/*if (empresaEJB.findEmpresa() != null){
				if (empresaEJB.findEmpresa().size() > 0){
					empresa = empresaEJB.findEmpresa().get(0);
				} else {
					empresa = new Empresa();
				}
			} else {
				empresa = new Empresa();
			}*/
			
		}
	}

	

	public String getCampoFactura1() {
		return campoFactura1;
	}



	public void setCampoFactura1(String campoFactura1) {
		this.campoFactura1 = campoFactura1;
	}



	public String getCampoFactura2() {
		return campoFactura2;
	}



	public void setCampoFactura2(String campoFactura2) {
		this.campoFactura2 = campoFactura2;
	}



	public String getCampoFactura3() {
		return campoFactura3;
	}



	public void setCampoFactura3(String campoFactura3) {
		this.campoFactura3 = campoFactura3;
	}



	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	//METODO PARA GUARDAR DATOS DE EMPRESA O NEGOCIO QUE SEAN EDITADOS
	public void guardarEmpresa(){
		
		//Persistiendo Objeto Empresa
		
		if (idEmpresa == null){
			//Empresa nueva
			try {
				if (empresa.getInicioTimbrado().before(empresa.getVencimientoTimbrado()) ){
					empresaEJB.create(empresa);
					FacesContext.getCurrentInstance().addMessage("Guardados Datos de Empresa", new FacesMessage("Datos Empresa.Modificado"));
				} else {
					FacesContext.getCurrentInstance().addMessage("Control de Fechas", new FacesMessage("Verficar Fechas de inicio y vencimiento Timbrado"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				FacesContext.getCurrentInstance().addMessage("Error al persistir Empresa", new FacesMessage("Error Empresa.Modificado"));

			}
			empresa = new Empresa();
		} else {
			try {
				if (empresa.getInicioTimbrado().before(empresa.getVencimientoTimbrado()) ){
					empresaEJB.update(empresa);
					FacesContext.getCurrentInstance().addMessage("Guardados Datos de Empresa", new FacesMessage("Datos Empresa.Modificado"));
				} else {
					FacesContext.getCurrentInstance().addMessage("Control de Fechas", new FacesMessage("Verficar Fechas de inicio y vencimiento Timbrado"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				FacesContext.getCurrentInstance().addMessage("Error al persistir Empresa", new FacesMessage("Error Empresa.Modificado"));

			}
			empresa = new Empresa();
		}
		
		
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
}

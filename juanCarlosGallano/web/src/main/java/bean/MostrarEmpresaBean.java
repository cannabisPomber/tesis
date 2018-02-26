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
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			if (empresaEJB.findEmpresa() != null){
				if (empresaEJB.findEmpresa().size() > 0){
					empresa = empresaEJB.findEmpresa().get(0);
				} else {
					empresa = new Empresa();
				}
			} else {
				empresa = new Empresa();
			}
			
		}
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
		try {
			if (empresa.getInicioTimbrado().before(empresa.getVencimientoTimbrado()) ){
				//Persistir por que fecha inicio es menor que fecha vencimiento
				empresaEJB.update(empresa);
				FacesContext.getCurrentInstance().addMessage("Guardados Datos de Empresa", new FacesMessage("Datos Empresa.Modificado"));
			} else {
				FacesContext.getCurrentInstance().addMessage("Control de Fechas", new FacesMessage("Verficar Fechas de inicio y vencimiento Timbrado"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			FacesContext.getCurrentInstance().addMessage("Error al persistir Empresa", new FacesMessage("Error Empresa.Modificado"));

		}
		
		
	}
}

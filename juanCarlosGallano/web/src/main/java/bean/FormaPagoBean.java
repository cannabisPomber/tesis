package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.FormaPagoEJB;
import entities.FormaPago;

@ManagedBean (name = "formaPagoBean")
@ViewScoped
public class FormaPagoBean {
	
	@EJB
	FormaPagoEJB formaPagoEJB;
	
	private FormaPago FormaPagoEdicion;
	
	private List<FormaPago> formaPagoList = new ArrayList<FormaPago>();

	public FormaPago getFormaPagoEdicion() {
		return FormaPagoEdicion;
	}

	public void setFormaPagoEdicion(FormaPago FormaPagoEdicion) {
		this.FormaPagoEdicion = FormaPagoEdicion;
	}

	public List<FormaPago> getFormaPagoList() {
		return formaPagoEJB.findAll();
	}

	public void setFormaPagoList(List<FormaPago> grupoList) {
		this.formaPagoList = grupoList;
	}
	
	public void eliminarFormaPago (FormaPago dep){
		formaPagoEJB.delete(dep);
	}
	
}


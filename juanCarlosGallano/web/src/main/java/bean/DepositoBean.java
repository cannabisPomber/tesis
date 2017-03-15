package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.DepositoEJB;
import entities.Deposito;


@ManagedBean (name = "depositoBean")
@ViewScoped
public class DepositoBean {
	
	@EJB
	DepositoEJB depositoEJB;
	
	private Deposito depositoEdicion;
	
	private List<Deposito> depositoList = new ArrayList<Deposito>();

	public Deposito getDepositoEdicion() {
		return depositoEdicion;
	}

	public void setDepositoEdicion(Deposito depositoEdicion) {
		this.depositoEdicion = depositoEdicion;
	}

	public List<Deposito> getDepositoList() {
		return depositoEJB.findAll();
	}

	public void setDepositoList(List<Deposito> grupoList) {
		this.depositoList = grupoList;
	}
	
	public void eliminarDeposito (Deposito dep){
		depositoEJB.delete(dep);
	}
	
}

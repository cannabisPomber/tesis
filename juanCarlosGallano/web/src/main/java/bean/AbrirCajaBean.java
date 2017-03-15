package bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.Usuario;

@ManagedBean (name = "abrirCajaBean")
@ViewScoped
public class AbrirCajaBean {
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	private Caja cajaAbierta = new Caja();
	
	private Date fechaSistema;
	
	private Caja cajaExistente = new Caja(); 
	
	public void buscarCaja(){
		HttpSession session;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession(); 
		Usuario usuarioCaja = usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
		this.cajaExistente = cajaEJB.cajaAbierta(usuarioCaja);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Ya Existe una Caja Abierta por este Usuario.");
		facesContext.addMessage(null, facesMessage);
		
	}

	public Caja getCajaAbierta() {
		return cajaAbierta;
	}

	public void setCajaAbierta(Caja cajaAbierta) {
		this.cajaAbierta = cajaAbierta;
	}
	
	public Date getFechaSistema() {
		//Fecha sistema es erroneo esta adelantado 3 horas
		fechaSistema = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSistema);
		calendar.add(Calendar.HOUR, (-3));
		fechaSistema = calendar.getTime();
		return fechaSistema;
	}

	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
	}

	
	public void generarCaja(){
		//Find Usuario
		HttpSession session;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		session = request.getSession(); 
		Usuario usuarioCaja = usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
		// Set Datos de Caja
		cajaAbierta.setUsuario(usuarioCaja);
		cajaAbierta.setFechaInicio(this.fechaSistema);
		//Persiste dato de caja cargado.
		cajaEJB.create(cajaAbierta);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Caja Abierta.");
		facesContext.addMessage(null, facesMessage);
		RequestContext.getCurrentInstance().execute("PF('cargarMontoInicial').hide();");
	}

	public Caja getCajaExistente() {
		return cajaExistente;
	}

	public void setCajaExistente(Caja cajaExistente) {
		this.cajaExistente = cajaExistente;
	}
	
}

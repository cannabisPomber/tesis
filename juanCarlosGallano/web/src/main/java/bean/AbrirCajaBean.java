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
	
	private Boolean cajaExiste;
	
	private Long montoCaja;
	
	public void buscarCaja(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			montoCaja = (long) 0;
			HttpSession session;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession(); 
			Usuario usuarioCaja = usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
			cajaExistente = cajaEJB.cajaAbierta(usuarioCaja);
			
			if (cajaExistente == null){
				System.out.println("CAJA RETORNADA : null");
				cajaExiste = false;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage("Abrir nueva sesion de caja de Usuario.");
				facesContext.addMessage(null, facesMessage);
			} else {
				if (cajaExistente.getFechaFin() == null){
					cajaExiste = false;
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage("Abrir nueva sesion de caja de Usuario.");
					facesContext.addMessage(null, facesMessage);
				} else {
					cajaExiste = true;
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage("Ya Existe una Caja Abierta por este Usuario.");
					facesContext.addMessage(null, facesMessage);
				}
				
			}
		}
		
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
		cajaAbierta = new Caja();
		cajaAbierta.setUsuario(usuarioCaja);
		fechaSistema = new Date();
		cajaAbierta.setFechaInicio(this.fechaSistema);
		if (montoCaja > 0){
			System.out.println("setea monto caja");
			cajaAbierta.setMontoCaja(montoCaja);
		}
		//Persiste dato de caja cargado.
		cajaEJB.create(cajaAbierta);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Caja Abierta.");
		facesContext.addMessage(null, facesMessage);
		RequestContext.getCurrentInstance().execute("PF('cargarMontoInicial').hide();");
	}
	
	
	public void cerrarCaja(){
		System.out.println("Cerrando Caja.. debe mostrar reporte");
		cajaExistente.setFechaFin(fechaSistema);
		cajaEJB.update(cajaExistente);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Caja Cerrada.");
		facesContext.addMessage(null, facesMessage);
		
	}


	public Boolean getCajaExiste() {
		return cajaExiste;
	}

	public void setCajaExiste(Boolean cajaExiste) {
		this.cajaExiste = cajaExiste;
	}

	public Caja getCajaExistente() {
		return cajaExistente;
	}

	public void setCajaExistente(Caja cajaExistente) {
		this.cajaExistente = cajaExistente;
	}

	public CajaEJB getCajaEJB() {
		return cajaEJB;
	}

	public void setCajaEJB(CajaEJB cajaEJB) {
		this.cajaEJB = cajaEJB;
	}

	public Long getMontoCaja() {
		return montoCaja;
	}

	public void setMontoCaja(Long montoCaja) {
		this.montoCaja = montoCaja;
	}
	
	
}

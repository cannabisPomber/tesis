package bean;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import ejb.PuestoVentaEJB;
import ejb.UsuarioEJB;
import entities.Caja;
import entities.PuestoVenta;
import entities.Usuario;

@ManagedBean (name = "abrirCajaBean")
@ViewScoped
public class AbrirCajaBean {
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@EJB
	PuestoVentaEJB puestoVentaEJB;
	
	private Caja cajaAbierta = new Caja();
	
	private Date fechaSistema;
	
	private Caja cajaExistente = new Caja(); 
	
	private Boolean cajaExiste;
	
	private Long montoCaja;
	
	private List<PuestoVenta> listPuestoVenta;
	
	private PuestoVenta puestoVenta;
	
	private Long idPuestoVenta;
	
	public void buscarCaja(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			montoCaja = (long) 0;
			HttpSession session;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			session = request.getSession(); 
			Usuario usuarioCaja = usuarioEJB.findIdUsuario(Long.valueOf((String)session.getAttribute("idUsuario")));
			cajaExistente = cajaEJB.cajaAbierta(usuarioCaja);
			listPuestoVenta = puestoVentaEJB.findAllNoAasignado();
			//Si Usuario no ha creado una Caja
			if (cajaExistente == null){
				cajaExiste = false;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no ha abierto una Caja.", null);
				facesContext.addMessage(null, facesMessage);
			} else {
				//Si caja Aun no fue Cerrada
				if (cajaExistente.getFechaFin() == null){
					cajaExiste = true;
					FacesContext facesContext = FacesContext.getCurrentInstance();
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario Ya posee una Caja Asignada.", null);
					facesContext.addMessage(null, facesMessage);
				} else {
					//Si ya fue Cerrada Debe Permitir
					cajaExiste = true;
					//FacesContext facesContext = FacesContext.getCurrentInstance();
					//FacesMessage facesMessage = new FacesMessage("Ya Existe una Caja Abierta por este Usuario.");
					//facesContext.addMessage(null, facesMessage);
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
		//Si fue Seleccionado un puesto de Venta
		if(idPuestoVenta != null){
			//Si existe idPuesto de Venta Verificar que no exista otra caja Asignada
			List<Caja> listCajasPuestoVenta = cajaEJB.findCajaPuestoVenta(idPuestoVenta);
			if(listCajasPuestoVenta == null){
				//Si no existe caja asignada a ese Puesto Venta
				cajaAbierta = new Caja();
				cajaAbierta.setUsuario(usuarioCaja);
				fechaSistema = new Date();
				cajaAbierta.setFechaInicio(this.fechaSistema);
				if (montoCaja > 0){
					cajaAbierta.setMontoCaja(montoCaja);
					cajaAbierta.setMontoInicial(montoCaja);
				}
				cajaAbierta.setPuestoVenta(puestoVentaEJB.findPuestoVentaId(idPuestoVenta));
				//Persiste dato de caja cargado.
				cajaEJB.create(cajaAbierta);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage("Caja Abierta.");
				facesContext.addMessage(null, facesMessage);
				RequestContext.getCurrentInstance().update("templateForm:formAbrirCaja:CajaExistente");
				RequestContext.getCurrentInstance().update("templateForm:formAbrirCaja:cajaNull");
				
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seleccionar Otro Puesto de Venta.", null);
				facesContext.addMessage("Seleccionar Otro Puesto de Venta.", facesMessage);
			}
			
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No se a selecccionado Puesto de Venta.", null);
		}
	}
	
	
	public void cerrarCaja(){
		cajaExistente.setFechaFin(new Date());
		cajaEJB.cerrarCaja(cajaExistente);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage("Caja Cerrada.");
		facesContext.addMessage(null, facesMessage);
		RequestContext.getCurrentInstance().update("templateForm:formAbrirCaja:CajaExistente");
		RequestContext.getCurrentInstance().update("templateForm:formAbrirCaja:cajaNull");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/protected/arqueoCaja.xhtml?idCaja="+cajaExistente.getIdCaja()+";faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public PuestoVenta getPuestoVenta() {
		return puestoVenta;
	}

	public void setPuestoVenta(PuestoVenta puestoVenta) {
		this.puestoVenta = puestoVenta;
	}

	public Long getIdPuestoVenta() {
		return idPuestoVenta;
	}

	public void setIdPuestoVenta(Long idPuestoVenta) {
		this.idPuestoVenta = idPuestoVenta;
	}

	public List<PuestoVenta> getListPuestoVenta() {
		return listPuestoVenta;
	}

	public void setListPuestoVenta(List<PuestoVenta> listPuestoVenta) {
		this.listPuestoVenta = listPuestoVenta;
	}
	
	
}

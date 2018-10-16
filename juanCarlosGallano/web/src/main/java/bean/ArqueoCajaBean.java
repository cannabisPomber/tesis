package bean;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ejb.CajaDetalleEJB;
import ejb.CajaEJB;
import entities.Caja;
import entities.CajaDetalle;

@ManagedBean (name = "arqueoCajaBean")
@ViewScoped
public class ArqueoCajaBean {
	
	@EJB
	CajaEJB cajaEJB;
	
	@EJB
	CajaDetalleEJB	cajaDetalleEJB;
	
	
	
	private Long canti50;
	private Long canti100;
	private Long canti500;

	private Long canti1000;
	private Long canti5mil;
	private Long canti10mil;
	private Long canti50mil;
	private Long canti100mil;
	
	private Boolean permitirCerrarCaja;
	private Long sumaIngreso;
	private Long sumaEgreso;
	private Long sumaCaja;
	private Long diferenciaEscasez;
	private List<Caja> listCajaCerradas;
	private List<CajaDetalle> listDetalleCajaCerrada;
	private Caja cajaSeleccionada;
	private Long sumaImporteCaja;
	private String focusComponent;
	private Long idcajaCerrada;
	
	
	public void init(){
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idcajaCerrada = Long.parseLong(params.get("idCajaCerrada"));
			} catch(Exception ex){
				System.out.println(" Parametro necesario de cajaCerrada");
			}
			cajaSeleccionada =cajaEJB.findCajaId(idcajaCerrada);
			listDetalleCajaCerrada =  cajaDetalleEJB.findDetalleCaja(cajaSeleccionada.getIdCaja());
			focusComponent = "templateForm:formArqueoCaja:mon50";
			sumaEgreso = 0l;
			sumaIngreso = 0l;
			sumaCaja = 0l;
			canti50 = 0l;
			canti100 = 0l;
			canti1000 = 0l;
			canti10mil = 0l;
			canti50mil = 0l;
			canti100mil = 0l;
			canti5mil = 0l;
			canti500 = 0l;
			diferenciaEscasez = 0l;
			sumaImporteCaja = (long) 0;
			permitirCerrarCaja = false;
			for (Iterator iterator = listDetalleCajaCerrada.iterator(); iterator.hasNext();) {
				CajaDetalle detCaja = (CajaDetalle) iterator.next();
				if (detCaja.getMontoEgreso() != null){
					sumaEgreso = sumaEgreso+ detCaja.getMontoEgreso();
				}
				if (detCaja.getMontoIngreso() != null){
					sumaIngreso = sumaIngreso+ detCaja.getMontoIngreso();
				}
			}
			if(cajaSeleccionada.getMontoInicial() != null){
				sumaCaja = (cajaSeleccionada.getMontoInicial()+sumaIngreso)-sumaEgreso;
			} else {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Verificar Monto Inicial de Caja.", null);
				facesContext.addMessage("Verificar Monto Inicial de Caja.", facesMessage);
			}
		}
	}
	public Long getCanti50() {
		return canti50;
	}

	public List<CajaDetalle> getListDetalleCajaCerrada() {
		return listDetalleCajaCerrada;
	}
	public void setListDetalleCajaCerrada(List<CajaDetalle> listDetalleCajaCerrada) {
		this.listDetalleCajaCerrada = listDetalleCajaCerrada;
	}
	public Long getSumaImporteCaja() {
		return sumaImporteCaja;
	}
	public void setSumaImporteCaja(Long sumaImporteCaja) {
		this.sumaImporteCaja = sumaImporteCaja;
	}
	public String getFocusComponent() {
		return focusComponent;
	}
	public void setFocusComponent(String focusComponent) {
		this.focusComponent = focusComponent;
	}
	public Long getCanti500() {
		return canti500;
	}


	public Long getDiferenciaEscasez() {
		return diferenciaEscasez;
	}
	public void setDiferenciaEscasez(Long diferenciaEscasez) {
		this.diferenciaEscasez = diferenciaEscasez;
	}
	public Long getSumaCaja() {
		return sumaCaja;
	}
	public void setSumaCaja(Long sumaCaja) {
		this.sumaCaja = sumaCaja;
	}
	public Long getSumaIngreso() {
		return sumaIngreso;
	}
	public void setSumaIngreso(Long sumaIngreso) {
		this.sumaIngreso = sumaIngreso;
	}
	public Long getSumaEgreso() {
		return sumaEgreso;
	}
	public void setSumaEgreso(Long sumaEgreso) {
		this.sumaEgreso = sumaEgreso;
	}
	public void setCanti500(Long canti500) {
		this.canti500 = canti500;
	}
	public List<Caja> getListCajaCerradas() {
		return listCajaCerradas;
	}


	public void setListCajaCerradas(List<Caja> listCajaCerradas) {
		this.listCajaCerradas = listCajaCerradas;
	}


	public Long getIdcajaCerrada() {
		return idcajaCerrada;
	}
	public void setIdcajaCerrada(Long idcajaCerrada) {
		this.idcajaCerrada = idcajaCerrada;
	}
	public void setCanti1000(Long canti1000) {
		this.canti1000 = canti1000;
	}
	public Caja getCajaSeleccionada() {
		return cajaSeleccionada;
	}


	public void setCajaSeleccionada(Caja cajaSeleccionada) {
		this.cajaSeleccionada = cajaSeleccionada;
	}


	public void setCanti50(Long canti50) {
		this.canti50 = canti50;
	}


	public Long getCanti100() {
		return canti100;
	}


	public void setCanti100(Long canti100) {
		this.canti100 = canti100;
	}


	public Long getCanti1000() {
		return canti1000;
	}


	public Boolean getPermitirCerrarCaja() {
		return permitirCerrarCaja;
	}
	public void setPermitirCerrarCaja(Boolean permitirCerrarCaja) {
		this.permitirCerrarCaja = permitirCerrarCaja;
	}
	public Long getCanti10mil() {
		return canti10mil;
	}


	public void setCanti10mil(Long canti10mil) {
		this.canti10mil = canti10mil;
	}


	public Long getCanti5mil() {
		return canti5mil;
	}


	public void setCanti5mil(Long canti5mil) {
		this.canti5mil = canti5mil;
	}


	public Long getCanti50mil() {
		return canti50mil;
	}


	public void setCanti50mil(Long canti50mil) {
		this.canti50mil = canti50mil;
	}


	public Long getCanti100mil() {
		return canti100mil;
	}


	public void setCanti100mil(Long canti100mil) {
		this.canti100mil = canti100mil;
	}

	public void sumarCantidadMonedas(){
		//Sumarizando los valores de campos de caja
		sumaImporteCaja = (long) 0;
		sumaImporteCaja = (canti50*50)+(canti100*100)+(canti500*500)+(canti1000*1000)+ 
				(canti5mil*5000)+(canti10mil*10000)+(canti50mil*50000)+(canti100mil*100000);
		RequestContext.getCurrentInstance().update("templateForm:formArqueoCaja:panelSumaCaja");
				
	}
	
	public void limpiarCamposCaja(){
		focusComponent = "templateForm:formArqueoCaja:mon50";
		canti50 = 0l;
		canti100 = 0l;
		canti1000 = 0l;
		canti10mil = 0l;
		canti50mil = 0l;
		canti100mil = 0l;
		canti5mil = 0l;
		canti500 = 0l;
		sumaImporteCaja =0l;
		diferenciaEscasez =0l;
		permitirCerrarCaja = false;
		RequestContext.getCurrentInstance().update("templateForm:formArqueoCaja:diferenciaCaja");
		RequestContext.getCurrentInstance().update("templateForm:formArqueoCaja:panelMonedasArqueo");
		RequestContext.getCurrentInstance().update("templateForm:formArqueoCaja:panelSumaCaja");
	}
	
	public void compararImportes(){
		//Comparara los importes ingresados y cargara negativo o positivo
		if(cajaSeleccionada.getMontoInicial() != null){
			diferenciaEscasez = sumaImporteCaja-(sumaCaja);
			permitirCerrarCaja = true;
			RequestContext.getCurrentInstance().update("templateForm:formArqueoCaja:diferenciaCaja");
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Verificar Monto Inicial de Caja.", null);
			facesContext.addMessage("Verificar Monto Inicial de Caja.", facesMessage);
		}
	}
	
	public void cerrarEnviarTesoreria(){
		if(cajaSeleccionada.getMontoInicial() != null){
			//Cargar diferencia de escasez en caja
			
			CajaDetalle cajaDetalleCerrada = new CajaDetalle();
			//buscar Caja Tesoreria
			Caja cajaTesoreria = cajaEJB.cajaAbiertaTesoreria();
			CajaDetalle cajaDetalleTesoreria = new CajaDetalle();
			//Hubo sobrante de dinero
			if(diferenciaEscasez>0){
				//equilibrando
				cajaDetalleCerrada.setMontoEgreso(diferenciaEscasez);
				cajaDetalleCerrada.setFechaHora(new Date());
				cajaDetalleCerrada.setCaja(cajaSeleccionada);
				cajaDetalleCerrada.setObservacion("ARQUEO DE CAJA");
				cajaSeleccionada.setEscasez(diferenciaEscasez);
				//agregando a Tesoreria
				cajaDetalleTesoreria.setFechaHora(new Date());
				cajaDetalleTesoreria.setMontoIngreso(sumaCaja+diferenciaEscasez);
				//Guardando 
				cajaTesoreria.setMontoCaja(cajaTesoreria.getMontoCaja()+(sumaCaja+diferenciaEscasez));
				cajaDetalleTesoreria.setObservacion("MONTO DE ARQUEO DE CAJA");
				diferenciaEscasez = 0L;
			}
			//Hubo Faltante de Dinero
			if(diferenciaEscasez<0){
				//equilibrando
				cajaDetalleCerrada.setMontoIngreso(diferenciaEscasez);
				cajaDetalleCerrada.setFechaHora(new Date());
				cajaDetalleCerrada.setCaja(cajaSeleccionada);
				cajaDetalleCerrada.setObservacion("ARQUEO DE CAJA");
				cajaSeleccionada.setEscasez(diferenciaEscasez);
				//agregando a Tesoreria
				cajaDetalleTesoreria.setFechaHora(new Date());
				cajaDetalleTesoreria.setMontoEgreso(sumaCaja-diferenciaEscasez);
				//Guardando 
				cajaTesoreria.setMontoCaja(cajaTesoreria.getMontoCaja()+(sumaCaja-diferenciaEscasez));
				cajaDetalleTesoreria.setObservacion("MONTO DE ARQUEO DE CAJA");
				
				diferenciaEscasez = 0L;
			}
			//Cerrar Caja
			cajaSeleccionada.setFechaFin(new Date());
			cajaEJB.update(cajaSeleccionada);
			//Guardando detalle Caja
			cajaDetalleCerrada.setFechaHora(new Date());
			cajaDetalleEJB.create(cajaDetalleCerrada);
			//Guardando en Detalle Tesoreria
			cajaDetalleTesoreria.setCaja(cajaTesoreria);
			cajaDetalleTesoreria.setFechaHora(new Date());
			cajaDetalleTesoreria.setCajaCerradaArqueo(cajaEJB.findCajaId(cajaSeleccionada.getIdCaja()));
			cajaDetalleEJB.create(cajaDetalleTesoreria);
			//Guardar Tesoreria
			cajaEJB.update(cajaTesoreria);
			limpiarCamposCaja();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cerrando Caja y enviando monto de dinero a Tesoreria.", null);
			facesContext.addMessage("Cerrando Caja y enviando monto de dinero a Tesoreria.", facesMessage);
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Verificar Monto Inicial de Caja.", null);
			facesContext.addMessage("Verificar Monto Inicial de Caja.", facesMessage);
		}
	}
}

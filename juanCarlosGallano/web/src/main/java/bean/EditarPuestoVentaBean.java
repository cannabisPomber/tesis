package bean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ejb.EmpresaEJB;
import ejb.PuestoVentaEJB;
import entities.Empresa;
import entities.PuestoVenta;

@ManagedBean (name = "editarPuestoVentaBean")
@ViewScoped
public class EditarPuestoVentaBean {
	
	private Long idPuestoVenta;
	
	private Long idEmpresa;
	
	private List<Empresa> empresas;
	private PuestoVenta puestoVentaEdicion;
	@EJB
	PuestoVentaEJB puestoVentaEjb;
	
	@EJB
	EmpresaEJB empresaEjb;
	
	public EditarPuestoVentaBean (){
		
	}
	// Proceso inicial del Bean
	public void init() throws IOException{
		//verificar que pertenezca al puestoVenta Admin
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			empresas = empresaEjb.findAll();
			try{
				idPuestoVenta = Long.parseLong(params.get("idPuestoVenta"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idPuestoVenta != null){
				puestoVentaEdicion = puestoVentaEjb.findPuestoVentaId(idPuestoVenta);
				idEmpresa = puestoVentaEdicion.getSucursal().getIdEmpresa();
			} else {
				puestoVentaEdicion = new PuestoVenta();
				puestoVentaEdicion.setAsignado(false);
			}
		}
		
		
	}
	public PuestoVenta getPuestoVentaEdicion() {
		return puestoVentaEdicion;
	}
	public void setPuestoVentaEdicion(PuestoVenta puestoVentaEdicion) {
		this.puestoVentaEdicion = puestoVentaEdicion;
	}
	
	public Long getIdPuestoVenta() {
		return idPuestoVenta;
	}
	public void setIdPuestoVenta(Long idPuestoVenta) {
		this.idPuestoVenta = idPuestoVenta;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public void guardarPuestoVenta(){
		// Debe persistir el usuario
		if(puestoVentaEdicion.getIdPuestoVenta() == null){
			puestoVentaEdicion.setSucursal(empresaEjb.findEmpresaId(idEmpresa));
			puestoVentaEdicion = puestoVentaEjb.create(puestoVentaEdicion);
			 FacesContext.getCurrentInstance().addMessage("PuestoVenta Creado", new FacesMessage("Nuevo PuestoVenta Creado."));
			 puestoVentaEdicion = new PuestoVenta();
		} else {
			// Modificar usuario
			puestoVentaEdicion.setSucursal(empresaEjb.findEmpresaId(idEmpresa));
			puestoVentaEdicion = puestoVentaEjb.update(puestoVentaEdicion);
			FacesContext.getCurrentInstance().addMessage("PuestoVenta Modificado", new FacesMessage("PuestoVenta Modificado."));
		}
	}
	
}



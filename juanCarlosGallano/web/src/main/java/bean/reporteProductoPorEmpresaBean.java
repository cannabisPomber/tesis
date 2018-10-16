package bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ejb.EmpresaEJB;
import ejb.UsuarioEJB;
import entities.Empresa;
import entities.Usuario;

@ManagedBean (name = "reporteProductoPorEmpresaBean")
@ViewScoped
public class reporteProductoPorEmpresaBean {
	
	@EJB
	EmpresaEJB empresaEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@Inject
	UsuarioBean usuarioBean;
	
	private String idUsuario;
	private Long idEmpresa;
	
	private Usuario usuLogueado;
	
	private List<Empresa> listSucursales;
	
	public void init(){
		
		listSucursales = empresaEJB.findAll();
		if (!FacesContext.getCurrentInstance().isPostback()){
			try {
				idUsuario = usuarioBean.usuarioLogueado();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			usuLogueado =  usuarioEJB.findUserUsuario(idUsuario); 
			
		}
		
	}
	
public void exportarProductoPorEmpresaPDF() throws IOException{
		
		
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametros();
		String nombreReporte = "/reportes/ProductosEmpresa.jrxml";
		
		//parameters.put("idHabCaja", idCajaHabilitacion);
		
		constructor.generarReporte(parameters, nombreReporte);
		
		}	

		public Map<String, Object> obtenerParametros(){
			String filename = "producto_por_empresa.pdf";
			Map<String, Object> parametros = new HashMap<String, Object>();
			Integer intEmpresa = (int) (long) idEmpresa;
			parametros.put("id_empresa", intEmpresa);
			
			return parametros;
		}


	public String getIdUsuario() {
		return idUsuario;
	}


	public Usuario getUsuLogueado() {
		return usuLogueado;
	}

	public void setUsuLogueado(Usuario usuLogueado) {
		this.usuLogueado = usuLogueado;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	public Long getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<Empresa> getListSucursales() {
		return listSucursales;
	}

	public void setListSucursales(List<Empresa> listSucursales) {
		this.listSucursales = listSucursales;
	}
	
	

}

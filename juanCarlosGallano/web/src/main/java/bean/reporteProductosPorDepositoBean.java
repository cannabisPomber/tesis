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

import ejb.DepositoEJB;
import ejb.EmpresaEJB;
import ejb.UsuarioEJB;
import entities.Deposito;
import entities.Empresa;
import entities.Usuario;

@ManagedBean (name = "reporteProductosPorDepositoBean")
@ViewScoped
public class reporteProductosPorDepositoBean {
	
	@EJB
	EmpresaEJB empresaEJB;
	
	@EJB
	DepositoEJB depositoEJB;
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@Inject
	UsuarioBean usuarioBean;
	
	private String idUsuario;
	private Long idDeposito;
	
	private Usuario usuLogueado;
	
	private List<Deposito> listDeposito;
	
	public void init(){
		
		listDeposito = depositoEJB.findAllActivo();
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
	
public void exportarProductoPorDepositoPDF() throws IOException{
		
		
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametros();
		String nombreReporte = "/reportes/ProductosDeposito.jrxml";
		
		//parameters.put("idHabCaja", idCajaHabilitacion);
		
		constructor.generarReporte(parameters, nombreReporte);
		
		}	

		public Map<String, Object> obtenerParametros(){
			String filename = "producto_por_empresa.pdf";
			Map<String, Object> parametros = new HashMap<String, Object>();
			Integer intEmpresa = (int) (long) idDeposito;
			parametros.put("id_deposito", intEmpresa);
			
			return parametros;
		}


	public String getIdUsuario() {
		return idUsuario;
	}


	public Usuario getUsuLogueado() {
		return usuLogueado;
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public List<Deposito> getListDeposito() {
		return listDeposito;
	}

	public void setListDeposito(List<Deposito> listDeposito) {
		this.listDeposito = listDeposito;
	}

	public void setUsuLogueado(Usuario usuLogueado) {
		this.usuLogueado = usuLogueado;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

}


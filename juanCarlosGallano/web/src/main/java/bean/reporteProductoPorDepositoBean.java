package bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean (name = "reporteProductoPorDepositoBean")
@ViewScoped
public class reporteProductoPorDepositoBean {
	
	public void init(){
		
	}
	
	public void exportarPDF() throws IOException{
		
		
		GeneradorReporte constructor = new GeneradorReporte();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = obtenerParametros();
		String nombreReporte = "/reportes/ProductosEmpresa.jrxml";
		
		//parameters.put("idHabCaja", idCajaHabilitacion);
		
		constructor.generarReporte(parameters, nombreReporte);
		
		}	

		public Map<String, Object> obtenerParametros(){
			String filename = "ticket_factura_contado.pdf";
			String jasperPath = "resource/reports/FacturaVenta.jasper";
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("id_empresa", "id_empresa");
			
			return parametros;
		}

}

package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import ejb.GenericDaoImpl;
/*import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;*/

@ManagedBean (name = "reportBean")
@ViewScoped
public class ReportBean {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@PostConstruct
	public void init(){
		
		
	}
	
	
	public void printPDF(){
		
		String filename = "ticket_factura_contado.pdf";
		String jasperPath = "resource/reports/FacturaVenta.jasper";
		Connection conn = entityManager.unwrap(java.sql.Connection.class);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("RUC", "123");
		parametros.put("SUCURSAL", "MARIANO ROQUE ALONSO");
		parametros.put("DIRECCION", "DIRECCION");
		parametros.put("DIRECCION_MATRIZ", "DIRECCION");
		parametros.put("TIMBRADO_NRO", "TIMBRADO");
		parametros.put("TIMBRADO_DESDE", "TIMBRADO DESDE");
		parametros.put("TIMBRADO_HASTA", "TIMBRADO HASTA");
		parametros.put("TIPO_VENTA", "CONTADO");
		parametros.put("CAJA_ID", " CAJA ID");
		parametros.put("CAJERO", "PUTAZO");
		parametros.put("FACTURA_FECHA", "FACTURA FECHA");
		parametros.put("FACTURA_ID", "1");
		parametros.put("FACTURA_NRO", "FACTURA NRO");
		parametros.put("IVA10", "IVA10");
		parametros.put("IVA5", "IVA5");
		parametros.put("MONTO_SIN_IVA5", "MONTO SIN IVA 5");
		parametros.put("MONTO_SIN_IVA10", "MONTO SIN IVA 10");
		parametros.put("MONTO_EXCENTOS", "MONTO EXENTOS");
		parametros.put("MONTO_PAGAR", "MONTO PAGAR");
		parametros.put("CLIENTE_RUC", "CLIENTE RUC");
		parametros.put("CLIENTE_NOMBRE", "CLIENTE NOMBRE");
		parametros.put("CLIENTE_DIR", "CLIENTE DIR");

		/*try {
			generarPDF(parametros, jasperPath, conn, filename);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void generarPDF(Map<String, Object> params, String jasperPath,Connection dataSource, String filename) {
/*try {
			
		/*String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
		File file = new File(relativeWebPath);
//		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource, false);
		JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, dataSource);	
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-Disposition", "attachment;filename" + filename);
		ServletOutputStream stream;
		stream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, stream);
		FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	}

}

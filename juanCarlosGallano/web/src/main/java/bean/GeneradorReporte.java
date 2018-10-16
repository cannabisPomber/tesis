package bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

//import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;*/

@ManagedBean
@ViewScoped
public class GeneradorReporte implements Serializable {

	private static final long serialVersionUID = 1L;
	
	

	private byte[] pdf = null;

	public GeneradorReporte() {

	}

	public void generarReporte(Map<String, Object> parametros, String rutaReporte) {
		try {
			
			ConexionConsulta conn = new ConexionConsulta(); // Conexión con la BD

			InputStream reportTemplate = this.getClass().getResourceAsStream(rutaReporte);
			JasperReport jasperReport;
			JasperPrint jasperPrint;
			JasperDesign jasperDesign;

			jasperDesign = JRXmlLoader.load(reportTemplate);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn.conexionConsultaReporte());
			
			pdf = JasperExportManager.exportReportToPdf(jasperPrint);
			
			exportarPDF();

		} catch (Exception ex) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Ocurrió un error al generar el reporte. \n" + ex.getMessage(), ""));
		}
	}
	
	/*public void generarReporteAntecedente(Tramite tramite, Persona ciudadano) throws JRException {

		procesadorAntecedente.procesadorAntecedente(ciudadano, tramite);

		//if (procesadorAntecedente.getListaRegistros().size() > 0) {
		if (procesadorAntecedente.getListaRegistrosFinal().size() > 0) {
			
			
			
			// genera el documento de Antecedentes Policiales
			Document documento = new Document(PageSize.A4, 30, 213, 120, 350);//
			//Document documento = new Document(new Rectangle(439, 566), 42, 56, 127, 113); original

			try {
				HttpServletResponse response  = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentType("application/pdf");

				PdfWriter.getInstance(documento, response.getOutputStream());
				documento.open();

				Font fuenteNormal = new Font(FontFamily.COURIER, 11, Font.NORMAL);
				//Font fuentesNegrita = new Font(FontFamily.COURIER, 11, Font.BOLD);
				
				for (int x = 0; x < procesadorAntecedente.getListaRegistrosFinal().size(); x++) {
					documento.add(new Paragraph(14, procesadorAntecedente.getListaRegistrosFinal().get(x), fuenteNormal));
				}

				documento.close();
				FacesContext.getCurrentInstance().responseComplete();

			} catch (Exception e) {
				log.warning("Error: " + e.getMessage() + "; Causa: " + e.getCause());
				log.log(Level.FINEST, "generarReporteAntecedente: ", e);
			}
		}
	}*/
	
	// exporta el reporte generado a PDF
	public void exportarPDF(){
		try {
			HttpServletResponse response  = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			 BufferedInputStream input = null;
		     BufferedOutputStream output = null;
			OutputStream os = response.getOutputStream();
			
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "_nameOFile.pdf\"");
			
			os.write(pdf);
			os.flush();
			os.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Ocurrió un error al generar el reporte. \n" + e.getMessage(), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}


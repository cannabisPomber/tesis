package bean;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;

import ejb.RucEmpresasEJB;
import ejb.RucPersonasEJB;
import entities.RucEmpresas;
import entities.RucPersonas;


@ManagedBean (name = "cargarDatosRucBean")
@ViewScoped
public class cargarDatosRucBean {
	
	@EJB
	RucPersonasEJB rucPersonasEJB;
	
	@EJB
	RucEmpresasEJB rucEmpresasEJB;
	
	private Part uploadedFile;
	 InputStream input;

	 public void handleFileUpload(FileUploadEvent event) {
		 
		 	try {		
		 		
				convertStreamToString(event.getFile().getInputstream());
				FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        
	    }
	 
	 public void handleFileUploadEmpresas(FileUploadEvent event) {
		 
		 	try {		
		 		
		 		convertStreamToStringEmpresas(event.getFile().getInputstream());
		 		 FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
			     FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       
	    }
	 
	 //Convertir InputStream a String listado de Ruc Empresas
	 public void convertStreamToString(InputStream is) throws IOException {
		 
		 	String ruc = "";
		 	RucPersonas personaJuridica = new RucPersonas();
		 	BufferedInputStream bis = new BufferedInputStream(is);
	 		ByteArrayOutputStream buf = new ByteArrayOutputStream();
	 		int result = bis.read();
	 		while(result != -1) {
	 		    buf.write((byte) result);
	 		    result = bis.read();
	 		}
	 		// StandardCharsets.UTF_8.name() > JDK 7
	 		//return buf.toString("UTF-8");
	 		List<String> datosRuc = new ArrayList<String>();
	 		
	 		String[] fila = buf.toString("UTF-8").split(",");
	 		for (int i = 0; i < fila.length; i++) {
				String stringFila = fila[i];
				datosRuc.add(stringFila);
			}
	 		for (Iterator iterator = datosRuc.iterator(); iterator.hasNext();) {
				String linea = (String) iterator.next();
				String[] campos = linea.split("\\|");
				if(campos.length == 4){
		 			personaJuridica.setPersona(campos[0]+""+campos[4]);
		 			ruc = campos[3]+"-"+campos[1];
		 			personaJuridica.setRuc(ruc.trim());
		 			if((rucPersonasEJB.findPorRucPersonas(campos[3]+"-"+campos[1])) == null){
		 				//Si es nulo no existe registro Insertar
		 				rucPersonasEJB.update(personaJuridica);
		 			} 
		 		}
			}
	 		
	 		
	 		
		}
	 
	//Convertir InputStream a String listado de Ruc Empresas
		 public void convertStreamToStringEmpresas(InputStream is) throws IOException {
			 
			 	String ruc = "";
			 	//RucEmpresas rucEmpresa = new RucEmpresas();
			 	RucPersonas rucPersonas = new RucPersonas();
			 	BufferedInputStream bis = new BufferedInputStream(is);
		 		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		 		int result = bis.read();
		 		while(result != -1) {
		 		    buf.write((byte) result);
		 		    result = bis.read();
		 		}
		 		// StandardCharsets.UTF_8.name() > JDK 7
		 		//return buf.toString("UTF-8");
		 		List<String> datosRuc = new ArrayList<String>();
		 		
		 		String[] fila = buf.toString("UTF-8").split("\\n");
		 		for (int i = 0; i < fila.length; i++) {
					String stringFila = fila[i];
					datosRuc.add(stringFila);
				}
		 		for (Iterator iterator = datosRuc.iterator(); iterator.hasNext();) {
					String linea = (String) iterator.next();
					String[] campos = linea.split("\\t");
					if(campos.length == 5){
						//rucEmpresa.setEmpresa(campos[2].trim());
						rucPersonas.setPersona(campos[2].trim());
			 			//rucEmpresa.setRuc((campos[0]+"-"+campos[1]).trim());
						rucPersonas.setRuc((campos[0]+"-"+campos[1]).trim());
			 			/*if((rucEmpresasEJB.findRucEmpresas(rucEmpresa.getRuc()) == null)){
			 				//Si es nulo no existe registro Insertar
			 				rucEmpresasEJB.update(rucEmpresa);
			 			} */
						if((rucPersonasEJB.findPorRucPersonas(campos[3]+"-"+campos[1])) == null){
			 				//Si es nulo no existe registro Insertar
			 				rucPersonasEJB.update(rucPersonas);
			 			} 
			 		}
				}
		 		
		 		
		 		
			}
	 
}

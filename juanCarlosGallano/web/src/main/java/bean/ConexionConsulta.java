package bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@ManagedBean(name = "conexionConsulta")
@ViewScoped
public class ConexionConsulta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public ConexionConsulta(){
	}

	// Obtiene la conexión con la BD exclusiva para las consultas realizadas en los reportes. 
	// Configuración proveniente del archivo standalone.xml del servidor
	public Connection conexionConsultaReporte() {

		InitialContext initialContext;
		
		try {
			initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:jboss/datasources/postgresDS");
			return (ds.getConnection());
		} catch (NamingException e) {
			System.out.println("Nombre JNI de conexión incorrecta");
			return null;
		} catch (SQLException e) {
			System.out.println("Error al obtener conexión SQL");
			e.printStackTrace();
			return null;
		}
	}
	
	// Permite generar la conexión JDBC para realizar consultas a BD externas al SNIC
	public Connection conexionExterna(){
		//OrigenDatos origen = applicationFacade.getOrigenDatos(idConexion);

		//if(origen != null){
		try {
			String host = "jdbc:postgresql://localhost:5432/delfra";
			
			String username = "postgres";
			String password = "300590";

			
				Class.forName("org.postgresql.Driver");
				//Class.forName(origen.getClaseDriver());
				return (DriverManager.getConnection(host, username, password));
			} catch (Exception ex) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Ocurrió un error al conectarse con la base de datos de Antecedentes.", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return null;
			}
	}

}


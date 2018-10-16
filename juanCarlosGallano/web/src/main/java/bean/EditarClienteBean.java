package bean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;


import ejb.ClienteEJB;
import ejb.TipoDocumentoClienteProveedorEJB;
import ejb.TipoDocumentoEJB;
import entities.Cliente;
import entities.TipoDocumento;
import entities.TipoDocumentoClienteProveedor;
@ManagedBean (name = "editarClienteBean")
@ViewScoped
public class EditarClienteBean {
	@EJB
	ClienteEJB clienteEjb;
	
	@EJB
	TipoDocumentoEJB tipoDocumentoEjb;
	
	@EJB
	TipoDocumentoClienteProveedorEJB tipoDocumentoClienteProveedorEjb;
	
	private List<TipoDocumento> listTipoDocumento;
	private Long idTipoDocumentoSeleccionado;
	private TipoDocumentoClienteProveedor tipoDocumentoClienteProveedor;
	private String numeroDocumento;
	//Variable para uso de mapa
	private MapModel emptyModel;
	private String title;
    
    private Float lat = (float) -25.242067;
      
    private Float lng = (float) -57.544233;
    
	private Long idCliente;
	private Cliente clienteEdicion;
	
	 private MapModel geoModel;
    private MapModel revGeoModel;
    private String centerGeoMap = "-25.242067, -57.544233";
    private String centerRevGeoMap = "-25.242067, -57.544233";
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Cliente getClienteEdicion() {
		return clienteEdicion;
	}
	public void setClienteEdicion(Cliente clienteEdicion) {
		this.clienteEdicion = clienteEdicion;
	}
	
	public MapModel getGeoModel() {
		return geoModel;
	}
	public void setGeoModel(MapModel geoModel) {
		this.geoModel = geoModel;
	}
	public MapModel getRevGeoModel() {
		return revGeoModel;
	}
	public void setRevGeoModel(MapModel revGeoModel) {
		this.revGeoModel = revGeoModel;
	}
	public String getCenterGeoMap() {
		return centerGeoMap;
	}
	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}
	public String getCenterRevGeoMap() {
		return centerRevGeoMap;
	}
	public void setCenterRevGeoMap(String centerRevGeoMap) {
		this.centerRevGeoMap = centerRevGeoMap;
	}
	
	
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Float getLat() {
		return lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public void init(){
		//inicializando variable de mapa
		 emptyModel = new DefaultMapModel();
		 listTipoDocumento = tipoDocumentoEjb.findAllActivo();
		 Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try{
			
			if (params.get("idCliente") != null){
				idCliente = Long.parseLong(params.get("idCliente"));
			} 
			
			if (!FacesContext.getCurrentInstance().isPostback()){
				if (idCliente != null){
					try{
						clienteEdicion = new Cliente();
						clienteEdicion = clienteEjb.findClienteIdCliente(idCliente);
						tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedorEjb.findTipoDocumentoClienteProveedorCliente(clienteEdicion.getIdCliente());
					} catch (Exception ex){
						FacesContext.getCurrentInstance().addMessage("Cliente no posee Tipo de Documento", new FacesMessage("Cliente no posee Tipo de Documento"));
					}
					idTipoDocumentoSeleccionado = tipoDocumentoClienteProveedor.getTipoDocumento().getId();
					numeroDocumento = tipoDocumentoClienteProveedor.getNumeroDocumento();
					
					lat = clienteEdicion.getLatitud();
					lng = clienteEdicion.getLongitud();
					emptyModel = new DefaultMapModel();
					Circle circulo = new Circle(new LatLng(lat,lng),50);
					circulo.setStrokeColor("#d93c3c");
					circulo.setFillColor("#d93c3c");
					Marker marker = new Marker(new LatLng(lat,lng));
					//emptyModel.addOverlay(circulo);
					emptyModel.addOverlay(marker);
					
				} else {
					tipoDocumentoClienteProveedor = new TipoDocumentoClienteProveedor();
					clienteEdicion = new Cliente();
					numeroDocumento= "";
				}
			}
		} catch (Exception ex){
			System.out.println("  ..." +ex.getMessage() +ex.getStackTrace() );
			ex.printStackTrace();
		}
	}
	
	
	public void guardarCliente(){
		// Debe persistir el usuario
		if(clienteEdicion.getIdCliente() == null){
			
			clienteEdicion.setLatitud((float) lat);
			clienteEdicion.setLongitud((float) lng);
			clienteEdicion = clienteEjb.create(clienteEdicion);
			
			tipoDocumentoClienteProveedor = new TipoDocumentoClienteProveedor();
			tipoDocumentoClienteProveedor.setCliente(clienteEdicion);
			tipoDocumentoClienteProveedor.setNumeroDocumento(numeroDocumento);
			tipoDocumentoClienteProveedor.setTipoDocumento(tipoDocumentoEjb.findTipoDocumentoId(idTipoDocumentoSeleccionado));
			tipoDocumentoClienteProveedorEjb.create(tipoDocumentoClienteProveedor);
			 FacesContext.getCurrentInstance().addMessage("Cliente Creado", new FacesMessage("Nuevo Cliente Creado."));
			 clienteEdicion = new Cliente();
		} else {
			
			tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedorEjb.findTipoDocumentoClienteProveedorCliente(clienteEdicion.getIdCliente());
			tipoDocumentoClienteProveedor.setTipoDocumento(tipoDocumentoEjb.findTipoDocumentoId(idTipoDocumentoSeleccionado));
			tipoDocumentoClienteProveedor.setNumeroDocumento(numeroDocumento);
			// Modificar Cliente
			clienteEdicion = clienteEjb.update(clienteEdicion);
			tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedorEjb.update(tipoDocumentoClienteProveedor);
			FacesContext.getCurrentInstance().addMessage("Cliente Modificado", new FacesMessage("Cliente Modificado."));
		}
	}
	public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        }
    }
	
	public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), clienteEdicion.getNombre() + clienteEdicion.getApellido());
        emptyModel.addOverlay(marker);
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Ubicacion Agregada", "Lat:" + lat + ", Lng:" + lng));
    }
	
	public void onReverseGeocode(ReverseGeocodeEvent event) {
        List<String> addresses = event.getAddresses();
        LatLng coord = event.getLatlng();
         
        if (addresses != null && !addresses.isEmpty()) {
            centerRevGeoMap = lat + "," + lng;
            revGeoModel.addOverlay(new Marker(coord, addresses.get(0)));
        }
    }
	public List<TipoDocumento> getListTipoDocumento() {
		return listTipoDocumento;
	}
	public void setListTipoDocumento(List<TipoDocumento> listTipoDocumento) {
		this.listTipoDocumento = listTipoDocumento;
	}
	public Long getIdTipoDocumentoSeleccionado() {
		return idTipoDocumentoSeleccionado;
	}
	public void setIdTipoDocumentoSeleccionado(Long idTipoDocumentoSeleccionado) {
		this.idTipoDocumentoSeleccionado = idTipoDocumentoSeleccionado;
	}
	public TipoDocumentoClienteProveedor getTipoDocumentoClienteProveedor() {
		return tipoDocumentoClienteProveedor;
	}
	public void setTipoDocumentoClienteProveedor(TipoDocumentoClienteProveedor tipoDocumentoClienteProveedor) {
		this.tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedor;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
}

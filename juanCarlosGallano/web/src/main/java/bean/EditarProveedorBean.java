package bean;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import ejb.ProveedorEJB;
import ejb.TipoDocumentoClienteProveedorEJB;
import ejb.TipoDocumentoEJB;
import entities.Cliente;
import entities.Deposito;
import entities.Proveedor;
import entities.TipoDocumento;
import entities.TipoDocumentoClienteProveedor;

@ManagedBean (name = "editarProveedorBean")
@ViewScoped
public class EditarProveedorBean {
	
	@EJB
	ProveedorEJB proveedorEjb;
	@EJB
	TipoDocumentoEJB tipoDocumentoEjb;
	
	@EJB
	TipoDocumentoClienteProveedorEJB tipoDocumentoClienteProveedorEjb;
	
	private Long idProveedor;
	private Proveedor proveedorEdicion;
	
	private MapModel emptyModel;
    
    private Float lat = (float) -25.242067;
      
    private Float lng = (float) -57.544233;
	
    
    private List<TipoDocumento> listTipoDocumento;
    private Long idTipoDocumentoSeleccionado;
    private TipoDocumentoClienteProveedor tipoDocumentoClienteProveedor;
	private String numeroDocumento;
	 private MapModel geoModel;
    private MapModel revGeoModel;
    private String centerGeoMap = "-25.242067, -57.544233";
    private String centerRevGeoMap = "-25.242067, -57.544233";
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public Proveedor getProveedorEdicion() {
		return proveedorEdicion;
	}
	public void setProveedorEdicion(Proveedor proveedorEdicion) {
		this.proveedorEdicion = proveedorEdicion;
	}
	
	
	public void init(){
		 listTipoDocumento = tipoDocumentoEjb.findAllActivo();
		if (!FacesContext.getCurrentInstance().isPostback()){
			Map<String,String> params =
	                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			try{
				idProveedor = Long.parseLong(params.get("idProveedor"));
			} catch (Exception ex){
				System.out.println("Se tiene un error al parsear el ID Param :" + ex.getMessage());
			}
			if (idProveedor != null){
				try{
					proveedorEdicion = proveedorEjb.findProveedorId(idProveedor);
					tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedorEjb.findTipoDocumentoClienteProveedorId(proveedorEdicion.getIdProveedor());
				} catch(Exception ex){
					FacesContext.getCurrentInstance().addMessage("Proveedor no posee Tipo de Documento", new FacesMessage("Proveedor no posee Tipo de Documento"));
				}
				if (tipoDocumentoClienteProveedor != null){
					idTipoDocumentoSeleccionado = tipoDocumentoClienteProveedor.getTipoDocumento().getId();
					numeroDocumento = tipoDocumentoClienteProveedor.getNumeroDocumento();
				} else {
					//idTipoDocumentoSeleccionado=(long) 0;
					numeroDocumento = "";
				}
				
				if (proveedorEdicion.getLatitud() != null){
					lat = proveedorEdicion.getLatitud();
					lng = proveedorEdicion.getLongitud();
				}
				emptyModel = new DefaultMapModel();
				Marker marker = new Marker(new LatLng(lat,lng));
				//emptyModel.addOverlay(circulo);
				emptyModel.addOverlay(marker);
			} else {
				proveedorEdicion = new Proveedor();
				tipoDocumentoClienteProveedor = new TipoDocumentoClienteProveedor();
				numeroDocumento= "";
			}
		}
	}
	
	public void guardarProveedor(){
		// Debe persistir el usuario
		if(proveedorEdicion.getIdProveedor() == null){
			proveedorEdicion = proveedorEjb.create(proveedorEdicion);
			
			tipoDocumentoClienteProveedor = new TipoDocumentoClienteProveedor();
			tipoDocumentoClienteProveedor.setProveedor(proveedorEdicion);
			tipoDocumentoClienteProveedor.setNumeroDocumento(numeroDocumento);
			tipoDocumentoClienteProveedor.setTipoDocumento(tipoDocumentoEjb.findTipoDocumentoId(idTipoDocumentoSeleccionado));
			tipoDocumentoClienteProveedorEjb.create(tipoDocumentoClienteProveedor);
			 FacesContext.getCurrentInstance().addMessage("Proveedor Creado", new FacesMessage("Nuevo Proveedor Creado."));
			 proveedorEdicion = new Proveedor();
		} else {
			
			tipoDocumentoClienteProveedor = tipoDocumentoClienteProveedorEjb.findTipoDocumentoClienteProveedorProveedor(proveedorEdicion.getIdProveedor());
			System.out.println("id Tipo Documento Seleccionado:" + idTipoDocumentoSeleccionado);
			tipoDocumentoClienteProveedor.setTipoDocumento(tipoDocumentoEjb.findTipoDocumentoId(idTipoDocumentoSeleccionado));
			tipoDocumentoClienteProveedor.setNumeroDocumento(numeroDocumento);
			// Modificar Proveedor
			proveedorEdicion = proveedorEjb.update(proveedorEdicion);
			FacesContext.getCurrentInstance().addMessage("Proveedor Modificado", new FacesMessage("Proveedor Modificado."));
		}
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
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
        Marker marker = new Marker(new LatLng(lat, lng), proveedorEdicion.getNombreEmpresa() + proveedorEdicion.getContacto());
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

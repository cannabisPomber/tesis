package Converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import entities.Rol;
@FacesConverter("teamPickListConverter")
public class RolPickLisConverter implements Converter {
	  @Override
	  public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
	      DualListModel<Rol> model = (DualListModel<Rol>) ((PickList) comp).getValue();
	      for (Rol rol : model.getSource()) {
	          if (rol.getIdRol().equals(value)) {
	              return rol;
	          }
	      }
	      for (Rol rol : model.getTarget()) {
	          if (rol.getIdRol().equals(value)) {
	              return rol;
	          }
	      }
	      return null;
	  }

	  @Override
	  public String getAsString(FacesContext fc, UIComponent comp, Object value) {
	      return ((Rol) value).getNombreRol();
	  }
	}

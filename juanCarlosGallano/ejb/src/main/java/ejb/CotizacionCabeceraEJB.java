package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.CotizacionCabecera;
import entities.CotizacionCabecera;



@Stateless
@LocalBean
public class CotizacionCabeceraEJB extends GenericDaoImpl<CotizacionCabecera, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	 public List<CotizacionCabecera> CotizacionCabeceras = new ArrayList<CotizacionCabecera>();
	    
    public List<CotizacionCabecera> findAll() {
    	TypedQuery<CotizacionCabecera> query = em.createQuery("select c from cotizacion_cabecera c", CotizacionCabecera.class);
    	CotizacionCabeceras= query.getResultList();
    	return CotizacionCabeceras;
    }
    
    public List<CotizacionCabecera> findAllActivo() {
    	TypedQuery<CotizacionCabecera> query = em.createQuery("select c from cotizacion_cabecera c where c.estado = 'ACTIVO'", CotizacionCabecera.class);
    	CotizacionCabeceras= query.getResultList();
    	return CotizacionCabeceras;
    }
    public List<CotizacionCabecera> findAllActualizado() {
    	TypedQuery<CotizacionCabecera> query = em.createQuery("select c from cotizacion_cabecera c where c.estado = 'ACTUALIZADO'", CotizacionCabecera.class);
    	CotizacionCabeceras= query.getResultList();
    	return CotizacionCabeceras;
    }
    
    public CotizacionCabecera findCotizacionCabecera (Long idCotizacionCabecera){
    	try {
    		   Query query = em.createQuery("select u from cotizacion_cabecera u where u.idCotizacionCabecera = :idCotizacionCabecera");
    		   query.setParameter("idCotizacionCabecera", idCotizacionCabecera);
    		   
    		   
    		   return (CotizacionCabecera) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }
}
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

import entities.TipoDocumento;

@Stateless
@LocalBean
public class TipoDocumentoEJB extends GenericDaoImpl<TipoDocumento, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	public List<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento>();
	
	public List<TipoDocumento> findAll() {
    	TypedQuery<TipoDocumento> query = em.createQuery("select c from tipo_documento c", TipoDocumento.class);
    	tipoDocumentos = query.getResultList();
    	return tipoDocumentos;
    }
	
	public List<TipoDocumento> findAllActivo() {
    	TypedQuery<TipoDocumento> query = em.createQuery("select c from tipo_documento c where c.estado = 'Activo'", TipoDocumento.class);
    	tipoDocumentos = query.getResultList();
    	return tipoDocumentos;
    }
	
	public TipoDocumento findTipoDocumentoId (Long idTipoDocumento){
    	try {
    		   Query query = em.createQuery("select u from tipo_documento u where u.id = :idTipoDocumento");
    		   query.setParameter("idTipoDocumento", idTipoDocumento);
    		   
    		   
    		   return (TipoDocumento) query.getSingleResult();
    		   }
    		   catch (Exception ex){
    			   System.out.println(" Error " + ex.getMessage());
    			   return null;
    		   }
    }

}

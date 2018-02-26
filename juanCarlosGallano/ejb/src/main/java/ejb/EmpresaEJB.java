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

import entities.Deposito;
import entities.Empresa;

@Stateless
@LocalBean
public class EmpresaEJB extends GenericDaoImpl<Empresa, Serializable>{
	
	@PersistenceContext(unitName="primary")
    EntityManager em;
	
	private List<Empresa> empresaList = new ArrayList<Empresa>();
	
	public Empresa findEmpresaId(Long id){
		try {
			   Query query = em.createQuery("select u from empresa u where u.idEmpresa = :id");
			   query.setParameter("id", id);
			   
			   
			   return (Empresa) query.getSingleResult();
			   }
			   catch (Exception ex){
				   System.out.println(" Error " + ex.getMessage());
				   return null;
			   }
	}
	
	public Empresa findEmpresaRucCedula(String rucCedula){
		try {
			   Query query = em.createQuery("select u from empresa u where u.rucCedula like '%ruc%'");
			   query.setParameter("ruc", rucCedula);
			   
			   
			   return (Empresa) query.getSingleResult();
			   }
			   catch (Exception ex){
				   System.out.println(" Error " + ex.getMessage());
				   return null;
			   }
	}
	
	public List<Empresa> findAll() {
    	TypedQuery<Empresa> query = em.createQuery("select u from empresa u", Empresa.class);
    	empresaList = query.getResultList();
    	return empresaList;
   }
	
	public List<Empresa> findEmpresa() {
    	TypedQuery<Empresa> query = em.createQuery("select u from empresa u", Empresa.class);
    	
    	List<Empresa> listEmpresa = query.getResultList();
    	return listEmpresa;
   }

}


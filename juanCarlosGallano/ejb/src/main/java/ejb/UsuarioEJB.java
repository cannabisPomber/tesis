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

import entities.Grupo;
import entities.Usuario;

@Stateless
@LocalBean
public class UsuarioEJB extends GenericDaoImpl<Usuario, Serializable>{
	@PersistenceContext(unitName="primary")
    EntityManager em;
   
    public List<Usuario> usuarios = new ArrayList<Usuario>();
    
    public List<Usuario> findAll() {
    	TypedQuery<Usuario> query = em.createQuery("select c from usuario c", Usuario.class);
    	usuarios = query.getResultList();
    	return usuarios;
   }
   public Usuario findUsuario(Usuario user){
	   try {
	   Query query = em.createQuery("select u from usuario u where u.usuario = :user and u.password = :pass");
	   query.setParameter("user", user.getUsuario());
	   query.setParameter("pass", user.getPassword());
	   
	   
	   return (Usuario) query.getSingleResult();
	   }
	   catch (Exception ex){
		   System.out.println(" Error " + ex.getMessage());
		   return null;
	   }
   }
   
   public Usuario findIdUsuario(Long id){
	   try {
	   Query query = em.createQuery("select u from usuario u where u.idUsuario = :id");
	   query.setParameter("id", id);
	   
	   Usuario prueba = (Usuario) query.getSingleResult();
	   return prueba;
//	   return (Usuario) query.getSingleResult();
	   }
	   catch (Exception ex){
		   System.out.println(" Error " + ex.getMessage());
		   return null;
	   }
   }
   
   public Usuario findUserUsuario(String user){
	   try {
	   Query query = em.createQuery("select u from usuario u where u.usuario = :user");
	   query.setParameter("user", user);
	   
	   Usuario prueba = (Usuario) query.getSingleResult();
	   return prueba;
//	   return (Usuario) query.getSingleResult();
	   }
	   catch (Exception ex){
		   System.out.println(" Error " + ex.getMessage());
		   return null;
	   }
   }
   
   public void eliminarUsuario(Usuario user){
	   em.createQuery("delete from usuario where idUsuario = : id").setParameter("id", user.getIdUsuario()).executeUpdate();
   }
    
   public Grupo usuarioFindGrupo (Usuario user){
	   try {
		   Query query = em.createQuery("select u.grupo from usuario u where u.idUsuario = :id");
		   query.setParameter("id", user.getIdUsuario());
		   
		   Grupo gruporesul = (Grupo) query.getSingleResult();
		   
		   System.out.println("query resul " + gruporesul.getDescripcionGrupo() + " " + gruporesul.getNombreGrupo());
		   return gruporesul;
		   }
		   catch (Exception ex){
			   System.out.println(" Error " + ex.getMessage());
			   return null;
		   }
   }
}

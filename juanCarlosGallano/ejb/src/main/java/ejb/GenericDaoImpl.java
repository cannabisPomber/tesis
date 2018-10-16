package ejb;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

public class GenericDaoImpl<T, PK extends Serializable> 
implements GenericDao<T, PK> {

protected Class<T> entityClass;

@PersistenceContext
protected EntityManager entityManager;

@SuppressWarnings("unchecked")
public GenericDaoImpl() {
    /*ParameterizedType genericSuperclass = (ParameterizedType) getClass()
         .getGenericSuperclass();
    this.entityClass = (Class<T>) genericSuperclass
         .getActualTypeArguments()[0];*/
}

@Override
public T create(T t) {
    this.entityManager.persist(t);
    this.entityManager.flush();
    return t;
}

/*@Override
public T read(PK id) {
    return this.entityManager.find(entityClass, id);
}*/

@Override
public T update(T t) {
	t = this.entityManager.merge(t);
	this.entityManager.flush();
    return t;
}

@Override
public void delete(T t) {
    t = this.entityManager.merge(t);
    this.entityManager.remove(t);
}

public void actualizar(T t) {

    //EntityManager manager = EM_FACTORY.createEntityManager();
    EntityTransaction transaction = null;
    try {
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(t);
        transaction.commit();
    } catch (Exception ex) {
        if (transaction != null) {
            transaction.rollback();
        }
        ex.printStackTrace();
    } finally {
    	entityManager.close();
    }
}

}

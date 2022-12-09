package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Method;

/**
 * @author zero
 */
@Repository
public class MethodDAOImpl implements MethodDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Methods_get1}")
    String sp_Methods_get1;

    @Value("${sp_Methods_get0}")
    String sp_Methods_get0;

    @Value("${sp_Methods_findID}")
    String sp_Methods_findID;

    @Value("${sp_Methods_insert}")
    String sp_Methods_insert;

    @Value("${sp_Methods_update}")
    String sp_Methods_update;

    @Value("${sp_Methods_delLogic}")
    String sp_Methods_delLogic;

    @Value("${sp_Methods_delPhysic}")
    String sp_Methods_delPhysic;

    @Value("${sp_Methods_restore}")
    String sp_Methods_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Method> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_get0, Method.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Method> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_get1, Method.class);
        return query.getResultList();
    }

    @Override
    public Method findID(Integer methodID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_findID, Method.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.setParameter(1, methodID);
        return (Method)query.getSingleResult();
    }

    @Override
    public boolean insert(Method method, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_insert, Method.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.setParameter(1, method.getMethodName());
		query.setParameter(2, method.getNote());
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Method method, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_update, Method.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
		query.setParameter(1, method.getMethodID());
		query.setParameter(2, method.getMethodName());
		query.setParameter(3, method.getNote());
        query.setParameter(4, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(Integer methodID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_delLogic, Method.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, methodID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer methodID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_delPhysic, Method.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, methodID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer methodID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Methods_restore, Method.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, methodID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

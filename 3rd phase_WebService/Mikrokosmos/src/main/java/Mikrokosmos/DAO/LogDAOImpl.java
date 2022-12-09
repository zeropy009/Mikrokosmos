package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Log;

/**
 * @author zero
 */
@Repository
public class LogDAOImpl implements LogDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Logs_get1}")
    String sp_Logs_get1;

    @Value("${sp_Logs_get0}")
    String sp_Logs_get0;

    @Value("${sp_Logs_findID}")
    String sp_Logs_findID;

    @Value("${sp_Logs_insert}")
    String sp_Logs_insert;

    @Value("${sp_Logs_update}")
    String sp_Logs_update;

    @Value("${sp_Logs_delLogic}")
    String sp_Logs_delLogic;

    @Value("${sp_Logs_delPhysic}")
    String sp_Logs_delPhysic;

    @Value("${sp_Logs_restore}")
    String sp_Logs_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Log> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_get0, Log.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Log> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_get1, Log.class);
        return query.getResultList();
    }

    @Override
    public Log findID(Integer logID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_findID, Log.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.setParameter(1, logID);
        return (Log)query.getSingleResult();
    }

    @Override
    public boolean deleteLogic(Integer logID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_delLogic, Log.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, logID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer logID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_delPhysic, Log.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, logID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer logID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Logs_restore, Log.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, logID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

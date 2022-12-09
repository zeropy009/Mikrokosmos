package Mikrokosmos.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.HistoryPoint;

/**
 * @author zero
 */
@Repository
public class HistoryPointDAOImpl implements HistoryPointDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_HistoryPoints_get1}")
    String sp_HistoryPoints_get1;

    @Value("${sp_HistoryPoints_get0}")
    String sp_HistoryPoints_get0;

    @Value("${sp_HistoryPoints_findID}")
    String sp_HistoryPoints_findID;

    @Value("${sp_HistoryPoints_insert}")
    String sp_HistoryPoints_insert;

    @Value("${sp_HistoryPoints_update}")
    String sp_HistoryPoints_update;

    @Value("${sp_HistoryPoints_delLogic}")
    String sp_HistoryPoints_delLogic;

    @Value("${sp_HistoryPoints_delPhysic}")
    String sp_HistoryPoints_delPhysic;

    @Value("${sp_HistoryPoints_restore}")
    String sp_HistoryPoints_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoryPoint> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_get0, HistoryPoint.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistoryPoint> get1(String customerID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_get1, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
         query.setParameter(1, customerID);
        return query.getResultList();
    }

    @Override
    public HistoryPoint findID(Integer historyPointID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_findID, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.setParameter(1, historyPointID);
        return (HistoryPoint) query.getSingleResult();
    }

    @Override
    public boolean insert(HistoryPoint historyPoint, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_insert, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.setParameter(1, historyPoint.getCustomerID());
        query.setParameter(2, historyPoint.getDate());
        query.setParameter(3, historyPoint.getPoint());
        query.setParameter(4, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(HistoryPoint historyPoint, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_update, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.setParameter(1, historyPoint.getHistoryPointID());
        query.setParameter(2, historyPoint.getCustomerID());
        query.setParameter(3, historyPoint.getDate());
        query.setParameter(4, historyPoint.getPoint());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(Integer historyPointID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_delLogic, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPointID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer historyPointID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_delPhysic, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPointID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer historyPointID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPoints_restore, HistoryPoint.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPointID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

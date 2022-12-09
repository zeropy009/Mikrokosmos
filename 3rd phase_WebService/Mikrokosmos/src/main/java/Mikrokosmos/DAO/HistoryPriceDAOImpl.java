package Mikrokosmos.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.HistoryPrice;

/**
 * @author zero
 */
@Repository
public class HistoryPriceDAOImpl implements HistoryPriceDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Value("${sp_HistoryPrices_get1}")
    String sp_HistoryPrices_get1;

    @Value("${sp_HistoryPrices_get0}")
    String sp_HistoryPrices_get0;

    @Value("${sp_HistoryPrices_findID}")
    String sp_HistoryPrices_findID;

    @Value("${sp_HistoryPrices_insert}")
    String sp_HistoryPrices_insert;

    @Value("${sp_HistoryPrices_update}")
    String sp_HistoryPrices_update;

    @Value("${sp_HistoryPrices_delLogic}")
    String sp_HistoryPrices_delLogic;

    @Value("${sp_HistoryPrices_delPhysic}")
    String sp_HistoryPrices_delPhysic;

    @Value("${sp_HistoryPrices_restore}")
    String sp_HistoryPrices_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<HistoryPrice> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_get0, HistoryPrice.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<HistoryPrice> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_get1, HistoryPrice.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<HistoryPrice> findID(String bookID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_findID, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        return query.getResultList();
    }

    @Override
    public boolean insert(HistoryPrice historyPrice, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_insert, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Double.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
		query.setParameter(1, historyPrice.getBookID());
		query.setParameter(2, historyPrice.getStartDate());
		query.setParameter(3, historyPrice.getPrice());
        query.setParameter(4, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(HistoryPrice historyPrice, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_update, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.setParameter(1, historyPrice.getHistoryPriceID());
		query.setParameter(2, historyPrice.getBookID());
		query.setParameter(3, historyPrice.getStartDate());
		query.setParameter(4, historyPrice.getPrice());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(Integer historyPriceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_delLogic, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPriceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer historyPriceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_delPhysic, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPriceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer historyPriceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_HistoryPrices_restore, HistoryPrice.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, historyPriceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

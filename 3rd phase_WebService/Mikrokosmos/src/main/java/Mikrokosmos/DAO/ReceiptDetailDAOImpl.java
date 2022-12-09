package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.ReceiptDetail;

/**
 * @author zero
 */
@Repository
public class ReceiptDetailDAOImpl implements ReceiptDetailDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_ReceiptDetails_get1}")
    String sp_ReceiptDetails_get1;

    @Value("${sp_ReceiptDetails_get0}")
    String sp_ReceiptDetails_get0;

    @Value("${sp_ReceiptDetails_findReceiptID}")
    String sp_ReceiptDetails_findReceiptID;

    @Value("${sp_ReceiptDetails_findBookID}")
    String sp_ReceiptDetails_findBookID;

    @Value("${sp_ReceiptDetails_insert}")
    String sp_ReceiptDetails_insert;

    @Value("${sp_ReceiptDetails_update}")
    String sp_ReceiptDetails_update;

    @Value("${sp_ReceiptDetails_delLogic}")
    String sp_ReceiptDetails_delLogic;

    @Value("${sp_ReceiptDetails_delPhysic}")
    String sp_ReceiptDetails_delPhysic;

    @Value("${sp_ReceiptDetails_restore}")
    String sp_ReceiptDetails_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<ReceiptDetail> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_get0, ReceiptDetail.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ReceiptDetail> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_get1, ReceiptDetail.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<ReceiptDetail> findReceiptID(String receiptID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_findReceiptID, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<ReceiptDetail> findBookID(String bookID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_findBookID, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        return query.getResultList();
    }

    @Override
    public boolean insert(ReceiptDetail receiptDetail, String receiptID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_insert, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, receiptDetail.getBookid());
        query.setParameter(3, receiptDetail.getAmount());
        query.setParameter(4, receiptDetail.getPrice());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(ReceiptDetail receiptDetail, String receiptID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_update, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, receiptDetail.getBookid());
        query.setParameter(3, receiptDetail.getAmount());
        query.setParameter(4, receiptDetail.getPrice());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String receiptID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_delLogic, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String receiptID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_delPhysic, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String receiptID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ReceiptDetails_restore, ReceiptDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }
}

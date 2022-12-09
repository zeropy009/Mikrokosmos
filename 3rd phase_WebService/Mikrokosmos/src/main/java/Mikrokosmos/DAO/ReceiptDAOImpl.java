package Mikrokosmos.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Receipt;
import Mikrokosmos.Model.ReceiptDetailDTO;

/**
 * @author zero
 */
@Repository
public class ReceiptDAOImpl implements ReceiptDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Receipts_get1}")
    String sp_Receipts_get1;

    @Value("${sp_Receipts_get0}")
    String sp_Receipts_get0;

    @Value("${sp_Receipts_findID}")
    String sp_Receipts_findID;

    @Value("${sp_Receipts_insert}")
    String sp_Receipts_insert;

    @Value("${sp_Receipts_update}")
    String sp_Receipts_update;

    @Value("${sp_Receipts_delLogic}")
    String sp_Receipts_delLogic;

    @Value("${sp_Receipts_delPhysic}")
    String sp_Receipts_delPhysic;

    @Value("${sp_Receipts_restore}")
    String sp_Receipts_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<Receipt> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_get0, Receipt.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Receipt> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_get1, Receipt.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<ReceiptDetailDTO> findID(String receiptID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_findID, ReceiptDetailDTO.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        return query.getResultList();
    }

    @Override
    public boolean insert(Receipt receipt, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_insert, Receipt.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, receipt.getSupplierID());
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Receipt receipt, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_update, Receipt.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.setParameter(1, receipt.getReceiptID());
        query.setParameter(2, receipt.getSupplierName());
        query.setParameter(3, receipt.getDate());
        query.setParameter(4, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String receiptID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_delLogic, Receipt.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String receiptID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_delPhysic, Receipt.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String receiptID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Receipts_restore, Receipt.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, receiptID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

package Mikrokosmos.DAO;

import Mikrokosmos.Model.BookInvoiceDTO;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.InvoiceDetail;

/**
 * @author zero
 */
@Repository
public class InvoiceDetailDAOImpl implements InvoiceDetailDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_InvoiceDetails_get1}")
    String sp_InvoiceDetails_get1;

    @Value("${sp_InvoiceDetails_get0}")
    String sp_InvoiceDetails_get0;

    @Value("${sp_InvoiceDetails_findInvoiceID}")
    String sp_InvoiceDetails_findInvoiceID;

    @Value("${sp_InvoiceDetails_findBookID}")
    String sp_InvoiceDetails_findBookID;

    @Value("${sp_InvoiceDetails_insert}")
    String sp_InvoiceDetails_insert;

    @Value("${sp_InvoiceDetails_update}")
    String sp_InvoiceDetails_update;

    @Value("${sp_InvoiceDetails_delLogic}")
    String sp_InvoiceDetails_delLogic;

    @Value("${sp_InvoiceDetails_delPhysic}")
    String sp_InvoiceDetails_delPhysic;

    @Value("${sp_InvoiceDetails_restore}")
    String sp_InvoiceDetails_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<InvoiceDetail> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_get0, InvoiceDetail.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InvoiceDetail> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_get1, InvoiceDetail.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<BookInvoiceDTO> findInvoiceID(String invoiceID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_findInvoiceID, BookInvoiceDTO.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        return query.getResultList();
    }

    @Override
    public InvoiceDetail findBookID(String bookID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_findBookID, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        return (InvoiceDetail) query.getSingleResult();
    }

    @Override
    public boolean insert(InvoiceDetail invoiceDetail, String invoiceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_insert, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Float.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, invoiceDetail.getBookID());
        query.setParameter(3, invoiceDetail.getQuantity());
        query.setParameter(4, invoiceDetail.getDiscount());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(InvoiceDetail invoiceDetail, String invoiceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_update, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Float.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, invoiceDetail.getBookID());
        query.setParameter(3, invoiceDetail.getQuantity());
        query.setParameter(4, invoiceDetail.getDiscount());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String invoiceID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_delLogic, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String invoiceID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_delPhysic, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);;
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String invoiceID, String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_InvoiceDetails_restore, InvoiceDetail.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, bookID);
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }
}

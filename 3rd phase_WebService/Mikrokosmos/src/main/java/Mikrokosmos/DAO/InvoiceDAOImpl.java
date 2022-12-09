package Mikrokosmos.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Invoice;
import Mikrokosmos.Model.InvoiceTableDTO;

/**
 * @author zero
 */
@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Invoices_get1}")
    String sp_Invoices_get1;

    @Value("${sp_Invoices_get0}")
    String sp_Invoices_get0;

    @Value("${sp_Invoices_findID}")
    String sp_Invoices_findID;

    @Value("${sp_Invoices_insert}")
    String sp_Invoices_insert;

    @Value("${sp_Invoices_update}")
    String sp_Invoices_update;

    @Value("${sp_Invoices_delLogic}")
    String sp_Invoices_delLogic;

    @Value("${sp_Invoices_delPhysic}")
    String sp_Invoices_delPhysic;

    @Value("${sp_Invoices_restore}")
    String sp_Invoices_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_get0, Invoice.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InvoiceTableDTO> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_get1, InvoiceTableDTO.class);
        return query.getResultList();
    }

    @Override
    public Invoice findID(String invoiceID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_findID, Invoice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        return (Invoice) query.getSingleResult();
    }

    @Override
    public boolean insert(Invoice invoice, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_insert, Invoice.class);
        query.registerStoredProcedureParameter(1, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.setParameter(1, invoice.getSolDate());
        //query.setParameter(2, invoice.getShipDate());
        query.setParameter(3, invoice.getShipStatus());
        query.setParameter(4, invoice.getPayStatus());
        //query.setParameter(5, invoice.getMethodID());
        query.setParameter(6, invoice.getStaffID());
        query.setParameter(7, invoice.getCustomerID());
        query.setParameter(8, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Invoice invoice, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_update, Invoice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
        query.setParameter(1, invoice.getInvoiceID());
        query.setParameter(2, invoice.getSolDate());
        query.setParameter(3, invoice.getPayStatus());
        query.setParameter(4, invoice.getShipStatus());;
        query.setParameter(5, invoice.getMethodID());
        query.setParameter(6, invoice.getCustomerID());
        query.setParameter(7, invoice.getStaffID());
        query.setParameter(8, invoice.getDiscount());
        query.setParameter(9, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String invoiceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_delLogic, Invoice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String invoiceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_delPhysic, Invoice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String invoiceID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Invoices_restore, Invoice.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

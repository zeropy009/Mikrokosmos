package Mikrokosmos.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import Mikrokosmos.DAO.InvoiceDAO;
import Mikrokosmos.DAO.InvoiceDAOImpl;
import Mikrokosmos.Model.Invoice;
import Mikrokosmos.Model.InvoiceTableDTO;

/**
 * @author zero
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    Logger logger = LoggerFactory.getLogger(InvoiceDAOImpl.class);

    @Autowired
    InvoiceDAO invoiceDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Invoice> get0() {
        return invoiceDAO.get0();
    }

    @Override
    public List<InvoiceTableDTO> get1() {
        return invoiceDAO.get1();
    }

    @Override
    public Invoice findID(String invoiceID) {
        try {
            return invoiceDAO.findID(invoiceID);
        } catch (Exception e) {
            logger.error("Get Invoice failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Invoice invoice, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoice.getMethodID() == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            if (invoice.getPayStatus().isEmpty()) {
                logger.warn("Pay status can't be empty");
                throw new Exception();
            }
            if (invoice.getShipStatus().isEmpty()) {
                logger.warn("Ship status can't be empty");
                throw new Exception();
            }
            if (invoice.getStaffID().isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            if (invoice.getCustomerID().isEmpty()) {
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDAO.insert(invoice, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Invoice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Invoice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Invoice invoice, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoice.getInvoiceID().isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (invoice.getPayStatus().isEmpty()) {
                logger.warn("Pay status can't be empty");
                throw new Exception();
            }
            if (invoice.getShipStatus().isEmpty()) {
                logger.warn("Ship status can't be empty");
                throw new Exception();
            }
            if (invoice.getMethodID() == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            if (invoice.getStaffID().isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            if (invoice.getCustomerID().isEmpty()) {
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDAO.update(invoice, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Invoice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Invoice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String invoiceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDAO.deleteLogic(invoiceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Invoice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Invoice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String invoiceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDAO.deletePhysical(invoiceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Invoice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Invoice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String invoiceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDAO.restore(invoiceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Invoice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Invoice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

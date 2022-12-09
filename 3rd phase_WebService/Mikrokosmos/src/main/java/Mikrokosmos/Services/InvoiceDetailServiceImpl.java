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

import Mikrokosmos.DAO.InvoiceDetailDAO;
import Mikrokosmos.DAO.InvoiceDetailDAOImpl;
import Mikrokosmos.Model.BookInvoiceDTO;
import Mikrokosmos.Model.InvoiceDetail;

/**
 * @invoiceDetail zero
 */
@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService {

    Logger logger = LoggerFactory.getLogger(InvoiceDetailDAOImpl.class);

    @Autowired
    InvoiceDetailDAO invoiceDetailDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<InvoiceDetail> get0() {
        return invoiceDetailDAO.get0();
    }

    @Override
    public List<InvoiceDetail> get1() {
        return invoiceDetailDAO.get1();
    }

    @Override
    public List<BookInvoiceDTO> findInvoiceID(String invoiceID) {
        try {
            return invoiceDetailDAO.findInvoiceID(invoiceID);
        } catch (Exception e) {
            logger.error("Get InvoiceDetail failed " + e);
            return null;
        }
    }

    @Override
    public InvoiceDetail findBookID(String bookID) {
        try {
            return invoiceDetailDAO.findBookID(bookID);
        } catch (Exception e) {
            logger.error("Get InvoiceDetail failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(InvoiceDetail invoiceDetail, String invoiceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (invoiceDetail.getBookID().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (invoiceDetail.getQuantity() <= 0) {
                logger.warn("Quantity must be > 0");
                throw new Exception();
            }
            if (invoiceDetail.getDiscount() < 0) {
                logger.warn("Discount must be >= 0");
                throw new Exception();
            }
            boolean result = invoiceDetailDAO.insert(invoiceDetail, invoiceID, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert InvoiceDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert InvoiceDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(InvoiceDetail invoiceDetail, String invoiceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (invoiceDetail.getBookID().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (invoiceDetail.getQuantity() <= 0) {
                logger.warn("Quantity must be > 0");
                throw new Exception();
            }
            if (invoiceDetail.getDiscount() < 0) {
                logger.warn("Discount must be >= 0");
                throw new Exception();
            }
            boolean result = invoiceDetailDAO.update(invoiceDetail, invoiceID, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update InvoiceDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update InvoiceDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String invoiceID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDetailDAO.deleteLogic(invoiceID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic InvoiceDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic InvoiceDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String invoiceID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDetailDAO.deletePhysical(invoiceID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete InvoiceDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete InvoiceDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String invoiceID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (invoiceID.isEmpty()) {
                logger.warn("Invoice ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = invoiceDetailDAO.restore(invoiceID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore InvoiceDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore InvoiceDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

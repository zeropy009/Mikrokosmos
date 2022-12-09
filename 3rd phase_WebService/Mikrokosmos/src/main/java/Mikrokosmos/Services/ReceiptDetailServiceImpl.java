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

import Mikrokosmos.DAO.ReceiptDetailDAO;
import Mikrokosmos.DAO.ReceiptDetailDAOImpl;
import Mikrokosmos.Model.ReceiptDetail;

/**
 * @author zero
 */
@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService {

    Logger logger = LoggerFactory.getLogger(ReceiptDetailDAOImpl.class);

    @Autowired
    ReceiptDetailDAO receiptDetailDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<ReceiptDetail> get0() {
        return receiptDetailDAO.get0();
    }

    @Override
    public List<ReceiptDetail> get1() {
        return receiptDetailDAO.get1();
    }

    @Override
    public List<ReceiptDetail> findReceiptID(String receiptID) {
        try {
            return receiptDetailDAO.findReceiptID(receiptID);
        } catch (Exception e) {
            logger.error("Get ReceiptDetail failed " + e);
            return null;
        }
    }

    @Override
    public List<ReceiptDetail> findBookID(String bookID) {
        try {
            return receiptDetailDAO.findBookID(bookID);
        } catch (Exception e) {
            logger.error("Get ReceiptDetail failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(ReceiptDetail receiptDetail,String receiptID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            if (receiptDetail.getBookid().isEmpty()) {
                logger.info("Book ID can't be empty");
                throw new Exception();
            }
            if (receiptDetail.getAmount() <= 0) {
                logger.info("Price must be > 0");
                throw new Exception();
            }
            if (receiptDetail.getPrice() <= 0) {
                logger.info("Price must be > 0");
                throw new Exception();
            }
            boolean result = receiptDetailDAO.insert(receiptDetail, receiptID, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert ReceiptDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert ReceiptDetail failed " + e);
            e.printStackTrace();
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(ReceiptDetail receiptDetail, String receiptID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            if (receiptDetail.getBookid().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (receiptDetail.getAmount() <= 0) {
                logger.warn("Price must be > 0");
                throw new Exception();
            }
            if (receiptDetail.getPrice() <= 0) {
                logger.warn("Price must be > 0");
                throw new Exception();
            }
            boolean result = receiptDetailDAO.update(receiptDetail,receiptID, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update ReceiptDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update ReceiptDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String receiptID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDetailDAO.deleteLogic(receiptID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic ReceiptDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic ReceiptDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String receiptID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDetailDAO.deletePhysical(receiptID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete ReceiptDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete ReceiptDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String receiptID, String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDetailDAO.restore(receiptID, bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore ReceiptDetail failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore ReceiptDetail failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

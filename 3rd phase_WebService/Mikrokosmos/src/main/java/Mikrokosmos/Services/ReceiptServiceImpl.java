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

import Mikrokosmos.DAO.ReceiptDAO;
import Mikrokosmos.DAO.ReceiptDAOImpl;
import Mikrokosmos.Model.Receipt;
import Mikrokosmos.Model.ReceiptDetailDTO;

/**
 * @author zero
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

    Logger logger = LoggerFactory.getLogger(ReceiptDAOImpl.class);

    @Autowired
    ReceiptDAO receiptDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Receipt> get0() {
        return receiptDAO.get0();
    }

    @Override
    public List<Receipt> get1() {
        return receiptDAO.get1();
    }

    @Override
    public List<ReceiptDetailDTO> findID(String receiptID) {
        try {
            return receiptDAO.findID(receiptID);
        } catch (Exception e) {
            logger.error("Get Receipt failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Receipt receipt, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            boolean result = receiptDAO.insert(receipt, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Receipt failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Receipt failed " + e);
            e.printStackTrace();
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Receipt receipt, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receipt.getReceiptID().isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            if (receipt.getSupplierName().isEmpty()) {
                logger.warn("Supplier ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDAO.update(receipt, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Receipt failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Receipt failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String receiptID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDAO.deleteLogic(receiptID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Receipt failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Receipt failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String receiptID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDAO.deletePhysical(receiptID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Receipt failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Receipt failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String receiptID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (receiptID.isEmpty()) {
                logger.warn("Receipt ID can't be empty");
                throw new Exception();
            }
            boolean result = receiptDAO.restore(receiptID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Receipt failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Receipt failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

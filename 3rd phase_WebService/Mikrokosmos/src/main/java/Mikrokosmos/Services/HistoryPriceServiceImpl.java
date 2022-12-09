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

import Mikrokosmos.DAO.HistoryPriceDAO;
import Mikrokosmos.DAO.HistoryPriceDAOImpl;
import Mikrokosmos.Model.HistoryPrice;

/**
 * @author zero
 */
@Service
public class HistoryPriceServiceImpl implements HistoryPriceService {

	Logger logger = LoggerFactory.getLogger(HistoryPriceDAOImpl.class);

    @Autowired
    HistoryPriceDAO historyPriceDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<HistoryPrice> get0() {
        return historyPriceDAO.get0();
    }

    @Override
    public List<HistoryPrice> get1() {
        return historyPriceDAO.get1();
    }

    @Override
    public List<HistoryPrice> findID(String bookID) {
        try {
            return historyPriceDAO.findID(bookID);
        } catch (Exception e) {
            logger.error("Get HistoryPrice failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(HistoryPrice historyPrice, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPrice.getBookID().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (historyPrice.getPrice() <= 0) {
                logger.warn("Price must be > 0");
                throw new Exception();
            }
            boolean result = historyPriceDAO.insert(historyPrice, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert HistoryPrice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert HistoryPrice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(HistoryPrice historyPrice, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPrice.getHistoryPriceID() == null) {
                logger.warn("HistoryPrice ID can't be empty");
                throw new Exception();
            }
            if (historyPrice.getBookID().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (historyPrice.getPrice() <= 0) {
                logger.warn("Price must be > 0");
                throw new Exception();
            }
            boolean result = historyPriceDAO.update(historyPrice, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update HistoryPrice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update HistoryPrice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(Integer historyPriceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPriceID == null) {
                logger.warn("HistoryPrice ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPriceDAO.deleteLogic(historyPriceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic HistoryPrice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic HistoryPrice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer historyPriceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPriceID == null) {
                logger.warn("HistoryPrice ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPriceDAO.deletePhysical(historyPriceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete HistoryPrice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete HistoryPrice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer historyPriceID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPriceID == null) {
                logger.warn("HistoryPrice ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPriceDAO.restore(historyPriceID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore HistoryPrice failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore HistoryPrice failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

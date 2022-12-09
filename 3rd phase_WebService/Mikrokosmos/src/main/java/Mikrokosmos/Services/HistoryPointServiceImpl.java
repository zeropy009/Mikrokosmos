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

import Mikrokosmos.DAO.HistoryPointDAO;
import Mikrokosmos.DAO.HistoryPointDAOImpl;
import Mikrokosmos.Model.HistoryPoint;

/**
 * @author zero
 */
@Service
public class HistoryPointServiceImpl implements HistoryPointService {

	Logger logger = LoggerFactory.getLogger(HistoryPointDAOImpl.class);

    @Autowired
    HistoryPointDAO historyPointDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<HistoryPoint> get0() {
        return historyPointDAO.get0();
    }

    @Override
    public List<HistoryPoint> get1(String customerID) {
        return historyPointDAO.get1(customerID);
    }

    @Override
    public HistoryPoint findID(Integer historyPointID) {
        try {
            return historyPointDAO.findID(historyPointID);
        } catch (Exception e) {
            logger.error("Get HistoryPoint failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(HistoryPoint historyPoint, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPoint.getCustomerID().isEmpty()) {
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPointDAO.insert(historyPoint, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert HistoryPoint failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert HistoryPoint failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(HistoryPoint historyPoint, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPoint.getHistoryPointID() == null) {
                logger.warn("HistoryPoint ID can't be empty");
                throw new Exception();
            }
            if (historyPoint.getCustomerID().isEmpty()) {
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPointDAO.update(historyPoint, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update HistoryPoint failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update HistoryPoint failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(Integer historyPointID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPointID == null) {
                logger.warn("HistoryPoint ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPointDAO.deleteLogic(historyPointID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic HistoryPoint failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic HistoryPoint failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer historyPointID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPointID == null) {
                logger.warn("HistoryPoint ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPointDAO.deletePhysical(historyPointID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete HistoryPoint failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete HistoryPoint failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer historyPointID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (historyPointID == null) {
                logger.warn("HistoryPoint ID can't be empty");
                throw new Exception();
            }
            boolean result = historyPointDAO.restore(historyPointID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore HistoryPoint failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore HistoryPoint failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

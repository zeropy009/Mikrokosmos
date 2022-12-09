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

import Mikrokosmos.DAO.LogDAO;
import Mikrokosmos.DAO.LogDAOImpl;
import Mikrokosmos.Model.Log;

/**
 * @author zero
 */
@Service
public class LogServiceImpl implements LogService {

	Logger logger = LoggerFactory.getLogger(LogDAOImpl.class);

    @Autowired
    LogDAO logDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Log> get0() {
        return logDAO.get0();
    }

    @Override
    public List<Log> get1() {
        return logDAO.get1();
    }

    @Override
    public Log findID(Integer logID) {
        try {
            return logDAO.findID(logID);
        } catch (Exception e) {
            logger.error("Get Log failed " + e);
            return null;
        }
    }

    @Override
    public boolean deleteLogic(Integer logID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (logID == null) {
                logger.warn("Log ID can't be empty");
                throw new Exception();
            }
            boolean result = logDAO.deleteLogic(logID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Log failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Log failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer logID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (logID == null) {
                logger.warn("Log ID can't be empty");
                throw new Exception();
            }
            boolean result = logDAO.deletePhysical(logID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Log failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Log failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer logID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (logID == null) {
                logger.warn("Log ID can't be empty");
                throw new Exception();
            }
            boolean result = logDAO.restore(logID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Log failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Log failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

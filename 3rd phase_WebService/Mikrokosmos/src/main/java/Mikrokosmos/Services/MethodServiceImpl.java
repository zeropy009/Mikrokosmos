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

import Mikrokosmos.DAO.MethodDAO;
import Mikrokosmos.DAO.MethodDAOImpl;
import Mikrokosmos.Model.Method;

/**
 * @author zero
 */
@Service
public class MethodServiceImpl implements MethodService {

	Logger logger = LoggerFactory.getLogger(MethodDAOImpl.class);

    @Autowired
    MethodDAO methodDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Method> get0() {
        return methodDAO.get0();
    }

    @Override
    public List<Method> get1() {
        return methodDAO.get1();
    }

    @Override
    public Method findID(Integer methodID) {
        try {
            return methodDAO.findID(methodID);
        } catch (Exception e) {
            logger.error("Get Method failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Method method, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (method.getMethodName().isEmpty()) {
                logger.warn("Method name can't be empty");
                throw new Exception();
            }
            boolean result = methodDAO.insert(method, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Method failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Method failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Method method, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (method.getMethodID() == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            if (method.getMethodName().isEmpty()) {
                logger.warn("Method name can't be empty");
                throw new Exception();
            }
            boolean result = methodDAO.update(method, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Method failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Method failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(Integer methodID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (methodID == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            boolean result = methodDAO.deleteLogic(methodID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Method failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Method failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer methodID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (methodID == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            boolean result = methodDAO.deletePhysical(methodID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Method failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Method failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer methodID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (methodID == null) {
                logger.warn("Method ID can't be empty");
                throw new Exception();
            }
            boolean result = methodDAO.restore(methodID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Method failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Method failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

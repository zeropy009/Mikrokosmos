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

import Mikrokosmos.DAO.LevelDAO;
import Mikrokosmos.DAO.LevelDAOImpl;
import Mikrokosmos.Model.Level;

/**
 * @author zero
 */
@Service
public class LevelServiceImpl implements LevelService {

	Logger logger = LoggerFactory.getLogger(LevelDAOImpl.class);

    @Autowired
    LevelDAO levelDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Level> get0() {
        return levelDAO.get0();
    }

    @Override
    public List<Level> get1() {
        return levelDAO.get1();
    }

    @Override
    public Level findID(Integer levelID) {
        try {
            return levelDAO.findID(levelID);
        } catch (Exception e) {
            logger.error("Get Level failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Level level, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (level.getName().isEmpty()) {
                logger.warn("Name can't be empty");
                throw new Exception();
            }
            boolean result = levelDAO.insert(level, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Level failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Insert Level failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Level level, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (level.getLevelID() == null) {
                logger.warn("Level ID can't be empty");
                throw new Exception();
            }
            if (level.getName().isEmpty()) {
                logger.warn("Name can't be empty");
                throw new Exception();
            }
            boolean result = levelDAO.update(level, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Level failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Level failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(Integer levelID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (levelID == null) {
                logger.warn("Level ID can't be empty");
                throw new Exception();
            }
            boolean result = levelDAO.deleteLogic(levelID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Level failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Level failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer levelID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (levelID == null) {
                logger.warn("Level ID can't be empty");
                throw new Exception();
            }
            boolean result = levelDAO.deletePhysical(levelID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Level failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Level failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer levelID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (levelID == null) {
                logger.warn("Level ID can't be empty");
                throw new Exception();
            }
            boolean result = levelDAO.restore(levelID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Level failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Level failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

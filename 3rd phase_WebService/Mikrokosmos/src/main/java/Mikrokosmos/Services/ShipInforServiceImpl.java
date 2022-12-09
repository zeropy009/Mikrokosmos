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

import Mikrokosmos.DAO.ShipInforDAO;
import Mikrokosmos.DAO.ShipInforDAOImpl;
import Mikrokosmos.Model.ShipInfor;

/**
 * @author zero
 */
@Service
public class ShipInforServiceImpl implements ShipInforService {

    Logger logger = LoggerFactory.getLogger(ShipInforDAOImpl.class);

    @Autowired
    ShipInforDAO shipInforDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<ShipInfor> get0() {
        return shipInforDAO.get0();
    }

    @Override
    public List<ShipInfor> get1() {
        return shipInforDAO.get1();
    }

    @Override
    public ShipInfor findID(String invoiceID) {
        try {
            return shipInforDAO.findID(invoiceID);
        } catch (Exception e) {
            logger.error("Get ShipInfor failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(ShipInfor shipInfor, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (shipInfor.getFullName().isEmpty()) {
                logger.warn("Full name can't be empty");
                throw new Exception();
            }
            if (shipInfor.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (shipInfor.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            boolean result = shipInforDAO.insert(shipInfor, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert ShipInfor failed ");
                throw new Exception();
            }
        } catch (Exception e) {
            logger.info("Insert ShipInfor failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(ShipInfor shipInfor, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (shipInfor.getShipInforID() == null) {
                logger.warn("ShipInfor ID can't be empty");
                throw new Exception();
            }
            if (shipInfor.getFullName().isEmpty()) {
                logger.warn("Full name can't be empty");
                throw new Exception();
            }
            if (shipInfor.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (shipInfor.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            boolean result = shipInforDAO.update(shipInfor, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update ShipInfor failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update ShipInfor failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(Integer shipInforID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (shipInforID == null) {
                logger.warn("ShipInfor ID can't be empty");
                throw new Exception();
            }
            boolean result = shipInforDAO.deleteLogic(shipInforID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic ShipInfor failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic ShipInfor failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(Integer shipInforID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (shipInforID == null) {
                logger.warn("ShipInfor ID can't be empty");
                throw new Exception();
            }
            boolean result = shipInforDAO.deletePhysical(shipInforID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete ShipInfor failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete ShipInfor failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(Integer shipInforID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (shipInforID == null) {
                logger.warn("ShipInfor ID can't be empty");
                throw new Exception();
            }
            boolean result = shipInforDAO.restore(shipInforID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore ShipInfor failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore ShipInfor failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

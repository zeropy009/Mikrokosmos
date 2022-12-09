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

import Mikrokosmos.DAO.DepartDAO;
import Mikrokosmos.DAO.DepartDAOImpl;
import Mikrokosmos.Model.Depart;

/**
 * @author zero
 */
@Service
public class DepartServiceImpl implements DepartService {

	Logger logger = LoggerFactory.getLogger(DepartDAOImpl.class);

    @Autowired
    DepartDAO customerDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Depart> get0() {
        return customerDAO.get0();
    }

    @Override
    public List<Depart> get1() {
        return customerDAO.get1();
    }

    @Override
    public Depart findID(String customerID) {
        try {
            return customerDAO.findID(customerID);
        } catch (Exception e) {
            logger.error("Get Depart failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Depart customer, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customer.getDepartName().isEmpty()) {
                logger.warn("Depart name can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.insert(customer, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Depart failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Depart failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Depart customer, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customer.getDepartID().isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            if (customer.getDepartName().isEmpty()) {
                logger.warn("Depart name can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.update(customer, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Depart failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Depart failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String customerID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customerID.isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.deleteLogic(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Depart failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Depart failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String customerID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customerID.isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.deletePhysical(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Depart failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Depart failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String customerID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customerID.isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.restore(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Depart failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Depart failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

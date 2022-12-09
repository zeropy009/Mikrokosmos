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

import Mikrokosmos.DAO.SupplierDAO;
import Mikrokosmos.DAO.SupplierDAOImpl;
import Mikrokosmos.Model.Supplier;

/**
 * @author zero
 */
@Service
public class SupplierServiceImpl implements SupplierService {

	Logger logger = LoggerFactory.getLogger(SupplierDAOImpl.class);

    @Autowired
    SupplierDAO supplierDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Supplier> get0() {
        return supplierDAO.get0();
    }

    @Override
    public List<Supplier> get1() {
        return supplierDAO.get1();
    }

    @Override
    public Supplier findID(String supplierID) {
        try {
            return supplierDAO.findID(supplierID);
        } catch (Exception e) {
            logger.error("Get Supplier failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Supplier supplier, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (supplier.getSupplierName().isEmpty()) {
                logger.warn("Supplier name can't be empty");
                throw new Exception();
            }
            boolean result = supplierDAO.insert(supplier, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Supplier failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Supplier failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Supplier supplier, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (supplier.getSupplierID().isEmpty()) {
                logger.warn("Supplier ID can't be empty");
                throw new Exception();
            }
            if (supplier.getSupplierName().isEmpty()) {
                logger.warn("Supplier name can't be empty");
                throw new Exception();
            }
            boolean result = supplierDAO.update(supplier, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Supplier failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Supplier failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String supplierID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (supplierID.isEmpty()) {
                logger.warn("Supplier ID can't be empty");
                throw new Exception();
            }
            boolean result = supplierDAO.deleteLogic(supplierID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Supplier failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Supplier failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String supplierID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (supplierID.isEmpty()) {
                logger.warn("Supplier ID can't be empty");
                throw new Exception();
            }
            boolean result = supplierDAO.deletePhysical(supplierID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Supplier failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Supplier failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String supplierID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (supplierID.isEmpty()) {
                logger.warn("Supplier ID can't be empty");
                throw new Exception();
            }
            boolean result = supplierDAO.restore(supplierID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Supplier failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Supplier failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

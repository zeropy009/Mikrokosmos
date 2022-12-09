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

import Mikrokosmos.DAO.CustomerDAO;
import Mikrokosmos.DAO.CustomerDAOImpl;
import Mikrokosmos.Model.Customer;
import Mikrokosmos.Model.CustomerDTO;

/**
 * @author zero
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Customer> get0() {
        return customerDAO.get0();
    }

    @Override
    public List<CustomerDTO> get1() {
        return customerDAO.get1();
    }

    @Override
    public Customer findID(String customerID) {
        try {
            return customerDAO.findID(customerID);
        } catch (Exception e) {
            logger.error("Get Customer failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Customer customer, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customer.getCustomerName().isEmpty()) {
                logger.warn("Customer name can't be empty");
                throw new Exception();
            }
            if (customer.getEmail().isEmpty()) {
                logger.warn("Email can't be empty");
                throw new Exception();
            }
            if (customer.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (customer.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            if (customer.getPassword().isEmpty()) {
                logger.warn("Password can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.insert(customer, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Customer failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Customer failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Customer customer, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (customer.getCustomerID().isEmpty()) {
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            if (customer.getCustomerName().isEmpty()) {
                logger.warn("Customer name can't be empty");
                throw new Exception();
            }
            if (customer.getEmail().isEmpty()) {
                logger.warn("Email can't be empty");
                throw new Exception();
            }
            if (customer.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (customer.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.update(customer, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Customer failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Customer failed " + e);
            e.printStackTrace();
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
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.deleteLogic(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Customer failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Customer failed " + e);
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
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.deletePhysical(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Customer failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Customer failed " + e);
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
                logger.warn("Customer ID can't be empty");
                throw new Exception();
            }
            boolean result = customerDAO.restore(customerID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Customer failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Customer failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

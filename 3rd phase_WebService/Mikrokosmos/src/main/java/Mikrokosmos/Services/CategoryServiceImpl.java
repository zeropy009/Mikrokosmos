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

import Mikrokosmos.DAO.CategoryDAO;
import Mikrokosmos.DAO.CategoryDAOImpl;
import Mikrokosmos.Model.Category;

/**
 * @author zero
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Category> get0() {
        return categoryDAO.get0();
    }

    @Override
    public List<Category> get1() {
        return categoryDAO.get1();
    }

    @Override
    public Category findID(String categoryID) {
        try {
            return categoryDAO.findID(categoryID);
        } catch (Exception e) {
            logger.error("Get Category failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Category category, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
//            if (category.getCategoryName().isEmpty()) {
//                logger.warn("Category name can't be empty");
//                throw new Exception();
//            }
            boolean result = categoryDAO.insert(category, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Category failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Category failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Category category, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (category.getCategoryID().isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            if (category.getCategoryName().isEmpty()) {
                logger.warn("Category name can't be empty");
                throw new Exception();
            }
            boolean result = categoryDAO.update(category, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Category failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Category failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String categoryID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (categoryID.isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            boolean result = categoryDAO.deleteLogic(categoryID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Category failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Category failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String categoryID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (categoryID.isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            boolean result = categoryDAO.deletePhysical(categoryID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Category failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Category failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String categoryID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (categoryID.isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            boolean result = categoryDAO.restore(categoryID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Category failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Category failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

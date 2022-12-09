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

import Mikrokosmos.DAO.AuthorDAO;
import Mikrokosmos.DAO.AuthorDAOImpl;
import Mikrokosmos.Model.Author;

/**
 * @author zero
 */
@Service
public class AuthorServiceImpl implements AuthorService {

	Logger logger = LoggerFactory.getLogger(AuthorDAOImpl.class);

    @Autowired
    AuthorDAO authorDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Author> get0() {
        return authorDAO.get0();
    }

    @Override
    public List<Author> get1() {
        return authorDAO.get1();
    }

    @Override
    public Author findID(String authorID) {
        try {
            return authorDAO.findID(authorID);
        } catch (Exception e) {
            logger.error("Get Author failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Author author, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (author.getAuthorName().isEmpty()) {
                logger.warn("Author name can't be empty");
                throw new Exception();
            }
            boolean result = authorDAO.insert(author, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Author failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Author failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Author author, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (author.getAuthorID().isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            if (author.getAuthorName().isEmpty()) {
                logger.warn("Author name can't be empty");
                throw new Exception();
            }
            boolean result = authorDAO.update(author, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Author failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Author failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String authorID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (authorID.isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            boolean result = authorDAO.deleteLogic(authorID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Author failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Author failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String authorID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (authorID.isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            boolean result = authorDAO.deletePhysical(authorID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Author failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Author failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String authorID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (authorID.isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            boolean result = authorDAO.restore(authorID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Author failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Author failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

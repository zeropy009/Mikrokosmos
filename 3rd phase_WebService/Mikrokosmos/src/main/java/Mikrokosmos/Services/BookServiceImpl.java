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

import Mikrokosmos.DAO.BookDAO;
import Mikrokosmos.DAO.BookDAOImpl;
import Mikrokosmos.Model.Book;
import Mikrokosmos.Model.BookTableDTO;

/**
 * @book zero
 */
@Service
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

    @Autowired
    BookDAO bookDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Book> get0() {
        return bookDAO.get0();
    }

    @Override
    public List<BookTableDTO> get1() {
        return bookDAO.get1();
    }

    @Override
    public Book findID(String bookID) {
        try {
            return bookDAO.findID(bookID);
        } catch (Exception e) {
            logger.error("Get Book failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Book book, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (book.getBookTitle().isEmpty()) {
                logger.warn("Book title can't be empty");
                throw new Exception();
            }
            if (book.getDescription().isEmpty()) {
                logger.warn("Description can't be empty");
                throw new Exception();
            }
            if (book.getCategoryID().isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            if (book.getAuthorID().isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            boolean result = bookDAO.insert(book, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Book failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Book failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Book book, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (book.getBookID().isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            if (book.getBookTitle().isEmpty()) {
                logger.warn("Book title can't be empty");
                throw new Exception();
            }
            if (book.getDescription().isEmpty()) {
                logger.warn("Description can't be empty");
                throw new Exception();
            }
            if (book.getCategoryID().isEmpty()) {
                logger.warn("Category ID can't be empty");
                throw new Exception();
            }
            if (book.getAuthorID().isEmpty()) {
                logger.warn("Author ID can't be empty");
                throw new Exception();
            }
            boolean result = bookDAO.update(book, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Book failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Book failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = bookDAO.deleteLogic(bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Book failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Book failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = bookDAO.deletePhysical(bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Book failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Book failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String bookID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (bookID.isEmpty()) {
                logger.warn("Book ID can't be empty");
                throw new Exception();
            }
            boolean result = bookDAO.restore(bookID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Book failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Book failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }
}

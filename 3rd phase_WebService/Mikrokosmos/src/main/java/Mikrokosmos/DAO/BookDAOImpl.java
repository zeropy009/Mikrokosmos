package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Book;
import Mikrokosmos.Model.BookTableDTO;

/**
 * @book zero
 */
@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Books_get1}")
    String sp_Books_get1;

    @Value("${sp_Books_get0}")
    String sp_Books_get0;

    @Value("${sp_Books_findID}")
    String sp_Books_findID;

    @Value("${sp_Books_insert}")
    String sp_Books_insert;

    @Value("${sp_Books_update}")
    String sp_Books_update;

    @Value("${sp_Books_delLogic}")
    String sp_Books_delLogic;

    @Value("${sp_Books_delPhysic}")
    String sp_Books_delPhysic;

    @Value("${sp_Books_restore}")
    String sp_Books_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_get0, Book.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BookTableDTO> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_get1, BookTableDTO.class);
        return query.getResultList();
    }

    @Override
    public Book findID(String bookID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_findID, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        return (Book) query.getSingleResult();
    }

    @Override
    public boolean insert(Book book, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_insert, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.setParameter(1, book.getBookTitle());
        query.setParameter(2, book.getPrice());
        query.setParameter(3, book.getDescription());
        query.setParameter(4, book.getCategoryID());
        query.setParameter(5, book.getAuthorID());
        query.setParameter(6, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Book book, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_update, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.setParameter(1, book.getBookID());
        query.setParameter(2, book.getBookTitle());
        query.setParameter(3, book.getAmount());
        query.setParameter(4, book.getPrice());
        query.setParameter(5, book.getDescription());
        query.setParameter(6, book.getCategoryID());
        query.setParameter(7, book.getAuthorID());
        query.setParameter(8, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_delLogic, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_delPhysic, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String bookID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Books_restore, Book.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, bookID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

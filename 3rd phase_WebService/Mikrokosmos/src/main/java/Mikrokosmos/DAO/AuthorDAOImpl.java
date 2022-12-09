package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Author;

/**
 * @author zero
 */
@Repository
public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Authors_get1}")
    String sp_Authors_get1;

    @Value("${sp_Authors_get0}")
    String sp_Authors_get0;

    @Value("${sp_Authors_findID}")
    String sp_Authors_findID;

    @Value("${sp_Authors_insert}")
    String sp_Authors_insert;

    @Value("${sp_Authors_update}")
    String sp_Authors_update;

    @Value("${sp_Authors_delLogic}")
    String sp_Authors_delLogic;

    @Value("${sp_Authors_delPhysic}")
    String sp_Authors_delPhysic;

    @Value("${sp_Authors_restore}")
    String sp_Authors_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Author> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_get0, Author.class);
        return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
    public List<Author> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_get1, Author.class);
        return query.getResultList();
    }

    @Override
    public Author findID(String authorID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_findID, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, authorID);
        return (Author)query.getSingleResult();
    }

    @Override
    public boolean insert(Author author, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_insert, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, author.getAuthorName());
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Author author, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_update, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, author.getAuthorID());
        query.setParameter(2, author.getAuthorName());
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String authorID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_delLogic, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, authorID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String authorID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_delPhysic, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, authorID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String authorID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Authors_restore, Author.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, authorID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

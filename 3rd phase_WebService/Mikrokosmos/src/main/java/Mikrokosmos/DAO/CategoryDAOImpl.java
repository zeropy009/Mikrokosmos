package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Category;

/**
 * @author zero
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Categorys_get1}")
    String sp_Categorys_get1;

    @Value("${sp_Categorys_get0}")
    String sp_Categorys_get0;

    @Value("${sp_Categorys_findID}")
    String sp_Categorys_findID;

    @Value("${sp_Categorys_insert}")
    String sp_Categorys_insert;

    @Value("${sp_Categorys_update}")
    String sp_Categorys_update;

    @Value("${sp_Categorys_delLogic}")
    String sp_Categorys_delLogic;

    @Value("${sp_Categorys_delPhysic}")
    String sp_Categorys_delPhysic;

    @Value("${sp_Categorys_restore}")
    String sp_Categorys_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_get0, Category.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_get1, Category.class);
        return query.getResultList();
    }

    @Override
    public Category findID(String categoryID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_findID, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, categoryID);
        return (Category) query.getSingleResult();
    }

    @Override
    public boolean insert(Category category, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_insert, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, category.getCategoryName());
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Category category, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_update, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, category.getCategoryID());
        query.setParameter(2, category.getCategoryName());
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String categoryID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_delLogic, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, categoryID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String categoryID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_delPhysic, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, categoryID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String categoryID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Categorys_restore, Category.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, categoryID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

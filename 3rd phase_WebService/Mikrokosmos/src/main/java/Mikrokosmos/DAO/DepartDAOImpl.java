package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Depart;

/**
 * @author zero
 */
@Repository
public class DepartDAOImpl implements DepartDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Departs_get1}")
    String sp_Departs_get1;

    @Value("${sp_Departs_get0}")
    String sp_Departs_get0;

    @Value("${sp_Departs_findID}")
    String sp_Departs_findID;

    @Value("${sp_Departs_insert}")
    String sp_Departs_insert;

    @Value("${sp_Departs_update}")
    String sp_Departs_update;

    @Value("${sp_Departs_delLogic}")
    String sp_Departs_delLogic;

    @Value("${sp_Departs_delPhysic}")
    String sp_Departs_delPhysic;

    @Value("${sp_Departs_restore}")
    String sp_Departs_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Depart> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_get0, Depart.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Depart> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_get1, Depart.class);
        return query.getResultList();
    }

    @Override
    public Depart findID(String departID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_findID, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, departID);
        return (Depart)query.getSingleResult();
    }

    @Override
    public boolean insert(Depart depart, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_insert, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.setParameter(1, depart.getDepartName());
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Depart depart, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_update, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.setParameter(1, depart.getDepartID());
		query.setParameter(2, depart.getDepartName());
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String departID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_delLogic, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, departID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String departID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_delPhysic, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, departID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String departID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Departs_restore, Depart.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, departID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

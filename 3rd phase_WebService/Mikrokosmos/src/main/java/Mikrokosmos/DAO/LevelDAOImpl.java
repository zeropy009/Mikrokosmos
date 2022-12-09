package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Level;

/**
 * @author zero
 */
@Repository
public class LevelDAOImpl implements LevelDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Levels_get1}")
    String sp_Levels_get1;

    @Value("${sp_Levels_get0}")
    String sp_Levels_get0;

    @Value("${sp_Levels_findID}")
    String sp_Levels_findID;

    @Value("${sp_Levels_insert}")
    String sp_Levels_insert;

    @Value("${sp_Levels_update}")
    String sp_Levels_update;

    @Value("${sp_Levels_delLogic}")
    String sp_Levels_delLogic;

    @Value("${sp_Levels_delPhysic}")
    String sp_Levels_delPhysic;

    @Value("${sp_Levels_restore}")
    String sp_Levels_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Level> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_get0, Level.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Level> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_get1, Level.class);
        return query.getResultList();
    }

    @Override
    public Level findID(Integer levelID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_findID, Level.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, levelID);
        return (Level)query.getSingleResult();
    }

    @Override
    public boolean insert(Level level, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_insert, Level.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.setParameter(1, level.getPoint());
        query.setParameter(2, level.getName());
        query.setParameter(3, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Level level, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_update, Level.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.setParameter(1, level.getLevelID());
        query.setParameter(2, level.getName());
        query.setParameter(3, level.getPoint());
        query.setParameter(4, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(Integer levelID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_delLogic, Level.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, levelID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer levelID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_delPhysic, Level.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, levelID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer levelID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Levels_restore, Level.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, levelID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

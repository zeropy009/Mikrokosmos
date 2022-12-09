package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.ShipInfor;

/**
 * @author zero
 */
@Repository
public class ShipInforDAOImpl implements ShipInforDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_ShipInfors_get1}")
    String sp_ShipInfors_get1;

    @Value("${sp_ShipInfors_get0}")
    String sp_ShipInfors_get0;

    @Value("${sp_ShipInfors_findID}")
    String sp_ShipInfors_findID;

    @Value("${sp_ShipInfors_insert}")
    String sp_ShipInfors_insert;

    @Value("${sp_ShipInfors_update}")
    String sp_ShipInfors_update;

    @Value("${sp_ShipInfors_delLogic}")
    String sp_ShipInfors_delLogic;

    @Value("${sp_ShipInfors_delPhysic}")
    String sp_ShipInfors_delPhysic;

    @Value("${sp_ShipInfors_restore}")
    String sp_ShipInfors_restore;

    @SuppressWarnings("unchecked")
    @Override
    public List<ShipInfor> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_get0, ShipInfor.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShipInfor> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_get1, ShipInfor.class);
        return query.getResultList();
    }

    @Override
    public ShipInfor findID(String invoiceID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_findID, ShipInfor.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, invoiceID);
        return (ShipInfor) query.getSingleResult();
    }

    @Override
    public boolean insert(ShipInfor shipInfor, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_insert, ShipInfor.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.setParameter(1, shipInfor.getInvoiceID());
        query.setParameter(2, shipInfor.getFullName());
        query.setParameter(3, shipInfor.getAddress());
        query.setParameter(4, shipInfor.getShipDate());
        query.setParameter(5, shipInfor.getPhone());
        query.setParameter(6, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(ShipInfor shipInfor, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_update, ShipInfor.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.setParameter(1, shipInfor.getShipInforID());
        query.setParameter(2, shipInfor.getInvoiceID());
        query.setParameter(3, shipInfor.getFullName());
        query.setParameter(4, shipInfor.getAddress());
        query.setParameter(5, shipInfor.getPhone());
        query.setParameter(6, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(Integer shipInforID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_delLogic, ShipInfor.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, shipInforID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(Integer shipInforID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_delPhysic, ShipInfor.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, shipInforID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(Integer shipInforID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_ShipInfors_restore, ShipInfor.class);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, shipInforID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

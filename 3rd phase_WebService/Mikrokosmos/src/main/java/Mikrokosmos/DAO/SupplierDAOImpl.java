package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Supplier;

/**
 * @author zero
 */
@Repository
public class SupplierDAOImpl implements SupplierDAO {

	@PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Suppliers_get1}")
    String sp_Suppliers_get1;

    @Value("${sp_Suppliers_get0}")
    String sp_Suppliers_get0;

    @Value("${sp_Suppliers_findID}")
    String sp_Suppliers_findID;

    @Value("${sp_Suppliers_insert}")
    String sp_Suppliers_insert;

    @Value("${sp_Suppliers_update}")
    String sp_Suppliers_update;

    @Value("${sp_Suppliers_delLogic}")
    String sp_Suppliers_delLogic;

    @Value("${sp_Suppliers_delPhysic}")
    String sp_Suppliers_delPhysic;

    @Value("${sp_Suppliers_restore}")
    String sp_Suppliers_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Supplier> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_get0, Supplier.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Supplier> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_get1, Supplier.class);
        return query.getResultList();
    }

    @Override
    public Supplier findID(String supplierID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_findID, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, supplierID);
        return (Supplier)query.getSingleResult();
    }

    @Override
    public boolean insert(Supplier supplier, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_insert, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.setParameter(1, supplier.getSupplierName());
		query.setParameter(2, supplier.getAddress());
		query.setParameter(3, supplier.getPhone());
		query.setParameter(4, supplier.getEmail());
        query.setParameter(5, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Supplier supplier, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_update, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
		query.setParameter(1, supplier.getSupplierID());
		query.setParameter(2, supplier.getSupplierName());
		query.setParameter(3, supplier.getAddress());
		query.setParameter(4, supplier.getPhone());
		query.setParameter(5, supplier.getEmail());
        query.setParameter(6, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String supplierID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_delLogic, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, supplierID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String supplierID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_delPhysic, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, supplierID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String supplierID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Suppliers_restore, Supplier.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, supplierID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

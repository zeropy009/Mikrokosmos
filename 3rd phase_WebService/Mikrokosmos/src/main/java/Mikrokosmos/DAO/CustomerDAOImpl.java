package Mikrokosmos.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Customer;
import Mikrokosmos.Model.CustomerDTO;

/**
 * @author zero
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Customers_get1}")
    String sp_Customers_get1;

    @Value("${sp_Customers_get0}")
    String sp_Customers_get0;

    @Value("${sp_Customers_findID}")
    String sp_Customers_findID;

    @Value("${sp_Customers_insert}")
    String sp_Customers_insert;

    @Value("${sp_Customers_update}")
    String sp_Customers_update;

    @Value("${sp_Customers_delLogic}")
    String sp_Customers_delLogic;

    @Value("${sp_Customers_delPhysic}")
    String sp_Customers_delPhysic;

    @Value("${sp_Customers_restore}")
    String sp_Customers_restore;

    @SuppressWarnings("unchecked")
	@Override
    public List<Customer> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_get0, Customer.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<CustomerDTO> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_get1, CustomerDTO.class);
        return query.getResultList();
    }

    @Override
    public Customer findID(String customerID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_findID, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, customerID);
        return (Customer)query.getSingleResult();
    }

    @Override
    public boolean insert(Customer customer, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_insert, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.setParameter(1, customer.getCustomerName());
//        query.setParameter(2, customer.getImage());
        query.setParameter(3, customer.getEmail());
        query.setParameter(4, customer.getAddress());
        query.setParameter(5, customer.getPhone());
        query.setParameter(6, customer.getPassword());
        query.setParameter(7, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Customer customer, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_update, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.setParameter(1, customer.getCustomerID());
        query.setParameter(2, customer.getCustomerName());
        query.setParameter(3, customer.getEmail());
        query.setParameter(4, customer.getCustomerPoint());
        query.setParameter(5, customer.getAddress());
        query.setParameter(6, customer.getPhone());
        query.setParameter(7, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String customerID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_delLogic, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, customerID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String customerID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_delPhysic, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, customerID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String customerID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Customers_restore, Customer.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, customerID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }
}

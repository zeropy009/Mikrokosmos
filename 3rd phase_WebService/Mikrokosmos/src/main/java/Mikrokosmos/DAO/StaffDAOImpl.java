package Mikrokosmos.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import Mikrokosmos.Model.Staff;
import Mikrokosmos.Model.StaffDTO;
import Mikrokosmos.Model.UserAccountDTO;

/**
 * @author zero
 */
@Repository
public class StaffDAOImpl implements StaffDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${sp_Staffs_get1}")
    String sp_Staffs_get1;

    @Value("${sp_Staffs_get0}")
    String sp_Staffs_get0;

    @Value("${sp_Staffs_findID}")
    String sp_Staffs_findID;

    @Value("${sp_Staffs_insert}")
    String sp_Staffs_insert;

    @Value("${sp_Staffs_update}")
    String sp_Staffs_update;

    @Value("${sp_Staffs_delLogic}")
    String sp_Staffs_delLogic;

    @Value("${sp_Staffs_delPhysic}")
    String sp_Staffs_delPhysic;

    @Value("${sp_Staffs_restore}")
    String sp_Staffs_restore;

    @Value("${sp_Staffs_login}")
    String sp_Staffs_login;

    @Value("${sp_Staffs_change_password}")
    String sp_Staffs_change_password;

    @SuppressWarnings("unchecked")
    @Override
    public List<Staff> get0() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_get0, Staff.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StaffDTO> get1() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_get1, StaffDTO.class);
        return query.getResultList();
    }

    @Override
    public Staff findID(String staffID) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_findID, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, staffID);
        return (Staff) query.getSingleResult();
    }

    @Override
    public boolean insert(Staff staff, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_insert, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(9, Float.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
        query.setParameter(1, staff.getStaffName());
        query.setParameter(2, staff.getUsername());
        query.setParameter(3, staff.getPassword());
        query.setParameter(4, staff.getRole());
        query.setParameter(5, staff.getEmail());
        query.setParameter(6, staff.getBirthDay());
        query.setParameter(7, staff.getAddress());
        query.setParameter(8, staff.getPhone());
        query.setParameter(9, staff.getSalary());
        query.setParameter(10, staff.getDepartID());
        query.setParameter(11, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean update(Staff staff, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_update, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(9, Float.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
        query.setParameter(1, staff.getStaffID());
        query.setParameter(2, staff.getStaffName());
        query.setParameter(3, staff.getUsername());
        query.setParameter(4, staff.getRole());
        query.setParameter(5, staff.getEmail());
        query.setParameter(6, staff.getBirthDay());
        query.setParameter(7, staff.getAddress());
        query.setParameter(8, staff.getPhone());
        query.setParameter(9, staff.getSalary());
        query.setParameter(10, staff.getDepartID());
        query.setParameter(11, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteLogic(String staffID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_delLogic, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, staffID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deletePhysical(String staffID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_delPhysic, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, staffID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean restore(String staffID, String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_restore, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, staffID);
        query.setParameter(2, userName);
        return query.executeUpdate() > 0;
    }

    @Override
    public UserAccountDTO getAccountByUsername(String username) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_login, UserAccountDTO.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1, username);
        return (UserAccountDTO) query.getSingleResult();
    }

    @Override
    public boolean changePassword(String staffID, String newPassword) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(sp_Staffs_change_password, Staff.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.setParameter(1, staffID);
        query.setParameter(2, newPassword);
        return query.executeUpdate() > 0;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<String> getListUsername() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_Staffs_getUsername");
        return query.getResultList();
    }
}

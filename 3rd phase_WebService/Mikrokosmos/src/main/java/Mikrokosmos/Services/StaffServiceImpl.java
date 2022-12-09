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

import Mikrokosmos.DAO.StaffDAO;
import Mikrokosmos.DAO.StaffDAOImpl;
import Mikrokosmos.Model.Staff;
import Mikrokosmos.Model.StaffDTO;
import Mikrokosmos.Model.UserAccountDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zero
 */
@Service
public class StaffServiceImpl implements StaffService {

    Logger logger = LoggerFactory.getLogger(StaffDAOImpl.class);

    @Autowired
    StaffDAO staffDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public List<Staff> get0() {
        return staffDAO.get0();
    }

    @Override
    public List<StaffDTO> get1() {
        return staffDAO.get1();
    }

    @Override
    public Staff findID(String staffID) {
        try {
            return staffDAO.findID(staffID);
        } catch (Exception e) {
            logger.error("Get Staff failed " + e);
            return null;
        }
    }

    @Override
    public boolean insert(Staff staff, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (staff.getStaffName().isEmpty()) {
                logger.warn("Staff name can't be empty");
                throw new Exception();
            }
            if (staff.getUsername().isEmpty()) {
                logger.warn("Username can't be empty");
                throw new Exception();
            }
            if (staff.getPassword().isEmpty()) {
                logger.warn("Password can't be empty");
                throw new Exception();
            }
            if (staff.getRole() == null) {
                logger.warn("Role can't be empty");
                throw new Exception();
            }
            if (staff.getEmail().isEmpty()) {
                logger.warn("Email can't be empty");
                throw new Exception();
            }
            if (staff.getBirthDay() == null) {
                logger.warn("Birth day can't be empty");
                throw new Exception();
            }
            if (staff.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (staff.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            if (staff.getSalary() <= 0) {
                logger.warn("Salary must be > 0");
                throw new Exception();
            }
            if (staff.getDepartID().isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            staff.setPassword(hashPassword(staff.getPassword()));
            boolean result = staffDAO.insert(staff, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Insert Staff failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Insert Staff failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean update(Staff staff, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (staff.getStaffID().isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            if (staff.getUsername().isEmpty()) {
                logger.warn("Username can't be empty");
                throw new Exception();
            }
            if (staff.getRole() == null) {
                logger.warn("Role can't be empty");
                throw new Exception();
            }
            if (staff.getEmail().isEmpty()) {
                logger.warn("Email can't be empty");
                throw new Exception();
            }
            if (staff.getBirthDay() == null) {
                logger.warn("Birth day can't be empty");
                throw new Exception();
            }
            if (staff.getAddress().isEmpty()) {
                logger.warn("Address can't be empty");
                throw new Exception();
            }
            if (staff.getPhone().isEmpty()) {
                logger.warn("Phone can't be empty");
                throw new Exception();
            }
            if (staff.getSalary() <= 0) {
                logger.warn("Salary must be > 0");
                throw new Exception();
            }
            if (staff.getDepartID().isEmpty()) {
                logger.warn("Depart ID can't be empty");
                throw new Exception();
            }
            boolean result = staffDAO.update(staff, userName);
            if (result) {
                //transactionManager.rollback(status);
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Update Staff failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.info("Update Staff failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deleteLogic(String staffID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (staffID.isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            boolean result = staffDAO.deleteLogic(staffID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Logic Staff failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Logic Staff failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean deletePhysical(String staffID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (staffID.isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            boolean result = staffDAO.deletePhysical(staffID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Delete Staff failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Delete Staff failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public boolean restore(String staffID, String userName) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (staffID.isEmpty()) {
                logger.warn("Staff ID can't be empty");
                throw new Exception();
            }
            boolean result = staffDAO.restore(staffID, userName);
            if (result) {
                transactionManager.commit(status);
                return true;
            } else {
                logger.info("Restore Staff failed ");
                transactionManager.rollback(status);
                return false;
            }
        } catch (Exception e) {
            logger.error("Restore Staff failed " + e);
            transactionManager.rollback(status);
            return false;
        }
    }

    @Override
    public UserAccountDTO getAccountByUsername() {
        return staffDAO.getAccountByUsername(getAuthenticatedUserName());
    }

    public String getAuthenticatedUserName() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "";
    }

    @Override
    public boolean changePassword(String staffID, String newPassword) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        boolean result = false;
        try {
            String hashPassword = hashPassword(newPassword);
            System.out.println("Test: " + hashPassword);
            result = staffDAO.changePassword(staffID, hashPassword);
            if (result) {
                transactionManager.commit(status);
            } else {
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        }
        return result;
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}

package Mikrokosmos.Services;

import java.util.List;

import Mikrokosmos.Model.Staff;
import Mikrokosmos.Model.StaffDTO;
import Mikrokosmos.Model.UserAccountDTO;

/**
 * @author zero
 */
public interface StaffService {

    public List<Staff> get0();

    public List<StaffDTO> get1();

    public Staff findID(String staffID);

    public boolean insert(Staff staff, String userName);

    public boolean update(Staff staff, String userName);

    public boolean deleteLogic(String staffID, String userName);

    public boolean deletePhysical(String staffID, String userName);

    public boolean restore(String staffID, String userName);

    UserAccountDTO getAccountByUsername();
    
    boolean changePassword(String staffID, String newPassword);
}

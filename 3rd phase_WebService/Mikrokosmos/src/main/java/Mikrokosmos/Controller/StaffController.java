package Mikrokosmos.Controller;

import Mikrokosmos.DAO.StaffDAO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Mikrokosmos.Model.Staff;
import Mikrokosmos.Model.StaffDTO;
import Mikrokosmos.Model.UserAccountDTO;
import Mikrokosmos.Services.StaffService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffDAO staffDAO;

    @CrossOrigin
    @GetMapping("/get1")
    public List<StaffDTO> get1() {
        return staffService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Staff> get0() {
        return staffService.get0();
    }

    @CrossOrigin
    @GetMapping("/get-detail-staff")
    public Staff findID(@RequestParam(value = "staffID") String staffID) {
        return staffService.findID(staffID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Staff staff, @RequestParam(value = "userName") String userName) {
        return staffService.insert(staff, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Staff staff, @RequestParam(value = "userName") String userName) {
        return staffService.update(staff, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "staffID") String staffID, @RequestParam(value = "userName") String userName) {
        return staffService.deleteLogic(staffID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "staffID") String staffID, @RequestParam(value = "userName") String userName) {
        return staffService.restore(staffID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "staffID") String staffID, @RequestParam(value = "userName") String userName) {
        return staffService.deletePhysical(staffID, userName);
    }

    @CrossOrigin
    @RequestMapping("/get-detail")
    public UserAccountDTO getAccountDTOLogin() {
        return staffService.getAccountByUsername();
    }

    @CrossOrigin
    @PostMapping("/change-password")
    public boolean changePassword(@RequestParam(value = "staffID") String staffID, @RequestParam(value = "newPass") String newPass) {
        return staffService.changePassword(staffID, newPass);
    }

    @CrossOrigin
    @GetMapping("/get-list-username")
    public List<String> getListUsername() {
        return staffDAO.getListUsername();
    }

}

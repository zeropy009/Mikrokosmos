package Mikrokosmos.Controller;

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

import Mikrokosmos.Model.Customer;
import Mikrokosmos.Model.CustomerDTO;
import Mikrokosmos.Services.CustomerService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<CustomerDTO> get1() {
        return customerService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Customer> get0() {
        return customerService.get0();
    }

    @CrossOrigin
    @GetMapping("/get-detail")
    public Customer findID(@RequestParam(value = "customerID") String customerID) {
        return customerService.findID(customerID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Customer customer, @RequestParam(value = "userName") String userName) {
        return customerService.insert(customer, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Customer customer, @RequestParam(value = "userName") String userName) {
        return customerService.update(customer, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "customerID") String customerID, @RequestParam(value = "userName") String userName) {
        return customerService.deleteLogic(customerID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "customerID") String customerID, @RequestParam(value = "userName") String userName) {
        return customerService.restore(customerID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "customerID") String customerID, @RequestParam(value = "userName") String userName) {
        return customerService.deletePhysical(customerID, userName);
    }
}

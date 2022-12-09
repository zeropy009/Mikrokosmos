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

import Mikrokosmos.Model.Supplier;
import Mikrokosmos.Services.SupplierService;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @book zero
 */
@RestController
@RequestMapping("/mik/supplier")
@CrossOrigin(origins="http://localhost:3000",maxAge = 3600)
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-004')")
public class SupplierController {
    
    @Autowired
    SupplierService supplierService;
    
    @GetMapping("/get1")
    public List<Supplier> get1(){
        return supplierService.get1();
    }
    
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Supplier> get0(){
        return supplierService.get0();
    }
    
    @PutMapping("findID/{id}")
    public Supplier findID(@PathVariable(name="id")String supplierId){
        return supplierService.findID(supplierId);
    }
    
    @PostMapping("add")
    public boolean add(@RequestBody Supplier supplier,@RequestParam(value="userName")String username){
        return supplierService.insert(supplier, username);
    }
    
    @PutMapping("update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Supplier supplier,@RequestParam(value="userName")String username){
        return supplierService.update(supplier, username);
    }
    
    @DeleteMapping("delPhysic/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean deletePhysical(@PathVariable(name="id")String supplierID,@RequestParam(value="userName")String username){
        return supplierService.deletePhysical(supplierID, username);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "supplierID") String supplierID, @RequestParam(value = "userName") String userName) {
        return supplierService.restore(supplierID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "supplierID") String supplierID, @RequestParam(value = "userName") String userName) {
        return supplierService.deletePhysical(supplierID, userName);
    }
}

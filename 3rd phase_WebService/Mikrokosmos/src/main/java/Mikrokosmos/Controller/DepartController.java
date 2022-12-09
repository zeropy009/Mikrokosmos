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

import Mikrokosmos.Model.Depart;
import Mikrokosmos.Services.DepartService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/depart")
@PreAuthorize("hasAnyAuthority('Depart-002','Depart-004')")
public class DepartController {

    @Autowired
    private DepartService departService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Depart> get1() {
        return departService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Depart> get0() {
        return departService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Depart findID(@RequestParam(value = "departID") String departID) {
        return departService.findID(departID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Depart depart, @RequestParam(value = "userName") String userName) {
        return departService.insert(depart, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Depart depart, @RequestParam(value = "userName") String userName) {
        return departService.update(depart, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "departID") String departID, @RequestParam(value = "userName") String userName) {
        return departService.deleteLogic(departID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "departID") String departID, @RequestParam(value = "userName") String userName) {
        return departService.restore(departID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "departID") String departID, @RequestParam(value = "userName") String userName) {
        return departService.deletePhysical(departID, userName);
    }
}

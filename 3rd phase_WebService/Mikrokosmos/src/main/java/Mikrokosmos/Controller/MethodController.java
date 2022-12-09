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

import Mikrokosmos.Model.Method;
import Mikrokosmos.Services.MethodService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/method")
@PreAuthorize("hasAnyAuthority('Depart-004')")
public class MethodController {

    @Autowired
    private MethodService methodService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Method> get1() {
        return methodService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    public List<Method> get0() {
        return methodService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Method findID(@RequestParam(value = "methodID") Integer methodID) {
        return methodService.findID(methodID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Method method, @RequestParam(value = "userName") String userName) {
        return methodService.insert(method, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody Method method, @RequestParam(value = "userName") String userName) {
        return methodService.update(method, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "methodID") Integer methodID, @RequestParam(value = "userName") String userName) {
        return methodService.deleteLogic(methodID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "methodID") Integer methodID, @RequestParam(value = "userName") String userName) {
        return methodService.restore(methodID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "methodID") Integer methodID, @RequestParam(value = "userName") String userName) {
        return methodService.deletePhysical(methodID, userName);
    }
}

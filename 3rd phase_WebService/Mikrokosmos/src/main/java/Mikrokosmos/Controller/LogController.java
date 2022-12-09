package Mikrokosmos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Mikrokosmos.Model.Log;
import Mikrokosmos.Services.LogService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/log")
public class LogController {

    @Autowired
    private LogService logService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Log> get1() {
        return logService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Log> get0() {
        return logService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Log findID(@RequestParam(value = "logID") Integer logID) {
        return logService.findID(logID);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "logID") Integer logID, @RequestParam(value = "userName") String userName) {
        return logService.deleteLogic(logID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "logID") Integer logID, @RequestParam(value = "userName") String userName) {
        return logService.restore(logID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "logID") Integer logID, @RequestParam(value = "userName") String userName) {
        return logService.deletePhysical(logID, userName);
    }
}

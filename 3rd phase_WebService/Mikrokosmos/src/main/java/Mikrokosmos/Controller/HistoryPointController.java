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

import Mikrokosmos.Model.HistoryPoint;
import Mikrokosmos.Services.HistoryPointService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/historyPoint")
public class HistoryPointController {

    @Autowired
    private HistoryPointService historyPointService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<HistoryPoint> get1(@RequestParam(value ="customerID")String customerID) {
        return historyPointService.get1(customerID);
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<HistoryPoint> get0() {
        return historyPointService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public HistoryPoint findID(@RequestParam(value = "historyPointID") Integer historyPointID) {
        return historyPointService.findID(historyPointID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody HistoryPoint historyPoint, @RequestParam(value = "userName") String userName) {
        return historyPointService.insert(historyPoint, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody HistoryPoint historyPoint, @RequestParam(value = "userName") String userName) {
        return historyPointService.update(historyPoint, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "historyPointID") Integer historyPointID, @RequestParam(value = "userName") String userName) {
        return historyPointService.deleteLogic(historyPointID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "historyPointID") Integer historyPointID, @RequestParam(value = "userName") String userName) {
        return historyPointService.restore(historyPointID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "historyPointID") Integer historyPointID, @RequestParam(value = "userName") String userName) {
        return historyPointService.deletePhysical(historyPointID, userName);
    }
}

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

import Mikrokosmos.Model.ShipInfor;
import Mikrokosmos.Services.ShipInforService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/shipInfor")
public class ShipInforController {

    @Autowired
    private ShipInforService shipInforService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<ShipInfor> get1() {
        return shipInforService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-04')")
    public List<ShipInfor> get0() {
        return shipInforService.get0();
    }

    @CrossOrigin
    @GetMapping("/findID")
    public ShipInfor findID(@RequestParam(value = "invoiceID") String invoiceID) {
        return shipInforService.findID(invoiceID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody ShipInfor shipInfor, @RequestParam(value = "userName") String userName) {
        return shipInforService.insert(shipInfor, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody ShipInfor shipInfor, @RequestParam(value = "userName") String userName) {
        return shipInforService.update(shipInfor, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "shipInforID") Integer shipInforID, @RequestParam(value = "userName") String userName) {
        return shipInforService.deleteLogic(shipInforID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-04')")
    public boolean restore(@RequestParam(value = "shipInforID") Integer shipInforID, @RequestParam(value = "userName") String userName) {
        return shipInforService.restore(shipInforID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-04')")
    public boolean delPhysic(@RequestParam(value = "shipInforID") Integer shipInforID, @RequestParam(value = "userName") String userName) {
        return shipInforService.deletePhysical(shipInforID, userName);
    }
}

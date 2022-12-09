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

import Mikrokosmos.Model.HistoryPrice;
import Mikrokosmos.Services.HistoryPriceService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/historyPrice")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-003','Depart-004')")
public class HistoryPriceController {

    @Autowired
    private HistoryPriceService historyPriceService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<HistoryPrice> get1() {
        return historyPriceService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<HistoryPrice> get0() {
        return historyPriceService.get0();
    }

    @CrossOrigin
    @GetMapping("/findID")
    public List<HistoryPrice> findID(@RequestParam(value = "bookID") String bookID) {
        return historyPriceService.findID(bookID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody HistoryPrice historyPrice, @RequestParam(value = "userName") String userName) {
        return historyPriceService.insert(historyPrice, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody HistoryPrice historyPrice, @RequestParam(value = "userName") String userName) {
        return historyPriceService.update(historyPrice, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "historyPriceID") Integer historyPriceID, @RequestParam(value = "userName") String userName) {
        return historyPriceService.deleteLogic(historyPriceID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "historyPriceID") Integer historyPriceID, @RequestParam(value = "userName") String userName) {
        return historyPriceService.restore(historyPriceID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "historyPriceID") Integer historyPriceID, @RequestParam(value = "userName") String userName) {
        return historyPriceService.deletePhysical(historyPriceID, userName);
    }
}

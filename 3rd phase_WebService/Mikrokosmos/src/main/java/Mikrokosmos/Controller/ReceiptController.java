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

import Mikrokosmos.Model.Receipt;
import Mikrokosmos.Model.ReceiptDetailDTO;
import Mikrokosmos.Services.ReceiptService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/receipt")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-004')")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Receipt> get1() {
        return receiptService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Receipt> get0() {
        return receiptService.get0();
    }

    @CrossOrigin
    @GetMapping("/get-receipt")
    public List<ReceiptDetailDTO> findID(@RequestParam(value = "receiptID") String receiptID) {
        return receiptService.findID(receiptID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Receipt receipt, @RequestParam(value = "userName") String userName) {
        return receiptService.insert(receipt, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Receipt receipt, @RequestParam(value = "userName") String userName) {
        return receiptService.update(receipt, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "userName") String userName) {
        return receiptService.deleteLogic(receiptID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "userName") String userName) {
        return receiptService.restore(receiptID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "userName") String userName) {
        return receiptService.deletePhysical(receiptID, userName);
    }
}

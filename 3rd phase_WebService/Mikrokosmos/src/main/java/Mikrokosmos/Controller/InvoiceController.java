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

import Mikrokosmos.Model.Invoice;
import Mikrokosmos.Model.InvoiceTableDTO;
import Mikrokosmos.Services.InvoiceService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<InvoiceTableDTO> get1() {
        return invoiceService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Invoice> get0() {
        return invoiceService.get0();
    }

    @CrossOrigin
    @GetMapping("/get-invoice")
    public Invoice findID(@RequestParam(value = "invoiceID") String invoiceID) {
        return invoiceService.findID(invoiceID);
    }
    
    @CrossOrigin
    @GetMapping("/get-list-book-invoice")
    public Invoice getList(@RequestParam(value = "invoiceID") String invoiceID) {
        return invoiceService.findID(invoiceID);
    }
    
    
    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Invoice invoice, @RequestParam(value = "userName") String userName) {
        return invoiceService.insert(invoice, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody Invoice invoice, @RequestParam(value = "userName") String userName) {
        return invoiceService.update(invoice, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "userName") String userName) {
        return invoiceService.deleteLogic(invoiceID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "userName") String userName) {
        return invoiceService.restore(invoiceID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "userName") String userName) {
        return invoiceService.deletePhysical(invoiceID, userName);
    }
}

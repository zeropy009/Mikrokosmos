package Mikrokosmos.Controller;

import Mikrokosmos.Model.BookInvoiceDTO;
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

import Mikrokosmos.Model.InvoiceDetail;
import Mikrokosmos.Services.InvoiceDetailService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/invoiceDetail")
public class InvoiceDetailController {

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<InvoiceDetail> get1() {
        return invoiceDetailService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<InvoiceDetail> get0() {
        return invoiceDetailService.get0();
    }

    @CrossOrigin
    @GetMapping("/findInvoiceID")
    public List<BookInvoiceDTO> findInvoiceID(@RequestParam(value = "invoiceID") String invoiceID) {
        return invoiceDetailService.findInvoiceID(invoiceID);
    }
    
    @CrossOrigin
    @PutMapping("/findBookID")
    public InvoiceDetail findBookID(@RequestParam(value = "bookID") String bookID) {
        return invoiceDetailService.findBookID(bookID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody InvoiceDetail invoiceDetail,@RequestParam(value="invoiceID")String invoiceID, @RequestParam(value = "userName") String userName) {
        return invoiceDetailService.insert(invoiceDetail, invoiceID,userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody InvoiceDetail invoiceDetail,@RequestParam(value="invoiceID")String invoiceID, @RequestParam(value = "userName") String userName) {
        return invoiceDetailService.update(invoiceDetail, invoiceID,userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return invoiceDetailService.deleteLogic(invoiceID, bookID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return invoiceDetailService.restore(invoiceID, bookID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "invoiceID") String invoiceID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return invoiceDetailService.deletePhysical(invoiceID, bookID, userName);
    }
}

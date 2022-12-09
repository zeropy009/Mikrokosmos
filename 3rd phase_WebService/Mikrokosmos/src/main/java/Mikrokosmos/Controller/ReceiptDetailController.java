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

import Mikrokosmos.Model.ReceiptDetail;
import Mikrokosmos.Services.ReceiptDetailService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/receiptdetail")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-004')")
public class ReceiptDetailController {

    @Autowired
    private ReceiptDetailService receiptDetailService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<ReceiptDetail> get1() {
        return receiptDetailService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    public List<ReceiptDetail> get0() {
        return receiptDetailService.get0();
    }

    @CrossOrigin
    @GetMapping("/findReceiptID")
    public List<ReceiptDetail> findReceiptID(@RequestParam(value = "receiptID") String receiptID) {
        return receiptDetailService.findReceiptID(receiptID);
    }
    
    @CrossOrigin
    @PutMapping("/findBookID")
    public List<ReceiptDetail> findBookID(@RequestParam(value = "bookID") String bookID) {
        return receiptDetailService.findBookID(bookID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody ReceiptDetail receiptDetail,@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "userName") String userName) {
        System.out.println("Ok Add");
        return receiptDetailService.insert(receiptDetail, receiptID,userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody ReceiptDetail receiptDetail, @RequestParam(value = "receiptID") String receiptID,@RequestParam(value = "userName") String userName) {
        System.out.println("Ok Update");
        return receiptDetailService.update(receiptDetail,receiptID, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    public boolean delLogic(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return receiptDetailService.deleteLogic(receiptID, bookID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    public boolean restore(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return receiptDetailService.restore(receiptID, bookID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    public boolean delPhysic(@RequestParam(value = "receiptID") String receiptID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return receiptDetailService.deletePhysical(receiptID, bookID, userName);
    }
}

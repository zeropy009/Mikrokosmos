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

import Mikrokosmos.Model.Book;
import Mikrokosmos.Model.BookTableDTO;
import Mikrokosmos.Services.BookService;

/**
 * @book zero
 */
@RestController
@RequestMapping("/mik/book")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-003','Depart-004')")
public class BookController {

    @Autowired
    private BookService bookService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<BookTableDTO> get1() {
        return bookService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Book> get0() {
        return bookService.get0();
    }

    @CrossOrigin
    @GetMapping("/get-book")
    public Book findID(@RequestParam(value = "bookID") String bookID) {
        return bookService.findID(bookID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Book book, @RequestParam(value = "userName") String userName) {
        return bookService.insert(book, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Book book, @RequestParam(value = "userName") String userName) {
        return bookService.update(book, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return bookService.deleteLogic(bookID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return bookService.restore(bookID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "bookID") String bookID, @RequestParam(value = "userName") String userName) {
        return bookService.deletePhysical(bookID, userName);
    }
}

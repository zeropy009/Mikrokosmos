package Mikrokosmos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Mikrokosmos.Model.Author;
import Mikrokosmos.Services.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/author")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-003','Depart-004')")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    
    @CrossOrigin
    @GetMapping("/get1")
    public List<Author> get1() {
        return authorService.get1();
    }
    
    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Author> get0() {
        return authorService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Author findID(@RequestParam(value = "authorID") String authorID) {
        return authorService.findID(authorID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Author author, @RequestParam(value = "userName") String userName) {
        return authorService.insert(author, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Author author, @RequestParam(value = "userName") String userName) {
        return authorService.update(author, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "authorID") String authorID, @RequestParam(value = "userName") String userName) {
        return authorService.deleteLogic(authorID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "authorID") String authorID, @RequestParam(value = "userName") String userName) {
        return authorService.restore(authorID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "authorID") String authorID, @RequestParam(value = "userName") String userName) {
        return authorService.deletePhysical(authorID, userName);
    }
}

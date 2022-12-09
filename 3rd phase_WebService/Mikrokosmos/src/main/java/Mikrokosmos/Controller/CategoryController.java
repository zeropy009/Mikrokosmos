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

import Mikrokosmos.Model.Category;
import Mikrokosmos.Services.CategoryService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/category")
@PreAuthorize("hasAnyAuthority('Depart-001','Depart-003','Depart-004')")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Category> get1() {
        return categoryService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public List<Category> get0() {
        return categoryService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Category findID(@RequestParam(value = "categoryID") String categoryID) {
        return categoryService.findID(categoryID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Category category, @RequestParam(value = "userName") String userName) {
        return categoryService.insert(category, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','Depart-004')")
    public boolean update(@RequestBody Category category, @RequestParam(value = "userName") String userName) {
        return categoryService.update(category, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "categoryID") String categoryID, @RequestParam(value = "userName") String userName) {
        return categoryService.deleteLogic(categoryID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "categoryID") String categoryID, @RequestParam(value = "userName") String userName) {
        return categoryService.restore(categoryID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "categoryID") String categoryID, @RequestParam(value = "userName") String userName) {
        return categoryService.deletePhysical(categoryID, userName);
    }
}

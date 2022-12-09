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

import Mikrokosmos.Model.Level;
import Mikrokosmos.Services.LevelService;

/**
 * @author zero
 */
@RestController
@RequestMapping("/mik/level")
@PreAuthorize("hasAnyAuthority('Depart-004')")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @CrossOrigin
    @GetMapping("/get1")
    public List<Level> get1() {
        return levelService.get1();
    }

    @CrossOrigin
    @GetMapping("/get0")
    public List<Level> get0() {
        return levelService.get0();
    }

    @CrossOrigin
    @PutMapping("/findID")
    public Level findID(@RequestParam(value = "levelID") Integer levelID) {
        return levelService.findID(levelID);
    }

    @CrossOrigin
    @PostMapping("/add")
    public boolean add(@RequestBody Level level, @RequestParam(value = "userName") String userName) {
        return levelService.insert(level, userName);
    }

    @CrossOrigin
    @PutMapping("/update")
    public boolean update(@RequestBody Level level, @RequestParam(value = "userName") String userName) {
        return levelService.update(level, userName);
    }

    @CrossOrigin
    @PutMapping("/delLogic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delLogic(@RequestParam(value = "levelID") Integer levelID, @RequestParam(value = "userName") String userName) {
        return levelService.deleteLogic(levelID, userName);
    }

    @CrossOrigin
    @PutMapping("/restore")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean restore(@RequestParam(value = "levelID") Integer levelID, @RequestParam(value = "userName") String userName) {
        return levelService.restore(levelID, userName);
    }

    @CrossOrigin
    @DeleteMapping("/delPhysic")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public boolean delPhysic(@RequestParam(value = "levelID") Integer levelID, @RequestParam(value = "userName") String userName) {
        return levelService.deletePhysical(levelID, userName);
    }
}

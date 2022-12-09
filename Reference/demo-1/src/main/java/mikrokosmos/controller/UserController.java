package mikrokosmos.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javassist.expr.NewArray;
import mikrokosmos.model.User;
import mikrokosmos.service.UserService;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;

	@ModelAttribute("users")
	public List getUsers(){
		return userService.findAll();
	}
	
	@GetMapping("/getAll")
	public String getAll(Model model,HttpSession session) {
		//model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		session.setAttribute("roles", "Admin");
		session.setAttribute("role", true);
		return "userList";
	}
	
	@RequestMapping(value="/addUser",method = RequestMethod.GET)
	public String showForm(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}
	
	@RequestMapping(value="/addUser",method = RequestMethod.POST)
	public String insert(Model model,@ModelAttribute("user")User user) {
		if(user.getId().isEmpty()){
			model.addAttribute("errorMessage", "Lỗi rồi");
			return "addUser";
		}else {
			userService.save(user);
		}
		return "redirect:/getAll";
	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser(@ModelAttribute("user")User user) {
		userService.delete(user);
		return "redirect:/getAll";
	}
	
}

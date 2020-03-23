package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.RoleService;
import sistem.operasional.sioperasional.service.UserService;

@Controller
@RequestMapping("/account")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    private String addUser(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        return "add-user";

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserModel user,
                                 @RequestParam(value = "password") String password) {
        
        if (!userService.verifUser(user)){
            return "add-user-gagal";
        
        
    }
    System.out.println(password);
    userService.addUser(user);
    return "home";
}




    
}
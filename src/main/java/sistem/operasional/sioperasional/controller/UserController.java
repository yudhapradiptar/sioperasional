package sistem.operasional.sioperasional.controller;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/edit/{username}")
    private String editUserForm(@PathVariable(value = "username") String  username, Model model) {
        UserModel existingUser = userService.getUserByUsername(username);
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("user", existingUser);
        return "form-edit-user";
    }

    @RequestMapping(value = "/edit/{username}" , method = RequestMethod.POST)
    private String editUserFormSubmit(@PathVariable String username, @ModelAttribute UserModel user, Model model) {
        UserModel newUserData = userService.changeUser(user);
        model.addAttribute("userbaru", newUserData);
        return "success-change-user";
    }

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    private String viewAllUser(Model model) {
        model.addAttribute("listUser", userService.getAllUser());
        return "view-all-user";

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserModel user,
                                 @RequestParam(value = "password") String password, Model model) {
        
                                    System.out.println("anjay");
        if (!userService.verifUser(user)){
            return "add-user-gagal";
        
        
    }
    System.out.println(user.getUsername() + user.getPassword() + user.getKode() + user.getNama() + user.getStatus());
    userService.addUser(user);
    model.addAttribute("userbaru", user);

    return "success-add-user";
}





    
}
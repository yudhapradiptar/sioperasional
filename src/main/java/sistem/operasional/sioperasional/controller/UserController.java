package sistem.operasional.sioperasional.controller;

import javax.persistence.Id;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.RoleService;
import sistem.operasional.sioperasional.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/account")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
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
        if(user.getNama().isBlank()) {
            String message = "Nama tidak boleh Kosong!";
            model.addAttribute("message", message);
            return "failed-edit-user";
        }
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
    private String addUserSubmit(@Valid  @ModelAttribute UserModel user, @ModelAttribute("passwordConfirm") String pass,
                                 @RequestParam(value = "password") String password, Model model) {


        if (!userService.verifUser(user)) {
            String message = "Sudah ada username dengan username: " + user.getUsername() + ". Mohon gunakan username lain!";
            model.addAttribute("message", message);
            return "failed-add-user";
        }
        user.getUsername()
        if (user.getUsername().isBlank() || user.getNama().isBlank()) {
            String message = "Username atau Nama tidak boleh Kosong!";
            model.addAttribute("message", message);
            return "failed-add-user";
        }

        if (!userService.verifPass(user.getPassword())) {
            String message = "Password tidak memenuhi syarat!";
            model.addAttribute("message", message);
            return "failed-add-user";
        }

        if (!user.getPassword().equals(pass)) {
            String message = "password tidak sama dengan konfirmasi!";
            model.addAttribute("message", message);
            return "failed-add-user";
        }

        userService.addUser(user);
        model.addAttribute("userbaru", user);

        return "success-add-user";
    }
}
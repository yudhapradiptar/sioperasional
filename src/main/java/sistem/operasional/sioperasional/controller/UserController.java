package sistem.operasional.sioperasional.controller;

import javax.persistence.Id;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = "";
        for(GrantedAuthority each : auth.getAuthorities()){
            role = each.getAuthority();
        }
        if(role.substring(0,4).equals("ROLE")){
            model.addAttribute("role", role.substring(5));
        } else {
            model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        }

        return "add-user";

    }

    @RequestMapping(value = "/edit/{username}")
    private String editUserForm(@PathVariable(value = "username") String  username, Model model) {
        UserModel existingUser = userService.getUserByUsername(username);
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("user", existingUser);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        if(existingUser==userService.getUserCurrentLoggedIn()){
            model.addAttribute("yourself", true);
        } else {
            model.addAttribute("yourself", false);
        }
        return "form-edit-user";
    }

    @RequestMapping(value = "/edit/{username}" , method = RequestMethod.POST)
    private String editUserFormSubmit(@PathVariable String username, @ModelAttribute UserModel user, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        String statusOldUser = userService.getUserByUsername(username).getStatus();
        if(user.getNama().isBlank()) {
            String message = "Nama tidak boleh Kosong!";
            model.addAttribute("message", message);
            return "failed-edit-user";
        }
        UserModel newUserData = userService.changeUser(user);
//        model.addAttribute("userbaru", newUserData);
        if(statusOldUser.equals("aktif") && newUserData.getStatus().equals("tidak aktif")){
            UserModel newNewUserData = userService.changeUserPassword(newUserData, "nonaktif123");
            model.addAttribute("userbaru", newNewUserData);
            if(newUserData.getUsername().equals(userService.getUserCurrentLoggedIn().getUsername())){
                return "redirect:/logout";
            }
            return "success-change-user";
        } else if(statusOldUser.equals("tidak aktif") && newUserData.getStatus().equals("aktif")){
            if(newUserData.getUsername().equals(userService.getUserCurrentLoggedIn().getUsername())){
                UserModel newNewUserData = userService.changeUserPassword(newUserData, "newpassword123");
                return "redirect:/logout";
            }
            UserModel newNewUserData = userService.changeUserPassword(newUserData, "newpassword123");
            return "redirect:/account/edit/" + username + "/reset-password/form";
        } else{
            model.addAttribute("userbaru", newUserData);
            return "success-change-user";
        }
    }

    @RequestMapping(value = "/edit/{username}/reset-password/form", method = {RequestMethod.GET })
    private String formResetPassword(@PathVariable(value = "username") String  username, Model model){
        UserModel existingUser = userService.getUserByUsername(username);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        model.addAttribute("user", existingUser);

        return "form-reset-password";
    }

    @RequestMapping(value = "/edit/{username}/reset-password", method = RequestMethod.POST)
    private String resetPasswordSubmit(@PathVariable String username, @ModelAttribute UserModel user, Model model){
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        model.addAttribute("userbaru", userService.getUserByUsername(username));

        UserModel newUserPassword = userService.changeUserPasswordReset(user);

        return "success-change-user";
    }

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    private String viewAllUser(Model model) {
        model.addAttribute("listUser", userService.getAllUser());
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = "";
        for(GrantedAuthority each : auth.getAuthorities()){
            role = each.getAuthority();
        }
        System.out.println(role);
        if(role.substring(0,4).equals("ROLE")){
            model.addAttribute("role", role.substring(5));
        } else {
            model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        }

        return "success-add-user";
    }
}
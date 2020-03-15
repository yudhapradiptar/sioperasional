package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.service.RoleService;

@Controller
public class PageController {

    @RequestMapping("/")
    public String home (Model model) {
        return "home";
    }

    @RequestMapping("/login")
    public String login (Model model){
        return "login";
    }


}

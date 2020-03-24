package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;
import sistem.operasional.sioperasional.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @RequestMapping("/")
    public String home (Model model, Authentication auth) {
        List<CustomerFeedbackModel> listOfAllFeedback = customerFeedbackService.getAllCustomerFeedback();
        model.addAttribute("allCustomerFeedback", listOfAllFeedback);
        List<Integer> listOfAllScore = new ArrayList<>();
        List<String> listOfAllCoach = new ArrayList<>();
        for (int i = 0; i < listOfAllFeedback.size(); i++) {
            listOfAllScore.add(listOfAllFeedback.get(i).getScore());
            listOfAllCoach.add(listOfAllFeedback.get(i).getPelatih());
        }
        model.addAttribute("listOfAllScore", listOfAllScore);
        model.addAttribute("listOfAllCoach", listOfAllCoach);
        return "index";
    }

    @RequestMapping("/login")
    public String login (Model model){
        return "login";
    }


}

package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;
import sistem.operasional.sioperasional.service.RoleService;
import sistem.operasional.sioperasional.service.TrainingService;
import sistem.operasional.sioperasional.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home (Model model, Authentication auth) {
        List<CustomerFeedbackModel> listOfAllFeedback = customerFeedbackService.getAllCustomerFeedback();
        model.addAttribute("allCustomerFeedback", listOfAllFeedback);
        List<UserModel> listAllUser = userService.getAllUser();
        List<String> listAllTrainer = new ArrayList<>();
        List<Integer> listAllNilaiKerapihan = new ArrayList<>();
        for(UserModel trainer: listAllUser){
            if(trainer.getListTrainingTrained()!=null){
                int rataNilaiKerapihan = 0;
                int countRata = 0;
                listAllTrainer.add(trainer.getNama());
                for(TrainingModel training : trainer.getListTrainingTrained()){
                    if(training.getListCustomerFeedback()!=null){
                        for(CustomerFeedbackModel customerFeedback : training.getListCustomerFeedback()){
                            rataNilaiKerapihan+=customerFeedback.getNilaiKerapihan();
                            countRata++;
                        }
                    }
                }
                if(countRata>0){
                    listAllNilaiKerapihan.add(rataNilaiKerapihan/countRata);
                }
            }
        }
        model.addAttribute("listAllTrainer", listAllTrainer);
        model.addAttribute("listAllNilaiKerapihan", listAllNilaiKerapihan);
//        List<Integer> listOfAllScore = new ArrayList<>();
//        List<String> listOfAllCoach = new ArrayList<>();
//        for (int i = 0; i < listOfAllFeedback.size(); i++) {
//            listOfAllScore.add(listOfAllFeedback.get(i).getScore());
//            listOfAllCoach.add(listOfAllFeedback.get(i).getPelatih());
//        }
//        model.addAttribute("listOfAllScore", listOfAllScore);
//        model.addAttribute("listOfAllCoach", listOfAllCoach);
        return "dashboard";
    }

    @RequestMapping("/login")
    public String login (Model model){
        return "login";
    }


}

package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;
import sistem.operasional.sioperasional.service.TrainingService;

import java.util.List;

@Controller
public class CustomerFeedbackController {
    @Qualifier("customerFeedbackServiceImpl")
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "/customer-feedback/{idTraining}", method = RequestMethod.GET)
    public String customerFeedbackFormPage(@PathVariable Long idTraining, Model model) {
        List<TrainingModel> listOfTraining = trainingService.getAllTraining();
        for(int i=0; i<listOfTraining.size();i++){
            if(listOfTraining.get(i).getIdTraining()==idTraining && listOfTraining.get(i).getStatusTraining().equals("Selesai")){
                CustomerFeedbackModel newCustomerFeedback = new CustomerFeedbackModel();
                newCustomerFeedback.setTraining(listOfTraining.get(i));
                model.addAttribute("customerFeedback", newCustomerFeedback);
                return "form-customer-feedback";
            }
        }

        return "error-customer-feedback";
    }

    @RequestMapping(value = "/customer-feedback", method = RequestMethod.POST)
    public String customerFeedbackSubmit(@ModelAttribute CustomerFeedbackModel customerFeedback, Long idTraining, Model model) {
        try {
            customerFeedbackService.addCustomerFeedback(customerFeedback);
            model.addAttribute(customerFeedback);
            return "submit-customer-feedback";
        } catch (NullPointerException e) {
            return "form-customer-feedback";
        }
    }
}

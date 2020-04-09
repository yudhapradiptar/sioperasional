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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class CustomerFeedbackController {
    @Qualifier("customerFeedbackServiceImpl")
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    TrainingService trainingService;

    @RequestMapping(value = "/customer-feedback/{idTraining}", method = RequestMethod.GET)
    public String customerFeedbackFormPage(@PathVariable String idTraining, Model model) {
        List<TrainingModel> listOfTraining = trainingService.getAllTraining();
        for(TrainingModel training: listOfTraining){
            if(training.getIdTraining().equals(idTraining) && training.getStatusTraining().equals("Selesai")){
                CustomerFeedbackModel newCustomerFeedback = new CustomerFeedbackModel();
                Date tanggalTraining = trainingService.getTrainingByIdTraining(idTraining).getTanggalTraining();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tanggalTraining);
                int tanggalToInt = calendar.get(Calendar.DATE);
                int bulanTraining = calendar.get(Calendar.MONTH)+1;
                String namaBulanTraining = "";
                switch (bulanTraining) {
                    case 1:
                        namaBulanTraining+="Januari";
                        break;
                    case 2:
                        namaBulanTraining+="Feburari";
                        break;
                    case 3:
                        namaBulanTraining+="Maret";
                        break;
                    case 4:
                        namaBulanTraining+="April";
                        break;
                    case 5:
                        namaBulanTraining+="Mei";
                        break;
                    case 6:
                        namaBulanTraining+="Juni";
                        break;
                    case 7:
                        namaBulanTraining+="Juli";
                        break;
                    case 8:
                        namaBulanTraining+="Agustus";
                        break;
                    case 9:
                        namaBulanTraining+="September";
                        break;
                    case 10:
                        namaBulanTraining+="Oktober";
                        break;
                    case 11:
                        namaBulanTraining+="November";
                        break;
                    case 12:
                        namaBulanTraining+="Desember";
                        break;
                }
                int tahunTraining = calendar.get(Calendar.YEAR);
                model.addAttribute("customerFeedback", newCustomerFeedback);
                model.addAttribute("training", training);
                model.addAttribute("tanggalTraining", tanggalToInt);
                model.addAttribute("bulanTraining", namaBulanTraining);
                model.addAttribute("tahunTraining", tahunTraining);
                return "form-customer-feedback";
            }
        }

        return "error-customer-feedback";
    }

    @RequestMapping(value = "/customer-feedback/{idTraining}", method = RequestMethod.POST)
    public String customerFeedbackSubmit(@PathVariable String idTraining, @ModelAttribute CustomerFeedbackModel customerFeedback, Model model) {
        try {
            customerFeedback.setTraining(trainingService.getTrainingByIdTraining(idTraining));
            customerFeedbackService.addCustomerFeedback(customerFeedback);
            model.addAttribute(customerFeedback);
            model.addAttribute("idTraining", idTraining);;
            return "submit-customer-feedback";
        } catch (NullPointerException e) {
            return "form-customer-feedback";
        }
    }
}

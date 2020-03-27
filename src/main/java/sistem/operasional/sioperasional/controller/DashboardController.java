package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;
import sistem.operasional.sioperasional.service.TrainingService;
import sistem.operasional.sioperasional.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @RequestMapping(value="/customer-feedback")
    public String dashboardFeedback(Model model){
        List<CustomerFeedbackModel> listOfAllFeedback = customerFeedbackService.getAllCustomerFeedback();
        model.addAttribute("allCustomerFeedback", listOfAllFeedback);
        List<UserModel> listAllUser = userService.getAllUser();
        List<String> listTrainer = new ArrayList<>();
        List<Integer> listNilai = new ArrayList<>();
        for (UserModel trainer : listAllUser) {
            if (trainer.getListTrainingTrained() != null) {
                int rataNilai = 0;
                int countRata = 0;
                listTrainer.add(trainer.getNama());
                for (TrainingModel training : trainer.getListTrainingTrained()) {
                    if (training.getListCustomerFeedback() != null) {
                        for (CustomerFeedbackModel customerFeedback : training.getListCustomerFeedback()) {
                            rataNilai += ((customerFeedback.getNilaiKerapihan()+customerFeedback.getNilaiTepatWaktu()+customerFeedback.getNilaiSimpatik()
                            +customerFeedback.getNilaiKesiapan()+customerFeedback.getNilaiPenanggapan()+customerFeedback.getNilaiKesopanan()
                            +customerFeedback.getNilaiMengetahuiKebutuhan()+customerFeedback.getNilaiPerhatian())/8);
                            countRata++;
                        }
                    }
                }
                if (countRata > 0) {
                    listNilai.add(rataNilai / countRata);
                }
            }
        }

//        for(int i=0;i<5;i++){
//            listTrainer.add("trainer " + i);
//            listNilai.add(i);
//        }


        model.addAttribute("listTrainer", listTrainer);
        model.addAttribute("listNilai", listNilai);

        return "dashboard-feedback";
    }

    @RequestMapping(value="/training")
    public String dashboardTraining(Model model){
        List<Integer> listJumlahTraining = new ArrayList<>();
        List<Integer> listBulan = new ArrayList<>();
        List<Integer> listTahun = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for(int i=13;i>1;i--){
            listBulan.add(now.minusMonths(i).getMonthValue());
            listTahun.add(now.minusMonths(i).getYear());
//            listJumlahTraining.add(i);
            int countTrainingBulanIni = 0;
//            Date today = new Date();
//            System.out.println(today.getMonth());
            for(TrainingModel training : trainingService.getAllTraining()){
                if(training.getTanggalTraining().getMonth()+1==now.minusMonths(i).getMonthValue()){
                    countTrainingBulanIni++;
                }
            }
            listJumlahTraining.add(countTrainingBulanIni);
        }

        model.addAttribute("listBulan", listBulan);
        model.addAttribute("listTahun", listTahun);
        model.addAttribute("listJumlahTraining", listJumlahTraining);

        return "dashboard-training";
    }

    @RequestMapping(value="/trainer")
    public String dashboardTrainer(Model model){
        List<UserModel> listAllUser = userService.getAllUser();
        List<String> listTrainer = new ArrayList<>();
        List<Integer> listJumlahTraining = new ArrayList<>();
        for(UserModel trainer : listAllUser){
            if(trainer.getRole().getNamaRole().equals("Operation Staff")){
                listTrainer.add(trainer.getNama());
                List<TrainingModel> listTrainingSelesai = new ArrayList<>();
                if(trainingService.getListTrainingByTrainer(trainer)==null){
                    listJumlahTraining.add(0);
                } else{
                    for(TrainingModel training : trainingService.getListTrainingByTrainer(trainer)){
                        if(training.getStatusTraining().equals("Selesai")){
                            listTrainingSelesai.add(training);
                        }
                    }
                    listJumlahTraining.add(listTrainingSelesai.size());
                }
            }
        }

//        for(int i=0;i<6;i++){
//            listTrainer.add("trainer" + i);
//            listJumlahTraining.add(i);
//        }

        model.addAttribute("listTrainer", listTrainer);
        model.addAttribute("listJumlahTraining", listJumlahTraining);
        return "dashboard-trainer";
    }
}

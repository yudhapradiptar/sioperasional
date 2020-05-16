package sistem.operasional.sioperasional.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.service.OutletService;
import sistem.operasional.sioperasional.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.service.UserService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @Autowired
    OutletService outletService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createTrainingFormPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        TrainingModel newTraining = new TrainingModel();
        List<UserModel> listTrainer = new ArrayList<>();
        for(UserModel trainer : userService.getAllUser()){
            if(trainer.getRole().getNamaRole().equals("Operation Staff")){
                listTrainer.add(trainer);
            }
        }

        List<OutletModel> listOutlet = outletService.getOutletList();

        model.addAttribute("training", newTraining);
        model.addAttribute("listTrainer", listTrainer);
        model.addAttribute("listOutlet", listOutlet);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "form-create-training";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createTrainingSubmit(@ModelAttribute TrainingModel training, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        List<TrainingModel> listTraining = trainingService.getAllTraining();
        int yearTanggalTraining = training.getTanggalTraining().getYear();
        int monthTanggalTraining = training.getTanggalTraining().getMonth();
        int dateTanggalTraining = training.getTanggalTraining().getDate();
        int startHour = Integer.parseInt(training.getWaktuMulai().substring(0,2));
        int startMinute = Integer.parseInt(training.getWaktuMulai().substring(3,4));
        int finishHour = Integer.parseInt(training.getWaktuSelesai().substring(0,2));
        int finishMinute = Integer.parseInt(training.getWaktuSelesai().substring(3,4));
        Date combinedStartDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, startHour, startMinute);
        Date combinedFinishedDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, finishHour, finishMinute);
        for(TrainingModel trainingCompared : listTraining){
            if(training.getTanggalTraining().getYear() == trainingCompared.getTanggalTraining().getYear() &&
                    training.getTanggalTraining().getMonth() == trainingCompared.getTanggalTraining().getMonth() &&
                    training.getTanggalTraining().getDate() == trainingCompared.getTanggalTraining().getDate()){
                int yearTanggalTrainingCompared = trainingCompared.getTanggalTraining().getYear();
                int monthTanggalTrainingCompared = trainingCompared.getTanggalTraining().getMonth();
                int dateTanggalTrainingCompared = trainingCompared.getTanggalTraining().getDate();
                int startHourCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(0,2));
                int startMinuteCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(3,4));
                int finishHourCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(0,2));
                int finishMinuteCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(3,4));
                Date combinedStartDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTraining, dateTanggalTraining, startHourCompared, startMinuteCompared);
                Date combinedFinishDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTrainingCompared, dateTanggalTrainingCompared, finishHourCompared, finishMinuteCompared);
                if(combinedStartDate.before(combinedFinishDateCompared) && combinedFinishedDate.after(combinedStartDateCompared)){
                    model.addAttribute("notifTimeOverlap", "Waktu Training Overlap dengan Training yang sudah ada");
                    return "fail-create-training";
                }
            }
            if(combinedStartDate.after(combinedFinishedDate)){
                return "fail-create-training";
            }
        }
        try {
            training.setCreator(userService.getUserByUsername(currentUser.getUsername()));
            training.setStatusTraining("Menunggu Persetujuan");
            trainingService.createTraining(training);

            model.addAttribute("idTraining", training.getIdTraining());
            model.addAttribute("namaTrainer", training.getTrainer().getNama());
            model.addAttribute("namaOutlet", training.getOutlet().getNamaOutlet());
            return "success-create-training";
        } catch (NullPointerException e) {
            return "redirect:/training/create";
        }
    }

    @RequestMapping(value = "/delete/{idTraining}/confirmation", method = RequestMethod.GET)
    public String confirmationDeleteTraining(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel trainingDeleted = trainingService.getTrainingByIdTraining(idTraining);
        model.addAttribute("idTraining", trainingDeleted.getIdTraining());
        model.addAttribute("outlet", trainingDeleted.getOutlet().getNamaOutlet());
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "confirmation-delete-training";
    }

    @RequestMapping(value = "/delete/{idTraining}/confirm", method = RequestMethod.GET)
    public String actualDeleteTraining(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel trainingDeleted = trainingService.getTrainingByIdTraining(idTraining);
        String attributIdTraining = trainingDeleted.getIdTraining();
        trainingService.deleteTraining(trainingDeleted);

        model.addAttribute("atributIdTraining", attributIdTraining);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "success-delete-training";
    }

    @RequestMapping(value = "/edit/{idTraining}")
    public String editTrainingForm(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel existingTraining = trainingService.getTrainingByIdTraining(idTraining);
        if(existingTraining!=null){
//            model.addAttribute("bayar", existingTraining.getBayar());
//            model.addAttribute("tanggalRequest", existingTraining.getTanggalRequest());
//            model.addAttribute("tanggalTraining", existingTraining.getTanggalTraining());
//            model.addAttribute("waktuMulai", existingTraining.getWaktuMulai());
//            model.addAttribute("waktuSelesai", existingTraining.getWaktuSelesai());
//            model.addAttribute("trainer", existingTraining.getTrainer().getNama());
//            model.addAttribute("outlet", existingTraining.getOutlet().getNamaOutlet());
//            model.addAttribute("keterangan", existingTraining.getKeteranganTraining());
            model.addAttribute("training", existingTraining);

            List<UserModel> listTrainer = new ArrayList<>();
            for(UserModel trainer : userService.getAllUser()){
                if(trainer.getRole().getNamaRole().equals("Operation Staff")){
                    listTrainer.add(trainer);
                }
            }

            model.addAttribute("listTrainer", listTrainer);
            model.addAttribute("listOutlet", outletService.getOutletList());
            model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

            return "form-edit-training";
        }
        return "error/403"; //403 Belum ada
    }

    @RequestMapping(value = "/edit/{idTraining}", method = RequestMethod.POST)
    public String editTrainingSubmit(@PathVariable String idTraining, @ModelAttribute TrainingModel training, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        List<TrainingModel> listTraining = trainingService.getAllTraining();

        TrainingModel trainingEdited = trainingService.getTrainingByIdTraining(training.getIdTraining());

        int yearTanggalTraining = training.getTanggalTraining().getYear();
        int monthTanggalTraining = training.getTanggalTraining().getMonth();
        int dateTanggalTraining = training.getTanggalTraining().getDate();
        int startHour = Integer.parseInt(training.getWaktuMulai().substring(0, 2));
        int startMinute = Integer.parseInt(training.getWaktuMulai().substring(3, 4));
        int finishHour = Integer.parseInt(training.getWaktuSelesai().substring(0, 2));
        int finishMinute = Integer.parseInt(training.getWaktuSelesai().substring(3, 4));
        Date combinedStartDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, startHour, startMinute);
        Date combinedFinishedDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, finishHour, finishMinute);
        for(TrainingModel trainingCompared : listTraining){
            if(training.getTanggalTraining().getYear() == trainingCompared.getTanggalTraining().getYear() &&
                    training.getTanggalTraining().getMonth() == trainingCompared.getTanggalTraining().getMonth() &&
                    training.getTanggalTraining().getDate() == trainingCompared.getTanggalTraining().getDate() && !(training.getIdTraining().equals(trainingCompared.getIdTraining()))){

                int yearTanggalTrainingCompared = trainingCompared.getTanggalTraining().getYear();
                int monthTanggalTrainingCompared = trainingCompared.getTanggalTraining().getMonth();
                int dateTanggalTrainingCompared = trainingCompared.getTanggalTraining().getDate();
                int startHourCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(0,2));
                int startMinuteCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(3,4));
                int finishHourCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(0,2));
                int finishMinuteCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(3,4));
                Date combinedStartDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTraining, dateTanggalTraining, startHourCompared, startMinuteCompared);
                Date combinedFinishDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTrainingCompared, dateTanggalTrainingCompared, finishHourCompared, finishMinuteCompared);
                if(combinedStartDate.before(combinedFinishDateCompared) && combinedFinishedDate.after(combinedStartDateCompared)){
                    model.addAttribute("notifTimeOverlap", "Waktu Training Overlap dengan Training yang sudah ada");
                    return "fail-edit-training";
                }

            }
            if(combinedStartDate.after(combinedFinishedDate)){
                model.addAttribute("idTraining", idTraining);
                return "fail-edit-training";
            }
        }
        TrainingModel newTraining = trainingService.editTraining(training);
        model.addAttribute("newTraining", newTraining);
        model.addAttribute("idTraining", newTraining.getIdTraining());
        model.addAttribute("namaTrainer", newTraining.getTrainer().getNama());
        model.addAttribute("namaOutlet", newTraining.getOutlet().getNamaOutlet());
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "success-edit-training";
    }

    @RequestMapping(path = "/view/{idTraining}", method = RequestMethod.GET)
    public String viewTraining(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel viewedTraining = trainingService.getTrainingByIdTraining(idTraining);
        String roleCurrentUser = userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole();
        UserModel user = userService.getUserByUsername(currentUser.getUsername());

        if(viewedTraining != null){
            if(roleCurrentUser.equals("Operation Staff")){
                for(TrainingModel training : user.getListTrainingTrained()){
                    System.out.println(training.getTrainer().getUsername());
                    if(training.getIdTraining().equals(viewedTraining.getIdTraining())){
                        model.addAttribute("training", viewedTraining);
                        model.addAttribute("idTraining", viewedTraining.getIdTraining());
                        model.addAttribute("role", roleCurrentUser);

                        model.addAttribute("tanggalRequestString",  trainingService.tanggalFormat(viewedTraining.getTanggalRequest()));
                        model.addAttribute("tanggalTrainingString", trainingService.tanggalFormat(viewedTraining.getTanggalTraining()));

                        return "detail-training";
                    }
                }
                if(user.getListTrainingTrained().size()==0){
                    return "error/403";
                }
            } else {
                model.addAttribute("training", viewedTraining);
                model.addAttribute("idTraining", viewedTraining.getIdTraining());
                model.addAttribute("role", roleCurrentUser);

                model.addAttribute("tanggalRequestString",  trainingService.tanggalFormat(viewedTraining.getTanggalRequest()));
                model.addAttribute("tanggalTrainingString", trainingService.tanggalFormat(viewedTraining.getTanggalTraining()));
                return "detail-training";
            }
        }
        return "error/403";
    }

    @RequestMapping(path = "/view/{idTraining}/selesai", method = RequestMethod.GET)
    public String selesaiTraining(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel viewedTraining = trainingService.getTrainingByIdTraining(idTraining);
        String roleCurrentUser = userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole();
        UserModel user = userService.getUserByUsername(currentUser.getUsername());
        model.addAttribute("role", roleCurrentUser);
        Date now = new Date();
        if(viewedTraining != null){
            model.addAttribute("training", viewedTraining);
            if(viewedTraining.getStatusTraining().equals("Disetujui") && !now.before(viewedTraining.getTanggalTraining()) && viewedTraining.getTrainer()==userService.getUserByUsername(currentUser.getUsername())){
                viewedTraining.setStatusTraining("Selesai");
                model.addAttribute("tanggalTrainingString", trainingService.tanggalFormat(viewedTraining.getTanggalTraining()));
                model.addAttribute("training", viewedTraining);
                return "success-selesai-training";
            } else {
                return "fail-selesai-training";
            }
        }
        return "error/403";
    }

//     @RequestMapping(value = "/{idTraining}", method = RequestMethod.GET)
//     public String DetailTraining(@PathVariable("idTraining") String idTraining, Model model){

//         TrainingModel training = trainingService.getTrainingDetail(idTraining);

//         System.out.println("Training Detail");
//         System.out.println(training.getBayar());
//         System.out.println("End");

//         model.addAttribute("training", training);

//         return "training-detail";
//     }

    @RequestMapping(value = "/{idTraining}/{statusTraining}", method = RequestMethod.POST)
    public String updateTrainingSubmit(@PathVariable String idTraining, @PathVariable String statusTraining, @ModelAttribute TrainingModel trainingModel, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        String html = "";
        if ((statusTraining.equals("approve") || statusTraining.equals("reject") && trainingService.getTrainingByIdTraining(idTraining).getStatusTraining().equals("Menunggu Persetujuan"))){
            String word = "";
            if (statusTraining.equals("approve")){
                word = "Disetujui";
            } else {
                word = "Ditolak";
            }
            trainingService.updateTraining(trainingModel, word);
            model.addAttribute("training", trainingModel);
            model.addAttribute("status", word);
            html = "success-update-training";
        }
        else {
            html = "form-approve-training";
        }
        return html;
    }

}

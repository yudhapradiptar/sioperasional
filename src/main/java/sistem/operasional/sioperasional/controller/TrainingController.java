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
    public String createTrainingFormPage(Model model) {
        TrainingModel newTraining = new TrainingModel();
        List<UserModel> listTrainer = new ArrayList<>();
        for(UserModel trainer : userService.getAllUser()){
            if(trainer.getRole().getNamaRole().equals("Operation Staff")){
                listTrainer.add(trainer);
            }
        }

        List<OutletModel> listOutlet = outletService.getOutletList();

        model.addAttribute("listTrainer", listTrainer);
        model.addAttribute("listOutlet", listOutlet);

        return "form-create-training";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createTrainingSubmit(@ModelAttribute TrainingModel training, @AuthenticationPrincipal UserDetails currentUser, Model model) {
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
            if(training.getTanggalTraining() == trainingCompared.getTanggalTraining()){
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
                else {
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
            }
        }
        return "fail-create-training";
    }

    @RequestMapping(value = "/delete/{idTraining}/confirmation", method = RequestMethod.GET)
    public String confirmationDeleteTraining(@PathVariable String idTraining, Model model){
        TrainingModel trainingDeleted = trainingService.getTrainingByIdTraining(idTraining);
        model.addAttribute("idTraining", trainingDeleted.getIdTraining());
        model.addAttribute("outlet", trainingDeleted.getOutlet().getNamaOutlet());

        return "confirmation-delete-training";
    }

    @RequestMapping(value = "/delete/{idTraining}/confirm", method = RequestMethod.GET)
    public String actualDeleteTraining(@PathVariable String idTraining, Model model){
        TrainingModel trainingDeleted = trainingService.getTrainingByIdTraining(idTraining);
        String attributIdTraining = trainingDeleted.getIdTraining();
        trainingService.deleteTraining(trainingDeleted);

        model.addAttribute("atributIdTraining", attributIdTraining);

        return "success-delete-training";
    }

    @RequestMapping(value = "/edit")
    public String editTrainingForm(@RequestParam("idTraining") String idTraining, Model model){
        TrainingModel existingTraining = trainingService.getTrainingByIdTraining(idTraining);
        if(existingTraining!=null){
            model.addAttribute("bayar", existingTraining.getBayar());
            model.addAttribute("tanggalRequest", existingTraining.getTanggalRequest());
            model.addAttribute("tanggalTraining", existingTraining.getTanggalTraining());
            model.addAttribute("waktuMulai", existingTraining.getWaktuMulai());
            model.addAttribute("waktuSelesai", existingTraining.getWaktuSelesai());
            model.addAttribute("trainer", existingTraining.getTrainer().getNama());
            model.addAttribute("outlet", existingTraining.getOutlet().getNamaOutlet());
            model.addAttribute("keterangan", existingTraining.getKeteranganTraining());

            return "form-edit-training";
        }
        return "404"; //Sementara sebelum ada 404
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editTrainingSubmit(String idTraining, @ModelAttribute TrainingModel training, Model model) {
        List<TrainingModel> listTraining = trainingService.getAllTraining();

        int yearTanggalTraining = training.getTanggalTraining().getYear();
        int monthTanggalTraining = training.getTanggalTraining().getMonth();
        int dateTanggalTraining = training.getTanggalTraining().getDate();
        int startHour = Integer.parseInt(training.getWaktuMulai().substring(0, 2));
        int startMinute = Integer.parseInt(training.getWaktuMulai().substring(3, 4));
        int finishHour = Integer.parseInt(training.getWaktuSelesai().substring(0, 2));
        int finishMinute = Integer.parseInt(training.getWaktuSelesai().substring(3, 4));
        Date combinedStartDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, startHour, startMinute);
        Date combinedFinishedDate = new Date(yearTanggalTraining, monthTanggalTraining, dateTanggalTraining, finishHour, finishMinute);
        for (TrainingModel trainingCompared : listTraining) {
            if (training.getTanggalTraining() == trainingCompared.getTanggalTraining()) {
                int yearTanggalTrainingCompared = trainingCompared.getTanggalTraining().getYear();
                int monthTanggalTrainingCompared = trainingCompared.getTanggalTraining().getMonth();
                int dateTanggalTrainingCompared = trainingCompared.getTanggalTraining().getDate();
                int startHourCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(0, 2));
                int startMinuteCompared = Integer.parseInt(trainingCompared.getWaktuMulai().substring(3, 4));
                int finishHourCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(0, 2));
                int finishMinuteCompared = Integer.parseInt(trainingCompared.getWaktuSelesai().substring(3, 4));
                Date combinedStartDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTraining, dateTanggalTraining, startHourCompared, startMinuteCompared);
                Date combinedFinishDateCompared = new Date(yearTanggalTrainingCompared, monthTanggalTrainingCompared, dateTanggalTrainingCompared, finishHourCompared, finishMinuteCompared);
                if (combinedStartDate.before(combinedFinishDateCompared) && combinedFinishedDate.after(combinedStartDateCompared)) {
                    model.addAttribute("notifTimeOverlap", "Waktu Training Overlap dengan Training yang sudah ada");
                    return "fail-edit-training";
                } else {
                    TrainingModel newTraining = trainingService.editTraining(training);
                    model.addAttribute("newTraining", newTraining);

                    return "success-edit-training";
                }
            }
        }
        return "fail-edit-training";
    }

    @RequestMapping(path = "/view/{idTraining}", method = RequestMethod.GET)
    public String viewTraining(@PathVariable String idTraining, @AuthenticationPrincipal UserDetails currentUser, Model model){
        TrainingModel viewedTraining = trainingService.getTrainingByIdTraining(idTraining);
        String roleCurrentUser = userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole();

        if(viewedTraining != null){
            model.addAttribute("training", viewedTraining);
            model.addAttribute("role", roleCurrentUser);

            return "detail-training";
        }
        return "404";
    }

}
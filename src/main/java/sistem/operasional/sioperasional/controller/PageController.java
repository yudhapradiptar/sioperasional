package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.PurchaseOrderService;
import sistem.operasional.sioperasional.service.TrainingService;
import sistem.operasional.sioperasional.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    CustomerFeedbackService customerFeedbackService;

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @Autowired
    DeliveryOrderService deliveryOrderService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    @RequestMapping("/")
    public String home (@AuthenticationPrincipal UserDetails currentUser, Model model, Authentication auth) {
        String roleNow = userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole();
        UserModel userNow = userService.getUserCurrentLoggedIn();
        if (roleNow.equals("Operation Manager") || roleNow.equals("Product Operation Specialist")) {
            List<DeliveryOrderModel> listDeliveryOrderBundling = deliveryOrderService.getDeliveryOrderListBySubscribed();
            float sumDeliveryOrderBundling = listDeliveryOrderBundling.size();
            model.addAttribute("sumDeliveryOrderBundling", (int)sumDeliveryOrderBundling);

            List<DeliveryOrderModel> listDeliveryOrderBundlingNotSetTanggalYet = deliveryOrderService.getDeliveryOrderListBySubscribedAndTanggalSubcribeStartNull();
            float sumDeliveryOrderBundlingNotSetTanggalYet = listDeliveryOrderBundlingNotSetTanggalYet.size();
            model.addAttribute("sumDeliveryOrderBundlingNotSetTanggalYet", (int)sumDeliveryOrderBundlingNotSetTanggalYet);

            if(sumDeliveryOrderBundling<1){
                float persentaseBelumSetTanggal = 0;
                model.addAttribute("persentaseBelumSetTanggal", persentaseBelumSetTanggal);
            }
            else {
                float persentaseBelumSetTanggal = 100 * (sumDeliveryOrderBundlingNotSetTanggalYet / sumDeliveryOrderBundling);
                model.addAttribute("persentaseBelumSetTanggal", persentaseBelumSetTanggal);
            }

            //Purchase Order
            List<PurchaseOrderModel> listPO = purchaseOrderDB.findAll();
            float sumPO = listPO.size();
            model.addAttribute("sumPO", (int)sumPO);

            List<PurchaseOrderModel> listPONotDisetujui = purchaseOrderService.getPurchaseOrderListByNotDisetujui();
            float sumPONotDisetujui = listPONotDisetujui.size();
            System.out.println(listPONotDisetujui);
            model.addAttribute("sumPONotDisetujui", (int)sumPONotDisetujui);

            float persentasePONotDisetujui = 100 * (sumPONotDisetujui / sumPO);
            model.addAttribute("persentasePONotDisetujui", persentasePONotDisetujui);
        }

        if (roleNow.equals("Operation Staff")) {
            List<TrainingModel> listMyTraining = trainingService.getListTrainingByTrainer(userNow);
            if (listMyTraining.size() > 2) {
                model.addAttribute("listMyTraining", listMyTraining.subList(0, 2));
            } else {
                model.addAttribute("listMyTraining", listMyTraining);
            }

            String date = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(LocalDateTime.now());
            List<TrainingModel> listMyTrainingToday = trainingService.getListTrainingByTrainerAndToday(userNow.getId(), date);
            System.out.println("--------------");
            System.out.println(listMyTrainingToday);
            System.out.println(date);
            model.addAttribute("listMyTrainingToday", listMyTrainingToday);
        }

        if (roleNow.equals("Operation Manager") || roleNow.equals("Operation Lead")) {
            List<TrainingModel> listTrainingMenungguPersetujuan = trainingService.getAllTrainingByStatusTraining("Menunggu Persetujuan");
            if (listTrainingMenungguPersetujuan.size() > 2) {
                model.addAttribute("listTrainingMenungguPersetujuan", listTrainingMenungguPersetujuan.subList(0, 2));
            } else {
                model.addAttribute("listTrainingMenungguPersetujuan", listTrainingMenungguPersetujuan);
            }

            List<TrainingModel> listTrainingDitolak = trainingService.getAllTrainingByStatusTraining("Ditolak");
            if (listTrainingDitolak.size() > 2) {
                model.addAttribute("listTrainingDitolak", listTrainingDitolak.subList(0, 2));
            } else {
                model.addAttribute("listTrainingDitolak", listTrainingDitolak);
            }
        }

        model.addAttribute("nama", userNow.getNama());
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "homepage-dev";
    }

    @RequestMapping("/login")
    public String login (Model model){
        return "login-dev";
    }

    @RequestMapping("/blank-page/")
    public String blankPage (Model model) {
        List<DeliveryOrderModel> listDeliveryOrder = deliveryOrderService.getDeliveryOrderList();

        model.addAttribute("listDeliveryOrder", listDeliveryOrder);
        return "blank-page";
    }

    @RequestMapping("/template/")
    public String template (Model model) {
        List<DeliveryOrderModel> listDeliveryOrder = deliveryOrderService.getDeliveryOrderList();

        model.addAttribute("listDeliveryOrder", listDeliveryOrder);
        return "template";
    }

}
package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sistem.operasional.sioperasional.model.CustomerFeedbackModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.CustomerFeedbackService;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.RoleModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.RoleService;
import sistem.operasional.sioperasional.service.TrainingService;
import sistem.operasional.sioperasional.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/")
    public String home (@AuthenticationPrincipal UserDetails currentUser, Model model, Authentication auth) {
        String roleNow = userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole();
        if (roleNow.equals("Operation Manager") || roleNow.equals("Product Operation Specialist")) {
            List<DeliveryOrderModel> listDeliveryOrderBundling = deliveryOrderService.getDeliveryOrderListBySubscribed();
            float sumDeliveryOrderBundling = listDeliveryOrderBundling.size();
            model.addAttribute("sumDeliveryOrderBundling", (int)sumDeliveryOrderBundling);

            List<DeliveryOrderModel> listDeliveryOrderBundlingNotSetTanggalYet = deliveryOrderService.getDeliveryOrderListBySubscribedAndTanggalSubcribeStartNull();
            float sumDeliveryOrderBundlingNotSetTanggalYet = listDeliveryOrderBundlingNotSetTanggalYet.size();
            model.addAttribute("sumDeliveryOrderBundlingNotSetTanggalYet", (int)sumDeliveryOrderBundlingNotSetTanggalYet);

            float persentaseBelumSetTanggal = 100 * (sumDeliveryOrderBundlingNotSetTanggalYet / sumDeliveryOrderBundling);
            model.addAttribute("persentaseBelumSetTanggal", persentaseBelumSetTanggal);
        }

        UserModel userNow = userService.getUserByUsername(currentUser.getUsername());
        model.addAttribute("nama", userNow.getNama());
        // return "homepage-with-card";

        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "homepage-dev";
    }

    @RequestMapping("/login")
    public String login (Model model){
        return "login";
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
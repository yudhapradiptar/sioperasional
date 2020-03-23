package sistem.operasional.sioperasional.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.RoleService;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    DeliveryOrderService deliveryOrderService;

    @RequestMapping("/")
    public String home (Model model, Authentication auth) {
        List<DeliveryOrderModel> listDeliveryOrder = deliveryOrderService.getDeliveryOrderList();

        model.addAttribute("listDeliveryOrder", listDeliveryOrder);
        return "home";
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

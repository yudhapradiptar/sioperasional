package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sistem.operasional.sioperasional.model.PurchaseOrderModel;
import sistem.operasional.sioperasional.service.PurchaseOrderService;

import java.util.List;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService POService;

    @RequestMapping("/")
    public String getAll(Model model){
        List<PurchaseOrderModel> listPO = POService.getAll();

        model.addAttribute("listPO", listPO);
        return "list-po";
    }

//    public void approve()

}

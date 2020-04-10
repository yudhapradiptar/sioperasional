package sistem.operasional.sioperasional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sistem.operasional.sioperasional.model.StatusItemModel;
import sistem.operasional.sioperasional.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StatusItemController {
    @Autowired
    StatusItemService statusItemService;

    @RequestMapping(value = "/hardware-fulfillment/item/status/create", method = RequestMethod.GET)
    public String createStatusItem(Model model) {
        StatusItemModel newStatus = new StatusItemModel();
        model.addAttribute("status", newStatus);

        return "form-create-status-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/item/status/create/success", method = RequestMethod.POST)
    public String createItemSubmit(@ModelAttribute StatusItemModel statusItem, Model model) {
        try {
            System.out.println(statusItem.getNamaStatusItem());
            System.out.println(statusItem.getIdStatusItem());
            System.out.println("Begin create");
            statusItemService.createStatusItem(statusItem);
            System.out.println("End");
            model.addAttribute("status", statusItem);
            return "success-create-status";
        } catch (NullPointerException e) {
            return "form-create-status-item";
        }
    }

    @RequestMapping(value = "/hardware-fulfillment/item/status/all", method = RequestMethod.GET)
    public String viewAllItem(Model model){
        List<StatusItemModel> listAllStatusItem = statusItemService.getListStatusItem();
        model.addAttribute("allStatusItem", listAllStatusItem);
        System.out.println("All Status");
        for (StatusItemModel s : listAllStatusItem){
            System.out.println(s.getNamaStatusItem());
            System.out.println(s.getIdStatusItem());
        }
        System.out.println("End");
        return "yyy";
    }
}
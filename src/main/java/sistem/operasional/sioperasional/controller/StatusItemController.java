package sistem.operasional.sioperasional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
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

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/hardware-fulfillment/item/status/create", method = RequestMethod.GET)
    public String createStatusItem(Model model) {
        StatusItemModel newStatus = new StatusItemModel();
        model.addAttribute("status", newStatus);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "form-create-status-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/item/status/create/success", method = RequestMethod.POST)
    public String createItemSubmit(@ModelAttribute StatusItemModel statusItem, Model model) {
        try {
            statusItemService.createStatusItem(statusItem);
            model.addAttribute("status", statusItem);
            model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
            return "success-create-status";
        } catch (NullPointerException e) {
            model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
            return "form-create-status-item";
        }
    }

    @RequestMapping(value = "/hardware-fulfillment/item/status/all", method = RequestMethod.GET)
    public String viewAllItem(Model model){
        List<StatusItemModel> listAllStatusItem = statusItemService.getListStatusItem();
        model.addAttribute("allStatusItem", listAllStatusItem);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "list-status-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/item/status/delete")
    public String deleteStatusItem(@RequestParam("idStatusItem") Long idStatusItem, @AuthenticationPrincipal UserDetails currentUser, Model model){
        StatusItemModel deletedStatusItem = statusItemService.getStatusItemByIdStatusItem(idStatusItem);
        model.addAttribute("statusItem", deletedStatusItem);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        List<ItemModel> listItemStatusItem = itemService.getItemListByStatusItem(statusItemService.getStatusItemByIdStatusItem(idStatusItem));
        if(listItemStatusItem.size()!=0){
            return "fail-delete-status-item";
        }
        statusItemService.deleteStatusItem(deletedStatusItem);
        return "success-delete-status-item";
    }
}
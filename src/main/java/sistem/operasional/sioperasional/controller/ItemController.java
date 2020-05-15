package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.repository.StatusItemDB;
import sistem.operasional.sioperasional.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hardware-fulfillment/item")
public class ItemController {
    @Autowired
    ItemPOService itemPOService;

    @Autowired
    ItemService itemService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    StatusItemService statusItemService;

    @Autowired
    KategoriItemService kategoriItemService;

    @Autowired
    JenisItemService jenisItemService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/{nomorPurchaseOrder}/close", method = RequestMethod.GET)
    public String addItemFromPO(@PathVariable String nomorPurchaseOrder, @AuthenticationPrincipal UserDetails currentUser, @ModelAttribute ItemModel item, @ModelAttribute ItemPOModel itemPO, Model model){
        PurchaseOrderModel purchaseOrder = purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder);
        if(purchaseOrder.getStatusPO().equals("Open") && purchaseOrder.isDisetujui()){
            List<ItemPOModel> listOfItemPOByPurchaseOrder = itemPOService.getItemPObyPurchaseOrder(purchaseOrder);
            for(ItemPOModel itemPOCreated : listOfItemPOByPurchaseOrder){
                for(int j=0; j<itemPOCreated.getJumlahItem(); j++){
                    ItemModel itemModel = new ItemModel();
                    itemModel.setRusak(false);
                    itemModel.setTanggalDatang(new Date());
                    itemModel.setKategoriItem(itemPOCreated.getKategoriItem());
                    itemModel.setJenisItem(itemPOCreated.getJenisItem());
                    itemModel.setPurchaseOrder(itemPOCreated.getPurchaseOrder());
                    itemModel.setStatusItem(statusItemService.getStatusItemByIdStatusItem(Long.valueOf(1)));
                    itemService.createItem(itemModel);
                }
            }
            purchaseOrder.setStatusPO("Closed");
//            purchaseOrderService.addPurchaseOrder(purchaseOrder);
            model.addAttribute("purchaseOrder", purchaseOrder);
            model.addAttribute("listItem", purchaseOrder.getListitem());
            model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
            return "success-close-po";
        }
        model.addAttribute("nomorpo", purchaseOrder.getNomorPurchaseOrder());
        return "fail-close-po";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createItemFormPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        ItemModel newItem = new ItemModel();
        List<KategoriItemModel> listKategori = kategoriItemService.getKategoriItemList();
        List<JenisItemModel> listJenis = jenisItemService.getJenisItemList();
        List<StatusItemModel> listStatus = statusItemService.getListStatusItem();
        model.addAttribute("item", newItem);
        model.addAttribute("listKategori", listKategori);
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("listStatus", listStatus);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "form-create-item";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createItemSubmit(@ModelAttribute ItemModel item, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        try {
            item.setRusak(false);
            itemService.createItem(item);

            model.addAttribute("kategoriItem", item.getKategoriItem().getNamaKategoriItem());
            model.addAttribute("jenisItem", item.getJenisItem().getNamaJenisItem());
            model.addAttribute("idItem", item.getIdItem());
            model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
            return "success-create-item";
        } catch (NullPointerException e) {
            return "form-create-item";
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String viewAllItem(@AuthenticationPrincipal UserDetails currentUser, Model model){
        List<ItemModel> listAllItem = itemService.getItemList();
        model.addAttribute("listAllItem", listAllItem);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "list-item";
    }

    @RequestMapping(value = "/update/{idItem}", method = RequestMethod.GET)
    public String UpdateStatusItem(@PathVariable("idItem") String idItem, @AuthenticationPrincipal UserDetails currentUser, Model model){
        ItemModel item = itemService.getItemDetailByIdItem(Long.parseLong(idItem));
        List<StatusItemModel> statusItem = statusItemService.getListStatusItem();
        ItemModel itemDetail = item;
        for(StatusItemModel s : statusItem){
            System.out.println(s.getNamaStatusItem());
        }

        model.addAttribute("item", itemDetail);
        model.addAttribute("allStatusItem", statusItem);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "status-item-isRusak";
    }

    @RequestMapping(value = "/update/{idItem}/success", method = RequestMethod.POST)
    public String updateStatusItemSubmit(@PathVariable String idItem, @ModelAttribute ItemModel itemModel, @AuthenticationPrincipal UserDetails currentUser, Model model) {

        itemService.updateStatusItem(itemModel);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "redirect:/hardware-fulfillment/item/all";
    }

}
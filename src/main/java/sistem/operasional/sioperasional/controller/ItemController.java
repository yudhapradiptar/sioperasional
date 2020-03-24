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

import java.util.Date;
import java.util.List;

@Controller
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
    MerekItemService merekItemService;

    @RequestMapping(path = "/view/{nomorPurchaseOrder}/close", method = RequestMethod.POST)
    public String addItemFromPO(@PathVariable String nomorPurchaseOrder, @ModelAttribute ItemModel item, @ModelAttribute ItemPOModel itemPO, Model model){
        PurchaseOrderModel purchaseOrder = purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder);
        List<ItemPOModel> listOfItemPOByPurchaseOrder = itemPOService.getItemPObyPurchaseOrder(purchaseOrder);
        for(int i=0; i<listOfItemPOByPurchaseOrder.size();i++){
            ItemPOModel itemPOCreated = listOfItemPOByPurchaseOrder.get(i);
            for(int j=0; j<itemPOCreated.getJumlahItem(); j++){
                ItemModel itemModel = new ItemModel();
                itemModel.setRusak(false);
                itemModel.setTanggalDatang(new Date());
                itemModel.setKategoriItem(itemPOCreated.getKategoriItem());
                itemModel.setMerekItem(itemPOCreated.getMerekItem());
                itemModel.setPurchaseOrder(itemPOCreated.getPurchaseOrder());
                itemModel.setStatusItem(statusItemService.getStatusItemByIdStatusItem(Long.valueOf(1)));
                itemService.createItem(itemModel);
            }
        }
        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("listItem", purchaseOrder.getListitem());
        return "success-close-po";
    }

    @RequestMapping(value = "/hardware-fulfillment/item/create", method = RequestMethod.GET)
    public String createItemFormPage(Model model) {
        ItemModel newItem = new ItemModel();
        List<KategoriItemModel> listKategori = kategoriItemService.getKategoriItemList();
        List<MerekItemModel> listMerek = merekItemService.getMerekItemList();
        List<StatusItemModel> listStatus = statusItemService.getListStatusItem();
        model.addAttribute("item", newItem);
        model.addAttribute("listKategori", listKategori);
        model.addAttribute("listMerek", listMerek);
        model.addAttribute("listStatus", listStatus);

        return "form-create-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/item", method = RequestMethod.POST)
    public String createItemSubmit(@ModelAttribute ItemModel item, Model model) {
        try {
            item.setRusak(false);
            itemService.createItem(item);

            model.addAttribute("kategoriItem", item.getKategoriItem().getNamaKategoriItem());
            model.addAttribute("merekItem", item.getMerekItem().getNamaMerekItem());
            model.addAttribute("idItem", item.getIdItem());
            return "success-create-item";
        } catch (NullPointerException e) {
            return "form-create-item";
        }
    }

    @RequestMapping(value = "/hardware-fulfillment/item/all", method = RequestMethod.GET)
    public String viewAllItem(Model model){
        List<ItemModel> listAllItem = itemService.getItemList();
        model.addAttribute("listAllItem", listAllItem);
        return "list-item";
    }




}

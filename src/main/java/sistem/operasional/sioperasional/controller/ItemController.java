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
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.ItemPOModel;
import sistem.operasional.sioperasional.service.ItemPOService;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.PurchaseOrderService;
import sistem.operasional.sioperasional.service.StatusItemService;

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

    @RequestMapping(path = "/view/{nomorPurchaseOrder}/close", method = RequestMethod.POST)
    public String addItemFromPO(@PathVariable String nomorPurchaseOrder, @ModelAttribute ItemModel item, @ModelAttribute ItemPOModel itemPO, Model model){
        List<ItemPOModel> listOfItemPOByPurchaseOrder = itemPOService.getItemPObyPurchaseOrder(purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder));
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
        return "viewAllItem";
    }

    @RequestMapping(value = "/hardware-fulfillment/item/create", method = RequestMethod.GET)
    public String createItemFormPage(Model model) {
        ItemModel newItem = new ItemModel();
        model.addAttribute("item", newItem);

        return "form-create-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/item", method = RequestMethod.POST)
    public String createItemSubmit(@ModelAttribute ItemModel item, Model model) {
        try {
            itemService.createItem(item);
            return "viewAllItem";
        } catch (NullPointerException e) {
            return "formCreateItem";
        }
    }

    @RequestMapping(value = "/hardware-fulfillment/item/all", method = RequestMethod.GET)
    public String viewAllItem(Model model){
        List<ItemModel> listAllItem = itemService.getItemList();
        model.addAttribute("listAllItem", listAllItem);
        return "viewAllItem";
    }

    


}

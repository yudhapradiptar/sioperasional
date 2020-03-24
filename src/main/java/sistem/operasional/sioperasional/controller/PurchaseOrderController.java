package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.repository.VendorDB;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.PurchaseOrderService;
import sistem.operasional.sioperasional.service.VendorService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    VendorService vendorService;

    @Autowired
    PurchaseOrderService POService;

    @Autowired
    ItemService itemService;

    @RequestMapping("/")
    public String getAll(Model model) {
//        List<PurchaseOrderModel> listPO = POService.getAll();
//
//        model.addAttribute("listPO", listPO);
        return "list-pre-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDeliveryOrderFormPage(Model model) {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNull();

        List<VendorModel> listVendor = vendorService.getVendorList();

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("purchaseOrder", purchaseOrderModel);
//        model.addAttribute("listItem", itemModels);

        return "form-add-purchase-order";

//    public void approve()

    }
}

package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.PurchaseOrderService;
import sistem.operasional.sioperasional.service.UserService;
import sistem.operasional.sioperasional.service.VendorService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    UserService userService;

    @Autowired
    VendorService vendorService;

    @Autowired
    PurchaseOrderService POService;

    @Autowired
    ItemService itemService;

    @RequestMapping("/")
    public String getAll(Model model) {
        List<PurchaseOrderModel> listPO = POService.getAll();

        model.addAttribute("listPO", listPO);
        return "list-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDeliveryOrderFormPage(Model model) {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

//        List<ItemModel> itemModels = itemService.getItemList();

        List<VendorModel> listVendor = vendorService.getVendorList();

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("purchaseOrder", purchaseOrderModel);
//        model.addAttribute("listItem", itemModels);

        return "form-add-purchase-order";

//    public void approve()

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPurchaseOrderSubmit(@ModelAttribute PurchaseOrderModel purchaseOrderModel,
                                         @ModelAttribute ItemModel itemModel, Model model) {

        UserModel user = userService.getUserByUsername("prodOpsSpec");
        purchaseOrderModel.setCreator(user);
        Long l = new Long(1);
        StatusItemModel status = new StatusItemModel();
        status.setIdStatusItem(l);
        purchaseOrderModel.setStatusPO(status);

        List<PurchaseOrderModel> purchaseOrderModels = POService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModels) {
            if (po.getNomorPurchaseOrder().equals(purchaseOrderModel.getNomorPurchaseOrder())) {
                String nomorPO = purchaseOrderModel.getNomorPurchaseOrder();
                model.addAttribute("nomorPO", nomorPO);
                return "purchase-order-already-exist";
            }
        }

        POService.addPurchaseOrder(purchaseOrderModel);

        List<VendorModel> vendors = vendorService.getVendorList();
        String namaVendor="<Nama Vendor Tidak Berhasil Diambil";
        for(VendorModel vendor:vendors){
            if(vendor.getIdVendor() == purchaseOrderModel.getVendor().getIdVendor()){
                namaVendor = vendor.getNamaVendor();
            }
        }

        model.addAttribute("purchaseOrder", purchaseOrderModel);
        model.addAttribute("namaVendor", namaVendor);
        return "add-purchase-order";
    }
}

package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.PurchaseOrderService;
import sistem.operasional.sioperasional.service.UserService;
import sistem.operasional.sioperasional.service.VendorService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderDB purchaseOrderDB;

    @Autowired
    UserService userService;

    @Autowired
    VendorService vendorService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ItemService itemService;

    @RequestMapping("/")
    public String getAll(Model model) {
        List<PurchaseOrderModel> listPO = purchaseOrderDB.findAll(Sort.by(Sort.Direction.ASC, "nomorPurchaseOrder"));
        for (PurchaseOrderModel po: listPO) {
            System.out.println(po.getNomorPurchaseOrder());
        }

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
                                         @ModelAttribute Model model) {

        UserModel user = userService.getUserByUsername("prodOpsSpec");
        purchaseOrderModel.setCreator(user);
        Long l = new Long(1);
        StatusItemModel status = new StatusItemModel();
        status.setIdStatusItem(l);
        purchaseOrderModel.setStatusPO(status);

//        LocalTime localTime = LocalTime.now();
//        String startingTime = new SimpleDateFormat("hh:mm:ss").format(localTime);
//
//        Date datePO = purchaseOrderModel.getTanggalOpen();
//        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(datePO);
//
//        LocalDateTime dt = LocalDateTime.of(startingDate, startingTime);

        List<PurchaseOrderModel> purchaseOrderModels = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModels) {
            if (po.getNomorPurchaseOrder().equals(purchaseOrderModel.getNomorPurchaseOrder())) {
                String nomorPO = purchaseOrderModel.getNomorPurchaseOrder();
                model.addAttribute("nomorPO", nomorPO);
                return "purchase-order-already-exist";
            }
        }

        purchaseOrderService.addPurchaseOrder(purchaseOrderModel);

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

        @RequestMapping(value = "/approve/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String approvePurchaseOrder(@PathVariable("nomorPurchaseOrder") String nomorPurchaseOrder, @ModelAttribute PurchaseOrderModel purchaseOrderModel){
        List<PurchaseOrderModel> purchaseOrderModelList = purchaseOrderService.getAll();
        Optional<PurchaseOrderModel> purchaseOrder = purchaseOrderDB.findById(nomorPurchaseOrder);
        purchaseOrder.get().setDisetujui(true);
        purchaseOrderDB.save(purchaseOrder.get());

        return "redirect:/purchase-order/";
    }

}

package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.repository.ItemPODB;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;
import sistem.operasional.sioperasional.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    ItemPODB itemPODB;

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

    @Autowired
    ItemPOService itemPOService;

    @Autowired
    MerekItemService merekItemService;

    @Autowired
    KategoriItemService kategoriItemService;


    @RequestMapping("/")
    public String getAll(Model model) {
        List<PurchaseOrderModel> listPO = purchaseOrderDB.findAll(Sort.by(Sort.Direction.ASC, "nomorPurchaseOrder"));
        model.addAttribute("listPO", listPO);
        return "list-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPurchaseOrderPage(Model model) {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

        List<VendorModel> listVendor = vendorService.getVendorList();

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("purchaseOrder", purchaseOrderModel);

//        model.addAttribute("listItem", itemModels);

        return "form-add-purchase-order";

//    public void approve()

    }

    @RequestMapping(value="/add/item/", method = RequestMethod.GET)
    public String addMoreForm(@ModelAttribute PurchaseOrderModel purchaseOrderModel, BindingResult bindingResult, Model model) {
        System.out.println("workkok-------------------------------------------------------------------");
        if(purchaseOrderModel.getItemPOModels() == null) {
            System.out.println("masuk loop");
            purchaseOrderModel.setItemPOModels(new ArrayList<ItemPOModel>());
        }

        purchaseOrderModel.getItemPOModels().add(new ItemPOModel());
        System.out.println("panjang :" + purchaseOrderModel.getItemPOModels().size());
        model.addAttribute("purchaseOrder", purchaseOrderModel);
        return "form-add-item-purchase-order";
    }

    @RequestMapping(value="/add/item/", method = RequestMethod.POST)
    public String addItems(@ModelAttribute ItemPOModel itemPOModel,
                           @ModelAttribute("nomerPO") String nomorPO,
                           Model model) {

        PurchaseOrderModel purchaseOrder = null;
        for (PurchaseOrderModel po: purchaseOrderService.getAll()) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(nomorPO)){
                purchaseOrder = po;
            }
        }

        itemPOModel.setPurchaseOrder(purchaseOrder);
        ItemPOModel temp = itemPOService.addItemPO(itemPOModel);

        String namaVendor="<Nama Vendor Tidak Berhasil Diambil>";
        for(VendorModel vendor : vendorService.getVendorList()){
            if(vendor.getIdVendor() == purchaseOrder.getVendor().getIdVendor()){
                namaVendor = vendor.getNamaVendor();
            }
        }


        int jumlahItem = temp.getJumlahItem();
        String kategoriItem = "<Kategori Tidak Berhasil Dicari>";
        String merekItem = "<Merek Tidak Berhasil Dicari>";

        for (KategoriItemModel kategoriItemModel : kategoriItemService.getKategoriItemList()) {
            if(kategoriItemModel.getIdKategoriItem() == temp.getKategoriItem().getIdKategoriItem()){
                kategoriItem = kategoriItemModel.getNamaKategoriItem();
            }
        }

        for (MerekItemModel merekItemModel : merekItemService.getMerekItemList()) {
            if(merekItemModel.getIdMerekItem() == temp.getMerekItem().getIdMerekItem()){
                merekItem = merekItemModel.getNamaMerekItem();
            }
        }


        model.addAttribute("jumlahItem", jumlahItem);
        model.addAttribute("kategoriItem", kategoriItem);
        model.addAttribute("merekItem", merekItem);
        model.addAttribute("namaVendor", namaVendor);
        model.addAttribute("purchaseOrder", purchaseOrder);
        return "add-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPurchaseOrderSubmit(@ModelAttribute PurchaseOrderModel purchaseOrderModel,
                                         Model model) {

        ItemPOModel itemPOModel = new ItemPOModel();
        UserModel user = userService.getUserByUsername("prodOpsSpec");
        purchaseOrderModel.setCreator(user);
        Long l = new Long(1);
        StatusItemModel status = new StatusItemModel();
        status.setIdStatusItem(l);
        purchaseOrderModel.setStatusPO(status);
        purchaseOrderModel.setItemPOModels(new ArrayList<ItemPOModel>());
        purchaseOrderModel.getItemPOModels().add(new ItemPOModel());

        List<PurchaseOrderModel> purchaseOrderModels = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModels) {
            if (po.getNomorPurchaseOrder().equals(purchaseOrderModel.getNomorPurchaseOrder())) {
                String nomorPO = purchaseOrderModel.getNomorPurchaseOrder();
                model.addAttribute("nomorPO", nomorPO);
                return "purchase-order-already-exist";
            }
        }

        purchaseOrderService.addPurchaseOrder(purchaseOrderModel);

        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<MerekItemModel> listMerek = merekItemService.getMerekItemList();
        String nomorPurchaseOrder = purchaseOrderModel.getNomorPurchaseOrder();

        model.addAttribute("nomerPO", nomorPurchaseOrder);
        model.addAttribute("listMerekItem", listMerek);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("itemPOModel",itemPOModel);
        model.addAttribute("purchaseOrder", purchaseOrderModel);
        return "form-add-item-purchase-order";
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

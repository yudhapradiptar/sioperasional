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

import java.util.*;

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
        List<ItemPOModel> itemPOModelList = new ArrayList<ItemPOModel>();
        itemPOModelList.add(new ItemPOModel());

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("purchaseOrder", purchaseOrderModel);
        model.addAttribute("listItem", itemPOModelList);

        return "form-add-purchase-order";

    }

    @RequestMapping(value="/add/item/{nomorPurchaseOrder}", method = RequestMethod.POST, params= {"addMore"})
    public String addMoreForm(@PathVariable("nomorPurchaseOrder") String nomorPO, @ModelAttribute PurchaseOrderModel purchaseOrder,
                              BindingResult bindingResult, Model model) {

        if(purchaseOrder.getListItemPO() == null) {
            purchaseOrder.setListItemPO(new ArrayList<ItemPOModel>());
        }

        purchaseOrder.getListItemPO().add(new ItemPOModel());
        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<MerekItemModel> listMerek = merekItemService.getMerekItemList();

        model.addAttribute("listMerekItem", listMerek);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("nomorPO", nomorPO);
        model.addAttribute("purchaseOrder", purchaseOrder);
        return "form-add-item-purchase-order";
    }

    @RequestMapping(value="/add/item/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String addItems(@PathVariable("nomorPurchaseOrder") String nomorPO, @ModelAttribute PurchaseOrderModel purchaseOrder,
                           Model model) {

        HashMap<String, ItemPOModel> itemPOMap = new HashMap<>();
        PurchaseOrderModel purchaseOrder1 = new PurchaseOrderModel();
        for (PurchaseOrderModel po : purchaseOrderService.getAll()) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(purchaseOrder.getNomorPurchaseOrder())){
                purchaseOrder1 = po;
            }
        }
        for (ItemPOModel itemPO : purchaseOrder.getListItemPO()) {
            itemPO.setPurchaseOrder(purchaseOrder1);
            String kategoriItem = itemPO.getKategoriItem().getNamaKategoriItem();
            itemPOMap.put(kategoriItem, itemPO);
//            temp = itemPO;
            itemPOService.addItemPO(itemPO);
        }

        String namaVendor = purchaseOrder1.getVendor().getNamaVendor();
        List<ItemPOModel> listItemPO = purchaseOrder1.getListItemPO();

        model.addAttribute("itemMaps", itemPOMap);
        model.addAttribute("listItemPO", listItemPO);
        model.addAttribute("namaVendor", namaVendor);
        model.addAttribute("purchaseOrder", purchaseOrder1);
        return "add-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPurchaseOrderSubmit(@ModelAttribute PurchaseOrderModel purchaseOrderModel,
                                         Model model) {

        UserModel user = userService.getUserByUsername("prodOpsSpec");
        purchaseOrderModel.setCreator(user);

        StatusItemModel status = new StatusItemModel();
        Long statusItem = new Long(1);
        status.setIdStatusItem(statusItem);
        purchaseOrderModel.setStatusPO(status);

        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<MerekItemModel> listMerek = merekItemService.getMerekItemList();
        String nomorPurchaseOrder = purchaseOrderModel.getNomorPurchaseOrder();

        List<PurchaseOrderModel> purchaseOrderModels = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModels) {
            if (po.getNomorPurchaseOrder().equals(purchaseOrderModel.getNomorPurchaseOrder())) {
                String nomorPO = purchaseOrderModel.getNomorPurchaseOrder();
                model.addAttribute("nomorPO", nomorPO);
                return "purchase-order-already-exist";
            }
        }

        purchaseOrderService.addPurchaseOrder(purchaseOrderModel);

        ItemPOModel itemPOModel = new ItemPOModel();
        PurchaseOrderModel purchaseOrderModel1 = purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder);

        ArrayList<ItemPOModel> itemPO = new ArrayList<>();
        itemPO.add(itemPOModel);
        purchaseOrderModel1.setListItemPO(itemPO);
        itemPOModel.setPurchaseOrder(purchaseOrderModel1);

        model.addAttribute("itemPO", itemPOModel);
        model.addAttribute("nomorPO", nomorPurchaseOrder);
        model.addAttribute("listMerekItem", listMerek);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("purchaseOrder", purchaseOrderModel1);
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

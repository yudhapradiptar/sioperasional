package sistem.operasional.sioperasional.controller;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.OutletModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.OutletService;
import sistem.operasional.sioperasional.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/delivery-order")
public class DeliveryOrderController {
    @Autowired
    DeliveryOrderService deliveryOrderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    OutletService outletService;

    @RequestMapping("/")
    public String viewAllDeliveryOrder(Model model) {
        List<DeliveryOrderModel> listDeliveryOrder = deliveryOrderService.getDeliveryOrderList();

        model.addAttribute("listDeliveryOrder", listDeliveryOrder);

        return "list-delivery-order";
    }

    @RequestMapping(value = "/detail/{nomor}", method = RequestMethod.GET)
    public String viewDeliveryOrderByNomorDeliveryOrder(@PathVariable String nomor, Model model) {

        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        List<ItemModel> listItem = deliveryOrderModel.getListItem();

        String tanggalCreateFormatted = deliveryOrderModel.getTanggalCreate().toString();
        tanggalCreateFormatted = tanggalCreateFormatted.substring(0, tanggalCreateFormatted.length() - 10);

        if (deliveryOrderModel.getTanggalSubscribeEnd() != null) {
            String tanggalStartFormatted = deliveryOrderModel.getTanggalSubscribeStart().toString();
            tanggalStartFormatted = tanggalStartFormatted.substring(0, tanggalStartFormatted.length() - 10);
            String tanggalEndFormatted = deliveryOrderModel.getTanggalSubscribeEnd().toString();
            tanggalEndFormatted = tanggalEndFormatted.substring(0, tanggalEndFormatted.length() - 10);
            model.addAttribute("tanggalStartFormatted", tanggalStartFormatted);
            model.addAttribute("tanggalEndFormatted", tanggalEndFormatted);
        }

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("tanggalCreate", tanggalCreateFormatted);
        model.addAttribute("listItem", listItem);
        return "detail-delivery-order";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDeliveryOrderFormPage(Model model) {
        DeliveryOrderModel deliveryOrderModel = new DeliveryOrderModel();

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNull();

        ArrayList<ItemModel> listItemModels = new ArrayList<ItemModel>();
        listItemModels.add(new ItemModel());
        deliveryOrderModel.setListItem(listItemModels);

        OutletModel outletModel = new OutletModel();
        deliveryOrderModel.setOutlet(outletModel);
        List<OutletModel> outletModels = outletService.getOutletList();

        model.addAttribute("listOutlet", outletModels);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", itemModels);

        return "form-add-delivery-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDeliveryOrderSubmit(@ModelAttribute DeliveryOrderModel deliveryOrderModel,
            @ModelAttribute ItemModel itemModel, Model model) {

        DeliveryOrderModel deliveryOrderModel2 = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());
        if (deliveryOrderModel2 != null) {
            model.addAttribute("deliveryOrder", deliveryOrderModel);
            return "delivery-order-already-exist";
        }

        // DeliveryOrderModel deliveryOrderNow  = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());
        System.out.println("=========================================");
        System.out.println(deliveryOrderModel.getListItem());
        System.out.println(itemModel);

        for(ItemModel itemModel2: deliveryOrderModel.getListItem()) {
            System.out.println("--------------------------------");
            System.out.println(itemModel2.getIdItem());
            System.out.println(deliveryOrderModel.getTanggalCreate());
			itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
			itemModel2.setDeliveryOrder(deliveryOrderModel);
		}

        UserModel user = userService.getUserByUsername("prodOpsSpec");
        deliveryOrderModel.setCreator(user);

        deliveryOrderService.addDeliveryOrder(deliveryOrderModel);

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("namaOutlet", deliveryOrderModel.getOutlet().getNamaOutlet());
        model.addAttribute("listItem", deliveryOrderModel.getListItem());
        return "add-delivery-order";
    }

    @RequestMapping(value = "/set-tanggal-subscribe/{nomor}", method = RequestMethod.GET)
    public String addSubscribeDateFormPage(@PathVariable String nomor, Model model) {
        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        if (deliveryOrderModel.getSubscribed() == false) {
            model.addAttribute("deliveryOrder", deliveryOrderModel);
            return "cant-add-subscribe-date";
        }

        List<ItemModel> listItem = deliveryOrderModel.getListItem();

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", listItem);
        
        return "form-add-tanggal-subscribe";
    }

    @RequestMapping(value = "/set-tanggal-subscribe/{nomor}", method = RequestMethod.POST)
    public String addSubscribeDate(@PathVariable String nomor, @ModelAttribute DeliveryOrderModel deliveryOrderModel,
            Model model) {
        
        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderService.changeDeliveryOrder(deliveryOrderModel);
        model.addAttribute("deliveryOrder", newDeliveryOrderModel);

        String tanggalStartFormatted = deliveryOrderModel.getTanggalSubscribeStart().toString();
        tanggalStartFormatted = tanggalStartFormatted.substring(0, tanggalStartFormatted.length() - 10);
        String tanggalEndFormatted = deliveryOrderModel.getTanggalSubscribeEnd().toString();
        tanggalEndFormatted = tanggalEndFormatted.substring(0, tanggalEndFormatted.length() - 10);
        model.addAttribute("tanggalStartFormatted", tanggalStartFormatted);
        model.addAttribute("tanggalEndFormatted", tanggalEndFormatted);

        return "add-tanggal-subscribe";
    }

    @RequestMapping(value = "/update/{nomor}", method = RequestMethod.GET)
    public String updateFormPage(@PathVariable String nomor, Model model) {
        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        
        List<ItemModel> itemModels = deliveryOrderModel.getListItem();

        ArrayList<ItemModel> listItemModels = new ArrayList<ItemModel>();
        listItemModels.add(new ItemModel());
        deliveryOrderModel.setListItem(listItemModels);

        OutletModel outletModel = new OutletModel();
        deliveryOrderModel.setOutlet(outletModel);
        List<OutletModel> outletModels = outletService.getOutletList();

        model.addAttribute("listOutlet", outletModels);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", itemModels);
        
        return "form-update-delivery-order";
    }

    @RequestMapping(value = "/update/{nomor}", method = RequestMethod.POST)
    public String updateSubmit(@PathVariable String nomor, @ModelAttribute DeliveryOrderModel deliveryOrderModel,
            Model model) {
        
        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderService.changeDeliveryOrder(deliveryOrderModel);
        model.addAttribute("deliveryOrder", newDeliveryOrderModel);

        List<ItemModel> listItem = deliveryOrderModel.getListItem();
        model.addAttribute("listItem", listItem);

        System.out.println("=========================================");
        System.out.println(deliveryOrderModel.getListItem());

        for(ItemModel itemModel2: deliveryOrderModel.getListItem()) {
            System.out.println("==============mau set null ====================");
            System.out.println();    
			itemModel2.setDeliveryOrder(null);
		}

        return "detail-delivery-order";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DeliveryOrderModel deliveryOrderModel, BindingResult bindingResult, Model model) {
		if (deliveryOrderModel.getListItem() == null) {
            deliveryOrderModel.setListItem(new ArrayList<ItemModel>());
        }
        deliveryOrderModel.getListItem().add(new ItemModel());
        model.addAttribute("deliveryOrder", deliveryOrderModel);

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNull();
        model.addAttribute("listItem", itemModels);

        List<OutletModel> outletModels = outletService.getOutletList();
        model.addAttribute("listOutlet", outletModels);

		return "form-add-delivery-order";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DeliveryOrderModel deliveryOrderModel, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        deliveryOrderModel.getListItem().remove(rowId.intValue());
        model.addAttribute("deliveryOrder", deliveryOrderModel);

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNull();
        model.addAttribute("listItem", itemModels);

        List<OutletModel> outletModels = outletService.getOutletList();
        model.addAttribute("listOutlet", outletModels);
        
	    return "form-add-delivery-order";
    }
}
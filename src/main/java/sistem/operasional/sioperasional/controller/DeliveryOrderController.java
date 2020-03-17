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

        // List<ItemModel> itemModels = itemService.getItemList();
        // model.addAttribute("listItem", itemModels);

        return "list-delivery-order";
    }

    @RequestMapping(value = "/detail/{nomor}", method = RequestMethod.GET)
    public String viewDeliveryOrderByNomorDeliveryOrder(@PathVariable String nomor, Model model) {

        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        
        List<ItemModel> listItem = deliveryOrderModel.getlistItem();

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", listItem);
        return "detail-delivery-order";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDeliveryOrderFormPage(Model model) {
        DeliveryOrderModel deliveryOrderModel = new DeliveryOrderModel();

        List<ItemModel> itemModels = itemService.getItemList();

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

    @RequestMapping(value="/add", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DeliveryOrderModel deliveryOrderModel, BindingResult bindingResult, Model model) {
		if (deliveryOrderModel.getlistItem() == null) {
            deliveryOrderModel.setListItem(new ArrayList<ItemModel>());
        }
        deliveryOrderModel.getlistItem().add(new ItemModel());
        model.addAttribute("deliveryOrder", deliveryOrderModel);

        List<ItemModel> itemModels = itemService.getItemList();
        model.addAttribute("listItem", itemModels);

        List<OutletModel> outletModels = outletService.getOutletList();
        model.addAttribute("listOutlet", outletModels);

		return "form-add-delivery-order";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DeliveryOrderModel deliveryOrderModel, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        deliveryOrderModel.getlistItem().remove(rowId.intValue());
        model.addAttribute("deliveryOrderModel", deliveryOrderModel);

        List<ItemModel> itemModels = itemService.getItemList();
        model.addAttribute("listItem", itemModels);

        List<OutletModel> outletModels = outletService.getOutletList();
        model.addAttribute("listOutlet", outletModels);
        
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

        UserModel user = userService.getUserByUsername("prodOpsSpec");
        deliveryOrderModel.setCreator(user);

        deliveryOrderService.addDeliveryOrder(deliveryOrderModel);

        String nomorDeliveryOrder = deliveryOrderModel.getNomorDeliveryOrder();
        model.addAttribute("nomorDeliveryOrder", nomorDeliveryOrder);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        return "add-delivery-order";
    }

    @RequestMapping(value = "/set-tanggal-subscribe/{nomor}", method = RequestMethod.GET)
    public String addSubscribeDateFormPage(@PathVariable String nomor, Model model) {
        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        
        List<ItemModel> listItem = deliveryOrderModel.getlistItem();

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", listItem);
        
        return "form-add-tanggal-subscribe";
    }

    @RequestMapping(value = "/set-tanggal-subscribe/{nomor}", method = RequestMethod.POST)
    public String addSubscribeDate(@PathVariable String nomor, @ModelAttribute DeliveryOrderModel deliveryOrderModel,
            Model model) {
        
        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderService.changeDeliveryOrder(deliveryOrderModel);
        model.addAttribute("deliveryOrder", newDeliveryOrderModel);

        return "detail-delivery-order";
    }

}
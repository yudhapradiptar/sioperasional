package sistem.operasional.sioperasional.controller;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.OutletModel;
import sistem.operasional.sioperasional.model.StatusItemModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.OutletService;
import sistem.operasional.sioperasional.service.StatusItemService;
import sistem.operasional.sioperasional.service.UserService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

    @Autowired
    StatusItemService statusItemService;

    @RequestMapping("")
    public String viewAllDeliveryOrder(Model model) {
        List<DeliveryOrderModel> listDeliveryOrder = deliveryOrderService.getDeliveryOrderList();

        model.addAttribute("listDeliveryOrder", listDeliveryOrder);

        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "list-delivery-order";
    }

    @RequestMapping("/daftar-delivery-order-set-tanggal")
    public String viewDeliveryOrderBundlingNotSetTanggal(Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
        List<DeliveryOrderModel> listDeliveryOrderBundlingNotSetTanggalYet = deliveryOrderService.getDeliveryOrderListBySubscribedAndTanggalSubcribeStartNull();
        model.addAttribute("listDeliveryOrder", listDeliveryOrderBundlingNotSetTanggalYet);

        return "list-delivery-order-set-tanggal";
    }

    @RequestMapping(value = "/detail/{nomor}", method = RequestMethod.GET)
    public String viewDeliveryOrderByNomorDeliveryOrder(@PathVariable String nomor, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);
        List<ItemModel> listItem = deliveryOrderModel.getListItem();

        String tanggalCreateFormatted = deliveryOrderModel.getTanggalCreate().toString();
        tanggalCreateFormatted = tanggalCreateFormatted.substring(0, tanggalCreateFormatted.length() - 14);

        if (deliveryOrderModel.getTanggalSubscribeEnd() != null) {
            String tanggalStartFormatted = deliveryOrderModel.getTanggalSubscribeStart().toString();
            tanggalStartFormatted = tanggalStartFormatted.substring(0, tanggalStartFormatted.length() - 14);
            String tanggalEndFormatted = deliveryOrderModel.getTanggalSubscribeEnd().toString();
            tanggalEndFormatted = tanggalEndFormatted.substring(0, tanggalEndFormatted.length() - 14);
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
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
        DeliveryOrderModel deliveryOrderModel = new DeliveryOrderModel();

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNullAndNotRusak();

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
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        UserModel user = userService.getUserCurrentLoggedIn();
        deliveryOrderModel.setCreator(user);
        deliveryOrderModel.setNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder().replaceAll("/", "-"));

        DeliveryOrderModel deliveryOrderModel2 = deliveryOrderService
                .getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());
        if (deliveryOrderModel2 != null) {
            model.addAttribute("nomorDeliveryOrder", deliveryOrderModel.getNomorDeliveryOrder());
            return "delivery-order-already-exist";
        }
        
        StatusItemModel statusItemModel = statusItemService.getStatusItemByIdStatusItem((long) 2);
        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
            itemModel2.setDeliveryOrder(deliveryOrderModel);
            itemModel2.setStatusItem(statusItemModel);
        }

        deliveryOrderService.addDeliveryOrder(deliveryOrderModel);

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        // model.addAttribute("tanggal", deliveryOrderModel.getTanggalCreate());
        model.addAttribute("namaOutlet", deliveryOrderModel.getOutlet().getNamaOutlet());
        model.addAttribute("listItem", deliveryOrderModel.getListItem());
        return "add-delivery-order";
    }

    @RequestMapping(value = "/set-tanggal-subscribe/{nomor}", method = RequestMethod.GET)
    public String addSubscribeDateFormPage(@PathVariable String nomor, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
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
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderService.changeDeliveryOrder(deliveryOrderModel);
        model.addAttribute("deliveryOrder", newDeliveryOrderModel);

        String tanggalStartFormatted = deliveryOrderModel.getTanggalSubscribeStart().toString();
        tanggalStartFormatted = tanggalStartFormatted.substring(0, tanggalStartFormatted.length() - 14);
        String tanggalEndFormatted = deliveryOrderModel.getTanggalSubscribeEnd().toString();
        tanggalEndFormatted = tanggalEndFormatted.substring(0, tanggalEndFormatted.length() - 14);
        model.addAttribute("tanggalStartFormatted", tanggalStartFormatted);
        model.addAttribute("tanggalEndFormatted", tanggalEndFormatted);

        return "add-tanggal-subscribe";
    }

    @RequestMapping(value = "/update/{nomor}", method = RequestMethod.GET)
    public String updateFormPage(@PathVariable String nomor, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        DeliveryOrderModel deliveryOrderModel = deliveryOrderService.getDeliveryOrderByNomorDeliveryOrder(nomor);

        List<ItemModel> itemModelsNull = itemService.getItemListAvailable(deliveryOrderModel.getNomorDeliveryOrder());
        List<ItemModel> itemModelsCurrent = deliveryOrderModel.getListItem();

        List<ItemModel> listItem = new ArrayList<>();
        listItem.addAll(itemModelsNull);
        listItem.addAll(itemModelsCurrent);

        ArrayList<ItemModel> listItemModels = new ArrayList<ItemModel>();
        listItemModels.add(new ItemModel());
        deliveryOrderModel.setListItem(listItemModels);

        OutletModel outletModel = new OutletModel();
        deliveryOrderModel.setOutlet(outletModel);
        List<OutletModel> outletModels = outletService.getOutletList();

        model.addAttribute("listOutlet", outletModels);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("itemModelsCurrent", itemModelsCurrent);
        model.addAttribute("listItem", listItem);

        return "form-update-delivery-order";
    }

    @RequestMapping(value = "/update/{nomor}", method = RequestMethod.POST)
    public String updateSubmit(@PathVariable String nomor, @ModelAttribute DeliveryOrderModel deliveryOrderModel,
            Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        DeliveryOrderModel deliveryOrderNow = deliveryOrderService
                .getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());

        StatusItemModel statusItemModel_1 = statusItemService.getStatusItemByIdStatusItem((long) 1);
        for(ItemModel itemModel3: deliveryOrderNow.getListItem()) {
            itemModel3.setDeliveryOrder(null);
            itemModel3.setTanggalKeluar(null);
            itemModel3.setStatusItem(statusItemModel_1);
        }

        StatusItemModel statusItemModel_2 = statusItemService.getStatusItemByIdStatusItem((long) 2);
        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            if (itemModel2 == null) {
            } else {
                itemModel2.setDeliveryOrder(deliveryOrderModel);
                itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
                itemModel2.setStatusItem(statusItemModel_2);
            }
        }

        DeliveryOrderModel newDeliveryOrderModel = deliveryOrderService.changeDeliveryOrder(deliveryOrderModel);

        List<ItemModel> listItem = deliveryOrderModel.getListItem();

        model.addAttribute("deliveryOrder", newDeliveryOrderModel);
        model.addAttribute("listItem", listItem);
        return "update-delivery-oder";
    }

    @RequestMapping(value = "/addwithtxt", method = RequestMethod.GET)
    public String addDeliveryOrderFormPageWithTxt(Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
        DeliveryOrderModel deliveryOrderModel = new DeliveryOrderModel();

        List<ItemModel> itemModels = itemService.geItemListByTanggalKeluarNullAndNotRusak();

        ArrayList<ItemModel> listItemModels = new ArrayList<ItemModel>();
        listItemModels.add(new ItemModel());
        deliveryOrderModel.setListItem(listItemModels);

        OutletModel outletModel = new OutletModel();
        deliveryOrderModel.setOutlet(outletModel);
        List<OutletModel> outletModels = outletService.getOutletList();

        model.addAttribute("listOutlet", outletModels);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("listItem", itemModels);

        return "form-add-delivery-order-with-txt";
    }

    @RequestMapping(value = "/addwithtxt", method = RequestMethod.POST)
    public String handleFileTxt(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
            @ModelAttribute DeliveryOrderModel deliveryOrderModel, @ModelAttribute ItemModel itemModel, Model model)
            throws IOException, ParseException {

        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        
        Date date;

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String myString = IOUtils.toString(stream, "UTF-8");

        int startNomorDO = myString.indexOf("INV");
        String nomorDeliveryOrder = myString.substring(startNomorDO, startNomorDO + 28);
        nomorDeliveryOrder = nomorDeliveryOrder.replaceAll("/", "-");

        // int startTokopediaLink = myString.indexOf("https://www.tokopedia.com/");
        // String tokopediaLinkRaw = myString.substring(startTokopediaLink);
        // String[] tokopediaArray = tokopediaLinkRaw.split(" ");
        // String tokopediaLink = tokopediaArray[0].substring(0, tokopediaArray[0].length()-3);

        String tanggal = myString.substring(0, 10);
        date = new SimpleDateFormat("MM/dd/yyyy").parse(tanggal);

        // batas
        DeliveryOrderModel deliveryOrderModel2 = deliveryOrderService
                .getDeliveryOrderByNomorDeliveryOrder(nomorDeliveryOrder);
        if (deliveryOrderModel2 != null) {
            model.addAttribute("nomorDeliveryOrder", nomorDeliveryOrder);
            return "delivery-order-already-exist";
        }

        UserModel user = userService.getUserCurrentLoggedIn();
        deliveryOrderModel.setCreator(user);
        deliveryOrderModel.setTanggalCreate(date);
        deliveryOrderModel.setNomorDeliveryOrder(nomorDeliveryOrder);

        StatusItemModel statusItemModel = statusItemService.getStatusItemByIdStatusItem((long) 2);
        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
            itemModel2.setDeliveryOrder(deliveryOrderModel);
            itemModel2.setStatusItem(statusItemModel);
        }

        deliveryOrderService.addDeliveryOrder(deliveryOrderModel);

        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("namaOutlet", deliveryOrderModel.getOutlet().getNamaOutlet());
        model.addAttribute("listItem", deliveryOrderModel.getListItem());

        model.addAttribute("date", date);
        model.addAttribute("nomorDeliveryOrder", nomorDeliveryOrder);
        // model.addAttribute("tanggal", tanggal);
        // model.addAttribute("tokopediaLink", tokopediaLink);


        return "add-delivery-order-with-txt";
    }
}
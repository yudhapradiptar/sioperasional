package sistem.operasional.sioperasional.controller;

import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.OutletModel;
import sistem.operasional.sioperasional.model.UserModel;
import sistem.operasional.sioperasional.service.DeliveryOrderService;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.OutletService;
import sistem.operasional.sioperasional.service.UserService;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import antlr.StringUtils;

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

import com.itextpdf.text.pdf.codec.Base64;

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

        UserModel user = userService.getUserCurrentLoggedIn();
        deliveryOrderModel.setCreator(user);
        DeliveryOrderModel deliveryOrderModel2 = deliveryOrderService
                .getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());
        if (deliveryOrderModel2 != null) {
            model.addAttribute("deliveryOrder", deliveryOrderModel);
            return "delivery-order-already-exist";
        }

        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            // System.out.println("--------------------------------");
            // System.out.println(itemModel2.getIdItem());
            // System.out.println(deliveryOrderModel.getTanggalCreate());
            itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
            itemModel2.setDeliveryOrder(deliveryOrderModel);
            itemModel2.setChecked(true);
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

        List<ItemModel> itemModelsNull = itemService.getItemListAvailable(deliveryOrderModel.getNomorDeliveryOrder());
        List<ItemModel> itemModelsCurrent = deliveryOrderModel.getListItem();

        ArrayList<ItemModel> listItemModels = new ArrayList<ItemModel>();
        listItemModels.add(new ItemModel());
        deliveryOrderModel.setListItem(listItemModels);

        OutletModel outletModel = new OutletModel();
        deliveryOrderModel.setOutlet(outletModel);
        List<OutletModel> outletModels = outletService.getOutletList();

        model.addAttribute("listOutlet", outletModels);
        model.addAttribute("deliveryOrder", deliveryOrderModel);
        model.addAttribute("itemModelsCurrent", itemModelsCurrent);
        model.addAttribute("listItem", itemModelsNull);

        return "form-update-delivery-order";
    }

    @RequestMapping(value = "/update/{nomor}", method = RequestMethod.POST)
    public String updateSubmit(@PathVariable String nomor, @ModelAttribute DeliveryOrderModel deliveryOrderModel,
            Model model) {

        DeliveryOrderModel deliveryOrderNow = deliveryOrderService
                .getDeliveryOrderByNomorDeliveryOrder(deliveryOrderModel.getNomorDeliveryOrder());

        System.out.println("item DO Now");
        System.out.println(deliveryOrderNow.getListItem());

        for (ItemModel itemModel3 : deliveryOrderNow.getListItem()) {
            System.out.println("==============mau set NUll ====================");
            itemModel3.setDeliveryOrder(null);
            itemModel3.setTanggalKeluar(null);
        }

        System.out.println("================= ITEM BARU ====================");
        System.out.println(deliveryOrderModel.getListItem());

        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            if (itemModel2 == null) {
                // System.out.println("=============null==================");
            } else {
                // System.out.println("==============NOT NULL===================");
                itemModel2.setDeliveryOrder(deliveryOrderModel);
                itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
                itemModel2.setChecked(true);
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

        for (ItemModel itemModel2 : deliveryOrderModel.getListItem()) {
            itemModel2.setTanggalKeluar(deliveryOrderModel.getTanggalCreate());
            itemModel2.setDeliveryOrder(deliveryOrderModel);
            itemModel2.setChecked(true);
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

    @RequestMapping(value = "/addwithpdf", method = RequestMethod.GET)
    public String addDeliveryOrderFormPageWithPDF(Model model) {
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

        return "form-add-delivery-order-with-pdf";
    }

    @RequestMapping(value = "/addwithpdf", method = RequestMethod.POST)
    public String handleFilePDF(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
            Model model) throws IOException, ParseException {

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String myString = IOUtils.toString(stream, "UTF-8");

        File convFile = new File( file.getOriginalFilename());
        file.transferTo(convFile);

        // File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        // file.transferTo(convFile);

        // // File f = new File(filename);
        String parsedText;
        PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(convFile, "r"));
        parser.parse();

        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);

        // String data = "";
        // Scanner myReader = new Scanner(convFile);
        // while (myReader.hasNextLine()) {
        //     data += myReader.nextLine();
        //     System.out.println("-------------------------------------print-------------------------------------");
        //     System.out.println(data);
        // }
        // myReader.close();

        // String nomorDeliveryOrder = myString.substring(40, 68);
        
        // String tanggal = myString.substring(0, 10);
        // date = new SimpleDateFormat("dd/MM/yyyy").parse(tanggal);  
        
        
        model.addAttribute("nomorDeliveryOrder", parsedText);

		return "add-delivery-order-with-pdf";
    }
}
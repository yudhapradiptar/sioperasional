package sistem.operasional.sioperasional.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.model.*;
import sistem.operasional.sioperasional.repository.ItemPODB;
import sistem.operasional.sioperasional.repository.PurchaseOrderDB;
import sistem.operasional.sioperasional.service.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    JenisItemService jenisItemService;

    @Autowired
    KategoriItemService kategoriItemService;

    @Autowired
    private ServletContext context;

    private static Logger logger = LogManager.getLogger(PurchaseOrderController.class);

    private static final String filePath = System.getProperty("user.home"+"/");

    private static final String image = "post.jpg";


    @RequestMapping("")
    public String getAll(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        List<PurchaseOrderModel> listPO = purchaseOrderDB.findAll(Sort.by(Sort.Direction.ASC, "nomorPurchaseOrder"));
        model.addAttribute("listPO", listPO);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "list-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPurchaseOrderPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

        List<VendorModel> listVendor = vendorService.getVendorList();
        List<ItemPOModel> itemPOModelList = new ArrayList<ItemPOModel>();
        itemPOModelList.add(new ItemPOModel());

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("purchaseOrder", purchaseOrderModel);
        model.addAttribute("listItem", itemPOModelList);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "form-add-purchase-order";

    }

    @RequestMapping(value="/add/item/{nomorPurchaseOrder}", method = RequestMethod.POST, params= {"addMore"})
    public String addMoreForm(@AuthenticationPrincipal UserDetails currentUser,
                              @PathVariable("nomorPurchaseOrder") String nomorPO,
                              @ModelAttribute PurchaseOrderModel purchaseOrder,
                              BindingResult bindingResult, Model model) {

        if(purchaseOrder.getListItemPO() == null) {
            purchaseOrder.setListItemPO(new ArrayList<ItemPOModel>());
        }

        purchaseOrder.getListItemPO().add(new ItemPOModel());
        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<JenisItemModel> listJenis = jenisItemService.getJenisItemList();

        model.addAttribute("listJenisItem", listJenis);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("nomorPO", nomorPO);
        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "form-add-item-purchase-order";
    }

    @RequestMapping(value="/add/item/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String addItems(@AuthenticationPrincipal UserDetails currentUser,
                           @PathVariable("nomorPurchaseOrder") String nomorPO,
                           @ModelAttribute PurchaseOrderModel purchaseOrder,
                           Model model) {

        HashMap<String, ItemPOModel> itemPOMap = new HashMap<>();
        PurchaseOrderModel purchaseOrder1 = new PurchaseOrderModel();
        for (PurchaseOrderModel po : purchaseOrderService.getAll()) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(purchaseOrder.getNomorPurchaseOrder())){
                purchaseOrder1 = po;
            }
        }
        List<ItemPOModel> oldItemPOModel = itemPOService.getAllItemPO();
        for (ItemPOModel item : oldItemPOModel) {
            if(item.getPurchaseOrder().getNomorPurchaseOrder().equalsIgnoreCase(purchaseOrder1.getNomorPurchaseOrder()))
            itemPODB.delete(item);
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
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "add-purchase-order";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPurchaseOrderSubmit(@AuthenticationPrincipal UserDetails currentUser,
                                         @ModelAttribute PurchaseOrderModel purchaseOrderModel,
                                         Model model) {


        UserModel user = userService.getUserCurrentLoggedIn();
        purchaseOrderModel.setCreator(user);

        purchaseOrderModel.setStatusPO("Open");

        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<JenisItemModel> listJenis = jenisItemService.getJenisItemList();
        String nomorPurchaseOrder = purchaseOrderModel.getNomorPurchaseOrder();

        List<PurchaseOrderModel> purchaseOrderModels = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModels) {
            if (po.getNomorPurchaseOrder().equals(purchaseOrderModel.getNomorPurchaseOrder())) {
                String nomorPO = purchaseOrderModel.getNomorPurchaseOrder();
                model.addAttribute("nomorPO", nomorPO);
                model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
                return "purchase-order-already-exist";
            }
        }

        purchaseOrderService.addPurchaseOrder(purchaseOrderModel);

        ItemPOModel itemPOModel = new ItemPOModel();
        PurchaseOrderModel purchaseOrderModel1 = purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder).get();

        ArrayList<ItemPOModel> itemPO = new ArrayList<>();
        itemPO.add(itemPOModel);
        purchaseOrderModel1.setListItemPO(itemPO);
        itemPOModel.setPurchaseOrder(purchaseOrderModel1);

        model.addAttribute("itemPO", itemPOModel);
        model.addAttribute("nomorPO", nomorPurchaseOrder);
        model.addAttribute("listJenisItem", listJenis);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("purchaseOrder", purchaseOrderModel1);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "form-add-item-purchase-order";
    }

    @RequestMapping(value = "/approve/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String approvePurchaseOrder(@PathVariable("nomorPurchaseOrder") String nomorPurchaseOrder,
                                       @ModelAttribute PurchaseOrderModel purchaseOrderModel){
        PurchaseOrderModel purchaseOrder = new PurchaseOrderModel();
        List<PurchaseOrderModel> purchaseOrderModelList = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModelList) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(nomorPurchaseOrder)){
                purchaseOrder = po;
            }
        }
        purchaseOrder.setDisetujui(true);
        purchaseOrderDB.save(purchaseOrder);

        return "redirect:/purchase-order/";
    }

    @RequestMapping(value = "/close/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String closePurchaseOrder(@PathVariable("nomorPurchaseOrder") String nomorPurchaseOrder,
                                       @ModelAttribute PurchaseOrderModel purchaseOrderModel){
        PurchaseOrderModel purchaseOrder = new PurchaseOrderModel();
        List<PurchaseOrderModel> purchaseOrderModelList = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModelList) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(nomorPurchaseOrder)){
                purchaseOrder = po;
            }
        }

        logger.info("Before:" + purchaseOrder.getStatusPO());
        purchaseOrder.setStatusPO("Closed");
        purchaseOrderDB.save(purchaseOrder);
        logger.info("After:" + purchaseOrder.getStatusPO());
        return "redirect:/purchase-order/";
    }

    @RequestMapping(value = "/detail/{nomorPurchaseOrder}", method = RequestMethod.GET)
    public String detailPurchaseOrder(@AuthenticationPrincipal UserDetails currentUser,
                                      @PathVariable("nomorPurchaseOrder") String nomorPurchaseOrder,
                                       Model model){
        PurchaseOrderModel purchaseOrder = new PurchaseOrderModel();
        List<PurchaseOrderModel> purchaseOrderModelList = purchaseOrderService.getAll();
        for (PurchaseOrderModel po:purchaseOrderModelList) {
            if(po.getNomorPurchaseOrder().equalsIgnoreCase(nomorPurchaseOrder)){
                purchaseOrder = po;
            }
        }

        List<ItemPOModel> listItemPO = purchaseOrder.getListItemPO();

        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("listItemPO", listItemPO);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "detail-purchase-order";
    }

    @RequestMapping(value = "/update/{nomorPurchaseOrder}", method = RequestMethod.GET)
    public String updateFormPage(@PathVariable String nomorPurchaseOrder, Model model) {
        PurchaseOrderModel purchaseOrderModel = purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder).get();

        List<ItemPOModel> listItemPO = purchaseOrderModel.getListItemPO();
        List<VendorModel> listVendor = vendorService.getVendorList();
        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<JenisItemModel> listJenis = jenisItemService.getJenisItemList();

        model.addAttribute("purchaseOrder", purchaseOrderModel);
        model.addAttribute("listVendor", listVendor);
        model.addAttribute("listItemPO", listItemPO);
        model.addAttribute("listJenisItem", listJenis);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "form-update-purchase-order";
    }

    @RequestMapping(value = "/update/{nomorPurchaseOrder}", method = RequestMethod.POST)
    public String updateFormSubmit(@PathVariable String nomorPurchaseOrder,  @ModelAttribute PurchaseOrderModel purchaseOrder, Model model) {

        PurchaseOrderModel newPurchaseOrder = purchaseOrderService.changePurchaseOrder(purchaseOrder);

        model.addAttribute("purchaseOrder", newPurchaseOrder);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "update-purchase-oder";
    }

    @RequestMapping(value="/update/edit-item/{nomorPurchaseOrder}", method = RequestMethod.POST, params= {"addMore"})
    public String addMoreFormUpdate(@AuthenticationPrincipal UserDetails currentUser,
                              @PathVariable("nomorPurchaseOrder") String nomorPO,
                              @ModelAttribute PurchaseOrderModel purchaseOrder,
                              BindingResult bindingResult, Model model) {

        if(purchaseOrder.getListItemPO() == null) {
            purchaseOrder.setListItemPO(new ArrayList<ItemPOModel>());
        }

        List<KategoriItemModel> listKategoriItem = kategoriItemService.getKategoriItemList();
        List<JenisItemModel> listJenis = jenisItemService.getJenisItemList();
        List<ItemPOModel> listItemPO = purchaseOrder.getListItemPO();

        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("listItemPO", listItemPO);
        model.addAttribute("listJenisItem", listJenis);
        model.addAttribute("listKategoriItem", listKategoriItem);
        model.addAttribute("nomorPO", nomorPO);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());
        return "form-add-item-purchase-order";
    }

    @RequestMapping(value="/download/{nomorPurchaseOrder}", method= RequestMethod.POST)
    public void createPdf(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable("nomorPurchaseOrder") String nomorPurchaseOrder)
    {

        PurchaseOrderModel purchaseOrderModel =
                purchaseOrderService.getPurchaseOrderByNomorPurchaseOrder(nomorPurchaseOrder).get();
        logger.info("MASUK KEK DOWNLOAD METHOD");
        boolean isFlag= purchaseOrderService.createPdf(purchaseOrderModel,context,request,response);
        logger.info("CREATE PDF METHOD UDH SELESAI, HASIL BOOLEANNYA: "+ isFlag);
        String namaFile = nomorPurchaseOrder+".pdf";
        if(isFlag)
        {
            logger.info("MASUK KE IF DI METHOD CREATE PDF");
            String fullPath = filePath+namaFile;
            fileDownload(fullPath,response,namaFile);
        }
    }

    private void fileDownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        logger.info("======================="+fullPath+"=======================");
        final int BUFFER_SIZE = 4096;
        if(file.exists())
        {
            try
            {
                logger.info("============= START DOWNLOAD FILE =============");
                FileInputStream fis= new FileInputStream(file);
                String mimeType= context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment;fileName="+fileName);

                OutputStream os= response.getOutputStream();
                byte[] buffer= new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while((bytesRead=fis.read(buffer))!=-1)
                {
                    os.write(buffer, 0, bytesRead);
                }

                fis.close();
                os.close();
                file.delete();
            }
            catch(Exception ex)
            {
                logger.info("ERROR DI FILE DOWNLOAD");
                logger.info(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}

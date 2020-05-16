package sistem.operasional.sioperasional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.VendorModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.repository.VendorDB;
import sistem.operasional.sioperasional.service.UserService;
import sistem.operasional.sioperasional.service.VendorService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    VendorService vendorService;

    @Autowired
    VendorDB vendorDB;

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String viewAllVendor(Model model) {
        List<VendorModel> listVendor = vendorService.getVendorList();

        model.addAttribute("listVendor", listVendor);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "list-vendor";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addVendorFormPage(Model model) {
        VendorModel vendorModel = new VendorModel();

        model.addAttribute("vendor", vendorModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "form-create-vendor";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addVendorSubmit(@ModelAttribute VendorModel vendorModel, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        List<VendorModel> vendorModelList = vendorService.getVendorList();
        for (VendorModel vendor:vendorModelList) {
            if (vendor.getNamaVendor().equals(vendorModel.getNamaVendor())) {
                model.addAttribute("vendor", vendorModel);
                return "vendor-already-exist";
            }
        }

        vendorService.addVendor(vendorModel);
        model.addAttribute("vendor", vendorModel);
        return "add-vendor";
    }

    @RequestMapping(value = "/delete/{idVendor}", method = RequestMethod.POST)
    public String deleteVendor(@PathVariable("idVendor") Long idVendor, Model model){
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        VendorModel deletedVendor = vendorDB.findById(idVendor).get();
        List<VendorModel> vendorModelList = vendorService.getVendorList();

        model.addAttribute("vendor", deletedVendor);
        vendorService.deleteVendor(deletedVendor);

        for (VendorModel vendor:vendorModelList) {
            if(vendor.getIdVendor().equals(vendorService.getVendorByIdVendor(idVendor))){
                return "fail-delete-vendor";
            }
        }

        return "success-delete-vendor";
    }

}
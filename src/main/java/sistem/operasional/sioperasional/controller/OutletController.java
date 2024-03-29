package sistem.operasional.sioperasional.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import sistem.operasional.sioperasional.model.DeliveryOrderModel;
import sistem.operasional.sioperasional.model.JenisOutletModel;
import sistem.operasional.sioperasional.model.OutletModel;
import sistem.operasional.sioperasional.model.TrainingModel;
import sistem.operasional.sioperasional.service.JenisOutletService;
import sistem.operasional.sioperasional.service.OutletService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.service.UserService;

@Controller
@RequestMapping("/outlet")
public class OutletController {
    @Autowired
    OutletService outletService;

    @Autowired
    JenisOutletService jenisOutletService;

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String viewAllOutlet(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        List<OutletModel> listOutlet = outletService.getOutletList();

        model.addAttribute("listOutlet", listOutlet);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "list-outlet";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String viewOutletByIdOutlet(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser, Model model) {

        OutletModel outletModel = outletService.getOutletByIdOutlet(id).get();
        List<DeliveryOrderModel> listDeliveryOrderModels = outletModel.getListDeliveryOrderOutlet();
        List<TrainingModel> listTrainingModels = outletModel.getListTrainingOutlet();

        model.addAttribute("outlet", outletModel);
        model.addAttribute("listDeliveryOrder", listDeliveryOrderModels);
        model.addAttribute("listTraining", listTrainingModels);
        model.addAttribute("role", userService.getUserByUsername(currentUser.getUsername()).getRole().getNamaRole());

        return "detail-outlet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOutletFormPage(Model model) {
        OutletModel outletModel = new OutletModel();

        JenisOutletModel jenisOutletModel = new JenisOutletModel();
        outletModel.setJenisOutlet(jenisOutletModel);
        List<JenisOutletModel> jenisOutletModels = jenisOutletService.getJenisOutletList();

        model.addAttribute("jenisOutlet", jenisOutletModels);
        model.addAttribute("outlet", outletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "form-add-outlet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOutletSubmit(@ModelAttribute OutletModel outletModel, Model model) {

        outletModel.setAktif(true);
        outletService.addOutlet(outletModel);

        model.addAttribute("outlet", outletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "add-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateOutletFormPage(@PathVariable Long id, Model model) {
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        OutletModel outletModel = outletService.getOutletByIdOutlet(id).get();

        List<JenisOutletModel> jenisOutletModels = jenisOutletService.getJenisOutletList();

        model.addAttribute("jenisOutlet", jenisOutletModels);
        model.addAttribute("outlet", outletModel);

//        String role = userService.getUserCurrentLoggedIn().getRole().getNamaRole();

        return "form-update-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateOutletSubmit(@PathVariable Long id, @ModelAttribute OutletModel outletModel, Model model) {

        OutletModel newOutletModel = outletService.changeOutlet(outletModel);

        model.addAttribute("outlet", newOutletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "update-outlet";
    }

}
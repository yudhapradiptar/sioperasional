package sistem.operasional.sioperasional.controller;

import sistem.operasional.sioperasional.model.JenisOutletModel;
import sistem.operasional.sioperasional.model.OutletModel;
import sistem.operasional.sioperasional.service.JenisOutletService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistem.operasional.sioperasional.service.UserService;

@Controller
@RequestMapping("/jenis-outlet")
public class JenisOutletController {
    @Autowired
    JenisOutletService jenisOutletService;

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String viewAllJenisOutlet(Model model) {
        List<JenisOutletModel> listJenisOutlet = jenisOutletService.getJenisOutletList();

        model.addAttribute("listJenisOutlet", listJenisOutlet);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "list-jenis-outlet";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String viewJenisOutletByIdOutlet(@PathVariable Long id, Model model) {

        JenisOutletModel jenisOutletModel = jenisOutletService.getJenisOutletById(id).get();

        List<OutletModel> listOutlet = jenisOutletModel.getListOutlet();
        
        model.addAttribute("jenisOutlet", jenisOutletModel);
        model.addAttribute("listOutlet", listOutlet);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "detail-jenis-outlet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addJenisOutletFormPage(Model model) {
        JenisOutletModel jenisOutletModel = new JenisOutletModel();

        model.addAttribute("jenisOutlet", jenisOutletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "form-add-jenis-outlet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addJenisOutletSubmit(@ModelAttribute JenisOutletModel jenisOutletModel, Model model) {

        jenisOutletService.addJenisOutlet(jenisOutletModel);

        model.addAttribute("jenisOutlet", jenisOutletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "add-jenis-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateJenisOutletFormPage(@PathVariable Long id, Model model) {
        JenisOutletModel jenisOutletModel = jenisOutletService.getJenisOutletById(id).get();
        
        model.addAttribute("jenisOutlet", jenisOutletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());

        return "form-update-jenis-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateJenisOutletSubmit(@PathVariable Long id, @ModelAttribute JenisOutletModel jenisOutletModel, Model model) {

        JenisOutletModel newJenisOutletModel = jenisOutletService.changeJenisOutlet(jenisOutletModel);

        model.addAttribute("jenisOutlet", newJenisOutletModel);
        model.addAttribute("role", userService.getUserCurrentLoggedIn().getRole().getNamaRole());
        return "update-jenis-outlet";
    }
}
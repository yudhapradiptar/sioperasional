package sistem.operasional.sioperasional.controller;

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

@Controller
@RequestMapping("/outlet")
public class OutletController {
    @Autowired
    OutletService outletService;

    @Autowired
    JenisOutletService jenisOutletService;

    @RequestMapping("/")
    public String viewAllOutlet(Model model) {
        List<OutletModel> listOutlet = outletService.getOutletList();

        model.addAttribute("listOutlet", listOutlet);

        return "list-outlet";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String viewOutletByIdOutlet(@PathVariable Long id, Model model) {

        OutletModel outletModel = outletService.getOutletByIdOutlet(id).get();
        List<DeliveryOrderModel> listDeliveryOrderModels = outletModel.getListDeliveryOrderOutlet();
        List<TrainingModel> listTrainingModels = outletModel.getListTrainingOutlet();

        model.addAttribute("outlet", outletModel);
        model.addAttribute("listDeliveryOrder", listDeliveryOrderModels);
        model.addAttribute("listTraining", listTrainingModels);

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

        return "form-add-outlet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOutletSubmit(@ModelAttribute OutletModel outletModel, Model model) {

        outletModel.setAktif(true);
        outletService.addOutlet(outletModel);

        model.addAttribute("outlet", outletModel);
        return "add-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateOutletFormPage(@PathVariable Long id, Model model) {
        OutletModel outletModel = outletService.getOutletByIdOutlet(id).get();
        
        JenisOutletModel jenisOutletModel = new JenisOutletModel();
        outletModel.setJenisOutlet(jenisOutletModel);
        List<JenisOutletModel> jenisOutletModels = jenisOutletService.getJenisOutletList();

        model.addAttribute("jenisOutlet", jenisOutletModels);
        model.addAttribute("outlet", outletModel);

        return "form-update-outlet";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateOutletSubmit(@PathVariable Long id, @ModelAttribute OutletModel outletModel, Model model) {

        OutletModel newOutletModel = outletService.changeOutlet(outletModel);

        model.addAttribute("outlet", newOutletModel);
        return "update-outlet";
    }

}
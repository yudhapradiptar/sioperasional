package sistem.operasional.sioperasional.controller;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sistem.operasional.sioperasional.model.ItemModel;
import sistem.operasional.sioperasional.model.KategoriItemModel;
import sistem.operasional.sioperasional.model.JenisItemModel;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.JenisItemService;

import java.util.List;

@Controller
@RequestMapping("/hardware-fulfillment/jenis")
public class JenisItemController {
    @Autowired
    JenisItemService jenisItemService;

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createJenisFormPage(Model model) {
        JenisItemModel newJenis = new JenisItemModel();
        model.addAttribute("jenis", newJenis);

        return "form-create-jenis";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createJenisSubmit(@ModelAttribute JenisItemModel jenisItem, Model model) {
        List<JenisItemModel> listJenisItem =  jenisItemService.getJenisItemList();
        try {
            for(JenisItemModel jenis : listJenisItem){
                if(jenisItem.getNamaJenisItem().equals(jenis.getNamaJenisItem())){
                    model.addAttribute("jenis",jenisItem);
                    return "fail-create-jenis";
                }
            }
            jenisItemService.createJenisItem(jenisItem);
            model.addAttribute("jenis",jenisItem);
            return "success-create-jenis";
        } catch (NullPointerException e) {
            return "form-create-jenis";
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String viewAllJenis(Model model){
        List<JenisItemModel> listAllJenisItem = jenisItemService.getJenisItemList();
        model.addAttribute("listAllJenisItem", listAllJenisItem);
        return "list-jenis-item";
    }

    @RequestMapping(value = "/delete")
    public String deleteJenisItem(@RequestParam("idJenisItem") Long idJenisItem, Model model){
        JenisItemModel deletedJenis = jenisItemService.getJenisItemByIdJenisItem(idJenisItem);
        model.addAttribute("jenis", deletedJenis);
        List<ItemModel> listItemKategori = itemService.getItemListByJenisItem(jenisItemService.getJenisItemByIdJenisItem(idJenisItem));
        if(listItemKategori.size()!=0){
            return "fail-delete-jenis";
        }
        jenisItemService.deleteJenisItem(deletedJenis);
        return "success-delete-jenis";
    }


}

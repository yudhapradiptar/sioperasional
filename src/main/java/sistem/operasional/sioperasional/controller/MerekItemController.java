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
import sistem.operasional.sioperasional.model.MerekItemModel;
import sistem.operasional.sioperasional.service.ItemService;
import sistem.operasional.sioperasional.service.MerekItemService;

import java.util.List;

@Controller
public class MerekItemController {
    @Autowired
    MerekItemService merekItemService;

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/hardware-fulfillment/merek/create", method = RequestMethod.GET)
    public String createMerekFormPage(Model model) {
        MerekItemModel newMerek = new MerekItemModel();
        model.addAttribute("merek", newMerek);

        return "form-create-merek";
    }

    @RequestMapping(value = "/hardware-fulfillment/merek", method = RequestMethod.POST)
    public String createMerekSubmit(@ModelAttribute MerekItemModel merekItem, Model model) {
        List<MerekItemModel> listItemMerek = new List<MerekItemModel>();
        try {
            if(listItemMerek.size()!=0){
                return "fail-delete-kategori";
            }
            merekItemService.createMerekItem(merekItem);
            return "success-create-merek";
        } catch (NullPointerException e) {
            return "form-create-merek";
        }
    }

    @RequestMapping(value = "/hardware-fulfillment/merek/all", method = RequestMethod.GET)
    public String viewAllMerek(Model model){
        List<MerekItemModel> listAllMerekItem = merekItemService.getMerekItemList();
        model.addAttribute("listAllMerekItem", listAllMerekItem);
        return "list-merek-item";
    }

    @RequestMapping(value = "/hardware-fulfillment/merek/delete")
    public String deleteMerekItem(@RequestParam("idMerekItem") Long idMerekItem, Model model){
        List<ItemModel> listItemKategori = itemService.getItemListByMerekItem(merekItemService.getMerekItemByIdMerekItem(idMerekItem));
        if(listItemKategori.size()!=0){
            return "fail-delete-merek";
        }
        merekItemService.deleteMerekItem(merekItemService.getMerekItemByIdMerekItem(idMerekItem));
        return "success-delete-merek";
    }


}
